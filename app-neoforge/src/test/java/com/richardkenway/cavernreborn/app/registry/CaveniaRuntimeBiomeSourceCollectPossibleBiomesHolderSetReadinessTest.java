package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness;

class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadinessTest {
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.java"
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
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void holderSetReadinessPinsTheFuturePreResolvedPossibleBiomesPolicyWithoutRuntimeWiring() throws IOException {
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.CANDIDATE_INVENTORY_SOURCE,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.DESIGNATED_CONVERTER_DEPENDENCY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.LOOKUP_PROVIDER_REQUIREMENT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.PRE_RESOLVED_HOLDER_SET_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.HOLDER_SET_OUTPUT_SHAPE,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.STREAM_RETURN_BOUNDARY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.DEDUPLICATION_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.ORDERING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.FALLBACK_IF_EMPTY_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.MISSING_CANDIDATE_HANDLING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.INVALID_CANDIDATE_HANDLING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.UNRESOLVED_HOLDER_HANDLING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_CONVERTER_WIRING_IN_RUNTIME_METHODS,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_RUNTIME_CONSTRUCTION_OR_FACTORY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent
                .REAL_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_STILL_DEFERRED
        );
        Set<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent> lookupProviderRequired = Set.of(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.DESIGNATED_CONVERTER_DEPENDENCY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.LOOKUP_PROVIDER_REQUIREMENT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.PRE_RESOLVED_HOLDER_SET_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.HOLDER_SET_OUTPUT_SHAPE,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.STREAM_RETURN_BOUNDARY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.FALLBACK_IF_EMPTY_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.MISSING_CANDIDATE_HANDLING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.INVALID_CANDIDATE_HANDLING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.UNRESOLVED_HOLDER_HANDLING_POLICY,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent
                .REAL_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry> entries =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.entries();
        List<Path> runtimeBiomeSourceFiles;
        List<Path> readinessSources = List.of(COMPONENT_SOURCE, ENTRY_SOURCE, READINESS_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String readinessSource = Files.readString(READINESS_SOURCE);

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            runtimeBiomeSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
        }

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.components());
        assertEquals(17, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.holderSetSurface().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.futurePolicy().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.implementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeWiringReady()));
        entries.forEach(entry -> assertEquals(
            lookupProviderRequired.contains(entry.component()),
            entry.lookupProviderRequired()
        ));
        assertTrue(entries.stream().allMatch(entry -> !entry.lookupProviderAvailableInThisSlice()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));

        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.collectPossibleBiomesHolderSetReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateInventorySourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.designatedConverterDependencyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.lookupProviderRequirementPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.preResolvedHolderSetPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.holderSetOutputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.streamReturnBoundaryPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.deduplicationPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.orderingPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.fallbackIfEmptyPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.missingCandidateHandlingPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.invalidCandidateHandlingPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.unresolvedHolderHandlingPolicyPinned());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.converterWiringInRuntimeMethodsReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.getNoiseBiomeWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.runtimeConstructionOrFactoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.lookupProviderRequiredForRuntimeHolderSet());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.lookupProviderAvailableInThisSlice());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.realCollectPossibleBiomesHolderSetStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.canActivateCaveniaNow());
        assertEquals(
            "CaveniaLegacyToModernBiomeKeyMappings",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureCandidateInventorySource()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureConverterDependency()
        );
        assertEquals(14, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateInventoryReady());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateModernBiomeKeys(),
            "minecraft:plains"
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness
                .candidateKeysStillStringOnlyOutsideConverter()
        );
        assertEquals(
            "future holder-set construction requires caller-provided biome lookup/provider before runtime construction",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureLookupProviderPolicy()
        );
        assertEquals(
            "future runtime source must receive or store a pre-resolved holder set because collectPossibleBiomes has no lookup/provider parameter",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futurePreResolvedHolderSetPolicy()
        );
        assertEquals(
            "pre-resolved biome holder set/list",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureHolderSetOutputShape()
        );
        assertEquals(
            "future collectPossibleBiomes may stream only pre-resolved holder outputs after runtime construction is explicitly allowed",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureStreamReturnBoundary()
        );
        assertEquals(
            "deduplicate resolved holders, not current string candidate inventory",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureDeduplicationPolicy()
        );
        assertEquals(
            "preserve legacy inventory order for first successful resolved holders",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureOrderingPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness
                .futureFallbackIfEmptyPolicy()
                .contains("minecraft:plains")
        );
        assertEquals(
            "skip unresolved or missing candidate keys during future holder-set construction without crashing runtime method",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureMissingCandidatePolicy()
        );
        assertEquals(
            "invalid candidate keys must not crash future holder-set construction",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureInvalidCandidatePolicy()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyToHolderConverterConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyToHolderConverterConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyToHolderConverterReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyToHolderConverterRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.holderForCandidateKeyReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.holderForCandidateKeyOrFallbackReady());
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyInputShape()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.selectorToWeightedCandidateBridgeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.selectorToWeightedCandidateBridgeRuntimeReady());
        assertEquals("minecraft:taiga", CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyForSampleOrigin());
        assertEquals("minecraft:desert", CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyForSampleMixed());
        assertEquals("minecraft:desert", CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.candidateKeyForSampleNegative());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.sampleKeysExistInInventory());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.goNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.goNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futurePossibleBiomesSourceIsCandidateInventory());
        assertEquals(14, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futurePossibleBiomesCandidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futurePossibleBiomesFallbackIfEmptyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.fallbackPolicyRuntimeReady());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.fallbackLegacyBiomeName());
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.fallbackCandidateModernBiomeKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.codecMethodShapeRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.globalReadinessMatrixBlockedRequirementCount());
        assertTrue(
            readinessSource.contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertTrue(
            readinessSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            readinessSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            readinessSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetReadinessReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness\n            .collectPossibleBiomesHolderSetReadinessReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetImplementationReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String collectPossibleBiomesHolderSetOutputShape() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureHolderSetOutputShape();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.entries(),
            entries.getFirst()
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.components(),
            expectedComponents.getFirst()
        );
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.entries().add(null)
        );

        assertTrue(Files.exists(COMPONENT_SOURCE));
        assertTrue(Files.exists(ENTRY_SOURCE));
        assertTrue(Files.exists(READINESS_SOURCE));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension").resolve("cavenia.json")));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension_type").resolve("cavenia.json")));
        assertSourceSafety(runtimeBiomeSourceFiles, readinessSources);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles, List<Path> readinessSources)
        throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String converterSource = Files.readString(CONVERTER_SOURCE);

        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("import net.minecraft")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("ResourceLocation")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("ResourceKey<Biome>")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("HolderLookup")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("Registries.BIOME")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("RegistryAccess")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("MapCodec")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("Codec<")));
        assertTrue(readinessSources.stream().noneMatch(path -> read(path).contains("RecordCodecBuilder")));

        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_CONVERTER_FILE_NAME))
                .count()
        );
        assertTrue(converterSource.contains("ResourceLocation"));
        assertTrue(converterSource.contains("ResourceKey<Biome>"));
        assertTrue(converterSource.contains("HolderLookup"));
        assertTrue(converterSource.contains("Registries.BIOME"));
        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));

        assertOnlyFileContains(runtimeBiomeSourceFiles, "ResourceLocation", CONVERTER_SOURCE);
        assertOnlyFileContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>", CONVERTER_SOURCE);
        assertOnlyFileContains(runtimeBiomeSourceFiles, "HolderLookup", CONVERTER_SOURCE);
        assertOnlyFileContains(
            runtimeBiomeSourceFiles,
            "ResourceKey.create(Registries.BIOME, candidateLocation)",
            CONVERTER_SOURCE
        );
        assertOnlyFileContains(
            runtimeBiomeSourceFiles,
            "lookupProvider.lookup(Registries.BIOME)",
            CONVERTER_SOURCE
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
                    )
            );
        }
    }

    private static void assertOnlyFileContains(List<Path> files, String text, Path expected) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        assertEquals(
            List.of(expected.getFileName().toString()),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
        );
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
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
