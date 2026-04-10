package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementResolver;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.CavernDimensionTravelPlanner;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalReturnOperation;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

public final class CavernTravelBridge {
    private static final double PORTAL_CENTER_X_OFFSET = 0.5D;
    private static final double PORTAL_CENTER_Z_OFFSET = 0.5D;
    private static final double PORTAL_AXIS_CENTER_OFFSET = 1.0D;

    private final CavernDimensionTravelPlanner travelPlanner;
    private final WorldPortalIndexStore worldPortalIndexStore;
    private final CavernPlacementResolver placementResolver;
    private final CavernArrivalPlacementResolver arrivalPlacementResolver;

    public CavernTravelBridge(CavernDimensionTravelPlanner travelPlanner, WorldPortalIndexStore worldPortalIndexStore) {
        this(travelPlanner, worldPortalIndexStore, new CavernPlacementResolver(), new CavernArrivalPlacementResolver());
    }

    public CavernTravelBridge(
        CavernDimensionTravelPlanner travelPlanner,
        WorldPortalIndexStore worldPortalIndexStore,
        CavernPlacementResolver placementResolver
    ) {
        this(travelPlanner, worldPortalIndexStore, placementResolver, new CavernArrivalPlacementResolver());
    }

    public CavernTravelBridge(
        CavernDimensionTravelPlanner travelPlanner,
        WorldPortalIndexStore worldPortalIndexStore,
        CavernPlacementResolver placementResolver,
        CavernArrivalPlacementResolver arrivalPlacementResolver
    ) {
        this.travelPlanner = Objects.requireNonNull(travelPlanner, "travelPlanner");
        this.worldPortalIndexStore = Objects.requireNonNull(worldPortalIndexStore, "worldPortalIndexStore");
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
        if (plan.isPresent()) {
            plan.ifPresent(cavernTravelPlan -> travel(player, cavernTravelPlan));
            return plan;
        }

        return fallbackReturnHome(player);
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

        placementTarget = resolveDestinationPortalTarget(player, plan, placementTarget).orElse(placementTarget);

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

    private Optional<CavernPlacementTarget> resolveDestinationPortalTarget(
        PlayerTravelContext player,
        CavernTravelPlan plan,
        CavernPlacementTarget preferredTarget
    ) {
        Optional<String> portalKey = portalKeyFor(plan);
        if (portalKey.isEmpty()) {
            return Optional.empty();
        }

        String targetDimensionId = preferredTarget.dimensionId();
        String resolvedPortalKey = portalKey.get();
        PortalWorldIndex worldIndex = worldPortalIndexStore.load(targetDimensionId);
        Optional<PortalWorldIndex.PortalPlacement> indexedPlacement = worldIndex.firstPlacementFor(resolvedPortalKey);

        if (indexedPlacement.isPresent()) {
            PortalWorldIndex.PortalPlacement placement = indexedPlacement.get();
            if (player.hasPortalAt(targetDimensionId, placement.x(), placement.y(), placement.z())) {
                return Optional.of(toPlacementTarget(targetDimensionId, placement));
            }

            Optional<PortalWorldIndex.PortalPlacement> relinkedPlacement = player.findPortalNear(
                targetDimensionId,
                placement.x(),
                placement.y(),
                placement.z()
            );
            if (relinkedPlacement.isPresent()) {
                PortalWorldIndex refreshedIndex = worldIndex
                    .withoutPortal(resolvedPortalKey, placement)
                    .withPortal(resolvedPortalKey, relinkedPlacement.get());
                worldPortalIndexStore.save(targetDimensionId, refreshedIndex);
                return Optional.of(toPlacementTarget(targetDimensionId, relinkedPlacement.get()));
            }

            worldPortalIndexStore.save(targetDimensionId, worldIndex.withoutPortal(resolvedPortalKey, placement));
        }

        Optional<PortalWorldIndex.PortalPlacement> nearbyPortal = player.findPortalNear(
            targetDimensionId,
            (int) Math.floor(preferredTarget.x()),
            (int) Math.floor(preferredTarget.y()),
            (int) Math.floor(preferredTarget.z())
        );
        if (nearbyPortal.isPresent()) {
            PortalWorldIndex refreshedIndex = worldPortalIndexStore.load(targetDimensionId).withPortal(resolvedPortalKey, nearbyPortal.get());
            worldPortalIndexStore.save(targetDimensionId, refreshedIndex);
            return Optional.of(toPlacementTarget(targetDimensionId, nearbyPortal.get()));
        }

        return player.createPortalAt(
                targetDimensionId,
                (int) Math.floor(preferredTarget.x()),
                (int) Math.floor(preferredTarget.y()),
                (int) Math.floor(preferredTarget.z())
            )
            .map(placement -> {
                PortalWorldIndex refreshedIndex = worldPortalIndexStore.load(targetDimensionId).withPortal(resolvedPortalKey, placement);
                worldPortalIndexStore.save(targetDimensionId, refreshedIndex);
                return toPlacementTarget(targetDimensionId, placement);
            });
    }

    private Optional<CavernTravelPlan> fallbackReturnHome(PlayerTravelContext player) {
        return player.fallbackReturnTarget()
            .map(CavernTravelBridge::fallbackReturnPlan)
            .flatMap(plan -> travel(player, plan));
    }

    private static CavernPlacementTarget cavernEntryTarget() {
        return new CavernPlacementTarget(
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_ENTRY_X,
            CavernDimensions.CAVERN_ENTRY_Y,
            CavernDimensions.CAVERN_ENTRY_Z
        );
    }

    private static CavernTravelPlan fallbackReturnPlan(CavernPlacementTarget placementTarget) {
        int targetX = (int) Math.floor(placementTarget.x());
        int targetY = (int) Math.floor(placementTarget.y());
        int targetZ = (int) Math.floor(placementTarget.z());

        PortalReturnState fallbackState = new PortalReturnState(
            "fallback_return",
            placementTarget.dimensionId(),
            targetX,
            targetY,
            targetZ
        );

        return CavernTravelPlan.returnHome(new PortalReturnOperation(fallbackState, Optional.empty()));
    }

    private static Optional<String> portalKeyFor(CavernTravelPlan plan) {
        return plan.entryReceipt()
            .map(receipt -> receipt.returnState().portalKey())
            .or(() -> plan.returnOperation().map(operation -> operation.returnState().portalKey()));
    }

    private static CavernPlacementTarget toPlacementTarget(String dimensionId, PortalWorldIndex.PortalPlacement placement) {
        double centeredX = placement.isXAxis()
            ? placement.x() + PORTAL_AXIS_CENTER_OFFSET
            : placement.x() + PORTAL_CENTER_X_OFFSET;
        double centeredZ = placement.isXAxis()
            ? placement.z() + PORTAL_CENTER_Z_OFFSET
            : placement.z() + PORTAL_AXIS_CENTER_OFFSET;

        return new CavernPlacementTarget(dimensionId, centeredX, placement.y(), centeredZ);
    }
}
