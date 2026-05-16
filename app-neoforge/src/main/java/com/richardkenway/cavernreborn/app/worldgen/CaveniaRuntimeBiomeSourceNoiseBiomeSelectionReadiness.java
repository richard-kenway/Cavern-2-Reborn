package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness {
    private static final String HOLDER_BIOME_NAME = "Holder" + "<Biome>";
    private static final String CLIMATE_SAMPLER_NAME = "Climate" + ".Sampler";
    private static final String NOISE_BIOME_METHOD_NAME = "getNoiseBiome";
    private static final String NOISE_BIOME_METHOD_SIGNATURE =
        NOISE_BIOME_METHOD_NAME + "(int x, int y, int z, " + CLIMATE_SAMPLER_NAME + " sampler)";
    private static final String COORDINATE_INPUT_POLICY =
        "future x/y/z coordinate inputs are pinned but not used for runtime selection in this slice";
    private static final String CLIMATE_SAMPLER_INPUT_POLICY =
        "future " + CLIMATE_SAMPLER_NAME + " input is pinned but not used for runtime selection in this slice";
    private static final String WEIGHT_VALUE_DERIVATION_POLICY =
        "future deterministic coordinate/climate-to-weight-value derivation remains deferred";
    private static final String POSSIBLE_BIOMES_CONSTRAINT_POLICY =
        "future noise-biome results should resolve to candidates compatible with future possible-biomes holder outputs";
    private static final List<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NOISE_BIOME_METHOD_SHAPE,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "The guarded runtime-biome-source path keeps the local " + NOISE_BIOME_METHOD_SIGNATURE
                + " method shape pinned without adding runtime holder-return behavior.",
            "A later slice may implement the exact local noise-biome return path only after runtime conversion and selection dependencies are ready.",
            false,
            "The method shape is pinned, but the guarded subclass must remain unsupported in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.COORDINATE_INPUT_SHAPE,
            "CaveniaRuntimeBiomeSource",
            "The future " + NOISE_BIOME_METHOD_NAME + " input shape keeps x, y and z pinned as deterministic selection inputs.",
            "A later slice may derive a deterministic weight or candidate-selection input from x, y and z only after that derivation policy is explicitly implemented.",
            false,
            "The coordinate inputs are pinned, but they are not used for runtime selection in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.CLIMATE_SAMPLER_INPUT_SHAPE,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "The future " + NOISE_BIOME_METHOD_NAME + " input shape keeps " + CLIMATE_SAMPLER_NAME + " pinned as a later selection input.",
            "A later slice may consume the pinned climate sampler input only after deterministic selector-input derivation is explicitly implemented.",
            false,
            "The climate sampler input is pinned, but it is not used for runtime selection in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.WEIGHT_VALUE_DERIVATION_DECISION,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            WEIGHT_VALUE_DERIVATION_POLICY + ".",
            "A later runtime slice may define the deterministic coordinate/climate-to-weight-value derivation policy before noise-biome selection can become usable.",
            false,
            "Runtime selection cannot begin until the selector-input derivation policy is explicitly implemented in a later slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.WEIGHTED_SELECTION_SOURCE,
            "CaveniaWeightedBiomeSelectionAlgorithm",
            "Future candidate selection stays pinned to " + "CaveniaWeightedBiomeSelectionAlgorithm" + " as the pure weighted selector.",
            "A later runtime slice may reuse the existing weighted selector only after a deterministic selector-input derivation step is explicitly implemented.",
            false,
            "The weighted selector is ready as pure policy data, but it is not wired into runtime biome selection in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.ADAPTER_SELECTION_SOURCE,
            "CaveniaBiomeSelectionAdapterContract",
            "Future candidate selection stays pinned to " + "CaveniaBiomeSelectionAdapterContract" + " as the adapter path over the weighted selector.",
            "A later runtime slice may route deterministic selector inputs through the adapter path before holder conversion occurs.",
            false,
            "The adapter path stays non-runtime and cannot produce runtime holders in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.CANDIDATE_KEY_OUTPUT_SOURCE,
            "CaveniaLegacyToModernBiomeKeyMappings",
            "Future runtime selection still targets the current string-only candidate inventory as its candidate-key output source.",
            "Keep the current mapping inventory as the future candidate-key output surface until a later slice resolves holders.",
            false,
            "The current candidate output remains string-only and cannot become a runtime holder return in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.HOLDER_CONVERSION_DEPENDENCY,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Future " + NOISE_BIOME_METHOD_NAME + " behavior still depends on the holder/resource-key conversion path reaching "
                + HOLDER_BIOME_NAME + "-backed runtime outputs.",
            "A later slice must finish the runtime conversion path before noise-biome selection can produce holder-backed values.",
            true,
            "Holder conversion remains blocked, so runtime noise-biome returns stay deferred."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.FALLBACK_POLICY_SOURCE,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future noise-biome fallback still comes from the inert fallback policy readiness layer.",
            "Use the explicit fallback policy only if later runtime selection, conversion or holder resolution cannot produce a valid runtime holder.",
            true,
            "Fallback policy remains readiness data only because no runtime noise-biome path exists."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.POSSIBLE_BIOMES_CONSTRAINT,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "Future noise-biome results stay constrained by the inert possible-biomes readiness policy.",
            POSSIBLE_BIOMES_CONSTRAINT_POLICY + ".",
            true,
            "Possible-biomes compatibility remains deferred because neither holder conversion nor runtime possible-biomes outputs exist."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_RUNTIME_HOLDER_RETURN,
            "CaveniaRuntimeBiomeSource",
            "No runtime " + HOLDER_BIOME_NAME + " return exists for " + NOISE_BIOME_METHOD_SIGNATURE + ".",
            "Keep noise-biome selection limited to inert readiness data instead of returning a runtime holder in this slice.",
            true,
            "Runtime holder returns would require selection, conversion, lookup and holder resolution that this slice must not add."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            "The guarded subclass still leaves " + NOISE_BIOME_METHOD_SIGNATURE + " unsupported.",
            "Do not implement runtime noise-biome behavior until a later explicit slice allows it.",
            true,
            "The guarded subclass must remain unsupported and unregistered in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            "No registry lookup access exists for future runtime noise-biome holder resolution.",
            "Keep registry lookup absent while noise-biome selection stays readiness-only and non-activating.",
            false,
            "Registry lookup access remains blocked by the existing readiness chain."
        ),
        entry(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent.REAL_NOISE_BIOME_SELECTION_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            "Real noise-biome runtime behavior remains deferred even though the future method shape, selector sources, candidate output source, fallback source and possible-biomes constraint are pinned.",
            "Keep the future noise-biome policy explicit without turning it into runtime holder-return behavior or activation.",
            true,
            "Real noise-biome work requires a later explicit implementation slice."
        )
    );

    private CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry> entryFor(
        CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return true;
    }

    public static boolean noiseBiomeMethodShapePinned() {
        return true;
    }

    public static boolean coordinateInputShapePinned() {
        return true;
    }

    public static boolean climateSamplerInputShapePinned() {
        return true;
    }

    public static boolean weightValueDerivationDecisionPinned() {
        return true;
    }

    public static boolean weightedSelectionSourcePinned() {
        return true;
    }

    public static boolean adapterSelectionSourcePinned() {
        return true;
    }

    public static boolean candidateKeyOutputSourcePinned() {
        return true;
    }

    public static boolean holderConversionDependencyPinned() {
        return true;
    }

    public static boolean fallbackPolicySourcePinned() {
        return true;
    }

    public static boolean possibleBiomesConstraintPinned() {
        return true;
    }

    public static boolean runtimeHolderReturnReady() {
        return false;
    }

    public static boolean getNoiseBiomeImplementationReady() {
        return false;
    }

    public static boolean registryLookupRequiredForRuntimeNoiseBiome() {
        return true;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean noiseBiomeRuntimeReady() {
        return false;
    }

    public static boolean realNoiseBiomeSelectionStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static String noiseBiomeMethodName() {
        return NOISE_BIOME_METHOD_NAME;
    }

    public static String noiseBiomeMethodSignature() {
        return NOISE_BIOME_METHOD_SIGNATURE;
    }

    public static String coordinateInputPolicy() {
        return COORDINATE_INPUT_POLICY;
    }

    public static String climateSamplerInputPolicy() {
        return CLIMATE_SAMPLER_INPUT_POLICY;
    }

    public static String weightValueDerivationPolicy() {
        return WEIGHT_VALUE_DERIVATION_POLICY;
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static boolean weightedSelectionRuntimeReady() {
        return false;
    }

    public static int weightedSelectionTotalWeight() {
        return CaveniaWeightedBiomeSelectionAlgorithm.totalWeight();
    }

    public static boolean adapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
    }

    public static boolean adapterRuntimeReady() {
        return false;
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

    public static List<String> candidateModernBiomeKeys() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys();
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

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean resourceLocationConversionReady() {
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

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
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

    public static String possibleBiomesConstraintPolicy() {
        return POSSIBLE_BIOMES_CONSTRAINT_POLICY;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean designatedSubclassReady() {
        return true;
    }

    public static boolean designatedSubclassRuntimeReady() {
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

    public static boolean goNoGoGuardrailsEnforced() {
        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allGuardrailsEnforcedInThisSlice();
    }

    public static boolean apiShapeInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
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

    private static CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry entry(
        CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent component,
        String sourceContractName,
        String readinessDecision,
        String expectedFutureBehavior,
        boolean registryLookupRequired,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry(
            component,
            sourceContractName,
            readinessDecision,
            expectedFutureBehavior,
            true,
            false,
            registryLookupRequired,
            false,
            false,
            false,
            blocker
        );
    }
}
