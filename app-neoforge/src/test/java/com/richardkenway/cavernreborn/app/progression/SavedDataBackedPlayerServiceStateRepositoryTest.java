package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState;
import com.richardkenway.cavernreborn.core.progression.CavernServiceEntry;

class SavedDataBackedPlayerServiceStateRepositoryTest {
    @Test
    void usesPersistentStateWhenAvailable() {
        UUID playerId = UUID.randomUUID();
        CavernPlayerServiceState serviceState = CavernPlayerServiceState.empty(playerId)
            .withServiceUsed(CavernServiceEntry.TORCH_SUPPLY, 1000L);
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        SavedDataBackedPlayerServiceStateRepository repository = new SavedDataBackedPlayerServiceStateRepository(
            () -> Optional.of(persistentState),
            new InMemoryPlayerServiceStateRepository()
        );

        repository.save(serviceState);

        assertEquals(serviceState, persistentState.loadPlayerServiceState(playerId));
        assertEquals(serviceState, repository.load(playerId));

        repository.clear(playerId);

        assertEquals(CavernPlayerServiceState.empty(playerId), persistentState.loadPlayerServiceState(playerId));
    }

    @Test
    void fallsBackToInMemoryStoreWhenPersistentStateIsUnavailable() {
        UUID playerId = UUID.randomUUID();
        CavernPlayerServiceState serviceState = CavernPlayerServiceState.empty(playerId)
            .withServiceUsed(CavernServiceEntry.TORCH_SUPPLY, 2000L);
        SavedDataBackedPlayerServiceStateRepository repository = new SavedDataBackedPlayerServiceStateRepository(
            Optional::<CavernPersistentStateData>empty,
            new InMemoryPlayerServiceStateRepository()
        );

        repository.save(serviceState);

        assertEquals(serviceState, repository.load(playerId));

        repository.clear(playerId);

        assertEquals(CavernPlayerServiceState.empty(playerId), repository.load(playerId));
    }
}
