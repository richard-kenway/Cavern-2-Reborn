package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PortalStateModelsTest {
    @Test
    void portalReturnStateRejectsBlankPortalKey() {
        assertThrows(IllegalArgumentException.class, () -> new PortalReturnState(" ", "minecraft:overworld", 10, 64, 10));
    }

    @Test
    void teleportContextKeepsTransientPlacementData() {
        TeleportContext context = new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north");

        assertEquals("cavern", context.portalKey());
        assertEquals(0.25D, context.entryOffsetX());
        assertEquals(0.5D, context.entryOffsetY());
        assertEquals(0.75D, context.entryOffsetZ());
        assertEquals("north", context.approachFacing());
    }

    @Test
    void portalWorldIndexKeepsPortalPlacementsUnique() {
        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3));

        assertEquals(1, index.placementsFor("cavern").size());
    }

    @Test
    void portalWorldIndexPromotesMostRecentPlacementToFront() {
        PortalWorldIndex.PortalPlacement olderPlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);
        PortalWorldIndex.PortalPlacement newerPlacement = new PortalWorldIndex.PortalPlacement(4, 5, 6);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", olderPlacement)
            .withPortal("cavern", newerPlacement);

        assertEquals(newerPlacement, index.firstPlacementFor("cavern").orElseThrow());
    }

    @Test
    void portalWorldIndexReusingExistingPlacementPromotesItToFront() {
        PortalWorldIndex.PortalPlacement firstPlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);
        PortalWorldIndex.PortalPlacement secondPlacement = new PortalWorldIndex.PortalPlacement(4, 5, 6);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", firstPlacement)
            .withPortal("cavern", secondPlacement)
            .withPortal("cavern", firstPlacement);

        assertEquals(firstPlacement, index.firstPlacementFor("cavern").orElseThrow());
        assertEquals(2, index.placementsFor("cavern").size());
    }

    @Test
    void portalWorldIndexReplacingIndexedPlacementRetainsCompetingEntries() {
        PortalWorldIndex.PortalPlacement stalePlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);
        PortalWorldIndex.PortalPlacement replacementPlacement = new PortalWorldIndex.PortalPlacement(4, 5, 6);
        PortalWorldIndex.PortalPlacement competingPlacement = new PortalWorldIndex.PortalPlacement(7, 8, 9);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", stalePlacement)
            .withPortal("cavern", competingPlacement)
            .withReplacementPortal("cavern", stalePlacement, replacementPlacement);

        assertEquals(replacementPlacement, index.firstPlacementFor("cavern").orElseThrow());
        assertEquals(2, index.placementsFor("cavern").size());
        assertEquals(
            competingPlacement,
            index.placementsFor("cavern").stream().skip(1).findFirst().orElseThrow()
        );
    }

    @Test
    void portalWorldIndexReplacingWithSamePlacementDoesNotDropHistory() {
        PortalWorldIndex.PortalPlacement firstPlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", firstPlacement)
            .withReplacementPortal("cavern", firstPlacement, firstPlacement);

        assertEquals(1, index.placementsFor("cavern").size());
        assertEquals(firstPlacement, index.firstPlacementFor("cavern").orElseThrow());
    }
}
