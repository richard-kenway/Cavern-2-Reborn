# Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Go/No-Go MVP

This is collect-possible-biomes holder-set go/no-go decision, not readiness and not implementation.

## Summary

- The selected decision is `PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT`.
- The next slice may add collect-possible-biomes holder-set readiness data.
- The next slice may pin candidate inventory source.
- The next slice may pin converter dependency.
- The next slice may pin holder-set output policy.
- The next slice may pin deduplication policy.
- The next slice may pin ordering policy.
- The next slice may pin fallback-if-empty policy.
- The next slice may pin missing candidate policy.
- The next slice may pin stream return boundary.
- The next slice may not implement `collectPossibleBiomes()`.
- The next slice may not wire converter into `collectPossibleBiomes()`.
- The next slice may not wire converter into `getNoiseBiome(...)`.
- converter consolidation remains ready.
- converter remains the only designated file for lookup/conversion.

## Contracts

- The exact go/no-go files added in this slice are:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrailContract.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision.java`
- The exact next-decision enum values are:
  - `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetNextDecision`
  - `PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT`
  - `DEFER_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_IMPLEMENTATION`
  - `DEFER_COLLECT_POSSIBLE_BIOMES_WIRING`
  - `DEFER_GET_NOISE_BIOME_WIRING`
  - `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
  - `DEFER_CODEC_REGISTRATION`
  - `DEFER_DIMENSION_ACTIVATION`
  - `DEFER_WORLDGEN_ACCESS_SPAWNING`
- The exact guardrail enum values are:
  - `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail`
  - `ALLOW_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS_NEXT`
  - `ALLOW_CANDIDATE_INVENTORY_SOURCE_PINNING_NEXT`
  - `ALLOW_CONVERTER_DEPENDENCY_PINNING_NEXT`
  - `ALLOW_HOLDER_SET_OUTPUT_POLICY_PINNING_NEXT`
  - `ALLOW_DEDUPLICATION_POLICY_PINNING_NEXT`
  - `ALLOW_ORDERING_POLICY_PINNING_NEXT`
  - `ALLOW_FALLBACK_IF_EMPTY_POLICY_PINNING_NEXT`
  - `ALLOW_MISSING_CANDIDATE_POLICY_PINNING_NEXT`
  - `ALLOW_STREAM_RETURN_BOUNDARY_PINNING_NEXT`
  - `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
  - `NO_COLLECT_POSSIBLE_BIOMES_WIRING`
  - `NO_GET_NOISE_BIOME_WIRING`
  - `NO_GET_NOISE_BIOME_IMPLEMENTATION`
  - `NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION`
  - `NO_GENERATOR_REGISTRATION`
  - `NO_DIMENSION_ACTIVATION`
  - `NO_WORLDGEN_ACCESS_SPAWNING`
  - `KEEP_RUNTIME_METHODS_UNSUPPORTED`
- The exact guardrail contract fields are:
  - `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGuardrail guardrail`
  - `String sourceContractName`
  - `boolean enforcedInThisSlice`
  - `boolean allowsNextSliceReadinessAction`
  - `boolean allowsNextSliceImplementationAction`
  - `boolean allowsNextSliceRuntimeWiringAction`
  - `boolean allowsNextSliceActivationAction`
  - `boolean blocksActivation`
  - `String blocker`

## Helper Surface

- The exact helper methods exposed by `CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision` include:
  - `selectedDecision()`
  - `decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext()`
  - `decisionIsReadinessOnlyForNextSlice()`
  - `collectPossibleBiomesHolderSetReadinessImplementedInThisSlice()`
  - `collectPossibleBiomesHolderSetImplementationReady()`
  - `collectPossibleBiomesHolderSetRuntimeReady()`
  - `guardrails()`
  - `guardrailValues()`
  - `guardrailFor(...)`
  - `allowedNextSliceReadinessActionCount()`
  - `nextSliceMayAddCollectPossibleBiomesHolderSetReadiness()`
  - `nextSliceMayPinCandidateInventorySource()`
  - `nextSliceMayPinConverterDependency()`
  - `nextSliceMayPinHolderSetOutputPolicy()`
  - `nextSliceMayPinDeduplicationPolicy()`
  - `nextSliceMayPinOrderingPolicy()`
  - `nextSliceMayPinFallbackIfEmptyPolicy()`
  - `nextSliceMayPinMissingCandidatePolicy()`
  - `nextSliceMayPinStreamReturnBoundary()`
  - `nextSliceMayImplementCollectPossibleBiomes()`
  - `nextSliceMayWireConverterIntoCollectPossibleBiomes()`
  - `nextSliceMayWireConverterIntoGetNoiseBiome()`
- The exact `CaveniaRuntimeBiomeSource` passthrough additions in this slice are:
  - `collectPossibleBiomesHolderSetGoNoGoDecisionReady()`
  - `collectPossibleBiomesHolderSetRuntimeReady()`
  - `collectPossibleBiomesHolderSetReadinessIsNext()`

## Boundaries

- `collectPossibleBiomes()` remains unsupported.
- `getNoiseBiome(...)` remains unsupported.
- `codec()` remains unsupported.
- no usable codec implementation exists.
- no codec registration exists.
- no biome-source type registration exists.
- no generator registration exists.
- Subclass remains guarded, unregistered, and non-activating.
- Normal runtime construction remains unavailable.
- no dimension JSON/type JSON exist.
- no active Cavenia level exists.
- no worldgen resources exist.
- no access/teleport exists.
- no spawning exists.
- `cavernreborn:caveman` remains absent.
- `EntityCaveman -> deferred:caveman` remains unchanged.
- Selected-surface readiness item count remains `68`.
- Global readiness matrix total/blocked count remains `46/46`.

## Relationships

- `CaveniaRuntimeBiomeSource` remains the guarded subclass boundary and exposes go/no-go passthrough only.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation` remains the prerequisite converter consolidation layer.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter` remains the only designated file for lookup/conversion.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness` still pins the future string-key conversion path.
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness` remains readiness-only and still does not use the converter.
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness` still keeps runtime holder conversion blocked.
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness` still keeps fallback runtime behavior absent.
- `CaveniaRegistryLookupReadiness` still keeps runtime lookup access absent outside the designated converter.
- `CaveniaLegacyToModernBiomeKeyMappings` remains the candidate inventory source.
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub` still keeps codec behavior unsupported.
- `CaveniaRuntimeBiomeSourceApiShapeInventory` still records the future stream return boundary.
- `CaveniaActivationReadinessMatrix` still keeps activation blocked.
- `CaveniaGeneratorRegistrationBoundary` still keeps generator registration blocked.

## Why This Is Still Bounded

- This is still not collectPossibleBiomes holder-set readiness because it does not add holder-set policy entries or readiness inventory.
- This is still not collectPossibleBiomes implementation because `CaveniaRuntimeBiomeSource` still does not call the converter.
- This is still not getNoiseBiome implementation because `CaveniaRuntimeBiomeSource` still does not call the converter.
- This is still not usable runtime biome resolution because the decision layer adds only inert policy gating.
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

- The converter consolidation next-decision note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The collect-possible-biomes holder-set go/no-go note is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The next slice can be `Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Readiness MVP`.
- That next slice should remain readiness-only unless explicitly requested otherwise.
- That next slice must still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
