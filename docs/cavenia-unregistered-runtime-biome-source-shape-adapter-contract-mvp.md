# Cavenia Unregistered Runtime Biome-Source Shape / Adapter Contract MVP

This is an unregistered runtime-biome-source shape / adapter contract, not runtime activation.

Selected surface remains `BIOME_SOURCE_STRATEGY`.

It builds on:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`
- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`

## Exact Adapter Classes Added

- `CaveniaBiomeSelectionAdapterQuery`
- `CaveniaBiomeSelectionAdapterResult`
- `CaveniaBiomeSelectionAdapterContract`

## Exact Adapter Query Fields

`CaveniaBiomeSelectionAdapterQuery` now exposes:

- `x`
- `y`
- `z`
- `weightValue`
- `registryLookupAvailable`
- `runtimeBiomeSourceAvailable`

The provided helper paths keep `registryLookupAvailable = false` and `runtimeBiomeSourceAvailable = false`.

## Exact Adapter Result Fields

`CaveniaBiomeSelectionAdapterResult` now exposes:

- `inputWeightValue`
- `normalizedWeightValue`
- `legacyBiomeName`
- `candidateModernBiomeKey`
- `weight`
- `candidateOnly`
- `finalRuntimeMapping`
- `registryVerified`
- `runtimeReady`
- `runtimeBiomeSourceResult`

## Exact Adapter Helpers

`CaveniaBiomeSelectionAdapterContract` now exposes:

- `queryForWeightValue(int)`
- `selectCandidate(CaveniaBiomeSelectionAdapterQuery)`
- `selectCandidateByWeightValue(int)`
- `entryCount()`
- `totalWeight()`
- `firstWeightValue()`
- `lastWeightValue()`
- `adapterShapeReady()`
- `adapterRuntimeReady()`
- `consumesWeightedSelector()`
- `weightedSelectionAlgorithmReady()`
- `weightedSelectionAlgorithmRuntimeReady()`
- `candidateInventoryReady()`
- `finalRuntimeMappingReady()`
- `registryLookupAccessReady()`
- `runtimeBiomeSourceReady()`
- `runtimeBiomeSourceRegistered()`
- `codecRegistered()`
- `modernBiomeMappingReady()`
- `planIsNonRuntime()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Current Facts

- adapter shape is ready
- adapter runtime is not ready
- adapter consumes `CaveniaWeightedBiomeSelectionAlgorithm`
- exact legacy entry count remains `14`
- exact total weight remains `675`
- weighted selector remains pure non-runtime
- final runtime mapping is not ready
- registry verification is not ready
- runtime `BiomeSource` is not ready
- runtime biome-source registration is not ready
- codec/registration is not ready
- registry lookup access is not ready
- `CaveniaBiomeSourceStrategyPlan.modernBiomeMappingReady()` remains false
- activation is not allowed in this slice
- Cavenia cannot activate now
- dimension JSON/type JSON are absent
- `EntityCaveman -> deferred:caveman` remains unchanged

Selection uses the existing pure weighted selector and does not use `x`, `y`, `z`, world seed, noise, registry lookup or biome holders.

Representative wrapping behavior:

- `0` first entry candidate
- `675` first entry candidate
- `-1` last entry candidate

## Relationship To Existing Contracts

This adapter sits directly on top of:

- `CaveniaWeightedBiomeSelectionAlgorithm`
- `CaveniaWeightedBiomeSelectionEntry`
- `CaveniaWeightedBiomeSelectionResult`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Stays Inert

This slice is still not a `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no codec or registry entry was added.

no registry lookup access was added.

It does not:

- create biome holders
- use registry lookup APIs
- create runtime biome-source registration
- create a runtime `BiomeSource`
- create a runtime `ChunkGenerator`
- add `dimension/cavenia.json`
- add `dimension_type/cavenia.json`
- add Cavenia worldgen resources
- create an active Cavenia level
- create active access or teleport
- create active Cavenia spawning

Current Reborn therefore still keeps:

- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia configured carver
- no active Cavenia configured or placed features
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active crazy spawning
- no fake normal `CAVERN` crazy spawning
- no active Cavenia biome modifiers
- no active Cavenia biome tags
- no active Cavenia worldgen resources
- no CAVERN resource reuse is accepted as Cavenia parity

## Future Recommendation

The adapter codec/registration readiness follow-up is now documented in `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`.

The later registry lookup readiness follow-up is now documented in `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`.

The final selected-surface readiness-matrix follow-up is now documented in `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`.

The runtime-biome-source first-implementation decision/guardrails follow-up is now documented in `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`.

The next slice may define registry lookup readiness contracts for the adapter, but must still avoid actual registry lookup access, codec registration and dimension activation unless explicitly chosen.

Real runtime `BiomeSource`, codec, registration and registry lookup access must remain separate explicit MVPs.

The unregistered runtime-biome-source skeleton follow-up is now documented in `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`.

The runtime-biome-source codec/holder/registry decision follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`.


The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.

The runtime-biome-source real-subclass go/no-go decision follow-up is now documented in `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`.

The guarded unregistered real biome-source subclass stub follow-up is now documented in `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`.

The runtime-biome-source codec-method-shape stub follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
The runtime-biome-source holder/resource-key conversion readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md`.
The runtime-biome-source fallback-policy readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md`.
The runtime-biome-source possible-biomes readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md`.

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.

The readiness-chain consolidation / next-decision follow-up is now documented in `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md`.

The selector-input derivation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md`.

The selector-input derivation readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md`.

The selector-input derivation implementation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md`.

The pure non-runtime selector-input derivation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md`.
