package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class PortalLoopServiceTest {
    @Test
    void captureEntryStoresReturnStateAndRegistersPortalPlacement() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        PortalLoopService service = new PortalLoopService(playerStore, worldStore);

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        TeleportContext teleportContext = new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north");

        service.captureEntry(playerId, returnState, teleportContext, new PortalWorldIndex.PortalPlacement(12, 65, 12));

        assertEquals(returnState, playerStore.load(playerId).orElseThrow());
        assertTrue(worldStore.load("minecraft:overworld").firstPlacementFor("cavern").isPresent());
    }

    @Test
    void captureEntryRejectsMismatchedPortalKeys() {
        PortalLoopService service = new PortalLoopService(new TestPlayerReturnStateStore(), new TestWorldPortalIndexStore());

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        TeleportContext teleportContext = new TeleportContext("other", 0.25D, 0.5D, 0.75D, "north");

        assertThrows(IllegalArgumentException.class, () ->
            service.captureEntry(playerId, returnState, teleportContext, new PortalWorldIndex.PortalPlacement(12, 65, 12))
        );
    }

    @Test
    void planReturnUsesWorldIndexWhenPortalExists() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        PortalLoopService service = new PortalLoopService(playerStore, worldStore);

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        playerStore.save(playerId, returnState);
        worldStore.save("minecraft:overworld", PortalWorldIndex.empty().withPortal("cavern", new PortalWorldIndex.PortalPlacement(20, 70, 20)));

        PortalReturnOperation operation = service.planReturn(playerId).orElseThrow();

        assertTrue(operation.usesWorldIndex());
        assertEquals(20, operation.targetX());
        assertEquals(70, operation.targetY());
        assertEquals(20, operation.targetZ());
    }

    @Test
    void planReturnFallsBackToSavedReturnStateWhenPortalMissing() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        PortalLoopService service = new PortalLoopService(playerStore, worldStore);

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", "minecraft:overworld", 10, 64, 10);
        playerStore.save(playerId, returnState);

        PortalReturnOperation operation = service.planReturn(playerId).orElseThrow();

        assertFalse(operation.usesWorldIndex());
        assertEquals(10, operation.targetX());
        assertEquals(64, operation.targetY());
        assertEquals(10, operation.targetZ());
    }

    @Test
    void registerPortalRejectsBlankWorldKey() {
        PortalLoopService service = new PortalLoopService(new TestPlayerReturnStateStore(), new TestWorldPortalIndexStore());

        assertThrows(IllegalArgumentException.class, () ->
            service.registerPortal(" ", "cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3))
        );
    }

    private static final class TestPlayerReturnStateStore implements PlayerReturnStateStore {
        private final Map<UUID, PortalReturnState> states = new HashMap<>();

        @Override
        public Optional<PortalReturnState> load(UUID playerId) {
            return Optional.ofNullable(states.get(playerId));
        }

        @Override
        public void save(UUID playerId, PortalReturnState returnState) {
            states.put(playerId, returnState);
        }

        @Override
        public void clear(UUID playerId) {
            states.remove(playerId);
        }
    }

    private static final class TestWorldPortalIndexStore implements WorldPortalIndexStore {
        private final Map<String, PortalWorldIndex> indices = new HashMap<>();

        @Override
        public PortalWorldIndex load(String worldKey) {
            return indices.getOrDefault(worldKey, PortalWorldIndex.empty());
        }

        @Override
        public void save(String worldKey, PortalWorldIndex worldIndex) {
            indices.put(worldKey, worldIndex);
        }
    }
}
