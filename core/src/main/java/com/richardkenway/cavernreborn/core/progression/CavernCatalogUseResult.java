package com.richardkenway.cavernreborn.core.progression;

public record CavernCatalogUseResult(
    CavernCatalogEntry entry,
    CavernRewardClaimResult rewardClaimResult,
    CavernServiceRequestResult serviceRequestResult
) {
    public CavernCatalogUseResult {
        entry = java.util.Objects.requireNonNull(entry, "entry");
        boolean hasRewardResult = rewardClaimResult != null;
        boolean hasServiceResult = serviceRequestResult != null;
        if (hasRewardResult == hasServiceResult) {
            throw new IllegalArgumentException("exactly one underlying result must be present");
        }
        if (hasRewardResult && entry.type() != CavernCatalogEntryType.REWARD) {
            throw new IllegalArgumentException("reward result requires a reward catalog entry");
        }
        if (hasServiceResult && entry.type() != CavernCatalogEntryType.SERVICE) {
            throw new IllegalArgumentException("service result requires a service catalog entry");
        }
    }

    public boolean rewardEntry() {
        return rewardClaimResult != null;
    }

    public boolean serviceEntry() {
        return serviceRequestResult != null;
    }

    public boolean granted() {
        return rewardEntry() ? rewardClaimResult.claimed() : serviceRequestResult.granted();
    }

    public boolean locked() {
        return rewardEntry() ? rewardClaimResult.locked() : serviceRequestResult.locked();
    }

    public boolean alreadyClaimed() {
        return rewardEntry() && rewardClaimResult.alreadyClaimed();
    }

    public boolean onCooldown() {
        return serviceEntry() && serviceRequestResult.onCooldown();
    }

    public boolean alreadyUsed() {
        return serviceEntry() && serviceRequestResult.alreadyUsed();
    }

    public CavernProgressionSnapshot progressionSnapshot() {
        return rewardEntry()
            ? rewardClaimResult.progressionSnapshot()
            : serviceRequestResult.progressionSnapshot();
    }

    public CavernProgressionReward reward() {
        if (!rewardEntry()) {
            throw new IllegalStateException("catalog result does not reference a reward");
        }
        return rewardClaimResult.reward();
    }

    public CavernServiceEntry service() {
        if (!serviceEntry()) {
            throw new IllegalStateException("catalog result does not reference a service");
        }
        return serviceRequestResult.service();
    }
}
