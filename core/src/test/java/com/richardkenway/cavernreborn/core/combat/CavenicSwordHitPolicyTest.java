package com.richardkenway.cavernreborn.core.combat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicSwordHitPolicyTest {
    @Test
    void appliesWhenInvulnerabilityCooldownIsActive() {
        assertTrue(CavenicSwordHitPolicy.shouldResetCooldown(12, 0));
    }

    @Test
    void appliesWhenHurtCooldownIsActive() {
        assertTrue(CavenicSwordHitPolicy.shouldResetCooldown(0, 6));
    }

    @Test
    void doesNotApplyWhenNoCooldownIsActive() {
        assertFalse(CavenicSwordHitPolicy.shouldResetCooldown(0, 0));
    }
}
