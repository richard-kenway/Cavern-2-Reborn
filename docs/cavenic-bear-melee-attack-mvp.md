# Cavenic Bear Legacy Melee Attack MVP

This note documents the bounded melee-attack follow-up for the existing `cavernreborn:cavenic_bear`.

It does not add a new mob, new loot, new spawn rules, new damage behavior, new hostile-targeting rules, broader pathfinding rewrites, taming, riding, mount behavior or Cavenia content. It restores only the small source-confirmed melee-goal drift from legacy `EntityCavenicBear`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- legacy inner `AIMeleeAttack`
- legacy `EntityAIAttackMelee`
- legacy `checkAndPerformAttack(...)`
- legacy `getAttackReachSqr(...)`
- legacy `setStanding(...)`
- legacy `playWarningSound()`
- legacy `resetTask()`
- current vanilla `PolarBearMeleeAttackGoal`
- current vanilla `MeleeAttackGoal`

## Exact Legacy Melee Behavior Found

Legacy `EntityCavenicBear` defines an inner `AIMeleeAttack` that extends `EntityAIAttackMelee`.

The source-confirmed behavior is:

- `super(EntityCavenicBear.this, 1.25D, true)`
- `checkAndPerformAttack(...)` uses a legacy reach calculation from `getAttackReachSqr(...)`
- `getAttackReachSqr(...)` returns `4.0F + attackTarget.width`
- the legacy body is effectively `return 4.0F + attackTarget.width`
- if the target is within that reach and the attack cooldown is ready, the bear attacks immediately and clears `setStanding(false)`
- if the target is outside direct melee reach but still within `distance <= reachSq * 2.0D`, the bear enters the warning branch
- during the warning branch, the bear raises `setStanding(true)` and calls `playWarningSound()` once the cooldown is at `10` ticks or less
- if the target moves outside the warning branch, the bear clears `setStanding(false)` and resets the cooldown
- `resetTask()` also clears `setStanding(false)`

The legacy inner melee goal does not change target selection itself, does not add Cavenia-specific rules, and does not contain baby-only melee logic.

## Comparison With Modern Vanilla PolarBear

The modern vanilla `PolarBearMeleeAttackGoal` is close, but not identical:

- it still uses `1.25D` speed and long-memory `true`
- it still uses standing/warning behavior
- it now checks direct melee hits through the generic `MeleeAttackGoal` / `Mob.isWithinMeleeAttackRange(...)` path
- it uses a broader warning threshold based on the modern vanilla `(targetBbWidth + 3.0F)^2` check

That means current vanilla `PolarBear` warns at a wider distance and uses a different melee attack reach than the inspected legacy Cavenic Bear.

## Exact Reborn Mapping

- Reborn keeps `CavenicBear` on the vanilla `PolarBear` base class.
- Reborn keeps the accepted hostile-targeting slice unchanged.
- Reborn keeps vanilla movement/pathfinding, follow-parent, wandering and look goals unchanged in this melee slice.
- Reborn overrides `registerGoals()` only to swap the melee goal on `goalSelector`:
  - remove the vanilla `PolarBearMeleeAttackGoal`
  - add `LegacyCavenicBearMeleeAttackGoal extends MeleeAttackGoal`
- The restored goal keeps the legacy `1.25D` speed and long-memory `true` values.
- The restored goal uses the legacy direct-hit reach formula `4.0D + target.getBbWidth()`.
- The restored goal uses the legacy warning branch `distance <= reachSq * 2.0D`.
- The restored goal keeps the legacy standing/warning behavior through `setStanding(true)` and `playWarningSound()`.
- The restored goal clears `setStanding(false)` on successful hits, out-of-range checks and `stop()`.

## Exact Reborn Behavior

- Direct melee hits now use the source-confirmed legacy reach instead of the broader modern vanilla `isWithinMeleeAttackRange(...)` path.
- Standing/warning behavior now uses the narrower legacy warning window instead of the broader modern vanilla polar-bear threshold.
- Attack cooldown stays on the modern `MeleeAttackGoal` timing path, which still maps to the legacy `20`-tick cadence for this bounded slice.
- Hostile targeting remains unchanged.
- Panic behavior is now documented separately in `docs/cavenic-bear-panic-behavior-mvp.md`.
- Anger behavior remains unchanged.
- Attributes remain unchanged.
- Natural spawning remains unchanged.
- Max spawn cluster size remains unchanged.
- Vanilla polar bear loot baseline remains unchanged.
- Source-confirmed bear custom loot/orb-drop absence remains unchanged.
- Fall/fire damage behavior remains unchanged.

## Why This Slice Stays Bounded

- The inspected legacy drift is in the melee goal, not in a broad combat system.
- Reborn therefore replaces only the conflicting vanilla melee goal instead of rewriting the whole polar-bear AI stack.
- Follow-parent, wandering, watch/look and broader anger/pathfinding feel remain separate follow-up work.

## Testing

- Resource tests pin the melee-goal swap, the legacy reach formula, the legacy standing/warning branch and the continued no-broad-AI boundary.
- Documentation tests pin the inspected legacy `AIMeleeAttack` behavior, the modern vanilla comparison and the explicit out-of-scope boundaries.
- NeoForge GameTest runtime smoke covers:
  - cavenic bear legacy melee-goal registration smoke
  - cavenic bear legacy melee reach smoke
  - cavenic bear legacy standing-warning threshold smoke
  - continued hostile-targeting helper and hurt-by-target retaliation stability
  - continued fall/fire, spawn, cluster-size and loot-baseline stability

## Still Out Of Scope

- follow-parent, wander, watch and look goal parity
- broader anger rewrites
- taming, riding and mount behavior
- Cavenia-specific behavior
- additional Cavenic mobs
- broad combat or AI-system refactors

actual long-running pathfinding/anger feel remains manual
