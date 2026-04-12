package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;

import net.minecraft.core.Direction;

final class CollisionApproachFacingResolver {
    private static final double MIN_MOTION = 1.0E-4D;

    private CollisionApproachFacingResolver() {
    }

    static String resolve(Direction.Axis portalAxis, double deltaX, double deltaZ, Direction lookDirection) {
        Objects.requireNonNull(portalAxis, "portalAxis");
        Objects.requireNonNull(lookDirection, "lookDirection");

        Direction motionApproach = motionApproach(portalAxis, deltaX, deltaZ);
        if (motionApproach != null) {
            return motionApproach.getName();
        }

        Direction fallbackApproach = fallbackApproach(portalAxis, lookDirection);
        return fallbackApproach.getName();
    }

    private static Direction motionApproach(Direction.Axis portalAxis, double deltaX, double deltaZ) {
        if (portalAxis == Direction.Axis.X && Math.abs(deltaZ) > MIN_MOTION) {
            return deltaZ > 0.0D ? Direction.NORTH : Direction.SOUTH;
        }

        if (portalAxis == Direction.Axis.Z && Math.abs(deltaX) > MIN_MOTION) {
            return deltaX > 0.0D ? Direction.WEST : Direction.EAST;
        }

        return null;
    }

    private static Direction fallbackApproach(Direction.Axis portalAxis, Direction lookDirection) {
        Direction oppositeLook = lookDirection.getOpposite();
        if (portalAxis == Direction.Axis.X && oppositeLook.getAxis() == Direction.Axis.Z) {
            return oppositeLook;
        }

        if (portalAxis == Direction.Axis.Z && oppositeLook.getAxis() == Direction.Axis.X) {
            return oppositeLook;
        }

        return portalAxis == Direction.Axis.X ? Direction.NORTH : Direction.WEST;
    }
}
