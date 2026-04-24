# Cavenic Zombie Legacy Orb Drop MVP

This note documents the bounded loot follow-up for the existing `cavernreborn:cavenic_zombie`.

It does not add a new mob, new AI, new spawn rules or broader Cavenic progression systems. It only restores the first missing legacy loot behavior: the rare `cavenic_orb` drop.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.entity.CaveEntityRegistry`
- `cavern.item.ItemCave`
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override

## Legacy Drop Mapping

- Legacy `EntityCavenicZombie` called `super.dropLoot(...)` first.
- It then ran `rand.nextInt(8) == 0`.
- On the winning roll it dropped one `cavenic_orb`.
- The legacy drop path did not use `lootingModifier`.
- The legacy drop path did not gate the orb behind progression, economy or Cavenia-only rules.

## Reborn Implementation

- Reborn keeps the vanilla zombie loot table as the base drop source.
- Reborn does not add a custom entity loot-table JSON for `cavenic_zombie` in this slice.
- Instead, Reborn appends the orb through a small `LivingDropsEvent` listener in `app.entity.CavenicZombieLootEvents`.
- The pure roll policy lives in `core.loot.CavenicZombieLootPolicy`.
- `CavenicZombieLootPolicy.ORB_DROP_ROLL_BOUND = 8` pins the legacy `1/8` chance.
- The winning roll is `0`, so the orb chance remains exactly `1/8`.

## Exact Drop Behavior

- `cavernreborn:cavenic_orb` is appended independently of the vanilla zombie drops.
- The orb chance is `1/8`.
- A winning roll adds exactly one `cavernreborn:cavenic_orb`.
- A losing roll adds nothing.
- Looting does not affect the orb chance in this MVP.
- The orb drop does not require a player kill in this MVP.
- No progression, dimension or economy hook changes affect the orb drop.
- No natural-spawn values change in this slice.

## Why Reborn Uses A Drop Event

- Legacy behavior already appended the orb after normal zombie loot instead of replacing the base drop path.
- Reborn keeps that shape so vanilla zombie drops stay intact without duplicating or guessing the full modern zombie loot-table JSON.
- This keeps the change surgical and preserves the already accepted vanilla-zombie-loot baseline.

## Testing

- Core tests pin the `1/8` orb-drop policy and reject out-of-range rolls.
- Resource tests pin:
  - continued vanilla zombie loot-table baseline usage
  - `CavenicZombieLootEvents` registration from `CavernReborn`
  - the `LivingDropsEvent` listener wiring
  - the absence of extra Cavenic mobs, Cavenia or economy references
- NeoForge GameTest runtime smoke covers:
  - cavenic zombie vanilla loot-table baseline smoke
  - cavenic zombie legacy orb-drop event wiring smoke
  - cavenic zombie legacy orb-drop deterministic winning/losing roll smoke

## Still Out Of Scope

- The legacy fall/fire damage behavior remains documented separately in `docs/cavenic-zombie-damage-behavior-mvp.md`
- Additional Cavenic mobs remain out of scope
- Custom Cavenic Zombie AI remains out of scope
- Cavenia remains out of scope
- Progression-gated or economy-gated drops remain out of scope
- Broader loot refactors remain out of scope
- Long-run orb-drop rate balance still needs manual gameplay validation
