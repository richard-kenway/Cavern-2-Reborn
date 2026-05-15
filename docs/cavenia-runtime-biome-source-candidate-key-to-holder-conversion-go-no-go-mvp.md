# Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Go/No-Go MVP

This is candidate-key-to-holder conversion go/no-go decision, not readiness and not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice decides only whether the next slice may add readiness planning for future conversion from a string modern biome candidate key to a future `"Holder" + "<Biome>"`-backed runtime surface.

## Added Types

- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision`

## Next-Decision Enum Values

- `PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT`
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

## Guardrail Enum Values

- `ALLOW_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT`
- `ALLOW_STRING_CANDIDATE_KEY_SURFACE_PINNING_NEXT`
- `ALLOW_RESOURCE_LOCATION_POLICY_PINNING_NEXT`
- `ALLOW_RESOURCE_KEY_POLICY_PINNING_NEXT`
- `ALLOW_REGISTRY_LOOKUP_POLICY_PINNING_NEXT`
- `ALLOW_HOLDER_RESOLUTION_POLICY_PINNING_NEXT`
- `ALLOW_MISSING_OR_FALLBACK_POLICY_PINNING_NEXT`
- `NO_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_HOLDER_RESOLUTION`
- `NO_RESOURCE_LOCATION_CONVERSION`
- `NO_RESOURCE_KEY_CONVERSION`
- `NO_GET_NOISE_BIOME_WIRING`
- `NO_GET_NOISE_BIOME_IMPLEMENTATION`
- `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
- `NO_FALLBACK_IMPLEMENTATION`
- `NO_USABLE_CODEC_OR_REGISTRATION`
- `NO_DIMENSION_ACTIVATION`
- `NO_WORLDGEN_ACCESS_SPAWNING`
- `KEEP_CANDIDATE_KEYS_STRING_ONLY`

## Guardrail Contract Fields

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSliceReadinessAction`
- `allowsNextSliceConversionImplementation`
- `allowsNextSliceRuntimeAction`
- `blocksActivation`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision` exposes deterministic helpers for:

- `selectedDecision()`
- `decisionIsGoForCandidateKeyToHolderConversionReadinessNext()`
- `decisionIsReadinessOnlyForNextSlice()`
- `candidateKeyToHolderConversionReadinessImplementedInThisSlice()`
- `candidateKeyToHolderConversionImplementationReady()`
- `candidateKeyToHolderConversionRuntimeReady()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(...)`
- `guardrailCount()`
- `allGuardrailsEnforcedInThisSlice()`
- `allowedNextSliceReadinessActionCount()`
- `allowedNextSliceConversionImplementationActionCount()`
- `allowedNextSliceRuntimeActionCount()`
- `anyConversionImplementationAllowedByGuardrails()`
- `anyRuntimeActionAllowedByGuardrails()`
- `nextSliceMayAddCandidateKeyToHolderConversionReadiness()`
- `nextSliceMayPinStringCandidateKeySurface()`
- `nextSliceMayPinResourceLocationPolicy()`
- `nextSliceMayPinResourceKeyPolicy()`
- `nextSliceMayPinRegistryLookupPolicy()`
- `nextSliceMayPinHolderResolutionPolicy()`
- `nextSliceMayPinMissingOrFallbackPolicy()`
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
- `selectorToWeightedCandidateBridgeConsolidationReady()`
- `selectorToWeightedCandidateBridgeConsolidationRuntimeReady()`
- `selectorToWeightedCandidateBridgeConsolidationSelectedNextDecisionMatches()`
- `selectorToWeightedCandidateBridgeReady()`
- `selectorToWeightedCandidateBridgeRuntimeReady()`
- `candidateKeyOutputShape()`
- `candidateKeysRemainStringOnly()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeyForSampleOrigin()`
- `candidateKeyForSampleMixed()`
- `candidateKeyForSampleNegative()`
- `expectedSampleOriginCandidateKey()`
- `expectedSampleMixedCandidateKey()`
- `expectedSampleNegativeCandidateKey()`
- `sampleKeysExistInInventory()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
- `holderResolutionReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
- `registryLookupAccessReady()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicyRuntimeReady()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesRuntimeReady()`
- `noiseBiomeSelectionReadinessReady()`
- `noiseBiomeSelectionRuntimeReady()`
- `readinessChainConsolidationReady()`
- `readinessChainRuntimeReady()`
- `allCurrentReadinessLayersReady()`
- `runtimeReadyLayerCount()`
- `consolidatedReadinessLayerCount()`
- `codecMethodShapeStubReady()`
- `codecMethodShapeRuntimeReady()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
- `runtimeHolderReturnReady()`
- `collectPossibleBiomesImplementationReady()`
- `getNoiseBiomeImplementationReady()`
- `usableRuntimeBiomeSourceReady()`
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

## Go / No-Go Facts

- candidate-key-to-holder conversion go/no-go decision is ready.
- selected decision is `PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT`.
- next slice may add candidate-key-to-holder conversion readiness data.
- next slice may pin string candidate key surface.
- next slice may pin resource-location policy.
- next slice may pin resource-key policy.
- next slice may pin registry lookup policy.
- next slice may pin holder resolution policy.
- next slice may pin missing/fallback policy.
- next slice may not implement candidate-key-to-holder conversion.
- next slice may not use registry lookup access.
- next slice may not resolve holders.
- next slice may not implement resource-location conversion.
- next slice may not implement resource-key conversion.
- next slice may not wire into `getNoiseBiome(...)`.
- next slice may not make `getNoiseBiome(...)` usable.
- this slice does not add candidate-key-to-holder conversion readiness.
- this slice does not implement candidate-key-to-holder conversion.
- selector-to-weighted-candidate bridge consolidation remains ready.
- pure selector-to-weighted-candidate bridge remains ready.
- candidate keys remain string-only.
- candidate-key-to-holder conversion runtime behavior is not ready.
- registry lookup access remains absent.
- holder resolution remains absent.
- resource-location conversion remains absent.
- resource-key conversion remains absent.
- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
- `codec()` remains unsupported.
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
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation`
- `CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`
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

## Why This Is Still Not Conversion Or Runtime Wiring

- This is still not candidate-key-to-holder conversion readiness because it adds no readiness contracts for string-to-runtime conversion policy data.
- This is still not candidate-key-to-holder conversion implementation because it adds no parsing, resource-key construction, registry lookup, or holder resolution behavior.
- This is still not registry lookup access because the next slice may pin only policy and may not add runtime lookup access.
- This is still not holder resolution because no runtime holder-backed biome values are created or returned here.
- This is still not resource-location or resource-key conversion because string candidate keys remain inert strings in this slice.
- This is still not runtime selection because `getNoiseBiome(...)` remains unsupported and unwired.
- This is still not usable noise-biome runtime behavior because the guarded noise-biome method remains blocked.
- This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` remains blocked.
- This is still not usable runtime biome resolution because no runtime lookup or holder conversion exists.
- This is still not a usable runtime biome source because runtime construction, codec work, registration, and activation remain blocked.
- This is still not a `ChunkGenerator` because no generator or dimension resource work is added.
- No usable codec implementation was added because this slice is decision-only.
- No codec or registry entry was added because runtime activation remains out of scope.
- No dimension JSON or dimension-type JSON was added because activation remains blocked.
- No active generator or active Cavenia level was added because worldgen and activation remain blocked.
- No access, spawning, or worldgen resources were added because those boundaries remain blocked.

## Recommendation

- The next slice can be `Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Readiness MVP`.
- That next slice should remain readiness-only unless explicitly requested otherwise.
- That next slice may pin the future conversion path for string candidate keys, future resource-location parsing, future resource-key construction, future biome-registry lookup, future holder resolution, future missing/fallback handling, and the future `"Holder" + "<Biome>"` return boundary.
- That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.

## Cross-Links

- The selector-to-weighted-candidate bridge consolidation note is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion go/no-go note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
