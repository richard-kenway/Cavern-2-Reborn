package com.richardkenway.cavernreborn.core.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CaveniaCaveCarverPolicyTest {
    @Test
    void sourceConfirmedFlagsAndTunnelOriginBandRemainPinned() {
        assertTrue(CaveniaCaveCarverPolicy.DEFAULT_GENERATE_CAVES);
        assertEquals(20, CaveniaCaveCarverPolicy.minTunnelOriginY());
        assertEquals(24, CaveniaCaveCarverPolicy.maxTunnelOriginY());

        for (int y = 20; y <= 24; y++) {
            assertTrue(CaveniaCaveCarverPolicy.isTunnelOriginY(y));
        }

        assertFalse(CaveniaCaveCarverPolicy.isTunnelOriginY(19));
        assertFalse(CaveniaCaveCarverPolicy.isTunnelOriginY(25));
    }

    @Test
    void sourceConfirmedTunnelParametersRemainPinned() {
        assertEquals(1.35D, CaveniaCaveCarverPolicy.SCALE_HEIGHT);
        assertEquals(18.0F, CaveniaCaveCarverPolicy.MAX_DISTANCE_EXTRA);
        assertEquals(0.92F, CaveniaCaveCarverPolicy.PITCH_DAMPENING_WHEN_CHANCE);
        assertEquals(0.7F, CaveniaCaveCarverPolicy.PITCH_DAMPENING_OTHERWISE);
    }

    @Test
    void replacementLogicRemainsSourceConfirmed() {
        assertEquals(CaveniaCaveCarverReplacement.GRAVEL, CaveniaCaveCarverPolicy.replacementForY(0));
        assertEquals(CaveniaCaveCarverReplacement.GRAVEL, CaveniaCaveCarverPolicy.replacementForY(2));
        assertEquals(CaveniaCaveCarverReplacement.WATER, CaveniaCaveCarverPolicy.replacementForY(3));
        assertEquals(CaveniaCaveCarverReplacement.WATER, CaveniaCaveCarverPolicy.replacementForY(10));
        assertEquals(CaveniaCaveCarverReplacement.AIR, CaveniaCaveCarverPolicy.replacementForY(11));
        assertEquals(CaveniaCaveCarverReplacement.AIR, CaveniaCaveCarverPolicy.replacementForY(40));
        assertEquals("minecraft:gravel", CaveniaCaveCarverPolicy.replacementBlockForY(0));
        assertEquals("minecraft:water", CaveniaCaveCarverPolicy.replacementBlockForY(3));
        assertEquals("minecraft:air", CaveniaCaveCarverPolicy.replacementBlockForY(11));
        assertTrue(CaveniaCaveCarverPolicy.isLowGravelBand(2));
        assertFalse(CaveniaCaveCarverPolicy.isLowWaterBand(2));
        assertTrue(CaveniaCaveCarverPolicy.isLowWaterBand(10));
        assertFalse(CaveniaCaveCarverPolicy.isLowWaterBand(11));
        assertTrue(CaveniaCaveCarverPolicy.isAirCarveBand(11));
    }

    @Test
    void caveCarvingOrderRemainsAlignedWithTheTerrainPolicy() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        assertTrue(steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING) > steps.indexOf(CaveniaTerrainStep.BASE_STONE_FILL));
        assertTrue(steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING) < steps.indexOf(CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT));
        assertTrue(steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING) < steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION));
        assertTrue(CaveniaCaveCarverPolicy.runsBeforeBiomeTopFilterReplacement());
        assertTrue(CaveniaCaveCarverPolicy.runsBeforeVeinsMutation());
    }
}
