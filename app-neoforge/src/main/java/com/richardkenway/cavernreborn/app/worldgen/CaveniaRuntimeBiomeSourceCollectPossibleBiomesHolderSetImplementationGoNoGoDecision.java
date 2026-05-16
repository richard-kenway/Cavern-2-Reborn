package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision {
    private static final String DESIGNATED_BUILDER_SIMPLE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder";
    private static final String DESIGNATED_BUILDER_FILE_NAME = DESIGNATED_BUILDER_SIMPLE_NAME + ".java";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationNextDecision
        SELECTED_DECISION =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationNextDecision
                .PROCEED_WITH_GUARDED_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_BUILDER_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract>
        GUARDRAILS = List.of(
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_GUARDED_PRE_RESOLVED_HOLDER_SET_BUILDER_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision",
                true,
                "The next slice may add only a guarded pre-resolved holder-set builder helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_INPUT_NEXT,
                "CaveniaLegacyToModernBiomeKeyMappings",
                true,
                "The next slice may use the current string candidate inventory as builder input."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_DESIGNATED_CONVERTER_DEPENDENCY_NEXT,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation",
                true,
                "The next slice may depend on the designated candidate-key-to-holder converter."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_LOOKUP_PROVIDER_PARAMETER_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
                true,
                "The next slice may require caller-provided biome lookup/provider input."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_RESOLVED_HOLDER_DEDUPLICATION_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
                true,
                "The next slice may deduplicate resolved holders while building a pre-resolved holder set."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_LEGACY_ORDER_PRESERVATION_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
                true,
                "The next slice may preserve legacy candidate inventory order for first successful resolved holders."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_FALLBACK_IF_EMPTY_NEXT,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                true,
                "The next slice may apply fallback-if-empty with minecraft:plains after all candidate conversions fail."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .ALLOW_MISSING_INVALID_UNRESOLVED_SKIP_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
                true,
                "The next slice may skip missing, invalid or unresolved candidate keys during builder aggregation."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the guarded builder into collectPossibleBiomes."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep collectPossibleBiomes unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the guarded builder into getNoiseBiome."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep getNoiseBiome unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_RUNTIME_CONSTRUCTION_OR_FACTORY,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not add runtime construction or factory behavior."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                false,
                "Codec behavior and biome-source type registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_GENERATOR_REGISTRATION,
                "CaveniaGeneratorRegistrationBoundary",
                false,
                "Generator registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_DIMENSION_ACTIVATION,
                "CaveniaActivationReadinessMatrix",
                false,
                "Dimension activation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                "CaveniaActivationReadinessMatrix",
                false,
                "Worldgen, access and spawning must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED,
                "CaveniaRuntimeBiomeSource",
                false,
                "Runtime methods must remain unsupported even if a guarded builder helper is added next."
            )
        );

    private CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationNextDecision
        selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext() {
        return true;
    }

    public static boolean decisionIsImplementationOnlyForNextSlice() {
        return true;
    }

    public static boolean collectPossibleBiomesHolderSetBuilderImplementedInThisSlice() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetBuilderReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.builderReady();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderRuntimeReady() {
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

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract>
        guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail>
        guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract>
        guardrailFor(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail guardrail) {
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

    public static int allowedNextSliceBuilderActionCount() {
        return 8;
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

    public static boolean nextSliceMayAddGuardedPreResolvedHolderSetBuilder() {
        return true;
    }

    public static boolean nextSliceMayUseCandidateInventoryInput() {
        return true;
    }

    public static boolean nextSliceMayUseDesignatedConverterDependency() {
        return true;
    }

    public static boolean nextSliceMayRequireLookupProviderParameter() {
        return true;
    }

    public static boolean nextSliceMayDeduplicateResolvedHolders() {
        return true;
    }

    public static boolean nextSliceMayPreserveLegacyOrder() {
        return true;
    }

    public static boolean nextSliceMayApplyFallbackIfEmpty() {
        return true;
    }

    public static boolean nextSliceMaySkipMissingInvalidUnresolvedCandidates() {
        return true;
    }

    public static String nextSliceDesignatedBuilderSimpleName() {
        return DESIGNATED_BUILDER_SIMPLE_NAME;
    }

    public static String nextSliceDesignatedBuilderFileName() {
        return DESIGNATED_BUILDER_FILE_NAME;
    }

    public static boolean nextSliceMayWireBuilderIntoCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayImplementCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayWireBuilderIntoGetNoiseBiome() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
        return false;
    }

    public static boolean nextSliceMayAddRuntimeConstructionOrFactory() {
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

    public static boolean collectPossibleBiomesHolderSetReadinessReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness
            .collectPossibleBiomesHolderSetReadinessReady();
    }

    public static boolean collectPossibleBiomesHolderSetReadinessRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetImplementationReady() {
        return false;
    }

    public static boolean lookupProviderRequiredForRuntimeHolderSet() {
        return true;
    }

    public static boolean lookupProviderAvailableInThisSlice() {
        return false;
    }

    public static String futureHolderSetOutputShape() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureHolderSetOutputShape();
    }

    public static String futureStreamReturnBoundary() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureStreamReturnBoundary();
    }

    public static String futureDeduplicationPolicy() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureDeduplicationPolicy();
    }

    public static String futureOrderingPolicy() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureOrderingPolicy();
    }

    public static String futureFallbackIfEmptyPolicy() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureFallbackIfEmptyPolicy();
    }

    public static String futureMissingCandidatePolicy() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureMissingCandidatePolicy();
    }

    public static boolean candidateKeyToHolderConverterConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .candidateKeyToHolderConverterConsolidationReady();
    }

    public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {
        return false;
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
        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaRuntimeBiomeSourceSkeleton.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRuntimeBiomeSourceSkeleton.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRuntimeBiomeSourceSkeleton.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceBuilderAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceBuilderAction,
            false,
            false,
            true,
            blocker
        );
    }
}
