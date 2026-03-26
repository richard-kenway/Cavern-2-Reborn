package com.richardkenway.cavernreborn.data.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

class CavernStateMappersTest {
    @Test
    void portalReturnStateRoundTripPreservesFields() {
        PortalReturnState original = new PortalReturnState("cavern", "minecraft:overworld", 12, 64, -4);

        PortalReturnState restored = CavernStateMappers.fromData(CavernStateMappers.toData(original));

        assertEquals(original, restored);
    }

    @Test
    void teleportContextRoundTripPreservesFields() {
        TeleportContext original = new TeleportContext("cavern", "minecraft:overworld", 1, 2, 3, 180.0F, 45.0F);

        TeleportContext restored = CavernStateMappers.fromData(CavernStateMappers.toData(original));

        assertEquals(original, restored);
    }

    @Test
    void portalWorldIndexRoundTripPreservesPlacements() {
        PortalWorldIndex original = PortalWorldIndex.empty()
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(4, 5, 6));

        PortalWorldIndex restored = CavernStateMappers.fromData(CavernStateMappers.toData(original));

        assertEquals(original, restored);
    }
}
