package com.richardkenway.cavernreborn.app.worldgen;

import com.mojang.serialization.MapCodec;

import java.util.stream.Stream;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

public final class CaveniaRuntimeBiomeSource extends BiomeSource {
    private static final String DESIGNATED_SUBCLASS_SIMPLE_NAME = "CaveniaRuntimeBiomeSource";
    private static final String DESIGNATED_SUBCLASS_FILE_NAME = DESIGNATED_SUBCLASS_SIMPLE_NAME + ".java";
    private static final String UNAVAILABLE_REASON =
        "Cavenia runtime biome source is still a guarded unregistered stub and is not usable, registered, or activating yet.";

    private CaveniaRuntimeBiomeSource() {
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        throw unsupported("codec()");
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        throw unsupported("collectPossibleBiomes()");
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        throw unsupported("getNoiseBiome(int, int, int, Climate.Sampler)");
    }

    public static boolean guardedSubclassStubReady() {
        return true;
    }

    public static boolean extendsMinecraftBiomeSource() {
        return true;
    }

    public static String designatedSubclassFileName() {
        return DESIGNATED_SUBCLASS_FILE_NAME;
    }

    public static String designatedSubclassSimpleName() {
        return DESIGNATED_SUBCLASS_SIMPLE_NAME;
    }

    public static boolean normalRuntimeConstructionAllowed() {
        return false;
    }

    public static boolean usableRuntimeBehaviorReady() {
        return false;
    }

    public static boolean unsupportedMethodStubsOnly() {
        return true;
    }

    public static boolean codecMethodStubbed() {
        return true;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean collectPossibleBiomesStubbed() {
        return true;
    }

    public static boolean getNoiseBiomeStubbed() {
        return true;
    }

    public static boolean usableCodecImplementationReady() {
        return false;
    }

    public static boolean realCodecStillDeferred() {
        return true;
    }

    public static boolean staticCodecFieldPresent() {
        return false;
    }

    public static boolean recordCodecBuilderUsed() {
        return false;
    }

    public static String codecMethodUnsupportedReason() {
        return UNAVAILABLE_REASON;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean biomeSourceTypeRegistered() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean holderResourceKeyConversionReady() {
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

    public static boolean collectPossibleBiomesRuntimeReady() {
        return false;
    }

    public static int candidatePossibleBiomesEntryCount() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateEntryCount();
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
    }

    public static boolean getNoiseBiomeRuntimeReady() {
        return false;
    }

    public static boolean noiseBiomeMethodShapePinned() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodShapePinned();
    }

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationGoNoGoIsNext() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationGoNoGoIsNext();
    }

    public static boolean selectorInputDerivationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();
    }

    public static boolean selectorInputDerivationReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady();
    }

    public static boolean selectorInputDerivationImplementationReady() {
        return false;
    }

    public static boolean selectorInputDerivationRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationReadinessIsNext() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddSelectorInputDerivationReadiness();
    }

    public static boolean selectorInputDerivationImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeAlgorithmNext();
    }

    public static boolean selectorInputDerivationAlgorithmReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady();
    }

    public static boolean selectorInputDerivationAlgorithmRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationPureAlgorithmIsNext() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
            .nextSliceMayAddPureNonRuntimeSelectorInputAlgorithm();
    }

    public static String selectorInputDerivationOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputOutputShape();
    }

    public static String selectorInputDerivationAlgorithmOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputOutputShape();
    }

    public static boolean selectorInputAlgorithmConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputAlgorithmConsolidationReady();
    }

    public static boolean selectorInputAlgorithmRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeGoNoGoIsNext() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeGoNoGoIsNext();
    }

    public static boolean selectorToWeightedCandidateBridgeGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
            .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext();
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .selectorToWeightedCandidateBridgeReadinessReady();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeBridgeNext();
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessIsNext() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
            .nextSliceMayAddSelectorToWeightedCandidateBridgeReadiness();
    }

    public static boolean selectorToWeightedCandidateBridgePureImplementationIsNext() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
            .nextSliceMayAddPureNonRuntimeSelectorToWeightedCandidateBridge();
    }

    public static String selectorToWeightedCandidateBridgeCandidateOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyOutputShape();
    }

    public static boolean candidateKeyToHolderConversionGoNoGoIsNext() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .candidateKeyToHolderConversionGoNoGoIsNext();
    }

    public static boolean candidateKeyToHolderConversionGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();
    }

    public static boolean candidateKeyToHolderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
            .candidateKeyToHolderConversionReadinessReady();
    }

    public static boolean candidateKeyToHolderConversionImplementationReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionReadinessIsNext() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
            .nextSliceMayAddCandidateKeyToHolderConversionReadiness();
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();
    }

    public static boolean candidateKeyToHolderConversionImplementationRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionImplementationIsNext() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
            .nextSliceMayAddGuardedConversionHelper();
    }

    public static boolean candidateKeyToHolderConverterReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .candidateKeyToHolderConverterReady();
    }

    public static boolean candidateKeyToHolderConverterRuntimeReady() {
        return false;
    }

    public static String candidateKeyToHolderConverterFileName() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.designatedConverterFileName();
    }

    public static boolean candidateKeyToHolderConverterConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .candidateKeyToHolderConverterConsolidationReady();
    }

    public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetGoNoGoIsNext() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .collectPossibleBiomesHolderSetGoNoGoIsNext();
    }

    public static boolean collectPossibleBiomesHolderSetGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
            .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext();
    }

    public static boolean collectPossibleBiomesHolderSetReadinessReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness
            .collectPossibleBiomesHolderSetReadinessReady();
    }

    public static boolean collectPossibleBiomesHolderSetImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
            .decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext();
    }

    public static boolean collectPossibleBiomesHolderSetImplementationReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetImplementationRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetBuilderReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
            .collectPossibleBiomesHolderSetBuilderReady();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetReadinessIsNext() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
            .nextSliceMayAddCollectPossibleBiomesHolderSetReadiness();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderIsNext() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
            .nextSliceMayAddGuardedPreResolvedHolderSetBuilder();
    }

    public static String collectPossibleBiomesHolderSetOutputShape() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureHolderSetOutputShape();
    }

    public static String collectPossibleBiomesHolderSetBuilderFileName() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision
            .nextSliceDesignatedBuilderFileName();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
            .collectPossibleBiomesHolderSetBuilderConsolidationReady();
    }

    public static boolean collectPossibleBiomesHolderSetBuilderConsolidationRuntimeReady() {
        return false;
    }

    public static boolean runtimeConstructionFactoryGoNoGoIsNext() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation
            .runtimeConstructionFactoryGoNoGoIsNext();
    }

    public static String candidateKeyToHolderConversionInputShape() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyInputShape();
    }

    public static String selectorToWeightedCandidateBridgeOutputShape() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeyOutputShape();
    }

    public static String fallbackLegacyBiomeName() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();
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

    public static boolean apiShapeInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
    }

    public static boolean goNoGoDecisionAllowsSubclass() {
        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddOneDesignatedRealSubclassFile();
    }

    public static boolean allGoNoGoGuardrailsEnforced() {
        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allGuardrailsEnforcedInThisSlice();
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

    private static UnsupportedOperationException unsupported(String methodName) {
        return new UnsupportedOperationException(methodName + " is unavailable because " + UNAVAILABLE_REASON);
    }
}
