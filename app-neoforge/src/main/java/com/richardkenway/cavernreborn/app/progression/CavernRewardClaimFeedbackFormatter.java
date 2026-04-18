package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernRewardClaimResult;

public final class CavernRewardClaimFeedbackFormatter {
    private CavernRewardClaimFeedbackFormatter() {
    }

    public static String format(CavernRewardClaimResult result) {
        CavernRewardClaimResult normalizedResult = Objects.requireNonNull(result, "result");
        String rewardId = normalizedResult.reward().id();
        if (normalizedResult.claimed()) {
            return "Claimed CAVERN reward " + rewardId + ": " + formatGrants(normalizedResult);
        }
        if (normalizedResult.alreadyClaimed()) {
            return "CAVERN reward " + rewardId + " is already claimed.";
        }
        return "CAVERN reward " + rewardId + " requires rank "
            + normalizedResult.reward().requiredRank().id()
            + ". Current rank: " + normalizedResult.progressionSnapshot().rank().id() + ".";
    }

    private static String formatGrants(CavernRewardClaimResult result) {
        return result.reward().grants().stream()
            .map(grant -> shortItemId(grant.itemId()) + " x" + grant.count())
            .collect(Collectors.joining(", "));
    }

    private static String shortItemId(String itemId) {
        int separator = itemId.indexOf(':');
        return separator >= 0 ? itemId.substring(separator + 1) : itemId;
    }
}
