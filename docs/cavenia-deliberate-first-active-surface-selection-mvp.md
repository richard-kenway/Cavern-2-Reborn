# Cavenia Deliberate First Active Surface Selection MVP

This note documents a decision-only first-active-surface selection layer for the current blocked Cavenia activation state.

It builds on:

- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-worldgen-resource-host-split-contracts-mvp.md`
- `docs/cavenia-dimension-access-spawn-split-contracts-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-generator-activation-readiness-host-contracts-mvp.md`
- `docs/cavenia-generator-runtime-contracts-non-registered-mvp.md`
- `docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md`
- `docs/cavenia-active-generator-registration-inert-boundary-mvp.md`
- `docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md`
- `docs/cavenia-active-generator-technical-scaffold-mvp.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later narrow non-runtime biome-source strategy plan note is now documented separately in:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`

The later legacy-to-modern biome-key mapping inventory note is now documented separately in:

- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`

## What This MVP Adds

Current Reborn now has three decision-only app-side selection types:

- `CaveniaFirstActiveSurfaceCandidate`
- `CaveniaFirstActiveSurfaceSelectionReason`
- `CaveniaFirstActiveSurfaceSelection`

They aggregate and summarize:

- `CaveniaActivationReadinessMatrix`
- `CaveniaActivationSurface`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaGeneratorHostContracts`
- `CaveniaDimensionResourceContracts`
- `CaveniaAccessTravelContracts`
- `CaveniaSpawnHostContracts`
- `CaveniaWorldgenResourceContracts`
- `CaveniaGeneratorRegistrationBoundary`

This is decision-only, not runtime activation.

## Exact Selected Future Activation Surface

The exact selected future activation surface is `BIOME_SOURCE_STRATEGY`.

## Exact Candidate Order And Selection Rank

The candidate order still matches `CaveniaActivationReadinessMatrix.surfaces()`:

1. `GENERATOR_HOST`
2. `BIOME_SOURCE_STRATEGY`
3. `DIMENSION_RESOURCE`
4. `ACCESS_TRAVEL`
5. `SPAWN_HOST`
6. `WORLDGEN_RESOURCE`

The exact selection ranking is:

1. `BIOME_SOURCE_STRATEGY`
2. `GENERATOR_HOST`
3. `DIMENSION_RESOURCE`
4. `WORLDGEN_RESOURCE`
5. `ACCESS_TRAVEL`
6. `SPAWN_HOST`

## Exact Reason Enum Values

`CaveniaFirstActiveSurfaceSelectionReason` now exposes:

- `PREREQUISITE_FOR_GENERATOR_HOST`
- `PREREQUISITE_FOR_DIMENSION_BINDING`
- `LOWER_RUNTIME_SIDE_EFFECT_RISK`
- `DOES_NOT_REQUIRE_DIMENSION_JSON_YET`
- `KEEPS_ACCESS_AND_SPAWN_BLOCKED`
- `REQUIRES_FUTURE_EXPLICIT_RUNTIME_BIOME_SOURCE_MVP`

## Exact Candidate Fields

`CaveniaFirstActiveSurfaceCandidate` now exposes:

- `surface`
- `requirementCount`
- `selected`
- `selectionRank`
- `activationAllowedInThisSlice`
- `remainsBlocked`
- `sourceContractName`
- `blocker`
- `reasons`

The exact source contract names still match the readiness matrix:

- `GENERATOR_HOST` -> `CaveniaGeneratorHostContracts`
- `BIOME_SOURCE_STRATEGY` -> `CaveniaBiomeSourceStrategyContracts`
- `DIMENSION_RESOURCE` -> `CaveniaDimensionResourceContracts`
- `ACCESS_TRAVEL` -> `CaveniaAccessTravelContracts`
- `SPAWN_HOST` -> `CaveniaSpawnHostContracts`
- `WORLDGEN_RESOURCE` -> `CaveniaWorldgenResourceContracts`

## Exact Selected-Surface Reasons

The selected `BIOME_SOURCE_STRATEGY` candidate is pinned to these exact reasons:

- `PREREQUISITE_FOR_GENERATOR_HOST`
- `PREREQUISITE_FOR_DIMENSION_BINDING`
- `LOWER_RUNTIME_SIDE_EFFECT_RISK`
- `DOES_NOT_REQUIRE_DIMENSION_JSON_YET`
- `KEEPS_ACCESS_AND_SPAWN_BLOCKED`
- `REQUIRES_FUTURE_EXPLICIT_RUNTIME_BIOME_SOURCE_MVP`

So the first future active surface is chosen deliberately because:

- it is a prerequisite for a real generator host
- it is a prerequisite for real dimension binding
- it has lower runtime side-effect risk than starting with dimension resources, travel, spawning or worldgen resources
- it does not require `dimension/cavenia.json` yet
- it keeps access and spawn blocked
- it still requires a later explicit runtime `BiomeSource` MVP with codec, registry entry, registry lookup access and modern biome-key mapping

## Current Inert Status

All candidates remain blocked.

Activation is not allowed in this slice.

activation is not allowed in this slice.

Cavenia cannot activate now.

The readiness matrix total requirement count remains `46`.

The readiness matrix blocked requirement count remains `46`.

Biome source runtime is not ready.

dimension JSON/type JSON are absent.

Access cannot teleport.

Spawn host cannot spawn.

Worldgen cannot affect worldgen.

`EntityCaveman -> deferred:caveman` remains unchanged.

## Why This Is Still Not Runtime Activation

This decision-only selection layer is not a `ChunkGenerator`.

This decision-only selection layer is not a `BiomeSource`.

no codec or registry entry was added.

It does not:

- add or write `dimension/cavenia.json`
- add or write `dimension_type/cavenia.json`
- add or write worldgen resources
- create a runtime generator
- create a runtime biome source
- create a `ServerLevel`
- generate chunks
- mutate world or chunk state
- create access or teleport hooks
- create spawn hooks
- accept `CAVERN` resource reuse as fake Cavenia parity

## Why Runtime Activation Still Stays Blocked

Current Reborn still keeps:

- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia generator
- no active Cavenia biome source
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning
- no active configured carver resources
- no active configured feature resources
- no active placed feature resources
- no active biome modifier resources
- no active biome tag resources
- no CAVERN resource reuse is accepted as Cavenia parity

## Future Recommendation

The later non-runtime and unregistered follow-ups are now documented in:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`
- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`

The next implementation slice should still stay inside `BIOME_SOURCE_STRATEGY`, but it can now move to codec/registration readiness contracts for the adapter without becoming runtime.

It should still avoid dimension JSON, active level, access, spawning and worldgen resources unless explicitly chosen later.


The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.

The runtime-biome-source real-subclass go/no-go decision follow-up is now documented in `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`.

The guarded unregistered real biome-source subclass stub follow-up is now documented in `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`.

The runtime-biome-source codec-method-shape stub follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
The runtime-biome-source holder/resource-key conversion readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md`.
The runtime-biome-source fallback-policy readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md`.
The runtime-biome-source possible-biomes readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md`.

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.

The readiness-chain consolidation / next-decision follow-up is now documented in `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md`.
