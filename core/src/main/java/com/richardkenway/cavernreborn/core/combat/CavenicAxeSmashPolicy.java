package com.richardkenway.cavernreborn.core.combat;

public final class CavenicAxeSmashPolicy {
    public static final double SMASH_RADIUS = 2.5D;
    public static final int MAX_EXTRA_TARGETS = 6;
    public static final float ADDITIONAL_DAMAGE = 4.0F;
    public static final int MAX_DURABILITY_COST = 3;

    private static final double SMASH_RADIUS_SQUARED = SMASH_RADIUS * SMASH_RADIUS;

    private CavenicAxeSmashPolicy() {
    }

    public static boolean isWithinRadius(double distanceSquared) {
        return Double.isFinite(distanceSquared) && distanceSquared <= SMASH_RADIUS_SQUARED;
    }

    public static boolean isEligibleExtraTarget(
        boolean living,
        boolean hostile,
        boolean primaryTarget,
        boolean attacker,
        boolean ownedOrFriendly,
        double distanceSquared
    ) {
        return living
            && hostile
            && !primaryTarget
            && !attacker
            && !ownedOrFriendly
            && isWithinRadius(distanceSquared);
    }

    public static int cappedExtraTargets(int eligibleTargetCount) {
        return Math.max(0, Math.min(eligibleTargetCount, MAX_EXTRA_TARGETS));
    }

    public static int durabilityCost(int extraAffectedTargets) {
        int countedTargets = Math.max(0, extraAffectedTargets) + 1;
        int legacyInspiredCost = countedTargets / 2;
        return Math.max(1, Math.min(legacyInspiredCost, MAX_DURABILITY_COST));
    }
}
