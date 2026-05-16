package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision {
    private static final String CANDIDATE_KEY_INPUT_SHAPE = "string modern biome candidate key";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision SELECTED_DECISION =
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision
            .PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract> GUARDRAILS =
        List.of(
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision",
                true,
                "The next slice may add only collectPossibleBiomes holder-set readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_SOURCE_PINNING_NEXT,
                "CaveniaLegacyToModernBiomeKeyMappings",
                true,
                "The next slice may pin the current string candidate inventory as the future possible-biomes source."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_CONVERTER_DEPENDENCY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation",
                true,
                "The next slice may pin the guarded candidate-key-to-holder converter as the future dependency for holder-backed possible-biomes outputs."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_HOLDER_SET_OUTPUT_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
                true,
                "The next slice may pin the future holder-set or list output policy without implementing runtime behavior."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_DEDUPLICATION_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
                true,
                "The next slice may pin the future deduplication policy without implementing runtime holder resolution."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_ORDERING_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
                true,
                "The next slice may pin the future holder-set ordering policy without implementing collectPossibleBiomes."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_FALLBACK_IF_EMPTY_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                true,
                "The next slice may pin the future fallback-if-empty policy without creating runtime fallback behavior."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_MISSING_CANDIDATE_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
                true,
                "The next slice may pin missing or unresolved candidate handling without wiring runtime methods."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .ALLOW_STREAM_RETURN_BOUNDARY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceApiShapeInventory",
                true,
                "The next slice may pin the future stream return boundary without producing a runtime stream."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep collectPossibleBiomes unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the converter into collectPossibleBiomes."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the converter into getNoiseBiome."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep getNoiseBiome unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                false,
                "Codec behavior and biome-source type registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_GENERATOR_REGISTRATION,
                "CaveniaGeneratorRegistrationBoundary",
                false,
                "Generator registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_DIMENSION_ACTIVATION,
                "CaveniaActivationReadinessMatrix",
                false,
                "Dimension activation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                "CaveniaActivationReadinessMatrix",
                false,
                "Worldgen, access and spawning must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED,
                "CaveniaRuntimeBiomeSource",
                false,
                "Runtime methods must remain unsupported even after the next readiness-only slice."
            )
        );

    private CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext() {
        return true;
    }

    public static boolean decisionIsReadinessOnlyForNextSlice() {
        return true;
    }

    public static boolean collectPossibleBiomesHolderSetReadinessReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness
            .collectPossibleBiomesHolderSetReadinessReady();
    }

    public static boolean collectPossibleBiomesHolderSetReadinessRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetReadinessImplementedInThisSlice() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetImplementationReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetRuntimeReady() {
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

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail guardrail
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
        return 9;
    }

    public static int allowedNextSliceImplementationActionCount() {
        return 0;
    }

    public static int allowedNextSliceRuntimeWiringActionCount() {
        return 0;
    }

    public static int allowedNextSliceActivationActionCount() {
        return 0;
    }

    public static boolean anyImplementationAllowedByGuardrails() {
        return false;
    }

    public static boolean anyRuntimeWiringAllowedByGuardrails() {
        return false;
    }

    public static boolean anyActivationAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddCollectPossibleBiomesHolderSetReadiness() {
        return true;
    }

    public static boolean nextSliceMayPinCandidateInventorySource() {
        return true;
    }

    public static boolean nextSliceMayPinConverterDependency() {
        return true;
    }

    public static boolean nextSliceMayPinHolderSetOutputPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinDeduplicationPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinOrderingPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinFallbackIfEmptyPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinMissingCandidatePolicy() {
        return true;
    }

    public static boolean nextSliceMayPinStreamReturnBoundary() {
        return true;
    }

    public static boolean nextSliceMayImplementCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayWireConverterIntoCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayWireConverterIntoGetNoiseBiome() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
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

    public static boolean candidateKeyToHolderConverterConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .candidateKeyToHolderConverterConsolidationReady();
    }

    public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConverterConsolidationSelectedNextDecisionMatches() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.selectedNextDecision()
            == CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision
                .PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_GO_NO_GO_NEXT;
    }

    public static boolean candidateKeyToHolderConverterReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.converterReady();
    }

    public static boolean candidateKeyToHolderConverterRuntimeReady() {
        return false;
    }

    public static boolean holderForCandidateKeyReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKeyReady();
    }

    public static boolean holderForCandidateKeyOrFallbackReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKeyOrFallbackReady();
    }

    public static String candidateKeyInputShape() {
        return CANDIDATE_KEY_INPUT_SHAPE;
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnlyOutsideConverter() {
        return true;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
        return false;
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

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean futurePossibleBiomesSourceIsCandidateInventory() {
        return true;
    }

    public static int futurePossibleBiomesCandidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean futurePossibleBiomesFallbackIfEmptyPinned() {
        return true;
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
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();
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

    private static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceReadinessAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceReadinessAction,
            false,
            false,
            false,
            true,
            blocker
        );
    }
}
