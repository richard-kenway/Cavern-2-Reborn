package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry(
    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent component,
    String sourceContractName,
    String conversionSurface,
    String futurePolicy,
    boolean readinessPinned,
    boolean conversionImplementationReady,
    boolean registryLookupRequired,
    boolean registryLookupAvailable,
    boolean holderResolved,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
