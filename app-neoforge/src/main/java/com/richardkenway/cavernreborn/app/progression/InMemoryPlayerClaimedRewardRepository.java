package com.richardkenway.cavernreborn.app.progression;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.richardkenway.cavernreborn.core.progression.CavernPlayerRewardState;
import com.richardkenway.cavernreborn.core.progression.PlayerClaimedRewardStore;

public final class InMemoryPlayerClaimedRewardRepository implements PlayerClaimedRewardStore {
    private final Map<UUID, CavernPlayerRewardState> states = new LinkedHashMap<>();

    @Override
    public CavernPlayerRewardState load(UUID playerId) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        return states.getOrDefault(normalizedPlayerId, CavernPlayerRewardState.empty(normalizedPlayerId));
    }

    @Override
    public void save(CavernPlayerRewardState rewardState) {
        CavernPlayerRewardState normalizedState = Objects.requireNonNull(rewardState, "rewardState");
        if (normalizedState.isEmpty()) {
            states.remove(normalizedState.playerId());
            return;
        }
        states.put(normalizedState.playerId(), normalizedState);
    }

    @Override
    public void clear(UUID playerId) {
        states.remove(Objects.requireNonNull(playerId, "playerId"));
    }
}
