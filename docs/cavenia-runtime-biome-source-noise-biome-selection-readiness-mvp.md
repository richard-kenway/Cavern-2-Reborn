# Cavenia Runtime BiomeSource Noise-Biome Selection Readiness MVP

This slice is noise-biome selection readiness, not implementation.

`CaveniaRuntimeBiomeSource.getNoiseBiome(...)` still does not produce runtime biome values. This slice only pins the future policy for how the guarded subclass may eventually select a candidate biome and return a holder-backed runtime biome.

## Scope

- future `getNoiseBiome(...)` method shape is pinned
- future coordinate input shape is pinned
- future climate sampler input shape is pinned
- future coordinate/climate-to-weight-value derivation remains deferred
- future weighted selection source is `CaveniaWeightedBiomeSelectionAlgorithm`
- future adapter selection source is `CaveniaBiomeSelectionAdapterContract`
- future candidate key output source is current string candidate inventory
- future holder conversion dependency is holder conversion readiness
- future holder conversion dependency is `CaveniaRuntimeBiomeSourceHolderConversionReadiness`
- future fallback policy source is fallback policy readiness
- future fallback policy source is `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- future possible-biomes constraint source is possible-biomes readiness
- future possible-biomes constraint source is `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`
- no runtime holder return exists
- `getNoiseBiome(...)` remains unsupported
- `collectPossibleBiomes()` remains unsupported
- `codec()` remains unsupported

## Added Types

- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent`
- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry`
- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`

## Component Order

- `NOISE_BIOME_METHOD_SHAPE`
- `COORDINATE_INPUT_SHAPE`
- `CLIMATE_SAMPLER_INPUT_SHAPE`
- `WEIGHT_VALUE_DERIVATION_DECISION`
- `WEIGHTED_SELECTION_SOURCE`
- `ADAPTER_SELECTION_SOURCE`
- `CANDIDATE_KEY_OUTPUT_SOURCE`
- `HOLDER_CONVERSION_DEPENDENCY`
- `FALLBACK_POLICY_SOURCE`
- `POSSIBLE_BIOMES_CONSTRAINT`
- `NO_RUNTIME_HOLDER_RETURN`
- `NO_GET_NOISE_BIOME_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `REAL_NOISE_BIOME_SELECTION_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `readinessDecision`
- `expectedFutureBehavior`
- `readinessPinned`
- `implementationReady`
- `registryLookupRequired`
- `registryLookupAvailable`
- `noiseBiomeRuntimeReady`
- `activationAllowedInThisSlice`
- `blocker`

## Future Policy

The future `getNoiseBiome(...)` method shape is pinned from `CaveniaRuntimeBiomeSourceApiShapeInventory`.

- runtime method name is `getNoiseBiome`
- runtime method signature is `getNoiseBiome(int x, int y, int z, Climate.Sampler sampler)`
- coordinate input shape is pinned for `x`, `y` and `z`
- climate sampler input shape is pinned for `Climate.Sampler`
- future `getNoiseBiome(...)` selection should select a candidate key deterministically before holder conversion

The future selector path is pinned to the existing pure selection chain.

- future weighted selection source is `CaveniaWeightedBiomeSelectionAlgorithm`
- future adapter selection source is `CaveniaBiomeSelectionAdapterContract`
- future candidate key output source is the current string candidate inventory from `CaveniaLegacyToModernBiomeKeyMappings`
- future coordinate/climate-to-weight-value derivation remains deferred
- deterministic coordinate/climate-to-weight-value derivation is required before runtime `getNoiseBiome(...)` can become usable

The future holder-return path stays blocked behind existing readiness layers.

- future holder conversion dependency is `CaveniaRuntimeBiomeSourceHolderConversionReadiness`
- future fallback policy source is `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- future possible-biomes constraint source is `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`
- future noise-biome results should resolve to candidates compatible with future possible-biomes holder outputs
- future fallback should be used only if selection/conversion/holder resolution cannot produce a valid runtime holder
- future return type remains `Holder<Biome>`

registry lookup is required for future runtime noise-biome holder returns but is not available in this slice.

- no resource-location conversion implementation exists
- no resource-key conversion implementation exists
- no holder resolution exists
- no runtime conversion implementation exists
- no usable possible-biomes behavior exists
- no usable noise-biome behavior exists

## Helper Surface

`CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness` exposes deterministic helpers including:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeMethodShapePinned()`
- `coordinateInputShapePinned()`
- `climateSamplerInputShapePinned()`
- `weightValueDerivationDecisionPinned()`
- `weightedSelectionSourcePinned()`
- `adapterSelectionSourcePinned()`
- `candidateKeyOutputSourcePinned()`
- `holderConversionDependencyPinned()`
- `fallbackPolicySourcePinned()`
- `possibleBiomesConstraintPinned()`
- `runtimeHolderReturnReady()`
- `getNoiseBiomeImplementationReady()`
- `registryLookupRequiredForRuntimeNoiseBiome()`
- `registryLookupAccessReady()`
- `noiseBiomeRuntimeReady()`
- `realNoiseBiomeSelectionStillDeferred()`
- `noiseBiomeMethodName()`
- `noiseBiomeMethodSignature()`
- `coordinateInputPolicy()`
- `climateSamplerInputPolicy()`
- `weightValueDerivationPolicy()`
- `weightedSelectionAlgorithmReady()`
- `weightedSelectionRuntimeReady()`
- `weightedSelectionTotalWeight()`
- `adapterShapeReady()`
- `adapterRuntimeReady()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `candidateModernBiomeKeys()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
- `holderResolutionReady()`
- `resourceKeyConversionReady()`
- `resourceLocationConversionReady()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicyRuntimeReady()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesRuntimeReady()`
- `possibleBiomesConstraintPolicy()`
- `codecMethodShapeStubReady()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `normalRuntimeConstructionAllowed()`
- `unsupportedMethodStubsOnly()`
- `collectPossibleBiomesStubbed()`
- `getNoiseBiomeStubbed()`
- `codecMethodStubbed()`
- `goNoGoGuardrailsEnforced()`
- `apiShapeInventoryReady()`
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

This slice is still not usable noise-biome runtime behavior.

- no runtime holder return exists
- `getNoiseBiome(...)` remains unsupported
- `collectPossibleBiomes()` remains unsupported
- `codec()` remains unsupported
- no usable possible-biomes behavior exists
- no usable runtime biome resolution exists
- no usable runtime biome source exists
- this is still not a `ChunkGenerator`
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- no registry lookup access was added
- no holder/resource-key conversion implementation was added
- no fallback implementation was added
- no codec/registry entry was added
- no dimension JSON/type JSON was added
- no active generator was added
- no active Cavenia level was added
- no access/spawning/worldgen resources were added
- subclass remains guarded, unregistered, and non-activating
- normal runtime construction remains unavailable

## Relationship To Existing Contracts

This slice builds directly on:

- `CaveniaRuntimeBiomeSource`
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
- no dimension JSON/type JSON exist
- no active Cavenia level exists
- no worldgen resources exist
- no access/teleport exists
- no spawning exists
- `cavernreborn:caveman` remains absent
- `EntityCaveman -> deferred:caveman` remains unchanged

## Follow-Up

- next slice can make a consolidation decision for the guarded runtime biome-source readiness chain
- a separate explicit decision could also define non-runtime selector input derivation while keeping registry lookup and activation absent

The readiness-chain consolidation / next-decision follow-up is now documented in `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md`.

The selector-input derivation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md`.

The selector-input derivation readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md`.

The selector-input derivation implementation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md`.

The pure non-runtime selector-input derivation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md`.

The selector-input algorithm consolidation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md`.
The selector-to-weighted-candidate bridge go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md`.

The selector-to-weighted-candidate bridge readiness follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md`.

The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
- The candidate-key-to-holder conversion readiness follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-readiness-mvp.md`.
- The candidate-key-to-holder conversion implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-implementation-go-no-go-mvp.md`.
- The guarded converter implementation follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md`.
- The converter consolidation next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The collect-possible-biomes holder-set readiness follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
- The collect-possible-biomes holder-set implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-implementation-go-no-go-mvp.md`.
