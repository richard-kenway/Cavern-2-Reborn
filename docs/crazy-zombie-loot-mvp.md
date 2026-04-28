# Crazy Zombie Legacy Loot / Orb Drop Boundary MVP

This note documents the bounded loot follow-up for the existing `cavernreborn:crazy_zombie`.

It does not add natural spawning, damage-behavior changes, particle trail, custom AI or broader crazy-mob combat systems. It only restores the small inherited legacy loot drift from `EntityCavenicZombie`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.item.ItemCave`
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` handling on `EntityCavenicZombie`
- legacy `attackEntityAsMob(Entity entity)` on `EntityCrazyZombie` for outgoing-combat boundary context
- legacy `BossInfoServer bossInfo` and `updateAITasks()` on `EntityCrazyZombie` for boss/visual boundary context

## Exact Legacy Behavior Found

- `EntityCrazyZombie` does not override `dropLoot(...)`.
- It inherits `EntityCavenicZombie#dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`.
- That inherited method:
  - calls `super.dropLoot(...)` first
  - runs `rand.nextInt(8) == 0`
  - drops `ItemCave.EnumType.CAVENIC_ORB.getItemStack()` at offset `0.5F` on the winning roll
- The inherited drop branch does not use `lootingModifier`.
- The inherited drop branch does not check `wasRecentlyHit`.
- No direct Crazy Zombie loot-table override was found.
- No direct Crazy Zombie non-orb custom drop branch was found.
- The legacy `attackEntityAsMob(...)` knockback branch is separate from loot handling and is now documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`.
- The client particle trail remains out of scope for this slice.
- The separate boss-event follow-up is now documented in `docs/crazy-zombie-boss-bar-mvp.md`.

## Reborn Mapping

- Reborn preserves the vanilla zombie loot table as its baseline drop source through `CrazyZombie#getDefaultLootTable()`.
- Reborn does not add a custom entity loot-table JSON for `crazy_zombie`.
- Reborn appends the inherited legacy orb drop through `app.entity.CrazyZombieLootEvents`.
- The pure roll policy lives in `core.loot.CrazyZombieLootPolicy`.
- `CrazyZombieLootPolicy.ORB_DROP_ROLL_BOUND = 8` pins the inherited legacy `1/8` chance.
- The winning roll is `0`, so the orb chance remains exactly `1/8`.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazyZombie` inherits the loot behavior from `EntityCavenicZombie`.
- Reborn `CrazyZombie` intentionally extends vanilla `Zombie`, not Reborn `CavenicZombie`.
- That baseline decision stays in place so Crazy Zombie does not silently inherit staged Reborn Cavenic natural-spawn wiring, orb-drop behavior or later Cavenic-specific follow-ups that still need separate inspection.
- Reborn therefore copies only the confirmed inherited loot branch explicitly onto `CrazyZombie`.

## Exact Reborn Behavior

- Vanilla zombie loot remains the base loot path.
- `cavernreborn:cavenic_orb` is appended independently of the vanilla zombie drops.
- The orb chance is `1/8`.
- A winning roll adds exactly one `cavernreborn:cavenic_orb`.
- A losing roll adds nothing.
- The appended drop uses the inherited legacy `0.5D` Y offset.
- Looting does not affect the new orb drop.
- A player kill is not required for the orb drop, because the inspected legacy branch ignores `wasRecentlyHit`.
- Progression, dimension, economy, Cavenia, boss state and spawn path do not affect this restored orb drop.
- Crazy Zombie natural spawning remains explicitly deferred because the inspected legacy path is still tied to the old Cavenia-only crazy-roster provider/config branch.
- Crazy Zombie damage behavior remains documented separately in `docs/crazy-zombie-damage-behavior-mvp.md`.
- Crazy Zombie knockback-on-hit behavior remains documented separately in `docs/crazy-zombie-knockback-on-hit-mvp.md`.
- Crazy Zombie boss bar / sky-darkening behavior remains documented separately in `docs/crazy-zombie-boss-bar-mvp.md`.

## Testing

- Core tests pin the `1/8` orb-drop policy and reject out-of-range rolls.
- Resource tests pin:
  - continued vanilla zombie loot-table baseline usage
  - `CrazyZombieLootEvents` registration from `CavernReborn`
  - the `LivingDropsEvent` listener wiring
  - the continued `Zombie` base instead of Reborn `CavenicZombie`
  - the preserved `2000.0D` source literal and `0.35F` damage multiplier constants
  - the continued absence of natural-spawn wiring and particle code
- Documentation tests pin the legacy source references, the inherited `1/8` orb chance, the explicit copy-not-inherit mapping and the unchanged natural-spawn deferral.
- NeoForge GameTest runtime smoke covers:
  - crazy zombie vanilla loot-table baseline smoke
  - crazy zombie legacy orb-drop event wiring smoke
  - crazy zombie legacy orb-drop deterministic winning/losing roll smoke
  - continued runtime `1024.0` max-health clamp stability
  - continued inherited damage-behavior stability
  - continued natural-spawn deferral through absent placement/biome-modifier/biome-tag wiring

## Still Out Of Scope

- Crazy Zombie natural spawning
- Crazy Zombie damage behavior changes
- Crazy Zombie particle trail
- Crazy Zombie custom AI
- Crazy Zombie custom loot beyond the restored inherited orb-drop branch
- Crazy Skeleton / Crazy Creeper / Crazy Spider
- Cavenia
- magic-book or spell systems
