# Cavenia Generator Activation-Readiness Runtime Host Contracts MVP

This note documents a non-registered activation-readiness host contract layer for future Cavenia runtime activation.

It builds on:

- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later generator-host / biome-source-strategy split-contract note is now documented separately in:

- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`

The later dimension-resource / access-travel / spawn-host split-contract note is now documented separately in:

- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`

The later worldgen-resource split-contract note is now documented separately in:

- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`

The later final inert readiness-matrix note is now documented separately in:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`

The later decision-only first-active-surface selection note is now documented separately in:

- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`

## What This MVP Adds

Current Reborn now has three app-side non-registered activation-readiness host contract types:

- `CaveniaActivationReadinessHost`
- `CaveniaActivationReadinessHostContract`
- `CaveniaActivationReadinessHosts`

They make the still-missing runtime host responsibilities explicit without adding runtime activation.

They consume or reference:

- `CaveniaGeneratorRuntimeContracts`
- `CaveniaGeneratorSkeleton`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaGeneratorRegistrationBoundary`
- `CaveniaGeneratorBridge`
- `CaveniaGeneratorScaffold`
- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`
- `CaveniaSpawnProviderPolicy`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Exact Host Enum Values

`CaveniaActivationReadinessHost` now exposes this exact host order:

1. `GENERATOR_HOST`
2. `BIOME_SOURCE_STRATEGY_HOST`
3. `DIMENSION_RESOURCE_HOST`
4. `ACCESS_TRAVEL_HOST`
5. `SPAWN_HOST`
6. `WORLDGEN_RESOURCE_HOST`

## Exact Host-To-Activation-Requirement Mapping

Each host now maps directly to one accepted `CaveniaGeneratorActivationRequirement`:

- `GENERATOR_HOST` -> `CHUNK_GENERATOR_IMPLEMENTATION`
- `BIOME_SOURCE_STRATEGY_HOST` -> `BIOME_SOURCE_STRATEGY`
- `DIMENSION_RESOURCE_HOST` -> `DIMENSION_JSON`
- `ACCESS_TRAVEL_HOST` -> `SAFE_ACCESS_OR_TELEPORT`
- `SPAWN_HOST` -> `CAVENIA_ONLY_SPAWN_HOST`
- `WORLDGEN_RESOURCE_HOST` -> `WORLDGEN_RESOURCE_MAPPING`

The dimension-resource host keeps `dimension type JSON` explicit in its blocker text even though the primary requirement field is `DIMENSION_JSON`.

## Exact Host Contract Fields

`CaveniaActivationReadinessHostContract` now exposes:

- `host`
- `responsibility`
- `activationRequirement`
- `ready`
- `runtimeImplemented`
- `canActivateCavenia`
- `blocker`

Every host stays blocked:

- `ready = false`
- `runtimeImplemented = false`
- `canActivateCavenia = false`

## Exact Helper Surface

`CaveniaActivationReadinessHosts` now exposes deterministic helpers:

- `contracts()` -> immutable host-contract list
- `hosts()` -> immutable host list
- `contractFor(CaveniaActivationReadinessHost)` -> inert lookup
- `allHostsBlocked()` -> `true`
- `anyHostReady()` -> `false`
- `anyHostRuntimeImplemented()` -> `false`
- `canActivateCavenia()` -> `false`
- `generatorHostReady()` -> `false`
- `biomeSourceStrategyHostReady()` -> `false`
- `dimensionResourceHostReady()` -> `false`
- `accessTravelHostReady()` -> `false`
- `spawnHostReady()` -> `false`
- `worldgenResourceHostReady()` -> `false`
- `activationRequirements()` -> the same immutable requirements from `CaveniaGeneratorRegistrationBoundary`
- `activationBlockedReason()` -> the same inert blocker text from `CaveniaGeneratorRegistrationBoundary`
- `runtimeOperationsBlocked()` -> `true`
- `generatorSkeletonCanCreateChunks()` -> `false`
- `biomeSelectionIsRuntimeBiomeSource()` -> `false`

So the missing runtime host responsibilities are now explicit while all hosts remain blocked and not ready.

## Why This Is Still Not Runtime Worldgen

This non-registered activation-readiness host contract layer is not a `ChunkGenerator`.

This non-registered activation-readiness host contract layer is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- generate chunks
- mutate world or chunk state
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

So no host can activate Cavenia.

## How This Narrows Future Active Generator Work

This slice reduces future activation ambiguity because:

- generator host is now explicit
- biome-source strategy host is now explicit
- dimension-resource host is now explicit
- access/travel host is now explicit
- Cavenia-only spawn host is now explicit
- worldgen-resource host is now explicit
- all remain blocked until activation requirements are satisfied

Until real generator, biome-source, dimension-resource, access/travel and spawn-host work exists, this slice remains a non-registered activation-readiness host contract layer only.
