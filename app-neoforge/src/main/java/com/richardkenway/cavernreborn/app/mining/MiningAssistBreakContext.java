package com.richardkenway.cavernreborn.app.mining;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;

public final class MiningAssistBreakContext {
    private static final ThreadLocal<Set<UUID>> ACTIVE_PLAYERS = ThreadLocal.withInitial(HashSet::new);
    private static final ThreadLocal<Map<UUID, Set<BlockPos>>> SUPPRESSED_BREAKS = ThreadLocal.withInitial(HashMap::new);

    private MiningAssistBreakContext() {
    }

    public static boolean isAssistActive(UUID playerId) {
        return ACTIVE_PLAYERS.get().contains(playerId);
    }

    public static boolean isSuppressed(UUID playerId, BlockPos pos) {
        Set<BlockPos> positions = SUPPRESSED_BREAKS.get().get(playerId);
        return positions != null && positions.contains(pos.immutable());
    }

    public static <T> T withActiveAssist(UUID playerId, Supplier<T> action) {
        ACTIVE_PLAYERS.get().add(playerId);
        try {
            return action.get();
        } finally {
            ACTIVE_PLAYERS.get().remove(playerId);
        }
    }

    public static boolean withSuppressedBreak(UUID playerId, BlockPos pos, BooleanSupplier action) {
        Map<UUID, Set<BlockPos>> suppressed = SUPPRESSED_BREAKS.get();
        Set<BlockPos> positions = suppressed.computeIfAbsent(playerId, ignored -> new HashSet<>());
        BlockPos immutablePos = pos.immutable();
        positions.add(immutablePos);
        try {
            return action.getAsBoolean();
        } finally {
            positions.remove(immutablePos);
            if (positions.isEmpty()) {
                suppressed.remove(playerId);
            }
        }
    }
}
