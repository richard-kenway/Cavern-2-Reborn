package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class CavernPortalTriggerActivationResolverTest {
    @Test
    void resolvesActivationForValidFrameClick() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        CavernPortalTriggerActivationResolver resolver = new CavernPortalTriggerActivationResolver(access);
        Optional<CavernPortalTriggerActivationResolver.PortalTriggerActivation> activation = resolver.resolve(
            new BlockPos(3, 1, 0),
            Direction.WEST
        );

        assertTrue(activation.isPresent());
        assertEquals(new BlockPos(1, 1, 0), activation.get().normalizedActivationOrigin());
        assertEquals(new BlockPos(1, 1, 0), activation.get().frame().bottomLeft());
        assertEquals(Direction.Axis.X, activation.get().frame().axis());
    }

    @Test
    void resolvesActivationForValidZAxisFrameClick() {
        SetPortalAccess access = new SetPortalAccess();
        access.addZAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        CavernPortalTriggerActivationResolver resolver = new CavernPortalTriggerActivationResolver(access);
        Optional<CavernPortalTriggerActivationResolver.PortalTriggerActivation> activation = resolver.resolve(
            new BlockPos(0, 1, 3),
            Direction.NORTH
        );

        assertTrue(activation.isPresent());
        assertEquals(new BlockPos(0, 1, 1), activation.get().normalizedActivationOrigin());
        assertEquals(new BlockPos(0, 1, 1), activation.get().frame().bottomLeft());
        assertEquals(Direction.Axis.Z, activation.get().frame().axis());
    }

    @Test
    void rejectsActivationWhenClickedBlockIsNotFrame() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        CavernPortalTriggerActivationResolver resolver = new CavernPortalTriggerActivationResolver(access);
        Optional<CavernPortalTriggerActivationResolver.PortalTriggerActivation> activation = resolver.resolve(
            new BlockPos(1, 1, 0),
            Direction.WEST
        );

        assertFalse(activation.isPresent());
    }

    @Test
    void rejectsActivationWhenClickedFaceDoesNotPointIntoFrameInterior() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        CavernPortalTriggerActivationResolver resolver = new CavernPortalTriggerActivationResolver(access);
        Optional<CavernPortalTriggerActivationResolver.PortalTriggerActivation> activation = resolver.resolve(
            new BlockPos(3, 1, 0),
            Direction.EAST
        );

        assertFalse(activation.isPresent());
    }

    @Test
    void rejectsActivationWhenClickedFacePointsAboveFrameInsteadOfIntoInterior() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);

        CavernPortalTriggerActivationResolver resolver = new CavernPortalTriggerActivationResolver(access);
        Optional<CavernPortalTriggerActivationResolver.PortalTriggerActivation> activation = resolver.resolve(
            new BlockPos(3, 1, 0),
            Direction.UP
        );

        assertFalse(activation.isPresent());
    }

    @Test
    void rejectsActivationWhenInteriorIsBlocked() {
        SetPortalAccess access = new SetPortalAccess();
        access.addXAxisFrame(new BlockPos(0, 0, 0), 2, 3);
        access.blockedInterior.add(new BlockPos(2, 1, 0));

        CavernPortalTriggerActivationResolver resolver = new CavernPortalTriggerActivationResolver(access);
        Optional<CavernPortalTriggerActivationResolver.PortalTriggerActivation> activation = resolver.resolve(
            new BlockPos(3, 1, 0),
            Direction.WEST
        );

        assertFalse(activation.isPresent());
    }

    private static final class SetPortalAccess implements CavernPortalFrameActivator.PortalAccess {
        private final Set<BlockPos> frames = new HashSet<>();
        private final Set<BlockPos> portals = new HashSet<>();
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
            return portals.contains(pos);
        }

        @Override
        public void setPortal(BlockPos pos, Direction.Axis axis) {
            portals.add(pos);
        }
    }
}
