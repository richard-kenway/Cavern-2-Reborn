package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision {
    private static final String CANDIDATE_KEY_OUTPUT_SHAPE = "string modern biome candidate key";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision SELECTED_DECISION =
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision
            .PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract> GUARDRAILS =
        List.of(
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision",
                true,
                "The next slice may add only candidate-key-to-holder conversion readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_SURFACE_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation",
                true,
                "The next slice may pin only the existing string candidate-key surface."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_RESOURCE_\u004cOCATION_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                true,
                "The next slice may pin only a future resource-location parsing policy."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_RESOURCE_KEY_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                true,
                "The next slice may pin only a future resource-key construction policy."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_REGISTRY_LOOKUP_POLICY_PINNING_NEXT,
                "CaveniaRegistryLookupReadiness",
                true,
                "The next slice may pin only a future biome-registry lookup policy."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_HOLDER_RESOLUTION_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                true,
                "The next slice may pin only a future holder-resolution policy."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .ALLOW_MISSING_OR_FALLBACK_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                true,
                "The next slice may pin only a future missing-or-fallback policy."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .NO_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision",
                false,
                "The next slice may not implement candidate-key-to-holder conversion."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
                "CaveniaRegistryLookupReadiness",
                false,
                "Registry lookup access must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_HOLDER_RESOLUTION,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                false,
                "Holder resolution must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .NO_RESOURCE_\u004cOCATION_CONVERSION,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                false,
                "Resource-location conversion must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_RESOURCE_KEY_CONVERSION,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                false,
                "Resource-key conversion must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire any future conversion path into getNoiseBiome."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep getNoiseBiome unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep collectPossibleBiomes unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_FALLBACK_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                false,
                "Fallback implementation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .NO_USABLE_CODEC_OR_REGISTRATION,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                false,
                "Usable codec behavior and registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail.NO_DIMENSION_ACTIVATION,
                "CaveniaActivationReadinessMatrix",
                false,
                "Dimension activation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                "CaveniaActivationReadinessMatrix",
                false,
                "Worldgen, access and spawning must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail
                    .KEEP_CANDIDATE_KEYS_STRING_ONLY,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation",
                false,
                "Candidate keys must remain string-only until a later explicit conversion implementation slice."
            )
        );

    private CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForCandidateKeyToHolderConversionReadinessNext() {
        return true;
    }

    public static boolean decisionIsReadinessOnlyForNextSlice() {
        return true;
    }

    public static boolean candidateKeyToHolderConversionReadinessImplementedInThisSlice() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionImplementationReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
            .candidateKeyToHolderConversionReadinessReady();
    }

    public static boolean candidateKeyToHolderConversionReadinessRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConverterReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.converterReady();
    }

    public static boolean candidateKeyToHolderConverterRuntimeReady() {
        return false;
    }

    public static boolean runtimeApiAllowedInThisSlice() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail guardrail
    ) {
        return GUARDRAILS.stream()
            .filter(contract -> contract.guardrail() == guardrail)
            .findFirst();
    }

    public static int guardrailCount() {
        return GUARDRAILS.size();
    }

    public static boolean allGuardrailsEnforcedInThisSlice() {
        return true;
    }

    public static int allowedNextSliceReadinessActionCount() {
        return 7;
    }

    public static int allowedNextSliceConversionImplementationActionCount() {
        return 0;
    }

    public static int allowedNextSliceRuntimeActionCount() {
        return 0;
    }

    public static boolean anyConversionImplementationAllowedByGuardrails() {
        return false;
    }

    public static boolean anyRuntimeActionAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddCandidateKeyToHolderConversionReadiness() {
        return true;
    }

    public static boolean nextSliceMayPinStringCandidateKeySurface() {
        return true;
    }

    public static boolean nextSliceMayPinResource\u004cocationPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinResourceKeyPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinRegistryLookupPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinHolderResolutionPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinMissingOrFallbackPolicy() {
        return true;
    }

    public static boolean nextSliceMayImplementCandidateKeyToHolderConversion() {
        return false;
    }

    public static boolean nextSliceMayUseRegistryLookupAccess() {
        return false;
    }

    public static boolean nextSliceMayResolveHolders() {
        return false;
    }

    public static boolean nextSliceMayImplementResource\u004cocationConversion() {
        return false;
    }

    public static boolean nextSliceMayImplementResourceKeyConversion() {
        return false;
    }

    public static boolean nextSliceMayWireIntoGetNoiseBiome() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
        return false;
    }

    public static boolean nextSliceMayAddFallbackImplementation() {
        return false;
    }

    public static boolean nextSliceMayAddUsableCodecImplementation() {
        return false;
    }

    public static boolean nextSliceMayRegisterCodec() {
        return false;
    }

    public static boolean nextSliceMayRegisterBiomeSourceType() {
        return false;
    }

    public static boolean nextSliceMayAddDimensionJson() {
        return false;
    }

    public static boolean nextSliceMayAddDimensionTypeJson() {
        return false;
    }

    public static boolean nextSliceMayCreateActiveCaveniaLevel() {
        return false;
    }

    public static boolean nextSliceMayAddWorldgenResources() {
        return false;
    }

    public static boolean nextSliceMayAddAccessOrTeleport() {
        return false;
    }

    public static boolean nextSliceMayAddSpawning() {
        return false;
    }

    public static boolean nextSliceMayRegisterCavemanEntity() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationSelectedNextDecisionMatches() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectedNextDecision()
            == CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationNextDecision
                .PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_GO_NO_GO_NEXT;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
        return false;
    }

    public static String candidateKeyOutputShape() {
        return CANDIDATE_KEY_OUTPUT_SHAPE;
    }

    public static boolean candidateKeysRemainStringOnly() {
        return true;
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static String candidateKeyForSampleOrigin() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleOrigin();
    }

    public static String candidateKeyForSampleMixed() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleMixed();
    }

    public static String candidateKeyForSampleNegative() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleNegative();
    }

    public static String expectedSampleOriginCandidateKey() {
        return EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY;
    }

    public static String expectedSampleMixedCandidateKey() {
        return EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY;
    }

    public static String expectedSampleNegativeCandidateKey() {
        return EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY;
    }

    public static boolean sampleKeysExistInInventory() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.sampleKeysExistInInventory();
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();
    }

    public static boolean holderConversionRuntimeReady() {
        return false;
    }

    public static boolean holderResolutionReady() {
        return false;
    }

    public static boolean resource\u004cocationConversionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static String fallbackLegacyBiomeName() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
    }

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
    }

    public static boolean allCurrentReadinessLayersReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.allCurrentReadinessLayersReady();
    }

    public static int runtimeReadyLayerCount() {
        return 0;
    }

    public static int consolidatedReadinessLayerCount() {
        return 6;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
    }

    public static boolean designatedSubclassReady() {
        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();
    }

    public static boolean designatedSubclassRuntimeReady() {
        return false;
    }

    public static boolean runtimeHolderReturnReady() {
        return false;
    }

    public static boolean collectPossibleBiomesImplementationReady() {
        return false;
    }

    public static boolean getNoiseBiomeImplementationReady() {
        return false;
    }

    public static boolean usableRuntimeBiomeSourceReady() {
        return false;
    }

    public static boolean usableCodecImplementationReady() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean biomeSourceTypeRegistered() {
        return false;
    }

    public static boolean normalRuntimeConstructionAllowed() {
        return false;
    }

    public static boolean unsupportedMethodStubsOnly() {
        return true;
    }

    public static boolean collectPossibleBiomesStubbed() {
        return true;
    }

    public static boolean getNoiseBiomeStubbed() {
        return true;
    }

    public static boolean codecMethodStubbed() {
        return true;
    }

    public static boolean dimensionBindingReady() {
        return false;
    }

    public static int selectedSurfaceReadinessItemCount() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceReadinessAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceReadinessAction,
            false,
            false,
            true,
            blocker
        );
    }
}
