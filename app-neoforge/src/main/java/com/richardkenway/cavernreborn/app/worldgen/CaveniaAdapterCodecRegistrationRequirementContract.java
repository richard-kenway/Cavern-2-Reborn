package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaAdapterCodecRegistrationRequirementContract(
    CaveniaAdapterCodecRegistrationRequirement requirement,
    String sourceContractName,
    boolean prerequisiteSatisfied,
    boolean readyForRuntime,
    boolean codecImplemented,
    boolean registered,
    boolean registryLookupAvailable,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
