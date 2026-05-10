package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSubclassRequirementContract(
    CaveniaRuntimeBiomeSourceSubclassRequirement requirement,
    String sourceContractName,
    boolean prerequisiteSatisfied,
    boolean decisionPinned,
    boolean readyForRealSubclass,
    boolean runtimeApiAllowedInThisSlice,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
