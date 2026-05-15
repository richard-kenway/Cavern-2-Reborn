# Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Implementation Go/No-Go MVP

This is selector-to-weighted-candidate bridge implementation go/no-go decision, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice decides only whether the next slice may add a pure non-runtime selector-to-weighted-candidate bridge helper while keeping all guarded runtime methods unsupported and unwired.

## Added Types

- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision`

## Selected Decision

- selected decision is `PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NEXT`.
- next slice may add a pure non-runtime selector-to-weighted-candidate bridge.
- next slice may accept signed int selector input.
- next slice may use existing weighted selector/adapter if they remain pure and non-runtime.
- next slice may return string modern biome candidate key.
- next slice may use candidate inventory data.
- next slice may not import Minecraft runtime APIs.
- next slice may not wire into `getNoiseBiome(...)`.
- next slice may not make `getNoiseBiome(...)` usable.
- next slice may not make `collectPossibleBiomes()` usable.
- next slice may not use registry lookup access.
- next slice may not resolve holders.
- next slice may not implement resource-location conversion.
- next slice may not implement resource-key conversion.
- next slice may not add fallback implementation.
- this slice does not implement the bridge.
- this slice does not produce output candidate keys.

## Next-Decision Enum Values

- `PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NEXT`
- `DEFER_GET_NOISE_BIOME_WIRING`
- `DEFER_HOLDER_CONVERSION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Guardrail Enum Values

- `ALLOW_PURE_NON_RUNTIME_BRIDGE_NEXT`
- `ALLOW_SIGNED_INT_SELECTOR_INPUT_NEXT`
- `ALLOW_EXISTING_WEIGHTED_SELECTOR_OR_ADAPTER_NEXT`
- `ALLOW_STRING_CANDIDATE_KEY_OUTPUT_NEXT`
- `ALLOW_CANDIDATE_INVENTORY_DATA_NEXT`
- `NO_MINECRAFT_RUNTIME_API_IMPORTS`
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
- `KEEP_RUNTIME_METHODS_UNSUPPORTED`

## Guardrail Contract Fields

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSlicePureBridgeAction`
- `allowsNextSliceRuntimeAction`
- `blocksActivation`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision` exposes deterministic helpers for:

- `selectedDecision()`
- `decisionIsGoForPureNonRuntimeBridgeNext()`
- `decisionIsImplementationOnlyForNextSlice()`
- `selectorToWeightedCandidateBridgeImplementedInThisSlice()`
- `selectorToWeightedCandidateBridgeRuntimeReady()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(...)`
- `guardrailCount()`
- `allGuardrailsEnforcedInThisSlice()`
- `allowedNextSlicePureBridgeActionCount()`
- `allowedNextSliceRuntimeActionCount()`
- `anyRuntimeActionAllowedByGuardrails()`
- `nextSliceMayAddPureNonRuntimeSelectorToWeightedCandidateBridge()`
- `nextSliceMayUseSignedIntSelectorInput()`
- `nextSliceMayUseExistingWeightedSelectorOrAdapter()`
- `nextSliceMayReturnStringCandidateKey()`
- `nextSliceMayUseCandidateInventoryData()`
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
- `nextSliceMayAddDimensionJson()`
- `nextSliceMayAddDimensionTypeJson()`
- `nextSliceMayCreateActiveCaveniaLevel()`
- `nextSliceMayAddWorldgenResources()`
- `nextSliceMayAddAccessOrTeleport()`
- `nextSliceMayAddSpawning()`
- `nextSliceMayRegisterCavemanEntity()`
- `bridgeReadinessReady()`
- `bridgeReadinessRuntimeReady()`
- `bridgeImplementationReady()`
- `bridgeRuntimeReady()`
- `selectorInputOutputShape()`
- `candidateKeyOutputShape()`
- `weightedSelectionTotalWeight()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `weightedSelectorCompatibilityReady()`
- `adapterCompatibilityReady()`
- `adapterRuntimeReady()`
- `selectorInputAlgorithmReady()`
- `selectorInputAlgorithmRuntimeReady()`
- `deriveSelectorInputSampleOrigin()`
- `deriveSelectorInputSampleMixed()`
- `deriveSelectorInputSampleNegative()`
- `bridgeGoNoGoDecisionReady()`
- `bridgeGoNoGoRuntimeReady()`
- `selectorInputAlgorithmConsolidationReady()`
- `selectorInputAlgorithmConsolidationRuntimeReady()`
- `implementationGoNoGoDecisionReady()`
- `implementationGoNoGoRuntimeReady()`
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesRuntimeReady()`
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
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Current Facts

- selector-to-weighted-candidate bridge readiness remains ready.
- selector-to-weighted-candidate bridge implementation is not added in this slice.
- bridge runtime behavior remains not ready.
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

## Why It Is Still Inert

This is still not selector-to-weighted-candidate bridge implementation because this slice adds only a decision surface and does not add a bridge helper.

This is still not weighted selector/adapter runtime wiring because no weighted selector or adapter call with derived selector input exists here and nothing is wired into runtime methods.

This is still not coordinate/climate runtime selection because `getNoiseBiome(...)` stays unsupported and the bridge surface remains outside runtime execution.

This is still not usable noise-biome runtime behavior because `getNoiseBiome(...)` remains unsupported.

This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains unsupported.

This is still not usable runtime biome resolution because holder conversion, registry lookup, resource-location conversion and resource-key conversion remain absent.

This is still not a usable runtime biome source because the guarded subclass remains unregistered, non-activating and runtime-inert.

This is still not a `ChunkGenerator` because no generator implementation, registration or binding work is added here.

This slice adds no registry lookup access, no holder/resource-key conversion implementation, no fallback implementation, no usable codec implementation, no codec or registry entry, no dimension JSON/type JSON, no active generator, no active Cavenia level and no access, spawning or worldgen resources.

## Follow-up

- The selector-to-weighted-candidate bridge readiness follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md`.
- The selector-to-weighted-candidate bridge implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md`.
- The pure non-runtime selector-to-weighted-candidate bridge implementation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation and next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
- The candidate-key-to-holder conversion readiness follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-readiness-mvp.md`.
- Future recommendation: next slice can be `Cavenia Runtime BiomeSource Selector-to-Weighted-Candidate Bridge Pure Non-Runtime Implementation MVP`.
- That next slice may implement only a pure non-runtime bridge helper.
- That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
