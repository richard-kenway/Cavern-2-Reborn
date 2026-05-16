# Cavenia Runtime BiomeSource Runtime Construction / Factory Go/No-Go MVP

This is runtime construction/factory go/no-go decision, not readiness and not implementation.

## Summary

- selected decision is `PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_READINESS_NEXT`
- next slice may add runtime construction/factory readiness data
- next slice may pin private constructor policy
- next slice may pin factory method policy
- next slice may pin pre-resolved holder state policy
- next slice may pin holder-set builder dependency
- next slice may pin lookup/provider input policy
- next slice may pin immutable state storage policy
- next slice may pin `collectPossibleBiomes()` stream-source policy
- next slice may pin no-activation boundary
- next slice may not implement runtime construction/factory
- next slice may not add public constructor
- next slice may not add factory method
- next slice may not add pre-resolved holder state
- next slice may not wire builder into runtime methods
- builder consolidation remains ready
- builder remains unwired

## Boundaries

- `collectPossibleBiomes()` remains unsupported
- `getNoiseBiome(...)` remains unsupported
- `codec()` remains unsupported
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- no generator registration exists
- subclass remains guarded, unregistered, and non-activating
- normal runtime construction remains unavailable
- no dimension JSON/type JSON exist
- no active Cavenia level exists
- no worldgen resources exist
- no access/teleport exists
- no spawning exists
- `cavernreborn:caveman` remains absent
- `EntityCaveman -> deferred:caveman` remains unchanged
- selected-surface readiness item count remains `68`
- global readiness matrix total/blocked count remains `46/46`

## Relationships

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness`
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness`
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- `CaveniaRegistryLookupReadiness`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub`
- `CaveniaRuntimeBiomeSourceApiShapeInventory`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Is Still Bounded

- this is still not runtime construction/factory readiness because the current slice adds only decision guardrails and no readiness matrix
- this is still not runtime construction/factory implementation because the current slice adds no constructor, factory method, or stored holder state
- this is still not collectPossibleBiomes implementation because `CaveniaRuntimeBiomeSource` still does not call the builder
- this is still not getNoiseBiome implementation because `CaveniaRuntimeBiomeSource` still does not call the builder or converter
- this is still not usable runtime biome resolution because holder aggregation remains outside runtime methods
- this is still not a usable runtime biome source because all runtime-facing abstract methods remain unsupported stubs
- this is still not a ChunkGenerator because no generator file or generator registration was added
- no usable codec implementation was added
- no codec/registry entry was added
- no generator registration was added
- no dimension JSON/type JSON was added
- no active generator was added
- no active Cavenia level was added
- no access/spawning/worldgen resources were added

## Follow-up

- the runtime construction/factory go/no-go note is documented in `docs/cavenia-runtime-biome-source-runtime-construction-factory-go-no-go-mvp.md`
- the next slice can be `Cavenia Runtime BiomeSource Runtime Construction / Factory Readiness MVP`
- that next slice should remain readiness-only unless explicitly requested otherwise
- that next slice must still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise
