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
    public static final String PORTAL_KEY_PREFIX = "cavern_portal";
    public static final String PORTAL_ENTRY_FAILED_MESSAGE_KEY = "message.cavernreborn.portal_entry_failed";

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

        String portalKey = portalKeyFor(context);
        PortalReturnState returnState = new PortalReturnState(
            portalKey,
            context.currentDimensionId(),
            context.portalX(),
            context.portalY(),
            context.portalZ()
        );
        TeleportContext teleportContext = new TeleportContext(
            portalKey,
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

        Optional<CavernTravelPlan> travelPlan = travelBridge.travelToCavern(context, returnState, teleportContext, portalPlacement);
        if (travelPlan.isEmpty()) {
            context.showPortalFeedback(PORTAL_ENTRY_FAILED_MESSAGE_KEY);
        }

        return travelPlan;
    }

    private static String portalKeyFor(CavernPortalInteractionContext context) {
        return PORTAL_KEY_PREFIX
            + "|"
            + context.currentDimensionId()
            + "|"
            + context.portalX()
            + "|"
            + context.portalY()
            + "|"
            + context.portalZ();
    }
}
