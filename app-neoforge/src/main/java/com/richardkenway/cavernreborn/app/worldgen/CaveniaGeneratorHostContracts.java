package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaGeneratorHostContracts {
    private static final List<CaveniaGeneratorHostRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaGeneratorHostRequirement.RUNTIME_CONTEXT,
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            "CaveniaGeneratorBridge",
            "No runtime Cavenia generator host context exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.BASE_TERRAIN_FILL,
            CaveniaGeneratorRuntimeOperation.BASE_TERRAIN_FILL,
            "CaveniaTerrainGeneratorPolicy",
            "No runtime base-terrain fill host exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.CAVE_CARVER_EXECUTION,
            CaveniaGeneratorRuntimeOperation.CAVE_CARVING,
            "CaveniaCaveCarverPolicy",
            "No runtime cave-carver execution host exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.BIOME_TOP_FILTER_EXECUTION,
            CaveniaGeneratorRuntimeOperation.BIOME_TOP_FILTER_REPLACEMENT,
            "CaveniaBiomeTopFilterPolicy",
            "No runtime biome top/filter execution host exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.VEINS_MUTATION_EXECUTION,
            CaveniaGeneratorRuntimeOperation.VEINS_MUTATION,
            "CaveniaVeinsContentPolicy",
            "No runtime VEINS mutation host exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.FINAL_CHUNK_CONSTRUCTION,
            CaveniaGeneratorRuntimeOperation.FINAL_CHUNK_CONSTRUCTION,
            "CaveniaGeneratorRuntimeContracts",
            "No runtime final chunk-construction host exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.POPULATION_DELEGATION,
            CaveniaGeneratorRuntimeOperation.POPULATION_INTEGRATION,
            "CaveniaPopulationPolicy",
            "No runtime population-delegation host exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.CODEC_AND_REGISTRATION,
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            "CaveniaGeneratorRegistrationBoundary",
            "No Cavenia generator codec or registry entry exists yet."
        ),
        contract(
            CaveniaGeneratorHostRequirement.DIMENSION_RESOURCE_BINDING,
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            "CaveniaActivationReadinessHosts",
            "No active Cavenia dimension JSON or dimension type JSON binding exists yet."
        )
    );

    private CaveniaGeneratorHostContracts() {
    }

    public static List<CaveniaGeneratorHostRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaGeneratorHostRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaGeneratorHostRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaGeneratorHostRequirementContract> contractFor(CaveniaGeneratorHostRequirement requirement) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static boolean allRequirementsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaGeneratorHostRequirementContract::ready);
    }

    public static boolean anyRequirementReady() {
        return CONTRACTS.stream().anyMatch(CaveniaGeneratorHostRequirementContract::ready);
    }

    public static boolean anyRequirementRuntimeImplemented() {
        return CONTRACTS.stream().anyMatch(CaveniaGeneratorHostRequirementContract::runtimeImplemented);
    }

    public static boolean canCreateChunks() {
        return CONTRACTS.stream().anyMatch(CaveniaGeneratorHostRequirementContract::canCreateChunks);
    }

    public static boolean canMutatePrimer() {
        return CONTRACTS.stream().anyMatch(CaveniaGeneratorHostRequirementContract::canMutatePrimer);
    }

    public static boolean runtimeOperationsBlocked() {
        return CaveniaGeneratorRuntimeContracts.allRuntimeOperationsBlocked();
    }

    public static boolean generatorSkeletonCanCreateChunks() {
        return CaveniaGeneratorRuntimeContracts.generatorSkeletonCanCreateChunks();
    }

    public static boolean generatorHostReady() {
        return CaveniaActivationReadinessHosts.generatorHostReady();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaGeneratorHostRequirementContract contract(
        CaveniaGeneratorHostRequirement requirement,
        CaveniaGeneratorRuntimeOperation runtimeOperation,
        String sourcePolicyName,
        String blocker
    ) {
        return new CaveniaGeneratorHostRequirementContract(
            requirement,
            runtimeOperation,
            sourcePolicyName,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
