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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainNextDecision;

class CaveniaRuntimeBiomeSourceReadinessChainConsolidationTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceReadinessChainComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceReadinessChainEntry.java"
    );
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceReadinessChainNextDecision.java"
    );
    private static final Path CONSOLIDATION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceReadinessChainConsolidation.java"
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
    void readinessChainConsolidationPinsTheCurrentChainAndTheNextDecisionWithoutImplementingRuntimeBehavior() throws IOException {
        List<CaveniaRuntimeBiomeSourceReadinessChainComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.DESIGNATED_SUBCLASS_STUB,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.CODEC_METHOD_SHAPE_STUB,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.HOLDER_RESOURCE_KEY_CONVERSION_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.FALLBACK_POLICY_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.POSSIBLE_BIOMES_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.NOISE_BIOME_SELECTION_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.SELECTOR_INPUT_DERIVATION_NOT_PINNED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.REGISTRY_LOOKUP_ACCESS_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.HOLDER_RESOLUTION_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.RESOURCE_LOCATION_CONVERSION_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.RESOURCE_KEY_CONVERSION_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.USABLE_RUNTIME_METHODS_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.USABLE_CODEC_IMPLEMENTATION_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.CODEC_AND_TYPE_REGISTRATION_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.DIMENSION_ACTIVATION_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.WORLDGEN_ACCESS_SPAWNING_BLOCKED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.NEXT_DECISION_SELECTED
        );
        Set<CaveniaRuntimeBiomeSourceReadinessChainComponent> nextDecisionInputs = Set.of(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.CODEC_METHOD_SHAPE_STUB,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.HOLDER_RESOURCE_KEY_CONVERSION_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.FALLBACK_POLICY_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.POSSIBLE_BIOMES_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.NOISE_BIOME_SELECTION_READINESS,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.SELECTOR_INPUT_DERIVATION_NOT_PINNED,
            CaveniaRuntimeBiomeSourceReadinessChainComponent.NEXT_DECISION_SELECTED
        );
        List<CaveniaRuntimeBiomeSourceReadinessChainEntry> entries =
            CaveniaRuntimeBiomeSourceReadinessChainConsolidation.entries();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.components());
        assertEquals(17, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.consolidatedReadiness() != null && !entry.consolidatedReadiness().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.runtimeState() != null && !entry.runtimeState().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceReadinessChainEntry::blocksActivation));
        entries.forEach(entry -> assertEquals(nextDecisionInputs.contains(entry.component()), entry.nextDecisionInput()));

        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.consolidatedReadinessLayerCount());
        assertEquals(
            CaveniaRuntimeBiomeSourceReadinessChainNextDecision.PROCEED_WITH_SELECTOR_INPUT_DERIVATION_GO_NO_GO_NEXT,
            CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectedNextDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationGoNoGoIsNext());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationReadinessRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationReadinessPinned());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddSelectorInputDerivationGoNoGoDecision());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayImplementSelectorInputDerivation());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayUseCoordinatesForRuntimeSelection());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayUseClimateSamplerForRuntimeSelection());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayMakeGetNoiseBiomeUsable());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayMakeCollectPossibleBiomesUsable());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayResolveHolders());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayImplementResourceLocationConversion());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayImplementResourceKeyConversion());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddFallbackImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddUsableCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.nextSliceMayRegisterCavemanEntity());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.designatedSubclassRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.codecMethodShapeStubReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.holderConversionReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.fallbackPolicyReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.possibleBiomesReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.codecMethodShapeRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.fallbackPolicyRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.possibleBiomesRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.noiseBiomeSelectionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.resourceKeyConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.codecMethodStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.weightedSelectionAlgorithmReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.adapterShapeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceReadinessChainConsolidation.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceReadinessChainConsolidation.fallbackCandidateModernBiomeKey());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.dimensionBindingReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.canActivateCaveniaNow());
        assertEquals(68, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceReadinessChainConsolidation.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean readinessChainConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean readinessChainRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationGoNoGoIsNext();\n    }"
            )
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceReadinessChainConsolidation.components(),
            CaveniaRuntimeBiomeSourceReadinessChainComponent.DESIGNATED_SUBCLASS_STUB
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void readinessChainConsolidationSourcesKeepRuntimeApiUsageAndActivationBlocked() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String nextDecisionSource = Files.readString(NEXT_DECISION_SOURCE);
        String consolidationSource = Files.readString(CONSOLIDATION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertSourceStaysInert(componentSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(nextDecisionSource);
        assertSourceStaysInert(consolidationSource);

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
                "Expected the readiness-chain consolidation slice to keep active Cavenia resources absent"
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
