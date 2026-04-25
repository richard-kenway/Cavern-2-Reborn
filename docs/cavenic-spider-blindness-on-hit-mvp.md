# Cavenic Spider Blindness-On-Hit MVP

This note documents the bounded melee-effect follow-up for the existing `cavernreborn:cavenic_spider`.

It does not add a new mob, natural-spawn changes, loot changes, poison behavior, web behavior or custom AI. It only restores the confirmed legacy blindness-on-hit path from `EntityCavenicSpider`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicSpider`
- legacy `attackEntityAsMob(Entity entity)` override
- legacy `getBlindnessAttackPower()`
- legacy `getPoisonAttackPower()`
- legacy `attackEntityFrom(DamageSource source, float damage)` override for damage-regression context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override for orb-drop regression context

## Exact Legacy Behavior Found

Legacy `EntityCavenicSpider#attackEntityAsMob(Entity entity)` first called `super.attackEntityAsMob(entity)`.

After a successful melee hit:

- only living targets were considered
- blindness used `getBlindnessAttackPower()`
- `getBlindnessAttackPower()` returned:
  - default branch: `3`
  - `NORMAL`: `5`
  - `HARD`: `10`
- blindness was applied only when the target was not already blinded
- the effect used `new PotionEffect(MobEffects.BLINDNESS, sec * 20)`
- the amplifier stayed on the legacy default `0`

The same method also checked `getPoisonAttackPower()`, but that legacy helper still returned `0`, so poison never actually applied in the legacy spider baseline.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicSpider`.
- The modern melee hook is `doHurtTarget(Entity target)`.
- Reborn calls `super.doHurtTarget(target)` first and stores whether the vanilla melee attack succeeded.
- Reborn applies blindness only through `tryApplyLegacyBlindnessOnHit(LivingEntity target, boolean attackSucceeded)`.
- Blindness is applied only after a successful melee hit.
- Only living targets can receive the effect.
- Reborn uses `MobEffects.BLINDNESS` and `MobEffectInstance`.
- Reborn resolves duration through `getLegacyBlindnessDurationTicks(Difficulty difficulty)`.

## Exact Reborn Behavior

- `LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS = 60`
- `LEGACY_BLINDNESS_DURATION_NORMAL_TICKS = 100`
- `LEGACY_BLINDNESS_DURATION_HARD_TICKS = 200`
- `LEGACY_BLINDNESS_AMPLIFIER = 0`
- Easy and peaceful difficulty fall back to `60` ticks (`3` seconds).
- Normal difficulty uses `100` ticks (`5` seconds).
- Hard difficulty uses `200` ticks (`10` seconds).
- Blindness is applied only after a successful melee hit.
- Only living targets can receive the effect.
- Targets that are already blind are left unchanged, matching the old `!target.isPotionActive(MobEffects.BLINDNESS)` guard.
- vanilla spider AI remains unchanged.
- attributes, natural spawning, `1/8` orb-drop behavior and fall/fire damage behavior remain unchanged.
- Poison remains out of scope because the legacy `getPoisonAttackPower()` path still returned `0`.

## Why This Mapping Is Safe

- The legacy blindness logic lived directly on `EntityCavenicSpider`.
- Reborn keeps the implementation entity-local instead of adding a shared mob-effect or combat framework.
- `doHurtTarget(Entity target)` is the closest current 1.21.1 equivalent to the old `attackEntityAsMob(Entity entity)` hook.
- Keeping poison and web behavior out of this slice avoids widening the spider follow-up beyond the confirmed blindness path.

## Testing

- Resource tests pin the blindness duration/amplifier constants, the modern attack hook, the success-only helper path and the absence of poison/web additions.
- Documentation tests pin the legacy source references, the exact difficulty-based duration mapping and the explicit poison/web boundary.
- NeoForge GameTest runtime smoke covers:
  - cavenic spider blindness-on-hit runtime smoke
  - vanilla spider no-blindness comparison smoke
  - unchanged orb-drop, natural-spawn and fall/fire-damage constants
  - continued vanilla spider loot-table baseline

## Still Out Of Scope

- Poison behavior remains out of scope
- Web behavior remains out of scope
- Other special spider behavior remains out of scope
- Custom Cavenic Spider AI remains out of scope
- Additional Cavenic mobs remain out of scope
- Cavenia remains out of scope
- Broad combat rewrites remain out of scope
