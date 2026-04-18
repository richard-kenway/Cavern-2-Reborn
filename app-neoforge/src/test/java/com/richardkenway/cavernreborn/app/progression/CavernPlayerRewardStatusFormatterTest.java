package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;

class CavernPlayerRewardStatusFormatterTest {
    @Test
    void formatsLockedRewardForNewPlayer() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            0,
            0,
            CavernProgressionRank.NOVICE,
            Map.of()
        );

        assertEquals(
            "CAVERN rewards for TestPlayer: apprentice_supply_cache [locked: requires apprentice, current novice, grants torch x16, bread x8]",
            CavernPlayerRewardStatusFormatter.format(
                "TestPlayer",
                snapshot,
                List.of(new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.LOCKED))
            )
        );
    }

    @Test
    void formatsAvailableAndClaimedRewardStates() {
        CavernProgressionSnapshot apprenticeSnapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            5,
            25,
            CavernProgressionRank.APPRENTICE,
            Map.of("minecraft:diamond_ore", 5)
        );

        assertEquals(
            "CAVERN rewards for TestPlayer: apprentice_supply_cache [available: claim with /cavern claim apprentice_supply_cache, grants torch x16, bread x8]",
            CavernPlayerRewardStatusFormatter.format(
                "TestPlayer",
                apprenticeSnapshot,
                List.of(new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.AVAILABLE))
            )
        );
        assertEquals(
            "CAVERN rewards for TestPlayer: apprentice_supply_cache [claimed: grants torch x16, bread x8]",
            CavernPlayerRewardStatusFormatter.format(
                "TestPlayer",
                apprenticeSnapshot,
                List.of(new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED))
            )
        );
    }
}
