# Cavenic Creeper Baseline MVP

This note documents the third direct Cavenic mob foundation for modern Reborn.

It ports exactly one additional hostile mob:

- `cavernreborn:cavenic_creeper`

The legacy repo contains a direct `EntityCavenicCreeper`, so this increment stays narrow and does not invent a fallback mob.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.client.renderer.RenderCavenicCreeper`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/cavenic_creeper.png`
- `assets/cavern/lang/en_us.lang`

## Selected Mob

- Reborn selects `cavernreborn:cavenic_creeper` because legacy Cavern 2 already contains a direct `EntityCavenicCreeper` mapping with a clear name, texture, renderer, spawn egg identity and baseline attribute set.
- This keeps the increment limited to one new Cavenic mob foundation instead of widening into the broader legacy mob roster.

## Runtime Shape

- `app.entity.CavenicCreeper` extends vanilla `Creeper`.
- The mob keeps vanilla creeper AI and vanilla explosion behavior for this baseline.
- The runtime entity id is `cavernreborn:cavenic_creeper`.
- The spawn egg id is `cavernreborn:cavenic_creeper_spawn_egg`.

## Attributes

- Reborn uses the direct legacy Cavenic Creeper attributes where the mapping is clear:
  - max health: `30.0`
  - movement speed: `0.2`
  - knockback resistance: `0.85`
- follow range intentionally stays on the vanilla creeper baseline because legacy `applyMobAttributes()` did not pin it explicitly.
- XP reward is pinned to `13`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CavenicCreeperRenderer` that reuses the vanilla creeper renderer/model path with a custom texture.
- The texture file is `assets/cavernreborn/textures/entity/cavenic_creeper.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:cavenic_creeper_spawn_egg`.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0xAAAAAA`
  - spot color: `0x2E8B57`

## Loot Decision

- Legacy `EntityCavenicCreeper` also added a `1/5` `cavenic_orb` drop.
- That orb-drop follow-up is now documented separately in `docs/cavenic-creeper-orb-drop-mvp.md`.
- The bounded baseline reuses the vanilla creeper loot table as its base drop source so the mob has safe hostile drops without widening the Cavenic loot line yet.

## Follow-Up Boundaries

- Legacy `EntityCavenicCreeper` also set `fuseTime = 15` and `explosionRadius = 5`.
- Custom explosion behavior remains out of scope for this baseline slice.
- Legacy `EntityCavenicCreeper` also included a `1/5` `cavenic_orb` drop and the same fall/fire damage behavior later restored for `cavenic_zombie` and `cavenic_skeleton`.
- Those loot and damage follow-ups remain intentionally out of scope here.
- The legacy fall/fire damage behavior remains out of scope in this baseline slice.
- The bounded natural-spawn follow-up is documented separately in `docs/cavenic-creeper-natural-spawn-mvp.md`.
- The bounded legacy orb-drop follow-up is now documented separately in `docs/cavenic-creeper-orb-drop-mvp.md`.
- custom AI remains out of scope.
- Cavenia remains out of scope.

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources and the explicit no-natural-spawn/no-custom-loot boundary.
- Documentation tests cover the legacy references inspected, the attribute mapping, spawn egg colors, renderer/texture provenance and the explicit explosion/loot/spawn boundaries.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - hostile runtime spawn
  - vanilla creeper loot-table baseline
  - spawn egg resolution
  - spawn egg entity creation

## Out Of Scope

- Natural spawning and biome spawn balance
- Custom Cavenic Creeper loot
- Legacy `1/5` `cavenic_orb` drop parity
- Legacy fall/fire damage behavior
- Custom Cavenic Creeper AI
- Custom explosion behavior parity
- Additional Cavenic mobs
- Cavenia
- Broad combat rewrites
- Cavenic Bow changes
