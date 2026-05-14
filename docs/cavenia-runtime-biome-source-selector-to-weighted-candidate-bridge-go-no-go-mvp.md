# Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Go/No-Go MVP

This is selector-to-weighted-candidate bridge go/no-go decision, not readiness and not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice adds only a guarded decision layer that decides whether the next inert slice may pin bridge-readiness data for the pure selector input and the existing weighted selector or adapter path.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision`

## Selected Decision

`PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT`

The selected decision is `PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT`.

- next slice may add selector-to-weighted-candidate bridge readiness data.
- next slice may pin selector-input surface policy.
- next slice may pin weighted selector query policy.
- next slice may pin adapter query policy.
- next slice may pin candidate-key output policy.
- next slice may not implement selector-to-weighted-candidate bridge.
- next slice may not call weighted selector with derived input.
- next slice may not call adapter with derived input.
- next slice may not wire into `getNoiseBiome(...)`.
- next slice may not make `getNoiseBiome(...)` usable.

## Next-Decision Values

- `PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT`
- `DEFER_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_IMPLEMENTATION`
- `DEFER_WEIGHTED_SELECTOR_CALLS`
- `DEFER_ADAPTER_CALLS`
- `DEFER_GET_NOISE_BIOME_WIRING`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOLUTION`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Guardrail Order

- `ALLOW_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT`
- `ALLOW_SELECTOR_INPUT_SURFACE_POLICY_PINNING_NEXT`
- `ALLOW_WEIGHTED_SELECTOR_QUERY_POLICY_PINNING_NEXT`
- `ALLOW_ADAPTER_QUERY_POLICY_PINNING_NEXT`
- `ALLOW_CANDIDATE_KEY_OUTPUT_POLICY_PINNING_NEXT`
- `NO_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_IMPLEMENTATION`
- `NO_WEIGHTED_SELECTOR_CALL_WITH_DERIVED_INPUT`
- `NO_ADAPTER_CALL_WITH_DERIVED_INPUT`
- `NO_GET_NOISE_BIOME_WIRING`
- `NO_GET_NOISE_BIOME_IMPLEMENTATION`
- `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_HOLDER_RESOLUTION`
- `NO_RESOURCE_LOCATION_OR_KEY_CONVERSION`
- `NO_FALLBACK_IMPLEMENTATION`
- `NO_USABLE_CODEC_OR_REGISTRATION`
- `NO_DIMENSION_ACTIVATION`
- `NO_WORLDGEN_ACCESS_SPAWNING`
- `KEEP_SELECTOR_INPUT_ALGORITHM_INERT`

## Guardrail Fields

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSliceReadinessAction`
- `allowsNextSliceBridgeImplementation`
- `allowsNextSliceRuntimeAction`
- `blocksActivation`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision` exposes deterministic helpers for decision state, guardrail lookup, allowed next-slice readiness-only actions, blocked bridge/runtime actions, selector-input algorithm consolidation state, weighted-selector and adapter compatibility, current readiness-layer counts, blocked runtime-biome-source surfaces, dimension/resource absence and activation blocking.

Key helpers include:

- `selectedDecision()`
- `decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext()`
- `decisionIsReadinessOnlyForNextSlice()`
- `selectorToWeightedCandidateBridgeReadinessImplementedInThisSlice()`
- `selectorToWeightedCandidateBridgeImplementationReady()`
- `selectorToWeightedCandidateBridgeRuntimeReady()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(...)`
- `guardrailCount()`
- `allowedNextSliceReadinessActionCount()`
- `allowedNextSliceBridgeImplementationActionCount()`
- `allowedNextSliceRuntimeActionCount()`
- `nextSliceMayAddSelectorToWeightedCandidateBridgeReadiness()`
- `nextSliceMayPinSelectorInputSurfacePolicy()`
- `nextSliceMayPinWeightedSelectorQueryPolicy()`
- `nextSliceMayPinAdapterQueryPolicy()`
- `nextSliceMayPinCandidateKeyOutputPolicy()`
- `nextSliceMayImplementSelectorToWeightedCandidateBridge()`
- `nextSliceMayCallWeightedSelectorWithDerivedInput()`
- `nextSliceMayCallAdapterWithDerivedInput()`
- `nextSliceMayWireIntoGetNoiseBiome()`
- `selectorInputAlgorithmConsolidationReady()`
- `selectorInputAlgorithmSelectedNextDecisionMatches()`
- `selectorInputAlgorithmReady()`
- `selectorInputOutputShape()`
- `weightedSelectorCompatibilityReady()`
- `weightedSelectionTotalWeight()`
- `adapterCompatibilityReady()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `implementationGoNoGoDecisionReady()`
- `readinessChainConsolidationReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `holderResolutionReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
- `possibleBiomesReadinessReady()`
- `fallbackPolicyReadinessReady()`
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
- `dimensionBindingReady()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Facts

- selector-input algorithm consolidation remains ready.
- pure selector-input algorithm remains ready.
- weighted selector compatibility remains ready.
- adapter compatibility remains ready.
- selector-to-weighted-candidate bridge is not implemented.
- bridge runtime behavior is not ready.
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

This is still not selector-to-weighted-candidate bridge readiness because this slice selects only whether readiness is allowed next.

This is still not selector-to-weighted-candidate bridge implementation because this slice does not call the weighted selector or adapter with derived selector input.

This is still not coordinate/climate runtime selection because the sampler remains excluded and the guarded runtime subclass stays unsupported.

This is still not usable noise-biome runtime behavior because `CaveniaRuntimeBiomeSource.getNoiseBiome(...)` remains unsupported and unwired.

This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains unsupported and no holder stream exists.

This is still not usable runtime biome resolution because registry lookup access, holder resolution and resource-key conversion remain absent.

This is still not a usable runtime biome source because codec implementation, codec registration, biome-source type registration, dimension binding and activation all remain absent.

This is still not a `ChunkGenerator` because this slice does not add generator code, generator registration or any active runtime generator surface.

No registry lookup access was added because the new layer is only a guarded inert decision contract.

No holder/resource-key conversion implementation was added because runtime biome resolution remains out of scope.

No fallback implementation was added because no runtime candidate-resolution bridge exists yet.

No usable codec implementation was added because runtime biome-source serialization remains deferred.

No codec or registry entry was added because the guarded runtime subclass remains unregistered and non-activating.

No dimension JSON/type JSON was added because this slice remains fully inert.

No active generator was added because bridge readiness is still only a future decision boundary.

No active Cavenia level was added because activation remains blocked.

No access, spawning or worldgen resources were added because those surfaces remain deferred behind later explicit slices.

## Future Recommendation

- next slice can be `Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Readiness MVP`.
- that next slice should remain readiness-only unless explicitly requested otherwise.
- that next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.

The selector-to-weighted-candidate bridge readiness follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md`.

The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
