package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness;

class CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.java"
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
    void noiseBiomeSelectionReadinessPinsTheFutureSelectionPathWithoutImplementingIt() throws IOException {
        List<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NOISE_BIOME_METHOD_SHAPE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.COORDINATE_INPUT_SHAPE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.CLIMATE_SAMPLER_INPUT_SHAPE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.WEIGHT_VALUE_DERIVATION_DECISION,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.WEIGHTED_SELECTION_SOURCE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.ADAPTER_SELECTION_SOURCE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.CANDIDATE_KEY_OUTPUT_SOURCE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.HOLDER_CONVERSION_DEPENDENCY,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.FALLBACK_POLICY_SOURCE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.POSSIBLE_BIOMES_CONSTRAINT,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_RUNTIME_HOLDER_RETURN,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.REAL_NOISE_BIOME_SELECTION_STILL_DEFERRED
        );
        Set<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent> registryLookupRequiredComponents = Set.of(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.HOLDER_CONVERSION_DEPENDENCY,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.FALLBACK_POLICY_SOURCE,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.POSSIBLE_BIOMES_CONSTRAINT,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_RUNTIME_HOLDER_RETURN,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.REAL_NOISE_BIOME_SELECTION_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry> entries =
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.entries();
        List<String> candidateKeys = CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.candidateModernBiomeKeys();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.components());
        assertEquals(14, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.readinessDecision() != null && !entry.readinessDecision().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.expectedFutureBehavior() != null && !entry.expectedFutureBehavior().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.implementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryLookupAvailable()));
        assertTrue(entries.stream().allMatch(entry -> !entry.noiseBiomeRuntimeReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        entries.forEach(entry -> assertEquals(
            registryLookupRequiredComponents.contains(entry.component()),
            entry.registryLookupRequired()
        ));

        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.entryFor(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NOISE_BIOME_METHOD_SHAPE
        ).orElseThrow().readinessDecision().contains("getNoiseBiome(int x, int y, int z, Climate.Sampler sampler)"));
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.entryFor(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.CLIMATE_SAMPLER_INPUT_SHAPE
        ).orElseThrow().readinessDecision().contains("Climate.Sampler"));
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.entryFor(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_RUNTIME_HOLDER_RETURN
        ).orElseThrow().readinessDecision().contains("Holder<Biome>"));

        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.coordinateInputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.climateSamplerInputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightValueDerivationDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightedSelectionSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.adapterSelectionSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.candidateKeyOutputSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.holderConversionDependencyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.fallbackPolicySourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.possibleBiomesConstraintPinned());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.getNoiseBiomeImplementationReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.registryLookupRequiredForRuntimeNoiseBiome());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.realNoiseBiomeSelectionStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.canActivateCaveniaNow());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputDerivationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputDerivationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputDerivationReadinessRuntimeReady());
        assertEquals("getNoiseBiome", CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodName());
        assertEquals(
            "getNoiseBiome(int x, int y, int z, Climate.Sampler sampler)",
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodSignature()
        );
        assertEquals(
            "future x/y/z coordinate inputs are pinned but not used for runtime selection in this slice",
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.coordinateInputPolicy()
        );
        assertEquals(
            "future Climate.Sampler input is pinned but not used for runtime selection in this slice",
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.climateSamplerInputPolicy()
        );
        assertEquals(
            "future deterministic coordinate/climate-to-weight-value derivation remains deferred",
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightValueDerivationPolicy()
        );
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightedSelectionAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightedSelectionRuntimeReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.adapterShapeReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.candidateKeysStillStringOnly());
        assertTrue(candidateKeys.contains("minecraft:plains"));
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.resourceKeyConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.resourceLocationConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.fallbackPolicyRuntimeReady());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.possibleBiomesRuntimeReady());
        assertEquals(
            "future noise-biome results should resolve to candidates compatible with future possible-biomes holder outputs",
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.possibleBiomesConstraintPolicy()
        );
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.codecMethodShapeStubReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.codecMethodStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.goNoGoGuardrailsEnforced());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.apiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean noiseBiomeSelectionReadinessReady() {\n        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean noiseBiomeSelectionRuntimeReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean getNoiseBiomeRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean noiseBiomeMethodShapePinned() {\n        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodShapePinned();\n    }"
            )
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.components(),
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NOISE_BIOME_METHOD_SHAPE
        );
        assertImmutableList(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.candidateModernBiomeKeys(), "minecraft:plains");
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void noiseBiomeSelectionReadinessSourcesKeepRuntimeApiUsageAndNoiseBiomeImplementationBlocked() throws IOException {
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
                "Expected the noise-biome readiness slice to keep active Cavenia resources absent"
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
