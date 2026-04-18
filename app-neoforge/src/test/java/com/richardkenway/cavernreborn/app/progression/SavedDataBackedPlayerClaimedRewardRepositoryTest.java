package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerRewardState;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;

class SavedDataBackedPlayerClaimedRewardRepositoryTest {
    @Test
    void usesPersistentStateWhenAvailable() {
        UUID playerId = UUID.randomUUID();
        CavernPlayerRewardState rewardState = CavernPlayerRewardState.empty(playerId)
            .withClaimed(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        SavedDataBackedPlayerClaimedRewardRepository repository = new SavedDataBackedPlayerClaimedRewardRepository(
            () -> Optional.of(persistentState),
            new InMemoryPlayerClaimedRewardRepository()
        );

        repository.save(rewardState);

        assertEquals(rewardState, persistentState.loadPlayerRewardState(playerId));
        assertEquals(rewardState, repository.load(playerId));

        repository.clear(playerId);

        assertEquals(CavernPlayerRewardState.empty(playerId), persistentState.loadPlayerRewardState(playerId));
    }

    @Test
    void fallsBackToInMemoryStoreWhenPersistentStateIsUnavailable() {
        UUID playerId = UUID.randomUUID();
        CavernPlayerRewardState rewardState = CavernPlayerRewardState.empty(playerId)
            .withClaimed(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);
        SavedDataBackedPlayerClaimedRewardRepository repository = new SavedDataBackedPlayerClaimedRewardRepository(
            Optional::<CavernPersistentStateData>empty,
            new InMemoryPlayerClaimedRewardRepository()
        );

        repository.save(rewardState);

        assertEquals(rewardState, repository.load(playerId));

        repository.clear(playerId);

        assertEquals(CavernPlayerRewardState.empty(playerId), repository.load(playerId));
    }
}
