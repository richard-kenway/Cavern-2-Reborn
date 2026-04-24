# Cavenic Bow Release Semantics Regression MVP

This stabilization slice hardens the real server-side `cavernreborn:cavenic_bow` release path after the baseline, mode-state, Snipe, Rapid and Torch MVPs.

It does not add a new gameplay feature. It pins the current bow behavior against vanilla `BowItem` semantics and the already accepted bounded Cavenic mode slices.

## Legacy References Inspected

- Legacy `cavern.item.ItemBowCavenic`
- Legacy `cavern.item.ItemBowCavenic.BowMode`
- Legacy `cavern.entity.projectile.EntityRapidArrow`
- Legacy `cavern.entity.projectile.EntityTorchArrow`

## Vanilla Release Semantics Inspected

- Vanilla `net.minecraft.world.item.BowItem#use`
- Vanilla `net.minecraft.world.item.BowItem#releaseUsing`
- Vanilla `net.minecraft.world.item.ProjectileWeaponItem.draw`
- Vanilla `net.minecraft.world.item.ProjectileWeaponItem.useAmmo`
- Vanilla projectile creation through the current bow and arrow path

## What This Stabilization Covers

- survival arrow behavior through the real `use(...)` plus `releaseUsing(...)` path
- creative/no-arrow behavior for the current 1.21.1 vanilla contract
- Infinity behavior for normal releases and Torch-mode releases
- Torch consumption stays separate from arrow consumption
- real release-path coverage for `NORMAL`, `RAPID`, `SNIPE` and `TORCH`
- wall torch orientation and grief-safe Torch placement checks

## Current Reborn Release Contract

- `NORMAL` stays on the vanilla bow release path.
- Survival players with arrows fire one vanilla arrow, lose one arrow and take the normal single bow durability cost.
- Survival players without arrows fail `use(...)` and do not enter the release path.
- In the current vanilla 1.21.1 contract, creative/no-arrow use can still start drawing, but `releaseUsing(...)` does not spawn a projectile when `getProjectile(...)` stays empty.
- Infinity does not create free arrows from an empty inventory in this version. Infinity still requires real arrow access, but keeps the consumed normal-arrow stack intact once a valid shot is fired.
- Torch consumption stays separate from arrow consumption and separate from Infinity. Infinity never creates free torches.
- `RAPID` keeps the bounded faster power ramp but still stays on the vanilla arrow entity path.
- `SNIPE` keeps the bounded full-charge velocity and damage boost plus the extra durability surcharge only when the Snipe boost actually applies.
- `TORCH` keeps the vanilla arrow entity path, uses a marker plus server-side impact handling and now keeps direct wall-torch orientation without opposite-wall fallback.

## Torch Placement Hardening

- A horizontal Torch-mode block hit now places `minecraft:wall_torch` only when the direct hit support is valid.
- The current hardening removes the old opposite-wall fallback so Torch mode does not attach to an unrelated support block behind the target position.
- Top-face placement still uses `minecraft:torch`.
- Bottom-face hits, liquid targets, occupied targets and entity hits still do nothing.

## Intentionally Out Of Scope

- `EntityRapidArrow`
- `EntityTorchArrow`
- custom projectile entities remain out of scope
- custom projectile classes
- client visual feel and model predicates
- Rapid rebalance
- Snipe rebalance
- broader Torch rebalance outside the proven placement-safety fix

## Testing

Coverage is split across:

- resource tests that pin the continued vanilla-compatible release flow, Torch event registration and the absence of custom projectile registration
- documentation tests for vanilla release semantics, Infinity behavior, Torch-consumption boundaries and the custom-projectile boundary
- NeoForge GameTest runtime smoke for:
  - real release-path NORMAL survival arrow behavior
  - real release-path survival no-arrow behavior
  - real release-path creative/no-arrow behavior
  - real release-path Infinity behavior
  - real release-path RAPID, SNIPE and TORCH behavior on actual vanilla arrow entities
  - Torch wall-torch orientation and placement-safety checks
