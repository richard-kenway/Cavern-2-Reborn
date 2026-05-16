package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderEntry(
    CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderComponent component,
    String sourceContractName,
    String builderBehavior,
    String runtimeBoundary,
    boolean builderReady,
    boolean runtimeBiomeSourceReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
