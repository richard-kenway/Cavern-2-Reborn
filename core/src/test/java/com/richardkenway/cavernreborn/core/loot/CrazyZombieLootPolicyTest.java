package com.richardkenway.cavernreborn.core.loot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CrazyZombieLootPolicyTest {
    @Test
    void orbDropRollBoundRemainsPinned() {
        assertEquals(8, CrazyZombieLootPolicy.ORB_DROP_ROLL_BOUND);
    }

    @Test
    void onlyWinningRollDropsOrb() {
        assertTrue(CrazyZombieLootPolicy.shouldDropOrb(0));
        assertFalse(CrazyZombieLootPolicy.shouldDropOrb(1));
        assertFalse(CrazyZombieLootPolicy.shouldDropOrb(7));
    }

    @Test
    void invalidRollsStayBoundedToNoDrop() {
        assertFalse(CrazyZombieLootPolicy.shouldDropOrb(-1));
        assertFalse(CrazyZombieLootPolicy.shouldDropOrb(8));
        assertFalse(CrazyZombieLootPolicy.shouldDropOrb(99));
    }
}
