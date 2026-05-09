package com.richardkenway.cavernreborn.core.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class CaveniaVeinsContentPolicyTest {
    @Test
    void defaultsRemainSourceConfirmed() {
        assertEquals("minecraft:stone", CaveniaVeinsContentPolicy.defaultTargetBlockId());
        assertEquals(1.0D, CaveniaVeinsContentPolicy.defaultChance());
        assertFalse(CaveniaVeinsContentPolicy.autoVeinsDefaultEnabled());
    }

    @Test
    void exactShippedEntriesRemainPinned() {
        assertEquals(
            List.of(
                new CaveniaVeinEntry("minecraft:stone", 1, "minecraft:stone", 0, 28, 1.0D, 25, 1, 255, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:stone", 3, "minecraft:stone", 0, 28, 1.0D, 25, 1, 255, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:stone", 5, "minecraft:stone", 0, 30, 1.0D, 25, 1, 255, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:coal_ore", 0, "minecraft:stone", 0, 50, 1.0D, 17, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:iron_ore", 0, "minecraft:stone", 0, 40, 1.0D, 10, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("cavern:cave_block", 0, "minecraft:stone", 0, 12, 1.0D, 8, 20, 127, List.of("Type.COLD", "Type.WATER", "Type.WET"), CaveniaVeinBiomeFilterMode.EXCLUDE_MATCHING_BIOMES),
                new CaveniaVeinEntry("cavern:cave_block", 2, "minecraft:stone", 0, 30, 1.0D, 10, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("cavern:cave_block", 4, "minecraft:stone", 0, 24, 1.0D, 4, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("cavern:cave_block", 5, "minecraft:stone", 0, 4, 1.0D, 5, 1, 30, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("cavern:cave_block", 7, "minecraft:stone", 0, 150, 1.0D, 2, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:dirt", 0, "minecraft:stone", 0, 20, 1.0D, 25, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:gravel", 0, "minecraft:stone", 0, 10, 1.0D, 20, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE),
                new CaveniaVeinEntry("minecraft:sand", 0, "minecraft:stone", 0, 10, 1.0D, 20, 1, 127, List.of("Type.SANDY"), CaveniaVeinBiomeFilterMode.EXCLUDE_MATCHING_BIOMES)
            ),
            CaveniaVeinsContentPolicy.entries()
        );
    }

    @Test
    void shippedEntryCountAndTotalWeightRemainPinned() {
        assertEquals(13, CaveniaVeinsContentPolicy.entries().size());
        assertEquals(436, CaveniaVeinsContentPolicy.totalWeight());
    }

    @Test
    void everyEntryKeepsSourceConfirmedDefaultsAndBounds() {
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> !entry.legacyBlockId().isBlank()));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> !entry.targetBlockId().isBlank()));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> entry.weight() > 0));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> entry.chance() > 0.0D));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> entry.size() > 0));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> entry.minHeight() > 0));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(entry -> entry.maxHeight() >= entry.minHeight()));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(CaveniaVeinsContentPolicy::usesDefaultTargetBlock));
        assertTrue(CaveniaVeinsContentPolicy.entries().stream().allMatch(CaveniaVeinsContentPolicy::usesDefaultChance));
    }

    @Test
    void lookupHelpersRemainDeterministic() {
        assertTrue(CaveniaVeinsContentPolicy.containsLegacyBlockId("minecraft:stone"));
        assertTrue(CaveniaVeinsContentPolicy.containsLegacyBlockId("minecraft:coal_ore"));
        assertFalse(CaveniaVeinsContentPolicy.containsLegacyBlockId("minecraft:gold_ore"));
        assertEquals(3, CaveniaVeinsContentPolicy.entriesByLegacyBlockId("minecraft:stone").size());
        assertEquals(
            Optional.of(new CaveniaVeinEntry("minecraft:coal_ore", 0, "minecraft:stone", 0, 50, 1.0D, 17, 1, 127, List.of(), CaveniaVeinBiomeFilterMode.NONE)),
            CaveniaVeinsContentPolicy.entryByLegacyBlockIdAndMeta("minecraft:coal_ore", 0)
        );
        assertEquals(
            Optional.of(new CaveniaVeinEntry("cavern:cave_block", 0, "minecraft:stone", 0, 12, 1.0D, 8, 20, 127, List.of("Type.COLD", "Type.WATER", "Type.WET"), CaveniaVeinBiomeFilterMode.EXCLUDE_MATCHING_BIOMES)),
            CaveniaVeinsContentPolicy.entryByLegacyBlockIdAndMeta("cavern:cave_block", 0)
        );
        assertEquals(List.of(), CaveniaVeinsContentPolicy.entriesByLegacyBlockId("minecraft:gold_ore"));
        assertEquals(Optional.empty(), CaveniaVeinsContentPolicy.entryByLegacyBlockIdAndMeta("minecraft:gold_ore", 0));
        assertTrue(CaveniaVeinsContentPolicy.hasBiomeFilters(CaveniaVeinsContentPolicy.entryByLegacyBlockIdAndMeta("cavern:cave_block", 0).orElseThrow()));
        assertFalse(CaveniaVeinsContentPolicy.hasBiomeFilters(CaveniaVeinsContentPolicy.entryByLegacyBlockIdAndMeta("minecraft:coal_ore", 0).orElseThrow()));
    }

    @Test
    void returnedCollectionsRemainSafelyUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaVeinsContentPolicy.entries().add(
                new CaveniaVeinEntry("minecraft:gold_ore", 0, "minecraft:stone", 0, 1, 1.0D, 1, 1, 1, List.of(), CaveniaVeinBiomeFilterMode.NONE)
            )
        );
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaVeinsContentPolicy.entriesByLegacyBlockId("minecraft:stone").add(
                new CaveniaVeinEntry("minecraft:stone", 7, "minecraft:stone", 0, 1, 1.0D, 1, 1, 1, List.of(), CaveniaVeinBiomeFilterMode.NONE)
            )
        );
    }

    @Test
    void veinsMutationOrderRemainsAlignedWithTheTerrainPolicy() {
        List<CaveniaTerrainStep> steps = CaveniaTerrainGeneratorPolicy.generationSteps();

        assertTrue(steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION) > steps.indexOf(CaveniaTerrainStep.OPTIONAL_CAVE_CARVING));
        assertTrue(steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION) > steps.indexOf(CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT));
        assertTrue(steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION) < steps.indexOf(CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION));
        assertTrue(steps.indexOf(CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION) < steps.indexOf(CaveniaTerrainStep.LATER_POPULATION_STAGE));
        assertTrue(CaveniaVeinsContentPolicy.runsAfterCaveCarving());
        assertTrue(CaveniaVeinsContentPolicy.runsAfterBiomeTopFilterReplacement());
        assertTrue(CaveniaVeinsContentPolicy.runsBeforeFinalChunkConstruction());
        assertTrue(CaveniaVeinsContentPolicy.runsBeforePopulationStage());
    }
}
