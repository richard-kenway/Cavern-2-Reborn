package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class CavernPortalFrameActivationService {
    private CavernPortalFrameActivationService() {
    }

    public static Optional<CavernPortalFrameDetector.PortalFrame> activate(
        CavernPortalFrameActivator.PortalAccess frameAccess,
        BlockPos clickedPos,
        Direction clickedFace
    ) {
        Objects.requireNonNull(frameAccess, "frameAccess");
        Objects.requireNonNull(clickedPos, "clickedPos");
        Objects.requireNonNull(clickedFace, "clickedFace");

        CavernPortalTriggerActivationResolver activationResolver = new CavernPortalTriggerActivationResolver(frameAccess);
        CavernPortalFrameActivator activator = new CavernPortalFrameActivator(frameAccess);

        return activationResolver.resolve(clickedPos, clickedFace)
            .flatMap(activation -> activator.activate(activation.normalizedActivationOrigin()));
    }
}
