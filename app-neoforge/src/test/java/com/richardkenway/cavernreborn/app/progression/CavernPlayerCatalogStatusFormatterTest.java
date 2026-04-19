package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntryType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardGrant;

class CavernPlayerCatalogStatusFormatterTest {
    @Test
    void formatsLockedCatalogForNewPlayer() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            0,
            0,
            CavernProgressionRank.NOVICE,
            Map.of()
        );

        assertEquals(
            "CAVERN catalog for TestPlayer: rank=novice, available=0, next=apprentice, tiers="
                + "apprentice -> apprentice_supply_cache [reward, locked: requires apprentice, current novice, grants torch x16, bread x8], "
                + "torch_supply [service, locked: requires apprentice, current novice, grants torch x16]; "
                + "journeyman -> journeyman_supply_cache [reward, locked: requires journeyman, current novice, grants torch x24, cooked_beef x8, water_bucket x1], "
                + "climbing_supply [service, locked: requires journeyman, current novice, grants ladder x16, cobblestone x32]",
            CavernPlayerCatalogStatusFormatter.format(
                "TestPlayer",
                snapshot,
                List.of(
                    rewardEntry(CavernCatalogAvailability.LOCKED),
                    serviceEntry(CavernCatalogAvailability.LOCKED, 0L),
                    journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                    journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
                )
            )
        );
    }

    @Test
    void formatsAvailableAndCooldownCatalogStates() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            5,
            25,
            CavernProgressionRank.APPRENTICE,
            Map.of("minecraft:diamond_ore", 5)
        );

        assertEquals(
            "CAVERN catalog for TestPlayer: rank=apprentice, available=2, next=journeyman, tiers="
                + "apprentice -> apprentice_supply_cache [reward, available: use /cavern use apprentice_supply_cache, grants torch x16, bread x8], "
                + "torch_supply [service, available: use /cavern use torch_supply, grants torch x16]; "
                + "journeyman -> journeyman_supply_cache [reward, locked: requires journeyman, current apprentice, grants torch x24, cooked_beef x8, water_bucket x1], "
                + "climbing_supply [service, locked: requires journeyman, current apprentice, grants ladder x16, cobblestone x32]",
            CavernPlayerCatalogStatusFormatter.format(
                "TestPlayer",
                snapshot,
                List.of(
                    rewardEntry(CavernCatalogAvailability.AVAILABLE),
                    serviceEntry(CavernCatalogAvailability.AVAILABLE, 0L),
                    journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                    journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
                )
            )
        );
        assertEquals(
            "CAVERN catalog for TestPlayer: rank=apprentice, available=0, next=journeyman, tiers="
                + "apprentice -> apprentice_supply_cache [reward, claimed: already claimed, grants torch x16, bread x8], "
                + "torch_supply [service, on cooldown: ready in 10m 0s, grants torch x16]; "
                + "journeyman -> journeyman_supply_cache [reward, locked: requires journeyman, current apprentice, grants torch x24, cooked_beef x8, water_bucket x1], "
                + "climbing_supply [service, locked: requires journeyman, current apprentice, grants ladder x16, cobblestone x32]",
            CavernPlayerCatalogStatusFormatter.format(
                "TestPlayer",
                snapshot,
                List.of(
                    rewardEntry(CavernCatalogAvailability.CLAIMED),
                    serviceEntry(CavernCatalogAvailability.ON_COOLDOWN, 600_000L),
                    journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                    journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
                )
            )
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

    private static CavernCatalogEntry serviceEntry(CavernCatalogAvailability availability, long cooldownRemainingMillis) {
        return new CavernCatalogEntry(
            CavernCatalogEntryType.SERVICE,
            "torch_supply",
            "Torch Supply",
            CavernProgressionRank.APPRENTICE,
            availability,
            true,
            List.of(new CavernRewardGrant("minecraft:torch", 16)),
            cooldownRemainingMillis
        );
    }

    private static CavernCatalogEntry journeymanRewardEntry(CavernCatalogAvailability availability) {
        return new CavernCatalogEntry(
            CavernCatalogEntryType.REWARD,
            "journeyman_supply_cache",
            "Journeyman Supply Cache",
            CavernProgressionRank.JOURNEYMAN,
            availability,
            false,
            List.of(
                new CavernRewardGrant("minecraft:torch", 24),
                new CavernRewardGrant("minecraft:cooked_beef", 8),
                new CavernRewardGrant("minecraft:water_bucket", 1)
            ),
            0L
        );
    }

    private static CavernCatalogEntry journeymanServiceEntry(CavernCatalogAvailability availability, long cooldownRemainingMillis) {
        return new CavernCatalogEntry(
            CavernCatalogEntryType.SERVICE,
            "climbing_supply",
            "Climbing Supply",
            CavernProgressionRank.JOURNEYMAN,
            availability,
            true,
            List.of(
                new CavernRewardGrant("minecraft:ladder", 16),
                new CavernRewardGrant("minecraft:cobblestone", 32)
            ),
            cooldownRemainingMillis
        );
    }
}
