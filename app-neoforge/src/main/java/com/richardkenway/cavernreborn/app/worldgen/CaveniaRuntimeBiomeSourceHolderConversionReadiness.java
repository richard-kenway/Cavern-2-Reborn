package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceHolderConversionReadiness {
    private static final String HOLDER_BIOME_NAME = "Holder" + "<Biome>";
    private static final String RESOURCE_LOCATION_NAME = "Resource" + "Location";
    private static final String RESOURCE_KEY_BIOME_NAME = "Resource" + "Key<Biome>";
    private static final String REGISTRY_LOOKUP_NAME = "Registry" + "Lookup";
    private static final String LEGACY_MAPPING_SOURCE = "CaveniaLegacyToModernBiomeKeyMappings";
    private static final String RUNTIME_BIOME_SOURCE_SOURCE = "CaveniaRuntimeBiomeSource";
    private static final String STRING_CANDIDATE_FORMAT =
        "Namespace:path biome id strings such as minecraft:ocean, minecraft:plains, minecraft:desert, "
            + "minecraft:windswept_hills, minecraft:savanna, and minecraft:badlands.";
    private static final String COLLECT_POSSIBLE_BIOMES_METHOD = "collectPossibleBiomes()";
    private static final String GET_NOISE_BIOME_METHOD = "getNoiseBiome(...)";
    private static final List<CaveniaRuntimeBiomeSourceHolderConversionEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.STRING_CANDIDATE_KEYS_SOURCE,
            LEGACY_MAPPING_SOURCE,
            "Candidate modern biome keys still come from " + LEGACY_MAPPING_SOURCE
                + ".candidateModernBiomeKeys() as plain strings with " + CaveniaLegacyToModernBiomeKeyMappings.entryCount()
                + " inventoried entries.",
            "Keep the current candidate inventory string-only until later runtime conversion steps are explicitly chosen.",
            false,
            "The current candidate biome ids are inventoried only as strings and do not yet enter runtime holder resolution."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.CANDIDATE_KEY_FORMAT_CONTRACT,
            LEGACY_MAPPING_SOURCE,
            "Candidate biome ids currently follow the string-only format contract: " + STRING_CANDIDATE_FORMAT,
            "Treat the current candidate biome ids as namespace:path strings before any future runtime parsing decision.",
            false,
            "The candidate key format is pinned, but no runtime parsing or verification exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.RESOURCE_LOCATION_CONVERSION_DECISION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Future runtime conversion still needs a string-to-" + RESOURCE_LOCATION_NAME + " parsing decision.",
            "A later slice must decide how string candidate ids become parsed runtime locations without enabling activation now.",
            false,
            "No runtime location parsing exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.RESOURCE_KEY_CONVERSION_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "The local BiomeSource API shape still leaves a separate future conversion step from parsed ids to "
                + RESOURCE_KEY_BIOME_NAME + ".",
            "A later slice must decide how parsed biome ids become biome resource keys before holder-backed returns can exist.",
            false,
            "No runtime resource-key conversion exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.BIOME_REGISTRY_LOOKUP_DECISION,
            "CaveniaRegistryLookupReadiness",
            "Future runtime conversion will need a biome-registry lookup step once parsed ids and biome resource keys exist.",
            "A later slice must decide how biome registry lookup will be obtained without widening this slice into runtime access.",
            true,
            "Registry lookup access is still blocked, so runtime biome lookup cannot happen in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.HOLDER_RESOLUTION_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "Future runtime conversion still needs a deliberate path from registry-selected biome entries to " + HOLDER_BIOME_NAME + ".",
            "A later slice must decide how the runtime subclass resolves holder-backed biome values after lookup succeeds.",
            true,
            "Holder resolution remains a future design step because runtime lookup access is still absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.POSSIBLE_BIOMES_HOLDER_SET_DECISION,
            RUNTIME_BIOME_SOURCE_SOURCE,
            "The guarded subclass still leaves " + COLLECT_POSSIBLE_BIOMES_METHOD + " unsupported even though it will eventually need "
                + HOLDER_BIOME_NAME + "-backed possible-biome values.",
            "A later slice must decide how the string candidate inventory becomes the holder-backed possible-biomes stream or set.",
            true,
            "Possible-biomes holder production remains blocked while runtime conversion and lookup stay unavailable."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NOISE_BIOME_HOLDER_RETURN_DECISION,
            RUNTIME_BIOME_SOURCE_SOURCE,
            "The guarded subclass still leaves " + GET_NOISE_BIOME_METHOD + " unsupported even though it will eventually need a "
                + HOLDER_BIOME_NAME + " runtime return.",
            "A later slice must decide how runtime noise-biome selection returns holder-backed biome values from resolved candidates.",
            true,
            "Noise-biome holder returns remain blocked while runtime conversion and lookup stay unavailable."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.MISSING_BIOME_FALLBACK_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "Missing or unresolved candidate biome ids still need a future fallback policy before holder-backed runtime returns can exist.",
            "A later slice must decide what fallback behavior applies when conversion, lookup, or holder resolution cannot produce a biome.",
            true,
            "Fallback policy remains blocked because the runtime conversion path is still absent."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NO_RUNTIME_CONVERSION_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "No runtime conversion implementation exists in the guarded subclass or its inert readiness contracts.",
            "Keep this slice limited to readiness data instead of adding string parsing, key conversion, lookup, or holder resolution.",
            false,
            "Runtime conversion implementation remains explicitly out of scope for this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            "No " + REGISTRY_LOOKUP_NAME + " access exists for the guarded runtime biome-source path.",
            "Keep registry lookup access absent while holder conversion stays readiness-only.",
            false,
            "Registry lookup access remains blocked by existing readiness contracts."
        ),
        entry(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.REAL_HOLDER_CONVERSION_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Real holder/resource-key conversion remains deferred even though the future path is now pinned as readiness data.",
            "Keep the planned conversion path explicit without turning it into runtime conversion or activation.",
            false,
            "Real holder conversion work requires a later explicit implementation slice."
        )
    );

    private CaveniaRuntimeBiomeSourceHolderConversionReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourceHolderConversionEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceHolderConversionComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceHolderConversionEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceHolderConversionEntry> entryFor(
        CaveniaRuntimeBiomeSourceHolderConversionComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean holderConversionReadinessReady() {
        return true;
    }

    public static boolean stringCandidateKeysSourcePinned() {
        return true;
    }

    public static boolean candidateKeyFormatPinned() {
        return true;
    }

    public static boolean resourceLocationConversionDecisionPinned() {
        return true;
    }

    public static boolean resourceKeyConversionDecisionPinned() {
        return true;
    }

    public static boolean biomeRegistryLookupDecisionPinned() {
        return true;
    }

    public static boolean holderResolutionDecisionPinned() {
        return true;
    }

    public static boolean possibleBiomesHolderSetDecisionPinned() {
        return true;
    }

    public static boolean noiseBiomeHolderReturnDecisionPinned() {
        return true;
    }

    public static boolean missingBiomeFallbackDecisionPinned() {
        return true;
    }

    public static boolean runtimeConversionImplementationReady() {
        return false;
    }

    public static boolean registryLookupRequiredForRuntimeConversion() {
        return true;
    }

    public static boolean registryLookupAccessReady() {
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

    public static boolean possibleBiomesRuntimeReady() {
        return false;
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

    public static boolean noiseBiomeRuntimeReady() {
        return false;
    }

    public static boolean missingBiomeFallbackReady() {
        return false;
    }

    public static boolean realHolderConversionStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
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

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
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

    private static CaveniaRuntimeBiomeSourceHolderConversionEntry entry(
        CaveniaRuntimeBiomeSourceHolderConversionComponent component,
        String sourceContractName,
        String observedInput,
        String futureDecision,
        boolean registryLookupRequired,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceHolderConversionEntry(
            component,
            sourceContractName,
            observedInput,
            futureDecision,
            true,
            false,
            registryLookupRequired,
            false,
            false,
            blocker
        );
    }
}
