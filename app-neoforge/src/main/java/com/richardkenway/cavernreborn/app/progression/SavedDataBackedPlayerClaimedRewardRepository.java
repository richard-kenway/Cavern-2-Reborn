package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateAccess;
import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerRewardState;
import com.richardkenway.cavernreborn.core.progression.PlayerClaimedRewardStore;

public final class SavedDataBackedPlayerClaimedRewardRepository implements PlayerClaimedRewardStore {
    private final Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier;
    private final PlayerClaimedRewardStore fallbackStore;

    public SavedDataBackedPlayerClaimedRewardRepository() {
        this(CavernPersistentStateAccess::resolve, new InMemoryPlayerClaimedRewardRepository());
    }

    SavedDataBackedPlayerClaimedRewardRepository(
        Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier,
        PlayerClaimedRewardStore fallbackStore
    ) {
        this.persistentStateSupplier = Objects.requireNonNull(persistentStateSupplier, "persistentStateSupplier");
        this.fallbackStore = Objects.requireNonNull(fallbackStore, "fallbackStore");
    }

    @Override
    public CavernPlayerRewardState load(UUID playerId) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        return persistentStateSupplier.get()
            .map(state -> state.loadPlayerRewardState(normalizedPlayerId))
            .orElseGet(() -> fallbackStore.load(normalizedPlayerId));
    }

    @Override
    public void save(CavernPlayerRewardState rewardState) {
        CavernPlayerRewardState normalizedState = Objects.requireNonNull(rewardState, "rewardState");
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().savePlayerRewardState(normalizedState);
            return;
        }

        fallbackStore.save(normalizedState);
    }

    @Override
    public void clear(UUID playerId) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().clearPlayerRewardState(normalizedPlayerId);
            return;
        }

        fallbackStore.clear(normalizedPlayerId);
    }
}
