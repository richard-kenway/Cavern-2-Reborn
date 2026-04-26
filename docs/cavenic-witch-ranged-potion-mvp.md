# Cavenic Witch Custom Ranged Potion MVP

This note documents the bounded ranged-potion follow-up for the existing `cavernreborn:cavenic_witch`.

It does not add a new mob, loot changes, natural-spawn changes, magic-book support, new AI goals, target-selector rewrites or Cavenia behavior. It restores the confirmed legacy `attackEntityWithRangedAttack(...)` combat behavior where that mapping is safe in NeoForge `1.21.1`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- legacy `attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)` override
- legacy `setAttackTarget(EntityLivingBase entity)` override for separate friendship-targeting context
- legacy `isEntityInvulnerable(DamageSource source)` override for separate same-type/self source-immunity context
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` override for separate orb-drop and deferred magic-book context

## Exact Legacy Behavior Found

Legacy `EntityCavenicWitch#attackEntityWithRangedAttack(...)` replaced the vanilla witch ranged attack path directly:

- it used `getAttackPotionCount()` and returned `world.getWorldInfo().getDifficulty().getDifficultyId()`
- that mapped to `0 / 1 / 2 / 3` throws for `PEACEFUL / EASY / NORMAL / HARD`
- each throw skipped entirely when the witch was currently drinking
- each throw predicted target movement with `target.motionX` / `target.motionZ`
- the default potion was `PotionTypes.HARMING`
- the first roll used `rand.nextFloat() < (target.getHealth() >= 8.0F ? 0.5F : 0.3F)`
- when that first roll won, the potion became `PotionTypes.HEALING` for undead targets and `PotionTypes.POISON` for other targets
- otherwise a second roll used `0.25F` for `PotionTypes.WEAKNESS`
- otherwise a third roll used `0.2F` for `PotionTypes.SLOWNESS`
- every spawned projectile used a splash potion item, `+20` pitch offset, `0.75F` speed and `8.0F` inaccuracy
- every spawned projectile played the witch throw sound and preserved the witch as the projectile owner/source

Legacy `distanceFactor` was ignored.

Legacy `attackEntityWithRangedAttack(...)` did not contain a separate raider-heal branch, because the old `1.12.2` mob model had no `Raider` system.

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicWitch`.
- The modern hook is the entity-local `performRangedAttack(LivingEntity target, float distanceFactor)` override.
- Reborn uses `createLegacyThrownPotionsFor(LivingEntity target)` to keep the deterministic legacy throw-count and projectile-construction path testable without widening this slice into a broader projectile framework.
- Reborn uses `getLegacyAttackPotionCount(Difficulty difficulty)` to pin the old `0 / 1 / 2 / 3` difficulty mapping.
- Reborn uses `selectLegacyRangedPotionFor(LivingEntity target, RandomSource random)` to keep the old harming/healing-or-poison/weakness/slowness selection order and probabilities.
- Runtime tests also pin the high-health `0.5F` threshold, the low-health `0.3F` threshold, and the explicit weakness/slowness/harming fallback cases directly.
- Reborn uses `ThrownPotion` plus `PotionContents.createItemStack(Items.SPLASH_POTION, potion)` as the modern equivalent of the old splash-potion construction.
- Reborn keeps the legacy throw profile:
  - pitch offset: `20.0F`
  - velocity: `0.75F`
  - inaccuracy: `8.0F`
  - throw sound: `SoundEvents.WITCH_THROW`

## Intentional Modern Bridge

Reborn keeps one narrow modern bridge that legacy `1.12.2` did not need:

- when the target is a non-witch `Raider`, `CavenicWitch` delegates to `super.performRangedAttack(...)`

That preserves modern vanilla `1.21.1` raid-heal behavior for non-witch raider allies instead of widening this slice into an AI-goal or raid-system rewrite. Vanilla `Witch` targets are not included in that bridge and still use the restored legacy combat branch.

## Exact Reborn Behavior

- Normal combat targets now use the restored legacy ranged-potion logic.
- The throw count is difficulty-scaled as `0 / 1 / 2 / 3` for `PEACEFUL / EASY / NORMAL / HARD`.
- The first branch keeps the legacy health-based `0.5 / 0.3` poison-or-heal roll.
- Undead targets map to `HEALING` on a winning first roll via modern `isInvertedHealAndHarm()`.
- Other targets map to `POISON` on that same winning first roll.
- Losing first rolls then keep the legacy `WEAKNESS` and `SLOWNESS` fallback rolls.
- Projectile owner/source stays on the attacking `CavenicWitch`.
- Vanilla witch AI goals and target selectors remain unchanged.
- Vanilla drinking behavior remains unchanged.
- Friendship targeting remains unchanged and is still documented in `docs/cavenic-witch-friendship-targeting-mvp.md`.
- Same-type/self source immunity remains unchanged and is still documented in `docs/cavenic-witch-projectile-immunity-mvp.md`.
- Fall/fire damage behavior remains unchanged and is still documented in `docs/cavenic-witch-damage-behavior-mvp.md`.
- Attributes, natural spawning and `1/5` orb-drop behavior remain unchanged.
- The deferred magic-book branch remains documented in `docs/cavenic-witch-loot-mvp.md`.

## Why This Mapping Is Safe

- The legacy ranged behavior lived directly on `EntityCavenicWitch`.
- Modern `performRangedAttack(...)` is the direct hook for the old `attackEntityWithRangedAttack(...)`.
- `ThrownPotion` and `PotionContents` map the legacy splash-potion projectile path cleanly.
- The only deliberate deviation is the preserved modern non-witch `Raider` branch, which avoids rewriting current vanilla raid behavior in a slice that is only meant to restore the legacy combat throw logic.
- No new potion framework, projectile framework or AI-goal system is introduced here.

## Testing

- Resource tests pin:
  - the `performRangedAttack(LivingEntity target, float distanceFactor)` override
  - the `createLegacyThrownPotionsFor(LivingEntity target)` helper
  - the `getLegacyAttackPotionCount(Difficulty difficulty)` helper
  - the `selectLegacyRangedPotionFor(LivingEntity target, RandomSource random)` helper
  - the legacy potion constants and potion types
  - the preserved friendship-targeting and source-immunity helpers
  - the unchanged fall/fire logic, loot baseline, orb policy and natural-spawn constants
  - the absence of magic-book, Cavenia and bow-projectile additions
- Documentation tests pin the legacy `attackEntityWithRangedAttack(...)` behavior, the exact probability/count mapping, the preserved non-witch `Raider` bridge and the unchanged bounded follow-up references.
- NeoForge GameTest runtime smoke covers:
  - cavenic witch legacy ranged-potion runtime smoke
  - seeded legacy potion-selection smoke against a real `Zombie` target
  - explicit high-health and low-health potion-threshold smoke
  - explicit weakness/slowness/harming fallback smoke
  - deterministic legacy thrown-potion construction smoke against real runtime entities
  - direct non-friend `performRangedAttack(...)` branch smoke
  - preserved non-witch `Raider` bridge smoke
  - continued friendship-targeting, source-immunity, fall/fire, loot and natural-spawn stability

## Still Out Of Scope

- The deferred legacy magic-book branch
- Any broader AI-goal or target-selector rewrite
- Any broader raid-behavior rewrite beyond the preserved non-witch `Raider` bridge
- Cavenia
- Additional Cavenic mobs
- Broad projectile or potion frameworks
