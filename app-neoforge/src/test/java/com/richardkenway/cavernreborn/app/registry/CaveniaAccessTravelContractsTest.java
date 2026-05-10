package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaAccessTravelContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaAccessTravelRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaAccessTravelRequirementContract;

class CaveniaAccessTravelContractsTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaAccessTravelRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaAccessTravelRequirementContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaAccessTravelContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void accessTravelContractsExposeOnlyBlockedRequirementFacts() {
        List<CaveniaAccessTravelRequirement> expectedRequirements = List.of(
            CaveniaAccessTravelRequirement.ENTRY_TRIGGER_DECISION,
            CaveniaAccessTravelRequirement.SAFE_ARRIVAL_POSITION,
            CaveniaAccessTravelRequirement.RETURN_PATH,
            CaveniaAccessTravelRequirement.SERVER_LEVEL_AVAILABILITY,
            CaveniaAccessTravelRequirement.PLAYER_STATE_GUARDS,
            CaveniaAccessTravelRequirement.TRAVEL_HOST_IMPLEMENTATION
        );
        List<CaveniaAccessTravelRequirementContract> contracts = CaveniaAccessTravelContracts.contracts();

        assertEquals(expectedRequirements, CaveniaAccessTravelContracts.requirements());
        assertEquals(6, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canTeleport()));
        assertTrue(contracts.stream().allMatch(CaveniaAccessTravelRequirementContract::requiresActiveLevel));
        assertTrue(CaveniaAccessTravelContracts.allRequirementsBlocked());
        assertFalse(CaveniaAccessTravelContracts.anyRequirementReady());
        assertFalse(CaveniaAccessTravelContracts.anyRequirementRuntimeImplemented());
        assertFalse(CaveniaAccessTravelContracts.canTeleport());
        assertTrue(CaveniaAccessTravelContracts.requiresActiveLevel());
        assertFalse(CaveniaAccessTravelContracts.accessTravelHostReady());
        assertFalse(CaveniaAccessTravelContracts.dimensionResourceHostReady());
        assertFalse(CaveniaAccessTravelContracts.dimensionCanCreateLevel());

        assertImmutableList(CaveniaAccessTravelContracts.contracts(), new Object());
        assertImmutableList(CaveniaAccessTravelContracts.requirements(), CaveniaAccessTravelRequirement.ENTRY_TRIGGER_DECISION);
        contracts.forEach(contract -> assertNotNull(contract.requirement()));
    }

    @Test
    void accessTravelContractSourcesStayOutOfTeleportPaths() throws IOException {
        String requirementSource = Files.readString(REQUIREMENT_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);
        String contractsSource = Files.readString(CONTRACTS_SOURCE);

        assertSourceStaysInert(requirementSource);
        assertSourceStaysInert(contractSource);
        assertSourceStaysInert(contractsSource);

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaPortal.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaPortalBlock.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MirageBook.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaAccessHandler.java");

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
                            || name.equals("CaveniaPortal.java")
                            || name.equals("CaveniaPortalBlock.java")
                            || name.equals("MirageBook.java")
                            || name.equals("CaveniaAccessHandler.java")
                    ),
                "Expected the access/travel split-contract MVP to avoid adding active teleport, portal or runtime worldgen classes"
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
