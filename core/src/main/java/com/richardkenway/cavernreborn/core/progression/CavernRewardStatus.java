package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public record CavernRewardStatus(CavernProgressionReward reward, CavernRewardAvailability availability) {
    public CavernRewardStatus {
        reward = Objects.requireNonNull(reward, "reward");
        availability = Objects.requireNonNull(availability, "availability");
    }

    public boolean availableToClaim() {
        return availability == CavernRewardAvailability.AVAILABLE;
    }

    public boolean claimed() {
        return availability == CavernRewardAvailability.CLAIMED;
    }

    public boolean locked() {
        return availability == CavernRewardAvailability.LOCKED;
    }
}
