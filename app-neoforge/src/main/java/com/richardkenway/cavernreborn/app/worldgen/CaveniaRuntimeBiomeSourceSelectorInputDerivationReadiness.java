package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness {
    private static final String CLIMATE_SAMPLER_NAME = "Climate" + ".Sampler";
    private static final String METHOD_INPUT_SOURCE =
        "getNoiseBiome(int x, int y, int z, " + CLIMATE_SAMPLER_NAME + " sampler)";
    private static final String COORDINATE_INPUT_POLICY =
        "future x/y/z coordinate inputs are pinned as selector-input sources but not used for runtime selection in this slice";
    private static final String CLIMATE_SAMPLER_INPUT_POLICY =
        "future " + CLIMATE_SAMPLER_NAME + " input is pinned as a selector-input source but no sampler methods are called in this slice";
    private static final String SELECTOR_INPUT_OUTPUT_SHAPE =
        "signed int weight value accepted by the existing adapter and weighted selector path";
    private static final String DETERMINISTIC_DERIVATION_POLICY =
        "same selector method inputs must produce the same selector input once implemented";
    private static final String PURE_NON_RUNTIME_POLICY =
        "future derivation must be a pure non-runtime calculation before holder conversion";
    private static final String NORMALIZATION_POLICY =
        "normalization and wrapping remain delegated to the existing weighted selector or adapter path";
    private static final String NEGATIVE_COORDINATE_STABILITY_POLICY =
        "future derivation must handle negative coordinates deterministically";
    private static final String CHUNK_BOUNDARY_STABILITY_POLICY =
        "future derivation must not depend on mutable chunk or server state";
    private static final String WORLD_OR_REGISTRY_DEPENDENCY_POLICY =
        "future derivation must not require registry lookup, holder resolution, resource keys, worldgen resources, or server level access";
    private static final String RANDOM_OR_MUTABLE_STATE_POLICY =
        "future derivation must not use random, wall-clock, mutable global state, or side effects";
    private static final List<CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.METHOD_INPUT_SHAPE,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            METHOD_INPUT_SOURCE,
            "The guarded selector-input derivation surface stays pinned to the local getNoiseBiome method input shape without adding runtime selection behavior.",
            "The method input shape is pinned, but selector-input derivation must remain non-runtime in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.COORDINATE_INPUT_POLICY,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            "x, y, z",
            COORDINATE_INPUT_POLICY + ".",
            "Coordinate inputs are pinned only as future selector-input sources in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.CLIMATE_SAMPLER_INPUT_POLICY,
            "CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness",
            CLIMATE_SAMPLER_NAME,
            CLIMATE_SAMPLER_INPUT_POLICY + ".",
            "Climate sampler input is pinned only as a future selector-input source in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.SELECTOR_INPUT_OUTPUT_SHAPE,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            "selector input output",
            "Future selector-input derivation stays pinned to a " + SELECTOR_INPUT_OUTPUT_SHAPE + ".",
            "The output shape is pinned, but no selector-input value is derived in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.DETERMINISTIC_DERIVATION_POLICY,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            METHOD_INPUT_SOURCE,
            DETERMINISTIC_DERIVATION_POLICY + ".",
            "Deterministic derivation is pinned only as readiness policy in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.PURE_NON_RUNTIME_POLICY,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            "selector input derivation phase",
            PURE_NON_RUNTIME_POLICY + ".",
            "Selector-input derivation must stay absent until a later pure non-runtime implementation slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.WEIGHTED_SELECTOR_COMPATIBILITY,
            "CaveniaWeightedBiomeSelectionAlgorithm",
            "weighted selector total weight",
            "Future selector-input outputs must stay compatible with the existing weighted selector total weight surface.",
            "Compatibility with the weighted selector is pinned, but no selector-input value reaches that path in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.ADAPTER_QUERY_COMPATIBILITY,
            "CaveniaBiomeSelectionAdapterContract",
            "adapter query path",
            "Future selector-input outputs must stay compatible with the existing adapter query path.",
            "Adapter compatibility is pinned, but no selector-input value reaches the adapter in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NORMALIZATION_POLICY,
            "CaveniaWeightedBiomeSelectionAlgorithm",
            SELECTOR_INPUT_OUTPUT_SHAPE,
            NORMALIZATION_POLICY + ".",
            "Normalization stays delegated because this slice must not add selector-input formula logic."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NEGATIVE_COORDINATE_STABILITY_POLICY,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            "negative x/y/z coordinates",
            NEGATIVE_COORDINATE_STABILITY_POLICY + ".",
            "Negative-coordinate handling is pinned only as deterministic future policy in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.CHUNK_BOUNDARY_STABILITY_POLICY,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            "chunk-boundary transitions",
            CHUNK_BOUNDARY_STABILITY_POLICY + ".",
            "Chunk-boundary stability is pinned only as future policy in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_WORLD_OR_REGISTRY_DEPENDENCY,
            "CaveniaRegistryLookupReadiness",
            "registry, worldgen and server access",
            WORLD_OR_REGISTRY_DEPENDENCY_POLICY + ".",
            "World, registry and server access must remain absent while selector-input derivation stays readiness-only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_RANDOM_OR_MUTABLE_STATE_DEPENDENCY,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            "random and mutable state surfaces",
            RANDOM_OR_MUTABLE_STATE_POLICY + ".",
            "Random and mutable-state dependencies must remain absent while selector-input derivation stays readiness-only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            "selector-input derivation runtime code",
            "No selector-input derivation implementation exists in this slice.",
            "This slice must not add a selector-input formula or any derivation code."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.NO_RUNTIME_SELECTION,
            "CaveniaRuntimeBiomeSource",
            METHOD_INPUT_SOURCE,
            "No runtime coordinate or climate-sampler selection behavior exists for the guarded getNoiseBiome path in this slice.",
            "Runtime selection remains blocked because the guarded subclass must stay unsupported."
        ),
        entry(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent.REAL_SELECTOR_INPUT_DERIVATION_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness",
            METHOD_INPUT_SOURCE,
            "Real selector-input derivation remains deferred even though the future input surfaces, output shape and compatibility policies are pinned.",
            "A later explicit implementation or go/no-go slice must still decide whether to allow the actual derivation algorithm."
        )
    );

    private CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry> entryFor(
        CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean selectorInputDerivationReadinessReady() {
        return true;
    }

    public static boolean methodInputShapePinned() {
        return true;
    }

    public static boolean coordinateInputPolicyPinned() {
        return true;
    }

    public static boolean climateSamplerInputPolicyPinned() {
        return true;
    }

    public static boolean selectorInputOutputShapePinned() {
        return true;
    }

    public static boolean deterministicDerivationPolicyPinned() {
        return true;
    }

    public static boolean pureNonRuntimePolicyPinned() {
        return true;
    }

    public static boolean weightedSelectorCompatibilityPinned() {
        return true;
    }

    public static boolean adapterQueryCompatibilityPinned() {
        return true;
    }

    public static boolean normalizationPolicyPinned() {
        return true;
    }

    public static boolean negativeCoordinateStabilityPolicyPinned() {
        return true;
    }

    public static boolean chunkBoundaryStabilityPolicyPinned() {
        return true;
    }

    public static boolean noWorldOrRegistryDependencyPinned() {
        return true;
    }

    public static boolean noRandomOrMutableStateDependencyPinned() {
        return true;
    }

    public static boolean selectorInputDerivationImplementationReady() {
        return false;
    }

    public static boolean selectorInputDerivationRuntimeReady() {
        return false;
    }

    public static boolean coordinateRuntimeSelectionReady() {
        return false;
    }

    public static boolean climateSamplerRuntimeSelectionReady() {
        return false;
    }

    public static boolean realSelectorInputDerivationStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static String methodInputSource() {
        return METHOD_INPUT_SOURCE;
    }

    public static String coordinateInputPolicy() {
        return COORDINATE_INPUT_POLICY;
    }

    public static String climateSamplerInputPolicy() {
        return CLIMATE_SAMPLER_INPUT_POLICY;
    }

    public static String selectorInputOutputShape() {
        return SELECTOR_INPUT_OUTPUT_SHAPE;
    }

    public static String deterministicDerivationPolicy() {
        return DETERMINISTIC_DERIVATION_POLICY;
    }

    public static String pureNonRuntimePolicy() {
        return PURE_NON_RUNTIME_POLICY;
    }

    public static String normalizationPolicy() {
        return NORMALIZATION_POLICY;
    }

    public static String negativeCoordinateStabilityPolicy() {
        return NEGATIVE_COORDINATE_STABILITY_POLICY;
    }

    public static String chunkBoundaryStabilityPolicy() {
        return CHUNK_BOUNDARY_STABILITY_POLICY;
    }

    public static String worldOrRegistryDependencyPolicy() {
        return WORLD_OR_REGISTRY_DEPENDENCY_POLICY;
    }

    public static String randomOrMutableStatePolicy() {
        return RANDOM_OR_MUTABLE_STATE_POLICY;
    }

    public static boolean actualDerivationFormulaPinned() {
        return false;
    }

    public static boolean actualDerivationFormulaImplemented() {
        return false;
    }

    public static boolean goNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();
    }

    public static boolean goNoGoRuntimeReady() {
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

    public static boolean noiseBiomeMethodShapePinned() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodShapePinned();
    }

    public static boolean weightValueDerivationDecisionPinned() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.weightValueDerivationDecisionPinned();
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

    private static CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry entry(
        CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent component,
        String sourceContractName,
        String inputSurface,
        String futurePolicy,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry(
            component,
            sourceContractName,
            inputSurface,
            futurePolicy,
            true,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
