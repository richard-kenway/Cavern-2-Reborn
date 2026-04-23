# Aquamarine Tool MVP

This note fixes the first bounded aquamarine tool slice for `CAVERN`.

It builds directly on `docs/cavern-ore-content-parity.md`.

## Added In This MVP

- `aquamarine_pickaxe`
- `aquamarine_axe`
- `aquamarine_shovel`

## Bounded Modern Scope

- This is the first bounded aquamarine tool slice, not full legacy equipment parity.
- The current pass adds only `aquamarine_pickaxe`, `aquamarine_axe` and `aquamarine_shovel`.
- The tools are repairable with aquamarine.
- No aquamarine sword/hoe/armor is added in this pass.
- Aquamarine remains the utility-oriented side-grade line.
- Magnite is now documented separately in `docs/magnite-tool-mvp.md`.
- Magnite armor is now documented separately in `docs/magnite-armor-mvp.md`.
- The underwater utility is underwater break-speed only.
- No water breathing is added.
- No swim-speed bonus is added.
- No progression changes are added.
- No Mining Assist, Miner's Orb or Ore Compass behavior changes are added.

## Tier Policy

- The modern Reborn MVP uses a bounded iron-adjacent custom `aquamarine` tier instead of copying broad legacy equipment balance directly.
- Durability is `200`.
- Mining speed is `8.0F`.
- Attack bonus is `1.5F`.
- Enchantability is `15`.
- The repair ingredient is `cavernreborn:aquamarine`.
- The incorrect-tool baseline currently mirrors `#minecraft:incorrect_for_iron_tool` through `cavernreborn:incorrect_for_aquamarine_tool`.

## Underwater Utility

- Aquamarine tools only affect underwater block-breaking speed.
- The bounded boost multiplier is `10.0F`.
- If Aqua Affinity is present, the bounded dampener is `0.5F`.
- The implementation does not add breathing, movement, combat or progression effects.

## Runtime Coverage

- NeoForge GameTest runtime smoke checks aquamarine tool runtime registry ids.
- Runtime smoke verifies repairability with `cavernreborn:aquamarine`.
- Runtime smoke verifies the expected mining and durability enchantment applicability.
- Runtime smoke also covers the bounded underwater utility policy.

## Manual Smoke Still Needed

- Craft the three aquamarine tools in survival.
- Compare underwater mining against a non-aquamarine tool.
- Verify the boost feels real but bounded.
- Verify the tools do not grant water breathing or swim-speed changes.
- Verify enchanting table and anvil behavior for the expected mining enchantments.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied legacy visual assets for the aquamarine tool textures came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy textures.
