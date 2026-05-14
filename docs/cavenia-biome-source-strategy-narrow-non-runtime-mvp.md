# Cavenia Biome Source Strategy Narrow Non-Runtime MVP

This note documents a narrow non-runtime biome-source strategy plan for the selected `BIOME_SOURCE_STRATEGY` surface.

It builds on:

- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

The later legacy-to-modern biome-key mapping inventory note is now documented separately in:

- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`

## What This MVP Adds

Current Reborn now has three narrow non-runtime biome-source strategy plan types:

- `CaveniaBiomeSourceStrategyPlanStep`
- `CaveniaBiomeSourceStrategyPlanEntry`
- `CaveniaBiomeSourceStrategyPlan`

They aggregate and summarize:

- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaGeneratorRegistrationBoundary`

This is a narrow non-runtime biome-source strategy plan, not runtime activation.

## Selected Surface Still Stays Narrow And Blocked

The selected surface remains `BIOME_SOURCE_STRATEGY`.

activation is not allowed in this slice.

Cavenia cannot activate now.

## Exact Plan Step Enum Values

`CaveniaBiomeSourceStrategyPlanStep` now exposes this exact order:

1. `CONFIRM_SELECTED_SURFACE`
2. `PIN_LEGACY_WEIGHTED_INPUTS`
3. `PIN_LEGACY_TOP_BLOCK_INPUTS`
4. `INVENTORY_LEGACY_TO_MODERN_BIOME_MAPPING`
5. `DEFER_WEIGHTED_SELECTION_ALGORITHM`
6. `DEFER_RUNTIME_BIOME_SOURCE`
7. `DEFER_CODEC_AND_REGISTRATION`
8. `DEFER_REGISTRY_LOOKUP_ACCESS`

## Exact Plan Entry Fields

`CaveniaBiomeSourceStrategyPlanEntry` now exposes:

- `step`
- `requirement`
- `sourceContractName`
- `selectedSurfaceStep`
- `inputPinned`
- `runtimeReady`
- `activationAllowedInThisSlice`
- `blocker`

Every plan entry remains blocked and non-runtime:

- `runtimeReady = false`
- `activationAllowedInThisSlice = false`

## Exact Legacy Inputs Pinned

The exact legacy biome entry count remains:

- `14`

The exact legacy total weight remains:

- `675`

The exact source of legacy weighted inputs is:

- `CaveniaBiomeTopFilterPolicy`

The exact source of legacy top-block inputs is:

- `CaveniaBiomeTopFilterPolicy`

So this slice now pins the source-confirmed non-runtime biome-source strategy inputs without inventing any modern mapping or runtime selector.

## Exact Current Narrow Boundaries

modern biome-key mapping is not ready.

legacy-to-modern biome-key candidate inventory is ready, but final runtime mapping is not ready.

Weighted selection algorithm is not ready.

runtime `BiomeSource` is not ready.

codec/registration is not ready.

registry lookup access is not ready.

dimension JSON/type JSON are absent.

`EntityCaveman -> deferred:caveman` remains unchanged.

## Why This Is Still Non-Runtime

This slice is still not a `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no codec or registry entry was added.

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
- no active Cavenia worldgen resources

## Relationship To Existing Contracts

This narrow non-runtime plan sits directly on top of:

- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaBiomeSelectionSkeleton`
- `CaveniaBiomeTopFilterPolicy`
- `CaveniaGeneratorRegistrationBoundary`

The plan deliberately uses:

- `CaveniaFirstActiveSurfaceSelection` to confirm the selected future surface still stays `BIOME_SOURCE_STRATEGY`
- `CaveniaBiomeTopFilterPolicy` to pin the legacy weighted biome roster and top-block inputs
- `CaveniaBiomeSelectionSkeleton` to keep legacy biome names inventory-only
- `CaveniaBiomeSourceStrategyContracts` to keep weighted selection, runtime `BiomeSource`, codec/registration and registry lookup access blocked
- `CaveniaActivationReadinessMatrix` to keep dimension JSON/type JSON absent and total blocked requirement count pinned

## Future Recommendation

The later legacy-to-modern biome-key mapping inventory follow-up is now documented in:

- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`

The later pure selector follow-up is now documented in:

- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`

The later unregistered adapter follow-up is now documented in:

- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`

The later adapter codec/registration readiness follow-up is now documented in:

- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`
- `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`

The next runtime-adjacent slice may define registry lookup readiness contracts for that unregistered adapter, but should still avoid actual registry lookup access, codec registration and runtime activation unless explicitly changing the boundary.

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
