package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class CavernDimensionTravelPlanner {
    private final PortalLoopService portalLoopService;

    public CavernDimensionTravelPlanner(PortalLoopService portalLoopService) {
        this.portalLoopService = Objects.requireNonNull(portalLoopService, "portalLoopService");
    }

    public CavernTravelPlan planEntry(
        UUID playerId,
        PortalReturnState returnState,
        TeleportContext teleportContext,
        PortalWorldIndex.PortalPlacement portalPlacement
    ) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(returnState, "returnState");
        Objects.requireNonNull(teleportContext, "teleportContext");
        Objects.requireNonNull(portalPlacement, "portalPlacement");

        if (CavernDimensions.isCavern(returnState.returnDimensionId())) {
            throw new IllegalArgumentException("planEntry requires a non-CAVERN home dimension");
        }

        PortalEntryReceipt receipt = portalLoopService.captureEntry(playerId, returnState, teleportContext, portalPlacement);
        return CavernTravelPlan.enterCavern(receipt);
    }

    public Optional<CavernTravelPlan> planReturn(UUID playerId) {
        return portalLoopService.planReturn(playerId).map(CavernTravelPlan::returnHome);
    }
}
