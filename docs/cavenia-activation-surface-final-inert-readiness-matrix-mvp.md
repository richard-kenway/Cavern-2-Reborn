# Cavenia Activation Surface Consolidation / Final Inert Readiness Matrix MVP

This note documents a final inert activation-surface readiness matrix for the current blocked Cavenia activation state.

It builds on:

- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`
- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`
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

The later decision-only first-active-surface selection note is now documented separately in:

- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`

The later narrow non-runtime biome-source strategy plan note is now documented separately in:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`

The later legacy-to-modern biome-key mapping inventory note is now documented separately in:

- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`

The later pure non-runtime weighted biome selection algorithm note is now documented separately in:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`

## What This MVP Adds

Current Reborn now has three final inert app-side matrix types:

- `CaveniaActivationSurface`
- `CaveniaActivationReadinessMatrixEntry`
- `CaveniaActivationReadinessMatrix`

They aggregate and summarize:

- `CaveniaGeneratorHostContracts`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaDimensionResourceContracts`
- `CaveniaAccessTravelContracts`
- `CaveniaSpawnHostContracts`
- `CaveniaWorldgenResourceContracts`
- `CaveniaActivationReadinessHosts`
- `CaveniaGeneratorRegistrationBoundary`
- `CaveniaGeneratorRuntimeContracts`
- `CaveniaGeneratorBridge`
- `CaveniaSpawnProviderPolicy`

This is a final inert activation-surface readiness matrix, not runtime activation.

## Exact Activation Surface Enum Values

`CaveniaActivationSurface` now exposes this exact order:

1. `GENERATOR_HOST`
2. `BIOME_SOURCE_STRATEGY`
3. `DIMENSION_RESOURCE`
4. `ACCESS_TRAVEL`
5. `SPAWN_HOST`
6. `WORLDGEN_RESOURCE`

## Exact Matrix Entry Fields

`CaveniaActivationReadinessMatrixEntry` now exposes:

- `surface`
- `sourceContractName`
- `requirementCount`
- `allRequirementsBlocked`
- `anyRequirementReady`
- `runtimeImplemented`
- `activeRuntimeSurfacePresent`
- `blocker`

## Exact Per-Surface And Total Requirement Counts

The matrix now pins these exact per-surface counts:

- `GENERATOR_HOST` -> `CaveniaGeneratorHostContracts`, `9`
- `BIOME_SOURCE_STRATEGY` -> `CaveniaBiomeSourceStrategyContracts`, `7`
- `DIMENSION_RESOURCE` -> `CaveniaDimensionResourceContracts`, `6`
- `ACCESS_TRAVEL` -> `CaveniaAccessTravelContracts`, `6`
- `SPAWN_HOST` -> `CaveniaSpawnHostContracts`, `8`
- `WORLDGEN_RESOURCE` -> `CaveniaWorldgenResourceContracts`, `10`

The exact total requirement count is `46`.

`CaveniaActivationReadinessMatrix` now exposes deterministic helpers:

- `entries()` -> immutable matrix-entry list
- `surfaces()` -> immutable surface list
- `entryFor(CaveniaActivationSurface)` -> inert lookup
- `totalRequirementCount()` -> `46`
- `blockedRequirementCount()` -> `46`
- `allSurfacesBlocked()` -> `true`
- `anySurfaceReady()` -> `false`
- `anyRuntimeImplemented()` -> `false`
- `activeRuntimeSurfacePresent()` -> `false`
- `canActivateCavenia()` -> `false`
- `generatorHostRequirementCount()` -> `9`
- `biomeSourceStrategyRequirementCount()` -> `7`
- `dimensionResourceRequirementCount()` -> `6`
- `accessTravelRequirementCount()` -> `6`
- `spawnHostRequirementCount()` -> `8`
- `worldgenResourceRequirementCount()` -> `10`
- `generatorCanCreateChunks()` -> `false`
- `biomeSourceRuntimeReady()` -> `false`
- `dimensionCanCreateLevel()` -> `false`
- `accessCanTeleport()` -> `false`
- `spawnHostCanSpawn()` -> `false`
- `worldgenCanAffectWorldgen()` -> `false`
- `dimensionJsonPresent()` -> `false`
- `dimensionTypeJsonPresent()` -> `false`
- `configuredCarverResourcesPresent()` -> `false`
- `configuredFeatureResourcesPresent()` -> `false`
- `placedFeatureResourcesPresent()` -> `false`
- `biomeModifierResourcesPresent()` -> `false`
- `biomeTagResourcesPresent()` -> `false`
- `cavemanRemainsDeferred()` -> `true`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So every surface is blocked/not ready.

No surface is runtime-implemented.

No active runtime surface is present.

Cavenia cannot activate.

Generator cannot create chunks.

Biome source runtime is not ready.

Dimension cannot create level.

Access cannot teleport.

Spawn host cannot spawn.

Worldgen cannot affect worldgen.

dimension JSON/type JSON are absent.

configured carver/configured feature/placed feature/biome modifier/biome tag resources are absent.

`EntityCaveman -> deferred:caveman` remains unchanged.

## Why This Is Still Not Runtime Activation

This final inert activation-surface readiness matrix is not a `ChunkGenerator`.

This final inert activation-surface readiness matrix is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- add or write `dimension/cavenia.json`
- add or write `dimension_type/cavenia.json`
- add or write worldgen resources
- create a runtime generator
- create a runtime biome source
- create a `ServerLevel`
- generate chunks
- mutate world or chunk state
- create access or teleport hooks
- create spawn hooks
- accept `CAVERN` resource reuse as fake Cavenia parity

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
- no active configured carver resources
- no active configured feature resources
- no active placed feature resources
- no active biome modifier resources
- no active biome tag resources
- no CAVERN resource reuse is accepted as Cavenia parity

So every aggregated surface remains blocked and not ready.

## How This Narrows Future Active Work

This slice reduces future ambiguity because:

- all activation surfaces are now visible in one matrix
- all remain blocked until activation requirements are satisfied
- future implementation can choose one surface to activate deliberately rather than accidentally crossing boundaries

The later biome-source follow-ups are documented in:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`


The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.

The runtime-biome-source real-subclass go/no-go decision follow-up is now documented in `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`.

The guarded unregistered real biome-source subclass stub follow-up is now documented in `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`.

The runtime-biome-source codec-method-shape stub follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
The runtime-biome-source holder/resource-key conversion readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md`.
The runtime-biome-source fallback-policy readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md`.
The runtime-biome-source possible-biomes readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md`.

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.

The readiness-chain consolidation / next-decision follow-up is now documented in `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md`.

The selector-input derivation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md`.
