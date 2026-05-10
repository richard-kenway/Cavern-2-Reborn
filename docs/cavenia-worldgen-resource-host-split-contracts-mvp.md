# Cavenia Worldgen-Resource Host Split Contracts MVP

This note documents a non-registered worldgen-resource split-contract layer for the remaining blocked Cavenia worldgen-resource host surface.

It builds on:

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

The later final inert readiness-matrix note is now documented separately in:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`

The later decision-only first-active-surface selection note is now documented separately in:

- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`

## What This MVP Adds

Current Reborn now has three narrower app-side worldgen-resource contract types:

- `CaveniaWorldgenResourceRequirement`
- `CaveniaWorldgenResourceRequirementContract`
- `CaveniaWorldgenResourceContracts`

They consume or reference:

- `CaveniaActivationReadinessHosts`
- `CaveniaDimensionResourceContracts`
- `CaveniaGeneratorHostContracts`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaGeneratorRuntimeContracts`
- `CaveniaGeneratorRegistrationBoundary`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaTerrainGeneratorPolicy`

This is a non-registered worldgen-resource split-contract layer, not runtime activation.

## Exact Worldgen-Resource Requirement Enum Values

`CaveniaWorldgenResourceRequirement` now exposes this exact order:

1. `CONFIGURED_CARVER_MAPPING`
2. `CONFIGURED_FEATURE_MAPPING`
3. `PLACED_FEATURE_MAPPING`
4. `BIOME_MODIFIER_MAPPING`
5. `BIOME_TAG_MAPPING`
6. `VEINS_RESOURCE_MAPPING`
7. `POPULATION_RESOURCE_MAPPING`
8. `CAVENIC_SHROOM_CAVENIA_MAPPING`
9. `RESOURCE_ORDERING_GUARD`
10. `NO_CAVERN_RESOURCE_REUSE_AS_PARITY`

## Exact Worldgen-Resource Contract Fields

`CaveniaWorldgenResourceRequirementContract` now exposes:

- `requirement`
- `activationRequirement`
- `sourcePolicyName`
- `ready`
- `resourcePresent`
- `runtimeBound`
- `canAffectWorldgen`
- `blocker`

The exact worldgen-resource requirement-to-activation mapping is:

- every requirement maps to `WORLDGEN_RESOURCE_MAPPING`

The exact worldgen-resource source-policy names are:

- `CONFIGURED_CARVER_MAPPING` -> `CaveniaCaveCarverPolicy`
- `CONFIGURED_FEATURE_MAPPING` -> `CaveniaVeinsContentPolicy`
- `PLACED_FEATURE_MAPPING` -> `CaveniaVeinsContentPolicy`
- `BIOME_MODIFIER_MAPPING` -> `CaveniaBiomeSourceStrategyContracts`
- `BIOME_TAG_MAPPING` -> `CaveniaBiomeSourceStrategyContracts`
- `VEINS_RESOURCE_MAPPING` -> `CaveniaVeinsContentPolicy`
- `POPULATION_RESOURCE_MAPPING` -> `CaveniaPopulationPolicy`
- `CAVENIC_SHROOM_CAVENIA_MAPPING` -> `CaveniaPopulationPolicy`
- `RESOURCE_ORDERING_GUARD` -> `CaveniaGeneratorRuntimeContracts`
- `NO_CAVERN_RESOURCE_REUSE_AS_PARITY` -> `CaveniaActivationReadinessHosts`

Every worldgen-resource requirement stays blocked and inert:

- `ready = false`
- `resourcePresent = false`
- `runtimeBound = false`
- `canAffectWorldgen = false`

`CaveniaWorldgenResourceContracts` now exposes deterministic helpers:

- `contracts()` -> immutable requirement-contract list
- `requirements()` -> immutable requirement list
- `contractFor(CaveniaWorldgenResourceRequirement)` -> inert lookup
- `allRequirementsBlocked()` -> `true`
- `anyRequirementReady()` -> `false`
- `anyResourcePresent()` -> `false`
- `anyRuntimeBound()` -> `false`
- `canAffectWorldgen()` -> `false`
- `configuredCarverResourcesPresent()` -> `false`
- `configuredFeatureResourcesPresent()` -> `false`
- `placedFeatureResourcesPresent()` -> `false`
- `biomeModifierResourcesPresent()` -> `false`
- `biomeTagResourcesPresent()` -> `false`
- `worldgenResourceHostReady()` -> `false`
- `dimensionResourceHostReady()` -> `false`
- `generatorHostReady()` -> `false`
- `biomeSourceStrategyHostReady()` -> `false`
- `veinsPolicyEntryCount()` -> `13`
- `activationRequirements()` -> the same immutable registration-boundary requirements
- `activationBlockedReason()` -> the same inert registration-boundary blocker text

So no worldgen-resource requirement has a resource present, no worldgen-resource requirement is runtime-bound and no worldgen-resource requirement can affect worldgen.

No configured carver resources exist.

No configured feature resources exist.

No placed feature resources exist.

No biome modifier resources exist.

No biome tag resources exist.

no CAVERN resource reuse is accepted as Cavenia parity.

## Why This Is Still Not Runtime Worldgen

This non-registered worldgen-resource split-contract layer is not a `ChunkGenerator`.

This non-registered worldgen-resource split-contract layer is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- add or write configured carver resources
- add or write configured feature resources
- add or write placed feature resources
- add or write biome modifier resources
- add or write biome tag resources
- add or write `dimension/cavenia.json`
- add or write `dimension_type/cavenia.json`
- bind runtime worldgen resources
- bind a runtime generator
- bind a runtime biome source
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
- no `CAVERN` resource reuse accepted as Cavenia parity

So every worldgen-resource requirement remains blocked and not ready.

## How This Narrows Future Active Work

This slice reduces future ambiguity because:

- worldgen-resource responsibilities are now split into finer requirements
- all remain blocked until activation requirements are satisfied

Until real worldgen resources, binding decisions and ordering guards exist, this slice remains a non-registered worldgen-resource split-contract layer only.
