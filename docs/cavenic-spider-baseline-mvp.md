# Cavenic Spider Baseline MVP

This note documents the fourth direct Cavenic mob foundation for modern Reborn.

It ports exactly one additional hostile mob:

- `cavernreborn:cavenic_spider`

The legacy repo contains a direct `EntityCavenicSpider`, so this increment stays narrow and does not invent a fallback mob.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.client.renderer.RenderCavenicSpider`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/cavenic_spider.png`
- `assets/cavern/lang/en_us.lang`

## Selected Mob

- Reborn selects `cavernreborn:cavenic_spider` because legacy Cavern 2 already contains a direct `EntityCavenicSpider` mapping with a clear name, texture, renderer, spawn egg identity and baseline attribute set.
- This keeps the increment limited to one new Cavenic mob foundation instead of widening into the broader legacy mob roster.

## Runtime Shape

- `app.entity.CavenicSpider` extends vanilla `Spider`.
- The mob keeps vanilla spider AI, climbing behavior and vanilla spider combat behavior for this baseline.
- The runtime entity id is `cavernreborn:cavenic_spider`.
- The spawn egg id is `cavernreborn:cavenic_spider_spawn_egg`.

## Attributes

- Reborn uses the direct legacy Cavenic Spider attributes where the mapping is clear:
  - max health: `20.0`
  - movement speed: `0.6`
  - knockback resistance: `1.0`
- follow range intentionally stays on the vanilla spider baseline because legacy `applyMobAttributes()` did not pin it explicitly.
- attack damage intentionally stays on the vanilla spider baseline because legacy `EntityCavenicSpider` did not pin it explicitly.
- XP reward is pinned to `12`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CavenicSpiderRenderer` that reuses the vanilla spider renderer/model path with a custom texture.
- The texture file is `assets/cavernreborn/textures/entity/cavenic_spider.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:cavenic_spider_spawn_egg`.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0xAAAAAA`
  - spot color: `0x811F1F`

## Loot Decision

- The bounded baseline reuses the vanilla spider loot table as its base drop source so the mob has safe hostile drops without widening the Cavenic loot line yet.
- Legacy `EntityCavenicSpider` also added a `1/8` `cavenic_orb` drop.
- That drop remains out of scope for this baseline slice and is left for a later bounded follow-up.

## Follow-Up Boundaries

- The bounded natural-spawn follow-up is now documented separately in `docs/cavenic-spider-natural-spawn-mvp.md`.
- custom loot remains out of scope
- `1/8` `cavenic_orb` drop parity remains out of scope
- fall/fire damage behavior remains out of scope
- blindness-on-hit behavior remains out of scope
- poison behavior remains out of scope
- custom web or special spider attack behavior remains out of scope
- the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope
- Cavenia remains out of scope

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources and the explicit no-natural-spawn/no-custom-loot/no-custom-damage boundary.
- Documentation tests cover the legacy references inspected, the attribute mapping, spawn egg colors, renderer/texture provenance and the explicit special-spider follow-up boundaries.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - hostile runtime spawn
  - vanilla spider loot-table baseline
  - spawn egg resolution
  - spawn egg entity creation

## Out Of Scope

- Natural spawning and biome spawn balance
- Custom Cavenic Spider loot
- Legacy `1/8` `cavenic_orb` drop parity
- Legacy fall/fire damage behavior
- Blindness, poison, web or other special spider behavior
- Additional Cavenic mobs
- Cavenia
- Broad combat rewrites
- Cavenic Bow changes
