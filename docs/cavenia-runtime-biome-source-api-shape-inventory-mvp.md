# Cavenia Runtime BiomeSource API Shape Inventory MVP

This is an API-shape inventory layer, not runtime activation.

The API was inspected from local dependencies, not web docs.

The local `BiomeSource` class origin used for this slice is:

- `app-neoforge/build/neoForm/neoFormJoined1.21.1-20240808.144430/steps/recompile/classes/net/minecraft/world/level/biome/BiomeSource.class`
- inspected locally with `javap -p -s -c`

The local climate-sampler shape used for this slice is:

- `app-neoforge/build/neoForm/neoFormJoined1.21.1-20240808.144430/steps/recompile/classes/net/minecraft/world/level/biome/Climate$Sampler.class`
- inspected locally with `javap -p`

the unregistered skeleton is ready.

the API shape inventory is ready.

the real Minecraft `BiomeSource` subclass is still deferred.

## Exact Classes Added

- `CaveniaRuntimeBiomeSourceApiShapeComponent`
- `CaveniaRuntimeBiomeSourceApiShapeInventoryEntry`
- `CaveniaRuntimeBiomeSourceApiShapeInventory`

## Exact Component Enum Values

- `LOCAL_BIOME_SOURCE_CLASS`
- `ABSTRACT_METHODS_INVENTORY`
- `CODEC_DISPATCH_SHAPE`
- `CODEC_METHOD_SHAPE`
- `POSSIBLE_BIOMES_SHAPE`
- `NOISE_BIOME_QUERY_SHAPE`
- `CLIMATE_SAMPLER_SHAPE`
- `HOLDER_RETURN_SHAPE`
- `REGISTRY_CONTEXT_SHAPE`
- `RESOURCE_KEY_CONVERSION_SHAPE`
- `MISSING_BIOME_FALLBACK_SHAPE`
- `REAL_SUBCLASS_STILL_DEFERRED`

## Exact Inventory Entry Fields

`CaveniaRuntimeBiomeSourceApiShapeInventoryEntry` exposes:

- `component`
- `sourceReference`
- `observedShape`
- `implementationImplication`
- `locallyInspected`
- `shapePinned`
- `runtimeApiImplemented`
- `runtimeApiAllowedInThisSlice`
- `activationAllowedInThisSlice`
- `blocker`

## Exact Inventory Helpers

`CaveniaRuntimeBiomeSourceApiShapeInventory` exposes:

- `entries()`
- `components()`
- `entryFor(CaveniaRuntimeBiomeSourceApiShapeComponent)`
- `entryCount()`
- `localBiomeSourceClassLocated()`
- `apiShapeInventoryReady()`
- `allComponentsLocallyInspected()`
- `allShapesPinned()`
- `runtimeApiImplemented()`
- `runtimeApiAllowedInThisSlice()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `abstractMethodsInventoryReady()`
- `codecDispatchShapeReady()`
- `codecMethodShapeReady()`
- `possibleBiomesShapeReady()`
- `noiseBiomeQueryShapeReady()`
- `climateSamplerShapeReady()`
- `holderReturnShapeReady()`
- `registryContextShapeReady()`
- `resourceKeyConversionShapeReady()`
- `missingBiomeFallbackShapeReady()`
- `realSubclassStillDeferred()`
- `readyForRealSubclassImplementation()`
- `nextSliceMayDecideRealSubclassImplementation()`
- `nextSliceMayImplementRealBiomeSourceSubclassDirectly()`
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
- `skeletonReady()`
- `skeletonRuntimeReady()`
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

## Exact Observed Local Shapes

- `BiomeSource` class identity/package:
  `public abstract class net.minecraft.world.level.biome.BiomeSource implements net.minecraft.world.level.biome.BiomeResolver`
- abstract methods inventory:
  `protected abstract com.mojang.serialization.MapCodec<? extends net.minecraft.world.level.biome.BiomeSource> codec();`
  `protected abstract java.util.stream.Stream<net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> collectPossibleBiomes();`
  `public abstract net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> getNoiseBiome(int, int, int, net.minecraft.world.level.biome.Climate$Sampler);`
- codec dispatch shape:
  static `CODEC` uses `net.minecraft.core.registries.BuiltInRegistries.BIOME_SOURCE.byNameCodec().dispatchStable(..., java.util.function.Function.identity())`
- codec method shape:
  `protected abstract com.mojang.serialization.MapCodec<? extends net.minecraft.world.level.biome.BiomeSource> codec();`
- possible-biomes shape:
  `BiomeSource` has a memoized `Supplier<Set<Holder<Biome>>> possibleBiomes`, an abstract `collectPossibleBiomes()` stream, and a public `possibleBiomes()` accessor
- noise-biome query shape:
  `getBiomesWithin(...)`, `findBiomeHorizontal(...)`, and `findClosestBiome3d(...)` delegate through `getNoiseBiome(int, int, int, Climate$Sampler)`
- climate sampler shape:
  `net.minecraft.world.level.biome.Climate$Sampler` is a record with `sample(int, int, int)`, `findSpawnPosition()`, six `DensityFunction` components, and `List<Climate$ParameterPoint> spawnTarget`
- holder return shape:
  local `BiomeSource` signatures return and collect `net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>` values
- registry context shape:
  local `BiomeSource` signatures do not expose `RegistryAccess` or `RegistryLookup` directly; registry context is implicit through holder returns and static `BuiltInRegistries.BIOME_SOURCE` codec dispatch
- resource-key conversion requirement:
  no observed `BiomeSource` signature mentions `ResourceKey<Biome>`, so current string-only candidate keys still need a separate future conversion design
- missing-biome fallback requirement:
  helper methods assume `getNoiseBiome(...)` returns usable holder values, so missing-biome fallback still needs an explicit future design

## Pinned Facts

- API shape decisions are now pinned as inventory/data
- no real `BiomeSource` subclass is added in this slice
- no runtime API usage is allowed in main source in this slice
- next slice may decide whether to implement a real subclass
- next slice may not implement real subclass directly without a separate explicit decision
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

This inventory layer sits on top of:

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

## Why This Stays Inert

This slice is still not a real `BiomeSource`.

This slice is still not a `ChunkGenerator`.

no registry lookup access was added.

no biome holder/resource-key conversion implementation was added.

no codec implementation was added.

no codec or registry entry was added.

It does not:

- create a real runtime biome-source subclass
- use runtime biome-source APIs in main source
- add registry lookup access
- add biome holder or runtime biome-key conversion implementation
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

The next slice can make an explicit go/no-go decision for a guarded real `BiomeSource` subclass based on this inventory.

Any real subclass must still avoid dimension activation unless explicitly chosen.

The runtime-biome-source real-subclass go/no-go decision follow-up is now documented in `docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md`.

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
- The candidate-key-to-holder conversion go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md`.
- The candidate-key-to-holder conversion readiness follow-up is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-readiness-mvp.md`.
