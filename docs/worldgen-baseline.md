# CAVERN Worldgen Baseline

This document fixes the current `CAVERN` worldgen slice as the regression-protected baseline on `main`.

It is closer to `Cavern 2` than the earlier generic cave placeholder, but it is not a claim of full legacy parity.

## Expected Behavior

- `CAVERN` stays a dry 192-block-tall cave dimension with `min_y = -64`, `height = 192` and `sea_level = -64`.
- Terrain is still driven by the bounded `contained_caves` fork, but the runtime baseline now uses `size_vertical = 2`, a denser horizontal tunnel layer and a broader ravine-like connector band around the main playable heights.
- The world remains arrival-compatible with the current portal slice: the safe-arrival search still targets the same vertical band, and portal create/relink/regenerate logic still relies on existing local cavities rather than carving new ones.
- The biome family is now a conscious four-biome baseline instead of two borrowed vanilla cave biomes:
  - `cavernreborn:stone_depths` is the dominant mining biome.
  - `cavernreborn:lush_grotto` is the humid vegetation slice.
  - `cavernreborn:dripstone_grotto` is the dry mineral slice.
  - `cavernreborn:highland_hollows` is the mountain/hill stand-in for richer upper mineral pockets.
- Surface exposure is intentionally biome-shaped:
  - `lush_grotto` uses `moss_block` floors and `rooted_dirt` ceilings.
  - `dripstone_grotto` uses `dripstone_block` on exposed floors and ceilings.
  - `highland_hollows` uses `tuff` on exposed surfaces.
  - deeper exposed surfaces fall back to `deepslate`.
- Resource generation is mining-oriented by default:
  - the vanilla overworld cave ore set stays present in each custom biome;
  - iron/copper ore veins are enabled through the dimension noise settings;
  - extra dense coal and iron placed features are added across the biome family;
  - `dripstone_grotto` adds extra gold;
  - `highland_hollows` adds extra emerald.
- Relevant cave features and structures are explicitly part of the baseline:
  - amethyst geodes;
  - lava lakes and fluid springs;
  - lush cave vegetation where appropriate;
  - dripstone clusters and large dripstone where appropriate;
  - denser monster-room placement than the earlier placeholder baseline;
  - mineshafts via the biome structure tag.

## Runtime Inputs

- Dimension definition: `data/cavernreborn/dimension/cavern.json`
- Dimension type: `data/cavernreborn/dimension_type/cavern.json`
- Noise settings: `data/cavernreborn/worldgen/noise_settings/contained_caves.json`
- Density tuning: `data/cavernreborn/worldgen/density_function/cave_tunnel_network.json`
- Density tuning: `data/cavernreborn/worldgen/density_function/cave_ravine_network.json`
- Biome baseline: `data/cavernreborn/worldgen/biome/*.json`
- Mining/features baseline: `data/cavernreborn/worldgen/placed_feature/*.json`
- Structure enablement: `data/minecraft/tags/worldgen/biome/has_structure/mineshaft.json`
- The new biome, placed-feature and structure-tag resources are currently checked in under `app-neoforge/src/generated/resources`, which is part of the runtime resource set via `build.gradle`.

## Supported Cases

- Fresh world generation with a recognizable `CAVERN` identity instead of a minimal cave placeholder.
- Multiple remote regions with the same bounded biome family and mining/resource profile.
- Fresh portal arrival into a newly generated `CAVERN` chunk column.
- Portal create/relink/regenerate on the updated terrain profile.
- Restart-safe portal return flow on the updated dimension baseline.
- Remote biome checks for `stone_depths`, `lush_grotto`, `dripstone_grotto` and `highland_hollows`.
- Remote resource checks for dense coal/iron, ore veins, dripstone gold bias and highland emerald bias.
- Remote feature checks for monster rooms, lush/dripstone decoration and mineshafts.

## Intentional Compromises

- This is not a literal 1:1 port of the old 1.12 `Cavern 2` chunk generator.
- The old weighted overworld-biome transcription is approximated through four custom cave biomes, not restored biome-for-biome.
- The old custom ores, tower dungeons, mirage remnants, Huge Cavern and Aqua Cavern are still out of scope.
- The baseline still uses modern vanilla ore/features where possible instead of restoring the full legacy custom vein table.
- No new user-facing worldgen config surface was added for this pass; the runtime source of truth is the checked-in data resources above.

## Minimal Checklist

Run this before any larger worldgen or progression-shell change:

1. `docker compose run --rm gradle ./gradlew --no-daemon test build`
2. Generate a fresh world and enter `CAVERN` for the first time through the current portal flow.
3. Check the arrival zone around the first portal for a safe, non-flooded cavern placement.
4. Visit several remote `CAVERN` regions, not only the arrival zone.
5. Confirm the world still reads as a contained cavern dimension rather than a generic overworld cave carve.
6. Confirm the biome mix includes `stone_depths`, `lush_grotto`, `dripstone_grotto` and `highland_hollows`.
7. Confirm coal/iron feel abundant, iron/copper veins can appear, `dripstone_grotto` trends richer in gold and `highland_hollows` can expose emerald.
8. Confirm monster rooms, lush/dripstone decoration and mineshafts can all appear.
9. Break or invalidate a destination portal on the new terrain and verify create/relink/regenerate still keeps the loop usable.
10. Restart the server and verify a repeated `Overworld -> CAVERN -> Overworld` loop still works on the generated world.
