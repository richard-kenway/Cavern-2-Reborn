package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaWorldgenResourceRequirementContract(
    CaveniaWorldgenResourceRequirement requirement,
    CaveniaGeneratorActivationRequirement activationRequirement,
    String sourcePolicyName,
    boolean ready,
    boolean resourcePresent,
    boolean runtimeBound,
    boolean canAffectWorldgen,
    String blocker
) {
}
