package com.richardkenway.cavernreborn.core.mining;

import java.util.Objects;

public record AquamarineAquaToolResult(
    AquamarineAquaToolDecision decision,
    float adjustedSpeed
) {
    public AquamarineAquaToolResult {
        decision = Objects.requireNonNull(decision, "decision");
        if (!Float.isFinite(adjustedSpeed)) {
            throw new IllegalArgumentException("adjustedSpeed must be finite");
        }
    }

    public boolean boosted() {
        return decision == AquamarineAquaToolDecision.BOOSTED;
    }
}
