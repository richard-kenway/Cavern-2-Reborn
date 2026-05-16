package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrailContract(
    CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceBuilderAction,
    boolean allowsNextSliceRuntimeWiringAction,
    boolean allowsNextSliceActivationAction,
    boolean blocksActivation,
    String blocker
) {
}
