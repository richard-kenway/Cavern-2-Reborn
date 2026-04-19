package com.richardkenway.cavernreborn.app.progression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntryType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

public final class CavernPlayerCatalogStatusFormatter {
    private CavernPlayerCatalogStatusFormatter() {
    }

    public static String format(
        String playerName,
        CavernProgressionSnapshot snapshot,
        List<CavernCatalogEntry> catalogEntries
    ) {
        String normalizedPlayerName = requireText(playerName, "playerName");
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        List<CavernCatalogEntry> normalizedEntries = List.copyOf(Objects.requireNonNull(catalogEntries, "catalogEntries"));
        String entrySummary = normalizedEntries.isEmpty()
            ? "none"
            : normalizedEntries.stream()
                .map(entry -> formatEntry(normalizedSnapshot, entry))
                .collect(Collectors.joining("; "));

        return "CAVERN catalog for " + normalizedPlayerName
            + ": rank=" + normalizedSnapshot.rank().id()
            + ", entries=" + entrySummary;
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
