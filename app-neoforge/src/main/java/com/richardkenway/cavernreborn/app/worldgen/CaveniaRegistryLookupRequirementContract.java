package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRegistryLookupRequirementContract(
    CaveniaRegistryLookupRequirement requirement,
    String sourceContractName,
    boolean prerequisiteSatisfied,
    boolean readyForRuntime,
    boolean registryLookupAvailable,
    boolean registryVerified,
    boolean runtimeBiomeResolved,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
