package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Objects;

public final class CaveniaRuntimeBiomeSourceSkeleton {
    private CaveniaRuntimeBiomeSourceSkeleton() {
    }

    public static CaveniaRuntimeBiomeSourceSkeletonQuery queryForWeightValue(int weightValue) {
        return CaveniaRuntimeBiomeSourceSkeletonQuery.ofWeightValue(weightValue);
    }

    public static CaveniaRuntimeBiomeSourceSkeletonQuery queryForCoordinatesAndWeightValue(
        int x,
        int y,
        int z,
        int weightValue
    ) {
        return CaveniaRuntimeBiomeSourceSkeletonQuery.ofCoordinatesAndWeightValue(x, y, z, weightValue);
    }

    public static CaveniaRuntimeBiomeSourceSkeletonResult selectCandidate(CaveniaRuntimeBiomeSourceSkeletonQuery query) {
        Objects.requireNonNull(query, "query");
        CaveniaBiomeSelectionAdapterResult adapterResult =
            CaveniaBiomeSelectionAdapterContract.selectCandidateByWeightValue(query.weightValue());

        return new CaveniaRuntimeBiomeSourceSkeletonResult(
            query.x(),
            query.y(),
            query.z(),
            adapterResult.inputWeightValue(),
            adapterResult.normalizedWeightValue(),
            adapterResult.legacyBiomeName(),
            adapterResult.candidateModernBiomeKey(),
            adapterResult.weight(),
            true,
            false,
            false,
            false,
            false
        );
    }

    public static CaveniaRuntimeBiomeSourceSkeletonResult selectCandidateByWeightValue(int weightValue) {
        return selectCandidate(queryForWeightValue(weightValue));
    }

    public static boolean skeletonShapeReady() {
        return true;
    }

    public static boolean unregisteredSkeletonReady() {
        return true;
    }

    public static boolean extendsMinecraftBiomeSource() {
        return false;
    }

    public static boolean runtimeBiomeSourceImplemented() {
        return false;
    }

    public static boolean runtimeBiomeSourceRegistered() {
        return false;
    }

    public static boolean codecImplemented() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean biomeHolderResolutionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
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

    public static boolean consumesBiomeSelectionAdapter() {
        return true;
    }

    public static boolean decisionAllowsUnregisteredSkeleton() {
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddUnregisteredSkeleton();
    }

    public static boolean decisionAllowsCodecImplementation() {
        return false;
    }

    public static boolean decisionAllowsRegistryLookupAccess() {
        return false;
    }

    public static boolean decisionAllowsDimensionActivation() {
        return false;
    }

    public static boolean realSubclassDecisionReady() {
        return CaveniaRuntimeBiomeSourceSubclassDecision.codecHolderRegistryShapeDecisionIsNext();
    }

    public static boolean realSubclassReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceApiShapeInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
    }

    public static boolean runtimeBiomeSourceApiShapeRuntimeReady() {
        return false;
    }

    public static boolean realSubclassGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision
            .decisionIsGoForGuardedUnregisteredRealSubclassNext();
    }

    public static boolean realSubclassGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean guardedRealSubclassStubImplemented() {
        return true;
    }

    public static boolean guardedRealSubclassRuntimeReady() {
        return false;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
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

    public static int guardrailCount() {
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrailCount();
    }

    public static boolean allGuardrailsEnforced() {
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.allGuardrailsEnforcedInThisSlice();
    }

    public static int adapterEntryCount() {
        return CaveniaBiomeSelectionAdapterContract.entryCount();
    }

    public static int adapterTotalWeight() {
        return CaveniaBiomeSelectionAdapterContract.totalWeight();
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
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRuntimeBiomeSourceFirstImplementationDecision.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }
}
