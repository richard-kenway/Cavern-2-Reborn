# Miner's Orb MVP

This note fixes the first bounded `Miner's Orb` slice for `CAVERN`.

It builds on top of `docs/cavern-special-ore-content-parity.md`, `docs/hexcite-tool-parity.md` and `docs/mining-assist-mvp.md`.

## Added In This MVP

- item id: `cavernreborn:miner_orb`
- very rare `randomite_ore` acquisition
- a small item-based progression-score bonus path for counted `CAVERN` ore breaks

## Bounded Modern Scope

- This is a bounded Miner's Orb MVP, not a full legacy Miner system.
- `miner_orb` is a simple inventory item and is not consumed by its bonus path.
- Multiple `miner_orb` items do not stack the chance or the score bonus.
- The bonus roll is `10%` on counted `CAVERN` ore breaks only.
- The bonus score is `max(base score / 2, 1)`.
- The bonus affects progression score only.
- The bonus does not increment counted block totals.
- The bonus does not add extra `Miner's Insight` XP.
- The bonus does not apply to Mining Assist extra blocks.
- No `cavenic_orb` is added here.
- Ore Compass MVP now exists separately in `docs/ore-compass-mvp.md` and uses `miner_orb` as a recipe ingredient.
- The Miner's Orb bonus behavior remains unchanged by that follow-up item slice.
- No particles, sounds, client GUI or keybinds are added in this MVP.

## Runtime Coverage

- NeoForge GameTest runtime smoke checks the `miner_orb` runtime registry id.
- Runtime smoke verifies the curated `randomite_ore` allowed-drop set includes `miner_orb`.
- Runtime smoke verifies the bounded Miner's Orb bonus policy with runtime ids.

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied `miner_orb` item texture came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy texture.
