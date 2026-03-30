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

No full `CAVERN` worldgen or broader gameplay systems are implemented yet.

## Current Limitations

- `CAVERN` still uses a first baseline cave profile rather than full legacy-parity worldgen.
- The current `contained_caves` preset is intentionally a narrow fork of vanilla `minecraft:caves`, not a new long-term worldgen direction.
- The current containment pass now also shifts only a small pair of `final_density` bias values; this is still a bounded tuning step and not a worldgen redesign.
- The current dry-out pass keeps `sea_level` pinned to `min_y`, so the normal `CAVERN` baseline no longer has an operative flood-line inside its playable volume.
- The new data-driven cave-biome family still needs manual in-game validation on a real generated world after the move away from the fixed biome stub.
- Large open cavities may still appear in the current baseline; custom cave-like dimension effects now handle sky/sun leakage, but the overall visual result still needs manual in-game validation.
- The tunnel-network layer is now denser again after repeated manual playtesting showed that players still had to dig too often; this remains a bounded tuning step rather than a new carve system.
- The ravine-like layer no longer uses raw entrance carving; it now relies on a band-limited `spaghetti_2d` connector field to bias the pass toward longer horizontal links instead of narrow vertical shafts.
- The current cave-biome family is intentionally minimal and does not yet cover ore veins, structures or a broader biome set.
- Safe arrival currently relies on a bounded local search around the target column and may cancel entry if no safe point is found nearby.
- Return-state is still stored in-memory in the current MVP slice, so a dedicated-server restart can lose the exact return target; when that happens, portal return now falls back to the shared overworld spawn instead of trapping the player in `CAVERN`.
- Portal denial feedback currently uses short overlay messages only; there is no broader notification policy yet.
- Cooldown and feedback suppression windows are fixed tick-based values and may need tuning after manual playtesting.
- Legacy portal branches such as `portalMenu`, shop flow and rank gating are intentionally not part of the current MVP slice.

## Structure

- `app-neoforge` - thin NeoForge bootstrap layer.
- `core` - future gameplay/domain layer.
- `data` - future data and schema layer.

## Design Notes

- Single target now, portable later.
- `CAVERN` is the MVP baseline.
- `HUGE_CAVERN`, `AQUA_CAVERN` and all mirage worlds are intentionally out of MVP scope.

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
