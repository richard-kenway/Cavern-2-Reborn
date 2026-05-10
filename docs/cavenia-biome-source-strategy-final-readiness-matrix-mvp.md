# Cavenia BIOME_SOURCE_STRATEGY Final Readiness Matrix MVP

This is the final selected-surface readiness matrix for `BIOME_SOURCE_STRATEGY`, not runtime activation.

This slice is consolidation/readiness-only and unregistered.

Selected surface remains `BIOME_SOURCE_STRATEGY`.

It builds on:

- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
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

## Exact Readiness Matrix Classes Added

- `CaveniaBiomeSourceStrategyReadinessSurface`
- `CaveniaBiomeSourceStrategyReadinessMatrixEntry`
- `CaveniaBiomeSourceStrategyReadinessMatrix`

## Exact Readiness Surface Enum Values

- `SELECTED_SURFACE_PLAN`
- `LEGACY_MAPPING_INVENTORY`
- `WEIGHTED_SELECTION_ALGORITHM`
- `BIOME_SELECTION_ADAPTER`
- `ADAPTER_CODEC_REGISTRATION_READINESS`
- `REGISTRY_LOOKUP_READINESS`

## Exact Readiness Matrix Entry Fields

`CaveniaBiomeSourceStrategyReadinessMatrixEntry` now exposes:

- `surface`
- `sourceContractName`
- `itemCount`
- `readinessLayerReady`
- `runtimeReady`
- `activationAllowedInThisSlice`
- `runtimeBiomeSourceReady`
- `codecRegistered`
- `registryLookupAccessReady`
- `blocker`

## Exact Matrix Helpers

`CaveniaBiomeSourceStrategyReadinessMatrix` now exposes:

- `entries()`
- `surfaces()`
- `entryFor(CaveniaBiomeSourceStrategyReadinessSurface)`
- `entryCount()`
- `totalReadinessItemCount()`
- `selectedSurface()`
- `selectedSurfaceIsBiomeSourceStrategy()`
- `allReadinessLayersReady()`
- `allRuntimeSurfacesBlocked()`
- `anyRuntimeReady()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `planStepCount()`
- `legacyMappingEntryCount()`
- `weightedSelectionEntryCount()`
- `adapterEntryCount()`
- `adapterCodecRegistrationRequirementCount()`
- `registryLookupRequirementCount()`
- `legacyBiomeTotalWeight()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `weightedSelectionAlgorithmReady()`
- `adapterShapeReady()`
- `codecRegistrationReadinessReady()`
- `registryLookupReadinessReady()`
- `finalRuntimeMappingReady()`
- `weightedSelectionRuntimeReady()`
- `adapterRuntimeReady()`
- `codecRegistrationRuntimeReady()`
- `registryLookupRuntimeReady()`
- `runtimeBiomeSourceReady()`
- `runtimeBiomeSourceRegistered()`
- `codecRegistered()`
- `registryLookupAccessReady()`
- `registryVerified()`
- `runtimeBiomeResolved()`
- `modernBiomeMappingReady()`
- `dimensionBindingReady()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Exact Per-Surface Item Counts

- `SELECTED_SURFACE_PLAN` -> `CaveniaBiomeSourceStrategyPlan`, count `8`
- `LEGACY_MAPPING_INVENTORY` -> `CaveniaLegacyToModernBiomeKeyMappings`, count `14`
- `WEIGHTED_SELECTION_ALGORITHM` -> `CaveniaWeightedBiomeSelectionAlgorithm`, count `14`
- `BIOME_SELECTION_ADAPTER` -> `CaveniaBiomeSelectionAdapterContract`, count `14`
- `ADAPTER_CODEC_REGISTRATION_READINESS` -> `CaveniaAdapterCodecRegistrationReadiness`, count `9`
- `REGISTRY_LOOKUP_READINESS` -> `CaveniaRegistryLookupReadiness`, count `9`

Exact total selected-surface readiness item count is `68`.

## Pinned Current Facts

- all six readiness layers are ready as contracts/data
- all runtime surfaces are blocked
- candidate inventory is ready
- candidate keys remain plain strings only
- weighted selection algorithm is ready as pure algorithm
- adapter shape is ready
- codec/registration readiness contract is ready
- registry lookup readiness contract is ready
- final runtime mapping is not ready
- weighted selection runtime is not ready
- adapter runtime is not ready
- codec implementation/registration is not ready
- registry lookup runtime is not ready
- registry lookup access is not ready
- registry verification is not ready
- runtime biome resolution is not ready
- runtime `BiomeSource` is not ready
- runtime biome-source registration is not ready
- dimension binding is not ready
- dimension JSON/type JSON are absent
- activation is not allowed in this slice
- Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged

## Relationship To Existing Contracts

This final selected-surface readiness matrix consolidates:

- `CaveniaRegistryLookupReadiness`
- `CaveniaAdapterCodecRegistrationReadiness`
- `CaveniaBiomeSelectionAdapterContract`
- `CaveniaWeightedBiomeSelectionAlgorithm`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Stays Inert

This slice is still not a `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no registry lookup access was added.

no biome holder/resource-key conversion was added.

no codec implementation was added.

no codec or registry entry was added.

It does not:

- use registry lookup access
- resolve runtime biome holders
- convert strings into runtime biome keys
- implement or register a codec
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
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active Cavenia worldgen resources

## Future Recommendation

The next slice may deliberately choose whether to start real runtime `BiomeSource` implementation or return to generator/dimension activation planning.

If choosing runtime `BiomeSource`, make it a separate explicit MVP with strict no-dimension-activation guardrails.

Real runtime `BiomeSource`, codec, registration and registry lookup access must remain separate explicit MVPs unless deliberately chosen.
