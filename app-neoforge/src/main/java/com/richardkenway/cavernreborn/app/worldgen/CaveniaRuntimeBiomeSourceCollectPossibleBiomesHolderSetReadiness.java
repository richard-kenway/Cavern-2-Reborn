package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness {
    private static final String CANDIDATE_KEY_INPUT_SHAPE = "string modern biome candidate key";
    private static final String FUTURE_CANDIDATE_INVENTORY_SOURCE = "CaveniaLegacyToModernBiomeKeyMappings";
    private static final String FUTURE_CONVERTER_DEPENDENCY =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter";
    private static final String FUTURE_LOOKUP_PROVIDER_POLICY =
        "future holder-set construction requires caller-provided biome lookup/provider before runtime construction";
    private static final String FUTURE_PRE_RESOLVED_HOLDER_SET_POLICY =
        "future runtime source must receive or store a pre-resolved holder set because collectPossibleBiomes has no lookup/provider parameter";
    private static final String FUTURE_HOLDER_SET_OUTPUT_SHAPE = "pre-resolved biome holder set/list";
    private static final String FUTURE_STREAM_RETURN_BOUNDARY =
        "future collectPossibleBiomes may stream only pre-resolved holder outputs after runtime construction is explicitly allowed";
    private static final String FUTURE_DEDUPLICATION_POLICY =
        "deduplicate resolved holders, not current string candidate inventory";
    private static final String FUTURE_ORDERING_POLICY =
        "preserve legacy inventory order for first successful resolved holders";
    private static final String FUTURE_FALLBACK_IF_EMPTY_POLICY =
        "attempt fallback minecraft:plains only if all candidate conversions fail or the resolved holder set is empty";
    private static final String FUTURE_MISSING_CANDIDATE_POLICY =
        "skip unresolved or missing candidate keys during future holder-set construction without crashing runtime method";
    private static final String FUTURE_INVALID_CANDIDATE_POLICY =
        "invalid candidate keys must not crash future holder-set construction";
    private static final String FUTURE_UNRESOLVED_HOLDER_POLICY =
        "skip unresolved holder results during future holder-set construction and rely on fallback only when no holders resolve";
    private static final List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.CANDIDATE_INVENTORY_SOURCE,
            "CaveniaLegacyToModernBiomeKeyMappings",
            "Current possible-biomes candidate inventory remains the existing string-only modern biome key list.",
            "Use the current string candidate inventory as the future holder-set input source.",
            false,
            "Future runtime holder-set construction still requires conversion and pre-resolution work."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.DESIGNATED_CONVERTER_DEPENDENCY,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "Future holder-set construction depends on the designated guarded candidate-key-to-holder converter.",
            "Require the designated converter for future candidate-key parsing, biome-key construction, lookup and holder resolution.",
            true,
            "The designated converter remains unwired from runtime biome-source methods in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.LOOKUP_PROVIDER_REQUIREMENT,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "Future holder-set construction requires caller-provided biome lookup/provider input before runtime construction.",
            FUTURE_LOOKUP_PROVIDER_POLICY,
            true,
            "No runtime lookup/provider is available to collectPossibleBiomes() in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.PRE_RESOLVED_HOLDER_SET_POLICY,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
            "Future collectPossibleBiomes output must be prepared as a pre-resolved holder set before runtime method use.",
            FUTURE_PRE_RESOLVED_HOLDER_SET_POLICY,
            true,
            "collectPossibleBiomes() has no lookup/provider parameter and cannot construct holders on demand in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.HOLDER_SET_OUTPUT_SHAPE,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
            "Future collectPossibleBiomes output shape is a resolved holder-backed biome set or list.",
            "Future holder-set output shape is pinned to " + FUTURE_HOLDER_SET_OUTPUT_SHAPE + ".",
            true,
            "No pre-resolved holder-backed possible-biomes output exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.STREAM_RETURN_BOUNDARY,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "Future collectPossibleBiomes return boundary stays pinned to a runtime stream over pre-resolved holder outputs only.",
            FUTURE_STREAM_RETURN_BOUNDARY,
            true,
            "No holder stream may be returned until runtime construction is explicitly opened in a later slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.DEDUPLICATION_POLICY,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "Future possible-biomes deduplication remains pinned to resolved holder outputs instead of current string inventory.",
            FUTURE_DEDUPLICATION_POLICY,
            false,
            "No resolved holder-backed outputs exist yet, so deduplication stays readiness-only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.ORDERING_POLICY,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "Future possible-biomes ordering remains pinned to the current legacy mapping order after successful holder resolution.",
            FUTURE_ORDERING_POLICY,
            false,
            "Ordering stays readiness-only until resolved holders exist."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.FALLBACK_IF_EMPTY_POLICY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future possible-biomes fallback remains pinned to the existing plains fallback only when resolved holder output would otherwise be empty.",
            FUTURE_FALLBACK_IF_EMPTY_POLICY,
            true,
            "Fallback behavior remains a future holder-set construction policy only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.MISSING_CANDIDATE_HANDLING_POLICY,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "Future holder-set construction must tolerate missing candidate keys by skipping unresolved entries.",
            FUTURE_MISSING_CANDIDATE_POLICY,
            true,
            "Missing candidate handling remains policy-only while runtime methods stay unwired."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.INVALID_CANDIDATE_HANDLING_POLICY,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "Future holder-set construction must tolerate invalid candidate keys without crashing runtime method behavior.",
            FUTURE_INVALID_CANDIDATE_POLICY,
            true,
            "Invalid candidate handling remains policy-only while runtime methods stay unwired."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.UNRESOLVED_HOLDER_HANDLING_POLICY,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "Future holder-set construction must tolerate unresolved holder attempts by skipping them during aggregation.",
            FUTURE_UNRESOLVED_HOLDER_POLICY,
            true,
            "Unresolved holder handling remains policy-only while runtime methods stay unwired."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            "collectPossibleBiomes() remains unsupported and does not build or return any holder-backed output.",
            "Keep collectPossibleBiomes() unsupported until a later explicit implementation slice opens runtime behavior.",
            false,
            "The guarded subclass must remain unsupported in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_CONVERTER_WIRING_IN_RUNTIME_METHODS,
            "CaveniaRuntimeBiomeSource",
            "The designated converter is not wired into collectPossibleBiomes() or any runtime holder-set construction path.",
            "Keep converter calls out of runtime biome-source methods until a later explicit implementation slice opens that boundary.",
            false,
            "Runtime method wiring must remain absent in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_GET_NOISE_BIOME_WIRING,
            "CaveniaRuntimeBiomeSource",
            "The designated converter is not wired into getNoiseBiome(...).",
            "Keep getNoiseBiome(...) unsupported and unrelated to future possible-biomes holder-set work in this slice.",
            false,
            "getNoiseBiome(...) must remain unsupported and unwired in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.NO_RUNTIME_CONSTRUCTION_OR_FACTORY,
            "CaveniaRuntimeBiomeSource",
            "No runtime construction path or factory exists that could receive a pre-resolved holder set today.",
            "Future runtime construction remains blocked until an explicit later slice allows constructor or factory planning.",
            false,
            "Normal runtime construction remains unavailable in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent
                .REAL_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
            "Real collectPossibleBiomes holder-set behavior remains deferred even though the future holder-set policy surface is now pinned.",
            "Keep holder-set behavior readiness-only and non-activating until a later explicit decision opens implementation planning.",
            true,
            "Real holder-set construction, runtime wiring and activation remain outside this slice."
        )
    );

    private CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry> entryFor(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean collectPossibleBiomesHolderSetReadinessReady() {
        return true;
    }

    public static boolean candidateInventorySourcePinned() {
        return true;
    }

    public static boolean designatedConverterDependencyPinned() {
        return true;
    }

    public static boolean lookupProviderRequirementPinned() {
        return true;
    }

    public static boolean preResolvedHolderSetPolicyPinned() {
        return true;
    }

    public static boolean holderSetOutputShapePinned() {
        return true;
    }

    public static boolean streamReturnBoundaryPinned() {
        return true;
    }

    public static boolean deduplicationPolicyPinned() {
        return true;
    }

    public static boolean orderingPolicyPinned() {
        return true;
    }

    public static boolean fallbackIfEmptyPolicyPinned() {
        return true;
    }

    public static boolean missingCandidateHandlingPolicyPinned() {
        return true;
    }

    public static boolean invalidCandidateHandlingPolicyPinned() {
        return true;
    }

    public static boolean unresolvedHolderHandlingPolicyPinned() {
        return true;
    }

    public static boolean collectPossibleBiomesImplementationReady() {
        return false;
    }

    public static boolean converterWiringInRuntimeMethodsReady() {
        return false;
    }

    public static boolean getNoiseBiomeWiringReady() {
        return false;
    }

    public static boolean runtimeConstructionOrFactoryReady() {
        return false;
    }

    public static boolean lookupProviderRequiredForRuntimeHolderSet() {
        return true;
    }

    public static boolean lookupProviderAvailableInThisSlice() {
        return false;
    }

    public static boolean realCollectPossibleBiomesHolderSetStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static String futureCandidateInventorySource() {
        return FUTURE_CANDIDATE_INVENTORY_SOURCE;
    }

    public static String futureConverterDependency() {
        return FUTURE_CONVERTER_DEPENDENCY;
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static List<String> candidateModernBiomeKeys() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys();
    }

    public static boolean candidateKeysStillStringOnlyOutsideConverter() {
        return true;
    }

    public static String futureLookupProviderPolicy() {
        return FUTURE_LOOKUP_PROVIDER_POLICY;
    }

    public static String futurePreResolvedHolderSetPolicy() {
        return FUTURE_PRE_RESOLVED_HOLDER_SET_POLICY;
    }

    public static String futureHolderSetOutputShape() {
        return FUTURE_HOLDER_SET_OUTPUT_SHAPE;
    }

    public static String futureStreamReturnBoundary() {
        return FUTURE_STREAM_RETURN_BOUNDARY;
    }

    public static String futureDeduplicationPolicy() {
        return FUTURE_DEDUPLICATION_POLICY;
    }

    public static String futureOrderingPolicy() {
        return FUTURE_ORDERING_POLICY;
    }

    public static String futureFallbackIfEmptyPolicy() {
        return FUTURE_FALLBACK_IF_EMPTY_POLICY;
    }

    public static String futureMissingCandidatePolicy() {
        return FUTURE_MISSING_CANDIDATE_POLICY;
    }

    public static String futureInvalidCandidatePolicy() {
        return FUTURE_INVALID_CANDIDATE_POLICY;
    }

    public static String futureUnresolvedHolderHandlingPolicy() {
        return FUTURE_UNRESOLVED_HOLDER_POLICY;
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

    public static String candidateKeyInputShape() {
        return CANDIDATE_KEY_INPUT_SHAPE;
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
        return "minecraft:taiga";
    }

    public static String expectedSampleMixedCandidateKey() {
        return "minecraft:desert";
    }

    public static String expectedSampleNegativeCandidateKey() {
        return "minecraft:desert";
    }

    public static boolean sampleKeysExistInInventory() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.sampleKeysExistInInventory();
    }

    public static boolean goNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
            .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext();
    }

    public static boolean goNoGoRuntimeReady() {
        return false;
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

    private static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry entry(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent component,
        String sourceContractName,
        String holderSetSurface,
        String futurePolicy,
        boolean lookupProviderRequired,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry(
            component,
            sourceContractName,
            holderSetSurface,
            futurePolicy,
            true,
            false,
            false,
            lookupProviderRequired,
            false,
            false,
            blocker
        );
    }
}
