package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

import net.minecraft.nbt.CompoundTag;

class CavernNonPlayerPortalInteractionServiceTest {
    private static String expectedPortalKey(String returnDimensionId, int x, int y, int z) {
        return "cavern_portal" + "|" + returnDimensionId + "|" + x + "|" + y + "|" + z;
    }

    @Test
    void useOutsideCavernSendsNonPlayerIntoCavernAndStoresReturnReceipt() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernNonPlayerPortalInteractionService service = bootstrap.nonPlayerPortalInteractionService();
        FakeNonPlayerPortalInteractionContext context = new FakeNonPlayerPortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11L,
            11,
            70,
            11,
            0.25D,
            0.50D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.of(new PortalWorldIndex.PortalPlacement(0, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, context.lastTargetDimensionId);
        assertTrue(context.lastX > 0);
        assertEquals(64.0D, context.lastY);
        assertTrue(context.lastZ > 0);
        assertEquals(30.0F, context.lastPitch);
        PortalEntryReceipt stored = context.loadPortalEntryReceipt().orElseThrow();
        assertEquals(expectedPortalKey(context.currentDimensionId(), 11, 70, 11), stored.returnState().portalKey());
        assertEquals(context.currentDimensionId, stored.returnState().returnDimensionId());
        assertEquals(context.portalX, stored.returnState().returnX());
        assertEquals(context.portalY, stored.returnState().returnY());
        assertEquals(context.portalZ, stored.returnState().returnZ());
    }

    @Test
    void useOutsideCavernDoesNotStoreReturnReceiptWhenEntryFails() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernNonPlayerPortalInteractionService service = bootstrap.nonPlayerPortalInteractionService();
        String portalKey = expectedPortalKey(CavernDimensions.OVERWORLD_DIMENSION_ID, 11, 70, 11);
        FakeNonPlayerPortalInteractionContext context = new FakeNonPlayerPortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11L,
            11,
            70,
            11,
            0.25D,
            0.50D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(),
            Optional.empty()
        );

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isEmpty());
        assertFalse(context.hasPortalEntryReceipt());
        assertTrue(bootstrap.worldPortalIndexStore().load(CavernDimensions.OVERWORLD_DIMENSION_ID).firstPlacementFor(portalKey).isEmpty());
    }

    @Test
    void useInsideCavernReturnsNonPlayerAndClearsReturnReceipt() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernNonPlayerPortalInteractionService service = bootstrap.nonPlayerPortalInteractionService();
        PortalEntryReceipt sourceReceipt = new PortalEntryReceipt(
            new PortalReturnState("portal", CavernDimensions.OVERWORLD_DIMENSION_ID, 11, 70, 11),
            new TeleportContext("portal", 0.25D, 0.50D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(11, 70, 11, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        bootstrap.worldPortalIndexStore().save(
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            PortalWorldIndex.empty().withPortal(
                "portal",
                new PortalWorldIndex.PortalPlacement(12, 80, 13, PortalWorldIndex.PortalPlacement.AXIS_X)
            )
        );

        FakeNonPlayerPortalInteractionContext context = new FakeNonPlayerPortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            11L,
            0,
            80,
            0,
            0.25D,
            0.50D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(12, 80, 13)),
            Optional.empty()
        );
        context.savePortalEntryReceipt(sourceReceipt);

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isPresent());
        CavernTravelPlan travelPlan = outcome.travelPlan().orElseThrow();
        assertTrue(travelPlan.isReturnHome());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, context.lastTargetDimensionId);
        assertTrue(context.lastX > 11);
        assertEquals(80.0D, context.lastY);
        assertTrue(context.lastZ > 12);
        assertEquals(30.0F, context.lastPitch);
        assertFalse(context.hasPortalEntryReceipt());
    }

    @Test
    void useOutsideCavernRegistersSourcePortalOnlyAfterSuccessfulEntry() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernNonPlayerPortalInteractionService service = bootstrap.nonPlayerPortalInteractionService();
        String portalKey = expectedPortalKey(CavernDimensions.OVERWORLD_DIMENSION_ID, 11, 70, 11);
        FakeNonPlayerPortalInteractionContext context = new FakeNonPlayerPortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            12L,
            11,
            70,
            11,
            0.25D,
            0.50D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.of(new PortalWorldIndex.PortalPlacement(0, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isPresent());
        assertTrue(bootstrap.worldPortalIndexStore().load(CavernDimensions.OVERWORLD_DIMENSION_ID).firstPlacementFor(portalKey).isPresent());
    }

    @Test
    void useInsideCavernDoesNothingWhenNoReceiptIsStored() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernNonPlayerPortalInteractionService service = bootstrap.nonPlayerPortalInteractionService();
        FakeNonPlayerPortalInteractionContext context = new FakeNonPlayerPortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            11L,
            0,
            80,
            0,
            0.25D,
            0.50D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(12, 80, 13)),
            Optional.empty()
        );

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertFalse(outcome.handled());
        assertTrue(outcome.travelPlan().isEmpty());
        assertEquals(0.0D, context.lastX);
    }

    private static final class FakeNonPlayerPortalInteractionContext implements NonPlayerPortalInteractionContext {
        private final UUID playerId;
        private final boolean clientSide;
        private final String currentDimensionId;
        private final long gameTime;
        private final int portalX;
        private final int portalY;
        private final int portalZ;
        private final double hitOffsetX;
        private final double hitOffsetY;
        private final double hitOffsetZ;
        private final String approachFacing;
        private final float yaw;
        private final float pitch;
        private final Set<SafeArrival> safeArrivals;
        private final Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement;
        private final Set<PortalLocation> existingPortals = new HashSet<>();
        private final CompoundTag entityTag = new CompoundTag();
        private String lastTargetDimensionId;
        private double lastX;
        private double lastY;
        private double lastZ;
        private float lastYaw;
        private float lastPitch;

        private FakeNonPlayerPortalInteractionContext(
            UUID playerId,
            boolean clientSide,
            String currentDimensionId,
            long gameTime,
            int portalX,
            int portalY,
            int portalZ,
            double hitOffsetX,
            double hitOffsetY,
            double hitOffsetZ,
            String approachFacing,
            float yaw,
            float pitch,
            Set<SafeArrival> safeArrivals,
            Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement
        ) {
            this.playerId = playerId;
            this.clientSide = clientSide;
            this.currentDimensionId = currentDimensionId;
            this.gameTime = gameTime;
            this.portalX = portalX;
            this.portalY = portalY;
            this.portalZ = portalZ;
            this.hitOffsetX = hitOffsetX;
            this.hitOffsetY = hitOffsetY;
            this.hitOffsetZ = hitOffsetZ;
            this.approachFacing = approachFacing;
            this.yaw = yaw;
            this.pitch = pitch;
            this.safeArrivals = safeArrivals;
            this.createdPortalPlacement = createdPortalPlacement;
        }

        @Override
        public boolean isClientSide() {
            return clientSide;
        }

        @Override
        public long gameTime() {
            return gameTime;
        }

        @Override
        public void showPortalFeedback(String translationKey) {
        }

        @Override
        public String currentDimensionId() {
            return currentDimensionId;
        }

        @Override
        public int portalX() {
            return portalX;
        }

        @Override
        public int portalY() {
            return portalY;
        }

        @Override
        public int portalZ() {
            return portalZ;
        }

        @Override
        public String portalAxis() {
            return PortalWorldIndex.PortalPlacement.AXIS_X;
        }

        @Override
        public double hitOffsetX() {
            return hitOffsetX;
        }

        @Override
        public double hitOffsetY() {
            return hitOffsetY;
        }

        @Override
        public double hitOffsetZ() {
            return hitOffsetZ;
        }

        @Override
        public String approachFacing() {
            return approachFacing;
        }

        @Override
        public UUID playerId() {
            return playerId;
        }

        @Override
        public float yaw() {
            return yaw;
        }

        @Override
        public float pitch() {
            return pitch;
        }

        @Override
        public boolean hasPortalAt(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .anyMatch(portal -> portal.dimensionId().equals(targetDimensionId)
                    && portal.x() == x && portal.y() == y && portal.z() == z);
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> resolvePortalAt(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .filter(portal -> portal.dimensionId().equals(targetDimensionId))
                .filter(portal -> portal.x() == x)
                .filter(portal -> portal.y() == y)
                .filter(portal -> portal.z() == z)
                .findFirst()
                .map(portal -> new PortalWorldIndex.PortalPlacement(portal.x(), portal.y(), portal.z(), portal.axis()));
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> findPortalNear(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .filter(portal -> portal.dimensionId().equals(targetDimensionId))
                .filter(portal -> Math.abs(portal.x() - x) <= 8)
                .filter(portal -> Math.abs(portal.y() - y) <= 6)
                .filter(portal -> Math.abs(portal.z() - z) <= 8)
                .findFirst()
                .map(portal -> new PortalWorldIndex.PortalPlacement(portal.x(), portal.y(), portal.z(), portal.axis()));
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> createPortalAt(String targetDimensionId, int x, int y, int z) {
            if (createdPortalPlacement.isEmpty()) {
                return Optional.empty();
            }

            PortalWorldIndex.PortalPlacement placement = createdPortalPlacement.get();
            existingPortals.add(new PortalLocation(targetDimensionId, placement.x(), placement.y(), placement.z(), placement.axis()));
            return Optional.of(placement);
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> createReplacementPortalAt(
            String targetDimensionId,
            PortalWorldIndex.PortalPlacement stalePlacement
        ) {
            return Optional.empty();
        }

        @Override
        public void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch) {
            this.lastTargetDimensionId = targetDimensionId;
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
            this.lastYaw = yaw;
            this.lastPitch = pitch;
        }

        @Override
        public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
            return CavernDimensions.CAVERN_DIMENSION_ID.equals(targetDimensionId)
                && safeArrivals.contains(new SafeArrival(x, y, z));
        }

        @Override
        public Optional<PortalEntryReceipt> loadPortalEntryReceipt() {
            return PortalEntryReceiptStateMapper.load(entityTag);
        }

        @Override
        public void savePortalEntryReceipt(PortalEntryReceipt entryReceipt) {
            PortalEntryReceiptStateMapper.save(entityTag, entryReceipt);
        }

        @Override
        public void clearPortalEntryReceipt() {
            PortalEntryReceiptStateMapper.clear(entityTag);
        }

        private boolean hasPortalEntryReceipt() {
            return PortalEntryReceiptStateMapper.load(entityTag).isPresent();
        }

    }

    private record SafeArrival(int x, int y, int z) {
    }

    private record PortalLocation(String dimensionId, int x, int y, int z, String axis) {
    }
}
