# Cavenia Generator Host / Biome Source Strategy Host Split Contracts MVP

This note documents a non-registered split-contract layer for the two most important future Cavenia runtime hosts.

It builds on:

- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`
- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later dimension-resource / access-travel / spawn-host split-contract note is now documented separately in:

- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`

The later worldgen-resource split-contract note is now documented separately in:

- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`

The later final inert readiness-matrix note is now documented separately in:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`

The later decision-only first-active-surface selection note is now documented separately in:

- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`

The later narrow non-runtime biome-source strategy plan note is now documented separately in:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`

The later legacy-to-modern biome-key mapping inventory note is now documented separately in:

- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`

## What This MVP Adds

Current Reborn now has two narrower app-side split-contract surfaces:

- `CaveniaGeneratorHostRequirement`
- `CaveniaGeneratorHostRequirementContract`
- `CaveniaGeneratorHostContracts`
- `CaveniaBiomeSourceStrategyRequirement`
- `CaveniaBiomeSourceStrategyRequirementContract`
- `CaveniaBiomeSourceStrategyContracts`

They consume or reference:

- `CaveniaActivationReadinessHosts`
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

## Exact Generator-Host Requirement Enum Values

`CaveniaGeneratorHostRequirement` now exposes this exact order:

1. `RUNTIME_CONTEXT`
2. `BASE_TERRAIN_FILL`
3. `CAVE_CARVER_EXECUTION`
4. `BIOME_TOP_FILTER_EXECUTION`
5. `VEINS_MUTATION_EXECUTION`
6. `FINAL_CHUNK_CONSTRUCTION`
7. `POPULATION_DELEGATION`
8. `CODEC_AND_REGISTRATION`
9. `DIMENSION_RESOURCE_BINDING`

## Exact Generator-Host Contract Fields

`CaveniaGeneratorHostRequirementContract` now exposes:

- `requirement`
- `runtimeOperation`
- `sourcePolicyName`
- `ready`
- `runtimeImplemented`
- `canCreateChunks`
- `canMutatePrimer`
- `blocker`

The exact requirement-to-runtime-operation mapping is:

- `RUNTIME_CONTEXT` -> `IDENTITY`
- `BASE_TERRAIN_FILL` -> `BASE_TERRAIN_FILL`
- `CAVE_CARVER_EXECUTION` -> `CAVE_CARVING`
- `BIOME_TOP_FILTER_EXECUTION` -> `BIOME_TOP_FILTER_REPLACEMENT`
- `VEINS_MUTATION_EXECUTION` -> `VEINS_MUTATION`
- `FINAL_CHUNK_CONSTRUCTION` -> `FINAL_CHUNK_CONSTRUCTION`
- `POPULATION_DELEGATION` -> `POPULATION_INTEGRATION`
- `CODEC_AND_REGISTRATION` -> `IDENTITY`
- `DIMENSION_RESOURCE_BINDING` -> `IDENTITY`

The exact generator-host source-policy names are:

- `RUNTIME_CONTEXT` -> `CaveniaGeneratorBridge`
- `BASE_TERRAIN_FILL` -> `CaveniaTerrainGeneratorPolicy`
- `CAVE_CARVER_EXECUTION` -> `CaveniaCaveCarverPolicy`
- `BIOME_TOP_FILTER_EXECUTION` -> `CaveniaBiomeTopFilterPolicy`
- `VEINS_MUTATION_EXECUTION` -> `CaveniaVeinsContentPolicy`
- `FINAL_CHUNK_CONSTRUCTION` -> `CaveniaGeneratorRuntimeContracts`
- `POPULATION_DELEGATION` -> `CaveniaPopulationPolicy`
- `CODEC_AND_REGISTRATION` -> `CaveniaGeneratorRegistrationBoundary`
- `DIMENSION_RESOURCE_BINDING` -> `CaveniaActivationReadinessHosts`

Every generator-host requirement stays blocked and inert:

- `ready = false`
- `runtimeImplemented = false`
- `canCreateChunks = false`
- `canMutatePrimer = false`

`CaveniaGeneratorHostContracts` now exposes deterministic helpers:

- `contracts()` -> immutable requirement-contract list
- `requirements()` -> immutable requirement list
- `contractFor(CaveniaGeneratorHostRequirement)` -> inert lookup
- `allRequirementsBlocked()` -> `true`
- `anyRequirementReady()` -> `false`
- `anyRequirementRuntimeImplemented()` -> `false`
- `canCreateChunks()` -> `false`
- `canMutatePrimer()` -> `false`
- `runtimeOperationsBlocked()` -> `true`
- `generatorSkeletonCanCreateChunks()` -> `false`
- `generatorHostReady()` -> `false`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So no generator-host requirement can create chunks or mutate primers.

## Exact Biome-Source-Strategy Requirement Enum Values

`CaveniaBiomeSourceStrategyRequirement` now exposes this exact order:

1. `LEGACY_WEIGHTED_BIOME_INPUTS`
2. `LEGACY_TO_MODERN_BIOME_KEY_MAPPING`
3. `WEIGHTED_SELECTION_ALGORITHM`
4. `TOP_BLOCK_STRATEGY`
5. `RUNTIME_BIOME_SOURCE_IMPLEMENTATION`
6. `BIOME_SOURCE_CODEC_AND_REGISTRATION`
7. `REGISTRY_LOOKUP_ACCESS`

## Exact Biome-Source-Strategy Contract Fields

`CaveniaBiomeSourceStrategyRequirementContract` now exposes:

- `requirement`
- `sourcePolicyName`
- `ready`
- `runtimeImplemented`
- `runtimeBiomeSource`
- `codecRegistered`
- `blocker`

The exact biome-source-strategy source-policy names are:

- `LEGACY_WEIGHTED_BIOME_INPUTS` -> `CaveniaBiomeTopFilterPolicy`
- `LEGACY_TO_MODERN_BIOME_KEY_MAPPING` -> `CaveniaBiomeSelectionSkeleton`
- `WEIGHTED_SELECTION_ALGORITHM` -> `CaveniaBiomeTopFilterPolicy`
- `TOP_BLOCK_STRATEGY` -> `CaveniaBiomeTopFilterPolicy`
- `RUNTIME_BIOME_SOURCE_IMPLEMENTATION` -> `CaveniaBiomeSelectionSkeleton`
- `BIOME_SOURCE_CODEC_AND_REGISTRATION` -> `CaveniaGeneratorRegistrationBoundary`
- `REGISTRY_LOOKUP_ACCESS` -> `CaveniaActivationReadinessHosts`

Every biome-source-strategy requirement stays blocked and inert:

- `ready = false`
- `runtimeImplemented = false`
- `runtimeBiomeSource = false`
- `codecRegistered = false`

`CaveniaBiomeSourceStrategyContracts` now exposes deterministic helpers:

- `contracts()` -> immutable requirement-contract list
- `requirements()` -> immutable requirement list
- `contractFor(CaveniaBiomeSourceStrategyRequirement)` -> inert lookup
- `allRequirementsBlocked()` -> `true`
- `anyRequirementReady()` -> `false`
- `anyRequirementRuntimeImplemented()` -> `false`
- `runtimeBiomeSourceReady()` -> `false`
- `codecRegistered()` -> `false`
- `biomeSelectionIsRuntimeBiomeSource()` -> `false`
- `biomeSourceStrategyHostReady()` -> `false`
- `legacyBiomeEntryCount()` -> `14`
- `legacyBiomeTotalWeight()` -> `675`
- `requiresBiomeSourceStrategyBeforeActivation()` -> `true`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So no biome-source-strategy requirement is a runtime biome source and no biome-source-strategy requirement has codec registration.

## Why This Is Still Not Runtime Worldgen

This non-registered split-contract layer is not a `ChunkGenerator`.

This non-registered split-contract layer is not a `BiomeSource`.

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

So every generator-host requirement and every biome-source-strategy requirement remains blocked and not ready.

## How This Narrows Future Active Generator Work

This slice reduces future ambiguity because:

- generator-host responsibilities are now split into finer requirements
- biome-source-strategy responsibilities are now split into finer requirements
- the generator-host split now maps directly onto the accepted runtime-operation order
- the biome-source-strategy split now maps directly onto the accepted legacy biome-policy inputs
- both remain blocked until activation requirements are satisfied

The later biome-source follow-ups are documented in:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`

Until a real generator host, real biome-source strategy, codec/registration path, dimension resources, safe access and Cavenia-only spawn-host decisions exist, this slice remains a non-registered split-contract layer only.

The runtime-biome-source API-shape inventory follow-up is now documented in .

The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.
