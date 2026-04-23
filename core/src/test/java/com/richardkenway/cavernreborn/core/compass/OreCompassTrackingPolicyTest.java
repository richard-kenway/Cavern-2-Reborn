package com.richardkenway.cavernreborn.core.compass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class OreCompassTrackingPolicyTest {
    @Test
    void noStoredTargetReturnsNoStoredTargetDecision() {
        assertEquals(
            OreCompassTrackingDecision.NO_STORED_TARGET,
            OreCompassTrackingPolicy.evaluate(
                false,
                true,
                CavernDimensions.CAVERN_DIMENSION_ID,
                CavernDimensions.CAVERN_DIMENSION_ID,
                0,
                0,
                0,
                true
            ).decision()
        );
    }

    @Test
    void noEntityContextReturnsNoEntityDecision() {
        assertEquals(
            OreCompassTrackingDecision.NO_ENTITY_CONTEXT,
            OreCompassTrackingPolicy.evaluate(
                true,
                false,
                CavernDimensions.CAVERN_DIMENSION_ID,
                CavernDimensions.CAVERN_DIMENSION_ID,
                0,
                0,
                -4,
                true
            ).decision()
        );
    }

    @Test
    void wrongDimensionReturnsWrongDimensionDecision() {
        assertEquals(
            OreCompassTrackingDecision.WRONG_DIMENSION,
            OreCompassTrackingPolicy.evaluate(
                true,
                true,
                CavernDimensions.OVERWORLD_DIMENSION_ID,
                CavernDimensions.CAVERN_DIMENSION_ID,
                0,
                0,
                -4,
                true
            ).decision()
        );
    }

    @Test
    void outOfRangeReturnsOutOfRangeDecision() {
        assertEquals(
            OreCompassTrackingDecision.OUT_OF_RANGE,
            OreCompassTrackingPolicy.evaluate(
                true,
                true,
                CavernDimensions.CAVERN_DIMENSION_ID,
                CavernDimensions.CAVERN_DIMENSION_ID,
                0,
                0,
                -(OreCompassTrackingPolicy.TRACKING_RADIUS + 1),
                true
            ).decision()
        );
    }

    @Test
    void blockMismatchReturnsBlockMismatchDecision() {
        assertEquals(
            OreCompassTrackingDecision.BLOCK_MISMATCH,
            OreCompassTrackingPolicy.evaluate(
                true,
                true,
                CavernDimensions.CAVERN_DIMENSION_ID,
                CavernDimensions.CAVERN_DIMENSION_ID,
                0,
                3,
                -4,
                false
            ).decision()
        );
    }

    @Test
    void trackingPathReturnsTrackingDecision() {
        OreCompassTrackingResult result = OreCompassTrackingPolicy.evaluate(
            true,
            true,
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_DIMENSION_ID,
            4,
            -3,
            0,
            true
        );

        assertEquals(OreCompassTrackingDecision.TRACKING, result.decision());
        assertEquals(OreCompassDirection.EAST, result.direction());
        assertEquals(4, result.horizontalDistance());
        assertEquals(-3, result.verticalOffset());
        assertEquals(8, result.angleFrame());
    }

    @Test
    void northAngleFrameIsDetected() {
        assertEquals(0, OreCompassTrackingPolicy.angleFrameForOffset(0, -4));
    }

    @Test
    void southAngleFrameIsDetected() {
        assertEquals(16, OreCompassTrackingPolicy.angleFrameForOffset(0, 4));
    }

    @Test
    void eastAngleFrameIsDetected() {
        assertEquals(8, OreCompassTrackingPolicy.angleFrameForOffset(4, 0));
    }

    @Test
    void westAngleFrameIsDetected() {
        assertEquals(24, OreCompassTrackingPolicy.angleFrameForOffset(-4, 0));
    }

    @Test
    void northEastAngleFrameIsDetected() {
        assertEquals(4, OreCompassTrackingPolicy.angleFrameForOffset(4, -4));
    }

    @Test
    void southWestAngleFrameIsDetected() {
        assertEquals(20, OreCompassTrackingPolicy.angleFrameForOffset(-4, 4));
    }

    @Test
    void samePositionAngleFrameIsDetected() {
        assertEquals(0, OreCompassTrackingPolicy.angleFrameForOffset(0, 0));
    }

    @Test
    void constantsRemainBounded() {
        assertEquals(50, OreCompassTrackingPolicy.TRACKING_RADIUS);
        assertEquals(32, OreCompassTrackingPolicy.ANGLE_FRAME_COUNT);
    }
}
