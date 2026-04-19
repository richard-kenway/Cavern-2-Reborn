package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;

public final class CavernCatalogUseFeedbackFormatter {
    private CavernCatalogUseFeedbackFormatter() {
    }

    public static String format(CavernCatalogUseResult result) {
        CavernCatalogUseResult normalizedResult = Objects.requireNonNull(result, "result");
        String entryId = normalizedResult.entry().id();
        if (normalizedResult.granted()) {
            return "Catalog entry used: " + entryId + " (" + formatGrants(normalizedResult) + ")";
        }
        if (normalizedResult.alreadyClaimed()) {
            return "Catalog entry " + entryId + " is already claimed.";
        }
        if (normalizedResult.onCooldown()) {
            return "Catalog entry " + entryId + " is on cooldown.";
        }
        if (normalizedResult.alreadyUsed()) {
            return "Catalog entry " + entryId + " has already been used.";
        }
        return "Catalog entry " + entryId + " requires rank "
            + normalizedResult.entry().requiredRank().id()
            + ". Current rank: " + normalizedResult.progressionSnapshot().rank().id() + ".";
    }

    private static String formatGrants(CavernCatalogUseResult result) {
        return result.entry().grants().stream()
            .map(grant -> shortItemId(grant.itemId()) + " x" + grant.count())
            .collect(Collectors.joining(", "));
    }

    private static String shortItemId(String itemId) {
        int separator = itemId.indexOf(':');
        return separator >= 0 ? itemId.substring(separator + 1) : itemId;
    }
}
