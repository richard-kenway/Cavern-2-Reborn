# Cavenia Unregistered Runtime BiomeSource Skeleton MVP

This is an unregistered runtime-biome-source skeleton, not runtime activation.

This is a runtime-shaped skeleton, not a real Minecraft `BiomeSource` subclass.

Selected future activation surface remains `BIOME_SOURCE_STRATEGY`.

This slice builds on:

- `docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md`
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
- `docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md`

## Exact Skeleton Classes Added

- `CaveniaRuntimeBiomeSourceSkeletonQuery`
- `CaveniaRuntimeBiomeSourceSkeletonResult`
- `CaveniaRuntimeBiomeSourceSkeleton`

## Exact Skeleton Query Fields

`CaveniaRuntimeBiomeSourceSkeletonQuery` exposes:

- `x`
- `y`
- `z`
- `weightValue`
- `climateSamplerAvailable`
- `registryLookupAvailable`
- `runtimeBiomeSourceAvailable`

## Exact Skeleton Result Fields

`CaveniaRuntimeBiomeSourceSkeletonResult` exposes:

- `x`
- `y`
- `z`
- `inputWeightValue`
- `normalizedWeightValue`
- `legacyBiomeName`
- `candidateModernBiomeKey`
- `weight`
- `candidateOnly`
- `finalRuntimeMapping`
- `registryVerified`
- `runtimeBiomeResolved`
- `runtimeBiomeSourceResult`

## Exact Skeleton Helpers

`CaveniaRuntimeBiomeSourceSkeleton` exposes:

- `queryForWeightValue(int)`
- `queryForCoordinatesAndWeightValue(int, int, int, int)`
- `selectCandidate(CaveniaRuntimeBiomeSourceSkeletonQuery)`
- `selectCandidateByWeightValue(int)`
- `skeletonShapeReady()`
- `unregisteredSkeletonReady()`
- `extendsMinecraftBiomeSource()`
- `runtimeBiomeSourceImplemented()`
- `runtimeBiomeSourceRegistered()`
- `codecImplemented()`
- `codecRegistered()`
- `registryLookupAccessReady()`
- `biomeHolderResolutionReady()`
- `resourceKeyConversionReady()`
- `dimensionBindingReady()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `consumesBiomeSelectionAdapter()`
- `decisionAllowsUnregisteredSkeleton()`
- `decisionAllowsCodecImplementation()`
- `decisionAllowsRegistryLookupAccess()`
- `decisionAllowsDimensionActivation()`
- `guardrailCount()`
- `allGuardrailsEnforced()`
- `adapterEntryCount()`
- `adapterTotalWeight()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`
- `activationRequirements()`
- `activationBlockedReason()`

## Pinned Facts

- skeleton shape is ready
- unregistered skeleton is ready
- skeleton consumes `CaveniaBiomeSelectionAdapterContract`
- skeleton preserves `x`/`y`/`z` but does not use coordinates for selection yet
- skeleton delegates selection through the existing adapter/weighted-selector path
- representative wrapping behavior remains pinned:
- `0` first entry candidate
- `675` first entry candidate
- `-1` last entry candidate
- the accepted decision allowed only an unregistered skeleton
- every guardrail remains enforced
- runtime `BiomeSource` is still not implemented
- runtime biome-source registration is still absent
- codec implementation/registration is still absent
- registry lookup access is still absent
- biome holder/resource-key conversion is still absent
- dimension binding is still absent
- dimension JSON/type JSON are absent
- active Cavenia level is absent
- active Cavenia worldgen resources are absent
- active access/teleport is absent
- active spawning is absent
- `cavernreborn:caveman` is absent
- `EntityCaveman -> deferred:caveman` remains unchanged
- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`

## Relationship To Existing Contracts

This runtime-shaped skeleton sits on top of:

- `CaveniaRuntimeBiomeSourceFirstImplementationDecision`
- `CaveniaRuntimeBiomeSourceGuardrail`
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

- extend or import Minecraft `BiomeSource`
- create runtime biome holders
- use registry lookup access
- implement or register a codec
- register a biome-source type
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

The next slice may decide whether to introduce a real Minecraft `BiomeSource` subclass or continue with additional guardrail/readiness work.

A real `BiomeSource` subclass must be a separate explicit MVP and must still avoid dimension activation unless explicitly chosen.

The runtime-biome-source codec/holder/registry decision follow-up is now documented in `docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md`.


The runtime-biome-source API-shape inventory follow-up is now documented in `docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md`.
