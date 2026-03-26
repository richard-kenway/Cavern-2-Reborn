package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class CavernDimensionsTest {
    @Test
    void cavernIdentityUsesTheExpectedDimensionId() {
        assertTrue(CavernDimensions.isCavern(CavernDimensions.CAVERN_DIMENSION_ID));
        assertFalse(CavernDimensions.isCavern(CavernDimensions.OVERWORLD_DIMENSION_ID));
    }

    @Test
    void travelPlanRejectsInvalidDirectionPayloadCombination() {
        assertThrows(IllegalArgumentException.class, () ->
            new CavernTravelPlan(
                CavernTravelDirection.ENTER_CAVERN,
                CavernDimensions.CAVERN_DIMENSION_ID,
                CavernDimensions.CAVERN_ENTRY_X,
                CavernDimensions.CAVERN_ENTRY_Y,
                CavernDimensions.CAVERN_ENTRY_Z,
                Optional.empty(),
                Optional.empty()
            )
        );
    }
}
