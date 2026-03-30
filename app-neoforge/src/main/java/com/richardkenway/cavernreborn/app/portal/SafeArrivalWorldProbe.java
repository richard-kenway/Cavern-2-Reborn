package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Set;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

final class SafeArrivalWorldProbe implements CavernArrivalPlacementProbe {
    private static final Set<String> DANGEROUS_BLOCK_IDS = Set.of(
        "minecraft:lava",
        "minecraft:magma_block",
        "minecraft:cactus",
        "minecraft:pointed_dripstone",
        "minecraft:fire",
        "minecraft:soul_fire",
        "minecraft:campfire",
        "minecraft:soul_campfire"
    );

    private final ServerLevel level;

    SafeArrivalWorldProbe(ServerLevel level) {
        this.level = Objects.requireNonNull(level, "level");
    }

    @Override
    public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        if (!isWithinBounds(y, targetLevel)) {
            return false;
        }

        BlockPos feetPos = new BlockPos(x, y, z);
        BlockState feetState = targetLevel.getBlockState(feetPos);
        BlockState headState = targetLevel.getBlockState(feetPos.above());
        BlockState groundState = targetLevel.getBlockState(feetPos.below());
        return isSafeArrivalState(feetState, headState, groundState);
    }

    static boolean isSafeArrivalState(BlockState feetState, BlockState headState, BlockState groundState) {
        return isPassableForBody(feetState)
            && isPassableForBody(headState)
            && isSafeGround(groundState);
    }

    static boolean isPassableForBody(BlockState state) {
        if (state == null) {
            return false;
        }
        return !state.blocksMotion()
            && !hasDangerousFluid(state)
            && !isDangerousBlock(state);
    }

    static boolean isSafeGround(BlockState state) {
        if (state == null) {
            return false;
        }
        return state.blocksMotion()
            && !hasDangerousFluid(state)
            && !isDangerousBlock(state);
    }

    static boolean isWithinBounds(int y, Level level) {
        Objects.requireNonNull(level, "level");
        return y > level.getMinBuildHeight() && y + 1 < level.getMaxBuildHeight();
    }

    private static boolean isDangerousBlock(BlockState state) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return blockId != null && DANGEROUS_BLOCK_IDS.contains(blockId.toString());
    }

    private static boolean hasDangerousFluid(BlockState state) {
        FluidState fluidState = state.getFluidState();
        return fluidState.is(FluidTags.LAVA) || fluidState.is(FluidTags.WATER);
    }

    private ServerLevel resolveLevel(String targetDimensionId) {
        ResourceLocation targetLocation = CavernNeoForgeDimensions.resourceLocation(targetDimensionId);
        ResourceLocation currentLocation = level.dimension().location();

        if (targetLocation.equals(currentLocation)) {
            return level;
        }

        ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, targetLocation);
        MinecraftServer server = level.getServer();
        if (server == null) {
            throw new IllegalStateException("Level is not attached to a server");
        }

        ServerLevel targetLevel = server.getLevel(levelKey);
        if (targetLevel == null) {
            throw new IllegalStateException("Missing target dimension: " + targetDimensionId);
        }

        return targetLevel;
    }
}
