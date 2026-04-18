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
  - the multi-noise list now uses extra `dripstone_grotto` and `highland_hollows` anchor points so those non-dominant slices stay observable on a fresh world instead of collapsing into rare edge cases.
- Surface exposure is intentionally biome-shaped:
  - `lush_grotto` uses `moss_block` floors and `rooted_dirt` ceilings.
  - `dripstone_grotto` uses `dripstone_block` on exposed floors and ceilings.
  - `highland_hollows` uses `tuff` on exposed surfaces.
  - deeper exposed surfaces fall back to `deepslate`.
- Resource generation is mining-oriented by default:
  - the vanilla overworld cave ore set stays present in each custom biome;
  - the imported `minecraft:ore_coal_upper` pass is intentionally removed because it produces an empty-height warning in a 192-block-tall `CAVERN` and the custom dense coal pass already covers the upper mining band;
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

## Runtime Validation

Local headless validation was rerun on `2026-04-18` against a fresh dedicated-server world built from the current jar.

- Resource inclusion was confirmed through:
  - classpath-backed tests;
  - the built mod jar;
  - a dedicated NeoForge server loading the same jar from `dev-server/server/mods`.
- The server-console smoke-check used `setworldspawn 0 70 0` before `execute in cavernreborn:cavern run locate biome ...` so biome distance checks started from a known source position.
- The fresh world runtime confirmed:
  - `stone_depths` at `[0, 70, 0]`;
  - `lush_grotto` at `[-256, 70, -832]`;
  - `dripstone_grotto` at `[480, 70, -352]`;
  - `highland_hollows` at `[-128, 70, 128]`;
  - a nearby `minecraft:mineshaft` at `[-240, ~, -96]`.
- The same smoke-check sampled generated blocks on real chunks:
  - `stone_depths`: `590` `coal_ore` and `174` `iron_ore` replacements in a `17x97x17` sample around `[32, 70, 0]`;
  - `lush_grotto`: `88` `moss_block` and `12` `cave_vines` replacements in a `17x97x17` sample around `[-240, 70, -832]`;
  - `dripstone_grotto`: `26` `dripstone_block` replacements in an upper-band sample and `34` `deepslate_gold_ore` replacements in a lower-band sample around `[480, 70, -352]`;
  - `highland_hollows`: `4` and `5` `emerald_ore` replacements in adjacent `31x31x31` samples around `[-128, 70, 128]`.
- The earlier runtime warning `Empty height range: [136 absolute-0 below top]` no longer appears after removing the imported `ore_coal_upper` pass from the custom biome baseline.

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
- The headless runtime pass uses server-console commands and block-sampling on disposable chunks; it is strong enough to validate generation/runtime wiring, but it is not a substitute for a visual client pass.
- No new user-facing worldgen config surface was added for this pass; the runtime source of truth is the checked-in data resources above.

## Follow-Up Gaps

- Raw ore-vein blocks were not directly hit in the sampled cubes, even though the runtime noise router still enables ore veins.
- The sampled chunks hit mineshafts and lush/dripstone cave dressing, but did not directly hit a monster room or exposed `tuff` patch during the headless pass.
- A full player-driven `Overworld -> CAVERN -> Overworld` visual loop after restart is still a separate manual smoke-check; portal compatibility for this pass is otherwise protected by the existing automated portal regression suite.

## Minimal Checklist

Run this before any larger worldgen or progression-shell change:

1. `docker compose run --rm gradle ./gradlew --no-daemon test build`
2. Stop the dev server before resetting the world or the `CAVERN` dimension.
3. Generate a fresh world and enter `CAVERN` for the first time through the current portal flow.
4. Check the arrival zone around the first portal for a safe, non-flooded cavern placement.
5. Visit several remote `CAVERN` regions, not only the arrival zone.
6. If you use the dedicated-server console for biome distance checks, set a known source first, for example `setworldspawn 0 70 0`, before running `locate biome`.
7. Confirm the world still reads as a contained cavern dimension rather than a generic overworld cave carve.
8. Confirm the biome mix includes `stone_depths`, `lush_grotto`, `dripstone_grotto` and `highland_hollows`.
9. Confirm coal/iron feel abundant, `dripstone_grotto` trends richer in gold and `highland_hollows` can expose emerald.
10. Confirm lush/dripstone decoration and mineshafts can all appear, and check monster rooms if the sampled chunks expose one.
11. Break or invalidate a destination portal on the new terrain and verify create/relink/regenerate still keeps the loop usable.
12. Restart the server and verify a repeated `Overworld -> CAVERN -> Overworld` loop still works on the generated world.
