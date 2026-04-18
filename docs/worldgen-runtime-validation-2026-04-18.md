# CAVERN Worldgen Runtime Validation — 2026-04-18

This note records a one-off headless dedicated-server validation pass for the current `CAVERN` worldgen baseline.

It is **not** the regression-protected baseline itself. The stable baseline lives in `docs/worldgen-baseline.md`; this note keeps the dated commands, coordinates and sampled observations separate so the baseline docs can stay focused on durable runtime expectations.

## Scope

- validation date: `2026-04-18`
- environment: local NeoForge dedicated server
- world type: fresh disposable world generated from the current built mod jar
- purpose:
  - confirm that generated worldgen resources are present in runtime;
  - confirm that the four-biome family is observable on generated chunks;
  - confirm that the new mining-oriented baseline shows up on real chunk data;
  - confirm that the `ore_coal_upper` warning disappeared after it was removed from the custom biome baseline.

## Validation Notes

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

## Limits Of This Run

- This was a headless server-console pass, not a visual client pass.
- Raw ore-vein blocks were not directly hit in the sampled cubes, even though the runtime noise router still enables ore veins.
- The sampled chunks hit mineshafts and lush/dripstone cave dressing, but did not directly hit a monster room or exposed `tuff` patch during this run.
- A full player-driven `Overworld -> CAVERN -> Overworld` visual loop after restart is still a separate manual smoke-check.
