# Cavenic Creeper Legacy Orb Drop MVP

This note documents the bounded loot follow-up for the existing `cavernreborn:cavenic_creeper`.

It does not add a new mob, natural-spawn changes, damage-behavior changes, explosion tuning or custom AI. It only restores the missing legacy `cavenic_orb` drop.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.entity.CaveEntityRegistry`
- `cavern.item.ItemCave`
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override

## Legacy Drop Mapping

- Legacy `EntityCavenicCreeper` called `super.dropLoot(...)` first.
- It then ran `rand.nextInt(5) == 0`.
- On the winning roll it dropped one `cavenic_orb`.
- The legacy drop path did not use `lootingModifier`.
- The legacy drop path did not gate the orb behind player-kill checks, progression, economy or Cavenia-only rules.

## Reborn Implementation

- Reborn keeps the vanilla creeper loot table as the base drop source.
- Reborn does not add a custom entity loot-table JSON for `cavenic_creeper` in this slice.
- Instead, Reborn appends the orb through a small `LivingDropsEvent` listener in `app.entity.CavenicCreeperLootEvents`.
- The pure roll policy lives in `core.loot.CavenicCreeperLootPolicy`.
- `CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND = 5` pins the legacy `1/5` chance.
- The winning roll is `0`, so the orb chance remains exactly `1/5`.

## Exact Drop Behavior

- `cavernreborn:cavenic_orb` is appended independently of the vanilla creeper drops.
- The chance is `1/5`.
- A winning roll adds exactly one `cavernreborn:cavenic_orb`.
- A losing roll adds nothing.
- Looting does not affect the orb chance in this MVP.
- The orb drop does not require a player kill in this MVP.
- No progression, dimension or economy hook changes affect the orb drop.
- Natural spawn values, attributes and fuse/explosion behavior do not change in this slice.

## Why Reborn Uses A Drop Event

- Legacy behavior already appended the orb after normal creeper loot instead of replacing the base drop path.
- Reborn keeps that shape so vanilla creeper drops stay intact without duplicating or guessing the full modern creeper loot-table JSON.
- This keeps the change surgical and preserves the already accepted vanilla-creeper-loot baseline.

## Testing

- Core tests pin the `1/5` orb-drop policy and reject out-of-range rolls.
- Resource tests pin:
  - continued vanilla creeper loot-table baseline usage
  - `CavenicCreeperLootEvents` registration from `CavernReborn`
  - the `LivingDropsEvent` listener wiring
  - the absence of extra Cavenic mobs, Cavenia, economy/progression references or unrelated creeper behavior changes
- NeoForge GameTest runtime smoke covers:
  - cavenic creeper vanilla loot-table baseline smoke
  - cavenic creeper legacy orb-drop event wiring smoke
  - cavenic creeper legacy orb-drop deterministic winning/losing roll smoke

## Still Out Of Scope

- The separate legacy fall/fire damage follow-up is documented in `docs/cavenic-creeper-damage-behavior-mvp.md`
- The separate legacy fuse/explosion follow-up is documented in `docs/cavenic-creeper-fuse-explosion-mvp.md`
- Natural spawn values remain unchanged
- Custom Cavenic Creeper AI remains out of scope
- Additional Cavenic mobs remain out of scope
- Cavenia remains out of scope
- Progression-gated or economy-gated drops remain out of scope
- Long-run orb-drop rate balance still needs manual gameplay validation
