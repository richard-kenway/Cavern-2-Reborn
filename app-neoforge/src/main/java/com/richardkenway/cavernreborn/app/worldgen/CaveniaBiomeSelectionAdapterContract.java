package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Objects;

public final class CaveniaBiomeSelectionAdapterContract {
    private CaveniaBiomeSelectionAdapterContract() {
    }

    public static CaveniaBiomeSelectionAdapterQuery queryForWeightValue(int weightValue) {
        return CaveniaBiomeSelectionAdapterQuery.ofWeightValue(weightValue);
    }

    public static CaveniaBiomeSelectionAdapterResult selectCandidate(CaveniaBiomeSelectionAdapterQuery query) {
        Objects.requireNonNull(query, "query");
        CaveniaWeightedBiomeSelectionResult selection = CaveniaWeightedBiomeSelectionAlgorithm.selectByWeightValue(query.weightValue());

        return new CaveniaBiomeSelectionAdapterResult(
            query.weightValue(),
            selection.normalizedWeightValue(),
            selection.legacyBiomeName(),
            selection.candidateModernBiomeKey(),
            selection.weight(),
            true,
            false,
            false,
            false,
            false
        );
    }

    public static CaveniaBiomeSelectionAdapterResult selectCandidateByWeightValue(int weightValue) {
        return selectCandidate(queryForWeightValue(weightValue));
    }

    public static int entryCount() {
        return CaveniaWeightedBiomeSelectionAlgorithm.entryCount();
    }

    public static int totalWeight() {
        return CaveniaWeightedBiomeSelectionAlgorithm.totalWeight();
    }

    public static int firstWeightValue() {
        return CaveniaWeightedBiomeSelectionAlgorithm.firstWeightValue();
    }

    public static int lastWeightValue() {
        return CaveniaWeightedBiomeSelectionAlgorithm.lastWeightValue();
    }

    public static boolean adapterShapeReady() {
        return true;
    }

    public static boolean adapterRuntimeReady() {
        return false;
    }

    public static boolean consumesWeightedSelector() {
        return true;
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady();
    }

    public static boolean weightedSelectionAlgorithmRuntimeReady() {
        return false;
    }

    public static boolean candidateInventoryReady() {
        return CaveniaWeightedBiomeSelectionAlgorithm.candidateInventoryReady();
    }

    public static boolean finalRuntimeMappingReady() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
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

    public static boolean modernBiomeMappingReady() {
        return false;
    }

    public static boolean planIsNonRuntime() {
        return CaveniaBiomeSourceStrategyPlan.planIsNonRuntime();
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaBiomeSourceStrategyPlan.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaBiomeSourceStrategyPlan.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaBiomeSourceStrategyPlan.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }
}
