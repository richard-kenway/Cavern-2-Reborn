# Cavenia Terrain Generator Foundation / Non-Runtime MVP

This note documents a pure non-runtime terrain-generator foundation for future active Cavenia work.

The later non-registered active-generator technical scaffold note is documented separately in:

- `docs/cavenia-active-generator-technical-scaffold-mvp.md`

It builds on:

- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- `docs/cavenia-dimension-key-type-contract-boundary.md`

## What This MVP Adds

Current Reborn now has a pure `core` terrain policy:

- `CaveniaTerrainGeneratorPolicy`
- `CaveniaTerrainStep`

These types encode source-confirmed legacy terrain constants and terrain-stage ordering without registering or activating a real generator.

This is not an active Cavenia generator implementation.

## Exact Constants Added

`CaveniaTerrainGeneratorPolicy` now pins:

- `WORLD_HEIGHT = 128`
- `MIN_Y = 0`
- `BEDROCK_FLOOR_Y = 0`
- `BEDROCK_CEILING_Y = 127`
- `AIR_CLEAR_FROM_Y = 128`
- `AIR_CLEAR_TO_Y = 255`
- base terrain block id `minecraft:stone`
- bedrock block id `minecraft:bedrock`
- air block id `minecraft:air`

The same policy also exposes simple pure helpers for:

- legacy-height inclusion
- bedrock-floor / bedrock-ceiling checks
- above-height detection
- the `128..255` clear-to-air range

## Exact Terrain Ordering Encoded

The non-runtime policy encodes the source-confirmed legacy order from `ChunkGeneratorCavenia#generateChunk(...)` and its later population relationship:

1. base stone fill
2. optional cave carving
3. biome top/filter replacement
4. VEINS generator-side mutation
5. final chunk construction
6. later population stage for lakes/falls/shrooms

The terrain policy therefore pins that population is later than final chunk construction instead of pretending it is part of the same generator-primer mutation pass.

## Relationship To Legacy `ChunkGeneratorCavenia`

This policy is a direct non-runtime encoding of the inspected legacy generator contract:

- `ChunkGeneratorCavenia` starts from full stone fill
- optional cave carving happens before top/filter replacement
- biome top/filter replacement happens before VEINS
- VEINS is generator-side mutation
- lakes, falls and `cavenic_shroom` happen later during population

That means the policy is intentionally narrower than an actual generator class, but it still preserves the generator-owned order that later active work must either match or explicitly approximate.

## Relationship To The Future Generator Direction

`docs/cavenia-active-foundation-technical-spike.md` already recommended:

- hybrid custom generator plus data-driven features

This non-runtime MVP supports that direction by pinning the terrain-sensitive steps that do not map cleanly to a pure data-driven approximation:

- full stone fill
- bedrock caps
- legacy `WORLD_HEIGHT = 128`
- carve-before-top/filter ordering
- VEINS mutation before final chunk construction

The later pure biome/top/filter follow-up is documented separately in:

- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`

The later pure cave-carver follow-up is documented separately in:

- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`

The later pure VEINS/content follow-up is documented separately in:

- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`

The later pure population follow-up is documented separately in:

- `docs/cavenia-population-policy-non-runtime-mvp.md`

## What This MVP Still Does Not Add

This slice does not add:

- an active Cavenia chunk generator class
- an active Cavenia biome source
- an active Cavenia cave carver
- active Cavenia VEINS/content worldgen
- active Cavenia population
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- any Cavenia worldgen resources
- an active Cavenia runtime `ServerLevel`
- active Cavenia access or teleport
- active Cavenia spawning
- active crazy spawning
- fake normal `CAVERN` crazy spawning
- `cavernreborn:caveman`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

Current Reborn still keeps:

- no active Cavenia runtime `ServerLevel`
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning

## Why No Active Generator Was Added

The current repository still lacks multiple decisions that must be locked before an honest active generator exists:

- custom generator class design
- biome source mapping
- cave carver mapping
- top/filter replacement implementation
- VEINS integration
- lakes/falls/shroom population integration
- dimension type `height` and `logical_height` values

Adding a runtime generator before those decisions would imply a real terrain path that the repository still does not implement.

## Why No Dimension Or Worldgen Resources Were Added

This non-runtime terrain policy does not justify checked-in runtime resources.

Current Reborn still keeps:

- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no Cavenia biome-source resources
- no Cavenia configured carvers
- no Cavenia configured or placed features
- no Cavenia noise settings, density functions or surface rules

The policy is intentionally pure Java only, so the terrain contract can be pinned before the project commits to any active runtime shape.

## Next Future Work

Before active generator work starts, the project still needs:

- custom generator class design
- biome source decisions
- cave-carver mapping decisions
- top/filter implementation decisions
- VEINS integration decisions
- population integration decisions
- dimension type height/logical-height decisions

This note is therefore a non-runtime foundation only, not an active Cavenia terrain slice.
