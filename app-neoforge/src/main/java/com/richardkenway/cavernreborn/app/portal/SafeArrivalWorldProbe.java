package com.richardkenway.cavernreborn.app.portal;

import java.util.Set;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

final class SafeArrivalWorldProbe implements CavernArrivalPlacementProbe {
    private static Set<net.minecraft.world.level.block.Block> dangerousBlocks;

    private final ServerLevel level;

    SafeArrivalWorldProbe(ServerLevel level) {
        this.level = level;
    }

    private static Set<net.minecraft.world.level.block.Block> dangerousBlocks() {
        if (dangerousBlocks == null) {
            dangerousBlocks = Set.of(
                Blocks.LAVA,
                Blocks.MAGMA_BLOCK,
                Blocks.CACTUS,
                Blocks.POINTED_DRIPSTONE,
                Blocks.FIRE,
                Blocks.SOUL_FIRE,
                Blocks.CAMPFIRE,
                Blocks.SOUL_CAMPFIRE
            );
        }
        return dangerousBlocks;
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
        if (level == null) {
            return false;
        }
        return y > level.getMinBuildHeight() && y + 1 < level.getMaxBuildHeight();
    }

    private static boolean isDangerousBlock(BlockState state) {
        return dangerousBlocks().contains(state.getBlock());
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
