# Cavenia Runtime BiomeSource Selector Input Derivation Implementation Go/No-Go MVP

This slice is selector-input derivation implementation go/no-go decision, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice only decides whether the next slice may add a pure non-runtime selector-input derivation algorithm for the guarded `getNoiseBiome(...)` path.

## Decision

The selected decision is `PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_INPUT_DERIVATION_ALGORITHM_NEXT`.

- next slice may add a pure non-runtime selector-input derivation algorithm
- next slice may use integer coordinate inputs
- next slice may return signed int selector input
- next slice may delegate normalization to the existing selector/adapter
- next slice may not call `Climate.Sampler` methods
- next slice may not import Minecraft runtime APIs
- next slice may not wire into `getNoiseBiome(...)`
- next slice may not make `getNoiseBiome(...)` usable
- next slice may not make `collectPossibleBiomes()` usable
- next slice may not use registry lookup access
- next slice may not resolve holders
- next slice may not implement resource-location conversion
- next slice may not implement resource-key conversion
- next slice may not add fallback implementation
- next slice may not add usable codec implementation
- next slice may not register codec
- next slice may not register biome-source type
- next slice may not use random or mutable state

This slice does not implement the algorithm.

This slice does not pin the actual formula.

selector-input derivation readiness remains ready.

selector-input derivation readiness runtime behavior is not ready.

readiness-chain consolidation remains ready.

all current readiness layers are ready.

no runtime layer is ready.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision`

## Next-Decision Values

- `PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_INPUT_DERIVATION_ALGORITHM_NEXT`
- `DEFER_RUNTIME_GET_NOISE_BIOME_WIRING`
- `DEFER_CLIMATE_SAMPLER_CONSUMPTION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOLUTION`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Guardrail Order

- `ALLOW_PURE_NON_RUNTIME_ALGORITHM_NEXT`
- `ALLOW_INTEGER_COORDINATE_INPUTS_NEXT`
- `ALLOW_SIGNED_INT_SELECTOR_OUTPUT_NEXT`
- `ALLOW_EXISTING_SELECTOR_NORMALIZATION_NEXT`
- `NO_CLIMATE_SAMPLER_METHOD_CALLS`
- `NO_MINECRAFT_RUNTIME_API_IMPORTS`
- `NO_GET_NOISE_BIOME_WIRING`
- `NO_GET_NOISE_BIOME_IMPLEMENTATION`
- `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_HOLDER_RESOLUTION`
- `NO_RESOURCE_LOCATION_OR_KEY_CONVERSION`
- `NO_FALLBACK_IMPLEMENTATION`
- `NO_USABLE_CODEC_OR_REGISTRATION`
- `NO_RANDOM_OR_MUTABLE_STATE`
- `NO_DIMENSION_ACTIVATION`
- `NO_WORLDGEN_ACCESS_SPAWNING`
- `KEEP_READINESS_CHAIN_INERT`

## Guardrail Contract Fields

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSlicePureAlgorithmAction`
- `allowsNextSliceRuntimeAction`
- `blocksActivation`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision` exposes deterministic helpers including:

- `selectedDecision()`
- `decisionIsGoForPureNonRuntimeAlgorithmNext()`
- `decisionIsImplementationOnlyForNextSlice()`
- `selectorInputDerivationAlgorithmImplementedInThisSlice()`
- `selectorInputDerivationRuntimeReady()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(...)`
- `guardrailCount()`
- `allGuardrailsEnforcedInThisSlice()`
- `allowedNextSlicePureAlgorithmActionCount()`
- `allowedNextSliceRuntimeActionCount()`
- `anyRuntimeActionAllowedByGuardrails()`
- `nextSliceMayAddPureNonRuntimeSelectorInputAlgorithm()`
- `nextSliceMayUseIntegerCoordinateInputs()`
- `nextSliceMayReturnSignedIntSelectorInput()`
- `nextSliceMayDelegateNormalizationToExistingSelectorOrAdapter()`
- `nextSliceMayCallClimateSamplerMethods()`
- `nextSliceMayImportMinecraftRuntimeApis()`
- `nextSliceMayWireIntoGetNoiseBiome()`
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
- `nextSliceMayUseRandomOrMutableState()`
- `nextSliceMayAddDimensionJson()`
- `nextSliceMayAddDimensionTypeJson()`
- `nextSliceMayCreateActiveCaveniaLevel()`
- `nextSliceMayAddWorldgenResources()`
- `nextSliceMayAddAccessOrTeleport()`
- `nextSliceMayAddSpawning()`
- `nextSliceMayRegisterCavemanEntity()`
- `selectorInputDerivationReadinessReady()`
- `selectorInputDerivationReadinessRuntimeReady()`
- `selectorInputDerivationImplementationReady()`
- `selectorInputDerivationOutputShape()`
- `actualDerivationFormulaPinned()`
- `actualDerivationFormulaImplemented()`
- `methodInputSource()`
- `coordinateInputPolicy()`
- `climateSamplerInputPolicy()`
- `normalizationPolicy()`
- `deterministicDerivationPolicy()`
- `pureNonRuntimePolicy()`
- `noRandomOrMutableStatePolicy()`
- `goNoGoDecisionReady()`
- `goNoGoRuntimeReady()`
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `weightedSelectionAlgorithmReady()`
- `weightedSelectionTotalWeight()`
- `adapterShapeReady()`
- `adapterRuntimeReady()`
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

## Relationships

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`
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
- `cavernreborn:caveman` remains absent
- `EntityCaveman -> deferred:caveman` remains unchanged

This is still not selector-input derivation implementation.

This is still not coordinate/climate runtime selection.

This is still not usable noise-biome runtime behavior.

This is still not usable possible-biomes runtime behavior.

This is still not usable runtime biome resolution.

This is still not a usable runtime biome source.

This is still not a `ChunkGenerator`.

- why no registry lookup access was added: the next allowed slice must stay pure and non-runtime
- why no holder/resource-key conversion implementation was added: conversion remains a separate blocked runtime surface
- why no fallback implementation was added: fallback remains inert readiness data only
- why no usable codec implementation was added: codec work remains blocked behind later explicit slices
- why no codec/registry entry was added: registration remains blocked and is still a non-goal
- why no dimension JSON/type JSON was added: activation remains blocked and out of scope
- why no active generator was added: the guarded biome-source chain still does not allow runtime wiring
- why no active Cavenia level was added: level binding and activation remain blocked
- why no access/spawning/worldgen resources were added: those activation surfaces remain intentionally absent

## Global Inert State

- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`

## Follow-Up

- next slice can be `Cavenia Runtime BiomeSource Selector Input Derivation Pure Non-Runtime Algorithm MVP`
- that next slice may implement only a pure non-runtime helper/algorithm
- that next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise
