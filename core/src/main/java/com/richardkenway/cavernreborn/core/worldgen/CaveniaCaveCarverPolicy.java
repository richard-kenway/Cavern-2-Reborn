package com.richardkenway.cavernreborn.core.worldgen;

import java.util.List;

public final class CaveniaCaveCarverPolicy {
    public static final boolean DEFAULT_GENERATE_CAVES = true;
    public static final int TUNNEL_ORIGIN_BASE_Y = 20;
    public static final int TUNNEL_ORIGIN_RANDOM_BOUND = 5;
    public static final double SCALE_HEIGHT = 1.35D;
    public static final float MAX_DISTANCE_EXTRA = 18.0F;
    public static final float PITCH_DAMPENING_WHEN_CHANCE = 0.92F;
    public static final float PITCH_DAMPENING_OTHERWISE = 0.7F;

    private CaveniaCaveCarverPolicy() {
    }

    public static int minTunnelOriginY() {
        return TUNNEL_ORIGIN_BASE_Y;
    }

    public static int maxTunnelOriginY() {
        return TUNNEL_ORIGIN_BASE_Y + TUNNEL_ORIGIN_RANDOM_BOUND - 1;
    }

    public static boolean isTunnelOriginY(int y) {
        return y >= minTunnelOriginY() && y <= maxTunnelOriginY();
    }

    public static boolean isLowGravelBand(int y) {
        return y <= 2;
    }

    public static boolean isLowWaterBand(int y) {
        return !isLowGravelBand(y) && y - 1 < 10;
    }

    public static boolean isAirCarveBand(int y) {
        return !isLowGravelBand(y) && !isLowWaterBand(y);
    }

    public static CaveniaCaveCarverReplacement replacementForY(int y) {
        if (isLowGravelBand(y)) {
            return CaveniaCaveCarverReplacement.GRAVEL;
        }
        if (isLowWaterBand(y)) {
            return CaveniaCaveCarverReplacement.WATER;
        }

        return CaveniaCaveCarverReplacement.AIR;
    }

    public static String replacementBlockForY(int y) {
        return replacementForY(y).blockId();
    }

    public static boolean runsBeforeBiomeTopFilterReplacement() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING)
            < steps.indexOf(CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT);
    }

    public static boolean runsBeforeVeinsMutation() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING)
            < steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION);
    }
}
