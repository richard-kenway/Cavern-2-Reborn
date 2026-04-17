package com.richardkenway.cavernreborn.app.block;

import java.util.Objects;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameDetector;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionOutcome;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.NeoForgeCavernPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.WorldPortalFrameAccess;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class CavernPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);

    private static final VoxelShape X_AXIS_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    private static final VoxelShape Z_AXIS_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    private final Supplier<CavernPortalInteractionService> interactionServiceSupplier;

    public CavernPortalBlock(BlockBehaviour.Properties properties, Supplier<CavernPortalInteractionService> interactionServiceSupplier) {
        super(properties);
        this.interactionServiceSupplier = Objects.requireNonNull(interactionServiceSupplier, "interactionServiceSupplier");
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide()) {
            return;
        }

        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (shouldIgnoreCollisionEntry(
            serverPlayer.isAlive(),
            serverPlayer.isSpectator(),
            serverPlayer.isPassenger(),
            serverPlayer.isVehicle(),
            serverPlayer.isOnPortalCooldown()
        )) {
            return;
        }

        CavernPortalInteractionContext context = NeoForgeCavernPortalInteractionContext.forCollision(serverPlayer, level, pos);
        CavernPortalInteractionOutcome outcome = interactionServiceSupplier.get().use(context);
        if (shouldApplyPortalCooldown(outcome.handled())) {
            serverPlayer.setPortalCooldown();
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(AXIS, context.getHorizontalDirection().getAxis());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        if (rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90) {
            return state.cycle(AXIS);
        }

        return state;
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return rotate(state, mirror.getRotation(state.getValue(AXIS) == Direction.Axis.X ? Direction.NORTH : Direction.EAST));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state.getValue(AXIS));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!hasValidFrame(state, pos, new WorldPortalFrameAccess(level, this))) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    static boolean hasValidFrame(BlockState state, BlockPos pos, CavernPortalFrameDetector.FrameAccess frameAccess) {
        return hasValidFrame(state.getValue(AXIS), pos, frameAccess);
    }

    static boolean hasValidFrame(Direction.Axis axis, BlockPos pos, CavernPortalFrameDetector.FrameAccess frameAccess) {
        return new CavernPortalFrameDetector(frameAccess)
            .detect(pos)
            .filter(frame -> frame.axis() == axis && frame.portalBlockCount() > 0)
            .isPresent();
    }

    static VoxelShape shapeFor(Direction.Axis axis) {
        return axis == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
    }

    static boolean shouldIgnoreCollisionEntry(
        boolean alive,
        boolean spectator,
        boolean passenger,
        boolean vehicle,
        boolean onPortalCooldown
    ) {
        return !alive
            || spectator
            || passenger
            || vehicle
            || onPortalCooldown;
    }

    static boolean shouldApplyPortalCooldown(boolean handled) {
        return handled;
    }
}
