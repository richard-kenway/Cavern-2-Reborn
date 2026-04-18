package com.richardkenway.cavernreborn.app.progression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;

public final class CavernPlayerRewardStatusFormatter {
    private CavernPlayerRewardStatusFormatter() {
    }

    public static String format(
        String playerName,
        CavernProgressionSnapshot snapshot,
        List<CavernRewardStatus> rewardStatuses
    ) {
        String normalizedPlayerName = requireText(playerName, "playerName");
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        List<CavernRewardStatus> normalizedStatuses = List.copyOf(Objects.requireNonNull(rewardStatuses, "rewardStatuses"));
        String rewardSummary = normalizedStatuses.isEmpty()
            ? "none"
            : normalizedStatuses.stream()
                .map(status -> formatStatus(normalizedSnapshot, status))
                .collect(Collectors.joining("; "));

        return "CAVERN rewards for " + normalizedPlayerName + ": " + rewardSummary;
    }

    private static String formatStatus(CavernProgressionSnapshot snapshot, CavernRewardStatus status) {
        String grants = formatGrants(status);
        if (status.claimed()) {
            return status.reward().id() + " [claimed: grants " + grants + "]";
        }
        if (status.availableToClaim()) {
            return status.reward().id() + " [available: claim with /cavern claim " + status.reward().id()
                + ", grants " + grants + "]";
        }
        return status.reward().id() + " [locked: requires " + status.reward().requiredRank().id()
            + ", current " + snapshot.rank().id()
            + ", grants " + grants + "]";
    }

    private static String formatGrants(CavernRewardStatus status) {
        return status.reward().grants().stream()
            .map(grant -> shortItemId(grant.itemId()) + " x" + grant.count())
            .collect(Collectors.joining(", "));
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
