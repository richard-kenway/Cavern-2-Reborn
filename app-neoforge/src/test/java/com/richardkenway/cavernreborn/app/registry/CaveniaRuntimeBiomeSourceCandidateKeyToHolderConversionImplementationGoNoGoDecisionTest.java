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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecisionTest {
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision.java"
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
    void candidateKeyToHolderConversionImplementationGoNoGoDecisionPinsOnlyGuardedConversionAsTheNextStep()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail> expectedGuardrails =
            List.of(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_GUARDED_CONVERSION_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_INPUT_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_RESOURCE_LOCATION_PARSING_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_RESOURCE_KEY_CONSTRUCTION_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_BIOME_REGISTRY_LOOKUP_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_HOLDER_RESOLUTION_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_MISSING_OR_FALLBACK_HANDLING_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_GENERATOR_REGISTRATION,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_DIMENSION_ACTIVATION,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED
            );
        Set<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail>
            allowedConversionActions = Set.of(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_GUARDED_CONVERSION_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_INPUT_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_RESOURCE_LOCATION_PARSING_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_RESOURCE_KEY_CONSTRUCTION_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_BIOME_REGISTRY_LOOKUP_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_HOLDER_RESOLUTION_IN_DESIGNATED_HELPER_NEXT,
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_MISSING_OR_FALLBACK_HANDLING_IN_DESIGNATED_HELPER_NEXT
            );
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision.guardrails();
        List<String> candidateKeys =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateModernBiomeKeys();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String implementationDecisionSource = Files.readString(DECISION_SOURCE);
        List<Path> mainSourceFiles;

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            mainSourceFiles = sourceFiles.filter(Files::isRegularFile).toList();
        }

        assertEquals(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision
                .PROCEED_WITH_GUARDED_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .decisionIsImplementationOnlyForNextSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyToHolderConversionImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyToHolderConversionRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .runtimeWiringAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .activationAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .canActivateCaveniaNow()
        );

        assertEquals(
            expectedGuardrails,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision.guardrailValues()
        );
        assertEquals(16, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract::enforcedInThisSlice
        ));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract::blocksActivation
        ));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.allowsNextSliceRuntimeWiringAction()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.allowsNextSliceActivationAction()));
        guardrails.forEach(contract -> assertEquals(
            allowedConversionActions.contains(contract.guardrail()),
            contract.allowsNextSliceConversionAction()
        ));
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .allGuardrailsEnforcedInThisSlice()
        );
        assertEquals(
            7,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .allowedNextSliceConversionActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .allowedNextSliceRuntimeWiringActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .allowedNextSliceActivationActionCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .anyRuntimeWiringAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .anyActivationAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayAddGuardedConversionHelper()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayUseStringCandidateKeyInput()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayParseResourceLocationInDesignatedHelper()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayConstructResourceKeyInDesignatedHelper()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayUseBiomeRegistryLookupInDesignatedHelper()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayResolveHolderInDesignatedHelper()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayHandleMissingOrFallbackInDesignatedHelper()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceDesignatedConverterSimpleName()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceDesignatedConverterFileName()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayWireIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayWireIntoCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayRegisterCodec()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayRegisterGenerator()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyToHolderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyToHolderConversionReadinessRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .conversionImplementationReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .registryLookupRequiredForRuntimeConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .registryLookupAccessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .holderResolutionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .holderReturnReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .resourceLocationConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .resourceKeyConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .fallbackRuntimeReady()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyInputShape()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeysRemainStringOnly()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateInventoryReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeConsolidationReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeReady()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .candidateKeyForSampleNegative()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .sampleKeysExistInInventory()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .holderConversionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .allCurrentReadinessLayersReady()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .runtimeReadyLayerCount()
        );
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .codecMethodShapeRuntimeReady()
        );
        assertTrue(
            implementationDecisionSource.contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .designatedSubclassRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .runtimeHolderReturnReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .usableCodecImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision.codecRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .biomeSourceTypeRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .normalRuntimeConstructionAllowed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .unsupportedMethodStubsOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .collectPossibleBiomesStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .getNoiseBiomeStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .codecMethodStubbed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .dimensionBindingReady()
        );
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertThrows(UnsupportedOperationException.class, () -> guardrails.add(null));
        assertThrows(UnsupportedOperationException.class, () -> candidateKeys.add("minecraft:plains"));
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision\n            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationIsNext() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision\n            .nextSliceMayAddGuardedConversionHelper();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.converterReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String candidateKeyToHolderConverterFileName() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.designatedConverterFileName();\n    }"
            )
        );
        assertTrue(
            Files.exists(
                resolveProjectPathOrSibling(
                    "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app",
                    "worldgen", DESIGNATED_CONVERTER_FILE_NAME
                )
            )
        );
        assertSourceSafety();
        assertEquals(
            1L,
            mainSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .count()
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

    private static void assertSourceSafety() throws IOException {
        String nextDecisionSource = Files.readString(NEXT_DECISION_SOURCE);
        String guardrailSource = Files.readString(GUARDRAIL_SOURCE);
        String guardrailContractSource = Files.readString(GUARDRAIL_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String converterSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            DESIGNATED_CONVERTER_FILE_NAME
        ));
        List<String> decisionSources = List.of(nextDecisionSource, guardrailSource, guardrailContractSource, decisionSource);
        List<String> forbiddenTokens = List.of(
            "Climate",
            "Climate.Sampler",
            "Holder<Biome>",
            "ResourceLocation",
            "ResourceKey<Biome>",
            "RegistryLookup<",
            "RegistryAccess",
            "MapCodec",
            "Codec<",
            "RecordCodecBuilder",
            "Registry.register",
            "BuiltInRegistries"
        );
        decisionSources.forEach(source -> forbiddenTokens.forEach(token -> assertFalse(source.contains(token))));
        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertTrue(converterSource.contains("ResourceLocation"));
        assertTrue(converterSource.contains("ResourceKey<Biome>"));
        assertTrue(converterSource.contains("HolderLookup"));
        assertTrue(converterSource.contains("Holder<Biome>"));

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            List<Path> files = sourceFiles.filter(Files::isRegularFile).toList();
            List<Path> runtimeBiomeSourceFiles = files.stream()
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
            assertOnlyDesignatedFileContains(files, "extends BiomeSource");
            assertOnlyDesignatedFileContains(files, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(files, "Holder<Biome>");
            assertOnlyDesignatedFileContains(files, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(files, "Climate.Sampler");
            assertOnlyConverterFileContains(runtimeBiomeSourceFiles, "ResourceLocation");
            assertOnlyConverterFileContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>");
            assertOnlyConverterFileContains(runtimeBiomeSourceFiles, "HolderLookup");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceLocation");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryLookup<");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryAccess");
            assertNoMainSourceContains(files, "Registry.register");
            assertNoMainSourceContains(files, "BuiltInRegistries.BIOME_SOURCE.register");
            assertNoMainSourceContains(files, "DeferredRegister");
            assertNoMainSourceContains(files, "RecordCodecBuilder");
            assertNoMainSourceContains(files, "public static final MapCodec");
            assertNoMainSourceContains(files, "static final MapCodec");
            assertNoMainSourceContains(files, "CODEC =");
            assertNoMainSourceContains(files, "return getNoiseBiome");
            assertNoMainSourceContains(files, "return collectPossibleBiomes");
            assertNoMainSourceContains(files, "return Stream.of");
            assertNoMainSourceContains(files, ".map(Holder");
            assertNoMainSourceContains(files, ".collect(Collectors");
        }
    }

    private static void assertOnlyDesignatedFileContains(List<Path> files, String text) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        if (text.equals("Holder<Biome>")) {
            assertEquals(
                List.of(
                    DESIGNATED_SOURCE,
                    resolveProjectFile(
                        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app",
                        "worldgen", DESIGNATED_CONVERTER_FILE_NAME
                    )
                ),
                matchingFiles
            );
            return;
        }

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles);
    }

    private static void assertOnlyConverterFileContains(List<Path> files, String text) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        assertEquals(
            List.of(DESIGNATED_CONVERTER_FILE_NAME),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
        );
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        boolean allowConverterFile = text.equals("ResourceLocation")
            || text.equals("ResourceKey<Biome>")
            || text.equals("HolderLookup")
            || text.equals("Registries.BIOME");
        assertTrue(files.stream().noneMatch(file ->
            (!allowConverterFile || !file.getFileName().toString().equals(DESIGNATED_CONVERTER_FILE_NAME))
                && read(file).contains(text)
        ));
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

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)));
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
