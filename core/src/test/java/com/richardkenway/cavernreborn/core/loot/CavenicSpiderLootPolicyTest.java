package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicSpiderLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(8, CavenicSpiderLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CavenicSpiderLootPolicy.shouldDropOrb(0));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(1));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(2));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(3));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(4));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(5));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(6));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(7));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(-1));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(8));
        assertFalse(CavenicSpiderLootPolicy.shouldDropOrb(99));
    }
}
