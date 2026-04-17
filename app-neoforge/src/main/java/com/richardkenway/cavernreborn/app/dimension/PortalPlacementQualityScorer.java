package com.richardkenway.cavernreborn.app.dimension;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public final class PortalPlacementQualityScorer {
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

    private static final int PORTAL_INNER_WIDTH = 2;
    private static final int PORTAL_INNER_HEIGHT = 3;
    private static final int HORIZONTAL_DISTANCE_WEIGHT = 24;
    private static final int VERTICAL_DISTANCE_WEIGHT = 18;
    private static final int SUPPORT_WEIGHT = 12;
    private static final int HAZARD_WEIGHT = 160;
    private static final int REPLACEMENT_WEIGHT = 10;

    private PortalPlacementQualityScorer() {
    }

    public static PortalPlacementCandidate evaluate(
        ServerLevel level,
        Block frameBlock,
        Block portalBlock,
        BlockPos bottomLeft,
        Direction.Axis axis,
        int targetX,
        int targetY,
        int targetZ
    ) {
        int horizontalDistance = Math.abs(bottomLeft.getX() - targetX) + Math.abs(bottomLeft.getZ() - targetZ);
        int verticalDistance = Math.abs(bottomLeft.getY() - targetY);
        int supportBlocks = countSupportBlocks(level, bottomLeft, axis);
        int nearbyHazards = countNearbyHazards(level, bottomLeft, axis);
        int replacementBlocks = countReplacementBlocks(level, frameBlock, portalBlock, bottomLeft, axis);

        return new PortalPlacementCandidate(
            bottomLeft,
            axis,
            horizontalDistance,
            verticalDistance,
            supportBlocks,
            nearbyHazards,
            replacementBlocks
        );
    }

    public static boolean isBetterCandidate(PortalPlacementCandidate candidate, PortalPlacementCandidate incumbent) {
        return isBetterCandidate(candidate, incumbent, null);
    }

    public static boolean isBetterCandidate(
        PortalPlacementCandidate candidate,
        PortalPlacementCandidate incumbent,
        Direction.Axis preferredAxis
    ) {
        if (incumbent == null) {
            return true;
        }

        if (candidate.score() != incumbent.score()) {
            return candidate.score() > incumbent.score();
        }

        if (candidate.horizontalDistance() != incumbent.horizontalDistance()) {
            return candidate.horizontalDistance() < incumbent.horizontalDistance();
        }

        if (candidate.verticalDistance() != incumbent.verticalDistance()) {
            return candidate.verticalDistance() < incumbent.verticalDistance();
        }

        if (candidate.nearbyHazards() != incumbent.nearbyHazards()) {
            return candidate.nearbyHazards() < incumbent.nearbyHazards();
        }

        if (candidate.replacementBlocks() != incumbent.replacementBlocks()) {
            return candidate.replacementBlocks() < incumbent.replacementBlocks();
        }

        if (candidate.supportBlocks() != incumbent.supportBlocks()) {
            return candidate.supportBlocks() > incumbent.supportBlocks();
        }

        if (preferredAxis != null && candidate.axis() != incumbent.axis()) {
            return candidate.axis() == preferredAxis;
        }

        if (candidate.axis() != incumbent.axis()) {
            return candidate.axis() == Direction.Axis.X;
        }

        if (candidate.bottomLeft().getY() != incumbent.bottomLeft().getY()) {
            return candidate.bottomLeft().getY() < incumbent.bottomLeft().getY();
        }

        if (candidate.bottomLeft().getX() != incumbent.bottomLeft().getX()) {
            return candidate.bottomLeft().getX() < incumbent.bottomLeft().getX();
        }

        return candidate.bottomLeft().getZ() < incumbent.bottomLeft().getZ();
    }

    private static int countSupportBlocks(ServerLevel level, BlockPos bottomLeft, Direction.Axis axis) {
        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        int supportBlocks = 0;

        for (int dx = -1; dx <= PORTAL_INNER_WIDTH; dx++) {
            BlockPos supportPos = bottomLeft.relative(right, dx).below(2);
            if (isStableSupport(level.getBlockState(supportPos))) {
                supportBlocks++;
            }
        }

        return supportBlocks;
    }

    private static int countNearbyHazards(ServerLevel level, BlockPos bottomLeft, Direction.Axis axis) {
        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        Direction normal = axis == Direction.Axis.X ? Direction.SOUTH : Direction.EAST;
        Set<BlockPos> scannedPositions = new HashSet<>();
        int hazards = 0;

        for (int dx = -1; dx <= PORTAL_INNER_WIDTH; dx++) {
            for (int dy = -1; dy <= PORTAL_INNER_HEIGHT; dy++) {
                BlockPos slabPos = bottomLeft.relative(right, dx).above(dy);
                for (int dn = -1; dn <= 1; dn++) {
                    BlockPos candidate = slabPos.relative(normal, dn);
                    if (scannedPositions.add(candidate) && isHazardous(level.getBlockState(candidate))) {
                        hazards++;
                    }
                }
            }
        }

        return hazards;
    }

    private static int countReplacementBlocks(
        ServerLevel level,
        Block frameBlock,
        Block portalBlock,
        BlockPos bottomLeft,
        Direction.Axis axis
    ) {
        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        Direction left = right.getOpposite();
        int replacementBlocks = 0;

        for (int dx = 0; dx < PORTAL_INNER_WIDTH; dx++) {
            for (int dy = 0; dy < PORTAL_INNER_HEIGHT; dy++) {
                if (requiresReplacement(level.getBlockState(bottomLeft.relative(right, dx).above(dy)), frameBlock, portalBlock)) {
                    replacementBlocks++;
                }
            }
        }

        for (int dx = -1; dx <= PORTAL_INNER_WIDTH; dx++) {
            if (requiresReplacement(level.getBlockState(bottomLeft.relative(right, dx).below()), frameBlock, portalBlock)) {
                replacementBlocks++;
            }

            if (requiresReplacement(level.getBlockState(bottomLeft.relative(right, dx).above(PORTAL_INNER_HEIGHT)), frameBlock, portalBlock)) {
                replacementBlocks++;
            }
        }

        for (int dy = 0; dy < PORTAL_INNER_HEIGHT; dy++) {
            if (requiresReplacement(level.getBlockState(bottomLeft.relative(left).above(dy)), frameBlock, portalBlock)) {
                replacementBlocks++;
            }

            if (requiresReplacement(level.getBlockState(bottomLeft.relative(right, PORTAL_INNER_WIDTH).above(dy)), frameBlock, portalBlock)) {
                replacementBlocks++;
            }
        }

        return replacementBlocks;
    }

    public static boolean requiresReplacement(BlockState state, Block frameBlock, Block portalBlock) {
        return !state.isAir() && !state.is(frameBlock) && !state.is(portalBlock);
    }

    private static boolean isStableSupport(BlockState state) {
        return state.blocksMotion() && !hasDangerousFluid(state) && !isDangerousBlock(state) && !state.canBeReplaced();
    }

    private static boolean isHazardous(BlockState state) {
        return hasDangerousFluid(state) || isDangerousBlock(state);
    }

    private static boolean isDangerousBlock(BlockState state) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return blockId != null && DANGEROUS_BLOCK_IDS.contains(blockId.toString());
    }

    private static boolean hasDangerousFluid(BlockState state) {
        FluidState fluidState = state.getFluidState();
        return fluidState.is(FluidTags.LAVA) || fluidState.is(FluidTags.WATER);
    }

    public record PortalPlacementCandidate(
        BlockPos bottomLeft,
        Direction.Axis axis,
        int horizontalDistance,
        int verticalDistance,
        int supportBlocks,
        int nearbyHazards,
        int replacementBlocks
    ) {
        int score() {
            return supportBlocks * SUPPORT_WEIGHT
                - nearbyHazards * HAZARD_WEIGHT
                - replacementBlocks * REPLACEMENT_WEIGHT
                - horizontalDistance * HORIZONTAL_DISTANCE_WEIGHT
                - verticalDistance * VERTICAL_DISTANCE_WEIGHT;
        }
    }
}
