# Cavenic Bow Snipe Mode MVP

This slice adds the first bounded behavioral follow-up for `cavernreborn:cavenic_bow`.

It builds on the existing baseline bow plus stack-local mode-state/cycling slices and makes only `SNIPE` mode behave differently.

The later bounded Rapid-only follow-up is documented separately in `docs/cavenic-bow-rapid-mode-mvp.md`.
The later bounded Torch-only follow-up is documented separately in `docs/cavenic-bow-torch-mode-mvp.md`.

## What This Increment Adds

- a pure `CavenicBowSnipePolicy` with pinned constants and activation rules
- a bounded full-charge Snipe boost for vanilla arrow projectiles
- an extra durability surcharge only when the Snipe boost actually applies
- runtime tests proving that Snipe uses vanilla arrow entities instead of custom cavenic projectile types

## Legacy References Inspected

- Legacy `cavern.item.ItemBowCavenic`
- Legacy `cavern.item.ItemBowCavenic.BowMode`
- Legacy `cavern.entity.projectile.EntityRapidArrow`
- Legacy `cavern.entity.projectile.EntityTorchArrow`
- Legacy bow-mode lang entries from `assets/cavern/lang/en_us.lang`

## Legacy Snipe Findings

The adjacent legacy bow implementation used:

- mode order `NORMAL -> RAPID -> SNIPE -> TORCH`
- a dedicated `SNIPE` mode entry with legacy attack power `3.0D`
- a legacy Snipe critical-ready threshold of `f >= 0.65D`
- custom projectile classes for other modes, even though Snipe itself still used the vanilla arrow entity path

## Exact Reborn Snipe Behavior

Reborn keeps this slice intentionally bounded:

- only `CavenicBowMode.SNIPE` can activate the boost
- the shot must be fully charged through the current `BowItem.getPowerForTime(...)` path
- minimum power threshold is pinned to `1.0F`
- projectile velocity multiplier is pinned to `1.5F`
- projectile base damage multiplier is pinned to `1.5D`
- extra bow durability cost is pinned to `1`

The fired projectile remains the normal vanilla arrow type that the active ammo stack would already create.

## Intentional Deviation From Legacy

Legacy Snipe was much stronger and activated earlier in the draw curve.

This Reborn MVP intentionally softens that behavior:

- it does not preserve the old `3.0D` attack-power multiplier
- it does not preserve the old `0.65D` near-full-charge activation threshold
- it does not add zoom, client camera behavior, particles or sounds

The current values are a bounded modern stand-in that is easier to test and less likely to replace the broader equipment balance.

## Runtime Wiring

- `NORMAL` still uses vanilla bow behavior.
- `RAPID` is now documented separately in `docs/cavenic-bow-rapid-mode-mvp.md`.
- `SNIPE` now boosts only fully charged shots.
- `TORCH` is now documented separately in `docs/cavenic-bow-torch-mode-mvp.md`.
- Mode state stays stored on the stack after firing.
- Ammo consumption still follows vanilla bow rules.
- The bow still repairs with `cavernreborn:cavenic_orb`.

## Intentionally Out Of Scope

- RAPID mode shooting behavior
- TORCH mode shooting behavior
- `EntityRapidArrow`
- `EntityTorchArrow`
- custom projectile entity registration
- torch-placement arrows
- rapid-fire shooting
- mode-specific model predicates or textures
- keybinds, packets, GUI or client networking

## Testing

Coverage is split across:

- pure core tests for activation rules and pinned Snipe constants
- resource tests for the new Snipe policy wiring plus the continued absence of custom projectile or mode-specific asset wiring
- documentation tests for the bounded Snipe scope and legacy boundary
- NeoForge GameTest runtime smoke for:
  - full-charge Snipe projectile boost on an actual vanilla arrow entity
  - extra durability cost only when Snipe applies
  - unchanged RAPID and TORCH baseline behavior
  - continued mode persistence after firing
