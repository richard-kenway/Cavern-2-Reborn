# Crazy Zombie Baseline MVP

This note documents the first bounded crazy-variant mob foundation for modern Reborn.

It ports exactly one crazy mob:

- `cavernreborn:crazy_zombie`

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCrazySpider`
- `cavern.client.renderer.RenderCrazyZombie`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/crazy_zombie.png`
- `assets/cavern/lang/en_us.lang`

## Crazy Roster Summary

The inspected direct crazy roster in legacy Cavern 2 is:

- `EntityCrazyZombie`
- `EntityCrazySkeleton`
- `EntityCrazyCreeper`
- `EntityCrazySpider`

`Crazy Zombie` was selected first because it has the cleanest baseline mapping:

- clear direct class, registry id, display name, renderer and texture
- clear spawn egg entry and colors
- a safe modern `Zombie` base class exists in 1.21.1
- no bow-specific, explosion-specific or spider-debuff-specific systems are required just to make the entity exist

The other crazy variants remain follow-up candidates for narrower slices:

- Crazy Skeleton remains a follow-up because it depends on the legacy `EntityAIAttackCavenicBow` path, guaranteed Infinity bow equipment and mixed ranged/melee AI.
- Crazy Creeper remains a follow-up because its direct legacy identity depends on a huge fuse/explosion branch.
- Crazy Spider remains a follow-up because it carries stronger blindness/poison combat hooks on top of the spider baseline.

## Runtime Shape

- The runtime entity id is `cavernreborn:crazy_zombie`.
- The spawn egg id is `cavernreborn:crazy_zombie_spawn_egg`.
- Reborn `CrazyZombie` extends vanilla `Zombie`.
- Legacy `EntityCrazyZombie` extends `EntityCavenicZombie`, but this baseline does not extend the current Reborn `CavenicZombie` baseline because that would silently pull in staged Cavenic orb-drop and damage behavior before those crazy follow-ups are inspected honestly.
- The inherited legacy fall/fire damage behavior is now restored explicitly on Reborn `CrazyZombie` and documented in `docs/crazy-zombie-damage-behavior-mvp.md`.
- The legacy Crazy Zombie knockback-on-hit follow-up is now restored explicitly on Reborn `CrazyZombie` and documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`.
- The mob keeps vanilla zombie AI and general hostile behavior for this baseline slice.

## Attributes

- Reborn uses the direct legacy Crazy Zombie combat attributes where the mapping is clear:
  - max health: `2000.0`
  - follow range: `50.0`
  - knockback resistance: `1.0`
  - attack damage: `7.5`
- Modern `generic.max_health` in NeoForge 1.21.1 is capped at `1024.0`, so the checked-in baseline keeps the legacy `2000.0` source literal for provenance while the runtime smoke expects the effective in-game max health to clamp to `1024.0`.
- Zombie reinforcement chance is pinned to `0.0`, matching the legacy override.
- Movement speed intentionally stays on the vanilla zombie baseline because the legacy crazy class does not override it directly.
- XP reward is pinned to `50`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CrazyZombieRenderer` that reuses the vanilla zombie renderer/model path with a custom texture.
- The texture file is `assets/cavernreborn/textures/entity/crazy_zombie.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:crazy_zombie_spawn_egg`.
- Legacy `CaveEntityRegistry` has a clear spawn egg entry for this mob, so the baseline keeps it.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0x909090`
  - spot color: `0x00A0A0`

## Loot Decision

- The bounded baseline reuses the vanilla zombie loot table as its base drop source.
- The inherited legacy orb-drop follow-up is now documented in `docs/crazy-zombie-loot-mvp.md`.
- The separate inherited damage behavior follow-up is documented in `docs/crazy-zombie-damage-behavior-mvp.md`.
- Custom loot beyond the restored inherited orb-drop branch and inherited Cavenic boss-like follow-ups remain out of scope for this first crazy baseline slice.

## Natural Spawning

- Natural spawning is intentionally out of scope for this baseline slice.
- The inspected legacy source does not use the normal `CAVERN` hostile-biome path for Crazy Zombie.
- `EntityCrazyZombie` only appears in the separate legacy `CaveEntityRegistry.CRAZY_SPAWNS` list with weight `1` and group size `1 / 1`.
- That crazy roster is only selected from the old `WorldProviderCavenia#createSpawnCreature(...)` branch, where `CaveniaConfig.crazySpawnChance` and a nearby non-boss `ICavenicMob` exclusion check decide whether the provider switches away from the normal spawn list.
- Reborn therefore does not add a fake `CAVERN` biome modifier or normal monster spawn rule for Crazy Zombie.
- Reborn therefore does not add a fake `CAVERN`-only spawn rule for Crazy Zombie.
- The exact deferred natural-spawn boundary is documented in `docs/crazy-zombie-natural-spawn-absent-or-deferred.md`.

## Intentionally Deferred Crazy-Zombie Behavior

This baseline does not yet port the legacy crazy-specific branches:

- Cavenia-only crazy-roster natural spawning
- boss bar / sky-darkening visibility behavior
- crazy particle trail
- max-spawn-in-chunk / boss classification follow-up
- custom loot beyond the restored inherited orb-drop branch

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources, the explicit no-natural-spawn boundary and the separate inherited loot/damage follow-up source checks.
- Documentation tests cover the legacy references inspected, the crazy-roster summary, the selection rationale, the attribute mapping, renderer/texture provenance, spawn egg decision, the separate damage-follow-up note and the explicit follow-up boundaries.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - hostile runtime spawn
  - vanilla zombie loot-table baseline
  - spawn egg resolution
  - spawn egg entity creation
  - separate inherited loot-behavior smoke documented in `docs/crazy-zombie-loot-mvp.md`
  - separate legacy damage-behavior smoke documented in `docs/crazy-zombie-damage-behavior-mvp.md`
  - separate legacy knockback-on-hit smoke documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`

## Out Of Scope

- crazy skeleton / crazy creeper / crazy spider follow-ups
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- natural spawning
- custom loot beyond the restored inherited orb-drop branch
- custom AI
- special attacks and boss behavior
- Cavenia
- Cavenia remains out of scope.
- magic-book or spell systems
- The magic-book system remains intentionally untouched.
