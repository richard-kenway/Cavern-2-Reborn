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
- a first bounded Magnite Tool Set MVP on top of that same ore-family slice: `magnite_sword`, `magnite_pickaxe`, `magnite_axe` and `magnite_shovel` now exist as repairable brittle/high-speed specialist equipment
- a first bounded Magnite Armor MVP on top of that same specialist line: `magnite_helmet`, `magnite_chestplate`, `magnite_leggings` and `magnite_boots` now exist as a brittle/enchant-friendly side-grade with no set bonus, while hexcite remains the main armor/combat line
- a first bounded Acresia Crop & Food MVP as a non-equipment cave-content follow-up: `acresia` now exists as a small renewable cave snack crop with seeds, fruits, bounded shear-harvest regrowth and sparse `lush_grotto` patch worldgen instead of a full farming system
- a first bounded Cavenic Shroom & Orb MVP as a hazardous flora follow-up: `cavenic_shroom` now exists as a mildly hazardous cave flora block with sparse `stone_depths` patch worldgen, while `cavenic_orb` now exists as a rare foundation item for later cavenic equipment; the bounded slice is documented in `docs/cavenic-shroom-mvp.md`
- a first bounded Cavenic Bow Baseline MVP plus mode-state, Snipe, Rapid, Torch and release-semantics follow-ups on top of that cavenic material line: `cavenic_bow` now exists as a repairable vanilla-baseline bow with stack-local mode state, sneak-use mode cycling, a bounded full-charge Snipe boost on vanilla arrows, a bounded Rapid power ramp on vanilla arrows, a bounded Torch marker-and-placement behavior on vanilla arrows and real release-path regression coverage for ammo, creative/no-arrow, Infinity and Torch-consumption semantics, while custom projectile entities and other special bow behavior are intentionally left for later follow-ups; the bounded slices are documented in `docs/cavenic-bow-baseline-mvp.md`, `docs/cavenic-bow-mode-state-mvp.md`, `docs/cavenic-bow-snipe-mode-mvp.md`, `docs/cavenic-bow-rapid-mode-mvp.md`, `docs/cavenic-bow-torch-mode-mvp.md` and `docs/cavenic-bow-release-semantics-mvp.md`
- a first bounded Cavenic Zombie Baseline MVP plus bounded natural-spawn, legacy-orb-drop and legacy-damage-behavior follow-ups on top of that same cavenic line: `cavenic_zombie` now exists as a hostile foundation mob with legacy-mapped core attributes, a legacy texture on the vanilla zombie renderer path, a bounded CAVERN-only natural spawning follow-up, the legacy `1/8` `cavenic_orb` drop appended on top of the vanilla zombie loot baseline and the legacy fall-damage reduction and fire-damage immunity behavior, documented in `docs/cavenic-zombie-baseline-mvp.md`, `docs/cavenic-zombie-natural-spawn-mvp.md`, `docs/cavenic-zombie-orb-drop-mvp.md` and `docs/cavenic-zombie-damage-behavior-mvp.md`
- a first bounded Cavenic Skeleton Baseline MVP plus bounded natural-spawn, legacy-orb-drop and legacy-damage-behavior follow-ups as the second direct Cavenic mob foundation on top of that same cavenic line: `cavenic_skeleton` now exists as a hostile foundation mob with legacy-mapped core attributes, a legacy texture on the vanilla skeleton renderer path, a baseline spawn egg, bounded CAVERN-only natural spawning, the legacy `1/5` `cavenic_orb` drop appended on top of the vanilla skeleton loot baseline and the legacy fall-damage reduction and fire-damage immunity behavior, while custom AI, custom arrow behavior and other legacy follow-ups remain intentionally out of scope; the bounded slices are documented in `docs/cavenic-skeleton-baseline-mvp.md`, `docs/cavenic-skeleton-natural-spawn-mvp.md`, `docs/cavenic-skeleton-orb-drop-mvp.md` and `docs/cavenic-skeleton-damage-behavior-mvp.md`
- a first bounded Cavenic Creeper Baseline MVP plus bounded natural-spawn, legacy-orb-drop, legacy-damage-behavior and legacy-fuse/explosion follow-ups as the third direct Cavenic mob foundation on top of that same cavenic line: `cavenic_creeper` now exists as a hostile foundation mob with legacy-mapped core attributes, a legacy texture on the vanilla creeper renderer path, a baseline spawn egg, bounded CAVERN-only natural spawning, the legacy `1/5` `cavenic_orb` drop appended on top of the vanilla creeper loot baseline, the legacy fall-damage reduction and fire-damage immunity behavior and the legacy `Fuse = 15` and `ExplosionRadius = 5` values, while custom explosion behavior remains intentionally out of scope for later bounded follow-ups; the bounded slices are documented in `docs/cavenic-creeper-baseline-mvp.md`, `docs/cavenic-creeper-natural-spawn-mvp.md`, `docs/cavenic-creeper-orb-drop-mvp.md`, `docs/cavenic-creeper-damage-behavior-mvp.md` and `docs/cavenic-creeper-fuse-explosion-mvp.md`
- a first bounded Cavenic Spider Baseline MVP plus bounded natural-spawn, legacy-orb-drop, legacy-damage-behavior and blindness-on-hit follow-ups as the fourth direct Cavenic mob foundation on top of that same cavenic line: `cavenic_spider` now exists as a hostile foundation mob with legacy-mapped core attributes, a legacy texture on the vanilla spider renderer path, a baseline spawn egg, a vanilla spider loot baseline, bounded CAVERN-only natural spawning, the legacy `1/8` `cavenic_orb` drop appended on top of that vanilla baseline, the legacy fall-damage reduction and fire-damage immunity behavior and the legacy difficulty-scaled blindness-on-hit behavior, while poison, web and other special spider behavior remain intentionally out of scope for later bounded follow-ups; the bounded slices are documented in `docs/cavenic-spider-baseline-mvp.md`, `docs/cavenic-spider-natural-spawn-mvp.md`, `docs/cavenic-spider-orb-drop-mvp.md`, `docs/cavenic-spider-damage-behavior-mvp.md` and `docs/cavenic-spider-blindness-on-hit-mvp.md`
- a first bounded Cavenic Witch Baseline MVP plus bounded natural-spawn, legacy-loot, legacy-damage-behavior, same-type projectile-immunity, friendship-targeting and custom-ranged-potion follow-ups as the fifth direct Cavenic mob foundation on top of that same cavenic line: `cavenic_witch` now exists as a hostile foundation mob with legacy-mapped core attributes, a legacy texture on the vanilla witch renderer path, a baseline spawn egg, a vanilla witch loot baseline, bounded CAVERN-only natural spawning, the legacy `1/5` `cavenic_orb` drop appended on top of the vanilla witch loot baseline, the legacy fall-damage reduction and fire-damage immunity behavior, the legacy same-type/self source-immunity behavior, the legacy same-type friendship-target rejection behavior and the restored legacy ranged-potion behavior for normal combat targets, while the modern non-witch Raider-heal branch remains an intentional bounded bridge and the inspected legacy magic-book branch remains explicitly deferred because `ItemMagicBook` is still a broader subtype/NBT/capability spellbook system; the bounded slices are documented in `docs/cavenic-witch-baseline-mvp.md`, `docs/cavenic-witch-natural-spawn-mvp.md`, `docs/cavenic-witch-loot-mvp.md`, `docs/cavenic-witch-damage-behavior-mvp.md`, `docs/cavenic-witch-projectile-immunity-mvp.md`, `docs/cavenic-witch-friendship-targeting-mvp.md`, `docs/cavenic-witch-ranged-potion-mvp.md` and `docs/cavenic-witch-magic-book-deferred.md`
- a first bounded Cavenic Bear Baseline MVP plus bounded natural-spawn, legacy-damage-behavior, bounded legacy hostile-targeting behavior, bounded legacy melee-attack behavior and bounded legacy panic behavior follow-ups as the sixth direct Cavenic mob foundation on top of that same cavenic line: `cavenic_bear` now exists as a `PolarBear`-based hostile foundation mob with legacy-mapped core attributes, a legacy texture on the vanilla polar bear renderer path, a baseline spawn egg, a vanilla polar bear loot baseline, bounded CAVERN-only natural spawning, the legacy fall-damage reduction and fire-damage immunity behavior, a bounded legacy player-target / hurt-by-target hostility slice, the restored legacy melee reach / standing-warning behavior and the restored legacy burning-only panic behavior, while the inspected legacy bear loot line remains explicitly absent because `EntityCavenicBear` never overrides `dropLoot(...)`, and broader anger rewrites, taming/riding behavior and other special bear behavior remain intentionally out of scope for later bounded follow-ups; the bounded slices are documented in `docs/cavenic-bear-baseline-mvp.md`, `docs/cavenic-bear-natural-spawn-mvp.md`, `docs/cavenic-bear-loot-absent-or-deferred.md`, `docs/cavenic-bear-damage-behavior-mvp.md`, `docs/cavenic-bear-hostile-targeting-mvp.md`, `docs/cavenic-bear-melee-attack-mvp.md` and `docs/cavenic-bear-panic-behavior-mvp.md`
- a direct Cavenic mob roster boundary follow-up confirmed from legacy source that there is no seventh direct `EntityCavenic*` mob after `cavenic_bear`: the direct legacy roster is exhausted by zombie, skeleton, creeper, spider, witch and bear, while the remaining related legacy entities are crazy variants, summon variants, projectiles or non-Cavenic mobs; the discovery boundary is documented in `docs/cavenic-mob-roster-discovery.md`
- a first Crazy Zombie Baseline MVP plus bounded legacy-loot, legacy-damage-behavior, legacy-knockback-on-hit, legacy boss-bar / sky-darkening and legacy particle-trail follow-ups as the first legacy crazy-variant foundation follow-up after that exhausted direct Cavenic roster: `crazy_zombie` now exists as a `Zombie`-based hostile baseline with legacy-mapped core attributes, a legacy texture on the vanilla zombie renderer path, a baseline spawn egg and a vanilla zombie loot baseline, with the inspected legacy `2000.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`, while the inherited legacy `1/8` `cavenic_orb` drop appended on top of the vanilla zombie loot baseline, the inherited legacy fall-damage reduction and fire-damage immunity behavior, the legacy roll-gated melee-knockback branch, the legacy blue progress boss bar and sky-darkening boss-event branch, and the legacy client-only portal-tinted particle trail are now restored explicitly on `CrazyZombie` without changing its vanilla `Zombie` base; broader custom-loot follow-ups remain intentionally deferred until they can be inspected and restored honestly, the inspected natural-spawn line is explicitly deferred because legacy Crazy Zombie comes from the old Cavenia-only crazy-roster provider/config branch rather than the normal `CAVERN` biome-spawn path, Crazy Creeper now has its own bounded baseline documented separately, and Crazy Spider remains a follow-up candidate; the bounded slices are documented in `docs/crazy-zombie-baseline-mvp.md`, `docs/crazy-zombie-loot-mvp.md`, `docs/crazy-zombie-damage-behavior-mvp.md`, `docs/crazy-zombie-knockback-on-hit-mvp.md`, `docs/crazy-zombie-boss-bar-mvp.md`, `docs/crazy-zombie-particle-trail-mvp.md` and `docs/crazy-zombie-natural-spawn-absent-or-deferred.md`
- a Crazy Skeleton Baseline MVP plus bounded Cavenic Bow equipment, legacy-loot, legacy-damage-behavior, legacy boss-bar and legacy particle-trail follow-ups as the second crazy-variant foundation follow-up on top of that same roster: `crazy_skeleton` now exists as a `Skeleton`-based hostile baseline with legacy-mapped core attributes, a legacy texture on the vanilla skeleton renderer path, a baseline spawn egg and a vanilla skeleton loot baseline, with the inspected legacy `2000.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`, while legacy crazy-roster natural spawning stays deferred because the source still routes Crazy Skeleton through the old Cavenia-only provider/config branch rather than the normal `CAVERN` biome-spawn path, guaranteed `Cavenic Bow` mainhand, forced `Infinity` enchantment and `1.0F` mainhand drop chance are now restored explicitly on top of that vanilla `Skeleton` base, the inherited legacy `1/5` `cavenic_orb` drop is now restored explicitly on top of that same vanilla skeleton loot baseline, the inherited legacy fall-damage reduction and fire-damage immunity behavior are now restored explicitly on top of that same vanilla skeleton baseline, the legacy white progress boss bar and sky-darkening boss-event branch are now restored explicitly on top of that same vanilla skeleton baseline, the legacy client-only portal-tinted particle trail is now restored explicitly on top of that same vanilla skeleton baseline through the shared `crazy_mob` particle mapping, the legacy `EntityAIAttackCavenicBow` path is source-confirmed but custom ranged AI identity stays intentionally deferred, Crazy Creeper now has its own bounded baseline documented separately, and Crazy Spider remains a follow-up candidate; the bounded slices are documented in `docs/crazy-skeleton-baseline-mvp.md`, `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md`, `docs/crazy-skeleton-loot-mvp.md`, `docs/crazy-skeleton-damage-behavior-mvp.md`, `docs/crazy-skeleton-boss-bar-mvp.md`, `docs/crazy-skeleton-particle-trail-mvp.md` and `docs/crazy-skeleton-ranged-ai-boundary.md`
- a Crazy Creeper Baseline MVP plus bounded legacy-loot follow-up as the third crazy-variant foundation follow-up on top of that same roster: `crazy_creeper` now exists as a `Creeper`-based hostile baseline with legacy-mapped core attributes, a legacy texture on the vanilla creeper renderer path, a baseline spawn egg and a vanilla creeper loot baseline, with the inspected legacy `1500.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`, while legacy crazy-roster natural spawning stays deferred because the source still routes Crazy Creeper through the old Cavenia-only provider/config branch rather than the normal `CAVERN` biome-spawn path, and the inherited legacy `1/5` `cavenic_orb` drop is now restored explicitly on top of that same vanilla creeper loot baseline; Crazy Creeper fuse/explosion, boss, particle and damage follow-ups remain intentionally deferred until each branch is inspected and restored honestly, and Crazy Spider remains the next crazy-variant follow-up candidate; the bounded slices are documented in `docs/crazy-creeper-baseline-mvp.md` and `docs/crazy-creeper-loot-mvp.md`
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
- The current GameTest slice covers runtime registry availability, `acresia` runtime ids/planting/harvest/shear-regrowth/worldgen-key smoke, bounded `cavenic_bow` registry/durability/repair/tag/recipe smoke plus mode-state/cycling, bounded Rapid runtime smoke, bounded Snipe runtime smoke, bounded Torch runtime smoke, `cavenic_zombie` runtime registry/attribute/spawn-egg smoke plus CAVERN-only natural-spawn placement/biome-modifier smoke and the legacy orb-drop wiring smoke, `cavenic_skeleton` runtime registry/attribute/spawn-egg smoke plus CAVERN-only natural-spawn placement/biome-modifier smoke, the legacy `1/5` `cavenic_orb` drop wiring smoke and the legacy fall-damage reduction/fire-damage immunity smoke, `cavenic_creeper` runtime registry/attribute/spawn-egg smoke plus CAVERN-only natural-spawn placement/biome-modifier smoke, the legacy `1/5` `cavenic_orb` drop wiring smoke, the vanilla creeper loot-table baseline smoke and the legacy fall-damage reduction/fire-damage immunity smoke, `cavenic_spider` runtime registry/attribute/spawn-egg smoke plus CAVERN-only natural-spawn placement/biome-modifier smoke, the vanilla spider loot baseline smoke, the legacy `1/8` `cavenic_orb` drop wiring smoke, the legacy fall-damage reduction/fire-damage immunity smoke and the bounded blindness-on-hit runtime smoke, `cavenic_witch` runtime registry/attribute/spawn-egg smoke plus CAVERN-only natural-spawn placement/biome-modifier smoke, the vanilla witch loot-table baseline smoke, the legacy `1/5` `cavenic_orb` drop wiring smoke, the legacy fall-damage reduction/fire-damage immunity smoke, the legacy same-type/self source-immunity smoke and the legacy same-type friendship-target rejection smoke, `cavenic_bear` runtime registry/attribute/spawn-egg smoke plus CAVERN-only natural-spawn placement/biome-modifier smoke, the vanilla polar bear loot-table baseline smoke, the legacy fall-damage reduction/fire-damage immunity smoke, the legacy hostile player-target / hurt-by-target retaliation smoke, the legacy melee reach / standing-warning smoke and the bounded legacy panic smoke, while the inspected legacy bear loot line remains source-confirmed as having no custom `dropLoot(...)` branch to port and the remaining passive/movement bear goals stay on the inherited modern polar bear base because the inspected legacy swimming, follow-parent, wander and look priorities already match closely enough, `crazy_zombie` runtime registry/attribute/spawn-egg smoke plus the vanilla zombie loot-table baseline smoke, the inherited legacy orb-drop event wiring smoke, the inherited legacy fall-damage reduction/fire-damage immunity smoke, the generic-damage baseline smoke, the bounded legacy knockback-on-hit smoke and the bounded legacy blue progress boss-event / sky-darkening smoke, including the modern `generic.max_health` clamp on the legacy `2000.0` max-health request, while the inspected Crazy Zombie natural-spawn line remains explicitly deferred because it is tied to the old Cavenia-only crazy-roster provider/config branch rather than the current `CAVERN` biome-spawn pattern, `aquamarine` tool runtime ids/repairability/enchantability, `aquamarine` underwater utility policy smoke, `magnite` tool runtime ids/repairability/enchantability plus brittle no-Assist smoke, `magnite` armor runtime ids/repairability/enchantability plus bounded durability/equippable smoke, `hexcite` normal and Silk Touch loot, `hexcite` tool runtime registry/mining paths, `hexcite` armor runtime ids/repairability/enchantability, `miner_orb` runtime registry/policy smoke, `ore_compass` runtime registry/scanner paths, curated `randomite` runtime drops, `fissured_stone` no-drop/effect/creative-guard/non-destructive behavior, progression policy ids/scores and special-ore worldgen key resolution.
- The same runtime smoke slice now also covers `ore_compass` stored-target state round-trip and tracking policy/runtime-id smoke, while actual client-visible needle feel still remains on the manual smoke checklist.
- The same runtime smoke slice now also covers `crazy_skeleton` runtime registry/attribute/spawn-egg smoke, the vanilla skeleton loot-table baseline, the guaranteed `Cavenic Bow` + `Infinity` equipment smoke, the inherited legacy `1/5` `cavenic_orb` drop event wiring smoke, the inherited legacy fall-damage reduction/fire-damage immunity smoke, the generic-damage baseline smoke, the bounded legacy white progress boss-event / sky-darkening smoke, the shared `crazy_mob` particle registration/provider/resource/source smoke, the explicit no-natural-spawn baseline boundary and reflection coverage that the current line still does not add `EntityAIAttackCavenicBow` follow-up overrides beyond the explicit particle, equipment, loot, damage and boss hooks.
- The current GameTest slice also covers Mining Assist runtime ids, bounded same-block vein breaks with `hexcite_pickaxe`, unlock-gated/no-unlock behavior, `fissured_stone` exclusion and target-preservation smoke.
- The first Acresia Crop & Food MVP is documented in `docs/acresia-crop-mvp.md`.
- The first Cavenic Bow Baseline MVP is documented in `docs/cavenic-bow-baseline-mvp.md`.
- The Cavenic Bow Mode State & Cycling MVP is documented in `docs/cavenic-bow-mode-state-mvp.md`.
- The Cavenic Bow Snipe Mode MVP is documented in `docs/cavenic-bow-snipe-mode-mvp.md`.
- The Cavenic Bow Rapid Mode MVP is documented in `docs/cavenic-bow-rapid-mode-mvp.md`.
- The Cavenic Bow Torch Mode MVP is documented in `docs/cavenic-bow-torch-mode-mvp.md`.
- The Cavenic Bow Release Semantics Regression MVP is documented in `docs/cavenic-bow-release-semantics-mvp.md`.
- The Cavenic Zombie Baseline MVP is documented in `docs/cavenic-zombie-baseline-mvp.md`.
- The Cavenic Zombie Natural Spawn MVP is documented in `docs/cavenic-zombie-natural-spawn-mvp.md`.
- The Cavenic Zombie Legacy Orb Drop MVP is documented in `docs/cavenic-zombie-orb-drop-mvp.md`.
- The Cavenic Zombie Legacy Damage Behavior MVP is documented in `docs/cavenic-zombie-damage-behavior-mvp.md`.
- The Cavenic Skeleton Baseline MVP is documented in `docs/cavenic-skeleton-baseline-mvp.md`.
- The Cavenic Skeleton Natural Spawn MVP is documented in `docs/cavenic-skeleton-natural-spawn-mvp.md`.
- The Cavenic Skeleton Legacy Orb Drop MVP is documented in `docs/cavenic-skeleton-orb-drop-mvp.md`.
- The Cavenic Skeleton Legacy Damage Behavior MVP is documented in `docs/cavenic-skeleton-damage-behavior-mvp.md`.
- The Cavenic Creeper Baseline MVP is documented in `docs/cavenic-creeper-baseline-mvp.md`.
- The Cavenic Creeper Natural Spawn MVP is documented in `docs/cavenic-creeper-natural-spawn-mvp.md`.
- The Cavenic Creeper Legacy Orb Drop MVP is documented in `docs/cavenic-creeper-orb-drop-mvp.md`.
- The Cavenic Creeper Legacy Damage Behavior MVP is documented in `docs/cavenic-creeper-damage-behavior-mvp.md`.
- The Cavenic Creeper Legacy Fuse/Explosion MVP is documented in `docs/cavenic-creeper-fuse-explosion-mvp.md`.
- The Cavenic Spider Baseline MVP is documented in `docs/cavenic-spider-baseline-mvp.md`.
- The Cavenic Spider Natural Spawn MVP is documented in `docs/cavenic-spider-natural-spawn-mvp.md`.
- The Cavenic Spider Legacy Orb Drop MVP is documented in `docs/cavenic-spider-orb-drop-mvp.md`.
- The Cavenic Spider Legacy Damage Behavior MVP is documented in `docs/cavenic-spider-damage-behavior-mvp.md`.
- The Cavenic Spider Blindness-On-Hit MVP is documented in `docs/cavenic-spider-blindness-on-hit-mvp.md`.
- The Cavenic Witch Baseline MVP is documented in `docs/cavenic-witch-baseline-mvp.md`.
- The Cavenic Witch Natural Spawn MVP is documented in `docs/cavenic-witch-natural-spawn-mvp.md`.
- The Cavenic Witch Legacy Loot MVP is documented in `docs/cavenic-witch-loot-mvp.md`.
- The Cavenic Witch Legacy Damage Behavior MVP is documented in `docs/cavenic-witch-damage-behavior-mvp.md`.
- The Cavenic Witch Same-Type Projectile Immunity MVP is documented in `docs/cavenic-witch-projectile-immunity-mvp.md`.
- The Cavenic Witch Friendship Targeting MVP is documented in `docs/cavenic-witch-friendship-targeting-mvp.md`.
- The Cavenic Witch Custom Ranged Potion MVP is documented in `docs/cavenic-witch-ranged-potion-mvp.md`.
- The deferred Cavenic Witch magic-book branch is documented in `docs/cavenic-witch-magic-book-deferred.md`.
- The Cavenic Bear Baseline MVP is documented in `docs/cavenic-bear-baseline-mvp.md`.
- The Cavenic Bear Natural Spawn MVP is documented in `docs/cavenic-bear-natural-spawn-mvp.md`.
- The Cavenic Bear Loot Boundary is documented in `docs/cavenic-bear-loot-absent-or-deferred.md`.
- The Cavenic Bear Legacy Damage Behavior MVP is documented in `docs/cavenic-bear-damage-behavior-mvp.md`.
- The Cavenic Bear Legacy Hostile Targeting MVP is documented in `docs/cavenic-bear-hostile-targeting-mvp.md`.
- The Cavenic Bear Legacy Melee Attack MVP is documented in `docs/cavenic-bear-melee-attack-mvp.md`.
- The Cavenic Bear Legacy Panic Behavior MVP is documented in `docs/cavenic-bear-panic-behavior-mvp.md`.
- The Cavenic Bear Passive / Movement AI Boundary MVP is documented in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`.
- The direct Cavenic mob roster boundary is documented in `docs/cavenic-mob-roster-discovery.md`.
- The Crazy Zombie Baseline MVP is documented in `docs/crazy-zombie-baseline-mvp.md`.
- The Crazy Skeleton Baseline MVP is documented in `docs/crazy-skeleton-baseline-mvp.md`.
- The Crazy Creeper Baseline MVP is documented in `docs/crazy-creeper-baseline-mvp.md`.
- The Crazy Creeper Legacy Loot / Orb Drop MVP is documented in `docs/crazy-creeper-loot-mvp.md`.
- The Crazy Skeleton Cavenic Bow Equipment MVP is documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md`.
- The Crazy Skeleton Legacy Loot / Orb Drop MVP is documented in `docs/crazy-skeleton-loot-mvp.md`.
- The Crazy Skeleton Legacy Damage Behavior MVP is documented in `docs/crazy-skeleton-damage-behavior-mvp.md`.
- The Crazy Skeleton Boss Bar / Sky-Darkening MVP is documented in `docs/crazy-skeleton-boss-bar-mvp.md`.
- The Crazy Skeleton Particle Trail MVP is documented in `docs/crazy-skeleton-particle-trail-mvp.md`.
- The Crazy Skeleton Ranged-AI Boundary is documented in `docs/crazy-skeleton-ranged-ai-boundary.md`.
- The Crazy Zombie Legacy Loot / Orb Drop Boundary MVP is documented in `docs/crazy-zombie-loot-mvp.md`.
- The Crazy Zombie Legacy Damage Behavior MVP is documented in `docs/crazy-zombie-damage-behavior-mvp.md`.
- The Crazy Zombie Knockback-On-Hit MVP is documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`.
- The Crazy Zombie Boss Bar / Sky-Darkening MVP is documented in `docs/crazy-zombie-boss-bar-mvp.md`.
- The Crazy Zombie Natural Spawn Boundary is documented in `docs/crazy-zombie-natural-spawn-absent-or-deferred.md`.
- The first Ore Compass MVP is documented in `docs/ore-compass-mvp.md`.
- The first Hexcite Armor MVP is documented in `docs/hexcite-armor-mvp.md`.
- The first Aquamarine Utility Tools MVP is documented in `docs/aquamarine-tool-mvp.md`.
- The first Magnite Tool Set MVP is documented in `docs/magnite-tool-mvp.md`.
- The first Magnite Armor MVP is documented in `docs/magnite-armor-mvp.md`.
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

Copied or derived legacy texture assets used for `hexcite`, `randomite`, `fissured_stone`, `miner_orb`, `ore_compass`, `hexcite` armor, `aquamarine` tools, `magnite` tools, the current `magnite` armor placeholder slice, `acresia` crop/item textures, the first `cavenic_shroom` / `cavenic_orb` slice and the baseline `cavenic_bow` slice are documented for provenance in `docs/cavern-special-ore-content-parity.md`, `docs/miner-orb-mvp.md`, `docs/ore-compass-mvp.md`, `docs/hexcite-armor-mvp.md`, `docs/aquamarine-tool-mvp.md`, `docs/magnite-tool-mvp.md`, `docs/magnite-armor-mvp.md`, `docs/acresia-crop-mvp.md`, `docs/cavenic-shroom-mvp.md`, `docs/cavenic-bow-baseline-mvp.md` and `docs/cavenic-bow-mode-state-mvp.md`. The original legacy code baseline was GPLv3, and the original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.
