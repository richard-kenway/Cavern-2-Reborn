package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceFallbackPolicyEntry(
    CaveniaRuntimeBiomeSourceFallbackPolicyComponent component,
    String sourceContractName,
    String fallbackDecision,
    String expectedFutureBehavior,
    boolean readinessPinned,
    boolean implementationReady,
    boolean registryLookupRequired,
    boolean registryLookupAvailable,
    boolean runtimeFallbackReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
