package com.richardkenway.cavernreborn.app.mining;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class MiningAssistBlockCollector {
    private static final List<Direction> NEIGHBOR_ORDER = List.of(
        Direction.UP,
        Direction.DOWN,
        Direction.NORTH,
        Direction.SOUTH,
        Direction.WEST,
        Direction.EAST
    );

    public List<BlockPos> collectSameBlockVein(
        ServerLevel level,
        BlockPos origin,
        BlockState originState,
        BiPredicate<BlockPos, BlockState> allowedTarget,
        int maxExtraBlocks
    ) {
        Objects.requireNonNull(level, "level");
        BlockPos normalizedOrigin = Objects.requireNonNull(origin, "origin").immutable();
        BlockState normalizedOriginState = Objects.requireNonNull(originState, "originState");
        BiPredicate<BlockPos, BlockState> normalizedAllowedTarget = Objects.requireNonNull(allowedTarget, "allowedTarget");
        if (maxExtraBlocks < 0) {
            throw new IllegalArgumentException("maxExtraBlocks must not be negative");
        }
        if (maxExtraBlocks == 0) {
            return List.of();
        }

        Block originBlock = normalizedOriginState.getBlock();
        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        List<BlockPos> result = new ArrayList<>();
        visited.add(normalizedOrigin);
        queue.add(normalizedOrigin);

        while (!queue.isEmpty() && result.size() < maxExtraBlocks) {
            BlockPos current = queue.removeFirst();
            for (Direction direction : NEIGHBOR_ORDER) {
                BlockPos neighbor = current.relative(direction).immutable();
                if (!visited.add(neighbor)) {
                    continue;
                }

                BlockState neighborState = level.getBlockState(neighbor);
                if (!neighborState.is(originBlock)) {
                    continue;
                }
                if (!normalizedAllowedTarget.test(neighbor, neighborState)) {
                    continue;
                }

                result.add(neighbor);
                if (result.size() >= maxExtraBlocks) {
                    break;
                }
                queue.addLast(neighbor);
            }
        }

        return List.copyOf(result);
    }
}
