# Crazy Creeper Legacy Damage Behavior MVP

This note documents the bounded incoming-damage follow-up for the existing `cavernreborn:crazy_creeper`.

It does not add natural spawning, loot changes, particles, custom fuse/explosion behavior, lightning/charged/swelling behavior or broader crazy-mob combat systems. It only restores the small inherited legacy damage drift from `EntityCavenicCreeper`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- legacy `attackEntityFrom(DamageSource source, float damage)` handling on `EntityCavenicCreeper`
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` on `EntityCavenicCreeper` for loot-regression context
- legacy `applyCustomValues()` on `EntityCrazyCreeper` and `EntityCavenicCreeper` for fuse/explosion boundary context

## Exact Legacy Behavior Found

- `EntityCrazyCreeper` does not override `attackEntityFrom(...)`.
- It inherits `EntityCavenicCreeper#attackEntityFrom(DamageSource source, float damage)`.
- That inherited method:
  - checks `source == DamageSource.FALL` and applies `damage *= 0.35F`
  - uses `!source.isFireDamage()` to guard the final super call
  - returns `!source.isFireDamage() && super.attackEntityFrom(source, damage)`
- So Crazy Creeper still takes fall damage, but only at `35%` of the original amount.
- Crazy Creeper rejects fire damage entirely.
- No extra direct incoming-damage immunity is declared on `EntityCrazyCreeper`.
- Generic non-fire, non-fall damage remains vanilla-like.
- The inherited `1/5` orb-drop branch is separate and remains documented in `docs/crazy-creeper-loot-mvp.md`.
- The risky fuse/explosion and lightning/charged/swelling branches are separate and remain deferred.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CrazyCreeper`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazyCreeper` inherits the damage behavior from `EntityCavenicCreeper`.
- Reborn `CrazyCreeper` intentionally extends vanilla `Creeper`, not Reborn `CavenicCreeper`.
- That baseline decision stays in place so Crazy Creeper does not silently inherit staged Reborn natural-spawn wiring, orb-drop behavior, boss behavior, particle behavior or fuse/explosion behavior before each follow-up is inspected honestly.
- Reborn therefore copies only the confirmed incoming-damage behavior explicitly onto `CrazyCreeper`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava and burning damage because they use fire-tagged damage sources in current 1.21.1 damage-type data.
- Generic non-fire, non-fall damage remains vanilla-like.
- Loot/orb behavior remains unchanged.
- The vanilla creeper loot baseline remains unchanged.
- Crazy Creeper still resolves the vanilla creeper loot table through `EntityType.CREEPER.getDefaultLootTable()`.
- Crazy Creeper still keeps the legacy `1500.0D` source literal in its attribute builder while the runtime effective max health remains clamped to `1024.0`.
- Crazy Creeper still keeps the inherited `1/5` orb-drop wiring through `CrazyCreeperLootEvents` and `CrazyCreeperLootPolicy`.
- Natural-spawn deferral remains unchanged.
- Custom fuse/explosion behavior remains deferred.
- Lightning/charged/swelling behavior remains deferred.
- Boss bar / sky-darkening now remains documented separately in `docs/crazy-creeper-boss-bar-mvp.md`.
- Particle trail now remains documented separately in `docs/crazy-creeper-particle-trail-mvp.md`.
- Crazy Spider, Cavenia and magic-book systems remain out of scope.

## Testing

- Resource tests pin the entity-local damage hook, the exact fall/fire tag usage, the preserved `1500.0D` source literal, the preserved vanilla creeper loot-table baseline, the preserved `CrazyCreeperLootEvents` / `CrazyCreeperLootPolicy` wiring and the continued absence of natural-spawn, fuse/explosion and lightning follow-up code beyond the separately restored boss and particle branches.
- Documentation tests pin the legacy source references, the inherited `0.35F` fall multiplier, the explicit copy-not-inherit mapping and the unchanged loot/natural-spawn/fuse boundaries.
- NeoForge GameTest runtime smoke covers:
  - crazy creeper legacy fall-damage reduction smoke
  - crazy creeper legacy fire-damage immunity smoke
  - crazy creeper generic-damage baseline smoke
  - continued vanilla creeper loot-table baseline stability
  - continued runtime `1024.0` max-health clamp stability
  - continued inherited `1/5` orb-drop wiring stability
  - continued natural-spawn deferral through absent placement/biome-modifier/biome-tag wiring
  - continued absence of fuse/explosion and lightning overrides beyond the separately restored boss and particle branches

## Still Out Of Scope

- Crazy Creeper natural spawning
- Crazy Creeper custom fuse/explosion behavior
- Crazy Creeper lightning/charged/swelling behavior
- Crazy Spider
- Cavenia
- magic-book or spell systems
