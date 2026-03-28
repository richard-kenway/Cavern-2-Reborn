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
    private static final long SUCCESS_COOLDOWN_TICKS = 20L;
    private static final long FAILURE_COOLDOWN_TICKS = 10L;

    private final CavernTravelBridge travelBridge;
    private final PortalInteractionCooldowns cooldowns;

    public CavernPortalInteractionService(CavernTravelBridge travelBridge, PortalInteractionCooldowns cooldowns) {
        this.travelBridge = Objects.requireNonNull(travelBridge, "travelBridge");
        this.cooldowns = Objects.requireNonNull(cooldowns, "cooldowns");
    }

    public boolean isOnCooldown(CavernPortalInteractionContext context) {
        Objects.requireNonNull(context, "context");
        return cooldowns.isOnCooldown(cooldownKeyFor(context), context.gameTime());
    }

    public Optional<CavernTravelPlan> use(CavernPortalInteractionContext context) {
        Objects.requireNonNull(context, "context");

        if (context.isClientSide()) {
            return Optional.empty();
        }

        if (isOnCooldown(context)) {
            return Optional.empty();
        }

        if (CavernDimensions.isCavern(context.currentDimensionId())) {
            Optional<CavernTravelPlan> returnPlan = travelBridge.returnHome(context);
            if (returnPlan.isPresent()) {
                applyCooldown(context, true);
            }
            return returnPlan;
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
            applyCooldown(context, false);
            context.showPortalFeedback(PORTAL_ENTRY_FAILED_MESSAGE_KEY);
        } else {
            applyCooldown(context, true);
        }

        return travelPlan;
    }

    private void applyCooldown(CavernPortalInteractionContext context, boolean success) {
        long durationTicks = success ? SUCCESS_COOLDOWN_TICKS : FAILURE_COOLDOWN_TICKS;
        cooldowns.markCooldown(cooldownKeyFor(context), context.gameTime(), durationTicks);
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

    private static String cooldownKeyFor(CavernPortalInteractionContext context) {
        return context.playerId()
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
