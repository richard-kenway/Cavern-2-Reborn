package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

class PortalPlacementSelectionPolicyTest {
    @Test
    void selectsAxisAlignedCandidateWhenDistanceIsTiedOrNear() {
        CavernPlacementTarget preferredTarget = new CavernPlacementTarget("dimension", 0.0D, 64.0D, 0.0D);
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements = new HashSet<>(
            List.of(
                new PortalWorldIndex.PortalPlacement(0, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_Z),
                new PortalWorldIndex.PortalPlacement(0, 64, -1, PortalWorldIndex.PortalPlacement.AXIS_X)
            )
        );

        Optional<PortalPlacementSelectionPolicy.IndexedPlacementCandidate<PortalWorldIndex.PortalPlacement>> candidate =
            PortalPlacementSelectionPolicy.nearestCandidate(
                indexedPlacements,
                Optional::of,
                indexedPlacement -> PortalPlacementSelectionPolicy.distanceFromPreferredTarget(preferredTarget, indexedPlacement),
                Optional.of(PortalWorldIndex.PortalPlacement.AXIS_X)
            );

        assertTrue(candidate.isPresent());
        assertEquals(PortalWorldIndex.PortalPlacement.AXIS_X, candidate.get().resolvedPlacement().axis());
    }

    @Test
    void prefersNearbyPlacementWhenAxisAlignmentWouldRequireClearlyLongerDistance() {
        CavernPlacementTarget preferredTarget = new CavernPlacementTarget("dimension", 0.0D, 64.0D, 0.0D);
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements = Set.of(
            new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_Z),
            new PortalWorldIndex.PortalPlacement(50, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        Optional<PortalPlacementSelectionPolicy.IndexedPlacementCandidate<PortalWorldIndex.PortalPlacement>> candidate =
            PortalPlacementSelectionPolicy.nearestCandidate(
                indexedPlacements,
                Optional::of,
                indexedPlacement -> PortalPlacementSelectionPolicy.distanceFromPreferredTarget(preferredTarget, indexedPlacement),
                Optional.of(PortalWorldIndex.PortalPlacement.AXIS_X)
            );

        assertTrue(candidate.isPresent());
        assertEquals(PortalWorldIndex.PortalPlacement.AXIS_Z, candidate.get().resolvedPlacement().axis());
    }

    @Test
    void deterministicFallbackWithoutAxisPreferenceUsesStableTieBreaker() {
        CavernPlacementTarget preferredTarget = new CavernPlacementTarget("dimension", 0.0D, 64.0D, 0.0D);
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements = new HashSet<>(
            List.of(
                new PortalWorldIndex.PortalPlacement(0, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_Z),
                new PortalWorldIndex.PortalPlacement(0, 64, -1, PortalWorldIndex.PortalPlacement.AXIS_X)
            )
        );

        for (int attempt = 0; attempt < 10; attempt++) {
            Optional<PortalPlacementSelectionPolicy.IndexedPlacementCandidate<PortalWorldIndex.PortalPlacement>> candidate =
                PortalPlacementSelectionPolicy.nearestCandidate(
                    indexedPlacements,
                    Optional::of,
                    indexedPlacement -> PortalPlacementSelectionPolicy.distanceFromPreferredTarget(preferredTarget, indexedPlacement)
                );

            assertTrue(candidate.isPresent());
            PortalWorldIndex.PortalPlacement resolvedPlacement = candidate.get().resolvedPlacement();
            assertEquals(64, resolvedPlacement.y());
            assertEquals(0, resolvedPlacement.x());
            assertEquals(-1, resolvedPlacement.z());
        }
    }
}
