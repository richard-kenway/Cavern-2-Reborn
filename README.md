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
- basic portal UX feedback for `cooldown`, failed cavern entry and missing return-state denial cases

No full `CAVERN` worldgen or broader gameplay systems are implemented yet.

## Current Limitations

- `CAVERN` still uses a first baseline cave profile rather than full legacy-parity worldgen.
- The current `contained_caves` preset is intentionally a narrow fork of vanilla `minecraft:caves`, not a new long-term worldgen direction.
- The new data-driven cave-biome family still needs manual in-game validation on a real generated world after the move away from the fixed biome stub.
- Large open cavities may still appear in the current baseline; custom cave-like dimension effects now handle sky/sun leakage, but the overall visual result still needs manual in-game validation.
- The current cave-biome family is intentionally minimal and does not yet cover ore veins, structures or a broader biome set.
- Safe arrival currently relies on a bounded local search around the target column and may cancel entry if no safe point is found nearby.
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
