package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFirstImplementationDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNextStepDecision;

class CaveniaRuntimeBiomeSourceFirstImplementationDecisionTest {
    private static final Path DECISION_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceNextStepDecision.java"
    );
    private static final Path GUARDRAIL_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceFirstImplementationDecision.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void decisionPinsTheNextRuntimeBiomeSourceBranchAndGuardrails() {
        List<CaveniaRuntimeBiomeSourceGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceGuardrail.NO_DIMENSION_JSON,
            CaveniaRuntimeBiomeSourceGuardrail.NO_DIMENSION_TYPE_JSON,
            CaveniaRuntimeBiomeSourceGuardrail.NO_ACTIVE_CAVENIA_LEVEL,
            CaveniaRuntimeBiomeSourceGuardrail.NO_CODEC_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceGuardrail.NO_CODEC_REGISTRATION,
            CaveniaRuntimeBiomeSourceGuardrail.NO_BIOME_SOURCE_TYPE_REGISTRATION,
            CaveniaRuntimeBiomeSourceGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceGuardrail.NO_BIOME_HOLDER_OR_RESOURCE_KEY_CONVERSION,
            CaveniaRuntimeBiomeSourceGuardrail.NO_WORLDGEN_RESOURCES,
            CaveniaRuntimeBiomeSourceGuardrail.NO_ACCESS_OR_TELEPORT,
            CaveniaRuntimeBiomeSourceGuardrail.NO_SPAWNING,
            CaveniaRuntimeBiomeSourceGuardrail.NO_CAVEMAN_ENTITY_REGISTRATION,
            CaveniaRuntimeBiomeSourceGuardrail.UNREGISTERED_SKELETON_ONLY,
            CaveniaRuntimeBiomeSourceGuardrail.KEEP_BIOME_SOURCE_STRATEGY_MATRIX_BLOCKED
        );
        List<CaveniaRuntimeBiomeSourceGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrails();

        assertEquals(
            CaveniaRuntimeBiomeSourceNextStepDecision.PROCEED_WITH_UNREGISTERED_RUNTIME_BIOME_SOURCE_SKELETON_NEXT,
            CaveniaRuntimeBiomeSourceFirstImplementationDecision.selectedDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.decisionIsToProceedWithUnregisteredSkeletonNext());
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.decisionIsImplementationOnlyForNextSlice());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.runtimeBiomeSourceImplementedInThisSlice());
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.unregisteredSkeletonImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.unregisteredSkeletonRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.runtimeBiomeSourceSubclassDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.runtimeBiomeSourceSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.canActivateCaveniaNow());
        assertEquals(expectedGuardrails, CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrailValues());
        assertEquals(14, CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceGuardrailContract::enforcedInThisSlice));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.runtimeActivationAllowed()));
        assertTrue(
            CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrailFor(
                CaveniaRuntimeBiomeSourceGuardrail.UNREGISTERED_SKELETON_ONLY
            ).orElseThrow().mayBeRelaxedInNextSlice()
        );
        guardrails.stream()
            .filter(contract -> contract.guardrail() != CaveniaRuntimeBiomeSourceGuardrail.UNREGISTERED_SKELETON_ONLY)
            .forEach(contract -> assertFalse(contract.mayBeRelaxedInNextSlice()));
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.allGuardrailsEnforcedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.anyRuntimeActivationAllowedByGuardrails());
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddUnregisteredSkeleton());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.nextSliceMayRegisterCavemanEntity());
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.biomeSourceStrategyMatrixReady());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.biomeSourceStrategyMatrixRuntimeReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceFirstImplementationDecision.biomeSourceStrategyTotalReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceFirstImplementationDecision.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceFirstImplementationDecision.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.runtimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceFirstImplementationDecision.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceFirstImplementationDecision.cavemanRemainsDeferred());

        assertImmutableList(CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrails(), new Object());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceFirstImplementationDecision.guardrailValues(),
            CaveniaRuntimeBiomeSourceGuardrail.NO_DIMENSION_JSON
        );
        guardrails.forEach(contract -> assertNotNull(contract.guardrail()));
    }

    @Test
    void decisionSourcesStayOutOfRuntimeBiomeSourceAndRegistrationPaths() throws IOException {
        String decisionEnumSource = Files.readString(DECISION_ENUM_SOURCE);
        String guardrailEnumSource = Files.readString(GUARDRAIL_ENUM_SOURCE);
        String guardrailContractSource = Files.readString(GUARDRAIL_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);

        assertSourceStaysInert(decisionEnumSource);
        assertSourceStaysInert(guardrailEnumSource);
        assertSourceStaysInert(guardrailContractSource);
        assertSourceStaysInert(decisionSource);

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the runtime-biome-source decision slice to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected file to be absent: " + Path.of(first, more));
    }

    @SuppressWarnings("unchecked")
    private static void assertImmutableList(List<?> list, Object value) {
        List<Object> mutableView = (List<Object>) list;

        try {
            mutableView.add(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }

        throw new AssertionError("Expected list to be immutable");
    }

    private static Path resolveProjectPathOrSibling(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || Files.exists(candidate.getParent())) {
                return candidate;
            }
            current = current.getParent();
        }

        return Path.of(first, more).toAbsolutePath();
    }
}
