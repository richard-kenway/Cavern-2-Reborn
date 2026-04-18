package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
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
        );

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
