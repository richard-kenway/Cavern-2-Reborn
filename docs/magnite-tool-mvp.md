# Magnite Tool Set MVP

This note fixes the first bounded magnite tool slice for `CAVERN`.

It builds directly on `docs/cavern-ore-content-parity.md`.

## Added In This MVP

- `magnite_sword`
- `magnite_pickaxe`
- `magnite_axe`
- `magnite_shovel`

## Bounded Modern Scope

- This is the first bounded magnite tool slice, not full legacy equipment parity.
- The current pass adds only `magnite_sword`, `magnite_pickaxe`, `magnite_axe` and `magnite_shovel`.
- The tools are repairable with `magnite_ingot`.
- The line is intentionally brittle and low-durability.
- The line is intentionally very fast and overclocked.
- The magnite line is a specialist side-grade, not the main combat/mining line.
- No magnite hoe is added in this pass.
- No magnite armor is added in this pass.
- No special abilities are added in this pass.
- No Mining Assist changes are added.
- No Miner's Orb changes are added.
- No Ore Compass changes are added.

## Tier Policy

- The modern Reborn MVP uses a bounded `magnite` tier instead of copying the old material values verbatim.
- The incorrect-tool baseline currently mirrors `#minecraft:incorrect_for_diamond_tool` through `cavernreborn:incorrect_for_magnite_tool`.
- Durability is `48`.
- Mining speed is `18.0F`.
- Attack bonus is `2.5F`.
- Enchantability is `24`.
- The repair ingredient is `cavernreborn:magnite_ingot`.

## Runtime Coverage

- NeoForge GameTest runtime smoke checks magnite tool runtime registry ids.
- Runtime smoke verifies repairability with `cavernreborn:magnite_ingot`.
- Runtime smoke verifies the expected mining, weapon and durability enchantment applicability.
- Runtime smoke verifies the brittle max-damage/runtime path.
- Runtime smoke also verifies that `magnite_pickaxe` does not trigger Mining Assist.

## Manual Smoke Still Needed

- Craft all four magnite tools in survival.
- Compare the mining feel against `hexcite_pickaxe`.
- Verify the tools feel very fast but noticeably brittle.
- Verify enchanting table and anvil behavior for the expected mining and weapon enchantments.
- Verify repair with `cavernreborn:magnite_ingot`.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied legacy visual assets for the magnite tool textures came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy textures.
