package com.richardkenway.cavernreborn.core.progression;

import java.util.List;
import java.util.Objects;

public enum CavernServiceEntry {
    TORCH_SUPPLY(
        "torch_supply",
        "Torch Supply",
        CavernProgressionRank.APPRENTICE,
        false,
        600_000L,
        List.of(
            new CavernRewardGrant("minecraft:torch", 16)
        )
    );

    private static final java.util.Map<String, CavernServiceEntry> SERVICES_BY_ID = createServicesById();

    private final String id;
    private final String label;
    private final CavernProgressionRank requiredRank;
    private final boolean oneTime;
    private final long cooldownMillis;
    private final List<CavernRewardGrant> grants;

    CavernServiceEntry(
        String id,
        String label,
        CavernProgressionRank requiredRank,
        boolean oneTime,
        long cooldownMillis,
        List<CavernRewardGrant> grants
    ) {
        this.id = requireText(id, "id");
        this.label = requireText(label, "label");
        this.requiredRank = Objects.requireNonNull(requiredRank, "requiredRank");
        this.oneTime = oneTime;
        this.cooldownMillis = cooldownMillis;
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

    public long cooldownMillis() {
        return cooldownMillis;
    }

    public List<CavernRewardGrant> grants() {
        return grants;
    }

    public boolean isEligible(CavernProgressionSnapshot snapshot) {
        return Objects.requireNonNull(snapshot, "snapshot").rank().ordinal() >= requiredRank.ordinal();
    }

    public static CavernServiceEntry findById(String serviceId) {
        return SERVICES_BY_ID.get(requireText(serviceId, "serviceId"));
    }

    private static java.util.Map<String, CavernServiceEntry> createServicesById() {
        java.util.Map<String, CavernServiceEntry> services = new java.util.LinkedHashMap<>();
        for (CavernServiceEntry entry : values()) {
            services.put(entry.id(), entry);
        }
        return java.util.Collections.unmodifiableMap(services);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
