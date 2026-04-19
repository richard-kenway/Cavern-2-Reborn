package com.richardkenway.cavernreborn.app.progression;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateAccess;
import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState;
import com.richardkenway.cavernreborn.core.progression.PlayerServiceStateStore;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class SavedDataBackedPlayerServiceStateRepository implements PlayerServiceStateStore {
    private final Supplier<Optional<CavernPersistentStateData>> persistentStateGetter;
    private final PlayerServiceStateStore inMemoryFallback;

    public SavedDataBackedPlayerServiceStateRepository() {
        this(CavernPersistentStateAccess::resolve, new InMemoryPlayerServiceStateRepository());
    }

    SavedDataBackedPlayerServiceStateRepository(
        Supplier<Optional<CavernPersistentStateData>> persistentStateGetter,
        PlayerServiceStateStore inMemoryFallback
    ) {
        this.persistentStateGetter = persistentStateGetter;
        this.inMemoryFallback = inMemoryFallback;
    }

    @Override
    public CavernPlayerServiceState load(UUID playerId) {
        return persistentStateGetter.get()
            .map(state -> state.loadPlayerServiceState(playerId))
            .orElseGet(() -> inMemoryFallback.load(playerId));
    }

    @Override
    public void save(CavernPlayerServiceState serviceState) {
        persistentStateGetter.get().ifPresent(state -> {
            state.savePlayerServiceState(serviceState);
            state.setDirty();
        });
        inMemoryFallback.save(serviceState);
    }

    @Override
    public void clear(UUID playerId) {
        persistentStateGetter.get().ifPresent(state -> {
            state.clearPlayerServiceState(playerId);
            state.setDirty();
        });
        if (!persistentStateGetter.get().isPresent()) {
            inMemoryFallback.clear(playerId);
        }
    }
}
