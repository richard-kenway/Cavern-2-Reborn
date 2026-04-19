# CAVERN Terrain-Signature Parity

This note fixes the narrow terrain-signature tranche for `CAVERN`.

## Mandatory Legacy Signals

- A mostly solid stone world first, with caves carved out of it afterward.
- Larger cavities and stronger vertical contrast than a generic modern cave preset.
- A clear upper extreme-like cavern band and a lower hot/lava-oriented band.
- Ravine-like variation that breaks up uniform tunnel volume.
- Biome slices that change exposed floor and ceiling feel, not only ore/features.

## Deliberately Not In This Tranche

- Literal 1:1 port of the 1.12 generator or carve math.
- Structure, mob, magic, mirage or broader content parity.
- Full ore/content parity beyond the already checked-in baseline.
- New dimensions, new registries or a new worldgen framework.

## Modern Approximation

- Keep the current `contained_caves` dimension wiring and 192-block profile.
- Make the base density slightly more stone-heavy so the world reads as carved mass again.
- Layer three carve signatures on top of that mass:
  - a denser tunnel/chamber field for the playable middle band;
  - a broader ravine connector band;
  - an upper extreme-like chamber band with pillar support.
- Add a lower hot surface band below the portal safe-arrival search floor so lava-oriented terrain returns without degrading arrival safety.
- Strengthen biome-conditioned floor and ceiling replacement so `lush_grotto`, `dripstone_grotto` and `highland_hollows` are easier to distinguish by terrain feel.
