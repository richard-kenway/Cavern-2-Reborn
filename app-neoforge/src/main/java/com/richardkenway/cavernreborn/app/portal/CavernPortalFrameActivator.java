package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class CavernPortalFrameActivator {
    private final PortalAccess portalAccess;
    private final CavernPortalFrameDetector frameDetector;

    public CavernPortalFrameActivator(PortalAccess portalAccess) {
        this.portalAccess = Objects.requireNonNull(portalAccess, "portalAccess");
        this.frameDetector = new CavernPortalFrameDetector(portalAccess);
    }

    public Optional<CavernPortalFrameDetector.PortalFrame> activate(BlockPos origin) {
        Objects.requireNonNull(origin, "origin");

        Optional<CavernPortalFrameDetector.PortalFrame> detectedFrame = frameDetector.detect(origin);
        if (detectedFrame.isEmpty()) {
            return Optional.empty();
        }

        CavernPortalFrameDetector.PortalFrame frame = detectedFrame.get();
        if (!frame.isEmpty()) {
            return Optional.empty();
        }

        fillInterior(frame);
        return Optional.of(frame);
    }

    private void fillInterior(CavernPortalFrameDetector.PortalFrame frame) {
        Direction right = frame.axis() == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

        for (int dx = 0; dx < frame.width(); dx++) {
            for (int dy = 0; dy < frame.height(); dy++) {
                BlockPos fillPos = frame.bottomLeft().relative(right, dx).above(dy);
                portalAccess.setPortal(fillPos);
            }
        }
    }

    public interface PortalAccess extends CavernPortalFrameDetector.FrameAccess {
        void setPortal(BlockPos pos);
    }
}
