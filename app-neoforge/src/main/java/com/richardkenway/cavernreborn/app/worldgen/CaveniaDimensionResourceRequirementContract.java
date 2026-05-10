package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaDimensionResourceRequirementContract(
    CaveniaDimensionResourceRequirement requirement,
    CaveniaGeneratorActivationRequirement activationRequirement,
    String sourcePolicyName,
    boolean ready,
    boolean resourcePresent,
    boolean runtimeBound,
    boolean canCreateLevel,
    String blocker
) {
}
