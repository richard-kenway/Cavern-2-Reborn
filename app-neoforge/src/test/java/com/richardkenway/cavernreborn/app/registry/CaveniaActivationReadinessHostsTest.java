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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationReadinessHost;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationReadinessHostContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationReadinessHosts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorActivationRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;

class CaveniaActivationReadinessHostsTest {
    private static final Path HOST_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaActivationReadinessHost.java"
    );
    private static final Path HOST_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaActivationReadinessHostContract.java"
    );
    private static final Path HOSTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaActivationReadinessHosts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void activationReadinessHostsExposeOnlyBlockedHostFacts() {
        List<CaveniaActivationReadinessHost> expectedHosts = List.of(
            CaveniaActivationReadinessHost.GENERATOR_HOST,
            CaveniaActivationReadinessHost.BIOME_SOURCE_STRATEGY_HOST,
            CaveniaActivationReadinessHost.DIMENSION_RESOURCE_HOST,
            CaveniaActivationReadinessHost.ACCESS_TRAVEL_HOST,
            CaveniaActivationReadinessHost.SPAWN_HOST,
            CaveniaActivationReadinessHost.WORLDGEN_RESOURCE_HOST
        );
        List<CaveniaActivationReadinessHostContract> contracts = CaveniaActivationReadinessHosts.contracts();

        assertEquals(expectedHosts, CaveniaActivationReadinessHosts.hosts());
        assertEquals(6, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.responsibility() != null && !contract.responsibility().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertEquals(
            CaveniaGeneratorActivationRequirement.CHUNK_GENERATOR_IMPLEMENTATION,
            CaveniaActivationReadinessHosts.contractFor(CaveniaActivationReadinessHost.GENERATOR_HOST).orElseThrow().activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
            CaveniaActivationReadinessHosts.contractFor(CaveniaActivationReadinessHost.BIOME_SOURCE_STRATEGY_HOST).orElseThrow().activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
            CaveniaActivationReadinessHosts.contractFor(CaveniaActivationReadinessHost.DIMENSION_RESOURCE_HOST).orElseThrow().activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.SAFE_ACCESS_OR_TELEPORT,
            CaveniaActivationReadinessHosts.contractFor(CaveniaActivationReadinessHost.ACCESS_TRAVEL_HOST).orElseThrow().activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.CAVENIA_ONLY_SPAWN_HOST,
            CaveniaActivationReadinessHosts.contractFor(CaveniaActivationReadinessHost.SPAWN_HOST).orElseThrow().activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            CaveniaActivationReadinessHosts.contractFor(CaveniaActivationReadinessHost.WORLDGEN_RESOURCE_HOST).orElseThrow().activationRequirement()
        );
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canActivateCavenia()));
        assertTrue(CaveniaActivationReadinessHosts.allHostsBlocked());
        assertFalse(CaveniaActivationReadinessHosts.anyHostReady());
        assertFalse(CaveniaActivationReadinessHosts.anyHostRuntimeImplemented());
        assertFalse(CaveniaActivationReadinessHosts.canActivateCavenia());
        assertFalse(CaveniaActivationReadinessHosts.generatorHostReady());
        assertFalse(CaveniaActivationReadinessHosts.biomeSourceStrategyHostReady());
        assertFalse(CaveniaActivationReadinessHosts.dimensionResourceHostReady());
        assertFalse(CaveniaActivationReadinessHosts.accessTravelHostReady());
        assertFalse(CaveniaActivationReadinessHosts.spawnHostReady());
        assertFalse(CaveniaActivationReadinessHosts.worldgenResourceHostReady());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationRequirements(), CaveniaActivationReadinessHosts.activationRequirements());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationBlockedReason(), CaveniaActivationReadinessHosts.activationBlockedReason());
        assertTrue(CaveniaActivationReadinessHosts.runtimeOperationsBlocked());
        assertFalse(CaveniaActivationReadinessHosts.generatorSkeletonCanCreateChunks());
        assertFalse(CaveniaActivationReadinessHosts.biomeSelectionIsRuntimeBiomeSource());

        assertImmutableList(CaveniaActivationReadinessHosts.contracts(), new Object());
        assertImmutableList(CaveniaActivationReadinessHosts.hosts(), CaveniaActivationReadinessHost.GENERATOR_HOST);
        contracts.forEach(contract -> {
            assertNotNull(contract.host());
            assertNotNull(contract.activationRequirement());
        });
    }

    @Test
    void activationReadinessHostSourcesStayOutOfRuntimeAndTeleportPaths() throws IOException {
        String hostEnumSource = Files.readString(HOST_ENUM_SOURCE);
        String hostContractSource = Files.readString(HOST_CONTRACT_SOURCE);
        String hostsSource = Files.readString(HOSTS_SOURCE);

        assertFalse(hostEnumSource.contains("extends ChunkGenerator"));
        assertFalse(hostEnumSource.contains("extends BiomeSource"));
        assertFalse(hostEnumSource.contains("MapCodec"));
        assertFalse(hostEnumSource.contains("Registry.register"));
        assertFalse(hostEnumSource.contains("CaveniaTeleporter"));
        assertFalse(hostEnumSource.contains("ITeleporter"));

        assertFalse(hostContractSource.contains("extends ChunkGenerator"));
        assertFalse(hostContractSource.contains("extends BiomeSource"));
        assertFalse(hostContractSource.contains("MapCodec"));
        assertFalse(hostContractSource.contains("Registry.register"));
        assertFalse(hostContractSource.contains("CaveniaTeleporter"));
        assertFalse(hostContractSource.contains("ITeleporter"));

        assertFalse(hostsSource.contains("extends ChunkGenerator"));
        assertFalse(hostsSource.contains("extends BiomeSource"));
        assertFalse(hostsSource.contains("MapCodec"));
        assertFalse(hostsSource.contains("Registry.register"));
        assertFalse(hostsSource.contains("CaveniaTeleporter"));
        assertFalse(hostsSource.contains("ITeleporter"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");

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
                    ),
                "Expected the activation-readiness host MVP to avoid adding active Cavenia generator, biome-source, teleport or spawn-host runtime classes"
            );
        }
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
