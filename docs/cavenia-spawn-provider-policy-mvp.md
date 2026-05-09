# Cavenia Spawn Provider Policy / Non-Runtime MVP

This note documents the new non-runtime policy layer that encodes the source-confirmed legacy Cavenia spawn-provider defaults, roster data and crazy-roster decision helpers without enabling any active Cavenia spawning.

The consolidated readiness and future implementation order are documented separately in:

- `docs/cavenia-active-foundation-readiness-plan.md`

The narrower active-foundation implementation-decision spike is documented separately in:

- `docs/cavenia-active-foundation-technical-spike.md`

The source-of-truth boundary notes remain:

- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/cavenia-dimension-provider-foundation-boundary.md`

## What This MVP Adds

Reborn now has a pure Java policy layer in:

- `core/src/main/java/com/richardkenway/cavernreborn/core/spawn/CaveniaSpawnProviderPolicy.java`
- `core/src/main/java/com/richardkenway/cavernreborn/core/spawn/CaveniaSpawnEntry.java`

The exported non-runtime types are:

- `CaveniaSpawnProviderPolicy`
- `CaveniaSpawnEntry`

This layer is:

- non-runtime
- not wired to NeoForge spawn placement registration
- not wired to biome modifiers or biome tags
- not wired to any active `cavenia` level
- not a `WorldProviderCavenia` port

It exists only to pin the source-confirmed data and decision rules for future active Cavenia spawn-provider work.

## Source-Confirmed Defaults Encoded

The policy pins:

- `DEFAULT_MONSTER_SPAWN = 200`
- `MIN_MONSTER_SPAWN = 0`
- `MAX_MONSTER_SPAWN = 5000`
- `DEFAULT_CRAZY_SPAWN_CHANCE = 0.1D`
- `MIN_CRAZY_SPAWN_CHANCE = 0.0D`
- `MAX_CRAZY_SPAWN_CHANCE = 1.0D`

The policy also exposes source-confirmed helpers for:

- `clampMonsterSpawn(int value)`
- `clampCrazySpawnChance(double value)`
- `isCrazyRosterChanceEnabled(double chance)`
- `scanRangeForCrazySpawnChance(double chance)`
- `verticalScanRangeFor(int range)`
- `canUseCrazyRoster(double chance, boolean chanceRollPassed, boolean nearbyBossLikeCavenicMobFound)`
- `canUseCrazyRosterAfterChanceRoll(double chance, boolean nearbyBossLikeCavenicMobFound)`

## Crazy Spawn Chance To Range Mapping

The policy pins the inspected legacy mapping exactly:

- `chance <= 0.1D` -> `range = 50`
- `chance <= 0.2D` -> `range = 32`
- `chance <= 0.4D` -> `range = 16`
- `chance <= 0.6D` -> `range = 8`
- `chance <= 0.8D` -> `range = 4`
- otherwise `range = 0`

The vertical range helper also stays source-confirmed:

- `rangeY = range > 1 ? range / 2 : range`

This means:

- `50 -> 25`
- `32 -> 16`
- `16 -> 8`
- `8 -> 4`
- `4 -> 2`
- `1 -> 1`
- `0 -> 0`

## Source-Literal Nearby Predicate Wording

The legacy nearby scan predicate remains documented source-literally:

- `entity instanceof ICavenicMob && !entity.isNonBoss()`

This MVP keeps the wording aligned with that source:

- nearby `ICavenicMob` whose `isNonBoss()` returns `false`
- boss-like / not-non-boss `ICavenicMob`

It does not rename that predicate as:

- nearby non-boss `ICavenicMob`

## Normal Roster Encoded

The non-runtime normal roster is:

- `EntityCavenicSkeleton` -> `cavernreborn:cavenic_skeleton`, weight `20`, group `1 / 1`
- `EntityCavenicCreeper` -> `cavernreborn:cavenic_creeper`, weight `30`, group `1 / 1`
- `EntityCavenicZombie` -> `cavernreborn:cavenic_zombie`, weight `30`, group `2 / 2`
- `EntityCavenicSpider` -> `cavernreborn:cavenic_spider`, weight `30`, group `1 / 1`
- `EntityCavenicWitch` -> `cavernreborn:cavenic_witch`, weight `15`, group `1 / 1`
- `EntityCavenicBear` -> `cavernreborn:cavenic_bear`, weight `30`, group `1 / 1`
- `EntityCaveman` -> deferred marker `deferred:caveman`, weight `35`, group `1 / 1`

`EntityCaveman` is intentionally a deferred legacy entry only. This policy does not add a Caveman entity or imply runtime registration.
The focused Caveman inspection and deferral reasons are documented separately in `docs/caveman-cavenia-normal-roster-boundary.md`.

## Crazy Roster Encoded

The non-runtime crazy roster is:

- `EntityCrazySkeleton` -> `cavernreborn:crazy_skeleton`, weight `1`, group `1 / 1`
- `EntityCrazyCreeper` -> `cavernreborn:crazy_creeper`, weight `1`, group `1 / 1`
- `EntityCrazyZombie` -> `cavernreborn:crazy_zombie`, weight `1`, group `1 / 1`
- `EntityCrazySpider` -> `cavernreborn:crazy_spider`, weight `1`, group `1 / 1`

This roster size remains exactly `4`.

## Why No Active Cavenia Spawning Was Added

This MVP does not add active Cavenia spawning because Reborn still has:

- no active `cavernreborn:cavenia` level
- no `dimension/cavenia.json`
- no `dimension_type/cavenia.json`
- no `CAVENIA_LOCATION`
- no `CAVENIA_LEVEL_KEY`
- no active Cavenia spawn host/service
- no active crazy natural spawning
- no normal `CAVERN` crazy biome modifiers
- no normal `CAVERN` crazy biome tags
- no crazy spawn placement registration

The policy is intentionally data-only and helper-only.

## How Future Active Spawn-Provider Work Can Use This Policy

A future active Cavenia spawn-provider implementation can reuse this non-runtime policy as the pinned source-confirmed baseline for:

- `monsterSpawn` defaults and clamping
- `crazySpawnChance` defaults and clamping
- chance-to-range mapping
- vertical scan range mapping
- normal roster composition
- crazy roster composition
- the boss-like nearby `ICavenicMob` gate after the chance roll

That future work still needs an actual Cavenia runtime host and should not activate through fake normal `CAVERN` biome resources.

## Explicit Non-Goals

- no active Cavenia dimension
- no active Cavenia spawning
- no active crazy natural spawning
- no fake normal `CAVERN` crazy spawns
- no spawn placements
- no biome modifiers or biome tags
- no Caveman entity implementation
- no `WorldProviderCavenia` or `WorldProviderCavern` port
