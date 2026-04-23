# Acresia Crop & Food MVP

This note documents the first bounded `Acresia` crop-and-food slice for modern Reborn.

It is intentionally a small renewable cave snack crop, not a full farming system.

## Scope

- Block id:
  - `cavernreborn:acresia`
- Item ids:
  - `cavernreborn:acresia_seeds`
  - `cavernreborn:acresia_fruits`

## Behavior

- `acresia` is a cave crop with ages `0..4`.
- `acresia_seeds` plants the crop.
- `acresia_fruits` is edible.
- Mature `acresia` drops a small bounded seed-and-fruit loop when harvested normally.
- Mature `acresia` can also be harvested with shears for bounded extra fruit and a regrowth reset.
- The shear-harvest path resets the crop to age `2`.

## Planting And Growth

- Plantable support is intentionally bounded to:
  - `minecraft:farmland`
  - `minecraft:grass_block`
  - `minecraft:dirt`
  - `minecraft:coarse_dirt`
  - `minecraft:rooted_dirt`
  - `minecraft:moss_block`
  - `minecraft:mycelium`
  - `minecraft:podzol`
- The crop uses normal random crop growth behavior, but it stays cave-compatible instead of requiring bright overworld farmland light.
- Bone meal compatibility remains the inherited vanilla-style crop path.

## Shear Harvest

- The special harvest path only works at max age.
- Mature shear harvest yields `4 + rand(0..2)` fruits, plus up to `+2` bounded Fortune bonus when Fortune is present on the shears.
- The crop is not destroyed by the shear-harvest path.
- Shears take `1` durability damage.
- No advancement, extra XP, custom particles or custom sounds are added in this MVP.

## Worldgen

- Natural acquisition is limited to small `lush_grotto` patches.
- The checked-in worldgen keys are:
  - `cavernreborn:acresia_patch`
  - `cavernreborn:cavern_acresia_patch_lush`
- The patch is intentionally sparse and bounded.
- No new progression unlock, mob AI, economy layer or service dependency is involved.

## Explicit Non-Goals

- No mob AI or Durang Hog interaction.
- No advancement or economy integration.
- No large agriculture system.
- No new progression unlock.
- No GUI, packets or client-side farming systems.

## Runtime Smoke Boundary

- NeoForge GameTest runtime smoke covers:
  - registry ids
  - seed planting
  - mature normal drops
  - bounded shear harvest and regrowth
  - edible fruit wiring
  - worldgen-key resolution
- Manual smoke is still required for in-world farming feel, growth cadence and final visual rendering.

## Asset Provenance

- `acresia` stage textures and the `acresia_seeds` / `acresia_fruits` item textures were copied from the adjacent legacy `../cavern-2` repository.
- The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0.
