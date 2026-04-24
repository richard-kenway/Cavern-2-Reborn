# Cavenic Bow Rapid Mode MVP

This slice adds the first bounded behavioral follow-up for `cavernreborn:cavenic_bow` after the mode-state and Snipe groundwork.

It keeps the projectile path vanilla and changes only `RAPID` mode behavior.

## What This Increment Adds

- a pure `CavenicBowRapidPolicy` with pinned constants and safe raw-power handling
- a bounded Rapid shot-power ramp on top of the current bow release path
- runtime tests proving that RAPID still uses vanilla arrow entities and does not add extra durability cost

## Legacy References Inspected

- Legacy `cavern.item.ItemBowCavenic`
- Legacy `cavern.item.ItemBowCavenic.BowMode`
- Legacy `cavern.entity.projectile.EntityRapidArrow`
- Legacy `cavern.entity.projectile.EntityTorchArrow`
- Legacy bow-mode lang entries from `assets/cavern/lang/en_us.lang`

## Legacy Rapid Findings

The adjacent legacy bow implementation used:

- mode order `NORMAL -> RAPID -> SNIPE -> TORCH`
- a dedicated `RAPID` mode entry with legacy attack power `0.65D`
- an old hand-state shortcut that forced a much shorter release path instead of a simple raw-power multiplier
- a separate `EntityRapidArrow` class for the projectile path

## Exact Reborn Rapid Behavior

Reborn keeps this slice intentionally bounded:

- only `CavenicBowMode.RAPID` adjusts shot power
- raw vanilla bow power is multiplied before projectile velocity is resolved
- power multiplier is pinned to `2.4F`
- adjusted shot power is capped at `1.0F`
- extra bow durability cost remains `0`
- Rapid does not add a damage multiplier in this MVP
- Rapid does not change ammo consumption or infinity rules

The fired projectile remains the normal vanilla arrow type that the active ammo stack would already create.

## Intentional Deviation From Legacy

Legacy Rapid depended on both `EntityRapidArrow` and older active-hand timing behavior that does not map cleanly onto the current bounded 1.21.1 bow path.

This Reborn MVP intentionally softens that port:

- it does not yet port `EntityRapidArrow`
- it does not add multi-shot behavior
- it does not preserve the old `0.65D` attack-power reduction
- it does not add client timing hacks, particles or sounds

The current values are a bounded modern stand-in that is easy to test and that keeps the vanilla projectile path intact.

## Runtime Wiring

- `NORMAL` still uses vanilla bow behavior.
- `RAPID` now reaches higher projectile velocity at the same draw duration, capped at vanilla full-power strength.
- `SNIPE` remains documented separately in `docs/cavenic-bow-snipe-mode-mvp.md` and is unchanged by this slice.
- `TORCH` still uses vanilla bow behavior.
- Mode state stays stored on the stack after firing.
- The bow still repairs with `cavernreborn:cavenic_orb`.

## Intentionally Out Of Scope

- `EntityRapidArrow`
- `EntityTorchArrow`
- custom projectile entity registration
- rapid multi-shot behavior
- TORCH mode shooting behavior
- torch-placement arrows
- mode-specific model predicates or textures
- keybinds, packets, GUI or client networking

## Testing

Coverage is split across:

- pure core tests for the Rapid constants, capping logic and safe invalid-power fallback
- resource tests for Rapid policy wiring plus the continued absence of custom projectile or mode-specific asset wiring
- documentation tests for the bounded Rapid scope and explicit legacy boundary
- NeoForge GameTest runtime smoke for:
  - higher RAPID adjusted shot power than NORMAL at the same raw draw power
  - a higher-velocity RAPID vanilla arrow entity at the same raw draw power
  - no extra durability cost in RAPID mode
  - continued SNIPE and TORCH boundaries
