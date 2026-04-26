# Cavenic Bear Natural Spawn MVP

This note documents the bounded natural-spawn follow-up for the existing `cavenic_bear` baseline mob.

It does not add a new mob. It only makes the already registered `cavernreborn:cavenic_bear` spawn naturally inside the existing `CAVERN` gameplay context.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- `cavern.entity.CaveEntityRegistry`
- `assets/cavern/lang/en_us.lang`

## Legacy Spawn Behavior Found

- Legacy `cavern.entity.CaveEntityRegistry` added `EntityCavenicBear` to the shared Cavenic spawn list with:
  - weight: `30`
  - group size: `1..1`
- Legacy `EntityCavenicBear#getCanSpawnHere()` also required:
  - a snowy biome
  - a valid spawnable block below
  - low-enough light via the hostile-light checks
  - non-peaceful difficulty
- Legacy `EntityCavenicBear#getMaxSpawnedInChunk()` returned `1`.

## Spawn Placement Rule

- Spawn placement is registered from `RegisterSpawnPlacementsEvent`.
- Reborn uses `SpawnPlacementTypes.ON_GROUND`.
- Reborn uses `Heightmap.Types.MOTION_BLOCKING_NO_LEAVES`.
- The spawn predicate is intentionally small:
  - the level must be `cavernreborn:cavern`
  - then the mob delegates to vanilla `Monster.checkMonsterSpawnRules`
- Reborn also maps the legacy chunk cap directly through `getMaxSpawnClusterSize() = 1`.

## Why Reborn Uses Monster-Style Rules

- The legacy mob is a hostile `IMob` and was registered in the shared hostile Cavenic spawn list.
- Current `PolarBear.checkPolarBearSpawnRules` is a surface-animal rule tied to polar-bear biome tags and alternate spawn blocks.
- That vanilla polar-bear predicate conflicts with the accepted underground hostile `CAVERN` pattern already used for the other Cavenic mobs.
- Reborn therefore keeps the modern spawn gate CAVERN-only and delegates the light/block validation to `Monster.checkMonsterSpawnRules` instead of `PolarBear.checkPolarBearSpawnRules`.
- This is the intentional bounded deviation in the natural-spawn slice.

## Biome Modifier Targeting

- Reborn uses a NeoForge biome modifier at `data/cavernreborn/neoforge/biome_modifier/cavenic_bear_spawns.json`.
- The modifier type is `neoforge:add_spawns`.
- It targets the narrow biome tag `cavernreborn:spawns_cavenic_bear`.
- That biome tag is defined at `data/cavernreborn/tags/worldgen/biome/spawns_cavenic_bear.json`.
- That tag currently contains only the four checked-in `CAVERN` biomes:
  - `cavernreborn:stone_depths`
  - `cavernreborn:lush_grotto`
  - `cavernreborn:dripstone_grotto`
  - `cavernreborn:highland_hollows`
- Reborn keeps the targeting explicit instead of using broad overworld-style biome tags.

## Spawn Values

- The legacy weight and group values map cleanly to the modern NeoForge biome modifier, so Reborn preserves them exactly:
  - weight: `30`
  - group size: `1..1`
- The legacy `getMaxSpawnedInChunk() = 1` also maps cleanly, so Reborn restores that limit through `getMaxSpawnClusterSize() = 1`.

## Why It Is Limited To CAVERN

- The tagged biomes are only used by the checked-in `cavernreborn:cavern` dimension definition.
- The spawn predicate also hard-gates the runtime level key to `CAVERN`.
- Natural spawning is deliberately limited to `CAVERN`.
- This keeps the increment narrow and prevents accidental spawning in the Overworld, Nether, End or unrelated custom dimensions.

## Testing

- Resource tests cover:
  - spawn-placement registration source
  - the `neoforge:add_spawns` biome modifier JSON
  - the `cavernreborn:spawns_cavenic_bear` biome tag
  - legacy weight/group-size pinning
  - the restored max-spawn-cluster cap of `1`
  - the no-custom-loot / no-fall-fire / no-custom-bear-AI / no-Cavenia / no-magic-book boundaries
- NeoForge GameTest runtime smoke covers:
  - registered spawn placement type and heightmap
  - CAVERN-only spawn predicate allow/deny behavior
  - legacy `30 / 1 / 1` spawn-constant pinning
  - legacy max-spawn-cluster size `1`
  - biome modifier runtime registry resolution
  - biome tag runtime resolution
  - the existing baseline entity/attribute/spawn-egg smoke

## Still Out Of Scope

- the bounded baseline is still documented separately in `docs/cavenic-bear-baseline-mvp.md`
- the bounded damage follow-up is documented separately in `docs/cavenic-bear-damage-behavior-mvp.md`
- the inspected bear loot line is documented separately in `docs/cavenic-bear-loot-absent-or-deferred.md`
- custom loot, orb drop, custom bear AI, anger behavior, taming, riding and mount behavior remain out of scope
- custom loot remains out of scope
- no direct legacy `cavenic_orb` bear drop was found in the inspected source
- custom bear AI and anger rewrites remain out of scope
- taming, riding and mount behavior remain out of scope
- Cavenia remains out of scope
- The magic-book system remains intentionally untouched.
- additional Cavenic mobs remain out of scope
- long-run population balance tuning remains out of scope
