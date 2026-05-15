package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract(
    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceReadinessAction,
    boolean allowsNextSliceConversionImplementation,
    boolean allowsNextSliceRuntimeAction,
    boolean blocksActivation,
    String blocker
) {
}
