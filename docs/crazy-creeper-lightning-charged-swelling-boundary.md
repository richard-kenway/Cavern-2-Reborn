# Crazy Creeper Lightning / Charged / Swelling Boundary

This note documents the inspected lightning / charged / swelling branch for the existing `cavernreborn:crazy_creeper`.

It adds no gameplay code. The legacy source shows no Crazy Creeper-specific override for lightning, powered state or swelling state, so Reborn keeps the inherited vanilla `Creeper` path on top of the already restored fixed `Fuse = 150` and `ExplosionRadius = 30` values.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- legacy vanilla `EntityCreeper` charged/lightning/swelling behavior through the inherited class chain
- legacy searches for:
  - `onStruckByLightning`
  - `powered`
  - `charged`
  - `ignite`
  - `setCreeperState`
  - `getCreeperState`
  - `timeSinceIgnited`
  - `lastActiveTime`
  - `explode`
  - `createExplosion`
  - `mobGriefing`
- modern `net.minecraft.world.entity.monster.Creeper`

## Exact Legacy Lightning / Charged / Swelling Behavior Found

- `EntityCrazyCreeper` does not override `onStruckByLightning(...)`.
- `EntityCavenicCreeper` also does not override `onStruckByLightning(...)`.
- I found no direct `powered` or `charged` state override on either legacy class.
- I found no direct override of:
  - `ignite`
  - `hasIgnited`
  - `setCreeperState`
  - `getCreeperState`
  - `timeSinceIgnited`
  - `lastActiveTime`
- I found no custom swelling/timer field or custom charged multiplier on either legacy class.
- I found no direct Crazy Creeper explosion-event, block-griefing, fire-flag or `mobGriefing` rewrite in this branch.

## What Legacy Actually Changes

- The direct Crazy Creeper explosion identity found in legacy is still only:
  - `fuseTime = 150`
  - `explosionRadius = 30`
- The direct Cavenic Creeper base identity found in legacy is:
  - `fuseTime = 15`
  - `explosionRadius = 5`
- Those fixed values are already restored separately in `docs/crazy-creeper-fuse-explosion-mvp.md`.
- Beyond those fixed values, legacy Crazy Creeper appears to inherit the vanilla creeper charged/lightning/swelling path unchanged.

## Reborn Boundary Decision

- Reborn does not add a local `thunderHit(...)` override on `CrazyCreeper`.
- Reborn does not add local powered-state fields or a local copy of `DATA_IS_POWERED`.
- Reborn does not add local swelling-state fields or local copies of `DATA_SWELL_DIR` / ignited data accessors.
- Reborn does not add a local charged multiplier, local `explodeCreeper(...)` override or local explosion interaction rewrite.
- Reborn therefore keeps the inherited modern vanilla `Creeper` charged/lightning/swelling behavior on top of the already restored fixed `Fuse = 150` and `ExplosionRadius = 30` values.

## Why No Gameplay Code Was Added

- The inspected legacy branch contains no direct Crazy Creeper-specific lightning/charged/swelling logic to port.
- Adding a local `thunderHit(...)` override, local powered-state fields or custom swelling hooks would invent behavior that the inspected legacy classes do not declare.
- The current modern vanilla creeper path already owns:
  - powered-state storage
  - lightning-to-powered transition
  - ignited state
  - swelling progression
  - charged explosion multiplier
- Destructive terrain parity and charged explosion feel remain separate manual or future concerns.

## What Remains Unchanged

- Fixed `Fuse = 150` / `ExplosionRadius = 30` mapping
- Vanilla creeper loot baseline
- Inherited `1/5` `cavenic_orb` drop
- Inherited fall-damage reduction / fire-damage immunity
- Green boss bar / sky-darkening
- Shared `crazy_mob` particle trail
- Natural-spawn deferral through the Cavenia crazy-roster boundary
- Reborn `CavenicCreeper`

## Testing

- Resource tests pin:
  - no `CrazyCreeper#thunderHit(...)` override
  - no local `explodeCreeper(...)` override
  - no local powered/swelling data-accessor copy
  - no local charged multiplier or explosion-interaction path
  - continued presence of `LEGACY_FUSE_TIME = 150`
  - continued presence of `LEGACY_EXPLOSION_RADIUS = 30`
- Documentation tests pin the inspected source findings and the explicit inherited-vanilla boundary.
- NeoForge GameTest runtime smoke covers:
  - crazy creeper saved `Fuse = 150`
  - crazy creeper saved `ExplosionRadius = 30`
  - reflection coverage that `CrazyCreeper` still declares no local `thunderHit(...)`
  - reflection coverage that `CrazyCreeper` still declares no local `explodeCreeper(...)`
  - unchanged no-natural-spawn Cavenia boundary

## Still Out Of Scope

- Destructive explosion gameplay parity
- Charged explosion feel parity beyond inherited vanilla behavior
- Custom lightning/charged/swelling overrides
- Custom explosion-event rewrites
- Cavenia active natural spawning
