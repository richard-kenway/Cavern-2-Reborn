package com.richardkenway.cavernreborn.app.block;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameDetector;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionOutcome;
import com.richardkenway.cavernreborn.app.portal.NonPlayerPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.CavernNonPlayerPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.PortalCollisionEligibilityPolicy;
import com.richardkenway.cavernreborn.app.portal.NeoForgeCavernPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.NeoForgeNonPlayerPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.WorldPortalFrameAccess;
import com.richardkenway.cavernreborn.app.progression.CavernCatalogGuiOpener;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class CavernPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);

    private static final VoxelShape X_AXIS_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    private static final VoxelShape Z_AXIS_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    private final Supplier<CavernPortalInteractionService> interactionServiceSupplier;
    private final Supplier<CavernNonPlayerPortalInteractionService> nonPlayerInteractionServiceSupplier;
    private final Supplier<CavernCatalogGuiOpener> catalogGuiOpenerSupplier;

    public CavernPortalBlock(
        BlockBehaviour.Properties properties,
        Supplier<CavernPortalInteractionService> interactionServiceSupplier,
        Supplier<CavernNonPlayerPortalInteractionService> nonPlayerInteractionServiceSupplier,
        Supplier<CavernCatalogGuiOpener> catalogGuiOpenerSupplier
    ) {
        super(properties);
        this.interactionServiceSupplier = Objects.requireNonNull(interactionServiceSupplier, "interactionServiceSupplier");
        this.nonPlayerInteractionServiceSupplier = Objects.requireNonNull(nonPlayerInteractionServiceSupplier, "nonPlayerInteractionServiceSupplier");
        this.catalogGuiOpenerSupplier = Objects.requireNonNull(catalogGuiOpenerSupplier, "catalogGuiOpenerSupplier");
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return routeUse(
            level.isClientSide(),
            player instanceof ServerPlayer,
            () -> openCatalogGui((ServerPlayer) player)
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(
        ItemStack stack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {
        return routeUseWithItem(
            level.isClientSide(),
            player instanceof ServerPlayer,
            () -> openCatalogGui((ServerPlayer) player)
        );
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide()) {
            return;
        }

        CollisionContract contract = CollisionContract.from(entity);
        CavernPortalInteractionOutcome outcome = contract.dispatch(
            level,
            pos,
            entity,
            this::runPlayerCollisionTransport,
            this::runNonPlayerCollisionTransport
        );

        if (contract.shouldApplyPortalCooldown(outcome)) {
            entity.setPortalCooldown();
        }
    }

    private CavernPortalInteractionOutcome runPlayerCollisionTransport(Level level, BlockPos pos, Entity entity) {
        ServerPlayer serverPlayer = (ServerPlayer) entity;
        CavernPortalInteractionContext context = NeoForgeCavernPortalInteractionContext.forCollision(serverPlayer, level, pos);
        return interactionServiceSupplier.get().use(context);
    }

    private CavernPortalInteractionOutcome runNonPlayerCollisionTransport(Level level, BlockPos pos, Entity entity) {
        NonPlayerPortalInteractionContext context = NeoForgeNonPlayerPortalInteractionContext.forCollision(entity, level, pos);
        return nonPlayerInteractionServiceSupplier.get().use(context);
    }

    private boolean openCatalogGui(ServerPlayer player) {
        return catalogGuiOpenerSupplier.get().open(player);
    }

    static InteractionResult routeUse(boolean clientSide, boolean serverPlayerActor, BooleanSupplier openCatalogGuiAction) {
        Objects.requireNonNull(openCatalogGuiAction, "openCatalogGuiAction");
        if (clientSide) {
            return InteractionResult.SUCCESS;
        }
        if (!serverPlayerActor) {
            return InteractionResult.PASS;
        }
        return openCatalogGuiAction.getAsBoolean() ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    static ItemInteractionResult routeUseWithItem(
        boolean clientSide,
        boolean serverPlayerActor,
        BooleanSupplier openCatalogGuiAction
    ) {
        Objects.requireNonNull(openCatalogGuiAction, "openCatalogGuiAction");
        if (clientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        if (!serverPlayerActor) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return openCatalogGuiAction.getAsBoolean()
            ? ItemInteractionResult.CONSUME
            : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @FunctionalInterface
    interface CollisionDispatcher {
        CavernPortalInteractionOutcome dispatch(Level level, BlockPos pos, Entity entity);
    }

    static final class CollisionContract {
        enum CollisionType {
            IGNORE,
            PLAYER,
            NON_PLAYER
        }

        private final CollisionType collisionType;

        private CollisionContract(CollisionType collisionType) {
            this.collisionType = collisionType;
        }

        static CollisionContract from(Entity entity) {
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility eligibility =
                PortalCollisionEligibilityPolicy.classify(entity, entity.isOnPortalCooldown());
            return from(eligibility, entity instanceof ServerPlayer);
        }

        static CollisionContract from(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility eligibility,
            boolean serverPlayerEntity
        ) {
            if (!eligibility.shouldTriggerPortalCollision()) {
                return new CollisionContract(CollisionType.IGNORE);
            }

            if (serverPlayerEntity && eligibility.shouldTransportPlayer()) {
                return new CollisionContract(CollisionType.PLAYER);
            }

            if (!serverPlayerEntity && eligibility.shouldTransportNonPlayer()) {
                return new CollisionContract(CollisionType.NON_PLAYER);
            }

            return new CollisionContract(CollisionType.IGNORE);
        }

        CavernPortalInteractionOutcome dispatch(
            Level level,
            BlockPos pos,
            Entity entity,
            CollisionDispatcher playerDispatcher,
            CollisionDispatcher nonPlayerDispatcher
        ) {
            return switch (collisionType) {
                case IGNORE -> CavernPortalInteractionOutcome.unhandled();
                case PLAYER -> playerDispatcher.dispatch(level, pos, entity);
                case NON_PLAYER -> nonPlayerDispatcher.dispatch(level, pos, entity);
            };
        }

        boolean shouldApplyPortalCooldown(CavernPortalInteractionOutcome outcome) {
            return outcome.handled();
        }

        boolean isPlayerDispatch() {
            return collisionType == CollisionType.PLAYER;
        }

        boolean isNonPlayerDispatch() {
            return collisionType == CollisionType.NON_PLAYER;
        }

        boolean isIgnoreDispatch() {
            return collisionType == CollisionType.IGNORE;
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
}
