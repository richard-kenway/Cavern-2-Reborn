# Cavenia Cave Carver / MapGenCaveniaCaves Contract Boundary

This note documents only the future `Cavenia` cave-carver contract around legacy `MapGenCaveniaCaves`.

The consolidated readiness and future implementation order are documented separately in:

- `docs/cavenia-active-foundation-readiness-plan.md`

The narrower active-foundation implementation-decision spike is documented separately in:

- `docs/cavenia-active-foundation-technical-spike.md`

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

The narrower future population/lakes/falls/shroom contract remains documented in:

- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`

The separate mirage entry/access contract remains documented in:

- `docs/cavenia-mirage-entry-access-contract-boundary.md`

The separate spawn-provider / crazy-roster activation contract remains documented in:

- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy References Inspected

- `cavern.world.gen.MapGenCaveniaCaves`
- `cavern.world.gen.MapGenCavernCaves`
- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.WorldProviderCavern`
- `cavern.config.CaveniaConfig`
- `net.minecraft.world.gen.MapGenCaves`

## Exact Legacy Inheritance Relationship Found

`MapGenCaveniaCaves` is not a standalone cave generator family.

The inspected class declaration is:

- `public class MapGenCaveniaCaves extends MapGenCavernCaves`

`MapGenCavernCaves` in turn extends:

- `MapGenCaves`

So the legacy Cavenia cave line is:

- vanilla `MapGenCaves`
- narrowed through shared Cavern cave overrides in `MapGenCavernCaves`
- then retuned again by `MapGenCaveniaCaves`

The inspected `MapGenCaveniaCaves` class overrides only:

- `addTunnel(...)`
- `recursiveGenerate(...)`
- `digBlock(...)`

It does not override:

- `addRoom(...)`
- the outer `generate(world, chunkX, chunkZ, primer)` entrypoint inherited from the mapgen stack

So room creation still comes from the inherited vanilla cave stack, while tunnel shape, origin selection and block replacement become Cavenia-specific.

## Exact `ChunkGeneratorCavenia` Invocation Found

The inspected terrain order in `ChunkGeneratorCavenia#generateChunk(int chunkX, int chunkZ)` is:

1. `setBlocksInChunk(primer)`
2. `if (CaveniaConfig.generateCaves) { caveGenerator.generate(world, chunkX, chunkZ, primer); }`
3. `replaceBiomeBlocks(chunkX, chunkZ, primer)`
4. `veinGenerator.generate(world, rand, biomesForGeneration, primer)`
5. final `Chunk` construction

That means the cave-carver contract runs:

- after the full stone base fill
- before biome top/filter replacement
- before `CaveniaConfig.VEINS`

The gate is exact:

- `CaveniaConfig.generateCaves`

The inspected default config value is:

- `CaveniaConfig.generateCaves = true`

When that flag is `false`, the entire `MapGenCaveniaCaves` branch is skipped.

## Exact Shared `MapGenCavernCaves` Behavior Found

The inspected shared Cavern cave base already changes normal vanilla cave behavior.

`MapGenCavernCaves`:

- uses `world.getActualHeight()` inside `addTunnel(...)`
- uses `world.provider.getActualHeight()` inside `recursiveGenerate(...)`
- branches tunnels at one random intermediate height:
  - `int nextInterHeight = random.nextInt(targetY / 2) + targetY / 4`
  - when `currentY == nextInterHeight && scale > 1.0F && targetY > 0`
- creates two child tunnels on that branch:
  - one at `leftRightRadian - PI / 2`
  - one at `leftRightRadian + PI / 2`
- carves only when `createFinalRoom || random.nextInt(4) != 0`
- clamps carved Y bounds to:
  - `yLow >= 1`
  - `yHigh <= worldHeight - 4`
- replaces dug blocks with:
  - lava when `y - 1 < 10`
  - air otherwise

Its shared chunk-level tunnel origin logic is:

- `int chance = rand.nextInt(rand.nextInt(rand.nextInt(25) + 1) + 1)`
- unless `rand.nextInt(4) == 0`, chance is forced to `0`
- tunnel origin Y is:
  - `rand.nextInt(rand.nextInt(worldHeight - 8) + 8)`
- optional room creation is:
  - `if (rand.nextInt(4) == 0) addRoom(...)`
- default tunnel scale is:
  - `rand.nextFloat() * 3.0F + rand.nextFloat()`
- rare wider tunnels multiply scale when:
  - `rand.nextInt(8) == 0`

So `MapGenCaveniaCaves` should be treated as a retuned variant on top of an already-custom shared Cavern cave base, not as a direct vanilla default.

## Exact `MapGenCaveniaCaves` Tunnel And Count Behavior Found

The inspected Cavenia override keeps the same overall cave-stack shape, but narrows origin height and widens tunnel rooms.

The exact per-chunk cave-system count gate is:

- `int chance = rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1)`
- unless `rand.nextInt(3) == 0`, chance is forced to `0`

So compared with shared `MapGenCavernCaves`, Cavenia:

- uses a smaller nested random bound of `10` instead of `25`
- but allows cave generation in `1 / 3` chunks instead of `1 / 4`

For each generated system:

- `blockX = chunkX * 16 + rand.nextInt(16)`
- `blockY = 20 + rand.nextInt(5)`
- `blockZ = chunkZ * 16 + rand.nextInt(16)`

So the inspected tunnel origin band is exact:

- `blockY = 20 + rand.nextInt(5)`

This is much narrower than the shared `MapGenCavernCaves` vertical origin sampling.

Optional room creation is:

- `if (rand.nextInt(5) == 0) addRoom(...)`

and then:

- `count += rand.nextInt(5)`

Each tunnel instance uses:

- `leftRightRadian = rand.nextFloat() * PI * 2.0F`
- `upDownRadian = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F`
- `scale = rand.nextFloat() * 2.5F + rand.nextFloat()`

Rare wider tunnels multiply scale when:

- `rand.nextInt(6) == 0`

The inspected Cavenia tunnel call always uses:

- `scaleHeight = 1.35D`

instead of the shared Cavern value:

- `scaleHeight = 1.15D`

## Exact `addTunnel(...)` Room Shape And Branching Found

Inside `MapGenCaveniaCaves#addTunnel(...)`, the inspected world-height ceiling is:

- `int worldHeight = world.getActualHeight()`

So the carver reads actual runtime height from the provider/world path, not directly from `CaveniaConfig.worldHeight`.

The final ceiling still depends on:

- `WorldProviderCavenia#getWorldHeight() -> CaveniaConfig.worldHeight`
- `WorldProviderCavern#getActualHeight()`

The inspected Cavenia room-shape formulas are:

- `double roomBase = MathHelper.sin(currentY * PI / targetY) * scale`
- `double roomWidth = 5.0D + roomBase`
- `double roomHeight = 3.75D + roomBase * scaleHeight`

Compared with shared `MapGenCavernCaves`, Cavenia explicitly widens the tunnel body because the shared base uses:

- `roomWidth = 2.0D + sin(...) * scale`
- `roomHeight = roomWidth * scaleHeight`

The inspected Cavenia dampening also differs:

- `boolean chance = random.nextInt(6) == 0`
- if true: `upDownRadian *= 0.92F`
- else: `upDownRadian *= 0.7F`

The shared Cavern base instead uses:

- `boolean chance = random.nextInt(10) == 0`
- if true: `upDownRadian *= 0.95F`
- else: `upDownRadian *= 0.8F`

The branch split condition remains structurally the same:

- `currentY == nextInterHeight`
- `scale > 1.0F`
- `targetY > 0`

and still creates two child tunnels at left/right quarter-turn offsets.

The Cavenia distance cutoff is slightly tighter:

- `double maxDistance = scale + 18.0F`

while the shared Cavern base uses:

- `scale + 20.0F`

## Exact Gravel / Water / Air Replacement Behavior Found

`MapGenCaveniaCaves#digBlock(...)` is the main Cavenia-specific block-replacement contract.

The inspected logic is exact:

- if `y <= 2`, write `Blocks.GRAVEL`
- else if `y - 1 < 10`, write `Blocks.WATER`
- else write `Blocks.AIR`

So the source-confirmed low-level thresholds are:

- gravel threshold: `y <= 2`
- water threshold: `y - 1 < 10`
- air replacement above that low band

This means the inspected Cavenia cave line is not a normal vanilla-style air carve and not the shared Cavern lava-bottom carve either.

Compared with shared `MapGenCavernCaves`:

- shared Cavern writes lava when `y - 1 < 10`
- Cavenia writes gravel first at the bottom
- Cavenia writes water in the low band above that
- Cavenia writes air above the low water band

## Exact Height And Vertical Limit Findings

The inspected carver uses actual runtime height in multiple places:

- `world.getActualHeight()` inside `addTunnel(...)`
- `world.provider.getActualHeight()` in shared `MapGenCavernCaves#recursiveGenerate(...)`

The carved Y window is clamped by:

- `yLow = max(floor(blockY - roomHeight) - 1, 1)`
- `yHigh = min(floor(blockY + roomHeight) + 1, worldHeight - 4)`

So the inspected carver:

- never targets `y <= 0`
- never reaches the topmost few layers
- does not directly rewrite the later bedrock cap at `y = 127`

With the shipped default:

- `CaveniaConfig.worldHeight = 128`

the runtime ceiling is still governed by the provider's actual-height path and not by a hardcoded literal inside `MapGenCaveniaCaves`.

## Exact Ordering Consequences Found

Because `MapGenCaveniaCaves` runs before `replaceBiomeBlocks(...)`:

- carved air/water/gravel shapes become the exposed cavities that later receive biome `topBlock` replacement
- remaining solid `Blocks.STONE` after carving can still be rewritten to biome `terrainBlock`

Because `MapGenCaveniaCaves` also runs before `veinGenerator.generate(...)`:

- VEINS can still mutate surviving post-carve, post-surface primer blocks

So the ordering matters in two separate ways:

- top/filter replacement sees the carved result
- VEINS sees the carved and top/filter-replaced result

## Why Reborn Does Not Add An Active Cavenia Carver Now

Current Reborn still has:

- no active `cavernreborn:cavenia`
- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- inert `CAVENIA_LOCATION` only
- inert `CAVENIA_LEVEL_KEY` only
- no active `ChunkGeneratorCavenia`
- no active `MapGenCaveniaCaves`
- no active `MapGenCavernCaves`
- no active Cavenia biome source
- no active Cavenia VEINS pipeline

Adding only `worldgen/configured_carver/cavenia*.json` now would be misleading because the inspected legacy behavior depends on:

- the Cavenia-specific actual-height contract
- the shared Cavern cave superclass behavior
- the Cavenia-specific gravel/water/air replacement rules
- the exact ordering before top/filter replacement
- the exact ordering before VEINS mutation

Those are not isolated data points that can be restored honestly with a single checked-in JSON resource.

## Future Modern Mapping Constraints

The inspected source suggests that future modern parity will likely require one of:

- a custom carver
- generator-side cave mutation inside a custom chunk generator
- a deliberate approximation that documents the lost low-level gravel/water ordering

Vanilla configured-carver data alone is likely not enough because:

- gravel/water/air replacement is not a normal vanilla configured-carver default
- the tunnel origin band is tightly pinned around `y = 20..24`
- the widened Cavenia tunnel/body formulas are custom
- ordering relative to biome top/filter replacement and VEINS is important

The future mapping will also need to decide:

- whether the shared `MapGenCavernCaves` behavior becomes its own modern contract
- whether the low water band stays a real carver output or moves into generator-side post-processing
- whether gravel-at-the-floor remains part of the carver or becomes a separate terrain rule
- how the actual-height contract maps to a future active `cavenia` key/type plus chunk-generator stack

## Reborn Boundary

Current Reborn intentionally keeps this line inactive:

- no active Cavenia cave carver
- no active `MapGenCaveniaCaves`
- no active `MapGenCavernCaves`
- no active Cavenia configured carver resources
- no active Cavenia noise settings
- no active Cavenia density functions
- no active Cavenia surface rules
- no active Cavenia chunk generator
- no active Cavenia VEINS implementation
- no active Cavenia biome source
- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- inert `CAVENIA_LOCATION` only
- inert `CAVENIA_LEVEL_KEY` only

That is intentional until a real Cavenia chunk-generator, biome-provider and content pipeline implementation is planned together.
