# Cavenia Population Policy / Non-Runtime MVP

This note documents a pure non-runtime population policy for future active Cavenia generator work.

It builds on:

- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

## What This MVP Adds

Current Reborn now has a pure `core` population policy:

- `CaveniaPopulationPolicy`
- `CaveniaPopulationStep`
- `CaveniaPopulationBranch`

These types encode the source-confirmed legacy `ChunkGeneratorCavenia#populate(...)` defaults, branch rules and ordering without registering or activating configured features, placed features, biome modifiers, a chunk generator, a biome source or a dimension resource.

This is not an active Cavenia population implementation or generator implementation.

## Exact Population-Stage Defaults Added

`CaveniaPopulationPolicy` now pins:

- `CaveniaConfig.generateLakes = true`
- no separate inspected `generateCavenicShroom` config flag
- no separate inspected `generateFalls` config flag
- falls depend on `BiomeDecorator.generateFalls`

The pure policy also keeps the source-confirmed population-stage ordering labels from `ChunkGeneratorCavenia#populate(...)`:

1. `BlockFalling.fallInstantly = true`
2. pre chunk-populate event
3. lake branches
4. `DecorateBiomeEvent.Pre`
5. `OreGenEvent.Post`
6. `cavenic_shroom` branch
7. fall/liquid branches
8. `DecorateBiomeEvent.Post`
9. post chunk-populate event
10. `BlockFalling.fallInstantly = false`

That population stage remains later than final chunk construction and later than VEINS mutation in `CaveniaTerrainGeneratorPolicy`.

## Exact Lakes Behavior Added

The pure policy keeps lakes outside `CaveniaConfig.VEINS`.

The exact source-confirmed lake rules now encoded are:

- Nether lava lake:
  - generator `WorldGenLakes(Blocks.LAVA)`
  - biome condition `Type.NETHER`
  - event gate `EventType.LAVA`
  - chance `rand.nextInt(4) == 0`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(worldHeight - 32) + 16`
- Non-sandy water lake:
  - generator `WorldGenLakes(Blocks.WATER)`
  - biome condition `!Type.END && !Type.NETHER`
  - excluded biome trait `Type.SANDY`
  - event gate `EventType.LAKE`
  - chance `rand.nextInt(4) == 0`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(worldHeight - 16)`
- Non-end lava lake:
  - generator `WorldGenLakes(Blocks.LAVA)`
  - biome condition `!Type.END && !Type.NETHER`
  - event gate `EventType.LAVA`
  - chance `rand.nextInt(10) == 0`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(worldHeight / 2 - 16) + 32`

## Exact Falls / Liquids Behavior Added

Falls and liquids remain outside `CaveniaConfig.VEINS`.

The pure policy keeps the source-confirmed `BiomeDecorator.generateFalls` dependency and these exact branch rules:

- Nether lava fall:
  - generator `WorldGenLiquids(Blocks.FLOWING_LAVA)`
  - biome condition `Type.NETHER`
  - event gate `Decorate.EventType.LAKE_LAVA`
  - loop count `70`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(worldHeight - 22) + 20`
- Water-biome water fall:
  - generator `WorldGenLiquids(Blocks.FLOWING_WATER)`
  - biome condition `Type.WATER`
  - event gate `Decorate.EventType.LAKE_WATER`
  - loop count `50`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`
- Other-biome water fall:
  - generator `WorldGenLiquids(Blocks.FLOWING_WATER)`
  - biome condition `!Type.END && !Type.NETHER && !Type.WATER`
  - event gate `Decorate.EventType.LAKE_WATER`
  - loop count `50`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`
- Other-biome lava fall:
  - generator `WorldGenLiquids(Blocks.FLOWING_LAVA)`
  - biome condition `!Type.END && !Type.NETHER && !Type.WATER`
  - event gate `Decorate.EventType.LAKE_LAVA`
  - loop count `50`
  - x/z offset `rand.nextInt(16) + 8`
  - y formula `rand.nextInt(worldHeight / 2 - 32) + 20`

## Exact `cavenic_shroom` Behavior Added

`cavenic_shroom` remains outside `CaveniaConfig.VEINS`.

The pure policy keeps the exact inspected relationship:

- generator `WorldGenBush(CaveBlocks.CAVENIC_SHROOM)`
- event gate `Decorate.EventType.SHROOM`
- base local bias `0`
- mushroom-biome bonus `2`
- Nether-biome bonus `1`
- chance expression `rand.nextInt(6) <= i`
- placement origin `blockPos.add(16, 0, 16)`

So the source-confirmed success windows stay visible:

- mushroom biomes `3 / 6`
- Nether biomes `2 / 6`
- all other biomes `1 / 6`

## Relationship To Legacy Population Source

This policy is a direct non-runtime encoding of the inspected legacy population stack:

- `ChunkGeneratorCavenia#populate(...)`
- `CaveniaConfig.generateLakes`
- `WorldGenLakes`
- `WorldGenLiquids`
- `WorldGenBush(CAVENIC_SHROOM)`
- `BiomeDecorator.generateFalls`

It also aligns intentionally with the already-pinned pure policies:

- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`
- `CaveniaVeinsContentPolicy`

That ordering matters because population must happen after final chunk construction and after VEINS mutation, rather than being merged into the same generator-side mutation pass.

## What This MVP Still Does Not Add

This slice does not add:

- active Cavenia configured features
- active Cavenia placed features
- active Cavenia biome modifiers
- active Cavenia chunk generator
- active Cavenia biome source
- active Cavenia worldgen resources
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- an active Cavenia runtime level
- an active Cavenia runtime `ServerLevel`
- active Cavenia access or teleport
- active Cavenia spawning
- active crazy spawning
- fake normal `CAVERN` crazy spawning
- `cavernreborn:caveman`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

Current Reborn still keeps:

- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning

## Why No Active Configured/Placed Features Or Generator Were Added

The repository still lacks the runtime decisions needed before an honest active Cavenia population path exists:

- modern configured/placed feature mapping
- whether population stays generator-side or becomes hybrid/data-driven
- interaction with the future custom generator
- interaction with the future biome source
- interaction with the active `CAVERN` `cavenic_shroom` resources
- lake/liquid/fall feature mapping
- dimension type `height` and `logical_height` values

So this slice pins the source-confirmed population defaults and order without pretending the project already has an active configured-feature or generator implementation.

## Why No Dimension Or Worldgen Resources Were Added

This pure policy does not justify checked-in runtime resources.

Current Reborn still keeps:

- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no active Cavenia configured features
- no active Cavenia placed features
- no active Cavenia biome modifiers
- no active Cavenia biome-source resources
- no active Cavenia worldgen resources
- no active Cavenia runtime level

The policy is intentionally pure Java only, so the population contract can be pinned before the project commits to an active runtime shape.

## Next Future Work

Before active population/generator work starts, the project still needs:

- modern configured/placed feature mapping decisions
- a decision on whether population stays generator-side or becomes hybrid/data-driven
- interaction with the future custom generator
- future biome-source integration decisions
- interaction design for the active `CAVERN` `cavenic_shroom` resources
- lake/liquid/fall feature-mapping decisions
- dimension type height/logical-height decisions

This note is therefore a non-runtime population foundation only, not an active Cavenia configured-feature, placed-feature, biome-source or generator slice.
