# Cavenic Skeleton Baseline MVP

This note documents the second direct Cavenic mob foundation for modern Reborn.

It ports exactly one additional hostile mob:

- `cavernreborn:cavenic_skeleton`

The legacy repo contains a direct `EntityCavenicSkeleton`, so this increment keeps the selection narrow and does not introduce fallback Cavenic mobs.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.client.renderer.RenderCavenicSkeleton`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/cavenic_skeleton.png`
- `assets/cavern/lang/en_us.lang`

## Selected Mob

- Reborn selects `cavernreborn:cavenic_skeleton` because legacy Cavern 2 already contains a direct `EntityCavenicSkeleton` mapping with a clear name, texture, renderer and spawn egg identity.
- This keeps the increment bounded to one new Cavenic mob foundation instead of widening into the broader Cavenic roster.

## Runtime Shape

- `app.entity.CavenicSkeleton` extends vanilla `Skeleton`.
- The mob keeps vanilla skeleton AI and vanilla bow/ranged behavior for this MVP.
- The runtime entity id is `cavernreborn:cavenic_skeleton`.
- The spawn egg id is `cavernreborn:cavenic_skeleton_spawn_egg`.

## Attributes

- Reborn uses the direct legacy Cavenic Skeleton combat attributes where the mapping is clear:
  - max health: `40.0`
  - movement speed: `0.2`
  - follow range: `21.0`
  - knockback resistance: `1.0`
- attack damage intentionally stays on the vanilla skeleton baseline because legacy `applyMobAttributes()` did not pin it explicitly.
- XP reward is pinned to `13`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CavenicSkeletonRenderer` that reuses the vanilla skeleton renderer/model path with a custom texture.
- The renderer also keeps the clear legacy `1.1x` visual scale from `RenderCavenicSkeleton`.
- The texture file is `assets/cavernreborn/textures/entity/cavenic_skeleton.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:cavenic_skeleton_spawn_egg`.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0xAAAAAA`
  - spot color: `0xDDDDDD`

## Loot Decision

- Legacy `EntityCavenicSkeleton` also added a `1/5` `cavenic_orb` drop.
- Reborn intentionally does not port that loot behavior in this baseline slice.
- The bounded baseline reuses the vanilla skeleton loot table as its base drop source so the mob has safe hostile drops without widening the Cavenic loot line yet.

## Follow-Up Boundaries

- Legacy `EntityCavenicSkeleton` also included custom melee/ranged AI switching, a dedicated Cavenic bow attack goal and custom `EntityCavenicArrow` handling.
- Reborn intentionally keeps vanilla skeleton AI and vanilla projectile behavior in this baseline slice.
- Legacy `EntityCavenicSkeleton` also included the same fall/fire damage behavior later restored for `cavenic_zombie`.
- That damage behavior is intentionally left for a later skeleton-specific follow-up instead of expanding this baseline slice.
- Natural spawning is now documented separately in `docs/cavenic-skeleton-natural-spawn-mvp.md`.
- Custom AI and custom projectile behavior remain out of scope.

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources and the explicit no-natural-spawn/no-custom-loot boundary.
- Documentation tests cover the legacy references inspected, the attribute mapping, spawn egg colors, renderer/texture provenance and the staged follow-up boundaries.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - hostile runtime spawn
  - spawn egg resolution
  - spawn egg entity creation

## Out Of Scope

- Natural spawning and biome spawn balance
- Custom Cavenic Skeleton loot
- `cavenic_orb` skeleton drops
- Custom Cavenic Skeleton AI
- Custom projectile entities or projectile rewrites
- Special attacks or transformations
- Boss behavior
- Additional Cavenic mobs
- Cavenia
- Broad combat rewrites
- Cavenic Bow changes
