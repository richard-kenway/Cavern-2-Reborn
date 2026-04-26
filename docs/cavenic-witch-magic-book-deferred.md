# Cavenic Witch Magic-Book Branch Deferred

This note documents the inspected legacy magic-book fallback branch for `cavernreborn:cavenic_witch` and why that branch remains deferred in Reborn.

It does not add a new mob, item, GUI, packet, keybind, advancement, spell framework or loot behavior. It only records the exact legacy findings and pins the reason Reborn still keeps the branch out of scope.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- `cavern.item.ItemMagicBook`
- `cavern.item.CaveItems`
- `cavern.capability.CaveCapabilities`
- `cavern.magic.MagicBook`
- `assets/cavern/lang/en_us.lang`
- `assets/cavern/models/item/magic_book_*.json`
- `assets/cavern/advancements/get_magic_book.json`
- `assets/cavern/advancements/magic_*.json`

## Exact Legacy `ItemMagicBook.getRandomBook()` Behavior Found

Legacy `ItemMagicBook` is registered as `cavern:magic_book`.

It is not a plain single-variant loot token:

- the item uses `setHasSubtypes(true)` and `setMaxStackSize(1)`
- `ItemMagicBook.EnumType` defines `12` subtype books:
  - `storage`
  - `heal`
  - `warp`
  - `teleport`
  - `torch`
  - `invisible`
  - `summon`
  - `explosion`
  - `thunderbolt`
  - `infinity`
  - `overload`
  - `physicalBoost`
- each subtype maps to a base mana value and a concrete legacy `Magic` implementation class
- `getRandomBook()` picks one random subtype from that enum table
- it then writes random `Mana` NBT onto the returned stack through `setMana(...)`
- the returned stack is `new ItemStack(CaveItems.MAGIC_BOOK, 1, subtypeMetadata)` plus that `Mana` NBT

Legacy `ItemMagicBook` also carries broader runtime behavior outside the witch drop:

- `LastUseTime` cooldown NBT
- subtype-specific display names and models
- client durability/effect display driven by spellbook state
- player `MagicBook` capability wiring
- storage-book `MAGIC_STORAGE` item capability wiring
- subtype-specific advancement coverage

That means `ItemMagicBook.getRandomBook()` already assumes a broader spellbook item family and state model.

## Exact Witch Fallback Branch Behavior Found

Legacy `EntityCavenicWitch#dropLoot(...)` first called `super.dropLoot(...)`.

After the vanilla witch loot path:

- it rolled `rand.nextInt(5) == 0` for exactly one `cavenic_orb`
- only when that orb roll failed, it rolled a second `rand.nextInt(5) == 0`
- that fallback roll dropped exactly one stack from `ItemMagicBook.getRandomBook()`
- the orb used the legacy `0.5F` drop offset
- the fallback magic-book branch used the legacy `0.25F` drop offset

The branch was still bounded:

- no `lootingModifier`
- no `wasRecentlyHit` gate
- no dimension gate
- no difficulty gate
- no progression gate
- no economy hook
- no Cavenia-only condition

## Decision

The branch remains deferred.

Reborn still does not have an honest modern foundation for the legacy `magic_book` line. Implementing it truthfully would require more than a single loot item:

- a registered `magic_book` item with subtype/state representation
- honest `Mana` and cooldown state handling
- subtype-specific model/lang coverage
- a bounded answer for the legacy player and item capability wiring
- a bounded answer for the subtype-to-magic dispatch that the legacy item already encodes

That is broader than this witch loot/foundation slice. Reborn therefore does not add:

- a fake placeholder `magic_book` item
- a dummy spellbook enum with no real meaning
- fake NBT that pretends to be a real spell system
- GUI, packets, keybinds or advancement ports just to satisfy the drop

## Current Reborn Behavior

- `CavenicWitch#getDefaultLootTable()` still preserves the vanilla witch loot table as the baseline path.
- `CavenicWitchLootEvents` still restores only the cleanly mappable legacy `1/5` `cavenic_orb` branch.
- `CavenicWitchLootPolicy` still models only the orb branch.
- Reborn still has no `magic_book`, `MagicBook`, or `ItemMagicBook` registry entry, item class, model or lang entry.

## Testing

- Resource tests pin that:
  - no `magic_book` registry item exists in `ModRegistries`
  - no `MagicBookItem` implementation exists in `app-neoforge`
  - no `magic_book` lang or item-model resource exists
  - `CavenicWitchLootPolicy` still contains only the orb branch
  - `CavenicWitchLootEvents` still appends only `cavenic_orb`
- Documentation tests pin:
  - the exact `ItemMagicBook` references inspected
  - the `12` subtype spellbook family
  - the random `Mana` NBT stack generation
  - the capability and advancement ties that keep the branch broad
- Runtime smoke docs explicitly keep the magic-book branch outside the current GameTest runtime layer.

## Still Out Of Scope

- Any broad magic-book or spell framework
- GUI, packets, keybinds or advancement ports for magic books
- Any non-witch magic-book usage
- Cavenia-specific magic-book behavior
- Additional Cavenic mobs
- Full legacy mob parity beyond the current bounded witch slices
