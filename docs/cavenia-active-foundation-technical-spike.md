# Cavenia Active Foundation Technical Spike

This note turns the broader readiness plan into concrete technical decisions for the first future active Cavenia foundation slice.

It builds directly on:

- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-dimension-provider-foundation-boundary.md`
- `docs/cavenia-dimension-key-type-contract-boundary.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-veins-content-pipeline-contract-boundary.md`
- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`
- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`
- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`
- `docs/cavenia-mirage-entry-access-contract-boundary.md`
- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/caveman-cavenia-normal-roster-boundary.md`

The follow-up inert identity implementation is now documented separately in:

- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

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

The later inert app-side runtime prototype note is now documented separately in:

- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`

The later inert generator-registration boundary note is now documented separately in:

- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`

The later unregistered generator/biome-selection skeleton note is now documented separately in:

- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`

The later non-registered runtime-contract note is now documented separately in:

- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`

The later activation-readiness host-contract note is now documented separately in:

- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`

The later generator-host / biome-source-strategy split-contract note is now documented separately in:

- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`

The later dimension-resource / access-travel / spawn-host split-contract note is now documented separately in:

- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`

The later worldgen-resource split-contract note is now documented separately in:

- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`

The later final inert readiness-matrix note is now documented separately in:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`
- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`

This spike still does not imply active `cavernreborn:cavenia`, `dimension/cavenia.json`, `dimension_type/cavenia.json`, active Cavenia spawning, active crazy spawning, fake normal `CAVERN` crazy spawning or a `cavernreborn:caveman` entity.

## Spike Decision

The later decision-only surface-selection note now chooses the first future activation surface explicitly:

- `BIOME_SOURCE_STRATEGY`

This technical spike still remains a preparatory decision layer for the broader foundation, but it is no longer the latest first-slice selection note by itself.

The current narrow recommendation is:

- start with a non-runtime `BIOME_SOURCE_STRATEGY` slice
- keep `cavernreborn:cavenia` unresolved at runtime
- keep `dimension/cavenia.json` absent
- keep `dimension_type/cavenia.json` absent
- keep entry/access, spawning and worldgen inactive
- defer runtime `BiomeSource`, codec, registration and registry lookup access to later explicit MVPs

That narrow non-runtime follow-up is now documented in:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`

## Minimum Safe Active Scaffold

The minimum safe active scaffold is smaller than a real active dimension.

It is safe to add:

- `CAVENIA_LOCATION` as an inert future identity constant for `cavernreborn:cavenia`
- `CAVENIA_LEVEL_KEY` as an inert future level key constant for `cavernreborn:cavenia`

Those constants are safe only if the same slice also proves:

- no checked-in `dimension/cavenia.json`
- no checked-in `dimension_type/cavenia.json`
- no runtime `ServerLevel` resolves for `cavernreborn:cavenia`
- no player access path exists
- no active Cavenia spawning exists

It is not yet safe to add:

- `dimension/cavenia.json`
- `dimension_type/cavenia.json`

`dimension/cavenia.json` is not safe yet because it would turn the identity into a checked-in runtime world target before these decisions are locked:

- terrain shape
- biome source
- generator strategy
- surface/top/filter behavior
- VEINS/content order
- population behavior
- safe arrival/access state

`dimension_type/cavenia.json` is not safe yet because these shape-defining values remain unresolved:

- final `height`
- final `logical_height`
- cave-like ambient/effects strategy
- sleep and respawn semantics
- monster spawn light semantics

So the minimum safe scaffold is:

- constants only
- docs/tests only
- no active dimension resources

## Likely Modern Dimension / Type Shape

The likely future identity remains:

- `cavernreborn:cavenia`

Legacy `dimensionId = -54` remains historical identity only and should not be recreated as a modern runtime id.

The likely modern type shape still needs to preserve the source-confirmed cave-provider constraints:

- `worldHeight = 128` as the key vertical contract
- no skylight / cave-like presentation
- non-surface behavior
- no normal respawn semantics
- dark cave-like effects rather than overworld sky behavior

The likely future `dimension_type` direction is therefore:

- `has_skylight: false`
- cave-like `natural` semantics rather than surface-world semantics
- `bed_works` and `respawn_anchor_works` chosen from the old no-normal-respawn model rather than copied from current `CAVERN`
- `height` and `logical_height` centered on the legacy `128` contract rather than current `CAVERN` `192`

Still unresolved:

- final ambient-light choice
- final effects id
- final monster light rules
- exact sleep semantics
- exact respawn-anchor semantics

## Recommended Generator Strategy

Three directions were considered.

### Custom chunk generator

Pros:

- strongest parity for full stone fill
- strongest parity for `worldHeight = 128`
- strongest parity for bedrock caps
- strongest parity for top/filter replacement order
- strongest parity for VEINS mutation order
- strongest parity for `MapGenCaveniaCaves`

Cons:

- highest implementation cost
- more code surface before the first visible runtime slice

### Data-driven approximation

Pros:

- smallest initial code surface
- easiest resource-only check-in path

Cons:

- weak fit for generator-owned terrain ordering
- weak fit for `MapGenCaveniaCaves` widened caves and gravel/water/air replacement
- weak fit for generator-side VEINS ordering after carving and top/filter replacement
- likely to imply a false parity story if checked in too early

### Hybrid custom generator plus data-driven features

Pros:

- preserves the generator-owned terrain, top/filter, carve and VEINS order where it matters
- still leaves later biome/features/population pieces open to data-driven resources where honest
- gives a narrow path for future active runtime slices without pretending pure JSON parity

Cons:

- still requires custom runtime code before any honest dimension JSON lands

### Recommendation

The recommended direction is:

- hybrid custom generator plus data-driven features

The future active Cavenia path should keep terrain, cave carving, top/filter replacement and VEINS/content ordering on a custom runtime path, while treating later population/features as data-driven only where that does not falsify the source-confirmed generator contract.

Pure data-driven approximation is not recommended as the primary foundation because the source-confirmed terrain order is too generator-owned.

The later pure `core` biome/top/filter follow-up now pins the shipped weighted roster and default top/filter block mapping separately in:

- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`

The later pure `core` VEINS/content follow-up now pins the shipped default content roster and ordering separately in:

- `docs/cavenia-veins-content-policy-non-runtime-mvp.md`

## Decisions Required Before Any Active Dimension JSON

Before any checked-in `dimension/cavenia.json` or `dimension_type/cavenia.json`, the project still needs explicit decisions for:

- terrain shape
- biome source
- height and logical height
- cave carving strategy
- surface/top/filter behavior
- VEINS/content integration
- lakes/falls/`cavenic_shroom` population integration
- safe entry/access behavior
- explicit spawn-provider disabled or deferred state

The active dimension JSON should not land before the project can explain what happens if a developer or command path reaches that dimension manually.

## What The First Active MVP Must Explicitly Exclude

The first active scaffold must still exclude:

- no player access or teleport
- no Mirage Book
- no portal
- no GUI or packets
- no active natural spawning
- no active crazy spawning
- no Caveman implementation
- no fake reuse of current `CAVERN` ore or `cavenic_shroom` distributions as Cavenia parity

It must also keep:

- no fake normal `CAVERN` crazy spawning
- no fake normal `CAVERN` crazy spawn placements
- no fake normal `CAVERN` crazy biome modifiers
- no fake normal `CAVERN` crazy biome tags
- `EntityCaveman -> deferred:caveman`

The source-literal spawn-provider wording must remain:

- nearby `ICavenicMob` whose `isNonBoss()` returns `false`

## Acceptance Criteria For The First Active Scaffold

The first active scaffold must:

- not break current `CAVERN`
- not expose broken travel
- not enable Cavenia spawning
- not enable active crazy spawning
- not enable fake normal `CAVERN` crazy spawning
- document any intentional approximation explicitly
- keep runtime smoke proving inactive access and spawning boundaries intact

If the scaffold adds `CAVENIA_LOCATION` and `CAVENIA_LEVEL_KEY`, it must still prove:

- `cavernreborn:cavenia` does not resolve as an active runtime level
- `dimension/cavenia.json` is absent
- `dimension_type/cavenia.json` is absent

## Recommended Next Implementation Slice

The recommended next implementation slice after this spike is:

- `Cavenia Runtime Key / Inactive Dimension Scaffold MVP`

That slice should be limited to inert identity constants, boundary-doc updates and runtime smoke/tests that prove the identity exists in code but still does not create a real Cavenia runtime path.

The later unregistered adapter follow-up is now documented in:

- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`

The later adapter codec/registration readiness follow-up is now documented in:

- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
