package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;

class CavernPortalInteractionServiceTest {
    @Test
    void useOutsideCavernSendsPlayerIntoCavernAndAppliesCooldown() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            100L,
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

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isPresent());
        assertTrue(context.feedbackKeys.isEmpty());
        assertTrue(service.isOnCooldown(context));
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, context.lastTargetDimensionId);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_X, context.lastX);
        assertEquals(64.0D, context.lastY);
        assertEquals((double) CavernDimensions.CAVERN_ENTRY_Z, context.lastZ);
        assertEquals(90.0F, context.lastYaw);
        assertEquals(30.0F, context.lastPitch);
    }

    @Test
    void useOutsideCavernCancelsWhenNoSafeArrivalExistsAndSuppressesRepeatFeedback() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            200L,
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

        CavernPortalInteractionOutcome firstOutcome = service.use(context);
        assertTrue(firstOutcome.handled());
        assertTrue(firstOutcome.travelPlan().isEmpty());
        assertNull(context.lastTargetDimensionId);
        assertEquals(1, context.feedbackKeys.size());
        assertEquals(CavernPortalInteractionService.PORTAL_ENTRY_FAILED_MESSAGE_KEY, context.feedbackKeys.get(0));
        assertTrue(service.isOnCooldown(context));

        CavernPortalInteractionOutcome secondOutcome = service.use(context);
        assertTrue(secondOutcome.handled());
        assertTrue(secondOutcome.travelPlan().isEmpty());
        assertEquals(1, context.feedbackKeys.size());

        CavernPortalInteractionOutcome thirdOutcome = service.use(context);
        assertTrue(thirdOutcome.handled());
        assertTrue(thirdOutcome.travelPlan().isEmpty());
        assertEquals(1, context.feedbackKeys.size());
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
            300L,
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
        CavernPortalInteractionOutcome entryOutcome = service.use(entryContext);
        assertTrue(entryOutcome.handled());
        assertTrue(entryOutcome.travelPlan().isPresent());

        FakePortalInteractionContext cavernContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            320L,
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

        CavernPortalInteractionOutcome outcome = service.use(cavernContext);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isPresent());
        assertTrue(cavernContext.feedbackKeys.isEmpty());
        assertTrue(service.isOnCooldown(cavernContext));
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
            400L,
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

        CavernPortalInteractionOutcome outcome = service.use(context);

        assertFalse(outcome.handled());
        assertTrue(outcome.travelPlan().isEmpty());
        assertEquals(0.0D, context.lastX);
        assertTrue(context.feedbackKeys.isEmpty());
    }

    @Test
    void useInsideCavernWithoutSavedReturnStateShowsMissingReturnFeedbackOnce() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        FakePortalInteractionContext context = new FakePortalInteractionContext(
            UUID.randomUUID(),
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            500L,
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

        CavernPortalInteractionOutcome firstOutcome = service.use(context);
        assertTrue(firstOutcome.handled());
        assertTrue(firstOutcome.travelPlan().isEmpty());
        assertEquals(0.0D, context.lastX);
        assertEquals(1, context.feedbackKeys.size());
        assertEquals(CavernPortalInteractionService.PORTAL_RETURN_MISSING_MESSAGE_KEY, context.feedbackKeys.get(0));
        assertFalse(service.isOnCooldown(context));

        CavernPortalInteractionOutcome secondOutcome = service.use(context);
        assertTrue(secondOutcome.handled());
        assertTrue(secondOutcome.travelPlan().isEmpty());
        assertEquals(1, context.feedbackKeys.size());
    }

    @Test
    void useInsideCavernReturnsToTheExactPortalUsedForEntry() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        UUID playerId = UUID.randomUUID();

        CavernPortalInteractionOutcome firstEntry = service.use(new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            600L,
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
        assertTrue(firstEntry.handled());
        assertTrue(firstEntry.travelPlan().isPresent());

        CavernPortalInteractionOutcome secondEntry = service.use(new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            620L,
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
        assertTrue(secondEntry.handled());
        assertTrue(secondEntry.travelPlan().isPresent());

        FakePortalInteractionContext returnContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.CAVERN_DIMENSION_ID,
            630L,
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

        CavernPortalInteractionOutcome outcome = service.use(returnContext);

        assertTrue(outcome.handled());
        assertTrue(outcome.travelPlan().isPresent());
        assertTrue(returnContext.feedbackKeys.isEmpty());
        assertTrue(service.isOnCooldown(returnContext));
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, returnContext.lastTargetDimensionId);
        assertEquals(25.0D, returnContext.lastX);
        assertEquals(72.0D, returnContext.lastY);
        assertEquals(25.0D, returnContext.lastZ);
    }

    @Test
    void useOutsideCavernWorksAgainAfterCooldownExpires() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();
        CavernPortalInteractionService service = bootstrap.cavernPortalInteractionService();
        UUID playerId = UUID.randomUUID();

        FakePortalInteractionContext firstContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            700L,
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
        CavernPortalInteractionOutcome firstOutcome = service.use(firstContext);
        assertTrue(firstOutcome.handled());
        assertTrue(firstOutcome.travelPlan().isPresent());
        assertTrue(service.isOnCooldown(firstContext));

        FakePortalInteractionContext cooledContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            710L,
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
        CavernPortalInteractionOutcome cooledOutcome = service.use(cooledContext);
        assertTrue(cooledOutcome.handled());
        assertTrue(cooledOutcome.travelPlan().isEmpty());
        assertNull(cooledContext.lastTargetDimensionId);
        assertEquals(1, cooledContext.feedbackKeys.size());
        assertEquals(CavernPortalInteractionService.PORTAL_COOLDOWN_MESSAGE_KEY, cooledContext.feedbackKeys.get(0));

        FakePortalInteractionContext laterContext = new FakePortalInteractionContext(
            playerId,
            false,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            721L,
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

        CavernPortalInteractionOutcome laterOutcome = service.use(laterContext);
        assertTrue(laterOutcome.handled());
        assertTrue(laterOutcome.travelPlan().isPresent());
        assertTrue(laterContext.feedbackKeys.isEmpty());
        assertEquals(CavernDimensions.CAVERN_DIMENSION_ID, laterContext.lastTargetDimensionId);
    }

    private static final class FakePortalInteractionContext implements CavernPortalInteractionContext, CavernArrivalPlacementProbe {
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
            Set<SafeArrival> safeArrivals
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
