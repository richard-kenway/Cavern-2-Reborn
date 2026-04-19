package com.richardkenway.cavernreborn.core.progression;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record CavernPlayerServiceState(UUID playerId, Map<String, Long> lastUsedServiceTimestamps) {
    public CavernPlayerServiceState {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(lastUsedServiceTimestamps, "lastUsedServiceTimestamps");
        lastUsedServiceTimestamps = Collections.unmodifiableMap(new LinkedHashMap<>(lastUsedServiceTimestamps));
    }

    public static CavernPlayerServiceState empty(UUID playerId) {
        return new CavernPlayerServiceState(playerId, Map.of());
    }

    public boolean isEmpty() {
        return lastUsedServiceTimestamps.isEmpty();
    }

    public long lastUsedTimestamp(CavernServiceEntry service) {
        Objects.requireNonNull(service, "service");
        return lastUsedServiceTimestamps.getOrDefault(service.id(), 0L);
    }

    public boolean isOnCooldown(CavernServiceEntry service, long currentTimeMillis) {
        if (service.oneTime()) {
            return lastUsedServiceTimestamps.containsKey(service.id());
        }
        if (!lastUsedServiceTimestamps.containsKey(service.id())) {
            return false;
        }
        long lastUsed = lastUsedTimestamp(service);
        long elapsed = currentTimeMillis - lastUsed;
        return elapsed < service.cooldownMillis();
    }

    public CavernPlayerServiceState withServiceUsed(CavernServiceEntry service, long usedAtMillis) {
        Objects.requireNonNull(service, "service");
        if (usedAtMillis < 0) {
            throw new IllegalArgumentException("usedAtMillis must not be negative");
        }
        LinkedHashMap<String, Long> updated = new LinkedHashMap<>(lastUsedServiceTimestamps);
        updated.put(service.id(), usedAtMillis);
        return new CavernPlayerServiceState(playerId, updated);
    }
}
