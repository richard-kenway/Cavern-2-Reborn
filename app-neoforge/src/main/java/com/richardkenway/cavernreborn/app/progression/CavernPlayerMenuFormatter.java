package com.richardkenway.cavernreborn.app.progression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntryType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class CavernPlayerMenuFormatter {
    private static final Comparator<CavernCatalogEntry> CATALOG_ENTRY_ORDER = Comparator
        .comparingInt((CavernCatalogEntry entry) -> entry.requiredRank().threshold())
        .thenComparingInt(entry -> entry.type().ordinal())
        .thenComparing(CavernCatalogEntry::id);

    private CavernPlayerMenuFormatter() {
    }

    public static List<Component> format(
        String playerName,
        CavernProgressionSnapshot snapshot,
        List<CavernCatalogEntry> catalogEntries
    ) {
        String normalizedPlayerName = requireText(playerName, "playerName");
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        List<CavernCatalogEntry> normalizedEntries = Objects.requireNonNull(catalogEntries, "catalogEntries").stream()
            .sorted(CATALOG_ENTRY_ORDER)
            .toList();
        String nextTier = nextTier(normalizedSnapshot, normalizedEntries);
        long availableCount = normalizedEntries.stream().filter(CavernCatalogEntry::availableToUse).count();

        ArrayList<Component> lines = new ArrayList<>();
        lines.add(Component.literal("=== CAVERN MENU: " + normalizedPlayerName + " ===")
            .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
        lines.add(Component.literal(
            "Rank " + normalizedSnapshot.rank().id()
                + " | score " + normalizedSnapshot.progressionScore()
                + " | available now " + availableCount
                + " | next tier " + nextTier
        ).withStyle(ChatFormatting.YELLOW));
        lines.add(Component.literal("Click CLAIM/USE or run /cavern menu use <entry>.")
            .withStyle(ChatFormatting.GRAY));
        lines.add(actionLine());

        if (normalizedEntries.isEmpty()) {
            lines.add(Component.literal("No CAVERN catalog entries are currently registered.")
                .withStyle(ChatFormatting.DARK_GRAY));
            return List.copyOf(lines);
        }

        Map<CavernProgressionRank, List<CavernCatalogEntry>> entriesByTier = normalizedEntries.stream()
            .collect(Collectors.groupingBy(
                CavernCatalogEntry::requiredRank,
                LinkedHashMap::new,
                Collectors.toList()
            ));
        for (Map.Entry<CavernProgressionRank, List<CavernCatalogEntry>> tier : entriesByTier.entrySet()) {
            lines.add(tierHeader(normalizedSnapshot, tier.getKey(), nextTier));
            tier.getValue().forEach(entry -> lines.add(entryLine(normalizedSnapshot, entry)));
        }
        return List.copyOf(lines);
    }

    private static MutableComponent actionLine() {
        return Component.literal("Actions: ")
            .withStyle(ChatFormatting.GRAY)
            .append(actionButton("Refresh", "/cavern menu"))
            .append(Component.literal(" "))
            .append(actionButton("Rank", "/cavern rank"))
            .append(Component.literal(" "))
            .append(actionButton("Rewards", "/cavern rewards"))
            .append(Component.literal(" "))
            .append(actionButton("Services", "/cavern services"))
            .append(Component.literal(" "))
            .append(actionButton("Catalog", "/cavern catalog"));
    }

    private static Component tierHeader(
        CavernProgressionSnapshot snapshot,
        CavernProgressionRank tier,
        String nextTier
    ) {
        return Component.literal("-- " + tier.id() + " tier [" + tierState(snapshot, tier, nextTier) + "] --")
            .withStyle(ChatFormatting.AQUA);
    }

    private static String tierState(
        CavernProgressionSnapshot snapshot,
        CavernProgressionRank tier,
        String nextTier
    ) {
        if (tier == snapshot.rank()) {
            return "current";
        }
        if (tier.id().equals(nextTier)) {
            return "next";
        }
        return tier.threshold() < snapshot.rank().threshold() ? "unlocked" : "locked";
    }

    private static Component entryLine(CavernProgressionSnapshot snapshot, CavernCatalogEntry entry) {
        MutableComponent line = Component.empty();
        line.append(statusLead(entry));
        line.append(Component.literal(" " + entry.label() + " | " + typeLabel(entry) + " | "));
        if (entry.claimed()) {
            line.append(Component.literal(entry.repeatable() ? "last use complete" : "already claimed"));
        } else if (entry.onCooldown()) {
            line.append(Component.literal("ready in " + formatCooldown(entry.cooldownRemainingMillis())));
        } else if (entry.availableToUse()) {
            line.append(Component.literal("available now"));
        } else {
            line.append(Component.literal("requires " + entry.requiredRank().id() + " | current " + snapshot.rank().id()));
        }
        line.append(Component.literal(" | grants " + formatGrants(entry)));
        return line;
    }

    private static Component statusLead(CavernCatalogEntry entry) {
        if (entry.availableToUse()) {
            String actionLabel = entry.type() == CavernCatalogEntryType.REWARD ? "CLAIM" : "USE";
            return actionButton(actionLabel, "/cavern menu use " + entry.id());
        }
        if (entry.onCooldown()) {
            return statusBadge("COOLDOWN", ChatFormatting.YELLOW);
        }
        if (entry.claimed()) {
            return statusBadge("CLAIMED", ChatFormatting.DARK_GRAY);
        }
        return statusBadge("LOCKED", ChatFormatting.RED);
    }

    private static String typeLabel(CavernCatalogEntry entry) {
        return entry.type() == CavernCatalogEntryType.REWARD ? "reward" : "service";
    }

    private static String formatGrants(CavernCatalogEntry entry) {
        return entry.grants().stream()
            .map(grant -> shortItemId(grant.itemId()) + " x" + grant.count())
            .collect(Collectors.joining(", "));
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

    private static String nextTier(CavernProgressionSnapshot snapshot, List<CavernCatalogEntry> entries) {
        return entries.stream()
            .map(CavernCatalogEntry::requiredRank)
            .filter(rank -> rank.ordinal() > snapshot.rank().ordinal())
            .distinct()
            .min(Comparator.comparingInt(CavernProgressionRank::threshold))
            .map(CavernProgressionRank::id)
            .orElse("none");
    }

    private static MutableComponent actionButton(String label, String command) {
        return statusBadge(label, ChatFormatting.GREEN)
            .withStyle(style -> style
                .withUnderlined(true)
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)));
    }

    private static MutableComponent statusBadge(String text, ChatFormatting color) {
        return Component.literal("[" + text + "]").withStyle(color);
    }

    private static String shortItemId(String itemId) {
        String normalizedItemId = requireText(itemId, "itemId");
        int separator = normalizedItemId.indexOf(':');
        return separator >= 0 ? normalizedItemId.substring(separator + 1) : normalizedItemId;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
