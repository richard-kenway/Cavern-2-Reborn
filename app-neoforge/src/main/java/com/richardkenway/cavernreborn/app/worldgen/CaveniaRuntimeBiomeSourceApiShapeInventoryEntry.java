package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceApiShapeInventoryEntry(
    CaveniaRuntimeBiomeSourceApiShapeComponent component,
    String sourceReference,
    String observedShape,
    String implementationImplication,
    boolean locallyInspected,
    boolean shapePinned,
    boolean runtimeApiImplemented,
    boolean runtimeApiAllowedInThisSlice,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
