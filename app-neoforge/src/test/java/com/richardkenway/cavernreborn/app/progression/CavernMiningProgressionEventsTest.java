package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernPlayerProgressionState;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.PlayerMiningProgressionStore;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernMiningProgressionEventsTest {
    @Test
    void appLayerMinerOrbBonusCanTriggerWithoutChangingCountedBlockTotals() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernMiningProgressionEvents events = new CavernMiningProgressionEvents(
            service,
            bound -> 0
        );

        events.recordMiningProgression(
            playerId,
            CavernDimensions.CAVERN_DIMENSION_ID,
            "cavernreborn:hexcite_ore",
            false,
            true,
            false
        );

        CavernProgressionSnapshot snapshot = service.inspect(playerId);
        assertEquals(1, snapshot.countedBlocks());
        assertEquals(6, snapshot.progressionScore());
        assertEquals(1, snapshot.minedBlocksById().get("cavernreborn:hexcite_ore"));
    }

    @Test
    void forcedRollMissLeavesProgressionAtBaseScore() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernMiningProgressionEvents events = new CavernMiningProgressionEvents(
            service,
            bound -> 1
        );

        events.recordMiningProgression(
            playerId,
            CavernDimensions.CAVERN_DIMENSION_ID,
            "cavernreborn:hexcite_ore",
            false,
            true,
            false
        );

        CavernProgressionSnapshot snapshot = service.inspect(playerId);
        assertEquals(1, snapshot.countedBlocks());
        assertEquals(4, snapshot.progressionScore());
        assertEquals(1, snapshot.minedBlocksById().get("cavernreborn:hexcite_ore"));
    }

    @Test
    void miningAssistSuppressedPathSkipsMinerOrbBonusEvenWhenOrbIsPresent() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService service = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernMiningProgressionEvents events = new CavernMiningProgressionEvents(
            service,
            bound -> 0
        );

        events.recordMiningProgression(
            playerId,
            CavernDimensions.CAVERN_DIMENSION_ID,
            "cavernreborn:hexcite_ore",
            false,
            true,
            true
        );

        CavernProgressionSnapshot snapshot = service.inspect(playerId);
        assertEquals(1, snapshot.countedBlocks());
        assertEquals(4, snapshot.progressionScore());
        assertEquals(1, snapshot.minedBlocksById().get("cavernreborn:hexcite_ore"));
        assertTrue(snapshot.minedBlocksById().containsKey("cavernreborn:hexcite_ore"));
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
