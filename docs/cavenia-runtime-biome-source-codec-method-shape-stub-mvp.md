# Cavenia Runtime BiomeSource Codec Method Shape Stub MVP

This slice is a codec method shape stub contract, not usable codec implementation.

`CaveniaRuntimeBiomeSource.codec()` now keeps the local `MapCodec<? extends BiomeSource>` method shape explicit while remaining unsupported and non-activating.

## Added Classes

- `CaveniaRuntimeBiomeSourceCodecMethodShapeComponent`
- `CaveniaRuntimeBiomeSourceCodecMethodShapeEntry`
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub`

## Component Values

- `LOCAL_CODEC_METHOD_SIGNATURE`
- `DESIGNATED_SUBCLASS_OVERRIDE`
- `UNSUPPORTED_CODEC_METHOD_BODY`
- `NO_USABLE_MAP_CODEC`
- `NO_STATIC_CODEC_FIELD`
- `NO_RECORD_CODEC_BUILDER`
- `NO_CODEC_REGISTRATION`
- `NO_BIOME_SOURCE_TYPE_REGISTRATION`
- `NO_REGISTRY_LOOKUP_DEPENDENCY`
- `REAL_CODEC_STILL_DEFERRED`

## Entry Fields

Each `CaveniaRuntimeBiomeSourceCodecMethodShapeEntry` records:

- `component`
- `sourceContractName`
- `observedShape`
- `expectedBehavior`
- `shapePinned`
- `stubbed`
- `usableCodecImplementation`
- `registered`
- `activationAllowedInThisSlice`
- `blocker`

## Helper Methods

`CaveniaRuntimeBiomeSourceCodecMethodShapeStub` exposes deterministic helpers for:

- `entries()`
- `components()`
- `entryFor(...)`
- `entryCount()`
- `codecMethodShapeStubReady()`
- `localCodecMethodSignaturePinned()`
- `designatedSubclassOverridesCodecMethod()`
- `codecMethodUnsupportedStubbed()`
- `usableMapCodecReady()`
- `staticCodecFieldPresent()`
- `recordCodecBuilderUsed()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `registryLookupDependencyReady()`
- `realCodecStillDeferred()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `designatedSubclassReady()`
- `designatedSubclassRuntimeReady()`
- `designatedSubclassFileName()`
- `designatedSubclassSimpleName()`
- `normalRuntimeConstructionAllowed()`
- `unsupportedMethodStubsOnly()`
- `goNoGoGuardrailsEnforced()`
- `apiShapeInventoryReady()`
- `registryLookupAccessReady()`
- `holderResourceKeyConversionReady()`
- `dimensionBindingReady()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Codec Facts

This contract pins these facts:

- `CaveniaRuntimeBiomeSource.codec()` has the local `MapCodec<? extends BiomeSource>` method shape.
- `codec()` still throws unsupported.
- no usable `MapCodec` exists.
- no static `CODEC` field exists.
- no `RecordCodecBuilder` usage exists.
- no codec registration exists.
- no biome-source type registration exists.
- no registry lookup dependency exists.
- real codec implementation remains deferred.
- subclass remains guarded, unregistered, and non-activating.
- `collectPossibleBiomes()` remains unsupported.
- `getNoiseBiome(...)` remains unsupported.
- normal runtime construction remains unavailable.

## Still Absent

This slice still does not add:

- usable codec implementation
- codec registration
- biome-source type registration
- registry lookup access
- holder/resource-key conversion implementation
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- active Cavenia level
- worldgen resources
- access or teleport
- spawning
- `cavernreborn:caveman`

`cavernreborn:caveman` remains absent.

`EntityCaveman -> deferred:caveman` remains unchanged.

Selected-surface readiness item count remains `68`.

Global readiness matrix total and blocked counts remain `46/46`.

## Relationships

This codec method shape stub builds on:

- `CaveniaRuntimeBiomeSource`
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

## Why This Is Still Inert

This is still not a usable runtime biome source because `codec()`, `collectPossibleBiomes()`, and `getNoiseBiome(...)` remain unsupported and no usable codec object exists.

This is still not a `ChunkGenerator`.

No registry lookup access was added.

No holder/resource-key conversion implementation was added.

No usable codec implementation was added.

No codec or registry entry was added.

No dimension JSON/type JSON exist.

no dimension JSON/type JSON exist

No active generator was added.

No active Cavenia level was added.

no active Cavenia level exists

No access, spawning, or worldgen resources were added.

no worldgen resources exist

no access/teleport exists

no spawning exists

## Future Recommendation

The next slice can define holder and resource-key conversion readiness for the subclass.

It can also decide a non-usable codec-object placeholder only if it remains unregistered and explicitly not usable.

The runtime-biome-source holder/resource-key conversion readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md`.
The runtime-biome-source fallback-policy readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md`.
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
- The collect-possible-biomes holder-set implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-implementation-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set guarded builder follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-guarded-builder-mvp.md`.
