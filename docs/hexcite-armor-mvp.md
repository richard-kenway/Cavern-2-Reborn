# Hexcite Armor MVP

This note fixes the first bounded `hexcite` armor slice for `CAVERN`.

It builds on top of `docs/hexcite-tool-parity.md` and keeps the current mining, progression, Miner's Orb and Ore Compass behavior unchanged.

## Added In This MVP

- `hexcite_helmet`
- `hexcite_chestplate`
- `hexcite_leggings`
- `hexcite_boots`
- Hexcite Helmet
- Hexcite Chestplate
- Hexcite Leggings
- Hexcite Boots

## Bounded Modern Scope

- This is the first bounded hexcite armor slice, not full legacy equipment parity.
- The armor is craftable from `cavernreborn:hexcite`.
- The repair ingredient is `cavernreborn:hexcite`.
- The current armor material is intentionally bounded and not netherite-tier.
- No special set bonus is added.
- No passive potion effects are added.
- No Mining Assist, Miner's Orb or Ore Compass behavior changes are added here.
- No custom GUI, config, keybinds, packets, particles or sounds are added here.

## Material Policy

- The current Reborn MVP uses a bounded modern `hexcite` armor material instead of copying legacy numbers verbatim.
- Durability multiplier is `20`.
- Defense values are `3 / 8 / 6 / 3` for helmet, chestplate, leggings and boots.
- Enchantability is `18`.
- Toughness is `1.0`.
- Knockback resistance remains `0.0`.
- Equip sound uses the diamond-equipment baseline.

## Rendering And Assets

- Inventory item art now exists for all four armor pieces.
- A companion equipment asset lives at `assets/cavernreborn/equipment/hexcite.json`.
- The current 1.21.1 + NeoForge 21.1.220 humanoid armor runtime in this repository still resolves the worn armor layers through `ArmorMaterial.Layer`, so the active runtime textures are `textures/models/armor/hexcite_layer_1.png` and `textures/models/armor/hexcite_layer_2.png`.
- Companion modern equipment textures are also checked in under `textures/entity/equipment/...` so the asset layout stays explicit and ready for future runtime shifts.

## Runtime Coverage

- NeoForge GameTest runtime smoke checks `hexcite` armor runtime registry ids.
- Runtime smoke verifies repairability with `cavernreborn:hexcite`.
- Runtime smoke verifies bounded damageable/equippable armor behavior.
- Runtime smoke verifies expected armor enchantment applicability and rejects unrelated weapon-only enchantments.
- Resource tests pin the companion equipment asset, the current runtime armor texture paths, recipes and vanilla armor/enchantable tag wiring.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied legacy visual assets for the `hexcite` armor inventory textures and worn armor layers came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy textures.
