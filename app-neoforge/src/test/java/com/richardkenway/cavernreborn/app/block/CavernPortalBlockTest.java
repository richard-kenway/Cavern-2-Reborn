package com.richardkenway.cavernreborn.app.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionOutcome;
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
    void collisionContractDispatchesPlayerWhenPlayerEntityIsAllowed() {
        CavernPortalBlock.CollisionContract contract = CavernPortalBlock.CollisionContract.from(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER,
            true
        );
        AtomicInteger playerDispatches = new AtomicInteger();
        AtomicInteger nonPlayerDispatches = new AtomicInteger();

        CavernPortalInteractionOutcome outcome = contract.dispatch(
            null,
            null,
            null,
            (level, pos, entity) -> {
                playerDispatches.incrementAndGet();
                return CavernPortalInteractionOutcome.handled(Optional.empty());
            },
            (level, pos, entity) -> {
                nonPlayerDispatches.incrementAndGet();
                return CavernPortalInteractionOutcome.handled(Optional.empty());
            }
        );

        assertTrue(contract.isPlayerDispatch());
        assertFalse(contract.isNonPlayerDispatch());
        assertFalse(contract.isIgnoreDispatch());
        assertEquals(1, playerDispatches.get());
        assertEquals(0, nonPlayerDispatches.get());
        assertTrue(contract.shouldApplyPortalCooldown(outcome));
    }

    @Test
    void collisionContractDispatchesNonPlayerWhenNonPlayerEntityIsAllowed() {
        CavernPortalBlock.CollisionContract contract = CavernPortalBlock.CollisionContract.from(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER,
            false
        );
        AtomicInteger playerDispatches = new AtomicInteger();
        AtomicInteger nonPlayerDispatches = new AtomicInteger();

        CavernPortalInteractionOutcome outcome = contract.dispatch(
            null,
            null,
            null,
            (level, pos, entity) -> {
                playerDispatches.incrementAndGet();
                return CavernPortalInteractionOutcome.handled(Optional.empty());
            },
            (level, pos, entity) -> {
                nonPlayerDispatches.incrementAndGet();
                return CavernPortalInteractionOutcome.unhandled();
            }
        );

        assertFalse(contract.isPlayerDispatch());
        assertTrue(contract.isNonPlayerDispatch());
        assertFalse(contract.isIgnoreDispatch());
        assertEquals(0, playerDispatches.get());
        assertEquals(1, nonPlayerDispatches.get());
        assertFalse(contract.shouldApplyPortalCooldown(outcome));
    }

    @Test
    void collisionContractSuppressesDispatchForIgnoreEligibilityStates() {
        List<PortalCollisionEligibilityPolicy.PortalCollisionEligibility> ignoreStates = List.of(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_DEAD,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_SPECTATOR,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_CROUCHING,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PASSENGER,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_VEHICLE,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_BOSS,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PROJECTILE,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PORTAL_INELIGIBLE,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PORTAL_COOLDOWN
        );

        for (PortalCollisionEligibilityPolicy.PortalCollisionEligibility eligibility : ignoreStates) {
            CavernPortalBlock.CollisionContract contract = CavernPortalBlock.CollisionContract.from(eligibility, true);
            AtomicInteger playerDispatches = new AtomicInteger();
            AtomicInteger nonPlayerDispatches = new AtomicInteger();

            CavernPortalInteractionOutcome outcome = contract.dispatch(
                null,
                null,
                null,
                (level, pos, entity) -> {
                    playerDispatches.incrementAndGet();
                    return CavernPortalInteractionOutcome.handled(Optional.empty());
                },
                (level, pos, entity) -> {
                    nonPlayerDispatches.incrementAndGet();
                    return CavernPortalInteractionOutcome.handled(Optional.empty());
                }
            );

            assertFalse(contract.isPlayerDispatch(), "contract should not use player dispatch for %s".formatted(eligibility.name()));
            assertFalse(contract.isNonPlayerDispatch(), "contract should not use non-player dispatch for %s".formatted(eligibility.name()));
            assertTrue(contract.isIgnoreDispatch(), "contract should ignore for %s".formatted(eligibility.name()));
            assertEquals(0, playerDispatches.get(), "player dispatch calls for %s".formatted(eligibility.name()));
            assertEquals(0, nonPlayerDispatches.get(), "non-player dispatch calls for %s".formatted(eligibility.name()));
            assertFalse(outcome.handled());
        }

        for (PortalCollisionEligibilityPolicy.PortalCollisionEligibility eligibility : ignoreStates) {
            CavernPortalBlock.CollisionContract contract = CavernPortalBlock.CollisionContract.from(eligibility, false);
            AtomicInteger playerDispatches = new AtomicInteger();
            AtomicInteger nonPlayerDispatches = new AtomicInteger();

            CavernPortalInteractionOutcome outcome = contract.dispatch(
                null,
                null,
                null,
                (level, pos, entity) -> {
                    playerDispatches.incrementAndGet();
                    return CavernPortalInteractionOutcome.handled(Optional.empty());
                },
                (level, pos, entity) -> {
                    nonPlayerDispatches.incrementAndGet();
                    return CavernPortalInteractionOutcome.handled(Optional.empty());
                }
            );

            assertFalse(contract.isPlayerDispatch(), "contract should not use player dispatch for %s".formatted(eligibility.name()));
            assertFalse(contract.isNonPlayerDispatch(), "contract should not use non-player dispatch for %s".formatted(eligibility.name()));
            assertTrue(contract.isIgnoreDispatch(), "contract should ignore for %s".formatted(eligibility.name()));
            assertEquals(0, playerDispatches.get(), "player dispatch calls for %s".formatted(eligibility.name()));
            assertEquals(0, nonPlayerDispatches.get(), "non-player dispatch calls for %s".formatted(eligibility.name()));
            assertFalse(outcome.handled());
        }
    }

    @Test
    void collisionContractAppliesCooldownOnlyWhenOutcomeHandled() {
        CavernPortalBlock.CollisionContract playerContract = CavernPortalBlock.CollisionContract.from(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER,
            true
        );
        CavernPortalBlock.CollisionContract nonPlayerContract = CavernPortalBlock.CollisionContract.from(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER,
            false
        );
        CavernPortalInteractionOutcome handledOutcome = CavernPortalInteractionOutcome.handled(Optional.empty());
        CavernPortalInteractionOutcome unhandledOutcome = CavernPortalInteractionOutcome.unhandled();

        assertTrue(playerContract.shouldApplyPortalCooldown(handledOutcome));
        assertFalse(playerContract.shouldApplyPortalCooldown(unhandledOutcome));
        assertTrue(nonPlayerContract.shouldApplyPortalCooldown(handledOutcome));
        assertFalse(nonPlayerContract.shouldApplyPortalCooldown(unhandledOutcome));
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
