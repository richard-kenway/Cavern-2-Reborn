package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionContext;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementResolver;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.CavernDimensionTravelPlanner;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalReturnOperation;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

import net.minecraft.core.Direction;

public final class CavernTravelBridge {
    private static final double PORTAL_CENTER_X_OFFSET = 0.5D;
    private static final double PORTAL_CENTER_Z_OFFSET = 0.5D;
    private static final double PORTAL_AXIS_CENTER_OFFSET = 1.0D;
    private static final double MAX_LATERAL_PORTAL_OFFSET = 0.45D;

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
        Optional<RelativePortalExit> relativePortalExit = relativePortalExitFor(player, plan);
        float targetYaw = player.yaw();

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

        Optional<ResolvedPortalDestination> resolvedPortalDestination = resolveDestinationPortalTarget(
            player,
            plan,
            placementTarget,
            relativePortalExit,
            player.yaw()
        );
        if (resolvedPortalDestination.isPresent()) {
            placementTarget = resolvedPortalDestination.get().placementTarget();
            targetYaw = resolvedPortalDestination.get().yaw();
        }

        player.teleportTo(
            placementTarget.dimensionId(),
            placementTarget.x(),
            placementTarget.y(),
            placementTarget.z(),
            targetYaw,
            player.pitch()
        );

        return Optional.of(plan);
    }

    private Optional<ResolvedPortalDestination> resolveDestinationPortalTarget(
        PlayerTravelContext player,
        CavernTravelPlan plan,
        CavernPlacementTarget preferredTarget,
        Optional<RelativePortalExit> relativePortalExit,
        float fallbackYaw
    ) {
        Optional<String> portalKey = portalKeyFor(plan);
        if (portalKey.isEmpty()) {
            return Optional.empty();
        }

        String targetDimensionId = preferredTarget.dimensionId();
        String resolvedPortalKey = portalKey.get();
        PortalWorldIndex worldIndex = worldPortalIndexStore.load(targetDimensionId);
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements = worldIndex.placementsFor(resolvedPortalKey);

        Optional<PortalPlacementSelectionPolicy.IndexedPlacementCandidate<PortalWorldIndex.PortalPlacement>> resolvedPlacement =
            PortalPlacementSelectionPolicy.nearestCandidate(
                indexedPlacements,
                placement -> player.resolvePortalAt(targetDimensionId, placement.x(), placement.y(), placement.z()),
                placement -> PortalPlacementSelectionPolicy.distanceFromPreferredTarget(preferredTarget, placement)
            );

        if (resolvedPlacement.isPresent()) {
            PortalWorldIndex refreshedIndex = worldIndex.withPortal(resolvedPortalKey, resolvedPlacement.get().resolvedPlacement());
            worldPortalIndexStore.save(targetDimensionId, refreshedIndex);
            return Optional.of(
                toResolvedPortalDestination(targetDimensionId, resolvedPlacement.get().resolvedPlacement(), relativePortalExit, fallbackYaw)
            );
        }

        Optional<PortalPlacementSelectionPolicy.IndexedPlacementCandidate<PortalWorldIndex.PortalPlacement>> relinkedPlacement =
            PortalPlacementSelectionPolicy.nearestCandidate(
                indexedPlacements,
                placement -> player.findPortalNear(
                    targetDimensionId,
                    placement.x(),
                    placement.y(),
                    placement.z()
                ),
                placement -> PortalPlacementSelectionPolicy.distanceFromPreferredTarget(preferredTarget, placement)
            );
        PortalWorldIndex cleanedIndex = removeIndexedPlacements(worldIndex, resolvedPortalKey, indexedPlacements);

        if (relinkedPlacement.isPresent()) {
            PortalWorldIndex refreshedIndex = cleanedIndex.withPortal(resolvedPortalKey, relinkedPlacement.get().resolvedPlacement());
            worldPortalIndexStore.save(targetDimensionId, refreshedIndex);
            return Optional.of(
                toResolvedPortalDestination(
                    targetDimensionId,
                    relinkedPlacement.get().resolvedPlacement(),
                    relativePortalExit,
                    fallbackYaw
                )
            );
        }

        Optional<PortalWorldIndex.PortalPlacement> regenerationAnchor = PortalPlacementSelectionPolicy.nearestCandidate(
            indexedPlacements,
            Optional::of,
            indexedPlacement -> PortalPlacementSelectionPolicy.distanceFromPreferredTarget(preferredTarget, indexedPlacement)
        ).map(PortalPlacementSelectionPolicy.IndexedPlacementCandidate::indexedPlacement);

        if (regenerationAnchor.isPresent()) {
            worldPortalIndexStore.save(targetDimensionId, cleanedIndex);

            Optional<PortalWorldIndex.PortalPlacement> regeneratedPlacement = player.createReplacementPortalAt(
                targetDimensionId,
                regenerationAnchor.get()
            );
            if (regeneratedPlacement.isPresent()) {
                PortalWorldIndex refreshedIndex = worldPortalIndexStore.load(targetDimensionId).withPortal(resolvedPortalKey, regeneratedPlacement.get());
                worldPortalIndexStore.save(targetDimensionId, refreshedIndex);
                return Optional.of(toResolvedPortalDestination(targetDimensionId, regeneratedPlacement.get(), relativePortalExit, fallbackYaw));
            }
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
            return Optional.of(toResolvedPortalDestination(targetDimensionId, nearbyPortal.get(), relativePortalExit, fallbackYaw));
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
                return toResolvedPortalDestination(targetDimensionId, placement, relativePortalExit, fallbackYaw);
            });
    }

    private static PortalWorldIndex removeIndexedPlacements(
        PortalWorldIndex worldIndex,
        String portalKey,
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements
    ) {
        PortalWorldIndex cleanedIndex = worldIndex;
        for (PortalWorldIndex.PortalPlacement indexedPlacement : indexedPlacements) {
            cleanedIndex = cleanedIndex.withoutPortal(portalKey, indexedPlacement);
        }
        return cleanedIndex;
    }

    private Optional<CavernTravelPlan> fallbackReturnHome(PlayerTravelContext player) {
        return player.fallbackReturnTarget()
            .map(target -> {
                CavernTravelPlan plan = fallbackReturnPlan(target);
                player.teleportTo(
                    target.dimensionId(),
                    target.x(),
                    target.y(),
                    target.z(),
                    player.yaw(),
                    player.pitch()
                );
                return plan;
            });
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

    private static CavernPlacementTarget toPlacementTarget(
        String dimensionId,
        PortalWorldIndex.PortalPlacement placement,
        Optional<RelativePortalExit> relativePortalExit
    ) {
        double centeredX = placement.isXAxis()
            ? placement.x() + PORTAL_AXIS_CENTER_OFFSET
            : placement.x() + PORTAL_CENTER_X_OFFSET;
        double centeredZ = placement.isXAxis()
            ? placement.z() + PORTAL_CENTER_Z_OFFSET
            : placement.z() + PORTAL_AXIS_CENTER_OFFSET;
        double lateralOffset = relativePortalExit.map(RelativePortalExit::lateralOffset).orElse(0.0D);

        if (placement.isXAxis()) {
            centeredX += lateralOffset;
        } else {
            centeredZ += lateralOffset;
        }

        return new CavernPlacementTarget(dimensionId, centeredX, placement.y(), centeredZ);
    }

    private static ResolvedPortalDestination toResolvedPortalDestination(
        String dimensionId,
        PortalWorldIndex.PortalPlacement placement,
        Optional<RelativePortalExit> relativePortalExit,
        float fallbackYaw
    ) {
        CavernPlacementTarget placementTarget = toPlacementTarget(dimensionId, placement, relativePortalExit);
        float targetYaw = resolveExitYaw(placement.axis(), relativePortalExit).orElse(fallbackYaw);
        return new ResolvedPortalDestination(placementTarget, targetYaw);
    }

    private static Optional<RelativePortalExit> relativePortalExitFor(PlayerTravelContext player, CavernTravelPlan plan) {
        if (plan.isEnterCavern()) {
            return plan.entryReceipt().map(CavernTravelBridge::relativePortalExitFromEntryReceipt);
        }

        if (plan.isReturnHome() && player instanceof CavernPortalInteractionContext context) {
            return Optional.of(relativePortalExitFromCurrentContext(context));
        }

        return Optional.empty();
    }

    private static RelativePortalExit relativePortalExitFromEntryReceipt(PortalEntryReceipt receipt) {
        return new RelativePortalExit(
            receipt.sourcePortalPlacement().axis(),
            normalizeLateralOffset(
                receipt.sourcePortalPlacement().axis(),
                receipt.teleportContext().entryOffsetX(),
                receipt.teleportContext().entryOffsetZ()
            ),
            receipt.teleportContext().approachFacing()
        );
    }

    private static RelativePortalExit relativePortalExitFromCurrentContext(CavernPortalInteractionContext context) {
        return new RelativePortalExit(
            context.portalAxis(),
            normalizeLateralOffset(context.portalAxis(), context.hitOffsetX(), context.hitOffsetZ()),
            context.approachFacing()
        );
    }

    private static double normalizeLateralOffset(String axis, double hitOffsetX, double hitOffsetZ) {
        double rawOffset = PortalWorldIndex.PortalPlacement.AXIS_X.equals(axis)
            ? hitOffsetX - PORTAL_AXIS_CENTER_OFFSET
            : hitOffsetZ - PORTAL_AXIS_CENTER_OFFSET;
        return clampLateralOffset(rawOffset);
    }

    private static double clampLateralOffset(double lateralOffset) {
        return Math.max(-MAX_LATERAL_PORTAL_OFFSET, Math.min(MAX_LATERAL_PORTAL_OFFSET, lateralOffset));
    }

    private static Optional<Float> resolveExitYaw(String destinationAxis, Optional<RelativePortalExit> relativePortalExit) {
        return relativePortalExit
            .flatMap(relativeExit -> normalizeApproachFacing(relativeExit.sourceAxis(), relativeExit.approachFacing())
                .map(approachFacing -> remapApproachFacing(relativeExit.sourceAxis(), destinationAxis, approachFacing)))
            .map(Direction::getOpposite)
            .map(Direction::toYRot);
    }

    private static Optional<Direction> normalizeApproachFacing(String sourceAxis, String approachFacing) {
        Direction direction = Direction.byName(approachFacing);
        if (direction == null || direction.getAxis().isVertical()) {
            return Optional.empty();
        }

        if (PortalWorldIndex.PortalPlacement.AXIS_X.equals(sourceAxis) && direction.getAxis() == Direction.Axis.Z) {
            return Optional.of(direction);
        }

        if (PortalWorldIndex.PortalPlacement.AXIS_Z.equals(sourceAxis) && direction.getAxis() == Direction.Axis.X) {
            return Optional.of(direction);
        }

        return Optional.empty();
    }

    private static Direction remapApproachFacing(String sourceAxis, String destinationAxis, Direction approachFacing) {
        if (sourceAxis.equals(destinationAxis)) {
            return approachFacing;
        }

        int sign = PortalWorldIndex.PortalPlacement.AXIS_X.equals(sourceAxis)
            ? Integer.signum(approachFacing.getStepZ())
            : Integer.signum(approachFacing.getStepX());

        if (PortalWorldIndex.PortalPlacement.AXIS_X.equals(destinationAxis)) {
            return sign < 0 ? Direction.NORTH : Direction.SOUTH;
        }

        return sign < 0 ? Direction.WEST : Direction.EAST;
    }

    private record RelativePortalExit(String sourceAxis, double lateralOffset, String approachFacing) {
        private RelativePortalExit {
            sourceAxis = Objects.requireNonNull(sourceAxis, "sourceAxis");
            approachFacing = Objects.requireNonNull(approachFacing, "approachFacing");
        }
    }

    private record ResolvedPortalDestination(CavernPlacementTarget placementTarget, float yaw) {
        private ResolvedPortalDestination {
            placementTarget = Objects.requireNonNull(placementTarget, "placementTarget");
        }
    }
}
