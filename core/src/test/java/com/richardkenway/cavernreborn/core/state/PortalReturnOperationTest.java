package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class PortalReturnOperationTest {
    @Test
    void usesSavedReturnStateWhenNoWorldPlacementIsResolved() {
        PortalReturnOperation operation = new PortalReturnOperation(
            new PortalReturnState("cavern", "minecraft:overworld", 1, 2, 3),
            Optional.empty()
        );

        assertFalse(operation.usesWorldIndex());
    }

    @Test
    void usesWorldIndexWhenPlacementIsResolved() {
        PortalReturnOperation operation = new PortalReturnOperation(
            new PortalReturnState("cavern", "minecraft:overworld", 1, 2, 3),
            Optional.of(new PortalWorldIndex.PortalPlacement(7, 8, 9))
        );

        assertTrue(operation.usesWorldIndex());
    }
}
