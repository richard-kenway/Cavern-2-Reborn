package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionConsequences;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUnlock;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.nbt.CompoundTag;

class CavernProgressionPersistenceTest {
    @Test
    void progressionStateSurvivesRestartAndContinuesFromSavedValue() {
        UUID playerId = UUID.randomUUID();
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService firstService = serviceFor(persistentState);

        firstService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:coal_ore");
        firstService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:iron_ore");

        CavernProgressionService restartedService = serviceFor(restart(persistentState));
        CavernProgressionSnapshot snapshot = restartedService.recordMiningEvent(
            playerId,
            CavernDimensions.CAVERN_DIMENSION_ID,
            "minecraft:diamond_ore"
        ).currentSnapshot();

        assertEquals(3, snapshot.countedBlocks());
        assertEquals(8, snapshot.progressionScore());
        assertEquals(CavernProgressionRank.NOVICE, snapshot.rank());
        assertEquals(1, snapshot.minedBlocksById().get("minecraft:coal_ore"));
        assertEquals(1, snapshot.minedBlocksById().get("minecraft:iron_ore"));
        assertEquals(1, snapshot.minedBlocksById().get("minecraft:diamond_ore"));
    }

    @Test
    void sameOreOutsideCavernDoesNotAdvancePersistedProgression() {
        UUID playerId = UUID.randomUUID();
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService service = serviceFor(persistentState);

        service.recordMiningEvent(playerId, CavernDimensions.OVERWORLD_DIMENSION_ID, "minecraft:emerald_ore");

        CavernProgressionSnapshot snapshot = service.inspect(playerId);
        assertEquals(0, snapshot.countedBlocks());
        assertEquals(0, snapshot.progressionScore());
    }

    @Test
    void minerOrbBonusScoreSurvivesRestartWithoutExtraCountedBlocks() {
        UUID playerId = UUID.randomUUID();
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService firstService = serviceFor(persistentState);

        firstService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "cavernreborn:hexcite_ore", 2);

        CavernProgressionService restartedService = serviceFor(restart(persistentState));
        CavernProgressionSnapshot snapshot = restartedService.inspect(playerId);

        assertEquals(1, snapshot.countedBlocks());
        assertEquals(6, snapshot.progressionScore());
        assertEquals(1, snapshot.minedBlocksById().get("cavernreborn:hexcite_ore"));
    }

    @Test
    void unlockedConsequenceSurvivesRestartAndMatchesPlayerFacingStatus() {
        UUID playerId = UUID.randomUUID();
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService firstService = serviceFor(persistentState);

        for (int i = 0; i < 5; i++) {
            firstService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionService restartedService = serviceFor(restart(persistentState));
        CavernProgressionSnapshot snapshot = restartedService.inspect(playerId);

        assertEquals(CavernProgressionRank.APPRENTICE, snapshot.rank());
        assertTrue(snapshot.hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT));
        assertEquals(1, CavernProgressionConsequences.bonusExperienceFor(snapshot));
        assertEquals(
            "CAVERN rank for TestPlayer: apprentice | score 25 | counted ores 5 | next journeyman in 50 score | unlocked: Miner's Insight (+1 bonus XP on counted ores in CAVERN) | next unlock: Mining Assist at journeyman",
            CavernPlayerProgressionStatusFormatter.format("TestPlayer", snapshot)
        );
    }

    private static CavernProgressionService serviceFor(CavernPersistentStateData persistentState) {
        return new CavernProgressionService(
            new SavedDataBackedPlayerMiningProgressionRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerMiningProgressionRepository()
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
