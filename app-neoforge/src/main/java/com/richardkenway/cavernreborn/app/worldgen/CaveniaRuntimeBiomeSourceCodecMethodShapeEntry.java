package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCodecMethodShapeEntry(
    CaveniaRuntimeBiomeSourceCodecMethodShapeComponent component,
    String sourceContractName,
    String observedShape,
    String expectedBehavior,
    boolean shapePinned,
    boolean stubbed,
    boolean usableCodecImplementation,
    boolean registered,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
