package com.richardkenway.cavernreborn.core.worldgen;

import java.util.List;
import java.util.Objects;

public final class CaveniaTerrainGeneratorPolicy {
    public static final int WORLD_HEIGHT = 128;
    public static final int MIN_Y = 0;
    public static final int BEDROCK_FLOOR_Y = 0;
    public static final int BEDROCK_CEILING_Y = 127;
    public static final int AIR_CLEAR_FROM_Y = 128;
    public static final int AIR_CLEAR_TO_Y = 255;
    public static final String BASE_TERRAIN_BLOCK_ID = "minecraft:stone";
    public static final String BEDROCK_BLOCK_ID = "minecraft:bedrock";
    public static final String AIR_BLOCK_ID = "minecraft:air";

    private static final List<CaveniaTerrainStep> GENERATION_STEPS = List.of(
        CaveniaTerrainStep.BASE_STONE_FILL,
        CaveniaTerrainStep.OPTIONAL_CAVE_CARVING,
        CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT,
        CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION,
        CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION,
        CaveniaTerrainStep.LATER_POPULATION_STAGE
    );

    private CaveniaTerrainGeneratorPolicy() {
    }

    public static boolean isInsideLegacyWorldHeight(int y) {
        return y >= MIN_Y && y < AIR_CLEAR_FROM_Y;
    }

    public static boolean isBedrockY(int y) {
        return y == BEDROCK_FLOOR_Y || y == BEDROCK_CEILING_Y;
    }

    public static boolean isAboveLegacyWorldHeight(int y) {
        return y >= AIR_CLEAR_FROM_Y;
    }

    public static boolean shouldClearToAir(int y) {
        return y >= AIR_CLEAR_FROM_Y && y <= AIR_CLEAR_TO_Y;
    }

    public static List<CaveniaTerrainStep> generationSteps() {
        return GENERATION_STEPS;
    }

    public static boolean isPopulationStep(CaveniaTerrainStep step) {
        return CaveniaTerrainStep.LATER_POPULATION_STAGE.equals(Objects.requireNonNull(step, "step"));
    }
}
