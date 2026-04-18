package com.richardkenway.cavernreborn.core.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernRewardServiceTest {
    @Test
    void newPlayerBelowThresholdSeesLockedReward() {
        UUID playerId = UUID.randomUUID();
        CavernRewardService rewardService = new CavernRewardService(new TestPlayerClaimedRewardStore());

        CavernRewardStatus status = rewardService.inspect(emptySnapshot(playerId)).getFirst();

        assertEquals(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE, status.reward());
        assertTrue(status.locked());
        assertFalse(status.availableToClaim());
    }

    @Test
    void rewardBecomesAvailableAtRequiredRank() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernRewardService rewardService = new CavernRewardService(new TestPlayerClaimedRewardStore());

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernRewardStatus status = rewardService.inspect(progressionService.inspect(playerId)).getFirst();

        assertEquals(CavernProgressionRank.APPRENTICE, progressionService.inspect(playerId).rank());
        assertTrue(status.availableToClaim());
        assertFalse(status.claimed());
    }

    @Test
    void oneTimeRewardCanBeClaimedOnlyOnce() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        TestPlayerClaimedRewardStore claimedRewardStore = new TestPlayerClaimedRewardStore();
        CavernRewardService rewardService = new CavernRewardService(claimedRewardStore);

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        CavernRewardClaimResult firstClaim = rewardService.claim(snapshot, CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);
        CavernRewardClaimResult secondClaim = rewardService.claim(snapshot, CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);

        assertTrue(firstClaim.claimed());
        assertTrue(firstClaim.previousStatus().availableToClaim());
        assertTrue(firstClaim.currentStatus().claimed());
        assertEquals(snapshot, firstClaim.progressionSnapshot());
        assertEquals(
            CavernPlayerRewardState.empty(playerId).withClaimed(CavernProgressionReward.APPRENTICE_SUPPLY_CACHE),
            claimedRewardStore.load(playerId)
        );

        assertFalse(secondClaim.claimed());
        assertTrue(secondClaim.alreadyClaimed());
        assertTrue(secondClaim.currentStatus().claimed());
        assertEquals(snapshot, secondClaim.progressionSnapshot());
    }

    @Test
    void claimedRewardStateStaysIsolatedPerPlayer() {
        UUID playerA = UUID.randomUUID();
        UUID playerB = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernRewardService rewardService = new CavernRewardService(new TestPlayerClaimedRewardStore());

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerA, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
            progressionService.recordMiningEvent(playerB, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        rewardService.claim(progressionService.inspect(playerA), CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);

        CavernRewardStatus playerAStatus = rewardService.inspect(progressionService.inspect(playerA)).getFirst();
        CavernRewardStatus playerBStatus = rewardService.inspect(progressionService.inspect(playerB)).getFirst();

        assertTrue(playerAStatus.claimed());
        assertTrue(playerBStatus.availableToClaim());
        assertFalse(playerBStatus.claimed());
    }

    private static CavernProgressionSnapshot emptySnapshot(UUID playerId) {
        return new CavernProgressionSnapshot(playerId, 0, 0, CavernProgressionRank.NOVICE, Map.of());
    }

    private static final class TestPlayerClaimedRewardStore implements PlayerClaimedRewardStore {
        private final Map<UUID, CavernPlayerRewardState> states = new LinkedHashMap<>();

        @Override
        public CavernPlayerRewardState load(UUID playerId) {
            return states.getOrDefault(playerId, CavernPlayerRewardState.empty(playerId));
        }

        @Override
        public void save(CavernPlayerRewardState rewardState) {
            if (rewardState.isEmpty()) {
                states.remove(rewardState.playerId());
                return;
            }
            states.put(rewardState.playerId(), rewardState);
        }

        @Override
        public void clear(UUID playerId) {
            states.remove(playerId);
        }
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
