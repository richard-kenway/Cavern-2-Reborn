package com.richardkenway.cavernreborn.core.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernProgressionServiceTest {
    @Test
    void newPlayerStartsWithEmptyProgressionState() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionSnapshot snapshot = service.inspect(playerId);

        assertEquals(playerId, snapshot.playerId());
        assertEquals(0, snapshot.countedBlocks());
        assertEquals(0, snapshot.progressionScore());
        assertEquals(CavernProgressionRank.NOVICE, snapshot.rank());
        assertEquals(Map.of(), snapshot.minedBlocksById());
    }

    @Test
    void cavernMiningUpdatesPlayerProgression() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionSnapshot snapshot = service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:iron_ore");

        assertEquals(1, snapshot.countedBlocks());
        assertEquals(2, snapshot.progressionScore());
        assertEquals(CavernProgressionRank.NOVICE, snapshot.rank());
        assertEquals(1, snapshot.minedBlocksById().get("minecraft:iron_ore"));
    }

    @Test
    void miningOutsideCavernDoesNotAffectCavernProgression() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionSnapshot snapshot = service.recordMiningEvent(playerId, CavernDimensions.OVERWORLD_DIMENSION_ID, "minecraft:iron_ore");

        assertEquals(0, snapshot.countedBlocks());
        assertEquals(0, snapshot.progressionScore());
        assertEquals(Map.of(), snapshot.minedBlocksById());
    }

    @Test
    void unsupportedBlocksAreIgnoredInsideCavern() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionSnapshot snapshot = service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:stone");

        assertEquals(0, snapshot.countedBlocks());
        assertEquals(0, snapshot.progressionScore());
    }

    @Test
    void differentPlayersStayIsolated() {
        UUID playerA = UUID.randomUUID();
        UUID playerB = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        service.recordMiningEvent(playerA, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:coal_ore");
        service.recordMiningEvent(playerA, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        service.recordMiningEvent(playerB, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:emerald_ore");

        CavernProgressionSnapshot snapshotA = service.inspect(playerA);
        CavernProgressionSnapshot snapshotB = service.inspect(playerB);

        assertEquals(2, snapshotA.countedBlocks());
        assertEquals(6, snapshotA.progressionScore());
        assertEquals(Map.of("minecraft:coal_ore", 1, "minecraft:diamond_ore", 1), snapshotA.minedBlocksById());

        assertEquals(1, snapshotB.countedBlocks());
        assertEquals(6, snapshotB.progressionScore());
        assertEquals(Map.of("minecraft:emerald_ore", 1), snapshotB.minedBlocksById());
    }

    @Test
    void rankEvaluationIsDeterministicAndSingleEventJumpsStayBounded() {
        List<String> miningSequence = List.of(
            "minecraft:coal_ore",
            "minecraft:iron_ore",
            "minecraft:diamond_ore",
            "minecraft:emerald_ore",
            "minecraft:raw_iron_block",
            "minecraft:deepslate_gold_ore",
            "minecraft:deepslate_iron_ore",
            "minecraft:deepslate_diamond_ore"
        );

        CavernProgressionSnapshot firstRun = applySequence(UUID.randomUUID(), miningSequence);
        CavernProgressionSnapshot secondRun = applySequence(firstRun.playerId(), miningSequence);

        assertEquals(firstRun, secondRun);
        assertEquals(8, firstRun.countedBlocks());
        assertEquals(30, firstRun.progressionScore());
        assertEquals(CavernProgressionRank.APPRENTICE, firstRun.rank());

        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernProgressionSnapshot singleEvent = service.recordMiningEvent(
            UUID.randomUUID(),
            CavernDimensions.CAVERN_DIMENSION_ID,
            "minecraft:raw_iron_block"
        );

        assertEquals(1, singleEvent.countedBlocks());
        assertEquals(6, singleEvent.progressionScore());
        assertEquals(CavernProgressionRank.NOVICE, singleEvent.rank());
    }

    private static CavernProgressionSnapshot applySequence(UUID playerId, List<String> miningSequence) {
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernProgressionSnapshot snapshot = service.inspect(playerId);
        for (String blockId : miningSequence) {
            snapshot = service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, blockId);
        }
        return snapshot;
    }

    private static final class TestPlayerMiningProgressionStore implements PlayerMiningProgressionStore {
        private final Map<UUID, CavernPlayerProgressionState> states = new LinkedHashMap<>();

        @Override
        public CavernPlayerProgressionState load(UUID playerId) {
            return states.getOrDefault(playerId, CavernPlayerProgressionState.empty(playerId));
        }

        @Override
        public void save(CavernPlayerProgressionState progressionState) {
            if (progressionState.isEmpty()) {
                states.remove(progressionState.playerId());
                return;
            }
            states.put(progressionState.playerId(), progressionState);
        }

        @Override
        public void clear(UUID playerId) {
            states.remove(playerId);
        }
    }
}
