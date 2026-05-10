package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaVeinsContentPolicy;

public final class CaveniaWorldgenResourceContracts {
    private static final List<CaveniaWorldgenResourceRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaWorldgenResourceRequirement.CONFIGURED_CARVER_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaCaveCarverPolicy",
            "No Cavenia configured carver resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.CONFIGURED_FEATURE_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaVeinsContentPolicy",
            "No Cavenia configured feature resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.PLACED_FEATURE_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaVeinsContentPolicy",
            "No Cavenia placed feature resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.BIOME_MODIFIER_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaBiomeSourceStrategyContracts",
            "No Cavenia biome modifier resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.BIOME_TAG_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaBiomeSourceStrategyContracts",
            "No Cavenia biome tag resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.VEINS_RESOURCE_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaVeinsContentPolicy",
            "No Cavenia VEINS resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.POPULATION_RESOURCE_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaPopulationPolicy",
            "No Cavenia population resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.CAVENIC_SHROOM_CAVENIA_MAPPING,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaPopulationPolicy",
            "No Cavenia cavenic_shroom resource mapping exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.RESOURCE_ORDERING_GUARD,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaGeneratorRuntimeContracts",
            "No Cavenia worldgen resource ordering guard exists yet."
        ),
        contract(
            CaveniaWorldgenResourceRequirement.NO_CAVERN_RESOURCE_REUSE_AS_PARITY,
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "CaveniaActivationReadinessHosts",
            "No CAVERN worldgen resource reuse is accepted as Cavenia parity."
        )
    );

    private CaveniaWorldgenResourceContracts() {
    }

    public static List<CaveniaWorldgenResourceRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaWorldgenResourceRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaWorldgenResourceRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaWorldgenResourceRequirementContract> contractFor(
        CaveniaWorldgenResourceRequirement requirement
    ) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static boolean allRequirementsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaWorldgenResourceRequirementContract::ready);
    }

    public static boolean anyRequirementReady() {
        return CONTRACTS.stream().anyMatch(CaveniaWorldgenResourceRequirementContract::ready);
    }

    public static boolean anyResourcePresent() {
        return CONTRACTS.stream().anyMatch(CaveniaWorldgenResourceRequirementContract::resourcePresent);
    }

    public static boolean anyRuntimeBound() {
        return CONTRACTS.stream().anyMatch(CaveniaWorldgenResourceRequirementContract::runtimeBound);
    }

    public static boolean canAffectWorldgen() {
        return CONTRACTS.stream().anyMatch(CaveniaWorldgenResourceRequirementContract::canAffectWorldgen);
    }

    public static boolean configuredCarverResourcesPresent() {
        return resourcePresent(CaveniaWorldgenResourceRequirement.CONFIGURED_CARVER_MAPPING);
    }

    public static boolean configuredFeatureResourcesPresent() {
        return resourcePresent(CaveniaWorldgenResourceRequirement.CONFIGURED_FEATURE_MAPPING);
    }

    public static boolean placedFeatureResourcesPresent() {
        return resourcePresent(CaveniaWorldgenResourceRequirement.PLACED_FEATURE_MAPPING);
    }

    public static boolean biomeModifierResourcesPresent() {
        return resourcePresent(CaveniaWorldgenResourceRequirement.BIOME_MODIFIER_MAPPING);
    }

    public static boolean biomeTagResourcesPresent() {
        return resourcePresent(CaveniaWorldgenResourceRequirement.BIOME_TAG_MAPPING);
    }

    public static boolean worldgenResourceHostReady() {
        return CaveniaActivationReadinessHosts.worldgenResourceHostReady();
    }

    public static boolean dimensionResourceHostReady() {
        return CaveniaDimensionResourceContracts.dimensionResourceHostReady();
    }

    public static boolean generatorHostReady() {
        return CaveniaGeneratorHostContracts.generatorHostReady();
    }

    public static boolean biomeSourceStrategyHostReady() {
        return CaveniaBiomeSourceStrategyContracts.biomeSourceStrategyHostReady();
    }

    public static int veinsPolicyEntryCount() {
        return CaveniaVeinsContentPolicy.entries().size();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static boolean resourcePresent(CaveniaWorldgenResourceRequirement requirement) {
        return contractFor(requirement)
            .map(CaveniaWorldgenResourceRequirementContract::resourcePresent)
            .orElse(false);
    }

    private static CaveniaWorldgenResourceRequirementContract contract(
        CaveniaWorldgenResourceRequirement requirement,
        CaveniaGeneratorActivationRequirement activationRequirement,
        String sourcePolicyName,
        String blocker
    ) {
        return new CaveniaWorldgenResourceRequirementContract(
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
