# Crazy Skeleton Baseline MVP

This note documents the second bounded crazy-variant mob foundation for modern Reborn.

It ports exactly one additional crazy mob:

- `cavernreborn:crazy_skeleton`

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.client.renderer.RenderCrazySkeleton`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/crazy_skeleton.png`
- `assets/cavern/lang/en_us.lang`

## Crazy Roster Context

The inspected direct crazy roster in legacy Cavern 2 is:

- `EntityCrazyZombie`
- `EntityCrazySkeleton`
- `EntityCrazyCreeper`
- `EntityCrazySpider`

`Crazy Skeleton` is the next safe baseline after `Crazy Zombie` because the legacy class has:

- a clear direct class and registry id
- a clear display name, renderer, texture and spawn egg entry
- a safe modern vanilla `Skeleton` base in 1.21.1
- direct baseline attributes and XP reward on the class itself

The risky parts are separate follow-up branches rather than prerequisites for the entity to exist:

- legacy `EntityAIAttackCavenicBow`
- inherited `cavenic_orb` drop behavior from `EntityCavenicSkeleton`
- inherited fall/fire damage behavior from `EntityCavenicSkeleton`
- direct boss-bar / sky-darkening behavior
- direct client-only particle trail
- legacy crazy-roster natural spawning through the old `Cavenia` provider/config path

`Crazy Creeper` and `Crazy Spider` remain later crazy-variant follow-up candidates.

## Runtime Shape

- The runtime entity id is `cavernreborn:crazy_skeleton`.
- The spawn egg id is `cavernreborn:crazy_skeleton_spawn_egg`.
- Reborn `CrazySkeleton` extends vanilla `Skeleton`.
- Legacy `EntityCrazySkeleton` extends `EntityCavenicSkeleton`, but this baseline does not extend the current Reborn `CavenicSkeleton` baseline because that would silently inherit staged Reborn orb-drop, damage-behavior and natural-spawn slices before each Crazy Skeleton follow-up is inspected honestly.
- The original baseline kept vanilla skeleton AI, vanilla skeleton ranged behavior and vanilla equipment rules.
- The dedicated follow-up documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md` now restores the guaranteed legacy `Cavenic Bow` + `Infinity` mainhand identity and the legacy `1.0F` mainhand drop chance without changing the vanilla `Skeleton` base.
- The custom ranged AI identity is still deferred and documented in `docs/crazy-skeleton-ranged-ai-boundary.md`.

## Attributes

- Reborn uses the direct legacy Crazy Skeleton attributes where the mapping is clear:
  - max health: `2000.0`
  - movement speed: `0.25`
  - follow range: `22.0`
  - knockback resistance: `1.0`
- Modern `generic.max_health` in NeoForge 1.21.1 is capped at `1024.0`, so the checked-in baseline keeps the legacy `2000.0` source literal for provenance while the runtime smoke expects the effective in-game max health to clamp to `1024.0`.
- Attack damage intentionally stays on the vanilla skeleton baseline because legacy `EntityCrazySkeleton#applyMobAttributes()` does not pin it directly.
- XP reward is pinned to `50`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CrazySkeletonRenderer` that reuses the vanilla skeleton renderer/model path with a custom texture.
- The renderer keeps the clear legacy `1.1x` visual scale from `RenderCrazySkeleton`.
- The texture file is `assets/cavernreborn/textures/entity/crazy_skeleton.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:crazy_skeleton_spawn_egg`.
- Legacy `CaveEntityRegistry` has a clear spawn egg entry for this mob, so the baseline keeps it.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0x909090`
  - spot color: `0xDDDDDD`

## Loot Decision

- The bounded baseline reuses the vanilla skeleton loot table as its base drop source.
- Legacy `EntityCrazySkeleton` inherits the `1/5` `cavenic_orb` drop from `EntityCavenicSkeleton`.
- That inherited loot branch is intentionally deferred in this baseline slice.
- Reborn does not add a Crazy Skeleton loot event, loot policy or custom loot table yet.

## Natural Spawning

- Natural spawning is intentionally out of scope for this baseline slice.
- Legacy `EntityCrazySkeleton` appears in `CaveEntityRegistry.CRAZY_SPAWNS` with weight `1` and group size `1 / 1`.
- Legacy `EntityCrazySkeleton#getMaxSpawnedInChunk()` returns `1`.
- Like Crazy Zombie, that crazy roster is only selected from the old `WorldProviderCavenia#createSpawnCreature(...)` branch instead of the normal `CAVERN` hostile-biome path.
- Reborn therefore does not add a fake `CAVERN` spawn placement or biome modifier for Crazy Skeleton.

## Cavenic Bow Equipment / Ranged AI Boundary

Legacy `EntityCrazySkeleton` includes a separate ranged-combat identity:

- the constructor sets mainhand drop chance to `1.0F`
- `initCustomAI()` swaps in `EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`
- the custom melee fallback uses speed `1.35D`
- `setEquipmentBasedOnDifficulty(...)` always equips `CaveItems.CAVENIC_BOW`
- that bow is forced to carry `Enchantments.INFINITY`

Reborn now restores only the equipment-only portion in the dedicated follow-up:

- guaranteed `Cavenic Bow`
- guaranteed `Infinity`
- constructor sets mainhand drop chance to `1.0F`

Reborn still does not port the custom AI/combat portion:

- no `EntityAIAttackCavenicBow`
- no custom ranged-combat loop
- no Cavenic Bow behavior changes

The baseline stayed safe because vanilla `Skeleton` already exists and can spawn/function without those legacy Crazy Skeleton-specific ranged branches, and the later equipment-only slice still keeps that same vanilla `Skeleton` base intact.

## Other Deferred Follow-Ups

- inherited fall/fire damage behavior from `EntityCavenicSkeleton`
- inherited `cavenic_orb` drop behavior from `EntityCavenicSkeleton`
- boss bar / sky-darkening behavior
- client-only particle trail
- `isNonBoss()`
- `canBeRidden(...)`
- lightning immunity override
- natural spawning through the old Cavenia crazy-roster provider/config path

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources, the explicit vanilla `Skeleton` base, the explicit vanilla skeleton loot-table baseline, the restored guaranteed `Cavenic Bow` + `Infinity` equipment hook and the continued absence of natural-spawn, loot, damage, boss, particle and `EntityAIAttackCavenicBow` ports.
- Documentation tests cover the legacy references inspected, the crazy-roster context, the attribute mapping, renderer/texture provenance, spawn egg decision, the Cavenia-tied natural-spawn deferral, the dedicated equipment follow-up and the deferred custom ranged AI boundary.
- NeoForge GameTest runtime smoke covers:
  - crazy skeleton runtime registry id
  - attribute registration
  - hostile runtime spawn
  - spawn egg resolution
  - spawn egg entity creation
  - vanilla skeleton loot-table baseline
  - guaranteed Cavenic Bow equipment smoke
  - Infinity enchantment smoke
  - mainhand drop chance smoke
  - explicit no-natural-spawn baseline boundary

## Out Of Scope

- Crazy Skeleton natural spawning
- Crazy Skeleton custom loot
- Crazy Skeleton `cavenic_orb` drop follow-up
- Crazy Skeleton damage-behavior follow-up
- Crazy Skeleton boss bar / sky-darkening follow-up
- Crazy Skeleton particle-trail follow-up
- Crazy Skeleton custom ranged AI
- `EntityAIAttackCavenicBow`
- Crazy Creeper / Crazy Spider
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
- magic-book or spell systems
