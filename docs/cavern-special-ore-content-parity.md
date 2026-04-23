# CAVERN Special Ore/Content Parity

This note fixes the second narrow special-ore/content tranche for `CAVERN` (Tranche 2).

## Added In Tranche 2

- `hexcite_ore`, `hexcite`, `hexcite_block`
- `randomite_ore`
- `fissured_stone`

## Bounded Modern Scope

- `hexcite` restores only the ore, gem and storage-block loop in this tranche.
- `hexcite` tools, armor, weapons, trims and tuning remain out of scope for this tranche itself.
- `randomite_ore` uses a curated bounded loot table instead of the old OreDictionary scan, registry-wide random item mode or `RandomiteDropEvent`.
- `fissured_stone` is restored as a bounded non-griefing cave effect block.
- The current `fissured_stone` pass does not create destructive explosions or terrain damage.
- Custom `CAVERN` ores now participate in progression scoring inside `cavernreborn:cavern`.

## Follow-Up Tool Slice

- The first hexcite tool set MVP is documented separately in `docs/hexcite-tool-parity.md`.
- The first Miner's Orb follow-up is documented separately in `docs/miner-orb-mvp.md`.
- The first Ore Compass follow-up is documented separately in `docs/ore-compass-mvp.md`.
- That follow-up slice adds the five core `hexcite` tools only.
- Armor remains out of scope there as well.
- No special tool abilities were added on top of the special ore tranche.

## Modern Reborn Slice

- `hexcite_ore` drops `hexcite` with Fortune-aware ore loot and a simple smelt/blast fallback into the same gem.
- `hexcite_block` compresses from `9x hexcite` and decompresses back to `9x hexcite`.
- `randomite_ore` always drops one item from a checked-in weighted pool and keeps stack growth bounded.
- `randomite_ore` includes a rare food branch and now also includes a very rare `miner_orb`, but still does not use a full registry random mechanic.
- `fissured_stone` keeps the legacy identity of "special block, no normal item drop, special break outcome" while routing that outcome through a deterministic policy plus a small runtime translation layer.
- Worldgen wiring stays narrow: `hexcite`, `randomite` and `fissured_stone` are added only to the current `CAVERN` biome family and only through checked-in configured/placed features.

## Intentional Compromises

- This is still not full legacy ore parity.
- No armor, mobs, economy hooks or additional dimensions are added here.
- `randomite_ore` does not try to reproduce the old "anything in the registry" chaos.
- `fissured_stone` keeps only positive or neutral local effects in this pass.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied legacy visual assets for `hexcite`, `randomite`, `fissured_stone`, `miner_orb` and `ore_compass` came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy textures.
