# Cavenic Bear Legacy Panic Behavior MVP

This note documents the bounded panic-behavior follow-up for the existing `cavernreborn:cavenic_bear`.

It does not add a new mob, new loot, new spawn rules, new damage behavior, new hostile-targeting rules, new melee behavior, taming, riding, mount behavior or Cavenia content. It restores only the small source-confirmed panic-goal drift from legacy `EntityCavenicBear`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- legacy inner `AIPanic`
- legacy `EntityAIPanic`
- legacy `shouldExecute()`
- legacy `EntityCavenicBear.this.isBurning() && super.shouldExecute()`
- current vanilla `PolarBear`
- current vanilla `PanicGoal`

## Exact Legacy Panic Behavior Found

Legacy `EntityCavenicBear` defines an inner `AIPanic` that extends `EntityAIPanic`.

The source-confirmed behavior is:

- `super(EntityCavenicBear.this, 2.0D)`
- the legacy inner goal overrides `shouldExecute()`
- that override returns `EntityCavenicBear.this.isBurning() && super.shouldExecute()`

That means the legacy Cavenic Bear panic hook is narrow:

- panic is burning-only
- the goal keeps the vanilla panic movement/pathfinding base from `EntityAIPanic`
- the goal speed stays at `2.0D`
- non-burning panic causes are intentionally ignored by the legacy override
- the goal does not change hostile targeting, melee reach, standing/warning melee behavior, anger state, loot, spawn values or Cavenia behavior

## Comparison With Modern Vanilla PolarBear

The modern vanilla `PolarBear` is not equivalent here.

- Modern adult `PolarBear` registers modern vanilla `PanicGoal(this, 2.0, ... DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES)`.
- That means adult vanilla polar bears panic for broader environmental causes such as freeze, cactus, hot floor, in-fire, lava, lightning and on-fire damage.
- The inspected legacy Cavenic Bear instead narrows panic to the burning state itself.

So the speed matches, but the trigger semantics do not.

## Exact Reborn Mapping

- Reborn keeps `CavenicBear` on the vanilla `PolarBear` base class.
- Reborn keeps the accepted hostile-targeting slice unchanged.
- Reborn keeps the accepted melee-goal slice unchanged.
- Reborn keeps follow-parent, wandering, watch/look and broader pathfinding behavior on the vanilla base.
- Reborn overrides `registerGoals()` only to swap the conflicting panic goal on `goalSelector`:
  - remove the vanilla `PanicGoal`
  - add `LegacyCavenicBearPanicGoal extends PanicGoal`
- `LegacyCavenicBearPanicGoal` keeps the legacy speed value `2.0D`.
- `LegacyCavenicBearPanicGoal` restores the narrow trigger through `shouldPanic()` with `return CavenicBear.this.isOnFire();`.

## Exact Reborn Behavior

- Panic is burning-only.
- panic speed stays at `2.0D`.
- movement/pathfinding remains on the vanilla `PanicGoal` base.
- Adult Cavenic Bears no longer inherit the broader vanilla adult environmental panic causes such as freeze for this bounded slice.
- Hostile targeting remains unchanged.
- Melee behavior remains unchanged.
- The remaining passive/movement AI boundary is now documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`.
- Anger behavior remains unchanged.
- Attributes remain unchanged.
- Natural spawning remains unchanged.
- Max spawn cluster size remains unchanged.
- Vanilla polar bear loot baseline remains unchanged.
- Source-confirmed bear custom loot/orb-drop absence remains unchanged.
- Fall/fire damage behavior remains unchanged.

## Why This Slice Stays Bounded

- The inspected drift is a small panic-trigger difference, not a broad AI rewrite.
- Reborn therefore swaps only the conflicting panic goal instead of replacing the whole polar-bear goal stack.
- The remaining passive/movement AI boundary is documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`.
- Broader anger/pathfinding feel remains separate follow-up work.

## Testing

- Resource tests pin the panic-goal swap, the `2.0D` speed and the burning-only trigger mapping.
- Documentation tests pin the inspected legacy `AIPanic` override, the modern vanilla comparison and the explicit out-of-scope boundaries.
- NeoForge GameTest runtime smoke covers:
  - cavenic bear legacy panic-goal registration smoke
  - cavenic bear legacy burning-only panic-trigger smoke
  - cavenic bear freeze panic rejection smoke
  - continued hostile-targeting stability
  - continued melee-goal stability
  - continued fall/fire, spawn, cluster-size and loot-baseline stability

## Still Out Of Scope

- broader anger rewrites
- taming, riding and mount behavior
- Cavenia-specific behavior
- additional Cavenic mobs
- broad AI or combat-system refactors

actual long-running panic/pathfinding feel remains manual
