package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicWitchLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(5, CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CavenicWitchLootPolicy.shouldDropOrb(0));
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(1));
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(2));
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(3));
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(4));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(-1));
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(5));
        assertFalse(CavenicWitchLootPolicy.shouldDropOrb(99));
    }
}
