package com.richardkenway.cavernreborn.core.mining;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AquamarineAquaToolPolicyTest {
    @Test
    void noToolLeavesSpeedUnchanged() {
        AquamarineAquaToolResult result = AquamarineAquaToolPolicy.evaluate(false, true, false, 2.0F);

        assertEquals(AquamarineAquaToolDecision.NO_TOOL, result.decision());
        assertEquals(2.0F, result.adjustedSpeed());
    }

    @Test
    void notSubmergedLeavesSpeedUnchanged() {
        AquamarineAquaToolResult result = AquamarineAquaToolPolicy.evaluate(true, false, false, 2.0F);

        assertEquals(AquamarineAquaToolDecision.NOT_SUBMERGED, result.decision());
        assertEquals(2.0F, result.adjustedSpeed());
    }

    @Test
    void invalidSpeedLeavesValueUnchanged() {
        AquamarineAquaToolResult result = AquamarineAquaToolPolicy.evaluate(true, true, false, 0.0F);

        assertEquals(AquamarineAquaToolDecision.INVALID_SPEED, result.decision());
        assertEquals(0.0F, result.adjustedSpeed());
    }

    @Test
    void submergedAquamarineToolGetsBoosted() {
        AquamarineAquaToolResult result = AquamarineAquaToolPolicy.evaluate(true, true, false, 2.0F);

        assertEquals(AquamarineAquaToolDecision.BOOSTED, result.decision());
        assertEquals(20.0F, result.adjustedSpeed());
    }

    @Test
    void aquaAffinityAppliesDampener() {
        AquamarineAquaToolResult result = AquamarineAquaToolPolicy.evaluate(true, true, true, 2.0F);

        assertEquals(AquamarineAquaToolDecision.BOOSTED, result.decision());
        assertEquals(10.0F, result.adjustedSpeed());
    }

    @Test
    void constantsRemainPinned() {
        assertEquals(10.0F, AquamarineAquaToolPolicy.AQUA_BREAK_SPEED_MULTIPLIER);
        assertEquals(0.5F, AquamarineAquaToolPolicy.AQUA_AFFINITY_DAMPENER);
    }
}
