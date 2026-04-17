package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class CavernPortalFrameActivationServiceTest {
    @Test
    void activatesXAxisFrameThroughResolverAndActivatorPipeline() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = CavernPortalFrameActivationService.activate(
            access,
            new BlockPos(3, 1, 0),
            Direction.WEST
        );

        assertTrue(frame.isPresent());
        assertEquals(new BlockPos(1, 1, 0), frame.get().bottomLeft());
        assertEquals(Set.of(
            new BlockPos(1, 1, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(1, 2, 0),
            new BlockPos(2, 2, 0),
            new BlockPos(1, 3, 0),
            new BlockPos(2, 3, 0)
        ), access.portals.keySet());
        assertTrue(access.portals.values().stream().allMatch(axis -> axis == Direction.Axis.X));
    }

    @Test
    void doesNothingWithoutValidFrame() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = CavernPortalFrameActivationService.activate(
            access,
            new BlockPos(1, 1, 0),
            Direction.WEST
        );

        assertTrue(frame.isEmpty());
    }

    @Test
    void doesNothingForAlreadyFilledFrame() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.put(new BlockPos(1, 1, 0), Direction.Axis.X);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = CavernPortalFrameActivationService.activate(
            access,
            new BlockPos(3, 1, 0),
            Direction.WEST
        );

        assertFalse(frame.isPresent());
        assertEquals(Map.of(new BlockPos(1, 1, 0), Direction.Axis.X), access.portals);
    }

    private static final class SetPortalAccess implements CavernPortalFrameActivator.PortalAccess {
        private final Set<BlockPos> frames = new HashSet<>();
        private final Map<BlockPos, Direction.Axis> portals = new HashMap<>();

        private void addXAxisFrame(BlockPos frameMin, int innerWidth, int innerHeight) {
            int leftX = frameMin.getX();
            int rightX = leftX + innerWidth + 1;
            int minY = frameMin.getY();
            int maxY = minY + innerHeight + 1;
            int z = frameMin.getZ();

            for (int x = leftX; x <= rightX; x++) {
                frames.add(new BlockPos(x, minY, z));
                frames.add(new BlockPos(x, maxY, z));
            }
            for (int y = minY + 1; y < maxY; y++) {
                frames.add(new BlockPos(leftX, y, z));
                frames.add(new BlockPos(rightX, y, z));
            }
        }

        @Override
        public boolean isFrame(BlockPos pos) {
            return frames.contains(pos);
        }

        @Override
        public boolean isInterior(BlockPos pos) {
            return !frames.contains(pos);
        }

        @Override
        public boolean isPortal(BlockPos pos) {
            return portals.containsKey(pos);
        }

        @Override
        public void setPortal(BlockPos pos, Direction.Axis axis) {
            portals.put(pos, axis);
        }
    }
}
