# Crazy Skeleton Boss Bar / Sky-Darkening MVP

This note documents the bounded boss-event follow-up for the existing `cavernreborn:crazy_skeleton`.

It restores only the legacy Crazy Skeleton boss bar / sky-darkening branch. It does not add particles, natural spawning, loot changes, incoming-damage changes, guaranteed `Cavenic Bow` equipment changes, custom ranged AI, custom packets or broader crazy-mob combat systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.client.particle.ParticleCrazyMob`
- legacy `BossInfoServer bossInfo`
- legacy `updateAITasks()`
- legacy `addTrackingPlayer(...)`
- legacy `removeTrackingPlayer(...)`
- legacy `readEntityFromNBT(...)`
- legacy `setCustomNameTag(...)`
- legacy `isNonBoss()`

## Exact Legacy Boss Bar Behavior Found

- `EntityCrazySkeleton` declares its own `BossInfoServer` directly. It is not inherited from `EntityCavenicSkeleton`.
- The boss event uses:
  - `BossInfo.Color.WHITE`
  - `BossInfo.Overlay.PROGRESS`
- Legacy `EntityCrazySkeleton#isNonBoss()` returns `false`.
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

## Reborn Mapping

- Reborn keeps `CrazySkeleton extends Skeleton`.
- Reborn restores the legacy boss event with a private `ServerBossEvent`.
- The modern mapping uses:
  - `BossEvent.BossBarColor.WHITE`
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

- The boss event uses the legacy white color and progress overlay.
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

## Why Reborn Keeps `CrazySkeleton extends Skeleton`

- Legacy `EntityCrazySkeleton` extends `EntityCavenicSkeleton`.
- Reborn `CrazySkeleton` intentionally extends vanilla `Skeleton`, not Reborn `CavenicSkeleton`.
- That baseline decision stays in place so Crazy Skeleton does not silently inherit staged Reborn natural-spawn, damage, loot or later Cavenic Skeleton follow-up behavior that still needs separate inspection.
- Reborn therefore copies only the confirmed boss-event branch explicitly onto `CrazySkeleton`.

## What Stays Unchanged

- Equipment remains unchanged.
- Loot remains unchanged.
- Damage remains unchanged.
- natural spawning remains deferred
- custom ranged AI remains deferred
- particles now remain documented separately in `docs/crazy-skeleton-particle-trail-mvp.md`
- no custom packets were added
- no Cavenia content was added

More specifically:

- guaranteed `Cavenic Bow` + `Infinity` equipment remains documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md`
- loot remains documented in `docs/crazy-skeleton-loot-mvp.md`
- damage remains documented in `docs/crazy-skeleton-damage-behavior-mvp.md`
- custom ranged AI remains documented in `docs/crazy-skeleton-ranged-ai-boundary.md`

## Testing

- Resource tests pin:
  - the `ServerBossEvent` field
  - the legacy white/progress boss-event styling
  - tracked-player add/remove hooks
  - percent updates from `health / maxHealth`
  - the exact `20.0D` visibility threshold
  - the exact `30.0D` darken-sky threshold
  - continued absence of particle and custom ranged-AI code on the boss-event branch
- Documentation tests pin:
  - the exact legacy boss-event references
  - the single global boss-event ambiguity note
  - the explicit copy-not-inherit mapping
  - the separate particle-trail follow-up mapping
- NeoForge GameTest runtime smoke covers:
  - crazy skeleton boss-event wiring smoke
  - crazy skeleton boss-bar color/overlay smoke
  - crazy skeleton boss-percent update smoke
  - crazy skeleton tracked-player add/remove smoke
  - crazy skeleton sky-darkening smoke
  - continued equipment, loot, damage and natural-spawn-deferral stability

## Still Out Of Scope

- `EntityAIAttackCavenicBow`
- Crazy Skeleton custom ranged AI
- Crazy Skeleton natural spawning
- Crazy Creeper / Crazy Spider
- Cavenia
- magic-book or spell systems
