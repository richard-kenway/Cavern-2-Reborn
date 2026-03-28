package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;

class CavernPortalInteractionServiceTest {
    @Test
    void useOutsideCavernSendsPlayerIntoCavern() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11,
            70,
            11,
            0.25D,
            0.5D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );

        Optional<CavernTravelPlan> plan = service.use(context);

        assertTrue(plan.isPresent());
        assertTrue(context.feedbackKeys.isEmpty());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, context.lastTargetDimensionId);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_X, context.lastX);
        assertEquals(64.0D, context.lastY);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Z, context.lastZ);
        assertEquals(90.0F, context.lastYaw);
        assertEquals(30.0F, context.lastPitch);
    }

    @Test
    void useOutsideCavernCancelsWhenNoSafeArrivalExists() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11,
            70,
            11,
            0.25D,
            0.5D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of()
        );

        assertTrue(service.use(context).isEmpty());
        assertNull(context.lastTargetDimensionId);
        assertEquals(1, context.feedbackKeys.size());
        assertEquals(CavernPortalInteractionService.PORTAL_ENTRY_FAILED_MESSAGE_KEY, context.feedbackKeys.get(0));
        assertTrue(bootstrap.cavernTravelBridge().returnHome(context).isEmpty());
    }

    @Test
    void useInsideCavernReturnsPlayerHome() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        UUID playerId = UUID.randomUUID();

        FakePortalInteractionContext entryContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11,
            70,
            11,
            0.25D,
            0.5D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );
        service.use(entryContext);

        FakePortalInteractionContext cavernContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            0,
            80,
            0,
            0.25D,
            0.5D,
            0.75D,
            "south",
            45.0F,
            15.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );

        Optional<CavernTravelPlan> plan = service.use(cavernContext);

        assertTrue(plan.isPresent());
        assertTrue(cavernContext.feedbackKeys.isEmpty());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, cavernContext.lastTargetDimensionId);
        assertEquals(11.0D, cavernContext.lastX);
        assertEquals(70.0D, cavernContext.lastY);
        assertEquals(11.0D, cavernContext.lastZ);
        assertEquals(45.0F, cavernContext.lastYaw);
        assertEquals(15.0F, cavernContext.lastPitch);
    }

    @Test
    void useOnClientSideDoesNothing() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            true,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11,
            70,
            11,
            0.25D,
            0.5D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );

        assertFalse(service.use(context).isPresent());
        assertEquals(0.0D, context.lastX);
        assertTrue(context.feedbackKeys.isEmpty());
    }

    @Test
    void useInsideCavernWithoutSavedReturnStateDoesNothing() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            0,
            80,
            0,
            0.25D,
            0.5D,
            0.75D,
            "south",
            45.0F,
            15.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );

        assertFalse(service.use(context).isPresent());
        assertEquals(0.0D, context.lastX);
        assertTrue(context.feedbackKeys.isEmpty());
    }

    @Test
    void useInsideCavernReturnsToTheExactPortalUsedForEntry() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        UUID playerId = UUID.randomUUID();

        service.use(new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11,
            70,
            11,
            0.25D,
            0.5D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0))
        ));
        service.use(new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            25,
            72,
            25,
            0.25D,
            0.5D,
            0.75D,
            "north",
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0))
        ));

        FakePortalInteractionContext returnContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            0,
            80,
            0,
            0.25D,
            0.5D,
            0.75D,
            "south",
            45.0F,
            15.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );

        Optional<CavernTravelPlan> plan = service.use(returnContext);

        assertTrue(plan.isPresent());
        assertTrue(returnContext.feedbackKeys.isEmpty());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, returnContext.lastTargetDimensionId);
        assertEquals(25.0D, returnContext.lastX);
        assertEquals(72.0D, returnContext.lastY);
        assertEquals(25.0D, returnContext.lastZ);
    }

    private static final class FakePortalInteractionContext implements CavernPortalInteractionContext, CavernArrivalPlacementProbe {
        private final UUID playerId;
        private final boolean clientSide;
        private final String currentDimensionId;
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
        private final ArrayList<String> feedbackKeys = new ArrayList<>();
        private String lastTargetDimensionId;
        private double lastX;
        private double lastY;
        private double lastZ;
        private float lastYaw;
        private float lastPitch;

        private FakePortalInteractionContext(
            UUID playerId,
            boolean clientSide,
            String currentDimensionId,
            int portalX,
            int portalY,
            int portalZ,
            double hitOffsetX,
            double hitOffsetY,
            double hitOffsetZ,
            String approachFacing,
            float yaw,
            float pitch,
            Set<SafeArrival> safeArrivals
        ) {
            this.playerId = playerId;
            this.clientSide = clientSide;
            this.currentDimensionId = currentDimensionId;
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
        }

        @Override
        public boolean isClientSide() {
            return clientSide;
        }

        @Override
        public void showPortalFeedback(String translationKey) {
            feedbackKeys.add(translationKey);
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
}
