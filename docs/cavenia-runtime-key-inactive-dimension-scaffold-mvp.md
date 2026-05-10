# Cavenia Runtime Key / Inactive Dimension Scaffold MVP

This note documents the inert Cavenia identity scaffold that follows `docs/cavenia-active-foundation-technical-spike.md`.

It builds on:

- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-dimension-provider-foundation-boundary.md`
- `docs/cavenia-dimension-key-type-contract-boundary.md`
- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/caveman-cavenia-normal-roster-boundary.md`

The later pure non-runtime terrain-generator foundation note is now documented separately in:

- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`

The later pure non-runtime cave-carver policy note is now documented separately in:

- `docs/cavenia-cave-carver-policy-non-runtime-mvp.md`

The later pure non-runtime biome top/filter policy note is now documented separately in:

- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`

The later pure non-runtime VEINS/content policy note is now documented separately in:

- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`

The later pure non-runtime population policy note is now documented separately in:

- `docs/cavenia-population-policy-non-runtime-mvp.md`

The later non-registered active-generator technical scaffold note is now documented separately in:

- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`
- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`
- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`
- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`

## Exact Constants Added

The current repository now exposes inert future Cavenia identity markers:

- `CavernDimensions.CAVENIA_DIMENSION_ID = "cavernreborn:cavenia"`
- `CavernNeoForgeDimensions.CAVENIA_LOCATION`
- `CavernNeoForgeDimensions.CAVENIA_LEVEL_KEY`

Those constants exist only to pin the future modern identity:

- `cavernreborn:cavenia`

Legacy `dimensionId = -54` remains historical identity only. It is not recreated as a modern runtime id.

## What This MVP Actually Does

This slice adds an inert identity scaffold only.

It does not create an active runtime `ServerLevel`.

It does not add:

- `app-neoforge/src/main/resources/data/cavernreborn/dimension/cavenia.json`
- `app-neoforge/src/main/resources/data/cavernreborn/dimension_type/cavenia.json`
- active Cavenia worldgen resources
- active Cavenia biome-source resources
- active Cavenia configured or placed features
- active Cavenia carvers, noise settings, density functions or surface rules
- active access or teleport behavior
- active Cavenia spawning
- active crazy spawning
- fake normal `CAVERN` crazy spawning
- `cavernreborn:caveman`

This means:

- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning

So the correct current boundary is now:

- `CAVENIA_LOCATION` and `CAVENIA_LEVEL_KEY` exist as inert identity constants
- no active `cavernreborn:cavenia` runtime level exists
- no checked-in dimension JSON exists
- no checked-in dimension-type JSON exists

## Runtime Boundary

At runtime, `level.getServer().getLevel(CavernNeoForgeDimensions.CAVENIA_LEVEL_KEY)` must still resolve to `null`.

That means:

- no active world is loaded for `cavernreborn:cavenia`
- no travel path is exposed
- no worldgen path is exposed
- no natural spawning path is exposed

Current `CAVERN` behavior remains the only active checked-in dimension/runtime path.

## Related Spawn And Roster State

This MVP does not change the non-runtime spawn-provider policy.

`CaveniaSpawnProviderPolicy` still pins the future Cavenia-only spawn-provider inputs, and the source-literal nearby predicate wording stays:

- nearby `ICavenicMob` whose `isNonBoss()` returns `false`

`EntityCaveman -> deferred:caveman` also remains unchanged and intentional.

## Why The Scaffold Stops Here

The active-foundation technical spike already documented that `dimension/cavenia.json` and `dimension_type/cavenia.json` were still unsafe until the project decides:

- generator strategy
- biome source
- surface/top/filter behavior
- cave-carver mapping
- VEINS/content ordering
- lakes/falls/`cavenic_shroom` population integration
- safe entry/access behavior
- explicit spawn-provider disabled or deferred runtime state

This MVP therefore stops at inert identity constants and proof that no runtime dimension was created accidentally.

## Explicit Non-Goals

- no active Cavenia dimension implementation
- no dimension JSON implementation
- no dimension-type JSON implementation
- no terrain generator implementation
- no biome provider implementation
- no cave carver implementation
- no VEINS/content implementation
- no population implementation
- no access or teleport implementation
- no spawn-provider implementation
- no Caveman implementation

## Next Future Work

The next future active work still requires:

- generator and type decisions
- biome-source decisions
- safe access and return decisions
- explicit deferred spawn-provider behavior inside any future active dimension JSON

The immediate non-runtime terrain follow-up is now documented in:

- `docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md`

The later non-registered active-generator scaffold follow-up is now documented in:

- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
This inert scaffold is only the identity floor for those later slices.
