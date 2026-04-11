package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class PortalContactCanonicalizerTest {
    @Test
    void canonicalizeUsesFrameBottomLeftForDifferentInteriorBlocksOfXAxisPortal() {
        FrameAccess access = new FrameAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.add(new BlockPos(1, 1, 0));
        access.portals.add(new BlockPos(2, 1, 0));

        PortalContactCanonicalizer.CanonicalPortalContact leftContact = PortalContactCanonicalizer.canonicalize(
            access,
            new BlockPos(1, 1, 0),
            1.25D,
            1.50D,
            0.50D,
            Direction.Axis.X
        );
        PortalContactCanonicalizer.CanonicalPortalContact rightContact = PortalContactCanonicalizer.canonicalize(
            access,
            new BlockPos(2, 1, 0),
            2.25D,
            1.50D,
            0.50D,
            Direction.Axis.X
        );

        assertEquals(new BlockPos(1, 1, 0), leftContact.bottomLeft());
        assertEquals(new BlockPos(1, 1, 0), rightContact.bottomLeft());
        assertEquals(Direction.Axis.X, leftContact.axis());
        assertEquals(Direction.Axis.X, rightContact.axis());
        assertEquals(0.25D, leftContact.hitOffsetX());
        assertEquals(1.25D, rightContact.hitOffsetX());
        assertEquals(0.50D, leftContact.hitOffsetZ());
        assertEquals(0.50D, rightContact.hitOffsetZ());
    }

    @Test
    void canonicalizeUsesFrameBottomLeftForDifferentInteriorBlocksOfZAxisPortal() {
        FrameAccess access = new FrameAccess();
        access.addZAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.add(new BlockPos(0, 1, 1));
        access.portals.add(new BlockPos(0, 1, 2));

        PortalContactCanonicalizer.CanonicalPortalContact nearContact = PortalContactCanonicalizer.canonicalize(
            access,
            new BlockPos(0, 1, 1),
            0.50D,
            1.50D,
            1.25D,
            Direction.Axis.Z
        );
        PortalContactCanonicalizer.CanonicalPortalContact farContact = PortalContactCanonicalizer.canonicalize(
            access,
            new BlockPos(0, 1, 2),
            0.50D,
            1.50D,
            2.25D,
            Direction.Axis.Z
        );

        assertEquals(new BlockPos(0, 1, 1), nearContact.bottomLeft());
        assertEquals(new BlockPos(0, 1, 1), farContact.bottomLeft());
        assertEquals(Direction.Axis.Z, nearContact.axis());
        assertEquals(Direction.Axis.Z, farContact.axis());
        assertEquals(0.50D, nearContact.hitOffsetX());
        assertEquals(0.50D, farContact.hitOffsetX());
        assertEquals(0.25D, nearContact.hitOffsetZ());
        assertEquals(1.25D, farContact.hitOffsetZ());
    }

    private static final class FrameAccess implements CavernPortalFrameDetector.FrameAccess {
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
