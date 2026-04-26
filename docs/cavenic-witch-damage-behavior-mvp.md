# Cavenic Witch Legacy Damage Behavior MVP

This note documents the bounded damage-behavior follow-up for the existing `cavernreborn:cavenic_witch`.

It does not add a new mob, loot changes, natural-spawn changes, custom potion logic, custom ranged attack behavior or custom AI. It only restores the small confirmed legacy fall/fire damage drift from `EntityCavenicWitch`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- legacy `attackEntityFrom(DamageSource source, float damage)` override
- legacy `isEntityInvulnerable(DamageSource source)` override for separate same-type/self projectile immunity context
- legacy `attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)` override for separate custom potion/ranged context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override for separate orb-drop and deferred magic-book context

## Exact Legacy Behavior Found

Legacy `EntityCavenicWitch` handled damage like this:

- `source == DamageSource.FALL` scaled incoming damage with `damage *= 0.35F`
- `!source.isFireDamage()` guarded the final super call

That means the mob kept taking fall damage, but only at `35%` of the original amount, and it rejected fire damage entirely.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicWitch`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava because lava uses a fire-tagged damage source in current 1.21.1 damage-type data.
- Generic non-fire, non-fall damage remains vanilla-like.
- vanilla witch AI remains unchanged.
- vanilla drinking and potion-throw behavior remain unchanged.
- attributes, natural spawning and `1/5` orb-drop behavior remain unchanged.
- The deferred legacy magic-book branch remains documented in `docs/cavenic-witch-magic-book-deferred.md`.
- The bounded same-type/self source-immunity follow-up is documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`.
- The bounded friendship-targeting follow-up is documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`.
- The bounded custom ranged-potion follow-up is documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`.

## Why This Mapping Is Safe

- The legacy behavior lived directly on `EntityCavenicWitch` rather than in a broad shared system.
- Reborn keeps the implementation entity-specific instead of using a global witch or mob damage event.
- The `DamageTypeTags.IS_FIRE` mapping is the closest current equivalent to the old `source.isFireDamage()` check.
- The separate legacy `isEntityInvulnerable(...)` logic for same-type/self projectile immunity stays out of scope for a later bounded follow-up.

## Testing

- Resource tests pin the entity-local damage hook, the exact fall/fire tag usage and the unchanged orb-drop/spawn constants while still asserting that no fake magic-book item or witch-specific AI/targeting work was added here.
- Documentation tests pin the legacy source references, the `0.35F` fall multiplier, the unchanged vanilla witch behavior and the deferred magic-book/projectile-immunity/friendship-targeting/ranged-potion follow-up boundaries.
- NeoForge GameTest runtime smoke covers:
  - cavenic witch legacy fall-damage reduction smoke
  - cavenic witch legacy fire-damage immunity smoke
  - cavenic witch generic-damage baseline smoke
  - continued vanilla witch loot-table baseline, `1/5` orb-drop follow-up and natural-spawn stability

## Still Out Of Scope

- The deferred legacy magic-book branch
- Any broader potion/raid behavior beyond the restored ranged-potion follow-up
- Same-type/self source immunity
- Same-type friendship targeting
- Additional Cavenic mobs
- Cavenia
- Loot chance changes remain out of scope
- Natural-spawn rebalancing remains out of scope
- Broader combat rewrites remain out of scope
