package com.richardkenway.cavernreborn.core.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicAxeSmashPolicyTest {
    @Test
    void radiusStaysBoundedAtTwoAndHalfBlocks() {
        assertTrue(CavenicAxeSmashPolicy.isWithinRadius(2.5D * 2.5D));
        assertFalse(CavenicAxeSmashPolicy.isWithinRadius((2.5D * 2.5D) + 0.01D));
    }

    @Test
    void capsExtraTargetsAtSix() {
        assertEquals(0, CavenicAxeSmashPolicy.cappedExtraTargets(-1));
        assertEquals(4, CavenicAxeSmashPolicy.cappedExtraTargets(4));
        assertEquals(6, CavenicAxeSmashPolicy.cappedExtraTargets(12));
    }

    @Test
    void excludesNonHostileFriendlyPrimaryAndAttackerTargets() {
        assertFalse(CavenicAxeSmashPolicy.isEligibleExtraTarget(true, false, false, false, false, 1.0D));
        assertFalse(CavenicAxeSmashPolicy.isEligibleExtraTarget(true, true, true, false, false, 1.0D));
        assertFalse(CavenicAxeSmashPolicy.isEligibleExtraTarget(true, true, false, true, false, 1.0D));
        assertFalse(CavenicAxeSmashPolicy.isEligibleExtraTarget(true, true, false, false, true, 1.0D));
    }

    @Test
    void acceptsNearbyHostileExtraTarget() {
        assertTrue(CavenicAxeSmashPolicy.isEligibleExtraTarget(true, true, false, false, false, 1.0D));
    }

    @Test
    void durabilityCostIsBoundedToOneThroughThree() {
        assertEquals(1, CavenicAxeSmashPolicy.durabilityCost(0));
        assertEquals(1, CavenicAxeSmashPolicy.durabilityCost(2));
        assertEquals(2, CavenicAxeSmashPolicy.durabilityCost(3));
        assertEquals(3, CavenicAxeSmashPolicy.durabilityCost(6));
        assertEquals(3, CavenicAxeSmashPolicy.durabilityCost(20));
    }

    @Test
    void constantsRemainPinned() {
        assertEquals(2.5D, CavenicAxeSmashPolicy.SMASH_RADIUS);
        assertEquals(6, CavenicAxeSmashPolicy.MAX_EXTRA_TARGETS);
        assertEquals(4.0F, CavenicAxeSmashPolicy.ADDITIONAL_DAMAGE);
        assertEquals(3, CavenicAxeSmashPolicy.MAX_DURABILITY_COST);
    }
}
