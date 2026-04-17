package com.richardkenway.cavernreborn.app.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameDetector;
import com.richardkenway.cavernreborn.app.portal.PortalCollisionEligibilityPolicy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;

class CavernPortalBlockTest {
    @Test
    void getShapeUsesThinPortalPlaneForXAxis() {
        VoxelShape shape = CavernPortalBlock.shapeFor(Direction.Axis.X);

        assertEquals(0.0D, shape.bounds().minX);
        assertEquals(1.0D, shape.bounds().maxX);
        assertEquals(6.0D / 16.0D, shape.bounds().minZ);
        assertEquals(10.0D / 16.0D, shape.bounds().maxZ);
    }

    @Test
    void getShapeUsesThinPortalPlaneForZAxis() {
        VoxelShape shape = CavernPortalBlock.shapeFor(Direction.Axis.Z);

        assertEquals(6.0D / 16.0D, shape.bounds().minX);
        assertEquals(10.0D / 16.0D, shape.bounds().maxX);
        assertEquals(0.0D, shape.bounds().minZ);
        assertEquals(1.0D, shape.bounds().maxZ);
    }

    @Test
    void hasValidFrameAcceptsMatchingAxisFrame() {
        FrameAccess access = new FrameAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.add(new BlockPos(1, 1, 0));

        boolean valid = CavernPortalBlock.hasValidFrame(Direction.Axis.X, new BlockPos(1, 1, 0), access);

        assertTrue(valid);
    }

    @Test
    void hasValidFrameRejectsMismatchedAxisFrame() {
        FrameAccess access = new FrameAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.portals.add(new BlockPos(1, 1, 0));

        boolean valid = CavernPortalBlock.hasValidFrame(Direction.Axis.Z, new BlockPos(1, 1, 0), access);

        assertFalse(valid);
    }

    @Test
    void shouldApplyPortalCooldownMatchesHandledOutcome() {
        assertTrue(CavernPortalBlock.shouldApplyPortalCooldown(true));
        assertFalse(CavernPortalBlock.shouldApplyPortalCooldown(false));
    }

    @Test
    void shouldDispatchCollisionTransportRespectsPlayerAndNonPlayerEligibility() {
        assertTrue(CavernPortalBlock.shouldDispatchCollisionTransport(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER,
            true
        ));

        assertFalse(CavernPortalBlock.shouldDispatchCollisionTransport(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER,
            false
        ));

        assertFalse(CavernPortalBlock.shouldDispatchCollisionTransport(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER,
            true
        ));

        assertTrue(CavernPortalBlock.shouldDispatchCollisionTransport(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER,
            false
        ));

        assertFalse(CavernPortalBlock.shouldDispatchCollisionTransport(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_CROUCHING,
            true
        ));

        assertFalse(CavernPortalBlock.shouldDispatchCollisionTransport(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_CROUCHING,
            false
        ));
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
