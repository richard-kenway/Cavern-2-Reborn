# Crazy Spider Boss Bar / Sky-Darkening MVP

This note documents the bounded boss-event follow-up for the existing `cavernreborn:crazy_spider`.

It restores only the legacy Crazy Spider boss bar / sky-darkening branch. It does not add particles, natural spawning, loot changes, incoming-damage changes, custom combat, blindness/poison behavior, custom AI, custom packets or broader crazy-mob systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySpider`
- `cavern.entity.monster.EntityCavenicSpider`
- `cavern.client.particle.ParticleCrazyMob`
- legacy particle class `ParticleCrazyMob` for out-of-scope client-visual boundary context
- legacy `BossInfoServer bossInfo`
- legacy `updateAITasks()`
- legacy `addTrackingPlayer(...)`
- legacy `removeTrackingPlayer(...)`
- legacy `readEntityFromNBT(...)`
- legacy `setCustomNameTag(...)`
- legacy `isNonBoss()`
- legacy `attackEntityAsMob(...)`
- legacy `getBlindnessAttackPower()`
- legacy `getPoisonAttackPower()`

## Exact Legacy Boss Bar Behavior Found

- `EntityCrazySpider` declares its own `BossInfoServer` directly. It does not inherit the boss event from `EntityCavenicSpider`.
- The boss event uses:
  - `BossInfo.Color.RED`
  - `BossInfo.Overlay.PROGRESS`
- Legacy `EntityCrazySpider#isNonBoss()` returns `false`.
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
- The inspected boss-event branch does not depend on Cavenia, config, spawn type, difficulty, combat state, blindness state, poison state or target state.

## Reborn Mapping

- Reborn keeps `CrazySpider extends Spider`.
- Reborn restores the legacy boss event with a private `ServerBossEvent`.
- The modern mapping uses:
  - `BossEvent.BossBarColor.RED`
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

- The boss event uses the legacy red color and progress overlay.
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

## Why Reborn Keeps `CrazySpider extends Spider`

- Legacy `EntityCrazySpider` extends `EntityCavenicSpider`.
- Reborn `CrazySpider` intentionally extends vanilla `Spider`, not Reborn `CavenicSpider`.
- That baseline decision stays in place so Crazy Spider does not silently inherit staged Reborn natural-spawn, orb-drop, damage, particle or blindness/poison combat behavior that still needs separate inspection.
- Reborn therefore copies only the confirmed boss-event branch explicitly onto `CrazySpider`.

## What Stays Unchanged

- Loot remains unchanged.
- Damage remains unchanged.
- Natural spawning remains deferred.
- Particles remain a separate client-visual follow-up.
- Custom combat, blindness and poison behavior remains deferred.
- Custom AI remains deferred.
- no custom packets were added
- no Cavenia content was added

More specifically:

- loot remains documented in `docs/crazy-spider-loot-mvp.md`
- damage remains documented in `docs/crazy-spider-damage-behavior-mvp.md`

## Testing

- Resource tests pin:
  - the `ServerBossEvent` field
  - the legacy red/progress boss-event styling
  - tracked-player add/remove hooks
  - percent updates from `health / maxHealth`
  - the exact `20.0D` visibility threshold
  - the exact `30.0D` darken-sky threshold
  - continued absence of particle, custom combat and custom AI follow-up code on the boss-event branch
- Documentation tests pin:
  - the exact legacy boss-event references
  - the single global boss-event ambiguity note
  - the explicit copy-not-inherit mapping
  - the separate particle-trail and custom-combat follow-up boundaries
- NeoForge GameTest runtime smoke covers:
  - crazy spider boss-event wiring smoke
  - crazy spider boss-bar color/overlay smoke
  - crazy spider boss-percent update smoke
  - crazy spider tracked-player add/remove smoke
  - crazy spider sky-darkening smoke
  - continued loot, damage and natural-spawn-deferral stability

## Still Out Of Scope

- Crazy Spider particle trail
- Crazy Spider custom combat / blindness / poison behavior
- Crazy Spider custom AI
- Crazy Spider natural spawning
- summon variants
- direct Cavenic mobs
- non-Crazy mobs
- Cavenia
- magic-book or spell systems
