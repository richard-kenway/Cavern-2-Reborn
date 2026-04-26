# Cavenic Witch Friendship Targeting MVP

This note documents the bounded friendship-targeting follow-up for the existing `cavernreborn:cavenic_witch`.

It does not add a new mob, loot changes, natural-spawn changes, AI-goal rewrites or magic-book support. It only restores the small confirmed legacy same-type target rejection from `EntityCavenicWitch`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCavenicWitch`
- legacy `setAttackTarget(EntityLivingBase entity)` override
- legacy `isFriends(@Nullable Entity entity)` helper
- legacy `isEntityInvulnerable(DamageSource source)` override for separate same-type/self source-immunity context
- legacy `attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)` override for separate custom potion/ranged context

## Exact Legacy Behavior Found

Legacy `EntityCavenicWitch` handled targeting like this:

- `isFriends(...)` returned `true` only for `EntityCavenicWitch`
- `setAttackTarget(EntityLivingBase entity)` returned immediately when `isFriends(entity)` was `true`
- otherwise it delegated to `super.setAttackTarget(entity)`

That means the legacy targeting rule was narrow:

- same-type `EntityCavenicWitch` targets were rejected
- vanilla `EntityWitch` targets were not treated as friends
- non-witch targets remained allowed
- explicit `null` target clearing still used the vanilla path
- the hook rejected the incoming target assignment instead of clearing the current target

## Reborn Mapping

- Reborn implements the behavior directly in `app.entity.CavenicWitch`.
- The modern hook is the entity-local `setTarget(@Nullable LivingEntity target)` override.
- Reborn uses `isLegacyFriendTarget(@Nullable LivingEntity target)` to mirror the old `isFriends(...)` helper.
- `isLegacyFriendTarget(...)` returns `true` only for `CavenicWitch`.
- When the incoming target is a friend, `setTarget(...)` returns without calling `super.setTarget(target)`.
- When the incoming target is not a friend, Reborn delegates to `super.setTarget(target)` unchanged.

## Exact Reborn Behavior

- Same-type `CavenicWitch` targets are rejected.
- Rejected same-type targets do not clear an existing non-friend target; the current target stays unchanged, matching legacy early-return behavior.
- Vanilla `Witch` targets remain allowed.
- Non-witch hostile targets such as `Zombie` remain allowed.
- Explicit `null` target clearing remains vanilla-like.
- Vanilla witch AI remains unchanged.
- Vanilla drinking and potion-throw behavior remain unchanged.
- Same-type/self source immunity remains unchanged and is still documented in `docs/cavenic-witch-projectile-immunity-mvp.md`.
- Fall/fire damage behavior remains unchanged and is still documented in `docs/cavenic-witch-damage-behavior-mvp.md`.
- Attributes, natural spawning and `1/5` orb-drop behavior remain unchanged.
- The deferred legacy magic-book branch remains documented in `docs/cavenic-witch-magic-book-deferred.md`.
- The bounded custom ranged-potion follow-up is documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`.
- Cavenia and additional mobs remain out of scope.

## Why This Mapping Is Safe

- The legacy behavior lived directly on `EntityCavenicWitch`.
- Reborn keeps the implementation entity-local instead of adding a global targeting event or a broad friendship framework.
- `setTarget(@Nullable LivingEntity target)` is the narrowest modern hook for the old `setAttackTarget(...)` behavior.
- The separate legacy same-type/self source-immunity rule remains unchanged and does not need to be widened to restore targeting parity.
- No AI goals or target selectors are rewritten here.

## Testing

- Resource tests pin:
  - the `setTarget(@Nullable LivingEntity target)` override
  - the `isLegacyFriendTarget(@Nullable LivingEntity target)` helper
  - the exact same-type-only friend check
  - the unchanged source-immunity hook, fall/fire logic, orb-drop policy, loot baseline and natural-spawn constants
  - the absence of magic-book, custom potion/ranged, AI-goal and Cavenia work
- Documentation tests pin the legacy `setAttackTarget(...)` and `isFriends(...)` behavior, the vanilla-witch exclusion and the unchanged bounded-follow-up references.
- NeoForge GameTest runtime smoke covers:
  - cavenic witch same-type friendship target rejection smoke
  - cavenic witch non-friend target baseline smoke
  - explicit null target clearing smoke
  - continued same-type/self source-immunity, fall/fire, loot and natural-spawn stability

## Still Out Of Scope

- The deferred legacy magic-book branch
- Any broader potion/raid behavior beyond the restored ranged-potion follow-up
- AI-goal or target-selector rewrites
- Cavenia
- Additional Cavenic mobs
- Broad combat or targeting rewrites
