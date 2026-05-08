# Cavenia VEINS / Content Pipeline Contract Boundary

This note documents only the future `Cavenia` VEINS/content-pipeline contract.

It does not add:

- `data/cavernreborn/dimension/cavenia.json`
- `data/cavernreborn/dimension_type/cavenia.json`
- active Cavenia biome-source JSON
- active Cavenia biome tags
- active Cavenia configured features
- active Cavenia placed features
- active Cavenia biome modifiers
- active Cavenia noise settings, density functions, carvers, processor lists, structures, template pools or other content-pipeline worldgen resources
- active `cavernreborn:cavenia`

The broader provider/runtime boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The narrower dimension key/type boundary remains documented in:

- `docs/cavenia-dimension-key-type-contract-boundary.md`

The narrower biome-provider boundary remains documented in:

- `docs/cavenia-biome-provider-contract-boundary.md`

The narrower chunk-generator and terrain-pipeline contract remains documented in:

- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`

The separate cave-carver contract remains documented in:

- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`

- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy References Inspected

- `cavern.config.CaveniaConfig`
- `cavern.world.mirage.ChunkGeneratorCavenia`
- `cavern.world.mirage.WorldProviderCavenia`
- `cavern.world.WorldProviderCavern`
- `cavern.config.manager.CaveVeinManager`
- `cavern.config.manager.CaveVein`
- `cavern.world.CaveVeinProvider`
- `cavern.world.gen.VeinGenerator`
- `cavern.config.CavernConfig`
- `cavern.block.BlockCave`

## Exact Legacy `CaveniaConfig.VEINS` Contract Found

`CaveniaConfig` declares:

- `public static final CaveVeinManager VEINS = new CaveVeinManager()`

`syncVeinsConfig()` loads or regenerates a dedicated `cavenia/veins` config file and fills that manager with explicit `CaveVein` entries.

The config serializer in `CavernConfig.generateVeinsConfig(...)` and `CavernConfig.addVeinsFromConfig(...)` confirms the stored per-entry fields are:

- `block`
- `blockMeta`
- `targetBlock`
- `targetBlockMeta`
- `weight`
- `chance`
- `size`
- `minHeight`
- `maxHeight`
- `biomes`

The default shorthand constructor used by `syncVeinsConfig()` implies:

- target block defaults to `minecraft:stone` meta `0`
- chance defaults to `1.0D`

The default global setting is:

- `autoVeins = false`

That means shipped legacy Cavenia uses the configured `VEINS` list by default.

When `autoVeins = true`, `ChunkGeneratorCavenia` passes `null` to `CaveVeinProvider`, and `CaveVeinProvider#getVeins()` synthesizes a dynamic ore-dictionary-driven list instead of consuming `CaveniaConfig.VEINS`.

That dynamic fallback scans:

- `ore*` ore-dictionary names
- `stone*` ore-dictionary names
- `dirt`
- `gravel`

and builds rarity/size/height guesses from the current runtime registry and actual world height.

So a future active Cavenia port must decide whether to preserve:

- only the shipped configured defaults
- the old `autoVeins = true` dynamic fallback
- or both

## Exact Legacy Default VEIN Entries And Parameters Found

Default `syncVeinsConfig()` entries are exactly:

- granite via `minecraft:stone` meta `1`, target `minecraft:stone` meta `0`, weight `28`, chance `1.0D`, size `25`, minY `1`, maxY `255`, no biome filter
- diorite via `minecraft:stone` meta `3`, target `minecraft:stone` meta `0`, weight `28`, chance `1.0D`, size `25`, minY `1`, maxY `255`, no biome filter
- andesite via `minecraft:stone` meta `5`, target `minecraft:stone` meta `0`, weight `30`, chance `1.0D`, size `25`, minY `1`, maxY `255`, no biome filter
- coal ore via `minecraft:coal_ore` meta `0`, target `minecraft:stone` meta `0`, weight `50`, chance `1.0D`, size `17`, minY `1`, maxY `127`, no biome filter
- iron ore via `minecraft:iron_ore` meta `0`, target `minecraft:stone` meta `0`, weight `40`, chance `1.0D`, size `10`, minY `1`, maxY `127`, no biome filter
- aquamarine ore via `cavern:cave_block` meta `0`, target `minecraft:stone` meta `0`, weight `12`, chance `1.0D`, size `8`, minY `20`, maxY `127`, biome filters expanded from `Type.COLD`, `Type.WATER` and `Type.WET`
- magnite ore via `cavern:cave_block` meta `2`, target `minecraft:stone` meta `0`, weight `30`, chance `1.0D`, size `10`, minY `1`, maxY `127`, no biome filter
- randomite ore via `cavern:cave_block` meta `4`, target `minecraft:stone` meta `0`, weight `24`, chance `1.0D`, size `4`, minY `1`, maxY `127`, no biome filter
- hexcite ore via `cavern:cave_block` meta `5`, target `minecraft:stone` meta `0`, weight `4`, chance `1.0D`, size `5`, minY `1`, maxY `30`, no biome filter
- fissured stone via `cavern:cave_block` meta `7`, target `minecraft:stone` meta `0`, weight `150`, chance `1.0D`, size `2`, minY `1`, maxY `127`, no biome filter
- dirt via `minecraft:dirt` meta `0`, target `minecraft:stone` meta `0`, weight `20`, chance `1.0D`, size `25`, minY `1`, maxY `127`, no biome filter
- gravel via `minecraft:gravel` meta `0`, target `minecraft:stone` meta `0`, weight `10`, chance `1.0D`, size `20`, minY `1`, maxY `127`, no biome filter
- sand via `minecraft:sand` meta `0`, target `minecraft:stone` meta `0`, weight `10`, chance `1.0D`, size `20`, minY `1`, maxY `127`, biome filters expanded from `Type.SANDY`

Notably absent from the shipped Cavenia defaults are:

- gold ore
- redstone ore
- lapis ore
- diamond ore
- emerald ore

So Cavenia does not simply reuse the normal Cavern `VEINS` list.

## Exact Legacy Biome-Filter Behavior Found

`CaveVein` stores biome filters as concrete biome registry-name strings, not as live `BiomeDictionary.Type` markers.

That happens because the constructor expands `Biome`, numeric biome ids and `BiomeDictionary.Type` values into a sorted string array immediately.

`VeinGenerator` then applies this check:

- `if (biome == null || Config.containsBiome(targetBiomes, biome)) { continue; }`

So the inspected legacy generator skips placement when the current biome matches one of the stored biome ids.

As written, that means the old `biomes` array behaves like an exclusion list inside the generator, even though the config shape looks like a positive biome filter.

That source-as-written detail matters for any future parity decision around:

- aquamarine ore
- sand
- any future edited `CaveniaConfig.VEINS` entries

## Exact `ChunkGeneratorCavenia` VEINS Relationship Found

`ChunkGeneratorCavenia#generateChunk(...)` runs in this order:

1. fill the full chunk primer with stone through `setBlocksInChunk(...)`
2. optionally run `MapGenCaveniaCaves` when `CaveniaConfig.generateCaves` is true
3. run `replaceBiomeBlocks(...)` using `CaveniaConfig.BIOMES`
4. run `veinGenerator.generate(world, rand, biomesForGeneration, primer)`

So the active VEINS pass runs:

- after initial stone fill
- after optional cave carving
- after biome top/filter replacement
- before the final `Chunk` instance is built

The fuller chunk-order and terrain-generator contract is documented separately in:

- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`

That means ore/content replacement is not a late population feature pass.

It is generator-side chunk-primer mutation.

Because `replaceBiomeBlocks(...)` already swaps exposed top blocks and stone-filter blocks before `VeinGenerator` runs, the default VEIN target of `minecraft:stone` means those VEIN entries only replace blocks that still remain stone at that stage.

## Exact Separate Content Pipeline Found Outside `VEINS`

`ChunkGeneratorCavenia#populate(...)` handles multiple content branches outside `CaveniaConfig.VEINS`.

### Lakes

`generateLakes` is a dedicated config gate, not part of `VEINS`.

When `CaveniaConfig.generateLakes` is true:

- NETHER biomes can place lava lakes with `1/4` chance at `y = rand.nextInt(worldHeight - 32) + 16`
- non-END and non-NETHER biomes can place water lakes with `1/4` chance when the biome is not `Type.SANDY`, at `y = rand.nextInt(worldHeight - 16)`
- the same non-END/non-NETHER path can place lava lakes with `1/10` chance at `y = rand.nextInt(worldHeight / 2 - 16) + 32`

### Cavenic Shroom

`cavenic_shroom` is not part of `CaveniaConfig.VEINS`.

It is generated by a separate `WorldGenBush(CaveBlocks.CAVENIC_SHROOM)` branch during `Decorate.EventType.SHROOM`.

The inspected behavior is:

- base chance is `rand.nextInt(6) <= i`
- `i += 2` in `Type.MUSHROOM` biomes
- `i += 1` in `Type.NETHER` biomes
- generation occurs at `blockPos.add(16, 0, 16)`

There is no separate `generateCavenicShroom` config flag in the inspected source.

### Falls

Falls are also outside `VEINS`.

They are gated by:

- `BiomeDecorator.generateFalls`
- biome traits
- terrain-gen decorate events

The inspected counts are:

- NETHER biomes: `70` lava-liquid placements at `y = rand.nextInt(worldHeight - 22) + 20`
- WATER biomes: `50` water-liquid placements at `y = rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`
- other non-END biomes: `50` water-liquid placements with the same nested range plus `50` lava-liquid placements at `y = rand.nextInt(worldHeight / 2 - 32) + 20`

So the full Cavenia content pipeline is wider than:

- ore features
- stone replacements
- one config list

## Exact Current Reborn Content Relationship

Current Reborn already has these ids as bounded content:

- `cavernreborn:aquamarine_ore`
- `cavernreborn:magnite_ore`
- `cavernreborn:randomite_ore`
- `cavernreborn:hexcite_ore`
- `cavernreborn:fissured_stone`
- `cavernreborn:cavenic_shroom`

Current active `CAVERN` worldgen already wires them through checked-in modern resources such as:

- `cavernreborn:aquamarine_ore` configured feature plus `cavernreborn:cavern_ore_aquamarine`
- `cavernreborn:magnite_ore` configured feature plus `cavernreborn:cavern_ore_magnite`
- `cavernreborn:hexcite_ore` configured feature plus `cavernreborn:cavern_ore_hexcite_deep` and `cavernreborn:cavern_ore_hexcite_upper_rare`
- `cavernreborn:randomite_ore` configured feature plus `cavernreborn:cavern_ore_randomite`
- `cavernreborn:fissured_stone` configured feature plus `cavernreborn:cavern_fissured_stone`
- `cavernreborn:cavenic_shroom_patch` plus `cavernreborn:cavern_cavenic_shroom_patch`

Those active Reborn resources are current `CAVERN`-only distributions.

They are not proof that active Cavenia placement should reuse:

- the same counts
- the same heights
- the same feature ordering
- the same biome coverage

## Why Reborn Does Not Add Active Cavenia Content Resources Here

This increment does not add active Cavenia configured features, placed features or biome modifiers because the inspected legacy source still ties Cavenia content to:

- generator-side replacement
- post-terrain VEIN application
- separate lake/fall/shroom population
- legacy biome-manager interaction
- `worldHeight = 128`
- the unresolved active `cavenia` dimension/runtime path

An honest active port therefore still needs decisions about:

- configured/placed features
- generator-side replacement
- Cavenia-specific biome tags
- separate lakes/falls/shroom features
- terrain/generator integration
- feature ordering
- content distribution rebalance

## Unresolved Mapping Questions

This boundary does not finalize:

- the exact mapping from legacy `VEINS` entries to modern configured/placed features
- whether stone-variant and soil entries should stay generator-side instead of becoming standard ore features
- whether the source-as-written biome-filter/exclusion behavior should be preserved exactly
- the exact feature order relative to future terrain/surface rules
- the exact relationship to current `CAVERN` distributions
- the exact relationship to a future Cavenia biome source
- the exact relationship to future active Cavenia key/type resources
- the exact relationship to future `CaveniaConfig.VEINS` editing support, if any

## Exact Current Reborn Boundary

Current Reborn still has:

- no active `cavenia` dimension resources
- no active Cavenia configured features
- no active Cavenia placed features
- no active Cavenia biome modifiers
- no active Cavenia noise settings, density functions, carvers, processor lists, structures or template pools
- no active Cavenia biome-source resources
- no active Cavenia biome tags
- no active Cavenia generator code

## Explicit Non-Goals

- no active Cavenia VEINS implementation
- no active Cavenia configured features
- no active Cavenia placed features
- no active Cavenia biome modifiers
- no active Cavenia biome provider
- no active Cavenia biome source
- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no `CAVENIA_LOCATION`
- no `CAVENIA_LEVEL_KEY`
- no `WorldProviderCavenia` port
- no `ChunkGeneratorCavenia` port
- no terrain generation
- no surface rules
- no lakes/falls/shroom generation
- no portal/teleport/access flow
- no active Cavenia spawning
- no active crazy spawning
