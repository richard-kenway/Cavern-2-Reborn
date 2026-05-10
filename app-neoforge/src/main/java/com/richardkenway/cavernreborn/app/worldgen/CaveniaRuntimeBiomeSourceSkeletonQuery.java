package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSkeletonQuery(
    int x,
    int y,
    int z,
    int weightValue,
    boolean climateSamplerAvailable,
    boolean registryLookupAvailable,
    boolean runtimeBiomeSourceAvailable
) {
    public static CaveniaRuntimeBiomeSourceSkeletonQuery ofWeightValue(int weightValue) {
        return new CaveniaRuntimeBiomeSourceSkeletonQuery(0, 0, 0, weightValue, false, false, false);
    }

    public static CaveniaRuntimeBiomeSourceSkeletonQuery ofCoordinatesAndWeightValue(int x, int y, int z, int weightValue) {
        return new CaveniaRuntimeBiomeSourceSkeletonQuery(x, y, z, weightValue, false, false, false);
    }
}
