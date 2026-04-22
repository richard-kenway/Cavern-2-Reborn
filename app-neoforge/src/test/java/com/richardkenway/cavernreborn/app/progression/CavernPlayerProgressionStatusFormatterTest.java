package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

class CavernPlayerProgressionStatusFormatterTest {
    @Test
    void formatsBaselineStatusForNewPlayer() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            0,
            0,
            CavernProgressionRank.NOVICE,
            Map.of()
        );

        assertEquals(
            "CAVERN rank for TestPlayer: novice | score 0 | counted ores 0 | next apprentice in 25 score | next unlock: Miner's Insight at apprentice",
            CavernPlayerProgressionStatusFormatter.format("TestPlayer", snapshot)
        );
    }

    @Test
    void formatsUnlockedConsequenceFromPersistedRank() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            5,
            25,
            CavernProgressionRank.APPRENTICE,
            Map.of("minecraft:diamond_ore", 5)
        );

        assertEquals(
            "CAVERN rank for TestPlayer: apprentice | score 25 | counted ores 5 | next journeyman in 50 score | unlocked: Miner's Insight (+1 bonus XP on counted ores in CAVERN) | next unlock: Mining Assist at journeyman",
            CavernPlayerProgressionStatusFormatter.format("TestPlayer", snapshot)
        );
    }

    @Test
    void formatsMiningAssistOnceJourneymanIsUnlocked() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            15,
            75,
            CavernProgressionRank.JOURNEYMAN,
            Map.of("minecraft:diamond_ore", 15)
        );

        assertEquals(
            "CAVERN rank for TestPlayer: journeyman | score 75 | counted ores 15 | next veteran in 100 score | unlocked: Miner's Insight (+1 bonus XP on counted ores in CAVERN) | unlocked: Mining Assist (bounded hexcite_pickaxe vein assist in CAVERN)",
            CavernPlayerProgressionStatusFormatter.format("TestPlayer", snapshot)
        );
    }
}
