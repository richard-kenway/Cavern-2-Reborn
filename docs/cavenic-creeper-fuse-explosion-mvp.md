# Cavenic Creeper Legacy Fuse/Explosion MVP

This note documents the bounded fuse/explosion follow-up for the existing `cavernreborn:cavenic_creeper`.

It does not add a new mob, loot changes, natural-spawn changes, damage-behavior changes or custom AI. It only restores the confirmed legacy fixed creeper values from `EntityCavenicCreeper`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicCreeper`
- legacy `fuseTime` and `explosionRadius` fields
- legacy `applyCustomValues()`
- `CaveUtils.setPrivateValue` usage inside the old creeper implementation
- legacy `attackEntityFrom(DamageSource source, float damage)` and `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` overrides for regression context

## Exact Legacy Behavior Found

Legacy `EntityCavenicCreeper` declared:

- `fuseTime = 15`
- `explosionRadius = 5`

Its constructor then called legacy `applyCustomValues()`, which pushed those fixed values into the vanilla `EntityCreeper` private fields through `CaveUtils.setPrivateValue`.

## Reborn Mapping

- Reborn keeps the implementation entity-local in `app.entity.CavenicCreeper`.
- Modern 1.21.1 `Creeper` persists these values under the saved-data keys `Fuse` and `ExplosionRadius`.
- `CavenicCreeper` now uses constructor-time `applyLegacyFuseAndExplosionValues()` to pass a narrow `CompoundTag` through vanilla `Creeper.readAdditionalSaveData(...)`.
- `CavenicCreeper` also overrides `addAdditionalSaveData(CompoundTag tag)` and writes the same `Fuse` and `ExplosionRadius` values back into saved data so round trips stay pinned.

## Exact Reborn Behavior

- New `cavernreborn:cavenic_creeper` instances resolve `Fuse = 15`.
- New `cavernreborn:cavenic_creeper` instances resolve `ExplosionRadius = 5`.
- Saved cavenic creeper data keeps those values pinned across a save/load style round trip.
- vanilla creeper AI remains unchanged.
- explosion logic remains vanilla except for the fixed fuse/radius values.
- natural spawning remains unchanged.
- `1/5` orb-drop behavior remains unchanged.
- fall/fire damage behavior remains unchanged.

## Why This Mapping Is Safe

- The legacy behavior was only a fixed-value field write, not a custom explosion system.
- Reborn reuses the modern vanilla save-data hook that already owns `Fuse` and `ExplosionRadius`.
- No global explosion event, charged-creeper rewrite, block-griefing rule change or custom AI hook is needed to restore these values.

## Testing

- Resource tests pin `LEGACY_FUSE_TIME = 15`, `LEGACY_EXPLOSION_RADIUS = 5` and the narrow `CompoundTag` save-data wiring.
- Documentation tests pin the legacy source references, the exact values and the out-of-scope boundaries.
- NeoForge GameTest runtime smoke covers:
  - cavenic creeper legacy fuse-time smoke
  - cavenic creeper legacy explosion-radius smoke
  - cavenic creeper save/load style round-trip pinning
  - unchanged natural-spawn constants, `1/5` orb-drop policy, fall/fire damage behavior and vanilla creeper loot-table baseline

## Still Out Of Scope

- Custom explosion behavior remains out of scope
- charged creeper changes remain out of scope
- Block-destruction or griefing-rule changes remain out of scope
- Custom Cavenic Creeper AI remains out of scope
- Additional Cavenic mobs remain out of scope
- Cavenia remains out of scope
