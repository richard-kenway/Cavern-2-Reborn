package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicSpiderDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_SPIDER_BASELINE_MVP = resolveProjectFile("docs", "cavenic-spider-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicSpiderBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Spider Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-baseline-mvp.md"));
        assertTrue(readme.contains("cavenic_spider"));
        assertTrue(readme.contains("fourth direct Cavenic mob foundation"));
        assertTrue(readme.contains("vanilla spider loot baseline"));
    }

    @Test
    void cavenicSpiderBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_SPIDER_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_spider`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_spider_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicSpider`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Spider`"));
        assertTrue(doc.contains("max health: `20.0`"));
        assertTrue(doc.contains("movement speed: `0.6`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("follow range intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("attack damage intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("XP reward is pinned to `12`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x811F1F`"));
        assertTrue(doc.contains("reuses the vanilla spider renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla spider loot table as its base drop source"));
        assertTrue(doc.contains("natural spawning intentionally stays out of scope"));
        assertTrue(doc.contains("custom loot remains out of scope"));
        assertTrue(doc.contains("`1/8` `cavenic_orb` drop"));
        assertTrue(doc.contains("fall/fire damage behavior"));
        assertTrue(doc.contains("blindness-on-hit behavior"));
        assertTrue(doc.contains("poison behavior"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void runtimeSmokeMentionsCavenicSpiderBaselineCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic spider runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic spider attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic spider spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Spider renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("Cavenic Spider natural spawning in this baseline slice"));
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
