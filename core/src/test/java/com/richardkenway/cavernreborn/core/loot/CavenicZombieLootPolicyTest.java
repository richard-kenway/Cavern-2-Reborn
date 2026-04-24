package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicZombieLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(8, CavenicZombieLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CavenicZombieLootPolicy.shouldDropOrb(0));
        assertFalse(CavenicZombieLootPolicy.shouldDropOrb(1));
        assertFalse(CavenicZombieLootPolicy.shouldDropOrb(7));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CavenicZombieLootPolicy.shouldDropOrb(-1));
        assertFalse(CavenicZombieLootPolicy.shouldDropOrb(8));
        assertFalse(CavenicZombieLootPolicy.shouldDropOrb(99));
    }
}
