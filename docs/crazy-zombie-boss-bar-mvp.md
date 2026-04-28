# Crazy Zombie Boss Bar / Sky-Darkening MVP

This note documents the bounded boss-event follow-up for the existing `cavernreborn:crazy_zombie`.

It restores only the legacy Crazy Zombie boss bar / sky-darkening branch. It does not add particles, natural spawning, loot changes, incoming-damage changes, knockback-on-hit changes, custom AI, custom packets or broader crazy-mob systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.entity.monster.EntityCavenicZombie`
- `cavern.client.particle.ParticleCrazyMob`
- legacy `BossInfoServer bossInfo`
- legacy `updateAITasks()`
- legacy `addTrackingPlayer(...)`
- legacy `removeTrackingPlayer(...)`
- legacy `readEntityFromNBT(...)`
- legacy `setCustomNameTag(...)`
- legacy `isNonBoss()`

## Exact Legacy Boss Bar Behavior Found

- `EntityCrazyZombie` owns a `BossInfoServer` with:
  - `BossInfo.Color.BLUE`
  - `BossInfo.Overlay.PROGRESS`
- Legacy `EntityCrazyZombie#isNonBoss()` returns `false`.
- Legacy `updateAITasks()` keeps the boss event server-side and updates:
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

- Legacy uses the same single global boss event for all tracked players.
- The legacy code iterates the tracked-player set, stores the last checked distance, and breaks early on the first visible player within `20.0D`.
- It then applies `bossInfo.setDarkenSky(!canSee || distance < 30.0D)`.
- In single-player deterministic cases, that means:
  - a nearby visible tracked player keeps darkening enabled
  - a blocked tracked player also keeps darkening enabled
- Multiplayer semantics are slightly ambiguous because the legacy code uses one global boss event while mutating it inside a player loop. Reborn keeps that same single global boss event rather than inventing per-player events.

## Reborn Mapping

- Reborn keeps `CrazyZombie extends Zombie`.
- Reborn restores the legacy boss event with a private `ServerBossEvent`.
- The modern mapping uses:
  - `BossEvent.BossBarColor.BLUE`
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

- The boss event uses the legacy blue color and progress overlay.
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

## Why Reborn Keeps `CrazyZombie extends Zombie`

- Legacy `EntityCrazyZombie` extends `EntityCavenicZombie`.
- Reborn `CrazyZombie` intentionally extends vanilla `Zombie`, not Reborn `CavenicZombie`.
- That baseline decision stays in place so Crazy Zombie does not silently inherit staged Reborn Cavenic natural-spawn, damage, loot or other follow-up behavior that still needs separate inspection.
- Reborn therefore copies only the confirmed boss-event branch explicitly onto `CrazyZombie`.

## What Stays Unchanged

- knockback-on-hit remains unchanged and is documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`
- damage behavior remains unchanged and is documented in `docs/crazy-zombie-damage-behavior-mvp.md`
- loot behavior remains unchanged and is documented in `docs/crazy-zombie-loot-mvp.md`
- natural spawning remains deferred and is documented in `docs/crazy-zombie-natural-spawn-absent-or-deferred.md`
- particle trail is now restored separately in `docs/crazy-zombie-particle-trail-mvp.md`
- no custom packets were added
- no custom AI was added
- no Cavenia content was added

## Testing

- Resource tests pin:
  - the `ServerBossEvent` field
  - the legacy blue/progress boss-event styling
  - tracked-player add/remove hooks
  - percent updates from `health / maxHealth`
  - the exact `20.0D` visibility threshold
  - the exact `30.0D` darken-sky threshold
  - continued absence of custom packets and natural-spawn wiring on the boss-event branch
- Documentation tests pin:
  - the exact legacy boss-event references
  - the single global boss event ambiguity note
  - the explicit copy-not-inherit mapping
  - the separate particle-trail document boundary
- NeoForge GameTest runtime smoke covers:
  - crazy zombie boss-event wiring smoke
  - crazy zombie boss-bar color/overlay smoke
  - crazy zombie boss-percent update smoke
  - crazy zombie tracked-player add/remove smoke
  - crazy zombie sky-darkening smoke
  - continued loot, damage, knockback and natural-spawn-deferral stability

## Still Out Of Scope

- Crazy Zombie natural spawning
- Crazy Zombie custom loot beyond the restored inherited orb-drop branch
- Crazy Zombie custom AI
- Crazy Skeleton / Crazy Creeper / Crazy Spider
- Cavenia
- magic-book or spell systems
