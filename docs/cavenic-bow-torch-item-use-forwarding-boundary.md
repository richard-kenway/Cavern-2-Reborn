# Cavenic Bow Torch Item-Use Forwarding Boundary

This slice inspects the remaining legacy `EntityTorchArrow` item-use forwarding behavior and pins why Reborn keeps the already accepted bounded Torch placement path on marked vanilla arrows.

## Legacy References Inspected

- `cavern.entity.projectile.EntityTorchArrow`
- `cavern.entity.projectile.EntityRapidArrow`
- `cavern.item.ItemBowCavenic`
- legacy searches for `EntityTorchArrow`, `setTorchItem`, `torchItem`, `onHit`, `RayTraceResult`, `Type.ENTITY`, `EnumHand.MAIN_HAND`, `onItemUse`, `BlockTorch`, `Blocks.TORCH`, `findTorch`, `subAmmo` and `Mode.TORCH`

## Exact Legacy `EntityTorchArrow#onHit(...)` Behavior

- Legacy `EntityTorchArrow` extends `EntityTippedArrow`.
- It stores one extra field:
  - `private ItemStack torchItem = ItemStack.EMPTY`
- It adds one setter:
  - `setTorchItem(ItemStack stack)`
- `onHit(RayTraceResult rayTrace)`:
  - calls `super.onHit(rayTrace)` first
  - returns immediately for entity hits
  - runs only on the logical server
  - requires `shootingEntity instanceof EntityPlayer`
  - resolves the forwarded item as `torchItem`, or `new ItemStack(Blocks.TORCH)` when that field is empty
  - stores the player's previous main-hand stack
  - temporarily swaps the player's main hand to the resolved torch item
  - calls the held item's `onItemUse(...)` with the impact block position, face and hit vector
  - restores the previous main-hand stack
  - only when `EnumActionResult.SUCCESS` is returned:
    - plays the wood place sound
    - kills the projectile with `setDead()`
- I found no direct custom behavior for:
  - damage
  - crit
  - gravity
  - pickup
  - entity-hit damage
  - NBT
  - renderer

## Exact Legacy `ItemBowCavenic#findTorch(...)` Behavior

- `findTorch(EntityPlayer player)` checks, in order:
  - off hand
  - main hand
  - main inventory
- `isTorch(ItemStack stack)` treats a stack as torch ammo only when `Block.getBlockFromItem(stack.getItem()) instanceof BlockTorch`.
- That means the old Torch path could forward any `BlockTorch` item, not only vanilla `minecraft:torch`.
- Legacy player `TORCH` shots create `new EntityTorchArrow(world, shooter).setTorchItem(subAmmo)`.
- Legacy skeleton paths do not create `EntityTorchArrow`.

## Current Reborn Mapping

- Reborn keeps `TORCH` on marked vanilla `minecraft:arrow` entities.
- Reborn keeps the current `CavenicBowTorchEvents` placement path:
  - server-side projectile-impact handling
  - standing `minecraft:torch` placement on valid top-face hits
  - horizontal `minecraft:wall_torch` placement on valid wall hits
  - deterministic orientation
  - no placement on entity hits, bottom-face hits, liquids or occupied non-replaceable targets
- Reborn currently accepts only `Items.TORCH` as Torch ammo in `CavenicBowItem#findTorchAmmo(...)`.
- Reborn does not register:
  - `cavernreborn:torch_arrow`
  - `EntityTorchArrow`
  - a Torch projectile renderer

## Why No Generic Item-Use Forwarding Was Added

- Current Reborn has no custom torch-like block items that would benefit from forwarding arbitrary `BlockTorch` item behavior.
- The inspected modern path is not a clean drop-in for the legacy behavior:
  - `UseOnContext` can carry an explicit `ItemStack`
  - but `BlockItem#useOn(...)` and related placement logic still perform full item-use and stack-consumption behavior on that supplied stack
- To match legacy behavior honestly, Reborn would need to do at least one risky thing:
  - temporarily mutate real player-held state again
  - or carry the selected torch item identity and placement stack across release and impact
  - or broaden the current bounded placement handler into generic torch-item use simulation
- Each of those options would increase risk around already accepted behavior:
  - Torch release semantics
  - survival torch consumption
  - creative Torch behavior
  - standing and wall torch placement tests
  - current marked-vanilla-arrow identity
- Because Reborn currently only supports plain vanilla torch ammo, the remaining gap is narrow and low-value compared to the extra inventory and item-use surface area it would introduce.

## Exact Deferral Reason

The behavior is deferred because the remaining legacy parity gap is specifically the old temporary main-hand `onItemUse(...)` forwarding wrapper for arbitrary `BlockTorch` items, and Reborn does not currently have a safe, bounded reason to reintroduce that pattern.

The blocker is a combination of:

- no current custom torch-like items in Reborn
- unsafe or at least brittle temporary main-hand mutation
- lack of a clearly superior zero-mutation forwarding path that keeps release semantics unchanged
- risk to the already accepted wall/standing placement coverage

## What Stays Unchanged

- no `EntityTorchArrow`
- no `cavernreborn:torch_arrow`
- no Torch projectile renderer
- no generic item-use forwarding helper
- marked vanilla arrow path stays intact
- `CavenicBowTorchEvents` stays the active Torch impact handler
- vanilla `minecraft:torch` and `minecraft:wall_torch` placement stays intact
- Rapid low-armor reset stays separate and unchanged
- Snipe and Rapid mode behavior stay unchanged

## What Future Safe Parity Would Require

- a concrete Reborn torch-like item that actually needs forwarded item-use behavior
- a safe way to preserve the selected torch-item identity from release to impact without widening the projectile entity path
- deterministic tests that prove no inventory corruption and no regression in Torch release semantics
- confirmation that forwarding arbitrary `BlockTorch` behavior is still worth the extra surface area compared to the current bounded plain-torch placement path

## Related Documentation

- `docs/cavenic-bow-torch-mode-mvp.md` documents the current accepted Torch marker and placement slice.
- `docs/cavenic-bow-release-semantics-mvp.md` documents the accepted release contract that this boundary must not weaken.
- `docs/entity-rapid-torch-arrow-projectile-boundary.md` documents the broader `EntityRapidArrow` / `EntityTorchArrow` no-entity decision.

## Testing

- Resource tests pin that Reborn still has no `EntityTorchArrow`, no `torch_arrow` registry id and no Torch item-use forwarding helper class.
- Resource tests also pin that `CavenicBowTorchEvents` still avoids `UseOnContext`, direct `ItemStack#useOn(...)` forwarding and player main-hand mutation.
- Documentation tests pin the exact legacy `onHit(...)` and `findTorch(...)` findings plus the deferral reason.
- NeoForge GameTest runtime smoke pins that Reborn still:
  - fires a vanilla `minecraft:arrow` in `TORCH` mode
  - marks that arrow with the Torch marker
  - keeps `cavernreborn:torch_arrow` absent at runtime
  - places standing and wall torches through the current bounded event path
