# Cavenic Witch Legacy Loot MVP

This note documents the bounded legacy-loot follow-up for the existing `cavenic_witch` baseline mob.

It does not add a new mob. It preserves the vanilla witch loot table as the base path and restores only the cleanly mappable legacy `cavenic_orb` branch.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- `cavern.entity.CaveEntityRegistry`
- `cavern.item.ItemCave`
- `cavern.item.ItemMagicBook`

## Legacy Loot Behavior Found

Legacy `EntityCavenicWitch` first calls `super.dropLoot(...)`.

After the vanilla witch loot path:

- it drops exactly one `cavenic_orb` when `rand.nextInt(5) == 0`
- otherwise it evaluates a fallback magic-book branch with another `rand.nextInt(5) == 0`
- that fallback branch drops exactly one random book from `ItemMagicBook.getRandomBook()`
- the orb uses the legacy `0.5F` drop offset
- the fallback magic-book branch uses the legacy `0.25F` drop offset

That means the orb chance is `1/5`, and the fallback magic-book branch is only evaluated after orb failure.

Legacy `dropLoot(...)` does not use `lootingModifier`, `wasRecentlyHit`, dimension, difficulty, progression or economy state for either branch.

## Reborn Mapping

- Reborn preserves the vanilla witch loot table as the baseline by keeping `CavenicWitch#getDefaultLootTable()` on `EntityType.WITCH.getDefaultLootTable()`.
- Reborn adds a small `LivingDropsEvent` listener in `app.entity.CavenicWitchLootEvents`.
- That listener appends exactly one `cavernreborn:cavenic_orb` when the bounded orb roll wins.
- The orb chance is pinned through `core.loot.CavenicWitchLootPolicy` with:
  - `ORB_DROP_ROLL_BOUND = 5`
  - winning roll `0`
- The appended orb keeps the legacy-style `0.5D` Y offset and default pickup delay.

## Magic-Book Branch

- The magic-book branch is deferred.
- The detailed deferred analysis is now documented separately in `docs/cavenic-witch-magic-book-deferred.md`.
- Legacy `ItemMagicBook.getRandomBook()` is not a plain loot-token factory. It chooses one of `12` subtype spellbooks, writes random `Mana` NBT to the returned stack and depends on broader magic-book runtime state.
- The legacy `magic_book` item is tied into subtype models/lang keys, cooldown/use NBT, player and item capabilities, storage-book inventory wiring, magic-class dispatch and advancement coverage.
- Reborn still does not expose an honest `magic_book`, `MagicBook` or `ItemMagicBook` foundation for that broader system.
- This increment does not invent a placeholder item, a fake spell system or a broad magic-book registry just to satisfy that legacy fallback path.

## Drop Boundaries

- Looting does not affect the orb branch.
- Player kill is not required for the orb branch.
- Progression, dimension and economy do not affect the orb branch.
- Natural spawn values remain unchanged.
- Fall/fire damage behavior remains unchanged and is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`.
- Same-type/self source immunity remains unchanged and is now documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`.
- Friendship targeting remains unchanged and is now documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`.
- The bounded custom ranged-potion follow-up is now documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`.

## Testing

- Core tests pin the orb-roll bound and winning/losing behavior in `CavenicWitchLootPolicyTest`.
- Resource tests pin:
  - the witch-only `LivingDropsEvent` listener
  - the vanilla witch loot-table baseline
  - the orb policy reference
  - the appended `cavenic_orb`
  - the absence of any fake magic-book placeholder
- NeoForge GameTest runtime smoke covers:
  - vanilla witch loot-table baseline retention
  - deterministic losing/winning orb rolls
  - the legacy-style `0.5D` orb-drop offset
  - unchanged witch natural-spawn constants

## Still Out Of Scope

- The deferred legacy magic-book branch
- Fall/fire damage behavior
- Same-type/self source immunity
- Any broader potion/raid behavior beyond the restored ranged-potion follow-up
- Same-type friendship targeting
- Additional Cavenic mobs
- Cavenia
- Long-run witch drop-rate balance tuning
