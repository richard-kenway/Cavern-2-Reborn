package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision {
    private static final String CANDIDATE_KEY_INPUT_SHAPE = "string modern biome candidate key";
    private static final String DESIGNATED_CONVERTER_SIMPLE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter";
    private static final String DESIGNATED_CONVERTER_FILE_NAME = DESIGNATED_CONVERTER_SIMPLE_NAME + ".java";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision
        SELECTED_DECISION =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision
                .PROCEED_WITH_GUARDED_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract>
        GUARDRAILS = List.of(
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_GUARDED_CONVERSION_HELPER_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision",
                true,
                "The next slice may add only a guarded candidate-key-to-holder conversion helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_INPUT_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
                true,
                "The next slice may keep using only the existing string modern biome candidate-key input."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_RESOURCE_\u004cOCATION_PARSING_IN_DESIGNATED_HELPER_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
                true,
                "The next slice may parse resource-location values only inside the designated helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_RESOURCE_KEY_CONSTRUCTION_IN_DESIGNATED_HELPER_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
                true,
                "The next slice may construct biome resource keys only inside the designated helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_BIOME_REGISTRY_LOOKUP_IN_DESIGNATED_HELPER_NEXT,
                "CaveniaRegistryLookupReadiness",
                true,
                "The next slice may use biome registry lookup only inside the designated helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_HOLDER_RESOLUTION_IN_DESIGNATED_HELPER_NEXT,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                true,
                "The next slice may resolve holders only inside the designated helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .ALLOW_MISSING_OR_FALLBACK_HANDLING_IN_DESIGNATED_HELPER_NEXT,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                true,
                "The next slice may handle missing-or-fallback conversion only inside the designated helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the designated helper into getNoiseBiome."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep getNoiseBiome unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the designated helper into collectPossibleBiomes."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep collectPossibleBiomes unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                false,
                "Codec behavior and biome-source type registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_GENERATOR_REGISTRATION,
                "CaveniaGeneratorRegistrationBoundary",
                false,
                "Generator registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_DIMENSION_ACTIVATION,
                "CaveniaActivationReadinessMatrix",
                false,
                "Dimension activation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                "CaveniaActivationReadinessMatrix",
                false,
                "Worldgen, access and spawning must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED,
                "CaveniaRuntimeBiomeSource",
                false,
                "Runtime methods must remain unsupported even if a guarded converter helper is added next."
            )
        );

    private CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext() {
        return true;
    }

    public static boolean decisionIsImplementationOnlyForNextSlice() {
        return true;
    }

    public static boolean candidateKeyToHolderConversionImplementedInThisSlice() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionRuntimeReady() {
        return false;
    }

    public static boolean runtimeWiringAllowedInThisSlice() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract>
        guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail>
        guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract>
        guardrailFor(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail guardrail) {
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

    public static int allowedNextSliceConversionActionCount() {
        return 7;
    }

    public static int allowedNextSliceRuntimeWiringActionCount() {
        return 0;
    }

    public static int allowedNextSliceActivationActionCount() {
        return 0;
    }

    public static boolean anyRuntimeWiringAllowedByGuardrails() {
        return false;
    }

    public static boolean anyActivationAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddGuardedConversionHelper() {
        return true;
    }

    public static boolean nextSliceMayUseStringCandidateKeyInput() {
        return true;
    }

    public static boolean nextSliceMayParseResource\u004cocationInDesignatedHelper() {
        return true;
    }

    public static boolean nextSliceMayConstructResourceKeyInDesignatedHelper() {
        return true;
    }

    public static boolean nextSliceMayUseBiomeRegistryLookupInDesignatedHelper() {
        return true;
    }

    public static boolean nextSliceMayResolveHolderInDesignatedHelper() {
        return true;
    }

    public static boolean nextSliceMayHandleMissingOrFallbackInDesignatedHelper() {
        return true;
    }

    public static String nextSliceDesignatedConverterSimpleName() {
        return DESIGNATED_CONVERTER_SIMPLE_NAME;
    }

    public static String nextSliceDesignatedConverterFileName() {
        return DESIGNATED_CONVERTER_FILE_NAME;
    }

    public static boolean nextSliceMayWireIntoGetNoiseBiome() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
        return false;
    }

    public static boolean nextSliceMayWireIntoCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
        return false;
    }

    public static boolean nextSliceMayRegisterCodec() {
        return false;
    }

    public static boolean nextSliceMayRegisterBiomeSourceType() {
        return false;
    }

    public static boolean nextSliceMayRegisterGenerator() {
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

    public static boolean candidateKeyToHolderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
            .candidateKeyToHolderConversionReadinessReady();
    }

    public static boolean candidateKeyToHolderConversionReadinessRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConverterReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.converterReady();
    }

    public static boolean candidateKeyToHolderConverterRuntimeReady() {
        return false;
    }

    public static boolean conversionImplementationReady() {
        return false;
    }

    public static boolean registryLookupRequiredForRuntimeConversion() {
        return true;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean holderResolutionReady() {
        return false;
    }

    public static boolean holderReturnReady() {
        return false;
    }

    public static boolean resource\u004cocationConversionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean fallbackRuntimeReady() {
        return false;
    }

    public static String candidateKeyInputShape() {
        return CANDIDATE_KEY_INPUT_SHAPE;
    }

    public static boolean candidateKeysRemainStringOnly() {
        return true;
    }

    public static int candidateEntryCount() {
        return 14;
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
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

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static String fallbackLegacyBiomeName() {
        return "PLAINS";
    }

    public static String fallbackCandidateModernBiomeKey() {
        return "minecraft:plains";
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
        return 68;
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return 46;
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return 46;
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

    private static CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceConversionAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceConversionAction,
            false,
            false,
            true,
            blocker
        );
    }
}
