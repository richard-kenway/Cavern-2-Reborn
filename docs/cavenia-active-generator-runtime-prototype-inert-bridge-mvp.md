# Cavenia Active Generator Runtime Prototype / Inert App-Side Bridge MVP

This note documents an inert app-side bridge for the future Cavenia custom-generator path.

It builds on:

- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later inert generator-registration boundary note is now documented separately in:

- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`

The later unregistered generator/biome-selection skeleton note is now documented separately in:

- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`

The later non-registered runtime-contract note is now documented separately in:

- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`

The later activation-readiness host-contract note is now documented separately in:

- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`

The later generator-host / biome-source-strategy split-contract note is now documented separately in:

- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`

The later dimension-resource / access-travel / spawn-host split-contract note is now documented separately in:

- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`

The later worldgen-resource split-contract note is now documented separately in:

- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`

## What This MVP Adds

Current Reborn now has an inert NeoForge-side bridge class:

- `CaveniaGeneratorBridge`

This bridge reads the accepted pure `core` scaffold and policy inputs from app-side code.

It consumes:

- `CaveniaGeneratorScaffold`
- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`
- `CaveniaSpawnProviderPolicy`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Exact Bridge Surface Added

`CaveniaGeneratorBridge` now exposes:

- `location()` -> `CavernNeoForgeDimensions.CAVENIA_LOCATION`
- `levelKey()` -> `CavernNeoForgeDimensions.CAVENIA_LEVEL_KEY`
- `dimensionId()` -> `CaveniaGeneratorScaffold.dimensionId()`
- `worldHeight()` -> `CaveniaGeneratorScaffold.worldHeight()`
- `scaffoldStages()` -> `CaveniaGeneratorScaffold.stages()`
- `terrainStages()` -> `CaveniaGeneratorScaffold.terrainStages()`
- `biomeEntryCount()` -> `CaveniaGeneratorScaffold.biomeEntryCount()`
- `veinEntryCount()` -> `CaveniaGeneratorScaffold.veinEntryCount()`
- `runtimeGeneratorRegistered()` -> `false`
- `runtimeDimensionResourcesPresent()` -> `false`
- `requiresDimensionJsonBeforeActivation()` -> `true`
- `requiresDimensionTypeJsonBeforeActivation()` -> `true`
- `activationBlockedReason()` -> `Cavenia generator bridge is inert until dimension JSON, dimension type JSON, generator registration, safe access and spawn-host decisions exist.`

So app-side code can now see the future Cavenia identity, accepted world-height contract, scaffold stage order and accepted policy counts without creating an active generator path.

## Why This Is Still Not A Generator

This bridge is not a `ChunkGenerator`.

This bridge is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- create chunks
- mutate chunks or primers
- register a generator type
- create a `ServerLevel`
- create access or teleport hooks
- create spawn hooks

## Why Runtime Activation Still Stays Blocked

Current Reborn still keeps:

- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia generator
- no active Cavenia biome source
- no active Cavenia configured carver
- no active Cavenia configured or placed features
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning

The bridge reports runtime generator registration as false because the project still lacks:

- dimension JSON
- dimension type JSON
- generator codec and registration
- safe access decisions
- explicit future Cavenia-only spawn-host decisions

## How This Narrows Future Active Generator Work

This bridge reduces future platform-side ambiguity because:

- app-side code can now see Cavenia identity through `CAVENIA_LOCATION` and `CAVENIA_LEVEL_KEY`
- app-side code can now read the accepted future generator stage order
- app-side code can now read accepted terrain, biome, cave-carver, VEINS and population inputs
- app-side code still reports runtime generator registration as false
- active generator implementation still requires dimension resources, codec/registration, safe access and spawn-host decisions

Until those runtime pieces exist, this slice remains an inert app-side bridge only.
