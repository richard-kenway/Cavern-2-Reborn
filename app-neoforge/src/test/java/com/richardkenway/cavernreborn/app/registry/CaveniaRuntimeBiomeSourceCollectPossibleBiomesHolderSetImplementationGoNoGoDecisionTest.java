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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationNextDecision;

class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecisionTest {
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.java"
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
    void holderSetImplementationGoNoGoDecisionKeepsRuntimeBlockedWhileSelectingOnlyTheGuardedBuilderHelperNext()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail> expectedGuardrails =
            List.of(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_GUARDED_PRE_RESOLVED_HOLDER_SET_BUILDER_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_INPUT_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_DESIGNATED_CONVERTER_DEPENDENCY_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_LOOKUP_PROVIDER_PARAMETER_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_RESOLVED_HOLDER_DEDUPLICATION_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_LEGACY_ORDER_PRESERVATION_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_FALLBACK_IF_EMPTY_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_MISSING_INVALID_UNRESOLVED_SKIP_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_RUNTIME_CONSTRUCTION_OR_FACTORY,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_GENERATOR_REGISTRATION,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_DIMENSION_ACTIVATION,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED
            );
        Set<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail> allowedBuilderActions =
            Set.of(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_GUARDED_PRE_RESOLVED_HOLDER_SET_BUILDER_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_INPUT_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_DESIGNATED_CONVERTER_DEPENDENCY_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_LOOKUP_PROVIDER_PARAMETER_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_RESOLVED_HOLDER_DEDUPLICATION_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_LEGACY_ORDER_PRESERVATION_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_FALLBACK_IF_EMPTY_NEXT,
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_MISSING_INVALID_UNRESOLVED_SKIP_NEXT
            );
        List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        List<Path> runtimeBiomeSourceFiles;
        List<Path> decisionSources = List.of(
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
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationNextDecision
                .PROCEED_WITH_GUARDED_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_BUILDER_NEXT,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .decisionIsImplementationOnlyForNextSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesHolderSetBuilderImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesHolderSetRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .runtimeWiringAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .activationAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .canActivateCaveniaNow()
        );

        assertEquals(
            expectedGuardrails,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.guardrailValues()
        );
        assertEquals(
            18,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.guardrailCount()
        );
        assertTrue(guardrails.stream().allMatch(contract -> !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract
                ::enforcedInThisSlice
        ));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract
                ::blocksActivation
        ));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.allowsNextSliceRuntimeWiringAction()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.allowsNextSliceActivationAction()));
        guardrails.forEach(contract -> assertEquals(
            allowedBuilderActions.contains(contract.guardrail()),
            contract.allowsNextSliceBuilderAction()
        ));
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .allGuardrailsEnforcedInThisSlice()
        );
        assertEquals(
            8,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .allowedNextSliceBuilderActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .allowedNextSliceRuntimeWiringActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .allowedNextSliceActivationActionCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .anyRuntimeWiringAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .anyActivationAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddGuardedPreResolvedHolderSetBuilder()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayUseCandidateInventoryInput()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayUseDesignatedConverterDependency()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayRequireLookupProviderParameter()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayDeduplicateResolvedHolders()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayPreserveLegacyOrder()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayApplyFallbackIfEmpty()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMaySkipMissingInvalidUnresolvedCandidates()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceDesignatedBuilderSimpleName()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceDesignatedBuilderFileName()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayWireBuilderIntoCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayImplementCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayWireBuilderIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddRuntimeConstructionOrFactory()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayRegisterCodec()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayRegisterGenerator()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesHolderSetReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesHolderSetReadinessRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesHolderSetImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesHolderSetRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .lookupProviderRequiredForRuntimeHolderSet()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .lookupProviderAvailableInThisSlice()
        );
        assertEquals(
            "pre-resolved biome holder set/list",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futureHolderSetOutputShape()
        );
        assertEquals(
            "future collectPossibleBiomes may stream only pre-resolved holder outputs after runtime construction is explicitly allowed",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futureStreamReturnBoundary()
        );
        assertEquals(
            "deduplicate resolved holders, not current string candidate inventory",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futureDeduplicationPolicy()
        );
        assertEquals(
            "preserve legacy inventory order for first successful resolved holders",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futureOrderingPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futureFallbackIfEmptyPolicy()
                .contains("minecraft:plains")
        );
        assertEquals(
            "skip unresolved or missing candidate keys during future holder-set construction without crashing runtime method",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futureMissingCandidatePolicy()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyToHolderConverterConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyToHolderConverterConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyToHolderConverterReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyToHolderConverterRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .holderForCandidateKeyReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .holderForCandidateKeyOrFallbackReady()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateInventoryReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeysStillStringOnlyOutsideConverter()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .candidateKeyForSampleNegative()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .expectedSampleOriginCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .expectedSampleMixedCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .expectedSampleNegativeCandidateKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .sampleKeysExistInInventory()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futurePossibleBiomesSourceIsCandidateInventory()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futurePossibleBiomesCandidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .futurePossibleBiomesFallbackIfEmptyPinned()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .holderConversionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .allCurrentReadinessLayersReady()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .runtimeReadyLayerCount()
        );
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .codecMethodShapeRuntimeReady()
        );
        assertTrue(
            Files.readString(DECISION_SOURCE).contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .designatedSubclassRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .usableCodecImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .codecRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .biomeSourceTypeRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .normalRuntimeConstructionAllowed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .unsupportedMethodStubsOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .collectPossibleBiomesStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .getNoiseBiomeStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .codecMethodStubbed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .dimensionBindingReady()
        );
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .dimensionJsonPresent()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .dimensionTypeJsonPresent()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .cavemanRemainsDeferred()
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.guardrails(),
            guardrails.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision.guardrailValues(),
            expectedGuardrails.get(0)
        );
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .guardrails()
                .add(null)
        );

        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetImplementationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderIsNext() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .nextSliceMayAddGuardedPreResolvedHolderSetBuilder();\n    }"
            )
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .activationRequirements()
                .equals(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                    .activationRequirements())
        );
        assertTrue(
            !CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
                .activationBlockedReason()
                .isBlank()
        );

        assertSourceSafety(runtimeBiomeSourceFiles, decisionSources);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles, List<Path> decisionSources)
        throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String converterSource = Files.readString(CONVERTER_SOURCE);

        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("import net.minecraft")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("ResourceLocation")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("ResourceKey<Biome>")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("HolderLookup")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("Registries.BIOME")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("RegistryAccess")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("MapCodec")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("Codec<")));
        assertTrue(decisionSources.stream().noneMatch(path -> read(path).contains("RecordCodecBuilder")));

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
        assertTrue(converterSource.contains("Holder<Biome>"));
        assertTrue(converterSource.contains("Biome"));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java"
        )));
        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder."));

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

    private static <T> void assertImmutableList(List<T> values, T sampleValue) {
        assertThrows(UnsupportedOperationException.class, () -> values.add(sampleValue));
    }

    private static void assertOnlyFileContains(List<Path> files, String needle, Path expectedFile) {
        List<Path> matches = files.stream()
            .filter(path -> read(path).contains(needle))
            .toList();
        assertEquals(List.of(expectedFile), matches);
    }

    private static void assertNoMainSourceContains(List<Path> files, String needle) {
        assertTrue(files.stream().noneMatch(path -> read(path).contains(needle)));
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
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || (candidate.getParent() != null && Files.exists(candidate.getParent()))) {
                return candidate;
            }
            current = current.getParent();
        }
        return Path.of(first, more).toAbsolutePath();
    }
}
