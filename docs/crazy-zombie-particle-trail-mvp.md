# Crazy Zombie Particle Trail MVP

This note documents the bounded client-visual particle follow-up for the existing `cavernreborn:crazy_zombie`.

It restores only the legacy Crazy Zombie particle trail. It does not change natural spawning, loot, incoming damage, knockback-on-hit, boss bar / sky-darkening, custom AI, packets or broader crazy-mob rendering systems.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazyZombie`
- `cavern.client.particle.ParticleCrazyMob`
- legacy client-only `onUpdate()` on `EntityCrazyZombie`
- legacy `ParticlePortal`
- legacy `FMLClientHandler.instance().getClient().effectRenderer.addEffect(...)`
- legacy resources under `../cavern-2/src/main/resources`

## Exact Legacy Particle Behavior Found

- `EntityCrazyZombie#onUpdate()` is client-only through `@SideOnly(Side.CLIENT)`.
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
- No packets are involved.
- There is no dedicated legacy particle texture asset in the adjacent `../cavern-2` resources.
- Boss bar, sky-darkening and knockback-on-hit are separate branches and do not drive the particle trail.

## Reborn Mapping

- Reborn registers one shared particle id: `cavernreborn:crazy_mob`.
- The particle type is a bounded common-side `SimpleParticleType`.
- The client implementation is `CrazyMobParticle extends PortalParticle`.
- The client provider is registered on `RegisterParticleProvidersEvent`.
- Reborn keeps the particle JSON at `assets/cavernreborn/particles/crazy_mob.json`.
- Because the legacy particle reused the portal base and no dedicated crazy texture asset was found, Reborn reuses the vanilla portal sprite set through:
  - `minecraft:generic_0`
  - `minecraft:generic_1`
  - `minecraft:generic_2`
  - `minecraft:generic_3`
  - `minecraft:generic_4`
  - `minecraft:generic_5`
  - `minecraft:generic_6`
  - `minecraft:generic_7`
- `CrazyMobParticle` preserves the legacy recolor formula while leaving the inherited portal scale/lifetime behavior on the modern `PortalParticle` base.

## Exact Reborn Entity Hook

- Reborn keeps `CrazyZombie extends Zombie`.
- Reborn restores the trail through `CrazyZombie#aiStep()`.
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
- Reborn uses `level().addParticle(...)` directly and does not add packets.

## What Stays Unchanged

- boss bar remains unchanged and is documented in `docs/crazy-zombie-boss-bar-mvp.md`
- knockback remains unchanged and is documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`
- damage remains unchanged and is documented in `docs/crazy-zombie-damage-behavior-mvp.md`
- loot remains unchanged and is documented in `docs/crazy-zombie-loot-mvp.md`
- natural spawning remains deferred and is documented in `docs/crazy-zombie-natural-spawn-absent-or-deferred.md`
- Reborn keeps `CrazyZombie extends Zombie`
- no custom packets were added
- no Cavenia content was added

## Testing

- Resource tests pin:
  - `crazy_mob` particle registration in `ModRegistries`
  - client provider registration in `CavernClientModEvents`
  - `CrazyMobParticle extends PortalParticle`
  - the legacy recolor formula
  - the `CrazyZombie#aiStep()` client-only guard
  - the exact `3` particles-per-tick count
  - the preserved position and motion formulas
  - the `assets/cavernreborn/particles/crazy_mob.json` description
  - continued absence of natural-spawn resources, packet code and legacy `ParticleCrazyMob` imports
- Documentation tests pin the exact legacy formulas, the no-dedicated-texture finding, the `cavernreborn:crazy_mob` id and the unchanged server-side slices.
- NeoForge GameTest runtime smoke covers:
  - crazy zombie particle type registry id smoke
  - continued boss, loot, damage, knockback and natural-spawn-deferral stability around the new client-only hook
- Actual client visual particle feel remains manual.

## Still Out Of Scope

- Crazy Zombie natural spawning
- Crazy Zombie custom loot beyond the restored inherited orb-drop branch
- Crazy Zombie custom AI
- Crazy Skeleton / Crazy Creeper / Crazy Spider
- Cavenia
- magic-book or spell systems
