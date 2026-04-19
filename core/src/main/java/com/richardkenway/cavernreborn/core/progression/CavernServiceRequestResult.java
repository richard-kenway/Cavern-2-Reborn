package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public record CavernServiceRequestResult(
    CavernProgressionSnapshot progressionSnapshot,
    CavernServiceStatus previousStatus,
    CavernServiceStatus currentStatus,
    boolean granted
) {
    public CavernServiceRequestResult {
        progressionSnapshot = Objects.requireNonNull(progressionSnapshot, "progressionSnapshot");
        previousStatus = Objects.requireNonNull(previousStatus, "previousStatus");
        currentStatus = Objects.requireNonNull(currentStatus, "currentStatus");
        if (previousStatus.service() != currentStatus.service()) {
            throw new IllegalArgumentException("service must stay stable across a request result");
        }
        if (granted && !previousStatus.availableToUse()) {
            throw new IllegalArgumentException("only an available service can be granted");
        }
    }

    public CavernServiceEntry service() {
        return currentStatus.service();
    }

    public boolean alreadyUsed() {
        return previousStatus.used() && !granted;
    }

    public boolean onCooldown() {
        return previousStatus.onCooldown() && !granted;
    }

    public boolean locked() {
        return previousStatus.locked() && !granted;
    }
}
