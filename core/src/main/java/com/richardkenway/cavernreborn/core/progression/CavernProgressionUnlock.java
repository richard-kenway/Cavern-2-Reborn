package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public enum CavernProgressionUnlock {
    MINERS_INSIGHT("miners_insight", "Miner's Insight", CavernProgressionRank.APPRENTICE),
    MINING_ASSIST("mining_assist", "Mining Assist", CavernProgressionRank.JOURNEYMAN);

    private final String id;
    private final String label;
    private final CavernProgressionRank requiredRank;

    CavernProgressionUnlock(String id, String label, CavernProgressionRank requiredRank) {
        this.id = requireText(id, "id");
        this.label = requireText(label, "label");
        this.requiredRank = Objects.requireNonNull(requiredRank, "requiredRank");
    }

    public String id() {
        return id;
    }

    public String label() {
        return label;
    }

    public CavernProgressionRank requiredRank() {
        return requiredRank;
    }

    public boolean isUnlocked(CavernProgressionRank rank) {
        return Objects.requireNonNull(rank, "rank").ordinal() >= requiredRank.ordinal();
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
