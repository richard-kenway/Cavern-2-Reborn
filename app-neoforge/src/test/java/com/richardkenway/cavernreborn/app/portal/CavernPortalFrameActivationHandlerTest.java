package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class CavernPortalFrameActivationHandlerTest {
    private static final BlockPos CLICKED_POS = new BlockPos(3, 1, 0);
    private static final CavernPortalFrameDetector.PortalFrame FRAME = new CavernPortalFrameDetector.PortalFrame(
        new BlockPos(1, 1, 0),
        2,
        3,
        Direction.Axis.X,
        0
    );

    @Test
    void activatesOnServerWithEmeraldAndConsumesItem() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicInteger remaining = new AtomicInteger(3);

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            false,
            false,
            true,
            CLICKED_POS,
            Direction.WEST,
            clickedPos -> clickedPos.equals(CLICKED_POS),
            (clickedPos, clickedFace) -> Optional.of(FRAME),
            remaining::decrementAndGet
        );

        assertTrue(outcome.isPresent());
        assertSame(FRAME, outcome.get().frame());
        assertEquals(Direction.WEST, outcome.get().clickedFace());
        assertEquals(2, remaining.get());
    }

    @Test
    void doesNotConsumeEmeraldForCreativePlayer() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicInteger remaining = new AtomicInteger(3);

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            false,
            true,
            true,
            CLICKED_POS,
            Direction.WEST,
            clickedPos -> true,
            (clickedPos, clickedFace) -> Optional.of(FRAME),
            remaining::decrementAndGet
        );

        assertTrue(outcome.isPresent());
        assertEquals(3, remaining.get());
    }

    @Test
    void ignoresClientSideActivation() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicInteger remaining = new AtomicInteger(1);

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            true,
            false,
            true,
            CLICKED_POS,
            Direction.WEST,
            clickedPos -> true,
            (clickedPos, clickedFace) -> Optional.of(FRAME),
            remaining::decrementAndGet
        );

        assertTrue(outcome.isEmpty());
        assertEquals(1, remaining.get());
    }

    @Test
    void ignoresNonEmeraldItem() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicInteger remaining = new AtomicInteger(1);

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            false,
            false,
            false,
            CLICKED_POS,
            Direction.WEST,
            clickedPos -> true,
            (clickedPos, clickedFace) -> Optional.of(FRAME),
            remaining::decrementAndGet
        );

        assertTrue(outcome.isEmpty());
        assertEquals(1, remaining.get());
    }

    @Test
    void defaultsNullFaceToUpBeforeActivationAttempt() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicReference<Direction> recordedFace = new AtomicReference<>();

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            false,
            false,
            true,
            CLICKED_POS,
            null,
            clickedPos -> true,
            (clickedPos, clickedFace) -> {
                recordedFace.set(clickedFace);
                return Optional.of(FRAME);
            },
            () -> {}
        );

        assertTrue(outcome.isPresent());
        assertEquals(Direction.UP, recordedFace.get());
        assertEquals(Direction.UP, outcome.get().clickedFace());
    }

    @Test
    void doesNotConsumeItemWhenActivationFails() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicInteger remaining = new AtomicInteger(2);

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            false,
            false,
            true,
            CLICKED_POS,
            Direction.WEST,
            clickedPos -> true,
            (clickedPos, clickedFace) -> Optional.empty(),
            remaining::decrementAndGet
        );

        assertFalse(outcome.isPresent());
        assertEquals(2, remaining.get());
    }

    @Test
    void supportsTaggedActivatorAndFrameGate() {
        CavernPortalFrameActivationHandler handler = new CavernPortalFrameActivationHandler();
        AtomicInteger remaining = new AtomicInteger(5);

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> outcome = handler.handle(
            false,
            false,
            true,
            CLICKED_POS,
            Direction.WEST,
            clickedPos -> clickedPos.equals(CLICKED_POS),
            (clickedPos, clickedFace) -> Optional.of(FRAME),
            remaining::decrementAndGet
        );

        assertTrue(outcome.isPresent());
        assertEquals(4, remaining.get());

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> blockedByFrameMaterial = handler.handle(
            false,
            false,
            true,
            new BlockPos(99, 99, 99),
            Direction.WEST,
            clickedPos -> false,
            (clickedPos, clickedFace) -> Optional.of(FRAME),
            remaining::decrementAndGet
        );

        assertFalse(blockedByFrameMaterial.isPresent());
        assertEquals(4, remaining.get());
    }
}
