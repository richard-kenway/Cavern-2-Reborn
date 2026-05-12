# Cavenia Runtime BiomeSource Holder/Resource-Key Conversion Readiness MVP

This slice is holder/resource-key conversion readiness, not implementation.

Candidate modern biome keys are still string-only.

The exact conceptual future path is now pinned as readiness data:
- string candidate biome id
- future resource-location conversion decision
- future resource-key conversion decision
- future biome registry lookup decision
- future holder resolution decision
- future `Holder<Biome>` return for `collectPossibleBiomes()` and `getNoiseBiome(...)`

Added app-side readiness classes:
- `CaveniaRuntimeBiomeSourceHolderConversionComponent`
- `CaveniaRuntimeBiomeSourceHolderConversionEntry`
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness`

`CaveniaRuntimeBiomeSourceHolderConversionComponent` values, in order:
- `STRING_CANDIDATE_KEYS_SOURCE`
- `CANDIDATE_KEY_FORMAT_CONTRACT`
- `RESOURCE_LOCATION_CONVERSION_DECISION`
- `RESOURCE_KEY_CONVERSION_DECISION`
- `BIOME_REGISTRY_LOOKUP_DECISION`
- `HOLDER_RESOLUTION_DECISION`
- `POSSIBLE_BIOMES_HOLDER_SET_DECISION`
- `NOISE_BIOME_HOLDER_RETURN_DECISION`
- `MISSING_BIOME_FALLBACK_DECISION`
- `NO_RUNTIME_CONVERSION_IMPLEMENTATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `REAL_HOLDER_CONVERSION_STILL_DEFERRED`

`CaveniaRuntimeBiomeSourceHolderConversionEntry` fields:
- `component`
- `sourceContractName`
- `observedInput`
- `futureDecision`
- `readinessPinned`
- `implementationReady`
- `registryLookupRequired`
- `registryLookupAvailable`
- `activationAllowedInThisSlice`
- `blocker`

Pinned facts in `CaveniaRuntimeBiomeSourceHolderConversionReadiness`:
- candidate modern biome keys are still string-only in `CaveniaLegacyToModernBiomeKeyMappings`
- the candidate key format contract is pinned as namespace:path string ids
- future resource-location conversion decision is pinned as a decision, not implementation
- future resource-key conversion decision is pinned as a decision, not implementation
- future biome registry lookup decision is pinned as a decision, not access
- future holder resolution decision is pinned as a decision, not implementation
- future possible-biomes holder set decision is pinned as a decision, not implementation
- future noise-biome holder return decision is pinned as a decision, not implementation
- missing-biome fallback decision is pinned as a decision, not implementation
- registry lookup is required for future runtime conversion but is not available in this slice
- no resource-location conversion implementation exists
- no resource-key conversion implementation exists
- no holder resolution exists
- no runtime conversion implementation exists
- real holder conversion remains deferred

Key helper methods:
- `entries()`, `components()`, `entryFor(...)`, `entryCount()`
- `holderConversionReadinessReady()`
- `stringCandidateKeysSourcePinned()`
- `candidateKeyFormatPinned()`
- `resourceLocationConversionDecisionPinned()`
- `resourceKeyConversionDecisionPinned()`
- `biomeRegistryLookupDecisionPinned()`
- `holderResolutionDecisionPinned()`
- `possibleBiomesHolderSetDecisionPinned()`
- `noiseBiomeHolderReturnDecisionPinned()`
- `missingBiomeFallbackDecisionPinned()`
- `runtimeConversionImplementationReady()`
- `registryLookupRequiredForRuntimeConversion()`
- `registryLookupAccessReady()`
- `holderResolutionReady()`
- `resourceKeyConversionReady()`
- `resourceLocationConversionReady()`
- `possibleBiomesRuntimeReady()`
- `noiseBiomeRuntimeReady()`
- `missingBiomeFallbackReady()`
- `realHolderConversionStillDeferred()`
- `candidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
- `codecMethodShapeStubReady()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `normalRuntimeConstructionAllowed()`
- `unsupportedMethodStubsOnly()`
- `goNoGoGuardrailsEnforced()`
- `apiShapeInventoryReady()`
- `dimensionBindingReady()`

This slice keeps the guarded subclass inert:
- `collectPossibleBiomes()` remains unsupported
- `getNoiseBiome(...)` remains unsupported
- `codec()` remains unsupported
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- subclass remains guarded, unregistered, and non-activating
- normal runtime construction remains unavailable

This slice also keeps every activation boundary blocked:
- no dimension JSON/type JSON exist
- no active Cavenia level exists
- no worldgen resources exist
- no access/teleport exists
- no spawning exists
- `cavernreborn:caveman` remains absent
- `EntityCaveman -> deferred:caveman` remains unchanged

Readiness counts remain pinned:
- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`

Relationship to existing contracts:
- `CaveniaRuntimeBiomeSource`
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

Why this is still not usable runtime biome resolution:
- the path from string candidate ids to runtime holder results is only planned as readiness data
- registry lookup access is still absent
- no conversion step from strings to resource-location or resource-key objects exists
- no holder resolution step exists

Why this is still not a usable runtime biome source:
- the guarded subclass still returns only unsupported stubs
- no runtime biome holder pipeline exists for `collectPossibleBiomes()` or `getNoiseBiome(...)`
- no usable codec or registry entry exists

Why this is still not a `ChunkGenerator`:
- this slice adds no generator class, no generator codec, and no generator registration

Why no registry lookup access was added:
- lookup remains a later explicit boundary owned by `CaveniaRegistryLookupReadiness`

Why no holder/resource-key conversion implementation was added:
- this slice only records the future path and blockers

Why no usable codec implementation was added:
- codec work remains limited to the existing method-shape stub

Why no codec/registry entry was added:
- codec registration and biome-source type registration remain blocked

Why no dimension JSON/type JSON was added:
- the guarded subclass remains non-activating

Why no active generator was added:
- generator activation remains outside this bounded readiness slice

Why no active Cavenia level was added:
- activation requirements remain blocked globally

Why no access/spawning/worldgen resources were added:
- this slice is limited to holder/resource-key conversion readiness only

Future recommendation:
- next slice can continue with possible-biomes readiness
- or make a go/no-go decision for non-runtime fallback implementation shape while keeping the subclass unregistered and non-activating

The runtime-biome-source fallback-policy readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md`.
The runtime-biome-source possible-biomes readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md`.

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.

The readiness-chain consolidation / next-decision follow-up is now documented in `docs/cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md`.

The selector-input derivation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md`.

The selector-input derivation readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md`.

The selector-input derivation implementation go/no-go follow-up is now documented in `docs/cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md`.
