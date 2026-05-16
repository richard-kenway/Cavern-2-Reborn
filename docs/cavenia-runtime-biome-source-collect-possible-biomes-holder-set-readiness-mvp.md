# Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Readiness MVP

This is collect-possible-biomes holder-set readiness, not implementation.

## Summary

- future candidate source is current string candidate inventory.
- future converter dependency is `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter`.
- future lookup/provider dependency is pinned.
- future holder-set construction must be pre-resolved before runtime source can stream it.
- future holder-set output shape is pinned.
- future stream return boundary is pinned.
- future deduplication policy is pinned.
- future ordering policy is pinned.
- future fallback-if-empty policy is pinned.
- future missing candidate handling policy is pinned.
- future invalid candidate handling policy is pinned.
- future unresolved holder handling policy is pinned.
- No holder-set construction is implemented in this slice.
- Converter is not wired into `collectPossibleBiomes()`.
- Converter is not wired into `getNoiseBiome(...)`.
- The bounded follow-up now exists as `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder`, which stays outside runtime biome-source methods and uses caller-provided lookup/provider input only.

## Contracts

- The exact readiness files added in this slice are:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.java`
- The exact holder-set readiness component enum values are:
  - `CANDIDATE_INVENTORY_SOURCE`
  - `DESIGNATED_CONVERTER_DEPENDENCY`
  - `LOOKUP_PROVIDER_REQUIREMENT`
  - `PRE_RESOLVED_HOLDER_SET_POLICY`
  - `HOLDER_SET_OUTPUT_SHAPE`
  - `STREAM_RETURN_BOUNDARY`
  - `DEDUPLICATION_POLICY`
  - `ORDERING_POLICY`
  - `FALLBACK_IF_EMPTY_POLICY`
  - `MISSING_CANDIDATE_HANDLING_POLICY`
  - `INVALID_CANDIDATE_HANDLING_POLICY`
  - `UNRESOLVED_HOLDER_HANDLING_POLICY`
  - `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
  - `NO_CONVERTER_WIRING_IN_RUNTIME_METHODS`
  - `NO_GET_NOISE_BIOME_WIRING`
  - `NO_RUNTIME_CONSTRUCTION_OR_FACTORY`
  - `REAL_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_STILL_DEFERRED`
- The exact holder-set entry fields are:
  - `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent component`
  - `String sourceContractName`
  - `String holderSetSurface`
  - `String futurePolicy`
  - `boolean readinessPinned`
  - `boolean implementationReady`
  - `boolean runtimeWiringReady`
  - `boolean lookupProviderRequired`
  - `boolean lookupProviderAvailableInThisSlice`
  - `boolean activationAllowedInThisSlice`
  - `String blocker`

## Policy Surface

- Future candidate source stays `CaveniaLegacyToModernBiomeKeyMappings`.
- Future converter dependency stays `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter`.
- Future lookup/provider policy is: caller-provided biome lookup/provider is required before runtime construction.
- Future pre-resolved holder-set policy is: the runtime biome source must receive or store a pre-resolved holder set because `collectPossibleBiomes()` has no lookup/provider parameter.
- Future holder-set output shape is `pre-resolved biome holder set/list`.
- Future stream return boundary is: future `collectPossibleBiomes()` may stream only pre-resolved holder outputs after runtime construction is explicitly allowed.
- Future deduplication policy is: deduplicate resolved holders, not current string candidate inventory.
- Future ordering policy is: preserve legacy inventory order for first successful resolved holders.
- Future fallback-if-empty policy is: attempt fallback `minecraft:plains` only if all candidate conversions fail or the resolved holder set is empty.
- Future missing candidate policy is: skip unresolved or missing candidate keys during future holder-set construction without crashing runtime method.
- Future invalid candidate policy is: invalid candidate keys must not crash future holder-set construction.
- Future unresolved holder policy is: skip unresolved holder results during future holder-set construction and rely on fallback only when no holders resolve.

## Boundaries

- `collectPossibleBiomes()` remains unsupported.
- `getNoiseBiome(...)` remains unsupported.
- `codec()` remains unsupported.
- No usable codec implementation exists.
- No codec registration exists.
- No biome-source type registration exists.
- No generator registration exists.
- Subclass remains guarded, unregistered, and non-activating.
- Normal runtime construction remains unavailable.
- No dimension JSON/type JSON exist.
- No active Cavenia level exists.
- No worldgen resources exist.
- No access/teleport exists.
- No spawning exists.
- `cavernreborn:caveman` remains absent.
- `EntityCaveman -> deferred:caveman` remains unchanged.
- Selected-surface readiness item count remains `68`.
- Global readiness matrix total/blocked count remains `46/46`.

## Relationships

- `CaveniaRuntimeBiomeSource` remains the guarded subclass boundary and exposes holder-set readiness passthrough only.
- `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision` remains the prerequisite decision-only slice.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation` remains the prerequisite converter-consolidation layer.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter` remains the only designated file for lookup/conversion.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness` still pins the future string-key conversion path.
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness` still pins possible-biomes ordering, deduplication and fallback boundaries.
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness` still keeps runtime holder conversion blocked.
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness` still keeps runtime fallback behavior absent.
- `CaveniaRegistryLookupReadiness` still keeps runtime lookup access absent outside the designated converter.
- `CaveniaLegacyToModernBiomeKeyMappings` remains the future candidate inventory source.
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub` still keeps codec behavior unsupported.
- `CaveniaRuntimeBiomeSourceApiShapeInventory` still records the future stream return boundary.
- `CaveniaActivationReadinessMatrix` still keeps activation blocked.
- `CaveniaGeneratorRegistrationBoundary` still keeps generator registration blocked.

## Why This Is Still Bounded

- This is still not collectPossibleBiomes implementation because `CaveniaRuntimeBiomeSource` still does not call the converter.
- This is still not getNoiseBiome implementation because `CaveniaRuntimeBiomeSource` still does not call the converter.
- This is still not usable runtime biome resolution because the slice adds only inert readiness data.
- This is still not a usable runtime biome source because all runtime-facing abstract methods remain unsupported stubs.
- This is still not a ChunkGenerator because no generator file or generator registration was added.
- No usable codec implementation was added.
- No codec or registry entry was added.
- No generator registration was added.
- No dimension JSON/type JSON was added.
- No active generator was added.
- No active Cavenia level was added.
- No access/spawning/worldgen resources were added.

## Follow-up

- The collect-possible-biomes holder-set go/no-go note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set readiness note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
- The collect-possible-biomes holder-set implementation go/no-go note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-implementation-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set guarded builder note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-guarded-builder-mvp.md`.
- The next slice can be `Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Builder Consolidation / Next Decision MVP`.
- That next slice should still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
