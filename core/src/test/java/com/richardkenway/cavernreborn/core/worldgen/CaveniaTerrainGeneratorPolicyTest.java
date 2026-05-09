package com.richardkenway.cavernreborn.core.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CaveniaTerrainGeneratorPolicyTest {
    @Test
    void legacyHeightConstantsRemainPinned() {
        assertEquals(128, CaveniaTerrainGeneratorPolicy.WORLD_HEIGHT);
        assertEquals(0, CaveniaTerrainGeneratorPolicy.MIN_Y);
        assertEquals(0, CaveniaTerrainGeneratorPolicy.BEDROCK_FLOOR_Y);
        assertEquals(127, CaveniaTerrainGeneratorPolicy.BEDROCK_CEILING_Y);
        assertEquals(128, CaveniaTerrainGeneratorPolicy.AIR_CLEAR_FROM_Y);
        assertEquals(255, CaveniaTerrainGeneratorPolicy.AIR_CLEAR_TO_Y);
    }

    @Test
    void bedrockHelpersRemainSourceConfirmed() {
        assertTrue(CaveniaTerrainGeneratorPolicy.isBedrockY(0));
        assertTrue(CaveniaTerrainGeneratorPolicy.isBedrockY(127));
        assertFalse(CaveniaTerrainGeneratorPolicy.isBedrockY(1));
        assertFalse(CaveniaTerrainGeneratorPolicy.isBedrockY(126));
    }

    @Test
    void worldHeightHelpersRemainSourceConfirmed() {
        assertTrue(CaveniaTerrainGeneratorPolicy.isInsideLegacyWorldHeight(0));
        assertTrue(CaveniaTerrainGeneratorPolicy.isInsideLegacyWorldHeight(127));
        assertFalse(CaveniaTerrainGeneratorPolicy.isInsideLegacyWorldHeight(128));
        assertTrue(CaveniaTerrainGeneratorPolicy.isAboveLegacyWorldHeight(128));
        assertFalse(CaveniaTerrainGeneratorPolicy.isAboveLegacyWorldHeight(127));
        assertTrue(CaveniaTerrainGeneratorPolicy.shouldClearToAir(128));
        assertTrue(CaveniaTerrainGeneratorPolicy.shouldClearToAir(255));
        assertFalse(CaveniaTerrainGeneratorPolicy.shouldClearToAir(127));
        assertFalse(CaveniaTerrainGeneratorPolicy.shouldClearToAir(256));
    }

    @Test
    void blockIdsRemainSourceConfirmed() {
        assertEquals("minecraft:stone", CaveniaTerrainGeneratorPolicy.BASE_TERRAIN_BLOCK_ID);
        assertEquals("minecraft:bedrock", CaveniaTerrainGeneratorPolicy.BEDROCK_BLOCK_ID);
        assertEquals("minecraft:air", CaveniaTerrainGeneratorPolicy.AIR_BLOCK_ID);
    }

    @Test
    void generationOrderRemainsSourceConfirmed() {
        assertEquals(
            List.of(
                CaveniaTerrainStep.BASE_STONE_FILL,
                CaveniaTerrainStep.OPTIONAL_CAVE_CARVING,
                CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT,
                CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION,
                CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION,
                CaveniaTerrainStep.LATER_POPULATION_STAGE
            ),
            CaveniaTerrainGeneratorPolicy.generationSteps()
        );
    }

    @Test
    void populationRemainsLaterThanFinalChunkConstruction() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        assertTrue(steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE) > steps.indexOf(CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION));
        assertTrue(CaveniaTerrainGeneratorPolicy.isPopulationStep(CaveniaTerrainStep.LATER_POPULATION_STAGE));
        assertFalse(CaveniaTerrainGeneratorPolicy.isPopulationStep(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION));
    }
}
