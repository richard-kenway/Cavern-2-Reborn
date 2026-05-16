package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision {
    private static final String FALLBACK_LEGACY_BIOME_NAME = "PLAINS";
    private static final String FALLBACK_CANDIDATE_MODERN_BIOME_KEY = "minecraft:plains";
    private static final CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryNextDecision SELECTED_DECISION =
        CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryNextDecision
            .PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_READINESS_NEXT;
    private static final List<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract> GUARDRAILS =
        List.of(
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_RUNTIME_CONSTRUCTION_FACTORY_READINESS_NEXT,
                "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision",
                true,
                "The next slice may add only runtime construction or factory readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_PRIVATE_CONSTRUCTOR_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSource",
                true,
                "The next slice may pin the current private-constructor policy only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_FACTORY_METHOD_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision",
                true,
                "The next slice may pin a future guarded factory-method policy only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_PRE_RESOLVED_HOLDER_STATE_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation",
                true,
                "The next slice may pin a future pre-resolved possible-biome holder-state policy only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_HOLDER_SET_BUILDER_DEPENDENCY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                true,
                "The next slice may pin the holder-set builder dependency only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_LOOKUP_PROVIDER_INPUT_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                true,
                "The next slice may pin caller-provided lookup or provider input policy only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_IMMUTABLE_STATE_STORAGE_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder",
                true,
                "The next slice may pin immutable runtime state-storage policy only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_COLLECT_POSSIBLE_BIOMES_STREAM_SOURCE_POLICY_PINNING_NEXT,
                "CaveniaRuntimeBiomeSourceApiShapeInventory",
                true,
                "The next slice may pin future collectPossibleBiomes stream-source policy only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .ALLOW_NO_ACTIVATION_BOUNDARY_PINNING_NEXT,
                "CaveniaActivationReadinessMatrix",
                true,
                "The next slice may pin the no-activation boundary only as readiness data."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_RUNTIME_CONSTRUCTION_FACTORY_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision",
                false,
                "The next slice may not implement runtime construction or factory behavior."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_PUBLIC_CONSTRUCTOR,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not add a public constructor to the guarded runtime biome source."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_FACTORY_METHOD,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not add a runtime factory method."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_PRE_RESOLVED_HOLDER_STATE,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not add pre-resolved holder state to the guarded runtime biome source."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the holder-set builder into collectPossibleBiomes."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep collectPossibleBiomes unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice may not wire the holder-set builder or converter into getNoiseBiome."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                "CaveniaRuntimeBiomeSource",
                false,
                "The next slice must keep getNoiseBiome unsupported."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
                "CaveniaRuntimeBiomeSourceCodecMethodShapeStub",
                false,
                "Codec behavior and biome-source type registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_GENERATOR_REGISTRATION,
                "CaveniaGeneratorRegistrationBoundary",
                false,
                "Generator registration must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_DIMENSION_ACTIVATION,
                "CaveniaActivationReadinessMatrix",
                false,
                "Dimension activation must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                "CaveniaActivationReadinessMatrix",
                false,
                "Worldgen, access and spawning must remain absent in the next slice."
            ),
            guardrail(
                CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED,
                "CaveniaRuntimeBiomeSource",
                false,
                "Runtime methods must remain unsupported even while construction or factory readiness is pinned next."
            )
        );

    private CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryNextDecision selectedDecision() {
        return SELECTED_DECISION;
    }

    public static boolean decisionIsGoForRuntimeConstructionFactoryReadinessNext() {
        return true;
    }

    public static boolean decisionIsReadinessOnlyForNextSlice() {
        return true;
    }

    public static boolean runtimeConstructionFactoryReadinessImplementedInThisSlice() {
        return false;
    }

    public static boolean runtimeConstructionFactoryImplementationReady() {
        return false;
    }

    public static boolean runtimeConstructionFactoryRuntimeReady() {
        return false;
    }

    public static boolean runtimeWiringAllowedInThisSlice() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail guardrail
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

    public static int allowedNextSliceReadinessActionCount() {
        return 9;
    }

    public static int allowedNextSliceImplementationActionCount() {
        return 0;
    }

    public static int allowedNextSliceRuntimeWiringActionCount() {
        return 0;
    }

    public static int allowedNextSliceActivationActionCount() {
        return 0;
    }

    public static boolean anyImplementationAllowedByGuardrails() {
        return false;
    }

    public static boolean anyRuntimeWiringAllowedByGuardrails() {
        return false;
    }

    public static boolean anyActivationAllowedByGuardrails() {
        return false;
    }

    public static boolean nextSliceMayAddRuntimeConstructionFactoryReadiness() {
        return true;
    }

    public static boolean nextSliceMayPinPrivateConstructorPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinFactoryMethodPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinPreResolvedHolderStatePolicy() {
        return true;
    }

    public static boolean nextSliceMayPinHolderSetBuilderDependency() {
        return true;
    }

    public static boolean nextSliceMayPinLookupProviderInputPolicy() {
        return true;
    }

    public static boolean nextSliceMayPinImmutableStateStoragePolicy() {
        return true;
    }

    public static boolean nextSliceMayPinCollectPossibleBiomesStreamSourcePolicy() {
        return true;
    }

    public static boolean nextSliceMayPinNoActivationBoundary() {
        return true;
    }

    public static boolean nextSliceMayImplementRuntimeConstructionFactory() {
        return false;
    }

    public static boolean nextSliceMayAddPublicConstructor() {
        return false;
    }

    public static boolean nextSliceMayAddFactoryMethod() {
        return false;
    }

    public static boolean nextSliceMayAddPreResolvedHolderState() {
        return false;
    }

    public static boolean nextSliceMayWireBuilderIntoCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayImplementCollectPossibleBiomes() {
        return false;
    }

    public static boolean nextSliceMayWireBuilderIntoGetNoiseBiome() {
        return false;
    }

    public static boolean nextSliceMayMakeGetNoiseBiomeUsable() {
        return false;
    }

    public static boolean nextSliceMayMakeCollectPossibleBiomesUsable() {
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

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
            .collectPossibleBiomesHolderSetBuilderConsolidationReady();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationRuntimeReady() {
        return false;
    }

    public static boolean builderConsolidationSelectedNextDecisionMatches() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation.selectedNextDecision()
            == CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationNextDecision
                .PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_GO_NO_GO_NEXT;
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
        return FALLBACK_LEGACY_BIOME_NAME;
    }

    public static String fallbackCandidateModernBiomeKey() {
        return FALLBACK_CANDIDATE_MODERN_BIOME_KEY;
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

    public static boolean normalRuntimeConstructionAllowed() {
        return false;
    }

    public static boolean publicConstructionAllowed() {
        return false;
    }

    public static boolean factoryMethodReady() {
        return false;
    }

    public static boolean preResolvedHolderStateReady() {
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

    private static CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceReadinessAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceReadinessAction,
            false,
            false,
            false,
            true,
            blocker
        );
    }
}
