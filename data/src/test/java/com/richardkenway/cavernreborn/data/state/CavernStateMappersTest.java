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
        TeleportContext original = new TeleportContext("cavern", 0.125D, 0.5D, 0.875D, "west");

        TeleportContext restored = CavernStateMappers.fromData(CavernStateMappers.toData(original));

        assertEquals(original, restored);
    }

    @Test
    void portalWorldIndexRoundTripPreservesPlacements() {
        PortalWorldIndex original = PortalWorldIndex.empty()
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3, PortalWorldIndex.PortalPlacement.AXIS_X))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(4, 5, 6, PortalWorldIndex.PortalPlacement.AXIS_Z));

        PortalWorldIndex restored = CavernStateMappers.fromData(CavernStateMappers.toData(original));

        assertEquals(original, restored);
    }
}
