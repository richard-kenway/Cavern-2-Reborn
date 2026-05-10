package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaBiomeSelectionAdapterQuery(
    int x,
    int y,
    int z,
    int weightValue,
    boolean registryLookupAvailable,
    boolean runtimeBiomeSourceAvailable
) {
    public static CaveniaBiomeSelectionAdapterQuery ofWeightValue(int weightValue) {
        return new CaveniaBiomeSelectionAdapterQuery(0, 0, 0, weightValue, false, false);
    }
}
