# Runtime Smoke

This document defines the automated runtime smoke layer for the completed `CAVERN` special ore/content parity tranche 2, the bounded `Acresia Crop & Food MVP`, the bounded `Cavenic Shroom & Orb MVP`, the bounded Cavenic Melee MVP, the bounded `Cavenic Bow Baseline MVP`, the bounded `Cavenic Bow Mode State & Cycling MVP`, the bounded `Cavenic Bow Snipe Mode MVP`, the bounded `Cavenic Bow Rapid Mode MVP`, the bounded `Cavenic Bow Torch Mode MVP`, the bounded `Cavenic Zombie Baseline MVP`, the bounded `Cavenic Zombie Natural Spawn MVP`, the bounded `Cavenic Zombie Legacy Orb Drop MVP`, the bounded `Cavenic Zombie Legacy Damage Behavior MVP`, the bounded `Cavenic Skeleton Baseline MVP`, the bounded `Cavenic Skeleton Natural Spawn MVP`, the bounded `Cavenic Skeleton Legacy Orb Drop MVP`, the bounded `Cavenic Skeleton Legacy Damage Behavior MVP`, the bounded `Cavenic Creeper Baseline MVP`, the bounded `Cavenic Creeper Natural Spawn MVP`, the bounded `Cavenic Creeper Legacy Orb Drop MVP`, the bounded `Cavenic Creeper Legacy Damage Behavior MVP`, the bounded `Cavenic Creeper Legacy Fuse/Explosion MVP`, the bounded `Cavenic Spider Baseline MVP`, the bounded `Cavenic Spider Natural Spawn MVP`, the bounded `Cavenic Spider Legacy Orb Drop MVP`, the bounded `Cavenic Spider Legacy Damage Behavior MVP`, the bounded `Cavenic Spider Blindness-On-Hit MVP`, the bounded `Cavenic Witch Baseline MVP`, the bounded `Cavenic Witch Natural Spawn MVP`, the bounded `Cavenic Witch Legacy Loot MVP`, the bounded `Cavenic Witch Legacy Damage Behavior MVP`, the bounded `Cavenic Witch Same-Type Projectile Immunity MVP`, the bounded `Cavenic Witch Friendship Targeting MVP`, the bounded `Cavenic Witch Custom Ranged Potion MVP`, the bounded `Cavenic Bear Baseline MVP`, the bounded `Cavenic Bear Natural Spawn MVP`, the bounded Aquamarine Utility Tools MVP, the bounded Magnite Tool Set MVP, the bounded `Magnite Armor MVP`, the follow-up `hexcite` tool-set MVP, the bounded `Hexcite Armor MVP`, the bounded Mining Assist slice, the first Miner's Orb MVP and the Ore Compass MVP plus tracking UX follow-up.

It is intentionally a NeoForge GameTest server pass, not a visual client smoke pass.

## What It Covers

NeoForge GameTest runtime smoke covers:

- runtime registry availability for tranche 2 content
- acresia runtime registry ids
- acresia seed planting smoke
- acresia mature normal harvest smoke
- acresia shear-harvest regrowth smoke
- acresia edible fruit wiring
- acresia worldgen configured/placed key resolution
- cavenic shroom runtime registry ids
- cavenic shroom bounded collision nausea
- cavenic shroom bounded shear-harvest helper wiring
- cavenic orb drop policy/runtime smoke
- cavenic sword and cavenic axe runtime registry ids
- cavenic sword and cavenic axe repairability with `cavernreborn:cavenic_orb`
- cavenic sword and cavenic axe cavenic item tag resolution
- cavenic sword and cavenic axe recipe manager resolution
- bounded cavenic sword cooldown-reset runtime smoke
- bounded cavenic axe hostile-nearby-target runtime smoke
- cavenic bow runtime registry id
- cavenic bow max-damage and damageable-item smoke
- cavenic bow repairability with `cavernreborn:cavenic_orb`
- cavenic bow no-repair-with-stick smoke
- cavenic bow default mode/runtime stack-state smoke
- cavenic bow mode cycling order and persistence
- cavenic bow sneak-use mode-cycling smoke
- cavenic bow real release-path NORMAL survival arrow-consumption smoke
- cavenic bow real release-path survival no-arrow smoke
- cavenic bow real release-path creative no-arrow smoke
- cavenic bow real release-path Infinity smoke
- cavenic bow RAPID adjusted-shot-power smoke
- cavenic bow RAPID higher-velocity vanilla-arrow smoke
- cavenic bow RAPID no-extra-durability smoke
- cavenic bow RAPID still spawning a vanilla arrow entity
- cavenic bow RAPID not inheriting the SNIPE damage multiplier
- cavenic bow full-charge SNIPE projectile boost smoke
- cavenic bow full-charge SNIPE extra-durability smoke
- cavenic bow full-charge SNIPE still spawning a vanilla arrow entity
- cavenic bow TORCH vanilla-arrow marker smoke
- cavenic bow TORCH valid block-placement smoke
- cavenic bow TORCH invalid-target no-placement smoke
- cavenic bow TORCH torch-consumption smoke
- cavenic bow TORCH wall-torch orientation smoke
- cavenic bow TORCH no-custom-entity smoke
- cavenic bow TORCH not inheriting RAPID or SNIPE behavior
- cavenic bow enchantment applicability
- cavenic bow cavenic item tag resolution
- cavenic bow recipe manager resolution
- cavenic zombie runtime registry id
- cavenic zombie attribute registration smoke
- cavenic zombie hostile runtime spawn smoke
- cavenic zombie spawn egg resolution
- cavenic zombie spawn egg entity-creation smoke
- cavenic zombie natural spawn placement registration
- cavenic zombie CAVERN-only spawn predicate smoke
- cavenic zombie biome modifier registry smoke
- cavenic zombie biome tag resolution
- cavenic zombie vanilla loot-table baseline smoke
- cavenic zombie legacy orb-drop event wiring smoke
- cavenic zombie legacy orb-drop deterministic winning/losing roll smoke
- cavenic zombie legacy fall-damage reduction smoke
- cavenic zombie legacy fire-damage immunity smoke
- cavenic zombie generic-damage baseline smoke
- cavenic skeleton runtime registry id
- cavenic skeleton attribute registration smoke
- cavenic skeleton hostile runtime spawn smoke
- cavenic skeleton spawn egg resolution
- cavenic skeleton spawn egg entity-creation smoke
- cavenic skeleton natural spawn placement registration
- cavenic skeleton CAVERN-only spawn predicate smoke
- cavenic skeleton biome modifier registry smoke
- cavenic skeleton biome tag resolution
- cavenic skeleton vanilla loot-table baseline smoke
- cavenic skeleton legacy orb-drop event wiring smoke
- cavenic skeleton legacy orb-drop deterministic winning/losing roll smoke
- cavenic skeleton legacy fall-damage reduction smoke
- cavenic skeleton legacy fire-damage immunity smoke
- cavenic skeleton generic-damage baseline smoke
- cavenic creeper runtime registry id
- cavenic creeper attribute registration smoke
- cavenic creeper hostile runtime spawn smoke
- cavenic creeper vanilla loot-table baseline smoke
- cavenic creeper spawn egg resolution
- cavenic creeper spawn egg entity-creation smoke
- cavenic creeper natural spawn placement registration
- cavenic creeper CAVERN-only spawn predicate smoke
- cavenic creeper biome modifier registry smoke
- cavenic creeper biome tag resolution
- cavenic creeper legacy orb-drop event wiring smoke
- cavenic creeper legacy orb-drop deterministic winning/losing roll smoke
- cavenic creeper legacy fall-damage reduction smoke
- cavenic creeper legacy fire-damage immunity smoke
- cavenic creeper generic-damage baseline smoke
- cavenic creeper legacy fuse-time smoke
- cavenic creeper legacy explosion-radius smoke
- cavenic spider runtime registry id
- cavenic spider attribute registration smoke
- cavenic spider hostile runtime spawn smoke
- cavenic spider vanilla loot-table baseline smoke
- cavenic spider spawn egg resolution
- cavenic spider spawn egg entity-creation smoke
- cavenic spider natural spawn placement registration
- cavenic spider CAVERN-only spawn predicate smoke
- cavenic spider biome modifier registry smoke
- cavenic spider biome tag resolution
- cavenic spider legacy orb-drop event wiring smoke
- cavenic spider legacy orb-drop deterministic winning/losing roll smoke
- cavenic spider legacy fall-damage reduction smoke
- cavenic spider legacy fire-damage immunity smoke
- cavenic spider generic-damage baseline smoke
- cavenic spider blindness-on-hit runtime smoke
- vanilla spider no-blindness comparison smoke
- cavenic witch runtime registry id
- cavenic witch attribute registration smoke
- cavenic witch hostile runtime spawn smoke
- cavenic witch vanilla loot-table baseline smoke
- cavenic witch spawn egg resolution
- cavenic witch spawn egg entity-creation smoke
- cavenic witch natural spawn placement registration
- cavenic witch CAVERN-only spawn predicate smoke
- cavenic witch biome modifier registry smoke
- cavenic witch biome tag resolution
- cavenic witch legacy orb-drop event wiring smoke
- cavenic witch deterministic winning/losing roll smoke
- cavenic witch legacy fall-damage reduction smoke
- cavenic witch legacy fire-damage immunity smoke
- cavenic witch generic-damage baseline smoke
- cavenic witch legacy same-type/self source-immunity smoke
- cavenic witch non-immune source baseline smoke
- cavenic witch same-type friendship target rejection smoke
- cavenic witch non-friend target baseline smoke
- cavenic witch legacy ranged-potion runtime smoke
- cavenic witch deterministic legacy potion-selection smoke
- cavenic witch explicit high-health and low-health potion-threshold smoke
- cavenic witch explicit weakness/slowness/harming fallback smoke
- cavenic witch deterministic legacy thrown-potion construction smoke
- cavenic witch direct non-friend ranged-attack branch smoke
- cavenic witch preserved non-witch Raider bridge smoke
- cavenic bear runtime registry id
- cavenic bear attribute registration smoke
- cavenic bear runtime entity spawn smoke
- cavenic bear vanilla polar bear loot-table baseline smoke
- cavenic bear explicit no-custom-loot boundary from the inspected legacy source
- cavenic bear spawn egg resolution
- cavenic bear spawn egg entity-creation smoke
- cavenic bear natural spawn placement registration
- cavenic bear CAVERN-only spawn predicate smoke
- cavenic bear biome modifier registry smoke
- cavenic bear biome tag resolution
- cavenic shroom worldgen configured/placed key resolution
- aquamarine tool runtime registry ids
- aquamarine tool repairability with `cavernreborn:aquamarine`
- aquamarine tool enchantment applicability
- aquamarine underwater utility policy/runtime smoke
- magnite tool runtime registry ids
- magnite tool repairability with `cavernreborn:magnite_ingot`
- magnite tool enchantment applicability
- magnite brittle max-damage/runtime smoke
- magnite no-unintended-Mining-Assist runtime smoke
- magnite armor runtime registry ids
- magnite armor repairability with `cavernreborn:magnite_ingot`
- magnite armor enchantment applicability
- magnite armor durability and slot/equippable runtime behavior
- hexcite tool runtime registry ids
- hexcite armor runtime registry ids
- hexcite normal and Silk Touch loot paths
- hexcite pickaxe enchantment applicability for mining and durability enchantments
- hexcite pickaxe normal mining path for special ores
- hexcite pickaxe Silk Touch path for hexcite ore
- hexcite armor repairability with `cavernreborn:hexcite`
- hexcite armor durability and slot/equippable runtime behavior
- hexcite armor enchantment applicability for the expected vanilla armor families
- miner_orb runtime registry availability
- randomite allowed-drop runtime coverage that now includes miner_orb in the curated pool
- miner_orb bonus policy/runtime smoke
- ore_compass runtime registry availability
- ore_compass target tag resolution
- ore_compass scanner nearest-target behavior
- ore_compass stored-target state round-trip
- ore_compass tracking policy/runtime ids
- unsupported/fissured/storage exclusion during ore_compass scans
- Mining Assist policy/runtime ids
- bounded same-block vein assist with hexcite_pickaxe
- no unlock means no assist
- sneaking disables assist
- fissured_stone exclusion and non-target preservation during assist
- randomite curated runtime loot output
- fissured stone no-drop behavior
- fissured stone survival effect behavior
- fissured stone creative guard
- fissured stone non-destructive behavior
- progression policy ids/scores
- worldgen configured/placed feature runtime resolution

## What It Does Not Cover

- visual portal UX
- block, model and texture appearance in the client
- actual worn hexcite armor appearance on a player or mob
- actual worn magnite armor appearance on a player or mob
- actual Acresia in-world farming feel and growth cadence
- actual Cavenic Shroom hazard feel, sparse spread feel and harvest feel
- actual Cavenic Sword and Cavenic Axe visual/client combat feel
- actual Cavenic Bow draw, release and visual client feel
- actual Cavenic Zombie renderer/model visual feel
- actual Cavenic Skeleton renderer/model visual feel
- actual Cavenic Creeper renderer/model visual feel
- actual Cavenic Spider renderer/model visual feel
- actual Cavenic Witch renderer/model visual feel
- actual Cavenic Bear renderer/model visual feel
- actual long-run Cavenic Bear drop-rate balance, although the current source inspection found no direct custom bear loot branch beyond the vanilla polar bear baseline
- actual long-run cavenic witch orb-drop rate balance
- actual long-running Cavenic Witch fire/lava gameplay feel
- actual long-run cavenic spider orb-drop rate balance
- actual long-running Cavenic Spider fire/lava gameplay feel
- actual long-running Cavenic Spider combat/pathfinding feel
- actual poison/web-based Cavenic Spider gameplay behavior
- actual Cavenic Witch combat, potion-throw and pathfinding feel
- actual long-run Cavenic Bear population balance inside CAVERN
- legacy cavenic witch magic-book branch because the inspected legacy `ItemMagicBook` depends on subtype, NBT, capability and spellbook systems that Reborn does not yet implement honestly
- any broader Cavenic Witch AI-goal or target-selector rewrite beyond the restored ranged-potion slice
- actual long-run Cavenic Witch population balance inside CAVERN
- actual long-run cavenic creeper orb-drop rate balance
- actual long-running Cavenic Creeper fire/lava gameplay feel
- actual long-run Cavenic Spider population balance inside CAVERN
- actual long-run cavenic zombie orb-drop rate balance
- actual long-run cavenic skeleton orb-drop rate balance
- actual long-running Cavenic Zombie fire/lava gameplay feel
- actual long-running Cavenic Skeleton fire/lava gameplay feel
- actual Cavenic Creeper fuse timing, explosion feel and block-damage behavior
- actual long-run Cavenic Creeper population balance inside CAVERN
- actual long-run Cavenic Skeleton population balance inside CAVERN
- legacy Cavenic Bow Rapid/Torch custom projectile behavior and client feel beyond the current bounded Snipe, Rapid and Torch slices
- the current Cavenic Bow release-semantics coverage is still server-side GameTest only, not a substitute for manual client feel validation
- actual long-run Cavenic Zombie population balance inside CAVERN
- actual underwater aquamarine mining feel
- actual brittle magnite mining feel
- particle and sound feel
- full player portal loop through a real client session
- restart persistence with a real dedicated-server save

The GameTest layer is meant to fail fast on runtime wiring regressions. It does not replace manual client smoke.

## Local Commands

Run the narrow runtime compile first:

```bash
./gradlew --no-daemon :app-neoforge:compileJava
```

Run the NeoForge GameTest server:

```bash
./gradlew --no-daemon :app-neoforge:runGameTestServer
```

Run the existing JUnit/resource suite and then the full build:

```bash
./gradlew --no-daemon test
./gradlew --no-daemon build
```

For a single local wrapper that runs the same sequence, use:

```bash
scripts/runtime-smoke.sh
```

## Docker Commands

The repository already uses Docker for repeatable Gradle execution. The equivalent commands are:

```bash
docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:compileJava
docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:runGameTestServer
docker compose run --rm gradle ./gradlew --no-daemon test
docker compose run --rm gradle ./gradlew --no-daemon build
```

## Expected Pass/Fail Behavior

- `:app-neoforge:runGameTestServer` must fail if a required special-ore GameTest fails.
- The GameTest server run is the automated runtime layer for tranche 2 special ores and progression/worldgen wiring.
- The same GameTest layer now also covers the bounded Mining Assist MVP runtime path.
- The same GameTest layer now also covers the bounded Ore Compass server-side scan path.
- The same GameTest layer now also covers the bounded Ore Compass stored-target and tracking-policy runtime path.
- The same GameTest layer now also covers the bounded Hexcite Armor MVP runtime path.
- The same GameTest layer now also covers the bounded Acresia crop planting/harvest/regrowth runtime path.
- The run is intentionally small and server-only; it should not require a GUI client or a human player.

## Registration Workaround

- `CavernSpecialOreGameTests` is currently registered from an unguarded `GameTestRegistry.register(CavernSpecialOreGameTests.class)` bootstrap call in `CavernReborn`.
- This is an infrastructure workaround for the current project setup: guarding registration with `GameTestHooks.isGametestEnabled()` prevents `:app-neoforge:runGameTestServer` from discovering any tests.
- The workaround is limited to GameTest bootstrap wiring and does not change gameplay behavior.
- If the NeoForge/GameTest discovery path becomes stable for this repository later, the unguarded registration path can be revisited.

## Template Strategy

- The current GameTests use a checked-in minimal `minecraft:empty` template.
- The template source lives at `app-neoforge/src/gameteststructures/empty.snbt`.
- `app-neoforge/build.gradle` stages that local `.snbt` into the GameTest run directory because the current GameTest server setup does not reliably provide a built-in empty template for this project.
- Under the current 1.21.1 GameTest local-structure lookup, that staged file resolves as `minecraft:empty`, so the namespace is intentionally explicit in the test annotations.
- Test setup happens programmatically through `GameTestHelper`.
- No large structure template or generated world is required for this runtime-smoke pass.

## Manual Follow-Up Still Needed

Even when the runtime-smoke pass is green, manual client smoke is still needed for:

- portal UX
- rendering and block/model appearance
- actual Acresia patch discovery and crop growth feel
- actual client-visible Ore Compass needle feel
- particle and sound feel
- end-to-end player movement through the portal loop
- restart persistence on a real server save

The Ore Compass tracking follow-up keeps this division intentionally explicit: GameTest covers state round-trip and runtime-id/policy wiring, while the actual client-visible needle feel still requires manual smoke.
