# Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Guarded Implementation MVP

This is guarded candidate-key-to-holder converter implementation, not runtime biome-source behavior.

## Summary

- The exact designated converter file added in this slice is `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/worldgen/CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java`.
- The exact converter method signatures chosen in this slice are:
  - `public static Optional<Holder<Biome>> holderForCandidateKey(String candidateKey, HolderLookup.Provider lookupProvider)`
  - `public static Optional<Holder<Biome>> holderForCandidateKeyOrFallback(String candidateKey, HolderLookup.Provider lookupProvider)`
- The exact converter component enum values are:
  - `DESIGNATED_CONVERTER_FILE`
  - `STRING_CANDIDATE_KEY_INPUT`
  - `RESOURCE_LOCATION_PARSE_STEP`
  - `BIOME_RESOURCE_KEY_STEP`
  - `BIOME_REGISTRY_LOOKUP_STEP`
  - `HOLDER_RESOLUTION_STEP`
  - `OPTIONAL_HOLDER_RESULT`
  - `FALLBACK_CANDIDATE_ATTEMPT`
  - `INVALID_OR_MISSING_KEY_HANDLING`
  - `NO_GET_NOISE_BIOME_WIRING`
  - `NO_COLLECT_POSSIBLE_BIOMES_WIRING`
  - `REAL_RUNTIME_BIOME_SOURCE_STILL_DEFERRED`
- The exact converter entry fields are:
  - `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent component`
  - `String sourceContractName`
  - `String converterBehavior`
  - `String runtimeBoundary`
  - `boolean converterReady`
  - `boolean runtimeBiomeSourceReady`
  - `boolean activationAllowedInThisSlice`
  - `String blocker`

## Converter Surface

- The exact helper methods exposed by `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter` include:
  - `holderForCandidateKey(...)`
  - `holderForCandidateKeyOrFallback(...)`
  - `candidateKeyInputShape()`
  - `fallbackCandidateModernBiomeKey()`
  - `fallbackLegacyBiomeName()`
  - `designatedConverterSimpleName()`
  - `designatedConverterFileName()`
  - `holderForCandidateKeyReady()`
  - `holderForCandidateKeyOrFallbackReady()`
  - `converterReady()`
  - `converterRuntimeBiomeSourceReady()`
  - `getNoiseBiomeWiringReady()`
  - `collectPossibleBiomesWiringReady()`
  - `runtimeBiomeSourceReady()`
  - `activationAllowedInThisSlice()`
  - `canActivateCaveniaNow()`
  - `entries()`
  - `components()`
  - `entryFor(...)`
  - `entryCount()`
- Resource-location parsing is allowed only in the designated converter.
- Resource-key construction is allowed only in the designated converter.
- Biome registry lookup is allowed only in the designated converter.
- Holder resolution is allowed only in the designated converter.
- Fallback holder attempt is allowed only in the designated converter.
- The converter requires caller-provided lookup/provider.
- The converter does not access server/world globals.
- The converter returns optional holder result.
- The converter accepts a string modern biome candidate key.
- The converter rejects null or blank keys by returning `Optional.empty()`.
- The converter returns `Optional.empty()` for invalid or unresolved candidate keys.
- The fallback holder attempt remains `PLAINS -> minecraft:plains`.
- Candidate keys remain string-only outside the designated converter.

## Boundaries

- The converter is not wired into `getNoiseBiome(...)`.
- The converter is not wired into `collectPossibleBiomes()`.
- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
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

- `CaveniaRuntimeBiomeSource` remains the guarded subclass boundary and exposes converter-readiness passthrough only.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision` selected this guarded implementation slice.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness` pinned the string-key, resource-location, resource-key, registry-lookup, holder-resolution and fallback policies before implementation began.
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision` kept the earlier branch decision read-only and non-runtime.
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge` remains the source of current modern biome candidate keys.
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness` still pins future holder-facing expectations around runtime method outputs.
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness` still pins `PLAINS -> minecraft:plains` as readiness data.
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

- This is still not `getNoiseBiome(...)` implementation because the converter is not called by `CaveniaRuntimeBiomeSource`.
- This is still not `collectPossibleBiomes()` implementation because the converter is not called by `CaveniaRuntimeBiomeSource`.
- This is still not usable runtime biome resolution because resolved holders stop at the guarded helper boundary.
- This is still not a usable runtime biome source because all runtime-facing abstract methods remain unsupported stubs.
- This is still not a `ChunkGenerator` because no generator file or generator registration was added.
- No usable codec implementation was added.
- No codec or biome-source registry entry was added.
- No generator registration was added.
- No dimension JSON/type JSON was added.
- No active generator was added.
- No active Cavenia level was added.
- No access/spawning/worldgen resources were added.

## Follow-up

- The implementation go/no-go note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-implementation-go-no-go-mvp.md`.
- The guarded converter implementation note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md`.
- The converter consolidation next-decision note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The collect-possible-biomes holder-set go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set readiness follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
- The next slice can be `Cavenia Runtime BiomeSource Candidate-Key-to-Holder Converter Consolidation / Next Decision MVP`.
- That next slice should decide whether to proceed toward `collectPossibleBiomes()` holder-set readiness or stop for review.
- That next slice must still keep `getNoiseBiome(...)` and `collectPossibleBiomes()` unusable unless explicitly requested otherwise.
