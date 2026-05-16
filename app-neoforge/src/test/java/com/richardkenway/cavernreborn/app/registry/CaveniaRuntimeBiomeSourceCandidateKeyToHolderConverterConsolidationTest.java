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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationTest {
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision.java"
    );
    private static final Path CONSOLIDATION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.java"
    );
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path CONVERTER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_CONVERTER_FILE_NAME
    );
    private static final Path APP_WORLDGEN_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );

    @Test
    void consolidationPinsConverterStateAndNextDecisionWithoutOpeningRuntimeBehavior() throws IOException {
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.DESIGNATED_CONVERTER_EXISTS,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.CANDIDATE_KEY_TO_HOLDER_CONVERTER_READY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.STRING_CANDIDATE_KEY_INPUT_READY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.RESOURCE_LOCATION_PARSE_CONFINED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.RESOURCE_KEY_CONSTRUCTION_CONFINED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.BIOME_REGISTRY_LOOKUP_CONFINED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.HOLDER_RESOLUTION_CONFINED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.OPTIONAL_HOLDER_RESULT_READY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.FALLBACK_HOLDER_ATTEMPT_READY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.SERVER_GLOBAL_ACCESS_ABSENT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.GET_NOISE_BIOME_WIRING_BLOCKED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.COLLECT_POSSIBLE_BIOMES_WIRING_BLOCKED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.CODEC_AND_REGISTRATION_BLOCKED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.DIMENSION_WORLDGEN_ACCESS_SPAWNING_BLOCKED,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.NEXT_DECISION_SELECTED
        );
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry> entries =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.entries();
        List<Path> runtimeBiomeSourceFiles;
        List<Path> consolidationSources = List.of(COMPONENT_SOURCE, ENTRY_SOURCE, DECISION_SOURCE, CONSOLIDATION_SOURCE);

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            runtimeBiomeSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
        }

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.components());
        assertEquals(15, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.entryCount());
        assertTrue(entries.stream().allMatch(entry -> !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.consolidatedState().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(
            entries.stream()
                .filter(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry::nextDecisionInput)
                .map(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry::component)
                .toList()
                .equals(List.of(
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .DESIGNATED_CONVERTER_EXISTS,
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .CANDIDATE_KEY_TO_HOLDER_CONVERTER_READY,
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .OPTIONAL_HOLDER_RESULT_READY,
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .FALLBACK_HOLDER_ATTEMPT_READY,
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .GET_NOISE_BIOME_WIRING_BLOCKED,
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .COLLECT_POSSIBLE_BIOMES_WIRING_BLOCKED,
                    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent
                        .NEXT_DECISION_SELECTED
                ))
        );

        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyToHolderConverterConsolidationReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.designatedConverterExists());
        assertEquals(
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.designatedConverterSimpleName()
        );
        assertEquals(
            DESIGNATED_CONVERTER_FILE_NAME,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.designatedConverterFileName()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyToHolderConverterReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyToHolderConverterRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.holderForCandidateKeyReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.holderForCandidateKeyOrFallbackReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.stringCandidateKeyInputReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.resourceLocationParseConfinedToConverter());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.resourceKeyConstructionConfinedToConverter());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.biomeRegistryLookupConfinedToConverter());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.holderResolutionConfinedToConverter());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.optionalHolderResultReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.fallbackHolderAttemptReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.serverGlobalAccessAbsent());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.getNoiseBiomeWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.runtimeBiomeSourceReady());
        assertEquals(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision
                .PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_GO_NO_GO_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.selectedNextDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesHolderSetGoNoGoIsNext());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesHolderSetReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesHolderSetImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesHolderSetRuntimeReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
                .collectPossibleBiomesHolderSetGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
                .collectPossibleBiomesHolderSetGoNoGoRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
                .collectPossibleBiomesHolderSetReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddCollectPossibleBiomesHolderSetGoNoGoDecision());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddCollectPossibleBiomesHolderSetReadiness());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayImplementCollectPossibleBiomes());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayWireConverterIntoCollectPossibleBiomes());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayWireConverterIntoGetNoiseBiome());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayMakeGetNoiseBiomeUsable());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayMakeCollectPossibleBiomesUsable());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddUsableCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayRegisterGenerator());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.nextSliceMayRegisterCavemanEntity());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyToHolderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyToHolderConversionReadinessRuntimeReady());
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyInputShape()
        );
        assertEquals(14, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeysStillStringOnlyOutsideConverter());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.selectorToWeightedCandidateBridgeReady());
        assertEquals("minecraft:taiga", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyForSampleOrigin());
        assertEquals("minecraft:desert", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyForSampleMixed());
        assertEquals("minecraft:desert", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.candidateKeyForSampleNegative());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.sampleKeysExistInInventory());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.implementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.implementationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.codecMethodShapeRuntimeReady());
        String consolidationSource = Files.readString(CONSOLIDATION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        assertTrue(
            consolidationSource.contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertTrue(
            consolidationSource.contains(
                "public static boolean designatedSubclassRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.dimensionBindingReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.canActivateCaveniaNow());
        assertEquals(68, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.globalReadinessMatrixBlockedRequirementCount());
        assertTrue(
            consolidationSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            consolidationSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            consolidationSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation\n            .candidateKeyToHolderConverterConsolidationReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation\n            .collectPossibleBiomesHolderSetGoNoGoIsNext();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.entries(),
            entries.getFirst()
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.components(),
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.DESIGNATED_CONVERTER_EXISTS
        );

        assertTrue(Files.exists(COMPONENT_SOURCE));
        assertTrue(Files.exists(ENTRY_SOURCE));
        assertTrue(Files.exists(DECISION_SOURCE));
        assertTrue(Files.exists(CONSOLIDATION_SOURCE));
        assertSourceSafety(runtimeBiomeSourceFiles, consolidationSources);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles, List<Path> consolidationSources)
        throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String converterSource = Files.readString(CONVERTER_SOURCE);

        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_CONVERTER_FILE_NAME))
                .count()
        );
        assertTrue(converterSource.contains("ResourceLocation"));
        assertTrue(converterSource.contains("ResourceKey<Biome>"));
        assertTrue(converterSource.contains("HolderLookup"));
        assertTrue(converterSource.contains("ResourceKey.create(Registries.BIOME, candidateLocation)"));
        assertTrue(converterSource.contains("lookupProvider.lookup(Registries.BIOME)"));
        assertTrue(converterSource.contains("Holder<Biome>"));
        assertTrue(converterSource.contains("Biome"));
        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));

        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("import net.minecraft")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("ResourceLocation")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("ResourceKey<Biome>")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("HolderLookup")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("Registries.BIOME")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("RegistryAccess")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("MapCodec")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("Codec<")));
        assertTrue(consolidationSources.stream().noneMatch(path -> read(path).contains("RecordCodecBuilder")));

        assertOnlyFileContains(runtimeBiomeSourceFiles, "ResourceLocation", CONVERTER_SOURCE);
        assertOnlyFileContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>", CONVERTER_SOURCE);
        assertOnlyFileContains(runtimeBiomeSourceFiles, "HolderLookup", CONVERTER_SOURCE);
        assertOnlyFileContains(
            runtimeBiomeSourceFiles,
            "ResourceKey.create(Registries.BIOME, candidateLocation)",
            CONVERTER_SOURCE
        );
        assertOnlyFileContains(runtimeBiomeSourceFiles, "lookupProvider.lookup(Registries.BIOME)", CONVERTER_SOURCE);
        assertNoSourceContains(runtimeBiomeSourceFiles, "Registry.register");
        assertNoSourceContains(runtimeBiomeSourceFiles, "BuiltInRegistries.BIOME_SOURCE.register");
        assertNoSourceContains(runtimeBiomeSourceFiles, "DeferredRegister");
        assertNoSourceContains(runtimeBiomeSourceFiles, "RecordCodecBuilder");
        assertNoSourceContains(runtimeBiomeSourceFiles, "public static final MapCodec");
        assertNoSourceContains(runtimeBiomeSourceFiles, "static final MapCodec");
        assertNoSourceContains(runtimeBiomeSourceFiles, "CODEC =");
        assertNoSourceContains(runtimeBiomeSourceFiles, "return getNoiseBiome");
        assertNoSourceContains(runtimeBiomeSourceFiles, "return collectPossibleBiomes");
        assertNoSourceContains(runtimeBiomeSourceFiles, "registerConfiguredFeature(");
        assertNoSourceContains(runtimeBiomeSourceFiles, "registerPlacedFeature(");
        assertNoSourceContains(runtimeBiomeSourceFiles, "registerConfiguredCarver(");
        assertNoSourceContains(runtimeBiomeSourceFiles, "addFreshEntity(");
        assertNoSourceContains(runtimeBiomeSourceFiles, "changeDimension(");
        assertNoSourceContains(runtimeBiomeSourceFiles, ".teleportTo(");

        assertTrue(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.java"
        )));

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

    private static void assertOnlyFileContains(List<Path> files, String text, Path expected) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        if (text.equals("HolderLookup")) {
            assertEquals(
                List.of(
                    "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java",
                    expected.getFileName().toString()
                ),
                matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
            );
            return;
        }

        assertEquals(
            List.of(expected.getFileName().toString()),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
        );
    }

    private static void assertNoSourceContains(List<Path> files, String text) throws IOException {
        assertTrue(files.stream().noneMatch(file -> read(file).contains(text)));
    }

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)));
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
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || (candidate.getParent() != null && Files.exists(candidate.getParent()))) {
                return candidate;
            }
            current = current.getParent();
        }
        return Path.of(first, more).toAbsolutePath();
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read " + path, exception);
        }
    }
}
