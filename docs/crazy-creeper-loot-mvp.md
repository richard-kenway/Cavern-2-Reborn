# Crazy Creeper Legacy Loot / Orb Drop MVP

This note documents the bounded inherited Crazy Creeper loot follow-up on top of the existing Crazy Creeper baseline.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.item.ItemCave`

## Legacy Loot Behavior Found

- `EntityCrazyCreeper` does not override `dropLoot(...)`.
- It inherits `EntityCavenicCreeper#dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`.
- That inherited branch calls `super.dropLoot(...)` first, so vanilla creeper loot remains the baseline drop source.
- The inherited extra branch then runs `rand.nextInt(5) == 0`.
- On the winning roll it appends `ItemCave.EnumType.CAVENIC_ORB.getItemStack()` with offset `0.5F`.
- The winning roll is `0`, so the orb chance remains exactly `1/5`.

## Conditions

- Looting does not affect the new orb drop.
- A player kill is not required for the inherited orb branch.
- No progression, dimension, economy or Cavenia check is part of the inherited orb branch.
- No boss state affects the inherited orb branch.
- No fuse state, charged state or lightning state affects the inherited orb branch.
- The risky Crazy Creeper fuse/explosion behavior is separate from this inherited loot branch and remains deferred.

## Reborn Mapping

- Reborn `CrazyCreeper` still extends vanilla `Creeper`, not Reborn `CavenicCreeper`.
- Reborn keeps the vanilla creeper loot table as the baseline drop source through `EntityType.CREEPER.getDefaultLootTable()`.
- Reborn restores the inherited orb branch explicitly through `CrazyCreeperLootEvents` and `CrazyCreeperLootPolicy`.
- `CrazyCreeperLootPolicy.ORB_DROP_ROLL_BOUND = 5`.
- `CrazyCreeperLootEvents` listens to `LivingDropsEvent`, ignores client-side drops, checks `event.getEntity() instanceof CrazyCreeper`, and appends one `cavernreborn:cavenic_orb` at `crazyCreeper.getY() + 0.5D` on winning roll `0`.
- Reborn copies the behavior explicitly instead of extending Reborn `CavenicCreeper` so Crazy Creeper does not silently inherit the separate damage, boss, particle, spawn or fuse/explosion slices.

## Boundaries Preserved

- Natural spawning remains deferred.
- Damage behavior remains unchanged in this loot slice.
- Damage behavior now remains documented separately in `docs/crazy-creeper-damage-behavior-mvp.md`.
- Boss bar / sky-darkening now remains documented separately in `docs/crazy-creeper-boss-bar-mvp.md`.
- Particle trail remains unchanged in this loot slice.
- Custom fuse/explosion behavior remains unchanged in this loot slice.
- Lightning / charged / swelling behavior remains unchanged in this loot slice.
- Cavenia remains out of scope.
- Magic-book systems remain out of scope.
- Other crazy variants remain out of scope.

## Testing

- Core unit tests pin `CrazyCreeperLootPolicy.ORB_DROP_ROLL_BOUND = 5`, the winning roll `0`, every losing roll, and invalid-roll rejection.
- Resource tests cover:
  - `CrazyCreeperLootEvents`
  - `CrazyCreeperLootPolicy`
  - event registration in `CavernReborn`
  - continued vanilla creeper loot-table baseline
  - continued absence of natural-spawn resources
  - continued absence of damage, boss, particle, fuse/explosion and lightning follow-up code
- NeoForge GameTest runtime smoke covers:
  - crazy creeper vanilla creeper loot-table baseline
  - crazy creeper legacy orb-drop event wiring
  - deterministic winning and losing rolls
  - expected `0.5D` drop offset
  - continued max-health clamp and baseline attribute stability
  - continued no-natural-spawn boundary
  - continued no-particle / no-fuse overrides, with damage and boss behavior documented separately

## Out Of Scope

- Crazy Creeper natural spawning
- Crazy Creeper damage behavior
- Crazy Creeper particle trail
- Crazy Creeper custom fuse/explosion behavior
- Crazy Creeper lightning/charged/swelling behavior
- Crazy Spider
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
