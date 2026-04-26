# Cavenic Witch Same-Type Projectile Immunity MVP

This note documents the bounded projectile-immunity follow-up for the existing `cavernreborn:cavenic_witch`.

The legacy source confirmed that the relevant hook is slightly broader than projectile-only behavior. It rejects damage when the direct or causing source is `this` or another `EntityCavenicWitch`, so the modern mapping restores the confirmed same-type/self source immunity without widening into the separate friendship-targeting or custom potion slices.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- legacy `isEntityInvulnerable(DamageSource source)` override
- legacy `setAttackTarget(EntityLivingBase entity)` override for separate friendship-targeting context
- legacy `attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)` override for separate custom potion/ranged context

## Exact Legacy Behavior Found

Legacy `EntityCavenicWitch#isEntityInvulnerable(DamageSource source)` did this:

- returned `true` when `super.isEntityInvulnerable(source)` already did
- returned `true` when `source.getTrueSource() == this || source.getImmediateSource() == this`
- returned `true` when `isFriends(source.getTrueSource()) || isFriends(source.getImmediateSource())`
- otherwise returned `false`

Legacy `isFriends(...)` only matched `EntityCavenicWitch`.

That means the legacy immunity rule was not limited to thrown potions. It also covered:

- self-owned projectile sources
- other `EntityCavenicWitch`-owned projectile sources
- direct same-type entity sources

Vanilla `Witch` sources are not included.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicWitch`.
- The modern hook is the entity-local `isInvulnerableTo(DamageSource source)` override.
- Reborn keeps the confirmed legacy source pattern by checking both:
  - `source.getEntity()`
  - `source.getDirectEntity()`
- Reborn treats the source as immune only when either entity is:
  - `this`
  - another `CavenicWitch`

## Exact Reborn Behavior

- `CavenicWitch` rejects same-type/self source patterns before normal damage processing.
- The restored rule includes same-type witch-owned projectile damage.
- The restored rule also includes direct same-type entity sources, because legacy `isEntityInvulnerable(...)` checked both true and immediate sources rather than only projectile classes.
- Vanilla `Witch` sources are not ignored.
- Generic non-witch damage remains vanilla-like.
- fall/fire damage behavior remains unchanged and still uses the existing `hurt(DamageSource source, float damage)` mapping.
- vanilla witch AI remains unchanged.
- vanilla drinking and potion-throw behavior remain unchanged.
- friendship targeting remains a separate bounded follow-up because the legacy `setAttackTarget(...)` rewrite is not required for this bounded immunity slice.
- attributes, natural spawning and `1/5` orb-drop behavior remain unchanged.
- The deferred magic-book branch remains documented in `docs/cavenic-witch-loot-mvp.md`.
- The bounded custom ranged-potion follow-up is documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`.
- Cavenia and additional mobs remain out of scope.

## Why This Mapping Is Safe

- The legacy rule lived directly on `EntityCavenicWitch`.
- Reborn keeps the implementation entity-local instead of adding a global damage event or a broad Cavenic immunity framework.
- `isInvulnerableTo(...)` is the narrowest modern hook for the old `isEntityInvulnerable(...)` behavior.
- Checking `source.getEntity()` and `source.getDirectEntity()` is the clean modern equivalent to the old `getTrueSource()` / `getImmediateSource()` pair.
- The separate legacy friendship-targeting rewrite is documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`.

## Testing

- Resource tests pin:
  - the `isInvulnerableTo(DamageSource source)` override
  - the `isLegacyCavenicWitchSourceImmuneTo(DamageSource source)` helper
  - the `source.getEntity()` / `source.getDirectEntity()` checks
  - the unchanged fall/fire logic, orb-drop policy and natural-spawn constants
  - the absence of magic-book, custom potion/ranged and Cavenia work in this slice
- Documentation tests pin the legacy `isEntityInvulnerable(...)` behavior, the broader same-type/self source scope and the continued out-of-scope boundaries.
- NeoForge GameTest runtime smoke covers:
  - cavenic witch legacy same-type/self source-immunity smoke
  - direct same-type source rejection smoke
  - cavenic witch non-immune source baseline smoke
  - continued fall/fire and generic-damage stability
  - continued vanilla witch loot-table baseline, `1/5` orb-drop follow-up and natural-spawn stability

## Still Out Of Scope

- The deferred legacy magic-book branch
- Any broader potion/raid behavior beyond the restored ranged-potion follow-up
- Same-type friendship targeting
- Cavenia
- Additional Cavenic mobs
- Broad combat rewrites
