package com.richardkenway.cavernreborn.core.progression;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class CavernInteractionService {
    private final PlayerClaimedRewardStore claimedRewardStore;
    private final PlayerServiceStateStore serviceStateStore;

    public CavernInteractionService(PlayerClaimedRewardStore claimedRewardStore, PlayerServiceStateStore serviceStateStore) {
        this.claimedRewardStore = Objects.requireNonNull(claimedRewardStore, "claimedRewardStore");
        this.serviceStateStore = Objects.requireNonNull(serviceStateStore, "serviceStateStore");
    }

    public List<CavernServiceStatus> inspectServices(CavernProgressionSnapshot snapshot, long currentTimeMillis) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        UUID playerId = normalizedSnapshot.playerId();
        CavernPlayerServiceState serviceState = serviceStateStore.load(playerId);
        return Arrays.stream(CavernServiceEntry.values())
            .map(service -> serviceStatusFor(normalizedSnapshot, serviceState, service, currentTimeMillis))
            .toList();
    }

    public CavernServiceRequestResult requestService(CavernProgressionSnapshot snapshot, CavernServiceEntry service, long currentTimeMillis) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        CavernServiceEntry normalizedService = Objects.requireNonNull(service, "service");
        UUID playerId = normalizedSnapshot.playerId();
        CavernPlayerServiceState serviceState = serviceStateStore.load(playerId);
        CavernServiceStatus previousStatus = serviceStatusFor(normalizedSnapshot, serviceState, normalizedService, currentTimeMillis);
        if (!previousStatus.availableToUse()) {
            return new CavernServiceRequestResult(normalizedSnapshot, previousStatus, previousStatus, false);
        }

        CavernPlayerServiceState updatedServiceState = serviceState.withServiceUsed(normalizedService, currentTimeMillis);
        serviceStateStore.save(updatedServiceState);
        return new CavernServiceRequestResult(
            normalizedSnapshot,
            previousStatus,
            serviceStatusFor(normalizedSnapshot, updatedServiceState, normalizedService, currentTimeMillis),
            true
        );
    }

    private static CavernServiceStatus serviceStatusFor(
        CavernProgressionSnapshot snapshot,
        CavernPlayerServiceState serviceState,
        CavernServiceEntry service,
        long currentTimeMillis
    ) {
        if (!service.isEligible(snapshot)) {
            return new CavernServiceStatus(service, CavernServiceAvailability.LOCKED);
        }
        if (service.oneTime() && serviceState.lastUsedTimestamp(service) > 0) {
            return new CavernServiceStatus(service, CavernServiceAvailability.USED);
        }
        if (serviceState.isOnCooldown(service, currentTimeMillis)) {
            return new CavernServiceStatus(service, CavernServiceAvailability.ON_COOLDOWN);
        }
        return new CavernServiceStatus(service, CavernServiceAvailability.AVAILABLE);
    }

    public List<CavernRewardStatus> inspectRewards(CavernProgressionSnapshot snapshot) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        CavernPlayerRewardState rewardState = claimedRewardStore.load(normalizedSnapshot.playerId());
        return Arrays.stream(CavernProgressionReward.values())
            .map(reward -> rewardStatusFor(normalizedSnapshot, rewardState, reward))
            .toList();
    }

    public CavernRewardClaimResult claimReward(CavernProgressionSnapshot snapshot, CavernProgressionReward reward) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        CavernProgressionReward normalizedReward = Objects.requireNonNull(reward, "reward");
        CavernPlayerRewardState rewardState = claimedRewardStore.load(normalizedSnapshot.playerId());
        CavernRewardStatus previousStatus = rewardStatusFor(normalizedSnapshot, rewardState, normalizedReward);
        if (!previousStatus.availableToClaim()) {
            return new CavernRewardClaimResult(normalizedSnapshot, previousStatus, previousStatus, false);
        }
        if (!normalizedReward.oneTime()) {
            return new CavernRewardClaimResult(normalizedSnapshot, previousStatus, previousStatus, true);
        }

        CavernPlayerRewardState updatedRewardState = rewardState.withClaimed(normalizedReward);
        claimedRewardStore.save(updatedRewardState);
        return new CavernRewardClaimResult(
            normalizedSnapshot,
            previousStatus,
            rewardStatusFor(normalizedSnapshot, updatedRewardState, normalizedReward),
            true
        );
    }

    private static CavernRewardStatus rewardStatusFor(
        CavernProgressionSnapshot snapshot,
        CavernPlayerRewardState rewardState,
        CavernProgressionReward reward
    ) {
        if (rewardState.hasClaimed(reward)) {
            return new CavernRewardStatus(reward, CavernRewardAvailability.CLAIMED);
        }
        if (reward.isEligible(snapshot)) {
            return new CavernRewardStatus(reward, CavernRewardAvailability.AVAILABLE);
        }
        return new CavernRewardStatus(reward, CavernRewardAvailability.LOCKED);
    }
}
