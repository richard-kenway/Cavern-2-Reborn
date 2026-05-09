package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaBiomeSourceStrategyContracts {
    private static final List<CaveniaBiomeSourceStrategyRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaBiomeSourceStrategyRequirement.LEGACY_WEIGHTED_BIOME_INPUTS,
            "CaveniaBiomeTopFilterPolicy",
            "No runtime biome-source strategy consumes the accepted legacy weighted biome inputs yet."
        ),
        contract(
            CaveniaBiomeSourceStrategyRequirement.LEGACY_TO_MODERN_BIOME_KEY_MAPPING,
            "CaveniaBiomeSelectionSkeleton",
            "No legacy-to-modern Cavenia biome-key mapping exists yet."
        ),
        contract(
            CaveniaBiomeSourceStrategyRequirement.WEIGHTED_SELECTION_ALGORITHM,
            "CaveniaBiomeTopFilterPolicy",
            "No runtime weighted biome-selection algorithm exists yet."
        ),
        contract(
            CaveniaBiomeSourceStrategyRequirement.TOP_BLOCK_STRATEGY,
            "CaveniaBiomeTopFilterPolicy",
            "No runtime biome top-block strategy host exists yet."
        ),
        contract(
            CaveniaBiomeSourceStrategyRequirement.RUNTIME_BIOME_SOURCE_IMPLEMENTATION,
            "CaveniaBiomeSelectionSkeleton",
            "No runtime Cavenia biome-source implementation exists yet."
        ),
        contract(
            CaveniaBiomeSourceStrategyRequirement.BIOME_SOURCE_CODEC_AND_REGISTRATION,
            "CaveniaGeneratorRegistrationBoundary",
            "No Cavenia biome-source codec or registration path exists yet."
        ),
        contract(
            CaveniaBiomeSourceStrategyRequirement.REGISTRY_LOOKUP_ACCESS,
            "CaveniaActivationReadinessHosts",
            "No biome registry lookup access for a Cavenia biome-source strategy exists yet."
        )
    );

    private CaveniaBiomeSourceStrategyContracts() {
    }

    public static List<CaveniaBiomeSourceStrategyRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaBiomeSourceStrategyRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaBiomeSourceStrategyRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaBiomeSourceStrategyRequirementContract> contractFor(
        CaveniaBiomeSourceStrategyRequirement requirement
    ) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static boolean allRequirementsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaBiomeSourceStrategyRequirementContract::ready);
    }

    public static boolean anyRequirementReady() {
        return CONTRACTS.stream().anyMatch(CaveniaBiomeSourceStrategyRequirementContract::ready);
    }

    public static boolean anyRequirementRuntimeImplemented() {
        return CONTRACTS.stream().anyMatch(CaveniaBiomeSourceStrategyRequirementContract::runtimeImplemented);
    }

    public static boolean runtimeBiomeSourceReady() {
        return CONTRACTS.stream().anyMatch(CaveniaBiomeSourceStrategyRequirementContract::runtimeBiomeSource);
    }

    public static boolean codecRegistered() {
        return CONTRACTS.stream().anyMatch(CaveniaBiomeSourceStrategyRequirementContract::codecRegistered);
    }

    public static boolean biomeSelectionIsRuntimeBiomeSource() {
        return CaveniaGeneratorRuntimeContracts.biomeSelectionIsRuntimeBiomeSource();
    }

    public static boolean biomeSourceStrategyHostReady() {
        return CaveniaActivationReadinessHosts.biomeSourceStrategyHostReady();
    }

    public static int legacyBiomeEntryCount() {
        return CaveniaBiomeSelectionSkeleton.create().entryCount();
    }

    public static int legacyBiomeTotalWeight() {
        return CaveniaBiomeSelectionSkeleton.create().totalWeight();
    }

    public static boolean requiresBiomeSourceStrategyBeforeActivation() {
        return CaveniaBiomeSelectionSkeleton.create().requiresBiomeSourceStrategyBeforeActivation();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaBiomeSourceStrategyRequirementContract contract(
        CaveniaBiomeSourceStrategyRequirement requirement,
        String sourcePolicyName,
        String blocker
    ) {
        return new CaveniaBiomeSourceStrategyRequirementContract(
            requirement,
            sourcePolicyName,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
