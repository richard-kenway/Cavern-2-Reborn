package com.richardkenway.cavernreborn.core.progression;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record CavernPlayerProgressionState(
    UUID playerId,
    int countedBlocks,
    int progressionScore,
    Map<String, Integer> minedBlocksById
) {
    public CavernPlayerProgressionState {
        Objects.requireNonNull(playerId, "playerId");
        if (countedBlocks < 0) {
            throw new IllegalArgumentException("countedBlocks must not be negative");
        }
        if (progressionScore < 0) {
            throw new IllegalArgumentException("progressionScore must not be negative");
        }

        Map<String, Integer> normalizedBlockCounts = new LinkedHashMap<>();
        Objects.requireNonNull(minedBlocksById, "minedBlocksById").forEach((blockId, count) -> {
            String normalizedBlockId = requireText(blockId, "blockId");
            if (count == null || count <= 0) {
                throw new IllegalArgumentException("block count must be positive");
            }
            normalizedBlockCounts.put(normalizedBlockId, count);
        });
        minedBlocksById = Collections.unmodifiableMap(normalizedBlockCounts);

        int derivedCountedBlocks = normalizedBlockCounts.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
        if (derivedCountedBlocks != countedBlocks) {
            throw new IllegalArgumentException("countedBlocks must equal the sum of minedBlocksById");
        }
        if (countedBlocks == 0 && progressionScore != 0) {
            throw new IllegalArgumentException("progressionScore must be zero when no blocks are counted");
        }
    }

    public static CavernPlayerProgressionState empty(UUID playerId) {
        return new CavernPlayerProgressionState(playerId, 0, 0, Map.of());
    }

    public boolean isEmpty() {
        return countedBlocks == 0;
    }

    public int minedBlockCount(String blockId) {
        return minedBlocksById.getOrDefault(requireText(blockId, "blockId"), 0);
    }

    public CavernPlayerProgressionState withMinedBlock(String blockId, int scoreDelta) {
        return withMinedBlock(blockId, scoreDelta, 0);
    }

    public CavernPlayerProgressionState withMinedBlock(String blockId, int baseScoreDelta, int bonusScoreDelta) {
        String normalizedBlockId = requireText(blockId, "blockId");
        if (baseScoreDelta <= 0) {
            throw new IllegalArgumentException("baseScoreDelta must be positive");
        }
        if (bonusScoreDelta < 0) {
            throw new IllegalArgumentException("bonusScoreDelta must not be negative");
        }

        Map<String, Integer> updatedCounts = new LinkedHashMap<>(minedBlocksById);
        updatedCounts.merge(normalizedBlockId, 1, Integer::sum);
        return new CavernPlayerProgressionState(
            playerId,
            countedBlocks + 1,
            progressionScore + baseScoreDelta + bonusScoreDelta,
            updatedCounts
        );
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
