package com.richardkenway.cavernreborn.app.state;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

public final class SavedDataBackedWorldPortalIndexRepository implements WorldPortalIndexStore {
    private final Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier;
    private final WorldPortalIndexStore fallbackStore;

    public SavedDataBackedWorldPortalIndexRepository() {
        this(CavernPersistentStateAccess::resolve, new InMemoryWorldPortalIndexRepository());
    }

    SavedDataBackedWorldPortalIndexRepository(
        Supplier<Optional<CavernPersistentStateData>> persistentStateSupplier,
        WorldPortalIndexStore fallbackStore
    ) {
        this.persistentStateSupplier = Objects.requireNonNull(persistentStateSupplier, "persistentStateSupplier");
        this.fallbackStore = Objects.requireNonNull(fallbackStore, "fallbackStore");
    }

    @Override
    public PortalWorldIndex load(String worldKey) {
        return persistentStateSupplier.get()
            .map(state -> state.loadWorldPortalIndex(worldKey))
            .orElseGet(() -> fallbackStore.load(worldKey));
    }

    @Override
    public void save(String worldKey, PortalWorldIndex worldIndex) {
        Optional<CavernPersistentStateData> persistentState = persistentStateSupplier.get();
        if (persistentState.isPresent()) {
            persistentState.get().saveWorldPortalIndex(worldKey, worldIndex);
            return;
        }

        fallbackStore.save(worldKey, worldIndex);
    }
}
