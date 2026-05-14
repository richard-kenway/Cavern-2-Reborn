package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision {
    private static final CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision SELECTED_DECISION =
        CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision
            .PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract> GUARDRAILS = List.of(
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision",
            true,
            "The next slice may add selector-to-weighted-candidate bridge readiness data only."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_INPUT_SURFACE_POLICY_PINNING_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm",
            true,
            "The next slice may pin only the future selector-input bridge surface policy."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_WEIGHTED_SELECTOR_QUERY_POLICY_PINNING_NEXT,
            "CaveniaWeightedBiomeSelectionAlgorithm",
            true,
            "The next slice may pin only the future weighted-selector query policy."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_ADAPTER_QUERY_POLICY_PINNING_NEXT,
            "CaveniaBiomeSelectionAdapterContract",
            true,
            "The next slice may pin only the future adapter query policy."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_CANDIDATE_KEY_OUTPUT_POLICY_PINNING_NEXT,
            "CaveniaLegacyToModernBiomeKeyMappings",
            true,
            "The next slice may pin only the future string candidate-key output policy."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision",
            false,
            "The next slice may not implement selector-to-weighted-candidate bridge behavior."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_WEIGHTED_SELECTOR_CALL_WITH_DERIVED_INPUT,
            "CaveniaWeightedBiomeSelectionAlgorithm",
            false,
            "The next slice may not call the weighted selector with derived selector input."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_ADAPTER_CALL_WITH_DERIVED_INPUT,
            "CaveniaBiomeSelectionAdapterContract",
            false,
            "The next slice may not call the adapter with derived selector input."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_GET_NOISE_BIOME_WIRING,
            "CaveniaRuntimeBiomeSource",
            false,
            "The next slice may not wire bridge data into the guarded getNoiseBiome path."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            false,
            "The guarded subclass must keep getNoiseBiome unsupported in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            false,
            "The guarded subclass must keep collectPossibleBiomes unsupported in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            false,
            "Registry lookup access must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_HOLDER_RESOLUTION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            false,
            "Holder resolution must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            false,
            "Resource-location and resource-key conversion must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_FALLBACK_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            false,
            "Fallback implementation must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_USABLE_CODEC_OR_REGISTRATION,
            "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
            false,
            "Usable codec behavior and registration must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_DIMENSION_ACTIVATION,
            "CaveniaActivationReadinessMatrix",
            false,
            "Dimension activation must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            "CaveniaActivationReadinessMatrix",
            false,
            "Worldgen, access and spawning must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .KEEP_SELECTOR_INPUT_ALGORITHM_INERT,
            "CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation",
            false,
            "The current selector-input algorithm surface must remain inert and non-runtime in the next slice."
        )
    );

    private CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext() {
        return true;
    }

    public static boolean decisionIsReadinessOnlyForNextSlice() {
        return true;
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessImplementedInThisSlice() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .\u0073electorToWeightedCandidateBridgeReadinessReady();
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeBridgeNext();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {
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

    public static List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail guardrail
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
        return 5;
    }

    public static int allowedNextSliceBridgeImplementationActionCount() {
        return 0;
    }

    public static int allowedNextSliceRuntimeActionCount() {
        return 0;
    }

    public static boolean anyBridgeImplementationAllowedByGuardrails() {
        return false;
    }

    public static boolean anyRuntimeActionAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddSelectorToWeightedCandidateBridgeReadiness() {
        return true;
    }

    public static boolean nextSliceMayPinSelectorInputSurfacePolicy() {
        return true;
    }

    public static boolean nextSliceMayPinWeightedSelectorQueryPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinAdapterQueryPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinCandidateKeyOutputPolicy() {
        return true;
    }

    public static boolean nextSliceMayImplementSelectorToWeightedCandidateBridge() {
        return false;
    }

    public static boolean nextSliceMayCallWeightedSelectorWithDerivedInput() {
        return false;
    }

    public static boolean nextSliceMayCallAdapterWithDerivedInput() {
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

    public static boolean selectorInputAlgorithmConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.\u0073electorInputAlgorithmConsolidationReady();
    }

    public static boolean selectorInputAlgorithmConsolidationRuntimeReady() {
        return false;
    }

    public static boolean selectorInputAlgorithmSelectedNextDecisionMatches() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.\u0073electedNextDecision()
            == CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationNextDecision
                .PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_GO_NO_GO_NEXT;
    }

    public static boolean selectorInputAlgorithmReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.\u0073electorInputDerivationAlgorithmReady();
    }

    public static boolean selectorInputAlgorithmRuntimeReady() {
        return false;
    }

    public static String selectorInputOutputShape() {
        return "signed int selector input";
    }

    public static boolean weightedSelectorCompatibilityReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static int weightedSelectionTotalWeight() {
        return 675;
    }

    public static boolean adapterCompatibilityReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.ad\u0061pterCompatibilityReady();
    }

    public static boolean adapterRuntimeReady() {
        return false;
    }

    public static int candidateEntryCount() {
        return 14;
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return true;
    }

    public static String fallbackLegacyBiomeName() {
        return "PLAINS";
    }

    public static String fallbackCandidateModernBiomeKey() {
        return "minecraft:plains";
    }

    public static boolean implementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeAlgorithmNext();
    }

    public static boolean implementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.\u0073electorInputDerivationReadinessReady();
    }

    public static boolean selectorInputDerivationReadinessRuntimeReady() {
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

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
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
        return 68;
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return 46;
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return 46;
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

    private static CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceReadinessAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract(
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
