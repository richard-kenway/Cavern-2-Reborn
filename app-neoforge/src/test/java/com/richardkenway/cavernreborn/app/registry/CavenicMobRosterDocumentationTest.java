package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavenicMobRosterDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path ROSTER_DOC = resolveProjectFile("docs", "cavenic-mob-roster-discovery.md");
    private static final Path MOD_REGISTRIES = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
    );
    private static final Path CLIENT_EVENTS = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
    );
    private static final Path ENTITY_DIR = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity"
    );
    private static final Path RENDERER_DIR = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer"
    );
    private static final Path LANG = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "assets", "cavernreborn", "lang", "en_us.json"
    );

    private static final List<String> EXPECTED_ENTITY_IDS = List.of(
        "cavenic_zombie",
        "cavenic_skeleton",
        "cavenic_creeper",
        "cavenic_spider",
        "cavenic_witch",
        "cavenic_bear"
    );

    private static final List<String> EXPECTED_ENTITY_FILES = List.of(
        "CavenicBear.java",
        "CavenicCreeper.java",
        "CavenicSkeleton.java",
        "CavenicSpider.java",
        "CavenicWitch.java",
        "CavenicZombie.java"
    );

    private static final List<String> EXPECTED_RENDERER_FILES = List.of(
        "CavenicBearRenderer.java",
        "CavenicCreeperRenderer.java",
        "CavenicSkeletonRenderer.java",
        "CavenicSpiderRenderer.java",
        "CavenicWitchRenderer.java",
        "CavenicZombieRenderer.java"
    );

    @Test
    void readmeAndRuntimeSmokeMentionDirectCavenicMobRosterBoundary() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(readme.contains("direct Cavenic mob roster boundary follow-up"));
        assertTrue(readme.contains("no seventh direct `EntityCavenic*` mob after `cavenic_bear`"));
        assertTrue(readme.contains("docs/cavenic-mob-roster-discovery.md"));
        assertTrue(runtimeSmoke.contains("the direct Cavenic mob roster boundary"));
        assertTrue(runtimeSmoke.contains("direct legacy `EntityCavenic*` roster boundary documentation confirming that the current six runtime-covered direct Cavenic mob foundations already exhaust the legacy direct roster"));
        assertTrue(runtimeSmoke.contains("a seventh direct `EntityCavenic*` mob baseline, because the inspected legacy direct roster ends at zombie, skeleton, creeper, spider, witch and bear"));
    }

    @Test
    void cavenicMobRosterDocStatesDirectLegacyRosterAndExcludedNonCandidates() throws IOException {
        String doc = Files.readString(ROSTER_DOC);

        assertTrue(doc.contains("# Direct Cavenic Mob Roster Boundary"));
        assertTrue(doc.contains("no seventh direct Cavenic baseline entity was added"));
        assertTrue(doc.contains("`EntityCavenicZombie`"));
        assertTrue(doc.contains("`EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`EntityCavenicCreeper`"));
        assertTrue(doc.contains("`EntityCavenicSpider`"));
        assertTrue(doc.contains("`EntityCavenicWitch`"));
        assertTrue(doc.contains("`EntityCavenicBear`"));
        assertTrue(doc.contains("`RenderCavenicZombie`"));
        assertTrue(doc.contains("`RenderCavenicSkeleton`"));
        assertTrue(doc.contains("`RenderCavenicCreeper`"));
        assertTrue(doc.contains("`RenderCavenicSpider`"));
        assertTrue(doc.contains("`RenderCavenicWitch`"));
        assertTrue(doc.contains("`RenderCavenicBear`"));
        assertTrue(doc.contains("`cavenic_zombie`"));
        assertTrue(doc.contains("`cavenic_skeleton`"));
        assertTrue(doc.contains("`cavenic_creeper`"));
        assertTrue(doc.contains("`cavenic_spider`"));
        assertTrue(doc.contains("`cavenic_witch`"));
        assertTrue(doc.contains("`cavenic_bear`"));
        assertTrue(doc.contains("`0xAAAAAA / 0x00A0A0`"));
        assertTrue(doc.contains("`0xAAAAAA / 0xDDDDDD`"));
        assertTrue(doc.contains("`0xAAAAAA / 0x2E8B57`"));
        assertTrue(doc.contains("`0xAAAAAA / 0x811F1F`"));
        assertTrue(doc.contains("`0xAAAAAA / 0x4A5348`"));
        assertTrue(doc.contains("`0xAAAAAA / 0xFFFFFF`"));
        assertTrue(doc.contains("`EntitySummonCavenicZombie` and `EntitySummonCavenicSkeleton`"));
        assertTrue(doc.contains("`EntityCrazyZombie`, `EntityCrazySkeleton`, `EntityCrazyCreeper` and `EntityCrazySpider`"));
        assertTrue(doc.contains("`EntityCaveman`, `EntityCrystalTurret`, `EntitySkySeeker`, `EntityDurangHog`, `EntityAquaSquid`, `EntityMagicTorcher`, `EntityBeam` and `EntityCavenicArrow`"));
        assertTrue(doc.contains("No remaining unported direct `EntityCavenic*` mobs exist in legacy Cavern 2."));
        assertTrue(doc.contains("Because no seventh direct class exists"));
        assertTrue(doc.contains("no new entity class, no new renderer, no new spawn egg"));
        assertTrue(doc.contains("Cavenia-specific behavior"));
        assertTrue(doc.contains("magic-book or spell systems"));
    }

    @Test
    void rebornDirectCavenicMobSourceRosterMatchesTheSixLegacyDirectEntries() throws IOException {
        String registriesSource = Files.readString(MOD_REGISTRIES);
        String clientSource = Files.readString(CLIENT_EVENTS);
        JsonObject lang = JsonParser.parseString(Files.readString(LANG)).getAsJsonObject();

        assertEquals(
            EXPECTED_ENTITY_IDS,
            extractMatches(registriesSource, Pattern.compile("ENTITY_TYPES\\.register\\(\\s*\"(cavenic_[a-z_]+)\"", Pattern.MULTILINE))
        );
        assertEquals(
            EXPECTED_ENTITY_IDS.stream().map(id -> id + "_spawn_egg").toList(),
            extractMatches(registriesSource, Pattern.compile("ITEMS\\.register\\(\\s*\"(cavenic_[a-z_]+_spawn_egg)\"", Pattern.MULTILINE))
        );
        assertEquals(
            List.of(
                "CAVENIC_ZOMBIE",
                "CAVENIC_SKELETON",
                "CAVENIC_CREEPER",
                "CAVENIC_SPIDER",
                "CAVENIC_WITCH",
                "CAVENIC_BEAR"
            ),
            extractMatches(clientSource, Pattern.compile("registerEntityRenderer\\(ModRegistries\\.(CAVENIC_[A-Z_]+)\\.get\\("))
        );
        assertEquals(EXPECTED_ENTITY_FILES, listMatchingFileNames(ENTITY_DIR, "^Cavenic[A-Za-z]+\\.java$", "LootEvents.java"));
        assertEquals(EXPECTED_RENDERER_FILES, listMatchingFileNames(RENDERER_DIR, "^Cavenic[A-Za-z]+Renderer\\.java$"));

        Map<String, String> entityLangEntries = new LinkedHashMap<>();
        for (String key : lang.keySet().stream().sorted().toList()) {
            if (key.startsWith("entity.cavernreborn.cavenic_")) {
                entityLangEntries.put(key, lang.get(key).getAsString());
            }
        }

        assertEquals(
            Map.of(
                "entity.cavernreborn.cavenic_bear", "Cavenic Bear",
                "entity.cavernreborn.cavenic_creeper", "Cavenic Creeper",
                "entity.cavernreborn.cavenic_skeleton", "Cavenic Skeleton",
                "entity.cavernreborn.cavenic_spider", "Cavenic Spider",
                "entity.cavernreborn.cavenic_witch", "Cavenic Witch",
                "entity.cavernreborn.cavenic_zombie", "Cavenic Zombie"
            ),
            entityLangEntries
        );
    }

    private static List<String> extractMatches(String input, Pattern pattern) {
        return pattern.matcher(input).results().map(match -> match.group(1)).toList();
    }

    private static List<String> listMatchingFileNames(Path directory, String regex, String... excludedSuffixes) throws IOException {
        Pattern pattern = Pattern.compile(regex);

        try (var stream = Files.list(directory)) {
            return stream
                .map(path -> path.getFileName().toString())
                .filter(name -> pattern.matcher(name).matches())
                .filter(name -> {
                    for (String excludedSuffix : excludedSuffixes) {
                        if (name.endsWith(excludedSuffix)) {
                            return false;
                        }
                    }
                    return true;
                })
                .sorted()
                .toList();
        }
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || i == 4) {
                return candidate;
            }
            current = current.getParent();
        }

        return Path.of(first, more);
    }
}
