package com.richardkenway.cavernreborn.app.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalReturnState;

class SavedDataBackedPlayerReturnStateRepositoryTest {
    @Test
    void usesPersistentStateWhenAvailable() {
        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("portal", "minecraft:overworld", 1, 2, 3);
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        SavedDataBackedPlayerReturnStateRepository repository = new SavedDataBackedPlayerReturnStateRepository(
            () -> Optional.of(persistentState),
            new InMemoryPlayerReturnStateRepository()
        );

        repository.save(playerId, returnState);

        assertEquals(returnState, persistentState.loadPlayerReturnState(playerId).orElseThrow());
        assertEquals(returnState, repository.load(playerId).orElseThrow());

        repository.clear(playerId);

        assertTrue(persistentState.loadPlayerReturnState(playerId).isEmpty());
    }

    @Test
    void fallsBackToInMemoryStoreWhenPersistentStateIsUnavailable() {
        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("portal", "minecraft:overworld", 1, 2, 3);
        SavedDataBackedPlayerReturnStateRepository repository = new SavedDataBackedPlayerReturnStateRepository(
            Optional::<CavernPersistentStateData>empty,
            new InMemoryPlayerReturnStateRepository()
        );

        repository.save(playerId, returnState);

        assertEquals(returnState, repository.load(playerId).orElseThrow());

        repository.clear(playerId);

        assertFalse(repository.load(playerId).isPresent());
    }
}
