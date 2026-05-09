package com.richardkenway.cavernreborn.core.worldgen;

import java.util.Objects;

public enum CaveniaTerrainStep {
    BASE_STONE_FILL("base stone fill"),
    OPTIONAL_CAVE_CARVING("optional cave carving"),
    BIOME_TOP_FILTER_REPLACEMENT("biome top/filter replacement"),
    VEINS_GENERATOR_SIDE_MUTATION("VEINS generator-side mutation"),
    FINAL_CHUNK_CONSTRUCTION("final chunk construction"),
    LATER_POPULATION_STAGE("later population stage for lakes/falls/shrooms");

    private final String legacyLabel;

    CaveniaTerrainStep(String legacyLabel) {
        this.legacyLabel = Objects.requireNonNull(legacyLabel, "legacyLabel");
    }

    public String legacyLabel() {
        return legacyLabel;
    }
}
