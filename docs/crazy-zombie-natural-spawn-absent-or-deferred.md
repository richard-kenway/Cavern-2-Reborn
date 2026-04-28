# Crazy Zombie Natural Spawn Absent Or Deferred

This note documents the inspected legacy natural-spawn path for `cavernreborn:crazy_zombie`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.CaveEntityRegistry`
- `cavern.world.WorldProviderCavern`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.config.CaveniaConfig`

## Exact Legacy Spawn Behavior Found

- `EntityCrazyZombie` is not registered in the normal legacy `CaveEntityRegistry.SPAWNS` list.
- It is registered in the separate legacy `CaveEntityRegistry.CRAZY_SPAWNS` list as:
  - `new SpawnListEntry(EntityCrazyZombie.class, 1, 1, 1)`
- Legacy `WorldProviderCavern#createSpawnCreature(...)` only selects from `CaveEntityRegistry.SPAWNS`, so Crazy Zombie is not part of the normal `CAVERN` hostile population path.
- Legacy `WorldProviderCavenia#createSpawnCreature(...)` starts from `CaveEntityRegistry.SPAWNS`, then may switch to `CaveEntityRegistry.CRAZY_SPAWNS` when all of the following are true:
  - the dimension is `Cavenia`
  - `CaveniaConfig.crazySpawnChance > 0.0D`
  - a `world.rand.nextDouble()` roll passes that config chance
  - no nearby `ICavenicMob` entity with `!entity.isNonBoss()` is found inside the chance-derived exclusion box
- When that switch happens, Crazy Zombie is only one member of the weighted crazy roster, not a dedicated standalone biome spawn entry.

## Weight, Group Size And Cluster Details

- The legacy crazy-roster entry for Crazy Zombie is:
  - weight: `1`
  - min group size: `1`
  - max group size: `1`
- Legacy `EntityCrazyZombie#getMaxSpawnedInChunk()` returns `1`.

## Why Reborn Does Not Implement Natural Spawning In This Slice

- The inspected legacy path is not a normal direct biome spawn like the current Cavenic mob lines.
- It is tied to:
  - the unimplemented `Cavenia` dimension
  - `CaveniaConfig.crazySpawnChance`
  - nearby non-boss `ICavenicMob` filtering
  - weighted switching across the whole crazy roster
- Reborn does not currently have `Cavenia`, the old Cavenia world-provider override, or the full crazy-roster population system.
- Because of that, adding a `CAVERN` biome modifier or a direct `crazy_zombie` spawn placement would invent behavior that the legacy source does not support.

## Current Reborn Boundary

- Reborn keeps `crazy_zombie` as a baseline entity only.
- Reborn does not register natural spawn placement for `crazy_zombie`.
- Reborn does not add:
  - `data/cavernreborn/neoforge/biome_modifier/crazy_zombie_spawns.json`
  - `data/cavernreborn/tags/worldgen/biome/spawns_crazy_zombie.json`
- Reborn does not add a fake `CAVERN` biome modifier or normal monster spawn rule.
- Reborn does not add a fake `CAVERN`-only spawn rule, because the inspected legacy source does not use one for Crazy Zombie.
- The separate inherited loot follow-up is documented in `docs/crazy-zombie-loot-mvp.md`.
- This natural-spawn deferral does not change the separate inherited damage follow-up documented in `docs/crazy-zombie-damage-behavior-mvp.md`.
- The separate knockback-on-hit follow-up is documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`.

## Testing

- Resource tests assert that:
  - `CrazyZombie` still has no natural-spawn constants or spawn-rule helpers
  - `ModEntityEvents` still does not register spawn placement for `CRAZY_ZOMBIE`
  - no Crazy Zombie biome modifier or biome tag resources exist
- Documentation tests assert that this boundary note and the Crazy Zombie baseline note both describe the Cavenia-only, config-gated legacy spawn path.
- Documentation tests also pin that the separate Crazy Zombie loot follow-up stays explicit and independent from natural spawning.
- Documentation tests also pin that the separate Crazy Zombie damage-behavior follow-up stays explicit and independent from natural spawning.
- Runtime smoke documentation now states the explicit deferred natural-spawn boundary instead of implying a pending generic spawn follow-up.

## Intentionally Out Of Scope

- `Cavenia`
- the legacy `crazySpawnChance` config loop
- weighted crazy-roster switching across Crazy Skeleton, Crazy Creeper, Crazy Zombie and Crazy Spider
- Crazy Zombie custom loot beyond the restored inherited orb-drop branch
- Crazy Zombie damage behavior is handled separately in `docs/crazy-zombie-damage-behavior-mvp.md`
- Crazy Zombie knockback-on-hit behavior is handled separately in `docs/crazy-zombie-knockback-on-hit-mvp.md`
- boss bar / sky-darkening
- particle trail
- custom AI
- magic-book or spell systems
