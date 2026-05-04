# Crazy Creeper Fuse / Explosion MVP

This note documents the bounded fuse/explosion follow-up for the existing `cavernreborn:crazy_creeper`.

It restores only the source-confirmed fixed creeper values from legacy `EntityCrazyCreeper`. It does not add natural spawning, loot changes, damage-behavior changes, boss changes, particle changes, custom explosion-event rewrites or Cavenia code.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- legacy `applyCustomValues()`
- legacy `fuseTime`
- legacy `explosionRadius`
- legacy `attackEntityFrom(...)`, `dropLoot(...)`, boss-info and `ParticleCrazyMob` branches for boundary context
- modern `net.minecraft.world.entity.monster.Creeper`

## Exact Legacy Fuse / Explosion Behavior Found

- `EntityCrazyCreeper` directly overrides `applyCustomValues()`.
- That override sets:
  - `fuseTime = 150`
  - `explosionRadius = 30`
- It then calls `super.applyCustomValues()`.
- Legacy `EntityCavenicCreeper#applyCustomValues()` pushes those values into the vanilla `EntityCreeper` private fields through `CaveUtils.setPrivateValue(...)`.
- Legacy `EntityCavenicCreeper` itself carries smaller defaults:
  - `fuseTime = 15`
  - `explosionRadius = 5`

## Exact Legacy Charged / Lightning Behavior Found

- I found no `onStruckByLightning(...)` override on `EntityCrazyCreeper`.
- I found no direct `powered`, `charged`, `ignite`, `explode`, `createExplosion`, `mobGriefing` or Forge explosion-event override on `EntityCrazyCreeper`.
- The legacy class therefore keeps vanilla creeper charged/lightning behavior and only changes the fixed fuse/radius values.
- The existing orb-drop, incoming-damage, boss-event and particle-trail branches are separate.
- The dedicated boundary note for that inherited branch now lives in `docs/crazy-creeper-lightning-charged-swelling-boundary.md`.

## Reborn Mapping

- Reborn keeps the implementation entity-local in `app.entity.CrazyCreeper`.
- Modern 1.21.1 `Creeper` persists these values under the save-data keys:
  - `Fuse`
  - `ExplosionRadius`
- Reborn restores the legacy values through:
  - `LEGACY_FUSE_TIME = 150`
  - `LEGACY_EXPLOSION_RADIUS = 30`
- The constructor calls `applyLegacyFuseAndExplosionValues()`, which feeds a narrow `CompoundTag` through vanilla `Creeper.readAdditionalSaveData(...)`.
- `CrazyCreeper` also overrides `addAdditionalSaveData(CompoundTag tag)` and writes the same values back so save/load-style round trips stay pinned.

## Exact Reborn Behavior

- New `cavernreborn:crazy_creeper` instances now resolve `Fuse = 150`.
- New `cavernreborn:crazy_creeper` instances now resolve `ExplosionRadius = 30`.
- Saved Crazy Creeper data keeps those values pinned across a save/load-style round trip.
- Loot/orb behavior remains unchanged.
- Incoming damage behavior remains unchanged.
- Boss bar / sky-darkening remains unchanged.
- Particle trail remains unchanged.
- Natural spawning remains deferred through the Cavenia crazy-roster boundary.

## Charged / Griefing Boundary

- Reborn does not add a local `thunderHit(...)` override.
- Reborn does not add local powered-state fields or a custom charged multiplier.
- Reborn does not add a custom explosion-event path, custom fire flag or custom block-damage path.
- The restored values therefore compose with the inherited modern vanilla creeper explosion path, which still owns charged-state handling and mob-griefing interaction.
- The inspected inherited-vanilla lightning/charged/swelling contract is now documented separately in `docs/crazy-creeper-lightning-charged-swelling-boundary.md`.
- Actual destructive explosion gameplay feel remains a manual smoke concern rather than a GameTest terrain-destruction assertion.

## Why This Mapping Is Safe

- The legacy Crazy Creeper branch was only a fixed-value field write, not a custom explosion system.
- Reborn already uses the same narrow save-data mapping successfully for `CavenicCreeper`.
- No mixins, access wideners, custom packets or global creeper rewrites are needed.
- `CrazyCreeper` still extends vanilla `Creeper`, not Reborn `CavenicCreeper`, so the fuse/explosion branch is copied explicitly instead of inheriting unrelated staged behavior.

## Testing

- Resource tests pin:
  - `LEGACY_FUSE_TIME = 150`
  - `LEGACY_EXPLOSION_RADIUS = 30`
  - constructor-time save-data injection
  - save-data round-trip pinning
  - continued absence of custom `thunderHit(...)`, custom explosion methods and natural-spawn wiring
- NeoForge GameTest runtime smoke covers:
  - crazy creeper legacy fuse-time smoke
  - crazy creeper legacy explosion-radius smoke
  - save/load-style round-trip pinning
  - unchanged vanilla creeper loot-table baseline
  - unchanged inherited `1/5` orb-drop wiring
  - unchanged fall-damage reduction / fire-damage immunity
  - unchanged green boss-event styling
  - unchanged shared `crazy_mob` particle registration
  - unchanged no-natural-spawn Cavenia boundary

## Still Out Of Scope

- Custom explosion-event rewrites
- Destructive terrain-parity checks
- Custom lightning/charged/swelling overrides
- Cavenia active natural spawning
- Crazy Spider and other non-Creeper follow-ups
