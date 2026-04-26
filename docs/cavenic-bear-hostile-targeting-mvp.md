# Cavenic Bear Legacy Hostile Targeting MVP

This note documents the bounded hostile-targeting follow-up for the existing `cavernreborn:cavenic_bear`.

It does not add a new mob, new loot, new spawn rules, new damage behavior, taming, riding, mount behavior, Cavenia content or a broad bear AI rewrite. It restores only the small source-confirmed target-selector slice that maps cleanly to current NeoForge `PolarBear`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- legacy `initEntityAI()` override
- legacy inner `AIHurtByTarget`
- legacy inner `AIMeleeAttack`
- legacy inner `AIPanic`
- legacy `EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 20, true, true, null)`
- legacy `EntityAIAttackMelee`
- legacy `EntityAIHurtByTarget`
- legacy `EntityPolarBear`

## Exact Legacy AI / Targeting Behavior Found

Legacy `EntityCavenicBear` overrides `initEntityAI()` and does not call `super.initEntityAI()`.

Its full legacy stack is broader than one target hook:

- `EntityAISwimming`
- `AIMeleeAttack`
- `AIPanic`
- `EntityAIFollowParent`
- `EntityAIWander`
- `EntityAIWatchClosest(this, EntityPlayer.class, 6.0F)`
- `EntityAILookIdle`
- `AIHurtByTarget`
- `EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 20, true, true, null)`

The source-confirmed hostile-targeting portion is:

- players are accepted as nearest hostile targets
- non-player nearest-hostile targets are not added by the legacy class
- `AIHurtByTarget` retaliates against the current attacker
- `AIHurtByTarget` alerts nearby same-type bears
- alerted allies only keep the attacker when they are adult `EntityPolarBear`
- baby cavenic bears keep retaliating after alerting nearby adults

The legacy class does not use the newer vanilla `Fox` target branch or a universal-anger reset goal.

## Exact Reborn Mapping

- Reborn keeps `CavenicBear` on the vanilla `PolarBear` base class.
- Reborn now overrides `registerGoals()`.
- The implementation calls `super.registerGoals()` first so the vanilla movement/follow-parent/wander/look stack stays intact before the bounded follow-up swaps the target-selector slice.
- Reborn then removes only the vanilla target-selector goals:
  - `HurtByTargetGoal`
  - `NearestAttackableTargetGoal`
  - `ResetUniversalAngerTargetGoal`
- Reborn re-adds only the bounded legacy player-target and hurt-by-target hostility slice:
  - `LegacyCavenicBearHurtByTargetGoal`
  - `LegacyNearestAttackablePlayerTargetGoal`
- Reborn also exposes `isLegacyHostileTarget(LivingEntity target)` so the accepted nearest-hostile target class is explicit and regression-testable.

## Exact Reborn Behavior

- Players are the only accepted nearest hostile targets for the restored legacy player-target goal.
- Fox targets are not restored.
- The newer vanilla reset-universal-anger target goal is not restored.
- Adult cavenic bears retaliate against recent attackers and alert nearby adult cavenic bears to the same attacker.
- Baby cavenic bears keep their own retaliation target after alerting nearby adult cavenic bears.
- This slice does not add a custom `setTarget(...)` rejection hook, so previous-target clear/preserve semantics are unchanged outside the restored goal behavior itself.
- The change is limited to target-selector behavior.
- the hostile-targeting slice itself left the vanilla panic goal in place, and the later bounded panic follow-up is now documented separately in `docs/cavenic-bear-panic-behavior-mvp.md`.
- vanilla polar bear movement, follow-parent, wandering and look goals remain unchanged, and the remaining passive/movement AI boundary is now documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`.
- the bounded melee-attack follow-up is documented separately in `docs/cavenic-bear-melee-attack-mvp.md`.
- anger behavior remains otherwise unchanged
- attributes remain unchanged
- natural spawning remains unchanged
- max spawn cluster size remains unchanged
- vanilla polar bear loot baseline remains unchanged
- source-confirmed bear custom loot/orb-drop absence remains unchanged
- fall/fire damage behavior remains unchanged

## Why This Slice Stays Bounded

- The legacy class replaced more than target goals, but most of that stack overlaps closely with modern `PolarBear`.
- Reborn therefore ports only the source-confirmed player-target and hurt-by-target hostility slice instead of rewriting the whole polar-bear AI stack.
- Broader anger/pathfinding feel and any future special bear behavior remain separate follow-up work beyond the now-separated panic follow-up.

## Testing

- Resource tests pin the `registerGoals()` override, the bounded target-goal removal/re-addition logic, the player-target helper and the continued no-broad-AI boundary.
- Documentation tests pin the inspected legacy `initEntityAI()` structure, the player-target goal, the hurt-by-target behavior and the explicit out-of-scope boundaries.
- NeoForge GameTest runtime smoke covers:
  - cavenic bear legacy hostile player-target goal smoke
  - cavenic bear legacy hurt-by-target retaliation smoke
  - cavenic bear non-player target baseline smoke
  - deterministic adult shared-retaliation comparison against vanilla polar bear
  - deterministic baby hurt-by-target comparison against vanilla polar bear
  - continued vanilla polar bear loot-table baseline
  - continued natural-spawn and fall/fire stability

## Still Out Of Scope

- broader custom bear AI
- anger rewrites beyond the bounded hostile-targeting slice
- taming, riding and mount behavior
- special attacks and transformations
- Cavenia-specific behavior
- additional Cavenic mobs
- broad combat or AI-system refactors

actual long-running anger/pathfinding feel remains manual
