package com.richardkenway.cavernreborn.core.worldgen;

import java.util.List;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

public final class CaveniaGeneratorScaffold {
    private static final List<CaveniaGeneratorScaffoldStage> STAGES = List.of(
        CaveniaGeneratorScaffoldStage.IDENTITY,
        CaveniaGeneratorScaffoldStage.BASE_TERRAIN,
        CaveniaGeneratorScaffoldStage.CAVE_CARVING,
        CaveniaGeneratorScaffoldStage.BIOME_TOP_FILTER_REPLACEMENT,
        CaveniaGeneratorScaffoldStage.VEINS_MUTATION,
        CaveniaGeneratorScaffoldStage.FINAL_CHUNK_CONSTRUCTION,
        CaveniaGeneratorScaffoldStage.POPULATION_INTEGRATION,
        CaveniaGeneratorScaffoldStage.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
        CaveniaGeneratorScaffoldStage.ENTRY_ACCESS_DEFERRED
    );

    private CaveniaGeneratorScaffold() {
    }

    public static String dimensionId() {
        return CavernDimensions.CAVENIA_DIMENSION_ID;
    }

    public static int worldHeight() {
        return CaveniaTerrainGeneratorPolicy.WORLD_HEIGHT;
    }

    public static List<CaveniaTerrainStep> terrainStages() {
        return CaveniaTerrainGeneratorPolicy.generationSteps();
    }

    public static int biomeEntryCount() {
        return CaveniaBiomeTopFilterPolicy.entries().size();
    }

    public static int biomeTotalWeight() {
        return CaveniaBiomeTopFilterPolicy.totalWeight();
    }

    public static int veinEntryCount() {
        return CaveniaVeinsContentPolicy.entries().size();
    }

    public static int veinTotalWeight() {
        return CaveniaVeinsContentPolicy.totalWeight();
    }

    public static boolean generateLakesDefaultEnabled() {
        return CaveniaPopulationPolicy.generateLakesDefaultEnabled();
    }

    public static boolean populationRunsAfterVeinsMutation() {
        return CaveniaPopulationPolicy.populationRunsAfterVeinsMutation();
    }

    public static boolean caveCarvingRunsBeforeBiomeTopFilterReplacement() {
        return CaveniaCaveCarverPolicy.runsBeforeBiomeTopFilterReplacement();
    }

    public static boolean isRuntimeGeneratorRegistered() {
        return false;
    }

    public static boolean requiresDimensionJsonBeforeActivation() {
        return true;
    }

    public static boolean requiresDimensionTypeJsonBeforeActivation() {
        return true;
    }

    public static List<CaveniaGeneratorScaffoldStage> stages() {
        return STAGES;
    }
}
