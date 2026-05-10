package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeEntry;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeTopFilterPolicy;

public final class CaveniaBiomeSourceStrategyPlan {
    private static final List<CaveniaBiomeSourceStrategyPlanEntry> ENTRIES = List.of(
        entry(
            CaveniaBiomeSourceStrategyPlanStep.CONFIRM_SELECTED_SURFACE,
            CaveniaBiomeSourceStrategyRequirement.LEGACY_WEIGHTED_BIOME_INPUTS,
            "CaveniaFirstActiveSurfaceSelection",
            true,
            false,
            "Biome-source strategy remains the selected surface, but this slice still does not allow runtime activation."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.PIN_LEGACY_WEIGHTED_INPUTS,
            CaveniaBiomeSourceStrategyRequirement.LEGACY_WEIGHTED_BIOME_INPUTS,
            "CaveniaBiomeTopFilterPolicy",
            false,
            true,
            "Legacy weighted biome inputs are pinned, but no runtime biome-source strategy consumes them yet."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.PIN_LEGACY_TOP_BLOCK_INPUTS,
            CaveniaBiomeSourceStrategyRequirement.TOP_BLOCK_STRATEGY,
            "CaveniaBiomeTopFilterPolicy",
            false,
            true,
            "Legacy top-block inputs are pinned, but no runtime top-block strategy host exists yet."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.INVENTORY_LEGACY_TO_MODERN_BIOME_MAPPING,
            CaveniaBiomeSourceStrategyRequirement.LEGACY_TO_MODERN_BIOME_KEY_MAPPING,
            "CaveniaBiomeSelectionSkeleton",
            false,
            false,
            "Legacy-to-modern biome-key mapping remains unresolved and is still inventory-only in this slice."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.DEFER_WEIGHTED_SELECTION_ALGORITHM,
            CaveniaBiomeSourceStrategyRequirement.WEIGHTED_SELECTION_ALGORITHM,
            "CaveniaBiomeSourceStrategyContracts",
            false,
            false,
            "Weighted biome-selection algorithm work remains deferred until a later explicit non-runtime or runtime strategy slice."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.DEFER_RUNTIME_BIOME_SOURCE,
            CaveniaBiomeSourceStrategyRequirement.RUNTIME_BIOME_SOURCE_IMPLEMENTATION,
            "CaveniaBiomeSelectionSkeleton",
            false,
            false,
            "Runtime Cavenia biome-source implementation remains deferred and blocked in this slice."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.DEFER_CODEC_AND_REGISTRATION,
            CaveniaBiomeSourceStrategyRequirement.BIOME_SOURCE_CODEC_AND_REGISTRATION,
            "CaveniaGeneratorRegistrationBoundary",
            false,
            false,
            "Biome-source codec and registration remain deferred until a later explicit runtime-biome-source MVP."
        ),
        entry(
            CaveniaBiomeSourceStrategyPlanStep.DEFER_REGISTRY_LOOKUP_ACCESS,
            CaveniaBiomeSourceStrategyRequirement.REGISTRY_LOOKUP_ACCESS,
            "CaveniaActivationReadinessMatrix",
            false,
            false,
            "Biome registry lookup access remains deferred until a later explicit runtime-biome-source MVP."
        )
    );

    private static final List<String> LEGACY_BIOME_NAMES = CaveniaBiomeTopFilterPolicy.entries()
        .stream()
        .map(CaveniaBiomeEntry::legacyBiomeName)
        .toList();

    private CaveniaBiomeSourceStrategyPlan() {
    }

    public static List<CaveniaBiomeSourceStrategyPlanEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaBiomeSourceStrategyPlanStep> steps() {
        return ENTRIES.stream()
            .map(CaveniaBiomeSourceStrategyPlanEntry::step)
            .toList();
    }

    public static Optional<CaveniaBiomeSourceStrategyPlanEntry> entryFor(CaveniaBiomeSourceStrategyPlanStep step) {
        return ENTRIES.stream()
            .filter(entry -> entry.step() == step)
            .findFirst();
    }

    public static CaveniaActivationSurface selectedSurface() {
        return CaveniaFirstActiveSurfaceSelection.selectedSurface();
    }

    public static boolean selectedSurfaceIsBiomeSourceStrategy() {
        return CaveniaFirstActiveSurfaceSelection.selectedSurfaceIsBiomeSourceStrategy();
    }

    public static boolean planIsNonRuntime() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean allStepsRuntimeBlocked() {
        return ENTRIES.stream().noneMatch(CaveniaBiomeSourceStrategyPlanEntry::runtimeReady);
    }

    public static boolean anyStepRuntimeReady() {
        return ENTRIES.stream().anyMatch(CaveniaBiomeSourceStrategyPlanEntry::runtimeReady);
    }

    public static boolean allStepsDisallowActivationInThisSlice() {
        return ENTRIES.stream().noneMatch(CaveniaBiomeSourceStrategyPlanEntry::activationAllowedInThisSlice);
    }

    public static int legacyBiomeEntryCount() {
        return CaveniaBiomeTopFilterPolicy.entries().size();
    }

    public static int legacyBiomeTotalWeight() {
        return CaveniaBiomeTopFilterPolicy.totalWeight();
    }

    public static List<String> legacyBiomeNames() {
        return LEGACY_BIOME_NAMES;
    }

    public static boolean legacyTopBlocksPinned() {
        return true;
    }

    public static boolean legacyWeightedInputsPinned() {
        return true;
    }

    public static boolean modernBiomeMappingReady() {
        return false;
    }

    public static boolean legacyToModernBiomeMappingInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean legacyToModernBiomeFinalMappingReady() {
        return false;
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return false;
    }

    public static boolean weightedSelectionAlgorithmInventoryReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static boolean weightedSelectionAlgorithmFinalRuntimeReady() {
        return false;
    }

    public static boolean unregisteredAdapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
    }

    public static boolean unregisteredAdapterRuntimeReady() {
        return false;
    }

    public static boolean adapterCodecRegistrationReadinessReady() {
        return CaveniaAdapterCodecRegistrationReadiness.codecRegistrationReadinessReady();
    }

    public static boolean adapterCodecRegistrationRuntimeReady() {
        return false;
    }

    public static boolean registryLookupReadinessReady() {
        return CaveniaRegistryLookupReadiness.registryLookupReadinessReady();
    }

    public static boolean registryLookupRuntimeReady() {
        return false;
    }

    public static boolean finalSelectedSurfaceReadinessMatrixReady() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.allReadinessLayersReady();
    }

    public static boolean finalSelectedSurfaceRuntimeReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceFirstImplementationDecisionReady() {
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.decisionIsImplementationOnlyForNextSlice();
    }

    public static boolean runtimeBiomeSourceFirstImplementationRuntimeReady() {
        return false;
    }

    public static boolean unregisteredRuntimeBiomeSourceSkeletonReady() {
        return CaveniaRuntimeBiomeSourceSkeleton.unregisteredSkeletonReady();
    }

    public static boolean unregisteredRuntimeBiomeSourceSkeletonRuntimeReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceSubclassDecisionReady() {
        return CaveniaRuntimeBiomeSourceSubclassDecision.codecHolderRegistryShapeDecisionIsNext();
    }

    public static boolean runtimeBiomeSourceSubclassRuntimeReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceApiShapeInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
    }

    public static boolean runtimeBiomeSourceApiShapeRuntimeReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaActivationReadinessMatrix.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaActivationReadinessMatrix.dimensionTypeJsonPresent();
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static int readinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int readinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaActivationReadinessMatrix.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaBiomeSourceStrategyPlanEntry entry(
        CaveniaBiomeSourceStrategyPlanStep step,
        CaveniaBiomeSourceStrategyRequirement requirement,
        String sourceContractName,
        boolean selectedSurfaceStep,
        boolean inputPinned,
        String blocker
    ) {
        return new CaveniaBiomeSourceStrategyPlanEntry(
            step,
            requirement,
            sourceContractName,
            selectedSurfaceStep,
            inputPinned,
            false,
            false,
            blocker
        );
    }
}
