package com.richardkenway.cavernreborn.app.dimension;

import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

public interface PlayerTravelContext {
    UUID playerId();

    long gameTime();

    float yaw();

    float pitch();

    default Optional<CavernPlacementTarget> fallbackReturnTarget() {
        return Optional.empty();
    }

    default boolean hasPortalAt(String targetDimensionId, int x, int y, int z) {
        return false;
    }

    default Optional<PortalWorldIndex.PortalPlacement> createPortalAt(String targetDimensionId, int x, int y, int z) {
        return Optional.empty();
    }

    void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch);
}
