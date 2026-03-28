package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
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
    private final CavernArrivalPlacementResolver arrivalPlacementResolver;

    public CavernTravelBridge(CavernDimensionTravelPlanner travelPlanner) {
        this(travelPlanner, new CavernPlacementResolver(), new CavernArrivalPlacementResolver());
    }

    public CavernTravelBridge(CavernDimensionTravelPlanner travelPlanner, CavernPlacementResolver placementResolver) {
        this(travelPlanner, placementResolver, new CavernArrivalPlacementResolver());
    }

    public CavernTravelBridge(
        CavernDimensionTravelPlanner travelPlanner,
        CavernPlacementResolver placementResolver,
        CavernArrivalPlacementResolver arrivalPlacementResolver
    ) {
        this.travelPlanner = Objects.requireNonNull(travelPlanner, "travelPlanner");
        this.placementResolver = Objects.requireNonNull(placementResolver, "placementResolver");
        this.arrivalPlacementResolver = Objects.requireNonNull(arrivalPlacementResolver, "arrivalPlacementResolver");
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

        if (!(player instanceof CavernArrivalPlacementProbe arrivalPlacementProbe)) {
            return Optional.empty();
        }

        Optional<CavernPlacementTarget> resolvedArrivalTarget = arrivalPlacementResolver.resolve(
            cavernEntryTarget(),
            arrivalPlacementProbe
        );
        if (resolvedArrivalTarget.isEmpty()) {
            return Optional.empty();
        }

        CavernTravelPlan plan = travelPlanner.planEntry(player.playerId(), returnState, teleportContext, portalPlacement);

        Optional<CavernTravelPlan> resolvedPlan = travel(player, plan);
        return resolvedPlan;
    }

    public Optional<CavernTravelPlan> returnHome(PlayerTravelContext player) {
        Objects.requireNonNull(player, "player");

        Optional<CavernTravelPlan> plan = travelPlanner.planReturn(player.playerId());
        plan.ifPresent(cavernTravelPlan -> travel(player, cavernTravelPlan));
        return plan;
    }

    public Optional<CavernTravelPlan> travel(PlayerTravelContext player, CavernTravelPlan plan) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(plan, "plan");
        CavernPlacementTarget placementTarget = placementResolver.resolve(plan);

        if (plan.isEnterCavern()) {
            if (!(player instanceof CavernArrivalPlacementProbe arrivalPlacementProbe)) {
                return Optional.empty();
            }

            Optional<CavernPlacementTarget> resolvedArrivalTarget = arrivalPlacementResolver.resolve(placementTarget, arrivalPlacementProbe);
            if (resolvedArrivalTarget.isEmpty()) {
                return Optional.empty();
            }

            placementTarget = resolvedArrivalTarget.get();
        }

        player.teleportTo(
            placementTarget.dimensionId(),
            placementTarget.x(),
            placementTarget.y(),
            placementTarget.z(),
            player.yaw(),
            player.pitch()
        );

        return Optional.of(plan);
    }

    private static CavernPlacementTarget cavernEntryTarget() {
        return new CavernPlacementTarget(
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_ENTRY_X,
            CavernDimensions.CAVERN_ENTRY_Y,
            CavernDimensions.CAVERN_ENTRY_Z
        );
    }
}
