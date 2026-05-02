# Crazy Creeper Boss Bar / Sky-Darkening MVP

This note documents the bounded boss-event follow-up for the existing `cavernreborn:crazy_creeper`.

It restores only the legacy Crazy Creeper boss bar / sky-darkening branch. It does not add particles, natural spawning, loot changes, incoming-damage changes, custom fuse/explosion behavior, lightning/charged/swelling behavior, custom packets or broader crazy-mob systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.client.particle.ParticleCrazyMob`
- legacy particle class `ParticleCrazyMob` for out-of-scope client-visual boundary context
- legacy `BossInfoServer bossInfo`
- legacy `updateAITasks()`
- legacy `addTrackingPlayer(...)`
- legacy `removeTrackingPlayer(...)`
- legacy `readEntityFromNBT(...)`
- legacy `setCustomNameTag(...)`
- legacy `isNonBoss()`
- legacy `fuseTime`
- legacy `explosionRadius`

## Exact Legacy Boss Bar Behavior Found

- `EntityCrazyCreeper` declares its own `BossInfoServer` directly. It does not inherit the boss event from `EntityCavenicCreeper`.
- The boss event uses:
  - `BossInfo.Color.GREEN`
  - `BossInfo.Overlay.PROGRESS`
- Legacy `EntityCrazyCreeper#isNonBoss()` returns `false`.
- Legacy `updateAITasks()` updates:
  - `bossInfo.setPercent(getHealth() / getMaxHealth())`
  - `bossInfo.setVisible(canSee)`
  - `bossInfo.setDarkenSky(!canSee || distance < 30.0D)`
- `canSee` only becomes true when a tracked player:
  - passes `canEntityBeSeen(player)`
  - is within `20.0D`
- `addTrackingPlayer(...)` adds players to the boss event.
- `removeTrackingPlayer(...)` removes players from the boss event.
- `readEntityFromNBT(...)` and `setCustomNameTag(...)` keep the boss-event name aligned with the entity display name.

## Exact Legacy Sky-Darkening Behavior Found

- Legacy uses one global boss event for all tracked players.
- The loop stores the last checked distance and breaks early on the first visible player within `20.0D`.
- It then applies `bossInfo.setDarkenSky(!canSee || distance < 30.0D)`.
- In the single-player deterministic case, that means:
  - a nearby visible tracked player keeps darkening enabled
  - a blocked tracked player also keeps darkening enabled
- Multiplayer semantics are slightly ambiguous because legacy mutates one shared boss event while iterating tracked players. Reborn keeps that same single global boss event rather than inventing per-player events.
- The inspected boss-event branch does not depend on Cavenia, config, spawn type, difficulty, fuse state, explosion state, charged state or lightning state.

## Reborn Mapping

- Reborn keeps `CrazyCreeper extends Creeper`.
- Reborn restores the legacy boss event with a private `ServerBossEvent`.
- The modern mapping uses:
  - `BossEvent.BossBarColor.GREEN`
  - `BossEvent.BossBarOverlay.PROGRESS`
  - `customServerAiStep()`
  - `startSeenByPlayer(ServerPlayer player)`
  - `stopSeenByPlayer(ServerPlayer player)`
  - `setCustomName(@Nullable Component name)`
  - `readAdditionalSaveData(CompoundTag compound)`
- Reborn keeps narrow helpers for deterministic coverage:
  - `getLegacyCrazyBossEventForTests()`
  - `shouldShowLegacyBossBarTo(ServerPlayer player)`
  - `shouldDarkenLegacyBossSkyFor(ServerPlayer player)`
  - `updateLegacyBossEvent()`

## Exact Reborn Behavior

- The boss event uses the legacy green color and progress overlay.
- Boss progress is updated from `getHealth() / getMaxHealth()`.
- Boss visibility stays gated by:
  - line of sight through `hasLineOfSight(player)`
  - distance under `20.0D`
- Sky darkening stays gated by the legacy condition:
  - `!canSee || distance < 30.0D`
- Tracking still adds players on `startSeenByPlayer(...)`.
- Tracking still removes players on `stopSeenByPlayer(...)`.
- Custom-name sync now updates the boss-event name through `setCustomName(...)`.
- Saved custom names also resync after `readAdditionalSaveData(...)`.

## Why Reborn Keeps `CrazyCreeper extends Creeper`

- Legacy `EntityCrazyCreeper` extends `EntityCavenicCreeper`.
- Reborn `CrazyCreeper` intentionally extends vanilla `Creeper`, not Reborn `CavenicCreeper`.
- That baseline decision stays in place so Crazy Creeper does not silently inherit staged Reborn natural-spawn, orb-drop, damage, particle, fuse/explosion or lightning/charged/swelling behavior that still needs separate inspection.
- Reborn therefore copies only the confirmed boss-event branch explicitly onto `CrazyCreeper`.

## What Stays Unchanged

- Loot remains unchanged.
- Damage remains unchanged.
- Natural spawning remains deferred.
- Particles remain a separate client-visual follow-up.
- Fuse/explosion behavior remains deferred.
- Lightning/charged/swelling behavior remains deferred.
- no custom packets were added
- no Cavenia content was added

More specifically:

- loot remains documented in `docs/crazy-creeper-loot-mvp.md`
- damage remains documented in `docs/crazy-creeper-damage-behavior-mvp.md`

## Testing

- Resource tests pin:
  - the `ServerBossEvent` field
  - the legacy green/progress boss-event styling
  - tracked-player add/remove hooks
  - percent updates from `health / maxHealth`
  - the exact `20.0D` visibility threshold
  - the exact `30.0D` darken-sky threshold
  - continued absence of particle, fuse/explosion and lightning/charged follow-up code on the boss-event branch
- Documentation tests pin:
  - the exact legacy boss-event references
  - the single global boss-event ambiguity note
  - the explicit copy-not-inherit mapping
  - the separate particle-trail follow-up mapping
- NeoForge GameTest runtime smoke covers:
  - crazy creeper boss-event wiring smoke
  - crazy creeper boss-bar color/overlay smoke
  - crazy creeper boss-percent update smoke
  - crazy creeper tracked-player add/remove smoke
  - crazy creeper sky-darkening smoke
  - continued loot, damage and natural-spawn-deferral stability

## Still Out Of Scope

- Crazy Creeper particle trail
- Crazy Creeper custom fuse/explosion behavior
- Crazy Creeper lightning/charged/swelling behavior
- Crazy Creeper natural spawning
- Crazy Spider
- Cavenia
- magic-book or spell systems
