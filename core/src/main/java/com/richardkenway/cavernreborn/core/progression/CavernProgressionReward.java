package com.richardkenway.cavernreborn.core.progression;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public enum CavernProgressionReward {
    APPRENTICE_SUPPLY_CACHE(
        "apprentice_supply_cache",
        "Apprentice Supply Cache",
        CavernProgressionRank.APPRENTICE,
        true,
        List.of(
            new CavernRewardGrant("minecraft:torch", 16),
            new CavernRewardGrant("minecraft:bread", 8)
        )
    ),
    JOURNEYMAN_SUPPLY_CACHE(
        "journeyman_supply_cache",
        "Journeyman Supply Cache",
        CavernProgressionRank.JOURNEYMAN,
        true,
        List.of(
            new CavernRewardGrant("minecraft:torch", 24),
            new CavernRewardGrant("minecraft:cooked_beef", 8),
            new CavernRewardGrant("minecraft:water_bucket", 1)
        )
    );

    private static final Map<String, CavernProgressionReward> REWARDS_BY_ID = createRewardsById();

    private final String id;
    private final String label;
    private final CavernProgressionRank requiredRank;
    private final boolean oneTime;
    private final List<CavernRewardGrant> grants;

    CavernProgressionReward(
        String id,
        String label,
        CavernProgressionRank requiredRank,
        boolean oneTime,
        List<CavernRewardGrant> grants
    ) {
        this.id = requireText(id, "id");
        this.label = requireText(label, "label");
        this.requiredRank = Objects.requireNonNull(requiredRank, "requiredRank");
        this.oneTime = oneTime;
        this.grants = List.copyOf(Objects.requireNonNull(grants, "grants"));
        if (this.grants.isEmpty()) {
            throw new IllegalArgumentException("grants must not be empty");
        }
    }

    public String id() {
        return id;
    }

    public String label() {
        return label;
    }

    public CavernProgressionRank requiredRank() {
        return requiredRank;
    }

    public boolean oneTime() {
        return oneTime;
    }

    public List<CavernRewardGrant> grants() {
        return grants;
    }

    public boolean isEligible(CavernProgressionSnapshot snapshot) {
        return Objects.requireNonNull(snapshot, "snapshot").rank().ordinal() >= requiredRank.ordinal();
    }

    public static Optional<CavernProgressionReward> findById(String rewardId) {
        return Optional.ofNullable(REWARDS_BY_ID.get(requireText(rewardId, "rewardId")));
    }

    private static Map<String, CavernProgressionReward> createRewardsById() {
        Map<String, CavernProgressionReward> rewards = new LinkedHashMap<>();
        Arrays.stream(values()).forEach(reward -> rewards.put(reward.id(), reward));
        return Collections.unmodifiableMap(rewards);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
