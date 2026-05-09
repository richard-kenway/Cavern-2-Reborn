package com.richardkenway.cavernreborn.core.worldgen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class CaveniaBiomeTopFilterPolicyTest {
    @Test
    void exactRosterRemainsSourceConfirmed() {
        assertEquals(
            List.of(
                new CaveniaBiomeEntry("OCEAN", 15, "minecraft:stone", "minecraft:gravel"),
                new CaveniaBiomeEntry("PLAINS", 100, "minecraft:stone", "minecraft:grass"),
                new CaveniaBiomeEntry("DESERT", 70, "minecraft:stone", "minecraft:sand"),
                new CaveniaBiomeEntry("DESERT_HILLS", 10, "minecraft:stone", "minecraft:sandstone"),
                new CaveniaBiomeEntry("FOREST", 80, "minecraft:stone", "minecraft:gravel"),
                new CaveniaBiomeEntry("FOREST_HILLS", 10, "minecraft:stone", "minecraft:gravel"),
                new CaveniaBiomeEntry("TAIGA", 80, "minecraft:stone", "minecraft:stone"),
                new CaveniaBiomeEntry("TAIGA_HILLS", 10, "minecraft:stone", "minecraft:stone"),
                new CaveniaBiomeEntry("JUNGLE", 80, "minecraft:stone", "minecraft:gravel"),
                new CaveniaBiomeEntry("JUNGLE_HILLS", 10, "minecraft:stone", "minecraft:gravel"),
                new CaveniaBiomeEntry("SWAMPLAND", 60, "minecraft:stone", "minecraft:grass"),
                new CaveniaBiomeEntry("EXTREME_HILLS", 50, "minecraft:stone", "minecraft:stone"),
                new CaveniaBiomeEntry("SAVANNA", 50, "minecraft:stone", "minecraft:grass"),
                new CaveniaBiomeEntry("MESA", 50, "minecraft:stone", "minecraft:red_sandstone")
            ),
            CaveniaBiomeTopFilterPolicy.entries()
        );
    }

    @Test
    void rosterSizeAndTotalWeightRemainPinned() {
        assertEquals(14, CaveniaBiomeTopFilterPolicy.entries().size());
        assertEquals(675, CaveniaBiomeTopFilterPolicy.totalWeight());
    }

    @Test
    void everyEntryKeepsTerrainAndTopBlocksPinned() {
        assertTrue(CaveniaBiomeTopFilterPolicy.entries().stream().allMatch(entry -> !entry.terrainBlockId().isBlank()));
        assertTrue(CaveniaBiomeTopFilterPolicy.entries().stream().allMatch(entry -> !entry.topBlockId().isBlank()));
        assertEquals("minecraft:stone", CaveniaBiomeTopFilterPolicy.defaultTerrainBlockId());
        assertTrue(CaveniaBiomeTopFilterPolicy.entries().stream().allMatch(CaveniaBiomeTopFilterPolicy::usesDefaultTerrainBlock));
    }

    @Test
    void sourceConfirmedTopBlockMappingsRemainPinned() {
        assertEquals(Optional.of("minecraft:gravel"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("OCEAN"));
        assertEquals(Optional.of("minecraft:grass"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("PLAINS"));
        assertEquals(Optional.of("minecraft:sand"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("DESERT"));
        assertEquals(Optional.of("minecraft:sandstone"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("DESERT_HILLS"));
        assertEquals(Optional.of("minecraft:gravel"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("FOREST"));
        assertEquals(Optional.of("minecraft:gravel"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("FOREST_HILLS"));
        assertEquals(Optional.of("minecraft:stone"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("TAIGA"));
        assertEquals(Optional.of("minecraft:stone"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("TAIGA_HILLS"));
        assertEquals(Optional.of("minecraft:gravel"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("JUNGLE"));
        assertEquals(Optional.of("minecraft:gravel"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("JUNGLE_HILLS"));
        assertEquals(Optional.of("minecraft:grass"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("SWAMPLAND"));
        assertEquals(Optional.of("minecraft:stone"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("EXTREME_HILLS"));
        assertEquals(Optional.of("minecraft:grass"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("SAVANNA"));
        assertEquals(Optional.of("minecraft:red_sandstone"), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("MESA"));
    }

    @Test
    void lookupHelpersRemainDeterministic() {
        assertTrue(CaveniaBiomeTopFilterPolicy.containsLegacyBiomeName("OCEAN"));
        assertFalse(CaveniaBiomeTopFilterPolicy.containsLegacyBiomeName("ICE_PLAINS"));
        assertEquals(
            Optional.of(new CaveniaBiomeEntry("MESA", 50, "minecraft:stone", "minecraft:red_sandstone")),
            CaveniaBiomeTopFilterPolicy.entryByLegacyBiomeName("MESA")
        );
        assertEquals(Optional.empty(), CaveniaBiomeTopFilterPolicy.entryByLegacyBiomeName("ICE_PLAINS"));
        assertEquals(Optional.empty(), CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName("ICE_PLAINS"));
    }

    @Test
    void returnedRosterRemainsSafelyUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () ->
            CaveniaBiomeTopFilterPolicy.entries().add(new CaveniaBiomeEntry("ICE_PLAINS", 1, "minecraft:stone", "minecraft:snow"))
        );
    }
}
