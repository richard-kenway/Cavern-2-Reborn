# Cavenia Runtime BiomeSource Selector Input Derivation Go/No-Go MVP

This slice is selector-input derivation go/no-go decision, not readiness and not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice only decides whether the next slice may pin selector-input derivation readiness data for the guarded `getNoiseBiome(...)` path.

## Decision

The selected decision is `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT`.

- next slice may add selector-input derivation readiness data
- next slice may pin coordinate input policy
- next slice may pin climate sampler input policy
- next slice may pin weight-value derivation policy
- next slice may not implement selector-input derivation
- next slice may not use coordinates for runtime selection
- next slice may not use climate sampler for runtime selection
- next slice may not make `getNoiseBiome(...)` usable
- next slice may not make `collectPossibleBiomes()` usable

readiness-chain consolidation remains ready, all current readiness layers are ready, and no runtime layer is ready.

selector-input derivation policy is not pinned in this slice.

selector-input derivation implementation is not ready.

selector-input derivation runtime behavior is not ready.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`

## Next-Decision Values

- `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT`
- `DEFER_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION`
- `DEFER_COORDINATE_RUNTIME_SELECTION`
- `DEFER_CLIMATE_SAMPLER_RUNTIME_SELECTION`
- `DEFER_GET_NOISE_BIOME_IMPLEMENTATION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOLUTION`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Guardrail Order

- `ALLOW_SELECTOR_INPUT_DERIVATION_READINESS_NEXT`
- `ALLOW_COORDINATE_INPUT_POLICY_PINNING_NEXT`
- `ALLOW_CLIMATE_SAMPLER_INPUT_POLICY_PINNING_NEXT`
- `ALLOW_WEIGHT_VALUE_DERIVATION_POLICY_PINNING_NEXT`
- `NO_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION`
- `NO_COORDINATE_RUNTIME_SELECTION`
- `NO_CLIMATE_SAMPLER_RUNTIME_SELECTION`
- `NO_GET_NOISE_BIOME_IMPLEMENTATION`
- `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_HOLDER_RESOLUTION`
- `NO_RESOURCE_LOCATION_OR_KEY_CONVERSION`
- `NO_FALLBACK_IMPLEMENTATION`
- `NO_USABLE_CODEC_OR_REGISTRATION`
- `NO_DIMENSION_ACTIVATION`
- `NO_WORLDGEN_ACCESS_SPAWNING`
- `KEEP_READINESS_CHAIN_INERT`

## Guardrail Contract Fields

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSliceReadinessAction`
- `allowsNextSliceRuntimeAction`
- `blocksActivation`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision` exposes deterministic helpers including:

- `selectedDecision()`
- `decisionIsGoForSelectorInputDerivationReadinessNext()`
- `decisionIsReadinessOnlyForNextSlice()`
- `selectorInputDerivationReadinessImplementedInThisSlice()`
- `selectorInputDerivationPolicyPinnedInThisSlice()`
- `selectorInputDerivationImplementationReady()`
- `selectorInputDerivationRuntimeReady()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(...)`
- `guardrailCount()`
- `allGuardrailsEnforcedInThisSlice()`
- `allowedNextSliceReadinessActionCount()`
- `allowedNextSliceRuntimeActionCount()`
- `anyRuntimeActionAllowedByGuardrails()`
- `nextSliceMayAddSelectorInputDerivationReadiness()`
- `nextSliceMayPinCoordinateInputPolicy()`
- `nextSliceMayPinClimateSamplerInputPolicy()`
- `nextSliceMayPinWeightValueDerivationPolicy()`
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
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `readinessChainSelectedNextDecisionMatches()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `coordinateInputShapePinned()`
- `climateSamplerInputShapePinned()`
- `weightValueDerivationDecisionPinned()`
- `weightValueDerivationPolicy()`
- `weightedSelectionAlgorithmReady()`
- `weightedSelectionTotalWeight()`
- `adapterShapeReady()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
- `holderResolutionReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesRuntimeReady()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicyRuntimeReady()`
- `codecMethodShapeStubReady()`
- `codecMethodShapeRuntimeReady()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
- `runtimeHolderReturnReady()`
- `collectPossibleBiomesImplementationReady()`
- `getNoiseBiomeImplementationReady()`
- `usableRuntimeBiomeSourceReady()`
- `registryLookupAccessReady()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `normalRuntimeConstructionAllowed()`
- `unsupportedMethodStubsOnly()`
- `collectPossibleBiomesStubbed()`
- `getNoiseBiomeStubbed()`
- `codecMethodStubbed()`
- `dimensionBindingReady()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Runtime Boundaries

This slice does not pin the actual coordinate/climate-to-weight algorithm.

This slice does not implement selector-input derivation.

This slice does not use coordinates or climate sampler for runtime selection.

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

This is still not selector-input derivation readiness.

This is still not selector-input derivation implementation.

This is still not usable noise-biome runtime behavior.

This is still not usable possible-biomes runtime behavior.

This is still not usable runtime biome resolution.

This is still not a usable runtime biome source.

This is still not a `ChunkGenerator`.

- why no registry lookup access was added: this slice only selects the next guarded decision branch
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
- `CaveniaRuntimeBiomeSourceReadinessChainConsolidation`
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

- next slice should be `Cavenia Runtime BiomeSource Selector Input Derivation Readiness MVP`
- that next slice should still be readiness-only unless explicitly requested otherwise
