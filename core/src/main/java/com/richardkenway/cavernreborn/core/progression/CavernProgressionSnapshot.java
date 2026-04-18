package com.richardkenway.cavernreborn.core.progression;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public record CavernProgressionSnapshot(
    UUID playerId,
    int countedBlocks,
    int progressionScore,
    CavernProgressionRank rank,
    Map<String, Integer> minedBlocksById
) {
    public CavernProgressionSnapshot {
        Objects.requireNonNull(playerId, "playerId");
        if (countedBlocks < 0) {
            throw new IllegalArgumentException("countedBlocks must not be negative");
        }
        if (progressionScore < 0) {
            throw new IllegalArgumentException("progressionScore must not be negative");
        }
        Objects.requireNonNull(rank, "rank");
        minedBlocksById = Collections.unmodifiableMap(new LinkedHashMap<>(Objects.requireNonNull(minedBlocksById, "minedBlocksById")));
    }

    public Optional<CavernProgressionRank> nextRank() {
        return rank.next();
    }

    public boolean hasUnlocked(CavernProgressionUnlock unlock) {
        return Objects.requireNonNull(unlock, "unlock").isUnlocked(rank);
    }

    public int pointsToNextRank() {
        return nextRank()
            .map(nextRank -> Math.max(0, nextRank.threshold() - progressionScore))
            .orElse(0);
    }
}
