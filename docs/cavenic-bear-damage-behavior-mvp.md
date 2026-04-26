# Cavenic Bear Legacy Damage Behavior MVP

This note documents the bounded damage-behavior follow-up for the existing `cavernreborn:cavenic_bear`.

It does not add a new mob, new loot, new spawn rules, new AI, anger rewrites or broader bear combat systems. It only restores the small confirmed legacy damage drift from `EntityCavenicBear`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicBear`
- legacy `attackEntityFrom(DamageSource source, float damage)` override
- legacy `AIPanic#shouldExecute()` for burning-only panic context
- legacy `getCanSpawnHere()` and `getMaxSpawnedInChunk()` only for regression context

## Exact Legacy Damage Behavior Found

Legacy `EntityCavenicBear` handled damage like this:

- `source == DamageSource.FALL` scaled incoming damage with `damage *= 0.35F`
- `!source.isFireDamage()` guarded the final super call

That means the mob kept taking fall damage, but only at `35%` of the original amount, and it rejected fire damage entirely.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicBear`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava because lava uses a fire-tagged damage source in current 1.21.1 damage-type data.
- Burning and other fire-tagged hurt paths are also covered by the same modern tag check.
- Generic non-fire, non-fall damage remains vanilla-like.
- the broader vanilla polar bear movement base remains unchanged in this slice.
- the bounded hostile-targeting follow-up is documented separately in `docs/cavenic-bear-hostile-targeting-mvp.md`.
- the bounded melee-attack follow-up is documented separately in `docs/cavenic-bear-melee-attack-mvp.md`.
- the bounded panic-behavior follow-up is documented separately in `docs/cavenic-bear-panic-behavior-mvp.md`.
- the remaining passive/movement AI boundary is documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`.
- anger behavior remains otherwise unchanged.
- attributes remain unchanged.
- natural spawning remains unchanged.
- max spawn cluster size remains unchanged.
- vanilla polar bear loot baseline remains unchanged.
- the source-confirmed bear custom loot/orb-drop absence remains unchanged.

## Why This Mapping Is Safe

- The legacy behavior lived directly on `EntityCavenicBear` rather than in a broad shared system.
- Reborn keeps the implementation entity-specific instead of using a global damage event.
- The `DamageTypeTags.IS_FIRE` mapping is the closest current equivalent to the old `source.isFireDamage()` check.
- The legacy class also had custom bear AI, the bounded hostile-targeting follow-up is now documented separately in `docs/cavenic-bear-hostile-targeting-mvp.md`, and the bounded melee-attack follow-up is now documented separately in `docs/cavenic-bear-melee-attack-mvp.md`.
- The remaining passive/movement AI boundary is documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`.

## Testing

- Resource tests pin the entity-local damage hook and the exact fall/fire tag usage.
- Documentation tests pin the legacy source references, the `0.35F` fall multiplier and the fire-damage immunity boundary.
- NeoForge GameTest runtime smoke covers:
  - cavenic bear legacy fall-damage reduction smoke
  - cavenic bear legacy fire-damage immunity smoke
  - cavenic bear generic-damage baseline smoke
  - continued vanilla polar bear loot-table baseline and natural-spawn stability

## Still Out Of Scope

- custom bear loot and orb drops
- custom bear AI and anger rewrites
- taming, riding and mount behavior
- Cavenia-specific behavior
- additional Cavenic mobs
- broader combat rewrites
