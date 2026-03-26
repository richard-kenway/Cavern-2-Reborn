package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;

public final class CavernPlacementResolver {
    public CavernPlacementTarget resolve(CavernTravelPlan travelPlan) {
        Objects.requireNonNull(travelPlan, "travelPlan");

        double safeY = travelPlan.targetY();
        if (CavernDimensions.isCavern(travelPlan.targetDimensionId())) {
            safeY = Math.max(CavernDimensions.MIN_SAFE_Y, safeY);
        }

        return new CavernPlacementTarget(
            travelPlan.targetDimensionId(),
            travelPlan.targetX(),
            safeY,
            travelPlan.targetZ()
        );
    }
}
