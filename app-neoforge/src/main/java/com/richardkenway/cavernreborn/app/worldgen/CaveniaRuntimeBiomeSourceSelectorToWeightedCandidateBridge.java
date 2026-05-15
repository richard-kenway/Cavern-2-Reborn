package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge {
    private static final String SELECTOR_INPUT_OUTPUT_SHAPE = "signed int selector input";
    private static final String CANDIDATE_KEY_OUTPUT_SHAPE = "string modern biome candidate key";
    private static final String BRIDGE_RUNTIME_BOUNDARY = "not wired into getNoiseBiome";
    private static final List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry> ENTRIES =
        List.of(
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .SIGNED_INT_SELECTOR_INPUT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness",
                "The bridge accepts the already-pinned signed int selector input.",
                "The bridge does not widen selector input into any runtime-only surface.",
                "Signed int selector input is ready only for the pure non-runtime bridge."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .PURE_NON_RUNTIME_BRIDGE,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge",
                "The bridge is implemented as a pure Java helper over the existing candidate-selection path.",
                "The bridge remains detached from runtime biome-source method execution.",
                "The pure bridge is ready, but runtime biome selection remains blocked."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .WEIGHTED_SELECTOR_OR_ADAPTER_PATH,
                "CaveniaBiomeSelectionAdapterContract",
                "The bridge delegates candidate selection to the existing pure weighted selector or adapter path.",
                "The bridge does not add any direct runtime biome-source wiring.",
                "The weighted selector and adapter path is ready only as pure non-runtime selection."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .STRING_CANDIDATE_KEY_OUTPUT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge",
                "The bridge returns only a string modern biome candidate key.",
                "The bridge does not widen output into runtime holder or registry state.",
                "Candidate output remains string-only until holder conversion is explicitly opened."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .CANDIDATE_INVENTORY_COMPATIBILITY,
                "CaveniaLegacyToModernBiomeKeyMappings",
                "The bridge output is constrained to the current candidate inventory.",
                "The bridge does not widen selection into runtime-only biome resolution.",
                "Candidate inventory compatibility is ready only as string inventory validation."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NORMALIZATION_DELEGATED_TO_SELECTOR_PATH,
                "CaveniaWeightedBiomeSelectionAlgorithm",
                "Normalization and wrapping remain delegated to the existing selector path.",
                "The bridge does not introduce a runtime-only normalization branch.",
                "Normalization stays bounded to the existing pure selector path."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .DETERMINISTIC_BRIDGE_OUTPUT,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge",
                "The bridge returns the same string candidate key for the same selector input every time.",
                "The bridge depends on no mutable runtime state, clock or randomness.",
                "Bridge output is deterministic only as pure non-runtime selection."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_MINECRAFT_RUNTIME_API_DEPENDENCY,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision",
                "The bridge imports no Minecraft runtime APIs.",
                "The bridge remains pure Java and does not cross the guarded runtime boundary.",
                "Minecraft runtime API dependency remains intentionally absent."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_REGISTRY_OR_HOLDER_DEPENDENCY,
                "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
                "The bridge uses no registry lookup, holder resolution or conversion path.",
                "The bridge does not widen into runtime registry or holder behavior.",
                "Registry and holder dependencies remain intentionally absent."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_FALLBACK_RUNTIME_USAGE,
                "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
                "The bridge does not call fallback runtime behavior during normal pure selection.",
                "The bridge remains detached from runtime fallback execution.",
                "Fallback runtime behavior remains intentionally absent."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                "The bridge is not wired into the guarded getNoiseBiome path.",
                "The designated runtime subclass still leaves getNoiseBiome unsupported.",
                "Runtime method wiring remains intentionally absent."
            ),
            entry(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .REAL_RUNTIME_SELECTION_STILL_DEFERRED,
                "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge",
                "Real runtime biome selection remains deferred even though the pure bridge now exists.",
                "The bridge does not make the runtime biome source usable.",
                "A later consolidation or conversion slice is still required before runtime use."
            )
        );

    private CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge() {
    }

    public static String candidateKeyForSelectorInput(int selectorInput) {
        String candidateKey = CaveniaBiomeSelectionAdapterContract.selectCandidateByWeightValue(selectorInput)
            .candidateModernBiomeKey();

        if (!candidateModernBiomeKeys().contains(candidateKey)) {
            throw new IllegalStateException("Expected candidate inventory to contain " + candidateKey);
        }

        return candidateKey;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry> entryFor(
        CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return true;
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

    public static boolean pureNonRuntimeBridgeReady() {
        return true;
    }

    public static boolean signedIntSelectorInputReady() {
        return true;
    }

    public static boolean weightedSelectorOrAdapterPathReady() {
        return true;
    }

    public static boolean stringCandidateKeyOutputReady() {
        return true;
    }

    public static boolean candidateInventoryCompatibilityReady() {
        return true;
    }

    public static boolean normalizationDelegatedToSelectorPath() {
        return true;
    }

    public static boolean deterministicBridgeOutputReady() {
        return true;
    }

    public static boolean minecraftRuntimeApiDependencyPresent() {
        return false;
    }

    public static boolean registryOrHolderDependencyPresent() {
        return false;
    }

    public static boolean fallbackRuntimeUsagePresent() {
        return false;
    }

    public static boolean getNoiseBiomeWiringReady() {
        return false;
    }

    public static boolean runtimeSelectionReady() {
        return false;
    }

    public static boolean realRuntimeSelectionStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static String selectorInputOutputShape() {
        return SELECTOR_INPUT_OUTPUT_SHAPE;
    }

    public static String candidateKeyOutputShape() {
        return CANDIDATE_KEY_OUTPUT_SHAPE;
    }

    public static String bridgeRuntimeBoundary() {
        return BRIDGE_RUNTIME_BOUNDARY;
    }

    public static boolean bridgeImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeBridgeNext();
    }

    public static boolean bridgeImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean bridgeReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .selectorToWeightedCandidateBridgeReadinessReady();
    }

    public static boolean bridgeReadinessRuntimeReady() {
        return false;
    }

    public static boolean selectorInputAlgorithmReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady();
    }

    public static boolean selectorInputAlgorithmRuntimeReady() {
        return false;
    }

    public static int deriveSelectorInputSampleOrigin() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(0, 0, 0);
    }

    public static int deriveSelectorInputSampleMixed() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(1, 2, 3);
    }

    public static int deriveSelectorInputSampleNegative() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.deriveSelectorInput(-16, 64, 16);
    }

    public static String candidateKeyForSampleOrigin() {
        return candidateKeyForSelectorInput(deriveSelectorInputSampleOrigin());
    }

    public static String candidateKeyForSampleMixed() {
        return candidateKeyForSelectorInput(deriveSelectorInputSampleMixed());
    }

    public static String candidateKeyForSampleNegative() {
        return candidateKeyForSelectorInput(deriveSelectorInputSampleNegative());
    }

    public static boolean sampleKeysExistInInventory() {
        List<String> candidateKeys = candidateModernBiomeKeys();
        return candidateKeys.contains(candidateKeyForSampleOrigin())
            && candidateKeys.contains(candidateKeyForSampleMixed())
            && candidateKeys.contains(candidateKeyForSampleNegative());
    }

    public static boolean weightedSelectorCompatibilityReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static int weightedSelectionTotalWeight() {
        return CaveniaWeightedBiomeSelectionAlgorithm.totalWeight();
    }

    public static boolean adapterCompatibilityReady() {
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

    public static String fallbackLegacyBiomeName() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
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
        return CaveniaRuntimeBiomeSource.selectedSurfaceReadinessItemCount();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaRuntimeBiomeSource.globalReadinessMatrixTotalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaRuntimeBiomeSource.globalReadinessMatrixBlockedRequirementCount();
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

    private static CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry entry(
        CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent component,
        String sourceContractName,
        String bridgeDecision,
        String runtimeBoundary,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry(
            component,
            sourceContractName,
            bridgeDecision,
            runtimeBoundary,
            true,
            false,
            false,
            false,
            blocker
        );
    }
}
