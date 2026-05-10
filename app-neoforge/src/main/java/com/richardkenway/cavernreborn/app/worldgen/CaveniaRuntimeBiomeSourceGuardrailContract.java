package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceGuardrailContract(
    CaveniaRuntimeBiomeSourceGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean mayBeRelaxedInNextSlice,
    boolean runtimeActivationAllowed,
    String blocker
) {
}
