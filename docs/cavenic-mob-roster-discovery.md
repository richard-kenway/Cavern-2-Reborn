# Direct Cavenic Mob Roster Boundary

## Summary

This slice inspected the remaining legacy `EntityCavenic*` roster after the completed `cavenic_bear` line.
The direct legacy roster is already exhausted in Reborn, so no seventh direct Cavenic baseline entity was added.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.entity.monster.EntityCavenicWitch`
- `cavern.entity.monster.EntityCavenicBear`
- `cavern.entity.CaveEntityRegistry`
- `cavern.client.renderer.RenderCavenicZombie`
- `cavern.client.renderer.RenderCavenicSkeleton`
- `cavern.client.renderer.RenderCavenicCreeper`
- `cavern.client.renderer.RenderCavenicSpider`
- `cavern.client.renderer.RenderCavenicWitch`
- `cavern.client.renderer.RenderCavenicBear`
- `assets/cavern/lang/en_us.lang`
- `assets/cavern/textures/entity`

## Direct Legacy Roster

| Legacy class | Registry id | Base class | Renderer | Texture | Display name | Spawn egg colors | Reborn status |
| --- | --- | --- | --- | --- | --- | --- | --- |
| `EntityCavenicZombie` | `cavenic_zombie` | `EntityZombie` | `RenderCavenicZombie` | `textures/entity/cavenic_zombie.png` | `Cavenic Zombie` | `0xAAAAAA / 0x00A0A0` | Already ported |
| `EntityCavenicSkeleton` | `cavenic_skeleton` | `EntitySkeleton` | `RenderCavenicSkeleton` | `textures/entity/cavenic_skeleton.png` | `Cavenic Skeleton` | `0xAAAAAA / 0xDDDDDD` | Already ported |
| `EntityCavenicCreeper` | `cavenic_creeper` | `EntityCreeper` | `RenderCavenicCreeper` | `textures/entity/cavenic_creeper.png` | `Cavenic Creeper` | `0xAAAAAA / 0x2E8B57` | Already ported |
| `EntityCavenicSpider` | `cavenic_spider` | `EntitySpider` | `RenderCavenicSpider` | `textures/entity/cavenic_spider.png` | `Cavenic Spider` | `0xAAAAAA / 0x811F1F` | Already ported |
| `EntityCavenicWitch` | `cavenic_witch` | `EntityWitch` | `RenderCavenicWitch` | `textures/entity/cavenic_witch.png` | `Cavenic Witch` | `0xAAAAAA / 0x4A5348` | Already ported |
| `EntityCavenicBear` | `cavenic_bear` | `EntityPolarBear` | `RenderCavenicBear` | `textures/entity/cavenic_bear.png` | `Cavenic Bear` | `0xAAAAAA / 0xFFFFFF` | Already ported |

## Excluded Follow-Ups

- `EntitySummonCavenicZombie` and `EntitySummonCavenicSkeleton` are summon-only derivatives, not direct `EntityCavenic*` baseline mobs.
- `EntityCrazyZombie`, `EntityCrazySkeleton`, `EntityCrazyCreeper` and `EntityCrazySpider` are separate crazy variants, not direct `EntityCavenic*` roster entries.
- `EntityCaveman`, `EntityCrystalTurret`, `EntitySkySeeker`, `EntityDurangHog`, `EntityAquaSquid`, `EntityMagicTorcher`, `EntityBeam` and `EntityCavenicArrow` are not direct Cavenic mob foundations for this slice.

## Decision

No remaining unported direct `EntityCavenic*` mobs exist in legacy Cavern 2.
The legacy direct Cavenic mob roster ends at the six classes already present in Reborn:

- `cavenic_zombie`
- `cavenic_skeleton`
- `cavenic_creeper`
- `cavenic_spider`
- `cavenic_witch`
- `cavenic_bear`

Because no seventh direct class exists, this slice adds no new entity class, no new renderer, no new spawn egg, no new loot baseline, no new natural-spawn slice, and no new GameTest runtime entity coverage.

## Out Of Scope

- crazy variants
- summon variants
- non-Cavenic mobs such as `caveman`, `durang_hog`, `sky_seeker` and `crystal_turret`
- Cavenia-specific behavior
- custom loot, `cavenic_orb` drops, damage behavior or AI follow-ups for non-direct variants
- magic-book or spell systems

## Testing

- source-level tests pin the current Reborn direct Cavenic entity registrations, spawn eggs, renderer registrations and entity lang entries to the same six-entry roster
- README and runtime-smoke docs explicitly record that the direct legacy `EntityCavenic*` roster is exhausted after `cavenic_bear`
