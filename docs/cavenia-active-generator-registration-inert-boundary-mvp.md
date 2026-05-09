# Cavenia Active Generator Registration / Inert Activation Boundary MVP

This note documents an inert registration boundary around the future Cavenia custom-generator path.

It builds on:

- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later unregistered generator/biome-selection skeleton note is now documented separately in:

- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`

The later non-registered runtime-contract note is now documented separately in:

- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`

## What This MVP Adds

Current Reborn now has an inert NeoForge-side registration-boundary class:

- `CaveniaGeneratorRegistrationBoundary`
- `CaveniaGeneratorActivationRequirement`

This inert boundary consumes `CaveniaGeneratorBridge`, which still consumes `CaveniaGeneratorScaffold` plus the accepted pure policy inputs:

- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`
- `CaveniaSpawnProviderPolicy`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Exact Status Surface Added

`CaveniaGeneratorRegistrationBoundary` now exposes:

- `dimensionId()` -> `CaveniaGeneratorBridge.dimensionId()`
- `location()` -> `CaveniaGeneratorBridge.location()`
- `levelKey()` -> `CaveniaGeneratorBridge.levelKey()`
- `bridgeRuntimeGeneratorRegistered()` -> `false`
- `codecRegistered()` -> `false`
- `generatorRegistryEntryRegistered()` -> `false`
- `chunkGeneratorClassPresent()` -> `false`
- `biomeSourceClassPresent()` -> `false`
- `dimensionJsonPresent()` -> `false`
- `dimensionTypeJsonPresent()` -> `false`
- `safeAccessReady()` -> `false`
- `spawnHostReady()` -> `false`
- `canActivateRuntimeGenerator()` -> `false`
- `activationRequirements()` -> immutable `CaveniaGeneratorActivationRequirement` values
- `activationBlockedReason()` -> `Cavenia generator activation stays blocked until dimension JSON, dimension type JSON, chunk generator implementation, biome source strategy, generator codec/registration, safe access, Cavenia-only spawn host and worldgen resource mapping decisions exist.`

The exact activation requirements now stay explicit and stable:

- `DIMENSION_JSON`
- `DIMENSION_TYPE_JSON`
- `CHUNK_GENERATOR_IMPLEMENTATION`
- `BIOME_SOURCE_STRATEGY`
- `GENERATOR_CODEC`
- `GENERATOR_REGISTRY_ENTRY`
- `SAFE_ACCESS_OR_TELEPORT`
- `CAVENIA_ONLY_SPAWN_HOST`
- `WORLDGEN_RESOURCE_MAPPING`

So app-side code can now report missing registration prerequisites deterministically without pretending generator activation is possible.

## Why This Is Still Not A Generator

This inert boundary is not a `ChunkGenerator`.

This inert boundary is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- register a generator type
- register a codec
- create chunks
- mutate chunks or primers
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

The registration boundary therefore still reports:

- codec registration as false
- generator registry entry registration as false
- runtime activation as blocked
- safe access as not ready
- Cavenia-only spawn host as not ready

## How This Narrows Future Active Generator Work

This inert registration boundary reduces future platform ambiguity because:

- app-side can now read Cavenia identity through the bridge
- app-side can now read accepted scaffold and policy facts through the bridge
- app-side can now report missing registration requirements explicitly
- app-side still reports codec and generator registration as false
- active generator implementation still requires dimension resources, chunk generator implementation, biome source strategy, codec/registration, safe access and spawn-host decisions

Until those runtime pieces exist, this slice remains an inert registration boundary only.
