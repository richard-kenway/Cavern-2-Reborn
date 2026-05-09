# Cavenia Spawn Provider / Crazy Roster Activation Contract Boundary

This note documents the inspected legacy provider-owned hostile spawn path behind Cavenia's normal roster and crazy-roster activation.

The consolidated readiness and future implementation order are documented separately in:

- `docs/cavenia-active-foundation-readiness-plan.md`

The broader inactive Cavenia runtime/provider boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The earlier crazy-roster roster-only summary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

The separate non-runtime policy follow-up is documented in:

- `docs/cavenia-spawn-provider-policy-mvp.md`

The separate focused Caveman normal-roster deferral note is documented in:

- `docs/caveman-cavenia-normal-roster-boundary.md`

The separate mirage entry/access boundary remains documented in:

- `docs/cavenia-mirage-entry-access-contract-boundary.md`

## Legacy References Inspected

- `cavern.world.CaveEntitySpawner`
- `cavern.world.WorldProviderCavern`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.entity.CaveEntityRegistry`
- `cavern.config.CaveniaConfig`
- `cavern.api.entity.ICavenicMob`
- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCrazySpider`
- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.entity.monster.EntityCavenicWitch`
- `cavern.entity.monster.EntityCavenicBear`
- `cavern.entity.monster.EntityCaveman`

## Exact Legacy Provider-Owned Spawn Loop Found

Legacy hostile spawning is not driven only by biome lists in cave dimensions.

`WorldProviderCavern` owns a `CaveEntitySpawner` instance and calls it from:

- `WorldProviderCavern#onWorldUpdateEntities()`

The exact provider-owned update path is:

- `worldServer.getGameRules().getBoolean("doMobSpawning")`
- `worldServer.getWorldInfo().getTerrainType() != WorldType.DEBUG_ALL_BLOCK_STATES`
- `entitySpawner.findChunksForSpawning(worldServer, spawnHostileMobs, spawnPeacefulMobs, worldServer.getWorldInfo().getWorldTotalTime() % 400L == 0L)`

Inside `CaveEntitySpawner#findChunksForSpawning(...)`:

- the provider can override the hostile-cap through `IWorldEntitySpawner#getMaxNumberOfCreature(...)`
- the provider can override actual entity creation through `IWorldEntitySpawner#createSpawnCreature(...)`
- spawn positions still pass through the broader `WorldEntitySpawner.canCreatureTypeSpawnAtLocation(...)`, `entity.getCanSpawnHere()`, `entity.isNotColliding()` and `ForgeEventFactory` checks

For hostile mobs, `WorldProviderCavern#getMaxNumberOfCreature(...)` returns:

- `getMonsterSpawn()` when `!type.getPeacefulCreature()`

That value feeds the cap check:

- `world.countEntities(type, true) > maxNumber * playerCount / MOB_COUNT_DIV`

Legacy Cavenia therefore uses a provider-owned hostile spawn host with a provider-owned monster cap, not normal Reborn-style biome modifiers alone.

## Exact Legacy `WorldProviderCavern#createSpawnCreature(...)` Baseline

Legacy `WorldProviderCavern#createSpawnCreature(...)`:

- only handles `EnumCreatureType.MONSTER`
- on `world.rand.nextInt(30) == 0`, selects from `CaveEntityRegistry.SPAWNS`
- otherwise returns `null`

Because `CaveEntitySpawner#createSpawnCreature(...)` falls back to:

- `EntityList.newEntity(entry.entityClass, world)`

the normal Cavern provider only occasionally overrides the biome-selected monster with a `CaveEntityRegistry.SPAWNS` entry.

This matters because Cavenia behaves differently.

## Exact Legacy `WorldProviderCavenia#createSpawnCreature(...)` Behavior

Legacy `WorldProviderCavenia#createSpawnCreature(...)`:

- only handles `EnumCreatureType.MONSTER`
- ignores the passed biome `entry` for monster choice once the provider override runs
- starts from `List<SpawnListEntry> list = CaveEntityRegistry.SPAWNS`
- reads `double chance = CaveniaConfig.crazySpawnChance`
- only enters the crazy-switch branch when `chance > 0.0D && world.rand.nextDouble() <= chance`

The source-confirmed chance-to-range mapping is:

- `chance <= 0.1D` -> `range = 50`
- `chance <= 0.2D` -> `range = 32`
- `chance <= 0.4D` -> `range = 16`
- `chance <= 0.6D` -> `range = 8`
- `chance <= 0.8D` -> `range = 4`
- otherwise `range = 0`

Vertical range is then reduced to:

- `rangeY = range > 1 ? range / 2 : range`

The exact exclusion scan is:

- `world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.add(-range, -rangeY, -range), pos.add(range, rangeY, range)), entity -> entity instanceof ICavenicMob && !entity.isNonBoss()).isEmpty()`
- predicate: `entity instanceof ICavenicMob && !entity.isNonBoss()`
- source-literally, the scan is for nearby `ICavenicMob` entities whose `isNonBoss()` returns `false`
- legacy naming elsewhere also points the same direction: `BlockPortalCavern` rejects `!entity.isNonBoss()`, and legacy `EntitySkySeeker#isNonBoss()` also returns `false`

The switch behavior is:

- keep `CaveEntityRegistry.SPAWNS` by default
- switch to `CaveEntityRegistry.CRAZY_SPAWNS` only when:
  - `range <= 0`, or
  - the nearby search finds no nearby `ICavenicMob` whose `isNonBoss()` returns `false`

The final entity choice is then:

- `WeightedRandom.getRandomItem(world.rand, list)`
- `spawnEntry.newInstance(world)`

Unlike `WorldProviderCavern`, legacy Cavenia always returns a provider-chosen monster for `EnumCreatureType.MONSTER` unless reflective construction fails.

## Exact Legacy `CaveniaConfig.monsterSpawn` And `CaveniaConfig.crazySpawnChance` Behavior

The inspected defaults are:

- `monsterSpawn = 200`
- `crazySpawnChance = 0.1D`

The inspected config surface also clamps:

- `monsterSpawn` to `0 .. 5000`
- `crazySpawnChance` to `0.0D .. 1.0D`

That means:

- `crazySpawnChance = 0.0D` disables the crazy-switch branch entirely
- `crazySpawnChance = 1.0D` always passes the chance roll, then falls into `range = 0`, which always allows `CaveEntityRegistry.CRAZY_SPAWNS`

## Exact Normal And Crazy Roster Relationship Found

Legacy normal Cavenia provider-owned monster choice starts from:

- `CaveEntityRegistry.SPAWNS`

The exact inspected normal roster is:

- `new SpawnListEntry(EntityCavenicSkeleton.class, 20, 1, 1)`
- `new SpawnListEntry(EntityCavenicCreeper.class, 30, 1, 1)`
- `new SpawnListEntry(EntityCavenicZombie.class, 30, 2, 2)`
- `new SpawnListEntry(EntityCavenicSpider.class, 30, 1, 1)`
- `new SpawnListEntry(EntityCavenicWitch.class, 15, 1, 1)`
- `new SpawnListEntry(EntityCavenicBear.class, 30, 1, 1)`
- `new SpawnListEntry(EntityCaveman.class, 35, 1, 1)`

The current Reborn reason for keeping `EntityCaveman -> deferred:caveman` is documented separately in `docs/caveman-cavenia-normal-roster-boundary.md`.

Legacy crazy activation swaps that list to:

- `new SpawnListEntry(EntityCrazySkeleton.class, 1, 1, 1)`
- `new SpawnListEntry(EntityCrazyCreeper.class, 1, 1, 1)`
- `new SpawnListEntry(EntityCrazyZombie.class, 1, 1, 1)`
- `new SpawnListEntry(EntityCrazySpider.class, 1, 1, 1)`

So the crazy path is not:

- per-entity spawn placement registration
- per-entity biome modifiers
- per-entity biome tags
- the normal `CAVERN` biome-spawn path

It is a provider-owned weighted-list swap from one roster to another.

## Exact `ICavenicMob`, `isNonBoss()` And Group-Size Relationship

The nearby exclusion predicate only cares about:

- `entity instanceof ICavenicMob`
- `!entity.isNonBoss()`

The full inspected `ICavenicMob` set in legacy source is:

- direct classes: `EntityCavenicSkeleton`, `EntityCavenicCreeper`, `EntityCavenicZombie`, `EntityCavenicSpider`, `EntityCavenicWitch`, `EntityCavenicBear`, `EntityCaveman`
- crazy subclasses: `EntityCrazySkeleton`, `EntityCrazyCreeper`, `EntityCrazyZombie`, `EntityCrazySpider`

I did not find any other `ICavenicMob` implementations in the legacy source tree.

The crazy entities inherit from direct Cavenic mob classes, so they still satisfy `instanceof ICavenicMob`, and all four inspected crazy classes override:

- `isNonBoss()` -> `false`

That makes the crazy variants the explicit source-confirmed matches for the nearby predicate.

The direct Cavenic classes inspected for the normal roster do implement `ICavenicMob`, but they do not override `isNonBoss()` in their own class bodies. Because `ICavenicMob` itself does not define `isNonBoss()`, this boundary keeps the wording source-literal instead of relabeling the predicate as a generic non-boss exclusion.

The crazy entries also keep:

- weight `1`
- min group size `1`
- max group size `1`
- `getMaxSpawnedInChunk() == 1`

That does not replace the broader spawn-pack pipeline, but it does constrain the selected crazy entity's own per-chunk cap once instantiated.

## What This Means For Reborn

Current Reborn still has:

- active direct Cavenic mob natural spawning in checked-in `CAVERN`
- a pure non-runtime spawn-provider policy layer in `core`
- no active `cavernreborn:cavenia` level
- no active Cavenia provider spawn loop
- no active Cavenia spawn callback
- no active crazy natural spawning
- no fake normal `CAVERN` crazy biome modifiers, biome tags or spawn placements

Normal checked-in `CAVERN` biome spawning does not activate the crazy roster because the inspected legacy crazy switch belongs to the missing provider-owned Cavenia path.

## Why No Active Spawn Provider Is Added Here

Adding active crazy natural spawning now would still be source-dishonest because current Reborn does not have:

- a real active Cavenia level
- a Cavenia-only hostile spawn host/service
- a modern policy for `SPAWNS` versus `CRAZY_SPAWNS`
- a modern policy for `crazySpawnChance`
- a nearby `ICavenicMob` scan for `!entity.isNonBoss()` in Cavenia-only runtime context
- the broader Cavenia access/provider/runtime foundation

Adding normal `CAVERN` biome modifiers or spawn placements for crazy mobs would still be wrong because the inspected legacy activation path is not a normal biome-list registration path.

## Future Modern Mapping Constraints

An honest future Reborn implementation likely needs:

- reuse of the non-runtime policy layer from `docs/cavenia-spawn-provider-policy-mvp.md`
- a Cavenia-only spawn host/service or equivalent server-tick spawn owner
- a modern policy representation for `CaveEntityRegistry.SPAWNS`
- a modern policy representation for `CaveEntityRegistry.CRAZY_SPAWNS`
- a checked-in config or policy for `monsterSpawn`
- a checked-in config or policy for `crazySpawnChance`
- a nearby entity scan equivalent for `ICavenicMob` whose `isNonBoss()` returns `false`
- a spawn validity strategy that fits modern spawn placements without pretending normal `CAVERN` biome modifiers are enough

## Unresolved Questions

- exact modern spawn-loop host
- exact config surface for `monsterSpawn` and `crazySpawnChance`
- exact modern mapping of the normal `SPAWNS` roster
- exact relationship to the current direct Cavenic `CAVERN` spawn placements
- exact Cavenia-only player/chunk ticking semantics once the dimension exists
- exact balancing decisions for a modern active Cavenia rollout

## Related Boundary Notes

- `docs/cavenia-dimension-provider-foundation-boundary.md`
- `docs/cavenia-dimension-key-type-contract-boundary.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- `docs/cavenia-mirage-entry-access-contract-boundary.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/caveman-cavenia-normal-roster-boundary.md`

## Testing

- Documentation tests pin the exact provider-owned spawn loop, `monsterSpawn`, `crazySpawnChance`, chance-to-range mapping and roster switch rules.
- Resource/source tests pin that no active Reborn Cavenia spawn-provider classes, crazy spawn placements, crazy biome modifiers or crazy biome tags were added.
- NeoForge GameTest runtime smoke pins that all four crazy entity types still resolve, all four still keep no registered normal spawn placement, the direct Cavenic `CAVERN` spawn placements stay active, and no checked-in normal `CAVERN` crazy spawn resources exist.
