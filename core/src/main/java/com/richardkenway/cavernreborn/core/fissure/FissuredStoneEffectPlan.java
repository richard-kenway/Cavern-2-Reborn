package com.richardkenway.cavernreborn.core.fissure;

import java.util.List;

public record FissuredStoneEffectPlan(
    FissuredStoneEffectType type,
    List<FissuredStoneBuff> buffs,
    int durationTicks,
    int amplifier,
    float radius
) {
    public FissuredStoneEffectPlan {
        buffs = List.copyOf(buffs);
        if (durationTicks < 0) {
            throw new IllegalArgumentException("durationTicks must not be negative");
        }
        if (amplifier < 0) {
            throw new IllegalArgumentException("amplifier must not be negative");
        }
        if (radius < 0.0F) {
            throw new IllegalArgumentException("radius must not be negative");
        }
    }

    public boolean spawnsAreaEffectCloud() {
        return type == FissuredStoneEffectType.NORMAL_AREA_EFFECT;
    }

    public boolean appliesDirectPlayerBuff() {
        return type == FissuredStoneEffectType.FORTUNE_INTENSIVE_EFFECT;
    }
}
