package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;
import java.util.Optional;

public record PortalReturnOperation(
    PortalReturnState returnState,
    Optional<PortalWorldIndex.PortalPlacement> resolvedPlacement
) {
    public PortalReturnOperation {
        returnState = Objects.requireNonNull(returnState, "returnState");
        resolvedPlacement = Objects.requireNonNull(resolvedPlacement, "resolvedPlacement");
    }

    public String targetDimensionId() {
        return returnState.returnDimensionId();
    }

    public int targetX() {
        return resolvedPlacement.map(PortalWorldIndex.PortalPlacement::x).orElse(returnState.returnX());
    }

    public int targetY() {
        return resolvedPlacement.map(PortalWorldIndex.PortalPlacement::y).orElse(returnState.returnY());
    }

    public int targetZ() {
        return resolvedPlacement.map(PortalWorldIndex.PortalPlacement::z).orElse(returnState.returnZ());
    }

    public boolean usesWorldIndex() {
        return resolvedPlacement.isPresent();
    }
}
