# Runtime Smoke

This document defines the automated runtime smoke layer for the completed `CAVERN` special ore/content parity tranche 2, the bounded `Acresia Crop & Food MVP`, the bounded `Cavenic Shroom & Orb MVP`, the bounded Cavenic Melee MVP, the bounded `Cavenic Bow Baseline MVP`, the bounded `Cavenic Bow Mode State & Cycling MVP`, the bounded Aquamarine Utility Tools MVP, the bounded Magnite Tool Set MVP, the bounded `Magnite Armor MVP`, the follow-up `hexcite` tool-set MVP, the bounded `Hexcite Armor MVP`, the bounded Mining Assist slice, the first Miner's Orb MVP and the Ore Compass MVP plus tracking UX follow-up.

It is intentionally a NeoForge GameTest server pass, not a visual client smoke pass.

## What It Covers

NeoForge GameTest runtime smoke covers:

- runtime registry availability for tranche 2 content
- acresia runtime registry ids
- acresia seed planting smoke
- acresia mature normal harvest smoke
- acresia shear-harvest regrowth smoke
- acresia edible fruit wiring
- acresia worldgen configured/placed key resolution
- cavenic shroom runtime registry ids
- cavenic shroom bounded collision nausea
- cavenic shroom bounded shear-harvest helper wiring
- cavenic orb drop policy/runtime smoke
- cavenic sword and cavenic axe runtime registry ids
- cavenic sword and cavenic axe repairability with `cavernreborn:cavenic_orb`
- cavenic sword and cavenic axe cavenic item tag resolution
- cavenic sword and cavenic axe recipe manager resolution
- bounded cavenic sword cooldown-reset runtime smoke
- bounded cavenic axe hostile-nearby-target runtime smoke
- cavenic bow runtime registry id
- cavenic bow max-damage and damageable-item smoke
- cavenic bow repairability with `cavernreborn:cavenic_orb`
- cavenic bow no-repair-with-stick smoke
- cavenic bow default mode/runtime stack-state smoke
- cavenic bow mode cycling order and persistence
- cavenic bow sneak-use mode-cycling smoke
- cavenic bow enchantment applicability
- cavenic bow cavenic item tag resolution
- cavenic bow recipe manager resolution
- cavenic shroom worldgen configured/placed key resolution
- aquamarine tool runtime registry ids
- aquamarine tool repairability with `cavernreborn:aquamarine`
- aquamarine tool enchantment applicability
- aquamarine underwater utility policy/runtime smoke
- magnite tool runtime registry ids
- magnite tool repairability with `cavernreborn:magnite_ingot`
- magnite tool enchantment applicability
- magnite brittle max-damage/runtime smoke
- magnite no-unintended-Mining-Assist runtime smoke
- magnite armor runtime registry ids
- magnite armor repairability with `cavernreborn:magnite_ingot`
- magnite armor enchantment applicability
- magnite armor durability and slot/equippable runtime behavior
- hexcite tool runtime registry ids
- hexcite armor runtime registry ids
- hexcite normal and Silk Touch loot paths
- hexcite pickaxe enchantment applicability for mining and durability enchantments
- hexcite pickaxe normal mining path for special ores
- hexcite pickaxe Silk Touch path for hexcite ore
- hexcite armor repairability with `cavernreborn:hexcite`
- hexcite armor durability and slot/equippable runtime behavior
- hexcite armor enchantment applicability for the expected vanilla armor families
- miner_orb runtime registry availability
- randomite allowed-drop runtime coverage that now includes miner_orb in the curated pool
- miner_orb bonus policy/runtime smoke
- ore_compass runtime registry availability
- ore_compass target tag resolution
- ore_compass scanner nearest-target behavior
- ore_compass stored-target state round-trip
- ore_compass tracking policy/runtime ids
- unsupported/fissured/storage exclusion during ore_compass scans
- Mining Assist policy/runtime ids
- bounded same-block vein assist with hexcite_pickaxe
- no unlock means no assist
- sneaking disables assist
- fissured_stone exclusion and non-target preservation during assist
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
- actual worn hexcite armor appearance on a player or mob
- actual worn magnite armor appearance on a player or mob
- actual Acresia in-world farming feel and growth cadence
- actual Cavenic Shroom hazard feel, sparse spread feel and harvest feel
- actual Cavenic Sword and Cavenic Axe visual/client combat feel
- actual Cavenic Bow draw, release and visual client feel
- legacy Cavenic Bow custom projectile behavior and client feel beyond the current mode-state slice
- actual underwater aquamarine mining feel
- actual brittle magnite mining feel
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
- The same GameTest layer now also covers the bounded Mining Assist MVP runtime path.
- The same GameTest layer now also covers the bounded Ore Compass server-side scan path.
- The same GameTest layer now also covers the bounded Ore Compass stored-target and tracking-policy runtime path.
- The same GameTest layer now also covers the bounded Hexcite Armor MVP runtime path.
- The same GameTest layer now also covers the bounded Acresia crop planting/harvest/regrowth runtime path.
- The run is intentionally small and server-only; it should not require a GUI client or a human player.

## Registration Workaround

- `CavernSpecialOreGameTests` is currently registered from an unguarded `GameTestRegistry.register(CavernSpecialOreGameTests.class)` bootstrap call in `CavernReborn`.
- This is an infrastructure workaround for the current project setup: guarding registration with `GameTestHooks.isGametestEnabled()` prevents `:app-neoforge:runGameTestServer` from discovering any tests.
- The workaround is limited to GameTest bootstrap wiring and does not change gameplay behavior.
- If the NeoForge/GameTest discovery path becomes stable for this repository later, the unguarded registration path can be revisited.

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
- actual Acresia patch discovery and crop growth feel
- actual client-visible Ore Compass needle feel
- particle and sound feel
- end-to-end player movement through the portal loop
- restart persistence on a real server save

The Ore Compass tracking follow-up keeps this division intentionally explicit: GameTest covers state round-trip and runtime-id/policy wiring, while the actual client-visible needle feel still requires manual smoke.
