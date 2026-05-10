package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaBiomeSourceStrategyReadinessMatrixEntry(
    CaveniaBiomeSourceStrategyReadinessSurface surface,
    String sourceContractName,
    int itemCount,
    boolean readinessLayerReady,
    boolean runtimeReady,
    boolean activationAllowedInThisSlice,
    boolean runtimeBiomeSourceReady,
    boolean codecRegistered,
    boolean registryLookupAccessReady,
    String blocker
) {
}
