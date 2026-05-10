# Cavenia Guarded Unregistered Real BiomeSource Subclass Stub MVP

This slice is a guarded unregistered real `BiomeSource` subclass stub, not runtime activation.

It adds exactly one new main-source subclass file:

- `CaveniaRuntimeBiomeSource.java`

This is the only main-source file allowed to extend, import, or use Minecraft `BiomeSource`.

This is the only main-source file allowed to extend/import/use Minecraft `BiomeSource`.

## Purpose

The subclass exists only to compile against the local abstract method signatures pinned by `CaveniaRuntimeBiomeSourceApiShapeInventory`.

Its purpose is narrow:

- compile against the local abstract method signatures
- keep method bodies unsupported and stubbed
- remain unregistered and non-activating

## Allowed Runtime API Types

The designated file uses only these runtime API types:

- `MapCodec`
- `Stream`
- `Holder`
- `Biome`
- `BiomeSource`
- `Climate`

## Unsupported Methods

`CaveniaRuntimeBiomeSource` implements only these locally pinned abstract method shapes:

- `codec()`
- `collectPossibleBiomes()`
- `getNoiseBiome(int, int, int, Climate.Sampler)`

All runtime-facing methods are unsupported and stubbed.

Method bodies are unsupported/stubbed.

method bodies unsupported/stubbed

Normal runtime construction remains unavailable because the class exposes no public constructor or public factory.

normal runtime construction remains unavailable

## App-Side Helpers

`CaveniaRuntimeBiomeSource` exposes deterministic static helpers:

- `guardedSubclassStubReady()`
- `extendsMinecraftBiomeSource()`
- `designatedSubclassFileName()`
- `designatedSubclassSimpleName()`
- `normalRuntimeConstructionAllowed()`
- `usableRuntimeBehaviorReady()`
- `unsupportedMethodStubsOnly()`
- `codecMethodStubbed()`
- `collectPossibleBiomesStubbed()`
- `getNoiseBiomeStubbed()`
- `usableCodecImplementationReady()`
- `codecRegistered()`
- `biomeSourceTypeRegistered()`
- `registryLookupAccessReady()`
- `holderResourceKeyConversionReady()`
- `dimensionBindingReady()`
- `activationAllowedInThisSlice()`
- `canActivateCaveniaNow()`
- `apiShapeInventoryReady()`
- `goNoGoDecisionAllowsSubclass()`
- `allGoNoGoGuardrailsEnforced()`
- `selectedSurfaceReadinessItemCount()`
- `globalReadinessMatrixTotalRequirementCount()`
- `globalReadinessMatrixBlockedRequirementCount()`
- `dimensionJsonPresent()`
- `dimensionTypeJsonPresent()`
- `cavemanRemainsDeferred()`

The guarded subclass stub is ready.

Usable runtime behavior is not ready.

## Still Absent

This slice still does not add:

- usable codec implementation
- codec registration
- biome-source type registration
- registry lookup access
- holder/resource-key conversion implementation
- `dimension/cavenia.json`
- `dimension_type/cavenia.json`
- active Cavenia level creation
- worldgen resources
- access or teleport
- spawning
- `cavernreborn:caveman`

`EntityCaveman -> deferred:caveman` remains unchanged.

No registry lookup access exists.

No holder/resource-key conversion implementation exists.

no registry lookup access exists

no holder/resource-key conversion implementation exists

no dimension JSON/type JSON exist

no active Cavenia level exists

no worldgen resources exist

no access/teleport exists

no spawning exists

`cavernreborn:caveman` remains absent

Selected-surface readiness item count remains `68`.

Global readiness matrix total and blocked counts remain `46/46`.

## Relationships

This guarded stub builds on:

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

## Why This Is Still Not Usable

This is still not a usable runtime biome source because:

- every runtime-facing method body is unsupported and stubbed
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- no registry lookup access was added
- no holder/resource-key conversion implementation was added
- the subclass remains unregistered and unreferenced by runtime surfaces

This is still not a `ChunkGenerator`.

No active generator was added.

No dimension JSON or dimension type JSON was added.

No active Cavenia level was added.

No access, spawning, or worldgen resources were added.

## Future Recommendation

The next slice can decide how to make the codec method shape explicit without creating a usable or registered codec yet.

It can also define holder and resource-key conversion readiness for the subclass before any runtime activation work.
