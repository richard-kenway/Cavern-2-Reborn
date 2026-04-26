# Cavenic Bear Loot Absent / Deferred

This note documents the inspected legacy loot line for `cavenic_bear` and the reason the current Reborn bear still keeps only the vanilla polar bear loot baseline.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- `cavern.entity.CaveEntityRegistry`
- `cavern.item.ItemCave`

I also searched the adjacent legacy repo for:

- `dropLoot`
- `CAVENIC_ORB`
- `rand.nextInt`
- `lootingModifier`
- `wasRecentlyHit`
- `getDropItem`
- `getLootTable`

## Exact Legacy Loot Behavior Found

- Legacy `EntityCavenicBear` does not override `dropLoot(...)`.
- Legacy `EntityCavenicBear` does not override `getLootTable()`.
- Legacy `EntityCavenicBear` does not reference `ItemCave.EnumType.CAVENIC_ORB`.
- Legacy `EntityCavenicBear` does not contain any bear-specific fallback drop branch.
- Legacy `EntityCavenicBear` does override `canDropLoot()` and returns `true`, but that only keeps the superclass loot path enabled.

The source-confirmed result is simple: the legacy bear had no direct custom bear loot branch to port in this bounded slice.

## Vanilla Polar Bear Loot Baseline

- Reborn continues to preserve the vanilla polar bear loot baseline through `CavenicBear#getDefaultLootTable()`.
- `CavenicBear#getDefaultLootTable()` still returns `EntityType.POLAR_BEAR.getDefaultLootTable()`.
- Reborn does not add a custom `cavenic_bear` loot-table JSON.
- Reborn does not add a `LivingDropsEvent` hook for `CavenicBear`.
- Reborn does not add a pure `CavenicBearLootPolicy`.

## Orb-Drop Finding

- No legacy `cavenic_orb` bear branch was found.
- Because the source contains no direct bear orb roll, Reborn does not invent one.
- There is no source-confirmed bear roll bound, winning roll, fallback branch, extra item count, or bear-specific Y offset to port.

## Looting, Player Kill, Progression And Dimension

- The inspected legacy bear class does not define a custom loot branch, so there is no custom bear drop path for looting to scale.
- The inspected legacy bear class does not define a custom bear drop path gated on player kill.
- The inspected legacy bear class does not define a custom bear drop path tied to difficulty, dimension, progression, economy or Cavenia.
- Natural spawning remains documented separately in `docs/cavenic-bear-natural-spawn-mvp.md`.
- The restored damage behavior is documented separately in `docs/cavenic-bear-damage-behavior-mvp.md`.

## Why This Slice Does Not Add New Loot Code

- A loot event or pure policy would be fake behavior here because the legacy bear has no small direct drop branch to restore.
- This bounded slice therefore keeps gameplay unchanged and pins the finding explicitly instead of widening into invented orb logic.
- The current bear baseline stays honest to the inspected source.

## Testing

- Resource tests assert that no `CavenicBearLootEvents` file exists.
- Resource tests assert that no `CavenicBearLootPolicy` file exists.
- Resource tests assert that `CavenicBear` still resolves the vanilla polar bear loot table.
- Resource tests assert that no custom `cavenic_bear` loot-table JSON exists.
- Documentation tests assert the inspected source finding and the explicit no-custom-loot boundary.
- Existing GameTest runtime smoke still covers the vanilla polar bear loot-table baseline for `cavenic_bear`.

## Still Out Of Scope

- custom bear loot, because no direct legacy branch was found
- any invented `cavenic_orb` bear drop
- fall/fire damage behavior beyond the dedicated damage slice
- custom bear AI and anger rewrites
- taming, riding and mount behavior
- Cavenia-specific behavior
- magic-book systems
- additional Cavenic mobs
