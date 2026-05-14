# Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Pure Non-Runtime Implementation MVP

This is pure non-runtime selector-to-weighted-candidate bridge implementation, not runtime selection.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice adds only a pure Java bridge helper from signed int selector input to string modern biome candidate key while keeping all guarded runtime methods unsupported and unwired.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`

## Component Enum Values

- `SIGNED_INT_SELECTOR_INPUT`
- `PURE_NON_RUNTIME_BRIDGE`
- `WEIGHTED_SELECTOR_OR_ADAPTER_PATH`
- `STRING_CANDIDATE_KEY_OUTPUT`
- `CANDIDATE_INVENTORY_COMPATIBILITY`
- `NORMALIZATION_DELEGATED_TO_SELECTOR_PATH`
- `DETERMINISTIC_BRIDGE_OUTPUT`
- `NO_MINECRAFT_RUNTIME_API_DEPENDENCY`
- `NO_REGISTRY_OR_HOLDER_DEPENDENCY`
- `NO_FALLBACK_RUNTIME_USAGE`
- `NO_GET_NOISE_BIOME_WIRING`
- `REAL_RUNTIME_SELECTION_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `bridgeDecision`
- `runtimeBoundary`
- `bridgeReady`
- `runtimeReady`
- `minecraftRuntimeApiRequired`
- `activationAllowedInThisSlice`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge` exposes deterministic helpers for:

- `candidateKeyForSelectorInput(int selectorInput)`
- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `selectorToWeightedCandidateBridgeReady()`
- `pureNonRuntimeBridgeReady()`
- `signedIntSelectorInputReady()`
- `weightedSelectorOrAdapterPathReady()`
- `stringCandidateKeyOutputReady()`
- `candidateInventoryCompatibilityReady()`
- `normalizationDelegatedToSelectorPath()`
- `deterministicBridgeOutputReady()`
- `minecraftRuntimeApiDependencyPresent()`
- `registryOrHolderDependencyPresent()`
- `fallbackRuntimeUsagePresent()`
- `getNoiseBiomeWiringReady()`
- `runtimeSelectionReady()`
- `realRuntimeSelectionStillDeferred()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `selectorInputOutputShape()`
- `candidateKeyOutputShape()`
- `bridgeRuntimeBoundary()`
- `bridgeImplementationGoNoGoDecisionReady()`
- `bridgeImplementationGoNoGoRuntimeReady()`
- `bridgeReadinessReady()`
- `bridgeReadinessRuntimeReady()`
- `selectorInputAlgorithmReady()`
- `selectorInputAlgorithmRuntimeReady()`
- `deriveSelectorInputSampleOrigin()`
- `deriveSelectorInputSampleMixed()`
- `deriveSelectorInputSampleNegative()`
- `candidateKeyForSampleOrigin()`
- `candidateKeyForSampleMixed()`
- `candidateKeyForSampleNegative()`
- `sampleKeysExistInInventory()`
- `weightedSelectorCompatibilityReady()`
- `weightedSelectionTotalWeight()`
- `adapterCompatibilityReady()`
- `adapterRuntimeReady()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `candidateModernBiomeKeys()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicyRuntimeReady()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesRuntimeReady()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
- `holderResolutionReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
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

## Bridge Facts

- bridge accepts signed int selector input.
- bridge returns string modern biome candidate key.
- bridge uses existing pure weighted selector or adapter or mapping path.
- bridge output remains string-only.
- bridge output exists in current candidate inventory.
- total selector weight remains `675`.
- candidate entry count remains `14`.
- normalization remains delegated to existing selector or adapter path.
- bridge is deterministic.
- bridge imports no Minecraft runtime APIs.
- bridge does not use registry lookup.
- bridge does not resolve holders.
- bridge does not use resource locations or resource keys.
- bridge does not call fallback runtime behavior.
- bridge is not wired into `getNoiseBiome(...)`.
- runtime selection remains not ready.

## Current Facts

- selector-to-weighted-candidate bridge implementation go/no-go decision remains ready.
- selector-to-weighted-candidate bridge readiness remains ready.
- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
- `codec()` remains unsupported.
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
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness`
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

## Why This Is Still Not Runtime Selection

- This is still not runtime selection because the bridge accepts no climate sampler input and remains pure Java only.
- This is still not coordinate or climate runtime selection because `CaveniaRuntimeBiomeSource.getNoiseBiome(...)` does not call the bridge.
- This is still not usable noise-biome runtime behavior because no `Holder<Biome>` return path exists.
- This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains unsupported.
- This is still not usable runtime biome resolution because no registry lookup, resource-location conversion, resource-key conversion or holder resolution exists.
- This is still not a usable runtime biome source because codec implementation, registration and activation remain absent.
- This is still not a `ChunkGenerator` because no generator or dimension activation work was added.
- No registry lookup access was added because this slice is bounded to pure non-runtime selection only.
- No holder or resource-key conversion implementation was added because candidate output remains string-only.
- No fallback implementation was added because the bridge performs normal selection only.
- No usable codec implementation was added because codec work remains outside this slice.
- No codec or registry entry was added because activation remains blocked.
- No dimension JSON or dimension type JSON was added because no runtime activation work was opened.
- No active generator was added because worldgen activation remains absent.
- No active Cavenia level was added because the dimension remains inactive.
- No access, spawning or worldgen resources were added because the bridge remains non-runtime and inert.

## Future Recommendation

- The next slice can be `Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Consolidation / Next Decision MVP`.
- That next slice should decide whether to move toward candidate-key-to-holder conversion go or no-go or stop for review.
- That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.

## Follow-Ups

- The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation note is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
