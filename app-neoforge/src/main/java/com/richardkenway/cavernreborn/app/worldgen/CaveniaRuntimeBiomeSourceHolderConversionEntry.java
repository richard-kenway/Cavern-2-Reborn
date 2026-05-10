package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceHolderConversionEntry(
    CaveniaRuntimeBiomeSourceHolderConversionComponent component,
    String sourceContractName,
    String observedInput,
    String futureDecision,
    boolean readinessPinned,
    boolean implementationReady,
    boolean registryLookupRequired,
    boolean registryLookupAvailable,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
