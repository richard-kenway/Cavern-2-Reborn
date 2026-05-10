package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;

public record CaveniaFirstActiveSurfaceCandidate(
    CaveniaActivationSurface surface,
    int requirementCount,
    boolean selected,
    int selectionRank,
    boolean activationAllowedInThisSlice,
    boolean remainsBlocked,
    String sourceContractName,
    String blocker,
    List<CaveniaFirstActiveSurfaceSelectionReason> reasons
) {
}
