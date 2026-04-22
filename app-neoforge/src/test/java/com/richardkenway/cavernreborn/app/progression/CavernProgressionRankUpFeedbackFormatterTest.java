package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionUnlock;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUpdateResult;

class CavernProgressionRankUpFeedbackFormatterTest {
    @Test
    void rankUpFeedbackAppearsOnlyWhenThresholdIsCrossed() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionUpdateResult noRankUp = new CavernProgressionUpdateResult(
            snapshot(playerId, 20, CavernProgressionRank.NOVICE, 4),
            snapshot(playerId, 21, CavernProgressionRank.NOVICE, 5),
            true,
            "minecraft:coal_ore",
            1
        );

        assertTrue(CavernProgressionRankUpFeedbackFormatter.formatActionBar(noRankUp).isEmpty());
        assertTrue(CavernProgressionRankUpFeedbackFormatter.formatUnlockMessage(noRankUp).isEmpty());

        CavernProgressionUpdateResult rankUp = new CavernProgressionUpdateResult(
            snapshot(playerId, 20, CavernProgressionRank.NOVICE, 4),
            snapshot(playerId, 25, CavernProgressionRank.APPRENTICE, 5),
            true,
            "minecraft:diamond_ore",
            5
        );

        assertEquals(
            "CAVERN rank up: apprentice (25 score)",
            CavernProgressionRankUpFeedbackFormatter.formatActionBar(rankUp).orElseThrow()
        );
        assertEquals(
            "Miner's Insight unlocked: counted ores in CAVERN now grant +1 bonus XP.",
            CavernProgressionRankUpFeedbackFormatter.formatUnlockMessage(rankUp).orElseThrow()
        );
    }

    @Test
    void miningAssistUnlockMessageAppearsAtJourneymanThreshold() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionUpdateResult rankUp = new CavernProgressionUpdateResult(
            snapshot(playerId, 74, CavernProgressionRank.APPRENTICE, 14),
            snapshot(playerId, 75, CavernProgressionRank.JOURNEYMAN, 15),
            true,
            "cavernreborn:hexcite_ore",
            1
        );

        assertTrue(rankUp.unlockJustReached(CavernProgressionUnlock.MINING_ASSIST));
        assertEquals(
            "Mining Assist unlocked: hexcite_pickaxe now breaks up to 6 extra connected ores in CAVERN.",
            CavernProgressionRankUpFeedbackFormatter.formatUnlockMessage(rankUp).orElseThrow()
        );
    }

    private static CavernProgressionSnapshot snapshot(UUID playerId, int score, CavernProgressionRank rank, int countedBlocks) {
        return new CavernProgressionSnapshot(
            playerId,
            countedBlocks,
            score,
            rank,
            Map.of("minecraft:diamond_ore", countedBlocks)
        );
    }
}
