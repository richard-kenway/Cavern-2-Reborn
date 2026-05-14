package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision {
    private static final CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision
        SELECTED_DECISION =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision
                .PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract>
        GUARDRAILS = List.of(
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_PURE_NON_RUNTIME_BRIDGE_NEXT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision",
                true,
                "The next slice may add only a pure non-runtime selector-to-weighted-candidate bridge helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_SIGNED_INT_SELECTOR_INPUT_NEXT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness",
                true,
                "The next slice may accept only the already-pinned signed int selector input."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_EXISTING_WEIGHTED_SELECTOR_OR_ADAPTER_NEXT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness",
                true,
                "The next slice may use only the existing pure weighted selector or adapter path."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_OUTPUT_NEXT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness",
                true,
                "The next slice may return only a string modern biome candidate key."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_DATA_NEXT,
                "CaveniaLegacyToModernBiomeKeyMappings",
                true,
                "The next slice may use only the existing candidate inventory data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_MINECRAFT_RUNTIME_API_IMPORTS,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision",
                false,
                "The next slice may not import Minecraft runtime APIs into the pure bridge helper."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the pure bridge helper into getNoiseBiome."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep getNoiseBiome unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep collectPossibleBiomes unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_REGISTRY_LOOKUP_ACCESS,
                "CaveniaRegistryLookupReadiness",
                false,
                "Registry lookup access must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_HOLDER_RESOLUTION,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                false,
                "Holder resolution must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                false,
                "Resource-location and resource-key conversion must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_FALLBACK_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                false,
                "Fallback implementation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_USABLE_CODEC_OR_REGISTRATION,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                false,
                "Usable codec behavior and registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_DIMENSION_ACTIVATION,
                "CaveniaActivationReadinessMatrix",
                false,
                "Dimension activation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                "CaveniaActivationReadinessMatrix",
                false,
                "Worldgen, access and spawning must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED,
                "CaveniaRuntimeBiomeSource",
                false,
                "The guarded runtime biome-source methods must remain unsupported in the next slice."
            )
        );

    private CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForPureNonRuntimeBridgeNext() {
        return true;
    }

    public static boolean decisionIsImplementationOnlyForNextSlice() {
        return true;
    }

    public static boolean selectorToWeightedCandidateBridgeImplementedInThisSlice() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
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

    public static List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract>
        guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail>
        guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract>
        guardrailFor(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail guardrail) {
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

    public static int allowedNextSlicePureBridgeActionCount() {
        return 5;
    }

    public static int allowedNextSliceRuntimeActionCount() {
        return 0;
    }

    public static boolean anyRuntimeActionAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddPureNonRuntimeSelectorToWeightedCandidateBridge() {
        return true;
    }

    public static boolean nextSliceMayUseSignedIntSelectorInput() {
        return true;
    }

    public static boolean nextSliceMayUseExistingWeightedSelectorOrAdapter() {
        return true;
    }

    public static boolean nextSliceMayReturnStringCandidateKey() {
        return true;
    }

    public static boolean nextSliceMayUseCandidateInventoryData() {
        return true;
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

    public static boolean bridgeReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .\u0073electorToWeightedCandidateBridgeReadinessReady();
    }

    public static boolean bridgeReadinessRuntimeReady() {
        return false;
    }

    public static boolean bridgeImplementationReady() {
        return false;
    }

    public static boolean bridgeRuntimeReady() {
        return false;
    }

    public static String selectorInputOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .\u0073electorInputOutputShape();
    }

    public static String candidateKeyOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeyOutputShape();
    }

    public static int weightedSelectionTotalWeight() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.weightedSelectionTotalWeight();
    }

    public static int candidateEntryCount() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateEntryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeysStillStringOnly();
    }

    public static boolean weightedSelectorCompatibilityReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.weightedSelectorCompatibilityReady();
    }

    public static boolean adapterCompatibilityReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .\u0061dapterCompatibilityReady();
    }

    public static boolean adapterRuntimeReady() {
        return false;
    }

    public static boolean selectorInputAlgorithmReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.\u0073electorInputAlgorithmReady();
    }

    public static boolean selectorInputAlgorithmRuntimeReady() {
        return false;
    }

    public static int deriveSelectorInputSampleOrigin() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.deriv\u0065SelectorInputSampleOrigin();
    }

    public static int deriveSelectorInputSampleMixed() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.deriv\u0065SelectorInputSampleMixed();
    }

    public static int deriveSelectorInputSampleNegative() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.deriv\u0065SelectorInputSampleNegative();
    }

    public static boolean bridgeGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
            .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext();
    }

    public static boolean bridgeGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorInputAlgorithmConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .\u0073electorInputAlgorithmConsolidationReady();
    }

    public static boolean selectorInputAlgorithmConsolidationRuntimeReady() {
        return false;
    }

    public static boolean implementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.implementationGoNoGoDecisionReady();
    }

    public static boolean implementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
    }

    public static boolean allCurrentReadinessLayersReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.allCurrentReadinessLayersReady();
    }

    public static int runtimeReadyLayerCount() {
        return 0;
    }

    public static int consolidatedReadinessLayerCount() {
        return 6;
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.possibleBiomesReadinessReady();
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static String fallbackLegacyBiomeName() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackCandidateModernBiomeKey();
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.holderConversionReadinessReady();
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

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.codecMethodShapeStubReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
    }

    public static boolean designatedSubclassReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.designatedSubclassReady();
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

    private static CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSlicePureBridgeAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSlicePureBridgeAction,
            false,
            true,
            blocker
        );
    }
}
