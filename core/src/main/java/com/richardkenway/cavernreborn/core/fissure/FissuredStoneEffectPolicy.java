package com.richardkenway.cavernreborn.core.fissure;

import java.util.List;

public final class FissuredStoneEffectPolicy {
    private static final int TICKS_PER_SECOND = 20;
    private static final int MAX_INTENSIVE_AMPLIFIER = 1;
    private static final int MAX_FORTUNE_LEVEL = 3;
    private static final FissuredStoneEffectPlan NO_EFFECT = new FissuredStoneEffectPlan(
        FissuredStoneEffectType.NO_EFFECT,
        List.of(),
        0,
        0,
        0.0F
    );
    private static final FissuredStoneEffectPlan NORMAL_AREA_EFFECT = new FissuredStoneEffectPlan(
        FissuredStoneEffectType.NORMAL_AREA_EFFECT,
        List.of(FissuredStoneBuff.NIGHT_VISION),
        15 * TICKS_PER_SECOND,
        0,
        2.0F
    );

    private FissuredStoneEffectPolicy() {
    }

    public static FissuredStoneEffectPlan effectForBreak(boolean playerContext, int fortuneLevel) {
        if (fortuneLevel < 0) {
            return NO_EFFECT;
        }
        if (playerContext && fortuneLevel > 0) {
            int cappedFortune = Math.min(fortuneLevel, MAX_FORTUNE_LEVEL);
            return new FissuredStoneEffectPlan(
                FissuredStoneEffectType.FORTUNE_INTENSIVE_EFFECT,
                List.of(FissuredStoneBuff.REGENERATION, FissuredStoneBuff.ABSORPTION, FissuredStoneBuff.RESISTANCE),
                (8 + (cappedFortune * 2)) * TICKS_PER_SECOND,
                Math.min(cappedFortune - 1, MAX_INTENSIVE_AMPLIFIER),
                0.0F
            );
        }
        return NORMAL_AREA_EFFECT;
    }
}
