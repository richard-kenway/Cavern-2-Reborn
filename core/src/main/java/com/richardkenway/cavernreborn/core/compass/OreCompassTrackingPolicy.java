package com.richardkenway.cavernreborn.core.compass;

import java.util.Objects;

public final class OreCompassTrackingPolicy {
    public static final int TRACKING_RADIUS = 50;
    public static final int ANGLE_FRAME_COUNT = 32;

    private OreCompassTrackingPolicy() {
    }

    public static OreCompassTrackingResult evaluate(
        boolean hasStoredTarget,
        boolean entityContext,
        String holderDimensionId,
        String storedDimensionId,
        int dx,
        int dy,
        int dz,
        boolean blockMatches
    ) {
        if (!hasStoredTarget) {
            return emptyResult(OreCompassTrackingDecision.NO_STORED_TARGET);
        }
        if (!entityContext) {
            return emptyResult(OreCompassTrackingDecision.NO_ENTITY_CONTEXT);
        }
        if (!Objects.equals(
            Objects.requireNonNull(holderDimensionId, "holderDimensionId"),
            Objects.requireNonNull(storedDimensionId, "storedDimensionId")
        )) {
            return emptyResult(OreCompassTrackingDecision.WRONG_DIMENSION);
        }

        int horizontalDistance = OreCompassScanPolicy.horizontalDistance(dx, dz);
        OreCompassDirection direction = OreCompassScanPolicy.directionForOffset(dx, dz);
        if (horizontalDistance > TRACKING_RADIUS) {
            return new OreCompassTrackingResult(OreCompassTrackingDecision.OUT_OF_RANGE, -1, direction, horizontalDistance, dy);
        }
        if (!blockMatches) {
            return new OreCompassTrackingResult(OreCompassTrackingDecision.BLOCK_MISMATCH, -1, direction, horizontalDistance, dy);
        }

        return new OreCompassTrackingResult(
            OreCompassTrackingDecision.TRACKING,
            angleFrameForOffset(dx, dz),
            direction,
            horizontalDistance,
            dy
        );
    }

    public static int angleFrameForOffset(int dx, int dz) {
        if (dx == 0 && dz == 0) {
            return 0;
        }

        double turns = Math.atan2(dx, -dz) / (Math.PI * 2.0D);
        int frame = Math.floorMod((int)Math.round(turns * ANGLE_FRAME_COUNT), ANGLE_FRAME_COUNT);
        return frame;
    }

    private static OreCompassTrackingResult emptyResult(OreCompassTrackingDecision decision) {
        return new OreCompassTrackingResult(decision, -1, OreCompassDirection.SAME_POSITION, 0, 0);
    }
}
