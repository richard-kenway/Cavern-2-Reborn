# Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Guarded Builder MVP

This is guarded pre-resolved holder-set builder implementation, not runtime method implementation.

## Summary

- the exact designated builder file added is `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java`
- the exact builder method signatures chosen are `public static List<Holder<Biome>> buildCandidateHolderList(HolderLookup.Provider lookupProvider)` and `public static List<Holder<Biome>> buildCandidateHolderList(List<String> candidateKeys, HolderLookup.Provider lookupProvider)`
- the exact builder component enum values are `DESIGNATED_BUILDER_FILE`, `CANDIDATE_INVENTORY_INPUT`, `LOOKUP_PROVIDER_PARAMETER`, `DESIGNATED_CONVERTER_USAGE`, `RESOLVED_HOLDER_COLLECTION`, `RESOLVED_HOLDER_DEDUPLICATION`, `LEGACY_ORDER_PRESERVATION`, `MISSING_INVALID_UNRESOLVED_SKIP`, `FALLBACK_IF_EMPTY`, `IMMUTABLE_OUTPUT_LIST`, `NO_COLLECT_POSSIBLE_BIOMES_WIRING`, `NO_GET_NOISE_BIOME_WIRING` and `REAL_RUNTIME_BIOME_SOURCE_STILL_DEFERRED`
- the exact builder entry fields are `component`, `sourceContractName`, `builderBehavior`, `runtimeBoundary`, `builderReady`, `runtimeBiomeSourceReady`, `activationAllowedInThisSlice` and `blocker`
- builder accepts caller-provided lookup/provider
- builder uses current candidate inventory
- builder uses the designated converter
- builder deduplicates resolved holders
- builder preserves first successful legacy order
- builder skips missing, invalid and unresolved candidate keys
- builder applies fallback-if-empty using `minecraft:plains`
- builder returns immutable or safely unmodifiable list
- builder does not access server or world globals
- builder is not wired into `collectPossibleBiomes()`
- builder is not wired into `getNoiseBiome(...)`

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

- this is still not collectPossibleBiomes implementation because `CaveniaRuntimeBiomeSource` still does not call the builder
- this is still not getNoiseBiome implementation because `CaveniaRuntimeBiomeSource` still does not call the builder
- this is still not usable runtime biome resolution because the builder only prepares guarded helper output outside runtime methods
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

- the collect-possible-biomes holder-set guarded builder note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-guarded-builder-mvp.md`
- the next slice can be `Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Builder Consolidation / Next Decision MVP`
- that next slice should decide whether to proceed toward guarded runtime construction or factory readiness or stop for review
- that next slice must still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise
