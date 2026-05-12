# Cavenia Runtime BiomeSource Possible-Biomes Readiness MVP

This slice is possible-biomes readiness, not implementation.

`CaveniaRuntimeBiomeSource.collectPossibleBiomes()` still does not produce runtime biome values. This slice only pins the future policy for how the guarded subclass may eventually turn the current string candidate inventory into holder-backed possible-biomes outputs.

## Scope

- future `collectPossibleBiomes()` source is current string candidate inventory
- candidate entry count remains `14`
- candidate modern biome keys remain string-only
- ordering policy is legacy inventory order
- future deduplication is only for resolved holder-backed outputs
- fallback policy source is `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- fallback `PLAINS -> minecraft:plains` is included only if future conversion would otherwise produce an empty possible-biomes result
- fallback inclusion is readiness data only
- no runtime holder stream exists
- `collectPossibleBiomes()` remains unsupported
- `getNoiseBiome(...)` remains unsupported
- `codec()` remains unsupported

## Added Types

- `CaveniaRuntimeBiomeSourcePossibleBiomesComponent`
- `CaveniaRuntimeBiomeSourcePossibleBiomesEntry`
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`

## Component Order

- `POSSIBLE_BIOMES_METHOD_SHAPE`
- `CANDIDATE_KEYS_SOURCE`
- `CANDIDATE_ENTRY_COUNT`
- `CANDIDATE_ORDERING_POLICY`
- `CANDIDATE_DEDUPLICATION_POLICY`
- `HOLDER_CONVERSION_DEPENDENCY`
- `FALLBACK_POLICY_SOURCE`
- `FALLBACK_INCLUDED_IF_EMPTY_POLICY`
- `NO_RUNTIME_HOLDER_STREAM`
- `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `REAL_POSSIBLE_BIOMES_STILL_DEFERRED`

## Entry Fields

- `component`
- `sourceContractName`
- `readinessDecision`
- `expectedFutureBehavior`
- `readinessPinned`
- `implementationReady`
- `registryLookupRequired`
- `registryLookupAvailable`
- `possibleBiomesRuntimeReady`
- `activationAllowedInThisSlice`
- `blocker`

## Future Policy

The future `collectPossibleBiomes()` source is the current string candidate inventory from `CaveniaLegacyToModernBiomeKeyMappings`.

- expected candidate entry count is `14`
- expected current candidate keys are all candidate modern biome keys from the current mapping inventory
- current candidate key ordering remains legacy inventory order
- current candidate keys stay string-only in this slice
- future deduplication is planned only for resolved holder-backed outputs, not for the current string inventory
- if collapsed legacy variants eventually resolve to the same runtime holder or key, later runtime possible-biomes behavior should deduplicate only at that resolved holder-backed stage

The fallback policy source is `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`.

- fallback legacy biome name is `PLAINS`
- fallback candidate modern biome key is `minecraft:plains`
- fallback `PLAINS -> minecraft:plains` is included only if future conversion would otherwise produce an empty possible-biomes result
- fallback inclusion is readiness data only

registry lookup is required for future runtime possible-biomes but is not available in this slice.

- no resource-location conversion implementation exists
- no resource-key conversion implementation exists
- no holder resolution exists
- no runtime conversion implementation exists
- no runtime holder stream exists
- no runtime possible-biomes implementation exists

## Helper Surface

`CaveniaRuntimeBiomeSourcePossibleBiomesReadiness` exposes deterministic helpers including:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `possibleBiomesReadinessReady()`
- `possibleBiomesMethodShapePinned()`
- `candidateKeysSourcePinned()`
- `candidateEntryCountPinned()`
- `candidateOrderingPolicyPinned()`
- `candidateDeduplicationPolicyPinned()`
- `holderConversionDependencyPinned()`
- `fallbackPolicySourcePinned()`
- `fallbackIncludedIfEmptyPolicyPinned()`
- `runtimeHolderStreamReady()`
- `collectPossibleBiomesImplementationReady()`
- `registryLookupRequiredForRuntimePossibleBiomes()`
- `registryLookupAccessReady()`
- `possibleBiomesRuntimeReady()`
- `realPossibleBiomesStillDeferred()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `candidateModernBiomeKeys()`
- `candidateOrderingPolicy()`
- `candidateDeduplicationPolicy()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicyRuntimeReady()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `fallbackIncludedIfEmpty()`
- `fallbackRuntimeReady()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
- `holderResolutionReady()`
- `resourceKeyConversionReady()`
- `resourceLocationConversionReady()`
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

This slice is still not usable possible-biomes runtime behavior.

- no runtime holder stream exists
- `collectPossibleBiomes()` remains unsupported
- `getNoiseBiome(...)` remains unsupported
- `codec()` remains unsupported
- no usable runtime biome resolution exists
- no usable runtime biome source exists
- this is still not a `ChunkGenerator`
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- no registry lookup access was added
- no holder/resource-key conversion implementation was added
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

- next slice can continue with noise-biome selection readiness
- a separate explicit decision could also define a non-runtime possible-biomes implementation shape while keeping registry lookup and activation absent

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.

The readiness-chain consolidation / next-decision follow-up is now documented in `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md`.

The selector-input derivation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md`.

The selector-input derivation readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md`.

The selector-input derivation implementation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md`.

The pure non-runtime selector-input derivation follow-up is documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md`.
