package com.richardkenway.cavernreborn.core.progression;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class CavernRewardService {
    private final PlayerClaimedRewardStore claimedRewardStore;

    public CavernRewardService(PlayerClaimedRewardStore claimedRewardStore) {
        this.claimedRewardStore = Objects.requireNonNull(claimedRewardStore, "claimedRewardStore");
    }

    public List<CavernRewardStatus> inspect(CavernProgressionSnapshot snapshot) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        CavernPlayerRewardState rewardState = claimedRewardStore.load(normalizedSnapshot.playerId());
        return Arrays.stream(CavernProgressionReward.values())
            .map(reward -> statusFor(normalizedSnapshot, rewardState, reward))
            .toList();
    }

    public CavernRewardClaimResult claim(CavernProgressionSnapshot snapshot, CavernProgressionReward reward) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        CavernProgressionReward normalizedReward = Objects.requireNonNull(reward, "reward");
        CavernPlayerRewardState rewardState = claimedRewardStore.load(normalizedSnapshot.playerId());
        CavernRewardStatus previousStatus = statusFor(normalizedSnapshot, rewardState, normalizedReward);
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
            statusFor(normalizedSnapshot, updatedRewardState, normalizedReward),
            true
        );
    }

    private static CavernRewardStatus statusFor(
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
