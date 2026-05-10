package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.spawn.CaveniaSpawnProviderPolicy;

public final class CaveniaSpawnHostContracts {
    private static final List<CaveniaSpawnHostRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaSpawnHostRequirement.CAVENIA_LEVEL_AVAILABILITY,
            "CaveniaDimensionResourceContracts",
            "No active Cavenia level exists for spawn hosting yet."
        ),
        contract(
            CaveniaSpawnHostRequirement.POLICY_CONSUMPTION,
            "CaveniaSpawnProviderPolicy",
            "No runtime Cavenia spawn host consumes the accepted spawn policy yet."
        ),
        contract(
            CaveniaSpawnHostRequirement.NORMAL_ROSTER_HANDLING,
            "CaveniaSpawnProviderPolicy",
            "No runtime Cavenia normal-roster host exists yet."
        ),
        contract(
            CaveniaSpawnHostRequirement.CRAZY_ROSTER_HANDLING,
            "CaveniaSpawnProviderPolicy",
            "No runtime Cavenia crazy-roster host exists yet."
        ),
        contract(
            CaveniaSpawnHostRequirement.NEARBY_ICAVENICMOB_SCAN,
            "CaveniaSpawnProviderPolicy",
            "No runtime nearby-ICavenicMob scan host exists yet."
        ),
        contract(
            CaveniaSpawnHostRequirement.CAVEMAN_DEFERRAL,
            "caveman-cavenia-normal-roster-boundary.md",
            "Caveman remains deferred until its wider runtime surface is decided."
        ),
        contract(
            CaveniaSpawnHostRequirement.HOST_LOOP_OR_TICK_SOURCE,
            "CaveniaActivationReadinessHosts",
            "No Cavenia spawn host loop or tick source exists yet."
        ),
        contract(
            CaveniaSpawnHostRequirement.NO_FAKE_CAVERN_CRAZY_SPAWNING,
            "cavenia-crazy-roster-natural-spawn-boundary.md",
            "No fake normal CAVERN crazy-spawn path is allowed for Cavenia."
        )
    );

    private CaveniaSpawnHostContracts() {
    }

    public static List<CaveniaSpawnHostRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaSpawnHostRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaSpawnHostRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaSpawnHostRequirementContract> contractFor(CaveniaSpawnHostRequirement requirement) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static boolean allRequirementsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaSpawnHostRequirementContract::ready);
    }

    public static boolean anyRequirementReady() {
        return CONTRACTS.stream().anyMatch(CaveniaSpawnHostRequirementContract::ready);
    }

    public static boolean anyRequirementRuntimeImplemented() {
        return CONTRACTS.stream().anyMatch(CaveniaSpawnHostRequirementContract::runtimeImplemented);
    }

    public static boolean canSpawn() {
        return CONTRACTS.stream().anyMatch(CaveniaSpawnHostRequirementContract::canSpawn);
    }

    public static boolean usesNormalCavernCrazySpawning() {
        return CONTRACTS.stream().anyMatch(CaveniaSpawnHostRequirementContract::usesNormalCavernCrazySpawning);
    }

    public static boolean spawnHostReady() {
        return CaveniaActivationReadinessHosts.spawnHostReady();
    }

    public static boolean dimensionResourceHostReady() {
        return CaveniaActivationReadinessHosts.dimensionResourceHostReady();
    }

    public static boolean dimensionCanCreateLevel() {
        return CaveniaDimensionResourceContracts.canCreateLevel();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaSpawnProviderPolicy.normalRoster().stream()
            .anyMatch(entry ->
                entry.legacyClassName().equals("EntityCaveman")
                    && entry.rebornEntityId().equals(CaveniaSpawnProviderPolicy.DEFERRED_CAVEMAN_REBORN_ENTITY_ID)
            );
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaSpawnHostRequirementContract contract(
        CaveniaSpawnHostRequirement requirement,
        String sourcePolicyName,
        String blocker
    ) {
        return new CaveniaSpawnHostRequirementContract(
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
