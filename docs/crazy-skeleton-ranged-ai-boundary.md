# Crazy Skeleton Ranged AI Boundary

This note documents the legacy Crazy Skeleton combat-goal branch that remains intentionally deferred after the bounded equipment-only follow-up.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.item.ItemBowCavenic`

## Exact Legacy `EntityAIAttackCavenicBow` Behavior Found

Legacy `EntityCrazySkeleton#initCustomAI()` installs:

- `EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`
- melee fallback speed `1.35D`

The inherited `EntityCavenicSkeleton#setCombatTask()` still controls the melee-versus-ranged switch.
The exact method boundary is `setCombatTask()`.

That task switch means:

- when a bow is held, it schedules the ranged goal
- when no bow is held, it switches to melee

Inside legacy `EntityAIAttackCavenicBow`:

- the attacker only runs while holding a bow
- it uses a separate strafe/visibility loop instead of the vanilla modern goal timing
- it starts with `attackCooldown = 20`
- it rapid-fires after that cooldown through an `attackRapid` counter
- `ItemBowCavenic` gets a special cadence path because the goal checks `instanceof ItemBowCavenic`
- the `ItemBowCavenic` path halves the configured attack-speed value with a lower bound of `1`
- ranged shots call `attackEntityWithRangedAttack(target, ItemBow.getArrowVelocity(5))`

That is a real combat-goal identity, not just a held-item assignment.

## Why It Remains Deferred

The current Reborn baseline already has a working `Cavenic Bow` item and vanilla `Skeleton` combat behavior.
Porting the full legacy goal would be a separate combat slice because it would change:

- attack cadence
- movement/strafing behavior
- melee fallback behavior
- ranged engagement distance
- how often the held `ItemBowCavenic` fires

It would also need honest verification against the current Reborn bow behavior without changing that bow implementation.

That is too broad for the bounded equipment-only increment.
The custom ranged AI remains intentionally deferred.

## What Was Restored Instead

The equipment-only follow-up documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md` restores the held bow identity without porting the custom goal:
This equipment-only follow-up restores the held bow identity without porting the custom goal.

- guaranteed `Cavenic Bow`
- guaranteed `Infinity I`
- mainhand drop chance `1.0F`

## What Reborn Still Does Not Add

- no `EntityAIAttackCavenicBow` port
- no custom `registerGoals()` override
- no custom `reassessWeaponGoal()` override
- no custom `performRangedAttack(...)` override
- no melee fallback rewrite
- no bow/projectile rewrite
- no natural-spawn, inherited orb-drop, damage, boss-bar or particle follow-up

## Testing Boundary

- Resource tests pin that `CrazySkeleton` now references `CAVENIC_BOW` and `Infinity`, while still not adding `EntityAIAttackCavenicBow` or any custom ranged-AI overrides.
- Runtime smoke verifies the equipment branch is present and that the class still lacks custom ranged-goal overrides.
- Actual ranged combat feel remains manual / follow-up.

## Out Of Scope

- full `EntityAIAttackCavenicBow` port
- melee fallback parity
- rapid-fire cadence parity
- natural spawning
- loot / `cavenic_orb`, now documented separately in `docs/crazy-skeleton-loot-mvp.md`
- damage behavior
- boss bar / sky-darkening
- particle trail
- Crazy Creeper / Crazy Spider
- Cavenia
