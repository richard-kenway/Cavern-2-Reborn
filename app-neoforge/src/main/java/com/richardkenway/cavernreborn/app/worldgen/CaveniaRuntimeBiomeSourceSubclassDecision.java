package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceSubclassDecision {
    private static final List<CaveniaRuntimeBiomeSourceSubclassRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.SKELETON_READY,
            "CaveniaRuntimeBiomeSourceSkeleton",
            true,
            true,
            "The unregistered runtime-biome-source skeleton is ready, but the real subclass remains deferred."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.REAL_SUBCLASS_DEFERRED,
            "CaveniaRuntimeBiomeSourceSubclassDecision",
            true,
            true,
            "The next real Minecraft biome-source subclass stays deferred until codec, holder and registry shape decisions are pinned."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.ABSTRACT_METHODS_INVENTORY,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The local BiomeSource abstract method inventory is pinned as inert inventory data, but the real subclass still stays deferred."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.CODEC_METHOD_SHAPE_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The local codec method shape is pinned as inert inventory data, but codec implementation and registration remain blocked."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.POSSIBLE_BIOMES_SHAPE_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The local possible-biomes shape is pinned as inert inventory data, but no holder-return path is implemented."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.NOISE_BIOME_QUERY_SHAPE_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The local noise-biome query shape is pinned as inert inventory data, but runtime biome resolution remains blocked."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.HOLDER_RETURN_PATH_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The holder-return requirement is pinned as inert inventory data, but no runtime holder resolution path exists."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.REGISTRY_CONTEXT_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The registry-context requirement is pinned as inert inventory data, but runtime registry access remains blocked."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.RESOURCE_KEY_CONVERSION_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The resource-key conversion requirement is pinned as inert inventory data, but no runtime conversion implementation exists."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.MISSING_BIOME_FALLBACK_DECISION,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            true,
            true,
            "The missing-biome fallback requirement is pinned as inert inventory data, but no runtime fallback implementation exists."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_DIMENSION_ACTIVATION,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            true,
            true,
            "Dimension activation remains explicitly blocked while runtime biome-source work stays non-activating."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_CODEC_REGISTRATION,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            true,
            true,
            "Codec registration remains explicitly blocked while the biome-source API shape stays undecided."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            true,
            true,
            "Registry lookup access remains explicitly blocked while the runtime biome return path stays undecided."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_WORLDGEN_ACCESS_SPAWNING,
            "CaveniaRuntimeBiomeSourceFirstImplementationDecision",
            true,
            true,
            "Worldgen, access and spawning remain blocked while the real biome-source subclass is still deferred."
        )
    );

    private CaveniaRuntimeBiomeSourceSubclassDecision() {
    }

    public static CaveniaRuntimeBiomeSourceSubclassNextStepDecision selectedDecision() {
        return CaveniaRuntimeBiomeSourceSubclassNextStepDecision
            .DEFER_REAL_BIOME_SOURCE_SUBCLASS_UNTIL_CODEC_HOLDER_REGISTRY_SHAPE_IS_PINNED;
    }

    public static CaveniaRuntimeBiomeSourceSubclassNextStepDecision allowedNextDecision() {
        return CaveniaRuntimeBiomeSourceSubclassNextStepDecision.PROCEED_WITH_CODEC_HOLDER_REGISTRY_SHAPE_DECISION_NEXT;
    }

    public static boolean realSubclassDeferred() {
        return true;
    }

    public static boolean codecHolderRegistryShapeDecisionIsNext() {
        return true;
    }

    public static boolean realMinecraftBiomeSourceImplementedInThisSlice() {
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

    public static List<CaveniaRuntimeBiomeSourceSubclassRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaRuntimeBiomeSourceSubclassRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaRuntimeBiomeSourceSubclassRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceSubclassRequirementContract> contractFor(
        CaveniaRuntimeBiomeSourceSubclassRequirement requirement
    ) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static int requirementCount() {
        return CONTRACTS.size();
    }

    public static boolean skeletonReady() {
        return CaveniaRuntimeBiomeSourceSkeleton.unregisteredSkeletonReady();
    }

    public static boolean skeletonRuntimeReady() {
        return false;
    }

    public static boolean allHardGuardrailsStillPinned() {
        return true;
    }

    public static boolean apiShapeDecisionsPinned() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
    }

    public static boolean readyForRealSubclass() {
        return false;
    }

    public static boolean abstractMethodsInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.abstractMethodsInventoryReady();
    }

    public static boolean codecMethodShapeReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.codecMethodShapeReady();
    }

    public static boolean possibleBiomesShapeReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.possibleBiomesShapeReady();
    }

    public static boolean noiseBiomeQueryShapeReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.noiseBiomeQueryShapeReady();
    }

    public static boolean holderReturnPathReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.holderReturnShapeReady();
    }

    public static boolean registryContextReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.registryContextShapeReady();
    }

    public static boolean resourceKeyConversionReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.resourceKeyConversionShapeReady();
    }

    public static boolean missingBiomeFallbackReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.missingBiomeFallbackShapeReady();
    }

    public static boolean nextSliceMayInventoryRuntimeBiomeSourceApiShape() {
        return true;
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

    public static boolean nextSliceMayImplementRealBiomeSourceSubclass() {
        return false;
    }

    public static boolean nextSliceMayAddCodecImplementation() {
        return false;
    }

    public static boolean nextSliceMayRegisterCodec() {
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

    public static boolean skeletonShapeReady() {
        return CaveniaRuntimeBiomeSourceSkeleton.skeletonShapeReady();
    }

    public static boolean skeletonConsumesAdapter() {
        return CaveniaRuntimeBiomeSourceSkeleton.consumesBiomeSelectionAdapter();
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

    private static CaveniaRuntimeBiomeSourceSubclassRequirementContract contract(
        CaveniaRuntimeBiomeSourceSubclassRequirement requirement,
        String sourceContractName,
        boolean prerequisiteSatisfied,
        boolean decisionPinned,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceSubclassRequirementContract(
            requirement,
            sourceContractName,
            prerequisiteSatisfied,
            decisionPinned,
            false,
            false,
            false,
            blocker
        );
    }
}
