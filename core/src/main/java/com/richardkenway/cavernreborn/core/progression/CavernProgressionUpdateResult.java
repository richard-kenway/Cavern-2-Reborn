package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public record CavernProgressionUpdateResult(
    CavernProgressionSnapshot previousSnapshot,
    CavernProgressionSnapshot currentSnapshot,
    boolean counted,
    String blockId,
    int scoreDelta
) {
    public CavernProgressionUpdateResult {
        previousSnapshot = Objects.requireNonNull(previousSnapshot, "previousSnapshot");
        currentSnapshot = Objects.requireNonNull(currentSnapshot, "currentSnapshot");
        blockId = requireText(blockId, "blockId");
        if (scoreDelta < 0) {
            throw new IllegalArgumentException("scoreDelta must not be negative");
        }
        if (!previousSnapshot.playerId().equals(currentSnapshot.playerId())) {
            throw new IllegalArgumentException("playerId must stay stable across a progression update");
        }
        if (!counted && scoreDelta != 0) {
            throw new IllegalArgumentException("scoreDelta must be zero when the event is not counted");
        }
    }

    public boolean rankAdvanced() {
        return currentSnapshot.rank().ordinal() > previousSnapshot.rank().ordinal();
    }

    public boolean unlockJustReached(CavernProgressionUnlock unlock) {
        CavernProgressionUnlock normalizedUnlock = Objects.requireNonNull(unlock, "unlock");
        return !previousSnapshot.hasUnlocked(normalizedUnlock) && currentSnapshot.hasUnlocked(normalizedUnlock);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
