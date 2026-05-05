# Crazy Skeleton Custom Ranged AI / EntityAIAttackCavenicBow MVP

This note documents the bounded ranged-combat follow-up for the existing `cavernreborn:crazy_skeleton`.

It restores only the legacy Crazy Skeleton combat-goal identity. It does not change natural spawning, loot, incoming damage, boss bar / sky-darkening, particle trail, guaranteed `Cavenic Bow` equipment, broad `CavenicBowItem` behavior or other crazy mobs.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.item.ItemBowCavenic`
- `net.minecraft.entity.ai.EntityAIAttackMelee`
- `net.minecraft.entity.ai.EntityAIAttackRangedBow`

## Exact Legacy `EntityAIAttackCavenicBow` Behavior Found

- `EntityCrazySkeleton#initCustomAI()` installs `EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`.
- The same method also creates a melee fallback goal with melee fallback speed `1.35D`.
- The inherited `setCombatTask()` method on `EntityCavenicSkeleton` removes both combat goals and re-adds either the ranged or melee branch at priority `4`.
- The ranged branch only runs while the skeleton holds a bow in its main hand.
- Legacy seeds the first ranged burst with `attackCooldown = 20`.
- When the branch has to reset because sight is lost for too long or because `attackTime > 200`, it uses `attackCooldown = 50`.
- The goal only enters its stationary strafing loop after `seeTime >= 15`.
- The strafe-direction jitter is reevaluated when `strafingTime >= `5``.
- The ranged fire path calls `attackEntityWithRangedAttack(target, ItemBow.getArrowVelocity(5))`.
- The `ItemBowCavenic` path halves the configured attack-speed value, but the configured value is already `1`, so Crazy Skeleton effectively still fires every successful rapid step.
- Legacy keeps the projectile implementation separate through `EntityCavenicSkeleton#getArrow(float)`, which can produce `EntityCavenicArrow`.

## Equipment Relationship

- The ranged-goal branch assumes the already restored guaranteed `Cavenic Bow` mainhand identity.
- It also assumes the already restored forced `Infinity` enchantment and `1.0F` mainhand drop chance.
- The combat-goal slice does not need to change that equipment branch.

## Reborn Mapping

Reborn implements a local Crazy Skeleton-only goal swap on `CrazySkeleton`.

- `CrazySkeleton` keeps the vanilla `Skeleton` base.
- Reborn overrides `registerGoals()` to initialize and install the local legacy-equivalent combat goals.
- Reborn overrides `reassessWeaponGoal()` to mirror the legacy held-weapon swap without changing vanilla `Skeleton` or Reborn `CavenicSkeleton`.
- Reborn adds:
  - `LegacyCrazySkeletonCavenicBowAttackGoal`
  - `LegacyCrazySkeletonMeleeAttackGoal`
- Reborn also adds a tiny local `performRangedAttack(LivingEntity target, float distanceFactor)` bridge so the local goal can actually fire while `CrazySkeleton` is holding `CavenicBowItem`.
- The local ranged goal keeps the source-confirmed constants:
  - movement speed `0.99D`
  - max attack distance `6.0F`
  - attack speed `1`
  - start cooldown `20`
  - reset cooldown `50`
  - minimum see-time to strafe `15`
  - strafing toggle interval `5`
  - lost-sight stop threshold `-20`
  - continuous attack reset threshold `200`
- Reborn maps the legacy draw power call to `BowItem.getPowerForTime(5)`.
- Reborn keeps the current Reborn vanilla-compatible `CavenicBowItem` projectile path.
- When no real projectile stack is found, the local bridge falls back to a vanilla `Items.ARROW` stack instead of changing global bow behavior.
- Reborn does not port `EntityCavenicArrow`.
- The exact projectile-parity gap is now documented separately in `docs/entity-cavenic-arrow-projectile-boundary.md`.

## Why This Mapping Stays Bounded

- The goal swap is local to `CrazySkeleton`.
- It does not change vanilla `Skeleton`.
- It does not change Reborn `CavenicSkeleton`.
- It does not change global `CavenicBowItem` mode state, Rapid, Snipe, Torch or release semantics.
- It restores the legacy movement/cadence/weapon-switch identity without opening a broader projectile-entity rewrite.

## What Stays Unchanged

- loot/orb unchanged
- damage unchanged
- boss unchanged
- particle unchanged
- natural spawning remains Cavenia-boundary-only
- guaranteed `Cavenic Bow` + `Infinity` equipment remains documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md`
- no fake normal `CAVERN` crazy spawning was added
- no broad bow/projectile rewrite was added

## Testing

- Resource tests pin:
  - the local goal constants
  - the local `registerGoals()` / `reassessWeaponGoal()` hooks
  - the preserved guaranteed `Cavenic Bow` + `Infinity` equipment branch
  - continued absence of global vanilla skeleton or Reborn `CavenicSkeleton` AI changes
- Documentation tests pin:
  - the exact legacy references
  - the local-goal mapping
  - the explicit `EntityCavenicArrow` non-port note
  - the dedicated `docs/entity-cavenic-arrow-projectile-boundary.md` follow-up reference
  - the unchanged loot/damage/boss/particle/natural-spawn boundaries
- NeoForge GameTest runtime smoke covers:
  - crazy skeleton legacy cavenic bow ranged-goal wiring smoke
  - crazy skeleton legacy ranged-goal constants smoke
  - crazy skeleton local melee fallback switch smoke
  - crazy skeleton vanilla and cavenic skeleton no-global-ai-change smoke
  - continued equipment, loot, damage, boss, particle and natural-spawn-boundary stability

Actual long-running ranged combat feel remains manual.

## Still Out Of Scope

- `EntityCavenicArrow`
- broader `CavenicBowItem` rewrites
- Crazy Skeleton natural spawning
- Cavenia
- Crazy Creeper fuse/explosion
- magic-book or spell systems
