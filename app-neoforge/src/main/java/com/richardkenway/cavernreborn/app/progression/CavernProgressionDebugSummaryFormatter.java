package com.richardkenway.cavernreborn.app.progression;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

public final class CavernProgressionDebugSummaryFormatter {
    private static final int MAX_BLOCK_ENTRIES = 5;

    private CavernProgressionDebugSummaryFormatter() {
    }

    public static String format(String playerName, CavernProgressionSnapshot snapshot) {
        String normalizedPlayerName = requireText(playerName, "playerName");
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        String topBlocks = normalizedSnapshot.minedBlocksById().isEmpty()
            ? "none"
            : normalizedSnapshot.minedBlocksById().entrySet().stream()
                .sorted(
                    Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
                )
                .limit(MAX_BLOCK_ENTRIES)
                .map(entry -> shortBlockId(entry.getKey()) + "=" + entry.getValue())
                .collect(Collectors.joining(", "));
        String nextRank = normalizedSnapshot.nextRank()
            .map(rank -> rank.id() + "@" + rank.threshold())
            .orElse("none");

        return "CAVERN progression for " + normalizedPlayerName
            + ": rank=" + normalizedSnapshot.rank().id()
            + ", score=" + normalizedSnapshot.progressionScore()
            + ", counted_blocks=" + normalizedSnapshot.countedBlocks()
            + ", next_rank=" + nextRank
            + ", points_to_next=" + normalizedSnapshot.pointsToNextRank()
            + ", top_blocks=" + topBlocks;
    }

    private static String shortBlockId(String blockId) {
        String normalizedBlockId = requireText(blockId, "blockId");
        int separator = normalizedBlockId.indexOf(':');
        return separator >= 0 ? normalizedBlockId.substring(separator + 1) : normalizedBlockId;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
