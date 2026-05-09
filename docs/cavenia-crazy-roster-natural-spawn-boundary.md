# Cavenia Crazy Roster Natural Spawn Boundary

This note documents the inspected legacy natural-spawn path for the four direct crazy variants:

- `cavernreborn:crazy_zombie`
- `cavernreborn:crazy_skeleton`
- `cavernreborn:crazy_creeper`
- `cavernreborn:crazy_spider`

The broader inactive Cavenia runtime/provider boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The separate mirage entry/access boundary remains documented in:

- `docs/cavenia-mirage-entry-access-contract-boundary.md`

The separate focused spawn-provider / crazy-roster activation contract remains documented in:

- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`

## Legacy References Inspected

- `cavern.entity.CaveEntityRegistry`
- `cavern.world.WorldProviderCavern`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.config.CaveniaConfig`
- `cavern.api.entity.ICavenicMob`
- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCrazySpider`

## Exact Legacy Crazy Roster Found

Legacy `CaveEntityRegistry.CRAZY_SPAWNS` contains exactly these four entries:

- `new SpawnListEntry(EntityCrazySkeleton.class, 1, 1, 1)`
- `new SpawnListEntry(EntityCrazyCreeper.class, 1, 1, 1)`
- `new SpawnListEntry(EntityCrazyZombie.class, 1, 1, 1)`
- `new SpawnListEntry(EntityCrazySpider.class, 1, 1, 1)`

That means:

- every crazy-roster entry has weight `1`
- every crazy-roster entry has min group size `1`
- every crazy-roster entry has max group size `1`
- the roster contains only Crazy Skeleton, Crazy Creeper, Crazy Zombie and Crazy Spider
- it does not contain summon variants
- it does not contain direct Cavenic mobs
- it does not contain non-Crazy mobs

The inspected crazy entities also pin `getMaxSpawnedInChunk()` to `1`:

- `EntityCrazyZombie#getMaxSpawnedInChunk()`
- `EntityCrazySkeleton#getMaxSpawnedInChunk()`
- `EntityCrazyCreeper#getMaxSpawnedInChunk()`
- `EntityCrazySpider#getMaxSpawnedInChunk()`

## Exact Legacy Provider Path Found

Legacy normal Cavern spawning and legacy Cavenia crazy-roster spawning are separate paths.

`WorldProviderCavern#createSpawnCreature(...)`:

- only handles `EnumCreatureType.MONSTER`
- does not reference `CaveEntityRegistry.CRAZY_SPAWNS`
- on a `world.rand.nextInt(30) == 0` roll, selects only from `CaveEntityRegistry.SPAWNS`

`WorldProviderCavenia#createSpawnCreature(...)`:

- only handles `EnumCreatureType.MONSTER`
- starts from `CaveEntityRegistry.SPAWNS`
- reads `double chance = CaveniaConfig.crazySpawnChance`
- only considers the crazy roster when `chance > 0.0D`
- then requires `world.rand.nextDouble() <= chance`
- computes a nearby exclusion range from that chance:
  - `chance <= 0.1D` -> `range = 50`
  - `chance <= 0.2D` -> `range = 32`
  - `chance <= 0.4D` -> `range = 16`
  - `chance <= 0.6D` -> `range = 8`
  - `chance <= 0.8D` -> `range = 4`
  - otherwise `range = 0`
- uses `rangeY = range > 1 ? range / 2 : range`
- switches to `CaveEntityRegistry.CRAZY_SPAWNS` only when either:
  - `range <= 0`, or
  - `world.getEntitiesWithinAABB(..., entity -> entity instanceof ICavenicMob && !entity.isNonBoss()).isEmpty()`
- after that switch, chooses a weighted entry from the selected list with `WeightedRandom.getRandomItem(world.rand, list)`

This means the crazy roster is:

- Cavenia-only
- config-gated by `CaveniaConfig.crazySpawnChance`
- additionally gated by nearby non-boss `ICavenicMob` exclusion
- selected as one weighted roster, not as four standalone biome spawn entries

## Checks Not Found In The Crazy-Roster Switch

I did not find any crazy-roster switch on:

- normal `CAVERN` biome modifiers or biome tags
- floor index
- biome identity
- light level
- progression
- economy
- custom combat state
- boss bar state
- particle state
- spawn egg use

Spawn eggs are a separate registry concern from natural spawning.

The crazy-roster switch also does not add custom per-crazy placement predicates in `WorldProviderCavenia#createSpawnCreature(...)`; it swaps only the weighted list and still relies on the broader legacy spawn pipeline around the provider.

## Why Reborn Does Not Implement Active Natural Spawning Here

Current Reborn has:

- the checked-in `cavern` dimension
- current `CAVERN`-only natural spawning for the direct Cavenic roster through `RegisterSpawnPlacementsEvent` plus biome modifiers and biome tags
- the separate architecture boundary documented in `docs/cavenia-dimension-provider-foundation-boundary.md`

Current Reborn does not have:

- a `cavenia` dimension
- a `WorldProviderCavenia` equivalent
- a Cavenia-only spawn callback that can swap between `SPAWNS` and `CRAZY_SPAWNS`
- a Reborn equivalent of `CaveniaConfig.crazySpawnChance`
- a nearby non-boss `ICavenicMob` exclusion loop for the crazy roster

Because of that, adding normal Reborn biome modifiers, biome tags or spawn placements for the crazy mobs would invent behavior that the legacy source does not support.

## Current Reborn Boundary

Reborn keeps all four crazy mobs available only as explicit registered entities:

- `crazy_zombie`
- `crazy_skeleton`
- `crazy_creeper`
- `crazy_spider`

Reborn does not add fake normal `CAVERN` natural spawning for any of them.

Reborn does not add:

- `data/cavernreborn/neoforge/biome_modifier/crazy_zombie_spawns.json`
- `data/cavernreborn/neoforge/biome_modifier/crazy_skeleton_spawns.json`
- `data/cavernreborn/neoforge/biome_modifier/crazy_creeper_spawns.json`
- `data/cavernreborn/neoforge/biome_modifier/crazy_spider_spawns.json`
- `data/cavernreborn/tags/worldgen/biome/spawns_crazy_zombie.json`
- `data/cavernreborn/tags/worldgen/biome/spawns_crazy_skeleton.json`
- `data/cavernreborn/tags/worldgen/biome/spawns_crazy_creeper.json`
- `data/cavernreborn/tags/worldgen/biome/spawns_crazy_spider.json`

Reborn also does not register normal spawn placement for:

- `CRAZY_ZOMBIE`
- `CRAZY_SKELETON`
- `CRAZY_CREEPER`
- `CRAZY_SPIDER`

## What Future Cavenia Work Must Provide First

Before active crazy natural spawning can be enabled honestly, Reborn needs:

- a real `Cavenia` dimension foundation
- the provider/terrain/biome split documented in `docs/cavenia-dimension-provider-foundation-boundary.md`
- a Cavenia-only monster spawn host equivalent to legacy `WorldProviderCavenia#createSpawnCreature(...)`
- a checked-in policy for `CaveniaConfig.crazySpawnChance`
- the nearby non-boss `ICavenicMob` exclusion behavior or a modern equivalent that is source-honest
- a Cavenia-only roster switch that keeps crazy mobs out of the normal `CAVERN` biome-spawn pipeline

## Behavior Kept Unchanged In This Boundary Slice

This boundary note does not change:

- crazy mob combat
- crazy mob loot
- crazy mob incoming damage behavior
- crazy mob boss bar / sky-darkening
- crazy mob particle behavior
- crazy mob renderer, texture, spawn egg or attributes
- crazy skeleton custom ranged AI deferral
- crazy creeper fuse/explosion deferral

## Testing

- Documentation tests pin the exact legacy `CRAZY_SPAWNS` roster, the `WorldProviderCavenia#createSpawnCreature(...)` path and the current Reborn deferral.
- Resource tests pin that no fake normal Reborn crazy-spawn biome modifiers, biome tags or spawn placements exist.
- NeoForge GameTest runtime smoke pins that all four crazy entity types still resolve while natural spawn placement stays unregistered and no crazy-spawn biome modifier or biome tag is present at runtime.

## Explicit Non-Goals

- no normal `CAVERN` spawn rules for crazy mobs
- no crazy biome modifiers or biome tags
- no full `Cavenia` dimension port
- no `WorldProviderCavenia` port
- no summon variants
- no direct Cavenic mob changes
- no crazy mob combat/loot/damage/boss/particle changes
