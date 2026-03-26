package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class CavernPlacementResolverTest {
    @Test
    void resolveKeepsHomeReturnPlacementIntact() {
        CavernPlacementResolver resolver = new CavernPlacementResolver();
        CavernTravelPlan travelPlan = CavernTravelPlan.returnHome(
            new PortalReturnOperation(
                new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 10, 12, 10),
                Optional.of(new PortalWorldIndex.PortalPlacement(20, 18, 20))
            )
        );

        CavernPlacementTarget placementTarget = resolver.resolve(travelPlan);

        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, placementTarget.dimensionId());
        assertEquals(20.0D, placementTarget.x());
        assertEquals(18.0D, placementTarget.y());
        assertEquals(20.0D, placementTarget.z());
    }

    @Test
    void resolveKeepsCavernEntryPlacementAboveSafeFloor() {
        CavernPlacementResolver resolver = new CavernPlacementResolver();
        CavernTravelPlan travelPlan = CavernTravelPlan.enterCavern(
            new PortalEntryReceipt(
                new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 10, 12, 10),
                new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north")
            )
        );

        CavernPlacementTarget placementTarget = resolver.resolve(travelPlan);

        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, placementTarget.dimensionId());
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_X, placementTarget.x());
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Y, placementTarget.y());
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Z, placementTarget.z());
    }
}
