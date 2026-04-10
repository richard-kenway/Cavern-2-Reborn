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
    public static final String PORTAL_COOLDOWN_MESSAGE_KEY = "message.cavernreborn.portal_cooldown";
    public static final String PORTAL_ENTRY_FAILED_MESSAGE_KEY = "message.cavernreborn.portal_entry_failed";
    public static final String PORTAL_RETURN_MISSING_MESSAGE_KEY = "message.cavernreborn.portal_return_missing";
    private static final long SUCCESS_COOLDOWN_TICKS = 20L;
    private static final long FAILURE_COOLDOWN_TICKS = 10L;
    private static final long FEEDBACK_SUPPRESSION_TICKS = 10L;

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

    public CavernPortalInteractionOutcome use(CavernPortalInteractionContext context) {
        Objects.requireNonNull(context, "context");

        if (context.isClientSide()) {
            return CavernPortalInteractionOutcome.unhandled();
        }

        if (isOnCooldown(context)) {
            showFeedbackOnce(context, "cooldown", PORTAL_COOLDOWN_MESSAGE_KEY);
            return CavernPortalInteractionOutcome.handled(Optional.empty());
        }

        if (CavernDimensions.isCavern(context.currentDimensionId())) {
            Optional<CavernTravelPlan> returnPlan = travelBridge.returnHome(context);
            if (returnPlan.isPresent()) {
                applyCooldown(context, true);
                return CavernPortalInteractionOutcome.handled(returnPlan);
            }

            showFeedbackOnce(context, "return_missing", PORTAL_RETURN_MISSING_MESSAGE_KEY);
            return CavernPortalInteractionOutcome.handled(Optional.empty());
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
            context.portalZ(),
            context.portalAxis()
        );

        Optional<CavernTravelPlan> travelPlan = travelBridge.travelToCavern(context, returnState, teleportContext, portalPlacement);
        if (travelPlan.isEmpty()) {
            applyCooldown(context, false);
            suppressFeedback(context, "cooldown");
            showFeedbackOnce(context, "enter_failed", PORTAL_ENTRY_FAILED_MESSAGE_KEY);
            return CavernPortalInteractionOutcome.handled(Optional.empty());
        }

        applyCooldown(context, true);
        return CavernPortalInteractionOutcome.handled(travelPlan);
    }

    private void applyCooldown(CavernPortalInteractionContext context, boolean success) {
        long durationTicks = success ? SUCCESS_COOLDOWN_TICKS : FAILURE_COOLDOWN_TICKS;
        cooldowns.markCooldown(cooldownKeyFor(context), context.gameTime(), durationTicks);
    }

    private void showFeedbackOnce(CavernPortalInteractionContext context, String feedbackReason, String translationKey) {
        String feedbackCooldownKey = feedbackCooldownKeyFor(context, feedbackReason);
        if (cooldowns.isOnCooldown(feedbackCooldownKey, context.gameTime())) {
            return;
        }

        suppressFeedback(context, feedbackReason);
        context.showPortalFeedback(translationKey);
    }

    private void suppressFeedback(CavernPortalInteractionContext context, String feedbackReason) {
        String feedbackCooldownKey = feedbackCooldownKeyFor(context, feedbackReason);
        cooldowns.markCooldown(feedbackCooldownKey, context.gameTime(), FEEDBACK_SUPPRESSION_TICKS);
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

    private static String feedbackCooldownKeyFor(CavernPortalInteractionContext context, String feedbackReason) {
        return cooldownKeyFor(context) + "|feedback|" + feedbackReason;
    }
}
