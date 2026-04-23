package com.richardkenway.cavernreborn.core.compass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class OreCompassScanPolicyTest {
    @Test
    void wrongDimensionReturnsWrongDimensionDecision() {
        assertEquals(
            OreCompassScanDecision.WRONG_DIMENSION,
            OreCompassScanPolicy.evaluate(
                CavernDimensions.OVERWORLD_DIMENSION_ID,
                true,
                "cavernreborn:hexcite_ore",
                0,
                0,
                -5
            ).decision()
        );
    }

    @Test
    void noPlayerContextReturnsNoPlayerDecision() {
        assertEquals(
            OreCompassScanDecision.NO_PLAYER_CONTEXT,
            OreCompassScanPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                false,
                "cavernreborn:hexcite_ore",
                0,
                0,
                -5
            ).decision()
        );
    }

    @Test
    void noTargetReturnsNoTargetDecision() {
        assertEquals(
            OreCompassScanDecision.NO_TARGET,
            OreCompassScanPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                true,
                null,
                0,
                0,
                0
            ).decision()
        );
    }

    @Test
    void foundTargetReturnsFoundDecision() {
        OreCompassScanResult result = OreCompassScanPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            true,
            "cavernreborn:hexcite_ore",
            4,
            -3,
            0
        );

        assertEquals(OreCompassScanDecision.FOUND, result.decision());
        assertEquals("cavernreborn:hexcite_ore", result.targetBlockId());
        assertEquals(4, result.horizontalDistance());
        assertEquals(-3, result.verticalOffset());
        assertEquals(OreCompassDirection.EAST, result.direction());
    }

    @Test
    void horizontalDistanceUsesRoundedEuclideanDistance() {
        assertEquals(5, OreCompassScanPolicy.horizontalDistance(3, 4));
    }

    @Test
    void directionNorthIsDetected() {
        assertEquals(OreCompassDirection.NORTH, OreCompassScanPolicy.directionForOffset(0, -4));
    }

    @Test
    void directionSouthIsDetected() {
        assertEquals(OreCompassDirection.SOUTH, OreCompassScanPolicy.directionForOffset(0, 4));
    }

    @Test
    void directionEastIsDetected() {
        assertEquals(OreCompassDirection.EAST, OreCompassScanPolicy.directionForOffset(4, 0));
    }

    @Test
    void directionWestIsDetected() {
        assertEquals(OreCompassDirection.WEST, OreCompassScanPolicy.directionForOffset(-4, 0));
    }

    @Test
    void directionNorthEastIsDetected() {
        assertEquals(OreCompassDirection.NORTH_EAST, OreCompassScanPolicy.directionForOffset(4, -4));
    }

    @Test
    void directionSouthWestIsDetected() {
        assertEquals(OreCompassDirection.SOUTH_WEST, OreCompassScanPolicy.directionForOffset(-4, 4));
    }

    @Test
    void samePositionDirectionIsDetected() {
        assertEquals(OreCompassDirection.SAME_POSITION, OreCompassScanPolicy.directionForOffset(0, 0));
    }

    @Test
    void constantsRemainBounded() {
        assertEquals(32, OreCompassScanPolicy.HORIZONTAL_RADIUS);
        assertEquals(24, OreCompassScanPolicy.VERTICAL_RADIUS);
        assertEquals(400, OreCompassScanPolicy.COOLDOWN_TICKS);
    }
}
