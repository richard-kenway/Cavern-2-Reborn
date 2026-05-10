# Cavenia Runtime BiomeSource Codec/Holder/Registry Decision MVP

This is a codec/holder/registry decision layer, not runtime activation.

the unregistered skeleton is ready.

the real Minecraft `BiomeSource` subclass is deferred.

Selected future activation surface remains `BIOME_SOURCE_STRATEGY`.

## Exact Classes Added

- `CaveniaRuntimeBiomeSourceSubclassNextStepDecision`
- `CaveniaRuntimeBiomeSourceSubclassRequirement`
- `CaveniaRuntimeBiomeSourceSubclassRequirementContract`
- `CaveniaRuntimeBiomeSourceSubclassDecision`

## Exact Selected Decision

`DEFER_REAL_BIOME_SOURCE_SUBCLASS_UNTIL_CODEC_HOLDER_REGISTRY_SHAPE_IS_PINNED`

## Exact Allowed Next Decision

`PROCEED_WITH_CODEC_HOLDER_REGISTRY_SHAPE_DECISION_NEXT`

## Exact Decision Enum Values

- `DEFER_REAL_BIOME_SOURCE_SUBCLASS_UNTIL_CODEC_HOLDER_REGISTRY_SHAPE_IS_PINNED`
- `PROCEED_WITH_CODEC_HOLDER_REGISTRY_SHAPE_DECISION_NEXT`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_ACCESS_AND_SPAWNING`

## Exact Requirement Enum Values

- `SKELETON_READY`
- `REAL_SUBCLASS_DEFERRED`
- `ABSTRACT_METHODS_INVENTORY`
- `CODEC_METHOD_SHAPE_DECISION`
- `POSSIBLE_BIOMES_SHAPE_DECISION`
- `NOISE_BIOME_QUERY_SHAPE_DECISION`
- `HOLDER_RETURN_PATH_DECISION`
- `REGISTRY_CONTEXT_DECISION`
- `RESOURCE_KEY_CONVERSION_DECISION`
- `MISSING_BIOME_FALLBACK_DECISION`
- `NO_DIMENSION_ACTIVATION`
- `NO_CODEC_REGISTRATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_WORLDGEN_ACCESS_SPAWNING`

## Exact Requirement Contract Fields

`CaveniaRuntimeBiomeSourceSubclassRequirementContract` exposes:

- `requirement`
- `sourceContractName`
- `prerequisiteSatisfied`
- `decisionPinned`
- `readyForRealSubclass`
- `runtimeApiAllowedInThisSlice`
- `activationAllowedInThisSlice`
- `blocker`

## Exact Decision Helpers

`CaveniaRuntimeBiomeSourceSubclassDecision` exposes:

- `selectedDecision()`
- `allowedNextDecision()`
- `realSubclassDeferred()`
- `codecHolderRegistryShapeDecisionIsNext()`
- `realMinecraftBiomeSourceImplementedInThisSlice()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `contracts()`
- `requirements()`
- `contractFor(CaveniaRuntimeBiomeSourceSubclassRequirement)`
- `requirementCount()`
- `skeletonReady()`
- `skeletonRuntimeReady()`
- `allHardGuardrailsStillPinned()`
- `apiShapeDecisionsPinned()`
- `readyForRealSubclass()`
- `abstractMethodsInventoryReady()`
- `codecMethodShapeReady()`
- `possibleBiomesShapeReady()`
- `noiseBiomeQueryShapeReady()`
- `holderReturnPathReady()`
- `registryContextReady()`
- `resourceKeyConversionReady()`
- `missingBiomeFallbackReady()`
- `nextSliceMayInventoryRuntimeBiomeSourceApiShape()`
- `nextSliceMayImplementRealBiomeSourceSubclass()`
- `nextSliceMayAddCodecImplementation()`
- `nextSliceMayRegisterCodec()`
- `nextSliceMayUseRegistryLookupAccess()`
- `nextSliceMayAddDimensionJson()`
- `nextSliceMayAddDimensionTypeJson()`
- `nextSliceMayCreateActiveCaveniaLevel()`
- `nextSliceMayAddWorldgenResources()`
- `nextSliceMayAddAccessOrTeleport()`
- `nextSliceMayAddSpawning()`
- `nextSliceMayRegisterCavemanEntity()`
- `skeletonShapeReady()`
- `skeletonConsumesAdapter()`
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

- requirement count remains `14`
- no real `BiomeSource` subclass is added in this slice
- no runtime API usage is allowed in this slice
- API shape decisions are not pinned yet:
- abstract methods inventory
- codec method shape
- possible biomes shape
- noise biome query shape
- holder return path
- registry context
- resource-key conversion
- missing-biome fallback
- next slice may inventory runtime-biome-source API shape
- next slice may not implement real `BiomeSource`
- next slice may not add codec implementation/registration
- next slice may not use registry lookup access
- next slice may not add dimension JSON/type JSON
- next slice may not create active Cavenia level
- next slice may not add worldgen resources
- next slice may not add access/teleport
- next slice may not add spawning
- next slice may not register `cavernreborn:caveman`
- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`
- dimension JSON/type JSON are absent
- runtime `BiomeSource` is not ready
- codec is not registered
- registry lookup access is not ready
- `EntityCaveman -> deferred:caveman` remains unchanged

## Relationship To Existing Contracts

This decision layer sits on top of:

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

## Why This Stays Inert

This slice is still not a real `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no registry lookup access was added.

no biome holder/resource-key conversion was added.

no codec implementation was added.

no codec or registry entry was added.

It does not:

- create a real runtime biome-source subclass
- use runtime biome-source APIs
- add registry lookup access
- add biome holder or runtime biome-key conversion
- add codec implementation or codec registration
- add biome-source type registration
- add active `dimension/cavenia.json`
- add active `dimension_type/cavenia.json`
- add active Cavenia worldgen resources
- create an active Cavenia level
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

The next slice can inventory the actual Minecraft `BiomeSource` API shape from local dependencies without creating a subclass.

After that, a real subclass can be considered as a separate explicit MVP.


The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.

The runtime-biome-source real-subclass go/no-go decision follow-up is now documented in `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`.

The guarded unregistered real biome-source subclass stub follow-up is now documented in `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`.

The runtime-biome-source codec-method-shape stub follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
The runtime-biome-source holder/resource-key conversion readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md`.
The runtime-biome-source fallback-policy readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md`.
The runtime-biome-source possible-biomes readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md`.

The runtime-biome-source noise-biome selection readiness follow-up is now documented in `docs/cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md`.
