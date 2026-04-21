package com.richardkenway.cavernreborn.core.fissure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class FissuredStoneEffectPolicyTest {
    @Test
    void normalBreakUsesLocalAreaEffect() {
        FissuredStoneEffectPlan plan = FissuredStoneEffectPolicy.effectForBreak(false, 0);

        assertEquals(FissuredStoneEffectType.NORMAL_AREA_EFFECT, plan.type());
        assertTrue(plan.spawnsAreaEffectCloud());
        assertFalse(plan.appliesDirectPlayerBuff());
        assertEquals(List.of(FissuredStoneBuff.NIGHT_VISION), plan.buffs());
        assertEquals(300, plan.durationTicks());
        assertEquals(0, plan.amplifier());
        assertEquals(2.0F, plan.radius());
    }

    @Test
    void fortuneBreakUsesBoundedPositiveBuffs() {
        FissuredStoneEffectPlan plan = FissuredStoneEffectPolicy.effectForBreak(true, 5);

        assertEquals(FissuredStoneEffectType.FORTUNE_INTENSIVE_EFFECT, plan.type());
        assertFalse(plan.spawnsAreaEffectCloud());
        assertTrue(plan.appliesDirectPlayerBuff());
        assertEquals(List.of(FissuredStoneBuff.REGENERATION, FissuredStoneBuff.ABSORPTION, FissuredStoneBuff.RESISTANCE), plan.buffs());
        assertEquals(280, plan.durationTicks());
        assertEquals(1, plan.amplifier());
        assertEquals(0.0F, plan.radius());
    }

    @Test
    void invalidFortuneLevelFallsBackToNoEffect() {
        FissuredStoneEffectPlan plan = FissuredStoneEffectPolicy.effectForBreak(false, -1);

        assertEquals(FissuredStoneEffectType.NO_EFFECT, plan.type());
        assertTrue(plan.buffs().isEmpty());
        assertEquals(0, plan.durationTicks());
        assertEquals(0, plan.amplifier());
        assertEquals(0.0F, plan.radius());
    }

    @Test
    void policyHasNoDestructiveExplosionOutcome() {
        assertTrue(Arrays.stream(FissuredStoneEffectType.values()).noneMatch(type -> type.name().contains("EXPLOSION")));
    }
}
