package com.richardkenway.cavernreborn.core.progression;

import java.util.List;
import java.util.Objects;

public record CavernCatalogEntry(
    CavernCatalogEntryType type,
    String id,
    String label,
    CavernProgressionRank requiredRank,
    CavernCatalogAvailability availability,
    boolean repeatable,
    List<CavernRewardGrant> grants,
    long cooldownRemainingMillis
) {
    public CavernCatalogEntry {
        type = Objects.requireNonNull(type, "type");
        id = requireText(id, "id");
        label = requireText(label, "label");
        requiredRank = Objects.requireNonNull(requiredRank, "requiredRank");
        availability = Objects.requireNonNull(availability, "availability");
        grants = List.copyOf(Objects.requireNonNull(grants, "grants"));
        if (grants.isEmpty()) {
            throw new IllegalArgumentException("grants must not be empty");
        }
        if (cooldownRemainingMillis < 0L) {
            throw new IllegalArgumentException("cooldownRemainingMillis must not be negative");
        }
    }

    public boolean availableToUse() {
        return availability == CavernCatalogAvailability.AVAILABLE;
    }

    public boolean locked() {
        return availability == CavernCatalogAvailability.LOCKED;
    }

    public boolean claimed() {
        return availability == CavernCatalogAvailability.CLAIMED;
    }

    public boolean onCooldown() {
        return availability == CavernCatalogAvailability.ON_COOLDOWN;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
