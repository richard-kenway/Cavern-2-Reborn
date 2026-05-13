# Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Readiness MVP

This is selector-to-weighted-candidate bridge readiness, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice pins only the future inert bridge surface from the already-derived signed int selector input to a string modern biome candidate key through the existing weighted selector or adapter path.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness`

## Component Order

- `SELECTOR_INPUT_SOURCE`
- `WEIGHTED_SELECTOR_SOURCE`
- `ADAPTER_SOURCE`
- `CANDIDATE_INVENTORY_SOURCE`
- `SELECTOR_INPUT_SHAPE`
- `TOTAL_WEIGHT_CONTRACT`
- `NORMALIZATION_POLICY`
- `CANDIDATE_KEY_OUTPUT_SHAPE`
- `CANDIDATE_ENTRY_COUNT`
- `FALLBACK_POLICY_DEPENDENCY`
- `POSSIBLE_BIOMES_CONSTRAINT`
- `NOISE_BIOME_SELECTION_DEPENDENCY`
- `NO_BRIDGE_IMPLEMENTATION`
- `NO_SELECTOR_OR_ADAPTER_CALLS`
- `NO_RUNTIME_WIRING`
- `REAL_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `bridgeSurface`
- `futurePolicy`
- `readinessPinned`
- `bridgeImplementationReady`
- `runtimeReady`
- `activationAllowedInThisSlice`
- `blocker`

## Pinned Bridge Surface

- selector input source is `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`.
- weighted selector source is `CaveniaWeightedBiomeSelectionAlgorithm`.
- adapter source is `CaveniaBiomeSelectionAdapterContract`.
- candidate inventory source is `CaveniaLegacyToModernBiomeKeyMappings`.
- selector input shape is signed int selector input.
- total weight contract is `675`.
- normalization policy delegates to the existing selector/adapter.
- candidate key output shape is string modern biome candidate key.
- candidate entry count is `14`.
- fallback policy dependency is pinned.
- possible-biomes constraint is pinned.
- noise-biome selection dependency is pinned.
- candidate keys remain string-only until holder conversion is explicitly implemented.
- future fallback usage remains readiness-only and is not called in this slice.

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness` exposes deterministic helpers for:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `selectorToWeightedCandidateBridgeReadinessReady()`
- `selectorInputSourcePinned()`
- `weightedSelectorSourcePinned()`
- `adapterSourcePinned()`
- `candidateInventorySourcePinned()`
- `selectorInputShapePinned()`
- `totalWeightContractPinned()`
- `normalizationPolicyPinned()`
- `candidateKeyOutputShapePinned()`
- `candidateEntryCountPinned()`
- `fallbackPolicyDependencyPinned()`
- `possibleBiomesConstraintPinned()`
- `noiseBiomeSelectionDependencyPinned()`
- `bridgeImplementationReady()`
- `selectorOrAdapterCallsReady()`
- `runtimeWiringReady()`
- `realSelectorToWeightedCandidateBridgeStillDeferred()`
- `selectorInputSource()`
- `selectorInputOutputShape()`
- `weightedSelectorSource()`
- `adapterSource()`
- `candidateInventorySource()`
- `candidateKeyOutputShape()`
- `normalizationPolicy()`
- `bridgeRuntimeBoundary()`
- `deriveSelectorInputSampleOrigin()`
- `deriveSelectorInputSampleMixed()`
- `deriveSelectorInputSampleNegative()`
- `weightedSelectionTotalWeight()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `bridgeGoNoGoDecisionReady()`
- `selectorInputAlgorithmConsolidationReady()`
- `selectorInputAlgorithmSelectedNextDecisionMatches()`
- `implementationGoNoGoDecisionReady()`
- `readinessChainConsolidationReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `holderResolutionReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
- `codecMethodShapeStubReady()`
- `designatedSubclassReady()`
- `collectPossibleBiomesImplementationReady()`
- `getNoiseBiomeImplementationReady()`
- `usableRuntimeBiomeSourceReady()`
- `registryLookupAccessReady()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `normalRuntimeConstructionAllowed()`
- `unsupportedMethodStubsOnly()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Facts

- selector-to-weighted-candidate bridge readiness is ready.
- selector input source is pinned.
- weighted selector source is pinned.
- adapter source is pinned.
- candidate inventory source is pinned.
- selector input shape is pinned.
- total weight contract is pinned.
- normalization policy is pinned.
- candidate key output shape is pinned.
- candidate entry count is pinned.
- fallback policy dependency is pinned.
- possible-biomes constraint is pinned.
- noise-biome selection dependency is pinned.
- bridge implementation is not ready.
- selector/adapter calls are not ready.
- runtime wiring is not ready.
- no output candidate key is produced by this bridge in this slice.
- real selector-to-weighted-candidate bridge is still deferred.
- weighted selector compatibility remains ready.
- adapter compatibility remains ready.
- candidate keys remain string-only.
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
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation`
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

This is still not selector-to-weighted-candidate bridge implementation because no bridge method exists and no output candidate key is produced by this bridge in this slice.

This is still not weighted selector/adapter wiring because no weighted selector is called with derived selector input and no adapter is called with derived selector input.

This is still not coordinate/climate runtime selection because the guarded subclass remains unsupported and the pinned bridge surface stays outside runtime method execution.

This is still not usable noise-biome runtime behavior because `CaveniaRuntimeBiomeSource.getNoiseBiome(...)` remains unsupported and unwired.

This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains unsupported and no holder stream exists.

This is still not usable runtime biome resolution because holder conversion, resource-location conversion, resource-key conversion and registry lookup access remain absent.

This is still not a usable runtime biome source because the guarded subclass remains unregistered, non-activating and unsupported.

This is still not a `ChunkGenerator` because no generator implementation or activation surface is added here.

No registry lookup access was added because this slice stays bounded to inert readiness data only.

No holder/resource-key conversion implementation was added because candidate keys must remain string-only until a later explicit conversion slice.

No fallback implementation was added because fallback remains a dependency surface only and is not called in this slice.

No usable codec implementation was added because codec behavior, codec registration and biome-source type registration remain blocked.

No codec/registry entry was added because this slice adds only inert readiness metadata.

No dimension JSON/type JSON was added because activation remains out of scope.

No active generator was added because generator activation remains out of scope.

No active Cavenia level was added because level activation remains out of scope.

No access/spawning/worldgen resources were added because travel, spawn-host and worldgen-resource work remain blocked.

## Follow-Up

- next slice can be `Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Implementation Go/No-Go MVP`.
- that next slice should remain decision-only unless explicitly requested otherwise.
- that next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
