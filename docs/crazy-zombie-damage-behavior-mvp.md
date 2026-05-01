# Crazy Zombie Legacy Damage Behavior MVP

This note documents the bounded incoming-damage follow-up for the existing `cavernreborn:crazy_zombie`.

It does not add natural spawning, loot changes beyond the separate orb-drop follow-up, knockback changes, boss-event changes, custom AI or broader crazy-mob combat systems. It only restores the small inherited legacy damage drift from `EntityCavenicZombie`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCavenicZombie`
- legacy `attackEntityFrom(DamageSource source, float damage)` handling on `EntityCavenicZombie`
- legacy `attackEntityAsMob(Entity entity)` on `EntityCrazyZombie` for outgoing-combat boundary context
- legacy `BossInfoServer bossInfo` and `updateAITasks()` on `EntityCrazyZombie` for boss/visual boundary context

## Exact Legacy Behavior Found

- `EntityCrazyZombie` does not override `attackEntityFrom(...)`.
- It inherits `EntityCavenicZombie#attackEntityFrom(DamageSource source, float damage)`.
- That inherited method:
  - checks `source == DamageSource.FALL` and applies `damage *= 0.35F`
  - uses `!source.isFireDamage()` to guard the final super call
  - returns `!source.isFireDamage() && super.attackEntityFrom(source, damage)`
- So Crazy Zombie still takes fall damage, but only at `35%` of the original amount.
- Crazy Zombie rejects fire damage entirely.
- No extra direct incoming-damage immunity is declared on `EntityCrazyZombie`.
- Generic non-fire, non-fall damage stays vanilla-like.
- The legacy `attackEntityAsMob(...)` knockback branch is separate from incoming damage and is now documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`.
- The particle trail is now documented separately in `docs/crazy-zombie-particle-trail-mvp.md`.
- The separate boss-event follow-up is now documented in `docs/crazy-zombie-boss-bar-mvp.md`.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CrazyZombie`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazyZombie` inherits the damage behavior from `EntityCavenicZombie`.
- Reborn `CrazyZombie` intentionally extends vanilla `Zombie`, not Reborn `CavenicZombie`.
- That baseline decision stays in place so Crazy Zombie does not silently inherit staged Reborn Cavenic orb-drop wiring or any later Cavenic-specific follow-ups that still need separate inspection.
- Reborn therefore copies only the confirmed incoming-damage behavior explicitly onto `CrazyZombie`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava and burning damage because they use fire-tagged damage sources in current 1.21.1 damage-type data.
- Generic non-fire, non-fall damage remains vanilla-like.
- Crazy Zombie still resolves the vanilla zombie loot table through `EntityType.ZOMBIE.getDefaultLootTable()`.
- The separate inherited orb-drop follow-up is documented in `docs/crazy-zombie-loot-mvp.md`.
- The separate knockback-on-hit follow-up is documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`.
- The separate boss bar / sky-darkening behavior remains documented separately in `docs/crazy-zombie-boss-bar-mvp.md`.
- Crazy Zombie still keeps the legacy `2000.0D` source literal in its attribute builder while the runtime effective max health remains clamped to `1024.0`.
- Crazy Zombie natural spawning remains explicitly deferred because the inspected legacy path is still tied to the old Cavenia-only crazy-roster provider/config branch.

## Testing

- Resource tests pin the entity-local damage hook, the exact fall/fire tag usage, the preserved `2000.0D` source literal and the continued absence of natural-spawn wiring, while the inherited orb-drop, knockback, boss-event and particle-trail follow-ups stay isolated from this incoming-damage slice.
- Documentation tests pin the legacy source references, the inherited `0.35F` fall multiplier, the explicit copy-not-inherit mapping and the unchanged natural-spawn deferral.
- NeoForge GameTest runtime smoke covers:
  - crazy zombie legacy fall-damage reduction smoke
  - crazy zombie legacy fire-damage immunity smoke
  - crazy zombie generic-damage baseline smoke
  - continued vanilla zombie loot-table baseline stability
  - continued runtime `1024.0` max-health clamp stability
  - continued natural-spawn deferral through absent placement/biome-modifier/biome-tag wiring

## Still Out Of Scope

- Crazy Zombie natural spawning
- Crazy Zombie custom loot beyond the restored inherited orb-drop branch
- Crazy Zombie custom AI
- Crazy Skeleton baseline is now documented separately in `docs/crazy-skeleton-baseline-mvp.md`, while its ranged-AI and equipment follow-ups remain separate
- Crazy Creeper follow-up branches documented after `docs/crazy-creeper-baseline-mvp.md` / Crazy Spider
- Cavenia
- magic-book or spell systems
