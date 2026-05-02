# Crazy Spider Legacy Damage Behavior MVP

This note documents the bounded incoming-damage follow-up for the existing `cavernreborn:crazy_spider`.

It does not add natural spawning, loot changes, boss-event changes, particle changes, custom combat, blindness/poison behavior, custom AI or broader crazy-mob combat systems. It only restores the small inherited legacy damage drift from `EntityCavenicSpider`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySpider`
- `cavern.entity.monster.EntityCavenicSpider`
- legacy `attackEntityFrom(DamageSource source, float damage)` handling on `EntityCavenicSpider`
- legacy `attackEntityAsMob(Entity entity)` handling on `EntityCavenicSpider`
- legacy `getBlindnessAttackPower()` and `getPoisonAttackPower()` on `EntityCrazySpider` for separate combat boundary context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` on `EntityCavenicSpider` for loot-regression context

## Exact Legacy Behavior Found

- `EntityCrazySpider` does not override `attackEntityFrom(...)`.
- It inherits `EntityCavenicSpider#attackEntityFrom(DamageSource source, float damage)`.
- That inherited method:
  - checks `source == DamageSource.FALL` and applies `damage *= 0.35F`
  - uses `!source.isFireDamage()` to guard the final super call
  - returns `!source.isFireDamage() && super.attackEntityFrom(source, damage)`
- So Crazy Spider still takes fall damage, but only at `35%` of the original amount.
- Crazy Spider rejects fire damage entirely.
- No extra direct incoming-damage immunity is declared on `EntityCrazySpider`.
- Generic non-fire, non-fall damage remains vanilla-like.
- The inherited `1/8` orb-drop branch is separate and remains documented in `docs/crazy-spider-loot-mvp.md`.
- The direct blindness/poison combat branch is separate and remains deferred.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CrazySpider`.
- The modern hook is the entity-local `hurt(DamageSource source, float damage)` override.
- Reborn uses `DamageTypeTags.IS_FALL` to detect fall damage and applies `LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`.
- Reborn uses `DamageTypeTags.IS_FIRE` to reject all modern fire-tagged damage sources before delegating to `super.hurt(...)`.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazySpider` inherits the damage behavior from `EntityCavenicSpider`.
- Reborn `CrazySpider` intentionally extends vanilla `Spider`, not Reborn `CavenicSpider`.
- That baseline decision stays in place so Crazy Spider does not silently inherit staged Reborn orb-drop wiring, blindness/poison combat behavior, natural-spawn rules or later boss/particle follow-ups before each slice is inspected honestly.
- Reborn therefore copies only the confirmed incoming-damage behavior explicitly onto `CrazySpider`.

## Exact Reborn Behavior

- Fall damage is reduced to `35%` of the incoming amount.
- All modern fire-tagged damage sources are ignored.
- That bounded modern mapping includes lava and burning damage because they use fire-tagged damage sources in current 1.21.1 damage-type data.
- Generic non-fire, non-fall damage remains vanilla-like.
- Loot/orb behavior remains unchanged.
- The vanilla spider loot baseline remains unchanged.
- Crazy Spider still resolves the vanilla spider loot table through `EntityType.SPIDER.getDefaultLootTable()`.
- Crazy Spider still keeps the legacy `1500.0D` source literal in its attribute builder while the runtime effective max health remains clamped to `1024.0`.
- Crazy Spider still keeps the inherited `1/8` orb-drop wiring through `CrazySpiderLootEvents` and `CrazySpiderLootPolicy`.
- Natural-spawn deferral remains unchanged.
- Custom combat, blindness and poison behavior remains deferred.
- Boss bar, sky-darkening and particle trail remain out of scope.
- Cavenia and magic-book systems remain out of scope.

## Testing

- Resource tests pin the entity-local damage hook, the exact fall/fire tag usage, the preserved `1500.0D` source literal, the preserved vanilla spider loot-table baseline, the preserved `CrazySpiderLootEvents` / `CrazySpiderLootPolicy` wiring and the continued absence of natural-spawn, boss, particle and custom combat follow-up code.
- Documentation tests pin the legacy source references, the inherited `0.35F` fall multiplier, the explicit copy-not-inherit mapping and the unchanged loot/natural-spawn/combat boundaries.
- NeoForge GameTest runtime smoke covers:
  - crazy spider legacy fall-damage reduction smoke
  - crazy spider legacy fire-damage immunity smoke
  - crazy spider generic-damage baseline smoke
  - continued vanilla spider loot-table baseline stability
  - continued runtime `1024.0` max-health clamp stability
  - continued inherited `1/8` orb-drop wiring stability
  - continued natural-spawn deferral through absent placement/biome-modifier/biome-tag wiring
  - continued absence of boss, particle and blindness/poison combat overrides

## Still Out Of Scope

- Crazy Spider natural spawning
- Crazy Spider boss bar / sky-darkening
- Crazy Spider particle trail
- Crazy Spider custom combat / blindness / poison behavior
- Crazy Spider custom AI
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
