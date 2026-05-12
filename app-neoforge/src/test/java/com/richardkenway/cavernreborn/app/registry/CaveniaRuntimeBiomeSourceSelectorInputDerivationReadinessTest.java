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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness;

class CaveniaRuntimeBiomeSourceSelectorInputDerivationReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.java"
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
    void selectorInputDerivationReadinessPinsTheFuturePolicySurfaceWithoutImplementingTheFormula() throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.METHOD_INPUT_SHAPE,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.COORDINATE_INPUT_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.CLIMATE_SAMPLER_INPUT_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.SELECTOR_INPUT_OUTPUT_SHAPE,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.DETERMINISTIC_DERIVATION_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.PURE_NON_RUNTIME_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.WEIGHTED_SELECTOR_COMPATIBILITY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.ADAPTER_QUERY_COMPATIBILITY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NORMALIZATION_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NEGATIVE_COORDINATE_STABILITY_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.CHUNK_BOUNDARY_STABILITY_POLICY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_WORLD_OR_REGISTRY_DEPENDENCY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_RANDOM_OR_MUTABLE_STATE_DEPENDENCY,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_RUNTIME_SELECTION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.REAL_SELECTOR_INPUT_DERIVATION_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry> entries =
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.entries();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.components());
        assertEquals(16, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.inputSurface() != null && !entry.inputSurface().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.futurePolicy() != null && !entry.futurePolicy().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.implementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeSelectionReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeApiUsageAllowedInThisSlice()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.methodInputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.coordinateInputPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.climateSamplerInputPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputOutputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.deterministicDerivationPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.pureNonRuntimePolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.weightedSelectorCompatibilityPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.adapterQueryCompatibilityPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.normalizationPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.negativeCoordinateStabilityPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.chunkBoundaryStabilityPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.noWorldOrRegistryDependencyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.noRandomOrMutableStateDependencyPinned());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.coordinateRuntimeSelectionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.climateSamplerRuntimeSelectionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.realSelectorInputDerivationStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.canActivateCaveniaNow());

        assertEquals(
            "getNoiseBiome(int x, int y, int z, Climate.Sampler sampler)",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.methodInputSource()
        );
        assertEquals(
            "future x/y/z coordinate inputs are pinned as selector-input sources but not used for runtime selection in this slice",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.coordinateInputPolicy()
        );
        assertEquals(
            "future Climate.Sampler input is pinned as a selector-input source but no sampler methods are called in this slice",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.climateSamplerInputPolicy()
        );
        assertEquals(
            "signed int weight value accepted by the existing adapter and weighted selector path",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputOutputShape()
        );
        assertEquals(
            "same selector method inputs must produce the same selector input once implemented",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.deterministicDerivationPolicy()
        );
        assertEquals(
            "future derivation must be a pure non-runtime calculation before holder conversion",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.pureNonRuntimePolicy()
        );
        assertEquals(
            "normalization and wrapping remain delegated to the existing weighted selector or adapter path",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.normalizationPolicy()
        );
        assertEquals(
            "future derivation must handle negative coordinates deterministically",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.negativeCoordinateStabilityPolicy()
        );
        assertEquals(
            "future derivation must not depend on mutable chunk or server state",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.chunkBoundaryStabilityPolicy()
        );
        assertEquals(
            "future derivation must not require registry lookup, holder resolution, resource keys, worldgen resources, or server level access",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.worldOrRegistryDependencyPolicy()
        );
        assertEquals(
            "future derivation must not use random, wall-clock, mutable global state, or side effects",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.randomOrMutableStatePolicy()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.actualDerivationFormulaPinned());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.actualDerivationFormulaImplemented());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.goNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.goNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.noiseBiomeMethodShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.weightValueDerivationDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.weightedSelectionAlgorithmReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.adapterShapeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationReadinessReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationImplementationReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static String selectorInputDerivationOutputShape() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputOutputShape();\n    }"
            )
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.components(),
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.METHOD_INPUT_SHAPE
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void selectorInputDerivationReadinessSourcesKeepRuntimeApiUsageAndFormulaImplementationBlocked() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String readinessSource = Files.readString(READINESS_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertSourceStaysInert(componentSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(readinessSource);

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
        assertFalse(designatedSource.contains("random.next"));
        assertFalse(designatedSource.contains("new Random"));
        assertFalse(designatedSource.contains("ThreadLocalRandom"));
        assertFalse(designatedSource.contains("System.currentTimeMillis"));
        assertFalse(designatedSource.contains("System.nanoTime"));

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
            assertNoMainSourceContains(regularFiles, "random.next");
            assertNoMainSourceContains(regularFiles, "new Random");
            assertNoMainSourceContains(regularFiles, "ThreadLocalRandom");
            assertNoMainSourceContains(regularFiles, "System.currentTimeMillis");
            assertNoMainSourceContains(regularFiles, "System.nanoTime");
            assertNoMainSourceContains(regularFiles, "registerConfiguredFeature(");
            assertNoMainSourceContains(regularFiles, "registerPlacedFeature(");
            assertNoMainSourceContains(regularFiles, "registerConfiguredCarver(");
            assertNoMainSourceContains(regularFiles, "addFreshEntity(");
            assertNoMainSourceContains(regularFiles, "changeDimension(");
            assertNoMainSourceContains(regularFiles, ".teleportTo(");
        }

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the selector-input derivation readiness slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("Climate.Sampler"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("random.next"));
        assertFalse(source.contains("new Random"));
        assertFalse(source.contains("ThreadLocalRandom"));
        assertFalse(source.contains("System.currentTimeMillis"));
        assertFalse(source.contains("System.nanoTime"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
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
