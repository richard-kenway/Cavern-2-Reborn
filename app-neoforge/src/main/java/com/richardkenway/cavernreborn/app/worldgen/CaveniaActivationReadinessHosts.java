package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaActivationReadinessHosts {
    private static final List<CaveniaActivationReadinessHostContract> CONTRACTS = List.of(
        contract(
            CaveniaActivationReadinessHost.GENERATOR_HOST,
            "Own the future Cavenia chunk generator runtime host.",
            CaveniaGeneratorActivationRequirement.CHUNK_GENERATOR_IMPLEMENTATION,
            "No Cavenia chunk generator implementation exists yet."
        ),
        contract(
            CaveniaActivationReadinessHost.BIOME_SOURCE_STRATEGY_HOST,
            "Own the future Cavenia biome-source strategy host.",
            CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
            "No Cavenia biome source strategy exists yet."
        ),
        contract(
            CaveniaActivationReadinessHost.DIMENSION_RESOURCE_HOST,
            "Own the future Cavenia dimension JSON and dimension type JSON resources.",
            CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
            "No active Cavenia dimension JSON or dimension type JSON exists yet."
        ),
        contract(
            CaveniaActivationReadinessHost.ACCESS_TRAVEL_HOST,
            "Own safe Cavenia access and travel readiness.",
            CaveniaGeneratorActivationRequirement.SAFE_ACCESS_OR_TELEPORT,
            "No safe Cavenia access or travel host exists yet."
        ),
        contract(
            CaveniaActivationReadinessHost.SPAWN_HOST,
            "Own a Cavenia-only spawn host that consumes the accepted spawn policy.",
            CaveniaGeneratorActivationRequirement.CAVENIA_ONLY_SPAWN_HOST,
            "No Cavenia-only spawn host exists yet."
        ),
        contract(
            CaveniaActivationReadinessHost.WORLDGEN_RESOURCE_HOST,
            "Own future Cavenia worldgen resource mapping decisions.",
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            "No Cavenia worldgen resource mapping exists yet."
        )
    );

    private CaveniaActivationReadinessHosts() {
    }

    public static List<CaveniaActivationReadinessHostContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaActivationReadinessHost> hosts() {
        return CONTRACTS.stream()
            .map(CaveniaActivationReadinessHostContract::host)
            .toList();
    }

    public static Optional<CaveniaActivationReadinessHostContract> contractFor(CaveniaActivationReadinessHost host) {
        return CONTRACTS.stream()
            .filter(contract -> contract.host() == host)
            .findFirst();
    }

    public static boolean allHostsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaActivationReadinessHostContract::ready);
    }

    public static boolean anyHostReady() {
        return CONTRACTS.stream().anyMatch(CaveniaActivationReadinessHostContract::ready);
    }

    public static boolean anyHostRuntimeImplemented() {
        return CONTRACTS.stream().anyMatch(CaveniaActivationReadinessHostContract::runtimeImplemented);
    }

    public static boolean canActivateCavenia() {
        return CONTRACTS.stream().anyMatch(CaveniaActivationReadinessHostContract::canActivateCavenia);
    }

    public static boolean generatorHostReady() {
        return isReady(CaveniaActivationReadinessHost.GENERATOR_HOST);
    }

    public static boolean biomeSourceStrategyHostReady() {
        return isReady(CaveniaActivationReadinessHost.BIOME_SOURCE_STRATEGY_HOST);
    }

    public static boolean dimensionResourceHostReady() {
        return isReady(CaveniaActivationReadinessHost.DIMENSION_RESOURCE_HOST);
    }

    public static boolean accessTravelHostReady() {
        return isReady(CaveniaActivationReadinessHost.ACCESS_TRAVEL_HOST);
    }

    public static boolean spawnHostReady() {
        return isReady(CaveniaActivationReadinessHost.SPAWN_HOST);
    }

    public static boolean worldgenResourceHostReady() {
        return isReady(CaveniaActivationReadinessHost.WORLDGEN_RESOURCE_HOST);
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    public static boolean runtimeOperationsBlocked() {
        return CaveniaGeneratorRuntimeContracts.allRuntimeOperationsBlocked();
    }

    public static boolean generatorSkeletonCanCreateChunks() {
        return CaveniaGeneratorRuntimeContracts.generatorSkeletonCanCreateChunks();
    }

    public static boolean biomeSelectionIsRuntimeBiomeSource() {
        return CaveniaGeneratorRuntimeContracts.biomeSelectionIsRuntimeBiomeSource();
    }

    private static boolean isReady(CaveniaActivationReadinessHost host) {
        return contractFor(host)
            .map(CaveniaActivationReadinessHostContract::ready)
            .orElse(false);
    }

    private static CaveniaActivationReadinessHostContract contract(
        CaveniaActivationReadinessHost host,
        String responsibility,
        CaveniaGeneratorActivationRequirement activationRequirement,
        String blocker
    ) {
        return new CaveniaActivationReadinessHostContract(
            host,
            responsibility,
            activationRequirement,
            false,
            false,
            false,
            blocker
        );
    }
}
