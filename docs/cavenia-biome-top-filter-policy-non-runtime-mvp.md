# Cavenia Biome Top/Filter Policy / Non-Runtime MVP

This note documents a pure non-runtime biome top/filter policy for future active Cavenia terrain work.

The later non-registered active-generator technical scaffold note is documented separately in:

- `docs/cavenia-active-generator-technical-scaffold-mvp.md`

It builds on:

- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`
- `docs/cavenia-population-policy-non-runtime-mvp.md`

The later narrow non-runtime biome-source strategy plan note is now documented separately in:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`

The later legacy-to-modern biome-key mapping inventory note is now documented separately in:

- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`

The later pure non-runtime weighted biome selection algorithm note is now documented separately in:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`

## What This MVP Adds

Current Reborn now has a pure `core` biome/top-filter policy:

- `CaveniaBiomeTopFilterPolicy`
- `CaveniaBiomeEntry`

These types encode the source-confirmed default `CaveniaConfig.BIOMES` roster, weights, terrain/filter block behavior and top-block mapping without registering or activating a real biome source, chunk generator or dimension resource.

This is not an active Cavenia biome provider or terrain implementation.

## Exact Legacy Roster And Weights Encoded

`CaveniaBiomeTopFilterPolicy` now pins the shipped legacy biome roster exactly:

- `OCEAN`, weight `15`
- `PLAINS`, weight `100`
- `DESERT`, weight `70`
- `DESERT_HILLS`, weight `10`
- `FOREST`, weight `80`
- `FOREST_HILLS`, weight `10`
- `TAIGA`, weight `80`
- `TAIGA_HILLS`, weight `10`
- `JUNGLE`, weight `80`
- `JUNGLE_HILLS`, weight `10`
- `SWAMPLAND`, weight `60`
- `EXTREME_HILLS`, weight `50`
- `SAVANNA`, weight `50`
- `MESA`, weight `50`

The total source-confirmed roster weight is:

- `675`

This policy is therefore a direct non-runtime encoding of the default `CaveniaConfig.BIOMES` list rather than a guessed modern approximation.

## Exact Terrain/Filter Block Behavior Encoded

The pure policy keeps the same default terrain/filter rule confirmed in the legacy source:

- default terrain/filter block id is `minecraft:stone`

Every shipped default entry therefore keeps:

- terrain/filter block id `minecraft:stone`

This matches the inspected `CaveBiome#getTerrainBlock()` fallback and the shipped default `syncBiomesConfig()` entries, which do not introduce alternate default terrain/filter blocks.

## Exact Top Block Mapping Encoded

The pure policy pins the shipped default top-block mappings exactly:

- `OCEAN` -> `minecraft:gravel`
- `PLAINS` -> `minecraft:grass`
- `DESERT` -> `minecraft:sand`
- `DESERT_HILLS` -> `minecraft:sandstone`
- `FOREST` -> `minecraft:gravel`
- `FOREST_HILLS` -> `minecraft:gravel`
- `TAIGA` -> `minecraft:stone`
- `TAIGA_HILLS` -> `minecraft:stone`
- `JUNGLE` -> `minecraft:gravel`
- `JUNGLE_HILLS` -> `minecraft:gravel`
- `SWAMPLAND` -> `minecraft:grass`
- `EXTREME_HILLS` -> `minecraft:stone`
- `SAVANNA` -> `minecraft:grass`
- `MESA` -> `minecraft:red_sandstone`

The stone top values above are not guesses. They encode the source-confirmed legacy fallback where `topBlock` defaults to `terrainBlock`, and the default terrain/filter block remains `minecraft:stone`.

## Relationship To Legacy Contracts

This policy is a direct non-runtime follow-up to the earlier contract notes:

- `CaveniaConfig.BIOMES` stores weighted `CaveBiome` entries
- `CaveBiomeProvider` consumes that manager
- `ChunkGeneratorCavenia#replaceBiomeBlocks(...)` resolves `top` and `filter` from the matching `CaveBiome`
- `CaveniaTerrainGeneratorPolicy` already pins that biome top/filter replacement happens after optional cave carving and before VEINS mutation
- `CaveniaCaveCarverPolicy` now pins the source-confirmed gravel/water/air carve bands and the carve-before-top/filter ordering

So this policy is intentionally narrow, but it preserves the exact roster and replacement inputs that later active generator work must either match or explicitly approximate.

It also complements the already recommended:

- hybrid custom generator plus data-driven features

## What This MVP Still Does Not Add

This slice does not add:

- an active Cavenia biome source
- an active Cavenia chunk generator
- active Cavenia biome resources
- active Cavenia biome tags
- active Cavenia configured or placed features
- active Cavenia configured carvers
- active Cavenia noise settings, density functions or surface rules
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- an active Cavenia runtime `ServerLevel`
- active Cavenia access or teleport
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning
- `cavernreborn:caveman`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Why No Active Biome Source Or Generator Was Added

The current repository still lacks the runtime decisions needed before an honest active biome/generator path exists:

- modern biome-source strategy
- custom generator integration
- actual surface/top/filter replacement implementation
- cave carver interaction
- VEINS integration
- dimension type `height` and `logical_height` values

So this slice pins the source-confirmed biome/top/filter data without pretending the project already has a valid active `CaveBiomeProvider` equivalent or an active `ChunkGeneratorCavenia#replaceBiomeBlocks(...)` equivalent.

## Why No Dimension Or Worldgen Resources Were Added

This pure policy does not justify checked-in runtime resources.

Current Reborn still keeps:

- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no Cavenia biome-source resources
- no Cavenia biome tags
- no Cavenia worldgen resources
- no active Cavenia runtime level
- no active Cavenia runtime `ServerLevel`

The policy is intentionally pure Java only, so the biome/top/filter contract can be pinned before the project commits to any active runtime shape.

## Next Future Work

Before active biome/generator work starts, the project still needs:

- modern biome-source strategy
- custom generator integration
- surface/top/filter replacement implementation
- cave carver interaction design
- VEINS integration design
- dimension type height/logical-height decisions

This note is therefore a non-runtime biome/top-filter foundation only, not an active Cavenia biome-provider or terrain slice.
