package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaBiomeSourceStrategyReadinessMatrix {
    private static final List<CaveniaBiomeSourceStrategyReadinessMatrixEntry> ENTRIES = List.of(
        entry(
            CaveniaBiomeSourceStrategyReadinessSurface.SELECTED_SURFACE_PLAN,
            "CaveniaBiomeSourceStrategyPlan",
            CaveniaBiomeSourceStrategyPlan.entries().size(),
            "The selected-surface plan exists, but runtime biome-source activation remains blocked."
        ),
        entry(
            CaveniaBiomeSourceStrategyReadinessSurface.LEGACY_MAPPING_INVENTORY,
            "CaveniaLegacyToModernBiomeKeyMappings",
            CaveniaLegacyToModernBiomeKeyMappings.entryCount(),
            "Legacy biome-key inventory exists, but final runtime biome mapping remains unresolved."
        ),
        entry(
            CaveniaBiomeSourceStrategyReadinessSurface.WEIGHTED_SELECTION_ALGORITHM,
            "CaveniaWeightedBiomeSelectionAlgorithm",
            CaveniaWeightedBiomeSelectionAlgorithm.entryCount(),
            "The pure weighted-selection algorithm exists, but runtime biome selection remains blocked."
        ),
        entry(
            CaveniaBiomeSourceStrategyReadinessSurface.BIOME_SELECTION_ADAPTER,
            "CaveniaBiomeSelectionAdapterContract",
            CaveniaBiomeSelectionAdapterContract.entryCount(),
            "The unregistered adapter shape exists, but it still cannot resolve runtime biomes."
        ),
        entry(
            CaveniaBiomeSourceStrategyReadinessSurface.ADAPTER_CODEC_REGISTRATION_READINESS,
            "CaveniaAdapterCodecRegistrationReadiness",
            CaveniaAdapterCodecRegistrationReadiness.requirementCount(),
            "Codec and registration readiness contracts exist, but codec implementation and registration remain blocked."
        ),
        entry(
            CaveniaBiomeSourceStrategyReadinessSurface.REGISTRY_LOOKUP_READINESS,
            "CaveniaRegistryLookupReadiness",
            CaveniaRegistryLookupReadiness.requirementCount(),
            "Registry-lookup readiness contracts exist, but runtime lookup and biome resolution remain blocked."
        )
    );

    private CaveniaBiomeSourceStrategyReadinessMatrix() {
    }

    public static List<CaveniaBiomeSourceStrategyReadinessMatrixEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaBiomeSourceStrategyReadinessSurface> surfaces() {
        return ENTRIES.stream()
            .map(CaveniaBiomeSourceStrategyReadinessMatrixEntry::surface)
            .toList();
    }

    public static Optional<CaveniaBiomeSourceStrategyReadinessMatrixEntry> entryFor(
        CaveniaBiomeSourceStrategyReadinessSurface surface
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.surface() == surface)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static int totalReadinessItemCount() {
        return ENTRIES.stream()
            .mapToInt(CaveniaBiomeSourceStrategyReadinessMatrixEntry::itemCount)
            .sum();
    }

    public static CaveniaActivationSurface selectedSurface() {
        return CaveniaFirstActiveSurfaceSelection.selectedSurface();
    }

    public static boolean selectedSurfaceIsBiomeSourceStrategy() {
        return CaveniaFirstActiveSurfaceSelection.selectedSurfaceIsBiomeSourceStrategy();
    }

    public static boolean allReadinessLayersReady() {
        return ENTRIES.stream().allMatch(CaveniaBiomeSourceStrategyReadinessMatrixEntry::readinessLayerReady);
    }

    public static boolean allRuntimeSurfacesBlocked() {
        return ENTRIES.stream().noneMatch(CaveniaBiomeSourceStrategyReadinessMatrixEntry::runtimeReady);
    }

    public static boolean anyRuntimeReady() {
        return ENTRIES.stream().anyMatch(CaveniaBiomeSourceStrategyReadinessMatrixEntry::runtimeReady);
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static int planStepCount() {
        return CaveniaBiomeSourceStrategyPlan.entries().size();
    }

    public static int legacyMappingEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static int weightedSelectionEntryCount() {
        return CaveniaWeightedBiomeSelectionAlgorithm.entryCount();
    }

    public static int adapterEntryCount() {
        return CaveniaBiomeSelectionAdapterContract.entryCount();
    }

    public static int adapterCodecRegistrationRequirementCount() {
        return CaveniaAdapterCodecRegistrationReadiness.requirementCount();
    }

    public static int registryLookupRequirementCount() {
        return CaveniaRegistryLookupReadiness.requirementCount();
    }

    public static int legacyBiomeTotalWeight() {
        return CaveniaBiomeSourceStrategyPlan.legacyBiomeTotalWeight();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return CaveniaRegistryLookupReadiness.candidateKeysStillStringOnly();
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static boolean adapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
    }

    public static boolean codecRegistrationReadinessReady() {
        return CaveniaAdapterCodecRegistrationReadiness.codecRegistrationReadinessReady();
    }

    public static boolean registryLookupReadinessReady() {
        return CaveniaRegistryLookupReadiness.registryLookupReadinessReady();
    }

    public static boolean finalRuntimeMappingReady() {
        return false;
    }

    public static boolean weightedSelectionRuntimeReady() {
        return false;
    }

    public static boolean adapterRuntimeReady() {
        return false;
    }

    public static boolean codecRegistrationRuntimeReady() {
        return false;
    }

    public static boolean registryLookupRuntimeReady() {
        return false;
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

    public static boolean registryVerified() {
        return false;
    }

    public static boolean runtimeBiomeResolved() {
        return false;
    }

    public static boolean modernBiomeMappingReady() {
        return false;
    }

    public static boolean dimensionBindingReady() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaRegistryLookupReadiness.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRegistryLookupReadiness.dimensionTypeJsonPresent();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRegistryLookupReadiness.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaBiomeSourceStrategyReadinessMatrixEntry entry(
        CaveniaBiomeSourceStrategyReadinessSurface surface,
        String sourceContractName,
        int itemCount,
        String blocker
    ) {
        return new CaveniaBiomeSourceStrategyReadinessMatrixEntry(
            surface,
            sourceContractName,
            itemCount,
            true,
            false,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
