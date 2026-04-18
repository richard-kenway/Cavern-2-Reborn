package com.richardkenway.cavernreborn.core.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        CavernProgressionUpdateResult update = service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:iron_ore");
        CavernProgressionSnapshot snapshot = update.currentSnapshot();

        assertTrue(update.counted());
        assertFalse(update.rankAdvanced());
        assertEquals(2, update.scoreDelta());
        assertEquals(1, snapshot.countedBlocks());
        assertEquals(2, snapshot.progressionScore());
        assertEquals(CavernProgressionRank.NOVICE, snapshot.rank());
        assertEquals(1, snapshot.minedBlocksById().get("minecraft:iron_ore"));
    }

    @Test
    void miningOutsideCavernDoesNotAffectCavernProgression() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionUpdateResult update = service.recordMiningEvent(playerId, CavernDimensions.OVERWORLD_DIMENSION_ID, "minecraft:iron_ore");
        CavernProgressionSnapshot snapshot = update.currentSnapshot();

        assertFalse(update.counted());
        assertFalse(update.rankAdvanced());
        assertEquals(0, update.scoreDelta());
        assertEquals(0, snapshot.countedBlocks());
        assertEquals(0, snapshot.progressionScore());
        assertEquals(Map.of(), snapshot.minedBlocksById());
    }

    @Test
    void unsupportedBlocksAreIgnoredInsideCavern() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionUpdateResult update = service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:stone");
        CavernProgressionSnapshot snapshot = update.currentSnapshot();

        assertFalse(update.counted());
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
        assertFalse(snapshotB.hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT));
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
        CavernProgressionUpdateResult singleEvent = service.recordMiningEvent(
            UUID.randomUUID(),
            CavernDimensions.CAVERN_DIMENSION_ID,
            "minecraft:raw_iron_block"
        );

        assertTrue(singleEvent.counted());
        assertFalse(singleEvent.rankAdvanced());
        assertEquals(1, singleEvent.currentSnapshot().countedBlocks());
        assertEquals(6, singleEvent.currentSnapshot().progressionScore());
        assertEquals(CavernProgressionRank.NOVICE, singleEvent.currentSnapshot().rank());
    }

    @Test
    void rankUpsAndUnlocksAreReportedExactlyOnceOnThresholdCrossing() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        CavernProgressionUpdateResult thresholdCrossing = service.recordMiningEvent(
            playerId,
            CavernDimensions.CAVERN_DIMENSION_ID,
            "minecraft:diamond_ore"
        );

        assertTrue(thresholdCrossing.counted());
        assertTrue(thresholdCrossing.rankAdvanced());
        assertTrue(thresholdCrossing.unlockJustReached(CavernProgressionUnlock.MINERS_INSIGHT));
        assertEquals(CavernProgressionRank.APPRENTICE, thresholdCrossing.currentSnapshot().rank());
        assertTrue(thresholdCrossing.currentSnapshot().hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT));

        CavernProgressionUpdateResult followUp = service.recordMiningEvent(
            playerId,
            CavernDimensions.CAVERN_DIMENSION_ID,
            "minecraft:coal_ore"
        );

        assertTrue(followUp.counted());
        assertFalse(followUp.rankAdvanced());
        assertFalse(followUp.unlockJustReached(CavernProgressionUnlock.MINERS_INSIGHT));
    }

    @Test
    void consequenceUnlockDependsOnDerivedRank() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());

        CavernProgressionSnapshot novice = service.inspect(playerId);
        assertEquals(0, CavernProgressionConsequences.bonusExperienceFor(novice));

        for (int i = 0; i < 5; i++) {
            service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot apprentice = service.inspect(playerId);
        assertEquals(CavernProgressionRank.APPRENTICE, apprentice.rank());
        assertEquals(1, CavernProgressionConsequences.bonusExperienceFor(apprentice));
    }

    private static CavernProgressionSnapshot applySequence(UUID playerId, List<String> miningSequence) {
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernProgressionSnapshot snapshot = service.inspect(playerId);
        for (String blockId : miningSequence) {
            snapshot = service.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, blockId).currentSnapshot();
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
