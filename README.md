# Cavern Reborn

Bootstrap for the modern rewrite of Cavern on `Minecraft 1.21.1 + NeoForge`.

## Current Status

This repository currently contains the project skeleton and a minimal content registration slice:

- `app-neoforge` for the NeoForge entrypoint and runtime wiring
- `core` for loader-agnostic gameplay rules and domain models
- `data` for serialization, schema and persistence-oriented types
- a bootstrap block, `BlockItem` and creative tab used to validate registry wiring
- a return-state, portal wiring and `CAVERN` travel skeleton for the first `CAVERN` architecture phase

No worldgen, player-facing dimension content or gameplay systems are implemented yet.

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
