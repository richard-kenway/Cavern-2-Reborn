# Cavenic Spider Legacy Orb Drop MVP

This note documents the bounded loot follow-up for the existing `cavernreborn:cavenic_spider`.

It does not add a new mob, natural-spawn changes, damage-behavior changes, the separately documented blindness-on-hit behavior, poison/web behavior or custom AI. It only restores the missing legacy `cavenic_orb` drop.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.entity.CaveEntityRegistry`
- `cavern.item.ItemCave`
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override

## Legacy Drop Mapping

- Legacy `EntityCavenicSpider` called `super.dropLoot(...)` first.
- It then ran `rand.nextInt(8) == 0`.
- On the winning roll it dropped one `cavenic_orb`.
- The legacy drop path did not use `lootingModifier`.
- The legacy drop path did not gate the orb behind player-kill checks, progression, economy or Cavenia-only rules.

## Reborn Implementation

- Reborn keeps the vanilla spider loot table as the base drop source.
- Reborn does not add a custom entity loot-table JSON for `cavenic_spider` in this slice.
- Instead, Reborn appends the orb through a small `LivingDropsEvent` listener in `app.entity.CavenicSpiderLootEvents`.
- The pure roll policy lives in `core.loot.CavenicSpiderLootPolicy`.
- `CavenicSpiderLootPolicy.ORB_DROP_ROLL_BOUND = 8` pins the legacy `1/8` chance.
- The winning roll is `0`, so the orb chance remains exactly `1/8`.

## Exact Drop Behavior

- `cavernreborn:cavenic_orb` is appended independently of the vanilla spider drops.
- The chance is `1/8`.
- A winning roll adds exactly one `cavernreborn:cavenic_orb`.
- A losing roll adds nothing.
- Looting does not affect the orb chance in this MVP.
- The orb drop does not require a player kill in this MVP.
- No progression, dimension or economy hook changes affect the orb drop.
- Natural spawn values, attributes and blindness-on-hit, poison, web and other special spider behavior do not change in this slice.

## Why Reborn Uses A Drop Event

- Legacy behavior already appended the orb after normal spider loot instead of replacing the base drop path.
- Reborn keeps that shape so vanilla spider drops stay intact without duplicating or guessing the full modern spider loot-table JSON.
- This keeps the change surgical and preserves the already accepted vanilla-spider-loot baseline.

## Testing

- Core tests pin the `1/8` orb-drop policy and reject out-of-range rolls.
- Resource tests pin:
  - continued vanilla spider loot-table baseline usage
  - `CavenicSpiderLootEvents` registration from `CavernReborn`
  - the `LivingDropsEvent` listener wiring
  - the absence of extra Cavenic mobs, Cavenia, economy/progression references or unrelated spider behavior changes
- NeoForge GameTest runtime smoke covers:
  - cavenic spider vanilla loot-table baseline smoke
  - cavenic spider legacy orb-drop event wiring smoke
  - cavenic spider legacy orb-drop deterministic winning/losing roll smoke

## Still Out Of Scope

- Natural spawn values remain unchanged
- The separate legacy damage-behavior follow-up is now documented separately in `docs/cavenic-spider-damage-behavior-mvp.md`.
- The separate blindness-on-hit follow-up is now documented separately in `docs/cavenic-spider-blindness-on-hit-mvp.md`.
- Poison behavior remains out of scope
- Web and other special spider behavior remain out of scope
- Custom Cavenic Spider AI remains out of scope
- Additional Cavenic mobs remain out of scope
- Cavenia remains out of scope
- Progression-gated or economy-gated drops remain out of scope
- Long-run orb-drop rate balance still needs manual gameplay validation
