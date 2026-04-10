package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

import net.minecraft.core.Direction;

class CavernTravelBridgeTest {
    @Test
    void travelToCavernCreatesDestinationPortalAndRegistersItsPlacement() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            90L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(2.75D, player.lastX);
        assertEquals(64.0D, player.lastY);
        assertEquals(0.5D, player.lastZ);
        assertEquals(Direction.SOUTH.toYRot(), player.lastYaw);
        assertEquals(1, player.createPortalCalls);
        assertTrue(bootstrap.worldPortalIndexStore().load(CavernDimensions.CAVERN_DIMENSION_ID).firstPlacementFor("cavern").isPresent());
    }

    @Test
    void travelToCavernReusesNearbyExistingDestinationPortalBeforeCreatingNewOne() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            95L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );
        player.existingPortals.add(new PortalLocation(CavernDimensions.CAVERN_DIMENSION_ID, 3, 64, 1, PortalWorldIndex.PortalPlacement.AXIS_Z));

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(3.5D, player.lastX);
        assertEquals(64.0D, player.lastY);
        assertEquals(1.75D, player.lastZ);
        assertEquals(Direction.EAST.toYRot(), player.lastYaw);
        assertEquals(0, player.createPortalCalls);
        assertEquals(
            new PortalWorldIndex.PortalPlacement(3, 64, 1, PortalWorldIndex.PortalPlacement.AXIS_Z),
            bootstrap.worldPortalIndexStore().load(CavernDimensions.CAVERN_DIMENSION_ID).firstPlacementFor("cavern").orElseThrow()
        );
    }

    @Test
    void travelToCavernRelinksStaleIndexedPlacementToNearbyPortal() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        bootstrap.worldPortalIndexStore().save(
            CavernDimensions.CAVERN_DIMENSION_ID,
            PortalWorldIndex.empty().withPortal("cavern", new PortalWorldIndex.PortalPlacement(0, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            96L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(8, 64, 8, PortalWorldIndex.PortalPlacement.AXIS_X))
        );
        player.existingPortals.add(new PortalLocation(CavernDimensions.CAVERN_DIMENSION_ID, 2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X));

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(2.75D, player.lastX);
        assertEquals(64.0D, player.lastY);
        assertEquals(0.5D, player.lastZ);
        assertEquals(Direction.SOUTH.toYRot(), player.lastYaw);
        assertEquals(0, player.createPortalCalls);
        assertEquals(
            new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X),
            bootstrap.worldPortalIndexStore().load(CavernDimensions.CAVERN_DIMENSION_ID).firstPlacementFor("cavern").orElseThrow()
        );
    }

    @Test
    void travelToCavernRegeneratesNearStaleIndexedPlacementBeforeGenericCreate() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        bootstrap.worldPortalIndexStore().save(
            CavernDimensions.CAVERN_DIMENSION_ID,
            PortalWorldIndex.empty().withPortal("cavern", new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z))
        );
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            96L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X)),
            Optional.of(new PortalWorldIndex.PortalPlacement(29, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z))
        );

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(29.5D, player.lastX);
        assertEquals(70.0D, player.lastY);
        assertEquals(30.75D, player.lastZ);
        assertEquals(Direction.EAST.toYRot(), player.lastYaw);
        assertEquals(1, player.createReplacementPortalCalls);
        assertEquals(0, player.createPortalCalls);
        assertEquals(
            new PortalWorldIndex.PortalPlacement(29, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z),
            bootstrap.worldPortalIndexStore().load(CavernDimensions.CAVERN_DIMENSION_ID).firstPlacementFor("cavern").orElseThrow()
        );
    }

    @Test
    void travelToCavernRemapsLateralOffsetAcrossPortalAxes() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            97L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.90D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8, PortalWorldIndex.PortalPlacement.AXIS_Z)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(3.40D, player.lastX);
        assertEquals(64.0D, player.lastY);
        assertEquals(0.5D, player.lastZ);
    }

    @Test
    void travelToCavernClampsPortalRelativeOffsetNearFrameEdge() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            98L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
        );

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.99D, 0.5D, 0.50D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(3.45D, player.lastX);
        assertEquals(64.0D, player.lastY);
        assertEquals(0.5D, player.lastZ);
        assertEquals(Direction.SOUTH.toYRot(), player.lastYaw);
    }

    @Test
    void travelToCavernRotatesExitFacingAcrossPortalAxes() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            99L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.empty(),
            Optional.of(new PortalWorldIndex.PortalPlacement(3, 64, 1, PortalWorldIndex.PortalPlacement.AXIS_Z))
        );

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(Direction.EAST.toYRot(), player.lastYaw);
    }

    @Test
    void travelToCavernAdjustsUnsafePlacementUsingWorldProbe() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(UUID.randomUUID(), 100L, 90.0F, 30.0F, Set.of(new SafeArrival(0, 64, 0)));

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_X, player.lastX);
        assertEquals(64.0D, player.lastY);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Z, player.lastZ);
        assertEquals(90.0F, player.lastYaw);
        assertEquals(30.0F, player.lastPitch);
    }

    @Test
    void travelToCavernCancelsWhenNoSafeArrivalExists() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        UUID playerId = UUID.randomUUID();
        FakePlayerTravelContext player = new FakePlayerTravelContext(playerId, 200L, 90.0F, 30.0F, Set.of());

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isEmpty());
        assertNull(player.lastTargetDimensionId);
        assertFalse(bootstrap.cavernTravelBridge().returnHome(player).isPresent());
    }

    @Test
    void travelToCavernSearchesWiderNeighboringColumnsWhenTargetColumnIsUnsafe() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(UUID.randomUUID(), 300L, 90.0F, 30.0F, Set.of(new SafeArrival(4, 80, 0)));

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(4.0D, player.lastX);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Y, player.lastY);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Z, player.lastZ);
    }

    @Test
    void returnHomeExecutesSavedWorldIndexPlacement() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            400L,
            45.0F,
            15.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );
        player.existingPortals.add(new PortalLocation(CavernDimensions.OVERWORLD_DIMENSION_ID, 8, 18, 8, PortalWorldIndex.PortalPlacement.AXIS_X));

        bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 12, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 18, 8)
        );
        int createPortalCallsAfterEntry = player.createPortalCalls;

        Optional<CavernTravelPlan> returnPlan = bootstrap.cavernTravelBridge().returnHome(player);

        assertTrue(returnPlan.isPresent());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(9.0D, player.lastX);
        assertEquals(18.0D, player.lastY);
        assertEquals(8.5D, player.lastZ);
        assertEquals(45.0F, player.lastYaw);
        assertEquals(15.0F, player.lastPitch);
        assertEquals(createPortalCallsAfterEntry, player.createPortalCalls);
    }

    @Test
    void returnHomeWithoutSavedStateFallsBackToOverworldTarget() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            500L,
            45.0F,
            15.0F,
            Set.of(new SafeArrival(0, 64, 0)),
            Optional.of(new CavernPlacementTarget(CavernDimensions.OVERWORLD_DIMENSION_ID, 3.0D, 80.0D, 4.0D))
        );

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().returnHome(player);

        assertTrue(plan.isPresent());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(3.0D, player.lastX);
        assertEquals(80.0D, player.lastY);
        assertEquals(4.0D, player.lastZ);
    }

    @Test
    void returnHomeWithoutSavedStateDoesNothingWhenFallbackIsUnavailable() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(UUID.randomUUID(), 500L, 45.0F, 15.0F, Set.of(new SafeArrival(0, 64, 0)));

        assertFalse(bootstrap.cavernTravelBridge().returnHome(player).isPresent());
        assertNull(player.lastTargetDimensionId);
    }

    private static final class FakePlayerTravelContext implements PlayerTravelContext, CavernArrivalPlacementProbe {
        private final UUID playerId;
        private final long gameTime;
        private final float yaw;
        private final float pitch;
        private final Set<SafeArrival> safeArrivals;
        private final Optional<CavernPlacementTarget> fallbackReturnTarget;
        private final Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement;
        private final Optional<PortalWorldIndex.PortalPlacement> replacementPortalPlacement;
        private final Set<PortalLocation> existingPortals = new HashSet<>();
        private int createPortalCalls;
        private int createReplacementPortalCalls;
        private String lastTargetDimensionId;
        private double lastX;
        private double lastY;
        private double lastZ;
        private float lastYaw;
        private float lastPitch;

        private FakePlayerTravelContext(UUID playerId, long gameTime, float yaw, float pitch, Set<SafeArrival> safeArrivals) {
            this(playerId, gameTime, yaw, pitch, safeArrivals, Optional.empty(), Optional.empty(), Optional.empty());
        }

        private FakePlayerTravelContext(
            UUID playerId,
            long gameTime,
            float yaw,
            float pitch,
            Set<SafeArrival> safeArrivals,
            Optional<CavernPlacementTarget> fallbackReturnTarget
        ) {
            this(playerId, gameTime, yaw, pitch, safeArrivals, fallbackReturnTarget, Optional.empty(), Optional.empty());
        }

        private FakePlayerTravelContext(
            UUID playerId,
            long gameTime,
            float yaw,
            float pitch,
            Set<SafeArrival> safeArrivals,
            Optional<CavernPlacementTarget> fallbackReturnTarget,
            Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement
        ) {
            this(playerId, gameTime, yaw, pitch, safeArrivals, fallbackReturnTarget, createdPortalPlacement, Optional.empty());
        }

        private FakePlayerTravelContext(
            UUID playerId,
            long gameTime,
            float yaw,
            float pitch,
            Set<SafeArrival> safeArrivals,
            Optional<CavernPlacementTarget> fallbackReturnTarget,
            Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement,
            Optional<PortalWorldIndex.PortalPlacement> replacementPortalPlacement
        ) {
            this.playerId = playerId;
            this.gameTime = gameTime;
            this.yaw = yaw;
            this.pitch = pitch;
            this.safeArrivals = safeArrivals;
            this.fallbackReturnTarget = fallbackReturnTarget;
            this.createdPortalPlacement = createdPortalPlacement;
            this.replacementPortalPlacement = replacementPortalPlacement;
        }

        @Override
        public UUID playerId() {
            return playerId;
        }

        @Override
        public long gameTime() {
            return gameTime;
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
        public Optional<CavernPlacementTarget> fallbackReturnTarget() {
            return fallbackReturnTarget;
        }

        @Override
        public boolean hasPortalAt(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .anyMatch(portal -> portal.dimensionId().equals(targetDimensionId)
                    && portal.x() == x
                    && portal.y() == y
                    && portal.z() == z);
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
            createPortalCalls++;
            createdPortalPlacement.ifPresent(placement -> existingPortals.add(new PortalLocation(
                targetDimensionId,
                placement.x(),
                placement.y(),
                placement.z(),
                placement.axis()
            )));
            return createdPortalPlacement;
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> createReplacementPortalAt(
            String targetDimensionId,
            PortalWorldIndex.PortalPlacement stalePlacement
        ) {
            createReplacementPortalCalls++;
            replacementPortalPlacement.ifPresent(placement -> existingPortals.add(new PortalLocation(
                targetDimensionId,
                placement.x(),
                placement.y(),
                placement.z(),
                placement.axis()
            )));
            return replacementPortalPlacement;
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
    }

    private record SafeArrival(int x, int y, int z) {
    }

    private record PortalLocation(String dimensionId, int x, int y, int z, String axis) {
    }
}
