# Cavenic Witch Baseline MVP

This note documents the fifth direct Cavenic mob foundation for modern Reborn.

It ports exactly one additional hostile mob:

- `cavernreborn:cavenic_witch`

The legacy repo contains a direct `EntityCavenicWitch`, so this increment stays narrow and does not invent a fallback mob.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- `cavern.client.renderer.RenderCavenicWitch`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/cavenic_witch.png`
- `assets/cavern/lang/en_us.lang`

## Selected Mob

- Reborn selects `cavernreborn:cavenic_witch` because legacy Cavern 2 already contains a direct `EntityCavenicWitch` mapping with a clear name, texture, renderer, spawn egg identity and baseline attribute set.
- This keeps the increment limited to one new Cavenic mob foundation instead of widening into the broader legacy mob roster.

## Runtime Shape

- `app.entity.CavenicWitch` extends vanilla `Witch`.
- The runtime entity id is `cavernreborn:cavenic_witch`.
- The spawn egg id is `cavernreborn:cavenic_witch_spawn_egg`.
- The bounded baseline keeps vanilla witch AI, vanilla drinking behavior, vanilla potion-throw behavior and vanilla ranged hostility.

## Attributes

- Reborn uses the direct legacy Cavenic Witch attributes where the mapping is clear:
  - max health: `50.0`
  - follow range: `32.0`
  - knockback resistance: `1.0`
- movement speed intentionally stays on the vanilla witch baseline because legacy `applyMobAttributes()` did not pin it explicitly.
- potion-throw behavior intentionally stays on the vanilla witch baseline because the richer legacy ranged-attack tuning is too coupled to old APIs for this bounded foundation slice.
- XP reward is pinned to `12`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CavenicWitchRenderer` that reuses the vanilla witch renderer/model path with a custom texture.
- The texture file is `assets/cavernreborn/textures/entity/cavenic_witch.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:cavenic_witch_spawn_egg`.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0xAAAAAA`
  - spot color: `0x4A5348`

## Loot Decision

- The bounded baseline reuses the vanilla witch loot table as its base drop source so the mob has safe hostile drops without widening the Cavenic loot line yet.
- Legacy `EntityCavenicWitch` also added a `1/5` `cavenic_orb` drop and a fallback random magic-book branch.
- The bounded legacy loot follow-up is now documented separately in `docs/cavenic-witch-loot-mvp.md`.
- The bounded legacy damage-behavior follow-up is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`.
- custom loot beyond the restored `1/5` `cavenic_orb` drop remains out of scope for this baseline slice.
- the legacy magic-book branch remains deferred because Reborn does not yet have a magic-book foundation.

## Follow-Up Boundaries

- The bounded natural-spawn follow-up is now documented separately in `docs/cavenic-witch-natural-spawn-mvp.md`.
- legacy fall/fire damage behavior is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`.
- custom potion logic, friendship targeting and same-type projectile immunity remain out of scope
- the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope
- Cavenia remains out of scope

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources and the explicit bounded-follow-up boundaries around natural spawn, loot, damage and witch-specific behavior.
- Documentation tests cover the legacy references inspected, the attribute mapping, spawn egg colors, renderer/texture provenance and the explicit potion/damage follow-up boundaries.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - hostile runtime spawn
  - vanilla witch loot-table baseline
  - spawn egg resolution
  - spawn egg entity creation

## Out Of Scope

- Custom Cavenic Witch loot beyond the restored `1/5` orb-drop follow-up
- The deferred legacy magic-book branch
- Fall/fire damage behavior
- Custom potion logic, target filtering or friendly-fire immunity
- Long-run natural spawning and biome spawn balance
- Additional Cavenic mobs
- Cavenia
- Broad combat rewrites
- Cavenic Bow changes
