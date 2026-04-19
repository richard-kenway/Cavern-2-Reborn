package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class CavernCatalogGuiLayoutTest {
    @Test
    void buildsLockedGuiForNewPlayer() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            0,
            0,
            CavernProgressionRank.NOVICE,
            Map.of()
        );

        CavernCatalogGuiLayout.Layout layout = CavernCatalogGuiLayout.create(
            snapshot,
            List.of(
                rewardEntry(CavernCatalogAvailability.LOCKED),
                serviceEntry(CavernCatalogAvailability.LOCKED, 0L),
                journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
            )
        );

        assertEquals(CavernCatalogGuiLayout.SLOT_COUNT, layout.slotItems().size());
        assertEquals("Rank: novice", itemName(layout.slotItems().get(CavernCatalogGuiLayout.RANK_SLOT)));
        assertEquals("Score: 0", itemName(layout.slotItems().get(CavernCatalogGuiLayout.SCORE_SLOT)));
        assertEquals("Next tier: apprentice", itemName(layout.slotItems().get(CavernCatalogGuiLayout.NEXT_TIER_SLOT)));
        assertEquals("Available now: 0", itemName(layout.slotItems().get(CavernCatalogGuiLayout.AVAILABLE_SLOT)));
        assertEquals("Refresh view", itemName(layout.slotItems().get(CavernCatalogGuiLayout.REFRESH_SLOT)));
        assertEquals("Apprentice Tier [next]", itemName(layout.slotItems().get(CavernCatalogGuiLayout.APPRENTICE_HEADER_SLOT)));
        assertEquals("Journeyman Tier [locked]", itemName(layout.slotItems().get(CavernCatalogGuiLayout.JOURNEYMAN_HEADER_SLOT)));
        assertEquals("[LOCKED] Apprentice Supply Cache", itemName(layout.slotItems().get(10)));
        assertEquals("[LOCKED] Torch Supply", itemName(layout.slotItems().get(11)));
        assertEquals(
            List.of(
                "Type: reward",
                "Status: locked",
                "Requires rank: apprentice",
                "Current rank: novice",
                "Grants: torch x16, bread x8"
            ),
            lore(layout.slotItems().get(10))
        );
        assertEquals("apprentice_supply_cache", layout.entryIdsBySlot().get(10));
        assertEquals("torch_supply", layout.entryIdsBySlot().get(11));
        assertEquals("journeyman_supply_cache", layout.entryIdsBySlot().get(19));
        assertEquals("climbing_supply", layout.entryIdsBySlot().get(20));
    }

    @Test
    void buildsAvailableClaimedAndCooldownStates() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            15,
            75,
            CavernProgressionRank.JOURNEYMAN,
            Map.of("minecraft:diamond_ore", 15)
        );

        CavernCatalogGuiLayout.Layout layout = CavernCatalogGuiLayout.create(
            snapshot,
            List.of(
                rewardEntry(CavernCatalogAvailability.AVAILABLE),
                serviceEntry(CavernCatalogAvailability.AVAILABLE, 0L),
                journeymanRewardEntry(CavernCatalogAvailability.CLAIMED),
                journeymanServiceEntry(CavernCatalogAvailability.ON_COOLDOWN, 1_200_000L)
            )
        );

        assertEquals("Rank: journeyman", itemName(layout.slotItems().get(CavernCatalogGuiLayout.RANK_SLOT)));
        assertEquals("Next tier: none", itemName(layout.slotItems().get(CavernCatalogGuiLayout.NEXT_TIER_SLOT)));
        assertEquals("Available now: 2", itemName(layout.slotItems().get(CavernCatalogGuiLayout.AVAILABLE_SLOT)));
        assertEquals("Apprentice Tier [unlocked]", itemName(layout.slotItems().get(CavernCatalogGuiLayout.APPRENTICE_HEADER_SLOT)));
        assertEquals("Journeyman Tier [current]", itemName(layout.slotItems().get(CavernCatalogGuiLayout.JOURNEYMAN_HEADER_SLOT)));
        assertEquals("[AVAILABLE] Apprentice Supply Cache", itemName(layout.slotItems().get(10)));
        assertEquals(
            List.of(
                "Type: reward",
                "Status: available now",
                "Required rank: apprentice",
                "Click to claim.",
                "Grants: torch x16, bread x8"
            ),
            lore(layout.slotItems().get(10))
        );
        assertEquals("[CLAIMED] Journeyman Supply Cache", itemName(layout.slotItems().get(19)));
        assertTrue(lore(layout.slotItems().get(19)).contains("Already claimed once."));
        assertEquals("[COOLDOWN] Climbing Supply", itemName(layout.slotItems().get(20)));
        assertTrue(lore(layout.slotItems().get(20)).contains("Status: on cooldown (20m 0s)"));
        assertTrue(lore(layout.slotItems().get(20)).contains("Ready in: 20m 0s"));
    }

    private static String itemName(CavernCatalogGuiLayout.SlotView slotView) {
        return slotView.title();
    }

    private static List<String> lore(CavernCatalogGuiLayout.SlotView slotView) {
        return slotView.lore();
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
