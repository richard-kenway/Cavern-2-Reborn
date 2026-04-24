# Cavenic Bow Baseline MVP

This slice adds the first bounded `cavernreborn:cavenic_bow` item on top of the existing `cavenic_orb` material line.

The later stack-local mode-state and cycling follow-up is documented separately in `docs/cavenic-bow-mode-state-mvp.md`.
The first bounded Snipe-only behavior follow-up is documented separately in `docs/cavenic-bow-snipe-mode-mvp.md`.

## What Was Ported

- `cavernreborn:cavenic_bow`
- a bounded shaped crafting recipe using `c:orbs/cavenic`
- cavenic repair support through `cavernreborn:cavenic_orb`
- vanilla-style bow family and enchantable tag wiring
- legacy-inspired base and pulling textures

## Legacy References Inspected

- Legacy `cavern.item.ItemBowCavenic`
- Legacy `assets/cavern/recipes/cavenic_bow.json`
- Legacy `assets/cavern/models/item/cavenic_bow.json`
- Legacy `assets/cavern/models/item/cavenic_bow_pulling_0.json`
- Legacy `assets/cavern/models/item/cavenic_bow_pulling_1.json`
- Legacy `assets/cavern/models/item/cavenic_bow_pulling_2.json`
- Legacy `item.bowCavenic.*` mode labels from `assets/cavern/lang/en_us.lang`
- Legacy `EntityRapidArrow`
- Legacy `EntityTorchArrow`

## Bounded MVP Behavior

- `cavenic_bow` extends the modern vanilla bow item path through a dedicated `CavenicBowItem` class.
- Shooting stays on the vanilla bow baseline: normal arrow consumption, charge timing, enchantment interaction and projectile spawning.
- Durability is pinned to the current `ModToolTiers.CAVENIC` baseline of `278`.
- The bow repairs with `cavernreborn:cavenic_orb` and does not repair with `minecraft:stick`.
- The creative-tab order keeps the cavenic equipment line stable:
  - `cavernreborn:cavenic_orb`
  - `cavernreborn:cavenic_sword`
  - `cavernreborn:cavenic_axe`
  - `cavernreborn:cavenic_bow`

## Intentionally Out Of Scope

- `EntityRapidArrow`
- `EntityTorchArrow`
- Custom projectile entities
- Torch-shot behavior
- Rapid-fire behavior
- Sniper-specific behavior
- Mode-specific predicates beyond normal vanilla pull states
- Keybinds, packets, GUI or client networking

This note intentionally documents only the safe baseline bow slice. Legacy custom bow behavior remains follow-up work, even though the later mode-state and bounded Snipe follow-ups now exist separately.

## Testing

Coverage is split across:

- resource tests for registration, repair wiring, model and pulling texture references, recipe contents, tag contents and the absence of custom projectile or mode-state wiring;
- documentation tests for the bounded scope and explicit legacy follow-up boundary;
- NeoForge GameTest runtime smoke for registry id resolution, durability, repair behavior, expected bow enchantment applicability, cavenic item tag resolution and recipe manager resolution.

## Asset Provenance

- `cavenic_bow`, `cavenic_bow_pulling_0`, `cavenic_bow_pulling_1` and `cavenic_bow_pulling_2` textures were copied from the adjacent legacy `../cavern-2` repository and renamed into the current Reborn item-texture layout.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.
