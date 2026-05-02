# Crazy Spider Legacy Loot / Orb Drop MVP

This note documents the bounded inherited Crazy Spider loot follow-up on top of the existing Crazy Spider baseline.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySpider`
- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.item.ItemCave`

## Legacy Loot Behavior Found

- `EntityCrazySpider` does not override `dropLoot(...)`.
- It inherits `EntityCavenicSpider#dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`.
- That inherited branch calls `super.dropLoot(...)` first, so vanilla spider loot remains the baseline drop source.
- The inherited extra branch then runs `rand.nextInt(8) == 0`.
- On the winning roll it appends `ItemCave.EnumType.CAVENIC_ORB.getItemStack()` with offset `0.5F`.
- The winning roll is `0`, so the orb chance remains exactly `1/8`.

## Conditions

- Looting does not affect the new orb drop.
- A player kill is not required for the inherited orb branch.
- No progression, dimension, economy or Cavenia check is part of the inherited orb branch.
- No boss state affects the inherited orb branch.
- No combat state, blindness state or poison state affects the inherited orb branch.
- The risky Crazy Spider combat identity is separate from this inherited loot branch and remains deferred.

## Reborn Mapping

- Reborn `CrazySpider` still extends vanilla `Spider`, not Reborn `CavenicSpider`.
- Reborn keeps the vanilla spider loot table as the baseline drop source through `EntityType.SPIDER.getDefaultLootTable()`.
- Reborn restores the inherited orb branch explicitly through `CrazySpiderLootEvents` and `CrazySpiderLootPolicy`.
- `CrazySpiderLootPolicy.ORB_DROP_ROLL_BOUND = 8`.
- `CrazySpiderLootEvents` listens to `LivingDropsEvent`, ignores client-side drops, checks `event.getEntity() instanceof CrazySpider`, and appends one `cavernreborn:cavenic_orb` at `crazySpider.getY() + 0.5D` on winning roll `0`.
- Reborn copies the behavior explicitly instead of extending Reborn `CavenicSpider` so Crazy Spider does not silently inherit the separate damage, combat, boss, particle or spawn slices.

## Boundaries Preserved

- Natural spawning remains deferred.
- Damage behavior now remains documented separately in `docs/crazy-spider-damage-behavior-mvp.md`.
- Boss bar / sky-darkening remains unchanged in this loot slice.
- Particle trail remains unchanged in this loot slice.
- Custom combat, blindness and poison behavior remains unchanged in this loot slice.
- Custom AI remains unchanged in this loot slice.
- Cavenia remains out of scope.
- Magic-book systems remain out of scope.
- Other variants remain out of scope.

## Testing

- Core unit tests pin `CrazySpiderLootPolicy.ORB_DROP_ROLL_BOUND = 8`, the winning roll `0`, every losing roll, and invalid-roll rejection.
- Resource tests cover:
  - `CrazySpiderLootEvents`
  - `CrazySpiderLootPolicy`
  - event registration in `CavernReborn`
  - continued vanilla spider loot-table baseline
  - continued absence of natural-spawn resources
  - continued absence of direct damage, boss, particle and custom combat follow-up code
- NeoForge GameTest runtime smoke covers:
  - crazy spider vanilla spider loot-table baseline
  - crazy spider legacy orb-drop event wiring
  - deterministic winning and losing rolls
  - expected `0.5D` drop offset
  - continued max-health clamp and baseline attribute stability
  - continued no-natural-spawn boundary
  - continued no-custom-combat / blindness / poison baseline boundary

## Out Of Scope

- Crazy Spider natural spawning
- Crazy Spider damage behavior
- Crazy Spider boss bar / sky-darkening
- Crazy Spider particle trail
- Crazy Spider custom combat / blindness / poison behavior
- Crazy Spider custom AI
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
