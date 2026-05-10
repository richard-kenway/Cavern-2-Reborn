package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaAdapterCodecRegistrationReadiness {
    private static final List<CaveniaAdapterCodecRegistrationRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaAdapterCodecRegistrationRequirement.ADAPTER_SHAPE_AVAILABLE,
            "CaveniaBiomeSelectionAdapterContract",
            CaveniaBiomeSelectionAdapterContract.adapterShapeReady(),
            "Adapter query and result shape exist, but runtime biome-source registration remains blocked."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.SERIALIZATION_MODEL_DECISION,
            "CaveniaBiomeSelectionAdapterQuery",
            false,
            "No serialization model decision exists for adapter query inputs yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.CODEC_SHAPE_DECISION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            "No codec shape decision exists for the adapter contract yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.CODEC_IMPLEMENTATION,
            "CaveniaGeneratorRegistrationBoundary",
            false,
            "No biome-source codec implementation exists for the adapter contract yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.BIOME_SOURCE_TYPE_KEY,
            "CaveniaActivationReadinessMatrix",
            false,
            "No biome-source type key exists for adapter runtime registration yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.BIOME_SOURCE_TYPE_REGISTRATION,
            "CaveniaGeneratorRegistrationBoundary",
            false,
            "No biome-source type registration exists for the adapter runtime path yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.REGISTRY_LOOKUP_ACCESS,
            "CaveniaBiomeSourceStrategyContracts",
            false,
            "No biome registry lookup access exists for adapter runtime resolution yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.RUNTIME_BIOME_SOURCE_CLASS,
            "CaveniaBiomeSelectionAdapterContract",
            false,
            "No runtime biome-source class exists to consume the adapter contract yet."
        ),
        contract(
            CaveniaAdapterCodecRegistrationRequirement.DIMENSION_BINDING_DEFERRED,
            "CaveniaActivationReadinessMatrix",
            false,
            "Dimension binding remains deferred until runtime biome-source registration exists."
        )
    );

    private CaveniaAdapterCodecRegistrationReadiness() {
    }

    public static List<CaveniaAdapterCodecRegistrationRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaAdapterCodecRegistrationRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaAdapterCodecRegistrationRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaAdapterCodecRegistrationRequirementContract> contractFor(
        CaveniaAdapterCodecRegistrationRequirement requirement
    ) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static int requirementCount() {
        return CONTRACTS.size();
    }

    public static boolean adapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
    }

    public static boolean adapterRuntimeReady() {
        return false;
    }

    public static boolean codecRegistrationReadinessReady() {
        return true;
    }

    public static boolean codecRegistrationRuntimeReady() {
        return false;
    }

    public static boolean allRequirementsRuntimeBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaAdapterCodecRegistrationRequirementContract::readyForRuntime);
    }

    public static boolean anyRequirementReadyForRuntime() {
        return CONTRACTS.stream().anyMatch(CaveniaAdapterCodecRegistrationRequirementContract::readyForRuntime);
    }

    public static boolean anyCodecImplemented() {
        return CONTRACTS.stream().anyMatch(CaveniaAdapterCodecRegistrationRequirementContract::codecImplemented);
    }

    public static boolean anyRegistered() {
        return CONTRACTS.stream().anyMatch(CaveniaAdapterCodecRegistrationRequirementContract::registered);
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

    public static boolean biomeSourceTypeKeyReady() {
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

    public static boolean consumesAdapterContract() {
        return true;
    }

    public static int adapterEntryCount() {
        return CaveniaBiomeSelectionAdapterContract.entryCount();
    }

    public static int adapterTotalWeight() {
        return CaveniaBiomeSelectionAdapterContract.totalWeight();
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return CaveniaBiomeSelectionAdapterContract.weightedSelectionAlgorithmReady();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaBiomeSelectionAdapterContract.candidateInventoryReady();
    }

    public static boolean registryLookupReadinessReady() {
        return CaveniaRegistryLookupReadiness.registryLookupReadinessReady();
    }

    public static boolean registryLookupRuntimeReady() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaBiomeSelectionAdapterContract.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaBiomeSelectionAdapterContract.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaBiomeSelectionAdapterContract.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaAdapterCodecRegistrationRequirementContract contract(
        CaveniaAdapterCodecRegistrationRequirement requirement,
        String sourceContractName,
        boolean prerequisiteSatisfied,
        String blocker
    ) {
        return new CaveniaAdapterCodecRegistrationRequirementContract(
            requirement,
            sourceContractName,
            prerequisiteSatisfied,
            false,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
