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
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision;

class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationTest {
    private static final String DESIGNATED_BUILDER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java";
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry.java"
    );
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision.java"
    );
    private static final Path CONSOLIDATION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.java"
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
    void builderConsolidationKeepsRuntimeBlockedWhileSelectingRuntimeConstructionFactoryGoNoGoNext()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent>
            expectedComponents = List.of(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DESIGNATED_BUILDER_EXISTS,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .HOLDER_SET_BUILDER_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .CANDIDATE_INVENTORY_INPUT_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DESIGNATED_CONVERTER_DEPENDENCY_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .LOOKUP_PROVIDER_PARAMETER_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .PRE_RESOLVED_HOLDER_LIST_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DEDUPLICATION_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .LEGACY_ORDER_PRESERVED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .FALLBACK_IF_EMPTY_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .IMMUTABLE_OUTPUT_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .COLLECT_POSSIBLE_BIOMES_WIRING_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .GET_NOISE_BIOME_WIRING_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .RUNTIME_CONSTRUCTION_FACTORY_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .CODEC_AND_REGISTRATION_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DIMENSION_WORLDGEN_ACCESS_SPAWNING_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .NEXT_DECISION_SELECTED
            );
        Set<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent>
            expectedNextDecisionInputs = Set.of(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DESIGNATED_BUILDER_EXISTS,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .HOLDER_SET_BUILDER_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .PRE_RESOLVED_HOLDER_LIST_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .FALLBACK_IF_EMPTY_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .IMMUTABLE_OUTPUT_READY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .COLLECT_POSSIBLE_BIOMES_WIRING_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .RUNTIME_CONSTRUCTION_FACTORY_BLOCKED,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .NEXT_DECISION_SELECTED
            );
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry> entries =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.entries();
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
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.components()
        );
        assertEquals(16, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.entryCount());
        assertTrue(entries.stream().allMatch(entry -> !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.consolidatedState().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        entries.forEach(entry -> assertEquals(
            expectedNextDecisionInputs.contains(entry.component()),
            entry.nextDecisionInput()
        ));

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesHolderSetBuilderConsolidationReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .designatedBuilderExists()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .designatedBuilderSimpleName()
        );
        assertEquals(
            DESIGNATED_BUILDER_FILE_NAME,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .designatedBuilderFileName()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesHolderSetBuilderReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesHolderSetBuilderRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .buildCandidateHolderListReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateInventoryInputReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .lookupProviderParameterReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .designatedConverterUsageReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .preResolvedHolderListReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .resolvedHolderDeduplicationReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .legacyOrderPreservationReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .fallbackIfEmptyReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .immutableOutputListReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesWiringReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .getNoiseBiomeWiringReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeConstructionFactoryReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeBiomeSourceReady()
        );
        assertEquals(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision
                .PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_GO_NO_GO_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.selectedNextDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeConstructionFactoryGoNoGoIsNext()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeConstructionFactoryReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeConstructionFactoryImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeConstructionFactoryRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddRuntimeConstructionFactoryGoNoGoDecision()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddRuntimeConstructionFactoryReadiness()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayImplementRuntimeConstructionFactory()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayWireBuilderIntoCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayWireBuilderIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayImplementCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayRegisterCodec()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayRegisterGenerator()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .nextSliceMayRegisterCavemanEntity()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesHolderSetReadinessReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesHolderSetImplementationGoNoGoDecisionReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateKeyToHolderConverterReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateKeyToHolderConverterRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .holderForCandidateKeyReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .holderForCandidateKeyOrFallbackReady()
        );
        assertEquals(14, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.candidateInventoryReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateKeysStillStringOnlyOutsideBuilder()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .selectorToWeightedCandidateBridgeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateKeyForSampleNegative()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .sampleKeysExistInInventory()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .futurePossibleBiomesSourceIsCandidateInventory()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .futurePossibleBiomesCandidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .futurePossibleBiomesFallbackIfEmptyPinned()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .holderConversionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .allCurrentReadinessLayersReady()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .runtimeReadyLayerCount()
        );
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .codecMethodShapeRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .designatedSubclassReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .designatedSubclassRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .usableCodecImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.codecRegistered());
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .biomeSourceTypeRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .normalRuntimeConstructionAllowed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .unsupportedMethodStubsOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .collectPossibleBiomesStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .getNoiseBiomeStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .codecMethodStubbed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .dimensionBindingReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .activationAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .canActivateCaveniaNow()
        );
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.dimensionJsonPresent()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.dimensionTypeJsonPresent()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.cavemanRemainsDeferred()
        );

        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation\n            .collectPossibleBiomesHolderSetBuilderConsolidationReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderConsolidationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean runtimeConstructionFactoryGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation\n            .runtimeConstructionFactoryGoNoGoIsNext();\n    }"
            )
        );

        assertImmutableList(entries, entries.getFirst());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.components(),
            expectedComponents.getFirst()
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.candidateModernBiomeKeys(),
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
                .candidateModernBiomeKeys()
                .getFirst()
        );
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.entries().add(null)
        );

        assertTrue(Files.exists(COMPONENT_SOURCE));
        assertTrue(Files.exists(ENTRY_SOURCE));
        assertTrue(Files.exists(NEXT_DECISION_SOURCE));
        assertTrue(Files.exists(CONSOLIDATION_SOURCE));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension").resolve("cavenia.json")));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension_type").resolve("cavenia.json")));
        assertSourceSafety(runtimeBiomeSourceFiles);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles) throws IOException {
        List<Path> consolidationSources = List.of(
            COMPONENT_SOURCE,
            ENTRY_SOURCE,
            NEXT_DECISION_SOURCE,
            CONSOLIDATION_SOURCE
        );
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        consolidationSources.forEach(path -> {
            String source = read(path);
            assertFalse(source.contains("import net.minecraft"), () -> "Unexpected runtime import in " + path);
            assertFalse(source.contains("ResourceLocation"), () -> "Unexpected ResourceLocation in " + path);
            assertFalse(source.contains("ResourceKey<Biome>"), () -> "Unexpected ResourceKey<Biome> in " + path);
            assertFalse(source.contains("HolderLookup"), () -> "Unexpected HolderLookup in " + path);
            assertFalse(source.contains("Registries.BIOME"), () -> "Unexpected Registries.BIOME in " + path);
            assertFalse(source.contains("RegistryAccess"), () -> "Unexpected RegistryAccess in " + path);
            assertFalse(source.contains("MapCodec"), () -> "Unexpected MapCodec in " + path);
            assertFalse(source.contains("Codec<"), () -> "Unexpected Codec< in " + path);
            assertFalse(source.contains("RecordCodecBuilder"), () -> "Unexpected RecordCodecBuilder in " + path);
        });

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

        assertFalse(designatedSource.contains("buildCandidateHolderList("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder."));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceRuntimeFactory.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceFactory.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceConstruction.java"
        );
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

    @SuppressWarnings("unchecked")
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
