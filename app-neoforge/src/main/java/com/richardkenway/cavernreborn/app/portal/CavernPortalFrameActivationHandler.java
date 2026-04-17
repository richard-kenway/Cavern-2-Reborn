package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class CavernPortalFrameActivationHandler {
    public Optional<ActivationOutcome> handle(
        boolean clientSide,
        boolean creativeMode,
        boolean hasValidTriggerItem,
        BlockPos clickedPos,
        @Nullable Direction clickedFace,
        FrameMaterialGate frameMaterialGate,
        ActivationAttempt activationAttempt,
        ItemConsumption itemConsumption
    ) {
        Objects.requireNonNull(clickedPos, "clickedPos");
        Objects.requireNonNull(frameMaterialGate, "frameMaterialGate");
        Objects.requireNonNull(activationAttempt, "activationAttempt");
        Objects.requireNonNull(itemConsumption, "itemConsumption");

        if (clientSide || !hasValidTriggerItem || !frameMaterialGate.allows(clickedPos)) {
            return Optional.empty();
        }

        Direction resolvedFace = clickedFace == null ? Direction.UP : clickedFace;
        Optional<CavernPortalFrameDetector.PortalFrame> activatedFrame = activationAttempt.activate(clickedPos, resolvedFace);
        if (activatedFrame.isEmpty()) {
            return Optional.empty();
        }

        if (!creativeMode) {
            itemConsumption.consumeOne();
        }

        return Optional.of(new ActivationOutcome(activatedFrame.get(), resolvedFace));
    }

    @FunctionalInterface
    public interface FrameMaterialGate {
        boolean allows(BlockPos clickedPos);
    }

    @FunctionalInterface
    public interface ActivationAttempt {
        Optional<CavernPortalFrameDetector.PortalFrame> activate(BlockPos clickedPos, Direction clickedFace);
    }

    @FunctionalInterface
    public interface ItemConsumption {
        void consumeOne();
    }

    public record ActivationOutcome(
        CavernPortalFrameDetector.PortalFrame frame,
        Direction clickedFace
    ) {
        public ActivationOutcome {
            Objects.requireNonNull(frame, "frame");
            Objects.requireNonNull(clickedFace, "clickedFace");
        }
    }
}
