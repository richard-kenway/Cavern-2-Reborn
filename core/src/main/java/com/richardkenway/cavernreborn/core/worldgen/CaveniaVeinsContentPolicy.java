package com.richardkenway.cavernreborn.core.worldgen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class CaveniaVeinsContentPolicy {
    public static final String DEFAULT_TARGET_BLOCK_ID = "minecraft:stone";
    public static final int DEFAULT_TARGET_BLOCK_META = 0;
    public static final double DEFAULT_CHANCE = 1.0D;
    public static final boolean AUTO_VEINS_DEFAULT_ENABLED = false;

    private static final List<CaveniaVeinEntry> ENTRIES = List.of(
        new CaveniaVeinEntry("minecraft:stone", 1, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 28, DEFAULT_CHANCE, 25, 1, 255, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:stone", 3, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 28, DEFAULT_CHANCE, 25, 1, 255, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:stone", 5, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 30, DEFAULT_CHANCE, 25, 1, 255, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:coal_ore", 0, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 50, DEFAULT_CHANCE, 17, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:iron_ore", 0, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 40, DEFAULT_CHANCE, 10, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("cavern:cave_block", 0, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 12, DEFAULT_CHANCE, 8, 20, 127, List.of("Type.COLD", "Type.WATER", "Type.WET"), CaveniaVeinBiomeFilterMode.EXCLUDE_MATCHING_BIOMES),
        new CaveniaVeinEntry("cavern:cave_block", 2, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 30, DEFAULT_CHANCE, 10, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("cavern:cave_block", 4, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 24, DEFAULT_CHANCE, 4, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("cavern:cave_block", 5, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 4, DEFAULT_CHANCE, 5, 1, 30, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("cavern:cave_block", 7, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 150, DEFAULT_CHANCE, 2, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:dirt", 0, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 20, DEFAULT_CHANCE, 25, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:gravel", 0, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 10, DEFAULT_CHANCE, 20, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
        new CaveniaVeinEntry("minecraft:sand", 0, DEFAULT_TARGET_BLOCK_ID, DEFAULT_TARGET_BLOCK_META, 10, DEFAULT_CHANCE, 20, 1, 127, List.of("Type.SANDY"), CaveniaVeinBiomeFilterMode.EXCLUDE_MATCHING_BIOMES)
    );

    private static final Map<String, List<CaveniaVeinEntry>> ENTRIES_BY_LEGACY_BLOCK_ID = buildEntriesByLegacyBlockId();
    private static final Map<String, CaveniaVeinEntry> ENTRIES_BY_LEGACY_BLOCK_KEY = buildEntriesByLegacyBlockKey();

    private CaveniaVeinsContentPolicy() {
    }

    public static List<CaveniaVeinEntry> entries() {
        return ENTRIES;
    }

    public static Optional<CaveniaVeinEntry> entryByLegacyBlockIdAndMeta(String legacyBlockId, int legacyBlockMeta) {
        return Optional.ofNullable(ENTRIES_BY_LEGACY_BLOCK_KEY.get(legacyBlockKey(legacyBlockId, legacyBlockMeta)));
    }

    public static List<CaveniaVeinEntry> entriesByLegacyBlockId(String legacyBlockId) {
        return ENTRIES_BY_LEGACY_BLOCK_ID.getOrDefault(legacyBlockId, List.of());
    }

    public static boolean containsLegacyBlockId(String legacyBlockId) {
        return ENTRIES_BY_LEGACY_BLOCK_ID.containsKey(legacyBlockId);
    }

    public static int totalWeight() {
        return ENTRIES.stream().mapToInt(CaveniaVeinEntry::weight).sum();
    }

    public static String defaultTargetBlockId() {
        return DEFAULT_TARGET_BLOCK_ID;
    }

    public static double defaultChance() {
        return DEFAULT_CHANCE;
    }

    public static boolean autoVeinsDefaultEnabled() {
        return AUTO_VEINS_DEFAULT_ENABLED;
    }

    public static boolean usesDefaultTargetBlock(CaveniaVeinEntry entry) {
        return DEFAULT_TARGET_BLOCK_ID.equals(entry.targetBlockId()) && DEFAULT_TARGET_BLOCK_META == entry.targetBlockMeta();
    }

    public static boolean usesDefaultChance(CaveniaVeinEntry entry) {
        return DEFAULT_CHANCE == entry.chance();
    }

    public static boolean hasBiomeFilters(CaveniaVeinEntry entry) {
        return !entry.biomeFilters().isEmpty();
    }

    public static boolean runsAfterCaveCarving() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION)
            > steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING);
    }

    public static boolean runsAfterBiomeTopFilterReplacement() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION)
            > steps.indexOf(CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT);
    }

    public static boolean runsBeforeFinalChunkConstruction() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION)
            < steps.indexOf(CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION);
    }

    public static boolean runsBeforePopulationStage() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        return steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION)
            < steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE);
    }

    private static Map<String, List<CaveniaVeinEntry>> buildEntriesByLegacyBlockId() {
        Map<String, List<CaveniaVeinEntry>> entriesByBlockId = new LinkedHashMap<>();

        for (CaveniaVeinEntry entry : ENTRIES) {
            entriesByBlockId.computeIfAbsent(entry.legacyBlockId(), unused -> new ArrayList<>()).add(entry);
        }

        entriesByBlockId.replaceAll((unused, entries) -> List.copyOf(entries));

        return Map.copyOf(entriesByBlockId);
    }

    private static Map<String, CaveniaVeinEntry> buildEntriesByLegacyBlockKey() {
        Map<String, CaveniaVeinEntry> entriesByBlockKey = new LinkedHashMap<>();

        for (CaveniaVeinEntry entry : ENTRIES) {
            entriesByBlockKey.put(legacyBlockKey(entry.legacyBlockId(), entry.legacyBlockMeta()), entry);
        }

        return Map.copyOf(entriesByBlockKey);
    }

    private static String legacyBlockKey(String legacyBlockId, int legacyBlockMeta) {
        return Objects.requireNonNull(legacyBlockId, "legacyBlockId") + "#" + legacyBlockMeta;
    }
}
