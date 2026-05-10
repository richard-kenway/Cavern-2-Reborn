package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaAccessTravelContracts {
    private static final List<CaveniaAccessTravelRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaAccessTravelRequirement.ENTRY_TRIGGER_DECISION,
            "cavenia-mirage-entry-access-contract-boundary.md",
            "No accepted Cavenia entry-trigger decision exists yet."
        ),
        contract(
            CaveniaAccessTravelRequirement.SAFE_ARRIVAL_POSITION,
            "CaveniaActivationReadinessHosts",
            "No safe Cavenia arrival-position host exists yet."
        ),
        contract(
            CaveniaAccessTravelRequirement.RETURN_PATH,
            "CaveniaActivationReadinessHosts",
            "No Cavenia return-path host exists yet."
        ),
        contract(
            CaveniaAccessTravelRequirement.SERVER_LEVEL_AVAILABILITY,
            "CaveniaDimensionResourceContracts",
            "No active Cavenia server level exists yet."
        ),
        contract(
            CaveniaAccessTravelRequirement.PLAYER_STATE_GUARDS,
            "CaveniaGeneratorRegistrationBoundary",
            "No Cavenia player-state guard contract exists yet."
        ),
        contract(
            CaveniaAccessTravelRequirement.TRAVEL_HOST_IMPLEMENTATION,
            "CaveniaActivationReadinessHosts",
            "No Cavenia access or travel host implementation exists yet."
        )
    );

    private CaveniaAccessTravelContracts() {
    }

    public static List<CaveniaAccessTravelRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaAccessTravelRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaAccessTravelRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaAccessTravelRequirementContract> contractFor(CaveniaAccessTravelRequirement requirement) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static boolean allRequirementsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaAccessTravelRequirementContract::ready);
    }

    public static boolean anyRequirementReady() {
        return CONTRACTS.stream().anyMatch(CaveniaAccessTravelRequirementContract::ready);
    }

    public static boolean anyRequirementRuntimeImplemented() {
        return CONTRACTS.stream().anyMatch(CaveniaAccessTravelRequirementContract::runtimeImplemented);
    }

    public static boolean canTeleport() {
        return CONTRACTS.stream().anyMatch(CaveniaAccessTravelRequirementContract::canTeleport);
    }

    public static boolean requiresActiveLevel() {
        return CONTRACTS.stream().allMatch(CaveniaAccessTravelRequirementContract::requiresActiveLevel);
    }

    public static boolean accessTravelHostReady() {
        return CaveniaActivationReadinessHosts.accessTravelHostReady();
    }

    public static boolean dimensionResourceHostReady() {
        return CaveniaActivationReadinessHosts.dimensionResourceHostReady();
    }

    public static boolean dimensionCanCreateLevel() {
        return CaveniaDimensionResourceContracts.canCreateLevel();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaAccessTravelRequirementContract contract(
        CaveniaAccessTravelRequirement requirement,
        String sourcePolicyName,
        String blocker
    ) {
        return new CaveniaAccessTravelRequirementContract(
            requirement,
            sourcePolicyName,
            false,
            false,
            false,
            true,
            blocker
        );
    }
}
