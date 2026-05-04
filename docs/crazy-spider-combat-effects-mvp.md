# Crazy Spider Combat / Blindness / Poison MVP

This note documents the bounded outgoing-combat follow-up for the existing `cavernreborn:crazy_spider`.

It restores only the legacy successful-hit blindness/poison branch. It does not add natural spawning, loot changes, incoming-damage changes, boss-event changes, particle changes, custom AI, packets or broader spider combat systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySpider`
- `cavern.entity.monster.EntityCavenicSpider`
- legacy `attackEntityAsMob(Entity entity)` on `EntityCavenicSpider`
- legacy `getBlindnessAttackPower()` on `EntityCrazySpider`
- legacy `getPoisonAttackPower()` on `EntityCrazySpider`
- legacy `attackEntityFrom(DamageSource source, float damage)` on `EntityCavenicSpider` for incoming-damage regression context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` on `EntityCavenicSpider` for orb-drop regression context
- legacy `BossInfoServer bossInfo` and `ParticleCrazyMob` references on `EntityCrazySpider` for preserved-boundary context

## Exact Legacy Behavior Found

- `EntityCrazySpider` does not override `attackEntityAsMob(Entity entity)` directly.
- It inherits `EntityCavenicSpider#attackEntityAsMob(Entity entity)`.
- `EntityCrazySpider` only overrides the power helpers that feed that inherited melee branch:
  - `getBlindnessAttackPower()`
  - `getPoisonAttackPower()`
- The inherited melee branch first calls `super.attackEntityAsMob(entity)`.
- Blindness and poison are only considered after that vanilla melee hit succeeds.
- The inherited branch only applies effects when the target is an `EntityLivingBase`.
- The inherited branch skips blindness when the target is already blinded.
- The inherited branch skips poison when the target is already poisoned.
- The inherited branch uses `sec * 20` tick durations.
- The legacy `PotionEffect` constructor used there leaves both effects on the default amplifier `0`.

## Exact Legacy Blindness Behavior

- `EntityCrazySpider#getBlindnessAttackPower()` returns seconds:
  - default branch: `5`
  - `NORMAL`: `10`
  - `HARD`: `20`
- In practice that means:
  - easy and peaceful fall through the default branch
  - easy and peaceful use `100` ticks
  - normal uses `200` ticks
  - hard uses `400` ticks

## Exact Legacy Poison Behavior

- `EntityCrazySpider#getPoisonAttackPower()` returns seconds:
  - default branch: `3`
  - `NORMAL`: `5`
  - `HARD`: `8`
- In practice that means:
  - easy and peaceful fall through the default branch
  - easy and peaceful use `60` ticks
  - normal uses `100` ticks
  - hard uses `160` ticks

## Reborn Mapping

- Reborn keeps `CrazySpider extends Spider`.
- Reborn restores the legacy melee-effect branch directly in `CrazySpider` instead of inheriting from Reborn `CavenicSpider`.
- The modern melee hook is `doHurtTarget(Entity target)`.
- Reborn calls `super.doHurtTarget(target)` first and stores whether the vanilla melee hit succeeded.
- Reborn only considers combat effects when:
  - the hit succeeded
  - `target instanceof LivingEntity`
- Reborn keeps narrow helpers for explicit mapping and deterministic tests:
  - `tryApplyLegacyCombatEffects(LivingEntity target, boolean attackSucceeded)`
  - `tryApplyLegacyBlindnessOnHit(LivingEntity target)`
  - `tryApplyLegacyPoisonOnHit(LivingEntity target)`
  - `getLegacyBlindnessDurationTicks(Difficulty difficulty)`
  - `getLegacyPoisonDurationTicks(Difficulty difficulty)`
- Reborn uses:
  - `MobEffects.BLINDNESS`
  - `MobEffects.POISON`
  - `MobEffectInstance`

## Exact Reborn Behavior

- Blindness and poison are applied only after a successful melee hit.
- Only living targets can receive the effects.
- Targets that already have blindness are left unchanged.
- Targets that already have poison are left unchanged.
- `LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS = 100`
- `LEGACY_BLINDNESS_DURATION_NORMAL_TICKS = 200`
- `LEGACY_BLINDNESS_DURATION_HARD_TICKS = 400`
- `LEGACY_POISON_DURATION_DEFAULT_TICKS = 60`
- `LEGACY_POISON_DURATION_NORMAL_TICKS = 100`
- `LEGACY_POISON_DURATION_HARD_TICKS = 160`
- `LEGACY_BLINDNESS_AMPLIFIER = 0`
- `LEGACY_POISON_AMPLIFIER = 0`
- Easy and peaceful difficulty fall back to `100` ticks (`5` seconds) for blindness.
- Normal difficulty uses `200` ticks (`10` seconds) for blindness.
- Hard difficulty uses `400` ticks (`20` seconds) for blindness.
- Easy and peaceful difficulty fall back to `60` ticks (`3` seconds) for poison.
- Normal difficulty uses `100` ticks (`5` seconds) for poison.
- Hard difficulty uses `160` ticks (`8` seconds) for poison.
- The modern `MobEffectInstance` constructor used here keeps the vanilla default visible/icon flags while matching the legacy zero-amplifier behavior.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazySpider` inherits the actual melee implementation from `EntityCavenicSpider`.
- Reborn `CrazySpider` intentionally extends vanilla `Spider`, not Reborn `CavenicSpider`.
- That baseline decision stays in place so Crazy Spider does not silently inherit staged Reborn natural-spawn, orb-drop, incoming-damage, boss-event or particle behavior before each slice is inspected honestly.
- Reborn therefore copies only the confirmed outgoing-combat branch explicitly onto `CrazySpider`.

## What Stays Unchanged

- Loot/orb behavior remains unchanged.
- Damage-intake behavior remains unchanged.
- Boss bar / sky-darkening remains unchanged.
- Particle trail remains unchanged.
- Attributes and max-health clamp remain unchanged.
- Natural-spawn deferral remains unchanged.
- Vanilla spider loot baseline remains unchanged.
- Cavenia remains out of scope.
- Magic-book systems remain out of scope.
- Broad custom AI/pathfinding remains out of scope.

## Testing

- Resource tests pin the entity-local melee hook, the successful-hit helper flow, the blindness/poison effect constants, the exact duration-mapping helpers and the continued absence of natural-spawn and broad custom-AI additions.
- Documentation tests pin the legacy source references, the inherited-melee mapping, the exact difficulty-scaled blindness/poison durations, the existing-effect skip behavior and the preserved loot/damage/boss/particle boundaries.
- NeoForge GameTest runtime smoke covers:
  - crazy spider successful-hit blindness/poison runtime smoke
  - crazy spider exact difficulty-duration mapping smoke
  - crazy spider existing-effect skip smoke
  - vanilla spider no-blindness/no-poison comparison smoke
  - continued vanilla spider loot-table baseline
  - continued inherited `1/8` orb-drop wiring
  - continued inherited fall/fire incoming-damage behavior
  - continued red progress boss-event wiring
  - continued shared `crazy_mob` particle mapping
  - continued no-natural-spawn boundary
  - continued no-broad-custom-AI boundary

## Still Out Of Scope

- Crazy Spider natural spawning
- Crazy Spider loot changes
- Crazy Spider damage-intake changes
- Crazy Spider boss bar / sky-darkening changes
- Crazy Spider particle trail changes
- broad custom AI/pathfinding
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
