# Cavenic Witch Natural Spawn MVP

This note documents the bounded natural-spawn follow-up for the existing `cavenic_witch` baseline mob.

It does not add a new mob. It only makes the already registered `cavernreborn:cavenic_witch` spawn naturally inside the existing `CAVERN` gameplay context.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/lang/en_us.lang`

## Spawn Placement Rule

- Spawn placement is registered from `RegisterSpawnPlacementsEvent`.
- Reborn uses `SpawnPlacementTypes.ON_GROUND`.
- Reborn uses `Heightmap.Types.MOTION_BLOCKING_NO_LEAVES`.
- The spawn predicate is intentionally small:
  - the level must be `cavernreborn:cavern`
  - then the mob delegates to vanilla `Monster.checkMonsterSpawnRules`
- This keeps the block/light logic witch-like while hard-gating the mob to the `CAVERN` dimension.

## Biome Modifier Targeting

- Reborn uses a NeoForge biome modifier at `data/cavernreborn/neoforge/biome_modifier/cavenic_witch_spawns.json`.
- The modifier type is `neoforge:add_spawns`.
- It targets the narrow biome tag `cavernreborn:spawns_cavenic_witch`.
- That biome tag is defined at `data/cavernreborn/tags/worldgen/biome/spawns_cavenic_witch.json`.
- That tag currently contains only the four checked-in `CAVERN` biomes:
  - `cavernreborn:stone_depths`
  - `cavernreborn:lush_grotto`
  - `cavernreborn:dripstone_grotto`
  - `cavernreborn:highland_hollows`
- Reborn keeps the targeting explicit instead of using broad overworld-style biome tags.

## Spawn Values

- Legacy `cavern.entity.CaveEntityRegistry` added `EntityCavenicWitch` to the shared Cavenic spawn list with:
  - weight: `15`
  - group size: `1..1`
- Those values map cleanly to the modern NeoForge biome modifier, so Reborn preserves them here.

## Why It Is Limited To CAVERN

- The tagged biomes are only used by the checked-in `cavernreborn:cavern` dimension definition.
- The spawn predicate also hard-gates the runtime level key to `CAVERN`.
- Natural spawning is deliberately limited to `CAVERN`.
- This keeps the increment narrow and prevents accidental spawning in the Overworld, Nether, End or unrelated custom dimensions.

## Testing

- Resource tests cover:
  - spawn-placement registration source
  - the `neoforge:add_spawns` biome modifier JSON
  - the `cavernreborn:spawns_cavenic_witch` biome tag
  - legacy weight/group-size pinning
  - the no-custom-loot / no-custom-potion-ranged-behavior / no-Cavenia boundary
- NeoForge GameTest runtime smoke covers:
  - registered spawn placement type and heightmap
  - CAVERN-only spawn predicate allow/deny behavior
  - biome modifier runtime registry resolution
  - biome tag runtime resolution
  - the existing baseline entity/attribute/spawn-egg smoke

## Still Out Of Scope

- The bounded legacy loot follow-up is now documented separately in `docs/cavenic-witch-loot-mvp.md`.
- The bounded legacy damage-behavior follow-up is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`.
- the restored legacy `1/5` `cavenic_orb` drop remains unchanged
- the deferred magic-book branch still needs a Reborn magic-book foundation
- the restored legacy fall-damage reduction and fire-damage immunity behavior remain unchanged
- custom potion logic, custom ranged attack behavior, friendship targeting and same-type projectile immunity remain out of scope
- the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope
- additional Cavenic mobs remain out of scope
- Cavenia remains out of scope
- long-run population balance tuning remains out of scope
