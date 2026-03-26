package com.richardkenway.cavernreborn.app.dimension;

import java.util.UUID;

public interface PlayerTravelContext {
    UUID playerId();

    float yaw();

    float pitch();

    void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch);
}
