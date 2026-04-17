package com.richardkenway.cavernreborn.app.dimension;

import java.util.Comparator;
import java.util.Locale;
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
    private static final double AXIS_COMPATIBILITY_DISTANCE_BAND = 1.0D;
    private static final Comparator<PortalWorldIndex.PortalPlacement> STABLE_PLACEMENT_ORDER = Comparator
        .comparingInt(PortalWorldIndex.PortalPlacement::y)
        .thenComparingInt(PortalWorldIndex.PortalPlacement::x)
        .thenComparingInt(PortalWorldIndex.PortalPlacement::z)
        .thenComparing(PortalWorldIndex.PortalPlacement::axis);

    private PortalPlacementSelectionPolicy() {
    }

    static <T> Optional<IndexedPlacementCandidate<T>> nearestCandidate(
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements,
        Function<PortalWorldIndex.PortalPlacement, Optional<T>> candidateResolver,
        ToDoubleFunction<T> distanceFromPreferredTarget
    ) {
        return nearestCandidate(indexedPlacements, candidateResolver, distanceFromPreferredTarget, Optional.empty());
    }

    static <T> Optional<IndexedPlacementCandidate<T>> nearestCandidate(
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements,
        Function<PortalWorldIndex.PortalPlacement, Optional<T>> candidateResolver,
        ToDoubleFunction<T> distanceFromPreferredTarget,
        Optional<String> preferredSourceAxis
    ) {
        IndexedPlacementCandidate<T> bestCandidate = null;
        String normalizedAxis = preferredSourceAxis
            .filter(axis -> !axis.isBlank())
            .map(axis -> axis.toLowerCase(Locale.ROOT))
            .orElse(null);

        for (PortalWorldIndex.PortalPlacement indexedPlacement : indexedPlacements) {
            Optional<T> resolvedPlacement = candidateResolver.apply(indexedPlacement);
            if (resolvedPlacement.isEmpty()) {
                continue;
            }

            T candidatePlacement = resolvedPlacement.get();
            double distance = distanceFromPreferredTarget.applyAsDouble(candidatePlacement);
            IndexedPlacementCandidate<T> candidate = new IndexedPlacementCandidate<>(
                indexedPlacement,
                candidatePlacement,
                distance
            );

            if (bestCandidate == null || isPreferredCandidate(candidate, bestCandidate, normalizedAxis)) {
                bestCandidate = candidate;
            }
        }

        return Optional.ofNullable(bestCandidate);
    }

    private static <T> boolean isPreferredCandidate(
        IndexedPlacementCandidate<T> candidate,
        IndexedPlacementCandidate<T> incumbent,
        String preferredSourceAxis
    ) {
        double distanceDiff = candidate.distance() - incumbent.distance();
        if (distanceDiff < -AXIS_COMPATIBILITY_DISTANCE_BAND) {
            return true;
        }

        if (distanceDiff > AXIS_COMPATIBILITY_DISTANCE_BAND) {
            return false;
        }

        String candidateAxis = axisForPlacement(candidate);
        String incumbentAxis = axisForPlacement(incumbent);
        int preferredAxisComparison = compareAxisAlignment(candidateAxis, incumbentAxis, preferredSourceAxis);

        if (preferredAxisComparison != 0) {
            return preferredAxisComparison > 0;
        }

        if (candidate.distance() != incumbent.distance()) {
            return candidate.distance() < incumbent.distance();
        }

        return STABLE_PLACEMENT_ORDER.compare(candidate.indexedPlacement(), incumbent.indexedPlacement()) < 0;
    }

    private static <T> String axisForPlacement(IndexedPlacementCandidate<T> candidate) {
        if (candidate.resolvedPlacement() instanceof PortalWorldIndex.PortalPlacement resolvedPlacement) {
            return resolvedPlacement.axis();
        }

        return candidate.indexedPlacement().axis();
    }

    private static int compareAxisAlignment(
        String candidateAxis,
        String incumbentAxis,
        String preferredSourceAxis
    ) {
        if (preferredSourceAxis == null || preferredSourceAxis.isBlank()) {
            return 0;
        }

        boolean candidateMatches = candidateAxis.equals(preferredSourceAxis);
        boolean incumbentMatches = incumbentAxis.equals(preferredSourceAxis);
        if (candidateMatches == incumbentMatches) {
            return 0;
        }

        return candidateMatches ? 1 : -1;
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
