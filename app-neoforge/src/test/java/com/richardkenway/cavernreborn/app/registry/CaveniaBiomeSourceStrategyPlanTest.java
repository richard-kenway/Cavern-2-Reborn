package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationSurface;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlan;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlanEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlanStep;

class CaveniaBiomeSourceStrategyPlanTest {
    private static final Path STEP_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSourceStrategyPlanStep.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSourceStrategyPlanEntry.java"
    );
    private static final Path PLAN_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSourceStrategyPlan.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void biomeSourceStrategyPlanPinsSelectedSurfaceLegacyInputsAndDeferredRuntimeWork() {
        List<CaveniaBiomeSourceStrategyPlanStep> expectedSteps = List.of(
            CaveniaBiomeSourceStrategyPlanStep.CONFIRM_SELECTED_SURFACE,
            CaveniaBiomeSourceStrategyPlanStep.PIN_LEGACY_WEIGHTED_INPUTS,
            CaveniaBiomeSourceStrategyPlanStep.PIN_LEGACY_TOP_BLOCK_INPUTS,
            CaveniaBiomeSourceStrategyPlanStep.INVENTORY_LEGACY_TO_MODERN_BIOME_MAPPING,
            CaveniaBiomeSourceStrategyPlanStep.DEFER_WEIGHTED_SELECTION_ALGORITHM,
            CaveniaBiomeSourceStrategyPlanStep.DEFER_RUNTIME_BIOME_SOURCE,
            CaveniaBiomeSourceStrategyPlanStep.DEFER_CODEC_AND_REGISTRATION,
            CaveniaBiomeSourceStrategyPlanStep.DEFER_REGISTRY_LOOKUP_ACCESS
        );
        List<CaveniaBiomeSourceStrategyPlanEntry> entries = CaveniaBiomeSourceStrategyPlan.entries();

        assertEquals(expectedSteps, CaveniaBiomeSourceStrategyPlan.steps());
        assertEquals(8, entries.size());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertEquals(CaveniaActivationSurface.BIOME_SOURCE_STRATEGY, CaveniaBiomeSourceStrategyPlan.selectedSurface());
        assertTrue(CaveniaBiomeSourceStrategyPlan.selectedSurfaceIsBiomeSourceStrategy());
        assertTrue(CaveniaBiomeSourceStrategyPlan.planIsNonRuntime());
        assertFalse(CaveniaBiomeSourceStrategyPlan.activationAllowedInThisSlice());
        assertTrue(CaveniaBiomeSourceStrategyPlan.allStepsRuntimeBlocked());
        assertFalse(CaveniaBiomeSourceStrategyPlan.anyStepRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.allStepsDisallowActivationInThisSlice());
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        assertTrue(
            CaveniaBiomeSourceStrategyPlan.entryFor(CaveniaBiomeSourceStrategyPlanStep.CONFIRM_SELECTED_SURFACE)
                .orElseThrow()
                .selectedSurfaceStep()
        );
        assertTrue(
            CaveniaBiomeSourceStrategyPlan.entryFor(CaveniaBiomeSourceStrategyPlanStep.PIN_LEGACY_WEIGHTED_INPUTS)
                .orElseThrow()
                .inputPinned()
        );
        assertTrue(
            CaveniaBiomeSourceStrategyPlan.entryFor(CaveniaBiomeSourceStrategyPlanStep.PIN_LEGACY_TOP_BLOCK_INPUTS)
                .orElseThrow()
                .inputPinned()
        );
        assertEquals(14, CaveniaBiomeSourceStrategyPlan.legacyBiomeEntryCount());
        assertEquals(675, CaveniaBiomeSourceStrategyPlan.legacyBiomeTotalWeight());
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyBiomeNames().contains("OCEAN"));
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyBiomeNames().contains("PLAINS"));
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyBiomeNames().contains("DESERT"));
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyBiomeNames().contains("MESA"));
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyWeightedInputsPinned());
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyTopBlocksPinned());
        assertFalse(CaveniaBiomeSourceStrategyPlan.modernBiomeMappingReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.legacyToModernBiomeMappingInventoryReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.legacyToModernBiomeFinalMappingReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.weightedSelectionAlgorithmReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.weightedSelectionAlgorithmInventoryReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.weightedSelectionAlgorithmFinalRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.unregisteredAdapterShapeReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.unregisteredAdapterRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.adapterCodecRegistrationReadinessReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.adapterCodecRegistrationRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.registryLookupReadinessReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.registryLookupRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.finalSelectedSurfaceReadinessMatrixReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.finalSelectedSurfaceRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceFirstImplementationDecisionReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceFirstImplementationRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.unregisteredRuntimeBiomeSourceSkeletonReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.unregisteredRuntimeBiomeSourceSkeletonRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceSubclassDecisionReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceSubclassRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceApiShapeInventoryReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceApiShapeRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.realSubclassGoNoGoDecisionReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.realSubclassGoNoGoRuntimeReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.runtimeBiomeSourceReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.codecRegistered());
        assertFalse(CaveniaBiomeSourceStrategyPlan.registryLookupAccessReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.dimensionJsonPresent());
        assertFalse(CaveniaBiomeSourceStrategyPlan.dimensionTypeJsonPresent());
        assertFalse(CaveniaBiomeSourceStrategyPlan.canActivateCaveniaNow());
        assertEquals(46, CaveniaBiomeSourceStrategyPlan.readinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaBiomeSourceStrategyPlan.readinessMatrixBlockedRequirementCount());
        assertTrue(CaveniaBiomeSourceStrategyPlan.cavemanRemainsDeferred());

        assertImmutableList(CaveniaBiomeSourceStrategyPlan.entries(), entries.get(0));
        assertImmutableList(CaveniaBiomeSourceStrategyPlan.steps(), CaveniaBiomeSourceStrategyPlanStep.CONFIRM_SELECTED_SURFACE);
        assertImmutableList(CaveniaBiomeSourceStrategyPlan.legacyBiomeNames(), "ICE_PLAINS");
    }

    @Test
    void biomeSourceStrategyPlanSourcesStayOutOfRuntimeBiomeSourcePaths() throws IOException {
        String stepSource = Files.readString(STEP_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String planSource = Files.readString(PLAN_SOURCE);

        assertSourceStaysInert(stepSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(planSource);

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the narrow non-runtime biome-source strategy plan to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected file to be absent: " + Path.of(first, more));
    }

    @SuppressWarnings("unchecked")
    private static void assertImmutableList(List<?> list, Object value) {
        List<Object> mutableView = (List<Object>) list;

        try {
            mutableView.add(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }

        throw new AssertionError("Expected list to be immutable");
    }

    private static Path resolveProjectPathOrSibling(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || Files.exists(candidate.getParent())) {
                return candidate;
            }
            current = current.getParent();
        }

        return Path.of(first, more).toAbsolutePath();
    }
}
