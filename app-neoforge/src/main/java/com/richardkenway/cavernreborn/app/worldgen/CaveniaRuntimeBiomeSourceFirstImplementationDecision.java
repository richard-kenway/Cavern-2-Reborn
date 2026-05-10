package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceFirstImplementationDecision {
    private static final List<CaveniaRuntimeBiomeSourceGuardrailContract> GUARDRAILS = List.of(
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_DIMENSION_JSON,
            "CaveniaBiomeSourceStrategyReadinessMatrix",
            false,
            "The next slice must still avoid adding dimension/cavenia.json while runtime surfaces remain blocked."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_DIMENSION_TYPE_JSON,
            "CaveniaBiomeSourceStrategyReadinessMatrix",
            false,
            "The next slice must still avoid adding dimension_type/cavenia.json while runtime surfaces remain blocked."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_ACTIVE_CAVENIA_LEVEL,
            "CaveniaActivationReadinessMatrix",
            false,
            "No active Cavenia runtime level may be created before explicit dimension activation is chosen."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_CODEC_IMPLEMENTATION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            "The next slice must not implement a biome-source codec while the unregistered skeleton remains isolated."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_CODEC_REGISTRATION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            "The next slice must not register a biome-source codec while registration remains explicitly deferred."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_BIOME_SOURCE_TYPE_REGISTRATION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            "The next slice must not register a biome-source type while the runtime skeleton stays unregistered."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            false,
            "The next slice must not add registry lookup access while runtime lookup readiness remains blocked."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_BIOME_HOLDER_OR_RESOURCE_KEY_CONVERSION,
            "CaveniaRegistryLookupReadiness",
            false,
            "The next slice must not add biome holder or runtime biome-key conversion while candidate keys remain string-only."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_WORLDGEN_RESOURCES,
            "CaveniaBiomeSourceStrategyReadinessMatrix",
            false,
            "The next slice must not add active Cavenia worldgen resources while the selected-surface matrix stays blocked."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_ACCESS_OR_TELEPORT,
            "CaveniaActivationReadinessMatrix",
            false,
            "The next slice must not add Cavenia access or teleport while activation remains blocked."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_SPAWNING,
            "CaveniaActivationReadinessMatrix",
            false,
            "The next slice must not add Cavenia spawning while runtime activation remains blocked."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.NO_CAVEMAN_ENTITY_REGISTRATION,
            "CaveniaSpawnHostContracts",
            false,
            "The next slice must not register cavernreborn:caveman while Caveman remains deferred by source-backed spawn contracts."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.UNREGISTERED_SKELETON_ONLY,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            true,
            "The chosen next branch allows only an unregistered runtime-biome-source skeleton without widening into registration work."
        ),
        guardrail(
            CaveniaRuntimeBiomeSourceGuardrail.KEEP_BIOME_SOURCE_STRATEGY_MATRIX_BLOCKED,
            "CaveniaBiomeSourceStrategyReadinessMatrix",
            false,
            "The final BIOME_SOURCE_STRATEGY readiness matrix must remain runtime-blocked until later explicit runtime slices."
        )
    );

    private CaveniaRuntimeBiomeSourceFirstImplementationDecision() {
    }

    public static CaveniaRuntimeBiomeSourceNextStepDecision selectedDecision() {
        return CaveniaRuntimeBiomeSourceNextStepDecision.PROCEED_WITH_UNREGISTERED_RUNTIME_BIOME_SOURCE_SKELETON_NEXT;
    }

    public static boolean decisionIsToProceedWithUnregisteredSkeletonNext() {
        return true;
    }

    public static boolean decisionIsImplementationOnlyForNextSlice() {
        return true;
    }

    public static boolean runtimeBiomeSourceImplementedInThisSlice() {
        return false;
    }

    public static boolean unregisteredSkeletonImplemented() {
        return CaveniaRuntimeBiomeSourceSkeleton.unregisteredSkeletonReady();
    }

    public static boolean unregisteredSkeletonRuntimeReady() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceGuardrail guardrail
    ) {
        return GUARDRAILS.stream()
            .filter(contract -> contract.guardrail() == guardrail)
            .findFirst();
    }

    public static int guardrailCount() {
        return GUARDRAILS.size();
    }

    public static boolean allGuardrailsEnforcedInThisSlice() {
        return GUARDRAILS.stream().allMatch(CaveniaRuntimeBiomeSourceGuardrailContract::enforcedInThisSlice);
    }

    public static boolean anyRuntimeActivationAllowedByGuardrails() {
        return GUARDRAILS.stream().anyMatch(CaveniaRuntimeBiomeSourceGuardrailContract::runtimeActivationAllowed);
    }

    public static boolean nextSliceMayAddUnregisteredSkeleton() {
        return true;
    }

    public static boolean nextSliceMayAddCodecImplementation() {
        return false;
    }

    public static boolean nextSliceMayRegisterCodec() {
        return false;
    }

    public static boolean nextSliceMayRegisterBiomeSourceType() {
        return false;
    }

    public static boolean nextSliceMayUseRegistryLookupAccess() {
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

    public static boolean biomeSourceStrategyMatrixReady() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.allReadinessLayersReady();
    }

    public static boolean biomeSourceStrategyMatrixRuntimeReady() {
        return false;
    }

    public static int biomeSourceStrategyTotalReadinessItemCount() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceRegistered() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRuntimeBiomeSourceGuardrailContract guardrail(
        CaveniaRuntimeBiomeSourceGuardrail guardrail,
        String sourceContractName,
        boolean mayBeRelaxedInNextSlice,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            mayBeRelaxedInNextSlice,
            false,
            blocker
        );
    }
}
