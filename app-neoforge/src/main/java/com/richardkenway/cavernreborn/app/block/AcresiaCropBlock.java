package com.richardkenway.cavernreborn.app.block;

import com.richardkenway.cavernreborn.app.registry.ModBlockTags;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.farming.AcresiaHarvestPolicy;
import com.richardkenway.cavernreborn.core.farming.AcresiaHarvestResult;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class AcresiaCropBlock extends CropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, AcresiaHarvestPolicy.MAX_AGE);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public AcresiaCropBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[getAge(state)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlockTags.ACRESIA_PLANTABLE_ON);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return AcresiaHarvestPolicy.MAX_AGE;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = getAge(state);
        if (age >= getMaxAge()) {
            return;
        }

        float growthSpeed = getGrowthSpeed(state, level, pos);
        if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
            level.setBlock(pos, getStateForAge(age + 1), 2);
        }
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
    protected ItemLike getBaseSeedId() {
        return ModRegistries.ACRESIA_SEEDS.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public ItemInteractionResult tryShearHarvest(
        ItemStack stack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand
    ) {
        if (!stack.is(Items.SHEARS) || !isMaxAge(state)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.sidedSuccess(true);
        }

        int fortuneLevel = fortuneLevel(serverLevel, stack);
        AcresiaHarvestResult result = AcresiaHarvestPolicy.evaluateShearHarvest(getAge(state), fortuneLevel, serverLevel.random.nextInt(3));
        if (!result.specialHarvest()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        for (int i = 0; i < result.fruitCount(); i++) {
            popResource(serverLevel, pos, new ItemStack(ModRegistries.ACRESIA_FRUITS.get()));
        }

        BlockState regrownState = getStateForAge(result.resetAge());
        serverLevel.setBlock(pos, regrownState, 2);
        serverLevel.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
        serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, regrownState));

        stack.hurtAndBreak(
            1,
            serverLevel,
            player instanceof ServerPlayer serverPlayer ? serverPlayer : null,
            item -> player.onEquippedItemBroken(item, LivingEntity.getSlotForHand(hand))
        );
        return ItemInteractionResult.CONSUME;
    }

    private static int fortuneLevel(Level level, ItemStack tool) {
        if (tool.isEmpty()) {
            return 0;
        }

        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return EnchantmentHelper.getItemEnchantmentLevel(enchantments.getOrThrow(Enchantments.FORTUNE), tool);
    }
}
