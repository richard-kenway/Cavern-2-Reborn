package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaAccessTravelRequirementContract(
    CaveniaAccessTravelRequirement requirement,
    String sourcePolicyName,
    boolean ready,
    boolean runtimeImplemented,
    boolean canTeleport,
    boolean requiresActiveLevel,
    String blocker
) {
}
