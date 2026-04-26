# Cavenic Bear Baseline MVP

This note documents the sixth direct Cavenic mob foundation for modern Reborn.

It ports exactly one additional legacy Cavenic mob:

- `cavernreborn:cavenic_bear`

The legacy repo contains a direct `EntityCavenicBear`, so this increment stays narrow and does not invent a replacement mob.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- `cavern.client.renderer.RenderCavenicBear`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/cavenic_bear.png`
- `assets/cavern/lang/en_us.lang`

## Selected Mob

- Reborn selects `cavernreborn:cavenic_bear` because legacy Cavern 2 already contains a direct `EntityCavenicBear` mapping with a clear registry name, display name, spawn egg identity, renderer path and texture.
- The legacy class directly extended `EntityPolarBear`, so the closest modern baseline is also a `PolarBear`-based entity.
- This keeps the increment limited to one new direct Cavenic mob foundation instead of widening into additional Cavenic or Cavenia-specific behavior.

## Runtime Shape

- `app.entity.CavenicBear` extends vanilla `PolarBear`.
- The runtime entity id is `cavernreborn:cavenic_bear`.
- The spawn egg id is `cavernreborn:cavenic_bear_spawn_egg`.
- Reborn keeps the vanilla polar bear AI and base behavior for this baseline slice.
- The entity type category is `MobCategory.MONSTER`, matching the legacy hostile `IMob` classification and the legacy shared hostile spawn list.

## Attributes

- Reborn uses the direct legacy Cavenic Bear attributes where the mapping is clear:
  - max health: `60.0`
  - movement speed: `0.3`
  - attack damage: `7.0`
- follow range intentionally stays on the vanilla polar bear baseline because the legacy bear did not pin it explicitly in `applyEntityAttributes()`.
- XP reward is pinned to `13`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CavenicBearRenderer` that reuses the vanilla polar bear renderer/model path with a custom texture.
- The texture file is `assets/cavernreborn/textures/entity/cavenic_bear.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:cavenic_bear_spawn_egg`.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0xAAAAAA`
  - spot color: `0xFFFFFF`

## Loot Decision

- The bounded baseline reuses the vanilla polar bear loot table as its base drop source so the mob has a safe baseline without widening the Cavenic bear loot line yet.
- Custom loot remains out of scope.
- The legacy bear also has natural spawning, fall/fire damage behavior and custom AI/hostile behavior that were follow-up work beyond the baseline slice.
- The bounded hostile-targeting follow-up is now documented separately in `docs/cavenic-bear-hostile-targeting-mvp.md`.
- The bounded melee-attack follow-up is now documented separately in `docs/cavenic-bear-melee-attack-mvp.md`.
- The bounded panic-behavior follow-up is now documented separately in `docs/cavenic-bear-panic-behavior-mvp.md`.
- The inspected legacy bear loot line is now documented separately in `docs/cavenic-bear-loot-absent-or-deferred.md`.
- That follow-up found no direct legacy `dropLoot(...)` override and no `cavenic_orb` bear branch to port.

## Follow-Up Boundaries

- Natural spawning was intentionally out of scope for the baseline slice and is now covered separately in `docs/cavenic-bear-natural-spawn-mvp.md`.
- fall/fire damage behavior was intentionally out of scope for the baseline slice and is now covered separately in `docs/cavenic-bear-damage-behavior-mvp.md`.
- bounded hostile-targeting follow-up is now documented separately in `docs/cavenic-bear-hostile-targeting-mvp.md`.
- bounded melee-attack follow-up is now documented separately in `docs/cavenic-bear-melee-attack-mvp.md`.
- bounded panic-behavior follow-up is now documented separately in `docs/cavenic-bear-panic-behavior-mvp.md`.
- The inspected bear loot line is now covered separately in `docs/cavenic-bear-loot-absent-or-deferred.md`.
- broader custom bear AI, anger behavior, taming, riding and mount behavior remain out of scope.
- custom loot remains out of scope, and the current source inspection found no direct legacy `cavenic_orb` bear branch to restore.
- Cavenia remains out of scope.
- The magic-book system remains intentionally untouched.

## Testing

- Resource tests cover registry source, spawn egg placement, attribute registration source, renderer registration source, texture/model/lang resources and the explicit out-of-scope boundaries around natural spawn, custom loot, damage behavior, hostile targeting, melee behavior, panic behavior, broader custom AI and magic-book systems.
- Documentation tests cover the legacy references inspected, the base-class mapping, the legacy attribute mapping, spawn egg colors, renderer/texture provenance and the explicit baseline-only boundaries.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - runtime entity spawn
  - vanilla polar bear loot-table baseline
  - spawn egg resolution
  - spawn egg entity creation

## Out Of Scope

- Natural spawning in the baseline slice
- Custom loot
- `cavenic_orb` bear drops
- Fall/fire damage behavior in the baseline slice
- Custom bear AI and anger behavior
- Taming, riding and mount behavior
- Cavenia-specific behavior
- The magic-book system
- Additional Cavenic mobs
- Broad combat or renderer refactors
