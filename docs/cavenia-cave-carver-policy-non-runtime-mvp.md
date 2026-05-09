# Cavenia Cave Carver Policy / Non-Runtime MVP

This note documents a pure non-runtime cave-carver policy for future active Cavenia generator work.

It builds on:

- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

## What This MVP Adds

Current Reborn now has a pure `core` cave-carver policy:

- `CaveniaCaveCarverPolicy`
- `CaveniaCaveCarverReplacement`

These types encode the source-confirmed `MapGenCaveniaCaves` tunnel-origin band, carve-replacement bands and carve-order relationship without registering or activating a real configured carver, chunk generator, biome source or dimension resource.

This is not an active Cavenia cave carver or generator implementation.

## Exact Constants And Helpers Added

`CaveniaCaveCarverPolicy` now pins:

- `DEFAULT_GENERATE_CAVES = true`
- tunnel-origin base Y `20`
- tunnel-origin random bound `5`
- tunnel-origin range `20..24`
- `SCALE_HEIGHT = 1.35D`
- `MAX_DISTANCE_EXTRA = 18.0F`
- `PITCH_DAMPENING_WHEN_CHANCE = 0.92F`
- `PITCH_DAMPENING_OTHERWISE = 0.7F`

The pure policy also exposes simple helpers for:

- `20..24` tunnel-origin validation
- low gravel-band checks
- low water-band checks
- higher air-carve checks
- replacement lookup by Y
- carve-order checks against `CaveniaTerrainGeneratorPolicy`

## Exact Low-Band Gravel / Water / Air Replacement Rules Encoded

The pure policy keeps the same source-confirmed `MapGenCaveniaCaves#digBlock(...)` replacement order:

- if `y <= 2`, replace with `minecraft:gravel`
- else if `y - 1 < 10`, replace with `minecraft:water`
- otherwise replace with `minecraft:air`

So the carved low band is intentionally not a normal vanilla air carve and not the shared Cavern lava-bottom carve:

- the bottom band becomes gravel
- the next low band becomes water
- the higher carve band becomes air

## Exact Tunnel-Origin Y Behavior Encoded

The pure policy pins the source-confirmed `MapGenCaveniaCaves#recursiveGenerate(...)` tunnel-origin band:

- `blockY = 20 + rand.nextInt(5)`

That means the encoded legacy tunnel-origin Y range is exactly:

- `20`
- `21`
- `22`
- `23`
- `24`

This stays intentionally narrower than the shared `MapGenCavernCaves` origin sampling.

## Relationship To Legacy `MapGenCaveniaCaves` And `MapGenCavernCaves`

This policy is a direct non-runtime follow-up to the earlier cave-carver boundary:

- `MapGenCaveniaCaves` overrides `addTunnel(...)`
- `MapGenCaveniaCaves` overrides `recursiveGenerate(...)`
- `MapGenCaveniaCaves` overrides `digBlock(...)`
- `MapGenCaveniaCaves` still inherits from `MapGenCavernCaves`
- `MapGenCavernCaves` still narrows the vanilla `MapGenCaves` baseline first

So the policy is intentionally narrow, but it preserves the Cavenia-specific carve inputs that later active work must either match or explicitly approximate.

## Relationship To Terrain And Biome Policies

`CaveniaTerrainGeneratorPolicy` already pins the source-confirmed terrain order:

1. base stone fill
2. optional cave carving
3. biome top/filter replacement
4. VEINS generator-side mutation
5. final chunk construction
6. later population stage

`CaveniaBiomeTopFilterPolicy` then pins the weighted biome roster and top/filter inputs used after carving.

So this cave-carver policy intentionally aligns with those other pure policies:

- cave carving happens after base stone fill
- cave carving happens before biome top/filter replacement
- cave carving happens before VEINS mutation

That ordering matters because:

- carved gravel/water/air cavities must exist before biome top/filter replacement sees the primer
- VEINS must see the post-carve, post-top/filter primer rather than a pre-carve stone-only primer

## What This MVP Still Does Not Add

This slice does not add:

- an active Cavenia configured carver
- an active Cavenia configured-carver JSON
- an active Cavenia chunk generator
- an active Cavenia biome source
- active Cavenia noise settings
- active Cavenia configured or placed features
- active Cavenia VEINS/content worldgen
- active Cavenia population
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- any Cavenia worldgen resources
- an active Cavenia runtime level
- an active Cavenia runtime `ServerLevel`
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning
- `cavernreborn:caveman`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Why No Active Configured Carver Or Generator Was Added

The current repository still lacks the runtime decisions needed before an honest active Cavenia cave path exists:

- modern custom carver class design
- configured-carver schema suitability
- integration with the future custom generator
- interaction with world height and bedrock caps
- interaction with top/filter replacement
- interaction with VEINS mutation
- dimension type `height` and `logical_height` values

So this slice pins the source-confirmed carve constants and replacement rules without pretending the project already has a valid active configured-carver or custom-generator implementation.

## Why No Dimension Or Worldgen Resources Were Added

This pure policy does not justify checked-in runtime resources.

Current Reborn still keeps:

- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no active Cavenia configured carver resource
- no active Cavenia noise settings
- no active Cavenia biome-source resources
- no active Cavenia worldgen resources
- no active Cavenia runtime level

The policy is intentionally pure Java only, so the carve contract can be pinned before the project commits to any active runtime shape.

## Next Future Work

Before active cave-carver/generator work starts, the project still needs:

- modern custom carver class design
- configured-carver schema suitability decisions
- integration with the future custom generator
- interaction design for world height and bedrock caps
- interaction design for top/filter replacement
- interaction design for VEINS mutation
- dimension type height/logical-height decisions

This note is therefore a non-runtime cave-carver foundation only, not an active Cavenia cave-carver, biome-source or generator slice.
