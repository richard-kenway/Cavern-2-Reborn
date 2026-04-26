# Cavenic Bear Passive / Movement AI Boundary MVP

This note documents the remaining passive and movement AI boundary for the existing `cavernreborn:cavenic_bear`.

It does not add a new mob, new loot, new spawn rules, new damage behavior, new hostile-targeting rules, new melee behavior, new panic behavior, taming, riding, mount behavior or Cavenia content. It inspects the remaining non-combat legacy `EntityCavenicBear` goals and records why no separate Reborn gameplay patch is needed yet.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- legacy `initEntityAI()`
- legacy `EntityAISwimming`
- legacy `EntityAIFollowParent`
- legacy `EntityAIWander`
- legacy `EntityAIWatchClosest`
- legacy `EntityAILookIdle`
- current vanilla `PolarBear`
- current vanilla `FloatGoal`
- current vanilla `FollowParentGoal`
- current vanilla `RandomStrollGoal`
- current vanilla `LookAtPlayerGoal`
- current vanilla `RandomLookAroundGoal`

## Exact Legacy Passive / Movement AI Behavior Found

Legacy `EntityCavenicBear` overrides `initEntityAI()` and does not call `super.initEntityAI()`.

The remaining non-target, non-melee, non-panic legacy goals are:

- priority `0`: `EntityAISwimming(this)`
- priority `4`: `EntityAIFollowParent(this, 1.25D)`
- priority `5`: `EntityAIWander(this, 1.0D)`
- priority `6`: `EntityAIWatchClosest(this, EntityPlayer.class, 6.0F)`
- priority `7`: `EntityAILookIdle(this)`

These goals do not add Cavenia-specific behavior, do not change loot or spawn values, and do not contain a separate legacy Cavenic helper body beyond the standard vanilla 1.12.2 goal classes.

## Modern PolarBear Comparison

Modern `PolarBear#registerGoals()` already provides the direct modern equivalents on the same priorities once the earlier bounded Cavenic Bear slices are applied:

- priority `0`: `FloatGoal(this)`
- priority `4`: `FollowParentGoal(this, 1.25)`
- priority `5`: `RandomStrollGoal(this, 1.0)`
- priority `6`: `LookAtPlayerGoal(this, Player.class, 6.0F)`
- priority `7`: `RandomLookAroundGoal(this)`

That comparison is equivalent enough for this bounded slice:

- legacy `EntityAISwimming` maps directly to modern `FloatGoal`
- legacy follow-parent speed `1.25D` already matches modern `FollowParentGoal(this, 1.25)`
- legacy wander speed `1.0D` already matches modern `RandomStrollGoal(this, 1.0)`
- legacy watch-player range `6.0F` already matches modern `LookAtPlayerGoal(this, Player.class, 6.0F)`
- legacy idle-look behavior is already covered by modern `RandomLookAroundGoal`

The remaining drift is implementation detail inside vanilla pathfinding internals, not a small source-confirmed gameplay difference worth porting as a separate Reborn patch.

## Exact Reborn Outcome

- No gameplay code is added in this slice.
- `CavenicBear` continues to call `super.registerGoals()`.
- The accepted hostile-targeting slice remains unchanged.
- The accepted melee-goal slice remains unchanged.
- The accepted panic-goal slice remains unchanged.
- The remaining passive and movement goals stay inherited from modern `PolarBear`.

## What Remains Unchanged

- swimming, follow-parent, wander, watch-player and idle-look behavior stay on the inherited modern vanilla base
- hostile targeting remains unchanged
- melee behavior remains unchanged
- panic behavior remains unchanged
- anger behavior remains unchanged
- attributes remain unchanged
- natural spawning remains unchanged
- max spawn cluster size remains unchanged
- vanilla polar bear loot baseline remains unchanged
- source-confirmed bear custom loot/orb-drop absence remains unchanged
- fall/fire damage behavior remains unchanged

## Why No Separate Reborn Patch Was Added

- The inspected legacy passive goal stack already matches the modern vanilla `PolarBear` layout closely enough after the earlier target, melee and panic slices.
- Adding a second bear-specific follow-parent, wander, watch or look rewrite here would only duplicate current vanilla behavior.
- The remaining differences are better treated as manual-feel/pathfinding validation, not as a bounded parity patch without a clearer source-confirmed gameplay drift.

## Testing

- Resource tests pin that `CavenicBear` still uses `super.registerGoals()` and does not add a separate passive-goal rewrite for float, follow-parent, wander, watch-player or idle-look behavior.
- Documentation tests pin the inspected legacy passive-goal list, the modern `PolarBear` comparison and the explicit no-code-needed boundary.
- `docs/runtime-smoke.md` now records the explicit passive/movement AI equivalence boundary while keeping long-running passive pathfinding feel on the manual checklist.

## Still Out Of Scope

- broader anger rewrites
- taming, riding and mount behavior
- special attacks and transformations
- Cavenia-specific behavior
- additional Cavenic mobs
- broad AI or combat-system refactors

actual long-running passive pathfinding feel remains manual
