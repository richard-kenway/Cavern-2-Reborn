package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

class IndexedPlacementRevalidationPolicyTest {
    @Test
    void classifyIndexedPlacementsSeparatesLiveRelinkableAndDeadPlacements() {
        PortalWorldIndex.PortalPlacement liveIndexedPlacement = new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement liveResolvedPlacement = new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement relinkableIndexedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement relinkedPlacement = new PortalWorldIndex.PortalPlacement(31, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement deadIndexedPlacement = new PortalWorldIndex.PortalPlacement(90, 70, 90, PortalWorldIndex.PortalPlacement.AXIS_X);

        FakePlayerTravelContext player = new FakePlayerTravelContext();
        player.resolvedPlacements.put(keyFor(liveIndexedPlacement.x(), liveIndexedPlacement.y(), liveIndexedPlacement.z()), liveResolvedPlacement);
        player.nearbyPlacements.put(keyFor(relinkableIndexedPlacement.x(), relinkableIndexedPlacement.y(), relinkableIndexedPlacement.z()), relinkedPlacement);

        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            IndexedPlacementRevalidationPolicy.classifyIndexedPlacements(
                "cavern",
                Set.of(liveIndexedPlacement, relinkableIndexedPlacement, deadIndexedPlacement),
                player
            );

        assertEquals(Set.of(liveIndexedPlacement), snapshot.liveIndexedPlacements());
        assertEquals(Set.of(relinkableIndexedPlacement), snapshot.relinkableIndexedPlacements());
        assertEquals(Set.of(deadIndexedPlacement), snapshot.deadIndexedPlacements());
        assertEquals(Optional.of(liveResolvedPlacement), snapshot.liveReplacement(liveIndexedPlacement));
        assertEquals(Optional.of(relinkedPlacement), snapshot.relinkableReplacement(relinkableIndexedPlacement));
        assertEquals(Optional.empty(), snapshot.liveReplacement(deadIndexedPlacement));
        assertEquals(Optional.empty(), snapshot.relinkableReplacement(deadIndexedPlacement));
    }

    @Test
    void retainedPortalUpdateDropsDeadIndexedPlacementsBeforePromotingReplacement() {
        PortalWorldIndex.PortalPlacement deadPlacement = new PortalWorldIndex.PortalPlacement(90, 64, 90, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement oldPlacement = new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement replacementPlacement = new PortalWorldIndex.PortalPlacement(3, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement competitivePlacement = new PortalWorldIndex.PortalPlacement(10, 64, 10, PortalWorldIndex.PortalPlacement.AXIS_X);

        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("cavern", oldPlacement)
            .withPortal("cavern", replacementPlacement)
            .withPortal("cavern", competitivePlacement)
            .withPortal("cavern", deadPlacement);
        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            new IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot(
                Set.of(oldPlacement),
                Set.of(),
                Set.of(deadPlacement),
                Map.of(oldPlacement, replacementPlacement),
                Map.of()
            );

        PortalWorldIndex refreshedIndex = snapshot.withRetainedPortal("cavern", worldIndex, replacementPlacement);

        Set<PortalWorldIndex.PortalPlacement> refreshedPlacements = refreshedIndex.placementsFor("cavern");
        assertEquals(3, refreshedPlacements.size());
        assertFalse(refreshedPlacements.contains(deadPlacement));
        assertTrue(refreshedPlacements.contains(replacementPlacement));
        assertTrue(refreshedPlacements.contains(competitivePlacement));
        assertEquals(replacementPlacement, refreshedIndex.firstPlacementFor("cavern").orElseThrow());
    }

    @Test
    void retainedReplacementUpdateDropsDeadIndexedPlacementsBeforeSwappingStalePlacement() {
        PortalWorldIndex.PortalPlacement stalePlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement replacementPlacement = new PortalWorldIndex.PortalPlacement(31, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement survivingPlacement = new PortalWorldIndex.PortalPlacement(10, 70, 10, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement deadPlacement = new PortalWorldIndex.PortalPlacement(90, 70, 90, PortalWorldIndex.PortalPlacement.AXIS_X);

        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("cavern", stalePlacement)
            .withPortal("cavern", survivingPlacement)
            .withPortal("cavern", deadPlacement);
        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            new IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot(
                Set.of(),
                Set.of(stalePlacement),
                Set.of(deadPlacement),
                Map.of(),
                Map.of(stalePlacement, replacementPlacement)
            );

        PortalWorldIndex refreshedIndex = snapshot.withRetainedReplacementPortal(
            "cavern",
            worldIndex,
            stalePlacement,
            replacementPlacement
        );

        Set<PortalWorldIndex.PortalPlacement> refreshedPlacements = refreshedIndex.placementsFor("cavern");
        assertEquals(2, refreshedPlacements.size());
        assertFalse(refreshedPlacements.contains(deadPlacement));
        assertFalse(refreshedPlacements.contains(stalePlacement));
        assertTrue(refreshedPlacements.contains(replacementPlacement));
        assertTrue(refreshedPlacements.contains(survivingPlacement));
        assertEquals(replacementPlacement, refreshedIndex.firstPlacementFor("cavern").orElseThrow());
    }

    private static final class FakePlayerTravelContext implements PlayerTravelContext {
        private final Map<String, PortalWorldIndex.PortalPlacement> resolvedPlacements = new LinkedHashMap<>();
        private final Map<String, PortalWorldIndex.PortalPlacement> nearbyPlacements = new LinkedHashMap<>();

        @Override
        public UUID playerId() {
            return UUID.randomUUID();
        }

        @Override
        public long gameTime() {
            return 0L;
        }

        @Override
        public float yaw() {
            return 0.0F;
        }

        @Override
        public float pitch() {
            return 0.0F;
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> resolvePortalAt(String targetDimensionId, int x, int y, int z) {
            return Optional.ofNullable(resolvedPlacements.get(keyFor(x, y, z)));
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> findPortalNear(String targetDimensionId, int x, int y, int z) {
            return Optional.ofNullable(nearbyPlacements.get(keyFor(x, y, z)));
        }

        @Override
        public void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch) {
        }
    }

    private static String keyFor(int x, int y, int z) {
        return x + ":" + y + ":" + z;
    }
}
