package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaDimensionResourceContracts {
    private static final List<CaveniaDimensionResourceRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaDimensionResourceRequirement.DIMENSION_JSON_SHAPE,
            CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
            "CavernNeoForgeDimensions",
            "No Cavenia dimension JSON shape exists yet."
        ),
        contract(
            CaveniaDimensionResourceRequirement.DIMENSION_TYPE_JSON_SHAPE,
            CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
            "CaveniaGeneratorRegistrationBoundary",
            "No Cavenia dimension type JSON shape exists yet."
        ),
        contract(
            CaveniaDimensionResourceRequirement.HEIGHT_AND_LOGICAL_HEIGHT,
            CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
            "CaveniaTerrainGeneratorPolicy",
            "No final Cavenia height and logical-height mapping exists yet."
        ),
        contract(
            CaveniaDimensionResourceRequirement.GENERATOR_BINDING,
            CaveniaGeneratorActivationRequirement.GENERATOR_REGISTRY_ENTRY,
            "CaveniaGeneratorHostContracts",
            "No active Cavenia generator binding exists yet."
        ),
        contract(
            CaveniaDimensionResourceRequirement.BIOME_SOURCE_BINDING,
            CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
            "CaveniaBiomeSourceStrategyContracts",
            "No active Cavenia biome-source binding exists yet."
        ),
        contract(
            CaveniaDimensionResourceRequirement.RESOURCE_ACTIVATION_GUARD,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaActivationReadinessHosts",
            "No Cavenia dimension-resource activation guard exists yet."
        )
    );

    private CaveniaDimensionResourceContracts() {
    }

    public static List<CaveniaDimensionResourceRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaDimensionResourceRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaDimensionResourceRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaDimensionResourceRequirementContract> contractFor(
        CaveniaDimensionResourceRequirement requirement
    ) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static boolean allRequirementsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaDimensionResourceRequirementContract::ready);
    }

    public static boolean anyRequirementReady() {
        return CONTRACTS.stream().anyMatch(CaveniaDimensionResourceRequirementContract::ready);
    }

    public static boolean anyResourcePresent() {
        return CONTRACTS.stream().anyMatch(CaveniaDimensionResourceRequirementContract::resourcePresent);
    }

    public static boolean anyRuntimeBound() {
        return CONTRACTS.stream().anyMatch(CaveniaDimensionResourceRequirementContract::runtimeBound);
    }

    public static boolean canCreateLevel() {
        return CONTRACTS.stream().anyMatch(CaveniaDimensionResourceRequirementContract::canCreateLevel);
    }

    public static boolean dimensionJsonPresent() {
        return contractFor(CaveniaDimensionResourceRequirement.DIMENSION_JSON_SHAPE)
            .map(CaveniaDimensionResourceRequirementContract::resourcePresent)
            .orElse(false);
    }

    public static boolean dimensionTypeJsonPresent() {
        return contractFor(CaveniaDimensionResourceRequirement.DIMENSION_TYPE_JSON_SHAPE)
            .map(CaveniaDimensionResourceRequirementContract::resourcePresent)
            .orElse(false);
    }

    public static boolean dimensionResourceHostReady() {
        return CaveniaActivationReadinessHosts.dimensionResourceHostReady();
    }

    public static boolean generatorHostReady() {
        return CaveniaGeneratorHostContracts.generatorHostReady();
    }

    public static boolean biomeSourceStrategyHostReady() {
        return CaveniaBiomeSourceStrategyContracts.biomeSourceStrategyHostReady();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaDimensionResourceRequirementContract contract(
        CaveniaDimensionResourceRequirement requirement,
        CaveniaGeneratorActivationRequirement activationRequirement,
        String sourcePolicyName,
        String blocker
    ) {
        return new CaveniaDimensionResourceRequirementContract(
            requirement,
            activationRequirement,
            sourcePolicyName,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
