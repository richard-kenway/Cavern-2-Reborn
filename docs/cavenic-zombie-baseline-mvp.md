# Cavenic Zombie Baseline MVP

This note documents the first bounded Cavenic mob foundation for modern Reborn.

It ports exactly one hostile mob:

- `cavernreborn:cavenic_zombie`

The legacy repo contains a direct `EntityCavenicZombie`, so this increment keeps the selection narrow and does not introduce fallback Cavenic mobs.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.client.renderer.RenderCavenicZombie`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/textures/entity/cavenic_zombie.png`
- `assets/cavern/lang/en_us.lang`

## Runtime Shape

- `app.entity.CavenicZombie` extends vanilla `Zombie`.
- The mob keeps vanilla zombie AI and general hostile behavior for this MVP.
- The runtime entity id is `cavernreborn:cavenic_zombie`.
- The spawn egg id is `cavernreborn:cavenic_zombie_spawn_egg`.

## Attributes

- Reborn uses the direct legacy Cavenic Zombie combat attributes where the mapping is clear:
  - max health: `50.0`
  - follow range: `50.0`
  - knockback resistance: `1.0`
  - attack damage: `5.0`
- Movement speed intentionally stays on the vanilla zombie baseline of `0.23`.
- XP reward is pinned to `12`, matching the legacy mob.

## Renderer And Texture

- Reborn uses a dedicated `CavenicZombieRenderer` that reuses the vanilla zombie renderer/model path with a custom texture.
- The texture file is `assets/cavernreborn/textures/entity/cavenic_zombie.png`.
- That texture was copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.

## Spawn Egg

- Reborn adds `cavernreborn:cavenic_zombie_spawn_egg`.
- Spawn egg colors follow the clear legacy registry values:
  - base color: `0xAAAAAA`
  - spot color: `0x00A0A0`

## Loot Decision

- Legacy `EntityCavenicZombie` also dropped `cavenic_orb` with a `1/8` chance and included extra fall/fire damage behavior.
- Reborn intentionally did not port those extra combat or orb-drop rules in the first baseline entity slice.
- The bounded baseline reuses the vanilla zombie loot table as its base drop source so the mob has safe hostile drops without dragging in broader Cavenic progression balance yet.
- Legacy orb-drop parity is now documented separately in `docs/cavenic-zombie-orb-drop-mvp.md`.

## Natural Spawning

- Natural spawning was intentionally left out of scope for this baseline MVP.
- The spawn-egg-driven slice stabilized entity registration, attributes, assets and runtime spawning first.
- Natural spawning is now documented separately in `docs/cavenic-zombie-natural-spawn-mvp.md`.

## Testing

- Resource tests cover registry source, spawn egg placement, renderer registration source, texture/model/lang resources and the explicit no-natural-spawn boundary.
- Documentation tests cover the legacy references inspected, the attribute mapping, renderer/texture provenance, loot decision and natural-spawn boundary.
- NeoForge GameTest runtime smoke covers:
  - entity registry resolution
  - attribute registration
  - hostile runtime spawn
  - spawn egg resolution
  - spawn egg entity creation

## Out Of Scope

- Additional Cavenic mobs
- Natural spawning and biome spawn balance
- Custom Cavenic Zombie AI
- Special attacks or transformations
- Boss behavior
- Cavenia
- Broad combat rewrites
- Cavenic Bow changes
