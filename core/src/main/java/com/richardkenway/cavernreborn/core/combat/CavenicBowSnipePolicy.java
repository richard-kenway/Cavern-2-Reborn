package com.richardkenway.cavernreborn.core.combat;

public final class CavenicBowSnipePolicy {
    public static final float MIN_POWER_THRESHOLD = 1.0F;
    public static final float VELOCITY_MULTIPLIER = 1.5F;
    public static final double BASE_DAMAGE_MULTIPLIER = 1.5D;
    public static final int EXTRA_DURABILITY_COST = 1;

    private CavenicBowSnipePolicy() {
    }

    public static boolean applies(CavenicBowMode mode, float power) {
        return mode == CavenicBowMode.SNIPE && power >= MIN_POWER_THRESHOLD;
    }

    public static float adjustedVelocity(CavenicBowMode mode, float baseVelocity, float power) {
        if (!applies(mode, power)) {
            return baseVelocity;
        }

        return baseVelocity * VELOCITY_MULTIPLIER;
    }

    public static double adjustedBaseDamage(CavenicBowMode mode, double baseDamage, float power) {
        if (!applies(mode, power)) {
            return baseDamage;
        }

        return baseDamage * BASE_DAMAGE_MULTIPLIER;
    }
}
