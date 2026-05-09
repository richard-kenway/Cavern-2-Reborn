package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaGeneratorHostRequirementContract(
    CaveniaGeneratorHostRequirement requirement,
    CaveniaGeneratorRuntimeOperation runtimeOperation,
    String sourcePolicyName,
    boolean ready,
    boolean runtimeImplemented,
    boolean canCreateChunks,
    boolean canMutatePrimer,
    String blocker
) {
}
