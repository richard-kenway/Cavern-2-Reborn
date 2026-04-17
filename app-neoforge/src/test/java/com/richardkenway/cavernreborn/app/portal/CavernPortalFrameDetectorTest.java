package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class CavernPortalFrameDetectorTest {
    @Test
    void detectFindsValidXAxisFrame() {
        SetFrameAccess access = new SetFrameAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameDetector(access).detect(new BlockPos(1, 2, 0));

        assertTrue(frame.isPresent());
        assertEquals(new BlockPos(1, 1, 0), frame.get().bottomLeft());
        assertEquals(2, frame.get().width());
        assertEquals(3, frame.get().height());
        assertEquals(Direction.Axis.X, frame.get().axis());
        assertEquals(0, frame.get().portalBlockCount());
        assertTrue(frame.get().isEmpty());
    }

    @Test
    void detectFindsValidZAxisFrameAndCountsExistingPortalBlocks() {
        SetFrameAccess access = new SetFrameAccess();
        access.addZAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.add(new BlockPos(0, 1, 1));
        access.portals.add(new BlockPos(0, 2, 1));

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameDetector(access).detect(new BlockPos(0, 2, 1));

        assertTrue(frame.isPresent());
        assertEquals(Direction.Axis.Z, frame.get().axis());
        assertEquals(2, frame.get().portalBlockCount());
        assertFalse(frame.get().isEmpty());
    }

    @Test
    void detectRejectsFrameWhenTopBorderIsMissing() {
        SetFrameAccess access = new SetFrameAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.frames.remove(new BlockPos(2, 4, 0));

        Optional<CavernPortalFrameDetector.PortalFrame> frame = new CavernPortalFrameDetector(access).detect(new BlockPos(1, 1, 0));

        assertTrue(frame.isEmpty());
    }

    @Test
    void frameMaterialPolicyRejectsNullFrameMaterial() {
        assertThrows(NullPointerException.class, () -> PortalFrameMaterialPolicy.of(null));
    }

    private static final class SetFrameAccess implements CavernPortalFrameDetector.FrameAccess {
        private final Set<BlockPos> frames = new HashSet<>();
        private final Set<BlockPos> portals = new HashSet<>();

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
            return !frames.contains(pos);
        }

        @Override
        public boolean isPortal(BlockPos pos) {
            return portals.contains(pos);
        }
    }
}
