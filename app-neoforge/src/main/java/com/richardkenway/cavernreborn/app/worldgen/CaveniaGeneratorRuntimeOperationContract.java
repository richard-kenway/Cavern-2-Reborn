package com.richardkenway.cavernreborn.app.worldgen;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffoldStage;

public record CaveniaGeneratorRuntimeOperationContract(
    CaveniaGeneratorRuntimeOperation operation,
    CaveniaGeneratorScaffoldStage scaffoldStage,
    String sourcePolicyName,
    boolean runtimeImplemented,
    boolean canCreateChunks,
    boolean canMutatePrimer,
    boolean requiresRegisteredGenerator,
    boolean requiresDimensionResources
) {
    public CaveniaGeneratorRuntimeOperationContract {
        Objects.requireNonNull(operation, "operation");
        Objects.requireNonNull(scaffoldStage, "scaffoldStage");
        Objects.requireNonNull(sourcePolicyName, "sourcePolicyName");

        if (sourcePolicyName.isBlank()) {
            throw new IllegalArgumentException("sourcePolicyName must not be blank");
        }
    }
}
