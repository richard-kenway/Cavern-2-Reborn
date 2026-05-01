# Crazy Skeleton Legacy Damage Behavior MVP

This note documents the bounded incoming-damage follow-up for the existing `cavernreborn:crazy_skeleton`.

It does not add natural spawning, equipment changes, loot changes beyond the separate orb-drop follow-up, custom ranged AI, boss-event changes, particles or broader crazy-mob combat systems. It only restores the small inherited legacy damage drift from `EntityCavenicSkeleton`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- legacy `attackEntityFrom(DamageSource source, float damage)` handling on `EntityCavenicSkeleton`
- legacy `setEquipmentBasedOnDifficulty(...)` and `initCustomAI()` on `EntityCrazySkeleton` for equipment/ranged-AI boundary context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` on `EntityCavenicSkeleton` for loot-regression context

## Exact Legacy Behavior Found

- `EntityCrazySkeleton` does not override `attackEntityFrom(...)`.
- It inherits `EntityCavenicSkeleton#attackEntityFrom(DamageSource source, float damage)`.
- That inherited method:
  - checks `source == DamageSource.FALL` and applies `damage *= 0.35F`
  - uses `!source.isFireDamage()` to guard the final super call
  - returns `!source.isFireDamage() && super.attackEntityFrom(source, damage)`
- So Crazy Skeleton still takes fall damage, but only at `35%` of the original amount.
- Crazy Skeleton rejects fire damage entirely.
- No extra direct incoming-damage immunity is declared on `EntityCrazySkeleton`.
- Generic non-fire, non-fall damage remains vanilla-like.
- The guaranteed `Cavenic Bow` + `Infinity` equipment path is separate and remains documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md`.
- The inherited `1/5` orb-drop branch is separate and remains documented in `docs/crazy-skeleton-loot-mvp.md`.
- The custom ranged AI / `EntityAIAttackCavenicBow` branch is separate and remains documented in `docs/crazy-skeleton-ranged-ai-boundary.md`.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CrazySkeleton`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazySkeleton` inherits the damage behavior from `EntityCavenicSkeleton`.
- Reborn `CrazySkeleton` intentionally extends vanilla `Skeleton`, not Reborn `CavenicSkeleton`.
- That baseline decision stays in place so Crazy Skeleton does not silently inherit staged Reborn natural-spawn wiring, orb-drop behavior or any later Cavenic Skeleton-specific follow-ups that still need separate inspection.
- Reborn therefore copies only the confirmed incoming-damage behavior explicitly onto `CrazySkeleton`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava and burning damage because they use fire-tagged damage sources in current 1.21.1 damage-type data.
- Generic non-fire, non-fall damage remains vanilla-like.
- Equipment remains unchanged.
- Loot/orb behavior remains unchanged.
- Crazy Skeleton still resolves the vanilla skeleton loot table through `EntityType.SKELETON.getDefaultLootTable()`.
- Crazy Skeleton still keeps the legacy `2000.0D` source literal in its attribute builder while the runtime effective max health remains clamped to `1024.0`.
- Crazy Skeleton still keeps the guaranteed `Cavenic Bow` + `Infinity` equipment identity and the legacy `1.0F` mainhand drop chance.
- Crazy Skeleton still keeps the inherited `1/5` orb-drop wiring through `CrazySkeletonLootEvents` and `CrazySkeletonLootPolicy`.
- Crazy Skeleton natural spawning remains explicitly deferred because the inspected legacy path is still tied to the old Cavenia-only crazy-roster provider/config branch.
- Crazy Skeleton custom ranged AI / `EntityAIAttackCavenicBow` remains deferred.
- Crazy Skeleton boss bar / sky-darkening now remains documented separately in `docs/crazy-skeleton-boss-bar-mvp.md`.
- Crazy Skeleton particle trail now remains documented separately in `docs/crazy-skeleton-particle-trail-mvp.md`.

## Testing

- Resource tests pin the entity-local damage hook, the exact fall/fire tag usage, the preserved `2000.0D` source literal, the preserved `1.0F` mainhand drop chance, the preserved boss-event field, the separate shared particle hook and the continued absence of natural-spawn wiring and custom ranged-AI overrides.
- Documentation tests pin the legacy source references, the inherited `0.35F` fall multiplier, the explicit copy-not-inherit mapping and the unchanged equipment/loot/natural-spawn boundaries.
- NeoForge GameTest runtime smoke covers:
  - crazy skeleton legacy fall-damage reduction smoke
  - crazy skeleton legacy fire-damage immunity smoke
  - crazy skeleton generic-damage baseline smoke
  - continued vanilla skeleton loot-table baseline stability
  - continued runtime `1024.0` max-health clamp stability
  - continued guaranteed `Cavenic Bow` + `Infinity` equipment stability
  - continued inherited `1/5` orb-drop wiring stability
  - continued natural-spawn deferral through absent placement/biome-modifier/biome-tag wiring

## Still Out Of Scope

- `EntityAIAttackCavenicBow`
- Crazy Skeleton custom ranged AI
- Crazy Skeleton natural spawning
- Crazy Creeper / Crazy Spider
- Cavenia
- magic-book or spell systems
