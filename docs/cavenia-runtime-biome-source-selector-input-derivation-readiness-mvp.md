# Cavenia Runtime BiomeSource Selector Input Derivation Readiness MVP

This slice is selector-input derivation readiness, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice only pins the future selector-input policy surface for the guarded `getNoiseBiome(...)` path.

## Pinned Future Surface

- future method input source is `getNoiseBiome(int x, int y, int z, Climate.Sampler sampler)`
- future coordinate input policy is pinned
- future climate sampler input policy is pinned
- future selector input output shape is signed int weight value accepted by the existing adapter and weighted selector path
- future deterministic derivation policy is pinned
- future pure non-runtime policy is pinned
- future weighted selector compatibility is pinned
- future adapter query compatibility is pinned
- future normalization policy delegates to existing selector/adapter
- future negative coordinate stability policy is pinned
- future chunk-boundary stability policy is pinned
- future no-world-or-registry dependency policy is pinned
- future no-random-or-mutable-state dependency policy is pinned

actual derivation formula is not pinned.

actual derivation formula is not implemented.

selector-input derivation implementation is not ready.

selector-input derivation runtime behavior is not ready.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness`

## Component Order

- `METHOD_INPUT_SHAPE`
- `COORDINATE_INPUT_POLICY`
- `CLIMATE_SAMPLER_INPUT_POLICY`
- `SELECTOR_INPUT_OUTPUT_SHAPE`
- `DETERMINISTIC_DERIVATION_POLICY`
- `PURE_NON_RUNTIME_POLICY`
- `WEIGHTED_SELECTOR_COMPATIBILITY`
- `ADAPTER_QUERY_COMPATIBILITY`
- `NORMALIZATION_POLICY`
- `NEGATIVE_COORDINATE_STABILITY_POLICY`
- `CHUNK_BOUNDARY_STABILITY_POLICY`
- `NO_WORLD_OR_REGISTRY_DEPENDENCY`
- `NO_RANDOM_OR_MUTABLE_STATE_DEPENDENCY`
- `NO_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION`
- `NO_RUNTIME_SELECTION`
- `REAL_SELECTOR_INPUT_DERIVATION_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `inputSurface`
- `futurePolicy`
- `readinessPinned`
- `implementationReady`
- `runtimeSelectionReady`
- `runtimeApiUsageAllowedInThisSlice`
- `activationAllowedInThisSlice`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness` exposes deterministic helpers including:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `selectorInputDerivationReadinessReady()`
- `methodInputShapePinned()`
- `coordinateInputPolicyPinned()`
- `climateSamplerInputPolicyPinned()`
- `selectorInputOutputShapePinned()`
- `deterministicDerivationPolicyPinned()`
- `pureNonRuntimePolicyPinned()`
- `weightedSelectorCompatibilityPinned()`
- `adapterQueryCompatibilityPinned()`
- `normalizationPolicyPinned()`
- `negativeCoordinateStabilityPolicyPinned()`
- `chunkBoundaryStabilityPolicyPinned()`
- `noWorldOrRegistryDependencyPinned()`
- `noRandomOrMutableStateDependencyPinned()`
- `selectorInputDerivationImplementationReady()`
- `selectorInputDerivationRuntimeReady()`
- `coordinateRuntimeSelectionReady()`
- `climateSamplerRuntimeSelectionReady()`
- `realSelectorInputDerivationStillDeferred()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `methodInputSource()`
- `coordinateInputPolicy()`
- `climateSamplerInputPolicy()`
- `selectorInputOutputShape()`
- `deterministicDerivationPolicy()`
- `pureNonRuntimePolicy()`
- `normalizationPolicy()`
- `negativeCoordinateStabilityPolicy()`
- `chunkBoundaryStabilityPolicy()`
- `worldOrRegistryDependencyPolicy()`
- `randomOrMutableStatePolicy()`
- `actualDerivationFormulaPinned()`
- `actualDerivationFormulaImplemented()`
- `goNoGoDecisionReady()`
- `goNoGoRuntimeReady()`
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `noiseBiomeMethodShapePinned()`
- `weightValueDerivationDecisionPinned()`
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

readiness-chain consolidation remains ready.

selector-input derivation go/no-go decision remains ready.

all current readiness layers are ready.

no runtime layer is ready.

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

Why no registry lookup access was added: future selector-input derivation must stay pure and non-runtime before any holder conversion.

Why no holder/resource-key conversion implementation was added: that conversion path remains separately blocked by holder-conversion readiness.

Why no fallback implementation was added: fallback remains readiness-only and is still not a runtime behavior surface.

Why no usable codec implementation was added: codec work is still blocked behind the inert codec-shape and registration chain.

Why no codec/registry entry was added: the guarded subclass remains unregistered and non-activating.

Why no dimension JSON/type JSON was added: this slice is only about inert selector-input policy data.

Why no active generator was added: this slice does not touch generator activation or runtime terrain code.

Why no active Cavenia level was added: activation remains blocked by the existing readiness chain.

Why no access/spawning/worldgen resources were added: selector-input readiness must stay isolated from world/runtime resource activation.

## Global Inert State

- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`

## Follow-Up

- next slice can be `Cavenia Runtime BiomeSource Selector Input Derivation Implementation Go/No-Go MVP`
- that next slice should decide whether to allow a pure non-runtime selector-input derivation algorithm
- that next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise

The selector-input derivation implementation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md`.

The pure non-runtime selector-input derivation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md`.

The selector-input algorithm consolidation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md`.
The selector-to-weighted-candidate bridge go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md`.

The selector-to-weighted-candidate bridge readiness follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md`.

The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
