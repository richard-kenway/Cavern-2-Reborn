package com.richardkenway.cavernreborn.core.loot;

public final class CrazySkeletonLootPolicy {
    public static final int ORB_DROP_ROLL_BOUND = 5;

    private CrazySkeletonLootPolicy() {
    }

    public static boolean shouldDropOrb(int roll) {
        return roll >= 0 && roll < ORB_DROP_ROLL_BOUND && roll == 0;
    }
}
