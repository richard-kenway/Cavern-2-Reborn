package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaActivationReadinessMatrixEntry(
    CaveniaActivationSurface surface,
    String sourceContractName,
    int requirementCount,
    boolean allRequirementsBlocked,
    boolean anyRequirementReady,
    boolean runtimeImplemented,
    boolean activeRuntimeSurfacePresent,
    String blocker
) {
}
