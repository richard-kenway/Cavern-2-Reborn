package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

class CavernTravelBridgeTest {
    @Test
    void travelToCavernAdjustsUnsafePlacementUsingWorldProbe() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(UUID.randomUUID(), 90.0F, 30.0F, Set.of(64));

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
    void returnHomeExecutesSavedWorldIndexPlacement() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(UUID.randomUUID(), 45.0F, 15.0F, Set.of(64));

        bootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 12, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 18, 8)
        );

        Optional<CavernTravelPlan> returnPlan = bootstrap.cavernTravelBridge().returnHome(player);

        assertTrue(returnPlan.isPresent());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, player.lastTargetDimensionId);
        assertEquals(8.0D, player.lastX);
        assertEquals(18.0D, player.lastY);
        assertEquals(8.0D, player.lastZ);
        assertEquals(45.0F, player.lastYaw);
        assertEquals(15.0F, player.lastPitch);
    }

    @Test
    void returnHomeWithoutSavedStateDoesNothing() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        FakePlayerTravelContext player = new FakePlayerTravelContext(UUID.randomUUID(), 45.0F, 15.0F, Set.of(64));

        assertFalse(bootstrap.cavernTravelBridge().returnHome(player).isPresent());
        assertNull(player.lastTargetDimensionId);
    }

    private static final class FakePlayerTravelContext implements PlayerTravelContext, CavernArrivalPlacementProbe {
        private final UUID playerId;
        private final float yaw;
        private final float pitch;
        private final Set<Integer> safeArrivalYs;
        private String lastTargetDimensionId;
        private double lastX;
        private double lastY;
        private double lastZ;
        private float lastYaw;
        private float lastPitch;

        private FakePlayerTravelContext(UUID playerId, float yaw, float pitch, Set<Integer> safeArrivalYs) {
            this.playerId = playerId;
            this.yaw = yaw;
            this.pitch = pitch;
            this.safeArrivalYs = safeArrivalYs;
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
                && safeArrivalYs.contains(y);
        }
    }
}
