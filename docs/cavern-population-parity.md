# CAVERN Population Parity

This note fixes the first narrow population tranche for `CAVERN`.

## Mandatory Legacy Signals

- More reliable cave dungeon / monster-room pressure after terrain generation.
- Cave fluid activity that reads as underground falls and springs instead of a mostly static carve result.
- Visible mushroom and cave-dressing scatter so populated slices feel less sterile.
- Biome-relevant decoration differences without introducing a new content roster.

## Deliberately Not In This Tranche

- Full ore or vein parity.
- Tower dungeons, mirage remnants or a broader structure port.
- New mobs, custom loot systems or gameplay progression hooks.
- New blocks, registries or a generic worldgen framework rewrite.

## Narrow Reborn Slice

- Reuse the existing `monster_room` baseline and raise its placement pressure slightly instead of porting tower dungeons.
- Add bounded extra water/lava spring passes as a modern stand-in for the old cave-fall feel, while keeping the extra lava pressure below the current arrival-safe band.
- Add narrow mushroom patch passes on top of the existing biome family and keep `lush_grotto` as the main vegetation-heavy slice.
- Keep everything data-driven through biome and placed-feature resources so the tranche stays small and compatible with the current portal and terrain baselines.
