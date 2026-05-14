package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision {
    private static final CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision SELECTED_DECISION =
        CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision
            .PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_INPUT_DERIVATION_ALGORITHM_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract> GUARDRAILS = List.of(
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_PURE_NON_RUNTIME_ALGORITHM_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision",
            true,
            "The next slice may add only a pure non-runtime selector-input derivation algorithm."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_INTEGER_COORDINATE_INPUTS_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            true,
            "The next slice may use integer coordinate inputs only inside a pure non-runtime helper."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_SIGNED_INT_SELECTOR_OUTPUT_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            true,
            "The next slice may return only a signed int selector input value."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_EXISTING_SELECTOR_NORMALIZATION_NEXT,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            true,
            "The next slice may keep normalization delegated to the existing selector or adapter path."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_CLIMATE_SAMPLER_METHOD_CALLS,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            false,
            "The next slice may not call climate-sampler methods while the pure algorithm stays non-runtime."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_MINECRAFT_RUNTIME_API_IMPORTS,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision",
            false,
            "The next slice may not import Minecraft runtime APIs while implementing the pure algorithm."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_GET_NOISE_BIOME_WIRING,
            "CaveniaRuntimeBiomeSource",
            false,
            "The next slice may not wire the pure algorithm into the guarded getNoiseBiome path."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            false,
            "The guarded subclass must keep getNoiseBiome unsupported in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            false,
            "The guarded subclass must keep collectPossibleBiomes unsupported in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            false,
            "Registry lookup access must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_HOLDER_RESOLUTION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            false,
            "Holder resolution must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            false,
            "Resource-location and resource-key conversion must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_FALLBACK_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            false,
            "Fallback implementation must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_USABLE_CODEC_OR_REGISTRATION,
            "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
            false,
            "Usable codec behavior and registration must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_RANDOM_OR_MUTABLE_STATE,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            false,
            "Random, wall-clock, mutable global state and side effects must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_DIMENSION_ACTIVATION,
            "CaveniaActivationReadinessMatrix",
            false,
            "Dimension activation must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            "CaveniaActivationReadinessMatrix",
            false,
            "Worldgen, access and spawning must remain absent in the next slice."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.KEEP_READINESS_CHAIN_INERT,
            "CaveniaRuntimeBiomeSourceReadinessChainConsolidation",
            false,
            "The guarded readiness chain must remain inert and non-activating in the next slice."
        )
    );

    private CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForPureNonRuntimeAlgorithmNext() {
        return true;
    }

    public static boolean decisionIsImplementationOnlyForNextSlice() {
        return true;
    }

    public static boolean selectorInputDerivationAlgorithmImplementedInThisSlice() {
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

    public static boolean selectorInputDerivationAlgorithmReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady();
    }

    public static boolean selectorInputDerivationAlgorithmRuntimeReady() {
        return false;
    }

    public static boolean selectorInputAlgorithmConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputAlgorithmConsolidationReady();
    }

    public static boolean selectorInputAlgorithmConsolidationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
            .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext();
    }

    public static boolean selectorToWeightedCandidateBridgeGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .selectorToWeightedCandidateBridgeReadinessReady();
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

    public static boolean runtimeApiAllowedInThisSlice() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail guardrail
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

    public static int allowedNextSlicePureAlgorithmActionCount() {
        return 4;
    }

    public static int allowedNextSliceRuntimeActionCount() {
        return 0;
    }

    public static boolean anyRuntimeActionAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddPureNonRuntimeSelectorInputAlgorithm() {
        return true;
    }

    public static boolean nextSliceMayUseIntegerCoordinateInputs() {
        return true;
    }

    public static boolean nextSliceMayReturnSignedIntSelectorInput() {
        return true;
    }

    public static boolean nextSliceMayDelegateNormalizationToExistingSelectorOrAdapter() {
        return true;
    }

    public static boolean nextSliceMayCallClimateSamplerMethods() {
        return false;
    }

    public static boolean nextSliceMayImportMinecraftRuntimeApis() {
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

    public static boolean nextSliceMayUseRandomOrMutableState() {
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

    public static String selectorInputDerivationOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputOutputShape();
    }

    public static boolean actualDerivationFormulaPinned() {
        return false;
    }

    public static boolean actualDerivationFormulaImplemented() {
        return false;
    }

    public static String methodInputSource() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.methodInputSource();
    }

    public static String coordinateInputPolicy() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.coordinateInputPolicy();
    }

    public static String climateSamplerInputPolicy() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.climateSamplerInputPolicy();
    }

    public static String normalizationPolicy() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.normalizationPolicy();
    }

    public static String deterministicDerivationPolicy() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.deterministicDerivationPolicy();
    }

    public static String pureNonRuntimePolicy() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.pureNonRuntimePolicy();
    }

    public static String noRandomOrMutableStatePolicy() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.randomOrMutableStatePolicy();
    }

    public static boolean goNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision
            .decisionIsGoForSelectorInputDerivationReadinessNext();
    }

    public static boolean goNoGoRuntimeReady() {
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

    public static boolean weightedSelectionAlgorithmReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static int weightedSelectionTotalWeight() {
        return 675;
    }

    public static boolean adapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
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

    private static CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSlicePureAlgorithmAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSlicePureAlgorithmAction,
            false,
            true,
            blocker
        );
    }
}
