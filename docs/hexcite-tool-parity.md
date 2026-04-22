# Hexcite Tool Parity

This note fixes the first bounded `hexcite` equipment slice for `CAVERN`.

It builds directly on `docs/cavern-special-ore-content-parity.md`.

## Added In This MVP

- `hexcite_pickaxe`
- `hexcite_axe`
- `hexcite_shovel`
- `hexcite_hoe`
- `hexcite_sword`
- Hexcite Pickaxe
- Hexcite Axe
- Hexcite Shovel
- Hexcite Hoe
- Hexcite Sword

## Bounded Modern Scope

- This is the first hexcite tool set MVP, not full legacy equipment parity.
- The current slice adds only the five core tools.
- armor remains out of scope.
- No special tool abilities are added in this pass.
- No mining assist, vein mining, area mining, particles or custom sounds are added here.
- Mining Assist is documented separately as the follow-up functional slice in `docs/mining-assist-mvp.md`.
- Miner's Orb is documented separately as the item-based progression follow-up in `docs/miner-orb-mvp.md`.

## Tier Policy

- The modern Reborn MVP uses a bounded custom `hexcite` tier instead of copying the old material values verbatim.
- The tier is intentionally diamond-capable but not netherite-tier.
- Durability is `1024`.
- Mining speed is `8.0F`.
- Attack bonus is `3.0F`.
- Enchantability is `18`.
- The repair ingredient is `cavernreborn:hexcite`.
- The incorrect-tool baseline currently mirrors `#minecraft:incorrect_for_diamond_tool` through `cavernreborn:incorrect_for_hexcite_tool`.

## Runtime Coverage

- NeoForge GameTest runtime smoke now checks `hexcite` tool runtime registry ids.
- Runtime smoke verifies the normal `hexcite_pickaxe` mining path for `hexcite_ore` and `randomite_ore`.
- Runtime smoke also verifies the Silk Touch `hexcite_pickaxe` path for `hexcite_ore`.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied legacy visual assets for the `hexcite` tool textures came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy textures.
