package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicCreeperLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(5, CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CavenicCreeperLootPolicy.shouldDropOrb(0));
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(1));
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(2));
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(3));
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(4));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(-1));
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(5));
        assertFalse(CavenicCreeperLootPolicy.shouldDropOrb(99));
    }
}
