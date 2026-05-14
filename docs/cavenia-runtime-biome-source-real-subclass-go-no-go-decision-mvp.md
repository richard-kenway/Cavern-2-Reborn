# Cavenia Runtime BiomeSource Real Subclass Go/No-Go Decision MVP

This is a go/no-go decision layer, not runtime activation.

API shape inventory is ready.

unregistered skeleton is ready.

The selected decision for this slice is:

- `PROCEED_WITH_GUARDED_UNREGISTERED_REAL_BIOME_SOURCE_SUBCLASS_NEXT`

The exact next-slice allowed subclass is:

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSource.java`

## Exact Classes Added

- `CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision`
- `CaveniaRuntimeBiomeSourceRealSubclassGuardrail`
- `CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract`
- `CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`

## Exact Decision Enum Values

- `PROCEED_WITH_GUARDED_UNREGISTERED_REAL_BIOME_SOURCE_SUBCLASS_NEXT`
- `DEFER_USABLE_CODEC_IMPLEMENTATION`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_BIOME_SOURCE_TYPE_REGISTRATION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_HOLDER_RESOURCE_KEY_CONVERSION_IMPLEMENTATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Exact Guardrail Enum Values

- `ALLOW_ONE_DESIGNATED_REAL_SUBCLASS_FILE_NEXT`
- `ALLOW_BIOME_SOURCE_EXTENDS_ONLY_IN_DESIGNATED_SUBCLASS`
- `ALLOW_ABSTRACT_METHOD_SIGNATURE_TYPES_ONLY`
- `ALLOW_UNSUPPORTED_METHOD_STUBS_ONLY`
- `NO_USABLE_CODEC_IMPLEMENTATION`
- `NO_CODEC_REGISTRATION`
- `NO_BIOME_SOURCE_TYPE_REGISTRATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_HOLDER_RESOURCE_KEY_CONVERSION_IMPLEMENTATION`
- `NO_DIMENSION_JSON`
- `NO_DIMENSION_TYPE_JSON`
- `NO_ACTIVE_CAVENIA_LEVEL`
- `NO_WORLDGEN_RESOURCES`
- `NO_ACCESS_OR_TELEPORT`
- `NO_SPAWNING`
- `NO_CAVEMAN_ENTITY_REGISTRATION`
- `KEEP_SUBCLASS_UNREGISTERED_AND_UNREFERENCED_BY_RUNTIME`
- `KEEP_EXISTING_SKELETON_AND_API_INVENTORY_INTACT`

## Exact Guardrail Contract Fields

`CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract` exposes:

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSliceAction`
- `runtimeActivationAllowed`
- `blocker`

## Exact Decision Helpers

`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision` exposes:

- `selectedDecision()`
- `decisionIsGoForGuardedUnregisteredRealSubclassNext()`
- `decisionIsImplementationOnlyForNextSlice()`
- `realSubclassImplementedInThisSlice()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(CaveniaRuntimeBiomeSourceRealSubclassGuardrail)`
- `guardrailCount()`
- `allGuardrailsEnforcedInThisSlice()`
- `anyRuntimeActivationAllowedByGuardrails()`
- `allowedNextSliceActionCount()`
- `nextSliceMayAddOneDesignatedRealSubclassFile()`
- `nextSliceDesignatedSubclassSimpleName()`
- `nextSliceDesignatedSubclassFileName()`
- `nextSliceMayExtendBiomeSourceOnlyInDesignatedSubclass()`
- `nextSliceMayUseAbstractMethodSignatureTypesOnly()`
- `nextSliceMayUseUnsupportedMethodStubsOnly()`
- `nextSliceMayAddUsableCodecImplementation()`
- `nextSliceMayRegisterCodec()`
- `nextSliceMayRegisterBiomeSourceType()`
- `nextSliceMayUseRegistryLookupAccess()`
- `nextSliceMayImplementHolderResourceKeyConversion()`
- `nextSliceMayAddDimensionJson()`
- `nextSliceMayAddDimensionTypeJson()`
- `nextSliceMayCreateActiveCaveniaLevel()`
- `nextSliceMayAddWorldgenResources()`
- `nextSliceMayAddAccessOrTeleport()`
- `nextSliceMayAddSpawning()`
- `nextSliceMayRegisterCavemanEntity()`
- `apiShapeInventoryReady()`
- `apiShapeRuntimeReady()`
- `skeletonReady()`
- `skeletonRuntimeReady()`
- `subclassDecisionApiShapePinned()`
- `subclassDecisionReadyForRealSubclass()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `runtimeBiomeSourceReady()`
- `runtimeBiomeSourceRegistered()`
- `codecRegistered()`
- `registryLookupAccessReady()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Facts

- guardrail count is `18`
- allowed next-slice action count is `4`
- this current slice does not create the real subclass
- this current slice does not use runtime API types
- next slice may add exactly one designated real subclass file
- next slice may extend Minecraft `BiomeSource` only in that designated file
- next slice may use abstract method signature types only where required by that subclass
- next slice may use unsupported/stub method bodies only
- next slice may not add usable codec implementation
- next slice may not register codec
- next slice may not register biome-source type
- next slice may not use registry lookup access
- next slice may not implement holder/resource-key conversion
- next slice may not add dimension JSON/type JSON
- next slice may not create active Cavenia level
- next slice may not add worldgen resources
- next slice may not add access/teleport
- next slice may not add spawning
- next slice may not register `cavernreborn:caveman`
- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`
- dimension JSON/type JSON are absent
- runtime `BiomeSource` is not ready in this slice
- codec is not registered
- registry lookup access is not ready
- `EntityCaveman -> deferred:caveman` remains unchanged

## Relationship To Existing Contracts

This go/no-go decision layer sits on top of:

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
- `CaveniaFirstActiveSurfaceSelection`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Current Slice Stays Inert

this current slice is still not a real `BiomeSource`.

this current slice is still not a `ChunkGenerator`.

no registry lookup access was added.

no biome holder/resource-key conversion implementation was added.

no codec implementation was added.

no codec/registry entry was added.

It does not:

- create `CaveniaRuntimeBiomeSource.java`
- create a real runtime biome-source subclass
- use runtime API types in main source
- add usable codec implementation
- add codec registration
- add biome-source type registration
- add registry lookup access
- add biome holder/resource-key conversion implementation
- add active `dimension/cavenia.json`
- add active `dimension_type/cavenia.json`
- create an active Cavenia level
- add active Cavenia worldgen resources
- add active Cavenia access or teleport
- add active Cavenia spawning
- register `cavernreborn:caveman`

Current Reborn therefore still keeps:

- no active `dimension/cavenia.json`
- no active `dimension_type/cavenia.json`
- no active Cavenia runtime level
- no active Cavenia access or teleport
- no active Cavenia spawning
- no active Cavenia worldgen resources

## Future Recommendation

The next slice may implement only the guarded unregistered `CaveniaRuntimeBiomeSource` subclass stub.

That future slice must keep the subclass unregistered and non-activating.

The guarded unregistered real biome-source subclass stub follow-up is now documented in `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`.

The runtime-biome-source codec-method-shape stub follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
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
