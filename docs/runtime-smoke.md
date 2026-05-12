# Runtime Smoke

This document defines the automated runtime smoke layer for the completed `CAVERN` special ore/content parity tranche 2, the bounded `Acresia Crop & Food MVP`, the bounded `Cavenic Shroom & Orb MVP`, the bounded Cavenic Melee MVP, the bounded `Cavenic Bow Baseline MVP`, the bounded `Cavenic Bow Mode State & Cycling MVP`, the bounded `Cavenic Bow Snipe Mode MVP`, the bounded `Cavenic Bow Rapid Mode MVP`, the bounded `Cavenic Bow Rapid Low-Armor Hurt-Resistance MVP`, the bounded `Cavenic Bow Torch Mode MVP`, the bounded `Cavenic Zombie Baseline MVP`, the bounded `Cavenic Zombie Natural Spawn MVP`, the bounded `Cavenic Zombie Legacy Orb Drop MVP`, the bounded `Cavenic Zombie Legacy Damage Behavior MVP`, the bounded `Cavenic Skeleton Baseline MVP`, the bounded `Cavenic Skeleton Natural Spawn MVP`, the bounded `Cavenic Skeleton Legacy Orb Drop MVP`, the bounded `Cavenic Skeleton Legacy Damage Behavior MVP`, the bounded `Cavenic Creeper Baseline MVP`, the bounded `Cavenic Creeper Natural Spawn MVP`, the bounded `Cavenic Creeper Legacy Orb Drop MVP`, the bounded `Cavenic Creeper Legacy Damage Behavior MVP`, the bounded `Cavenic Creeper Legacy Fuse/Explosion MVP`, the bounded `Cavenic Spider Baseline MVP`, the bounded `Cavenic Spider Natural Spawn MVP`, the bounded `Cavenic Spider Legacy Orb Drop MVP`, the bounded `Cavenic Spider Legacy Damage Behavior MVP`, the bounded `Cavenic Spider Blindness-On-Hit MVP`, the bounded `Cavenic Witch Baseline MVP`, the bounded `Cavenic Witch Natural Spawn MVP`, the bounded `Cavenic Witch Legacy Loot MVP`, the bounded `Cavenic Witch Legacy Damage Behavior MVP`, the bounded `Cavenic Witch Same-Type Projectile Immunity MVP`, the bounded `Cavenic Witch Friendship Targeting MVP`, the bounded `Cavenic Witch Custom Ranged Potion MVP`, the bounded `Cavenic Bear Baseline MVP`, the bounded `Cavenic Bear Natural Spawn MVP`, the bounded `Cavenic Bear Legacy Damage Behavior MVP`, the bounded `Cavenic Bear Legacy Hostile Targeting MVP`, the bounded `Cavenic Bear Legacy Melee Attack MVP`, the bounded `Cavenic Bear Legacy Panic Behavior MVP`, the bounded `Cavenic Bear Passive / Movement AI Boundary MVP`, the direct Cavenic mob roster boundary, the bounded `Crazy Zombie Baseline MVP`, the bounded `Crazy Zombie Legacy Loot / Orb Drop Boundary MVP`, the bounded `Crazy Zombie Legacy Damage Behavior MVP`, the bounded `Crazy Zombie Knockback-On-Hit MVP`, the bounded `Crazy Zombie Boss Bar / Sky-Darkening MVP`, the bounded `Crazy Zombie Particle Trail MVP`, the bounded `Crazy Skeleton Baseline MVP`, the bounded `Crazy Skeleton Cavenic Bow Equipment MVP`, the bounded `Crazy Skeleton Legacy Loot / Orb Drop MVP`, the bounded `Crazy Skeleton Legacy Damage Behavior MVP`, the bounded `Crazy Skeleton Boss Bar / Sky-Darkening MVP`, the bounded `Crazy Skeleton Particle Trail MVP`, the bounded `Crazy Skeleton` ranged-AI MVP, the bounded `Crazy Creeper Baseline MVP`, the bounded `Crazy Creeper Legacy Loot / Orb Drop MVP`, the bounded `Crazy Creeper Legacy Damage Behavior MVP`, the bounded `Crazy Creeper Boss Bar / Sky-Darkening MVP`, the bounded `Crazy Creeper Particle Trail MVP`, the bounded `Crazy Creeper Fuse / Explosion MVP`, the bounded `Crazy Creeper` lightning/charged/swelling boundary, the bounded `Crazy Spider Baseline MVP`, the bounded `Crazy Spider Legacy Loot / Orb Drop MVP`, the bounded `Crazy Spider Legacy Damage Behavior MVP`, the bounded `Crazy Spider Boss Bar / Sky-Darkening MVP`, the bounded `Crazy Spider Particle Trail MVP`, the bounded `Crazy Spider Combat / Blindness / Poison MVP`, the bounded `Cavenia Crazy Roster Natural Spawn Boundary MVP`, the bounded `Cavenia Dimension / Provider Foundation Boundary`, the bounded `Cavenia Dimension Key / Type Contract Boundary`, the bounded `Cavenia Biome Provider / Biome List Contract Boundary`, the bounded `Cavenia VEINS / Content Pipeline Contract Boundary`, the bounded `Cavenia Chunk Generator / Terrain Pipeline Contract Boundary`, the bounded `Cavenia Cave Carver / MapGenCaveniaCaves Contract Boundary`, the bounded `Cavenia Population / Lakes / Falls / Cavenic Shroom Contract Boundary`, the bounded `Cavenia Mirage Entry / Access Path Contract Boundary`, the bounded `Cavenia Spawn Provider / Crazy Roster Activation Contract Boundary`, the bounded `Cavenia Spawn Provider Policy / Non-Runtime MVP`, the bounded `Caveman Baseline / Cavenia Normal Spawn Roster Boundary`, the bounded `Cavenia Active Foundation Readiness / Implementation Plan`, the bounded `Cavenia Active Foundation Technical Spike`, the bounded `Cavenia Runtime Key / Inactive Dimension Scaffold MVP`, the bounded `Cavenia Terrain Generator Foundation / Non-Runtime MVP`, the bounded `Cavenia Cave Carver Policy / Non-Runtime MVP`, the bounded `Cavenia Biome Top/Filter Policy / Non-Runtime MVP`, the bounded `Cavenia VEINS / Content Policy / Non-Runtime MVP`, the bounded `Cavenia Population Policy / Non-Runtime MVP`, the bounded `Cavenia Active Generator Technical Scaffold / MVP`, the bounded `Cavenia Active Generator Runtime Prototype / Inert App-Side Bridge MVP`, the bounded `Cavenia Active Generator Registration / Inert Activation Boundary MVP`, the bounded `Cavenia Chunk Generator / Biome Source Unregistered Skeleton MVP`, the bounded `Cavenia Generator Runtime Contract Interfaces / Non-Registered MVP`, the bounded `Cavenia Generator Activation-Readiness Runtime Host Contracts MVP`, the bounded `Cavenia Generator Host / Biome Source Strategy Host Split Contracts MVP`, the bounded `Cavenia Dimension-Resource / Access-Travel / Spawn-Host Split Contracts MVP`, the bounded `Cavenia Worldgen-Resource Host Split Contracts MVP`, the bounded `Cavenia Activation Surface Consolidation / Final Inert Readiness Matrix MVP`, the bounded `Cavenia Deliberate First Active Surface Selection MVP`, the bounded Aquamarine Utility Tools MVP, the bounded Magnite Tool Set MVP, the bounded `Magnite Armor MVP`, the follow-up `hexcite` tool-set MVP, the bounded `Hexcite Armor MVP`, the bounded Mining Assist slice, the first Miner's Orb MVP and the Ore Compass MVP plus tracking UX follow-up.

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
- cavenic bow RAPID low-armor hurt-resistance reset smoke
- cavenic bow RAPID armored-target no-reset smoke
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
- cavenic bow TORCH item-use-forwarding boundary smoke
- entity cavenic arrow projectile boundary smoke
- rapid/torch arrow projectile boundary smoke
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
- cavenic bear legacy fall-damage reduction smoke
- cavenic bear legacy fire-damage immunity smoke
- cavenic bear generic-damage baseline smoke
- cavenic bear legacy hostile player-target goal smoke
- cavenic bear legacy hurt-by-target retaliation smoke
- cavenic bear non-player target baseline smoke
- cavenic bear legacy melee-goal registration smoke
- cavenic bear legacy melee reach smoke
- cavenic bear legacy standing-warning threshold smoke
- cavenic bear legacy panic-goal registration smoke
- cavenic bear legacy burning-only panic-trigger smoke
- cavenic bear freeze panic rejection smoke
- cavenic bear explicit passive/movement AI equivalence boundary against the inherited modern polar bear base
- direct legacy `EntityCavenic*` roster boundary documentation confirming that the current six runtime-covered direct Cavenic mob foundations already exhaust the legacy direct roster, so no seventh direct-mob baseline smoke was added in this slice
- crazy zombie runtime registry id
- crazy zombie attribute registration smoke, including the modern `generic.max_health` clamp on the legacy `2000.0` max-health request
- crazy zombie hostile runtime spawn smoke
- crazy zombie vanilla loot-table baseline smoke
- crazy zombie spawn egg resolution
- crazy zombie spawn egg entity-creation smoke
- crazy zombie legacy orb-drop event wiring smoke
- crazy zombie legacy orb-drop deterministic winning/losing roll smoke
- crazy zombie legacy fall-damage reduction smoke
- crazy zombie legacy fire-damage immunity smoke
- crazy zombie generic-damage baseline smoke
- crazy zombie legacy knockback-on-hit melee smoke
- crazy zombie deterministic legacy knockback helper smoke
- crazy zombie boss-event wiring smoke
- crazy zombie boss-bar color/overlay smoke
- crazy zombie boss-percent update smoke
- crazy zombie tracked-player add/remove smoke
- crazy zombie sky-darkening smoke
- crazy zombie particle type registry id smoke
- crazy zombie particle provider registration source smoke
- crazy zombie particle description resource smoke
- crazy zombie client-only particle spawn-source smoke
- explicit Crazy Zombie natural-spawn deferred boundary from the inspected Cavenia-only crazy-roster provider/config path
- crazy skeleton runtime registry id
- crazy skeleton attribute registration
- crazy skeleton hostile runtime spawn
- crazy skeleton spawn egg resolution
- crazy skeleton spawn egg entity creation
- crazy skeleton vanilla skeleton loot-table baseline
- crazy skeleton guaranteed cavenic bow equipment smoke
- crazy skeleton Infinity enchantment smoke
- crazy skeleton mainhand drop chance smoke
- crazy skeleton legacy orb-drop event wiring smoke
- crazy skeleton legacy orb-drop deterministic winning/losing roll smoke
- crazy skeleton legacy fall-damage reduction smoke
- crazy skeleton legacy fire-damage immunity smoke
- crazy skeleton generic-damage baseline smoke
- crazy skeleton boss-event wiring smoke
- crazy skeleton boss-bar color/overlay smoke
- crazy skeleton boss-percent update smoke
- crazy skeleton tracked-player add/remove smoke
- crazy skeleton sky-darkening smoke
- crazy skeleton particle type registry id smoke
- crazy skeleton particle provider registration source smoke
- crazy skeleton particle description resource smoke
- crazy skeleton client-only particle spawn-source smoke
- crazy skeleton explicit no-natural-spawn baseline boundary
- crazy skeleton continued vanilla attack-damage baseline, explicit `1024.0` max-health clamp and guaranteed `Cavenic Bow` + `Infinity` equipment stability smoke
- crazy skeleton legacy cavenic bow ranged-goal wiring smoke
- crazy skeleton legacy ranged-goal constants smoke
- crazy skeleton local melee fallback switch smoke
- crazy skeleton vanilla and cavenic skeleton no-global-ai-change smoke
- crazy creeper runtime registry id
- crazy creeper attribute registration
- crazy creeper hostile runtime spawn
- crazy creeper spawn egg resolution
- crazy creeper spawn egg entity creation
- crazy creeper vanilla creeper loot-table baseline
- crazy creeper legacy orb-drop event wiring smoke
- crazy creeper legacy orb-drop deterministic winning/losing roll smoke
- crazy creeper legacy fall-damage reduction smoke
- crazy creeper legacy fire-damage immunity smoke
- crazy creeper generic-damage baseline smoke
- crazy creeper boss-event wiring smoke
- crazy creeper boss-bar color/overlay smoke
- crazy creeper boss-percent update smoke
- crazy creeper tracked-player add/remove smoke
- crazy creeper sky-darkening smoke
- crazy creeper particle type registry id smoke
- crazy creeper particle provider registration source smoke
- crazy creeper particle description resource smoke
- crazy creeper client-only particle spawn-source smoke
- crazy creeper legacy fuse-time smoke
- crazy creeper legacy explosion-radius smoke
- crazy creeper fuse/explosion save-data round-trip smoke
- crazy creeper explicit no-natural-spawn baseline boundary
- crazy creeper inherited-vanilla lightning/charged/swelling boundary smoke
- crazy spider runtime registry id
- crazy spider attribute registration
- crazy spider hostile runtime spawn
- crazy spider spawn egg resolution
- crazy spider spawn egg entity creation
- crazy spider vanilla spider loot-table baseline
- crazy spider legacy orb-drop event wiring smoke
- crazy spider legacy orb-drop deterministic winning/losing roll smoke
- crazy spider legacy fall-damage reduction smoke
- crazy spider legacy fire-damage immunity smoke
- crazy spider generic-damage baseline smoke
- crazy spider boss-event wiring smoke
- crazy spider boss-bar color/overlay smoke
- crazy spider boss-percent update smoke
- crazy spider tracked-player add/remove smoke
- crazy spider sky-darkening smoke
- crazy spider particle type registry id smoke
- crazy spider particle provider registration source smoke
- crazy spider particle description resource smoke
- crazy spider client-only particle spawn-source smoke
- crazy spider successful-hit blindness/poison runtime smoke
- crazy spider exact difficulty-duration mapping smoke
- crazy spider existing-effect skip smoke
- vanilla spider no-blindness/no-poison comparison smoke
- consolidated crazy-mob natural-spawn deferral smoke through the inspected Cavenia-only roster/provider path
- crazy zombie, crazy skeleton, crazy creeper and crazy spider spawn placement unregistered smoke
- crazy zombie, crazy skeleton, crazy creeper and crazy spider no-normal-`CAVERN` biome-modifier smoke
- crazy zombie, crazy skeleton, crazy creeper and crazy spider no-normal-`CAVERN` spawn-biome-tag smoke
- the shared Cavenia crazy-roster natural-spawn boundary note at `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- inactive Cavenia dimension/provider foundation boundary
- no active `cavernreborn:cavenia` level resolves at runtime
- the Cavenia dimension/provider foundation boundary note at `docs/cavenia-dimension-provider-foundation-boundary.md`
- inactive Cavenia dimension key/type contract boundary
- no checked-in `data/cavernreborn/dimension/cavenia.json`
- no checked-in `data/cavernreborn/dimension_type/cavenia.json`
- the Cavenia dimension key/type contract boundary note at `docs/cavenia-dimension-key-type-contract-boundary.md`
- inactive Cavenia biome-provider contract boundary
- no checked-in active Cavenia biome-source, biome-tag or worldgen resources
- the Cavenia biome-provider contract boundary note at `docs/cavenia-biome-provider-contract-boundary.md`
- inactive Cavenia VEINS/content-pipeline contract boundary
- no checked-in active Cavenia configured features, placed features, biome modifiers or content-pipeline worldgen resources
- the Cavenia VEINS/content-pipeline contract boundary note at `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- inactive Cavenia chunk-generator/terrain-pipeline contract boundary
- no checked-in active Cavenia noise settings, density functions, configured carvers, configured features or placed features
- the Cavenia chunk-generator/terrain-pipeline contract boundary note at `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- inactive Cavenia cave-carver / `MapGenCaveniaCaves` contract boundary
- no checked-in active Cavenia configured carvers, noise settings or density functions tied to a `cavenia` runtime path
- the Cavenia cave-carver / `MapGenCaveniaCaves` contract boundary note at `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- inactive Cavenia population / lakes / falls / `cavenic_shroom` contract boundary
- no checked-in active Cavenia lake/fall/shroom configured or placed features tied to a `cavenia` runtime path
- the active `CAVERN` `cavenic_shroom` configured/placed keys still resolve separately from any Cavenia boundary work
- the Cavenia population / lakes / falls / `cavenic_shroom` contract boundary note at `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- inactive Cavenia mirage entry/access contract boundary
- no registered `cavernreborn:mirage_book` item exists at runtime
- no checked-in Cavenia access GUI, packet or portal resources tied to a `cavenia` runtime path
- the Cavenia mirage entry/access contract boundary note at `docs/cavenia-mirage-entry-access-contract-boundary.md`
- inactive Cavenia spawn-provider / crazy-roster activation contract boundary
- inactive Caveman / Cavenia normal-roster boundary
- inactive Cavenia active-foundation readiness-plan boundary
- the readiness plan note at `docs/cavenia-active-foundation-readiness-plan.md`
- inactive Cavenia active-foundation technical-spike boundary
- the technical spike note at `docs/cavenia-active-foundation-technical-spike.md`
- inert Cavenia runtime-key / inactive-dimension scaffold MVP
- the inert scaffold note at `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`
- `CAVENIA_LOCATION` and `CAVENIA_LEVEL_KEY` now resolve only as inert identity constants
- non-runtime Cavenia terrain-generator foundation MVP
- `CaveniaTerrainGeneratorPolicy.WORLD_HEIGHT` remains pinned to `128`
- `CaveniaTerrainGeneratorPolicy` still keeps the source-confirmed order of base stone fill, optional cave carving, biome top/filter replacement, VEINS generator-side mutation, final chunk construction and later population stage
- the terrain-generator foundation note at `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`
- non-runtime Cavenia cave-carver policy MVP
- `CaveniaCaveCarverPolicy` keeps the source-confirmed `20..24` tunnel-origin band and gravel/water/air carve-replacement rules visible from `core`
- `CaveniaCaveCarverPolicy` still keeps optional cave carving before biome top/filter replacement and before VEINS mutation
- the cave-carver policy note at `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`
- non-runtime Cavenia biome top/filter policy MVP
- `CaveniaBiomeTopFilterPolicy.entries().size()` remains pinned to `14`
- `CaveniaBiomeTopFilterPolicy.totalWeight()` remains pinned to `675`
- `CaveniaBiomeTopFilterPolicy` still keeps the default `minecraft:stone` terrain/filter rule and the shipped top-block mappings for the weighted `CaveniaConfig.BIOMES` roster
- the biome top/filter policy note at `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- non-runtime Cavenia VEINS/content policy MVP
- `CaveniaVeinsContentPolicy.entries().size()` remains pinned to `13`
- `CaveniaVeinsContentPolicy.totalWeight()` remains pinned to `436`
- `CaveniaVeinsContentPolicy` still keeps the default target block `minecraft:stone`, default chance `1.0D`, `autoVeins = false` and the generator-side VEINS ordering after cave carving and biome top/filter replacement but before final chunk construction
- the VEINS/content policy note at `docs/cavenia-veins-content-policy-non-runtime-mvp.md`
- non-runtime Cavenia population policy MVP
- `CaveniaPopulationPolicy.generateLakesDefaultEnabled()` remains pinned to `true`
- `CaveniaPopulationPolicy` still keeps population later than VEINS mutation and final chunk construction
- `CaveniaPopulationPolicy` still keeps representative water-lake, lava-lake, fall/liquid and `cavenic_shroom` branches visible from `core`
- the population policy note at `docs/cavenia-population-policy-non-runtime-mvp.md`
- non-registered Cavenia active-generator technical scaffold MVP
- `CaveniaGeneratorScaffold` now resolves from `core`
- `CaveniaGeneratorScaffold` still keeps `worldHeight = 128`, biome roster size `14` and VEINS roster size `13`
- `CaveniaGeneratorScaffold` still reports runtime generator registration as false and still requires dimension JSON plus dimension-type JSON before activation
- the non-registered scaffold note at `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- Cavenia Active Generator Runtime Prototype / Inert App-Side Bridge MVP
- inert app-side Cavenia generator bridge MVP
- `CaveniaGeneratorBridge` now resolves from `app-neoforge`
- `CaveniaGeneratorBridge` still mirrors `CAVENIA_LOCATION`, `CAVENIA_LEVEL_KEY`, `worldHeight = 128` and the accepted scaffold stage order from `core`
- `CaveniaGeneratorBridge` still reports runtime generator registration and runtime dimension resources as false, and still requires dimension JSON plus dimension-type JSON before activation
- the inert bridge note at `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- Cavenia Active Generator Registration / Inert Activation Boundary MVP
- inert Cavenia generator registration boundary MVP
- `CaveniaGeneratorRegistrationBoundary` now resolves from `app-neoforge`
- `CaveniaGeneratorRegistrationBoundary` still mirrors `CAVENIA_LOCATION`, `CAVENIA_LEVEL_KEY` and the accepted `cavernreborn:cavenia` identity from the bridge
- `CaveniaGeneratorRegistrationBoundary` still reports codec registration and generator registry entry registration as false, keeps runtime activation blocked and still requires dimension JSON plus dimension-type JSON before activation
- the inert registration boundary note at `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- Cavenia Chunk Generator / Biome Source Unregistered Skeleton MVP
- unregistered Cavenia generator/biome-source skeleton MVP
- `CaveniaGeneratorSkeleton` and `CaveniaBiomeSelectionSkeleton` now resolve from `app-neoforge`
- `CaveniaGeneratorSkeleton` still mirrors `CAVENIA_LOCATION`, `CAVENIA_LEVEL_KEY`, `worldHeight = 128` and the accepted scaffold plus terrain stage order from the bridge
- `CaveniaGeneratorSkeleton` still reports chunk creation, primer mutation, codec registration and generator registry entry registration as false
- `CaveniaBiomeSelectionSkeleton` still keeps biome roster size `14`, total weight `675` and runtime biome-source functionality as false
- the unregistered skeleton note at `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- Cavenia Generator Runtime Contract Interfaces / Non-Registered MVP
- non-registered Cavenia generator runtime-contract MVP
- `CaveniaGeneratorRuntimeContracts` now resolves from `app-neoforge`
- `CaveniaGeneratorRuntimeContracts` still keeps the future generator operation list size at `9` and keeps the operation order aligned with `CaveniaGeneratorScaffoldStage`
- `CaveniaGeneratorRuntimeContracts` still reports all runtime operations as blocked and still reports chunk creation plus primer mutation as absent
- `CaveniaGeneratorRuntimeContracts` still reports the generator skeleton as unable to create chunks and the biome-selection skeleton as not being a runtime biome source
- the non-registered runtime-contract note at `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- Cavenia Generator Activation-Readiness Runtime Host Contracts MVP
- non-registered Cavenia activation-readiness host-contract MVP
- `CaveniaActivationReadinessHosts` now resolves from `app-neoforge`
- `CaveniaActivationReadinessHosts` still keeps the host contract list size at `6` and keeps generator-host, biome-source-strategy-host, dimension-resource-host, access/travel-host, spawn-host and worldgen-resource-host readiness explicit
- `CaveniaActivationReadinessHosts` still reports all hosts as blocked, still reports no host as ready and still reports that no host can activate Cavenia
- `CaveniaActivationReadinessHosts` still reports runtime operations as blocked, the generator skeleton as unable to create chunks and the biome-selection skeleton as not being a runtime biome source
- the non-registered activation-readiness host-contract note at `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`
- Cavenia Generator Host / Biome Source Strategy Host Split Contracts MVP
- non-registered Cavenia generator-host / biome-source-strategy split-contract MVP
- `CaveniaGeneratorHostContracts` and `CaveniaBiomeSourceStrategyContracts` now resolve from `app-neoforge`
- `CaveniaGeneratorHostContracts` still keeps the generator-host requirement list size at `9`, still keeps all generator-host requirements blocked and still reports chunk creation plus primer mutation as absent
- `CaveniaBiomeSourceStrategyContracts` still keeps the biome-source-strategy requirement list size at `7`, still keeps all biome-source-strategy requirements blocked and still reports runtime biome-source behavior plus codec registration as absent
- the non-registered split-contract note at `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- Cavenia Dimension-Resource / Access-Travel / Spawn-Host Split Contracts MVP
- non-registered Cavenia dimension-resource / access-travel / spawn-host split-contract MVP
- `CaveniaDimensionResourceContracts`, `CaveniaAccessTravelContracts` and `CaveniaSpawnHostContracts` now resolve from `app-neoforge`
- `CaveniaDimensionResourceContracts` still keeps the dimension-resource requirement list size at `6`, still keeps all dimension-resource requirements blocked and still keeps dimension resources absent plus level creation blocked
- `CaveniaAccessTravelContracts` still keeps the access-travel requirement list size at `6`, still keeps all access-travel requirements blocked and still keeps teleport blocked
- `CaveniaSpawnHostContracts` still keeps the spawn-host requirement list size at `8`, still keeps all spawn-host requirements blocked and still keeps spawning blocked
- level creation, teleport and spawning blocked
- the non-registered split-contract note at `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`
- Cavenia Worldgen-Resource Host Split Contracts MVP
- non-registered Cavenia worldgen-resource split-contract MVP
- `CaveniaWorldgenResourceContracts` now resolves from `app-neoforge`
- `CaveniaWorldgenResourceContracts` still keeps the worldgen-resource requirement list size at `10`, still keeps all worldgen-resource requirements blocked and still keeps configured carver/configured feature/placed feature/biome modifier/biome tag resources absent
- worldgen-resource requirements cannot affect worldgen
- `CaveniaVeinsContentPolicy.entries().size()` remains pinned to `13`
- no `CAVERN` resource reuse is accepted as Cavenia parity
- the non-registered split-contract note at `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`
- Cavenia Activation Surface Consolidation / Final Inert Readiness Matrix MVP
- final inert Cavenia activation-surface readiness matrix MVP
- `CaveniaActivationReadinessMatrix` now resolves from `app-neoforge`
- `CaveniaActivationReadinessMatrix` still keeps the matrix entry count at `6`, still keeps the total and blocked requirement counts pinned to `46` and still keeps all activation surfaces blocked
- generator cannot create chunks, biome source runtime is not ready, dimension cannot create level, access cannot teleport, spawn host cannot spawn and worldgen cannot affect worldgen
- dimension JSON/type JSON and configured carver/configured feature/placed feature/biome modifier/biome tag resources remain absent
- `EntityCaveman -> deferred:caveman` remains unchanged
- no `CAVERN` resource reuse is accepted as Cavenia parity
- the final inert matrix note at `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- Cavenia Deliberate First Active Surface Selection MVP
- decision-only Cavenia deliberate first-active-surface selection MVP
- `CaveniaFirstActiveSurfaceSelection` now resolves from `app-neoforge`
- `CaveniaFirstActiveSurfaceSelection` still keeps the candidate count at `6`, still keeps exactly one selected candidate and still keeps `BIOME_SOURCE_STRATEGY` selected with requirement count `7`
- activation is not allowed in this slice and all candidates remain blocked
- readiness-matrix total and blocked requirement counts remain `46`
- biome source runtime is not ready, dimension JSON/type JSON remain absent, access cannot teleport, spawn host cannot spawn and worldgen cannot affect worldgen
- `EntityCaveman -> deferred:caveman` remains unchanged
- no `CAVERN` resource reuse is accepted as Cavenia parity
- the decision-only selection note at `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- Cavenia Biome Source Strategy Narrow Non-Runtime MVP
- narrow non-runtime Cavenia biome-source strategy plan MVP
- `CaveniaBiomeSourceStrategyPlan` now resolves from `app-neoforge`
- `CaveniaBiomeSourceStrategyPlan` still keeps the plan entry count at `8`
- the exact legacy biome entry count remains `14`
- the exact legacy total weight remains `675`
- legacy weighted inputs are pinned and legacy top-block inputs are pinned
- modern biome-key mapping is not ready, weighted selection algorithm is not ready, runtime `BiomeSource` is not ready, codec/registration is not ready and registry lookup access is not ready
- activation is not allowed in this slice and Cavenia cannot activate now
- dimension JSON/type JSON remain absent
- `EntityCaveman -> deferred:caveman` remains unchanged
- the narrow non-runtime strategy note at `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- Cavenia Legacy-To-Modern Biome-Key Mapping Inventory MVP
- non-runtime Cavenia legacy-to-modern biome-key mapping inventory MVP
- `CaveniaLegacyToModernBiomeKeyMappings` now resolves from `app-neoforge`
- the mapping entry count remains `14`
- every legacy biome has one candidate modern biome key
- candidate inventory is ready while final runtime mapping and registry verification are not ready
- representative candidate mappings remain pinned: `PLAINS -> minecraft:plains`, `SWAMPLAND -> minecraft:swamp`, `EXTREME_HILLS -> minecraft:windswept_hills`, `MESA -> minecraft:badlands`
- runtime `BiomeSource` is not ready, codec/registration is not ready and registry lookup access is not ready
- selected surface remains `BIOME_SOURCE_STRATEGY`, plan remains non-runtime and activation is not allowed in this slice
- dimension JSON/type JSON remain absent and Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged
- the candidate inventory note at `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`
- Cavenia Weighted Biome Selection Algorithm / Pure Non-Runtime MVP
- pure non-runtime Cavenia weighted biome selection algorithm MVP
- `CaveniaWeightedBiomeSelectionAlgorithm` now resolves from `app-neoforge`
- the algorithm entry count remains `14` and the total weight remains `675`
- ranges remain contiguous and normalized `0..674` selection stays deterministic
- candidate inventory is ready and weighted selection algorithm inventory is ready
- weighted selection runtime is not ready, final runtime mapping is not ready and registry verification is not ready
- runtime `BiomeSource` is not ready, codec/registration is not ready and registry lookup access is not ready
- representative selections remain pinned: `0` first entry, `675` first entry and `-1` last entry
- selected surface remains `BIOME_SOURCE_STRATEGY`, plan remains non-runtime and activation is not allowed in this slice
- dimension JSON/type JSON remain absent and Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged
- the pure non-runtime selector note at `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- Cavenia Unregistered Runtime Biome-Source Shape / Adapter Contract MVP
- unregistered Cavenia runtime-biome-source shape / adapter contract MVP
- `CaveniaBiomeSelectionAdapterContract` now resolves from `app-neoforge`
- adapter shape is ready while adapter runtime is not ready
- adapter consumes the pure weighted selector and keeps the exact entry count `14` and total weight `675`
- representative adapter selections remain pinned: `0` first entry candidate, `675` first entry candidate and `-1` last entry candidate
- adapter results remain candidate-only while runtime `BiomeSource`, runtime biome-source registration, codec/registration and registry lookup access are not ready
- selected surface remains `BIOME_SOURCE_STRATEGY`, plan remains non-runtime and activation is not allowed in this slice
- dimension JSON/type JSON remain absent and Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged
- the unregistered adapter note at `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- the Cavenia Adapter Codec/Registration Readiness Contracts MVP note at `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- the adapter codec/registration readiness note at `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- Cavenia Registry Lookup Readiness Contracts MVP
- registry lookup readiness-contract MVP
- `CaveniaRegistryLookupReadiness` now resolves from `app-neoforge`
- the requirement count remains `9`
- candidate keys are inventoried and candidate keys remain string-only
- registry lookup readiness is ready while registry lookup runtime is not ready
- registry access source decision is not ready, biome registry reference is not ready and resource-key conversion is not ready
- holder resolution is not ready, missing-biome fallback decision is not ready, runtime lookup context is not ready and adapter result to runtime biome is not ready
- `EntityCaveman -> deferred:caveman` remains unchanged
- the readiness-only registry-lookup note at `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- Cavenia BIOME_SOURCE_STRATEGY Final Readiness Matrix MVP
- final selected-surface BIOME_SOURCE_STRATEGY readiness-matrix MVP
- `CaveniaBiomeSourceStrategyReadinessMatrix` now resolves from `app-neoforge`
- the matrix entry count remains `6` and the total selected-surface readiness item count remains `68`
- all six readiness layers are ready as contracts/data while all runtime surfaces remain blocked
- candidate inventory is ready, candidate keys remain string-only, the weighted selection algorithm is ready, adapter shape is ready, codec/registration readiness is ready and registry lookup readiness is ready
- registry lookup runtime is not ready, registry lookup access is not ready, registry verification is not ready and runtime biome resolution is not ready
- runtime `BiomeSource` is not ready, runtime biome-source registration is not ready, codec is not registered and dimension binding is not ready
- selected surface remains `BIOME_SOURCE_STRATEGY`, the plan remains non-runtime and activation is not allowed in this slice
- dimension JSON/type JSON remain absent, Cavenia cannot activate now and `EntityCaveman -> deferred:caveman` remains unchanged
- the final selected-surface readiness-matrix note at `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- Cavenia Runtime BiomeSource First Implementation Decision / Guardrails MVP
- runtime-biome-source first-implementation decision/guardrails MVP
- `CaveniaRuntimeBiomeSourceFirstImplementationDecision` now resolves from `app-neoforge`
- selected decision is `PROCEED_WITH_UNREGISTERED_RUNTIME_BIOME_SOURCE_SKELETON_NEXT`
- decision is to proceed with unregistered skeleton next while runtime biome source is not implemented in this slice
- activation is not allowed in this slice, guardrail count remains `14` and all guardrails are enforced in this slice
- no runtime activation is allowed by guardrails and next slice may add only an unregistered skeleton
- next slice may not add codec implementation, may not register codec and may not register biome-source type
- next slice may not use registry lookup access, may not add dimension JSON/type JSON and may not create an active Cavenia level
- next slice may not add worldgen resources, may not add access/teleport, may not add spawning and may not register `cavernreborn:caveman`
- selected-surface readiness matrix remains ready while selected-surface runtime remains blocked
- selected-surface readiness item count remains `68` and global readiness matrix total/blocked requirement counts remain `46/46`
- runtime biome source is not ready, runtime biome source is not registered, codec is not registered and registry lookup access is not ready
- dimension JSON/type JSON remain absent, Cavenia cannot activate now and `EntityCaveman -> deferred:caveman` remains unchanged
- the decision/guardrails note at `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- Cavenia Unregistered Runtime BiomeSource Skeleton MVP
- unregistered runtime-biome-source skeleton MVP
- `CaveniaRuntimeBiomeSourceSkeleton` now resolves from `app-neoforge`
- skeleton shape is ready and unregistered skeleton is ready
- the skeleton does not extend Minecraft `BiomeSource` and is not runtime implemented or registered
- the skeleton consumes the biome-selection adapter and preserves coordinate query fields without using them for selection yet
- representative skeleton selections remain pinned: `0` first entry candidate, `675` first entry candidate and `-1` last entry candidate
- coordinate query preserves coordinates but does not change selection for the same weight value
- codec implementation is absent, codec registration is absent and registry lookup access is absent
- biome holder/resource-key conversion is absent and dimension binding is absent
- decision guardrail count remains `14` and all guardrails remain enforced
- selected-surface readiness item count remains `68` and global readiness matrix total/blocked requirement counts remain `46/46`
- dimension JSON/type JSON remain absent, Cavenia cannot activate now and `EntityCaveman -> deferred:caveman` remains unchanged
- the unregistered runtime-biome-source skeleton note at `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- the runtime-biome-source first-implementation decision/guardrails note at `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- Cavenia Runtime BiomeSource Codec/Holder/Registry Decision MVP
- runtime-biome-source codec/holder/registry decision MVP
- the runtime-biome-source codec/holder/registry decision note at `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`
- registry lookup access is not ready, registry verification is not ready and runtime biome source is not ready
- runtime biome source is not registered and codec is not registered
- adapter entry count remains `14` and adapter total weight remains `675`
- selected surface remains `BIOME_SOURCE_STRATEGY`, plan remains non-runtime and activation is not allowed in this slice
- dimension JSON/type JSON remain absent and Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged
- Cavenia Runtime BiomeSource API Shape Inventory MVP
- runtime-biome-source API-shape inventory MVP
- `CaveniaRuntimeBiomeSourceApiShapeInventory` now resolves from `app-neoforge`
- entry count is `12`, local `BiomeSource` class was located, all components were locally inspected and all shapes are pinned
- runtime API is not implemented and is not allowed in this slice
- abstract methods inventory, codec dispatch/method, possible-biomes, noise-biome query, climate sampler, holder return, registry context, resource-key conversion and missing-biome fallback shapes are now pinned as inventory/data
- real subclass is still deferred and not ready for real subclass implementation
- existing subclass decision now reports API shape decisions pinned while still keeping real subclass deferred
- next slice may decide real subclass implementation, but may not implement a real subclass directly without a separate explicit decision
- next slice may not add codec implementation/registration, may not use registry lookup access and may not add dimension JSON/type JSON
- next slice may not create an active Cavenia level, may not add worldgen resources, may not add access/teleport, may not add spawning and may not register `cavernreborn:caveman`
- selected-surface readiness item count remains `68` and global readiness matrix total/blocked requirement counts remain `46/46`
- runtime biome source is not ready, runtime biome source is not registered, codec is not registered and registry lookup access is not ready
- dimension JSON/type JSON remain absent, Cavenia cannot activate now and `EntityCaveman -> deferred:caveman` remains unchanged
- the runtime-biome-source API-shape inventory note at `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`
- Cavenia Runtime BiomeSource Real Subclass Go/No-Go Decision MVP
- runtime-biome-source real-subclass go/no-go decision MVP
- `CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision` now resolves from `app-neoforge`
- selected decision is `PROCEED_WITH_GUARDED_UNREGISTERED_REAL_BIOME_SOURCE_SUBCLASS_NEXT`
- decision is go for guarded unregistered real subclass next
- real subclass is not implemented in this slice and runtime API is not allowed in this slice
- guardrail count is `18`, all guardrails are enforced in this slice and no runtime activation is allowed by guardrails
- next slice may add one designated real subclass file named `CaveniaRuntimeBiomeSource.java`
- next slice may extend `BiomeSource` only in the designated subclass
- next slice may use abstract method signature types only and may use unsupported method stubs only
- next slice may not add usable codec implementation, may not register codec and may not register biome-source type
- next slice may not use registry lookup access and may not implement holder/resource-key conversion
- next slice may not add dimension JSON/type JSON and may not create an active Cavenia level
- next slice may not add worldgen resources, may not add access/teleport, may not add spawning and may not register `cavernreborn:caveman`
- API shape inventory is ready, skeleton is ready, selected-surface readiness item count remains `68` and global readiness matrix total/blocked requirement counts remain `46/46`
- runtime biome source is not ready in this slice, runtime biome source is not registered, codec is not registered and registry lookup access is not ready
- dimension JSON/type JSON remain absent, Cavenia cannot activate now and `EntityCaveman -> deferred:caveman` remains unchanged
- the runtime-biome-source real-subclass go/no-go decision note at `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`
- Cavenia Guarded Unregistered Real BiomeSource Subclass Stub MVP
- guarded unregistered real `BiomeSource` subclass stub MVP
- `CaveniaRuntimeBiomeSource` now resolves from `app-neoforge`
- it extends Minecraft `BiomeSource`, but normal runtime construction is not allowed
- unsupported method stubs only
- codec method is stubbed, possible-biomes collection method is stubbed and noise-biome method is stubbed
- usable runtime behavior is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered
- registry lookup access is not ready, holder/resource-key conversion is not ready and dimension binding is not ready
- go/no-go guardrails remain enforced, selected-surface readiness item count remains `68` and global readiness matrix total/blocked requirement counts remain `46/46`
- dimension JSON/type JSON remain absent, Cavenia cannot activate now and `EntityCaveman -> deferred:caveman` remains unchanged
- the guarded runtime-biome-source subclass stub note at `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`
- the registry lookup readiness note at `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `CaveniaSpawnProviderPolicy` still keeps `EntityCaveman -> deferred:caveman`
- no registered `cavernreborn:caveman` entity type exists at runtime
- no checked-in Caveman spawn placement, biome modifier or biome tag exists
- the focused Caveman normal-roster boundary note at `docs/caveman-cavenia-normal-roster-boundary.md`
- non-runtime Cavenia spawn-provider policy constant and roster smoke
- all four crazy entity types still keep no registered normal spawn placement tied to a normal `CAVERN` spawn path
- no checked-in normal `CAVERN` crazy biome modifiers or crazy biome tags exist
- the active direct Cavenic `CAVERN` spawn placements still resolve separately from any Cavenia spawn-provider boundary work
- the Cavenia spawn-provider / crazy-roster activation contract boundary note at `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- crazy spider explicit no-natural-spawn baseline boundary
- actual long-run crazy spider orb drop-rate balance is not covered by server GameTests
- actual long-running Crazy Spider fire/lava gameplay feel remains manual
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
- actual Crazy Zombie renderer/model visual feel
- actual Crazy Skeleton renderer/model visual feel
- actual Crazy Creeper renderer/model visual feel
- actual Crazy Spider renderer/model visual feel
- actual client-visible Crazy Creeper boss-bar feel remains manual
- actual client visual crazy creeper particle feel remains manual
- actual client-visible Crazy Skeleton boss-bar feel remains manual
- actual Crazy Skeleton client particle-trail visual feel
- Crazy Skeleton particle-trail follow-up visual feel remains manual
- actual long-running Crazy Creeper fire/lava gameplay feel
- actual Crazy Creeper destructive explosion gameplay feel remains manual
- a seventh direct `EntityCavenic*` mob baseline, because the inspected legacy direct roster ends at zombie, skeleton, creeper, spider, witch and bear; the remaining related legacy entities are crazy variants, summon variants or non-Cavenic mobs
- actual long-run Crazy Zombie orb-drop rate balance
- actual long-running Crazy Zombie fire/lava gameplay feel
- actual Crazy Zombie client boss-bar visual feel
- actual Crazy Zombie client particle-trail visual feel
- actual long-running Crazy Zombie melee-knockback feel beyond the deterministic helper/runtime smoke
- Crazy Zombie natural spawning, because the inspected legacy path is tied to the old Cavenia-only crazy-roster provider/config branch rather than the current `CAVERN` biome-spawn pattern
- Crazy Zombie custom loot beyond the restored inherited `cavenic_orb` branch
- actual long-running Crazy Spider fire/lava gameplay feel
- actual client visual crazy spider boss-bar feel remains manual
- actual Crazy Spider client particle-trail visual feel
- Crazy Spider particle-trail follow-up visual feel remains manual
- actual long-running Crazy Spider combat/pathfinding feel remains manual
- actual client-visible Crazy Skeleton boss-bar feel remains manual
- Crazy Skeleton natural spawning, because the inspected legacy path still belongs to the old Cavenia-only crazy-roster provider/config branch rather than the current `CAVERN` biome-spawn pattern
- Crazy Skeleton custom loot beyond the restored inherited `cavenic_orb` branch
- actual long-running Crazy Skeleton fire/lava gameplay feel
- actual long-running Crazy Skeleton ranged combat feel remains manual
- natural spawning remains deferred through Cavenia boundary
- actual long-run Cavenic Bear drop-rate balance, although the current source inspection found no direct custom bear loot branch beyond the vanilla polar bear baseline
- actual long-running Cavenic Bear fire/lava gameplay feel
- actual long-running Cavenic Bear melee/pathfinding feel
- actual long-running Cavenic Bear panic/pathfinding feel
- actual long-running Cavenic Bear passive follow/wander/look pathfinding feel
- actual Cavenic Bear anger/pathfinding feel
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
- legacy `EntityCavenicArrow` parity beyond the current vanilla-compatible skeleton/player projectile boundary
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

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Codec Method Shape Stub MVP: `CaveniaRuntimeBiomeSourceCodecMethodShapeStub` now resolves from `app-neoforge`, pins the local `codec()` method shape as `MapCodec<? extends BiomeSource>`, keeps `CaveniaRuntimeBiomeSource.codec()` unsupported, keeps usable codec implementation, static `CODEC`, `RecordCodecBuilder`, codec registration, biome-source type registration, registry lookup access, holder/resource-key conversion implementation and activation absent, and documents that stub in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Holder/Resource-Key Conversion Readiness MVP: `CaveniaRuntimeBiomeSourceHolderConversionReadiness` now resolves from `app-neoforge`, holder conversion readiness is ready, candidate key format is pinned and registry lookup is required for future runtime conversion, runtime conversion implementation is not ready, registry lookup access is not ready, holder resolution is not ready, resource-key conversion is not ready and resource-location conversion is not ready, possible-biomes runtime is not ready, noise-biome runtime is not ready, missing-biome fallback is not ready and real holder conversion is still deferred, candidate entry count remains `14`, candidate inventory is ready and candidate keys remain string-only, designated subclass remains ready, codec method shape stub remains ready, usable codec implementation is not ready and biome-source type registration is not ready, normal runtime construction is not allowed, unsupported method stubs only remain in place and `EntityCaveman -> deferred:caveman` remains unchanged, and the holder/resource-key conversion readiness note at `docs/cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md` documents that inert path.
The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Fallback Policy Readiness MVP: `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness` now resolves from `app-neoforge`, fallback policy readiness is ready, fallback policy source is pinned and fallback trigger unparseable candidate id is pinned, fallback trigger unresolved resource key is pinned, fallback trigger missing holder is pinned and fallback trigger empty possible biomes is pinned, fallback legacy biome name is pinned, fallback candidate modern key is pinned, fallback registry verification is deferred and fallback holder resolution is deferred, fallback runtime usage is deferred, runtime fallback implementation is not ready and registry lookup is required for future runtime fallback, registry lookup access is not ready, fallback is not registry verified, fallback holder is not resolved and fallback runtime is not ready, possible-biomes fallback is not ready, noise-biome fallback is not ready and real fallback policy is still deferred, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains` and fallback candidate exists in inventory, fallback candidate entry count remains `14`, candidate inventory is ready and candidate keys remain string-only, holder conversion readiness is ready, holder conversion runtime is not ready, codec method shape stub is ready and designated subclass remains ready, designated subclass runtime is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place and `EntityCaveman -> deferred:caveman` remains unchanged, and the fallback-policy readiness note at `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md` documents that inert path.
The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Possible-Biomes Readiness MVP: `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness` now resolves from `app-neoforge`, possible-biomes readiness is ready, possible-biomes method shape is pinned, candidate keys source is pinned, candidate entry count is pinned, candidate ordering policy is pinned, candidate deduplication policy is pinned, holder conversion dependency is pinned and fallback-included-if-empty policy is pinned, runtime holder stream is not ready, collectPossibleBiomes implementation is not ready and registry lookup is required for future runtime possible-biomes, registry lookup access is not ready, possible-biomes runtime is not ready and real possible-biomes is still deferred, candidate entry count remains `14`, candidate inventory is ready and candidate keys remain string-only, fallback policy readiness is ready, fallback policy runtime is not ready, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, fallback included if empty is true and fallback runtime is not ready, holder conversion readiness is ready, holder conversion runtime is not ready, holder resolution is not ready, resource-key conversion is not ready and resource-location conversion is not ready, codec method shape stub is ready, designated subclass remains ready and designated subclass runtime is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place, `collectPossibleBiomes()` remains stubbed, `getNoiseBiome(...)` remains stubbed, `codec()` remains stubbed and `EntityCaveman -> deferred:caveman` remains unchanged, and the possible-biomes readiness note at `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md` documents that inert path.
The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Noise-Biome Selection Readiness MVP: `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness` now resolves from `app-neoforge`, noise-biome selection readiness is ready, noise-biome method shape is pinned, coordinate input shape is pinned, climate sampler input shape is pinned, weight-value derivation decision is pinned, weighted selection source is pinned, adapter selection source is pinned, candidate key output source is pinned, holder conversion dependency is pinned, fallback policy source is pinned and possible-biomes constraint is pinned, runtime holder return is not ready, getNoiseBiome implementation is not ready and registry lookup is required for future runtime noise-biome, registry lookup access is not ready, noise-biome runtime is not ready and real noise-biome selection is still deferred, weighted selection algorithm is ready, weighted selection runtime is not ready and weighted selection total weight remains `675`, adapter shape is ready and adapter runtime is not ready, candidate entry count remains `14`, candidate inventory is ready and candidate keys remain string-only, holder conversion readiness is ready, holder conversion runtime is not ready, holder resolution is not ready, resource-key conversion is not ready and resource-location conversion is not ready, fallback policy readiness is ready, fallback policy runtime is not ready, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, possible-biomes readiness is ready and possible-biomes runtime is not ready, codec method shape stub is ready, designated subclass remains ready and designated subclass runtime is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place, `collectPossibleBiomes()` remains stubbed, `getNoiseBiome(...)` remains stubbed, `codec()` remains stubbed and `EntityCaveman -> deferred:caveman` remains unchanged, and the noise-biome selection readiness note at `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md` documents that inert path.

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Readiness Chain Consolidation / Next Decision MVP: `CaveniaRuntimeBiomeSourceReadinessChainConsolidation` now resolves from `app-neoforge`, readiness-chain consolidation is ready, all current readiness layers are ready, runtime-ready layer count is `0`, consolidated readiness layer count is `6`, the selected next decision is `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_GO_NO_GO_NEXT`, selector-input-derivation go/no-go is next, selector-input derivation is not pinned, selector-input derivation implementation is not ready, selector-input derivation runtime is not ready, next slice may add selector-input-derivation go/no-go decision, the next slice may not implement selector-input derivation or use coordinates or climate sampler for runtime selection, the next slice may not make `getNoiseBiome(...)` or `collectPossibleBiomes()` usable, the next slice may not use registry lookup access, resolve holders, implement resource-location conversion, implement resource-key conversion, add fallback implementation, add usable codec implementation, register codec, register biome-source type, add dimension JSON/type JSON, create an active Cavenia level, add worldgen resources, add access/teleport, add spawning or register Caveman entity, the current codec, holder-conversion, fallback, possible-biomes and noise-biome readiness layers all remain ready while every runtime surface remains blocked, and the readiness-chain consolidation note at `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md` documents that inert path.

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Selector Input Derivation Go/No-Go MVP: `CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision` now resolves from `app-neoforge`, selected decision is `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT`, decision is go for selector-input derivation readiness next, decision is readiness-only for next slice, selector-input derivation readiness is not implemented in this slice, selector-input derivation policy is not pinned in this slice, selector-input derivation implementation is not ready and selector-input derivation runtime is not ready, guardrail count is `17`, all guardrails are enforced in this slice, allowed next-slice readiness action count is `4`, allowed next-slice runtime action count is `0` and no runtime action is allowed by guardrails, next slice may add selector-input derivation readiness, may pin coordinate input policy, may pin climate sampler input policy and may pin weight-value derivation policy, next slice may not implement selector-input derivation, may not use coordinates or climate sampler for runtime selection, may not make `getNoiseBiome(...)` or `collectPossibleBiomes()` usable, and may not use registry lookup access or resolve holders, readiness-chain consolidation is ready, readiness-chain runtime is not ready, readiness-chain selected next decision matches, all current readiness layers are ready, runtime-ready layer count remains `0` and consolidated readiness layer count remains `6`, noise-biome selection readiness is ready, noise-biome selection runtime is not ready, coordinate input shape is pinned, climate sampler input shape is pinned, weight-value derivation decision is pinned and weighted selection total weight remains `675`, candidate entry count remains `14`, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, holder conversion readiness is ready, fallback policy readiness is ready, possible-biomes readiness is ready and codec method shape stub is ready, designated subclass remains ready, usable runtime biome source is not ready, registry lookup access is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, and the selector-input derivation go/no-go note at `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md` documents that inert path.

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Selector Input Derivation Readiness MVP: `CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness` now resolves from `app-neoforge`, selector-input derivation readiness is ready, method input shape is pinned, coordinate input policy is pinned, climate sampler input policy is pinned, selector input output shape is pinned, deterministic derivation policy is pinned, pure non-runtime policy is pinned, weighted selector compatibility is pinned, adapter query compatibility is pinned, normalization policy is pinned, negative coordinate stability policy is pinned, chunk-boundary stability policy is pinned, no-world-or-registry dependency is pinned and no-random-or-mutable-state dependency is pinned, selector-input derivation implementation is not ready, selector-input derivation runtime is not ready, coordinate runtime selection is not ready, climate sampler runtime selection is not ready and real selector-input derivation is still deferred, actual derivation formula is not pinned and actual derivation formula is not implemented, go/no-go decision is ready and go/no-go runtime is not ready, readiness-chain consolidation is ready and readiness-chain runtime is not ready, all current readiness layers are ready, runtime-ready layer count remains `0` and consolidated readiness layer count remains `6`, noise-biome selection readiness is ready and noise-biome selection runtime is not ready, weighted selection total weight remains `675`, adapter shape is ready and adapter runtime is not ready, candidate entry count remains `14`, candidate inventory is ready and candidate keys are string-only, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, holder conversion readiness is ready, holder conversion runtime is not ready, holder resolution is not ready, resource-location conversion is not ready and resource-key conversion is not ready, possible-biomes readiness is ready and possible-biomes runtime is not ready, fallback policy readiness is ready and fallback policy runtime is not ready, codec method shape stub is ready and codec method shape runtime is not ready, designated subclass remains ready and designated subclass runtime is not ready, runtime holder return is not ready, collectPossibleBiomes implementation is not ready, getNoiseBiome implementation is not ready, usable runtime biome source is not ready, registry lookup access is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place, selected-surface readiness item count remains `68`, global readiness matrix total requirement count remains `46`, global readiness matrix blocked requirement count remains `46`, dimension JSON is absent, dimension type JSON is absent, Caveman remains deferred and `EntityCaveman -> deferred:caveman` remains unchanged, and the selector-input derivation readiness note at `docs/cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md` documents that inert path.

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Selector Input Derivation Implementation Go/No-Go MVP: `CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision` now resolves from `app-neoforge`, selected decision is `PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_INPUT_DERIVATION_ALGORITHM_NEXT`, decision is go for pure non-runtime algorithm next, selector-input derivation algorithm is not implemented in this slice, guardrail count is `18`, all guardrails are enforced in this slice, allowed next-slice pure algorithm action count is `4`, allowed next-slice runtime action count is `0` and no runtime action is allowed by guardrails, next slice may add pure non-runtime selector-input algorithm, may use integer coordinate inputs, may return signed int selector input and may delegate normalization to existing selector/adapter, next slice may not call climate sampler methods, may not import Minecraft runtime APIs, may not wire into `getNoiseBiome(...)`, may not make `getNoiseBiome(...)` or `collectPossibleBiomes()` usable, may not use registry lookup access or resolve holders, may not implement resource-location conversion or resource-key conversion, may not add fallback implementation and may not use random or mutable state, selector-input derivation readiness is ready, selector-input derivation readiness runtime is not ready, selector-input derivation output shape is pinned, actual derivation formula is not pinned and actual derivation formula is not implemented, readiness-chain consolidation is ready, readiness-chain runtime is not ready, all current readiness layers are ready, runtime-ready layer count remains `0` and consolidated readiness layer count remains `6`, noise-biome selection readiness is ready and noise-biome selection runtime is not ready, weighted selection total weight remains `675`, adapter shape is ready and adapter runtime is not ready, candidate entry count remains `14`, candidate inventory is ready and candidate keys are string-only, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, holder conversion readiness is ready and holder conversion runtime is not ready, holder resolution is not ready, resource-location conversion is not ready and resource-key conversion is not ready, possible-biomes readiness is ready and possible-biomes runtime is not ready, fallback policy readiness is ready and fallback policy runtime is not ready, codec method shape stub is ready and codec method shape runtime is not ready, designated subclass remains ready and designated subclass runtime is not ready, runtime holder return is not ready, collectPossibleBiomes implementation is not ready, getNoiseBiome implementation is not ready, usable runtime biome source is not ready, registry lookup access is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place, selected-surface readiness item count remains `68`, global readiness matrix total requirement count remains `46`, global readiness matrix blocked requirement count remains `46`, dimension JSON is absent, dimension type JSON is absent, Caveman remains deferred and `EntityCaveman -> deferred:caveman` remains unchanged, and the selector-input derivation implementation go/no-go note at `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md` documents that inert path.

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Selector Input Derivation Pure Non-Runtime Algorithm MVP: `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm` now resolves from `app-neoforge`, selector-input derivation algorithm is ready, pure non-runtime algorithm is ready, integer coordinate inputs are ready, climate sampler is excluded, climate sampler methods are not called, Minecraft runtime API imports are not required, deterministic mixing formula is ready, signed int selector output is ready, normalization is delegated, negative coordinate stability is ready, chunk boundary stability is ready, world/registry dependency is absent, random/mutable-state dependency is absent, getNoiseBiome wiring is not ready, runtime selection is not ready and real runtime selection is still deferred, sample vectors `deriveSelectorInput(0, 0, 0) == -104499101`, `deriveSelectorInput(1, 2, 3) == 700186390` and `deriveSelectorInput(-16, 64, 16) == -1039724642` are pinned, implementation go/no-go decision is ready and implementation go/no-go runtime is not ready, selector-input derivation readiness is ready and selector-input derivation readiness runtime is not ready, readiness-chain consolidation is ready and readiness-chain runtime is not ready, all current readiness layers are ready, runtime-ready layer count remains `0` and consolidated readiness layer count remains `6`, weighted selection total weight remains `675`, adapter shape is ready and adapter runtime is not ready, candidate entry count remains `14`, candidate inventory is ready and candidate keys are string-only, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, holder conversion readiness is ready and holder conversion runtime is not ready, holder resolution is not ready, resource-location conversion is not ready and resource-key conversion is not ready, possible-biomes readiness is ready and possible-biomes runtime is not ready, fallback policy readiness is ready and fallback policy runtime is not ready, codec method shape stub is ready and codec method shape runtime is not ready, designated subclass remains ready and designated subclass runtime is not ready, runtime holder return is not ready, collectPossibleBiomes implementation is not ready, getNoiseBiome implementation is not ready, usable runtime biome source is not ready, registry lookup access is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place, selected-surface readiness item count remains `68`, global readiness matrix total requirement count remains `46`, global readiness matrix blocked requirement count remains `46`, dimension JSON is absent, dimension type JSON is absent, Caveman remains deferred and `EntityCaveman -> deferred:caveman` remains unchanged, and the pure non-runtime selector-input derivation algorithm note at `docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md` documents that inert path.

The same runtime smoke slice now also covers the Cavenia Runtime BiomeSource Selector Input Algorithm Consolidation / Next Decision MVP: `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation` now resolves from `app-neoforge`, selector-input algorithm consolidation is ready, selector-input algorithm is ready, pure non-runtime algorithm is ready, integer coordinate inputs are ready, climate sampler is excluded, signed int selector output is ready, deterministic formula is ready, normalization is delegated, weighted selector compatibility is ready and adapter compatibility is ready, selector-to-weighted-candidate bridge is not ready, is not implemented and is not runtime-ready, selected next decision is `PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_GO_NO_GO_NEXT`, selector-to-weighted-candidate bridge go/no-go is next, next slice may add only a selector-to-weighted-candidate bridge go/no-go decision, next slice may not implement bridge behavior, may not call the weighted selector or adapter with derived input and may not wire into `getNoiseBiome(...)`, sample derivations `deriveSelectorInput(0, 0, 0) == -104499101`, `deriveSelectorInput(1, 2, 3) == 700186390` and `deriveSelectorInput(-16, 64, 16) == -1039724642` remain pinned, implementation go/no-go decision is ready and implementation go/no-go runtime is not ready, selector-input derivation readiness is ready and selector-input derivation readiness runtime is not ready, readiness-chain consolidation is ready and readiness-chain runtime is not ready, all current readiness layers are ready, runtime-ready layer count remains `0` and consolidated readiness layer count remains `6`, weighted selection total weight remains `675`, adapter shape is ready and adapter runtime is not ready, candidate entry count remains `14`, candidate inventory is ready and candidate keys are string-only, fallback legacy biome name is `PLAINS`, fallback candidate modern biome key is `minecraft:plains`, holder conversion readiness is ready and holder conversion runtime is not ready, holder resolution is not ready, resource-location conversion is not ready and resource-key conversion is not ready, possible-biomes readiness is ready and possible-biomes runtime is not ready, fallback policy readiness is ready and fallback policy runtime is not ready, codec method shape stub is ready and codec method shape runtime is not ready, designated subclass remains ready and designated subclass runtime is not ready, runtime holder return is not ready, collectPossibleBiomes implementation is not ready, getNoiseBiome implementation is not ready, usable runtime biome source is not ready, registry lookup access is not ready, usable codec implementation is not ready, codec is not registered and biome-source type is not registered, normal runtime construction is not allowed, unsupported method stubs only remain in place, selected-surface readiness item count remains `68`, global readiness matrix total requirement count remains `46`, global readiness matrix blocked requirement count remains `46`, dimension JSON is absent, dimension type JSON is absent, Caveman remains deferred and `EntityCaveman -> deferred:caveman` remains unchanged, and the selector-input algorithm consolidation note at `docs/cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md` documents that inert path.
