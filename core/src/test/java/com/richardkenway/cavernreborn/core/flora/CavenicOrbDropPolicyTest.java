package com.richardkenway.cavernreborn.core.flora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicOrbDropPolicyTest {
    @Test
    void shearsWinningRollDropsSingleOrb() {
        CavenicOrbDropResult result = CavenicOrbDropPolicy.evaluate(true, 0, true);

        assertTrue(result.dropsOrb());
        assertEquals(1, result.orbCount());
    }

    @Test
    void shearsLosingRollDropsNothing() {
        CavenicOrbDropResult result = CavenicOrbDropPolicy.evaluate(true, 1, true);

        assertFalse(result.dropsOrb());
        assertEquals(0, result.orbCount());
    }

    @Test
    void normalWinningRollDropsSingleOrb() {
        CavenicOrbDropResult result = CavenicOrbDropPolicy.evaluate(false, 0, true);

        assertTrue(result.dropsOrb());
        assertEquals(1, result.orbCount());
    }

    @Test
    void normalLosingRollDropsNothing() {
        CavenicOrbDropResult result = CavenicOrbDropPolicy.evaluate(false, 1, true);

        assertFalse(result.dropsOrb());
        assertEquals(0, result.orbCount());
    }

    @Test
    void invalidOrNonHarvestableInputStaysBounded() {
        assertFalse(CavenicOrbDropPolicy.evaluate(true, -1, true).dropsOrb());
        assertFalse(CavenicOrbDropPolicy.evaluate(true, CavenicOrbDropPolicy.SHEAR_DROP_ROLL_BOUND, true).dropsOrb());
        assertFalse(CavenicOrbDropPolicy.evaluate(false, CavenicOrbDropPolicy.NORMAL_DROP_ROLL_BOUND, true).dropsOrb());
        assertFalse(CavenicOrbDropPolicy.evaluate(true, 0, false).dropsOrb());
    }

    @Test
    void constantsRemainPinned() {
        assertEquals(10, CavenicOrbDropPolicy.SHEAR_DROP_ROLL_BOUND);
        assertEquals(50, CavenicOrbDropPolicy.NORMAL_DROP_ROLL_BOUND);
    }
}
