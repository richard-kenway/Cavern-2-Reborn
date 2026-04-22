package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardClaimResult;
import com.richardkenway.cavernreborn.core.progression.CavernRewardService;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.nbt.CompoundTag;

class CavernRewardPersistenceTest {
    @Test
    void claimedRewardStateSurvivesRestartAndStaysAlignedWithProgression() {
        UUID playerId = UUID.randomUUID();
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService progressionService = progressionServiceFor(persistentState);
        CavernRewardService rewardService = rewardServiceFor(persistentState);

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        CavernRewardClaimResult claimResult = rewardService.claim(snapshot, CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);
        assertTrue(claimResult.claimed());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernProgressionService restartedProgressionService = progressionServiceFor(restartedState);
        CavernRewardService restartedRewardService = rewardServiceFor(restartedState);

        CavernProgressionSnapshot restartedSnapshot = restartedProgressionService.inspect(playerId);
        CavernRewardStatus restartedStatus = restartedRewardService.inspect(restartedSnapshot).getFirst();

        assertEquals(snapshot.progressionScore(), restartedSnapshot.progressionScore());
        assertEquals(snapshot.rank(), restartedSnapshot.rank());
        assertTrue(restartedStatus.claimed());
        assertFalse(restartedStatus.availableToClaim());
        assertEquals(
            "CAVERN rank for TestPlayer: apprentice | score 25 | counted ores 5 | next journeyman in 50 score | unlocked: Miner's Insight (+1 bonus XP on counted ores in CAVERN) | next unlock: Mining Assist at journeyman",
            CavernPlayerProgressionStatusFormatter.format("TestPlayer", restartedSnapshot)
        );
        assertEquals(
            "CAVERN progression for TestPlayer: rank=apprentice, score=25, counted_blocks=5, next_rank=journeyman@75, points_to_next=50, top_blocks=diamond_ore=5",
            CavernProgressionDebugSummaryFormatter.format("TestPlayer", restartedSnapshot)
        );
        assertEquals(
            "CAVERN rewards for TestPlayer: apprentice_supply_cache [claimed: grants torch x16, bread x8]; "
                + "journeyman_supply_cache [locked: requires journeyman, current apprentice, grants torch x24, cooked_beef x8, water_bucket x1]",
            CavernPlayerRewardStatusFormatter.format("TestPlayer", restartedSnapshot, restartedRewardService.inspect(restartedSnapshot))
        );
    }

    private static CavernProgressionService progressionServiceFor(CavernPersistentStateData persistentState) {
        return new CavernProgressionService(
            new SavedDataBackedPlayerMiningProgressionRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerMiningProgressionRepository()
            )
        );
    }

    private static CavernRewardService rewardServiceFor(CavernPersistentStateData persistentState) {
        return new CavernRewardService(
            new SavedDataBackedPlayerClaimedRewardRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerClaimedRewardRepository()
            )
        );
    }

    private static CavernPersistentStateData restart(CavernPersistentStateData persistentState) {
        CompoundTag serialized = persistentState.save(new CompoundTag(), null);
        try {
            Method loadMethod = CavernPersistentStateData.class.getDeclaredMethod("load", CompoundTag.class, net.minecraft.core.HolderLookup.Provider.class);
            loadMethod.setAccessible(true);
            return (CavernPersistentStateData) loadMethod.invoke(null, serialized, null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Failed to restore persistent state snapshot", exception);
        }
    }
}
