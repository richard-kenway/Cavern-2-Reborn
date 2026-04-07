package com.richardkenway.cavernreborn.app.state;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.core.state.PlayerReturnStateStore;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;

public final class SavedDataBackedPlayerReturnStateRepository implements PlayerReturnStateStore {
    private final Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier;
    private final PlayerReturnStateStore fallbackStore;

    public SavedDataBackedPlayerReturnStateRepository() {
        this(CavernPersistentStateAccess::resolve, new InMemoryPlayerReturnStateRepository());
    }

    SavedDataBackedPlayerReturnStateRepository(
        Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier,
        PlayerReturnStateStore fallbackStore
    ) {
        this.persistentStateSupplier = Objects.requireNonNull(persistentStateSupplier, "persistentStateSupplier");
        this.fallbackStore = Objects.requireNonNull(fallbackStore, "fallbackStore");
    }

    @Override
    public Optional<PortalReturnState> load(UUID playerId) {
        return persistentStateSupplier.get()
            .map(state -> state.loadPlayerReturnState(playerId))
            .orElseGet(() -> fallbackStore.load(playerId));
    }

    @Override
    public void save(UUID playerId, PortalReturnState returnState) {
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().savePlayerReturnState(playerId, returnState);
            return;
        }

        fallbackStore.save(playerId, returnState);
    }

    @Override
    public void clear(UUID playerId) {
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().clearPlayerReturnState(playerId);
            return;
        }

        fallbackStore.clear(playerId);
    }
}
