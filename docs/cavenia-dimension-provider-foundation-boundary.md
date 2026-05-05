# Cavenia Dimension / Provider Foundation Boundary

This note documents the inspected legacy `Cavenia` dimension/provider stack and the current Reborn boundary.

It does not add an active `cavenia` dimension, a provider port, a chunk generator port, portals, teleports or crazy natural spawning.

The narrower future key/type contract is documented separately in:

- `docs/cavenia-dimension-key-type-contract-boundary.md`

## Legacy References Inspected

- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.config.CaveniaConfig`
- `cavern.world.CaveDimensions`
- `cavern.world.WorldProviderCavern`
- `cavern.entity.CaveEntityRegistry`
- `cavern.item.ItemMirageBook`
- `cavern.handler.api.DimensionHandler`

## Exact Legacy Registration Path Found

Legacy `Cavenia` is not just a spawn rule or a biome list.

It is registered as a full mirage-world dimension in `cavern.world.CaveDimensions`:

- `CAVENIA = register("cavenia", CaveniaConfig.dimensionId, WorldProviderCavenia.class)`
- `register(...)` calls `DimensionType.register(...)`
- `register(...)` then calls `DimensionManager.registerDimension(id, type)`

The default legacy config value is:

- `CaveniaConfig.dimensionId = -54`

The same legacy registration class also registers other mirage worlds:

- `caveland`
- `frost_mountains`
- `wide_desert`
- `the_void`
- `dark_forest`
- `crown_cliffs`
- `skyland`

That means `Cavenia` was part of a broader mirage-world family, not an isolated one-off dimension key.

## Exact Legacy Provider Behavior Found

`WorldProviderCavenia` extends `WorldProviderCavern`.

The inspected `WorldProviderCavenia` overrides only a small set of provider hooks directly:

- `createBiomeProvider()` -> `new CaveBiomeProvider(world, CaveniaConfig.BIOMES)`
- `createChunkGenerator()` -> `new ChunkGeneratorCavenia(world)`
- `getDimensionType()` -> `CaveDimensions.CAVENIA`
- `getWorldHeight()` -> `CaveniaConfig.worldHeight`
- `getMonsterSpawn()` -> `CaveniaConfig.monsterSpawn`
- `getBrightness()` -> `CaveniaConfig.caveBrightness`
- `getMusicType()` -> `CaveMusics.CAVENIA`
- `createSpawnCreature(...)` -> special normal/crazy roster switch

Everything else comes from the inherited `WorldProviderCavern` base.

That inherited base keeps the dimension on the cave-provider path, including:

- `hasSkyLight = false`
- `isSurfaceWorld() == false`
- dark fog and sky color
- `canRespawnHere() == false`
- `canSleepAt(...) == ALLOW`
- `canDoLightning(...) == false`
- `canDoRainSnowIce(...) == false`
- `getSpawnPoint()` and `getRespawnDimension(...)` using the cave portal list capability
- `onWorldUpdateEntities()` using the provider-owned hostile-mob spawn loop

So even though `WorldProviderCavenia` itself is small, it sits on top of the older custom `WorldProviderCavern` provider/runtime framework rather than a data-only dimension definition.

## Exact Legacy Cavenia Config Found

The inspected `CaveniaConfig` defines a dedicated config namespace, not a single toggle.

Defaults found in `syncConfig()`:

- `dimensionId = -54`
- `worldHeight = 128`
- `generateCaves = true`
- `generateLakes = true`
- `monsterSpawn = 200`
- `crazySpawnChance = 0.1D`
- `caveBrightness = 0.125D`
- `autoVeins = false`

It also defines two separate configurable managers:

- `CaveniaConfig.BIOMES`
- `CaveniaConfig.VEINS`

Default configured biome set found in `syncBiomesConfig()`:

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

Those biome entries also carry dimension-specific top/terrain block overrides in several cases.

Default configured vein/content set found in `syncVeinsConfig()` includes:

- granite, diorite and andesite stone variants
- coal and iron
- `aquamarine_ore`
- `magnite_ore`
- `randomite_ore`
- `hexcite_ore`
- `fissured_stone`
- dirt, gravel and sand

This means a real Cavenia port is tied to its own biome manager and vein/content manager, not only to a dimension id and a monster list.

## Exact Legacy Chunk/Terrain Dependency Found

`WorldProviderCavenia#createChunkGenerator()` returns `ChunkGeneratorCavenia`.

The inspected `ChunkGeneratorCavenia` is not a thin alias to the normal Cavern generator. It has its own population and terrain rules:

- fills chunks with solid stone first
- optionally runs `MapGenCaveniaCaves`
- replaces top/filter blocks from `CaveniaConfig.BIOMES`
- generates veins through `CaveniaConfig.VEINS`
- optionally generates water/lava lakes
- generates `CAVENIC_SHROOM`
- generates water/lava falls based on biome traits

So an honest Cavenia port needs:

- a biome-provider story
- a terrain-generator story
- a cave-carver/cave-map story
- a configured-vein/content story
- a post-generation population story

before a `cavenia` dimension key would represent anything real.

## Exact Legacy Crazy Spawn Relationship Found

`WorldProviderCavenia#createSpawnCreature(...)` is the provider hook that ties Cavenia to the crazy roster.

The inspected behavior is:

- starts from `CaveEntityRegistry.SPAWNS`
- reads `double chance = CaveniaConfig.crazySpawnChance`
- when the chance roll passes, computes a nearby exclusion radius from that chance
- switches to `CaveEntityRegistry.CRAZY_SPAWNS` only when the nearby search finds no `ICavenicMob && !entity.isNonBoss()`
- then chooses from the selected weighted list

That means active crazy spawning is not separable from the missing Cavenia provider foundation.

The crazy-roster details are documented separately in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy Entry / Access Relationship Found

The inspected references also show that legacy `Cavenia` belonged to the mirage-world access path:

- `ItemMirageBook.EnumType.CAVENIA` maps to `CaveDimensions.CAVENIA`
- `DimensionHandler#getCavenia()` exposes `CaveDimensions.CAVENIA`

I did not inspect a standalone `CaveniaTeleporter` class because the inspected references already show that Cavenia was integrated into the broader mirage-world access model rather than a tiny isolated entry hook.

That means future Cavenia work is not only about worldgen. It also needs a user-facing entry/access decision that is honest to the old mirage-world family.

## Current Reborn Architecture

Current Reborn has one active checked-in dimension/content path:

- `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/dimension/CavernNeoForgeDimensions.java`
- `app-neoforge/src/main/resources/data/cavernreborn/dimension/cavern.json`
- `app-neoforge/src/main/resources/data/cavernreborn/dimension_type/cavern.json`
- `app-neoforge/src/main/resources/data/cavernreborn/worldgen/...`

Current Reborn `CavernNeoForgeDimensions` exposes:

- `CAVERN_LOCATION`
- `CAVERN_LEVEL_KEY`

It does not expose:

- `CAVENIA_LOCATION`
- `CAVENIA_LEVEL_KEY`
- any placeholder `cavenia` dimension key

Current Reborn natural-spawn architecture is also narrower than the old provider model:

- direct Cavenic mobs use `RegisterSpawnPlacementsEvent`
- direct Cavenic mobs use biome modifiers and biome tags
- crazy mobs intentionally keep no normal spawn placement
- crazy mobs intentionally keep no normal `CAVERN` spawn biome modifiers or tags

So the current active Reborn architecture is centered on one real `CAVERN` dimension plus data-driven worldgen resources, not on a family of provider-owned mirage worlds.

## Why This Increment Does Not Add `cavenia` Dimension JSONs

This increment does not add:

- `data/cavernreborn/dimension/cavenia.json`
- `data/cavernreborn/dimension_type/cavenia.json`

because those files would imply an active dimension foundation that the project does not actually have yet.

Without a source-honest Cavenia terrain/biome/provider/access story, a checked-in `cavenia` dimension key would be:

- incomplete
- misleading
- likely broken at runtime
- easy to misread as approval for active Cavenia or crazy natural spawning

## Why This Increment Does Not Add Active Cavenia Worldgen

This increment does not add active Cavenia worldgen because the inspected legacy source shows that real Cavenia depends on:

- its own biome provider input set
- its own chunk generator
- its own configured veins/content
- its own population behavior
- its own provider-owned spawn callback

Those are larger follow-up slices, not a tiny safe foundation patch.

## Why This Increment Does Not Add Active Crazy Natural Spawning

This increment does not add active crazy natural spawning because the inspected legacy crazy-roster switch belongs to the missing `WorldProviderCavenia#createSpawnCreature(...)` path.

Adding crazy spawn placements, biome modifiers or biome tags in the current normal Reborn `CAVERN` path would still be fake parity.

## What Future Cavenia Foundation Must Be Split Into

Future honest Cavenia work should be split into separate slices:

- dimension key/type contract documented in `docs/cavenia-dimension-key-type-contract-boundary.md`
- mirage-world entry/access contract
- biome provider / biome list contract
- chunk generator / terrain contract
- configured veins/content contract
- provider-owned spawn callback contract
- crazy roster config/policy contract
- runtime smoke plus manual gameplay smoke

Those slices can then decide separately whether they need:

- new data resources
- new dimension/runtime keys
- new arrival or access flow
- new spawn policy wiring

## Current Reborn Boundary

Current Reborn still has:

- no active `cavenia` dimension
- no `WorldProviderCavenia` equivalent
- no active Cavenia terrain generator
- no active Cavenia biome provider
- no active Cavenia crazy-roster spawn callback
- no active Cavenia config surface

Current Reborn still keeps:

- the active `CAVERN` dimension/worldgen path
- direct Cavenic mob spawning in `CAVERN`
- crazy mob natural spawning deferred

## Testing

- Documentation tests pin the inspected `WorldProviderCavenia`, `CaveniaConfig`, `ChunkGeneratorCavenia`, `CaveDimensions` and crazy-roster/provider relationship.
- Resource tests pin that no active `cavenia` dimension or dimension-type resources were added.
- Runtime GameTest smoke pins that `CAVERN` remains active while `cavenia` remains inactive and crazy mobs still keep no active normal spawn placement.

## Explicit Non-Goals

- no active `cavenia` dimension
- no `WorldProviderCavenia` port
- no `ChunkGeneratorCavenia` port
- no biome provider port
- no mirage-world access implementation
- no portals or teleports
- no active crazy natural spawning
- no new blocks, items, mobs, ores, biomes or structures
- no economy or magic-book systems
- no broad worldgen rewrite
