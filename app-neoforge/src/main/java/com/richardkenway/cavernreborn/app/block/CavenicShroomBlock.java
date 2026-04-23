package com.richardkenway.cavernreborn.app.block;

import javax.annotation.Nullable;

import com.richardkenway.cavernreborn.app.registry.ModBlockTags;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.mojang.serialization.MapCodec;
import com.richardkenway.cavernreborn.core.flora.CavenicOrbDropPolicy;
import com.richardkenway.cavernreborn.core.flora.CavenicOrbDropResult;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class CavenicShroomBlock extends BushBlock {
    public static final MapCodec<CavenicShroomBlock> CODEC = simpleCodec(CavenicShroomBlock::new);

    private static final int SPREAD_CHECK_RADIUS = 4;
    private static final int SPREAD_VERTICAL_RADIUS = 1;
    private static final int MAX_NEARBY_SHROOMS = 4;
    private static final int SPREAD_ATTEMPTS = 4;
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);

    public CavenicShroomBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<CavenicShroomBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlockTags.CAVENIC_SHROOM_PLACEABLE_ON);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        trySpread(level, pos, random);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        applyCollisionEffect(level, entity);
    }

    public void applyCollisionEffect(Level level, Entity entity) {
        if (!(level instanceof ServerLevel) || !(entity instanceof LivingEntity living)) {
            return;
        }

        living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 40));
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
        ItemInteractionResult shearHarvestResult = tryShearHarvest(stack, state, level, pos, player, hand);
        if (shearHarvestResult != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION) {
            return shearHarvestResult;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void playerDestroy(
        Level level,
        Player player,
        BlockPos pos,
        BlockState state,
        @Nullable BlockEntity blockEntity,
        ItemStack tool
    ) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (player.isCreative() || tool.is(Items.SHEARS)) {
            return;
        }

        CavenicOrbDropResult result = CavenicOrbDropPolicy.evaluate(
            false,
            serverLevel.random.nextInt(CavenicOrbDropPolicy.NORMAL_DROP_ROLL_BOUND),
            state.is(this)
        );
        if (result.dropsOrb()) {
            popResource(serverLevel, pos, new ItemStack(ModRegistries.CAVENIC_ORB.get(), result.orbCount()));
        }
    }

    public ItemInteractionResult tryShearHarvest(
        ItemStack stack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand
    ) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return stack.is(Items.SHEARS) ? ItemInteractionResult.sidedSuccess(true) : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        return tryShearHarvest(
            stack,
            state,
            serverLevel,
            pos,
            player,
            hand,
            serverLevel.random.nextInt(CavenicOrbDropPolicy.SHEAR_DROP_ROLL_BOUND)
        );
    }

    public ItemInteractionResult tryShearHarvest(
        ItemStack stack,
        BlockState state,
        ServerLevel level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        int roll
    ) {
        if (!stack.is(Items.SHEARS) || !state.is(this)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        CavenicOrbDropResult result = CavenicOrbDropPolicy.evaluate(true, roll, true);
        popResource(level, pos, new ItemStack(ModRegistries.CAVENIC_SHROOM_ITEM.get()));
        if (result.dropsOrb()) {
            popResource(level, pos, new ItemStack(ModRegistries.CAVENIC_ORB.get(), result.orbCount()));
        }

        level.removeBlock(pos, false);
        level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, level.getBlockState(pos)));
        stack.hurtAndBreak(
            1,
            level,
            player instanceof ServerPlayer serverPlayer ? serverPlayer : null,
            item -> player.onEquippedItemBroken(item, LivingEntity.getSlotForHand(hand))
        );
        return ItemInteractionResult.CONSUME;
    }

    boolean trySpread(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(25) != 0) {
            return false;
        }
        if (countNearbyShrooms(level, pos) > MAX_NEARBY_SHROOMS) {
            return false;
        }

        for (int i = 0; i < SPREAD_ATTEMPTS; i++) {
            BlockPos candidate = pos.offset(
                random.nextInt(SPREAD_CHECK_RADIUS * 2 + 1) - SPREAD_CHECK_RADIUS,
                random.nextInt(SPREAD_VERTICAL_RADIUS * 2 + 1) - SPREAD_VERTICAL_RADIUS,
                random.nextInt(SPREAD_CHECK_RADIUS * 2 + 1) - SPREAD_CHECK_RADIUS
            );
            if (level.isEmptyBlock(candidate) && defaultBlockState().canSurvive(level, candidate)) {
                level.setBlock(candidate, defaultBlockState(), Block.UPDATE_ALL);
                return true;
            }
        }

        return false;
    }

    private int countNearbyShrooms(ServerLevel level, BlockPos pos) {
        int count = 0;
        for (BlockPos nearbyPos : BlockPos.betweenClosed(
            pos.offset(-SPREAD_CHECK_RADIUS, -SPREAD_VERTICAL_RADIUS, -SPREAD_CHECK_RADIUS),
            pos.offset(SPREAD_CHECK_RADIUS, SPREAD_VERTICAL_RADIUS, SPREAD_CHECK_RADIUS)
        )) {
            if (level.getBlockState(nearbyPos).is(this)) {
                count++;
            }
        }
        return count;
    }
}
