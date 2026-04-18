package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerProgressionState;

class SavedDataBackedPlayerMiningProgressionRepositoryTest {
    @Test
    void usesPersistentStateWhenAvailable() {
        UUID playerId = UUID.randomUUID();
        CavernPlayerProgressionState progressionState = CavernPlayerProgressionState.empty(playerId)
            .withMinedBlock("minecraft:coal_ore", 1)
            .withMinedBlock("minecraft:iron_ore", 2);
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        SavedDataBackedPlayerMiningProgressionRepository repository = new SavedDataBackedPlayerMiningProgressionRepository(
            () -> Optional.of(persistentState),
            new InMemoryPlayerMiningProgressionRepository()
        );

        repository.save(progressionState);

        assertEquals(progressionState, persistentState.loadPlayerMiningProgression(playerId));
        assertEquals(progressionState, repository.load(playerId));

        repository.clear(playerId);

        assertEquals(CavernPlayerProgressionState.empty(playerId), persistentState.loadPlayerMiningProgression(playerId));
    }

    @Test
    void fallsBackToInMemoryStoreWhenPersistentStateIsUnavailable() {
        UUID playerId = UUID.randomUUID();
        CavernPlayerProgressionState progressionState = CavernPlayerProgressionState.empty(playerId)
            .withMinedBlock("minecraft:emerald_ore", 6);
        SavedDataBackedPlayerMiningProgressionRepository repository = new SavedDataBackedPlayerMiningProgressionRepository(
            Optional::<CavernPersistentStateData>empty,
            new InMemoryPlayerMiningProgressionRepository()
        );

        repository.save(progressionState);

        assertEquals(progressionState, repository.load(playerId));

        repository.clear(playerId);

        assertEquals(CavernPlayerProgressionState.empty(playerId), repository.load(playerId));
    }
}
