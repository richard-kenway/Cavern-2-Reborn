package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntryType;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernRewardClaimResult;
import com.richardkenway.cavernreborn.core.progression.CavernRewardGrant;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;
import com.richardkenway.cavernreborn.core.progression.CavernServiceAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernServiceEntry;
import com.richardkenway.cavernreborn.core.progression.CavernServiceRequestResult;
import com.richardkenway.cavernreborn.core.progression.CavernServiceStatus;

class CavernCatalogUseFeedbackFormatterTest {
    @Test
    void formatsRewardAndServiceCatalogUseOutcomes() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionSnapshot noviceSnapshot = snapshot(playerId, CavernProgressionRank.NOVICE, 0, 0);
        CavernProgressionSnapshot apprenticeSnapshot = snapshot(playerId, CavernProgressionRank.APPRENTICE, 25, 5);

        assertEquals(
            "Catalog entry used: apprentice_supply_cache (torch x16, bread x8)",
            CavernCatalogUseFeedbackFormatter.format(new CavernCatalogUseResult(
                rewardEntry(CavernCatalogAvailability.CLAIMED),
                new CavernRewardClaimResult(
                    apprenticeSnapshot,
                    new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.AVAILABLE),
                    new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                    true
                ),
                null
            ))
        );
        assertEquals(
            "Catalog entry apprentice_supply_cache is already claimed.",
            CavernCatalogUseFeedbackFormatter.format(new CavernCatalogUseResult(
                rewardEntry(CavernCatalogAvailability.CLAIMED),
                new CavernRewardClaimResult(
                    apprenticeSnapshot,
                    new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                    new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                    false
                ),
                null
            ))
        );
        assertEquals(
            "Catalog entry apprentice_supply_cache requires rank apprentice. Current rank: novice.",
            CavernCatalogUseFeedbackFormatter.format(new CavernCatalogUseResult(
                rewardEntry(CavernCatalogAvailability.LOCKED),
                new CavernRewardClaimResult(
                    noviceSnapshot,
                    new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.LOCKED),
                    new CavernRewardStatus(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, CavernRewardAvailability.LOCKED),
                    false
                ),
                null
            ))
        );
        assertEquals(
            "Catalog entry used: torch_supply (torch x16)",
            CavernCatalogUseFeedbackFormatter.format(new CavernCatalogUseResult(
                serviceEntry(CavernCatalogAvailability.ON_COOLDOWN),
                null,
                new CavernServiceRequestResult(
                    apprenticeSnapshot,
                    new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.AVAILABLE),
                    new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.ON_COOLDOWN),
                    true
                )
            ))
        );
        assertEquals(
            "Catalog entry torch_supply is on cooldown.",
            CavernCatalogUseFeedbackFormatter.format(new CavernCatalogUseResult(
                serviceEntry(CavernCatalogAvailability.ON_COOLDOWN),
                null,
                new CavernServiceRequestResult(
                    apprenticeSnapshot,
                    new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.ON_COOLDOWN),
                    new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.ON_COOLDOWN),
                    false
                )
            ))
        );
        assertEquals(
            "Catalog entry used: journeyman_supply_cache (torch x24, cooked_beef x8, water_bucket x1)",
            CavernCatalogUseFeedbackFormatter.format(new CavernCatalogUseResult(
                new CavernCatalogEntry(
                    CavernCatalogEntryType.REWARD,
                    "journeyman_supply_cache",
                    "Journeyman Supply Cache",
                    CavernProgressionRank.JOURNEYMAN,
                    CavernCatalogAvailability.CLAIMED,
                    false,
                    List.of(
                        new CavernRewardGrant("minecraft:torch", 24),
                        new CavernRewardGrant("minecraft:cooked_beef", 8),
                        new CavernRewardGrant("minecraft:water_bucket", 1)
                    ),
                    0L
                ),
                new CavernRewardClaimResult(
                    snapshot(playerId, CavernProgressionRank.JOURNEYMAN, 75, 15),
                    new CavernRewardStatus(CavernProgressionReward.JOURNEYMAN_SUPPLY_CACHE, CavernRewardAvailability.AVAILABLE),
                    new CavernRewardStatus(CavernProgressionReward.JOURNEYMAN_SUPPLY_CACHE, CavernRewardAvailability.CLAIMED),
                    true
                ),
                null
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

    private static CavernCatalogEntry rewardEntry(CavernCatalogAvailability availability) {
        return new CavernCatalogEntry(
            CavernCatalogEntryType.REWARD,
            "apprentice_supply_cache",
            "Apprentice Supply Cache",
            CavernProgressionRank.APPRENTICE,
            availability,
            false,
            List.of(
                new CavernRewardGrant("minecraft:torch", 16),
                new CavernRewardGrant("minecraft:bread", 8)
            ),
            0L
        );
    }

    private static CavernCatalogEntry serviceEntry(CavernCatalogAvailability availability) {
        return new CavernCatalogEntry(
            CavernCatalogEntryType.SERVICE,
            "torch_supply",
            "Torch Supply",
            CavernProgressionRank.APPRENTICE,
            availability,
            true,
            List.of(new CavernRewardGrant("minecraft:torch", 16)),
            availability == CavernCatalogAvailability.ON_COOLDOWN ? 600_000L : 0L
        );
    }
}
