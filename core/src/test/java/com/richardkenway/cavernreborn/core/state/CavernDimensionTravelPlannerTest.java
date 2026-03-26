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

class CavernDimensionTravelPlannerTest {
    @Test
    void planEntryTargetsCavernDimension() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        CavernDimensionTravelPlanner planner = new CavernDimensionTravelPlanner(new PortalLoopService(playerStore, worldStore));

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 10, 64, 10);
        TeleportContext teleportContext = new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north");

        CavernTravelPlan plan = planner.planEntry(playerId, returnState, teleportContext, new PortalWorldIndex.PortalPlacement(12, 65, 12));

        assertTrue(plan.isEnterCavern());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, plan.targetDimensionId());
        assertEquals(CavernDimensions.CAVERN_ENTRY_X, plan.targetX());
        assertEquals(CavernDimensions.CAVERN_ENTRY_Y, plan.targetY());
        assertEquals(CavernDimensions.CAVERN_ENTRY_Z, plan.targetZ());
    }

    @Test
    void planEntryRejectsCavernAsHomeDimension() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        CavernDimensionTravelPlanner planner = new CavernDimensionTravelPlanner(new PortalLoopService(playerStore, worldStore));

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", CavernDimensions.CAVERN_DIMENSION_ID, 10, 64, 10);
        TeleportContext teleportContext = new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north");

        assertThrows(IllegalArgumentException.class, () ->
            planner.planEntry(playerId, returnState, teleportContext, new PortalWorldIndex.PortalPlacement(12, 65, 12))
        );
    }

    @Test
    void planReturnUsesSavedTargetWhenWorldIndexIsMissing() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        CavernDimensionTravelPlanner planner = new CavernDimensionTravelPlanner(new PortalLoopService(playerStore, worldStore));

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 10, 64, 10);
        playerStore.save(playerId, returnState);

        CavernTravelPlan plan = planner.planReturn(playerId).orElseThrow();

        assertTrue(plan.isReturnHome());
        assertFalse(plan.usesWorldIndex());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, plan.targetDimensionId());
        assertEquals(10, plan.targetX());
        assertEquals(64, plan.targetY());
        assertEquals(10, plan.targetZ());
    }

    @Test
    void planReturnUsesWorldIndexWhenPlacementExists() {
        TestPlayerReturnStateStore playerStore = new TestPlayerReturnStateStore();
        TestWorldPortalIndexStore worldStore = new TestWorldPortalIndexStore();
        CavernDimensionTravelPlanner planner = new CavernDimensionTravelPlanner(new PortalLoopService(playerStore, worldStore));

        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 10, 64, 10);
        playerStore.save(playerId, returnState);
        worldStore.save(CavernDimensions.OVERWORLD_DIMENSION_ID, PortalWorldIndex.empty().withPortal("cavern", new PortalWorldIndex.PortalPlacement(20, 70, 20)));

        CavernTravelPlan plan = planner.planReturn(playerId).orElseThrow();

        assertTrue(plan.isReturnHome());
        assertTrue(plan.usesWorldIndex());
        assertEquals(20, plan.targetX());
        assertEquals(70, plan.targetY());
        assertEquals(20, plan.targetZ());
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
