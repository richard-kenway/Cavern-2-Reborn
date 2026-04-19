package com.richardkenway.cavernreborn.app.progression;

import com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernServiceAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernServiceEntry;
import com.richardkenway.cavernreborn.core.progression.CavernServiceStatus;

import java.util.List;

public final class CavernPlayerServiceStatusFormatter {
    public static String format(
        String playerName,
        CavernProgressionSnapshot snapshot,
        List<CavernServiceStatus> serviceStatuses,
        CavernPlayerServiceState serviceState,
        long currentTimeMillis
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(playerName).append(" | Services\n");
        sb.append("Rank: ").append(snapshot.rank().id()).append(" (").append(snapshot.progressionScore()).append(" pts)\n");
        for (CavernServiceStatus status : serviceStatuses) {
            sb.append(formatEntry(snapshot, status, serviceState, currentTimeMillis)).append("\n");
        }
        return sb.toString().trim();
    }

    private static String formatEntry(
        CavernProgressionSnapshot snapshot,
        CavernServiceStatus status,
        CavernPlayerServiceState serviceState,
        long currentTimeMillis
    ) {
        CavernServiceEntry entry = status.service();
        String label = entry.label();
        String required = entry.requiredRank().id();

        switch (status.availability()) {
            case LOCKED -> {
                return String.format("[%s] %s (unlocks at %s)", "LOCKED", label, required);
            }
            case ON_COOLDOWN -> {
                long remaining = computeRemainingCooldown(serviceState, entry, currentTimeMillis);
                String timeStr = formatCooldown(remaining);
                return String.format("[%s] %s (%s)", "COOLDOWN", label, timeStr);
            }
            case USED -> {
                return String.format("[%s] %s", "CLAIMED", label);
            }
            case AVAILABLE -> {
                return String.format("[%s] %s", "READY", label);
            }
            default -> {
                return String.format("[?] %s", label);
            }
        }
    }

    private static long computeRemainingCooldown(CavernPlayerServiceState serviceState, CavernServiceEntry entry, long currentTimeMillis) {
        if (entry.oneTime()) {
            return 0L;
        }
        long lastUsed = serviceState.lastUsedTimestamp(entry);
        if (lastUsed == 0L) {
            return 0L;
        }
        long elapsed = currentTimeMillis - lastUsed;
        long remaining = entry.cooldownMillis() - elapsed;
        return Math.max(0L, remaining);
    }

    private static String formatCooldown(long millis) {
        if (millis <= 0) {
            return "ready";
        }
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        if (hours > 0) {
            return String.format("%dh %dm", hours, minutes % 60);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds % 60);
        } else {
            return String.format("%ds", seconds);
        }
    }
}
