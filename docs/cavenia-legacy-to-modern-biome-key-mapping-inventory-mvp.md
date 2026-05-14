# Cavenia Legacy-To-Modern Biome-Key Mapping Inventory MVP

This note documents a non-runtime candidate mapping inventory for the selected `BIOME_SOURCE_STRATEGY` surface.

It builds on:

- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

## What This MVP Adds

Current Reborn now has three non-runtime app-side mapping-inventory types:

- `CaveniaBiomeKeyMappingKind`
- `CaveniaLegacyToModernBiomeKeyMappingEntry`
- `CaveniaLegacyToModernBiomeKeyMappings`

They aggregate and summarize:

- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

This is a non-runtime candidate mapping inventory, not runtime activation.

The selected surface remains `BIOME_SOURCE_STRATEGY`.

## Exact Mapping Inventory Classes Added

- `CaveniaBiomeKeyMappingKind`
- `CaveniaLegacyToModernBiomeKeyMappingEntry`
- `CaveniaLegacyToModernBiomeKeyMappings`

## Exact Mapping Kind Enum Values

`CaveniaBiomeKeyMappingKind` now exposes:

- `EXACT_OR_DIRECT`
- `RENAMED`
- `COLLAPSED_LEGACY_VARIANT`
- `INVENTORY_ONLY`

## Exact Mapping Entry Fields

`CaveniaLegacyToModernBiomeKeyMappingEntry` now exposes:

- `legacyBiomeName`
- `candidateModernBiomeKey`
- `mappingKind`
- `candidateInventoried`
- `finalRuntimeMapping`
- `registryVerified`
- `runtimeReady`
- `rationale`

Every entry remains inventory-only:

- `candidateInventoried = true`
- `finalRuntimeMapping = false`
- `registryVerified = false`
- `runtimeReady = false`

## Exact 14 Legacy-To-Modern Candidate Mapping Rows

The exact legacy entry count remains `14`.

The exact legacy total weight remains `675`.

Every legacy biome has one candidate key:

| Legacy biome | Candidate modern biome key | Mapping kind |
| --- | --- | --- |
| `OCEAN` | `minecraft:ocean` | `EXACT_OR_DIRECT` |
| `PLAINS` | `minecraft:plains` | `EXACT_OR_DIRECT` |
| `DESERT` | `minecraft:desert` | `EXACT_OR_DIRECT` |
| `DESERT_HILLS` | `minecraft:desert` | `COLLAPSED_LEGACY_VARIANT` |
| `FOREST` | `minecraft:forest` | `EXACT_OR_DIRECT` |
| `FOREST_HILLS` | `minecraft:forest` | `COLLAPSED_LEGACY_VARIANT` |
| `TAIGA` | `minecraft:taiga` | `EXACT_OR_DIRECT` |
| `TAIGA_HILLS` | `minecraft:taiga` | `COLLAPSED_LEGACY_VARIANT` |
| `JUNGLE` | `minecraft:jungle` | `EXACT_OR_DIRECT` |
| `JUNGLE_HILLS` | `minecraft:jungle` | `COLLAPSED_LEGACY_VARIANT` |
| `SWAMPLAND` | `minecraft:swamp` | `RENAMED` |
| `EXTREME_HILLS` | `minecraft:windswept_hills` | `RENAMED` |
| `SAVANNA` | `minecraft:savanna` | `EXACT_OR_DIRECT` |
| `MESA` | `minecraft:badlands` | `RENAMED` |

The exact source of legacy weighted inputs remains `CaveniaBiomeTopFilterPolicy`.

The exact source of legacy top-block inputs remains `CaveniaBiomeTopFilterPolicy`.

## Exact Current Narrow Boundaries

Every legacy biome has one candidate key.

candidate inventory is ready.

final runtime mapping is not ready.

registry verification is not ready.

runtime `BiomeSource` is not ready.

codec/registration is not ready.

registry lookup access is not ready.

`CaveniaBiomeSourceStrategyPlan.modernBiomeMappingReady()` remains false.

activation is not allowed in this slice.

Cavenia cannot activate now.

dimension JSON/type JSON are absent.

`EntityCaveman -> deferred:caveman` remains unchanged.

## Why This Is Still Non-Runtime

This slice is still not a `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no codec or registry entry was added.

no registry lookup access was added.

It does not:

- create biome holders
- use registry lookup APIs
- create a runtime weighted selector
- create a runtime `BiomeSource`
- add runtime biome-source registration
- add generator registration
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
- no active Cavenia biome source
- no active Cavenia generator
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active Cavenia biome modifiers
- no active Cavenia biome tags
- no active Cavenia worldgen resources

## Relationship To Existing Contracts

This inventory sits directly on top of:

- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

The inventory deliberately uses:

- `CaveniaBiomeSourceStrategyPlan` to keep the selected surface pinned to `BIOME_SOURCE_STRATEGY`
- `CaveniaBiomeTopFilterPolicy` to pin the exact `14` legacy biome names and the exact total weight `675`
- `CaveniaBiomeSelectionSkeleton` to keep the legacy list source-backed while runtime biome selection remains absent
- `CaveniaBiomeSourceStrategyContracts` to keep runtime `BiomeSource`, codec/registration and registry lookup access blocked
- `CaveniaActivationReadinessMatrix` to keep dimension JSON/type JSON absent and the blocked requirement count pinned

## Future Recommendation

The later pure selector and unregistered adapter follow-ups are now documented in:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`

The next slice can define codec/registration readiness contracts for that adapter, but should still avoid actual codec registration, registry lookup access and runtime activation unless explicitly changing the boundary.

Runtime `BiomeSource`, codec, registration and registry lookup access must remain separate explicit MVPs.


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

The selector-input algorithm consolidation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md`.
The selector-to-weighted-candidate bridge go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md`.

The selector-to-weighted-candidate bridge readiness follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md`.

The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
