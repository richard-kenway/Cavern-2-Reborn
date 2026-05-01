# Crazy Skeleton Legacy Loot / Orb Drop MVP

This note documents the bounded loot follow-up for the existing `cavernreborn:crazy_skeleton`.

It does not add natural spawning, equipment changes, ranged-AI changes, damage behavior, boss-event changes, particles or broader crazy-mob combat systems. It only restores the small inherited legacy loot branch from `EntityCavenicSkeleton`.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.item.ItemCave`
- legacy `dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)` handling on `EntityCavenicSkeleton`
- legacy `setEquipmentBasedOnDifficulty(...)` and `initCustomAI()` on `EntityCrazySkeleton` for out-of-scope equipment/ranged-AI boundary context
- legacy `BossInfoServer bossInfo` and particle spawning on `EntityCrazySkeleton` for boss/visual boundary context

## Exact Legacy Behavior Found

- `EntityCrazySkeleton` does not override `dropLoot(...)`.
- It inherits `EntityCavenicSkeleton#dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`.
- That inherited method:
  - calls `super.dropLoot(...)` first
  - runs `rand.nextInt(5) == 0`
  - drops `ItemCave.EnumType.CAVENIC_ORB.getItemStack()` at offset `0.5F` on the winning roll
- The inherited drop branch does not use `lootingModifier`.
- The inherited drop branch does not check `wasRecentlyHit`.
- No direct Crazy Skeleton loot-table override was found in legacy.
- No direct Crazy Skeleton non-orb custom drop branch was found.
- The guaranteed `Cavenic Bow` + `Infinity` equipment path is separate and remains documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md`.
- The custom ranged identity remains documented separately in `docs/crazy-skeleton-ranged-ai-boundary.md`.

## Reborn Mapping

- Reborn preserves the vanilla skeleton loot table as the baseline drop source through `CrazySkeleton#getDefaultLootTable()`.
- Reborn does not add a custom entity loot-table JSON for `crazy_skeleton`.
- Reborn appends the inherited legacy orb drop through `app.entity.CrazySkeletonLootEvents`.
- The pure roll policy lives in `core.loot.CrazySkeletonLootPolicy`.
- `CrazySkeletonLootPolicy.ORB_DROP_ROLL_BOUND = 5` pins the inherited legacy `1/5` chance.
- The winning roll is `0`, so the orb chance remains exactly `1/5`.

## Why The Behavior Is Copied Explicitly

- Legacy `EntityCrazySkeleton` inherits the loot behavior from `EntityCavenicSkeleton`.
- Reborn `CrazySkeleton` intentionally extends vanilla `Skeleton`, not Reborn `CavenicSkeleton`.
- That baseline decision stays in place so Crazy Skeleton does not silently inherit staged Reborn natural-spawn, damage, orb-drop or later Cavenic Skeleton-specific follow-ups.
- Reborn therefore copies only the confirmed inherited loot branch explicitly onto `CrazySkeleton`.

## Exact Reborn Behavior

- Vanilla skeleton loot remains the base loot path.
- `cavernreborn:cavenic_orb` is appended independently of the vanilla skeleton drops.
- The orb chance is `1/5`.
- A winning roll adds exactly one `cavernreborn:cavenic_orb`.
- A losing roll adds nothing.
- The appended drop uses the inherited legacy `0.5D` Y offset.
- Looting does not affect the new orb drop.
- A player kill is not required for the orb drop, because the inspected legacy branch ignores `wasRecentlyHit`.
- Progression, dimension, economy, Cavenia, boss state, spawn path and guaranteed `Cavenic Bow` equipment do not affect this restored orb drop.
- Crazy Skeleton natural spawning remains explicitly deferred because the inspected legacy path is still tied to the old Cavenia-only crazy-roster provider/config branch.
- Crazy Skeleton damage behavior remains documented separately in `docs/crazy-skeleton-damage-behavior-mvp.md`.
- Crazy Skeleton boss bar / sky-darkening remains documented separately in `docs/crazy-skeleton-boss-bar-mvp.md`.
- Crazy Skeleton particle trail now remains documented separately in `docs/crazy-skeleton-particle-trail-mvp.md`.
- Crazy Skeleton custom ranged AI remains out of scope in this slice.

## Testing

- Core tests pin the `1/5` orb-drop policy and reject out-of-range rolls.
- Resource tests pin:
  - continued vanilla skeleton loot-table baseline usage
  - `CrazySkeletonLootEvents` registration from `CavernReborn`
  - the `LivingDropsEvent` listener wiring
  - the continued `Skeleton` base instead of Reborn `CavenicSkeleton`
  - the preserved `2000.0D` source literal
  - the preserved guaranteed `Cavenic Bow` + `Infinity` equipment hook and `1.0F` mainhand drop chance
  - the continued absence of natural-spawn wiring and custom ranged-AI overrides beyond the explicit particle, damage and boss hooks
- Documentation tests pin the legacy source references, the inherited `1/5` orb chance, the explicit copy-not-inherit mapping and the unchanged natural-spawn/equipment/ranged-AI boundaries.
- NeoForge GameTest runtime smoke covers:
  - crazy skeleton vanilla skeleton loot-table baseline smoke
  - crazy skeleton legacy orb-drop event wiring smoke
  - crazy skeleton legacy orb-drop deterministic winning/losing roll smoke
  - continued runtime `1024.0` max-health clamp stability
  - continued guaranteed `Cavenic Bow` + `Infinity` equipment stability
  - continued natural-spawn deferral through absent placement/biome-modifier/biome-tag wiring
  - continued absence of particle and custom ranged-AI follow-up overrides beyond the explicit damage and boss hooks

## Still Out Of Scope

- `EntityAIAttackCavenicBow`
- Crazy Skeleton custom ranged AI
- Crazy Skeleton natural spawning
- Crazy Skeleton damage behavior
- Crazy Creeper follow-up branches documented after `docs/crazy-creeper-baseline-mvp.md` / Crazy Spider
- Cavenia
- magic-book or spell systems
