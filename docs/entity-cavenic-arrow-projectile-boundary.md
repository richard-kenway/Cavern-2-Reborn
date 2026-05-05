# EntityCavenicArrow / Projectile Boundary

This slice inspects the legacy `EntityCavenicArrow` path and pins the current Reborn projectile boundary without widening the active bow or skeleton projectile pipeline.

## Legacy References Inspected

- `cavern.entity.projectile.EntityCavenicArrow`
- `cavern.item.ItemBowCavenic`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.entity.CaveEntityRegistry`
- `cavern.client.CaveRenderingRegistry`
- legacy searches for `EntityCavenicArrow`, `getArrow`, `attackEntityWithRangedAttack`, `createArrow`, `onHit`, `onHitEntity`, `onHitBlock`, `setDamage`, `setIsCritical`, `setKnockbackStrength`, `setFire`, `pickupStatus`, `Power`, `Punch`, `Flame`, `Infinity`, `renderer`, `RenderCavenicArrow`, `NBT` and `DataParameter`

## Exact Legacy `EntityCavenicArrow` Behavior Found

- Legacy `EntityCavenicArrow` exists as a distinct class.
- It extends `EntityTippedArrow`.
- It declares only the three normal constructors.
- Its only custom behavior is:
  - call `super.onUpdate()`
  - if `timeInGround > 10`, call `setDead()`
- I found no direct override for:
  - damage
  - gravity
  - drag
  - pickup status
  - critical hits
  - knockback
  - flame
  - punch
  - power
  - custom entity-hit behavior
  - custom block-hit behavior
  - custom NBT/data-parameter state

## Exact Legacy Creation Paths Found

- `ItemBowCavenic` player shots do not create `EntityCavenicArrow`.
- Legacy player shots use:
  - vanilla ammo `ItemArrow#createArrow(...)` for `NORMAL` and `SNIPE`
  - `EntityRapidArrow` for `RAPID`
  - `EntityTorchArrow` for `TORCH`
- `EntityAIAttackCavenicBow` does not create the projectile directly.
- It only calls `attackEntityWithRangedAttack(...)`.
- `EntityCavenicSkeleton#getArrow(float)` creates the legacy projectile path:
  - spectral offhand ammo keeps `EntitySpectralArrow`
  - otherwise it creates `new EntityCavenicArrow(world, this)`
  - enchantment effects are applied through `setEnchantmentEffectsFromEntity(this, dist)`
  - tipped-arrow offhand ammo copies potion contents onto that arrow
- Legacy `EntityCrazySkeleton` extends legacy `EntityCavenicSkeleton`, so it inherits that arrow factory.

## Registration / Renderer Findings

- I found no dedicated `cavenic_arrow` registration in `CaveEntityRegistry`.
- I found no dedicated `RenderCavenicArrow` registration in `CaveRenderingRegistry`.
- I found no dedicated projectile texture asset for `EntityCavenicArrow`.

## Current Reborn Mapping

- Reborn player `cavernreborn:cavenic_bow` shots intentionally stay on the vanilla-compatible arrow path.
- Reborn `RAPID`, `SNIPE` and `TORCH` slices explicitly pin vanilla-arrow runtime identity.
- Reborn `TORCH` mode uses a marked vanilla arrow plus `CavenicBowTorchEvents`.
- Reborn `CrazySkeleton` ranged AI intentionally keeps the current local `performRangedAttack(...)` bridge:
  - it creates the projectile through the current bow-compatible arrow path
  - it calls `BowItem.customArrow(...)`
  - it does not register or spawn `cavernreborn:cavenic_arrow`

## Why No Projectile Entity Was Added

- The source-confirmed gameplay difference on `EntityCavenicArrow` is very small: it despawns after more than `10` ticks in the ground.
- In practice, the inspected legacy class is a thin tipped-arrow shell around that one in-ground despawn rule.
- Porting that directly would still require new Reborn entity registration and client renderer wiring.
- It would also reopen the current documented vanilla-arrow guarantees around:
  - `Cavenic Bow` player shots
  - `RAPID`
  - `SNIPE`
  - `TORCH`
  - the current Crazy Skeleton local ranged bridge
- The current Reborn line already preserves the more important bounded projectile behavior for the active bow-mode slices, while the remaining `EntityCavenicArrow` gap is narrow and mostly skeleton-only.

## Relationship To Existing Slices

- `docs/cavenic-bow-baseline-mvp.md` keeps custom projectile entities out of scope for the item baseline.
- `docs/cavenic-bow-snipe-mode-mvp.md` keeps `SNIPE` on vanilla arrows.
- `docs/cavenic-bow-rapid-mode-mvp.md` keeps `RAPID` on vanilla arrows and does not port `EntityRapidArrow`.
- `docs/cavenic-bow-torch-mode-mvp.md` keeps `TORCH` on marked vanilla arrows and does not port `EntityTorchArrow`.
- `docs/cavenic-bow-release-semantics-mvp.md` keeps the real release path on vanilla-compatible arrows.
- `docs/entity-rapid-torch-arrow-projectile-boundary.md` covers the separate player-shot `EntityRapidArrow` and `EntityTorchArrow` gaps.
- `docs/crazy-skeleton-ranged-ai-mvp.md` restores the legacy goal cadence and weapon switching while keeping the current Reborn vanilla-compatible projectile bridge.

## What Full Projectile Parity Would Still Require

- Decide whether to restore a skeleton-only `CavenicArrow` path or a broader projectile path.
- If skeleton-only, wire the projectile only through the source-confirmed skeleton attack factory.
- Reconcile that with the current Reborn `CrazySkeleton` local ranged bridge and any later `CavenicSkeleton` ranged-AI follow-up.
- Re-test all current `Cavenic Bow` mode guarantees so player shots do not accidentally stop being vanilla-arrow-based.
- Only add renderer or asset work if a real custom client-visible projectile identity is actually needed.

## What Stays Unchanged

- no `Cavenic Bow` mode changes
- no `CrazySkeleton` goal-cadence or equipment changes
- no natural-spawn changes
- no Cavenia work
- no packets
- no broad renderer/client changes
- no `EntityRapidArrow`
- no `EntityTorchArrow`

## Testing

- Resource tests pin that Reborn still does not register `cavernreborn:cavenic_arrow` or a projectile renderer for it.
- Documentation tests pin the exact legacy `EntityCavenicArrow` findings and the current no-port decision.
- NeoForge GameTest runtime smoke pins:
  - no `cavernreborn:cavenic_arrow` entity type at runtime
  - current `CrazySkeleton` ranged attack still spawning a vanilla-compatible arrow entity
  - current `Cavenic Bow` mode slices remaining on their existing vanilla-arrow paths
