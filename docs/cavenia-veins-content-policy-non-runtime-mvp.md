# Cavenia VEINS / Content Policy / Non-Runtime MVP

This note documents a pure non-runtime VEINS/content policy for future active Cavenia generator work.

It builds on:

- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

## What This MVP Adds

Current Reborn now has a pure `core` VEINS/content policy:

- `CaveniaVeinsContentPolicy`
- `CaveniaVeinEntry`
- `CaveniaVeinBiomeFilterMode`

These types encode the source-confirmed legacy `CaveniaConfig.VEINS` defaults, default target/chance settings, `autoVeins` default and VEINS ordering without registering or activating configured features, placed features, biome modifiers, a chunk generator, a biome source or a dimension resource.

This is not an active Cavenia VEINS/content implementation or generator implementation.

## Exact Defaults Added

`CaveniaVeinsContentPolicy` now pins:

- default target block id `minecraft:stone`
- default target block meta `0`
- default chance `1.0D`
- `autoVeins = false`

That matches the inspected legacy shorthand constructor path used by `CaveniaConfig.syncVeinsConfig()`.

## Exact Shipped Default Entries And Parameters Added

The pure policy keeps the shipped default `CaveniaConfig.VEINS` roster exactly as inspected:

- granite via `minecraft:stone` meta `1`, target `minecraft:stone` meta `0`, weight `28`, chance `1.0D`, size `25`, minY `1`, maxY `255`
- diorite via `minecraft:stone` meta `3`, target `minecraft:stone` meta `0`, weight `28`, chance `1.0D`, size `25`, minY `1`, maxY `255`
- andesite via `minecraft:stone` meta `5`, target `minecraft:stone` meta `0`, weight `30`, chance `1.0D`, size `25`, minY `1`, maxY `255`
- coal ore via `minecraft:coal_ore` meta `0`, target `minecraft:stone` meta `0`, weight `50`, chance `1.0D`, size `17`, minY `1`, maxY `127`
- iron ore via `minecraft:iron_ore` meta `0`, target `minecraft:stone` meta `0`, weight `40`, chance `1.0D`, size `10`, minY `1`, maxY `127`
- aquamarine ore via `cavern:cave_block` meta `0`, target `minecraft:stone` meta `0`, weight `12`, chance `1.0D`, size `8`, minY `20`, maxY `127`, biome filters from `Type.COLD`, `Type.WATER` and `Type.WET`
- magnite ore via `cavern:cave_block` meta `2`, target `minecraft:stone` meta `0`, weight `30`, chance `1.0D`, size `10`, minY `1`, maxY `127`
- randomite ore via `cavern:cave_block` meta `4`, target `minecraft:stone` meta `0`, weight `24`, chance `1.0D`, size `4`, minY `1`, maxY `127`
- hexcite ore via `cavern:cave_block` meta `5`, target `minecraft:stone` meta `0`, weight `4`, chance `1.0D`, size `5`, minY `1`, maxY `30`
- fissured stone via `cavern:cave_block` meta `7`, target `minecraft:stone` meta `0`, weight `150`, chance `1.0D`, size `2`, minY `1`, maxY `127`
- dirt via `minecraft:dirt` meta `0`, target `minecraft:stone` meta `0`, weight `20`, chance `1.0D`, size `25`, minY `1`, maxY `127`
- gravel via `minecraft:gravel` meta `0`, target `minecraft:stone` meta `0`, weight `10`, chance `1.0D`, size `20`, minY `1`, maxY `127`
- sand via `minecraft:sand` meta `0`, target `minecraft:stone` meta `0`, weight `10`, chance `1.0D`, size `20`, minY `1`, maxY `127`, biome filters from `Type.SANDY`

The shipped default entry count is `13`.

The shipped default total weight is `436`.

## Exact Biome-Filter Behavior Added

The non-runtime policy also pins the source-as-written biome-filter mode from legacy `VeinGenerator`.

For the shipped defaults:

- aquamarine ore uses an exclusion-style filter branch sourced from `Type.COLD`, `Type.WATER` and `Type.WET`
- sand uses an exclusion-style filter branch sourced from `Type.SANDY`
- all other shipped defaults have no biome filters

That wording is intentional.

`CaveVein` stores concrete biome registry-name strings at runtime, but the source-confirmed default inputs are those `BiomeDictionary.Type` markers, and `VeinGenerator` then skips placement when the current biome matches one of the stored biome ids.

## Shipped Defaults That Are Absent

The shipped Cavenia defaults do not include:

- `minecraft:gold_ore`
- `minecraft:redstone_ore`
- `minecraft:lapis_ore`
- `minecraft:diamond_ore`
- `minecraft:emerald_ore`

So the Cavenia roster is not a simple copy of normal Cavern content.

## Relationship To Legacy `CaveniaConfig.VEINS`, `CaveVeinManager` And `VeinGenerator`

This policy is a direct non-runtime encoding of the inspected legacy content stack:

- `CaveniaConfig.VEINS` owns the shipped configurable roster
- `CaveVeinManager` stores that roster
- `CaveVeinProvider` either exposes that configured roster or switches to dynamic fallback when `autoVeins = true`
- `VeinGenerator` applies the generator-side mutation inside `ChunkGeneratorCavenia`

The pure policy intentionally keeps only the shipped default roster and ordering contract. It does not implement the old dynamic `autoVeins = true` ore-dictionary fallback.

## Relationship To `ChunkGeneratorCavenia` And The Other Pure Policies

`ChunkGeneratorCavenia#generateChunk(...)` applies VEINS after:

- base stone fill
- optional cave carving
- biome top/filter replacement

and before:

- final chunk construction
- later population

So `CaveniaVeinsContentPolicy` intentionally aligns with:

- `CaveniaTerrainGeneratorPolicy`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaCaveCarverPolicy`

That ordering matters because:

- VEINS mutation must happen after cave carving
- VEINS mutation must happen after biome top/filter replacement
- cave carving must establish the gravel/water/air carve bands first
- top/filter replacement must run before VEINS mutates remaining stone targets
- VEINS must still remain generator-side mutation, not a later population-only pass

## What This MVP Still Does Not Add

This slice does not add:

- active Cavenia configured features
- active Cavenia placed features
- active Cavenia biome modifiers
- active Cavenia chunk generator
- active Cavenia biome source
- active Cavenia worldgen resources
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- an active Cavenia runtime level
- an active Cavenia runtime `ServerLevel`
- active Cavenia access or teleport
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning
- `cavernreborn:caveman`

`EntityCaveman -> deferred:caveman` remains unchanged and intentional.

## Why No Active Configured/Placed Features Or Generator Were Added

The repository still lacks the runtime decisions needed before an honest active Cavenia VEINS/content path exists:

- modern configured/placed feature mapping
- whether VEINS stays generator-side or becomes a hybrid
- interaction with the future custom generator
- interaction with top/filter replacement
- interaction with cave-carver replacement bands
- interaction with the future biome source
- dimension type `height` and `logical_height` values

So this slice pins the shipped VEINS/content defaults and ordering without pretending the project already has an active configured-feature or custom-generator implementation.

## Why No Dimension Or Worldgen Resources Were Added

This pure policy does not justify checked-in runtime resources.

Current Reborn still keeps:

- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no active Cavenia configured features
- no active Cavenia placed features
- no active Cavenia biome modifiers
- no active Cavenia biome-source resources
- no active Cavenia worldgen resources
- no active Cavenia runtime level

The policy is intentionally pure Java only, so the VEINS/content contract can be pinned before the project commits to an active runtime shape.

## Next Future Work

Before active VEINS/generator work starts, the project still needs:

- modern configured/placed feature mapping decisions
- a decision on whether VEINS stays generator-side or becomes hybrid
- integration with the future custom generator
- interaction design for top/filter replacement
- interaction design for cave-carver replacement bands
- future biome-source integration decisions
- dimension type height/logical-height decisions

This note is therefore a non-runtime VEINS/content foundation only, not an active Cavenia configured-feature, placed-feature, biome-source or generator slice.
