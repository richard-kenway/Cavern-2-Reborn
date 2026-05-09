package com.richardkenway.cavernreborn.core.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CaveniaGeneratorScaffoldTest {
    @Test
    void scaffoldPinsTheFutureCaveniaIdentityAndWorldHeight() {
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorScaffold.dimensionId());
        assertEquals(128, CaveniaGeneratorScaffold.worldHeight());
    }

    @Test
    void scaffoldReusesTheTerrainPolicyOrder() {
        assertEquals(CaveniaTerrainGeneratorPolicy.generationSteps(), CaveniaGeneratorScaffold.terrainStages());
        assertTrue(CaveniaGeneratorScaffold.caveCarvingRunsBeforeBiomeTopFilterReplacement());
        assertTrue(CaveniaVeinsContentPolicy.runsAfterCaveCarving());
        assertTrue(CaveniaVeinsContentPolicy.runsAfterBiomeTopFilterReplacement());
        assertTrue(CaveniaGeneratorScaffold.populationRunsAfterVeinsMutation());
        assertTrue(CaveniaPopulationPolicy.populationRunsAfterFinalChunkConstruction());
    }

    @Test
    void scaffoldPinsTheExistingBiomeVeinsAndPopulationInputs() {
        assertEquals(14, CaveniaGeneratorScaffold.biomeEntryCount());
        assertEquals(675, CaveniaGeneratorScaffold.biomeTotalWeight());
        assertEquals(13, CaveniaGeneratorScaffold.veinEntryCount());
        assertEquals(436, CaveniaGeneratorScaffold.veinTotalWeight());
        assertTrue(CaveniaGeneratorScaffold.generateLakesDefaultEnabled());
    }

    @Test
    void scaffoldRemainsExplicitlyInactiveUntilRuntimeResourcesExist() {
        assertFalse(CaveniaGeneratorScaffold.isRuntimeGeneratorRegistered());
        assertTrue(CaveniaGeneratorScaffold.requiresDimensionJsonBeforeActivation());
        assertTrue(CaveniaGeneratorScaffold.requiresDimensionTypeJsonBeforeActivation());
    }

    @Test
    void scaffoldStageOrderRemainsPinned() {
        assertEquals(
            List.of(
                CaveniaGeneratorScaffoldStage.IDENTITY,
                CaveniaGeneratorScaffoldStage.BASE_TERRAIN,
                CaveniaGeneratorScaffoldStage.CAVE_CARVING,
                CaveniaGeneratorScaffoldStage.BIOME_TOP_FILTER_REPLACEMENT,
                CaveniaGeneratorScaffoldStage.VEINS_MUTATION,
                CaveniaGeneratorScaffoldStage.FINAL_CHUNK_CONSTRUCTION,
                CaveniaGeneratorScaffoldStage.POPULATION_INTEGRATION,
                CaveniaGeneratorScaffoldStage.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
                CaveniaGeneratorScaffoldStage.ENTRY_ACCESS_DEFERRED
            ),
            CaveniaGeneratorScaffold.stages()
        );
    }

    @Test
    void returnedStageListRemainsSafelyUnmodifiable() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaGeneratorScaffold.stages().add(CaveniaGeneratorScaffoldStage.IDENTITY)
        );
    }
}
