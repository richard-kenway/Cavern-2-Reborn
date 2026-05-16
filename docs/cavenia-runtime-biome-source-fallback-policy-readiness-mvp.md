# Cavenia Runtime BiomeSource Fallback Policy Readiness MVP

This slice is fallback policy readiness, not implementation.

The fallback policy is `PLAINS -> minecraft:plains`.

The fallback is sourced from the current string candidate inventory.

The fallback candidate exists in current inventory.

The fallback candidate is not registry-verified.

The fallback holder is not resolved.

The fallback runtime behavior is not ready.

Future fallback triggers are now pinned as readiness data only:
- unparseable candidate id
- unresolved resource key
- missing holder
- empty possible-biomes collection

Added app-side fallback-policy readiness classes:
- `CaveniaRuntimeBiomeSourceFallbackPolicyComponent`
- `CaveniaRuntimeBiomeSourceFallbackPolicyEntry`
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`

`CaveniaRuntimeBiomeSourceFallbackPolicyComponent` values, in order:
- `FALLBACK_POLICY_SOURCE`
- `FALLBACK_TRIGGER_UNPARSEABLE_CANDIDATE_ID`
- `FALLBACK_TRIGGER_UNRESOLVED_RESOURCE_KEY`
- `FALLBACK_TRIGGER_MISSING_HOLDER`
- `FALLBACK_TRIGGER_EMPTY_POSSIBLE_BIOMES`
- `FALLBACK_LEGACY_BIOME_NAME`
- `FALLBACK_CANDIDATE_MODERN_KEY`
- `FALLBACK_REGISTRY_VERIFICATION_DEFERRED`
- `FALLBACK_HOLDER_RESOLUTION_DEFERRED`
- `FALLBACK_RUNTIME_USAGE_DEFERRED`
- `NO_RUNTIME_FALLBACK_IMPLEMENTATION`
- `REAL_FALLBACK_POLICY_STILL_DEFERRED`

`CaveniaRuntimeBiomeSourceFallbackPolicyEntry` fields:
- `component`
- `sourceContractName`
- `fallbackDecision`
- `expectedFutureBehavior`
- `readinessPinned`
- `implementationReady`
- `registryLookupRequired`
- `registryLookupAvailable`
- `runtimeFallbackReady`
- `activationAllowedInThisSlice`
- `blocker`

Pinned facts in `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`:
- fallback policy readiness is ready
- fallback policy source is pinned
- fallback trigger unparseable candidate id is pinned
- fallback trigger unresolved resource key is pinned
- fallback trigger missing holder is pinned
- fallback trigger empty possible-biomes collection is pinned
- fallback legacy biome name is pinned
- fallback candidate modern key is pinned
- fallback registry verification is deferred
- fallback holder resolution is deferred
- fallback runtime usage is deferred
- runtime fallback implementation is not ready
- registry lookup is required for future runtime fallback but is not available in this slice
- fallback is not registry verified
- fallback holder is not resolved
- fallback runtime is not ready
- possible-biomes fallback is not ready
- noise-biome fallback is not ready
- real fallback policy is still deferred
- fallback legacy biome name is `PLAINS`
- fallback candidate modern biome key is `minecraft:plains`
- fallback source contract name is `CaveniaLegacyToModernBiomeKeyMappings`
- fallback candidate exists in inventory through the current string-only mapping inventory and is not registry-verified
- no resource-location conversion implementation exists
- no resource-key conversion implementation exists
- no holder resolution exists
- no runtime conversion implementation exists

Key helper methods:
- `entries()`, `components()`, `entryFor(...)`, `entryCount()`
- `fallbackPolicyReadinessReady()`
- `fallbackPolicySourcePinned()`
- `fallbackTriggerUnparseableCandidateIdPinned()`
- `fallbackTriggerUnresolvedResourceKeyPinned()`
- `fallbackTriggerMissingHolderPinned()`
- `fallbackTriggerEmptyPossibleBiomesPinned()`
- `fallbackLegacyBiomeNamePinned()`
- `fallbackCandidateModernKeyPinned()`
- `fallbackRegistryVerificationDeferred()`
- `fallbackHolderResolutionDeferred()`
- `fallbackRuntimeUsageDeferred()`
- `runtimeFallbackImplementationReady()`
- `registryLookupRequiredForRuntimeFallback()`
- `registryLookupAccessReady()`
- `fallbackRegistryVerified()`
- `fallbackHolderResolved()`
- `fallbackRuntimeReady()`
- `possibleBiomesFallbackReady()`
- `noiseBiomeFallbackReady()`
- `realFallbackPolicyStillDeferred()`
- `fallbackLegacyBiomeName()`
- `fallbackCandidateModernBiomeKey()`
- `fallbackSourceContractName()`
- `fallbackCandidateExistsInInventory()`
- `fallbackCandidateEntryCount()`
- `candidateInventoryReady()`
- `candidateKeysStillStringOnly()`
- `holderConversionReadinessReady()`
- `holderConversionRuntimeReady()`
- `codecMethodShapeStubReady()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
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

Why this is still not usable runtime fallback:
- the fallback choice is pinned only as readiness data
- no runtime fallback implementation exists
- no registry verification exists
- no holder resolution exists

Why this is still not usable runtime biome resolution:
- the future fallback is only one inert branch in a still-missing runtime conversion pipeline
- registry lookup access is still absent
- no string-to-runtime conversion implementation exists
- no holder-backed runtime return path exists

Why this is still not a usable runtime biome source:
- the guarded subclass still returns only unsupported stubs
- no runtime fallback is used by `collectPossibleBiomes()` or `getNoiseBiome(...)`
- no usable codec or registry entry exists

Why this is still not a `ChunkGenerator`:
- this slice adds no generator class, no generator codec, and no generator registration

Why no registry lookup access was added:
- lookup remains a later explicit boundary owned by `CaveniaRegistryLookupReadiness`

Why no holder/resource-key conversion implementation was added:
- this slice only records the deterministic fallback choice and its blockers

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
- this slice is limited to fallback-policy readiness only

Future recommendation:
- next slice can continue with possible-biomes readiness
- or make a go/no-go decision for non-runtime fallback implementation shape while keeping registry lookup and activation absent

The runtime-biome-source possible-biomes readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md`.

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.

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
- The collect-possible-biomes holder-set go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set readiness follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
