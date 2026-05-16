# Cavenia Runtime BiomeSource Candidate-Key-to-Holder Converter Consolidation / Next Decision MVP

This is converter consolidation and next-decision, not runtime method implementation.

## Summary

- The designated converter exists.
- The converter is ready.
- `holderForCandidateKey(...)` is ready.
- `holderForCandidateKeyOrFallback(...)` is ready.
- Resource-location parsing remains confined to the designated converter.
- Resource-key construction remains confined to the designated converter.
- Biome registry lookup remains confined to the designated converter.
- Holder resolution remains confined to the designated converter.
- Fallback holder attempt remains confined to the designated converter.
- The converter requires caller-provided lookup/provider.
- The converter does not access server/world globals.
- The converter is not wired into `getNoiseBiome(...)`.
- The converter is not wired into `collectPossibleBiomes()`.
- The selected next decision is `PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_GO_NO_GO_NEXT`.
- The next slice may add only `collectPossibleBiomes()` holder-set go/no-go decision.
- The next slice may not add holder-set readiness.
- The next slice may not implement `collectPossibleBiomes()`.
- The next slice may not wire converter into `collectPossibleBiomes()`.
- The next slice may not wire converter into `getNoiseBiome(...)`.

## Contracts

- The exact consolidation files added in this slice are:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.java`
- The exact consolidation component enum values are:
  - `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent`
  - `DESIGNATED_CONVERTER_EXISTS`
  - `CANDIDATE_KEY_TO_HOLDER_CONVERTER_READY`
  - `STRING_CANDIDATE_KEY_INPUT_READY`
  - `RESOURCE_LOCATION_PARSE_CONFINED`
  - `RESOURCE_KEY_CONSTRUCTION_CONFINED`
  - `BIOME_REGISTRY_LOOKUP_CONFINED`
  - `HOLDER_RESOLUTION_CONFINED`
  - `OPTIONAL_HOLDER_RESULT_READY`
  - `FALLBACK_HOLDER_ATTEMPT_READY`
  - `SERVER_GLOBAL_ACCESS_ABSENT`
  - `GET_NOISE_BIOME_WIRING_BLOCKED`
  - `COLLECT_POSSIBLE_BIOMES_WIRING_BLOCKED`
  - `CODEC_AND_REGISTRATION_BLOCKED`
  - `DIMENSION_WORLDGEN_ACCESS_SPAWNING_BLOCKED`
  - `NEXT_DECISION_SELECTED`
- The exact consolidation entry fields are:
  - `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry`
  - `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent component`
  - `String sourceContractName`
  - `String consolidatedState`
  - `String runtimeBoundary`
  - `boolean converterReady`
  - `boolean runtimeReady`
  - `boolean nextDecisionInput`
  - `String blocker`
- The exact next-decision enum values are:
  - `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision`
  - `PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_GO_NO_GO_NEXT`
  - `DEFER_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_READINESS`
  - `DEFER_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
  - `DEFER_GET_NOISE_BIOME_WIRING`
  - `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
  - `DEFER_CODEC_REGISTRATION`
  - `DEFER_DIMENSION_ACTIVATION`
  - `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Helper Surface

- The exact helper methods exposed by `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation` include:
  - `entries()`
  - `components()`
  - `entryFor(...)`
  - `entryCount()`
  - `candidateKeyToHolderConverterConsolidationReady()`
  - `designatedConverterExists()`
  - `designatedConverterSimpleName()`
  - `designatedConverterFileName()`
  - `candidateKeyToHolderConverterReady()`
  - `candidateKeyToHolderConverterRuntimeReady()`
  - `holderForCandidateKeyReady()`
  - `holderForCandidateKeyOrFallbackReady()`
  - `stringCandidateKeyInputReady()`
  - `resourceLocationParseConfinedToConverter()`
  - `resourceKeyConstructionConfinedToConverter()`
  - `biomeRegistryLookupConfinedToConverter()`
  - `holderResolutionConfinedToConverter()`
  - `optionalHolderResultReady()`
  - `fallbackHolderAttemptReady()`
  - `serverGlobalAccessAbsent()`
  - `getNoiseBiomeWiringReady()`
  - `collectPossibleBiomesWiringReady()`
  - `runtimeBiomeSourceReady()`
  - `collectPossibleBiomesHolderSetGoNoGoIsNext()`
  - `collectPossibleBiomesHolderSetReadinessReady()`
  - `collectPossibleBiomesHolderSetImplementationReady()`
  - `collectPossibleBiomesHolderSetRuntimeReady()`
  - `selectedNextDecision()`
  - `nextSliceMayAddCollectPossibleBiomesHolderSetGoNoGoDecision()`
  - `nextSliceMayAddCollectPossibleBiomesHolderSetReadiness()`
  - `nextSliceMayImplementCollectPossibleBiomes()`
  - `nextSliceMayWireConverterIntoCollectPossibleBiomes()`
  - `nextSliceMayWireConverterIntoGetNoiseBiome()`
  - `nextSliceMayMakeGetNoiseBiomeUsable()`
  - `nextSliceMayMakeCollectPossibleBiomesUsable()`
  - `nextSliceMayAddUsableCodecImplementation()`
  - `nextSliceMayRegisterCodec()`
  - `nextSliceMayRegisterBiomeSourceType()`
  - `nextSliceMayRegisterGenerator()`
  - `nextSliceMayAddDimensionJson()`
  - `nextSliceMayAddDimensionTypeJson()`
  - `nextSliceMayCreateActiveCaveniaLevel()`
  - `nextSliceMayAddWorldgenResources()`
  - `nextSliceMayAddAccessOrTeleport()`
  - `nextSliceMayAddSpawning()`
  - `nextSliceMayRegisterCavemanEntity()`
- The exact `CaveniaRuntimeBiomeSource` passthrough additions in this slice are:
  - `candidateKeyToHolderConverterConsolidationReady()`
  - `candidateKeyToHolderConverterConsolidationRuntimeReady()`
  - `collectPossibleBiomesHolderSetGoNoGoIsNext()`

## Boundaries

- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
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

- `CaveniaRuntimeBiomeSource` remains the guarded subclass boundary and exposes consolidation-state passthrough only.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter` remains the only designated conversion file.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision` selected the guarded converter implementation slice before this consolidation.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness` remains the pinned string-key and future conversion policy layer.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision` remains the earlier decision-only branch that opened readiness.
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge` remains the source of current modern biome candidate keys.
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness` still pins holder-facing expectations without runtime wiring.
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness` still pins `PLAINS -> minecraft:plains` as readiness data outside runtime methods.
- `CaveniaRegistryLookupReadiness` remains the source contract for lookup gating.
- `CaveniaLegacyToModernBiomeKeyMappings` remains the candidate inventory source.
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness` remains readiness-only and does not use the converter yet.
- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness` remains readiness-only and does not use the converter yet.
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub` still keeps codec behavior unsupported.
- `CaveniaRuntimeBiomeSourceApiShapeInventory` still records the guarded abstract method boundary.
- `CaveniaRuntimeBiomeSourceSkeleton` still keeps dimension resources absent.
- `CaveniaBiomeSourceStrategyPlan` still points to `BIOME_SOURCE_STRATEGY` as the first future activation surface.
- `CaveniaActivationReadinessMatrix` still keeps activation blocked.
- `CaveniaGeneratorRegistrationBoundary` still keeps generator registration blocked.

## Why This Is Still Bounded

- This is still not `collectPossibleBiomes()` implementation because the converter is still not called by `CaveniaRuntimeBiomeSource`.
- This is still not `getNoiseBiome(...)` implementation because the converter is still not called by `CaveniaRuntimeBiomeSource`.
- This is still not usable runtime biome resolution because the consolidated helper state remains outside runtime method wiring.
- This is still not a usable runtime biome source because all runtime-facing abstract methods remain unsupported stubs.
- This is still not a `ChunkGenerator` because no generator file or generator registration was added.
- No usable codec implementation was added.
- No codec or registry entry was added.
- No generator registration was added.
- No dimension JSON/type JSON was added.
- No active generator was added.
- No active Cavenia level was added.
- No access/spawning/worldgen resources were added.

## Follow-up

- The guarded converter implementation note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md`.
- The converter consolidation next-decision note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The next slice can be `Cavenia Runtime BiomeSource Collect-Possible-Biomes Holder Set Go/No-Go MVP`.
- That next slice should remain decision-only unless explicitly requested otherwise.
- That next slice must still keep `collectPossibleBiomes()` and `getNoiseBiome(...)` unusable unless explicitly requested otherwise.
