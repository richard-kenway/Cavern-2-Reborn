# Cavern Reborn

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
- a bounded `contained_caves` noise-settings fork of vanilla `minecraft:caves`, used to reduce oversized cavity formation without changing the overall cave-first direction of the baseline
- a first weighted tunnel-network layer on top of `contained_caves`, used to improve underground connectedness without reopening the baseline too aggressively
- a first weighted ravine-like layer on top of the tunnel baseline, now rebuilt as a band-limited horizontal connector pass instead of raw entrance carving
- basic portal UX feedback for `cooldown`, failed cavern entry and missing return-state denial cases, plus an overworld fallback return target when no saved return-state exists
- a legacy-like `CAVERN` ritual activation step: any frame block in the `cavernreborn:cavern_portal_frames` block tag can now be activated with any item in the `cavernreborn:cavern_portal_activators` item tag via a server-side frame-click hook and fill the `cavern_portal` interior block
- a first entity-inside portal flow step: walking into the `cavern_portal` interior block now triggers the main transfer loop; right-click interaction is disabled so runtime usage is now collision-only
- frame-level portal identity semantics: interaction now canonicalizes a touched interior block to the portal frame anchor before building keys, cooldowns and portal placements
- legacy-like portal collision eligibility policy is now applied at block level before travel dispatch; dead/crouching/spectator/passenger/vehicle/projectile/cooldown filters are explicit and test-covered, non-portal-capable entities are now rejected up front, and bounded non-player transport now uses an entity-persisted entry receipt instead of the player return-state store
- an axis-aware `cavern_portal` interior plane with thin portal geometry and frame-integrity invalidation when a tagged portal frame block is broken
- a first destination portal placement step: travel now uses a configurable legacy-like find-or-create portal target in the destination dimension instead of requiring a second portal to be placed manually every time
- a persistent control-plane state backend: player return-state and world portal indices now survive server restarts through overworld-level NeoForge `SavedData`
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

No full `CAVERN` worldgen or broader gameplay systems are implemented yet.

## Current Limitations

- `CAVERN` still uses a first baseline cave profile rather than full legacy-parity worldgen.
- The current `contained_caves` preset is intentionally a narrow fork of vanilla `minecraft:caves`, not a new long-term worldgen direction.
- The current containment pass now also shifts only a small pair of `final_density` bias values; this is still a bounded tuning step and not a worldgen redesign.
- The current dry-out pass keeps `sea_level` pinned to `min_y`, so the normal `CAVERN` baseline no longer has an operative flood-line inside its playable volume.
- Collision handling now applies a legacy-like eligibility filter matrix in the portal block, and a bounded non-player transport path now exists for eligible entities, but it still is not full legacy parity for bosses, broader entity classes or richer cache semantics.
- The new data-driven cave-biome family still needs manual in-game validation on a real generated world after the move away from the fixed biome stub.
- Large open cavities may still appear in the current baseline; custom cave-like dimension effects now handle sky/sun leakage, but the overall visual result still needs manual in-game validation.
- The tunnel-network layer is now denser again after repeated manual playtesting showed that players still had to dig too often; this remains a bounded tuning step rather than a new carve system.
- The ravine-like layer no longer uses raw entrance carving; it now relies on a band-limited `spaghetti_2d` connector field to bias the pass toward longer horizontal links instead of narrow vertical shafts.
- The current cave-biome family is intentionally minimal and does not yet cover ore veins, structures or a broader biome set.
- Safe arrival currently relies on a bounded local search around the target column and may cancel entry if no safe point is found nearby.
- Return-state and world portal indices now persist through an overworld-level `SavedData` control plane, but this is still a bounded MVP backend rather than full player/world attachment wiring.
- The new persistent backend still needs manual restart validation on a real dedicated server, especially for `portal -> CAVERN -> restart -> return` and indexed destination-portal reuse after restart.
- The current portal flow now supports an axis-aware thin interior portal plane with frame-integrity invalidation; full legacy collision semantics still need manual validation.
- Portal interaction now canonicalizes touched interior blocks to a frame-level anchor, but this still needs manual validation across different interior blocks of the same portal.
- Destination portal placement is now automatic in a bounded search-relink-regenerate-or-create form, but it still does not implement full legacy cache, wider radius search and broader regeneration semantics.
- `CAVERN` now validates against the `cavernreborn:cavern_portal_frames` block tag and can auto-create/regenerate frames with a configurable frame block from `config/cavernreborn-portal.properties`.
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
- Legacy portal branches such as `portalMenu`, shop flow and rank gating are intentionally not part of the current MVP slice.

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
- Default frame validation and activation are data-driven through the `cavernreborn:cavern_portal_frames` and `cavernreborn:cavern_portal_activators` tags.
- The regression-protected portal baseline, supported cases and required runtime smoke-checks are documented in `docs/portal-baseline.md`.
- The checked-in defaults restore the old `findPortalRange = 32` behavior more closely than the previous hardcoded `8/6` and `4/2` windows.

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
- The generated `runs/` directories are ignored by git, so resets and local test worlds do not dirty the repository.
- If `25585` is busy too, set a different host port with `DEV_SERVER_PORT=<port> scripts/dev-server/up.sh`.
