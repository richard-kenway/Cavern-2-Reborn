# Cavenia Active Generator Technical Scaffold / MVP

This note documents a non-registered technical scaffold for the future active Cavenia custom-generator path.

It builds on:

- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`
- `docs/cavenia-population-policy-non-runtime-mvp.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-active-foundation-technical-spike.md`

The later inert app-side runtime prototype note is now documented separately in:

- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`

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

The later final inert readiness-matrix note is now documented separately in:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`

## What This MVP Adds

Current Reborn now has a pure `core` technical scaffold:

- `CaveniaGeneratorScaffold`
- `CaveniaGeneratorScaffoldStage`

This scaffold is not a `ChunkGenerator`.

This scaffold is not a `BiomeSource`.

It does not add a codec, registry entry, configured carver, configured feature, placed feature, data-pack dimension resource or runtime world.

It only collects and exposes the already accepted `core` policies that a future active Cavenia custom-generator path would need:

- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`
- `CaveniaPopulationPolicy`
- `CaveniaSpawnProviderPolicy`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Exact Scaffold Surface Added

`CaveniaGeneratorScaffold` now exposes deterministic, testable future-generator inputs:

- `dimensionId()` -> `cavernreborn:cavenia`
- `worldHeight()` -> `128`
- `terrainStages()` -> `CaveniaTerrainGeneratorPolicy.generationSteps()`
- `biomeEntryCount()` -> `14`
- `biomeTotalWeight()` -> `675`
- `veinEntryCount()` -> `13`
- `veinTotalWeight()` -> `436`
- `generateLakesDefaultEnabled()` -> `true`
- `populationRunsAfterVeinsMutation()` -> `true`
- `caveCarvingRunsBeforeBiomeTopFilterReplacement()` -> `true`
- `isRuntimeGeneratorRegistered()` -> `false`
- `requiresDimensionJsonBeforeActivation()` -> `true`
- `requiresDimensionTypeJsonBeforeActivation()` -> `true`

So the scaffold proves that the future generator inputs are already available from `core` without creating a runtime generator implementation.

## Exact Future Generator Order Exposed

`CaveniaGeneratorScaffoldStage` now pins this explicit future generator scaffold order:

1. identity
2. base terrain
3. cave carving
4. biome top/filter replacement
5. VEINS mutation
6. final chunk construction
7. population integration
8. spawn provider integration deferred
9. entry/access deferred

That future order narrows the active custom-generator path without pretending the project already has a real `ChunkGeneratorCavenia` port.

It also aligns directly with the already pinned `core` worldgen policies:

- terrain order from `CaveniaTerrainGeneratorPolicy`
- top/filter roster from `CaveniaBiomeTopFilterPolicy`
- carve order and low-band replacement from `CaveniaCaveCarverPolicy`
- generator-side VEINS mutation from `CaveniaVeinsContentPolicy`
- later population ordering from `CaveniaPopulationPolicy`
- Cavenia-only future spawn-host inputs from `CaveniaSpawnProviderPolicy`

## Why This Is Still Not An Active Generator

This scaffold is intentionally not registered anywhere.

It does not:

- create chunks
- mutate a chunk or primer
- extend `ChunkGenerator`
- create or extend a `BiomeSource`
- expose a codec
- register a generator type
- create a `ServerLevel`
- expose travel or spawning

So current Reborn still keeps:

- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia generator
- no active Cavenia biome source
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning

## Why No Runtime Resources Were Added

This scaffold only proves that the policy inputs already exist and that the future custom-generator order is explicit.

It does not justify checked-in runtime resources yet.

Current Reborn still keeps:

- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no active Cavenia configured carver
- no active Cavenia configured features
- no active Cavenia placed features
- no active Cavenia biome modifiers
- no active Cavenia worldgen resources

## How This Narrows Future Active Generator Work

This scaffold reduces future ambiguity because:

- identity is already known through `cavernreborn:cavenia`
- terrain order is already known
- biome top/filter policy is already available
- cave-carver policy is already available
- VEINS policy is already available
- population policy is already available
- spawn policy still stays deferred to a future Cavenia-only spawn host
- entry/access still stays deferred

## What Still Remains Before Honest Activation

The project still needs:

- an actual custom generator class design
- a real biome-source strategy
- real chunk or primer mutation code
- dimension JSON and dimension-type JSON decisions
- generator registration and codec decisions only when runtime activation is honest
- safe access/travel decisions
- an explicit future Cavenia-only spawn host

Until then, this slice remains a non-registered technical scaffold only.
