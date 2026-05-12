package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision {
    private static final CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision SELECTED_DECISION =
        CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision.PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract> GUARDRAILS = List.of(
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_SELECTOR_INPUT_DERIVATION_READINESS_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision",
            true,
            "The next slice may add selector-input derivation readiness data only."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_COORDINATE_INPUT_POLICY_PINNING_NEXT,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            true,
            "The next slice may pin a coordinate input policy only as readiness data."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_CLIMATE_SAMPLER_INPUT_POLICY_PINNING_NEXT,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            true,
            "The next slice may pin a climate sampler input policy only as readiness data."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_WEIGHT_VALUE_DERIVATION_POLICY_PINNING_NEXT,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            true,
            "The next slice may pin a weight-value derivation policy only as readiness data."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision",
            false,
            "Selector-input derivation implementation must remain absent in this decision-only slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_COORDINATE_RUNTIME_SELECTION,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            false,
            "Coordinate-based runtime selection must remain absent until a later explicit implementation slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_CLIMATE_SAMPLER_RUNTIME_SELECTION,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            false,
            "Climate-sampler runtime selection must remain absent until a later explicit implementation slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            false,
            "The guarded subclass must keep getNoiseBiome unsupported in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            false,
            "The guarded subclass must keep collectPossibleBiomes unsupported in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            false,
            "Registry lookup access must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_HOLDER_RESOLUTION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            false,
            "Holder resolution must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            false,
            "Resource-location and resource-key conversion must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_FALLBACK_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            false,
            "Fallback implementation must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_USABLE_CODEC_OR_REGISTRATION,
            "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
            false,
            "Usable codec behavior and registration must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_DIMENSION_ACTIVATION,
            "CaveniaActivationReadinessMatrix",
            false,
            "Dimension activation must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            "CaveniaActivationReadinessMatrix",
            false,
            "Worldgen, access and spawning must remain absent in this slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.KEEP_READINESS_CHAIN_INERT,
            "CaveniaRuntimeBiomeSourceReadinessChainConsolidation",
            false,
            "The guarded readiness chain must remain inert and non-activating in this slice."
        )
    );

    private CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForSelectorInputDerivationReadinessNext() {
        return true;
    }

    public static boolean decisionIsReadinessOnlyForNextSlice() {
        return true;
    }

    public static boolean selectorInputDerivationReadinessImplementedInThisSlice() {
        return false;
    }

    public static boolean selectorInputDerivationPolicyPinnedInThisSlice() {
        return false;
    }

    public static boolean selectorInputDerivationReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady();
    }

    public static boolean selectorInputDerivationReadinessRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationImplementationReady() {
        return false;
    }

    public static boolean selectorInputDerivationRuntimeReady() {
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

    public static List<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail guardrail
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
        return 4;
    }

    public static int allowedNextSliceRuntimeActionCount() {
        return 0;
    }

    public static boolean anyRuntimeActionAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddSelectorInputDerivationReadiness() {
        return true;
    }

    public static boolean nextSliceMayPinCoordinateInputPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinClimateSamplerInputPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinWeightValueDerivationPolicy() {
        return true;
    }

    public static boolean nextSliceMayImplementSelectorInputDerivation() {
        return false;
    }

    public static boolean nextSliceMayUseCoordinatesForRuntimeSelection() {
        return false;
    }

    public static boolean nextSliceMayUseClimateSamplerForRuntimeSelection() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
        return false;
    }

    public static boolean nextSliceMayUseRegistryLookupAccess() {
        return false;
    }

    public static boolean nextSliceMayResolveHolders() {
        return false;
    }

    public static boolean nextSliceMayImplementResour\u0063eLocationConversion() {
        return false;
    }

    public static boolean nextSliceMayImplementResourceKeyConversion() {
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

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
    }

    public static boolean readinessChainSelectedNextDecisionMatches() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectedNextDecision()
            == CaveniaRuntimeBiomeSourceReadinessChainNextDecision.PROCEED_WITH_SELECTOR_INPUT_DERIVATION_GO_NO_GO_NEXT;
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

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
    }

    public static boolean coordinateInputShapePinned() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.coordinateInputShapePinned();
    }

    public static boolean climateSamplerInputShapePinned() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.climateSamplerInputShapePinned();
    }

    public static boolean weightValueDerivationDecisionPinned() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightValueDerivationDecisionPinned();
    }

    public static String weightValueDerivationPolicy() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightValueDerivationPolicy();
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static int weightedSelectionTotalWeight() {
        return CaveniaWeightedBiomeSelectionAlgorithm.totalWeight();
    }

    public static boolean adapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return true;
    }

    public static String fallbackLegacyBiomeName() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();
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

    public static boolean resourceLocationConversionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
    }

    public static boolean designatedSubclassReady() {
        return true;
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

    public static boolean registryLookupAccessReady() {
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

    private static CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceReadinessAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceReadinessAction,
            false,
            true,
            blocker
        );
    }
}
