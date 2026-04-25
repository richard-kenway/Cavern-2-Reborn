# Cavenic Spider Legacy Damage Behavior MVP

This note documents the bounded damage-behavior follow-up for the existing `cavernreborn:cavenic_spider`.

It does not add a new mob, loot changes, natural-spawn changes, poison behavior, web behavior or custom AI. It only restores the small confirmed legacy fall/fire damage drift from `EntityCavenicSpider`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicSpider`
- legacy `attackEntityFrom(DamageSource source, float damage)` override
- legacy `attackEntityAsMob(Entity entity)` override for separate blindness/poison context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override for orb-drop regression context

## Exact Legacy Behavior Found

Legacy `EntityCavenicSpider` handled damage like this:

- `source == DamageSource.FALL` scaled incoming damage with `damage *= 0.35F`
- `!source.isFireDamage()` guarded the final super call

That means the mob kept taking fall damage, but only at `35%` of the original amount, and it rejected fire damage entirely.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicSpider`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava because lava uses a fire-tagged damage source in current 1.21.1 damage-type data.
- Generic non-fire, non-fall damage remains vanilla-like.
- vanilla spider AI remains unchanged.
- attributes, natural spawning and `1/8` orb-drop behavior remain unchanged.
- The separate legacy blindness-on-hit follow-up is now documented separately in `docs/cavenic-spider-blindness-on-hit-mvp.md`.
- Poison, web and other special spider behavior remain out of scope for separate follow-ups.

## Why This Mapping Is Safe

- The legacy behavior lived directly on `EntityCavenicSpider` rather than in a broad shared system.
- Reborn keeps the implementation entity-specific instead of using a global spider or mob damage event.
- The `DamageTypeTags.IS_FIRE` mapping is the closest current equivalent to the old `source.isFireDamage()` check.

## Testing

- Resource tests pin the entity-local damage hook, the exact fall/fire tag usage and the unchanged orb-drop/spawn constants.
- Documentation tests pin the legacy source references, the `0.35F` fall multiplier and the separate blindness/poison/web follow-up boundary.
- NeoForge GameTest runtime smoke covers:
  - cavenic spider legacy fall-damage reduction smoke
  - cavenic spider legacy fire-damage immunity smoke
  - cavenic spider generic-damage baseline smoke
  - continued vanilla spider loot-table baseline, `1/8` orb-drop follow-up and natural-spawn stability

## Still Out Of Scope

- Poison behavior remains out of scope
- Web and other special spider behavior remain out of scope
- Custom Cavenic Spider AI remains out of scope
- Additional Cavenic mobs remain out of scope
- Cavenia remains out of scope
- Loot chance changes remain out of scope
- Natural-spawn rebalancing remains out of scope
- Broader combat rewrites remain out of scope
