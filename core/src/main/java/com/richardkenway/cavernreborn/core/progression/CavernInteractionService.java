package com.richardkenway.cavernreborn.core.progression;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class CavernInteractionService {
    private static final Comparator<CavernCatalogEntry> CATALOG_ENTRY_ORDER = Comparator
        .comparingInt((CavernCatalogEntry entry) -> entry.requiredRank().threshold())
        .thenComparingInt(entry -> entry.type().ordinal())
        .thenComparing(CavernCatalogEntry::id);

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

    public List<CavernCatalogEntry> inspectCatalog(CavernProgressionSnapshot snapshot, long currentTimeMillis) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        UUID playerId = normalizedSnapshot.playerId();
        CavernPlayerRewardState rewardState = claimedRewardStore.load(playerId);
        CavernPlayerServiceState serviceState = serviceStateStore.load(playerId);

        List<CavernCatalogEntry> rewardEntries = Arrays.stream(CavernProgressionReward.values())
            .map(reward -> rewardStatusFor(normalizedSnapshot, rewardState, reward))
            .map(CavernInteractionService::catalogEntryForReward)
            .toList();
        List<CavernCatalogEntry> serviceEntries = Arrays.stream(CavernServiceEntry.values())
            .map(service -> serviceStatusFor(normalizedSnapshot, serviceState, service, currentTimeMillis))
            .map(status -> catalogEntryForService(status, serviceState, currentTimeMillis))
            .toList();

        java.util.ArrayList<CavernCatalogEntry> entries = new java.util.ArrayList<>(rewardEntries.size() + serviceEntries.size());
        entries.addAll(rewardEntries);
        entries.addAll(serviceEntries);
        return entries.stream()
            .sorted(CATALOG_ENTRY_ORDER)
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

    public Optional<CavernCatalogUseResult> useCatalogEntry(
        CavernProgressionSnapshot snapshot,
        String entryId,
        long currentTimeMillis
    ) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        String normalizedEntryId = requireText(entryId, "entryId");
        Optional<CavernProgressionReward> reward = CavernProgressionReward.findById(normalizedEntryId);
        CavernServiceEntry service = CavernServiceEntry.findById(normalizedEntryId);
        if (reward.isPresent() && service != null) {
            throw new IllegalStateException("Catalog id collision: " + normalizedEntryId);
        }
        if (reward.isPresent()) {
            CavernRewardClaimResult rewardResult = claimReward(normalizedSnapshot, reward.get());
            return Optional.of(new CavernCatalogUseResult(
                catalogEntryForReward(rewardResult.currentStatus()),
                rewardResult,
                null
            ));
        }
        if (service != null) {
            CavernServiceRequestResult serviceResult = requestService(normalizedSnapshot, service, currentTimeMillis);
            CavernPlayerServiceState serviceState = serviceStateStore.load(normalizedSnapshot.playerId());
            return Optional.of(new CavernCatalogUseResult(
                catalogEntryForService(serviceResult.currentStatus(), serviceState, currentTimeMillis),
                null,
                serviceResult
            ));
        }
        return Optional.empty();
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

    private static CavernCatalogEntry catalogEntryForReward(CavernRewardStatus status) {
        CavernCatalogAvailability availability = switch (status.availability()) {
            case LOCKED -> CavernCatalogAvailability.LOCKED;
            case AVAILABLE -> CavernCatalogAvailability.AVAILABLE;
            case CLAIMED -> CavernCatalogAvailability.CLAIMED;
        };
        return new CavernCatalogEntry(
            CavernCatalogEntryType.REWARD,
            status.reward().id(),
            status.reward().label(),
            status.reward().requiredRank(),
            availability,
            !status.reward().oneTime(),
            status.reward().grants(),
            0L
        );
    }

    private static CavernCatalogEntry catalogEntryForService(
        CavernServiceStatus status,
        CavernPlayerServiceState serviceState,
        long currentTimeMillis
    ) {
        CavernCatalogAvailability availability = switch (status.availability()) {
            case LOCKED -> CavernCatalogAvailability.LOCKED;
            case AVAILABLE -> CavernCatalogAvailability.AVAILABLE;
            case USED -> CavernCatalogAvailability.CLAIMED;
            case ON_COOLDOWN -> CavernCatalogAvailability.ON_COOLDOWN;
        };
        return new CavernCatalogEntry(
            CavernCatalogEntryType.SERVICE,
            status.service().id(),
            status.service().label(),
            status.service().requiredRank(),
            availability,
            !status.service().oneTime(),
            status.service().grants(),
            remainingCooldownMillis(serviceState, status.service(), currentTimeMillis)
        );
    }

    private static long remainingCooldownMillis(
        CavernPlayerServiceState serviceState,
        CavernServiceEntry service,
        long currentTimeMillis
    ) {
        long lastUsedTimestamp = serviceState.lastUsedTimestamp(service);
        if (lastUsedTimestamp <= 0L || service.oneTime()) {
            return 0L;
        }
        return Math.max(0L, (lastUsedTimestamp + service.cooldownMillis()) - currentTimeMillis);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
