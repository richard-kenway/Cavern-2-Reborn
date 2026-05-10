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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaSpawnHostContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaSpawnHostRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaSpawnHostRequirementContract;

class CaveniaSpawnHostContractsTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaSpawnHostRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaSpawnHostRequirementContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaSpawnHostContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void spawnHostContractsExposeOnlyBlockedRequirementFacts() {
        List<CaveniaSpawnHostRequirement> expectedRequirements = List.of(
            CaveniaSpawnHostRequirement.CAVENIA_LEVEL_AVAILABILITY,
            CaveniaSpawnHostRequirement.POLICY_CONSUMPTION,
            CaveniaSpawnHostRequirement.NORMAL_ROSTER_HANDLING,
            CaveniaSpawnHostRequirement.CRAZY_ROSTER_HANDLING,
            CaveniaSpawnHostRequirement.NEARBY_ICAVENICMOB_SCAN,
            CaveniaSpawnHostRequirement.CAVEMAN_DEFERRAL,
            CaveniaSpawnHostRequirement.HOST_LOOP_OR_TICK_SOURCE,
            CaveniaSpawnHostRequirement.NO_FAKE_CAVERN_CRAZY_SPAWNING
        );
        List<CaveniaSpawnHostRequirementContract> contracts = CaveniaSpawnHostContracts.contracts();

        assertEquals(expectedRequirements, CaveniaSpawnHostContracts.requirements());
        assertEquals(8, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canSpawn()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.usesNormalCavernCrazySpawning()));
        assertTrue(CaveniaSpawnHostContracts.allRequirementsBlocked());
        assertFalse(CaveniaSpawnHostContracts.anyRequirementReady());
        assertFalse(CaveniaSpawnHostContracts.anyRequirementRuntimeImplemented());
        assertFalse(CaveniaSpawnHostContracts.canSpawn());
        assertFalse(CaveniaSpawnHostContracts.usesNormalCavernCrazySpawning());
        assertFalse(CaveniaSpawnHostContracts.spawnHostReady());
        assertFalse(CaveniaSpawnHostContracts.dimensionResourceHostReady());
        assertFalse(CaveniaSpawnHostContracts.dimensionCanCreateLevel());
        assertTrue(CaveniaSpawnHostContracts.cavemanRemainsDeferred());

        assertImmutableList(CaveniaSpawnHostContracts.contracts(), new Object());
        assertImmutableList(CaveniaSpawnHostContracts.requirements(), CaveniaSpawnHostRequirement.CAVENIA_LEVEL_AVAILABILITY);
        contracts.forEach(contract -> assertNotNull(contract.requirement()));
    }

    @Test
    void spawnHostContractSourcesStayOutOfSpawnRuntimePaths() throws IOException {
        String requirementSource = Files.readString(REQUIREMENT_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);
        String contractsSource = Files.readString(CONTRACTS_SOURCE);

        assertSourceStaysInert(requirementSource);
        assertSourceStaysInert(contractSource);
        assertSourceStaysInert(contractsSource);

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnEvents.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");

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
                            || name.equals("CaveniaSpawnEvents.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the spawn-host split-contract MVP to avoid adding active Cavenia spawn-host, teleport or runtime worldgen classes"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("ITeleporter"));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
        assertFalse(source.contains("addFreshEntity("));
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
