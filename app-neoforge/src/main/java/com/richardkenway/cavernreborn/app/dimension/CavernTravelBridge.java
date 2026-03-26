package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.state.CavernPlacementResolver;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.CavernDimensionTravelPlanner;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

public final class CavernTravelBridge {
    private final CavernDimensionTravelPlanner travelPlanner;
    private final CavernPlacementResolver placementResolver;

    public CavernTravelBridge(CavernDimensionTravelPlanner travelPlanner) {
        this(travelPlanner, new CavernPlacementResolver());
    }

    public CavernTravelBridge(CavernDimensionTravelPlanner travelPlanner, CavernPlacementResolver placementResolver) {
        this.travelPlanner = Objects.requireNonNull(travelPlanner, "travelPlanner");
        this.placementResolver = Objects.requireNonNull(placementResolver, "placementResolver");
    }

    public Optional<CavernTravelPlan> travelToCavern(
        PlayerTravelContext player,
        PortalReturnState returnState,
        TeleportContext teleportContext,
        PortalWorldIndex.PortalPlacement portalPlacement
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(returnState, "returnState");
        Objects.requireNonNull(teleportContext, "teleportContext");
        Objects.requireNonNull(portalPlacement, "portalPlacement");

        CavernTravelPlan plan = travelPlanner.planEntry(player.playerId(), returnState, teleportContext, portalPlacement);

        travel(player, plan);
        return Optional.of(plan);
    }

    public Optional<CavernTravelPlan> returnHome(PlayerTravelContext player) {
        Objects.requireNonNull(player, "player");

        Optional<CavernTravelPlan> plan = travelPlanner.planReturn(player.playerId());
        plan.ifPresent(cavernTravelPlan -> travel(player, cavernTravelPlan));
        return plan;
    }

    public void travel(PlayerTravelContext player, CavernTravelPlan plan) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(plan, "plan");
        CavernPlacementTarget placementTarget = placementResolver.resolve(plan);

        player.teleportTo(
            placementTarget.dimensionId(),
            placementTarget.x(),
            placementTarget.y(),
            placementTarget.z(),
            player.yaw(),
            player.pitch()
        );
    }
}
