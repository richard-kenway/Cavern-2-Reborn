package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class PortalLoopServiceIsolationTest {
    @Test
    void removingOnePortalDoesNotBreakReturnForAnotherPortalKey() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        PortalLoopService service = new PortalLoopService(playerStore, worldStore);

        UUID playerA = UUID.randomUUID();
        UUID playerB = UUID.randomUUID();
        PortalReturnState returnStateA = new PortalReturnState("portal-a", "minecraft:overworld", 10, 64, 10);
        PortalReturnState returnStateB = new PortalReturnState("portal-b", "minecraft:overworld", 30, 70, 30);
        PortalWorldIndex.PortalPlacement placementA = new PortalWorldIndex.PortalPlacement(10, 64, 10);
        PortalWorldIndex.PortalPlacement placementB = new PortalWorldIndex.PortalPlacement(30, 70, 30);

        service.captureEntry(playerA, returnStateA, new TeleportContext("portal-a", 0.25D, 0.5D, 0.75D, "north"), placementA);
        service.captureEntry(playerB, returnStateB, new TeleportContext("portal-b", 0.25D, 0.5D, 0.75D, "north"), placementB);

        PortalWorldIndex updatedIndex = worldStore.load("minecraft:overworld").withoutPortal("portal-a", placementA);
        worldStore.save("minecraft:overworld", updatedIndex);

        PortalReturnOperation operationForPortalB = service.planReturn(playerB).orElseThrow();
        assertTrue(operationForPortalB.usesWorldIndex());
        assertEquals(30, operationForPortalB.targetX());
        assertEquals(70, operationForPortalB.targetY());
        assertEquals(30, operationForPortalB.targetZ());

        PortalReturnOperation operationForPortalA = service.planReturn(playerA).orElseThrow();
        assertFalse(operationForPortalA.usesWorldIndex());
        assertEquals(10, operationForPortalA.targetX());
        assertEquals(64, operationForPortalA.targetY());
        assertEquals(10, operationForPortalA.targetZ());
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
