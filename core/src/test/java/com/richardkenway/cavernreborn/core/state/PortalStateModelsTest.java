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
}
