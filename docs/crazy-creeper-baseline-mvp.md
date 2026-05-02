# Crazy Creeper Baseline MVP

This note documents the third bounded crazy-variant mob foundation for modern Reborn.

It ports exactly one additional crazy mob:

- `cavernreborn:crazy_creeper`

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.client.renderer.RenderCrazyCreeper`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/crazy_creeper.png`
- `assets/cavern/lang/en_us.lang`

## Crazy Roster Context

The inspected direct crazy roster in legacy Cavern 2 is:

- `EntityCrazyZombie`
- `EntityCrazySkeleton`
- `EntityCrazyCreeper`
- `EntityCrazySpider`

`Crazy Creeper` is safe enough for a baseline because the legacy class has:

- a clear direct class and registry id
- a clear display name, renderer, texture and spawn egg entry
- a safe modern vanilla `Creeper` base in 1.21.1
- direct baseline attributes and XP reward on the class itself

The risky parts are separate follow-up branches rather than prerequisites for the entity to exist:

- direct fuse/explosion changes
- inherited `cavenic_orb` drop behavior from `EntityCavenicCreeper`
- inherited fall/fire damage behavior from `EntityCavenicCreeper`
- direct green boss-bar / sky-darkening behavior
- direct client-only `ParticleCrazyMob` trail
- lightning/charged/swelling behavior
- legacy crazy-roster natural spawning through the old `Cavenia` provider/config path

`Crazy Spider` remains the next crazy-variant follow-up candidate.

## Runtime Shape

- The runtime entity id is `cavernreborn:crazy_creeper`.
- The spawn egg id is `cavernreborn:crazy_creeper_spawn_egg`.
- Reborn `CrazyCreeper` extends vanilla `Creeper`.
- Legacy `EntityCrazyCreeper` extends `EntityCavenicCreeper`, but this baseline does not extend the current Reborn `CavenicCreeper` baseline because that would silently inherit staged Reborn loot, damage, natural-spawn and fuse/explosion slices before each Crazy Creeper follow-up is inspected honestly.
- Reborn keeps vanilla creeper AI, vanilla fuse timing, vanilla explosion radius, vanilla swelling behavior, vanilla charged behavior and vanilla lightning behavior for now.

## Attributes

- Reborn uses the direct legacy Crazy Creeper attributes where the mapping is clear:
  - max health: `1500.0`
  - movement speed: `0.23`
  - knockback resistance: `1.0`
- Modern `generic.max_health` in NeoForge 1.21.1 is capped at `1024.0`, so the checked-in baseline keeps the legacy `1500.0` source literal for provenance while the runtime smoke expects the effective in-game max health to clamp to `1024.0`.
- Follow range intentionally stays on the vanilla creeper baseline because legacy `EntityCrazyCreeper#applyMobAttributes()` does not pin it directly.
- Attack damage intentionally stays on the vanilla creeper baseline because the legacy class does not pin it directly.
- XP reward is pinned to `50`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CrazyCreeperRenderer` that reuses the vanilla creeper renderer/model path with a custom texture.
- Legacy `RenderCrazyCreeper` only swaps the texture; it does not add a baseline-only scale or glow hook.
- The texture file is `assets/cavernreborn/textures/entity/crazy_creeper.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:crazy_creeper_spawn_egg`.
- Legacy `CaveEntityRegistry` has a clear spawn egg entry for this mob, so the baseline keeps it.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0x909090`
  - spot color: `0x2E8B57`

## Loot Decision

- The bounded baseline reuses the vanilla creeper loot table as its base drop source.
- Legacy `EntityCrazyCreeper` inherits the `1/5` `cavenic_orb` drop from `EntityCavenicCreeper`.
- The dedicated follow-up documented in `docs/crazy-creeper-loot-mvp.md` now restores that inherited orb branch explicitly while keeping the vanilla creeper loot table as the baseline drop source.
- Reborn does not add a custom Crazy Creeper loot table JSON.

## Natural Spawning

- Natural spawning is intentionally out of scope for this baseline slice.
- Legacy `EntityCrazyCreeper` appears in `CaveEntityRegistry.CRAZY_SPAWNS` with weight `1` and group size `1 / 1`.
- Legacy `EntityCrazyCreeper#getMaxSpawnedInChunk()` returns `1`.
- Like Crazy Zombie and Crazy Skeleton, that crazy roster is only selected from the old `WorldProviderCavenia#createSpawnCreature(...)` branch instead of the normal `CAVERN` hostile-biome path.
- Reborn therefore does not add a fake `CAVERN` spawn placement or biome modifier for Crazy Creeper.

## Fuse / Explosion Boundary

Legacy `EntityCrazyCreeper` includes a direct explosion identity:

- `applyCustomValues()` sets `fuseTime = 150`
- `applyCustomValues()` sets `explosionRadius = 30`
- legacy base `EntityCavenicCreeper` also changes the default creeper explosion path and carries its own smaller `Fuse = 15` and `ExplosionRadius = 5` values

This baseline does not restore any of that. Reborn does not restore:

- custom fuse/explosion behavior
- custom swelling behavior
- custom griefing behavior
- custom explosion-event logic

The baseline stayed safe because vanilla `Creeper` already exists and can spawn/function without those legacy Crazy Creeper-specific explosion branches.

## Lightning / Charged / Boss / Particle / Damage Boundaries

- Legacy `EntityCrazyCreeper` has direct green boss-bar behavior through `BossInfo.Color.GREEN`.
- Legacy `EntityCrazyCreeper` has a direct client-only `ParticleCrazyMob` trail in `onUpdate()`.
- Legacy `EntityCrazyCreeper` inherits fall/fire damage behavior from `EntityCavenicCreeper`.
- Legacy `EntityCrazyCreeper` inherits the `cavenic_orb` drop from `EntityCavenicCreeper`.
- Legacy `EntityCrazyCreeper` overrides `canBeRidden(Entity entity)` to return `false`.
- This baseline originally kept an explicit damage behavior boundary.
- The inherited damage branch is now restored explicitly in `docs/crazy-creeper-damage-behavior-mvp.md` while the rest of the baseline stays narrow.
- The inherited loot/orb branch is now restored explicitly in `docs/crazy-creeper-loot-mvp.md` while the rest of the baseline stays narrow.
- The dedicated follow-up documented in `docs/crazy-creeper-boss-bar-mvp.md` now restores the legacy green progress boss-event / sky-darkening branch explicitly while keeping the vanilla `Creeper` base.
- The dedicated follow-up documented in `docs/crazy-creeper-particle-trail-mvp.md` now restores the legacy client-only `ParticleCrazyMob` trail explicitly while reusing the shared `cavernreborn:crazy_mob` particle mapping.
- This baseline still does not restore any lightning/charged/swelling follow-up.

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources, the explicit vanilla `Creeper` base, the explicit vanilla creeper loot-table baseline, the explicit no-natural-spawn boundary, and the continued absence of custom fuse/explosion and lightning/charged/swelling follow-up code beyond the explicitly restored loot, damage, boss and particle branches.
- Documentation tests cover the legacy references inspected, the crazy-roster context, the attribute mapping, renderer/texture provenance, spawn egg decision, the Cavenia-tied natural-spawn deferral and the deferred explosion/lightning boundaries.
- NeoForge GameTest runtime smoke covers:
  - crazy creeper runtime registry id
  - crazy creeper attribute registration
  - crazy creeper hostile runtime spawn
  - crazy creeper spawn egg resolution
  - crazy creeper spawn egg entity creation
  - crazy creeper vanilla creeper loot-table baseline
  - crazy creeper explicit no-natural-spawn baseline boundary
  - separate legacy particle-trail smoke documented in `docs/crazy-creeper-particle-trail-mvp.md`
  - separate legacy boss-event / sky-darkening smoke documented in `docs/crazy-creeper-boss-bar-mvp.md`
  - crazy creeper custom fuse/explosion branch remains follow-up
  - crazy creeper custom lightning/charged/swelling behavior remains follow-up

## Out Of Scope

- Crazy Creeper natural spawning
- Crazy Creeper custom loot
- Crazy Creeper damage behavior
- Crazy Creeper custom fuse/explosion behavior
- Crazy Creeper lightning/charged/swelling behavior
- Crazy Spider
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
- magic-book or spell systems
