package com.richardkenway.cavernreborn.core.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernProgressionPolicyTest {
    @Test
    void scoreTableIncludesCustomCavernOreEntries() {
        assertEquals(1, CavernProgressionPolicy.scoreForBlock("cavernreborn:magnite_ore"));
        assertEquals(2, CavernProgressionPolicy.scoreForBlock("cavernreborn:aquamarine_ore"));
        assertEquals(2, CavernProgressionPolicy.scoreForBlock("cavernreborn:randomite_ore"));
        assertEquals(3, CavernProgressionPolicy.scoreForBlock("cavernreborn:fissured_stone"));
        assertEquals(4, CavernProgressionPolicy.scoreForBlock("cavernreborn:hexcite_ore"));
    }

    @Test
    void customCavernOresCountOnlyInsideCavern() {
        List<String> customBlockIds = List.of(
            "cavernreborn:magnite_ore",
            "cavernreborn:aquamarine_ore",
            "cavernreborn:randomite_ore",
            "cavernreborn:fissured_stone",
            "cavernreborn:hexcite_ore"
        );

        for (String blockId : customBlockIds) {
            assertTrue(CavernProgressionPolicy.countsTowardProgression(CavernDimensions.CAVERN_DIMENSION_ID, blockId));
            assertFalse(CavernProgressionPolicy.countsTowardProgression(CavernDimensions.OVERWORLD_DIMENSION_ID, blockId));
        }
    }

    @Test
    void unsupportedBlocksRemainIgnored() {
        assertEquals(0, CavernProgressionPolicy.scoreForBlock("minecraft:stone"));
        assertFalse(CavernProgressionPolicy.countsTowardProgression(CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:stone"));
    }

    @Test
    void scoreTableIterationOrderRemainsDeterministic() {
        List<String> expectedTail = List.of(
            "cavernreborn:magnite_ore",
            "cavernreborn:aquamarine_ore",
            "cavernreborn:randomite_ore",
            "cavernreborn:fissured_stone",
            "cavernreborn:hexcite_ore"
        );

        List<String> blockIds = CavernProgressionPolicy.scoreByBlock().keySet().stream().toList();

        assertIterableEquals(expectedTail, blockIds.subList(blockIds.size() - expectedTail.size(), blockIds.size()));
        assertEquals(blockIds, CavernProgressionPolicy.scoreByBlock().keySet().stream().toList());
    }
}
