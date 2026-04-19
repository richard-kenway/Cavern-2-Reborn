package com.richardkenway.cavernreborn.core.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernInteractionServiceTest {
    @Test
    void newPlayerBelowApprenticeSeesLockedService() {
        UUID playerId = UUID.randomUUID();
        CavernInteractionService service = new CavernInteractionService(
            new TestPlayerClaimedRewardStore(),
            new TestPlayerServiceStateStore()
        );

        CavernServiceStatus status = service.inspectServices(emptySnapshot(playerId), 0L).getFirst();

        assertEquals(CavernServiceEntry.TORCH_SUPPLY, status.service());
        assertTrue(status.locked());
        assertFalse(status.availableToUse());
    }

    @Test
    void serviceBecomesAvailableAtRequiredRank() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernServiceStatus status = service.inspectServices(progressionService.inspect(playerId), 0L).getFirst();

        assertEquals(CavernProgressionRank.APPRENTICE, progressionService.inspect(playerId).rank());
        assertTrue(status.availableToUse());
        assertFalse(status.onCooldown());
    }

    @Test
    void repeatableServiceCanBeUsedMultipleTimesWithCooldown() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        long baseTime = 0L;

        CavernServiceRequestResult firstRequest = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, baseTime);
        assertTrue(firstRequest.granted());
        assertTrue(firstRequest.previousStatus().availableToUse());
        assertFalse(firstRequest.currentStatus().availableToUse());

        CavernServiceRequestResult immediateSecondRequest = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, baseTime);
        assertFalse(immediateSecondRequest.granted());
        assertTrue(immediateSecondRequest.onCooldown());

        long afterCooldown = baseTime + CavernServiceEntry.TORCH_SUPPLY.cooldownMillis();
        CavernServiceRequestResult afterCooldownRequest = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, afterCooldown);
        assertTrue(afterCooldownRequest.granted());
        assertTrue(afterCooldownRequest.previousStatus().availableToUse());
    }

    @Test
    void serviceUsageIsIsolatedPerPlayer() {
        UUID playerA = UUID.randomUUID();
        UUID playerB = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerA, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
            progressionService.recordMiningEvent(playerB, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        service.requestService(progressionService.inspect(playerA), CavernServiceEntry.TORCH_SUPPLY, 0L);

        CavernServiceStatus playerAStatus = service.inspectServices(progressionService.inspect(playerA), 0L).getFirst();
        CavernServiceStatus playerBStatus = service.inspectServices(progressionService.inspect(playerB), 0L).getFirst();

        assertFalse(playerAStatus.availableToUse());
        assertTrue(playerBStatus.availableToUse());
    }

    @Test
    void oneTimeRewardStaysClaimedAfterServiceUse() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        service.claimReward(snapshot, CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);
        service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, 0L);

        CavernRewardStatus rewardStatus = service.inspectRewards(snapshot).getFirst();
        assertTrue(rewardStatus.claimed());
    }

    @Test
    void serviceAndRewardEligibilityAreIndependent() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        service.claimReward(snapshot, CavernProgressionReward.APPRENTICE_SUPPLY_CACHE);

        CavernRewardStatus rewardStatus = service.inspectRewards(snapshot).getFirst();
        CavernServiceStatus serviceStatus = service.inspectServices(snapshot, 0L).getFirst();

        assertTrue(rewardStatus.claimed());
        assertTrue(serviceStatus.availableToUse());
    }

    private static CavernProgressionSnapshot emptySnapshot(UUID playerId) {
        return new CavernProgressionSnapshot(playerId, 0, 0, CavernProgressionRank.NOVICE, Map.of());
    }

    private static CavernInteractionService newInteractionService() {
        return new CavernInteractionService(
            new TestPlayerClaimedRewardStore(),
            new TestPlayerServiceStateStore()
        );
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

    private static final class TestPlayerServiceStateStore implements PlayerServiceStateStore {
        private final Map<UUID, CavernPlayerServiceState> states = new LinkedHashMap<>();

        @Override
        public CavernPlayerServiceState load(UUID playerId) {
            return states.getOrDefault(playerId, CavernPlayerServiceState.empty(playerId));
        }

        @Override
        public void save(CavernPlayerServiceState serviceState) {
            if (serviceState.isEmpty()) {
                states.remove(serviceState.playerId());
                return;
            }
            states.put(serviceState.playerId(), serviceState);
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
