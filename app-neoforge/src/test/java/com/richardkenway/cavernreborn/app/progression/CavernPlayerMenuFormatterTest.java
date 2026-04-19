package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;

class CavernPlayerMenuFormatterTest {
    @Test
    void formatsLockedMenuForNewPlayer() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            0,
            0,
            CavernProgressionRank.NOVICE,
            Map.of()
        );

        List<Component> lines = CavernPlayerMenuFormatter.format(
            "TestPlayer",
            snapshot,
            List.of(
                rewardEntry(CavernCatalogAvailability.LOCKED),
                serviceEntry(CavernCatalogAvailability.LOCKED, 0L),
                journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
            )
        );

        assertEquals("=== CAVERN MENU: TestPlayer ===", lines.get(0).getString());
        assertEquals("Rank novice | score 0 | available now 0 | next tier apprentice", lines.get(1).getString());
        assertEquals("Click CLAIM/USE or run /cavern menu use <entry>.", lines.get(2).getString());
        assertEquals("Actions: [Refresh] [Rank] [Rewards] [Services] [Catalog]", lines.get(3).getString());
        assertEquals("/cavern menu", clickCommand(lines.get(3), 0));
        assertEquals("-- apprentice tier [next] --", lines.get(4).getString());
        assertEquals(
            "[LOCKED] Apprentice Supply Cache | reward | requires apprentice | current novice | grants torch x16, bread x8",
            lines.get(5).getString()
        );
        assertNull(clickCommand(lines.get(5), 0));
        assertEquals(
            "[LOCKED] Torch Supply | service | requires apprentice | current novice | grants torch x16",
            lines.get(6).getString()
        );
        assertEquals("-- journeyman tier [locked] --", lines.get(7).getString());
    }

    @Test
    void formatsAvailableAndCooldownMenuStatesWithClickableActions() {
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            UUID.randomUUID(),
            5,
            25,
            CavernProgressionRank.APPRENTICE,
            Map.of("minecraft:diamond_ore", 5)
        );

        List<Component> availableLines = CavernPlayerMenuFormatter.format(
            "TestPlayer",
            snapshot,
            List.of(
                rewardEntry(CavernCatalogAvailability.AVAILABLE),
                serviceEntry(CavernCatalogAvailability.AVAILABLE, 0L),
                journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
            )
        );

        assertEquals("Rank apprentice | score 25 | available now 2 | next tier journeyman", availableLines.get(1).getString());
        assertEquals("-- apprentice tier [current] --", availableLines.get(4).getString());
        assertEquals(
            "[CLAIM] Apprentice Supply Cache | reward | available now | grants torch x16, bread x8",
            availableLines.get(5).getString()
        );
        assertEquals("/cavern menu use apprentice_supply_cache", clickCommand(availableLines.get(5), 0));
        assertEquals(
            "[USE] Torch Supply | service | available now | grants torch x16",
            availableLines.get(6).getString()
        );
        assertEquals("/cavern menu use torch_supply", clickCommand(availableLines.get(6), 0));
        assertEquals("-- journeyman tier [next] --", availableLines.get(7).getString());

        List<Component> cooldownLines = CavernPlayerMenuFormatter.format(
            "TestPlayer",
            snapshot,
            List.of(
                rewardEntry(CavernCatalogAvailability.CLAIMED),
                serviceEntry(CavernCatalogAvailability.ON_COOLDOWN, 600_000L),
                journeymanRewardEntry(CavernCatalogAvailability.LOCKED),
                journeymanServiceEntry(CavernCatalogAvailability.LOCKED, 0L)
            )
        );

        assertEquals(
            "[CLAIMED] Apprentice Supply Cache | reward | already claimed | grants torch x16, bread x8",
            cooldownLines.get(5).getString()
        );
        assertNull(clickCommand(cooldownLines.get(5), 0));
        assertEquals(
            "[COOLDOWN] Torch Supply | service | ready in 10m 0s | grants torch x16",
            cooldownLines.get(6).getString()
        );
        assertNull(clickCommand(cooldownLines.get(6), 0));
    }

    private static String clickCommand(Component component, int siblingIndex) {
        ClickEvent clickEvent = component.getSiblings().get(siblingIndex).getStyle().getClickEvent();
        return clickEvent == null ? null : clickEvent.getValue();
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
