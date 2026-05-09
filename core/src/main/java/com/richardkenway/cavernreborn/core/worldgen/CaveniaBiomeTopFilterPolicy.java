package com.richardkenway.cavernreborn.core.worldgen;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CaveniaBiomeTopFilterPolicy {
    public static final String DEFAULT_TERRAIN_BLOCK_ID = "minecraft:stone";
    public static final String GRAVEL_TOP_BLOCK_ID = "minecraft:gravel";
    public static final String GRASS_TOP_BLOCK_ID = "minecraft:grass";
    public static final String SAND_TOP_BLOCK_ID = "minecraft:sand";
    public static final String SANDSTONE_TOP_BLOCK_ID = "minecraft:sandstone";
    public static final String RED_SANDSTONE_TOP_BLOCK_ID = "minecraft:red_sandstone";

    private static final List<CaveniaBiomeEntry> ENTRIES = List.of(
        new CaveniaBiomeEntry("OCEAN", 15, DEFAULT_TERRAIN_BLOCK_ID, GRAVEL_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("PLAINS", 100, DEFAULT_TERRAIN_BLOCK_ID, GRASS_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("DESERT", 70, DEFAULT_TERRAIN_BLOCK_ID, SAND_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("DESERT_HILLS", 10, DEFAULT_TERRAIN_BLOCK_ID, SANDSTONE_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("FOREST", 80, DEFAULT_TERRAIN_BLOCK_ID, GRAVEL_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("FOREST_HILLS", 10, DEFAULT_TERRAIN_BLOCK_ID, GRAVEL_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("TAIGA", 80, DEFAULT_TERRAIN_BLOCK_ID, DEFAULT_TERRAIN_BLOCK_ID),
        new CaveniaBiomeEntry("TAIGA_HILLS", 10, DEFAULT_TERRAIN_BLOCK_ID, DEFAULT_TERRAIN_BLOCK_ID),
        new CaveniaBiomeEntry("JUNGLE", 80, DEFAULT_TERRAIN_BLOCK_ID, GRAVEL_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("JUNGLE_HILLS", 10, DEFAULT_TERRAIN_BLOCK_ID, GRAVEL_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("SWAMPLAND", 60, DEFAULT_TERRAIN_BLOCK_ID, GRASS_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("EXTREME_HILLS", 50, DEFAULT_TERRAIN_BLOCK_ID, DEFAULT_TERRAIN_BLOCK_ID),
        new CaveniaBiomeEntry("SAVANNA", 50, DEFAULT_TERRAIN_BLOCK_ID, GRASS_TOP_BLOCK_ID),
        new CaveniaBiomeEntry("MESA", 50, DEFAULT_TERRAIN_BLOCK_ID, RED_SANDSTONE_TOP_BLOCK_ID)
    );

    private static final Map<String, CaveniaBiomeEntry> ENTRIES_BY_LEGACY_BIOME_NAME = ENTRIES
        .stream()
        .collect(Collectors.toUnmodifiableMap(CaveniaBiomeEntry::legacyBiomeName, Function.identity()));

    private CaveniaBiomeTopFilterPolicy() {
    }

    public static List<CaveniaBiomeEntry> entries() {
        return ENTRIES;
    }

    public static int totalWeight() {
        return ENTRIES.stream().mapToInt(CaveniaBiomeEntry::weight).sum();
    }

    public static Optional<CaveniaBiomeEntry> entryByLegacyBiomeName(String legacyBiomeName) {
        return Optional.ofNullable(ENTRIES_BY_LEGACY_BIOME_NAME.get(legacyBiomeName));
    }

    public static boolean containsLegacyBiomeName(String legacyBiomeName) {
        return ENTRIES_BY_LEGACY_BIOME_NAME.containsKey(legacyBiomeName);
    }

    public static String defaultTerrainBlockId() {
        return DEFAULT_TERRAIN_BLOCK_ID;
    }

    public static boolean usesDefaultTerrainBlock(CaveniaBiomeEntry entry) {
        return DEFAULT_TERRAIN_BLOCK_ID.equals(entry.terrainBlockId());
    }

    public static Optional<String> topBlockForLegacyBiomeName(String legacyBiomeName) {
        return entryByLegacyBiomeName(legacyBiomeName).map(CaveniaBiomeEntry::topBlockId);
    }
}
