package com.richardkenway.cavernreborn.core.compass;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

public final class OreCompassScanPolicy {
    public static final int HORIZONTAL_RADIUS = 32;
    public static final int VERTICAL_RADIUS = 24;
    public static final int COOLDOWN_TICKS = 20 * 20;

    private OreCompassScanPolicy() {
    }

    public static OreCompassScanResult evaluate(
        String dimensionId,
        boolean playerContext,
        String targetBlockId,
        int dx,
        int dy,
        int dz
    ) {
        if (!playerContext) {
            return emptyResult(OreCompassScanDecision.NO_PLAYER_CONTEXT);
        }
        if (!CavernDimensions.isCavern(Objects.requireNonNull(dimensionId, "dimensionId"))) {
            return emptyResult(OreCompassScanDecision.WRONG_DIMENSION);
        }
        if (targetBlockId == null || targetBlockId.isBlank()) {
            return emptyResult(OreCompassScanDecision.NO_TARGET);
        }

        return new OreCompassScanResult(
            OreCompassScanDecision.FOUND,
            targetBlockId,
            horizontalDistance(dx, dz),
            dy,
            directionForOffset(dx, dz)
        );
    }

    public static OreCompassScanResult cooldown() {
        return emptyResult(OreCompassScanDecision.ON_COOLDOWN);
    }

    public static int horizontalDistance(int dx, int dz) {
        long squared = (long) dx * dx + (long) dz * dz;
        return (int) Math.round(Math.sqrt(squared));
    }

    public static OreCompassDirection directionForOffset(int dx, int dz) {
        if (dx == 0 && dz == 0) {
            return OreCompassDirection.SAME_POSITION;
        }

        int horizontal = Integer.compare(dx, 0);
        int vertical = Integer.compare(dz, 0);

        if (vertical < 0) {
            if (horizontal < 0) {
                return OreCompassDirection.NORTH_WEST;
            }
            if (horizontal > 0) {
                return OreCompassDirection.NORTH_EAST;
            }
            return OreCompassDirection.NORTH;
        }
        if (vertical > 0) {
            if (horizontal < 0) {
                return OreCompassDirection.SOUTH_WEST;
            }
            if (horizontal > 0) {
                return OreCompassDirection.SOUTH_EAST;
            }
            return OreCompassDirection.SOUTH;
        }
        return horizontal < 0 ? OreCompassDirection.WEST : OreCompassDirection.EAST;
    }

    private static OreCompassScanResult emptyResult(OreCompassScanDecision decision) {
        return new OreCompassScanResult(decision, null, 0, 0, OreCompassDirection.SAME_POSITION);
    }
}
