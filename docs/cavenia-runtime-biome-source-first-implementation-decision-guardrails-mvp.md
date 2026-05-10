# Cavenia Runtime BiomeSource First Implementation Decision / Guardrails MVP

This is a decision/guardrails layer, not runtime activation.

The selected next branch is `PROCEED_WITH_UNREGISTERED_RUNTIME_BIOME_SOURCE_SKELETON_NEXT`.

This slice does not implement runtime `BiomeSource`.

Selected surface remains `BIOME_SOURCE_STRATEGY`.

This slice builds on:

- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`
- `docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md`
- `docs/cavenia-registry-lookup-readiness-contracts-mvp.md`
- `docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md`
- `docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md`
- `docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md`
- `docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md`
- `docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md`
- `docs/cavenia-deliberate-first-active-surface-selection-mvp.md`
- `docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md`
- `docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md`
- `docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md`
- `docs/cavenia-biome-provider-contract-boundary.md`
- `docs/cavenia-active-foundation-technical-spike.md`
- `docs/cavenia-active-foundation-readiness-plan.md`

## Exact Classes Added

- `CaveniaRuntimeBiomeSourceNextStepDecision`
- `CaveniaRuntimeBiomeSourceGuardrail`
- `CaveniaRuntimeBiomeSourceGuardrailContract`
- `CaveniaRuntimeBiomeSourceFirstImplementationDecision`

## Exact Decision Enum Values

- `PROCEED_WITH_UNREGISTERED_RUNTIME_BIOME_SOURCE_SKELETON_NEXT`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_REGISTRY_LOOKUP_ACCESS`
- `DEFER_ACCESS_AND_SPAWNING`

## Exact Guardrail Enum Values

- `NO_DIMENSION_JSON`
- `NO_DIMENSION_TYPE_JSON`
- `NO_ACTIVE_CAVENIA_LEVEL`
- `NO_CODEC_IMPLEMENTATION`
- `NO_CODEC_REGISTRATION`
- `NO_BIOME_SOURCE_TYPE_REGISTRATION`
- `NO_REGISTRY_LOOKUP_ACCESS`
- `NO_BIOME_HOLDER_OR_RESOURCE_KEY_CONVERSION`
- `NO_WORLDGEN_RESOURCES`
- `NO_ACCESS_OR_TELEPORT`
- `NO_SPAWNING`
- `NO_CAVEMAN_ENTITY_REGISTRATION`
- `UNREGISTERED_SKELETON_ONLY`
- `KEEP_BIOME_SOURCE_STRATEGY_MATRIX_BLOCKED`

## Exact Guardrail Contract Fields

`CaveniaRuntimeBiomeSourceGuardrailContract` exposes:

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `mayBeRelaxedInNextSlice`
- `runtimeActivationAllowed`
- `blocker`

## Exact Decision Helpers

`CaveniaRuntimeBiomeSourceFirstImplementationDecision` exposes:

- `selectedDecision()`
- `decisionIsToProceedWithUnregisteredSkeletonNext()`
- `decisionIsImplementationOnlyForNextSlice()`
- `runtimeBiomeSourceImplementedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `guardrails()`
- `guardrailValues()`
- `guardrailFor(CaveniaRuntimeBiomeSourceGuardrail)`
- `guardrailCount()`
- `allGuardrailsEnforcedInThisSlice()`
- `anyRuntimeActivationAllowedByGuardrails()`
- `nextSliceMayAddUnregisteredSkeleton()`
- `nextSliceMayAddCodecImplementation()`
- `nextSliceMayRegisterCodec()`
- `nextSliceMayRegisterBiomeSourceType()`
- `nextSliceMayUseRegistryLookupAccess()`
- `nextSliceMayAddDimensionJson()`
- `nextSliceMayAddDimensionTypeJson()`
- `nextSliceMayCreateActiveCaveniaLevel()`
- `nextSliceMayAddWorldgenResources()`
- `nextSliceMayAddAccessOrTeleport()`
- `nextSliceMayAddSpawning()`
- `nextSliceMayRegisterCavemanEntity()`
- `biomeSourceStrategyMatrixReady()`
- `biomeSourceStrategyMatrixRuntimeReady()`
- `biomeSourceStrategyTotalReadinessItemCount()`
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

- this is a decision/guardrails layer, not runtime activation
- the final `BIOME_SOURCE_STRATEGY` readiness matrix is complete enough to choose the next branch
- the selected next branch is `PROCEED_WITH_UNREGISTERED_RUNTIME_BIOME_SOURCE_SKELETON_NEXT`
- this slice does not implement runtime `BiomeSource`
- guardrail count remains `14`
- every guardrail is enforced in this slice
- no guardrail allows runtime activation
- next slice may add only an unregistered skeleton
- next slice may not add codec implementation/registration
- next slice may not add registry lookup access
- next slice may not add dimension JSON/type JSON
- next slice may not create an active Cavenia level
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
- all runtime surfaces remain blocked

## Relationship To Existing Contracts

This decision/guardrails layer sits on top of:

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

This slice is still not a `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no registry lookup access was added.

no biome holder/resource-key conversion was added.

no codec implementation was added.

no codec or registry entry was added.

It does not:

- create runtime `BiomeSource`
- create runtime `ChunkGenerator`
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

The next slice can implement an unregistered runtime-biome-source skeleton only.

That future slice must keep no dimension activation, no codec registration, no registry lookup access, no worldgen resources, no access/teleport and no spawning unless those are explicitly chosen later.

The unregistered runtime-biome-source skeleton follow-up is now documented in `docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md`.

The runtime-biome-source codec/holder/registry decision follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`.


The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.

The runtime-biome-source real-subclass go/no-go decision follow-up is now documented in `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`.

The guarded unregistered real biome-source subclass stub follow-up is now documented in `docs/cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md`.

The runtime-biome-source codec-method-shape stub follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md`.
