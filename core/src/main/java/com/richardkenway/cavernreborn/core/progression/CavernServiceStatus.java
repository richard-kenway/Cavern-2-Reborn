package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public record CavernServiceStatus(CavernServiceEntry service, CavernServiceAvailability availability) {
    public CavernServiceStatus {
        service = Objects.requireNonNull(service, "service");
        availability = Objects.requireNonNull(availability, "availability");
    }

    public boolean availableToUse() {
        return availability == CavernServiceAvailability.AVAILABLE;
    }

    public boolean onCooldown() {
        return availability == CavernServiceAvailability.ON_COOLDOWN;
    }

    public boolean used() {
        return availability == CavernServiceAvailability.USED;
    }

    public boolean locked() {
        return availability == CavernServiceAvailability.LOCKED;
    }
}
