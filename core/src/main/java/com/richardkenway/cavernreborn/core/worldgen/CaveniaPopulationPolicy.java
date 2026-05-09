package com.richardkenway.cavernreborn.core.worldgen;

import java.util.List;
import java.util.Objects;

public final class CaveniaPopulationPolicy {
    public static final boolean DEFAULT_GENERATE_LAKES = true;
    public static final boolean HAS_SEPARATE_CAVENIC_SHROOM_CONFIG_FLAG = false;
    public static final boolean HAS_SEPARATE_GENERATE_FALLS_CONFIG_FLAG = false;
    public static final boolean FALLS_REQUIRE_BIOME_DECORATOR_GENERATE_FALLS = true;

    private static final List<CaveniaPopulationStep> POPULATION_STEPS = List.of(
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
    );

    private static final List<LakeRule> LAKE_RULES = List.of(
        new LakeRule(
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
        new LakeRule(
            "non_sandy_water_lake",
            CaveniaPopulationBranch.WATER_LAKE,
            "WorldGenLakes(Blocks.WATER)",
            "!Type.END && !Type.NETHER",
            "Type.SANDY",
            "EventType.LAKE",
            "rand.nextInt(4) == 0",
            "rand.nextInt(16) + 8",
            "rand.nextInt(worldHeight - 16)"
        ),
        new LakeRule(
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
    );

    private static final List<FallRule> FALL_RULES = List.of(
        new FallRule(
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
        new FallRule(
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
        new FallRule(
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
        new FallRule(
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
    );

    private static final List<ShroomRule> CAVENIC_SHROOM_RULES = List.of(
        new ShroomRule(
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
    );

    private CaveniaPopulationPolicy() {
    }

    public static boolean generateLakesDefaultEnabled() {
        return DEFAULT_GENERATE_LAKES;
    }

    public static boolean populationRunsAfterFinalChunkConstruction() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE)
            > steps.indexOf(CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION);
    }

    public static boolean populationRunsAfterVeinsMutation() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE)
            > steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION);
    }

    public static boolean isVeinsBranch(CaveniaPopulationBranch branch) {
        Objects.requireNonNull(branch, "branch");

        return false;
    }

    public static boolean isLakeBranch(CaveniaPopulationBranch branch) {
        return CaveniaPopulationBranch.WATER_LAKE.equals(Objects.requireNonNull(branch, "branch"))
            || CaveniaPopulationBranch.LAVA_LAKE.equals(branch);
    }

    public static boolean isFallBranch(CaveniaPopulationBranch branch) {
        return CaveniaPopulationBranch.WATER_FALL.equals(Objects.requireNonNull(branch, "branch"))
            || CaveniaPopulationBranch.LAVA_FALL.equals(branch);
    }

    public static boolean isShroomBranch(CaveniaPopulationBranch branch) {
        return CaveniaPopulationBranch.CAVENIC_SHROOM.equals(Objects.requireNonNull(branch, "branch"));
    }

    public static List<CaveniaPopulationStep> populationSteps() {
        return POPULATION_STEPS;
    }

    public static List<LakeRule> waterLakeRules() {
        return LAKE_RULES.stream()
            .filter(rule -> CaveniaPopulationBranch.WATER_LAKE.equals(rule.branch()))
            .toList();
    }

    public static List<LakeRule> lavaLakeRules() {
        return LAKE_RULES.stream()
            .filter(rule -> CaveniaPopulationBranch.LAVA_LAKE.equals(rule.branch()))
            .toList();
    }

    public static List<FallRule> fallRules() {
        return FALL_RULES;
    }

    public static List<ShroomRule> cavenicShroomRules() {
        return CAVENIC_SHROOM_RULES;
    }

    public record LakeRule(
        String id,
        CaveniaPopulationBranch branch,
        String generatorName,
        String biomeCondition,
        String excludedBiomeCondition,
        String eventType,
        String chanceExpression,
        String xzOffsetFormula,
        String yFormula
    ) {
        public LakeRule {
            id = Objects.requireNonNull(id, "id");
            branch = Objects.requireNonNull(branch, "branch");
            generatorName = Objects.requireNonNull(generatorName, "generatorName");
            biomeCondition = Objects.requireNonNull(biomeCondition, "biomeCondition");
            excludedBiomeCondition = Objects.requireNonNull(excludedBiomeCondition, "excludedBiomeCondition");
            eventType = Objects.requireNonNull(eventType, "eventType");
            chanceExpression = Objects.requireNonNull(chanceExpression, "chanceExpression");
            xzOffsetFormula = Objects.requireNonNull(xzOffsetFormula, "xzOffsetFormula");
            yFormula = Objects.requireNonNull(yFormula, "yFormula");
        }
    }

    public record FallRule(
        String id,
        CaveniaPopulationBranch branch,
        String generatorName,
        String biomeCondition,
        String excludedBiomeCondition,
        String eventType,
        int attempts,
        String xzOffsetFormula,
        String yFormula
    ) {
        public FallRule {
            id = Objects.requireNonNull(id, "id");
            branch = Objects.requireNonNull(branch, "branch");
            generatorName = Objects.requireNonNull(generatorName, "generatorName");
            biomeCondition = Objects.requireNonNull(biomeCondition, "biomeCondition");
            excludedBiomeCondition = Objects.requireNonNull(excludedBiomeCondition, "excludedBiomeCondition");
            eventType = Objects.requireNonNull(eventType, "eventType");
            xzOffsetFormula = Objects.requireNonNull(xzOffsetFormula, "xzOffsetFormula");
            yFormula = Objects.requireNonNull(yFormula, "yFormula");
        }
    }

    public record ShroomRule(
        String id,
        CaveniaPopulationBranch branch,
        String generatorName,
        String eventType,
        int baseBias,
        int mushroomBiomeBonus,
        int netherBiomeBonus,
        int randomBound,
        String successExpression,
        String placementOrigin
    ) {
        public ShroomRule {
            id = Objects.requireNonNull(id, "id");
            branch = Objects.requireNonNull(branch, "branch");
            generatorName = Objects.requireNonNull(generatorName, "generatorName");
            eventType = Objects.requireNonNull(eventType, "eventType");
            successExpression = Objects.requireNonNull(successExpression, "successExpression");
            placementOrigin = Objects.requireNonNull(placementOrigin, "placementOrigin");
        }
    }
}
