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
        TeleportContext context = new TeleportContext("cavern", "minecraft:overworld", 1, 2, 3, 90.0F, 15.0F);

        assertEquals("cavern", context.portalKey());
        assertEquals("minecraft:overworld", context.sourceDimensionId());
        assertEquals(1, context.sourceX());
        assertEquals(2, context.sourceY());
        assertEquals(3, context.sourceZ());
        assertEquals(90.0F, context.entryYaw());
        assertEquals(15.0F, context.entryPitch());
    }

    @Test
    void portalWorldIndexKeepsPortalPlacementsUnique() {
        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3));

        assertEquals(1, index.placementsFor("cavern").size());
    }
}
