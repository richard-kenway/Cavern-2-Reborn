# Cavenia Dimension Key / Type Contract Boundary

This note documents only the future `Cavenia` dimension key/type contract.

It does not add:

- `data/cavernreborn/dimension/cavenia.json`
- `data/cavernreborn/dimension_type/cavenia.json`
- `CAVENIA_LOCATION`
- `CAVENIA_LEVEL_KEY`
- an active `cavernreborn:cavenia` level

The broader provider, terrain, entry and spawn architecture boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy References Inspected

- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.WorldProviderCavern`
- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.config.CaveniaConfig`
- `cavern.world.CaveDimensions`
- `cavern.item.ItemMirageBook`
- `cavern.handler.api.DimensionHandler`

## Exact Legacy Identity Found

Legacy `Cavenia` identity is split across:

- `CaveniaConfig.dimensionId = -54`
- `CaveDimensions.CAVENIA`
- `WorldProviderCavenia`

That legacy numeric `dimensionId = -54` is historical identity only.

It does not map directly to the modern checked-in data-pack resource model, so Reborn should document it but must not try to recreate it as a modern runtime id.

## Exact Legacy Provider Properties Found

`WorldProviderCavenia` extends `WorldProviderCavern`.

The inspected direct `WorldProviderCavenia` overrides are:

- `getDimensionType()` -> `CaveDimensions.CAVENIA`
- `getWorldHeight()` -> `CaveniaConfig.worldHeight`
- `getMonsterSpawn()` -> `CaveniaConfig.monsterSpawn`
- `getBrightness()` -> `CaveniaConfig.caveBrightness`
- `createBiomeProvider()` -> `CaveniaConfig.BIOMES`
- `createChunkGenerator()` -> `ChunkGeneratorCavenia`
- `createSpawnCreature(...)` -> normal/crazy roster switch

Inherited `WorldProviderCavern` properties still apply:

- `hasSkyLight = false`
- `isSurfaceWorld() == false`
- `canRespawnHere() == false`
- `canSleepAt(...) == ALLOW`
- `canDoLightning(...) == false`
- `canDoRainSnowIce(...) == false`
- dark cave fog/sky path

The inspected default config values relevant to a future key/type contract are:

- `worldHeight = 128`
- `caveBrightness = 0.125D`
- `monsterSpawn = 200`

## Current Reborn Modern Reference Point

Current Reborn has one active checked-in dimension contract:

- `CavernNeoForgeDimensions.CAVERN_LOCATION`
- `CavernNeoForgeDimensions.CAVERN_LEVEL_KEY`
- `data/cavernreborn/dimension/cavern.json`
- `data/cavernreborn/dimension_type/cavern.json`

The current checked-in `cavern` dimension type already chooses concrete modern values such as:

- `has_skylight: false`
- `natural: false`
- `bed_works: false`
- `respawn_anchor_works: false`
- `height: 192`
- `logical_height: 192`
- `ambient_light: 0.0`
- `effects: cavernreborn:cavern`

That active `cavern` JSON pair is the modern Reborn reference point for schema shape only.

It is not proof that the same values would be correct for future `Cavenia`.

## Future Modern Identity To Document, Not Activate

The likely future modern resource identity is:

- `cavernreborn:cavenia`

But in this increment it remains future identity only.

Current Reborn still has:

- no active `cavernreborn:cavenia`
- no `CAVENIA_LOCATION`
- no `CAVENIA_LEVEL_KEY`
- no `data/cavernreborn/dimension/cavenia.json`
- no `data/cavernreborn/dimension_type/cavenia.json`

## Plausible Legacy-To-Modern Dimension-Type Mapping Constraints

The inspected legacy provider suggests the future `dimension_type` contract will likely need to consider:

- `has_skylight: false` because legacy `WorldProviderCavern` sets `hasSkyLight = false`
- a cave-like `natural` decision because legacy `isSurfaceWorld() == false`
- `bed_works` and `respawn_anchor_works` choices that must reflect the old no-normal-respawn behavior instead of being guessed from `CAVERN`
- `height` / `logical_height` choices that account for legacy `worldHeight = 128`
- an `ambient_light` or equivalent visual strategy that accounts for legacy `caveBrightness = 0.125D`
- an `effects` strategy for the dark cave/fog identity
- explicit monster light rules if the final modern dimension type needs them

These are mapping constraints, not final values.

## What Cannot Be Finalized Yet

This increment does not finalize:

- the actual `dimension/cavenia.json` generator object
- the biome source
- terrain settings
- exact `height` and `logical_height`
- exact `ambient_light`
- exact `effects`
- exact `bed_works` semantics
- exact `respawn_anchor_works` semantics
- exact `monster_spawn_light_level`
- exact `monster_spawn_block_light_limit`
- entry/access flow
- provider-owned spawn callback
- crazy-roster activation
- client visual/fog/lighting parity

Those choices depend on future generator, biome-provider, entry and spawn work rather than the key/type contract alone.

## Why Reborn Does Not Add `dimension/cavenia.json` Now

Adding `data/cavernreborn/dimension/cavenia.json` now would be misleading because the inspected legacy source ties `Cavenia` identity to:

- `WorldProviderCavenia`
- `ChunkGeneratorCavenia`
- `CaveniaConfig.BIOMES`
- `CaveniaConfig.VEINS`
- mirage-world entry/access
- provider-owned crazy-roster switching

A checked-in modern dimension JSON without those dependencies would suggest active support that does not exist yet.

## Why Reborn Does Not Add `dimension_type/cavenia.json` Now

Adding `data/cavernreborn/dimension_type/cavenia.json` now would also be premature because the final values depend on unresolved choices about:

- terrain height and logical height
- cave brightness and ambient-light strategy
- fog/effects identity
- respawn/sleep semantics
- spawn-light semantics

Those decisions should be made together with the first honest active Cavenia MVP, not guessed in isolation.

## Future Slice Order

Future honest Cavenia work should split this contract into follow-up slices:

- active dimension key/type MVP
- terrain/generator MVP
- biome provider MVP
- mirage entry/access MVP
- spawn provider / crazy roster MVP
- client visual/fog/lighting MVP

## Explicit Non-Goals

- no active `cavenia` dimension
- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no `CAVENIA_LOCATION`
- no `CAVENIA_LEVEL_KEY`
- no `WorldProviderCavenia` port
- no `ChunkGeneratorCavenia` port
- no biome provider port
- no terrain generation
- no portal/teleport/access flow
- no active Cavenia spawning
- no active crazy spawning
