package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlan;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceApiShapeInventory;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeStub;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;

class CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry.java"
    );
    private static final Path ALGORITHM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.java"
    );
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path APP_WORLDGEN_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void selectorInputDerivationAlgorithmImplementsPureDeterministicCoordinateMixingWithoutRuntimeWiring()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.INTEGER_COORDINATE_INPUTS,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.CLIMATE_SAMPLER_EXCLUDED,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.PURE_NON_RUNTIME_ALGORITHM,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.DETERMINISTIC_MIXING_FORMULA,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.SIGNED_INT_SELECTOR_OUTPUT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.NORMALIZATION_DELEGATED,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.NEGATIVE_COORDINATE_STABILITY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.CHUNK_BOUNDARY_STABILITY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.NO_WORLD_OR_REGISTRY_DEPENDENCY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.NO_RANDOM_OR_MUTABLE_STATE,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.REAL_RUNTIME_SELECTION_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry> entries =
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.entries();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.components());
        assertEquals(12, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.algorithmDecision() != null && !entry.algorithmDecision().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.runtimeBoundary() != null && !entry.runtimeBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry::algorithmReady));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.minecraftRuntimeApiRequired()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.pureNonRuntimeAlgorithmReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.integerCoordinateInputsReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.climateSamplerExcluded());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.climateSamplerMethodsCalled());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.minecraftRuntimeApiImportsRequired());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deterministicMixingFormulaReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.signedIntSelectorOutputReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.normalizationDelegated());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.negativeCoordinateStable());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.chunkBoundaryStable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.worldOrRegistryDependencyPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.randomOrMutableStateDependencyPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.getNoiseBiomeWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.runtimeSelectionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.realRuntimeSelectionStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.canActivateCaveniaNow());

        assertEquals(0x9E3779B97F4A7C15L, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.initialSeed());
        assertEquals(0xBF58476D1CE4E5B9L, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.xMultiplier());
        assertEquals(0x94D049BB133111EBL, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.yMultiplier());
        assertEquals(0xD6E8FEB86659FD93L, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.zMultiplier());

        assertEquals(-104499101, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, 0, 0));
        assertEquals(-1057403244, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 0, 0));
        assertEquals(-1906836072, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, 1, 0));
        assertEquals(1179339926, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, 0, 1));
        assertEquals(700186390, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3));
        assertEquals(-1698937329, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(-1, 0, 0));
        assertEquals(1171624622, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, -1, 0));
        assertEquals(38125441, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, 0, -1));
        assertEquals(-1039724642, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(-16, 64, 16));
        assertEquals(1320310950, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(12345, -67, 89012));
        assertEquals(
            602747642,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(Integer.MAX_VALUE, Integer.MIN_VALUE, 0)
        );

        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3),
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3)
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3)
                != CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(2, 2, 3)
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3)
                != CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 3, 3)
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3)
                != CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 4)
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, 0, 0) < 0);
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3) >= 0
                && CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3) < 675
        );

        assertEquals("signed int selector input", CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputOutputShape());
        assertEquals(
            "normalization and wrapping remain delegated to the existing selector or adapter path",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.normalizationPolicy()
        );
        assertEquals("integer x/y/z coordinates only", CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.algorithmInputPolicy());
        assertEquals("not wired into getNoiseBiome", CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.algorithmRuntimeBoundary());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.implementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.implementationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.weightedSelectionAlgorithmReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.adapterShapeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.cavemanRemainsDeferred());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.selectorInputDerivationAlgorithmRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.selectorInputDerivationAlgorithmRuntimeReady());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationAlgorithmReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationAlgorithmRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static String selectorInputDerivationAlgorithmOutputShape() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputOutputShape();\n    }"
            )
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.components(),
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent.INTEGER_COORDINATE_INPUTS
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void selectorInputDerivationAlgorithmSourcesStayPureAndKeepRuntimeActivationBlocked() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String algorithmSource = Files.readString(ALGORITHM_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertPureAlgorithmSource(componentSource);
        assertPureAlgorithmSource(entrySource);
        assertPureAlgorithmSource(algorithmSource);

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("ResourceLocation"));
        assertFalse(designatedSource.contains("ResourceKey<Biome>"));
        assertFalse(designatedSource.contains("RegistryLookup<"));
        assertFalse(designatedSource.contains("RegistryAccess"));
        assertFalse(designatedSource.contains("Registry.register"));
        assertFalse(designatedSource.contains("BuiltInRegistries.BIOME_SOURCE.register"));
        assertFalse(designatedSource.contains("DeferredRegister"));
        assertFalse(designatedSource.contains("RecordCodecBuilder"));
        assertFalse(designatedSource.contains("public static final MapCodec"));
        assertFalse(designatedSource.contains("static final MapCodec"));
        assertFalse(designatedSource.contains("CODEC ="));
        assertFalse(designatedSource.contains("return holder"));
        assertFalse(designatedSource.contains("return fallback"));
        assertFalse(designatedSource.contains("return getNoiseBiome"));
        assertFalse(designatedSource.contains("return collectPossibleBiomes"));
        assertFalse(designatedSource.contains("return Stream.of"));
        assertFalse(designatedSource.contains(".map(Holder"));
        assertFalse(designatedSource.contains(".collect(Collectors"));

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            List<Path> regularFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();

            List<Path> designatedFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .toList();

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "class CaveniaRuntimeBiomeSource extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(regularFiles, "protected MapCodec<? extends BiomeSource> codec()");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");

            List<Path> runtimeBiomeSourceFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();

            assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceLocation");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryLookup<");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryAccess");
            assertNoMainSourceContains(regularFiles, "Registry.register");
            assertNoMainSourceContains(regularFiles, "BuiltInRegistries.BIOME_SOURCE.register");
            assertNoMainSourceContains(regularFiles, "DeferredRegister");
            assertNoMainSourceContains(regularFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(regularFiles, "public static final MapCodec");
            assertNoMainSourceContains(regularFiles, "static final MapCodec");
            assertNoMainSourceContains(regularFiles, "CODEC =");
            assertNoMainSourceContains(regularFiles, "return holder");
            assertNoMainSourceContains(regularFiles, "return fallback");
            assertNoMainSourceContains(regularFiles, "return getNoiseBiome");
            assertNoMainSourceContains(regularFiles, "return collectPossibleBiomes");
            assertNoMainSourceContains(regularFiles, "return Stream.of");
            assertNoMainSourceContains(regularFiles, ".map(Holder");
            assertNoMainSourceContains(regularFiles, ".collect(Collectors");
            assertNoMainSourceContains(regularFiles, "registerConfiguredFeature(");
            assertNoMainSourceContains(regularFiles, "registerPlacedFeature(");
            assertNoMainSourceContains(regularFiles, "registerConfiguredCarver(");
            assertNoMainSourceContains(regularFiles, "addFreshEntity(");
            assertNoMainSourceContains(regularFiles, "changeDimension(");
            assertNoMainSourceContains(regularFiles, ".teleportTo(");
        }

        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "is_cavenia.json");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the pure selector-input derivation algorithm slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertPureAlgorithmSource(String source) {
        assertFalse(source.contains("import net.minecraft"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("Climate"));
        assertFalse(source.contains("Climate.Sampler"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Random"));
        assertFalse(source.contains("ThreadLocalRandom"));
        assertFalse(source.contains("System.currentTimeMillis"));
        assertFalse(source.contains("System.nanoTime"));
    }

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
    }

    private static void assertOnlyDesignatedFileContains(List<Path> sourceFiles, String fragment) throws IOException {
        List<Path> matchingFiles = sourceFiles.stream()
            .filter(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            })
            .toList();

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles, "Expected only the designated subclass file to contain: " + fragment);
    }

    private static void assertNoMainSourceContains(List<Path> sourceFiles, String fragment) throws IOException {
        assertTrue(
            sourceFiles.stream().noneMatch(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            }),
            "Expected no main-source file to contain: " + fragment
        );
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected missing project file: " + Path.of(first, more));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(first).resolve(Path.of("", more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
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
