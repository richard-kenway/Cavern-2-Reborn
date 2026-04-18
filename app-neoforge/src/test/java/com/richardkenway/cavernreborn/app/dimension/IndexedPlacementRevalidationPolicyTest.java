package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
        assertEquals(Set.of(), snapshot.displacedIndexedPlacements());
        assertEquals(Set.of(deadIndexedPlacement), snapshot.deadIndexedPlacements());
        assertEquals(Optional.of(liveResolvedPlacement), snapshot.liveReplacement(liveIndexedPlacement));
        assertEquals(Optional.of(relinkedPlacement), snapshot.relinkableReplacement(relinkableIndexedPlacement));
        assertEquals(Optional.empty(), snapshot.liveReplacement(deadIndexedPlacement));
        assertEquals(Optional.empty(), snapshot.relinkableReplacement(deadIndexedPlacement));
    }

    @Test
    void classifyIndexedPlacementsRejectsFarAwayNearbyPortal() {
        PortalWorldIndex.PortalPlacement indexedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement farPortalPlacement = new PortalWorldIndex.PortalPlacement(35, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);

        FakePlayerTravelContext player = new FakePlayerTravelContext();
        player.nearbyPlacements.put(keyFor(indexedPlacement.x(), indexedPlacement.y(), indexedPlacement.z()), farPortalPlacement);

        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            IndexedPlacementRevalidationPolicy.classifyIndexedPlacements(
                "cavern",
                Set.of(indexedPlacement),
                player
            );

        assertEquals(Set.of(), snapshot.relinkableIndexedPlacements());
        assertEquals(Set.of(indexedPlacement), snapshot.displacedIndexedPlacements());
        assertEquals(Set.of(), snapshot.deadIndexedPlacements());
        assertEquals(Optional.empty(), snapshot.relinkableReplacement(indexedPlacement));
    }

    @Test
    void classifyIndexedPlacementsAcceptsNearbyPortalAtAllowedDriftBoundary() {
        PortalWorldIndex.PortalPlacement indexedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement boundaryPortalPlacement = new PortalWorldIndex.PortalPlacement(32, 71, 28, PortalWorldIndex.PortalPlacement.AXIS_X);

        FakePlayerTravelContext player = new FakePlayerTravelContext();
        player.nearbyPlacements.put(keyFor(indexedPlacement.x(), indexedPlacement.y(), indexedPlacement.z()), boundaryPortalPlacement);

        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            IndexedPlacementRevalidationPolicy.classifyIndexedPlacements(
                "cavern",
                Set.of(indexedPlacement),
                player
            );

        assertEquals(Set.of(indexedPlacement), snapshot.relinkableIndexedPlacements());
        assertEquals(Set.of(), snapshot.deadIndexedPlacements());
        assertEquals(Optional.of(boundaryPortalPlacement), snapshot.relinkableReplacement(indexedPlacement));
        assertEquals(Set.of(), snapshot.displacedIndexedPlacements());
    }

    @Test
    void classifyIndexedPlacementsRejectsNearbyPortalWithTooMuchVerticalDrift() {
        PortalWorldIndex.PortalPlacement indexedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement highPortalPlacement = new PortalWorldIndex.PortalPlacement(31, 72, 29, PortalWorldIndex.PortalPlacement.AXIS_Z);

        FakePlayerTravelContext player = new FakePlayerTravelContext();
        player.nearbyPlacements.put(keyFor(indexedPlacement.x(), indexedPlacement.y(), indexedPlacement.z()), highPortalPlacement);

        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            IndexedPlacementRevalidationPolicy.classifyIndexedPlacements(
                "cavern",
                Set.of(indexedPlacement),
                player
            );

        assertEquals(Set.of(), snapshot.relinkableIndexedPlacements());
        assertEquals(Set.of(indexedPlacement), snapshot.displacedIndexedPlacements());
        assertEquals(Set.of(), snapshot.deadIndexedPlacements());
        assertEquals(Optional.empty(), snapshot.relinkableReplacement(indexedPlacement));
    }

    @Test
    void regenerationEligibilityKeepsDeadPlacementsAndExcludesDisplacedPlacements() {
        PortalWorldIndex.PortalPlacement displacedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement deadPlacement = new PortalWorldIndex.PortalPlacement(90, 70, 90, PortalWorldIndex.PortalPlacement.AXIS_X);

        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            new IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot(
                Set.of(),
                Set.of(),
                Set.of(displacedPlacement),
                Set.of(deadPlacement),
                Map.of(),
                Map.of()
            );

        assertEquals(Set.of(deadPlacement), snapshot.regenerationEligibleIndexedPlacements());
        assertFalse(snapshot.regenerationEligibleIndexedPlacements().contains(displacedPlacement));
    }

    @Test
    void retainedPortalUpdatePreservesDisplacedIndexedPlacements() {
        PortalWorldIndex.PortalPlacement displacedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement resolvedPlacement = new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement competingPlacement = new PortalWorldIndex.PortalPlacement(10, 64, 10, PortalWorldIndex.PortalPlacement.AXIS_X);

        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("cavern", displacedPlacement)
            .withPortal("cavern", resolvedPlacement)
            .withPortal("cavern", competingPlacement);
        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            new IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot(
                Set.of(resolvedPlacement),
                Set.of(),
                Set.of(displacedPlacement),
                Set.of(),
                Map.of(),
                Map.of()
            );

        PortalWorldIndex refreshedIndex = snapshot.withRetainedPortal(
            "cavern",
            worldIndex,
            resolvedPlacement
        );

        Set<PortalWorldIndex.PortalPlacement> refreshedPlacements = refreshedIndex.placementsFor("cavern");
        assertEquals(3, refreshedPlacements.size());
        assertTrue(refreshedPlacements.contains(displacedPlacement));
        assertTrue(refreshedPlacements.contains(resolvedPlacement));
        assertTrue(refreshedPlacements.contains(competingPlacement));
        assertEquals(resolvedPlacement, refreshedIndex.firstPlacementFor("cavern").orElseThrow());
    }

    @Test
    void retainedPortalUpdateDropsDeadPlacementsButKeepsDisplacedHistory() {
        PortalWorldIndex.PortalPlacement deadPlacement = new PortalWorldIndex.PortalPlacement(90, 64, 90, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement displacedPlacement = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement resolvedPlacement = new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X);

        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("cavern", deadPlacement)
            .withPortal("cavern", displacedPlacement)
            .withPortal("cavern", resolvedPlacement);
        IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot snapshot =
            new IndexedPlacementRevalidationPolicy.IndexedPlacementRevalidationSnapshot(
                Set.of(resolvedPlacement),
                Set.of(),
                Set.of(displacedPlacement),
                Set.of(deadPlacement),
                Map.of(resolvedPlacement, resolvedPlacement),
                Map.of()
            );

        PortalWorldIndex refreshedIndex = snapshot.withRetainedPortal(
            "cavern",
            worldIndex,
            resolvedPlacement
        );

        Set<PortalWorldIndex.PortalPlacement> refreshedPlacements = refreshedIndex.placementsFor("cavern");
        assertEquals(2, refreshedPlacements.size());
        assertFalse(refreshedPlacements.contains(deadPlacement));
        assertTrue(refreshedPlacements.contains(displacedPlacement));
        assertTrue(refreshedPlacements.contains(resolvedPlacement));
        assertEquals(resolvedPlacement, refreshedIndex.firstPlacementFor("cavern").orElseThrow());
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
                Set.of(),
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
