# Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Consolidation / Next Decision MVP

This is selector-to-weighted-candidate bridge consolidation and next-decision, not holder conversion.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice consolidates the current pure selector-to-weighted-candidate bridge state and selects the next safe decision-only branch while keeping all guarded runtime methods unsupported and unwired.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationNextDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`

## Component Enum Values

- `PURE_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READY`
- `SIGNED_INT_SELECTOR_INPUT_READY`
- `WEIGHTED_SELECTOR_OR_ADAPTER_PATH_READY`
- `STRING_CANDIDATE_KEY_OUTPUT_READY`
- `CANDIDATE_INVENTORY_COMPATIBILITY_READY`
- `SAMPLE_CANDIDATE_KEYS_VERIFIED`
- `CANDIDATE_KEYS_REMAIN_STRING_ONLY`
- `NO_GET_NOISE_BIOME_WIRING`
- `NO_HOLDER_CONVERSION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_RESOURCE_LOCATION_OR_KEY_CONVERSION`
- `RUNTIME_SELECTION_BLOCKED`
- `CODEC_AND_REGISTRATION_BLOCKED`
- `DIMENSION_WORLDGEN_ACCESS_SPAWNING_BLOCKED`
- `NEXT_DECISION_SELECTED`

## Next-Decision Enum Values

- `PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_GO_NO_GO_NEXT`
- `DEFER_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS`
- `DEFER_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOLUTION`
- `DEFER_RESOURCE_LOCATION_CONVERSION`
- `DEFER_RESOURCE_KEY_CONVERSION`
- `DEFER_GET_NOISE_BIOME_WIRING`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Entry Fields

- `component`
- `sourceContractName`
- `consolidatedState`
- `nextBoundary`
- `bridgeReady`
- `runtimeReady`
- `nextDecisionInput`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation` exposes deterministic helpers for:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `selectorToWeightedCandidateBridgeConsolidationReady()`
- `selectorToWeightedCandidateBridgeReady()`
- `selectorToWeightedCandidateBridgeRuntimeReady()`
- `pureNonRuntimeBridgeReady()`
- `signedIntSelectorInputReady()`
- `weightedSelectorOrAdapterPathReady()`
- `stringCandidateKeyOutputReady()`
- `candidateInventoryCompatibilityReady()`
- `sampleCandidateKeysVerified()`
- `candidateKeysRemainStringOnly()`
- `candidateKeyToHolderConversionGoNoGoIsNext()`
- `candidateKeyToHolderConversionReadinessReady()`
- `candidateKeyToHolderConversionImplementationReady()`
- `candidateKeyToHolderConversionRuntimeReady()`
- `selectedNextDecision()`
- `nextSliceMayAddCandidateKeyToHolderConversionGoNoGoDecision()`
- `nextSliceMayAddCandidateKeyToHolderConversionReadiness()`
- `nextSliceMayImplementCandidateKeyToHolderConversion()`
- `nextSliceMayUseRegistryLookupAccess()`
- `nextSliceMayResolveHolders()`
- `nextSliceMayImplementResourceLocationConversion()`
- `nextSliceMayImplementResourceKeyConversion()`
- `nextSliceMayWireIntoGetNoiseBiome()`
- `nextSliceMayMakeGetNoiseBiomeUsable()`
- `nextSliceMayMakeCollectPossibleBiomesUsable()`
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
- `selectorInputOutputShape()`
- `candidateKeyOutputShape()`
- `weightedSelectionTotalWeight()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateModernBiomeKeys()`
- `candidateKeyForSampleOrigin()`
- `candidateKeyForSampleMixed()`
- `candidateKeyForSampleNegative()`
- `sampleKeysExistInInventory()`
- `expectedSampleOriginCandidateKey()`
- `expectedSampleMixedCandidateKey()`
- `expectedSampleNegativeCandidateKey()`
- `selectorInputAlgorithmReady()`
- `selectorInputAlgorithmRuntimeReady()`
- `weightedSelectorCompatibilityReady()`
- `adapterCompatibilityReady()`
- `adapterRuntimeReady()`
- `bridgeImplementationGoNoGoDecisionReady()`
- `bridgeImplementationGoNoGoRuntimeReady()`
- `bridgeReadinessReady()`
- `bridgeReadinessRuntimeReady()`
- `bridgeGoNoGoDecisionReady()`
- `bridgeGoNoGoRuntimeReady()`
- `selectorInputAlgorithmConsolidationReady()`
- `selectorInputAlgorithmConsolidationRuntimeReady()`
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesRuntimeReady()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicyRuntimeReady()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
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

## Consolidated Facts

- selector-to-weighted-candidate bridge consolidation is ready.
- pure selector-to-weighted-candidate bridge is ready.
- bridge output remains string modern biome candidate key.
- bridge output is compatible with current candidate inventory.
- sample candidate keys are verified:
  - origin -> `minecraft:taiga`
  - mixed -> `minecraft:desert`
  - negative -> `minecraft:desert`
- candidate keys remain string-only.
- selected next decision is `PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_GO_NO_GO_NEXT`.
- next slice may add candidate-key-to-holder conversion go/no-go decision.
- next slice may not add candidate-key-to-holder conversion readiness.
- next slice may not implement candidate-key-to-holder conversion.
- next slice may not use registry lookup access.
- next slice may not resolve holders.
- next slice may not implement resource-location conversion.
- next slice may not implement resource-key conversion.
- next slice may not wire into `getNoiseBiome(...)`.
- next slice may not make `getNoiseBiome(...)` usable.
- bridge imports no Minecraft runtime APIs.
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
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`
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

## Why This Is Still Not Holder Conversion

- This is still not candidate-key-to-holder conversion go/no-go because it only consolidates the current bridge and selects the next branch.
- This is still not candidate-key-to-holder conversion readiness because it adds no new holder-conversion readiness contract.
- This is still not candidate-key-to-holder conversion implementation because it adds no conversion behavior at all.
- This is still not registry lookup access because lookup surfaces remain blocked and absent.
- This is still not holder resolution because string candidate output remains intentionally unresolved.
- This is still not resource-location or resource-key conversion because candidate keys remain string-only.
- This is still not runtime selection because the bridge remains unwired from `getNoiseBiome(...)`.
- This is still not usable noise-biome runtime behavior because no holder-return path exists.
- This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains unsupported.
- This is still not usable runtime biome resolution because no registry-backed conversion path exists.
- This is still not a usable runtime biome source because codec implementation, registration and activation remain absent.
- This is still not a `ChunkGenerator` because no generator or dimension activation work was added.
- No usable codec implementation was added because codec work remains outside this slice.
- No codec or registry entry was added because activation remains blocked.
- No dimension JSON or dimension type JSON was added because no runtime activation work was opened.
- No active generator was added because worldgen activation remains absent.
- No active Cavenia level was added because the dimension remains inactive.
- No access, spawning or worldgen resources were added because this slice stays bounded to consolidation and next-decision only.

## Future Recommendation

- The next slice can be `Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Go/No-Go MVP`.
- That next slice should remain decision-only unless explicitly requested otherwise.
- That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.

## Follow-Ups

- The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision note is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
- The candidate-key-to-holder conversion readiness follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-readiness-mvp.md`.
- The candidate-key-to-holder conversion implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-implementation-go-no-go-mvp.md`.
- The guarded converter implementation follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md`.
- The converter consolidation next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The collect-possible-biomes holder-set go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
