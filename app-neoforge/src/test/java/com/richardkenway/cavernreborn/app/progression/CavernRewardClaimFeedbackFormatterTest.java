package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernRewardClaimResult;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;

class CavernRewardClaimFeedbackFormatterTest {
    @Test
    void formatsClaimOutcomes() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionSnapshot noviceSnapshot = snapshot(playerId, CavernProgressionRank.NOVICE, 0, 0);
        CavernProgressionSnapshot apprenticeSnapshot = snapshot(playerId, CavernProgressionRank.APPRENTICE, 25, 5);

        assertEquals(
            "Claimed CAVERN reward apprentice_supply_cache: torch x16, bread x8",
            CavernRewardClaimFeedbackFormatter.format(new CavernRewardClaimResult(
                apprenticeSnapshot,
                new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.AVAILABLE),
                new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                true
            ))
        );
        assertEquals(
            "CAVERN reward apprentice_supply_cache is already claimed.",
            CavernRewardClaimFeedbackFormatter.format(new CavernRewardClaimResult(
                apprenticeSnapshot,
                new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                false
            ))
        );
        assertEquals(
            "CAVERN reward apprentice_supply_cache requires rank apprentice. Current rank: novice.",
            CavernRewardClaimFeedbackFormatter.format(new CavernRewardClaimResult(
                noviceSnapshot,
                new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.LOCKED),
                new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.LOCKED),
                false
            ))
        );
    }

    private static CavernProgressionSnapshot snapshot(UUID playerId, CavernProgressionRank rank, int score, int countedBlocks) {
        return new CavernProgressionSnapshot(
            playerId,
            countedBlocks,
            score,
            rank,
            countedBlocks == 0 ? Map.of() : Map.of("minecraft:diamond_ore", countedBlocks)
        );
    }
}
