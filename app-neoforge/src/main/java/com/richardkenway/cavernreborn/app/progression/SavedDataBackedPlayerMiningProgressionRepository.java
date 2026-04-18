package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateAccess;
import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerProgressionState;
import com.richardkenway.cavernreborn.core.progression.PlayerMiningProgressionStore;

public final class SavedDataBackedPlayerMiningProgressionRepository implements PlayerMiningProgressionStore {
    private final Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier;
    private final PlayerMiningProgressionStore fallbackStore;

    public SavedDataBackedPlayerMiningProgressionRepository() {
        this(CavernPersistentStateAccess::resolve, new InMemoryPlayerMiningProgressionRepository());
    }

    SavedDataBackedPlayerMiningProgressionRepository(
        Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier,
        PlayerMiningProgressionStore fallbackStore
    ) {
        this.persistentStateSupplier = Objects.requireNonNull(persistentStateSupplier, "persistentStateSupplier");
        this.fallbackStore = Objects.requireNonNull(fallbackStore, "fallbackStore");
    }

    @Override
    public CavernPlayerProgressionState load(UUID playerId) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        return persistentStateSupplier.get()
            .map(state -> state.loadPlayerMiningProgression(normalizedPlayerId))
            .orElseGet(() -> fallbackStore.load(normalizedPlayerId));
    }

    @Override
    public void save(CavernPlayerProgressionState progressionState) {
        CavernPlayerProgressionState normalizedState = Objects.requireNonNull(progressionState, "progressionState");
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().savePlayerMiningProgression(normalizedState);
            return;
        }

        fallbackStore.save(normalizedState);
    }

    @Override
    public void clear(UUID playerId) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().clearPlayerMiningProgression(normalizedPlayerId);
            return;
        }

        fallbackStore.clear(normalizedPlayerId);
    }
}
