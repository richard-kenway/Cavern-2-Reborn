package com.richardkenway.cavernreborn.core.state;

public final class CavernDimensions {
    public static final String CAVERN_DIMENSION_ID = "cavernreborn:cavern";
    public static final String OVERWORLD_DIMENSION_ID = "minecraft:overworld";
    public static final int CAVERN_ENTRY_X = 0;
    public static final int CAVERN_ENTRY_Y = 80;
    public static final int CAVERN_ENTRY_Z = 0;

    private CavernDimensions() {
    }

    public static boolean isCavern(String dimensionId) {
        return CAVERN_DIMENSION_ID.equals(dimensionId);
    }
}
