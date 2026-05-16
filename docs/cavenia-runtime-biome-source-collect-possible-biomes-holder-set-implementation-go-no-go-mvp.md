# Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Implementation Go/No-Go MVP

This is collect-possible-biomes holder-set implementation go/no-go decision, not implementation.

## Summary

- selected decision is `PROCEED_WITH_GUARDED_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_BUILDER_NEXT`.
- next slice may add guarded pre-resolved holder-set builder.
- next slice designated builder simple name is `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder`.
- next slice designated builder file name is `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java`.
- next slice may use candidate inventory input.
- next slice may use designated converter dependency.
- next slice may require lookup provider parameter.
- next slice may deduplicate resolved holders.
- next slice may preserve legacy order.
- next slice may apply fallback-if-empty.
- next slice may skip missing/invalid/unresolved candidate keys.
- next slice may not wire builder into `collectPossibleBiomes()`.
- next slice may not implement `collectPossibleBiomes()`.
- next slice may not wire builder into `getNoiseBiome(...)`.
- next slice may not make runtime methods usable.
- this slice does not create the builder.
- this slice does not construct a holder set.
- the bounded follow-up now exists as `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder`, but it still remains unwired from runtime biome-source methods.
- collect-possible-biomes holder-set readiness remains ready.
- converter consolidation remains ready.
- converter remains the only designated file for lookup/conversion.

## Boundaries

- `collectPossibleBiomes()` remains unsupported.
- `getNoiseBiome(...)` remains unsupported.
- `codec()` remains unsupported.
- no usable codec implementation exists.
- no codec registration exists.
- no biome-source type registration exists.
- no generator registration exists.
- subclass remains guarded, unregistered, and non-activating.
- normal runtime construction remains unavailable.
- no dimension JSON/type JSON exist.
- no active Cavenia level exists.
- no worldgen resources exist.
- no access/teleport exists.
- no spawning exists.
- `cavernreborn:caveman` remains absent.
- `EntityCaveman -> deferred:caveman` remains unchanged.
- selected-surface readiness item count remains `68`.
- global readiness matrix total/blocked count remains `46/46`.

## Relationships

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter`
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

- this is still not collectPossibleBiomes holder-set builder implementation because no builder file exists in this slice.
- this is still not collectPossibleBiomes implementation because `CaveniaRuntimeBiomeSource` still does not call the converter or any builder.
- this is still not getNoiseBiome implementation because `CaveniaRuntimeBiomeSource` still does not call the converter or any builder.
- this is still not usable runtime biome resolution because the slice adds only inert decision data.
- this is still not a usable runtime biome source because all runtime-facing abstract methods remain unsupported stubs.
- this is still not a ChunkGenerator because no generator file or generator registration was added.
- no usable codec implementation was added.
- no codec/registry entry was added.
- no generator registration was added.
- no dimension JSON/type JSON was added.
- no active generator was added.
- no active Cavenia level was added.
- no access/spawning/worldgen resources were added.

## Follow-up

- The collect-possible-biomes holder-set go/no-go note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set readiness note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
- The collect-possible-biomes holder-set implementation go/no-go note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-implementation-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set guarded builder note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-guarded-builder-mvp.md`.
- The holder-set builder consolidation next-decision note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-builder-consolidation-next-decision-mvp.md`.
- The next slice can be `Cavenia Runtime BiomeSource Runtime Construction / Factory Go/No-Go MVP`.
- That next slice should still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
