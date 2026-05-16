package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry;

class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderTest {
    private static final String DESIGNATED_BUILDER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java";
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry.java"
    );
    private static final Path BUILDER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_BUILDER_FILE_NAME
    );
    private static final Path CONVERTER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_CONVERTER_FILE_NAME
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
    void guardedBuilderPinsPreResolvedHolderListAggregationWithoutRuntimeWiring() throws IOException {
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.DESIGNATED_BUILDER_FILE,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.CANDIDATE_INVENTORY_INPUT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.LOOKUP_PROVIDER_PARAMETER,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.DESIGNATED_CONVERTER_USAGE,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.RESOLVED_HOLDER_COLLECTION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.RESOLVED_HOLDER_DEDUPLICATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.LEGACY_ORDER_PRESERVATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.MISSING_INVALID_UNRESOLVED_SKIP,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.FALLBACK_IF_EMPTY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.IMMUTABLE_OUTPUT_LIST,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.NO_COLLECT_POSSIBLE_BIOMES_WIRING,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent
                .REAL_RUNTIME_BIOME_SOURCE_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry> entries =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.entries();
        String builderSource = Files.readString(BUILDER_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        List<Path> runtimeBiomeSourceFiles;

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            runtimeBiomeSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
        }

        assertEquals(
            expectedComponents,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.components()
        );
        assertEquals(13, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.entryCount());
        assertTrue(entries.stream().allMatch(entry -> !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.builderBehavior().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry::builderReady));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBiomeSourceReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));

        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.builderReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.builderRuntimeBiomeSourceReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.buildCandidateHolderListReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateInventoryInputReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.lookupProviderParameterReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.designatedConverterUsageReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.resolvedHolderCollectionReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.resolvedHolderDeduplicationReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.legacyOrderPreservationReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.missingInvalidUnresolvedSkipReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.fallbackIfEmptyReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.immutableOutputListReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.collectPossibleBiomesWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.getNoiseBiomeWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.runtimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.canActivateCaveniaNow());
        assertEquals(
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.designatedBuilderSimpleName()
        );
        assertEquals(
            DESIGNATED_BUILDER_FILE_NAME,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.designatedBuilderFileName()
        );
        assertEquals(14, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateKeysStillStringOnlyOutsideBuilder());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateKeyToHolderConverterReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateKeyToHolderConverterRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.holderForCandidateKeyReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.holderForCandidateKeyOrFallbackReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.collectPossibleBiomesHolderSetReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.collectPossibleBiomesHolderSetReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.implementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.implementationGoNoGoRuntimeReady());
        assertEquals(
            "pre-resolved biome holder set/list",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.futureHolderSetOutputShape()
        );
        assertEquals(
            "future collectPossibleBiomes may stream only pre-resolved holder outputs after runtime construction is explicitly allowed",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.futureStreamReturnBoundary()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.fallbackCandidateModernBiomeKey()
        );
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.fallbackLegacyBiomeName());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.selectorToWeightedCandidateBridgeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.selectorToWeightedCandidateBridgeRuntimeReady());
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateKeyForSampleNegative()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.sampleKeysExistInInventory());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.futurePossibleBiomesSourceIsCandidateInventory());
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.futurePossibleBiomesCandidateEntryCount()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.futurePossibleBiomesFallbackIfEmptyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.cavemanRemainsDeferred());
        List<?> nullLookupList = CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder
            .buildCandidateHolderList((net.minecraft.core.HolderLookup.Provider) null);
        List<?> nullCandidateNullLookupList = CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder
            .buildCandidateHolderList(List.of("minecraft:taiga"), null);

        assertTrue(nullLookupList.isEmpty());
        assertTrue(nullCandidateNullLookupList.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> nullLookupList.add(null));
        assertThrows(UnsupportedOperationException.class, () -> nullCandidateNullLookupList.add(null));
        assertEquals(
            nullCandidateNullLookupList,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder
                .buildCandidateHolderList(List.of("minecraft:taiga"), null)
        );

        assertTrue(
            builderSource.contains(
                "public static List<Holder<Biome>> buildCandidateHolderList(HolderLookup.Provider lookupProvider)"
            )
        );
        assertTrue(
            builderSource.contains(
                "public static List<Holder<Biome>> buildCandidateHolderList(\n        List<String> candidateKeys,\n        HolderLookup.Provider lookupProvider\n    )"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .collectPossibleBiomesHolderSetBuilderReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String collectPossibleBiomesHolderSetBuilderFileName() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .nextSliceDesignatedBuilderFileName();\n    }"
            )
        );
        assertTrue(
            Files.readString(
                resolveProjectFile(
                    "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app",
                    "gametest", "CavernSpecialOreGameTests.java"
                )
            ).contains("CaveniaRuntimeBiomeSource.collectPossibleBiomesHolderSetBuilderReady()")
        );
        assertFalse(designatedSource.contains("buildCandidateHolderList("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder."));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));

        assertImmutableList(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.entries(), entries.getFirst());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.components(),
            expectedComponents.getFirst()
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateModernBiomeKeys(),
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.candidateModernBiomeKeys().getFirst()
        );
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.entries().add(null)
        );

        assertTrue(Files.exists(COMPONENT_SOURCE));
        assertTrue(Files.exists(ENTRY_SOURCE));
        assertTrue(Files.exists(BUILDER_SOURCE));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension").resolve("cavenia.json")));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension_type").resolve("cavenia.json")));
        assertSourceSafety(runtimeBiomeSourceFiles);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles) throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String builderSource = Files.readString(BUILDER_SOURCE);
        String converterSource = Files.readString(CONVERTER_SOURCE);

        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_BUILDER_FILE_NAME))
                .count()
        );
        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_CONVERTER_FILE_NAME))
                .count()
        );

        assertFalse(Files.readString(COMPONENT_SOURCE).contains("import net.minecraft"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("import net.minecraft"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("ResourceLocation"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("ResourceLocation"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("ResourceKey<Biome>"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("ResourceKey<Biome>"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("HolderLookup"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("HolderLookup"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("Registries.BIOME"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("Registries.BIOME"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("RegistryAccess"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("RegistryAccess"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("MapCodec"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("MapCodec"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("Codec<"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("Codec<"));
        assertFalse(Files.readString(COMPONENT_SOURCE).contains("RecordCodecBuilder"));
        assertFalse(Files.readString(ENTRY_SOURCE).contains("RecordCodecBuilder"));

        assertTrue(builderSource.contains("HolderLookup.Provider"));
        assertTrue(builderSource.contains("Holder<Biome>"));
        assertTrue(builderSource.contains("Biome"));
        assertFalse(builderSource.contains("ResourceLocation"));
        assertFalse(builderSource.contains("ResourceKey<Biome>"));
        assertFalse(builderSource.contains("Registries.BIOME"));

        assertTrue(converterSource.contains("HolderLookup"));
        assertTrue(converterSource.contains("Holder<Biome>"));
        assertTrue(converterSource.contains("Biome"));
        assertTrue(converterSource.contains("ResourceLocation"));
        assertTrue(converterSource.contains("ResourceKey<Biome>"));
        assertTrue(converterSource.contains("Registries.BIOME"));

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));

        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "HolderLookup",
            List.of(BUILDER_SOURCE, CONVERTER_SOURCE)
        );
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "Holder<Biome>",
            List.of(DESIGNATED_SOURCE, BUILDER_SOURCE, CONVERTER_SOURCE)
        );
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "import net.minecraft.world.level.biome.Biome;",
            List.of(DESIGNATED_SOURCE, BUILDER_SOURCE, CONVERTER_SOURCE)
        );
        assertOnlyFilesContain(runtimeBiomeSourceFiles, "ResourceLocation", List.of(CONVERTER_SOURCE));
        assertOnlyFilesContain(runtimeBiomeSourceFiles, "ResourceKey<Biome>", List.of(CONVERTER_SOURCE));
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "lookupProvider.lookup(Registries.BIOME)",
            List.of(CONVERTER_SOURCE)
        );

        assertNoMainSourceContains(runtimeBiomeSourceFiles, "Registry.register");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "BuiltInRegistries.BIOME_SOURCE.register");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "DeferredRegister");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "RecordCodecBuilder");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "public static final MapCodec");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "static final MapCodec");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "CODEC =");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "return getNoiseBiome");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "return collectPossibleBiomes");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "registerConfiguredFeature(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "registerPlacedFeature(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "registerConfiguredCarver(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "addFreshEntity(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "changeDimension(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, ".teleportTo(");

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaBiomeSource.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaChunkGenerator.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "ChunkGeneratorCavenia.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "MapGenCaveniaCaves.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaTeleporter.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaSpawnProvider.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaSpawnHandler.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaServerTickSpawner.java"
        );
    }

    private static void assertImmutableList(List<?> list, Object probe) {
        assertThrows(UnsupportedOperationException.class, () -> ((List<Object>) list).add(probe));
    }

    private static void assertOnlyFilesContain(List<Path> files, String text, List<Path> expected) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        assertEquals(
            expected.stream().map(path -> path.getFileName().toString()).toList(),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
        );
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        Optional<Path> matchingFile = files.stream()
            .filter(file -> read(file).contains(text))
            .findFirst();

        assertTrue(matchingFile.isEmpty(), () -> "Unexpected fragment in " + matchingFile.orElseThrow());
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)));
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read source file: " + path, exception);
        }
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
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
        for (int i = 0; i < 8 && current != null; i++) {
            Path candidate = current.resolve(first).resolve(Path.of("", more));
            if (Files.exists(candidate) || Files.exists(candidate.getParent())) {
                return candidate;
            }
            current = current.getParent();
        }
        throw new IllegalStateException("Could not resolve project path or sibling: " + Path.of(first, more));
    }
}
