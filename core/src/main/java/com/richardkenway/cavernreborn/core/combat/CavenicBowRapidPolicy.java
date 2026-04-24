package com.richardkenway.cavernreborn.core.combat;

public final class CavenicBowRapidPolicy {
    public static final float POWER_MULTIPLIER = 2.4F;
    public static final float MAX_POWER = 1.0F;
    public static final int EXTRA_DURABILITY_COST = 0;

    private CavenicBowRapidPolicy() {
    }

    public static float adjustedPower(CavenicBowMode mode, float rawPower) {
        float sanitizedPower = sanitizeRawPower(rawPower);
        if (mode != CavenicBowMode.RAPID) {
            return sanitizedPower;
        }

        return Math.min(sanitizedPower * POWER_MULTIPLIER, MAX_POWER);
    }

    private static float sanitizeRawPower(float rawPower) {
        if (!Float.isFinite(rawPower) || rawPower <= 0.0F) {
            return 0.0F;
        }

        return rawPower;
    }
}
