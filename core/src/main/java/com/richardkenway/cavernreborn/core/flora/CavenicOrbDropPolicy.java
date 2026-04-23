package com.richardkenway.cavernreborn.core.flora;

public final class CavenicOrbDropPolicy {
    public static final int SHEAR_DROP_ROLL_BOUND = 10;
    public static final int NORMAL_DROP_ROLL_BOUND = 50;

    private static final CavenicOrbDropResult NO_DROP = new CavenicOrbDropResult(false, 0);
    private static final CavenicOrbDropResult SINGLE_ORB = new CavenicOrbDropResult(true, 1);

    private CavenicOrbDropPolicy() {
    }

    public static CavenicOrbDropResult evaluate(boolean shears, int roll, boolean matureOrValid) {
        if (!matureOrValid) {
            return NO_DROP;
        }

        int rollBound = shears ? SHEAR_DROP_ROLL_BOUND : NORMAL_DROP_ROLL_BOUND;
        if (roll < 0 || roll >= rollBound) {
            return NO_DROP;
        }

        return roll == 0 ? SINGLE_ORB : NO_DROP;
    }
}
