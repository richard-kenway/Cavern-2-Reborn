# Cavenia Runtime BiomeSource Selector Input Algorithm Consolidation / Next Decision MVP

This is selector-input algorithm consolidation and next-decision, not bridge implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice consolidates the existing pure selector-input algorithm state, pins the blocked runtime surfaces around it, and selects only the next inert decision branch.

## Added Type

- `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent`
- `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationEntry`
- `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationNextDecision`
- `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation`

## Component Order

- `PURE_SELECTOR_INPUT_ALGORITHM_READY`
- `INTEGER_COORDINATE_INPUTS_READY`
- `CLIMATE_SAMPLER_EXCLUDED`
- `SIGNED_INT_SELECTOR_OUTPUT_READY`
- `DETERMINISTIC_FORMULA_READY`
- `NORMALIZATION_DELEGATED`
- `NEGATIVE_COORDINATE_STABILITY_READY`
- `NO_RANDOM_OR_MUTABLE_STATE`
- `WEIGHTED_SELECTOR_COMPATIBILITY_READY`
- `ADAPTER_COMPATIBILITY_READY`
- `SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NOT_IMPLEMENTED`
- `GET_NOISE_BIOME_WIRING_BLOCKED`
- `RUNTIME_SELECTION_BLOCKED`
- `REGISTRY_AND_HOLDER_SURFACES_BLOCKED`
- `NEXT_DECISION_SELECTED`

## Next Decision Values

- `PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_GO_NO_GO_NEXT`
- `DEFER_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_IMPLEMENTATION`
- `DEFER_GET_NOISE_BIOME_WIRING`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOLUTION`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Entry Fields

- `component`
- `sourceContractName`
- `consolidatedState`
- `nextBoundary`
- `algorithmReady`
- `bridgeReady`
- `runtimeReady`
- `nextDecisionInput`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation` exposes deterministic helpers including:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `selectorInputAlgorithmConsolidationReady()`
- `selectorInputAlgorithmReady()`
- `pureNonRuntimeAlgorithmReady()`
- `integerCoordinateInputsReady()`
- `climateSamplerExcluded()`
- `climateSamplerMethodsCalled()`
- `signedIntSelectorOutputReady()`
- `deterministicFormulaReady()`
- `normalizationDelegated()`
- `negativeCoordinateStable()`
- `randomOrMutableStateDependencyPresent()`
- `weightedSelectorCompatibilityReady()`
- `weightedSelectionTotalWeight()`
- `adapterCompatibilityReady()`
- `selectorToWeightedCandidateBridgeReady()`
- `selectorToWeightedCandidateBridgeImplemented()`
- `selectorToWeightedCandidateBridgeRuntimeReady()`
- `selectedNextDecision()`
- `selectorToWeightedCandidateBridgeGoNoGoIsNext()`
- `nextSliceMayAddSelectorToWeightedCandidateBridgeGoNoGoDecision()`
- `nextSliceMayImplementSelectorToWeightedCandidateBridge()`
- `nextSliceMayCallWeightedSelectorWithDerivedInput()`
- `nextSliceMayCallAdapterWithDerivedInput()`
- `nextSliceMayWireIntoGetNoiseBiome()`
- `nextSliceMayMakeGetNoiseBiomeUsable()`
- `deriveSelectorInputSampleOrigin()`
- `deriveSelectorInputSampleMixed()`
- `deriveSelectorInputSampleNegative()`
- `selectorInputOutputShape()`
- `normalizationPolicy()`
- `algorithmRuntimeBoundary()`
- `implementationGoNoGoDecisionReady()`
- `implementationGoNoGoRuntimeReady()`
- `selectorInputDerivationReadinessReady()`
- `selectorInputDerivationReadinessRuntimeReady()`
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
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

## Pinned Facts

- pure selector-input algorithm is ready.
- integer coordinate inputs are ready.
- climate sampler remains excluded.
- signed int selector output is ready.
- deterministic formula is ready.
- normalization remains delegated.
- negative coordinate stability is ready.
- random/mutable state dependency is absent.
- weighted selector compatibility is ready.
- adapter compatibility is ready.
- selector-to-weighted-candidate bridge is not implemented.
- selected next decision is `PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_GO_NO_GO_NEXT`.
- next slice may add selector-to-weighted-candidate bridge go/no-go decision.
- next slice may not implement selector-to-weighted-candidate bridge.
- next slice may not call weighted selector with derived input.
- next slice may not call adapter with derived input.
- next slice may not wire into `getNoiseBiome(...)`.
- next slice may not make `getNoiseBiome(...)` usable.
- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
- `codec()` remains unsupported.
- no registry lookup access exists.
- no holder resolution exists.
- no resource-location conversion implementation exists.
- no resource-key conversion implementation exists.
- no fallback implementation exists.
- no usable codec implementation exists.
- no codec registration exists.
- no biome-source type registration exists.
- subclass remains guarded, unregistered, and non-activating.
- normal runtime construction remains unavailable.
- no dimension JSON/type JSON exist.
- no active Cavenia level exists.
- no worldgen resources exist.
- no access/teleport exists.
- no spawning exists.
- `cavernreborn:caveman` remains absent.
- `EntityCaveman -> deferred:caveman` remains unchanged.
- selected-surface readiness item count remains `68`.
- global readiness matrix total/blocked count remains `46/46`.

## Relationships

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision`
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

## Why It Is Still Inert

This is still not selector-to-weighted-candidate bridge because this slice only consolidates the pure selector-input algorithm state and selects the next decision branch.

This is still not coordinate/climate runtime selection because the current selector-input algorithm remains non-runtime and still excludes the sampler surface.

This is still not usable noise-biome runtime behavior because `CaveniaRuntimeBiomeSource.getNoiseBiome(...)` remains unsupported and unwired.

This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains unsupported and no holder stream exists.

This is still not usable runtime biome resolution because registry lookup access, holder resolution, and resource-key conversion remain absent.

This is still not a usable runtime biome source because codec implementation, codec registration, biome-source type registration, dimension binding, and activation all remain absent.

This is still not a `ChunkGenerator` because this slice does not add generator code, generator registration, or any active runtime generator surface.

No registry lookup access was added because selector-input consolidation remains a pure inert contract layer.

No holder/resource-key conversion implementation was added because runtime biome resolution remains out of scope.

No fallback implementation was added because no runtime candidate resolution or holder-backed selection path exists yet.

No usable codec implementation was added because runtime construction and registration remain deferred.

No codec or registry entry was added because the designated subclass remains guarded and unregistered.

No dimension JSON or dimension-type JSON was added because activation remains blocked.

No active generator was added because this slice selects only the next bridge decision surface.

No active Cavenia level was added because dimension binding and activation remain absent.

No access, spawning, or worldgen resources were added because those runtime surfaces remain blocked.

## Future Recommendation

- Next slice can be `Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Go/No-Go MVP`.
- That next slice should remain decision-only unless explicitly requested otherwise.
- That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
