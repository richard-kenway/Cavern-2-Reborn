# Cavenia Runtime BiomeSource Selector Input Derivation Pure Non-Runtime Algorithm MVP

This slice is pure non-runtime selector-input derivation algorithm, not runtime selection.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice adds only a deterministic pure Java selector-input helper for future `getNoiseBiome(...)` selection and keeps every runtime biome-source surface inert.

## Added Type

- `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`

## Component Order

- `INTEGER_COORDINATE_INPUTS`
- `CLIMATE_SAMPLER_EXCLUDED`
- `PURE_NON_RUNTIME_ALGORITHM`
- `DETERMINISTIC_MIXING_FORMULA`
- `SIGNED_INT_SELECTOR_OUTPUT`
- `NORMALIZATION_DELEGATED`
- `NEGATIVE_COORDINATE_STABILITY`
- `CHUNK_BOUNDARY_STABILITY`
- `NO_WORLD_OR_REGISTRY_DEPENDENCY`
- `NO_RANDOM_OR_MUTABLE_STATE`
- `NO_GET_NOISE_BIOME_WIRING`
- `REAL_RUNTIME_SELECTION_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `algorithmDecision`
- `runtimeBoundary`
- `algorithmReady`
- `runtimeReady`
- `minecraftRuntimeApiRequired`
- `activationAllowedInThisSlice`
- `blocker`

## Constants

- `0x9E3779B97F4A7C15L`
- `0xBF58476D1CE4E5B9L`
- `0x94D049BB133111EBL`
- `0xD6E8FEB86659FD93L`

## Formula

- Start with `INITIAL_SEED`.
- XOR `Integer.toUnsignedLong(x) * X_MULTIPLIER`.
- Apply `mix64`.
- XOR `Integer.toUnsignedLong(y) * Y_MULTIPLIER`.
- Apply `mix64`.
- XOR `Integer.toUnsignedLong(z) * Z_MULTIPLIER`.
- Apply `mix64`.
- Return the final mixed value narrowed to `int`.

`mix64` uses:

- `value ^= value >>> 30`
- `value *= 0xBF58476D1CE4E5B9L`
- `value ^= value >>> 27`
- `value *= 0x94D049BB133111EBL`
- `value ^= value >>> 31`

Java `long` overflow is intentional.

## Test Vectors

- `deriveSelectorInput(0, 0, 0) == -104499101`
- `deriveSelectorInput(1, 0, 0) == -1057403244`
- `deriveSelectorInput(0, 1, 0) == -1906836072`
- `deriveSelectorInput(0, 0, 1) == 1179339926`
- `deriveSelectorInput(1, 2, 3) == 700186390`
- `deriveSelectorInput(-1, 0, 0) == -1698937329`
- `deriveSelectorInput(0, -1, 0) == 1171624622`
- `deriveSelectorInput(0, 0, -1) == 38125441`
- `deriveSelectorInput(-16, 64, 16) == -1039724642`
- `deriveSelectorInput(12345, -67, 89012) == 1320310950`
- `deriveSelectorInput(Integer.MAX_VALUE, Integer.MIN_VALUE, 0) == 602747642`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm` exposes deterministic helpers including:

- `deriveSelectorInput(int x, int y, int z)`
- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `selectorInputDerivationAlgorithmReady()`
- `pureNonRuntimeAlgorithmReady()`
- `integerCoordinateInputsReady()`
- `climateSamplerExcluded()`
- `climateSamplerMethodsCalled()`
- `minecraftRuntimeApiImportsRequired()`
- `deterministicMixingFormulaReady()`
- `signedIntSelectorOutputReady()`
- `normalizationDelegated()`
- `negativeCoordinateStable()`
- `chunkBoundaryStable()`
- `worldOrRegistryDependencyPresent()`
- `randomOrMutableStateDependencyPresent()`
- `getNoiseBiomeWiringReady()`
- `runtimeSelectionReady()`
- `realRuntimeSelectionStillDeferred()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `initialSeed()`
- `xMultiplier()`
- `yMultiplier()`
- `zMultiplier()`
- `selectorInputOutputShape()`
- `normalizationPolicy()`
- `algorithmInputPolicy()`
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

## Pinned Facts

- The algorithm accepts only integer `x/y/z`.
- The algorithm excludes `Climate.Sampler`.
- The algorithm imports no Minecraft runtime APIs.
- The algorithm returns signed int selector input.
- normalization remains delegated to existing selector/adapter.
- The algorithm is deterministic.
- The algorithm handles negative coordinates deterministically.
- The algorithm is chunk-boundary stable because it depends only on provided integers.
- The algorithm uses no random, wall-clock, mutable global state, registry, world, server, or holder state.
- The algorithm is not wired into `getNoiseBiome(...)`.
- runtime selection remains not ready.
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
- The subclass remains guarded, unregistered, and non-activating.
- normal runtime construction remains unavailable.
- no dimension JSON/type JSON exist.
- no active Cavenia level exists.
- no worldgen resources exist.
- no access/teleport exists.
- no spawning exists.
- `cavernreborn:caveman` remains absent.
- `EntityCaveman -> deferred:caveman` remains unchanged.
- Selected-surface readiness item count remains `68`.
- Global readiness matrix total/blocked count remains `46/46`.

## Relationships

- `CaveniaRuntimeBiomeSource`
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

This is still not coordinate/climate runtime selection because the helper consumes only integer coordinates and never touches the pinned sampler surface.

This is still not usable noise-biome runtime behavior because `CaveniaRuntimeBiomeSource.getNoiseBiome(...)` stays unsupported and does not call the algorithm.

This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` stays unsupported and there is still no holder-backed runtime stream.

This is still not usable runtime biome resolution because registry lookup, holder resolution and resource-key conversion remain absent.

This is still not a usable runtime biome source because codec implementation, codec registration, biome-source type registration, dimension binding and activation all remain absent.

This is still not a `ChunkGenerator` because the slice adds only a pure selector-input helper and no runtime generator type.

No registry lookup access was added because the algorithm remains a pure Java helper with no runtime resolution path.

No holder/resource-key conversion implementation was added because the algorithm returns only a signed int selector input.

No fallback implementation was added because the helper does not resolve or return biome outputs.

No usable codec implementation was added because codec work remains outside this pure helper slice.

No codec or registry entry was added because this slice does not activate runtime biome-source construction.

No dimension JSON/type JSON was added because the slice keeps dimension activation absent.

No active generator was added because the helper is not wired into runtime worldgen.

No active Cavenia level was added because runtime activation remains blocked.

No access, spawning or worldgen resources were added because the slice stays pure, non-runtime and non-activating.

## Recommendation

The next follow-up can be `Cavenia Runtime BiomeSource Selector Input Algorithm Consolidation / Next Decision MVP`.

That next slice should decide whether to bridge the selector input to the existing weighted selector/adapter path.

That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.

The selector-input algorithm consolidation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md`.
The selector-to-weighted-candidate bridge go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md`.

The selector-to-weighted-candidate bridge readiness follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md`.

The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
