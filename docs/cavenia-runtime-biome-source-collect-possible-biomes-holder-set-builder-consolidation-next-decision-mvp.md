# Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Builder Consolidation / Next Decision MVP

This is holder-set builder consolidation and next-decision, not runtime construction/factory readiness.

## Summary

- designated builder exists
- builder is ready
- `buildCandidateHolderList(...)` is ready
- builder accepts caller-provided lookup/provider
- builder uses candidate inventory input
- builder uses the designated converter dependency
- builder returns immutable or safely unmodifiable pre-resolved holder list
- builder deduplicates resolved holders
- builder preserves first-success legacy order
- builder applies fallback-if-empty
- builder is not wired into `collectPossibleBiomes()`
- builder is not wired into `getNoiseBiome(...)`
- selected next decision is `PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_GO_NO_GO_NEXT`
- next slice may add only runtime construction/factory go/no-go decision
- next slice may not add runtime construction/factory readiness
- next slice may not add runtime construction/factory implementation
- next slice may not wire builder into runtime methods

## Boundaries

- `collectPossibleBiomes()` remains unsupported
- `getNoiseBiome(...)` remains unsupported
- `codec()` remains unsupported
- no usable codec implementation exists
- no codec registration exists
- no biome-source type registration exists
- no generator registration exists
- subclass remains guarded, unregistered and non-activating
- normal runtime construction remains unavailable
- no dimension JSON or dimension type JSON exist
- no active Cavenia level exists
- no worldgen resources exist
- no access or teleport exists
- no spawning exists
- `cavernreborn:caveman` remains absent
- `EntityCaveman -> deferred:caveman` remains unchanged
- selected-surface readiness item count remains `68`
- global readiness matrix total or blocked count remains `46/46`

## Relationships

- `CaveniaRuntimeBiomeSource`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder`
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision`
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

- this is still not runtime construction/factory go/no-go implementation because the current slice adds only a builder consolidation note and selects the next decision enum
- this is still not runtime construction/factory readiness because no readiness matrix or runtime-construction input contract was added
- this is still not collectPossibleBiomes implementation because `CaveniaRuntimeBiomeSource` still does not call the builder
- this is still not getNoiseBiome implementation because `CaveniaRuntimeBiomeSource` still does not call the builder or converter
- this is still not usable runtime biome resolution because the builder remains a guarded helper outside runtime methods
- this is still not a usable runtime biome source because all runtime-facing abstract methods remain unsupported stubs
- this is still not a ChunkGenerator because no generator file or generator registration was added
- no usable codec implementation was added
- no codec or registry entry was added
- no generator registration was added
- no dimension JSON or dimension type JSON was added
- no active generator was added
- no active Cavenia level was added
- no access, spawning or worldgen resources were added

## Follow-up

- the holder-set builder consolidation note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-builder-consolidation-next-decision-mvp.md`
- the next slice can be `Cavenia Runtime BiomeSource Runtime Construction / Factory Go/No-Go MVP`
- that next slice should remain decision-only unless explicitly requested otherwise
- that next slice must still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise
