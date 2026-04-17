package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class CavernPortalTriggerActivationResolver {
    private final CavernPortalFrameActivator.PortalAccess frameAccess;
    private final CavernPortalFrameDetector frameDetector;

    public CavernPortalTriggerActivationResolver(CavernPortalFrameActivator.PortalAccess frameAccess) {
        this.frameAccess = Objects.requireNonNull(frameAccess, "frameAccess");
        this.frameDetector = new CavernPortalFrameDetector(frameAccess);
    }

    public Optional<PortalTriggerActivation> resolve(BlockPos clickedPos, Direction clickedFace) {
        Objects.requireNonNull(clickedPos, "clickedPos");
        Objects.requireNonNull(clickedFace, "clickedFace");

        if (!frameAccess.isFrame(clickedPos)) {
            return Optional.empty();
        }

        BlockPos candidateOrigin = clickedPos.relative(clickedFace);
        Optional<CavernPortalFrameDetector.PortalFrame> frame = frameDetector.detect(candidateOrigin);

        return frame.map(detectedFrame -> new PortalTriggerActivation(
            detectedFrame,
            detectedFrame.bottomLeft()
        ));
    }

    public record PortalTriggerActivation(
        CavernPortalFrameDetector.PortalFrame frame,
        BlockPos normalizedActivationOrigin
    ) {
        public PortalTriggerActivation {
            Objects.requireNonNull(frame, "frame");
            Objects.requireNonNull(normalizedActivationOrigin, "normalizedActivationOrigin");
        }
    }
}

