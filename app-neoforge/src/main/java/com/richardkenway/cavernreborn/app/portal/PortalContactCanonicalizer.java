package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

final class PortalContactCanonicalizer {
    private PortalContactCanonicalizer() {
    }

    static CanonicalPortalContact canonicalize(
        CavernPortalFrameDetector.FrameAccess frameAccess,
        BlockPos touchedPortalBlock,
        double worldX,
        double worldY,
        double worldZ,
        Direction.Axis fallbackAxis
    ) {
        Objects.requireNonNull(frameAccess, "frameAccess");
        Objects.requireNonNull(touchedPortalBlock, "touchedPortalBlock");
        Objects.requireNonNull(fallbackAxis, "fallbackAxis");

        Optional<CavernPortalFrameDetector.PortalFrame> detectedFrame = new CavernPortalFrameDetector(frameAccess).detect(touchedPortalBlock);
        if (detectedFrame.isPresent()) {
            CavernPortalFrameDetector.PortalFrame frame = detectedFrame.get();
            BlockPos bottomLeft = frame.bottomLeft();
            return new CanonicalPortalContact(
                bottomLeft,
                frame.axis(),
                clampHorizontalOffset(worldX - bottomLeft.getX(), frame.axis() == Direction.Axis.Z ? 1.0D : frame.width()),
                Mth.clamp(worldY - bottomLeft.getY(), 0.0D, frame.height()),
                clampHorizontalOffset(worldZ - bottomLeft.getZ(), frame.axis() == Direction.Axis.X ? 1.0D : frame.width())
            );
        }

        return new CanonicalPortalContact(
            touchedPortalBlock,
            fallbackAxis,
            Mth.clamp(worldX - touchedPortalBlock.getX(), 0.0D, 1.0D),
            Mth.clamp(worldY - touchedPortalBlock.getY(), 0.0D, 1.0D),
            Mth.clamp(worldZ - touchedPortalBlock.getZ(), 0.0D, 1.0D)
        );
    }

    private static double clampHorizontalOffset(double value, double max) {
        return Mth.clamp(value, 0.0D, max);
    }

    record CanonicalPortalContact(
        BlockPos bottomLeft,
        Direction.Axis axis,
        double hitOffsetX,
        double hitOffsetY,
        double hitOffsetZ
    ) {
        CanonicalPortalContact {
            Objects.requireNonNull(bottomLeft, "bottomLeft");
            Objects.requireNonNull(axis, "axis");
        }
    }
}
