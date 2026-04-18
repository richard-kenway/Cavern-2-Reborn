package com.richardkenway.cavernreborn.core.progression;

import java.util.Optional;

public enum CavernProgressionRank {
    NOVICE("novice", 0),
    APPRENTICE("apprentice", 25),
    JOURNEYMAN("journeyman", 75),
    VETERAN("veteran", 175),
    MASTER("master", 325);

    private final String id;
    private final int threshold;

    CavernProgressionRank(String id, int threshold) {
        this.id = id;
        this.threshold = threshold;
    }

    public String id() {
        return id;
    }

    public int threshold() {
        return threshold;
    }

    public Optional<CavernProgressionRank> next() {
        CavernProgressionRank[] ranks = values();
        int nextOrdinal = ordinal() + 1;
        return nextOrdinal >= ranks.length ? Optional.empty() : Optional.of(ranks[nextOrdinal]);
    }

    public static CavernProgressionRank forScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("score must not be negative");
        }

        CavernProgressionRank resolved = NOVICE;
        for (CavernProgressionRank candidate : values()) {
            if (score >= candidate.threshold) {
                resolved = candidate;
            }
        }

        return resolved;
    }
}
