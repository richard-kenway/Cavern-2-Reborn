package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;

public final class CavernArrivalPlacementResolver {
    private static final int VERTICAL_SEARCH_WINDOW = 24;
    private static final int HORIZONTAL_SEARCH_RADIUS = 1;

    public Optional<CavernPlacementTarget> resolve(CavernPlacementTarget placementTarget, CavernArrivalPlacementProbe arrivalProbe) {
        Objects.requireNonNull(placementTarget, "placementTarget");
        Objects.requireNonNull(arrivalProbe, "arrivalProbe");

        if (!CavernDimensions.isCavern(placementTarget.dimensionId())) {
            return Optional.of(placementTarget);
        }

        CavernPlacementTarget resolvedTarget = resolveColumn(placementTarget, arrivalProbe, (int) placementTarget.x(), (int) placementTarget.z());
        if (resolvedTarget != null) {
            return Optional.of(resolvedTarget);
        }

        for (int horizontalOffset = 1; horizontalOffset <= HORIZONTAL_SEARCH_RADIUS; horizontalOffset++) {
            for (int deltaX = -horizontalOffset; deltaX <= horizontalOffset; deltaX++) {
                for (int deltaZ = -horizontalOffset; deltaZ <= horizontalOffset; deltaZ++) {
                    if (Math.max(Math.abs(deltaX), Math.abs(deltaZ)) != horizontalOffset) {
                        continue;
                    }

                    resolvedTarget = resolveColumn(
                        placementTarget,
                        arrivalProbe,
                        (int) placementTarget.x() + deltaX,
                        (int) placementTarget.z() + deltaZ
                    );
                    if (resolvedTarget != null) {
                        return Optional.of(resolvedTarget);
                    }
                }
            }
        }

        return Optional.empty();
    }

    private CavernPlacementTarget resolveColumn(
        CavernPlacementTarget placementTarget,
        CavernArrivalPlacementProbe arrivalProbe,
        int x,
        int z
    ) {
        if (arrivalProbe.isSafeArrivalAt(placementTarget.dimensionId(), x, (int) placementTarget.y(), z)) {
            return new CavernPlacementTarget(placementTarget.dimensionId(), x, placementTarget.y(), z);
        }

        for (int verticalOffset = 1; verticalOffset <= VERTICAL_SEARCH_WINDOW; verticalOffset++) {
            int lowerY = (int) placementTarget.y() - verticalOffset;
            if (arrivalProbe.isSafeArrivalAt(placementTarget.dimensionId(), x, lowerY, z)) {
                return new CavernPlacementTarget(placementTarget.dimensionId(), x, lowerY, z);
            }

            int upperY = (int) placementTarget.y() + verticalOffset;
            if (arrivalProbe.isSafeArrivalAt(placementTarget.dimensionId(), x, upperY, z)) {
                return new CavernPlacementTarget(placementTarget.dimensionId(), x, upperY, z);
            }
        }

        return null;
    }
}
