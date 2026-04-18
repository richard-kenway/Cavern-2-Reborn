package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionConsequences;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUnlock;

public final class CavernPlayerProgressionStatusFormatter {
    private CavernPlayerProgressionStatusFormatter() {
    }

    public static String format(String playerName, CavernProgressionSnapshot snapshot) {
        String normalizedPlayerName = requireText(playerName, "playerName");
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        String nextRank = normalizedSnapshot.nextRank()
            .map(rank -> rank.id() + " in " + normalizedSnapshot.pointsToNextRank() + " score")
            .orElse("max rank reached");
        String minersInsight = normalizedSnapshot.hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT)
            ? "unlocked: Miner's Insight (+" + CavernProgressionConsequences.MINERS_INSIGHT_BONUS_EXPERIENCE
                + " bonus XP on counted ores in CAVERN)"
            : "next unlock: Miner's Insight at " + CavernProgressionUnlock.MINERS_INSIGHT.requiredRank().id();

        return "CAVERN rank for " + normalizedPlayerName
            + ": " + normalizedSnapshot.rank().id()
            + " | score " + normalizedSnapshot.progressionScore()
            + " | counted ores " + normalizedSnapshot.countedBlocks()
            + " | next " + nextRank
            + " | " + minersInsight;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
