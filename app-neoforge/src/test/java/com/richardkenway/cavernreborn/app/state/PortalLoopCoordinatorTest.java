package com.richardkenway.cavernreborn.app.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalReturnOperation;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

class PortalLoopCoordinatorTest {
    @Test
    void captureEntryStoresReturnStateAndRegistersPortalPlacement() {
        InMemoryPlayerReturnStateRepository playerRepository = new InMemoryPlayerReturnStateRepository();
        InMemoryWorldPortalIndexRepository worldRepository = new InMemoryWorldPortalIndexRepository();
        PortalLoopCoordinator coordinator = new PortalLoopCoordinator(playerRepository, worldRepository);

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        TeleportContext teleportContext = new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north");

        coordinator.captureEntry(playerId, returnState, teleportContext, new PortalWorldIndex.PortalPlacement(12, 65, 12));

        assertEquals(returnState, playerRepository.load(playerId).orElseThrow());
        assertTrue(worldRepository.load("minecraft:overworld").firstPlacementFor("cavern").isPresent());
    }

    @Test
    void planReturnUsesWorldIndexWhenPortalExists() {
        InMemoryPlayerReturnStateRepository playerRepository = new InMemoryPlayerReturnStateRepository();
        InMemoryWorldPortalIndexRepository worldRepository = new InMemoryWorldPortalIndexRepository();
        PortalLoopCoordinator coordinator = new PortalLoopCoordinator(playerRepository, worldRepository);

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        playerRepository.save(playerId, returnState);
        worldRepository.save("minecraft:overworld", PortalWorldIndex.empty().withPortal("cavern", new PortalWorldIndex.PortalPlacement(20, 70, 20)));

        PortalReturnOperation operation = coordinator.planReturn(playerId).orElseThrow();

        assertTrue(operation.usesWorldIndex());
        assertEquals(20, operation.targetX());
        assertEquals(70, operation.targetY());
        assertEquals(20, operation.targetZ());
    }

    @Test
    void planReturnFallsBackToSavedReturnStateWhenPortalMissing() {
        InMemoryPlayerReturnStateRepository playerRepository = new InMemoryPlayerReturnStateRepository();
        InMemoryWorldPortalIndexRepository worldRepository = new InMemoryWorldPortalIndexRepository();
        PortalLoopCoordinator coordinator = new PortalLoopCoordinator(playerRepository, worldRepository);

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        playerRepository.save(playerId, returnState);

        PortalReturnOperation operation = coordinator.planReturn(playerId).orElseThrow();

        assertFalse(operation.usesWorldIndex());
        assertEquals(10, operation.targetX());
        assertEquals(64, operation.targetY());
        assertEquals(10, operation.targetZ());
    }

    @Test
    void registerPortalRejectsBlankWorldKey() {
        PortalLoopCoordinator coordinator = new PortalLoopCoordinator(
            new InMemoryPlayerReturnStateRepository(),
            new InMemoryWorldPortalIndexRepository()
        );

        assertThrows(IllegalArgumentException.class, () ->
            coordinator.registerPortal(" ", "cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3))
        );
    }
}
