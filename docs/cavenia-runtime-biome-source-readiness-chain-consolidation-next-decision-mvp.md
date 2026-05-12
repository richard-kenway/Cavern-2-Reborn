# Cavenia Runtime BiomeSource Readiness Chain Consolidation / Next Decision MVP

This slice is readiness-chain consolidation and next-decision, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice only consolidates the current guarded runtime-biome-source readiness chain and records the next safe decision branch.

## Consolidated Chain

The current consolidated readiness chain is:

- guarded subclass stub
- codec method shape stub
- holder/resource-key conversion readiness
- fallback policy readiness
- possible-biomes readiness
- noise-biome selection readiness

All current readiness layers are ready.

all current readiness layers are ready.

No runtime layer is ready.

no runtime layer is ready.

The selected next decision is `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_GO_NO_GO_NEXT`.

- next slice may add only selector-input-derivation go/no-go decision
- selector-input derivation is not pinned and not implemented
- selector-input derivation runtime behavior is not ready

## Added Types

- `CaveniaRuntimeBiomeSourceReadinessChainComponent`
- `CaveniaRuntimeBiomeSourceReadinessChainEntry`
- `CaveniaRuntimeBiomeSourceReadinessChainNextDecision`
- `CaveniaRuntimeBiomeSourceReadinessChainConsolidation`

## Component Order

- `DESIGNATED_SUBCLASS_STUB`
- `CODEC_METHOD_SHAPE_STUB`
- `HOLDER_RESOURCE_KEY_CONVERSION_READINESS`
- `FALLBACK_POLICY_READINESS`
- `POSSIBLE_BIOMES_READINESS`
- `NOISE_BIOME_SELECTION_READINESS`
- `SELECTOR_INPUT_DERIVATION_NOT_PINNED`
- `REGISTRY_LOOKUP_ACCESS_BLOCKED`
- `HOLDER_RESOLUTION_BLOCKED`
- `RESOURCE_LOCATION_CONVERSION_BLOCKED`
- `RESOURCE_KEY_CONVERSION_BLOCKED`
- `USABLE_RUNTIME_METHODS_BLOCKED`
- `USABLE_CODEC_IMPLEMENTATION_BLOCKED`
- `CODEC_AND_TYPE_REGISTRATION_BLOCKED`
- `DIMENSION_ACTIVATION_BLOCKED`
- `WORLDGEN_ACCESS_SPAWNING_BLOCKED`
- `NEXT_DECISION_SELECTED`

## Next Decision Values

- `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_GO_NO_GO_NEXT`
- `DEFER_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOLUTION`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Entry Fields

- `component`
- `sourceContractName`
- `consolidatedReadiness`
- `runtimeState`
- `readinessReady`
- `runtimeReady`
- `blocksActivation`
- `nextDecisionInput`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceReadinessChainConsolidation` exposes deterministic helpers including:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `readinessChainConsolidationReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `selectedNextDecision()`
- `selectorInputDerivationGoNoGoIsNext()`
- `selectorInputDerivationReadinessPinned()`
- `selectorInputDerivationImplementationReady()`
- `selectorInputDerivationRuntimeReady()`
- `nextSliceMayAddSelectorInputDerivationGoNoGoDecision()`
- `nextSliceMayImplementSelectorInputDerivation()`
- `nextSliceMayUseCoordinatesForRuntimeSelection()`
- `nextSliceMayUseClimateSamplerForRuntimeSelection()`
- `nextSliceMayMakeGetNoiseBiomeUsable()`
- `nextSliceMayMakeCollectPossibleBiomesUsable()`
- `nextSliceMayUseRegistryLookupAccess()`
- `nextSliceMayResolveHolders()`
- `nextSliceMayImplementResourceLocationConversion()`
- `nextSliceMayImplementResourceKeyConversion()`
- `nextSliceMayAddFallbackImplementation()`
- `nextSliceMayAddUsableCodecImplementation()`
- `nextSliceMayRegisterCodec()`
- `nextSliceMayRegisterBiomeSourceType()`
- `nextSliceMayAddDimensionJson()`
- `nextSliceMayAddDimensionTypeJson()`
- `nextSliceMayCreateActiveCaveniaLevel()`
- `nextSliceMayAddWorldgenResources()`
- `nextSliceMayAddAccessOrTeleport()`
- `nextSliceMayAddSpawning()`
- `nextSliceMayRegisterCavemanEntity()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
- `codecMethodShapeStubReady()`
- `holderConversionReadinessReady()`
- `fallbackPolicyReadinessReady()`
- `possibleBiomesReadinessReady()`
- `noiseBiomeSelectionReadinessReady()`
- `codecMethodShapeRuntimeReady()`
- `holderConversionRuntimeReady()`
- `fallbackPolicyRuntimeReady()`
- `possibleBiomesRuntimeReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `runtimeHolderReturnReady()`
- `collectPossibleBiomesImplementationReady()`
- `getNoiseBiomeImplementationReady()`
- `usableRuntimeBiomeSourceReady()`
- `registryLookupAccessReady()`
- `holderResolutionReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `normalRuntimeConstructionAllowed()`
- `unsupportedMethodStubsOnly()`
- `collectPossibleBiomesStubbed()`
- `getNoiseBiomeStubbed()`
- `codecMethodStubbed()`
- `weightedSelectionAlgorithmReady()`
- `weightedSelectionTotalWeight()`
- `adapterShapeReady()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `dimensionBindingReady()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Runtime Boundaries

`getNoiseBiome(...)` remains unsupported.

`collectPossibleBiomes()` remains unsupported.

`codec()` remains unsupported.

- no registry lookup access exists
- no holder resolution exists
- no resource-location conversion implementation exists
- no resource-key conversion implementation exists
- no fallback implementation exists
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- subclass remains guarded, unregistered, and non-activating
- normal runtime construction remains unavailable
- no dimension JSON/type JSON exist
- no active Cavenia level exists
- no worldgen resources exist
- no access/teleport exists
- no spawning exists

This is still not selector-input derivation.

This is still not usable noise-biome runtime behavior.

This is still not usable possible-biomes runtime behavior.

This is still not usable runtime biome resolution.

This is still not a usable runtime biome source.

This is still not a `ChunkGenerator`.

- why no registry lookup access was added: this slice only consolidates readiness state and records the next decision branch
- why no holder/resource-key conversion implementation was added: conversion remains a blocked runtime surface
- why no fallback implementation was added: fallback remains inert readiness data only
- why no usable codec implementation was added: codec work remains blocked behind later explicit slices
- why no codec/registry entry was added: registration remains blocked and non-goal in this slice
- why no dimension JSON/type JSON was added: activation remains blocked and non-goal in this slice
- why no active generator was added: the guarded runtime-biome-source chain still does not allow activation
- why no active Cavenia level was added: dimension binding and activation remain blocked
- why no access/spawning/worldgen resources were added: those activation surfaces remain intentionally absent

## Relationship To Existing Contracts

This slice builds directly on:

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness`
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub`
- `CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceApiShapeInventory`
- `CaveniaRuntimeBiomeSourceSubclassDecision`
- `CaveniaRuntimeBiomeSourceSkeleton`
- `CaveniaRuntimeBiomeSourceFirstImplementationDecision`
- `CaveniaBiomeSourceStrategyReadinessMatrix`
- `CaveniaRegistryLookupReadiness`
- `CaveniaAdapterCodecRegistrationReadiness`
- `CaveniaBiomeSelectionAdapterContract`
- `CaveniaWeightedBiomeSelectionAlgorithm`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Global Inert State

- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`
- `cavernreborn:caveman` remains absent
- `EntityCaveman -> deferred:caveman` remains unchanged

## Follow-Up

- next slice should be `Cavenia Runtime BiomeSource Selector Input Derivation Go/No-Go MVP`
- that next slice should remain decision-only unless explicitly requested otherwise

The selector-input derivation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md`.

The selector-input derivation readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md`.

The selector-input derivation implementation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md`.

The pure non-runtime selector-input derivation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md`.
