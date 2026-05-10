# Cavenia Adapter Codec/Registration Readiness Contracts MVP

This is adapter codec/registration readiness only, not runtime activation.

This slice is readiness-only and unregistered.

Selected surface remains `BIOME_SOURCE_STRATEGY`.

It builds on:

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

- `CaveniaAdapterCodecRegistrationRequirement`
- `CaveniaAdapterCodecRegistrationRequirementContract`
- `CaveniaAdapterCodecRegistrationReadiness`

## Exact Requirement Enum Values

- `ADAPTER_SHAPE_AVAILABLE`
- `SERIALIZATION_MODEL_DECISION`
- `CODEC_SHAPE_DECISION`
- `CODEC_IMPLEMENTATION`
- `BIOME_SOURCE_TYPE_KEY`
- `BIOME_SOURCE_TYPE_REGISTRATION`
- `REGISTRY_LOOKUP_ACCESS`
- `RUNTIME_BIOME_SOURCE_CLASS`
- `DIMENSION_BINDING_DEFERRED`

## Exact Requirement Contract Fields

`CaveniaAdapterCodecRegistrationRequirementContract` now exposes:

- `requirement`
- `sourceContractName`
- `prerequisiteSatisfied`
- `readyForRuntime`
- `codecImplemented`
- `registered`
- `registryLookupAvailable`
- `activationAllowedInThisSlice`
- `blocker`

## Exact Readiness Helpers

`CaveniaAdapterCodecRegistrationReadiness` now exposes:

- `contracts()`
- `requirements()`
- `contractFor(CaveniaAdapterCodecRegistrationRequirement)`
- `requirementCount()`
- `adapterShapeReady()`
- `adapterRuntimeReady()`
- `codecRegistrationReadinessReady()`
- `codecRegistrationRuntimeReady()`
- `allRequirementsRuntimeBlocked()`
- `anyRequirementReadyForRuntime()`
- `anyCodecImplemented()`
- `anyRegistered()`
- `registryLookupAccessReady()`
- `runtimeBiomeSourceReady()`
- `runtimeBiomeSourceRegistered()`
- `codecRegistered()`
- `biomeSourceTypeKeyReady()`
- `dimensionBindingReady()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `consumesAdapterContract()`
- `adapterEntryCount()`
- `adapterTotalWeight()`
- `weightedSelectionAlgorithmReady()`
- `candidateInventoryReady()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Current Facts

- adapter shape is ready
- codec/registration readiness contract is ready
- codec/registration runtime is not ready
- codec implementation is not ready
- biome-source type key is not ready
- biome-source type registration is not ready
- registry lookup access is not ready
- runtime `BiomeSource` is not ready
- runtime biome-source registration is not ready
- dimension binding is deferred
- dimension JSON/type JSON are absent
- activation is not allowed in this slice
- Cavenia cannot activate now
- `EntityCaveman -> deferred:caveman` remains unchanged

## Relationship To Existing Contracts

This readiness layer sits directly on top of:

- `CaveniaBiomeSelectionAdapterContract`
- `CaveniaBiomeSelectionAdapterQuery`
- `CaveniaBiomeSelectionAdapterResult`
- `CaveniaWeightedBiomeSelectionAlgorithm`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaBiomeSourceStrategyContracts`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Stays Inert

This slice is still not a `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no codec implementation was added.

no codec or registry entry was added.

no registry lookup access was added.

It does not:

- define a codec
- register a biome-source type
- register a generator type
- use registry lookup APIs
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

The next slice may define registry lookup readiness contracts, but must still avoid actual registry lookup access and runtime activation unless explicitly chosen.

Real runtime `BiomeSource`, codec, registration and registry lookup access must remain separate explicit MVPs.

The registry lookup readiness follow-up is now documented in `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`.

The final selected-surface readiness-matrix follow-up is now documented in `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`.

The runtime-biome-source first-implementation decision/guardrails follow-up is now documented in `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`.

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
