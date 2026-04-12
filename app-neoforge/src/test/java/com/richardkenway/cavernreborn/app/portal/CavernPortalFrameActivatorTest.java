package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class CavernPortalFrameActivatorTest {
    @Test
    void activateFillsInteriorForEmptyXAxisFrame() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameActivator(access).activate(new BlockPos(1, 2, 0));

        assertTrue(frame.isPresent());
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
    void activateFillsInteriorForEmptyZAxisFrame() {
        SetPortalAccess access = new SetPortalAccess();
        access.addZAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameActivator(access).activate(new BlockPos(0, 2, 1));

        assertTrue(frame.isPresent());
        assertEquals(Set.of(
            new BlockPos(0, 1, 1),
            new BlockPos(0, 1, 2),
            new BlockPos(0, 2, 1),
            new BlockPos(0, 2, 2),
            new BlockPos(0, 3, 1),
            new BlockPos(0, 3, 2)
        ), access.portals.keySet());
        assertTrue(access.portals.values().stream().allMatch(axis -> axis == Direction.Axis.Z));
    }

    @Test
    void activateDoesNothingForAlreadyFilledFrame() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.put(new BlockPos(1, 1, 0), Direction.Axis.X);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameActivator(access).activate(new BlockPos(1, 2, 0));

        assertTrue(frame.isEmpty());
        assertEquals(Map.of(new BlockPos(1, 1, 0), Direction.Axis.X), access.portals);
    }

    @Test
    void activateDoesNothingWhenInteriorContainsBlockedCell() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.blockedInterior.add(new BlockPos(1, 2, 0));

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameActivator(access).activate(new BlockPos(1, 2, 0));

        assertTrue(frame.isEmpty());
        assertTrue(access.portals.isEmpty());
    }

    private static final class SetPortalAccess implements CavernPortalFrameActivator.PortalAccess {
        private final Set<BlockPos> frames = new HashSet<>();
        private final java.util.Map<BlockPos, Direction.Axis> portals = new java.util.HashMap<>();
        private final Set<BlockPos> blockedInterior = new HashSet<>();

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

        private void addZAxisFrame(BlockPos frameMin, int innerWidth, int innerHeight) {
            int x = frameMin.getX();
            int nearZ = frameMin.getZ();
            int farZ = nearZ + innerWidth + 1;
            int minY = frameMin.getY();
            int maxY = minY + innerHeight + 1;

            for (int z = nearZ; z <= farZ; z++) {
                frames.add(new BlockPos(x, minY, z));
                frames.add(new BlockPos(x, maxY, z));
            }
            for (int y = minY + 1; y < maxY; y++) {
                frames.add(new BlockPos(x, y, nearZ));
                frames.add(new BlockPos(x, y, farZ));
            }
        }

        @Override
        public boolean isFrame(BlockPos pos) {
            return frames.contains(pos);
        }

        @Override
        public boolean isInterior(BlockPos pos) {
            return !frames.contains(pos) && !blockedInterior.contains(pos);
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
