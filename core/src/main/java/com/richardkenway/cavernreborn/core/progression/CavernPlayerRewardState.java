package com.richardkenway.cavernreborn.core.progression;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record CavernPlayerRewardState(UUID playerId, Set<String> claimedRewardIds) {
    public CavernPlayerRewardState {
        Objects.requireNonNull(playerId, "playerId");
        LinkedHashSet<String> normalizedRewardIds = new LinkedHashSet<>();
        Objects.requireNonNull(claimedRewardIds, "claimedRewardIds").forEach(rewardId ->
            normalizedRewardIds.add(requireText(rewardId, "rewardId"))
        );
        claimedRewardIds = Collections.unmodifiableSet(normalizedRewardIds);
    }

    public static CavernPlayerRewardState empty(UUID playerId) {
        return new CavernPlayerRewardState(playerId, Set.of());
    }

    public boolean isEmpty() {
        return claimedRewardIds.isEmpty();
    }

    public boolean hasClaimed(CavernProgressionReward reward) {
        return claimedRewardIds.contains(Objects.requireNonNull(reward, "reward").id());
    }

    public CavernPlayerRewardState withClaimed(CavernProgressionReward reward) {
        CavernProgressionReward normalizedReward = Objects.requireNonNull(reward, "reward");
        if (hasClaimed(normalizedReward)) {
            return this;
        }

        LinkedHashSet<String> updatedRewardIds = new LinkedHashSet<>(claimedRewardIds);
        updatedRewardIds.add(normalizedReward.id());
        return new CavernPlayerRewardState(playerId, updatedRewardIds);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
