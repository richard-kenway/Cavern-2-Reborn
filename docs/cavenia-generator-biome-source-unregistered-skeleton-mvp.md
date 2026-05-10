# Cavenia Chunk Generator / Biome Source Unregistered Skeleton MVP

This note documents the first explicit app-side unregistered skeleton shape for the future Cavenia generator and biome-selection path.

It builds on:

- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

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

The later final inert readiness-matrix note is now documented separately in:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`

## What This MVP Adds

Current Reborn now has two app-side unregistered skeleton classes:

- `CaveniaGeneratorSkeleton`
- `CaveniaBiomeSelectionSkeleton`

The names stay explicit on purpose:

- `CaveniaGeneratorSkeleton`, not `CaveniaChunkGenerator`
- `CaveniaBiomeSelectionSkeleton`, not `CaveniaBiomeSource`

These types consume the accepted inert app-side bridge and activation boundary plus the accepted pure `core` scaffold and policies:

- `CaveniaGeneratorBridge`
- `CaveniaGeneratorRegistrationBoundary`
- `CaveniaGeneratorScaffold`
- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`
- `CaveniaSpawnProviderPolicy`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Exact Generator Skeleton Surface

`CaveniaGeneratorSkeleton` now exposes:

- `dimensionId()` -> `CaveniaGeneratorBridge.dimensionId()`
- `location()` -> `CaveniaGeneratorBridge.location()`
- `levelKey()` -> `CaveniaGeneratorBridge.levelKey()`
- `worldHeight()` -> `CaveniaGeneratorBridge.worldHeight()`
- `scaffoldStages()` -> `CaveniaGeneratorBridge.scaffoldStages()`
- `terrainStages()` -> `CaveniaGeneratorBridge.terrainStages()`
- `biomeSelection()` -> `CaveniaBiomeSelectionSkeleton.create()`
- `canCreateChunks()` -> `false`
- `canMutatePrimer()` -> `false`
- `runtimeGeneratorRegistered()` -> `false`
- `codecRegistered()` -> `false`
- `generatorRegistryEntryRegistered()` -> `false`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

That means app-side code can now see future generator responsibilities directly without pretending chunk creation or primer mutation already exists.

## Exact Biome-Selection Skeleton Surface

`CaveniaBiomeSelectionSkeleton` now exposes:

- `create()` -> singleton inert biome-selection skeleton
- `entryCount()` -> `14`
- `totalWeight()` -> `675`
- `legacyBiomeNames()` -> immutable shipped legacy biome names from `CaveniaBiomeTopFilterPolicy`
- `hasLegacyBiome(String)` -> legacy-name presence check
- `topBlockForLegacyBiomeName(String)` -> optional top-block lookup from `CaveniaBiomeTopFilterPolicy`
- `isRuntimeBiomeSource()` -> `false`
- `codecRegistered()` -> `false`
- `requiresBiomeSourceStrategyBeforeActivation()` -> `true`

That makes the future biome-selection input shape visible from app-side code without creating a runtime biome source, biome holders or registry lookups.

## Why This Is Still Not Runtime Worldgen

This unregistered skeleton is not a `ChunkGenerator`.

This unregistered skeleton is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- create chunks
- mutate chunks or primers
- create biome holders
- create a runtime biome source
- register a generator type
- register a codec
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

So both skeletons still report runtime functionality as absent.

## How This Narrows Future Active Generator Work

This slice reduces future app-side ambiguity because:

- generator responsibilities now have an app-side skeleton
- biome-selection responsibilities now have an app-side skeleton
- app-side code can now read accepted identity, scaffold order and biome-selection facts directly
- both skeletons still report runtime functionality as absent
- active implementation still requires a real generator class, a real biome source strategy, codec and registration, dimension resources, safe access and Cavenia-only spawn-host decisions

Until those runtime pieces exist, this slice remains an unregistered skeleton only.
