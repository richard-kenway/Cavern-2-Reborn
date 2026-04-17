package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.dimension.CavernTravelBridge;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalReturnOperation;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;
import com.richardkenway.cavernreborn.core.state.PortalLoopService;

public final class CavernNonPlayerPortalInteractionService {
    private static final String PORTAL_KEY_PREFIX = "cavern_portal";

    private final CavernTravelBridge travelBridge;
    private final PortalLoopService portalLoopService;
    private final WorldPortalIndexStore worldPortalIndexStore;

    public CavernNonPlayerPortalInteractionService(
        CavernTravelBridge travelBridge,
        PortalLoopService portalLoopService,
        WorldPortalIndexStore worldPortalIndexStore
    ) {
        this.travelBridge = Objects.requireNonNull(travelBridge, "travelBridge");
        this.portalLoopService = Objects.requireNonNull(portalLoopService, "portalLoopService");
        this.worldPortalIndexStore = Objects.requireNonNull(worldPortalIndexStore, "worldPortalIndexStore");
    }

    public CavernPortalInteractionOutcome use(NonPlayerPortalInteractionContext context) {
        Objects.requireNonNull(context, "context");

        if (context.isClientSide()) {
            return CavernPortalInteractionOutcome.unhandled();
        }

        if (CavernDimensions.isCavern(context.currentDimensionId())) {
            return returnHome(context);
        }

        return enterCavern(context);
    }

    private CavernPortalInteractionOutcome enterCavern(NonPlayerPortalInteractionContext context) {
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
        PortalWorldIndex.PortalPlacement sourcePortalPlacement = new PortalWorldIndex.PortalPlacement(
            context.portalX(),
            context.portalY(),
            context.portalZ(),
            context.portalAxis()
        );
        PortalEntryReceipt entryReceipt = new PortalEntryReceipt(returnState, teleportContext, sourcePortalPlacement);
        CavernTravelPlan travelPlan = CavernTravelPlan.enterCavern(entryReceipt);
        Optional<CavernTravelPlan> resolvedPlan = travelBridge.travel(context, travelPlan);
        if (resolvedPlan.isEmpty()) {
            return CavernPortalInteractionOutcome.handled(Optional.empty());
        }

        portalLoopService.registerPortal(returnState.returnDimensionId(), portalKey, sourcePortalPlacement);
        context.savePortalEntryReceipt(entryReceipt);
        return CavernPortalInteractionOutcome.handled(resolvedPlan);
    }

    private CavernPortalInteractionOutcome returnHome(NonPlayerPortalInteractionContext context) {
        Optional<PortalEntryReceipt> savedEntryReceipt = context.loadPortalEntryReceipt();
        if (savedEntryReceipt.isEmpty()) {
            return CavernPortalInteractionOutcome.unhandled();
        }

        PortalEntryReceipt entryReceipt = savedEntryReceipt.get();
        PortalReturnOperation returnOperation = new PortalReturnOperation(
            entryReceipt.returnState(),
            worldPortalIndexStore.load(entryReceipt.returnState().returnDimensionId())
                .firstPlacementFor(entryReceipt.returnState().portalKey())
        );
        CavernTravelPlan returnPlan = CavernTravelPlan.returnHome(returnOperation);

        Optional<CavernTravelPlan> resolvedPlan = travelBridge.travel(context, returnPlan);
        if (resolvedPlan.isEmpty()) {
            return CavernPortalInteractionOutcome.handled(Optional.empty());
        }

        context.clearPortalEntryReceipt();
        return CavernPortalInteractionOutcome.handled(resolvedPlan);
    }

    private static String portalKeyFor(NonPlayerPortalInteractionContext context) {
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
