package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaSpawnHostRequirementContract(
    CaveniaSpawnHostRequirement requirement,
    String sourcePolicyName,
    boolean ready,
    boolean runtimeImplemented,
    boolean canSpawn,
    boolean usesNormalCavernCrazySpawning,
    String blocker
) {
}
