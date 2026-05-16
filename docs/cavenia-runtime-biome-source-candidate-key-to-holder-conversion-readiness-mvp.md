# Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Readiness MVP

This is candidate-key-to-holder conversion readiness, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice pins the future conversion path from a string modern biome candidate key to a future `"Holder" + "<Biome>"`-backed runtime surface as inert readiness data only.

## Added Types

- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness`

## Component Enum Values

- `STRING_CANDIDATE_KEY_INPUT`
- `CANDIDATE_KEY_INVENTORY_SOURCE`
- `RESOURCE_LOCATION_POLICY`
- `RESOURCE_KEY_POLICY`
- `BIOME_REGISTRY_LOOKUP_POLICY`
- `HOLDER_RESOLUTION_POLICY`
- `HOLDER_RETURN_BOUNDARY`
- `MISSING_CANDIDATE_KEY_POLICY`
- `UNRESOLVED_RESOURCE_LOCATION_POLICY`
- `UNRESOLVED_RESOURCE_KEY_POLICY`
- `MISSING_HOLDER_FALLBACK_POLICY`
- `NO_CONVERSION_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `REAL_CANDIDATE_KEY_TO_HOLDER_CONVERSION_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `conversionSurface`
- `futurePolicy`
- `readinessPinned`
- `conversionImplementationReady`
- `registryLookupRequired`
- `registryLookupAvailable`
- `holderResolved`
- `activationAllowedInThisSlice`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness` exposes deterministic helpers for:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `candidateKeyToHolderConversionReadinessReady()`
- `stringCandidateKeyInputPinned()`
- `candidateKeyInventorySourcePinned()`
- `resourceLocationPolicyPinned()`
- `resourceKeyPolicyPinned()`
- `biomeRegistryLookupPolicyPinned()`
- `holderResolutionPolicyPinned()`
- `holderReturnBoundaryPinned()`
- `missingCandidateKeyPolicyPinned()`
- `unresolvedResourceLocationPolicyPinned()`
- `unresolvedResourceKeyPolicyPinned()`
- `missingHolderFallbackPolicyPinned()`
- `conversionImplementationReady()`
- `registryLookupRequiredForRuntimeConversion()`
- `registryLookupAccessReady()`
- `holderResolutionReady()`
- `holderReturnReady()`
- `resourceLocationConversionReady()`
- `resourceKeyConversionReady()`
- `fallbackRuntimeReady()`
- `realCandidateKeyToHolderConversionStillDeferred()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `candidateKeyInputShape()`
- `futureResourceLocationPolicy()`
- `futureResourceKeyPolicy()`
- `futureRegistryLookupPolicy()`
- `futureHolderResolutionPolicy()`
- `futureHolderReturnBoundary()`
- `futureMissingFallbackPolicy()`
- `selectorToWeightedCandidateBridgeConsolidationReady()`
- `selectorToWeightedCandidateBridgeConsolidationRuntimeReady()`
- `selectorToWeightedCandidateBridgeReady()`
- `selectorToWeightedCandidateBridgeRuntimeReady()`
- `candidateKeyOutputShape()`
- `candidateKeysRemainStringOnly()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateModernBiomeKeys()`
- `candidateKeyForSampleOrigin()`
- `candidateKeyForSampleMixed()`
- `candidateKeyForSampleNegative()`
- `expectedSampleOriginCandidateKey()`
- `expectedSampleMixedCandidateKey()`
- `expectedSampleNegativeCandidateKey()`
- `sampleKeysExistInInventory()`
- `goNoGoDecisionReady()`
- `goNoGoRuntimeReady()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
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

## Readiness Facts

- future input surface is string modern biome candidate key.
- current candidate key source is `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`.
- candidate inventory source is `CaveniaLegacyToModernBiomeKeyMappings`.
- future resource-location parsing policy is pinned but not implemented.
- future resource-key construction policy is pinned but not implemented.
- future biome registry lookup policy is pinned but lookup access is absent.
- future holder resolution policy is pinned but no holder is resolved.
- future holder return boundary is pinned but no holder is returned.
- future missing/fallback policy is pinned but no runtime fallback is called.
- fallback remains `PLAINS -> minecraft:plains` readiness data.
- candidate keys remain string-only.
- no string key is parsed into a resource location in this slice.
- no resource key is constructed in this slice.
- no registry lookup is performed in this slice.
- no holder is resolved in this slice.
- no holder is returned in this slice.
- no fallback implementation exists.
- no usable codec implementation exists.
- no codec registration exists.
- no biome-source type registration exists.
- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
- `codec()` remains unsupported.
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
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness`
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- `CaveniaRegistryLookupReadiness`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`
- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub`
- `CaveniaRuntimeBiomeSourceApiShapeInventory`
- `CaveniaRuntimeBiomeSourceSkeleton`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Is Still Not Runtime Conversion

- This is still not candidate-key-to-holder conversion implementation because there is no parser, no biome key construction, no lookup, no holder resolution and no holder return.
- This is still not registry lookup access because the registry policy is pinned only as future readiness data and access remains absent.
- This is still not holder resolution because no holder is resolved and no holder-backed runtime method is wired.
- This is still not resource-location or resource-key conversion because both policies are pinned only as future readiness data.
- This is still not runtime selection because `getNoiseBiome(...)` remains unsupported and unwired.
- This is still not usable noise-biome runtime behavior because no holder-backed noise-biome return exists.
- This is still not usable possible-biomes runtime behavior because no holder-backed possible-biomes output exists.
- This is still not usable runtime biome resolution because every conversion and lookup stage remains deferred.
- This is still not a usable runtime biome source because the guarded subclass remains unregistered, unsupported and inactive.
- This is still not a `ChunkGenerator` because no generator work, codec work or activation work was added.
- Why no usable codec implementation was added: codec behavior remains stubbed.
- Why no codec or registry entry was added: codec registration and biome-source type registration remain absent.
- Why no dimension JSON or type JSON was added: activation remains out of scope.
- Why no active generator was added: generator activation remains blocked.
- Why no active Cavenia level was added: dimension binding and activation remain blocked.
- Why no access, spawning or worldgen resources were added: those activation surfaces remain blocked.

## Future Recommendation

- The next slice can be `Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Implementation Go/No-Go MVP`.
- That next slice should remain decision-only unless explicitly requested otherwise.
- That next slice must still keep `getNoiseBiome(...)` unusable unless explicitly requested otherwise.

## Cross-Links

- The candidate-key-to-holder conversion go/no-go note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
- The selector-to-weighted-candidate bridge consolidation note is documented in `docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md`.
- The candidate-key-to-holder conversion implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-implementation-go-no-go-mvp.md`.
- The guarded converter implementation follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md`.
- The converter consolidation next-decision follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The collect-possible-biomes holder-set go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set readiness follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
