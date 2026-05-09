package com.richardkenway.cavernreborn.core.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CaveniaPopulationPolicyTest {
    @Test
    void defaultsRemainSourceConfirmed() {
        assertTrue(CaveniaPopulationPolicy.generateLakesDefaultEnabled());
        assertFalse(CaveniaPopulationPolicy.HAS_SEPARATE_CAVENIC_SHROOM_CONFIG_FLAG);
        assertFalse(CaveniaPopulationPolicy.HAS_SEPARATE_GENERATE_FALLS_CONFIG_FLAG);
        assertTrue(CaveniaPopulationPolicy.FALLS_REQUIRE_BIOME_DECORATOR_GENERATE_FALLS);
    }

    @Test
    void populationOrderRemainsLaterThanFinalChunkConstructionAndVeins() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        assertTrue(steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE) > steps.indexOf(CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION));
        assertTrue(steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE) > steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION));
        assertTrue(CaveniaPopulationPolicy.populationRunsAfterFinalChunkConstruction());
        assertTrue(CaveniaPopulationPolicy.populationRunsAfterVeinsMutation());
    }

    @Test
    void exactPopulationStepOrderingRemainsPinned() {
        assertEquals(
            List.of(
                CaveniaPopulationStep.ENABLE_FALLING_INSTANTLY,
                CaveniaPopulationStep.PRE_CHUNK_POPULATE_EVENT,
                CaveniaPopulationStep.LAKE_BRANCHES,
                CaveniaPopulationStep.DECORATE_BIOME_PRE_EVENT,
                CaveniaPopulationStep.ORE_GEN_POST_EVENT,
                CaveniaPopulationStep.CAVENIC_SHROOM_BRANCH,
                CaveniaPopulationStep.FALL_LIQUID_BRANCHES,
                CaveniaPopulationStep.DECORATE_BIOME_POST_EVENT,
                CaveniaPopulationStep.POST_CHUNK_POPULATE_EVENT,
                CaveniaPopulationStep.DISABLE_FALLING_INSTANTLY
            ),
            CaveniaPopulationPolicy.populationSteps()
        );
    }

    @Test
    void lakeRulesRemainSourceConfirmed() {
        assertEquals(
            List.of(
                new CaveniaPopulationPolicy.LakeRule(
                    "non_sandy_water_lake",
                    CaveniaPopulationBranch.WATER_LAKE,
                    "WorldGenLakes(Blocks.WATER)",
                    "!Type.END && !Type.NETHER",
                    "Type.SANDY",
                    "EventType.LAKE",
                    "rand.nextInt(4) == 0",
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(worldHeight - 16)"
                )
            ),
            CaveniaPopulationPolicy.waterLakeRules()
        );
        assertEquals(
            List.of(
                new CaveniaPopulationPolicy.LakeRule(
                    "nether_lava_lake",
                    CaveniaPopulationBranch.LAVA_LAKE,
                    "WorldGenLakes(Blocks.LAVA)",
                    "Type.NETHER",
                    "",
                    "EventType.LAVA",
                    "rand.nextInt(4) == 0",
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(worldHeight - 32) + 16"
                ),
                new CaveniaPopulationPolicy.LakeRule(
                    "non_end_lava_lake",
                    CaveniaPopulationBranch.LAVA_LAKE,
                    "WorldGenLakes(Blocks.LAVA)",
                    "!Type.END && !Type.NETHER",
                    "",
                    "EventType.LAVA",
                    "rand.nextInt(10) == 0",
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(worldHeight / 2 - 16) + 32"
                )
            ),
            CaveniaPopulationPolicy.lavaLakeRules()
        );
    }

    @Test
    void fallRulesRemainSourceConfirmed() {
        assertEquals(
            List.of(
                new CaveniaPopulationPolicy.FallRule(
                    "nether_lava_fall",
                    CaveniaPopulationBranch.LAVA_FALL,
                    "WorldGenLiquids(Blocks.FLOWING_LAVA)",
                    "Type.NETHER",
                    "",
                    "Decorate.EventType.LAKE_LAVA",
                    70,
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(worldHeight - 22) + 20"
                ),
                new CaveniaPopulationPolicy.FallRule(
                    "water_biome_water_fall",
                    CaveniaPopulationBranch.WATER_FALL,
                    "WorldGenLiquids(Blocks.FLOWING_WATER)",
                    "Type.WATER",
                    "",
                    "Decorate.EventType.LAKE_WATER",
                    50,
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(rand.nextInt(worldHeight - 16) + 10)"
                ),
                new CaveniaPopulationPolicy.FallRule(
                    "other_biome_water_fall",
                    CaveniaPopulationBranch.WATER_FALL,
                    "WorldGenLiquids(Blocks.FLOWING_WATER)",
                    "!Type.END && !Type.NETHER && !Type.WATER",
                    "",
                    "Decorate.EventType.LAKE_WATER",
                    50,
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(rand.nextInt(worldHeight - 16) + 10)"
                ),
                new CaveniaPopulationPolicy.FallRule(
                    "other_biome_lava_fall",
                    CaveniaPopulationBranch.LAVA_FALL,
                    "WorldGenLiquids(Blocks.FLOWING_LAVA)",
                    "!Type.END && !Type.NETHER && !Type.WATER",
                    "",
                    "Decorate.EventType.LAKE_LAVA",
                    50,
                    "rand.nextInt(16) + 8",
                    "rand.nextInt(worldHeight / 2 - 32) + 20"
                )
            ),
            CaveniaPopulationPolicy.fallRules()
        );
    }

    @Test
    void shroomRuleRemainsSourceConfirmed() {
        assertEquals(
            List.of(
                new CaveniaPopulationPolicy.ShroomRule(
                    "cavenic_shroom",
                    CaveniaPopulationBranch.CAVENIC_SHROOM,
                    "WorldGenBush(CaveBlocks.CAVENIC_SHROOM)",
                    "Decorate.EventType.SHROOM",
                    0,
                    2,
                    1,
                    6,
                    "rand.nextInt(6) <= i",
                    "blockPos.add(16, 0, 16)"
                )
            ),
            CaveniaPopulationPolicy.cavenicShroomRules()
        );
    }

    @Test
    void branchHelpersRemainDeterministic() {
        assertFalse(CaveniaPopulationPolicy.isVeinsBranch(CaveniaPopulationBranch.WATER_LAKE));
        assertTrue(CaveniaPopulationPolicy.isLakeBranch(CaveniaPopulationBranch.WATER_LAKE));
        assertTrue(CaveniaPopulationPolicy.isLakeBranch(CaveniaPopulationBranch.LAVA_LAKE));
        assertTrue(CaveniaPopulationPolicy.isFallBranch(CaveniaPopulationBranch.WATER_FALL));
        assertTrue(CaveniaPopulationPolicy.isFallBranch(CaveniaPopulationBranch.LAVA_FALL));
        assertTrue(CaveniaPopulationPolicy.isShroomBranch(CaveniaPopulationBranch.CAVENIC_SHROOM));
        assertFalse(CaveniaPopulationPolicy.isLakeBranch(CaveniaPopulationBranch.CAVENIC_SHROOM));
        assertFalse(CaveniaPopulationPolicy.isFallBranch(CaveniaPopulationBranch.WATER_LAKE));
    }

    @Test
    void returnedCollectionsRemainSafelyUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaPopulationPolicy.populationSteps().add(CaveniaPopulationStep.ENABLE_FALLING_INSTANTLY)
        );
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaPopulationPolicy.waterLakeRules().add(
                new CaveniaPopulationPolicy.LakeRule(
                    "extra",
                    CaveniaPopulationBranch.WATER_LAKE,
                    "WorldGenLakes(Blocks.WATER)",
                    "",
                    "",
                    "EventType.LAKE",
                    "false",
                    "rand.nextInt(16) + 8",
                    "0"
                )
            )
        );
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaPopulationPolicy.fallRules().add(
                new CaveniaPopulationPolicy.FallRule(
                    "extra",
                    CaveniaPopulationBranch.WATER_FALL,
                    "WorldGenLiquids(Blocks.FLOWING_WATER)",
                    "",
                    "",
                    "Decorate.EventType.LAKE_WATER",
                    1,
                    "rand.nextInt(16) + 8",
                    "0"
                )
            )
        );
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaPopulationPolicy.cavenicShroomRules().add(
                new CaveniaPopulationPolicy.ShroomRule(
                    "extra",
                    CaveniaPopulationBranch.CAVENIC_SHROOM,
                    "WorldGenBush(CaveBlocks.CAVENIC_SHROOM)",
                    "Decorate.EventType.SHROOM",
                    0,
                    0,
                    0,
                    1,
                    "true",
                    "blockPos"
                )
            )
        );
    }
}
