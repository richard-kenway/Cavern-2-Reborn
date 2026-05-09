# Cavenia Chunk Generator / Terrain Pipeline Contract Boundary

This note documents only the future `Cavenia` chunk-generator and terrain-pipeline contract.

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
- active Cavenia carvers
- active Cavenia surface rules
- active Cavenia structures, processor lists or template pools
- active Cavenia chunk-generator code
- active `cavernreborn:cavenia`

The broader provider/runtime boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The narrower future dimension key/type contract remains documented in:

- `docs/cavenia-dimension-key-type-contract-boundary.md`

The narrower future biome-provider contract remains documented in:

- `docs/cavenia-biome-provider-contract-boundary.md`

The narrower future VEINS/content-pipeline contract remains documented in:

- `docs/cavenia-veins-content-pipeline-contract-boundary.md`

The narrower future cave-carver contract remains documented in:

- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`

- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`

The separate mirage entry/access contract remains documented in:

- `docs/cavenia-mirage-entry-access-contract-boundary.md`

The separate spawn-provider / crazy-roster activation contract remains documented in:

- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy References Inspected

- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.gen.MapGenCaveniaCaves`
- `cavern.world.WorldProviderCavern`
- `cavern.config.CaveniaConfig`
- `cavern.world.CaveBiomeProvider`
- `cavern.world.CaveVeinProvider`
- `cavern.world.gen.VeinGenerator`
- `net.minecraft.world.gen.feature.WorldGenBush`
- `net.minecraft.world.gen.feature.WorldGenLakes`
- `net.minecraft.world.gen.feature.WorldGenLiquids`

## Exact Legacy `ChunkGeneratorCavenia` Constructor And Dependencies Found

`ChunkGeneratorCavenia` is not a static config shell.

The inspected constructor takes:

- `World world`

and stores:

- `this.world = world`
- `this.rand = new Random(world.getSeed())`
- `this.veinGenerator = new VeinGenerator(new CaveVeinProvider(world, () -> CaveniaConfig.autoVeins ? null : CaveniaConfig.VEINS))`

The inspected generator also owns dedicated generation helpers up front:

- `MapGenCaveniaCaves` as `caveGenerator`
- `WorldGenLakes(Blocks.WATER)` as `lakeWaterGen`
- `WorldGenLakes(Blocks.LAVA)` as `lakeLavaGen`
- `WorldGenBush(CaveBlocks.CAVENIC_SHROOM)` as `cavenicShroomGen`
- `WorldGenLiquids(Blocks.FLOWING_WATER)` as `liquidWaterGen`
- `WorldGenLiquids(Blocks.FLOWING_LAVA)` as `liquidLavaGen`

So the legacy terrain/content line is generator-owned code, not a loose collection of later decorators.

## Exact Legacy Base Fill Behavior Found

`setBlocksInChunk(...)` iterates every local column and fills the entire `ChunkPrimer` with stone:

- `x = 0..15`
- `z = 0..15`
- `y = 255..0`
- `primer.setBlockState(x, y, z, STONE)`

That means legacy Cavenia starts from:

- a full 256-block stone prism

not from a modern noise-shaped density field.

The base fill block is exactly:

- `Blocks.STONE`

## Exact Legacy World Height Role Found

The active provider contract feeds terrain height through:

- `WorldProviderCavenia#getWorldHeight() -> CaveniaConfig.worldHeight`
- `WorldProviderCavern#getActualHeight()`

The inspected default config value is:

- `CaveniaConfig.worldHeight = 128`

`replaceBiomeBlocks(...)` then derives:

- `int worldHeight = world.provider.getActualHeight()`
- `int blockHeight = worldHeight - 1`

With the shipped default `128`, that means:

- bottom bedrock sits at `y = 0`
- top bedrock sits at `y = 127`
- `y = 128..255` is cleared back to air

So the legacy terrain contract depends on a generator-owned height ceiling instead of a modern dimension-type JSON alone.

## Exact Legacy `generateChunk(...)` Order Found

`ChunkGeneratorCavenia#generateChunk(int chunkX, int chunkZ)` runs in this exact order:

1. `rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L)`
2. `biomesForGeneration = world.getBiomeProvider().getBiomes(...)`
3. construct a fresh `ChunkPrimer`
4. `setBlocksInChunk(primer)`
5. if `CaveniaConfig.generateCaves`, run `caveGenerator.generate(world, chunkX, chunkZ, primer)`
6. `replaceBiomeBlocks(chunkX, chunkZ, primer)`
7. `veinGenerator.generate(world, rand, biomesForGeneration, primer)`
8. `new Chunk(world, primer, chunkX, chunkZ)`
9. copy `biomesForGeneration` ids into `chunk.getBiomeArray()`

So the exact inspected primer-mutation order is:

- base stone fill
- optional cave carving
- biome top/filter replacement
- VEINS mutation
- final `Chunk` construction

That ordering is the main future terrain/generator contract.

## Exact Legacy `generateCaves` / `MapGenCaveniaCaves` Relationship Found

The cave-carving branch is gated directly by:

- `CaveniaConfig.generateCaves`

The inspected default config value is:

- `CaveniaConfig.generateCaves = true`

When enabled, the generator runs:

- `MapGenCaveniaCaves`

This is not a vanilla alias. The inspected cave map:

- extends `MapGenCavernCaves`
- starts tunnel origins around `blockY = 20 + rand.nextInt(5)`
- uses `world.getActualHeight()` as its height ceiling
- writes `gravel` when `y <= 2`
- writes water when `y - 1 < 10`
- writes air above that

So the old cave-carver contract is not simply:

- carve air out of stone

It also owns low-level fill behavior near the floor and water band.

## Exact Legacy `replaceBiomeBlocks(...)` Top / Filter Behavior Found

`replaceBiomeBlocks(...)` depends directly on:

- `biomesForGeneration`
- `CaveniaConfig.BIOMES`

For each local column it does:

- `Biome biome = biomesForGeneration[x * 16 + z]`
- `CaveBiome caveBiome = CaveniaConfig.BIOMES.getCaveBiome(biome)`
- `top = caveBiome == null ? STONE : caveBiome.getTopBlock().getBlockState()`
- `filter = caveBiome == null ? top : caveBiome.getTerrainBlock().getBlockState()`

Then it mutates the primer in this exact way:

- sets `BEDROCK` at `y = 0`
- sets `BEDROCK` at `y = blockHeight`
- for `y = 1..blockHeight - 1`
  - if the current block is solid and the block above is air, replace the current block with `top`
  - else if the current block is still `Blocks.STONE`, replace it with `filter`
- if `blockHeight < 255`, clear `y = blockHeight + 1 .. 255` to air

So the inspected terrain pipeline does not use a later surface-rule pass.

It performs generator-owned:

- exposed top replacement
- full stone-to-filter replacement
- top-height air clearing
- bedrock caps

before the chunk is finalized.

## Exact Legacy `CaveniaConfig.BIOMES` And `CaveniaConfig.VEINS` Relationship Found

The terrain generator consumes both config managers directly:

- `CaveniaConfig.BIOMES` supplies the biome roster and top/filter replacement contract
- `CaveniaConfig.VEINS` supplies the configured generator-side replacement contract through `CaveVeinProvider` and `VeinGenerator`

The exact order matters:

- `CaveniaConfig.BIOMES` runs before `veinGenerator.generate(...)`
- `CaveniaConfig.VEINS` runs after cave carving and after top/filter replacement

That means any future modern approximation has to decide how to preserve or intentionally approximate:

- biome-driven surface identity
- vein replacement order
- interaction between cave carving and later content mutation

## Exact Legacy Final Chunk / Structure / Creature Hooks Found

After primer mutation, the inspected generator:

- builds `new Chunk(world, primer, chunkX, chunkZ)`
- fills only the chunk biome byte array explicitly

The inspected generator does not add structure handling:

- `generateStructures(...) -> false`
- `isInsideStructure(...) -> false`
- `getNearestStructurePos(...) -> null`
- `recreateStructures(...)` is empty

It also returns:

- `getPossibleCreatures(...) -> Collections.emptyList()`

So creature spawning stays outside the chunk generator itself and is handled by the broader provider/runtime system.

## Exact Legacy Population / Lakes / Falls / Shroom Relationship Found

`populate(...)` is a separate post-chunk stage.

It sets:

- `BlockFalling.fallInstantly = true`

It looks up:

- the biome at `blockPos.add(16, 0, 16)`
- `BiomeDecorator decorator = biome.decorator`
- `int worldHeight = world.provider.getActualHeight()`

It also wraps population with:

- `ForgeEventFactory.onChunkPopulate(true, ...)`
- `DecorateBiomeEvent.Pre`
- `OreGenEvent.Post`
- `DecorateBiomeEvent.Post`
- `ForgeEventFactory.onChunkPopulate(false, ...)`

The inspected separate branches are:

### Lakes

`CaveniaConfig.generateLakes` is the dedicated config gate.

The inspected default config value is:

- `CaveniaConfig.generateLakes = true`

When enabled:

- NETHER biomes can place lava lakes with `1/4` chance at `y = rand.nextInt(worldHeight - 32) + 16`
- non-END, non-NETHER, non-SANDY biomes can place water lakes with `1/4` chance at `y = rand.nextInt(worldHeight - 16)`
- the same non-END, non-NETHER branch can place lava lakes with `1/10` chance at `y = rand.nextInt(worldHeight / 2 - 16) + 32`

### Cavenic Shroom

`cavenic_shroom` is not part of `CaveniaConfig.VEINS`.

It is generated through:

- `WorldGenBush(CAVENIC_SHROOM)`
- `TerrainGen.decorate(..., Decorate.EventType.SHROOM)`

The inspected chance modifier is biome-trait based:

- `+2` for `Type.MUSHROOM`
- `+1` for `Type.NETHER`

There is no separate `generateCavenicShroom` config flag in the inspected source.

### Falls

Falls are outside `VEINS` and depend on:

- `BiomeDecorator.generateFalls`
- `TerrainGen.decorate(...)`
- biome traits

The inspected counts are:

- NETHER biomes: `70` lava-liquid placements at `y = rand.nextInt(worldHeight - 22) + 20`
- WATER biomes: `50` water-liquid placements at `y = rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`
- other non-END biomes: `50` water-liquid placements with the same nested range plus `50` lava-liquid placements at `y = rand.nextInt(worldHeight / 2 - 32) + 20`

So the legacy terrain pipeline is broader than:

- a chunk primer
- a cave pass
- ore placement

It includes a distinct population contract.

## Exact Current Reborn Reference Point

Current Reborn already has:

- one active checked-in `CAVERN` dimension path
- one active checked-in `data/cavernreborn/dimension/cavern.json`
- one active checked-in `data/cavernreborn/dimension_type/cavern.json`
- data-driven `contained_caves` noise settings
- checked-in density functions
- checked-in configured and placed features for the active `CAVERN` path

Current Reborn does not have:

- no active `cavernreborn:cavenia`
- no active `ChunkGeneratorCavenia` equivalent
- no active Cavenia noise settings
- no active Cavenia density functions
- no active Cavenia configured carvers
- no active Cavenia surface rules
- no active Cavenia configured/placed features

Current active `CAVERN` special ore/content resources are also still:

- `CAVERN`-only distributions

They are not active Cavenia terrain parity.

## Why Reborn Does Not Add An Active Cavenia Chunk Generator Here

This increment does not add active Cavenia terrain code because the inspected legacy contract is not a small missing worldgen JSON.

It is a generator-owned pipeline that combines:

- full stone base fill
- custom cave carving
- biome-driven top/filter replacement
- generator-side VEINS mutation
- separate lakes/falls/shroom population
- provider-owned height rules

Adding active resources now would imply a real terrain pipeline that does not exist yet.

## Why Reborn Does Not Add Active Noise Settings / Carvers / Surface Rules Here

This increment also does not add active Cavenia:

- noise settings
- density functions
- configured carvers
- surface rules
- configured features
- placed features

because the old source does not map cleanly to one isolated data-pack layer.

The inspected legacy line may ultimately require:

- a custom chunk generator
- a custom cave carver
- a data-driven approximation around custom code
- generator-side replacement
- separate lake/fall/shroom feature slices
- strict feature ordering
- a world height contract that is distinct from the active `CAVERN` path

## Future Modern Mapping Constraints

The inspected legacy terrain contract suggests future Reborn Cavenia work may need:

- a custom chunk generator
- a custom cave carver
- a data-driven approximation only where it stays source-honest
- surface rules or equivalent top/filter mapping
- generator-side replacement for the VEINS stage
- separate lake/fall/shroom features or population hooks
- strict ordering between carve, top/filter replacement and VEINS mutation
- an explicit world-height contract

## Unresolved Questions

This increment does not resolve:

- the exact custom generator architecture
- the exact `MapGenCaveniaCaves` modern mapping
- the exact future terrain height
- the exact surface/top/filter strategy
- the exact `CaveniaConfig.VEINS` mapping
- the exact lake/fall/shroom feature mapping
- the exact relationship to future active key/type work
- the exact relationship to future biome-provider work
- the exact relationship to future entry/access work

## Explicit Non-Goals

- no active Cavenia chunk generator
- no active Cavenia terrain generation
- no active `MapGenCaveniaCaves`
- no active Cavenia noise settings
- no active Cavenia carvers
- no active Cavenia density functions
- no active Cavenia surface rules
- no active Cavenia VEINS implementation
- no active Cavenia configured features
- no active Cavenia placed features
- no active Cavenia biome modifiers
- no active Cavenia biome provider
- no active Cavenia biome source
- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no `CAVENIA_LOCATION`
- no `CAVENIA_LEVEL_KEY`
- no `WorldProviderCavenia` port
- no lakes/falls/shroom generation
- no portal/teleport/access flow
- no active Cavenia spawning
- no active crazy spawning
