package com.richardkenway.cavernreborn.core.mining;

import java.util.Objects;

public record MinerOrbBonusResult(
    MinerOrbBonusDecision decision,
    int bonusScore
) {
    public MinerOrbBonusResult {
        decision = Objects.requireNonNull(decision, "decision");
        if (bonusScore < 0) {
            throw new IllegalArgumentException("bonusScore must not be negative");
        }
        if (decision != MinerOrbBonusDecision.TRIGGERED && bonusScore != 0) {
            throw new IllegalArgumentException("bonusScore must be zero when the orb bonus does not trigger");
        }
    }

    public boolean triggered() {
        return decision == MinerOrbBonusDecision.TRIGGERED;
    }
}
