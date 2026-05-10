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
            "CaveniaRuntimeBiomeSourceSubclassDecision",
            false,
            false,
            "The required real-biome-source abstract method inventory has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.CODEC_METHOD_SHAPE_DECISION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            false,
            "The codec method shape for a real biome-source subclass has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.POSSIBLE_BIOMES_SHAPE_DECISION,
            "CaveniaRuntimeBiomeSourceSkeleton",
            false,
            false,
            "The possible-biomes return shape for a real biome-source subclass has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.NOISE_BIOME_QUERY_SHAPE_DECISION,
            "CaveniaRuntimeBiomeSourceSkeleton",
            false,
            false,
            "The noise-biome query shape for a real biome-source subclass has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.HOLDER_RETURN_PATH_DECISION,
            "CaveniaRegistryLookupReadiness",
            false,
            false,
            "The holder return path for resolved runtime biomes has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.REGISTRY_CONTEXT_DECISION,
            "CaveniaRegistryLookupReadiness",
            false,
            false,
            "The registry context needed by a real biome-source subclass has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.RESOURCE_KEY_CONVERSION_DECISION,
            "CaveniaRegistryLookupReadiness",
            false,
            false,
            "The string-to-runtime biome-key conversion path has not been pinned yet."
        ),
        contract(
            CaveniaRuntimeBiomeSourceSubclassRequirement.MISSING_BIOME_FALLBACK_DECISION,
            "CaveniaRegistryLookupReadiness",
            false,
            false,
            "The missing-biome fallback contract for a real biome-source subclass has not been pinned yet."
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
        return false;
    }

    public static boolean readyForRealSubclass() {
        return false;
    }

    public static boolean abstractMethodsInventoryReady() {
        return false;
    }

    public static boolean codecMethodShapeReady() {
        return false;
    }

    public static boolean possibleBiomesShapeReady() {
        return false;
    }

    public static boolean noiseBiomeQueryShapeReady() {
        return false;
    }

    public static boolean holderReturnPathReady() {
        return false;
    }

    public static boolean registryContextReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean missingBiomeFallbackReady() {
        return false;
    }

    public static boolean nextSliceMayInventoryRuntimeBiomeSourceApiShape() {
        return true;
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
