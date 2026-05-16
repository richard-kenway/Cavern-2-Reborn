package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceReadinessChainConsolidation {
    private static final CaveniaRuntimeBiomeSourceReadinessChainNextDecision SELECTED_NEXT_DECISION =
        CaveniaRuntimeBiomeSourceReadinessChainNextDecision.PROCEED_WITH_SELECTOR_INPUT_DERIVATION_GO_NO_GO_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceReadinessChainEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.DESIGNATED_SUBCLASS_STUB,
            "CaveniaRuntimeBiomeSource",
            "The guarded designated subclass stub is present as the only allowed runtime-biome-source subclass surface.",
            "The designated subclass remains guarded, unregistered, non-activating and runtime-blocked.",
            true,
            false,
            "The designated subclass must remain an unsupported guarded stub in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.CODEC_METHOD_SHAPE_STUB,
            "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
            "The codec method shape stub is pinned and ready as the first consolidated runtime-biome-source readiness layer.",
            "Runtime codec behavior remains blocked even though the codec method shape stub is ready.",
            true,
            true,
            "A usable codec implementation, static codec field and registration remain absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.HOLDER_RESOURCE_KEY_CONVERSION_READINESS,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "The holder/resource-key conversion readiness layer is pinned and ready as the second consolidated readiness layer.",
            "Runtime holder conversion remains blocked because lookup access, conversion implementation and holder resolution are absent.",
            true,
            true,
            "Runtime holder/resource-key conversion remains deferred."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.FALLBACK_POLICY_READINESS,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "The fallback policy readiness layer is pinned and ready as the third consolidated readiness layer.",
            "Runtime fallback behavior remains blocked because there is still no runtime selection, conversion or holder resolution path.",
            true,
            true,
            "Runtime fallback implementation remains absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.POSSIBLE_BIOMES_READINESS,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "The possible-biomes readiness layer is pinned and ready as the fourth consolidated readiness layer.",
            "Runtime possible-biomes behavior remains blocked because there is still no runtime holder stream.",
            true,
            true,
            "Runtime possible-biomes implementation remains absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.NOISE_BIOME_SELECTION_READINESS,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            "The noise-biome selection readiness layer is pinned and ready as the fifth runtime-facing readiness layer after the possible-biomes contract.",
            "Runtime noise-biome behavior remains blocked because deterministic selector-input derivation, conversion and holder-return behavior are absent.",
            true,
            true,
            "Runtime noise-biome implementation remains absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.SELECTOR_INPUT_DERIVATION_NOT_PINNED,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            "Selector-input derivation is the first missing decision after the current guarded readiness chain.",
            "Deterministic coordinate/climate-to-weight-value derivation remains deferred and no runtime selector-input behavior exists.",
            false,
            true,
            "Selector-input derivation is not pinned and must stay decision-only in the next slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.REGISTRY_LOOKUP_ACCESS_BLOCKED,
            "CaveniaRegistryLookupReadiness",
            "Registry lookup access is a known blocked runtime surface in the consolidated chain.",
            "Runtime lookup access remains absent and continues to block holder-backed runtime behavior.",
            true,
            false,
            "Registry lookup access stays intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.HOLDER_RESOLUTION_BLOCKED,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Holder resolution is a known blocked runtime surface in the consolidated chain.",
            "Runtime holder resolution remains absent and blocks possible-biomes and noise-biome runtime behavior.",
            true,
            false,
            "Holder resolution remains intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.RESOURCE_LOCATION_CONVERSION_BLOCKED,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Resource-location conversion is a known blocked runtime surface in the consolidated chain.",
            "Runtime resource-location conversion remains absent and blocks the future conversion path.",
            true,
            false,
            "Resource-location conversion remains intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.RESOURCE_KEY_CONVERSION_BLOCKED,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Resource-key conversion is a known blocked runtime surface in the consolidated chain.",
            "Runtime resource-key conversion remains absent and blocks the future holder-resolution path.",
            true,
            false,
            "Resource-key conversion remains intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.USABLE_RUNTIME_METHODS_BLOCKED,
            "CaveniaRuntimeBiomeSource",
            "The runtime-biome-source methods are pinned only as unsupported stubs in the consolidated chain.",
            "The guarded subclass still cannot provide usable codec, possible-biomes or noise-biome runtime behavior.",
            true,
            false,
            "All runtime methods must remain unsupported in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.USABLE_CODEC_IMPLEMENTATION_BLOCKED,
            "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
            "Usable codec implementation remains a known blocked runtime surface in the consolidated chain.",
            "A usable codec implementation is still absent and blocks runtime construction and activation.",
            true,
            false,
            "Usable codec implementation remains intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.CODEC_AND_TYPE_REGISTRATION_BLOCKED,
            "CaveniaAdapterCodecRegistrationReadiness",
            "Codec registration and biome-source type registration remain known blocked runtime surfaces in the consolidated chain.",
            "No codec registration or biome-source type registration exists for the guarded subclass.",
            true,
            false,
            "Registration remains intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.DIMENSION_ACTIVATION_BLOCKED,
            "CaveniaActivationReadinessMatrix",
            "Dimension activation remains a known blocked runtime surface in the consolidated chain.",
            "No dimension JSON, dimension-type JSON, level binding or active Cavenia level exists.",
            true,
            false,
            "Dimension activation remains intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.WORLDGEN_ACCESS_SPAWNING_BLOCKED,
            "CaveniaActivationReadinessMatrix",
            "Worldgen, access and spawning remain known blocked runtime surfaces in the consolidated chain.",
            "No worldgen resources, access path, teleport path, spawn host or active spawning exists for Cavenia.",
            true,
            false,
            "Worldgen, access and spawning remain intentionally absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceReadinessChainComponent.NEXT_DECISION_SELECTED,
            "CaveniaRuntimeBiomeSourceReadinessChainConsolidation",
            "The consolidated chain selects the next safe branch as a selector-input-derivation go/no-go decision.",
            "The next slice may add only a selector-input-derivation go/no-go decision while all runtime behavior remains blocked.",
            true,
            true,
            "The next branch must remain decision-only unless a later prompt explicitly allows implementation."
        )
    );

    private CaveniaRuntimeBiomeSourceReadinessChainConsolidation() {
    }

    public static List<CaveniaRuntimeBiomeSourceReadinessChainEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceReadinessChainComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceReadinessChainEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceReadinessChainEntry> entryFor(
        CaveniaRuntimeBiomeSourceReadinessChainComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean readinessChainConsolidationReady() {
        return true;
    }

    public static boolean allCurrentReadinessLayersReady() {
        return designatedSubclassReady()
            && codecMethodShapeStubReady()
            && holderConversionReadinessReady()
            && fallbackPolicyReadinessReady()
            && possibleBiomesReadinessReady()
            && noiseBiomeSelectionReadinessReady();
    }

    public static int runtimeReadyLayerCount() {
        return 0;
    }

    public static int consolidatedReadinessLayerCount() {
        return 6;
    }

    public static CaveniaRuntimeBiomeSourceReadinessChainNextDecision selectedNextDecision() {
        return SELECTED_NEXT_DECISION;
    }

    public static boolean selectorInputDerivationGoNoGoIsNext() {
        return true;
    }

    public static boolean selectorInputDerivationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();
    }

    public static boolean selectorInputDerivationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady();
    }

    public static boolean selectorInputDerivationReadinessRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeAlgorithmNext();
    }

    public static boolean selectorInputDerivationImplementationGoNoGoRuntimeReady() {
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

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();
    }

    public static boolean candidateKeyToHolderConversionGoNoGoRuntimeReady() {
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

    public static boolean candidateKeyToHolderConverterConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .candidateKeyToHolderConverterConsolidationReady();
    }

    public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
            .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext();
    }

    public static boolean collectPossibleBiomesHolderSetGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetReadinessReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness
            .collectPossibleBiomesHolderSetReadinessReady();
    }

    public static boolean collectPossibleBiomesHolderSetReadinessRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
            .decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext();
    }

    public static boolean collectPossibleBiomesHolderSetImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationReadinessPinned() {
        return false;
    }

    public static boolean selectorInputDerivationImplementationReady() {
        return false;
    }

    public static boolean selectorInputDerivationRuntimeReady() {
        return false;
    }

    public static boolean nextSliceMayAddSelectorInputDerivationGoNoGoDecision() {
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

    public static boolean designatedSubclassReady() {
        return true;
    }

    public static boolean designatedSubclassRuntimeReady() {
        return false;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
    }

    public static boolean holderConversionRuntimeReady() {
        return false;
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
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

    public static boolean holderResolutionReady() {
        return false;
    }

    public static boolean resourceLocationConversionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
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

    public static boolean dimensionBindingReady() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
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

    private static CaveniaRuntimeBiomeSourceReadinessChainEntry entry(
        CaveniaRuntimeBiomeSourceReadinessChainComponent component,
        String sourceContractName,
        String consolidatedReadiness,
        String runtimeState,
        boolean readinessReady,
        boolean nextDecisionInput,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceReadinessChainEntry(
            component,
            sourceContractName,
            consolidatedReadiness,
            runtimeState,
            readinessReady,
            false,
            true,
            nextDecisionInput,
            blocker
        );
    }
}
