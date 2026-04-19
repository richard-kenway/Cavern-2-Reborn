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
    void catalogShowsRewardAndServiceAsLockedForNewPlayer() {
        UUID playerId = UUID.randomUUID();
        CavernInteractionService service = new CavernInteractionService(
            new TestPlayerClaimedRewardStore(),
            new TestPlayerServiceStateStore()
        );

        java.util.List<CavernCatalogEntry> catalogEntries = service.inspectCatalog(emptySnapshot(playerId), 0L);

        assertEquals(4, catalogEntries.size());
        assertEquals(CavernCatalogEntryType.REWARD, catalogEntries.get(0).type());
        assertEquals(CavernCatalogAvailability.LOCKED, catalogEntries.get(0).availability());
        assertEquals(CavernCatalogEntryType.SERVICE, catalogEntries.get(1).type());
        assertEquals(CavernCatalogAvailability.LOCKED, catalogEntries.get(1).availability());
        assertEquals(CavernProgressionRank.JOURNEYMAN, catalogEntries.get(2).requiredRank());
        assertEquals(CavernProgressionRank.JOURNEYMAN, catalogEntries.get(3).requiredRank());
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
    void apprenticeCatalogLeavesJourneymanTierLocked() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        java.util.List<CavernCatalogEntry> catalogEntries = service.inspectCatalog(progressionService.inspect(playerId), 0L);

        assertTrue(catalogEntryFor(catalogEntries, "apprentice_supply_cache").availableToUse());
        assertTrue(catalogEntryFor(catalogEntries, "torch_supply").availableToUse());
        assertTrue(catalogEntryFor(catalogEntries, "journeyman_supply_cache").locked());
        assertTrue(catalogEntryFor(catalogEntries, "climbing_supply").locked());
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
    void unifiedCatalogUseDispatchesToRewardAndServiceSemantics() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        CavernCatalogUseResult rewardUse = service.useCatalogEntry(snapshot, "apprentice_supply_cache", 0L).orElseThrow();
        CavernCatalogUseResult serviceUse = service.useCatalogEntry(snapshot, "torch_supply", 0L).orElseThrow();
        CavernCatalogUseResult serviceRetry = service.useCatalogEntry(snapshot, "torch_supply", 0L).orElseThrow();

        assertTrue(rewardUse.granted());
        assertTrue(rewardUse.rewardEntry());
        assertEquals(CavernCatalogAvailability.CLAIMED, rewardUse.entry().availability());

        assertTrue(serviceUse.granted());
        assertTrue(serviceUse.serviceEntry());
        assertEquals(CavernCatalogAvailability.ON_COOLDOWN, serviceUse.entry().availability());

        assertFalse(serviceRetry.granted());
        assertTrue(serviceRetry.onCooldown());
        assertEquals(CavernCatalogAvailability.ON_COOLDOWN, serviceRetry.entry().availability());
    }

    @Test
    void journeymanCatalogEntriesUnlockAndKeepTheirOwnSemantics() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new TestPlayerMiningProgressionStore());
        CavernInteractionService service = newInteractionService();

        for (int i = 0; i < 15; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        CavernCatalogUseResult rewardUse = service.useCatalogEntry(snapshot, "journeyman_supply_cache", 0L).orElseThrow();
        CavernCatalogUseResult rewardRetry = service.useCatalogEntry(snapshot, "journeyman_supply_cache", 0L).orElseThrow();
        CavernCatalogUseResult serviceUse = service.useCatalogEntry(snapshot, "climbing_supply", 0L).orElseThrow();
        CavernCatalogUseResult serviceRetry = service.useCatalogEntry(snapshot, "climbing_supply", 0L).orElseThrow();

        assertTrue(rewardUse.granted());
        assertTrue(rewardUse.rewardEntry());
        assertEquals(CavernCatalogAvailability.CLAIMED, rewardUse.entry().availability());

        assertFalse(rewardRetry.granted());
        assertTrue(rewardRetry.alreadyClaimed());

        assertTrue(serviceUse.granted());
        assertTrue(serviceUse.serviceEntry());
        assertEquals(CavernCatalogAvailability.ON_COOLDOWN, serviceUse.entry().availability());

        assertFalse(serviceRetry.granted());
        assertTrue(serviceRetry.onCooldown());
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

    @Test
    void journeymanServiceCooldownSurvivesItsOwnWindow() {
        UUID playerId = UUID.randomUUID();
        CavernInteractionService service = newInteractionService();

        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            playerId, 15, 75, CavernProgressionRank.JOURNEYMAN, Map.of("minecraft:diamond_ore", 15)
        );

        long baseTime = 2_000_000L;
        CavernServiceRequestResult first = service.requestService(snapshot, CavernServiceEntry.CLIMBING_SUPPLY, baseTime);
        assertTrue(first.granted());

        long justBeforeReady = baseTime + CavernServiceEntry.CLIMBING_SUPPLY.cooldownMillis() - 1L;
        CavernServiceStatus onCooldown = serviceStatusFor(
            service.inspectServices(snapshot, justBeforeReady),
            CavernServiceEntry.CLIMBING_SUPPLY
        );
        assertTrue(onCooldown.onCooldown());

        long justAfterReady = baseTime + CavernServiceEntry.CLIMBING_SUPPLY.cooldownMillis() + 1L;
        CavernServiceStatus available = serviceStatusFor(
            service.inspectServices(snapshot, justAfterReady),
            CavernServiceEntry.CLIMBING_SUPPLY
        );
        assertTrue(available.availableToUse());
    }

    @Test
    void catalogReturnsEmptyForUnknownEntryId() {
        UUID playerId = UUID.randomUUID();
        CavernInteractionService service = newInteractionService();

        assertTrue(service.useCatalogEntry(emptySnapshot(playerId), "missing_entry", 0L).isEmpty());
    }

    private static CavernProgressionSnapshot emptySnapshot(UUID playerId) {
        return new CavernProgressionSnapshot(playerId, 0, 0, CavernProgressionRank.NOVICE, Map.of());
    }

    private static CavernCatalogEntry catalogEntryFor(java.util.List<CavernCatalogEntry> entries, String entryId) {
        return entries.stream()
            .filter(entry -> entry.id().equals(entryId))
            .findFirst()
            .orElseThrow();
    }

    private static CavernServiceStatus serviceStatusFor(
        java.util.List<CavernServiceStatus> statuses,
        CavernServiceEntry service
    ) {
        return statuses.stream()
            .filter(status -> status.service() == service)
            .findFirst()
            .orElseThrow();
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

    @Test
    void serviceCooldownSurvivesRestartAndRemainsConsistent() {
        UUID playerId = UUID.randomUUID();
        TestPlayerServiceStateStore serviceStore = new TestPlayerServiceStateStore();
        CavernInteractionService service = new CavernInteractionService(
            new TestPlayerClaimedRewardStore(),
            serviceStore
        );

        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            playerId, 30, 5, CavernProgressionRank.APPRENTICE, Map.of()
        );
        long realWallClockBeforeUse = 1_000_000_000L;
        long cooldownMillis = CavernServiceEntry.TORCH_SUPPLY.cooldownMillis();

        CavernServiceRequestResult useResult = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, realWallClockBeforeUse);
        assertTrue(useResult.granted());
        assertTrue(useResult.previousStatus().availableToUse());
        assertTrue(useResult.currentStatus().onCooldown());

        CavernPlayerServiceState persistedState = serviceStore.load(playerId);
        assertEquals(realWallClockBeforeUse, persistedState.lastUsedTimestamp(CavernServiceEntry.TORCH_SUPPLY));

        long justBeforeCooldownEnds = realWallClockBeforeUse + cooldownMillis - 1L;
        CavernServiceStatus statusJustBefore = service.inspectServices(snapshot, justBeforeCooldownEnds).getFirst();
        assertTrue(statusJustBefore.onCooldown());
        assertFalse(statusJustBefore.availableToUse());

        long justAfterCooldownEnds = realWallClockBeforeUse + cooldownMillis + 1L;
        CavernServiceStatus statusJustAfter = service.inspectServices(snapshot, justAfterCooldownEnds).getFirst();
        assertFalse(statusJustAfter.onCooldown());
        assertTrue(statusJustAfter.availableToUse());
    }

    @Test
    void repeatedRequestsDuringCooldownAreRejected() {
        UUID playerId = UUID.randomUUID();
        CavernInteractionService service = newInteractionService();

        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(
            playerId, 30, 5, CavernProgressionRank.APPRENTICE, Map.of()
        );

        long baseTime = 500_000_000L;
        CavernServiceRequestResult first = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, baseTime);
        assertTrue(first.granted());

        CavernServiceRequestResult immediateRetry = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, baseTime);
        assertFalse(immediateRetry.granted());
        assertTrue(immediateRetry.onCooldown());

        long stillOnCooldown = baseTime + (CavernServiceEntry.TORCH_SUPPLY.cooldownMillis() / 2);
        CavernServiceRequestResult midCooldown = service.requestService(snapshot, CavernServiceEntry.TORCH_SUPPLY, stillOnCooldown);
        assertFalse(midCooldown.granted());
        assertTrue(midCooldown.onCooldown());
    }

}
