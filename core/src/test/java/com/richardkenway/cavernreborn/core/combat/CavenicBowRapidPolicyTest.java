package com.richardkenway.cavernreborn.core.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CavenicBowRapidPolicyTest {
    @Test
    void constantsRemainPinned() {
        assertEquals(2.4F, CavenicBowRapidPolicy.POWER_MULTIPLIER);
        assertEquals(1.0F, CavenicBowRapidPolicy.MAX_POWER);
        assertEquals(0, CavenicBowRapidPolicy.EXTRA_DURABILITY_COST);
    }

    @Test
    void onlyRapidModeAdjustsValidRawPower() {
        assertEquals(0.72F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.RAPID, 0.3F));
        assertEquals(0.3F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.NORMAL, 0.3F));
        assertEquals(0.3F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.SNIPE, 0.3F));
        assertEquals(0.3F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.TORCH, 0.3F));
    }

    @Test
    void adjustedPowerIsCappedAtVanillaFullPower() {
        assertEquals(1.0F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.RAPID, 0.5F));
        assertEquals(1.0F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.RAPID, 1.0F));
    }

    @Test
    void invalidRawPowerSafelyFallsBackToZero() {
        assertEquals(0.0F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.RAPID, -0.1F));
        assertEquals(0.0F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.RAPID, Float.NaN));
        assertEquals(0.0F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.RAPID, Float.POSITIVE_INFINITY));
        assertEquals(0.0F, CavenicBowRapidPolicy.adjustedPower(CavenicBowMode.NORMAL, -0.1F));
    }
}
