package com.richardkenway.cavernreborn.core.farming;

public final class AcresiaHarvestPolicy {
    public static final int MAX_AGE = 4;
    public static final int REGROWTH_AGE = 2;

    private AcresiaHarvestPolicy() {
    }

    public static AcresiaHarvestResult evaluateShearHarvest(
        int age,
        int fortuneLevel,
        int rollInclusive0to2
    ) {
        if (rollInclusive0to2 < 0 || rollInclusive0to2 > 2) {
            throw new IllegalArgumentException("rollInclusive0to2 must be between 0 and 2");
        }

        if (age < MAX_AGE) {
            return new AcresiaHarvestResult(false, 0, age);
        }

        int boundedFortune = Math.min(Math.max(fortuneLevel, 0), 2);
        return new AcresiaHarvestResult(true, 4 + rollInclusive0to2 + boundedFortune, REGROWTH_AGE);
    }
}
