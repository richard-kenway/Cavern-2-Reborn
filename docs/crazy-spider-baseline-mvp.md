# Crazy Spider Baseline MVP

This note documents the fourth bounded crazy-variant mob foundation for modern Reborn.

It ports exactly one additional crazy mob:

- `cavernreborn:crazy_spider`

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySpider`
- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.client.renderer.RenderCrazySpider`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/crazy_spider.png`
- `assets/cavern/lang/en_us.lang`

## Crazy Roster Context

The inspected direct crazy roster in legacy Cavern 2 is:

- `EntityCrazyZombie`
- `EntityCrazySkeleton`
- `EntityCrazyCreeper`
- `EntityCrazySpider`

`Crazy Spider` is safe enough for a baseline because the legacy class has:

- a clear direct class and registry id
- a clear display name, renderer, texture and spawn egg entry
- a safe modern vanilla `Spider` base in 1.21.1
- direct baseline attributes and XP reward on the class itself

The risky parts are separate follow-up branches rather than prerequisites for the entity to exist:

- direct blindness-on-hit and poison-on-hit power overrides
- inherited `cavenic_orb` drop behavior from `EntityCavenicSpider`
- inherited fall/fire damage behavior from `EntityCavenicSpider`
- direct red boss-bar / sky-darkening behavior
- direct client-only `ParticleCrazyMob` trail
- legacy crazy-roster natural spawning through the old `Cavenia` provider/config path

## Runtime Shape

- The runtime entity id is `cavernreborn:crazy_spider`.
- The spawn egg id is `cavernreborn:crazy_spider_spawn_egg`.
- Reborn `CrazySpider` extends vanilla `Spider`.
- Legacy `EntityCrazySpider` extends `EntityCavenicSpider`, but this baseline does not extend the current Reborn `CavenicSpider` baseline because that would silently inherit staged Reborn blindness, loot, damage and natural-spawn behavior before each Crazy Spider follow-up is inspected honestly.
- Legacy Crazy Spider is still closer to vanilla `Spider` than `CaveSpider`; the legacy base path is `EntitySpider` through `EntityCavenicSpider`, so Reborn uses vanilla `Spider`.
- Reborn keeps vanilla spider AI, targeting, climbing, melee behavior and other general hostile behavior for this baseline slice.

## Attributes

- Reborn uses the direct legacy Crazy Spider attributes where the mapping is clear:
  - max health: `1500.0`
  - movement speed: `0.6`
  - knockback resistance: `1.0`
- Modern `generic.max_health` in NeoForge 1.21.1 is capped at `1024.0`, so the checked-in baseline keeps the legacy `1500.0` source literal for provenance while the runtime smoke expects the effective in-game max health to clamp to `1024.0`.
- Follow range intentionally stays on the vanilla spider baseline because legacy `EntityCrazySpider#applyMobAttributes()` does not pin it directly.
- Attack damage intentionally stays on the vanilla spider baseline because the legacy class does not pin it directly.
- XP reward is pinned to `50`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CrazySpiderRenderer` that reuses the vanilla spider renderer/model path with a custom texture.
- Legacy `RenderCrazySpider` only swaps the texture; it does not add a baseline-only scale, glow or eyes-layer hook.
- The texture file is `assets/cavernreborn/textures/entity/crazy_spider.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:crazy_spider_spawn_egg`.
- Legacy `CaveEntityRegistry` has a clear spawn egg entry for this mob, so the baseline keeps it.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0x909090`
  - spot color: `0x811F1F`

## Loot Decision

- The bounded baseline reuses the vanilla spider loot table as its base drop source.
- Legacy `EntityCrazySpider` inherits the `1/8` `cavenic_orb` drop from `EntityCavenicSpider`.
- This baseline does not restore that inherited orb branch yet.
- Reborn does not add a custom Crazy Spider loot table JSON.

## Natural Spawning

- Natural spawning is intentionally out of scope for this baseline slice.
- Legacy `EntityCrazySpider` appears in `CaveEntityRegistry.CRAZY_SPAWNS` with weight `1` and group size `1 / 1`.
- Legacy `EntityCrazySpider#getMaxSpawnedInChunk()` returns `1`.
- Like the other crazy variants, that crazy roster is only selected from the old `WorldProviderCavenia#createSpawnCreature(...)` branch instead of the normal `CAVERN` hostile-biome path.
- Reborn therefore does not add a fake `CAVERN` spawn placement or biome modifier for Crazy Spider.

## Combat / Blindness / Poison Boundary

Legacy `EntityCrazySpider` carries a separate combat identity on top of the spider baseline:

- `getBlindnessAttackPower()` returns difficulty-scaled durations in seconds:
  - default: `5`
  - normal: `10`
  - hard: `20`
- `getPoisonAttackPower()` returns difficulty-scaled durations in seconds:
  - default: `3`
  - normal: `5`
  - hard: `8`
- inherited `EntityCavenicSpider#attackEntityAsMob(Entity entity)` only applies those effects after a successful melee hit
- the inherited branch only applies effects to `EntityLivingBase`
- the inherited branch skips blindness when the target is already blinded
- the inherited branch skips poison when the target is already poisoned
- the inherited branch converts the returned power into `sec * 20` tick durations

This baseline does not restore:

- blindness-on-hit
- poison-on-hit
- potion effects
- custom melee behavior
- custom AI/targeting/pathing rewrites

Those hooks stay deferred because they are the main risky Crazy Spider identity and they can be restored later without blocking the safe vanilla `Spider` baseline.

## Other Deferred Follow-Ups

- inherited fall/fire damage behavior from `EntityCavenicSpider`
- inherited `cavenic_orb` drop behavior from `EntityCavenicSpider`
- direct red progress boss-event / sky-darkening behavior
- direct client-only `ParticleCrazyMob` trail
- `isNonBoss()`
- `canBeRidden(...)`
- `onStruckByLightning(...)`
- natural spawning through the old Cavenia crazy-roster provider/config path

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources, the explicit vanilla `Spider` base, the explicit vanilla spider loot-table baseline, the explicit no-natural-spawn boundary and the continued absence of custom loot, damage, boss, particle and combat-effect follow-up code.
- Documentation tests cover the legacy references inspected, the crazy-roster context, the attribute mapping, renderer/texture provenance, spawn egg decision, the Cavenia-tied natural-spawn deferral and the deferred combat/blindness/poison boundary.
- NeoForge GameTest runtime smoke covers:
  - crazy spider runtime registry id
  - crazy spider attribute registration
  - crazy spider hostile runtime spawn
  - crazy spider spawn egg resolution
  - crazy spider spawn egg entity creation
  - crazy spider vanilla spider loot-table baseline
  - crazy spider explicit no-natural-spawn baseline boundary
  - crazy spider explicit no-custom-combat / blindness / poison baseline boundary

## Out Of Scope

- Crazy Spider natural spawning
- Crazy Spider custom loot
- Crazy Spider `cavenic_orb` drops
- Crazy Spider damage behavior
- Crazy Spider boss bar / sky-darkening
- Crazy Spider particle trail
- Crazy Spider custom combat / blindness / poison behavior
- Crazy Spider custom AI
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
- magic-book or spell systems
