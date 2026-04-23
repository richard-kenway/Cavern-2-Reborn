# Cavern II: Reborn

Bootstrap for the modern rewrite of Cavern on `Minecraft 1.21.1 + NeoForge`.

## Current Status

This repository currently contains the project skeleton and a minimal content registration slice:

- `app-neoforge` for the NeoForge entrypoint and runtime wiring
- `core` for loader-agnostic gameplay rules and domain models
- `data` for serialization, schema and persistence-oriented types
- a bootstrap block, `BlockItem` and creative tab used to validate registry wiring
- a return-state, portal block flow and `CAVERN` travel skeleton for the first `CAVERN` architecture phase
- a registered `CAVERN` target dimension with a data-driven cave-biome family and the first real safe-arrival check on top of that skeleton
- custom cave-like dimension effects for `CAVERN`, used to reduce visible sky and sun leakage in large open cavities
- a bounded `contained_caves` noise-settings fork of vanilla `minecraft:caves`, now tuned into a dry 192-block cavern profile with ore veins enabled and portal-compatible vertical assumptions kept intact
- a terrain-signature parity pass on top of `contained_caves`: denser stone mass, a stronger tunnel/chamber band, broader ravine cuts, an upper extreme-like chamber band and a lower hot band that stays below the current portal safe-arrival window
- a first population-parity pass on top of that terrain slice: slightly stronger monster-room pressure, biome-biased water/lava spring passes and extra mushroom scatter so `CAVERN` reads as a more populated underground world without adding a new content roster
- a first ore/content-parity pass on top of the terrain and population baselines: `aquamarine` and `magnite` now exist as cavern-native ore families with registered blocks/items, minimal storage loops and narrow `CAVERN` worldgen wiring instead of relying only on vanilla ore identity
- a first bounded Aquamarine Utility Tools MVP on top of that ore-family slice: `aquamarine_pickaxe`, `aquamarine_axe` and `aquamarine_shovel` now exist as repairable utility equipment with underwater break-speed utility only, while aquamarine sword/hoe/armor remain out of scope
- a first bounded Magnite Tool Set MVP on top of that same ore-family slice: `magnite_sword`, `magnite_pickaxe`, `magnite_axe` and `magnite_shovel` now exist as repairable brittle/high-speed specialist equipment, while magnite armor remains out of scope
- a second special ore/content tranche on top of that slice: `hexcite`, `randomite` and `fissured_stone` now exist as bounded custom `CAVERN` content with checked-in assets, loot, recipes, worldgen and non-griefing fissure behavior instead of broad legacy-system ports
- a first hexcite tool-set MVP on top of that special ore slice: `hexcite_pickaxe`, `hexcite_axe`, `hexcite_shovel`, `hexcite_hoe` and `hexcite_sword` now exist as bounded craftable equipment with `hexcite` repair support, while special abilities remain out of scope
- a first bounded Hexcite Armor MVP on top of that same equipment line: `hexcite_helmet`, `hexcite_chestplate`, `hexcite_leggings` and `hexcite_boots` now exist as craftable, repairable, diamond-adjacent armor that does not add a set bonus or other new gameplay systems
- a first progression-gated Mining Assist MVP on top of the progression baseline and the hexcite tool slice: `Mining Assist` now unlocks at `journeyman` and lets `hexcite_pickaxe` break up to 6 extra connected ore blocks inside `CAVERN`, while sneaking disables the assist for that break and assisted extra blocks do not grant extra progression score
- a first Miner's Orb MVP on top of the progression and mining loop: `miner_orb` now exists as a rare `randomite_ore` drop and can occasionally grant a small extra `CAVERN` progression-score bonus without being consumed
- a bounded Ore Compass MVP plus bounded tracking UX follow-up on top of that mining loop: `ore_compass` is now crafted from `miner_orb`, provides a bounded server-side `CAVERN` ore scan, stores its last successful target and can track that target visually while GUI target selection and full legacy parity remain out of scope
- a conscious four-biome `CAVERN` family closer to the old mining dimension baseline: dominant `stone_depths`, plus `lush_grotto`, `dripstone_grotto` and `highland_hollows`
- a mining-oriented worldgen baseline for `CAVERN`: dense coal/iron passes, biome-shaped gold/emerald bias, denser monster rooms and mineshaft-enabled cave biomes
- basic portal UX feedback for `cooldown`, failed cavern entry and missing return-state denial cases, plus an overworld fallback return target when no saved return-state exists
- a legacy-like `CAVERN` ritual activation step: any frame block in the `cavernreborn:cavern_portal_frames` block tag can now be activated with any item in the `cavernreborn:cavern_portal_activators` item tag via a server-side frame-click hook and fill the `cavern_portal` interior block
- a first entity-inside portal flow step: walking into the `cavern_portal` interior block now triggers the main transfer loop; right-click interaction is disabled so runtime usage is now collision-only
- frame-level portal identity semantics: interaction now canonicalizes a touched interior block to the portal frame anchor before building keys, cooldowns and portal placements
- legacy-like portal collision eligibility policy is now applied at block level before travel dispatch; dead/crouching/spectator/passenger/vehicle/projectile/cooldown filters are explicit and test-covered, non-portal-capable entities are now rejected up front, and bounded non-player transport now uses an entity-persisted entry receipt instead of the player return-state store
- an axis-aware `cavern_portal` interior plane with thin portal geometry and frame-integrity invalidation when a tagged portal frame block is broken
- a first destination portal placement step: travel now uses a configurable legacy-like find-or-create portal target in the destination dimension instead of requiring a second portal to be placed manually every time
- a persistent control-plane state backend: player return-state and world portal indices now survive server restarts through overworld-level NeoForge `SavedData`
- a minimal server-side `CAVERN` progression shell: player-scoped mining counters, weighted progression score and deterministic rank evaluation now persist through the same overworld-level `SavedData` control plane
- cavern-only mining accounting for progression: uncanceled non-creative player block breaks count only for the fixed baseline ore list inside `CAVERN`; that list now includes the checked-in custom cavern ores, while overworld mining and unsupported blocks do not move the cavern-specific state
- a minimal dev inspection path for progression: `/cavern progression` reports the current player score/rank/counts, and `/cavern progression <player>` lets op/console inspect another player
- a first player-facing progression layer: `/cavern rank` now shows a compact player-oriented rank summary, and threshold crossing sends a rank-up overlay instead of leaving progression fully hidden behind the debug command
- a first real progression consequence: `Miner's Insight` unlocks at `apprentice` and grants `+1` bonus XP on each counted ore break inside `CAVERN`, derived directly from the persisted rank with no second progression model
- a first bounded reward and eligibility layer: `/cavern rewards` shows the current reward surface, `/cavern claim <reward>` claims eligible one-time rewards, and the checked-in baseline now includes `apprentice_supply_cache` at `apprentice` plus `journeyman_supply_cache` at `journeyman`
- a first bounded interaction and service layer: `/cavern services` shows available services, `/cavern request <service>` uses an eligible service, and the checked-in baseline now includes `torch_supply` at `apprentice` plus `climbing_supply` at `journeyman`
- a first tiered catalog/shop-like surface: `/cavern catalog` aggregates rank-gated rewards and services into one compact player-facing view, while `/cavern use <entry>` provides a unified interaction path without adding GUI or currency
- a first bounded in-game menu-like presentation layer: `/cavern menu` opens a clickable chat menu over the same tiered catalog, and `/cavern menu use <entry>` routes into the same backend interaction semantics without adding a second shop model
- a first bounded in-game GUI/container layer: `/cavern gui` opens a chest-like inventory view over the same tiered catalog backend and routes slot clicks through the same reward/service use semantics without adding a second shop model
- a bounded nearby portal relink step: travel now searches for an existing destination portal near the target and relinks stale index entries before creating a new frame
- a bounded destination portal regeneration step: when an indexed destination portal is gone and nearby relink fails, travel now tries to rebuild a replacement portal near the stale anchor before falling back to generic create
- `PortalWorldIndex` now promotes the most recently reused, relinked or recreated placement to the front, so portal churn prefers fresh anchors over older stale records
- exact indexed portal reuse now rehydrates the actual placement from the world before promoting it, so stale stored axis values do not keep reinforcing themselves
- axis-aware portal placement semantics: destination portal indices now persist portal axis, and arrival is centered to the interior portal plane instead of the raw frame anchor
- bounded placement-quality scoring for auto-created destination portals: creation now prefers closer and safer frame anchors instead of the first valid spot in the search window
- aligned portal creation and activation semantics: auto-create and regeneration now accept only interiors that the activator can actually fill, avoiding naked frame shells from activation mismatches
- fallback return now teleports directly to the bounded overworld fallback target instead of running destination-portal creation logic near shared spawn
- bounded portal-relative exit semantics: destination arrival now preserves a clamped lateral offset from the source portal plane instead of always dropping the player into the exact portal center
- bounded portal-relative facing semantics: destination exit yaw now remaps stored approach-facing by portal axis instead of always preserving the pre-teleport player yaw

No full legacy-parity `CAVERN` gameplay stack is implemented yet.

## Current Limitations

- `CAVERN` is now noticeably closer to the old mining-dimension profile, but it is still a bounded modern stand-in rather than full legacy-parity worldgen.
- The current `contained_caves` preset is intentionally a narrow fork of vanilla `minecraft:caves`, not a new long-term worldgen direction.
- The current terrain profile is still a tuned vanilla-noise fork, not a literal port of the old 1.12 chunk generator.
- The current lower lava feel is still a modern approximation through surface rules and existing lava features, not a literal port of the old carve-time lava fill.
- The current dry-out pass keeps `sea_level` pinned to `min_y`, so the normal `CAVERN` baseline no longer has an operative flood-line inside its playable volume.
- Collision handling now applies a legacy-like eligibility filter matrix in the portal block, and a bounded non-player transport path now exists for eligible entities, but it still is not full legacy parity for bosses, broader entity classes or richer cache semantics.
- The new data-driven cave-biome family is now a conscious four-biome baseline, and a dedicated-server validation note exists for the current runtime slice; a full visual client pass across remote regions is still pending.
- Large open cavities may still appear in the current baseline; custom cave-like dimension effects now handle sky/sun leakage, but the overall visual result still needs manual in-game validation.
- The tunnel/ravine tuning is still a bounded density-function pass rather than a full custom carve stack.
- The current baseline restores only the worldgen slices that materially affect terrain, biome identity, mining usefulness and relevant cave features; legacy custom ores/content and extra dimensions are still out of scope.
- Safe arrival currently relies on a bounded local search around the target column and may cancel entry if no safe point is found nearby.
- Return-state and world portal indices now persist through an overworld-level `SavedData` control plane, but this is still a bounded MVP backend rather than full player/world attachment wiring.
- The current progression shell is intentionally narrow: it counts only a fixed baseline ore list in `CAVERN`, uses no config-driven scoring table and currently drives only one small unlock plus lightweight player-facing feedback.
- Progression currently listens to uncanceled server-side block-break events from non-creative players; it tracks qualifying block breaks, not item pickup, smelting or broader activity telemetry.
- The new persistent backend still needs manual restart validation on a real dedicated server, especially for `portal -> CAVERN -> restart -> return` and indexed destination-portal reuse after restart.
- The current portal flow now supports an axis-aware thin interior portal plane with frame-integrity invalidation; full legacy collision semantics still need manual validation.
- Portal interaction now canonicalizes touched interior blocks to a frame-level anchor, but this still needs manual validation across different interior blocks of the same portal.
- Destination portal placement is now automatic in a bounded search-relink-regenerate-or-create form, but it still does not implement full legacy cache, wider radius search and broader regeneration semantics.
- `CAVERN` now validates against the `cavernreborn:cavern_portal_frames` block tag and auto-create/regenerate still use the checked-in mossy-cobblestone generation default.
- Player-facing ritual activation for `CAVERN` now uses the `cavernreborn:cavern_portal_activators` item tag on a valid tagged frame block and points into the portal interior.
- The older `cavern_portal_trigger` runtime path has been removed; older worlds or inventories that still contained that item may now surface it as an unknown/removed item until they are cleaned up manually.
- Portal index churn now prefers the most recently reused placement, but broader eviction and history policies for repeated portal churn still are not implemented.
- Persistent world portal-index loading now skips invalid placement entries instead of dropping the entire world index, but broader corruption-repair tooling is still not implemented.
- Destination portal arrival is now centered by stored portal axis, but this still needs manual in-game validation for both axes and for relinked/recreated portals after index churn.
- Portal-relative exit placement now preserves only a bounded lateral offset inside the destination portal plane; full facing/orientation parity still needs manual validation, especially for cross-axis transfers.
- Portal-relative facing now remaps yaw in a bounded way for same-axis and cross-axis transfers, but full legacy orientation parity still needs manual validation before the older fallback path can be retired.
- Collision-triggered portal entry now derives `approachFacing` from real motion across the portal plane before falling back to look direction, but this still needs manual validation for backpedal and strafe entry cases.
- Eligible non-player entities now keep a bounded portal-entry receipt on the entity itself so they can enter `CAVERN` and return through the same portal loop, but this still needs manual validation for cross-dimension transport and missing-receipt denial cases.
- Collision eligibility now also rejects entities that report `canUsePortal(false) == false`, which is a bounded modern stand-in for the old legacy non-boss barrier and still needs manual validation on real boss-like or modded entities.
- Auto-created destination portals now use bounded placement-quality scoring, but the resulting anchor quality still needs manual validation in awkward terrain, near hazards and after repeated recreate/relink scenarios.
- Portal create/regenerate now uses the same interior contract as activation, but this still needs manual validation in terrain with replaceable non-air filler to confirm that failed activation no longer leaves naked frame shells behind.
- Portal denial feedback currently uses short overlay messages only; there is no broader notification policy yet.
- Cooldown, feedback suppression and portal search windows are now configurable through `config/cavernreborn-portal.properties`, but still need manual playtesting for final tuning.
- Legacy player-facing branches such as `portalMenu`, shop flow, economy, rewards trees and broader rank gating are intentionally not part of the current MVP slice.
- The current `/cavern menu` and `/cavern gui` layers are bounded presentations over the same catalog baseline; the GUI is a vanilla chest-like container, not a custom screen framework, currency shop or broader menu system.

## Structure

- `app-neoforge` - thin NeoForge bootstrap layer.
- `core` - future gameplay/domain layer.
- `data` - future data and schema layer.

## Design Notes

- Single target now, portable later.
- `CAVERN` is the MVP baseline.
- `HUGE_CAVERN`, `AQUA_CAVERN` and all mirage worlds are intentionally out of MVP scope.

## Portal Tuning

- Runtime portal tuning now lives in `config/cavernreborn-portal.properties`.
- The checked-in portal properties currently cover only portal search windows and cooldown semantics used by the runtime flow.
- The `cavernreborn:cavern_portal_frames` and `cavernreborn:cavern_portal_activators` tags are the source of truth for allowed frame blocks and allowed activators.
- Newly generated portal frames still default to `minecraft:mossy_cobblestone`.
- That generation default does not narrow the allowlist; both legacy frame blocks remain allowed through tags.
- The regression-protected portal baseline, supported cases and required runtime smoke-checks are documented in `docs/portal-baseline.md`.
- The checked-in defaults restore the old `findPortalRange = 32` behavior more closely than the previous hardcoded `8/6` and `4/2` windows.

## Worldgen Baseline

- Runtime worldgen source-of-truth lives in the checked-in data resources:
  - `data/cavernreborn/dimension/cavern.json`
  - `data/cavernreborn/worldgen/noise_settings/contained_caves.json`
  - `data/cavernreborn/worldgen/density_function/cave_tunnel_network.json`
  - `data/cavernreborn/worldgen/density_function/cave_ravine_network.json`
  - `data/cavernreborn/worldgen/density_function/cave_extreme_upper_network.json`
  - `data/cavernreborn/worldgen/biome/*.json`
  - `data/cavernreborn/worldgen/placed_feature/*.json`
  - `data/minecraft/tags/worldgen/biome/has_structure/mineshaft.json`
- The narrow terrain-signature parity note for this tranche lives in `docs/cavern-worldgen-parity.md`.
- The current biome baseline is intentionally small and explicit: `stone_depths`, `lush_grotto`, `dripstone_grotto`, `highland_hollows`.
- Newly added biome, placed-feature and structure-tag resources are currently checked in under `app-neoforge/src/generated/resources`, which is part of the runtime resource set via `build.gradle`.
- Runtime validation is split between automated resource/classpath checks and a dedicated-server validation note at `docs/worldgen-runtime-validation-2026-04-18.md`.
- `stone_depths` is the dominant mining biome; `lush_grotto` carries the humid vegetation slice; `dripstone_grotto` carries the dry mineral slice; `highland_hollows` is the mountain/hill stand-in for richer emerald pockets.
- Terrain signature now intentionally emphasizes carved stone mass, larger upper cavities, stronger middle ravine variation and a lower `Y < -32` hot band.
- Mining usefulness now comes from the baseline ore set plus enabled ore veins, dense coal/iron passes, extra gold in `dripstone_grotto` and extra emerald in `highland_hollows`.
- The first narrow custom ore/content note for `aquamarine` and `magnite` lives in `docs/cavern-ore-content-parity.md`.
- The first bounded Aquamarine Utility Tools MVP note lives in `docs/aquamarine-tool-mvp.md`.
- The second narrow special-ore/content note for `hexcite`, `randomite`, `fissured_stone`, bounded randomite drops and non-griefing fissure behavior lives in `docs/cavern-special-ore-content-parity.md`.
- The first bounded hexcite tool-set MVP note lives in `docs/hexcite-tool-parity.md`.
- The first bounded Mining Assist note lives in `docs/mining-assist-mvp.md`.
- The first bounded Miner's Orb note lives in `docs/miner-orb-mvp.md`.
- If you use the dedicated-server console for biome distance checks, set a known source position first, for example `setworldspawn 0 70 0`, before comparing biome distances in `CAVERN`.
- Relevant cave features/structures in the baseline are amethyst geodes, lava lakes, fluid springs, lush/dripstone decoration, denser monster rooms and mineshafts.
- The first narrow population-parity note for post-terrain cave dressing lives in `docs/cavern-population-parity.md`.
- The regression-protected worldgen baseline, intentional compromises and runtime checklist are documented in `docs/worldgen-baseline.md`.

## Automated Runtime Validation

- NeoForge GameTest is now the automated runtime smoke layer for tranche 2 special ore/content parity.
- Run the checked-in validation sequence with:
  - `./gradlew --no-daemon test`
  - `./gradlew --no-daemon :app-neoforge:runGameTestServer`
  - `./gradlew --no-daemon build`
- Or run the executable wrapper directly:
  - `scripts/runtime-smoke.sh`
- The runtime smoke scope is documented in `docs/runtime-smoke.md`.
- The current GameTest slice covers runtime registry availability, `aquamarine` tool runtime ids/repairability/enchantability, `aquamarine` underwater utility policy smoke, `magnite` tool runtime ids/repairability/enchantability plus brittle no-Assist smoke, `hexcite` normal and Silk Touch loot, `hexcite` tool runtime registry/mining paths, `hexcite` armor runtime ids/repairability/enchantability, `miner_orb` runtime registry/policy smoke, `ore_compass` runtime registry/scanner paths, curated `randomite` runtime drops, `fissured_stone` no-drop/effect/creative-guard/non-destructive behavior, progression policy ids/scores and special-ore worldgen key resolution.
- The same runtime smoke slice now also covers `ore_compass` stored-target state round-trip and tracking policy/runtime-id smoke, while actual client-visible needle feel still remains on the manual smoke checklist.
- The current GameTest slice also covers Mining Assist runtime ids, bounded same-block vein breaks with `hexcite_pickaxe`, unlock-gated/no-unlock behavior, `fissured_stone` exclusion and target-preservation smoke.
- The first Ore Compass MVP is documented in `docs/ore-compass-mvp.md`.
- The first Hexcite Armor MVP is documented in `docs/hexcite-armor-mvp.md`.
- The first Aquamarine Utility Tools MVP is documented in `docs/aquamarine-tool-mvp.md`.
- The first Magnite Tool Set MVP is documented in `docs/magnite-tool-mvp.md`.
- This is still a server-side smoke layer, not a substitute for manual client validation of portal UX, rendering, particles, sounds or overall player feel.

## Progression Baseline

- Runtime progression source-of-truth is code, not config: `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionPolicy.java` defines the counted block list, per-block score deltas and rank thresholds.
- Player progression state is persisted in the same overworld-level `cavernreborn_control_plane` `SavedData` file already used by the portal control plane.
- The current baseline counts only uncanceled non-creative player block breaks for the fixed ore list inside `cavernreborn:cavern`, including the checked-in custom cavern ores `aquamarine_ore`, `magnite_ore`, `randomite_ore`, `fissured_stone` and `hexcite_ore`.
- Rank is derived deterministically from the persisted score; it is not stored as a separate mutable field.
- `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionUnlock.java` is the checked-in unlock surface for future systems; the first unlock is `Miner's Insight`, which activates at `apprentice`.
- `Miner's Insight` currently grants `+1` bonus XP on each counted ore break inside `CAVERN`; the effect is derived from the persisted rank at runtime and does not add a second saved flag.
- `Mining Assist` is the next rank-derived unlock at `journeyman`; it remains server-side only, works only inside `CAVERN`, requires `hexcite_pickaxe`, breaks only tagged same-block ore veins, stops after at most 6 extra blocks and does not add extra progression score for those assisted extra breaks in this MVP.
- `Miner's Orb` is now a bounded item-based follow-up on top of the same progression loop: it drops very rarely from `randomite_ore`, is not consumed, does not add a second saved unlock flag and can occasionally add a small extra score bonus on counted `CAVERN` ore breaks without changing counted-block totals.
- `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionReward.java` is the checked-in reward catalog surface for the next gameplay layers; the current baseline is tiered: `apprentice_supply_cache` at `apprentice` and `journeyman_supply_cache` at `journeyman`.
- `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernServiceEntry.java` is the checked-in repeatable service catalog surface; the current baseline is `torch_supply` at `apprentice` and `climbing_supply` at `journeyman`.
- `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernInteractionService.java` now also projects the first compact tiered catalog surface used by `/cavern catalog` and `/cavern use <entry>`.
- `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernPlayerMenuFormatter.java` is the first bounded in-game presentation layer for that catalog; it drives `/cavern menu` and the clickable `/cavern menu use <entry>` path without adding new business rules.
- `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernCatalogAccess.java`, `CavernCatalogGuiLayout.java` and `CavernCatalogGuiMenu.java` add the first bounded inventory/container GUI over that same backend catalog; `/cavern gui` reuses the same persisted progression, reward and service semantics instead of introducing a second menu model.
- Reward eligibility is derived from the same persisted progression score/rank; only claimed reward ids are stored persistently.
- Service cooldown state is derived from the same persisted progression and per-player service timestamps; only last-use timestamps are stored persistently.
- Use `/cavern rank` for the player-facing rank summary, `/cavern progression` for the dev/debug summary, `/cavern rewards` and `/cavern claim <reward>` for the reward path, `/cavern services` and `/cavern request <service>` for the service path, `/cavern catalog` plus `/cavern use <entry>` for the compact tiered aggregation layer, `/cavern menu` plus `/cavern menu use <entry>` for the clickable chat presentation, and `/cavern gui` for the first bounded chest-like inventory GUI over that same backend state. The catalog/menu/gui layers group apprentice and journeyman entries, show the next unlock tier and still read the same persisted progression baseline as the low-level commands.
- The regression-protected progression baseline, intentional compromises and local verification checklist are documented in `docs/progression-baseline.md`.

## Docker Build

This repository can be validated inside Docker without installing Java on the host machine.

- Build and run the default Gradle sanity check:
  - `docker compose run --rm gradle`
- Run a specific Gradle task:
  - `docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:help`
  - `docker compose run --rm gradle ./gradlew --no-daemon build`
  - `docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:runData`

The container uses JDK 21 and the project Gradle wrapper.

## Docker Dev Server

For repeated `CAVERN` worldgen and portal checks, use the dedicated local dev server instead of rebuilding a fresh singleplayer world every time.

- Start the server:
  - `scripts/dev-server/up.sh`
- Stop the server:
  - `scripts/dev-server/down.sh`
- Restart the server after server-side changes:
  - `scripts/dev-server/restart.sh`
- Tail server logs:
  - `scripts/dev-server/logs.sh`
- Reset only the generated `CAVERN` dimension:
  - `scripts/dev-server/reset-cavern.sh`
- Reset the full server world:
  - `scripts/dev-server/reset-world.sh`
- Build the server-side mod JAR and sync it into `dev-server/server/mods`:
  - `scripts/dev-server/build-server-mod.sh`
- Build and sync the mod JAR into both the dedicated server and the CurseForge client:
  - `scripts/dev-server/build-mods.sh`
- Build the client mod JAR and sync it into the CurseForge instance only:
  - `scripts/dev-server/build-client-mod.sh`

Practical workflow:

1. Keep one persistent local multiplayer world on the dedicated dev server.
2. If you change only server-side logic or worldgen resources, run `scripts/dev-server/build-server-mod.sh`, then restart the server and optionally reset just `CAVERN`.
3. If you change shared or client-loaded code/resources, run `scripts/dev-server/build-mods.sh` and restart the client too.
4. Connect the client to `localhost:25585` by default.

Notes:

- The dedicated dev server installs NeoForge into `dev-server/server` on first start and then loads `cavernreborn-0.1.0.jar` from `dev-server/server/mods`.
- The first dedicated-server start can take a while because NeoForge server runtime is installed into `dev-server/server`.
- The server world lives under `dev-server/server/world`.
- `scripts/dev-server/reset-world.sh` and `scripts/dev-server/reset-cavern.sh` now require the server to be stopped first and can clean up container-owned world files through Docker when needed.
- The generated `runs/` directories are ignored by git, so resets and local test worlds do not dirty the repository.
- If `25585` is busy too, set a different host port with `DEV_SERVER_PORT=<port> scripts/dev-server/up.sh`.


## License

This project is licensed under the GNU General Public License v3.0 only (`GPL-3.0-only`). See `LICENSE` for the full license text.

The licensing baseline for the original `Cavern II` project is GPLv3 as listed on CurseForge, and this repository is published under GPLv3 accordingly.

Copied legacy texture assets used for `hexcite`, `randomite`, `fissured_stone`, `miner_orb`, `ore_compass`, `hexcite` armor and `aquamarine` tools are documented for provenance in `docs/cavern-special-ore-content-parity.md`, `docs/miner-orb-mvp.md`, `docs/ore-compass-mvp.md`, `docs/hexcite-armor-mvp.md` and `docs/aquamarine-tool-mvp.md`. The original legacy code baseline was GPLv3, and the original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.
