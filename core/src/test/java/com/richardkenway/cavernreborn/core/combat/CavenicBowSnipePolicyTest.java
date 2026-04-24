package com.richardkenway.cavernreborn.core.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicBowSnipePolicyTest {
    @Test
    void constantsRemainPinned() {
        assertEquals(1.0F, CavenicBowSnipePolicy.MIN_POWER_THRESHOLD);
        assertEquals(1.5F, CavenicBowSnipePolicy.VELOCITY_MULTIPLIER);
        assertEquals(1.5D, CavenicBowSnipePolicy.BASE_DAMAGE_MULTIPLIER);
        assertEquals(1, CavenicBowSnipePolicy.EXTRA_DURABILITY_COST);
    }

    @Test
    void snipeOnlyAppliesForSnipeModeAtFullCharge() {
        assertTrue(CavenicBowSnipePolicy.applies(CavenicBowMode.SNIPE, 1.0F));
        assertFalse(CavenicBowSnipePolicy.applies(CavenicBowMode.NORMAL, 1.0F));
        assertFalse(CavenicBowSnipePolicy.applies(CavenicBowMode.RAPID, 1.0F));
        assertFalse(CavenicBowSnipePolicy.applies(CavenicBowMode.TORCH, 1.0F));
    }

    @Test
    void underchargedSnipeShotsDoNotApplyBoosts() {
        assertFalse(CavenicBowSnipePolicy.applies(CavenicBowMode.SNIPE, 0.99F));
        assertEquals(3.0F, CavenicBowSnipePolicy.adjustedVelocity(CavenicBowMode.SNIPE, 3.0F, 0.99F));
        assertEquals(2.0D, CavenicBowSnipePolicy.adjustedBaseDamage(CavenicBowMode.SNIPE, 2.0D, 0.99F));
    }

    @Test
    void fullChargeSnipeShotsApplyBoundedVelocityAndDamageBoosts() {
        assertEquals(4.5F, CavenicBowSnipePolicy.adjustedVelocity(CavenicBowMode.SNIPE, 3.0F, 1.0F));
        assertEquals(3.0D, CavenicBowSnipePolicy.adjustedBaseDamage(CavenicBowMode.SNIPE, 2.0D, 1.0F));
    }
}
