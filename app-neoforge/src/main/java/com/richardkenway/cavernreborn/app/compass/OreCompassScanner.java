package com.richardkenway.cavernreborn.app.compass;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.registry.ModBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class OreCompassScanner {
    public Optional<OreCompassTarget> findNearestTarget(
        ServerLevel level,
        BlockPos origin,
        int horizontalRadius,
        int verticalRadius
    ) {
        Objects.requireNonNull(level, "level");
        BlockPos scanOrigin = Objects.requireNonNull(origin, "origin").immutable();
        if (horizontalRadius < 0) {
            throw new IllegalArgumentException("horizontalRadius must not be negative");
        }
        if (verticalRadius < 0) {
            throw new IllegalArgumentException("verticalRadius must not be negative");
        }

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        OreCompassTarget bestTarget = null;
        long bestSquaredDistance = Long.MAX_VALUE;
        int bestAbsoluteVerticalOffset = Integer.MAX_VALUE;

        for (int y = scanOrigin.getY() - verticalRadius; y <= scanOrigin.getY() + verticalRadius; y++) {
            for (int z = scanOrigin.getZ() - horizontalRadius; z <= scanOrigin.getZ() + horizontalRadius; z++) {
                for (int x = scanOrigin.getX() - horizontalRadius; x <= scanOrigin.getX() + horizontalRadius; x++) {
                    cursor.set(x, y, z);
                    if (!level.isLoaded(cursor)) {
                        continue;
                    }

                    BlockState state = level.getBlockState(cursor);
                    if (state.isAir() || !state.is(ModBlockTags.ORE_COMPASS_TARGETS)) {
                        continue;
                    }

                    BlockEntity blockEntity = level.getBlockEntity(cursor);
                    if (blockEntity != null) {
                        continue;
                    }

                    String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
                    long squaredDistance = squaredDistance(scanOrigin, cursor);
                    int absoluteVerticalOffset = Math.abs(cursor.getY() - scanOrigin.getY());
                    if (!isBetterCandidate(
                        cursor,
                        blockId,
                        squaredDistance,
                        absoluteVerticalOffset,
                        bestTarget,
                        bestSquaredDistance,
                        bestAbsoluteVerticalOffset
                    )) {
                        continue;
                    }

                    bestTarget = new OreCompassTarget(cursor.immutable(), blockId);
                    bestSquaredDistance = squaredDistance;
                    bestAbsoluteVerticalOffset = absoluteVerticalOffset;
                }
            }
        }

        return Optional.ofNullable(bestTarget);
    }

    private static boolean isBetterCandidate(
        BlockPos candidatePos,
        String candidateBlockId,
        long candidateSquaredDistance,
        int candidateAbsoluteVerticalOffset,
        OreCompassTarget bestTarget,
        long bestSquaredDistance,
        int bestAbsoluteVerticalOffset
    ) {
        if (bestTarget == null) {
            return true;
        }
        if (candidateSquaredDistance != bestSquaredDistance) {
            return candidateSquaredDistance < bestSquaredDistance;
        }
        if (candidateAbsoluteVerticalOffset != bestAbsoluteVerticalOffset) {
            return candidateAbsoluteVerticalOffset < bestAbsoluteVerticalOffset;
        }
        int blockIdOrder = candidateBlockId.compareTo(bestTarget.blockId());
        if (blockIdOrder != 0) {
            return blockIdOrder < 0;
        }
        return comparePositions(candidatePos, bestTarget.pos()) < 0;
    }

    private static long squaredDistance(BlockPos origin, BlockPos target) {
        long dx = target.getX() - origin.getX();
        long dy = target.getY() - origin.getY();
        long dz = target.getZ() - origin.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    private static int comparePositions(BlockPos left, BlockPos right) {
        int byX = Integer.compare(left.getX(), right.getX());
        if (byX != 0) {
            return byX;
        }

        int byY = Integer.compare(left.getY(), right.getY());
        if (byY != 0) {
            return byY;
        }

        return Integer.compare(left.getZ(), right.getZ());
    }
}
