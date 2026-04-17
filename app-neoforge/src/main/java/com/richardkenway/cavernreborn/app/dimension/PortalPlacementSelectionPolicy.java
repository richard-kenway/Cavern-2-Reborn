package com.richardkenway.cavernreborn.app.dimension;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

final class PortalPlacementSelectionPolicy {
    private static final double PORTAL_CENTER_X_OFFSET = 0.5D;
    private static final double PORTAL_CENTER_Z_OFFSET = 0.5D;
    private static final double PORTAL_AXIS_CENTER_OFFSET = 1.0D;

    private PortalPlacementSelectionPolicy() {
    }

    static <T> Optional<IndexedPlacementCandidate<T>> nearestCandidate(
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements,
        Function<PortalWorldIndex.PortalPlacement, Optional<T>> candidateResolver,
        ToDoubleFunction<T> distanceFromPreferredTarget
    ) {
        IndexedPlacementCandidate<T> bestCandidate = null;

        for (PortalWorldIndex.PortalPlacement indexedPlacement : indexedPlacements) {
            Optional<T> resolvedPlacement = candidateResolver.apply(indexedPlacement);
            if (resolvedPlacement.isEmpty()) {
                continue;
            }

            T candidatePlacement = resolvedPlacement.get();
            double distance = distanceFromPreferredTarget.applyAsDouble(candidatePlacement);
            if (bestCandidate == null || distance < bestCandidate.distance()) {
                bestCandidate = new IndexedPlacementCandidate<>(indexedPlacement, candidatePlacement, distance);
            }
        }

        return Optional.ofNullable(bestCandidate);
    }

    static double distanceFromPreferredTarget(CavernPlacementTarget target, PortalWorldIndex.PortalPlacement placement) {
        double centeredX = placement.isXAxis()
            ? placement.x() + PORTAL_AXIS_CENTER_OFFSET
            : placement.x() + PORTAL_CENTER_X_OFFSET;

        double centeredZ = placement.isXAxis()
            ? placement.z() + PORTAL_CENTER_Z_OFFSET
            : placement.z() + PORTAL_AXIS_CENTER_OFFSET;

        double dx = centeredX - target.x();
        double dy = placement.y() - target.y();
        double dz = centeredZ - target.z();

        return dx * dx + dy * dy + dz * dz;
    }

    static record IndexedPlacementCandidate<T>(PortalWorldIndex.PortalPlacement indexedPlacement, T resolvedPlacement, double distance) {
    }
}
