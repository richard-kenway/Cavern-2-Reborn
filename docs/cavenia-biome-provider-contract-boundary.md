# Cavenia Biome Provider / Biome List Contract Boundary

This note documents only the future `Cavenia` biome-provider and biome-list contract.

It does not add:

- `data/cavernreborn/dimension/cavenia.json`
- `data/cavernreborn/dimension_type/cavenia.json`
- active Cavenia biome-source JSON
- active Cavenia biome tags
- active Cavenia worldgen resources
- active `cavernreborn:cavenia`

The broader provider/runtime boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The narrower dimension key/type boundary remains documented in:

- `docs/cavenia-dimension-key-type-contract-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy References Inspected

- `cavern.config.CaveniaConfig`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.world.WorldProviderCavern`
- `cavern.world.CaveBiomeProvider`
- `cavern.config.manager.CaveBiomeManager`
- `cavern.config.manager.CaveBiome`
- `cavern.config.CavernConfig`

## Exact Legacy Biome-Provider Input Found

`WorldProviderCavenia#createBiomeProvider()` returns:

- `new CaveBiomeProvider(world, CaveniaConfig.BIOMES)`

That means the active legacy Cavenia biome source is driven directly by:

- `CaveniaConfig.BIOMES`

It is not a fixed provider hardcoded to one biome.

It is also not a modern data-pack biome-source JSON.

## Exact Legacy `CaveniaConfig.BIOMES` Contract Found

`CaveniaConfig` declares:

- `public static final CaveBiomeManager BIOMES = new CaveBiomeManager()`

`syncBiomesConfig()` loads or regenerates a dedicated `cavenia/biomes` config file and fills that manager with weighted `CaveBiome` entries.

Each `CaveBiome` entry stores:

- a vanilla `Biome`
- a weighted spawn/selection value
- `terrainBlock`
- `topBlock`

The config serializer in `CavernConfig.generateBiomesConfig(...)` and `CavernConfig.addBiomesFromConfig(...)` confirms the stored per-entry fields are:

- `weight`
- `terrainBlock`
- `terrainBlockMeta`
- `topBlock`
- `topBlockMeta`

There is no separate Cavenia-specific stored `fillerBlock` contract.

The closest legacy equivalent is `terrainBlock`.

`CaveBiome#getTerrainBlock()` defaults to `minecraft:stone` when the field is `null`.

`CaveBiome#getTopBlock()` defaults to `terrainBlock` when the field is `null`.

## Exact Legacy Default Biome List And Weights Found

Default `syncBiomesConfig()` entries are exactly:

- `OCEAN` weight `15`
- `PLAINS` weight `100`
- `DESERT` weight `70`
- `DESERT_HILLS` weight `10`
- `FOREST` weight `80`
- `FOREST_HILLS` weight `10`
- `TAIGA` weight `80`
- `TAIGA_HILLS` weight `10`
- `JUNGLE` weight `80`
- `JUNGLE_HILLS` weight `10`
- `SWAMPLAND` weight `60`
- `EXTREME_HILLS` weight `50`
- `SAVANNA` weight `50`
- `MESA` weight `50`

The list uses reused vanilla biomes.

I did not find any dedicated legacy Cavenia-only biome classes in this contract.

## Exact Legacy Top / Terrain Override Behavior Found

The shipped default entries do not introduce any non-stone default `terrainBlock`.

In other words:

- the default terrain/filter layer stays stone everywhere unless the config is edited

The default `topBlock` overrides are:

- `OCEAN` -> `minecraft:gravel`
- `PLAINS` -> `minecraft:grass`
- `DESERT` -> `minecraft:sand`
- `DESERT_HILLS` -> `minecraft:sandstone`
- `FOREST` -> `minecraft:gravel`
- `FOREST_HILLS` -> `minecraft:gravel`
- `TAIGA` -> default top falls back to stone
- `TAIGA_HILLS` -> default top falls back to stone
- `JUNGLE` -> `minecraft:gravel`
- `JUNGLE_HILLS` -> `minecraft:gravel`
- `SWAMPLAND` -> `minecraft:grass`
- `EXTREME_HILLS` -> default top falls back to stone
- `SAVANNA` -> `minecraft:grass`
- `MESA` -> `minecraft:red_sandstone`

So the shipped legacy Cavenia biome contract is mostly:

- weighted vanilla biome identity
- a stone-default terrain filter
- selected exposed top-layer swaps

It is not a full custom-biome family with dedicated new biome ids.

## Exact Legacy Biome Selection Behavior Found

`CaveBiomeProvider` does not use climate parameters or a modern data-driven biome source.

The inspected behavior is:

- it owns a `CaveBiomeManager`
- `getRandomBiome()` chooses from `WeightedRandom.getRandomItem(world.rand, getCachedBiomes())`
- cached biome entries are rebuilt from `biomeManager.getCaveBiomes().values()` every `400` world ticks
- `makeLayers(seed)` builds `GenLayerCaveBiomes(() -> getRandomBiome(), 1L)`
- then applies five `GenLayerZoom` passes
- then applies `GenLayerVoronoiZoom`
- both layers are seeded from the world seed

That means biome selection depends on:

- the configured weighted biome-manager contents
- world seed
- the legacy GenLayer zoom/voronoi pipeline

It is not just a one-shot random biome pick per chunk.

## Exact `ChunkGeneratorCavenia` Relationship To Biome Entries Found

`ChunkGeneratorCavenia#generateChunk(...)` pulls:

- `biomesForGeneration = world.getBiomeProvider().getBiomes(...)`

Then `replaceBiomeBlocks(...)` does:

- `Biome biome = biomesForGeneration[x * 16 + z]`
- `CaveBiome caveBiome = CaveniaConfig.BIOMES.getCaveBiome(biome)`
- `top = caveBiome == null ? STONE : caveBiome.getTopBlock().getBlockState()`
- `filter = caveBiome == null ? top : caveBiome.getTerrainBlock().getBlockState()`

For each column:

- the top exposed solid block under air becomes `top`
- remaining stone blocks become `filter`

So the biome-manager contract is used directly for:

- exposed top-layer replacement
- full stone-to-filter replacement

It is not only cosmetic metadata.

The same chosen biome also affects population logic later through biome traits such as:

- `BiomeDictionary` checks
- `BiomeDecorator generateFalls`
- lake/water/lava/shroom branching

So a future biome-source decision also influences non-surface post-generation behavior, not only top-block visuals.

## Exact Current Reborn Reference Point

Current Reborn has:

- one active checked-in `cavern` dimension path
- checked-in `cavern` dimension and dimension-type JSONs
- checked-in `CAVERN` worldgen resources under `data/cavernreborn/worldgen/...`
- checked-in direct Cavenic spawn biome tags and biome modifiers for the active `CAVERN` path

Current Reborn does not have:

- no active `cavenia` dimension resources
- no active Cavenia biome-source resources
- no active Cavenia biome tags
- no active Cavenia worldgen resources
- no active Cavenia biome-provider code

## Why Reborn Does Not Add An Active Cavenia Biome Source Here

This increment does not add an active Cavenia biome source because the inspected legacy contract does not map cleanly to one isolated modern file.

An honest port still needs decisions about:

- whether to implement a weighted biome-source strategy directly
- whether to add custom biome-provider code
- whether to approximate the old weighted manager through a modern data-driven source
- how to express the old GenLayer zoom/voronoi behavior
- how to map top/terrain replacement into terrain surface-rules or generator code
- how to keep the biome contract consistent with future Cavenia terrain and VEIN/content work

Adding active resources now would suggest a real biome pipeline that does not exist yet.

## Future Modern Mapping Constraints

The inspected legacy biome contract suggests that future Reborn Cavenia work may need:

- a weighted biome-source strategy
- custom biome-provider code
- a data-driven approximation of the legacy weighted-biome manager
- a terrain surface-rules or generator-side top/filter mapping
- biome tags for downstream feature/spawn hooks
- a separate terrain/top/filter contract note if those layers become their own slice

## Unresolved Questions

This increment does not decide:

- the exact modern biome-source type
- the exact biome resource ids
- whether to reuse vanilla biome ids indirectly or define Cavenia-specific cave biomes
- the exact surface/top-block mapping strategy
- the exact terrain-generator dependency
- the exact interaction with `CaveniaConfig.VEINS`
- the exact relationship between biome identity and later population content in modern Reborn

## Explicit Non-Goals

- no active Cavenia biome provider
- no active Cavenia biome source
- no active Cavenia biome tags
- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no `CAVENIA_LOCATION`
- no `CAVENIA_LEVEL_KEY`
- no `WorldProviderCavenia` port
- no `ChunkGeneratorCavenia` port
- no terrain generation
- no surface rules
- no configured/placed features
- no `CaveniaConfig.VEINS` implementation
- no portal/teleport/access flow
- no active Cavenia spawning
- no active crazy spawning
