package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicZombieDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_ZOMBIE_MVP = resolveProjectFile("docs", "cavenic-zombie-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicZombieBaselineAndItsBoundedIdentity() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Zombie Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-baseline-mvp.md"));
        assertTrue(readme.contains("cavenic_zombie"));
        assertTrue(readme.contains("spawn-egg-driven hostile foundation mob"));
        assertTrue(readme.contains("no natural spawning yet"));
    }

    @Test
    void cavenicZombieDocStatesLegacyReferencesAttributesSpawnEggRendererAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_ZOMBIE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_zombie`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_zombie_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicZombie`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Zombie`"));
        assertTrue(doc.contains("max health: `50.0`"));
        assertTrue(doc.contains("follow range: `50.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("attack damage: `5.0`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x00A0A0`"));
        assertTrue(doc.contains("reuses the vanilla zombie loot table"));
        assertTrue(doc.contains("Natural spawning is intentionally out of scope"));
        assertTrue(doc.contains("Additional Cavenic mobs"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void runtimeSmokeMentionsCavenicZombieCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic zombie runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic zombie attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic zombie spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Zombie renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("natural Cavenic Zombie spawning and cave-population balance"));
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
}
