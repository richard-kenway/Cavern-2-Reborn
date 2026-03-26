package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;

public final class CavernArrivalPlacementResolver {
    private static final int VERTICAL_SEARCH_WINDOW = 24;

    public CavernPlacementTarget resolve(CavernPlacementTarget placementTarget, CavernArrivalPlacementProbe arrivalProbe) {
        Objects.requireNonNull(placementTarget, "placementTarget");
        Objects.requireNonNull(arrivalProbe, "arrivalProbe");

        if (!CavernDimensions.isCavern(placementTarget.dimensionId())) {
            return placementTarget;
        }

        if (arrivalProbe.isSafeArrivalAt(placementTarget.dimensionId(), (int) placementTarget.x(), (int) placementTarget.y(), (int) placementTarget.z())) {
            return placementTarget;
        }

        for (int verticalOffset = 1; verticalOffset <= VERTICAL_SEARCH_WINDOW; verticalOffset++) {
            int lowerY = (int) placementTarget.y() - verticalOffset;
            if (arrivalProbe.isSafeArrivalAt(placementTarget.dimensionId(), (int) placementTarget.x(), lowerY, (int) placementTarget.z())) {
                return new CavernPlacementTarget(placementTarget.dimensionId(), placementTarget.x(), lowerY, placementTarget.z());
            }

            int upperY = (int) placementTarget.y() + verticalOffset;
            if (arrivalProbe.isSafeArrivalAt(placementTarget.dimensionId(), (int) placementTarget.x(), upperY, (int) placementTarget.z())) {
                return new CavernPlacementTarget(placementTarget.dimensionId(), placementTarget.x(), upperY, placementTarget.z());
            }
        }

        return placementTarget;
    }
}
