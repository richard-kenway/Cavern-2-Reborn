# Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Implementation Go/No-Go MVP

This is candidate-key-to-holder conversion implementation go/no-go decision, not implementation.

`CaveniaRuntimeBiomeSource` still does not provide usable runtime biome behavior. This slice decides only whether the next slice may add a guarded conversion helper for candidate-key-to-holder conversion while keeping runtime methods unsupported and unwired.

## Added Types

- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision`

## Next-Decision Enum Values

- `PROCEED_WITH_GUARDED_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION_NEXT`
- `DEFER_GET_NOISE_BIOME_WIRING`
- `DEFER_COLLECT_POSSIBLE_BIOMES_WIRING`
- `DEFER_USABLE_RUNTIME_BIOME_SOURCE`
- `DEFER_CODEC_REGISTRATION`
- `DEFER_DIMENSION_ACTIVATION`
- `DEFER_WORLDGEN_ACCESS_SPAWNING`

## Guardrail Enum Values

- `ALLOW_GUARDED_CONVERSION_HELPER_NEXT`
- `ALLOW_STRING_CANDIDATE_KEY_INPUT_NEXT`
- `ALLOW_RESOURCE_LOCATION_PARSING_IN_DESIGNATED_HELPER_NEXT`
- `ALLOW_RESOURCE_KEY_CONSTRUCTION_IN_DESIGNATED_HELPER_NEXT`
- `ALLOW_BIOME_REGISTRY_LOOKUP_IN_DESIGNATED_HELPER_NEXT`
- `ALLOW_HOLDER_RESOLUTION_IN_DESIGNATED_HELPER_NEXT`
- `ALLOW_MISSING_OR_FALLBACK_HANDLING_IN_DESIGNATED_HELPER_NEXT`
- `NO_GET_NOISE_BIOME_WIRING`
- `NO_GET_NOISE_BIOME_IMPLEMENTATION`
- `NO_COLLECT_POSSIBLE_BIOMES_WIRING`
- `NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION`
- `NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION`
- `NO_GENERATOR_REGISTRATION`
- `NO_DIMENSION_ACTIVATION`
- `NO_WORLDGEN_ACCESS_SPAWNING`
- `KEEP_RUNTIME_METHODS_UNSUPPORTED`

## Guardrail Contract Fields

- `guardrail`
- `sourceContractName`
- `enforcedInThisSlice`
- `allowsNextSliceConversionAction`
- `allowsNextSliceRuntimeWiringAction`
- `allowsNextSliceActivationAction`
- `blocksActivation`
- `blocker`

## Helper Surface

`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision` exposes deterministic helpers for the exact implementation-decision surface described in code, including selection helpers, guardrail accessors, next-slice allowances, blocked runtime surfaces, candidate-key sample passthroughs, readiness passthroughs, designated subclass passthroughs and activation-blocked helpers.

## Decision Facts

- selected decision is `PROCEED_WITH_GUARDED_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION_NEXT`.
- next slice may add guarded conversion helper.
- next slice designated converter simple name is `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter`.
- next slice designated converter file name is `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java`.
- next slice may use string candidate key input.
- next slice may parse resource locations only in the designated helper.
- next slice may construct resource keys only in the designated helper.
- next slice may use biome registry lookup only in the designated helper.
- next slice may resolve holders only in the designated helper.
- next slice may handle missing/fallback only in the designated helper.
- next slice may not wire into `getNoiseBiome(...)`.
- next slice may not make `getNoiseBiome(...)` usable.
- next slice may not wire into `collectPossibleBiomes()`.
- next slice may not make `collectPossibleBiomes()` usable.
- this slice does not implement candidate-key-to-holder conversion.
- this slice does not create the designated converter file.
- exact helper methods are the ones exposed by `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision`.
- candidate-key-to-holder conversion readiness remains ready.
- candidate-key-to-holder conversion runtime behavior is not ready.
- registry lookup access remains absent.
- holder resolution remains absent.
- resource-location conversion remains absent.
- resource-key conversion remains absent.
- fallback runtime behavior remains absent.
- `getNoiseBiome(...)` remains unsupported.
- `collectPossibleBiomes()` remains unsupported.
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
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness`
- `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`
- `CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`
- `CaveniaRuntimeBiomeSourceHolderConversionReadiness`
- `CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`
- `CaveniaRegistryLookupReadiness`
- `CaveniaLegacyToModernBiomeKeyMappings`
- `CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`
- `CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`
- `CaveniaRuntimeBiomeSourceCodecMethodShapeStub`
- `CaveniaRuntimeBiomeSourceApiShapeInventory`
- `CaveniaRuntimeBiomeSourceSkeleton`
- `CaveniaBiomeSourceStrategyPlan`
- `CaveniaActivationReadinessMatrix`
- `CaveniaGeneratorRegistrationBoundary`

## Why This Is Still Not Runtime Conversion

- This is still not candidate-key-to-holder conversion implementation because no conversion helper exists yet.
- This is still not registry lookup access because access remains absent outside a future designated helper.
- This is still not holder resolution because no holder is resolved in this slice.
- This is still not resource-location or resource-key conversion because no conversion is implemented in this slice.
- This is still not runtime selection because runtime methods remain unsupported and unwired.
- This is still not usable noise-biome runtime behavior because `getNoiseBiome(...)` stays unsupported.
- This is still not usable possible-biomes runtime behavior because `collectPossibleBiomes()` stays unsupported.
- This is still not usable runtime biome resolution because no holder-backed runtime value is returned.
- This is still not a usable runtime biome source because the guarded subclass remains unregistered, unsupported and inactive.
- This is still not a `ChunkGenerator` because no generator work or activation work was added.
- Why no usable codec implementation was added: codec behavior remains stubbed.
- Why no codec or registry entry was added: codec registration and biome-source type registration remain absent.
- Why no generator registration was added: generator registration remains absent.
- Why no dimension JSON or type JSON was added: activation remains out of scope.
- Why no active generator was added: generator activation remains blocked.
- Why no active Cavenia level was added: dimension binding and activation remain blocked.
- Why no access, spawning or worldgen resources were added: those activation surfaces remain blocked.

## Future Recommendation

- The next slice can be `Cavenia Runtime BiomeSource Candidate-Key-to-Holder Conversion Guarded Implementation MVP`.
- That next slice may create only the designated converter helper.
- That next slice must still keep `getNoiseBiome(...)` and `collectPossibleBiomes()` unusable unless explicitly requested otherwise.
- The guarded converter implementation note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md`.
- The converter consolidation next-decision note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`.
- The collect-possible-biomes holder-set go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md`.
- The collect-possible-biomes holder-set readiness follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md`.
- The collect-possible-biomes holder-set implementation go/no-go follow-up is documented in `docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-implementation-go-no-go-mvp.md`.
