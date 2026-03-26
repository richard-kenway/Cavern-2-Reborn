package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.dimension.CavernTravelBridge;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

public final class CavernPortalInteractionService {
    public static final String PORTAL_KEY = "cavern_portal";

    private final CavernTravelBridge travelBridge;

    public CavernPortalInteractionService(CavernTravelBridge travelBridge) {
        this.travelBridge = Objects.requireNonNull(travelBridge, "travelBridge");
    }

    public Optional<CavernTravelPlan> use(CavernPortalInteractionContext context) {
        Objects.requireNonNull(context, "context");

        if (context.isClientSide()) {
            return Optional.empty();
        }

        if (CavernDimensions.isCavern(context.currentDimensionId())) {
            return travelBridge.returnHome(context);
        }

        PortalReturnState returnState = new PortalReturnState(
            PORTAL_KEY,
            context.currentDimensionId(),
            context.portalX(),
            context.portalY(),
            context.portalZ()
        );
        TeleportContext teleportContext = new TeleportContext(
            PORTAL_KEY,
            context.hitOffsetX(),
            context.hitOffsetY(),
            context.hitOffsetZ(),
            context.approachFacing()
        );
        PortalWorldIndex.PortalPlacement portalPlacement = new PortalWorldIndex.PortalPlacement(
            context.portalX(),
            context.portalY(),
            context.portalZ()
        );

        return travelBridge.travelToCavern(context, returnState, teleportContext, portalPlacement);
    }
}
