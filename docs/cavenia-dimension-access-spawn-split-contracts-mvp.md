# Cavenia Dimension-Resource / Access-Travel / Spawn-Host Split Contracts MVP

This note documents a non-registered split-contract layer for the remaining blocked Cavenia dimension-resource, access/travel and spawn-host surfaces.

It builds on:

- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`
- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later worldgen-resource split-contract note is now documented separately in:

- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`

## What This MVP Adds

Current Reborn now has three narrower app-side split-contract surfaces:

- `CaveniaDimensionResourceRequirement`
- `CaveniaDimensionResourceRequirementContract`
- `CaveniaDimensionResourceContracts`
- `CaveniaAccessTravelRequirement`
- `CaveniaAccessTravelRequirementContract`
- `CaveniaAccessTravelContracts`
- `CaveniaSpawnHostRequirement`
- `CaveniaSpawnHostRequirementContract`
- `CaveniaSpawnHostContracts`

They consume or reference:

- `CaveniaActivationReadinessHosts`
- `CaveniaGeneratorRegistrationBoundary`
- `CaveniaGeneratorHostContracts`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaGeneratorRuntimeContracts`
- `CaveniaGeneratorBridge`
- `CaveniaSpawnProviderPolicy`
- `CaveniaSpawnEntry`
- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`

This is a non-registered split-contract layer, not runtime activation.

## Exact Dimension-Resource Requirement Enum Values

`CaveniaDimensionResourceRequirement` now exposes this exact order:

1. `DIMENSION_JSON_SHAPE`
2. `DIMENSION_TYPE_JSON_SHAPE`
3. `HEIGHT_AND_LOGICAL_HEIGHT`
4. `GENERATOR_BINDING`
5. `BIOME_SOURCE_BINDING`
6. `RESOURCE_ACTIVATION_GUARD`

## Exact Dimension-Resource Contract Fields

`CaveniaDimensionResourceRequirementContract` now exposes:

- `requirement`
- `activationRequirement`
- `sourcePolicyName`
- `ready`
- `resourcePresent`
- `runtimeBound`
- `canCreateLevel`
- `blocker`

The exact dimension-resource requirement-to-activation mapping is:

- `DIMENSION_JSON_SHAPE` -> `DIMENSION_JSON`
- `DIMENSION_TYPE_JSON_SHAPE` -> `DIMENSION_TYPE_JSON`
- `HEIGHT_AND_LOGICAL_HEIGHT` -> `DIMENSION_TYPE_JSON`
- `GENERATOR_BINDING` -> `GENERATOR_REGISTRY_ENTRY`
- `BIOME_SOURCE_BINDING` -> `BIOME_SOURCE_STRATEGY`
- `RESOURCE_ACTIVATION_GUARD` -> `WORLDGEN_RESOURCE_MAPPING`

The exact dimension-resource source-policy names are:

- `DIMENSION_JSON_SHAPE` -> `CavernNeoForgeDimensions`
- `DIMENSION_TYPE_JSON_SHAPE` -> `CaveniaGeneratorRegistrationBoundary`
- `HEIGHT_AND_LOGICAL_HEIGHT` -> `CaveniaTerrainGeneratorPolicy`
- `GENERATOR_BINDING` -> `CaveniaGeneratorHostContracts`
- `BIOME_SOURCE_BINDING` -> `CaveniaBiomeSourceStrategyContracts`
- `RESOURCE_ACTIVATION_GUARD` -> `CaveniaActivationReadinessHosts`

Every dimension-resource requirement stays blocked and inert:

- `ready = false`
- `resourcePresent = false`
- `runtimeBound = false`
- `canCreateLevel = false`

`CaveniaDimensionResourceContracts` now exposes deterministic helpers:

- `contracts()` -> immutable requirement-contract list
- `requirements()` -> immutable requirement list
- `contractFor(CaveniaDimensionResourceRequirement)` -> inert lookup
- `allRequirementsBlocked()` -> `true`
- `anyRequirementReady()` -> `false`
- `anyResourcePresent()` -> `false`
- `anyRuntimeBound()` -> `false`
- `canCreateLevel()` -> `false`
- `dimensionJsonPresent()` -> `false`
- `dimensionTypeJsonPresent()` -> `false`
- `dimensionResourceHostReady()` -> `false`
- `generatorHostReady()` -> `false`
- `biomeSourceStrategyHostReady()` -> `false`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So no dimension-resource requirement can create a level or bind runtime resources.

## Exact Access-Travel Requirement Enum Values

`CaveniaAccessTravelRequirement` now exposes this exact order:

1. `ENTRY_TRIGGER_DECISION`
2. `SAFE_ARRIVAL_POSITION`
3. `RETURN_PATH`
4. `SERVER_LEVEL_AVAILABILITY`
5. `PLAYER_STATE_GUARDS`
6. `TRAVEL_HOST_IMPLEMENTATION`

## Exact Access-Travel Contract Fields

`CaveniaAccessTravelRequirementContract` now exposes:

- `requirement`
- `sourcePolicyName`
- `ready`
- `runtimeImplemented`
- `canTeleport`
- `requiresActiveLevel`
- `blocker`

The exact access/travel source-policy names are:

- `ENTRY_TRIGGER_DECISION` -> `cavenia-mirage-entry-access-contract-boundary.md`
- `SAFE_ARRIVAL_POSITION` -> `CaveniaActivationReadinessHosts`
- `RETURN_PATH` -> `CaveniaActivationReadinessHosts`
- `SERVER_LEVEL_AVAILABILITY` -> `CaveniaDimensionResourceContracts`
- `PLAYER_STATE_GUARDS` -> `CaveniaGeneratorRegistrationBoundary`
- `TRAVEL_HOST_IMPLEMENTATION` -> `CaveniaActivationReadinessHosts`

Every access/travel requirement stays blocked and inert:

- `ready = false`
- `runtimeImplemented = false`
- `canTeleport = false`
- `requiresActiveLevel = true`

`CaveniaAccessTravelContracts` now exposes deterministic helpers:

- `contracts()` -> immutable requirement-contract list
- `requirements()` -> immutable requirement list
- `contractFor(CaveniaAccessTravelRequirement)` -> inert lookup
- `allRequirementsBlocked()` -> `true`
- `anyRequirementReady()` -> `false`
- `anyRequirementRuntimeImplemented()` -> `false`
- `canTeleport()` -> `false`
- `requiresActiveLevel()` -> `true`
- `accessTravelHostReady()` -> `false`
- `dimensionResourceHostReady()` -> `false`
- `dimensionCanCreateLevel()` -> `false`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So no access/travel requirement can teleport.

## Exact Spawn-Host Requirement Enum Values

`CaveniaSpawnHostRequirement` now exposes this exact order:

1. `CAVENIA_LEVEL_AVAILABILITY`
2. `POLICY_CONSUMPTION`
3. `NORMAL_ROSTER_HANDLING`
4. `CRAZY_ROSTER_HANDLING`
5. `NEARBY_ICAVENICMOB_SCAN`
6. `CAVEMAN_DEFERRAL`
7. `HOST_LOOP_OR_TICK_SOURCE`
8. `NO_FAKE_CAVERN_CRAZY_SPAWNING`

## Exact Spawn-Host Contract Fields

`CaveniaSpawnHostRequirementContract` now exposes:

- `requirement`
- `sourcePolicyName`
- `ready`
- `runtimeImplemented`
- `canSpawn`
- `usesNormalCavernCrazySpawning`
- `blocker`

The exact spawn-host source-policy names are:

- `CAVENIA_LEVEL_AVAILABILITY` -> `CaveniaDimensionResourceContracts`
- `POLICY_CONSUMPTION` -> `CaveniaSpawnProviderPolicy`
- `NORMAL_ROSTER_HANDLING` -> `CaveniaSpawnProviderPolicy`
- `CRAZY_ROSTER_HANDLING` -> `CaveniaSpawnProviderPolicy`
- `NEARBY_ICAVENICMOB_SCAN` -> `CaveniaSpawnProviderPolicy`
- `CAVEMAN_DEFERRAL` -> `caveman-cavenia-normal-roster-boundary.md`
- `HOST_LOOP_OR_TICK_SOURCE` -> `CaveniaActivationReadinessHosts`
- `NO_FAKE_CAVERN_CRAZY_SPAWNING` -> `cavenia-crazy-roster-natural-spawn-boundary.md`

Every spawn-host requirement stays blocked and inert:

- `ready = false`
- `runtimeImplemented = false`
- `canSpawn = false`
- `usesNormalCavernCrazySpawning = false`

`CaveniaSpawnHostContracts` now exposes deterministic helpers:

- `contracts()` -> immutable requirement-contract list
- `requirements()` -> immutable requirement list
- `contractFor(CaveniaSpawnHostRequirement)` -> inert lookup
- `allRequirementsBlocked()` -> `true`
- `anyRequirementReady()` -> `false`
- `anyRequirementRuntimeImplemented()` -> `false`
- `canSpawn()` -> `false`
- `usesNormalCavernCrazySpawning()` -> `false`
- `spawnHostReady()` -> `false`
- `dimensionResourceHostReady()` -> `false`
- `dimensionCanCreateLevel()` -> `false`
- `cavemanRemainsDeferred()` -> `true`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So no spawn-host requirement can spawn entities.

Spawn-host requirements do not use fake normal `CAVERN` crazy spawning.

`EntityCaveman -> deferred:caveman` remains unchanged.

## Why This Is Still Not Runtime Worldgen

This non-registered split-contract layer is not a `ChunkGenerator`.

This non-registered split-contract layer is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- add or write `dimension/cavenia.json`
- add or write `dimension_type/cavenia.json`
- bind runtime generator resources
- bind a runtime biome source
- create a `ServerLevel`
- generate chunks
- mutate world or chunk state
- create access or teleport hooks
- create spawn hooks
- create fake normal `CAVERN` crazy spawning

## Why Runtime Activation Still Stays Blocked

Current Reborn still keeps:

- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia generator
- no active Cavenia biome source
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning

So every dimension-resource requirement, every access/travel requirement and every spawn-host requirement remains blocked and not ready.

## How This Narrows Future Active Work

This slice reduces future ambiguity because:

- dimension-resource responsibilities are now split into finer requirements
- access/travel responsibilities are now split into finer requirements
- spawn-host responsibilities are now split into finer requirements
- all remain blocked until activation requirements are satisfied

Until real dimension resources, access/travel hosting and spawn hosting exist, this slice remains a non-registered split-contract layer only.
