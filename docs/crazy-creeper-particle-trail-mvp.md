# Crazy Creeper Particle Trail MVP

This note documents the bounded client-visual particle follow-up for the existing `cavernreborn:crazy_creeper`.

It restores only the legacy Crazy Creeper particle trail. It does not change natural spawning, loot, incoming damage, boss bar / sky-darkening, custom fuse/explosion behavior, lightning/charged/swelling behavior, packets or broader crazy-mob rendering systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyCreeper`
- `cavern.entity.monster.EntityCavenicCreeper`
- `cavern.client.particle.ParticleCrazyMob`
- legacy client-only `onUpdate()` on `EntityCrazyCreeper`
- legacy `ParticlePortal`
- legacy `FMLClientHandler.instance().getClient().effectRenderer.addEffect(...)`
- legacy resources under `../cavern-2/src/main/resources`

## Exact Legacy Particle Behavior Found

- `EntityCrazyCreeper#onUpdate()` is client-only through `@SideOnly(Side.CLIENT)`.
- Inside the `world.isRemote` branch it spawns `3` particles per tick.
- Each loop iteration uses:
  - `var1 = rand.nextInt(2) * 2 - 1`
  - `var2 = rand.nextInt(2) * 2 - 1`
  - `ptX = posX + 0.25D * var1`
  - `ptY = posY + 0.65D + rand.nextFloat()`
  - `ptZ = posZ + 0.25D * var2`
  - `motionX = rand.nextFloat() * 1.0F * var1`
  - `motionY = (rand.nextFloat() - 0.25D) * 0.125D`
  - `motionZ = rand.nextFloat() * 1.0F * var2`
- The spawned particle is `ParticleCrazyMob`.
- `ParticleCrazyMob extends ParticlePortal`.
- `ParticleCrazyMob` only changes the tint:
  - `float f = rand.nextFloat() * 0.5F + 0.4F`
  - `particleRed = particleGreen = particleBlue = 0.65F * f * 0.8F`
- This particle branch matches Crazy Zombie and Crazy Skeleton exactly.
- No packets are involved.
- There is no dedicated legacy particle texture asset in the adjacent `../cavern-2` resources.
- Boss bar / sky-darkening, fuse/explosion and lightning/charged/swelling are separate branches and do not drive the particle trail.

## Reborn Mapping

- Reborn reuses the existing shared particle id: `cavernreborn:crazy_mob`.
- The shared particle type stays a bounded common-side `SimpleParticleType`.
- The shared particle class is `CrazyMobParticle`.
- The shared client implementation stays `CrazyMobParticle extends PortalParticle`.
- The shared client provider stays registered on `RegisterParticleProvidersEvent`.
- Reborn keeps the particle JSON at `assets/cavernreborn/particles/crazy_mob.json`.
- Because the legacy particle reused the portal base and no dedicated crazy texture asset was found, Reborn continues to reuse the vanilla portal sprite set through:
  - `minecraft:generic_0`
  - `minecraft:generic_1`
  - `minecraft:generic_2`
  - `minecraft:generic_3`
  - `minecraft:generic_4`
  - `minecraft:generic_5`
  - `minecraft:generic_6`
  - `minecraft:generic_7`
- No second Crazy Creeper-specific particle id, provider or texture set is added.

## Exact Reborn Entity Hook

- Reborn keeps `CrazyCreeper extends Creeper`.
- Reborn restores the trail through `CrazyCreeper#aiStep()`.
- The hook calls `super.aiStep()` first.
- The particle branch is guarded with a client-only guard:
  - `if (this.level().isClientSide())`
- Reborn spawns the exact legacy count of `3` particles per tick.
- Reborn keeps the source-confirmed position formula:
  - side offsets at `0.25D`
  - base height `0.65D`
  - extra random height from `rand.nextFloat()`
- Reborn keeps the source-confirmed motion formula:
  - X/Z motion from `rand.nextFloat() * 1.0F * +/-1`
  - Y motion from `(rand.nextFloat() - 0.25D) * 0.125D`
- Reborn uses `level().addParticle(ModRegistries.CRAZY_MOB_PARTICLE.get(), ...)` directly and does not add packets.

## What Stays Unchanged

- Boss bar remains unchanged and is documented in `docs/crazy-creeper-boss-bar-mvp.md`.
- Damage remains unchanged and is documented in `docs/crazy-creeper-damage-behavior-mvp.md`.
- Loot remains unchanged and is documented in `docs/crazy-creeper-loot-mvp.md`.
- Boss bar remains unchanged.
- Damage remains unchanged.
- Loot remains unchanged.
- Natural spawning remains deferred.
- Fuse/explosion behavior remains deferred.
- Lightning/charged/swelling behavior remains deferred.
- no custom packets were added
- no Cavenia content was added

## Testing

- Resource tests pin:
  - reuse of the existing `crazy_mob` particle registration in `ModRegistries`
  - reuse of the shared provider registration in `CavernClientModEvents`
  - `CrazyCreeper#aiStep()` and its client-only guard
  - the exact `3` particles-per-tick count
  - the preserved position and motion formulas
  - the `assets/cavernreborn/particles/crazy_mob.json` description
  - continued absence of natural-spawn resources, packet code, custom fuse/explosion code and lightning/charged/swelling code
- Documentation tests pin the exact legacy formulas, the shared `ParticleCrazyMob` finding, the no-dedicated-texture finding, the reused `cavernreborn:crazy_mob` id and the unchanged server-side slices.
- NeoForge GameTest runtime smoke covers:
  - crazy creeper particle type registry id smoke
  - crazy creeper particle provider registration source smoke
  - crazy creeper particle description resource smoke
  - crazy creeper client-only particle spawn-source smoke
  - continued boss, loot, damage and natural-spawn-deferral stability around the new client-only hook
- Actual client visual particle feel remains manual.

## Still Out Of Scope

- Crazy Creeper natural spawning
- Crazy Creeper custom fuse/explosion behavior
- Crazy Creeper lightning/charged/swelling behavior
- Crazy Spider
- Cavenia
- magic-book or spell systems
