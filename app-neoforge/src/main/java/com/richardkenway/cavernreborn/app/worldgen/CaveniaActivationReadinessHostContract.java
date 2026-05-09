package com.richardkenway.cavernreborn.app.worldgen;

import java.util.Objects;

public record CaveniaActivationReadinessHostContract(
    CaveniaActivationReadinessHost host,
    String responsibility,
    CaveniaGeneratorActivationRequirement activationRequirement,
    boolean ready,
    boolean runtimeImplemented,
    boolean canActivateCavenia,
    String blocker
) {
    public CaveniaActivationReadinessHostContract {
        Objects.requireNonNull(host, "host");
        Objects.requireNonNull(responsibility, "responsibility");
        Objects.requireNonNull(activationRequirement, "activationRequirement");
        Objects.requireNonNull(blocker, "blocker");

        if (responsibility.isBlank()) {
            throw new IllegalArgumentException("responsibility must not be blank");
        }
        if (blocker.isBlank()) {
            throw new IllegalArgumentException("blocker must not be blank");
        }
    }
}
