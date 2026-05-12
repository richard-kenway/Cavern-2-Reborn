# Cavenia Weighted Biome Selection Algorithm / Pure Non-Runtime MVP

This is a pure non-runtime weighted biome selection algorithm, not runtime activation.

Selected surface remains `BIOME_SOURCE_STRATEGY`.

Exact weighted selection classes added:
- `CaveniaWeightedBiomeSelectionEntry`
- `CaveniaWeightedBiomeSelectionResult`
- `CaveniaWeightedBiomeSelectionAlgorithm`

Exact selection entry fields:
- `legacyBiomeName`
- `candidateModernBiomeKey`
- `weight`
- `inclusiveStartWeight`
- `exclusiveEndWeight`
- `candidateInventoryOnly`
- `finalRuntimeMapping`
- `registryVerified`
- `runtimeReady`

Exact selection result fields:
- `inputValue`
- `normalizedWeightValue`
- `legacyBiomeName`
- `candidateModernBiomeKey`
- `weight`
- `finalRuntimeMapping`
- `registryVerified`
- `runtimeReady`

Exact algorithm helpers:
- `entries()`
- `legacyBiomeNames()`
- `candidateModernBiomeKeys()`
- `entryForLegacyBiomeName(String)`
- `entryForWeightValue(int)`
- `selectByWeightValue(int)`
- `entryCount()`
- `totalWeight()`
- `firstWeightValue()`
- `lastWeightValue()`
- `allEntriesHavePositiveWeights()`
- `allWeightRangesContiguous()`
- `allLegacyBiomesHaveCandidateKeys()`
- `candidateInventoryReady()`
- `weightedSelectionAlgorithmReady()`
- `weightedSelectionAlgorithmRuntimeReady()`
- `anyFinalRuntimeMapping()`
- `anyRegistryVerified()`
- `runtimeBiomeSourceReady()`
- `codecRegistered()`
- `registryLookupAccessReady()`
- `modernBiomeMappingReady()`
- `planIsNonRuntime()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

Pinned facts:
- exact legacy entry count `14`
- exact total weight `675`
- every legacy biome has one candidate key
- candidate inventory is ready
- weighted selection algorithm inventory is ready
- weighted selection runtime is not ready
- final runtime mapping is not ready
- registry verification is not ready
- runtime `BiomeSource` is not ready
- codec/registration is not ready
- registry lookup access is not ready
- `CaveniaBiomeSourceStrategyPlan.modernBiomeMappingReady()` remains false
- activation is not allowed in this slice
- Cavenia cannot activate now
- dimension JSON/type JSON are absent
- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active Cavenia worldgen resources
- `EntityCaveman -> deferred:caveman` remains unchanged

Selection uses normalized `0..674` values with inclusive start / exclusive end ranges.

Representative wrapping behavior:
- `0` first entry
- `674` last entry
- `675` first entry
- `-1` last entry

Relationship to:
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

Why this stays inert:
- this is still not a `BiomeSource`
- this is still not a `ChunkGenerator`
- no codec or registry entry was added
- no registry lookup access was added
- no dimension JSON/type JSON was added
- no active generator was added
- no active Cavenia runtime level was added
- no active Cavenia access or teleport was added
- no active Cavenia spawning was added
- no active Cavenia worldgen resources were added

Cross-links:
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

Future recommendation:
- the unregistered runtime-biome-source shape/adapter contract follow-up is now documented in `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- the adapter codec/registration readiness follow-up is now documented in `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- the registry lookup readiness follow-up is now documented in `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- the final selected-surface readiness-matrix follow-up is now documented in `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- the runtime-biome-source first-implementation decision/guardrails follow-up is now documented in `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- the unregistered runtime-biome-source skeleton follow-up is now documented in `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- the runtime-biome-source codec/holder/registry decision follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`
- later readiness-only slices can define registry lookup readiness contracts for that adapter, but must still avoid actual registry lookup access, codec registration and dimension activation unless explicitly chosen
- runtime `BiomeSource`, codec, registration and registry lookup access must remain separate explicit MVPs


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
