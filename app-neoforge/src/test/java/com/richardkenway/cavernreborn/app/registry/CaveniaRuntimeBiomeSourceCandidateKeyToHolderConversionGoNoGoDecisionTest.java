package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlan;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceApiShapeInventory;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeStub;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecisionTest {
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.java"
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
    void candidateKeyToHolderConversionGoNoGoDecisionPinsReadinessOnlyBoundaries() throws IOException {
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_STRING_CANDIDATE_KEY_SURFACE_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_RESOURCE_LOCATION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_RESOURCE_KEY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_REGISTRY_LOOKUP_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_HOLDER_RESOLUTION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_MISSING_OR_FALLBACK_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .NO_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_HOLDER_RESOLUTION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_RESOURCE_LOCATION_CONVERSION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_RESOURCE_KEY_CONVERSION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_FALLBACK_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_USABLE_CODEC_OR_REGISTRATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.KEEP_CANDIDATE_KEYS_STRING_ONLY
        );
        Set<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail> allowedReadinessActions = Set.of(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_STRING_CANDIDATE_KEY_SURFACE_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_RESOURCE_LOCATION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_RESOURCE_KEY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_REGISTRY_LOOKUP_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_HOLDER_RESOLUTION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                .ALLOW_MISSING_OR_FALLBACK_POLICY_PINNING_NEXT
        );
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.guardrails();
        List<String> candidateKeys =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateModernBiomeKeys();
        List<Path> mainSourceFiles;
        String decisionSource = Files.readString(DECISION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            mainSourceFiles = sourceFiles.filter(Files::isRegularFile).toList();
        }

        assertEquals(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision
                .PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .decisionIsGoForCandidateKeyToHolderConversionReadinessNext()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .decisionIsReadinessOnlyForNextSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .candidateKeyToHolderConversionReadinessImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .candidateKeyToHolderConversionImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .candidateKeyToHolderConversionRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.runtimeApiAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.activationAllowedInThisSlice()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.canActivateCaveniaNow());

        assertEquals(
            expectedGuardrails,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.guardrailValues()
        );
        assertEquals(20, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract::enforcedInThisSlice));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract::blocksActivation));
        assertTrue(guardrails.stream().noneMatch(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract::allowsNextSliceConversionImplementation));
        assertTrue(guardrails.stream().noneMatch(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract::allowsNextSliceRuntimeAction));
        guardrails.forEach(contract ->
            assertEquals(
                allowedReadinessActions.contains(contract.guardrail()),
                contract.allowsNextSliceReadinessAction()
            )
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .allGuardrailsEnforcedInThisSlice()
        );
        assertEquals(
            7,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .allowedNextSliceReadinessActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .allowedNextSliceConversionImplementationActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .allowedNextSliceRuntimeActionCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .anyConversionImplementationAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.anyRuntimeActionAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayAddCandidateKeyToHolderConversionReadiness()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayPinStringCandidateKeySurface()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayPinResourceLocationPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayPinResourceKeyPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayPinRegistryLookupPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayPinHolderResolutionPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayPinMissingOrFallbackPolicy()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayImplementCandidateKeyToHolderConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayUseRegistryLookupAccess()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayResolveHolders()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayImplementResourceLocationConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayImplementResourceKeyConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayWireIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayAddFallbackImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayRegisterCodec());
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .selectorToWeightedCandidateBridgeConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .selectorToWeightedCandidateBridgeConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .selectorToWeightedCandidateBridgeConsolidationSelectedNextDecisionMatches()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .selectorToWeightedCandidateBridgeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateKeyOutputShape()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateKeysRemainStringOnly());
        assertEquals(14, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateInventoryReady());
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.candidateKeyForSampleNegative()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.expectedSampleOriginCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.expectedSampleMixedCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.expectedSampleNegativeCandidateKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.sampleKeysExistInInventory());

        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.holderConversionRuntimeReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.holderResolutionReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.resourceLocationConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.resourceKeyConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.registryLookupAccessReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.allCurrentReadinessLayersReady()
        );
        assertEquals(0, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.runtimeReadyLayerCount());
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.codecMethodShapeRuntimeReady()
        );
        assertTrue(
            decisionSource.contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.designatedSubclassRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.runtimeHolderReturnReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.usableCodecImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.biomeSourceTypeRegistered());
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.normalRuntimeConstructionAllowed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.unsupportedMethodStubsOnly()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.dimensionBindingReady());
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertTrue(
            decisionSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            decisionSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            decisionSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.guardrails(),
            guardrails.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision.guardrailValues(),
            expectedGuardrails.get(0)
        );
        assertImmutableList(candidateKeys, candidateKeys.get(0));

        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision\n            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision\n            .nextSliceMayAddCandidateKeyToHolderConversionReadiness();\n    }"
            )
        );

        assertOnlyDesignatedFileContains(mainSourceFiles, "extends BiomeSource");
        assertOnlyDesignatedFileContains(mainSourceFiles, "import net.minecraft.world.level.biome.BiomeSource;");
        assertOnlyDesignatedFileContains(mainSourceFiles, "Holder<Biome>");
        assertOnlyDesignatedFileContains(mainSourceFiles, "import com.mojang.serialization.MapCodec;");
        assertOnlyDesignatedFileContains(mainSourceFiles, "Climate.Sampler");

        assertDecisionSource(Files.readString(NEXT_DECISION_SOURCE));
        assertDecisionSource(Files.readString(GUARDRAIL_SOURCE));
        assertDecisionSource(Files.readString(GUARDRAIL_CONTRACT_SOURCE));
        assertDecisionSource(Files.readString(DECISION_SOURCE));

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
                    )
            );
        }
    }

    private static void assertDecisionSource(String source) {
        assertFalse(source.contains("import net.minecraft"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("Climate.Sampler"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("BuiltInRegistries"));
    }

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
    }

    private static void assertOnlyDesignatedFileContains(List<Path> files, String text) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        if (text.equals("Holder<Biome>")) {
            assertEquals(
                List.of("CaveniaRuntimeBiomeSource.java", "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java", "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"),
                matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
            );
            return;
        }

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles);
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        boolean allowConverterFile = text.equals("ResourceLocation")
            || text.equals("ResourceKey<Biome>")
            || text.equals("HolderLookup")
            || text.equals("Registries.BIOME")
            || text.equals("return holder");
        assertTrue(files.stream().noneMatch(file ->
            (!allowConverterFile
                || !file.getFileName().toString().equals("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"))
                && read(file).contains(text)
        ));
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
