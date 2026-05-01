# Crazy Zombie Knockback-On-Hit MVP

This note documents the bounded outgoing-combat follow-up for the existing `cavernreborn:crazy_zombie`.

It restores only the small legacy Crazy Zombie knockback branch. It does not add natural spawning, loot changes, incoming-damage changes, boss-event changes, custom AI or broader crazy-mob combat rewrites.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.client.particle.ParticleCrazyMob`
- legacy `attackEntityAsMob(Entity entity)` on `EntityCrazyZombie`
- legacy `BossInfoServer bossInfo`, `updateAITasks()`, `addTrackingPlayer(...)` and `removeTrackingPlayer(...)` on `EntityCrazyZombie`
- legacy client-only `onUpdate()` particle code on `EntityCrazyZombie`

## Exact Legacy Boss Bar And Sky-Darkening Behavior Found

- `EntityCrazyZombie` owns a `BossInfoServer` with:
  - color `BossInfo.Color.BLUE`
  - overlay `BossInfo.Overlay.PROGRESS`
- `EntityCrazyZombie#isNonBoss()` returns `false`.
- `updateAITasks()` keeps the boss bar server-side:
  - it iterates tracked `EntityPlayerMP` viewers already registered in `bossInfo`
  - `canSee` only becomes true when a tracked player is visible through `canEntityBeSeen(player)` and is within `20.0D`
  - `bossInfo.setDarkenSky(!canSee || distance < 30.0D)`
  - `bossInfo.setVisible(canSee)`
  - `bossInfo.setPercent(getHealth() / getMaxHealth())`
- `addTrackingPlayer(...)` and `removeTrackingPlayer(...)` add and remove players from `bossInfo`.
- `readEntityFromNBT(...)` and `setCustomNameTag(...)` keep the boss-bar name aligned with the entity display name.

## Exact Legacy Particle Behavior Found

- `EntityCrazyZombie#onUpdate()` is client-only.
- When `world.isRemote`, it spawns `3` `ParticleCrazyMob` particles per tick.
- Each particle uses:
  - side-offset positions at `posX/posZ +/- 0.25D`
  - `ptY = posY + 0.65D + rand.nextFloat()`
  - X/Z motion from `rand.nextFloat() * 1.0F * +/-1`
  - Y motion from `(rand.nextFloat() - 0.25D) * 0.125D`
- `ParticleCrazyMob` extends `ParticlePortal` and tints itself with a muted grayscale-blue value derived from `0.65F * f * 0.8F`.

## Exact Legacy Knockback-On-Hit Behavior Found

- `EntityCrazyZombie` overrides `attackEntityAsMob(Entity entity)`.
- It calls `super.attackEntityAsMob(entity)` first and stores the return value.
- It then rolls:
  - trigger roll: `rand.nextInt(5) == 0`
  - winning magnitude roll: `rand.nextInt(3) + 3`
- So the extra knockback power is:
  - `0` on losing trigger rolls
  - `3`, `4`, or `5` on winning trigger rolls
- If `power > 0` and the target is an `EntityLivingBase`, legacy calls:
  - `target.knockBack(this, power * 0.5F, sin(rotationYaw * 0.017453292F), -cos(rotationYaw * 0.017453292F))`
- If `power > 0` and the target is not living, legacy calls:
  - `entity.addVelocity(-sin(rotationYaw * 0.017453292F) * power * 0.5F, 0.1D, cos(rotationYaw * 0.017453292F) * power * 0.5F)`
- The extra knockback branch is not gated on the `super.attackEntityAsMob(...)` return value.
- The legacy code does not directly change the damage amount in this branch.

## Reborn Mapping

- Reborn keeps `CrazyZombie extends Zombie`.
- Reborn restores only the outgoing knockback branch through `CrazyZombie#doHurtTarget(Entity target)`.
- The modern mapping:
  - calls `super.doHurtTarget(target)` first
  - keeps the normal modern zombie melee-damage path intact
  - rolls the legacy `1/5` trigger and `3..5` power values
  - applies the extra knockback with:
    - `LivingEntity#knockback(...)` for living targets
    - `Entity#push(...)` for non-living targets
- Reborn keeps the legacy constants explicitly in the entity class:
  - `LEGACY_KNOCKBACK_TRIGGER_ROLL_BOUND = 5`
  - `LEGACY_KNOCKBACK_POWER_VARIANT_COUNT = 3`
  - `LEGACY_KNOCKBACK_BASE_POWER = 3`
  - `LEGACY_KNOCKBACK_STRENGTH_MULTIPLIER = 0.5F`
  - `LEGACY_NON_LIVING_KNOCKBACK_VERTICAL_BOOST = 0.1D`
- Reborn also exposes tiny deterministic helpers for testing the exact legacy branch:
  - `getLegacyKnockbackPower(int triggerRoll, int magnitudeRoll)`
  - `tryApplyLegacyKnockback(Entity target, int power)`

## Why Boss Bar And Particles Are Separate

- The boss bar and sky-darkening path is now restored separately in `docs/crazy-zombie-boss-bar-mvp.md`.
- The particle trail is now restored separately in `docs/crazy-zombie-particle-trail-mvp.md`.
- This increment restores only one behavior family.
- Knockback-on-hit is the smallest source-confirmed server-side combat slice that maps cleanly without touching the separate boss-event and particle follow-ups.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazyZombie` inherits from `EntityCavenicZombie`.
- Reborn `CrazyZombie` intentionally extends vanilla `Zombie`, not Reborn `CavenicZombie`.
- That baseline decision stays in place so Crazy Zombie does not silently inherit staged Reborn Cavenic natural-spawn, orb-drop or other follow-up behavior that still needs separate inspection.
- Reborn therefore copies only the confirmed outgoing knockback branch explicitly onto `CrazyZombie`.

## Testing

- Resource tests pin:
  - the entity-local `doHurtTarget(...)` hook
  - the legacy knockback constants
  - the deterministic helper methods
  - the preserved vanilla `Zombie` base
  - the continued absence of natural-spawn wiring
- Documentation tests pin:
  - the exact boss-bar color/overlay/darken-sky findings
  - the client-only particle trail findings
  - the exact `1/5` trigger and `3..5` power mapping
  - the explicit decision to implement only knockback in this slice
- NeoForge GameTest runtime smoke covers:
  - crazy zombie melee-hit success through the real `doHurtTarget(...)` path
  - deterministic losing and winning knockback helper smoke
  - vanilla zombie baseline comparison with no extra crazy-zombie knockback branch
  - continued orb-drop, incoming-damage, max-health-clamp and natural-spawn-deferral stability
  - continued boss-event and particle-trail separation from the knockback branch

## Still Out Of Scope

- Crazy Zombie natural spawning
- Crazy Zombie custom loot beyond the restored inherited orb-drop branch
- Crazy Zombie incoming-damage behavior changes
- Crazy Zombie custom AI
- Crazy Skeleton baseline is now documented separately in `docs/crazy-skeleton-baseline-mvp.md`, while its ranged-AI and equipment follow-ups remain separate
- Crazy Creeper follow-up branches documented after `docs/crazy-creeper-baseline-mvp.md` / Crazy Spider
- Cavenia
- magic-book or spell systems
