package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

class CavernProgressionDebugSummaryFormatterTest {
    @Test
    void formatsEmptySummaryForNewPlayer() {
        String summary = CavernProgressionDebugSummaryFormatter.format(
            "TestPlayer",
            new CavernProgressionSnapshot(UUID.randomUUID(), 0, 0, CavernProgressionRank.NOVICE, Map.of())
        );

        assertEquals(
            "CAVERN progression for TestPlayer: rank=novice, score=0, counted_blocks=0, next_rank=apprentice@25, points_to_next=25, top_blocks=none",
            summary
        );
    }

    @Test
    void formatsSortedBreakdownForPopulatedState() {
        String summary = CavernProgressionDebugSummaryFormatter.format(
            "Miner",
            new CavernProgressionSnapshot(
                UUID.randomUUID(),
                6,
                19,
                CavernProgressionRank.NOVICE,
                Map.of(
                    "minecraft:diamond_ore", 1,
                    "minecraft:iron_ore", 3,
                    "minecraft:coal_ore", 2
                )
            )
        );

        assertTrue(summary.contains("top_blocks=iron_ore=3, coal_ore=2, diamond_ore=1"));
        assertTrue(summary.contains("score=19"));
        assertTrue(summary.contains("counted_blocks=6"));
    }
}
