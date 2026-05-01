package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CrazyCreeperLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(5, CrazyCreeperLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CrazyCreeperLootPolicy.shouldDropOrb(0));
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(1));
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(2));
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(3));
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(4));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(-1));
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(5));
        assertFalse(CrazyCreeperLootPolicy.shouldDropOrb(99));
    }
}
