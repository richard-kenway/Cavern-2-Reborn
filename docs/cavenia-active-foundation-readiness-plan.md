# Cavenia Active Foundation Readiness / Implementation Plan

This note consolidates the current source-confirmed Cavenia readiness state into a planning bridge for the first future active implementation slice.

It does not add active `cavernreborn:cavenia`, `dimension/cavenia.json`, `dimension_type/cavenia.json`, active Cavenia worldgen, active Cavenia spawning or a runtime Cavenia access path.

The later inert identity follow-up is now documented separately in:

- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later pure non-runtime terrain-generator foundation note is now documented separately in:

- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`

The later pure non-runtime cave-carver policy note is now documented separately in:

- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`

The later pure non-runtime biome top/filter policy note is now documented separately in:

- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`

The later pure non-runtime VEINS/content policy note is now documented separately in:

- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`

The later pure non-runtime population policy note is now documented separately in:

- `docs/cavenia-population-policy-non-runtime-mvp.md`

The later non-registered active-generator technical scaffold note is now documented separately in:

- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`
- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`

Current readiness state still has no active Cavenia spawning and no fake normal `CAVERN` crazy spawning.

Current repository state now also has:

- inert `CAVENIA_LOCATION`
- inert `CAVENIA_LEVEL_KEY`
- pure `CaveniaTerrainGeneratorPolicy`
- pure `CaveniaTerrainStep`
- pure `CaveniaCaveCarverPolicy`
- pure `CaveniaCaveCarverReplacement`
- pure `CaveniaBiomeTopFilterPolicy`
- pure `CaveniaBiomeEntry`
- pure `CaveniaVeinsContentPolicy`
- pure `CaveniaVeinEntry`
- pure `CaveniaVeinBiomeFilterMode`
- pure `CaveniaPopulationPolicy`
- pure `CaveniaPopulationStep`
- pure `CaveniaPopulationBranch`
- pure `CaveniaGeneratorScaffold`
- pure `CaveniaGeneratorScaffoldStage`
- inert `CaveniaGeneratorBridge`
- inert `CaveniaGeneratorRegistrationBoundary`
- inert `CaveniaGeneratorActivationRequirement`
- inert `CaveniaGeneratorSkeleton`
- inert `CaveniaBiomeSelectionSkeleton`
- inert `CaveniaGeneratorRuntimeOperation`
- inert `CaveniaGeneratorRuntimeOperationContract`
- inert `CaveniaGeneratorRuntimeContracts`
- inert `CaveniaActivationReadinessHost`
- inert `CaveniaActivationReadinessHostContract`
- inert `CaveniaActivationReadinessHosts`
- inert `CaveniaGeneratorHostRequirement`
- inert `CaveniaGeneratorHostRequirementContract`
- inert `CaveniaGeneratorHostContracts`
- inert `CaveniaBiomeSourceStrategyRequirement`
- inert `CaveniaBiomeSourceStrategyRequirementContract`
- inert `CaveniaBiomeSourceStrategyContracts`
- inert `CaveniaDimensionResourceRequirement`
- inert `CaveniaDimensionResourceRequirementContract`
- inert `CaveniaDimensionResourceContracts`
- inert `CaveniaAccessTravelRequirement`
- inert `CaveniaAccessTravelRequirementContract`
- inert `CaveniaAccessTravelContracts`
- inert `CaveniaSpawnHostRequirement`
- inert `CaveniaSpawnHostRequirementContract`
- inert `CaveniaSpawnHostContracts`
- inert `CaveniaWorldgenResourceRequirement`
- inert `CaveniaWorldgenResourceRequirementContract`
- inert `CaveniaWorldgenResourceContracts`

## Completed Readiness Inputs

The current completed readiness base is:

- `docs/cavenia-dimension-provider-foundation-boundary.md`
- `docs/cavenia-dimension-key-type-contract-boundary.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- `docs/cavenia-mirage-entry-access-contract-boundary.md`
- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/caveman-cavenia-normal-roster-boundary.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`
- `docs/cavenia-population-policy-non-runtime-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`

Together those notes now pin:

- the dimension/provider foundation boundary
- the future key/type contract boundary
- the weighted biome-provider contract boundary
- the VEINS/content pipeline contract boundary
- the chunk-generator / terrain-pipeline contract boundary
- the `MapGenCaveniaCaves` cave-carver contract boundary
- the populate/lakes/falls/`cavenic_shroom` population contract boundary
- the shared mirage entry/access contract boundary
- the provider-owned spawn-provider / crazy-roster activation contract boundary
- the pure non-runtime `CaveniaSpawnProviderPolicy`
- the pure non-runtime `CaveniaTerrainGeneratorPolicy`
- the pure non-runtime `CaveniaCaveCarverPolicy`
- the pure non-runtime `CaveniaBiomeTopFilterPolicy`
- the pure non-runtime `CaveniaVeinsContentPolicy`
- the pure non-runtime `CaveniaPopulationPolicy`
- the pure non-registered `CaveniaGeneratorScaffold`
- the intentional `EntityCaveman -> deferred:caveman` normal-roster deferral

Phase planning below should treat those documents and the pure `core` policy types `CaveniaSpawnProviderPolicy` and `CaveniaSpawnEntry` as the accepted readiness base.

The narrower follow-up implementation-decision spike is now documented separately in:

- `docs/cavenia-active-foundation-technical-spike.md`

## Stable Implementation Inputs

The current repository state and inspected legacy source now pin these implementation inputs:

- the likely future modern id is `cavernreborn:cavenia`
- legacy `dimensionId = -54` is identity history only, not a modern runtime id
- `worldHeight = 128` is a key terrain constraint
- `CaveniaConfig.BIOMES` is a source-confirmed weighted biome list contract
- `CaveniaConfig.VEINS` default entries and generator-side ordering are source-confirmed
- `MapGenCaveniaCaves` gravel/water/air carve rules are source-confirmed
- `ChunkGeneratorCavenia#populate(...)` lake/fall/`cavenic_shroom` behavior is source-confirmed
- mirage access is a shared portal-plus-book flow, not a direct Cavenia right-click teleport
- `CaveniaSpawnProviderPolicy` now pins `monsterSpawn`, `crazySpawnChance`, the chance-to-range mapping, the vertical scan-range mapping, the normal roster and the crazy roster
- the source-literal nearby scan wording remains: nearby `ICavenicMob` whose `isNonBoss()` returns `false`
- `EntityCaveman -> deferred:caveman` remains intentional and source-backed
- inert `CAVENIA_LOCATION` and `CAVENIA_LEVEL_KEY` now pin the future `cavernreborn:cavenia` identity without creating a runtime level
- an inert app-side `CaveniaGeneratorBridge` can now mirror that accepted identity and the accepted `core` scaffold facts without becoming a registered generator
- `CaveniaTerrainGeneratorPolicy` now pins `WORLD_HEIGHT = 128`, the bedrock/air height bands and the exact terrain-stage order without creating a runtime generator
- `CaveniaCaveCarverPolicy` now pins the `20..24` tunnel-origin band, the gravel/water/air carve-replacement rules and the carve-before-top/filter-plus-VEINS ordering without creating a runtime carver
- `CaveniaBiomeTopFilterPolicy` now pins the shipped `CaveniaConfig.BIOMES` roster, total weight `675`, the default stone terrain/filter rule and the shipped top-block mappings without creating a runtime biome source
- `CaveniaVeinsContentPolicy` now pins the shipped `CaveniaConfig.VEINS` roster, total weight `436`, default target block `minecraft:stone`, default chance `1.0D`, `autoVeins = false` and the source-as-written exclusion-style biome-filter branch without creating configured/placed features or a runtime generator
- `CaveniaPopulationPolicy` now pins `CaveniaConfig.generateLakes = true`, the exact lake/fall/`cavenic_shroom` branch formulas from legacy `ChunkGeneratorCavenia#populate(...)`, the `BiomeDecorator.generateFalls` dependency and the later population ordering after VEINS mutation and final chunk construction without creating configured/placed features or a runtime generator
- `CaveniaGeneratorScaffold` now pins the future active generator stage order and proves that the terrain, biome, cave-carver, VEINS, population and identity inputs already resolve from `core` without creating a runtime generator

## Unresolved Blockers

The first active implementation slice still depends on unresolved choices or missing foundations:

- active dimension key/type values
- active `dimension/cavenia.json` and `dimension_type/cavenia.json`
- custom generator versus a documented approximation strategy
- biome source mapping
- surface/top/filter mapping
- cave carver mapping
- VEINS/content mapping
- lake/fall/shroom population mapping
- entry/access UX plus safe arrival and return
- spawn-provider runtime host
- Caveman full parity

## Strict Non-Goals For This Increment

This readiness-plan increment does not add:

- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- active `cavernreborn:cavenia`
- active Cavenia worldgen resources
- active Cavenia biome source
- active Cavenia configured features or placed features
- active Cavenia carvers, noise settings, density functions or surface rules
- active Cavenia spawn provider
- active Cavenia natural spawning
- active crazy natural spawning
- fake normal `CAVERN` crazy spawn placements, biome modifiers or biome tags
- `cavernreborn:caveman`
- Mirage Book, Cavenia portal, teleporters, GUI, packets, keybinds, commands, economy, magic-book systems or progression gates

## Recommended Future Implementation Phases

### Phase 0: Completed readiness inputs

Treat all current Cavenia boundary docs and the non-runtime spawn-provider policy as the completed source-confirmed readiness base.

### Phase 1: Active Cavenia key/type and inactive runtime identity MVP

Only do this if it can be checked in without exposing broken gameplay.

This phase must define the minimum safe modern identity contract and must not imply terrain, access, spawning or full parity.

### Phase 2: Minimal terrain/generator MVP

Decide custom generator versus documented approximation.

This phase must address:

- `worldHeight = 128`
- full stone fill
- bedrock caps
- top/filter replacement
- terrain mutation ordering

### Phase 3: Cave carver MVP

Map or intentionally approximate `MapGenCaveniaCaves`.

This phase must address:

- tunnel origin band
- widened cave formulas
- actual-height usage
- gravel/water/air replacement

### Phase 4: Biome provider and surface/top/filter MVP

Map the weighted biome contract and top/terrain block replacement.

Do not pretend a simple biome-source JSON is enough unless that approximation is deliberate and documented.

### Phase 5: VEINS/content MVP

Map generator-side VEINS ordering after carving and top/filter replacement.

Keep current `CAVERN` ore distributions separate from future Cavenia distributions.

### Phase 6: Population MVP

Split lakes, falls/liquids and `cavenic_shroom` into narrow slices.

Do not reuse the current `CAVERN` `cavenic_shroom` placement as fake Cavenia parity.

### Phase 7: Entry/access MVP

Decide whether to preserve the shared mirage portal-plus-book UX or intentionally replace it.

This phase must include safe arrival and return behavior before exposing travel.

### Phase 8: Spawn provider MVP

Consume `CaveniaSpawnProviderPolicy`.

This phase must be Cavenia-only and must not use normal `CAVERN` crazy biome modifiers, biome tags or spawn placements.

It must preserve the source-literal nearby predicate wording:

- nearby `ICavenicMob` whose `isNonBoss()` returns `false`

It must keep `EntityCaveman -> deferred:caveman` unless full Caveman parity is explicitly in scope.

### Phase 9: Caveman full parity

This should remain a separate future slice.

It requires explicit decisions for:

- backpack inventory
- GUI/menu interaction
- sitting/model behavior
- item pickup and persistence
- Mirage/Magic-book seeding

## First Active MVP Candidate

The follow-up implementation-decision bridge now lives in:

- `docs/cavenia-active-foundation-technical-spike.md`

The recommended next real slice after that spike is:

- `Cavenia Runtime Key / Inactive Dimension Scaffold MVP`

That next slice should stop at inert identity constants and boundary smoke. It should not add `dimension/cavenia.json`, `dimension_type/cavenia.json`, active travel, terrain, spawning or fake `CAVERN` parity.

## Minimum Acceptance Checklist Before Enabling Cavenia

- active dimension key/type agreed
- safe generator strategy agreed
- no broken teleport/access path
- no fake `CAVERN` spawn path
- spawn provider consumes the non-runtime policy
- Caveman handling explicitly decided
- validation loop green

## Related Notes

- `docs/cavenia-dimension-provider-foundation-boundary.md`
- `docs/cavenia-dimension-key-type-contract-boundary.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- `docs/cavenia-mirage-entry-access-contract-boundary.md`
- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/caveman-cavenia-normal-roster-boundary.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
