package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CrazySpiderLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(8, CrazySpiderLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CrazySpiderLootPolicy.shouldDropOrb(0));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(1));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(2));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(3));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(4));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(5));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(6));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(7));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(-1));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(8));
        assertFalse(CrazySpiderLootPolicy.shouldDropOrb(99));
    }
}
