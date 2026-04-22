package com.richardkenway.cavernreborn.core.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class CavernProgressionUnlockTest {
    @Test
    void miningAssistUnlockUsesTheNextRankAfterApprentice() {
        assertEquals(CavernProgressionRank.APPRENTICE, CavernProgressionUnlock.MINERS_INSIGHT.requiredRank());
        assertEquals(CavernProgressionRank.JOURNEYMAN, CavernProgressionUnlock.MINING_ASSIST.requiredRank());
    }

    @Test
    void unlocksAreDerivedFromRankWithoutExtraSavedFlags() {
        CavernProgressionSnapshot apprentice = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            5,
            CavernProgressionRank.APPRENTICE.threshold(),
            CavernProgressionRank.APPRENTICE,
            Map.of("minecraft:diamond_ore", 5)
        );
        CavernProgressionSnapshot journeyman = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            15,
            CavernProgressionRank.JOURNEYMAN.threshold(),
            CavernProgressionRank.JOURNEYMAN,
            Map.of("minecraft:diamond_ore", 15)
        );

        assertTrue(apprentice.hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT));
        assertFalse(apprentice.hasUnlocked(CavernProgressionUnlock.MINING_ASSIST));
        assertTrue(journeyman.hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT));
        assertTrue(journeyman.hasUnlocked(CavernProgressionUnlock.MINING_ASSIST));
    }
}
