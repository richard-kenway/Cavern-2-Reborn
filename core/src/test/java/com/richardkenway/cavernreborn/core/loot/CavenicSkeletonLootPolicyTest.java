package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicSkeletonLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(5, CavenicSkeletonLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CavenicSkeletonLootPolicy.shouldDropOrb(0));
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(1));
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(2));
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(3));
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(4));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(-1));
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(5));
        assertFalse(CavenicSkeletonLootPolicy.shouldDropOrb(99));
    }
}
