# Cavenic Shroom & Orb MVP

This note documents the first bounded cavenic content slice for modern Reborn.

It is intentionally a small hazardous flora block plus a rare collectible foundation item, not the full legacy cavenic ecosystem.

## Scope

- Block id:
  - `cavernreborn:cavenic_shroom`
- Item id:
  - `cavernreborn:cavenic_orb`

## Behavior

- `cavenic_shroom` is a small hazardous cave flora block.
- Colliding with it applies brief nausea.
- The shroom spreads slowly and stays locally bounded instead of rewriting whole biomes.
- Using shears on the shroom cleanly harvests it for relocation, always damages the shears by `1` and has a bounded `10%` chance to drop one `cavenic_orb`.
- Normal break behavior stays simple, and any orb acquisition outside the shears path remains bounded and intentionally rare.

## Placement And Worldgen

- The shroom can only survive on a bounded support baseline:
  - `minecraft:stone`
  - `minecraft:mycelium`
  - `minecraft:podzol`
  - `minecraft:moss_block`
  - `minecraft:dirt`
  - `minecraft:coarse_dirt`
- Sparse natural acquisition is limited to `stone_depths`.
- The checked-in worldgen keys are:
  - `cavernreborn:cavenic_shroom_patch`
  - `cavernreborn:cavern_cavenic_shroom_patch`

## Cavenic Orb Bridge

- `cavenic_orb` is introduced here as a rare collectible foundation item.
- The orb does not add gameplay behavior by itself in this MVP.
- Orb acquisition from `cavenic_shroom` is a bounded modern Reborn bridge for later cavenic equipment work, not full legacy ecosystem parity.

## Explicit Non-Goals

- No cavenic weapons yet.
- No cavenic mobs yet.
- No entity transformation yet.
- No Cavenia yet.
- No GUI, packets, progression unlocks or economy hooks.

## Runtime Smoke Boundary

- NeoForge GameTest runtime smoke covers:
  - registry ids
  - bounded collision nausea
  - bounded shear-harvest helper wiring
  - orb drop policy/runtime smoke
  - worldgen-key resolution
- Manual smoke is still required for actual hazard feel, sparse spread feel and final visual rendering.

## Asset Provenance

- `cavenic_shroom` and `cavenic_orb` textures were copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.
