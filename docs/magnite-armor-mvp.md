# Magnite Armor MVP

This note fixes the first bounded magnite armor slice for `CAVERN`.

It builds on `docs/magnite-tool-mvp.md` and keeps the current Mining Assist, Miner's Orb and Ore Compass behavior unchanged.

## Added In This MVP

- `magnite_helmet`
- `magnite_chestplate`
- `magnite_leggings`
- `magnite_boots`

## Bounded Modern Scope

- This is the first bounded magnite armor slice, not full legacy equipment parity.
- The armor is repairable with `magnite_ingot`.
- The line is intentionally brittle / lower-durability.
- The line is intentionally enchant-friendly.
- This is a side-grade, not the main armor line.
- No magnite set bonus is added.
- No passive potion effects are added.
- No Mining Assist changes are added.
- No Miner's Orb changes are added.
- No Ore Compass changes are added.

## Material Policy

- The modern Reborn MVP uses a bounded `magnite` armor material instead of copying the old material values verbatim.
- Durability multiplier is `12`.
- Defense values are `2 / 5 / 4 / 2` for helmet, chestplate, leggings and boots.
- Enchantability is `24`.
- Toughness remains `0.0`.
- Knockback resistance remains `0.0`.
- The repair ingredient is `cavernreborn:magnite_ingot`.
- Equip sound uses the chain-equipment baseline.

## Rendering And Assets

- Inventory item art now exists for all four armor pieces.
- A companion equipment asset lives at `assets/cavernreborn/equipment/magnite.json`.
- The current 1.21.1 + NeoForge humanoid armor runtime in this repository still resolves the worn armor layers through `ArmorMaterial.Layer`, so the active runtime textures are `textures/models/armor/magnite_layer_1.png` and `textures/models/armor/magnite_layer_2.png`.
- Companion modern equipment textures are also checked in under `textures/entity/equipment/...` so the asset layout stays explicit and ready for future runtime shifts.
- The adjacent legacy archive used for this project did not include magnite armor textures, so the current magnite armor visuals are bounded placeholder derivatives of the imported hexcite armor art rather than final magnite-specific parity art.

## Runtime Coverage

- NeoForge GameTest runtime smoke checks magnite armor runtime registry ids.
- Runtime smoke verifies repairability with `cavernreborn:magnite_ingot`.
- Runtime smoke verifies bounded damageable/equippable armor behavior.
- Runtime smoke verifies expected armor enchantment applicability and rejects unrelated weapon-only enchantments.
- Resource tests pin the companion equipment asset, the current runtime armor texture paths, recipes and vanilla armor/enchantable tag wiring.

## Manual Smoke Still Needed

- Craft all four magnite armor pieces in survival.
- Equip the full set on a player.
- Verify the worn appearance and armor bar feel stay below the hexcite line.
- Verify enchanting table and anvil behavior for the expected armor enchantments.
- Verify repair with `cavernreborn:magnite_ingot`.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied legacy visual assets for the hexcite armor textures already used by this repository came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- The current magnite armor placeholder textures are derived from that imported hexcite armor art because adjacent legacy magnite armor textures were not available in `../cavern-2`.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the current temporary magnite armor visuals.
