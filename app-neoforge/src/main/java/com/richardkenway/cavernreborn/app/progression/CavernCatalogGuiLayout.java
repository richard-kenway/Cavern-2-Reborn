package com.richardkenway.cavernreborn.app.progression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntryType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardGrant;

import net.minecraft.ChatFormatting;

public final class CavernCatalogGuiLayout {
    static final int SLOT_COUNT = 27;
    static final int RANK_SLOT = 0;
    static final int SCORE_SLOT = 1;
    static final int NEXT_TIER_SLOT = 2;
    static final int AVAILABLE_SLOT = 3;
    static final int GUIDE_SLOT = 4;
    static final int REFRESH_SLOT = 8;
    static final int APPRENTICE_HEADER_SLOT = 9;
    static final int JOURNEYMAN_HEADER_SLOT = 18;

    private static final Comparator<CavernCatalogEntry> CATALOG_ENTRY_ORDER = Comparator
        .comparingInt((CavernCatalogEntry entry) -> entry.requiredRank().threshold())
        .thenComparingInt(entry -> entry.type().ordinal())
        .thenComparing(CavernCatalogEntry::id);
    private static final List<Integer> APPRENTICE_ENTRY_SLOTS = List.of(10, 11, 12, 13, 14, 15, 16, 17);
    private static final List<Integer> JOURNEYMAN_ENTRY_SLOTS = List.of(19, 20, 21, 22, 23, 24, 25, 26);

    private CavernCatalogGuiLayout() {
    }

    public static Layout create(
        CavernProgressionSnapshot snapshot,
        List<CavernCatalogEntry> catalogEntries
    ) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        List<CavernCatalogEntry> normalizedEntries = Objects.requireNonNull(catalogEntries, "catalogEntries").stream()
            .sorted(CATALOG_ENTRY_ORDER)
            .toList();
        Optional<CavernProgressionRank> nextTier = nextTier(normalizedSnapshot, normalizedEntries);
        long availableCount = normalizedEntries.stream().filter(CavernCatalogEntry::availableToUse).count();
        SlotView[] slotItems = new SlotView[SLOT_COUNT];
        Arrays.fill(slotItems, fillerItem());

        slotItems[RANK_SLOT] = summaryItem(
            "minecraft:experience_bottle",
            ChatFormatting.GOLD,
            "Rank: " + normalizedSnapshot.rank().id(),
            List.of(
                "Score: " + normalizedSnapshot.progressionScore(),
                "Counted blocks: " + normalizedSnapshot.countedBlocks()
            )
        );
        slotItems[SCORE_SLOT] = summaryItem(
            "minecraft:emerald",
            ChatFormatting.YELLOW,
            "Score: " + normalizedSnapshot.progressionScore(),
            List.of(
                "Current rank: " + normalizedSnapshot.rank().id(),
                "Progression remains server-side and persistent."
            )
        );
        slotItems[NEXT_TIER_SLOT] = summaryItem(
            "minecraft:compass",
            ChatFormatting.AQUA,
            "Next tier: " + nextTier.map(CavernProgressionRank::id).orElse("none"),
            nextTier
                .<List<String>>map(rank -> List.of(
                    "Unlocks at score " + rank.threshold(),
                    "Current score: " + normalizedSnapshot.progressionScore()
                ))
                .orElseGet(() -> List.of(
                    "All checked-in tiers are unlocked.",
                    "Use rewards/services or keep mining."
                ))
        );
        slotItems[AVAILABLE_SLOT] = summaryItem(
            "minecraft:lantern",
            ChatFormatting.GREEN,
            "Available now: " + availableCount,
            List.of(
                "Ready entries use the same backend as /cavern catalog.",
                "Locked/claimed/cooldown states are read live."
            )
        );
        slotItems[GUIDE_SLOT] = summaryItem(
            "minecraft:book",
            ChatFormatting.BLUE,
            "How to use",
            List.of(
                "Click a reward or service entry to route it.",
                "No currency or prices exist in this baseline.",
                "Use /cavern menu or /cavern catalog for text views."
            )
        );
        slotItems[REFRESH_SLOT] = summaryItem(
            "minecraft:clock",
            ChatFormatting.GREEN,
            "Refresh view",
            List.of(
                "Click to rebuild this GUI from persisted state.",
                "Useful after rank or cooldown changes."
            )
        );

        Map<CavernProgressionRank, List<CavernCatalogEntry>> entriesByTier = normalizedEntries.stream()
            .collect(Collectors.groupingBy(
                CavernCatalogEntry::requiredRank,
                LinkedHashMap::new,
                Collectors.toList()
            ));
        LinkedHashMap<Integer, String> entryIdsBySlot = new LinkedHashMap<>();
        int tierRowIndex = 0;
        for (Map.Entry<CavernProgressionRank, List<CavernCatalogEntry>> tier : entriesByTier.entrySet()) {
            if (tierRowIndex > 1) {
                break;
            }
            int headerSlot = tierRowIndex == 0 ? APPRENTICE_HEADER_SLOT : JOURNEYMAN_HEADER_SLOT;
            List<Integer> tierSlots = tierRowIndex == 0 ? APPRENTICE_ENTRY_SLOTS : JOURNEYMAN_ENTRY_SLOTS;
            slotItems[headerSlot] = tierHeaderItem(normalizedSnapshot, tier.getKey(), nextTier, tier.getValue().size());
            for (int i = 0; i < tier.getValue().size() && i < tierSlots.size(); i++) {
                int slot = tierSlots.get(i);
                CavernCatalogEntry entry = tier.getValue().get(i);
                slotItems[slot] = entryItem(normalizedSnapshot, entry);
                entryIdsBySlot.put(slot, entry.id());
            }
            tierRowIndex++;
        }

        if (entriesByTier.size() > 2) {
            slotItems[26] = summaryItem(
                "minecraft:paper",
                ChatFormatting.YELLOW,
                "Additional tiers exist",
                List.of(
                    "This GUI is intentionally bounded to the current baseline.",
                    "Use /cavern catalog for any extra tiers."
                )
            );
        }

        return new Layout(
            Arrays.stream(slotItems).toList(),
            Map.copyOf(entryIdsBySlot)
        );
    }

    private static SlotView tierHeaderItem(
        CavernProgressionSnapshot snapshot,
        CavernProgressionRank tier,
        Optional<CavernProgressionRank> nextTier,
        int entryCount
    ) {
        String tierState = tierState(snapshot, tier, nextTier);
        return summaryItem(
            tier == CavernProgressionRank.APPRENTICE ? "minecraft:coal" : "minecraft:iron_ingot",
            ChatFormatting.AQUA,
            capitalize(tier.id()) + " Tier [" + tierState + "]",
            List.of(
                "Unlock score: " + tier.threshold(),
                "Entries in this tier: " + entryCount
            )
        );
    }

    private static SlotView entryItem(CavernProgressionSnapshot snapshot, CavernCatalogEntry entry) {
        return new SlotView(
            entryIconId(entry),
            statusColor(entry.availability()),
            statusPrefix(entry.availability()) + " " + entry.label(),
            entryLore(snapshot, entry)
        );
    }

    private static List<String> entryLore(CavernProgressionSnapshot snapshot, CavernCatalogEntry entry) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Type: " + (entry.type() == CavernCatalogEntryType.REWARD ? "reward" : "service"));
        lines.add("Status: " + statusLabel(entry.availability(), entry.cooldownRemainingMillis()));
        if (entry.locked()) {
            lines.add("Requires rank: " + entry.requiredRank().id());
            lines.add("Current rank: " + snapshot.rank().id());
        } else if (entry.claimed()) {
            lines.add(entry.repeatable() ? "Last use complete." : "Already claimed once.");
        } else if (entry.onCooldown()) {
            lines.add("Ready in: " + formatCooldown(entry.cooldownRemainingMillis()));
        } else {
            lines.add("Required rank: " + entry.requiredRank().id());
            lines.add("Click to " + (entry.type() == CavernCatalogEntryType.REWARD ? "claim" : "use") + ".");
        }
        lines.add("Grants: " + formatGrants(entry.grants()));
        return List.copyOf(lines);
    }

    private static String entryIconId(CavernCatalogEntry entry) {
        if (entry.type() == CavernCatalogEntryType.REWARD) {
            return "minecraft:chest";
        }
        return entry.grants().getFirst().itemId();
    }

    private static String formatGrants(List<CavernRewardGrant> grants) {
        return grants.stream()
            .map(grant -> shortItemId(grant.itemId()) + " x" + grant.count())
            .collect(Collectors.joining(", "));
    }

    private static String shortItemId(String itemId) {
        int separator = itemId.indexOf(':');
        return separator >= 0 ? itemId.substring(separator + 1) : itemId;
    }

    private static String statusPrefix(CavernCatalogAvailability availability) {
        return switch (availability) {
            case AVAILABLE -> "[AVAILABLE]";
            case LOCKED -> "[LOCKED]";
            case CLAIMED -> "[CLAIMED]";
            case ON_COOLDOWN -> "[COOLDOWN]";
        };
    }

    private static String statusLabel(CavernCatalogAvailability availability, long cooldownRemainingMillis) {
        return switch (availability) {
            case AVAILABLE -> "available now";
            case LOCKED -> "locked";
            case CLAIMED -> "already claimed";
            case ON_COOLDOWN -> "on cooldown (" + formatCooldown(cooldownRemainingMillis) + ")";
        };
    }

    private static ChatFormatting statusColor(CavernCatalogAvailability availability) {
        return switch (availability) {
            case AVAILABLE -> ChatFormatting.GREEN;
            case LOCKED -> ChatFormatting.RED;
            case CLAIMED -> ChatFormatting.DARK_GRAY;
            case ON_COOLDOWN -> ChatFormatting.YELLOW;
        };
    }

    private static Optional<CavernProgressionRank> nextTier(
        CavernProgressionSnapshot snapshot,
        List<CavernCatalogEntry> entries
    ) {
        return entries.stream()
            .map(CavernCatalogEntry::requiredRank)
            .filter(rank -> rank.ordinal() > snapshot.rank().ordinal())
            .distinct()
            .min(Comparator.comparingInt(CavernProgressionRank::threshold));
    }

    private static String tierState(
        CavernProgressionSnapshot snapshot,
        CavernProgressionRank tier,
        Optional<CavernProgressionRank> nextTier
    ) {
        if (tier == snapshot.rank()) {
            return "current";
        }
        if (nextTier.isPresent() && nextTier.get() == tier) {
            return "next";
        }
        return tier.threshold() < snapshot.rank().threshold() ? "unlocked" : "locked";
    }

    private static SlotView summaryItem(
        String itemId,
        ChatFormatting nameColor,
        String name,
        List<String> lore
    ) {
        return new SlotView(itemId, nameColor, name, lore);
    }

    private static SlotView fillerItem() {
        return new SlotView("minecraft:gray_stained_glass_pane", ChatFormatting.DARK_GRAY, " ", List.of());
    }

    private static String formatCooldown(long millis) {
        if (millis <= 0L) {
            return "now";
        }
        long seconds = millis / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        if (hours > 0L) {
            return hours + "h " + (minutes % 60L) + "m";
        }
        if (minutes > 0L) {
            return minutes + "m " + (seconds % 60L) + "s";
        }
        return seconds + "s";
    }

    private static String capitalize(String value) {
        if (value.isEmpty()) {
            return value;
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public record Layout(
        List<SlotView> slotItems,
        Map<Integer, String> entryIdsBySlot
    ) {
        public Layout {
            slotItems = List.copyOf(Objects.requireNonNull(slotItems, "slotItems"));
            entryIdsBySlot = Map.copyOf(Objects.requireNonNull(entryIdsBySlot, "entryIdsBySlot"));
            if (slotItems.size() != SLOT_COUNT) {
                throw new IllegalArgumentException("slotItems must contain exactly " + SLOT_COUNT + " entries");
            }
        }
    }

    public record SlotView(
        String itemId,
        ChatFormatting nameColor,
        String title,
        List<String> lore
    ) {
        public SlotView {
            itemId = requireText(itemId, "itemId");
            nameColor = Objects.requireNonNull(nameColor, "nameColor");
            title = Objects.requireNonNull(title, "title");
            lore = List.copyOf(Objects.requireNonNull(lore, "lore"));
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
