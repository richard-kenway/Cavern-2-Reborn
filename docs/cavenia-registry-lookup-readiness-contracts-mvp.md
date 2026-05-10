# Cavenia Registry Lookup Readiness Contracts MVP

This is registry lookup readiness only, not runtime activation.

This slice is readiness-only and unregistered.

Selected surface remains `BIOME_SOURCE_STRATEGY`.

It builds on:

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

## Exact Readiness Classes Added

- `CaveniaRegistryLookupRequirement`
- `CaveniaRegistryLookupRequirementContract`
- `CaveniaRegistryLookupReadiness`

## Exact Requirement Enum Values

- `CANDIDATE_KEYS_INVENTORIED`
- `CANDIDATE_KEYS_STILL_STRING_ONLY`
- `REGISTRY_ACCESS_SOURCE_DECISION`
- `BIOME_REGISTRY_REFERENCE`
- `RESOURCE_KEY_CONVERSION`
- `HOLDER_RESOLUTION`
- `MISSING_BIOME_FALLBACK_DECISION`
- `RUNTIME_LOOKUP_CONTEXT`
- `ADAPTER_RESULT_TO_RUNTIME_BIOME`

## Exact Requirement Contract Fields

`CaveniaRegistryLookupRequirementContract` now exposes:

- `requirement`
- `sourceContractName`
- `prerequisiteSatisfied`
- `readyForRuntime`
- `registryLookupAvailable`
- `registryVerified`
- `runtimeBiomeResolved`
- `activationAllowedInThisSlice`
- `blocker`

## Exact Readiness Helpers

`CaveniaRegistryLookupReadiness` now exposes:

- `contracts()`
- `requirements()`
- `contractFor(CaveniaRegistryLookupRequirement)`
- `requirementCount()`
- `candidateKeysInventoried()`
- `candidateKeysStillStringOnly()`
- `registryLookupReadinessReady()`
- `registryLookupRuntimeReady()`
- `allRequirementsRuntimeBlocked()`
- `anyRequirementReadyForRuntime()`
- `anyRegistryLookupAvailable()`
- `anyRegistryVerified()`
- `anyRuntimeBiomeResolved()`
- `registryAccessSourceReady()`
- `biomeRegistryReferenceReady()`
- `resourceKeyConversionReady()`
- `holderResolutionReady()`
- `missingBiomeFallbackReady()`
- `runtimeLookupContextReady()`
- `adapterResultToRuntimeBiomeReady()`
- `adapterShapeReady()`
- `adapterRuntimeReady()`
- `codecRegistrationReadinessReady()`
- `codecRegistrationRuntimeReady()`
- `runtimeBiomeSourceReady()`
- `runtimeBiomeSourceRegistered()`
- `codecRegistered()`
- `registryLookupAccessReady()`
- `modernBiomeMappingReady()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `candidateEntryCount()`
- `adapterEntryCount()`
- `adapterTotalWeight()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Current Facts

- candidate keys are inventoried
- candidate keys remain plain strings only
- registry lookup readiness contract is ready
- registry lookup runtime is not ready
- registry access source decision is not ready
- biome registry reference is not ready
- resource-key conversion is not ready
- holder resolution is not ready
- missing-biome fallback decision is not ready
- runtime lookup context is not ready
- adapter result to runtime biome conversion is not ready
- registry lookup access is not ready
- registry verification is not ready
- runtime `BiomeSource` is not ready
- runtime biome-source registration is not ready
- codec implementation/registration is not ready
- dimension JSON/type JSON are absent
- activation is not allowed in this slice
- Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged

## Relationship To Existing Contracts

This readiness layer sits directly on top of:

- `CaveniaAdapterCodecRegistrationReadiness`
- `CaveniaBiomeSelectionAdapterContract`
- `CaveniaBiomeSelectionAdapterQuery`
- `CaveniaBiomeSelectionAdapterResult`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaWeightedBiomeSelectionAlgorithm`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaBiomeSourceStrategyContracts`
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
- convert candidate strings into runtime biome keys
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

The selected-surface consolidation follow-up is now documented in `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`.

The next slice may consolidate `BIOME_SOURCE_STRATEGY` readiness into a final selected-surface readiness matrix.

The runtime-biome-source first-implementation decision/guardrails follow-up is now documented in `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`.

Real runtime `BiomeSource`, codec, registration and registry lookup access must remain separate explicit MVPs.

The unregistered runtime-biome-source skeleton follow-up is now documented in `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`.

The runtime-biome-source codec/holder/registry decision follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`.
