package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CrazySkeletonLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(5, CrazySkeletonLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CrazySkeletonLootPolicy.shouldDropOrb(0));
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(1));
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(2));
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(3));
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(4));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(-1));
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(5));
        assertFalse(CrazySkeletonLootPolicy.shouldDropOrb(99));
    }
}
