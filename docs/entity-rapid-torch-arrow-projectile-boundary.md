# EntityRapidArrow / EntityTorchArrow Projectile Boundary

This slice inspects the legacy `EntityRapidArrow` and `EntityTorchArrow` paths and pins the current Reborn projectile boundary without widening the active `cavernreborn:cavenic_bow` projectile pipeline.

## Legacy References Inspected

- `cavern.entity.projectile.EntityRapidArrow`
- `cavern.entity.projectile.EntityTorchArrow`
- `cavern.entity.projectile.EntityCavenicArrow`
- `cavern.item.ItemBowCavenic`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.entity.CaveEntityRegistry`
- `cavern.client.CaveRenderingRegistry`
- legacy searches for `EntityRapidArrow`, `EntityTorchArrow`, `createCustomArrow`, `onHit`, `arrowHit`, `hurtResistantTime`, `BlockTorch`, `Blocks.TORCH`, `setTorchItem`, `renderer`, `RenderRapidArrow`, `RenderTorchArrow`, `registerEntityRenderingHandler`, `NBT` and `DataParameter`

## Exact Legacy `EntityRapidArrow` Behavior Found

- Legacy `EntityRapidArrow` exists as a distinct class.
- It extends `EntityTippedArrow`.
- It declares only the three normal constructors plus one override:
  - `arrowHit(EntityLivingBase living)`
- That override:
  - calls `super.arrowHit(living)`
  - checks `living.getTotalArmorValue() < 20`
  - sets `living.hurtResistantTime = 0`
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
  - custom block-hit behavior
  - custom renderer or texture
- Legacy player `ItemBowCavenic` `RAPID` shots create `EntityRapidArrow`.
- Legacy skeleton paths do not create `EntityRapidArrow`.

## Exact Legacy `EntityTorchArrow` Behavior Found

- Legacy `EntityTorchArrow` exists as a distinct class.
- It extends `EntityTippedArrow`.
- It stores one extra field:
  - `private ItemStack torchItem = ItemStack.EMPTY`
- It adds one setter:
  - `setTorchItem(ItemStack stack)`
- Its only real behavior is in `onHit(RayTraceResult rayTrace)`:
  - call `super.onHit(rayTrace)`
  - return immediately on entity hits
  - run only on the logical server
  - run only when `shootingEntity` is an `EntityPlayer`
  - temporarily swap the player main hand to `torchItem`, or to `new ItemStack(Blocks.TORCH)` when empty
  - call the held item's `onItemUse(...)` against the hit block / face / hit vector
  - restore the player's previous main-hand stack
  - if placement succeeds, play the wood place sound and `setDead()`
- This means the legacy Torch projectile delegated placement rules to normal item-use behavior rather than a custom block-placement algorithm.
- Because legacy `ItemBowCavenic#findTorch(...)` accepted any `BlockTorch` item, the projectile could forward whichever matching torch-like block item the player had.
- I found no direct override for:
  - damage
  - gravity
  - drag
  - pickup status
  - critical hits
  - fire
  - custom entity-hit damage
  - custom renderer or texture
- Legacy player `ItemBowCavenic` `TORCH` shots create `EntityTorchArrow`.
- Legacy skeleton paths do not create `EntityTorchArrow`.

## Registration / Renderer Findings

- I found no dedicated `rapid_arrow` registration in `CaveEntityRegistry`.
- I found no dedicated `torch_arrow` registration in `CaveEntityRegistry`.
- I found no dedicated `RenderRapidArrow` registration in `CaveRenderingRegistry`.
- I found no dedicated `RenderTorchArrow` registration in `CaveRenderingRegistry`.
- I found no dedicated projectile texture asset for either entity.

## Exact Legacy Creation Paths Found

- `ItemBowCavenic#createCustomArrow(...)` creates:
  - `new EntityRapidArrow(world, shooter)` for `RAPID`
  - `new EntityTorchArrow(world, shooter).setTorchItem(subAmmo)` for `TORCH`
- `NORMAL` and `SNIPE` stay on the normal ammo-arrow creation path.
- `EntityAIAttackCavenicBow` does not create either projectile directly.
- `EntityCavenicSkeleton#getArrow(float)` creates `EntityCavenicArrow`, not `EntityRapidArrow` or `EntityTorchArrow`.
- Legacy `EntityCrazySkeleton` inherits that skeleton-only `EntityCavenicArrow` factory.

## Current Reborn Mapping

- Reborn `RAPID` intentionally keeps the vanilla-compatible arrow path.
- Reborn `RAPID` now restores:
  - the bounded power ramp and velocity identity
  - the low-armor hurt-resistance reset through the separate follow-up documented in `docs/cavenic-bow-rapid-low-armor-hurt-resistance-mvp.md`
- Reborn `TORCH` intentionally keeps the vanilla-compatible arrow path.
- Reborn `TORCH` currently restores torch placement through:
  - a marked vanilla arrow
  - `CavenicBowTorchEvents`
  - direct placement of standing `minecraft:torch` and horizontal `minecraft:wall_torch`
- Reborn `TORCH` intentionally keeps the already accepted release semantics:
  - normal arrow ammo still uses the vanilla release path
  - torch consumption stays separate from arrow consumption
  - creative shooters can mark Torch shots without carrying torch stacks
- Reborn `CrazySkeleton` ranged AI keeps the current local vanilla-compatible projectile bridge documented in `docs/crazy-skeleton-ranged-ai-mvp.md`.

## Why No Projectile Entity Was Added

- The source-confirmed legacy `EntityRapidArrow` gameplay difference is narrow:
  - reset hurt resistance after a hit, but only when total armor is below `20`
- The source-confirmed legacy `EntityTorchArrow` behavior is also narrow:
  - delegate torch placement through temporary player-held item use on block hit
- Porting either entity directly would reopen current accepted Reborn guarantees around:
  - `RAPID` still spawning a vanilla arrow entity
  - `TORCH` still spawning a marked vanilla arrow entity
  - the current bounded release-semantics coverage
  - the current Crazy Skeleton ranged-AI projectile bridge
- The current Reborn Torch slice already preserves the important gameplay identity:
  - valid top-face torch placement
  - valid wall-torch placement
  - correct horizontal orientation
  - grief-safe no-placement failures
- The remaining Torch gap is that legacy delegated to arbitrary `BlockTorch` item use, while the accepted Reborn slice currently pins plain vanilla torch placement.
- The old Rapid hurt-resistance gap is now restored separately without adding `EntityRapidArrow`.
- The remaining meaningful Torch gap is still that legacy delegated to arbitrary `BlockTorch` item use while Reborn intentionally keeps the already accepted bounded plain-torch placement path.
- Those remaining gaps are narrow. They do not justify adding new projectile entity registrations and client/runtime surface area in this bounded slice.

## Relationship To Existing Slices

- `docs/cavenic-bow-rapid-mode-mvp.md` keeps `RAPID` on vanilla arrows and now explicitly defers `EntityRapidArrow`.
- `docs/cavenic-bow-rapid-low-armor-hurt-resistance-mvp.md` documents the restored legacy Rapid low-armor cooldown reset without widening the projectile entity path.
- `docs/cavenic-bow-torch-mode-mvp.md` keeps `TORCH` on marked vanilla arrows and now explicitly defers `EntityTorchArrow`.
- `docs/cavenic-bow-release-semantics-mvp.md` keeps the real release path on vanilla-compatible arrows and now points to this boundary for the remaining Rapid/Torch projectile gap.
- `docs/entity-cavenic-arrow-projectile-boundary.md` covers the separate skeleton-side `EntityCavenicArrow` gap.
- `docs/crazy-skeleton-ranged-ai-mvp.md` keeps the restored Crazy Skeleton goal cadence while preserving the current vanilla-compatible projectile bridge.

## What Full Projectile Parity Would Still Require

- Decide whether Torch parity needs:
  - a real `TorchArrow` entity, or
  - a broader generic torch-item delegation path beyond plain `minecraft:torch`
- Re-test all accepted `Cavenic Bow` release, `RAPID`, `TORCH` and Crazy Skeleton guarantees so a projectile follow-up does not silently weaken them.
- Only add renderer or asset work if a real custom client-visible projectile identity becomes necessary.

## What Stays Unchanged

- no `Cavenic Bow` mode changes
- no `EntityCavenicArrow` implementation
- no `CrazySkeleton` goal-cadence or equipment changes
- no natural-spawn changes
- no Cavenia work
- no packets
- no broad renderer/client changes

## Testing

- Resource tests pin that Reborn still does not register `cavernreborn:rapid_arrow` or `cavernreborn:torch_arrow`.
- Resource tests also pin that Reborn still does not add projectile classes or renderer registrations for those names.
- Documentation tests pin the exact legacy `EntityRapidArrow` and `EntityTorchArrow` findings and the current no-port decision.
- NeoForge GameTest runtime smoke pins:
  - no `cavernreborn:rapid_arrow` entity type at runtime
  - no `cavernreborn:torch_arrow` entity type at runtime
  - `RAPID` still spawning a vanilla-compatible arrow entity
  - `RAPID` still restoring the low-armor cooldown reset without `rapid_arrow`
  - `TORCH` still spawning a marked vanilla-compatible arrow entity
  - Torch placement still flowing through `CavenicBowTorchEvents`
  - Crazy Skeleton ranged AI still keeping the current local vanilla-compatible projectile bridge
