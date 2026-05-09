package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaBiomeSourceStrategyRequirementContract(
    CaveniaBiomeSourceStrategyRequirement requirement,
    String sourcePolicyName,
    boolean ready,
    boolean runtimeImplemented,
    boolean runtimeBiomeSource,
    boolean codecRegistered,
    String blocker
) {
}
