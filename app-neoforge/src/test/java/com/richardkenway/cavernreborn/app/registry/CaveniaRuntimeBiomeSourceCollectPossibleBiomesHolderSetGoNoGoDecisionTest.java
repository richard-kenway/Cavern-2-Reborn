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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision;

class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecisionTest {
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.java"
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
    void collectPossibleBiomesHolderSetGoNoGoDecisionPinsOnlyReadinessPlanningAsTheNextStep() throws IOException {
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_CANDIDATE_INVENTORY_SOURCE_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_CONVERTER_DEPENDENCY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_HOLDER_SET_OUTPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_DEDUPLICATION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_ORDERING_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_FALLBACK_IF_EMPTY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_MISSING_CANDIDATE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_STREAM_RETURN_BOUNDARY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_GENERATOR_REGISTRATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .NO_WORLDGEN_ACCESS_SPAWNING,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .KEEP_RUNTIME_METHODS_UNSUPPORTED
        );
        Set<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail> allowedReadinessActions = Set.of(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_CANDIDATE_INVENTORY_SOURCE_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_CONVERTER_DEPENDENCY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_HOLDER_SET_OUTPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_DEDUPLICATION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_ORDERING_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_FALLBACK_IF_EMPTY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_MISSING_CANDIDATE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                .ALLOW_STREAM_RETURN_BOUNDARY_PINNING_NEXT
        );
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        List<Path> runtimeBiomeSourceFiles;
        List<Path> goNoGoSources = List.of(
            NEXT_DECISION_SOURCE,
            GUARDRAIL_SOURCE,
            GUARDRAIL_CONTRACT_SOURCE,
            DECISION_SOURCE
        );

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            runtimeBiomeSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
        }

        assertEquals(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision
                .PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .decisionIsReadinessOnlyForNextSlice()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .collectPossibleBiomesHolderSetReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .collectPossibleBiomesHolderSetReadinessRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .collectPossibleBiomesHolderSetReadinessImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .collectPossibleBiomesHolderSetImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .collectPossibleBiomesHolderSetRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .runtimeWiringAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .activationAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .canActivateCaveniaNow()
        );

        assertEquals(
            expectedGuardrails,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.guardrailValues()
        );
        assertEquals(18, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract::enforcedInThisSlice
        ));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract::blocksActivation
        ));
        assertTrue(guardrails.stream().allMatch(
            contract -> !contract.allowsNextSliceImplementationAction()
        ));
        assertTrue(guardrails.stream().allMatch(
            contract -> !contract.allowsNextSliceRuntimeWiringAction()
        ));
        assertTrue(guardrails.stream().allMatch(
            contract -> !contract.allowsNextSliceActivationAction()
        ));
        guardrails.forEach(contract -> assertEquals(
            allowedReadinessActions.contains(contract.guardrail()),
            contract.allowsNextSliceReadinessAction()
        ));
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .allGuardrailsEnforcedInThisSlice()
        );
        assertEquals(
            9,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .allowedNextSliceReadinessActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .allowedNextSliceImplementationActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .allowedNextSliceRuntimeWiringActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .allowedNextSliceActivationActionCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .anyImplementationAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .anyRuntimeWiringAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .anyActivationAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddCollectPossibleBiomesHolderSetReadiness()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinCandidateInventorySource()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinConverterDependency()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinHolderSetOutputPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinDeduplicationPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinOrderingPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinFallbackIfEmptyPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinMissingCandidatePolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayPinStreamReturnBoundary()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayImplementCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayWireConverterIntoCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayWireConverterIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayRegisterCodec()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayRegisterGenerator()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .candidateKeyToHolderConverterConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .candidateKeyToHolderConverterConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .candidateKeyToHolderConverterConsolidationSelectedNextDecisionMatches()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .candidateKeyToHolderConverterReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .candidateKeyToHolderConverterRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.holderForCandidateKeyReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .holderForCandidateKeyOrFallbackReady()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.candidateKeyInputShape()
        );
        assertEquals(14, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.candidateInventoryReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .candidateKeysStillStringOnlyOutsideConverter()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .selectorToWeightedCandidateBridgeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.candidateKeyForSampleNegative()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .expectedSampleOriginCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .expectedSampleMixedCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .expectedSampleNegativeCandidateKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.sampleKeysExistInInventory()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .futurePossibleBiomesSourceIsCandidateInventory()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .futurePossibleBiomesCandidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .futurePossibleBiomesFallbackIfEmptyPinned()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .holderConversionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .allCurrentReadinessLayersReady()
        );
        assertEquals(0, CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.runtimeReadyLayerCount());
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .codecMethodShapeRuntimeReady()
        );
        assertTrue(
            Files.readString(DECISION_SOURCE).contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertTrue(
            Files.readString(DECISION_SOURCE).contains(
                "public static boolean designatedSubclassRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .usableCodecImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.codecRegistered());
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.biomeSourceTypeRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .normalRuntimeConstructionAllowed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.unsupportedMethodStubsOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.collectPossibleBiomesStubbed()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.dimensionBindingReady());
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertTrue(
            Files.readString(DECISION_SOURCE).contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            Files.readString(DECISION_SOURCE).contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            Files.readString(DECISION_SOURCE).contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.guardrails(),
            guardrails.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.guardrailValues(),
            expectedGuardrails.get(0)
        );
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.guardrails().add(null)
        );

        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision\n            .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext();\n    }"
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
                "public static boolean collectPossibleBiomesHolderSetReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision\n            .nextSliceMayAddCollectPossibleBiomesHolderSetReadiness();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String collectPossibleBiomesHolderSetOutputShape() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureHolderSetOutputShape();\n    }"
            )
        );

        assertSourceSafety(runtimeBiomeSourceFiles, goNoGoSources);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles, List<Path> goNoGoSources)
        throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String converterSource = Files.readString(CONVERTER_SOURCE);

        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("import net.minecraft")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("ResourceLocation")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("ResourceKey<Biome>")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("HolderLookup")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("Registries.BIOME")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("RegistryAccess")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("MapCodec")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("Codec<")));
        assertTrue(goNoGoSources.stream().noneMatch(path -> read(path).contains("RecordCodecBuilder")));

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
