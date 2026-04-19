package com.richardkenway.cavernreborn.app.progression;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntryType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

public final class CavernPlayerCatalogStatusFormatter {
    private static final Comparator<CavernCatalogEntry> CATALOG_ENTRY_ORDER = Comparator
        .comparingInt((CavernCatalogEntry entry) -> entry.requiredRank().threshold())
        .thenComparingInt(entry -> entry.type().ordinal())
        .thenComparing(CavernCatalogEntry::id);

    private CavernPlayerCatalogStatusFormatter() {
    }

    public static String format(
        String playerName,
        CavernProgressionSnapshot snapshot,
        List<CavernCatalogEntry> catalogEntries
    ) {
        String normalizedPlayerName = requireText(playerName, "playerName");
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        List<CavernCatalogEntry> normalizedEntries = Objects.requireNonNull(catalogEntries, "catalogEntries").stream()
            .sorted(CATALOG_ENTRY_ORDER)
            .toList();
        long availableCount = normalizedEntries.stream().filter(CavernCatalogEntry::availableToUse).count();
        String nextTier = nextTier(normalizedSnapshot, normalizedEntries);
        String entrySummary = normalizedEntries.isEmpty()
            ? "none"
            : normalizedEntries.stream()
                .collect(Collectors.groupingBy(
                    CavernCatalogEntry::requiredRank,
                    java.util.LinkedHashMap::new,
                    Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().id() + " -> " + entry.getValue().stream()
                    .map(value -> formatEntry(normalizedSnapshot, value))
                    .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("; "));

        return "CAVERN catalog for " + normalizedPlayerName
            + ": rank=" + normalizedSnapshot.rank().id()
            + ", available=" + availableCount
            + ", next=" + nextTier
            + ", tiers=" + entrySummary;
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

    private static String formatEntry(CavernProgressionSnapshot snapshot, CavernCatalogEntry entry) {
        String grants = formatGrants(entry);
        String type = entry.type() == CavernCatalogEntryType.REWARD ? "reward" : "service";
        if (entry.claimed()) {
            String claimedText = entry.repeatable() ? "last use complete" : "already claimed";
            return entry.id() + " [" + type + ", claimed: " + claimedText + ", grants " + grants + "]";
        }
        if (entry.onCooldown()) {
            return entry.id() + " [" + type + ", on cooldown: ready in " + formatCooldown(entry.cooldownRemainingMillis())
                + ", grants " + grants + "]";
        }
        if (entry.availableToUse()) {
            return entry.id() + " [" + type + ", available: use /cavern use " + entry.id() + ", grants " + grants + "]";
        }
        return entry.id() + " [" + type + ", locked: requires " + entry.requiredRank().id()
            + ", current " + snapshot.rank().id()
            + ", grants " + grants + "]";
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
