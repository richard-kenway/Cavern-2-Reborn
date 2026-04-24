# Cavenic Bow Torch Mode MVP

This slice adds the first bounded Torch-only behavioral follow-up for `cavernreborn:cavenic_bow`.

It keeps the projectile entity vanilla and builds on the existing baseline bow, mode-state, Snipe and Rapid slices.

## What This Increment Adds

- a pure `CavenicBowTorchPolicy` with pinned zero-modifier constants and Torch-shot eligibility rules
- a Torch-only marker path for vanilla arrow entities fired from `cavernreborn:cavenic_bow`
- a server-side projectile-impact handler that can place a normal torch from a marked vanilla arrow impact
- bounded torch-item consumption for survival shooters
- runtime tests proving that Torch mode stays on the vanilla arrow entity path and uses grief-safe placement rules

## Legacy References Inspected

- Legacy `cavern.item.ItemBowCavenic`
- Legacy `cavern.item.ItemBowCavenic.BowMode`
- Legacy `cavern.entity.projectile.EntityTorchArrow`
- Legacy `cavern.entity.projectile.EntityRapidArrow`
- Legacy bow-mode lang entries from `assets/cavern/lang/en_us.lang`

## Legacy Torch Findings

The adjacent legacy bow implementation used:

- mode order `NORMAL -> RAPID -> SNIPE -> TORCH`
- a dedicated `TORCH` mode entry with legacy attack power `1.0D`
- a dedicated `EntityTorchArrow` projectile that tried to use a real torch item on block hit
- a survival torch requirement before a Torch-mode shot could proceed

## Exact Reborn Torch Behavior

Reborn keeps this slice intentionally bounded:

- only `CavenicBowMode.TORCH` is eligible for the Torch marker path
- the fired projectile remains the normal vanilla arrow type that the active ammo stack would already create
- Torch mode does not change velocity
- Torch mode does not change base damage
- extra bow durability cost remains `0`
- a marked Torch arrow uses a namespaced scoreboard-tag marker: `cavernreborn:cavenic_bow_torch`
- a marked Torch shot consumes one `minecraft:torch` for survival shooters
- creative shooters do not consume torches
- a survival shooter with no torch still fires a normal vanilla arrow with no Torch marker

## Placement Rules

When a marked vanilla arrow hits a block on the server:

- a top-face hit may place a standing `minecraft:torch`
- a horizontal-face hit may place a horizontal `minecraft:wall_torch`
- bottom-face hits are ignored
- entity hits are ignored
- the target position must stay air-or-replaceable
- Torch mode never replaces liquids
- the chosen torch block state must be able to survive at that position
- no impacted support block is replaced or broken
- no placement happens outside loaded chunks

This keeps Torch mode non-destructive and grief-safe for the current MVP.

## Intentional Deviation From Legacy

Legacy Torch mode depended on `EntityTorchArrow` and older item-use-on-hit plumbing.

This Reborn MVP intentionally softens that port:

- it does not yet port `EntityTorchArrow`
- it does not register any custom projectile entity
- it keeps custom projectile entity registration out of scope
- it does not place torches through a custom projectile class
- it does not add Torch-specific particles, sounds or model predicates
- RAPID and SNIPE remain unchanged

The current behavior is a bounded modern bridge that preserves the Torch identity without widening the projectile/entity system yet.

## Testing

Coverage is split across:

- pure core tests for Torch eligibility and pinned zero-modifier constants
- resource tests for Torch helper wiring, event registration and the continued absence of custom projectile or mode-specific asset wiring
- documentation tests for Torch consumption, grief-safety and the custom-projectile boundary
- NeoForge GameTest runtime smoke for:
  - Torch-mode marker eligibility on real bow stacks
  - survival torch consumption and creative no-consumption behavior
  - valid top-face `minecraft:torch` placement
  - valid horizontal-face `minecraft:wall_torch` placement
  - no placement on entity hits, occupied targets or liquids
  - continued vanilla-arrow runtime identity
