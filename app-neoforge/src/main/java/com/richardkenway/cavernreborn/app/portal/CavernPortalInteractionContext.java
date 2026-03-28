package com.richardkenway.cavernreborn.app.portal;

import com.richardkenway.cavernreborn.app.dimension.PlayerTravelContext;

public interface CavernPortalInteractionContext extends PlayerTravelContext {
    boolean isClientSide();

    void showPortalFeedback(String translationKey);

    String currentDimensionId();

    int portalX();

    int portalY();

    int portalZ();

    double hitOffsetX();

    double hitOffsetY();

    double hitOffsetZ();

    String approachFacing();
}
