package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public record CavernRewardClaimResult(
    CavernProgressionSnapshot progressionSnapshot,
    CavernRewardStatus previousStatus,
    CavernRewardStatus currentStatus,
    boolean claimed
) {
    public CavernRewardClaimResult {
        progressionSnapshot = Objects.requireNonNull(progressionSnapshot, "progressionSnapshot");
        previousStatus = Objects.requireNonNull(previousStatus, "previousStatus");
        currentStatus = Objects.requireNonNull(currentStatus, "currentStatus");
        if (previousStatus.reward() != currentStatus.reward()) {
            throw new IllegalArgumentException("reward must stay stable across a claim result");
        }
        if (claimed && !previousStatus.availableToClaim()) {
            throw new IllegalArgumentException("only an available reward can be claimed");
        }
    }

    public CavernProgressionReward reward() {
        return currentStatus.reward();
    }

    public boolean alreadyClaimed() {
        return previousStatus.claimed() && !claimed;
    }

    public boolean locked() {
        return previousStatus.locked() && !claimed;
    }
}
