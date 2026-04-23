package com.richardkenway.cavernreborn.core.farming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AcresiaHarvestPolicyTest {
    @Test
    void immatureCropDoesNotTriggerSpecialHarvest() {
        AcresiaHarvestResult result = AcresiaHarvestPolicy.evaluateShearHarvest(3, 0, 0);

        assertFalse(result.specialHarvest());
        assertEquals(0, result.fruitCount());
        assertEquals(3, result.resetAge());
    }

    @Test
    void matureCropTriggersSpecialHarvest() {
        AcresiaHarvestResult result = AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, 0, 1);

        assertTrue(result.specialHarvest());
        assertEquals(5, result.fruitCount());
        assertEquals(AcresiaHarvestPolicy.REGROWTH_AGE, result.resetAge());
    }

    @Test
    void matureCropDefaultRollsStayBounded() {
        assertEquals(4, AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, 0, 0).fruitCount());
        assertEquals(6, AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, 0, 2).fruitCount());
    }

    @Test
    void fortuneBonusStaysBounded() {
        assertEquals(8, AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, 5, 2).fruitCount());
        assertEquals(4, AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, -2, 0).fruitCount());
    }

    @Test
    void constantsRemainPinned() {
        assertEquals(4, AcresiaHarvestPolicy.MAX_AGE);
        assertEquals(2, AcresiaHarvestPolicy.REGROWTH_AGE);
    }

    @Test
    void invalidRollThrows() {
        assertThrows(IllegalArgumentException.class, () -> AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> AcresiaHarvestPolicy.evaluateShearHarvest(AcresiaHarvestPolicy.MAX_AGE, 0, 3));
    }
}
