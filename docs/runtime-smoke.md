# Runtime Smoke

This document defines the automated runtime smoke layer for the completed `CAVERN` special ore/content parity tranche 2 and the follow-up `hexcite` tool-set MVP.

It is intentionally a NeoForge GameTest server pass, not a visual client smoke pass.

## What It Covers

NeoForge GameTest runtime smoke covers:

- runtime registry availability for tranche 2 content
- hexcite tool runtime registry ids
- hexcite normal and Silk Touch loot paths
- hexcite pickaxe normal mining path for special ores
- hexcite pickaxe Silk Touch path for hexcite ore
- randomite curated runtime loot output
- fissured stone no-drop behavior
- fissured stone survival effect behavior
- fissured stone creative guard
- fissured stone non-destructive behavior
- progression policy ids/scores
- worldgen configured/placed feature runtime resolution

## What It Does Not Cover

- visual portal UX
- block, model and texture appearance in the client
- particle and sound feel
- full player portal loop through a real client session
- restart persistence with a real dedicated-server save

The GameTest layer is meant to fail fast on runtime wiring regressions. It does not replace manual client smoke.

## Local Commands

Run the narrow runtime compile first:

```bash
./gradlew --no-daemon :app-neoforge:compileJava
```

Run the NeoForge GameTest server:

```bash
./gradlew --no-daemon :app-neoforge:runGameTestServer
```

Run the existing JUnit/resource suite and then the full build:

```bash
./gradlew --no-daemon test
./gradlew --no-daemon build
```

For a single local wrapper that runs the same sequence, use:

```bash
scripts/runtime-smoke.sh
```

## Docker Commands

The repository already uses Docker for repeatable Gradle execution. The equivalent commands are:

```bash
docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:compileJava
docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:runGameTestServer
docker compose run --rm gradle ./gradlew --no-daemon test
docker compose run --rm gradle ./gradlew --no-daemon build
```

## Expected Pass/Fail Behavior

- `:app-neoforge:runGameTestServer` must fail if a required special-ore GameTest fails.
- The GameTest server run is the automated runtime layer for tranche 2 special ores and progression/worldgen wiring.
- The run is intentionally small and server-only; it should not require a GUI client or a human player.

## Template Strategy

- The current GameTests use a checked-in minimal `minecraft:empty` template.
- The template source lives at `app-neoforge/src/gameteststructures/empty.snbt`.
- `app-neoforge/build.gradle` stages that local `.snbt` into the GameTest run directory because the current GameTest server setup does not reliably provide a built-in empty template for this project.
- Under the current 1.21.1 GameTest local-structure lookup, that staged file resolves as `minecraft:empty`, so the namespace is intentionally explicit in the test annotations.
- Test setup happens programmatically through `GameTestHelper`.
- No large structure template or generated world is required for this runtime-smoke pass.

## Manual Follow-Up Still Needed

Even when the runtime-smoke pass is green, manual client smoke is still needed for:

- portal UX
- rendering and block/model appearance
- particle and sound feel
- end-to-end player movement through the portal loop
- restart persistence on a real server save
