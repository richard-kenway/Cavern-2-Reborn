# Cavenia Generator Runtime Contract Interfaces / Non-Registered MVP

This note documents a non-registered runtime-contract layer for future Cavenia generator operations.

It builds on:

- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

## What This MVP Adds

Current Reborn now has three app-side non-registered contract types:

- `CaveniaGeneratorRuntimeOperation`
- `CaveniaGeneratorRuntimeOperationContract`
- `CaveniaGeneratorRuntimeContracts`

They make the future generator operation boundaries explicit without creating a runtime generator.

They consume or reference:

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

## Exact Operation Order

`CaveniaGeneratorRuntimeOperation` now exposes this exact future operation order:

1. `IDENTITY`
2. `BASE_TERRAIN_FILL`
3. `CAVE_CARVING`
4. `BIOME_TOP_FILTER_REPLACEMENT`
5. `VEINS_MUTATION`
6. `FINAL_CHUNK_CONSTRUCTION`
7. `POPULATION_INTEGRATION`
8. `SPAWN_PROVIDER_INTEGRATION_DEFERRED`
9. `ENTRY_ACCESS_DEFERRED`

That order aligns directly with the accepted `CaveniaGeneratorScaffoldStage` order.

## Exact Operation-To-Stage Mapping

`CaveniaGeneratorRuntimeOperationContract` now maps each operation to one accepted scaffold stage:

- `IDENTITY` -> `CaveniaGeneratorScaffoldStage.IDENTITY`
- `BASE_TERRAIN_FILL` -> `CaveniaGeneratorScaffoldStage.BASE_TERRAIN`
- `CAVE_CARVING` -> `CaveniaGeneratorScaffoldStage.CAVE_CARVING`
- `BIOME_TOP_FILTER_REPLACEMENT` -> `CaveniaGeneratorScaffoldStage.BIOME_TOP_FILTER_REPLACEMENT`
- `VEINS_MUTATION` -> `CaveniaGeneratorScaffoldStage.VEINS_MUTATION`
- `FINAL_CHUNK_CONSTRUCTION` -> `CaveniaGeneratorScaffoldStage.FINAL_CHUNK_CONSTRUCTION`
- `POPULATION_INTEGRATION` -> `CaveniaGeneratorScaffoldStage.POPULATION_INTEGRATION`
- `SPAWN_PROVIDER_INTEGRATION_DEFERRED` -> `CaveniaGeneratorScaffoldStage.SPAWN_PROVIDER_INTEGRATION_DEFERRED`
- `ENTRY_ACCESS_DEFERRED` -> `CaveniaGeneratorScaffoldStage.ENTRY_ACCESS_DEFERRED`

## Exact Source-Policy Names

Each contract now keeps an explicit source-policy name:

- `IDENTITY` -> `CavernDimensions / CavernNeoForgeDimensions`
- `BASE_TERRAIN_FILL` -> `CaveniaTerrainGeneratorPolicy`
- `CAVE_CARVING` -> `CaveniaCaveCarverPolicy`
- `BIOME_TOP_FILTER_REPLACEMENT` -> `CaveniaBiomeTopFilterPolicy`
- `VEINS_MUTATION` -> `CaveniaVeinsContentPolicy`
- `FINAL_CHUNK_CONSTRUCTION` -> `CaveniaGeneratorScaffold`
- `POPULATION_INTEGRATION` -> `CaveniaPopulationPolicy`
- `SPAWN_PROVIDER_INTEGRATION_DEFERRED` -> `CaveniaSpawnProviderPolicy`
- `ENTRY_ACCESS_DEFERRED` -> `CaveniaGeneratorRegistrationBoundary / CaveniaGeneratorBridge`

## Exact Blocked Runtime Surface

Every `CaveniaGeneratorRuntimeOperationContract` stays inactive:

- `runtimeImplemented = false`
- `canCreateChunks = false`
- `canMutatePrimer = false`
- `requiresRegisteredGenerator = true`
- `requiresDimensionResources = true`

`CaveniaGeneratorRuntimeContracts` now exposes deterministic helpers:

- `contracts()` -> immutable operation-contract list
- `operations()` -> immutable operation list
- `contractFor(CaveniaGeneratorRuntimeOperation)` -> inert lookup
- `allRuntimeOperationsBlocked()` -> `true`
- `anyOperationCanCreateChunks()` -> `false`
- `anyOperationCanMutatePrimer()` -> `false`
- `allRequireActivationBeforeRuntime()` -> `true`
- `matchesScaffoldStageOrder()` -> `true`
- `activationRequirements()` -> the same immutable requirements from `CaveniaGeneratorRegistrationBoundary`
- `activationBlockedReason()` -> the same inert blocker text from `CaveniaGeneratorRegistrationBoundary`
- `generatorSkeletonCanCreateChunks()` -> `false`
- `biomeSelectionIsRuntimeBiomeSource()` -> `false`

So the future runtime operation boundaries are now explicit while runtime implementation remains blocked.

## Why This Is Still Not Runtime Worldgen

This non-registered runtime-contract layer is not a `ChunkGenerator`.

This non-registered runtime-contract layer is not a `BiomeSource`.

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

So all runtime operations stay blocked until the activation requirements from `CaveniaGeneratorRegistrationBoundary` are satisfied.

## How This Narrows Future Active Generator Work

This slice reduces future ambiguity because:

- future generator operation boundaries are now explicit
- source policies for each operation are now explicit
- app-side code can now read the operation-to-stage mapping deterministically
- app-side code can now prove that no operation can create chunks
- app-side code can now prove that no operation can mutate primers
- runtime implementation remains blocked until activation requirements are satisfied

Until real generator, biome-source, codec, registration, dimension-resource, safe-access and Cavenia-only spawn-host work exists, this slice remains a non-registered runtime-contract layer only.
