# Mining Assist MVP

This note fixes the first bounded Mining Assist slice for `CAVERN`.

It builds on top of `docs/cavern-special-ore-content-parity.md` and the follow-up `docs/hexcite-tool-parity.md`.

## Added In This MVP

- `Mining Assist` as a rank-derived progression unlock
- `hexcite_pickaxe` as the only valid Mining Assist tool
- a narrow ore-vein assist path for same-block connected ore targets

## Bounded Modern Scope

- This is a bounded first pass, not a full legacy Mining Assist port.
- The current slice is server-side only.
- The unlock is progression-gated and derived from rank instead of a second saved flag.
- The assist is `CAVERN`-only.
- The assist requires `cavernreborn:hexcite_pickaxe`.
- The assist only targets same-block connected ore veins from the checked-in mining-assist target tag.
- The assist breaks at most `6` extra blocks per origin break.
- Sneaking disables the assist for that break.
- Assisted extra blocks do not add extra progression score in this MVP.
- Assisted extra blocks also stay excluded from the Miner's Orb score bonus path.
- `fissured_stone` is intentionally excluded.
- No 3x3 mining, tunnel mining, GUI, keybinds, particles or sounds are added here.

## Unlock Policy

- `Miner's Insight`
  - rank: `apprentice`
  - effect: `+1` bonus XP on counted `CAVERN` ore breaks
- `Mining Assist`
  - rank: `journeyman`
  - effect: bounded `hexcite_pickaxe` vein assist inside `CAVERN`

## Runtime Coverage

- NeoForge GameTest runtime smoke now checks Mining Assist runtime ids.
- Runtime smoke verifies bounded same-block vein assist with `hexcite_pickaxe`.
- Runtime smoke verifies no-unlock and sneaking-disabled behavior.
- Runtime smoke verifies `fissured_stone` exclusion and target preservation.

## Intentional Compromises

- This slice does not reproduce full legacy Mining Assist modes.
- Assisted extra blocks use the normal runtime block-break path, but they are intentionally excluded from extra progression gain in this MVP.
- The current pass adds no visual or audio feedback beyond the normal block-breaking loop.
