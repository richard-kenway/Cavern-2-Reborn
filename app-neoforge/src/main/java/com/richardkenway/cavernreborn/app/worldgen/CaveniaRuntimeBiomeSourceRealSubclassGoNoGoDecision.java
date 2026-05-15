package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision {
    private static final String DESIGNATED_SUBCLASS_SIMPLE_NAME = "CaveniaRuntimeBiomeSource";
    private static final String DESIGNATED_SUBCLASS_FILE_NAME = DESIGNATED_SUBCLASS_SIMPLE_NAME + ".java";
    private static final List<CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract> GUARDRAILS = List.of(
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ONE_DESIGNATED_REAL_SUBCLASS_FILE_NEXT,
            "CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision",
            true,
            "The next slice may add exactly one guarded real-subclass file, but this slice stays decision-only."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_BIOME_SOURCE_EXTENDS_ONLY_IN_DESIGNATED_SUBCLASS,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            "The next slice may extend the local BiomeSource API only in the designated subclass file, but this slice stays runtime-API free."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ABSTRACT_METHOD_SIGNATURE_TYPES_ONLY,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            "The next slice may use abstract method signature types only where the guarded real subclass requires them."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_UNSUPPORTED_METHOD_STUBS_ONLY,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            "The next slice may use unsupported or stub method bodies only while the real subclass remains unregistered and inert."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_USABLE_CODEC_IMPLEMENTATION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            "Usable codec implementation remains blocked even if a guarded real subclass stub is introduced next."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_CODEC_REGISTRATION,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            false,
            "Codec registration remains blocked while the guarded real subclass must stay unregistered."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_BIOME_SOURCE_TYPE_REGISTRATION,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            false,
            "Biome-source type registration remains blocked while the guarded real subclass must stay unregistered."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            false,
            "Registry lookup access remains blocked while the runtime biome return path stays inert."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_HOLDER_RESOURCE_KEY_CONVERSION_IMPLEMENTATION,
            "CaveniaRegistryLookupReadiness",
            false,
            "Holder and resource-key conversion implementation remains blocked while the guarded real subclass stays inert."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_DIMENSION_JSON,
            "CaveniaRuntimeBiomeSourceSkeleton",
            false,
            "Dimension JSON stays absent while the guarded real subclass remains non-activating."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_DIMENSION_TYPE_JSON,
            "CaveniaRuntimeBiomeSourceSkeleton",
            false,
            "Dimension-type JSON stays absent while the guarded real subclass remains non-activating."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_ACTIVE_CAVENIA_LEVEL,
            "CaveniaActivationReadinessMatrix",
            false,
            "No active Cavenia level may be created while the guarded real subclass remains non-activating."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_WORLDGEN_RESOURCES,
            "CaveniaBiomeSourceStrategyReadinessMatrix",
            false,
            "Worldgen resources remain blocked while the guarded real subclass is allowed only as an inert file stub."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_ACCESS_OR_TELEPORT,
            "CaveniaActivationReadinessMatrix",
            false,
            "Access and teleport remain blocked while the guarded real subclass remains non-activating."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_SPAWNING,
            "CaveniaActivationReadinessMatrix",
            false,
            "Spawning remains blocked while the guarded real subclass remains non-activating."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_CAVEMAN_ENTITY_REGISTRATION,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            false,
            "Caveman entity registration remains blocked while the guarded real subclass stays inert."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.KEEP_SUBCLASS_UNREGISTERED_AND_UNREFERENCED_BY_RUNTIME,
            "CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision",
            false,
            "Any future guarded real subclass must remain unregistered and unreferenced by runtime surfaces."
        ),
        contract(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.KEEP_EXISTING_SKELETON_AND_API_INVENTORY_INTACT,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            false,
            "The existing skeleton and API inventory must remain intact while a later guarded real subclass is introduced."
        )
    );

    private CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision() {
    }

    public static CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision selectedDecision() {
        return CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision
            .PROCEED_WITH_GUARDED_UNREGISTERED_REAL_BIOME_SOURCE_SUBCLASS_NEXT;
    }

    public static boolean decisionIsGoForGuardedUnregisteredRealSubclassNext() {
        return true;
    }

    public static boolean decisionIsImplementationOnlyForNextSlice() {
        return true;
    }

    public static boolean realSubclassImplementedInThisSlice() {
        return false;
    }

    public static boolean runtimeApiAllowedInThisSlice() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract> guardrails() {
        return GUARDRAILS;
    }

    public static List<CaveniaRuntimeBiomeSourceRealSubclassGuardrail> guardrailValues() {
        return GUARDRAILS.stream()
            .map(CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract::guardrail)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract> guardrailFor(
        CaveniaRuntimeBiomeSourceRealSubclassGuardrail guardrail
    ) {
        return GUARDRAILS.stream()
            .filter(contract -> contract.guardrail() == guardrail)
            .findFirst();
    }

    public static int guardrailCount() {
        return GUARDRAILS.size();
    }

    public static boolean allGuardrailsEnforcedInThisSlice() {
        return GUARDRAILS.stream().allMatch(CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract::enforcedInThisSlice);
    }

    public static boolean anyRuntimeActivationAllowedByGuardrails() {
        return GUARDRAILS.stream().anyMatch(CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract::runtimeActivationAllowed);
    }

    public static int allowedNextSliceActionCount() {
        return (int) GUARDRAILS.stream()
            .filter(CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract::allowsNextSliceAction)
            .count();
    }

    public static boolean nextSliceMayAddOneDesignatedRealSubclassFile() {
        return true;
    }

    public static String nextSliceDesignatedSubclassSimpleName() {
        return DESIGNATED_SUBCLASS_SIMPLE_NAME;
    }

    public static String nextSliceDesignatedSubclassFileName() {
        return DESIGNATED_SUBCLASS_FILE_NAME;
    }

    public static boolean nextSliceMayExtendBiomeSourceOnlyInDesignatedSubclass() {
        return true;
    }

    public static boolean nextSliceMayUseAbstractMethodSignatureTypesOnly() {
        return true;
    }

    public static boolean nextSliceMayUseUnsupportedMethodStubsOnly() {
        return true;
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

    public static boolean nextSliceMayUseRegistryLookupAccess() {
        return false;
    }

    public static boolean nextSliceMayImplementHolderResourceKeyConversion() {
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

    public static boolean apiShapeInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
    }

    public static boolean apiShapeRuntimeReady() {
        return false;
    }

    public static boolean skeletonReady() {
        return CaveniaRuntimeBiomeSourceSkeleton.unregisteredSkeletonReady();
    }

    public static boolean skeletonRuntimeReady() {
        return false;
    }

    public static boolean subclassDecisionApiShapePinned() {
        return CaveniaRuntimeBiomeSourceSubclassDecision.apiShapeDecisionsPinned();
    }

    public static boolean subclassDecisionReadyForRealSubclass() {
        return CaveniaRuntimeBiomeSourceSubclassDecision.readyForRealSubclass();
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

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeBridgeNext();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
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

    public static boolean candidateKeyToHolderConversionGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();
    }

    public static boolean candidateKeyToHolderConversionGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
            .candidateKeyToHolderConversionReadinessReady();
    }

    public static boolean candidateKeyToHolderConversionReadinessRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoRuntimeReady() {
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

    private static CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract contract(
        CaveniaRuntimeBiomeSourceRealSubclassGuardrail guardrail,
        String sourceContractName,
        boolean allowsNextSliceAction,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract(
            guardrail,
            sourceContractName,
            true,
            allowsNextSliceAction,
            false,
            blocker
        );
    }
}
