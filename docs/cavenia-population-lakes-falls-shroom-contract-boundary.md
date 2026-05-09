# Cavenia Population / Lakes / Falls / Cavenic Shroom Contract Boundary

This note documents only the future post-terrain population contract around legacy `ChunkGeneratorCavenia#populate(...)`.

The consolidated readiness and future implementation order are documented separately in:

- `docs/cavenia-active-foundation-readiness-plan.md`

It does not add:

- `data/cavernreborn/dimension/cavenia.json`
- `data/cavernreborn/dimension_type/cavenia.json`
- active Cavenia biome-source JSON
- active Cavenia biome tags
- active Cavenia configured features
- active Cavenia placed features
- active Cavenia biome modifiers
- active Cavenia noise settings
- active Cavenia density functions
- active Cavenia configured carvers
- active Cavenia surface rules
- active Cavenia chunk-generator code
- active Cavenia populate/decorate hooks
- active Cavenia lakes, falls or shroom features
- active `cavernreborn:cavenia`

The broader provider/runtime boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The narrower future dimension key/type contract remains documented in:

- `docs/cavenia-dimension-key-type-contract-boundary.md`

The narrower future biome-provider contract remains documented in:

- `docs/cavenia-biome-provider-contract-boundary.md`

The narrower future VEINS/content-pipeline contract remains documented in:

- `docs/cavenia-veins-content-pipeline-contract-boundary.md`

The broader terrain/generator contract remains documented in:

- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`

The narrower future cave-carver contract remains documented in:

- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`

The separate mirage entry/access contract remains documented in:

- `docs/cavenia-mirage-entry-access-contract-boundary.md`

The separate spawn-provider / crazy-roster activation contract remains documented in:

- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

The active bounded Reborn `CAVERN` shroom slice remains documented in:

- `docs/cavenic-shroom-mvp.md`

## Legacy References Inspected

- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.WorldProviderCavern`
- `cavern.world.gen.MapGenCaveniaCaves`
- `cavern.config.CaveniaConfig`
- `cavern.block.CaveBlocks`
- `net.minecraft.world.biome.BiomeDecorator`
- `net.minecraft.world.gen.feature.WorldGenBush`
- `net.minecraft.world.gen.feature.WorldGenLakes`
- `net.minecraft.world.gen.feature.WorldGenLiquids`
- `net.minecraftforge.event.ForgeEventFactory`
- `net.minecraftforge.event.terraingen.DecorateBiomeEvent`
- `net.minecraftforge.event.terraingen.OreGenEvent`
- `net.minecraftforge.event.terraingen.TerrainGen`

## Exact `ChunkGeneratorCavenia#populate(...)` Ordering Found

`populate(...)` is a separate post-chunk stage after `generateChunk(...)`.

The exact inspected order is:

1. `BlockFalling.fallInstantly = true`
2. derive:
   - `BlockPos blockPos = new BlockPos(worldX, 0, worldZ)`
   - `ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ)`
   - `Biome biome = world.getBiome(blockPos.add(16, 0, 16))`
   - `BiomeDecorator decorator = biome.decorator`
   - `int worldHeight = world.provider.getActualHeight()`
3. `ForgeEventFactory.onChunkPopulate(true, this, world, rand, chunkX, chunkZ, false)`
4. lake generation branches
5. `MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rand, chunkPos))`
6. `MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(world, rand, blockPos))`
7. `WorldGenBush(CAVENIC_SHROOM)` branch behind `TerrainGen.decorate(..., Decorate.EventType.SHROOM)`
8. fall/liquid branches behind `decorator.generateFalls`
9. `MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, chunkPos))`
10. `ForgeEventFactory.onChunkPopulate(false, this, world, rand, chunkX, chunkZ, false)`
11. `BlockFalling.fallInstantly = false`

This population stage does not call `CaveniaConfig.VEINS`.

It runs after:

- base stone fill
- optional `MapGenCaveniaCaves`
- biome top/filter replacement from `CaveniaConfig.BIOMES`
- generator-side `VEINS` mutation

So the inspected Cavenia population contract is a later decorate/populate layer on top of already-built terrain.

## Exact Config And Height Inputs Found

The inspected default config gate is:

- `CaveniaConfig.generateLakes = true`

The inspected height source is:

- `int worldHeight = world.provider.getActualHeight()`

That means lake and fall placement reads the provider/world runtime height, not a separate local constant in `populate(...)`.

The same provider path ultimately depends on:

- `WorldProviderCavenia#getWorldHeight() -> CaveniaConfig.worldHeight`

There is no separate inspected config flag named:

- `generateCavenicShroom`
- `generateFalls`

So only lakes have a dedicated Cavenia config gate in the inspected source.

## Exact Legacy Lake Generation Behavior Found

Lakes are outside `CaveniaConfig.VEINS`.

They run before `DecorateBiomeEvent.Pre`.

### Nether Biomes

When `BiomeDictionary.hasType(biome, Type.NETHER)`:

- gate: `CaveniaConfig.generateLakes`
- chance: `rand.nextInt(4) == 0`
- event gate: `TerrainGen.populate(..., EventType.LAVA)`
- placement offsets:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(worldHeight - 32) + 16`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLakes(Blocks.LAVA)`

No water-lake branch exists in this Nether-biome path.

### Non-End, Non-Nether Biomes

When the biome is not `Type.END` and not `Type.NETHER`, the inspected source checks `CaveniaConfig.generateLakes` and then runs two separate branches.

Water lake branch:

- biome exclusion: `!BiomeDictionary.hasType(biome, Type.SANDY)`
- chance: `rand.nextInt(4) == 0`
- event gate: `TerrainGen.populate(..., EventType.LAKE)`
- placement offsets:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(worldHeight - 16)`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLakes(Blocks.WATER)`

Lava lake branch:

- chance: `rand.nextInt(10) == 0`
- event gate: `TerrainGen.populate(..., EventType.LAVA)`
- placement offsets:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(worldHeight / 2 - 16) + 32`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLakes(Blocks.LAVA)`

### End Biomes

When `BiomeDictionary.hasType(biome, Type.END)`:

- the inspected `populate(...)` method runs no lake branch at all

## Exact Legacy `cavenic_shroom` Generation Behavior Found

`cavenic_shroom` is outside `CaveniaConfig.VEINS`.

The inspected generator instance is exact:

- `WorldGenBush(CaveBlocks.CAVENIC_SHROOM)`

It runs only when:

- `TerrainGen.decorate(world, rand, chunkPos, Decorate.EventType.SHROOM)` returns true

The inspected local chance bias is:

- start `int i = 0`
- if `BiomeDictionary.hasType(biome, Type.MUSHROOM)`, then `i += 2`
- else if `BiomeDictionary.hasType(biome, Type.NETHER)`, then `i += 1`

Generation then happens when:

- `rand.nextInt(6) <= i`

So the inspected success windows are:

- mushroom biomes: `3 / 6`
- Nether biomes: `2 / 6`
- all other biomes: `1 / 6`

The inspected placement call is:

- `cavenicShroomGen.generate(world, rand, blockPos.add(16, 0, 16))`

So this branch hands the generator the chunk-centerish origin offset, rather than a separately randomized per-attempt x/y/z triple inside `populate(...)`.

No separate inspected config flag exists for enabling or disabling `cavenic_shroom` generation on Cavenia.

## Exact Legacy Fall / Liquid Generation Behavior Found

Falls are outside `CaveniaConfig.VEINS`.

They run only when:

- `decorator.generateFalls`

This stage is after:

- `DecorateBiomeEvent.Pre`
- `OreGenEvent.Post`
- the shroom branch

### Nether Biomes

When `BiomeDictionary.hasType(biome, Type.NETHER)`:

- event gate: `TerrainGen.decorate(..., Decorate.EventType.LAKE_LAVA)`
- loop count: `70`
- offsets per attempt:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(worldHeight - 22) + 20`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLiquids(Blocks.FLOWING_LAVA)`

### Water Biomes

When `BiomeDictionary.hasType(biome, Type.WATER)`:

- event gate: `TerrainGen.decorate(..., Decorate.EventType.LAKE_WATER)`
- loop count: `50`
- offsets per attempt:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLiquids(Blocks.FLOWING_WATER)`

### Other Non-End Biomes

When the biome is neither `Type.NETHER`, nor `Type.WATER`, nor `Type.END`:

Water-fall branch:

- event gate: `TerrainGen.decorate(..., Decorate.EventType.LAKE_WATER)`
- loop count: `50`
- offsets per attempt:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLiquids(Blocks.FLOWING_WATER)`

Lava-fall branch:

- event gate: `TerrainGen.decorate(..., Decorate.EventType.LAKE_LAVA)`
- loop count: `50`
- offsets per attempt:
  - `x = rand.nextInt(16) + 8`
  - `y = rand.nextInt(worldHeight / 2 - 32) + 20`
  - `z = rand.nextInt(16) + 8`
- generator:
  - `WorldGenLiquids(Blocks.FLOWING_LAVA)`

### End Biomes

When `BiomeDictionary.hasType(biome, Type.END)`:

- the inspected fall/liquid block does not run

## Exact Relationship To Current Reborn Found

Current Reborn already has an active bounded `CAVERN` shroom slice documented in:

- `docs/cavenic-shroom-mvp.md`

The checked-in active Reborn worldgen keys are:

- `cavernreborn:cavenic_shroom_patch`
- `cavernreborn:cavern_cavenic_shroom_patch`

Those resources are part of the active `CAVERN` path only.

They are not evidence of:

- an active Cavenia shroom-population contract
- an active Cavenia lake/fall pipeline
- an active `cavernreborn:cavenia` population stage

Current Reborn still has:

- no active `cavenia` dimension
- no active Cavenia populate/decorate code
- no active Cavenia lake configured/placed features
- no active Cavenia fall configured/placed features
- no active Cavenia shroom configured/placed features
- no active Cavenia biome modifiers

## Future Modern Mapping Constraints

The inspected source does not collapse cleanly into one modern data file.

A future honest port could require some mix of:

- configured/placed features for Cavenia-specific lakes
- configured/placed features for Cavenia-specific shroom placement
- biome tags or equivalent biome-classification inputs for `Type.NETHER`, `Type.END`, `Type.SANDY`, `Type.WATER` and `Type.MUSHROOM`
- custom population hooks or generator-owned post-terrain logic
- strict feature ordering relative to future Cavenia terrain, cave-carver and `VEINS` work
- separate bounded slices for lakes, falls and shroom population instead of one broad port

Legacy `BlockFalling.fallInstantly` toggling and Forge populate/decorate event ordering are also not a direct one-to-one modern datapack contract.

## Why Reborn Still Defers This

This increment does not add active Cavenia population resources because the inspected behavior depends on multiple missing foundations together:

- active Cavenia key/type resources
- active Cavenia biome-provider work
- active Cavenia chunk-generator and cave-carver work
- active Cavenia `VEINS`/content ordering
- active Cavenia biome-trait classification or equivalent tags

Adding fake Cavenia lake/fall/shroom resources now would be misleading, and reusing the current active `CAVERN` shroom feature path would blur two different contracts.

## Future Work Split

If Cavenia becomes active later, population work should probably stay split into narrow follow-ups:

1. active Cavenia lake generation contract
2. active Cavenia fall/liquid generation contract
3. active Cavenia shroom generation contract
4. modern biome-trait/tag contract for those branches
5. population ordering integration with future terrain/carver/VEINS work
