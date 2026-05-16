package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation {
    private static final String DESIGNATED_BUILDER_SIMPLE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder";
    private static final String DESIGNATED_BUILDER_FILE_NAME = DESIGNATED_BUILDER_SIMPLE_NAME + ".java";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision
        SELECTED_NEXT_DECISION =
            CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision
                .PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_GO_NO_GO_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry>
        ENTRIES = List.of(
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DESIGNATED_BUILDER_EXISTS,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "The designated guarded holder-set builder file exists as the only bounded main-source helper that may aggregate pre-resolved biome holders for future collectPossibleBiomes use.",
                "The designated builder remains unwired from runtime biome-source methods and does not activate Cavenia.",
                true,
                true,
                "The designated builder remains bounded to guarded helper behavior only."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .HOLDER_SET_BUILDER_READY,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "The guarded holder-set builder is implemented and ready as a bounded helper surface.",
                "The builder remains unavailable to runtime biome-source methods in this slice.",
                true,
                true,
                "The guarded builder does not make the runtime biome source usable in this slice."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .CANDIDATE_INVENTORY_INPUT_READY,
                "CaveniaLegacyToModernBiomeKeyMappings",
                "The current string candidate inventory remains ready as the builder input surface.",
                "Candidate keys remain string-only outside the designated builder and designated converter.",
                true,
                false,
                "Candidate inventory input remains bounded to guarded helper aggregation."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DESIGNATED_CONVERTER_DEPENDENCY_READY,
                "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
                "The designated candidate-key-to-holder converter remains ready as the only guarded dependency for individual holder resolution.",
                "The builder may aggregate holder results, but individual lookup and conversion stay confined to the designated converter.",
                true,
                false,
                "The designated converter dependency remains intentionally bounded."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .LOOKUP_PROVIDER_PARAMETER_READY,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "Caller-provided biome lookup/provider input remains the only guarded holder-resolution input path for the builder.",
                "No lookup/provider is available inside collectPossibleBiomes() in this slice.",
                true,
                false,
                "Lookup/provider input remains intentionally external and guarded."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .PRE_RESOLVED_HOLDER_LIST_READY,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "The guarded builder produces a pre-resolved holder list outside runtime biome-source methods.",
                "Pre-resolved holder output remains helper-only and is not yet stored in or streamed by the runtime biome source.",
                true,
                true,
                "Pre-resolved holder output alone does not make collectPossibleBiomes usable."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DEDUPLICATION_READY,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "Resolved holders are deduplicated while preserving first successful resolved order.",
                "Deduplication remains limited to guarded helper output and does not affect runtime method behavior in this slice.",
                true,
                false,
                "Resolved-holder deduplication does not open runtime wiring."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .LEGACY_ORDER_PRESERVED,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "Legacy candidate inventory order is preserved for first successful resolved holders.",
                "Legacy-order preservation remains limited to guarded helper output and does not affect runtime method behavior in this slice.",
                true,
                false,
                "Legacy-order preservation does not make collectPossibleBiomes usable."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .FALLBACK_IF_EMPTY_READY,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "Fallback-if-empty through minecraft:plains remains ready after candidate holder resolution yields no holders.",
                "Fallback aggregation remains a guarded helper behavior outside collectPossibleBiomes() in this slice.",
                true,
                true,
                "Fallback-if-empty alone does not make the runtime biome source usable."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .IMMUTABLE_OUTPUT_READY,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                "Immutable or safely unmodifiable pre-resolved holder output remains ready as the builder result surface.",
                "Immutable output remains helper-only and is not exposed through runtime biome-source methods in this slice.",
                true,
                true,
                "Immutable helper output does not open runtime wiring or activation."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .COLLECT_POSSIBLE_BIOMES_WIRING_BLOCKED,
                "CaveniaRuntimeBiomeSource",
                "Builder wiring into collectPossibleBiomes() remains blocked.",
                "collectPossibleBiomes() stays unsupported and unwired in this slice.",
                false,
                true,
                "Runtime collectPossibleBiomes wiring remains intentionally blocked."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .GET_NOISE_BIOME_WIRING_BLOCKED,
                "CaveniaRuntimeBiomeSource",
                "Builder or converter wiring into getNoiseBiome(...) remains blocked.",
                "getNoiseBiome(...) stays unsupported and unwired in this slice.",
                false,
                false,
                "Runtime getNoiseBiome wiring remains intentionally blocked."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .RUNTIME_CONSTRUCTION_FACTORY_BLOCKED,
                "CaveniaRuntimeBiomeSource",
                "Runtime construction or factory behavior remains blocked after guarded builder implementation.",
                "No runtime construction or factory layer exists in this slice.",
                false,
                true,
                "Runtime construction or factory work remains intentionally blocked."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .CODEC_AND_REGISTRATION_BLOCKED,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                "Usable codec behavior and runtime registration remain blocked after guarded builder implementation.",
                "Codec implementation, codec registration and biome-source type registration remain outside this slice.",
                false,
                false,
                "Codec and registration work remain intentionally blocked."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .DIMENSION_WORLDGEN_ACCESS_SPAWNING_BLOCKED,
                "CaveniaActivationReadinessMatrix",
                "Dimension activation, worldgen resources, access and spawning remain blocked after guarded builder implementation.",
                "No dimension resources, worldgen resources, access or spawning behavior may open from this slice.",
                false,
                false,
                "Activation, worldgen, access and spawning remain intentionally blocked."
            ),
            entry(
                CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent
                    .NEXT_DECISION_SELECTED,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation",
                "The consolidated guarded builder state selects runtime construction or factory go or no-go as the next safe branch.",
                "The next slice may add only a runtime construction or factory go/no-go decision and must remain decision-only unless explicitly opened later.",
                true,
                true,
                "The next branch must remain decision-only and must not widen into readiness, implementation or runtime wiring."
            )
        );

    private CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation() {
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent>
        components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry> entryFor(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationReady() {
        return true;
    }

    public static boolean runtimeConstructionFactoryGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
            .decisionIsGoForRuntimeConstructionFactoryReadinessNext();
    }

    public static boolean runtimeConstructionFactoryGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean designatedBuilderExists() {
        return true;
    }

    public static String designatedBuilderSimpleName() {
        return DESIGNATED_BUILDER_SIMPLE_NAME;
    }

    public static String designatedBuilderFileName() {
        return DESIGNATED_BUILDER_FILE_NAME;
    }

    public static boolean collectPossibleBiomesHolderSetBuilderReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.builderReady();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderRuntimeReady() {
        return false;
    }

    public static boolean buildCandidateHolderListReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.buildCandidateHolderListReady();
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

    public static boolean preResolvedHolderListReady() {
        return true;
    }

    public static boolean resolvedHolderDeduplicationReady() {
        return true;
    }

    public static boolean legacyOrderPreservationReady() {
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

    public static boolean runtimeConstructionFactoryReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision
        selectedNextDecision() {
        return SELECTED_NEXT_DECISION;
    }

    public static boolean runtimeConstructionFactoryGoNoGoIsNext() {
        return true;
    }

    public static boolean runtimeConstructionFactoryReadinessReady() {
        return false;
    }

    public static boolean runtimeConstructionFactoryImplementationReady() {
        return false;
    }

    public static boolean runtimeConstructionFactoryRuntimeReady() {
        return false;
    }

    public static boolean nextSliceMayAddRuntimeConstructionFactoryGoNoGoDecision() {
        return true;
    }

    public static boolean nextSliceMayAddRuntimeConstructionFactoryReadiness() {
        return false;
    }

    public static boolean nextSliceMayImplementRuntimeConstructionFactory() {
        return false;
    }

    public static boolean nextSliceMayWireBuilderIntoCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayWireBuilderIntoGetNoiseBiome() {
        return false;
    }

    public static boolean nextSliceMayImplementCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
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

    public static boolean nextSliceMayRegisterGenerator() {
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

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
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

    private static CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry entry(
        CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent component,
        String sourceContractName,
        String consolidatedState,
        String runtimeBoundary,
        boolean builderReady,
        boolean nextDecisionInput,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry(
            component,
            sourceContractName,
            consolidatedState,
            runtimeBoundary,
            builderReady,
            false,
            nextDecisionInput,
            blocker
        );
    }
}
