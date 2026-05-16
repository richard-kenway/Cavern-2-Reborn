package com.richardkenway.cavernreborn.app.worldgen;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.biome.Biome;

public final class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder {
    private static final String DESIGNATED_BUILDER_SIMPLE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder";
    private static final String DESIGNATED_BUILDER_FILE_NAME = DESIGNATED_BUILDER_SIMPLE_NAME + ".java";
    private static final String FUTURE_HOLDER_SET_OUTPUT_SHAPE = "pre-resolved biome holder set/list";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final String FALLBACK_CANDIDATE_MODERN_BIOME_KEY = "minecraft:plains";
    private static final String FALLBACK_LEGACY_BIOME_NAME = "PLAINS";
    private static final List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.DESIGNATED_BUILDER_FILE,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "The designated holder-set builder exists as the only guarded main-source helper that may aggregate pre-resolved biome holders for future collectPossibleBiomes use.",
            "The guarded builder remains unwired from runtime biome-source methods and does not activate Cavenia.",
            "The designated holder-set builder does not make the runtime biome source usable in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.CANDIDATE_INVENTORY_INPUT,
            "CaveniaLegacyToModernBiomeKeyMappings",
            "The guarded builder reads the current string candidate inventory as its default input surface.",
            "Candidate inventory traversal remains outside runtime biome-source methods in this slice.",
            "Candidate inventory input alone does not make collectPossibleBiomes usable."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.LOOKUP_PROVIDER_PARAMETER,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness",
            "The guarded builder accepts only caller-provided biome lookup/provider input before any holder aggregation attempt.",
            "No runtime lookup/provider is available inside collectPossibleBiomes() in this slice.",
            "Runtime construction remains absent, so holder aggregation must stay external and guarded."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.DESIGNATED_CONVERTER_USAGE,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The guarded builder uses the designated candidate-key-to-holder converter instead of duplicating candidate parsing or individual holder resolution logic.",
            "Individual holder conversion remains confined to the designated converter while aggregation stays outside runtime biome-source methods.",
            "Designated converter usage does not open runtime wiring in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.RESOLVED_HOLDER_COLLECTION,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "The guarded builder collects only successfully resolved biome holders into a pre-resolved output list.",
            "Holder aggregation remains a helper-only operation outside collectPossibleBiomes() in this slice.",
            "Resolved holder collection alone does not make the runtime biome source usable."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.RESOLVED_HOLDER_DEDUPLICATION,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "The guarded builder deduplicates resolved holders while preserving first successful order.",
            "Deduplication remains limited to guarded helper output and does not affect runtime method behavior in this slice.",
            "Resolved holder deduplication does not open runtime wiring in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.LEGACY_ORDER_PRESERVATION,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "The guarded builder preserves legacy candidate inventory order for first successful resolved holders.",
            "Legacy-order preservation remains limited to guarded helper output and does not affect runtime method behavior in this slice.",
            "Legacy-order preservation does not make collectPossibleBiomes usable in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.MISSING_INVALID_UNRESOLVED_SKIP,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "The guarded builder skips missing, invalid and unresolved candidate keys by relying on converter Optional results.",
            "Missing, invalid and unresolved skip behavior remains confined to guarded helper aggregation and does not affect runtime method behavior in this slice.",
            "Skipping unresolved candidates does not activate the runtime biome source."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.FALLBACK_IF_EMPTY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "The guarded builder applies the pinned plains fallback only when no candidate holders resolve.",
            "Fallback aggregation remains a guarded helper behavior outside collectPossibleBiomes() in this slice.",
            "Fallback-if-empty alone does not make the runtime biome source usable."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.IMMUTABLE_OUTPUT_LIST,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "The guarded builder returns an immutable or safely unmodifiable resolved holder list.",
            "Immutable output remains helper-only and is not exposed through runtime biome-source methods in this slice.",
            "Immutable helper output does not open runtime wiring or activation."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.NO_COLLECT_POSSIBLE_BIOMES_WIRING,
            "CaveniaRuntimeBiomeSource",
            "The guarded builder is not called by collectPossibleBiomes().",
            "collectPossibleBiomes() remains unsupported and unwired in this slice.",
            "The guarded builder does not open runtime collectPossibleBiomes wiring."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent.NO_GET_NOISE_BIOME_WIRING,
            "CaveniaRuntimeBiomeSource",
            "The guarded builder is not called by getNoiseBiome(...).",
            "getNoiseBiome(...) remains unsupported and unwired in this slice.",
            "The guarded builder does not open runtime getNoiseBiome wiring."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent
                .REAL_RUNTIME_BIOME_SOURCE_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
            "Real runtime biome-source behavior remains deferred even though the guarded holder-set builder now exists.",
            "The guarded builder does not register codec or biome-source type, does not activate Cavenia and does not create a usable runtime biome source.",
            "Real runtime biome-source behavior remains outside this bounded guarded-builder slice."
        )
    );

    private CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder() {
    }

    public static List<Holder<Biome>> buildCandidateHolderList(HolderLookup.Provider lookupProvider) {
        return buildCandidateHolderList(CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys(), lookupProvider);
    }

    public static List<Holder<Biome>> buildCandidateHolderList(
        List<String> candidateKeys,
        HolderLookup.Provider lookupProvider
    ) {
        if (lookupProvider == null) {
            return List.of();
        }

        LinkedHashSet<Holder<Biome>> resolvedHolders = new LinkedHashSet<>();
        List<String> safeCandidateKeys = candidateKeys == null ? List.of() : candidateKeys;

        for (String candidateKey : safeCandidateKeys) {
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter
                .holderForCandidateKey(candidateKey, lookupProvider)
                .ifPresent(resolvedHolders::add);
        }

        if (resolvedHolders.isEmpty()) {
            return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter
                .holderForCandidateKeyOrFallback(FALLBACK_CANDIDATE_MODERN_BIOME_KEY, lookupProvider)
                .map(List::of)
                .orElseGet(List::of);
        }

        return List.copyOf(resolvedHolders);
    }

    public static String designatedBuilderSimpleName() {
        return DESIGNATED_BUILDER_SIMPLE_NAME;
    }

    public static String designatedBuilderFileName() {
        return DESIGNATED_BUILDER_FILE_NAME;
    }

    public static boolean builderReady() {
        return true;
    }

    public static boolean builderRuntimeBiomeSourceReady() {
        return false;
    }

    public static boolean buildCandidateHolderListReady() {
        return true;
    }

    public static boolean candidateInventoryInputReady() {
        return true;
    }

    public static boolean lookupProviderParameterReady() {
        return true;
    }

    public static boolean designatedConverterUsageReady() {
        return true;
    }

    public static boolean resolvedHolderCollectionReady() {
        return true;
    }

    public static boolean resolvedHolderDeduplicationReady() {
        return true;
    }

    public static boolean legacyOrderPreservationReady() {
        return true;
    }

    public static boolean missingInvalidUnresolvedSkipReady() {
        return true;
    }

    public static boolean fallbackIfEmptyReady() {
        return true;
    }

    public static boolean immutableOutputListReady() {
        return true;
    }

    public static boolean collectPossibleBiomesWiringReady() {
        return false;
    }

    public static boolean getNoiseBiomeWiringReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry> entryFor(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
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

    public static boolean candidateKeysStillStringOnlyOutsideBuilder() {
        return true;
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

    public static boolean collectPossibleBiomesHolderSetReadinessReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.collectPossibleBiomesHolderSetReadinessReady();
    }

    public static boolean collectPossibleBiomesHolderSetReadinessRuntimeReady() {
        return false;
    }

    public static boolean implementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
            .decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext();
    }

    public static boolean implementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
            .collectPossibleBiomesHolderSetBuilderConsolidationReady();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationRuntimeReady() {
        return false;
    }

    public static boolean runtimeConstructionFactoryGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
            .decisionIsGoForRuntimeConstructionFactoryReadinessNext();
    }

    public static boolean runtimeConstructionFactoryGoNoGoRuntimeReady() {
        return false;
    }

    public static String futureHolderSetOutputShape() {
        return FUTURE_HOLDER_SET_OUTPUT_SHAPE;
    }

    public static String futureStreamReturnBoundary() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureStreamReturnBoundary();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return FALLBACK_CANDIDATE_MODERN_BIOME_KEY;
    }

    public static String fallbackLegacyBiomeName() {
        return FALLBACK_LEGACY_BIOME_NAME;
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
        return EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY;
    }

    public static String expectedSampleMixedCandidateKey() {
        return EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY;
    }

    public static String expectedSampleNegativeCandidateKey() {
        return EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY;
    }

    public static boolean sampleKeysExistInInventory() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.sampleKeysExistInInventory();
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
        return 14;
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
        return CaveniaRuntimeBiomeSourceSkeleton.guardedRealSubclassStubImplemented();
    }

    public static boolean designatedSubclassRuntimeReady() {
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

    private static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry entry(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent component,
        String sourceContractName,
        String builderBehavior,
        String runtimeBoundary,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry(
            component,
            sourceContractName,
            builderBehavior,
            runtimeBoundary,
            true,
            false,
            false,
            blocker
        );
    }
}
