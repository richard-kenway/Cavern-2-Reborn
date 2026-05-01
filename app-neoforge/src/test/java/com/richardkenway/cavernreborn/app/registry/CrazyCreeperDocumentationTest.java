package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazyCreeperDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-creeper-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazyCreeperBaselineDocAndFuseBoundary() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Creeper Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-creeper-baseline-mvp.md"));
        assertTrue(readme.contains("third crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_creeper`"));
        assertTrue(readme.contains("vanilla creeper loot baseline"));
        assertTrue(readme.contains("legacy `1500.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`"));
        assertTrue(readme.contains("Crazy Creeper fuse/explosion, boss, particle, loot and damage follow-ups remain intentionally deferred"));
        assertTrue(readme.contains("Crazy Spider remains the next crazy-variant follow-up candidate"));
    }

    @Test
    void crazyCreeperBaselineDocStatesLegacyReferencesRuntimeShapeAndDeferredExplosionIdentity() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`assets/cavern/textures/entity/crazy_creeper.png`"));
        assertTrue(doc.contains("`assets/cavern/lang/en_us.lang`"));
        assertTrue(doc.contains("`cavernreborn:crazy_creeper`"));
        assertTrue(doc.contains("`cavernreborn:crazy_creeper_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Creeper`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicCreeper` baseline"));
        assertTrue(doc.contains("max health: `1500.0`"));
        assertTrue(doc.contains("generic.max_health"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("movement speed: `0.23`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("Follow range intentionally stays on the vanilla creeper baseline"));
        assertTrue(doc.contains("Attack damage intentionally stays on the vanilla creeper baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("base color: `0x909090`"));
        assertTrue(doc.contains("spot color: `0x2E8B57`"));
        assertTrue(doc.contains("reuses the vanilla creeper renderer/model path"));
        assertTrue(doc.contains("vanilla creeper loot table as its base drop source"));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("weight `1` and group size `1 / 1`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("does not add a fake `CAVERN` spawn placement or biome modifier"));
        assertTrue(doc.contains("`fuseTime = 150`"));
        assertTrue(doc.contains("`explosionRadius = 30`"));
        assertTrue(doc.contains("`BossInfo.Color.GREEN`"));
        assertTrue(doc.contains("`ParticleCrazyMob`"));
        assertTrue(doc.contains("`canBeRidden(Entity entity)`"));
        assertTrue(doc.contains("does not restore"));
        assertTrue(doc.contains("custom fuse/explosion behavior"));
        assertTrue(doc.contains("lightning/charged/swelling"));
        assertTrue(doc.contains("damage behavior boundary"));
        assertTrue(doc.contains("loot/orb boundary"));
        assertTrue(doc.contains("boss/particle boundary"));
        assertTrue(doc.contains("Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void runtimeSmokeDocMentionsCrazyCreeperRegistryAttributeSpawnEggAndDeferredExplosionBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy creeper runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy creeper attribute registration"));
        assertTrue(runtimeSmoke.contains("crazy creeper hostile runtime spawn"));
        assertTrue(runtimeSmoke.contains("crazy creeper spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("crazy creeper spawn egg entity creation"));
        assertTrue(runtimeSmoke.contains("crazy creeper vanilla creeper loot-table baseline"));
        assertTrue(runtimeSmoke.contains("crazy creeper explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("crazy creeper custom fuse/explosion branch remains follow-up"));
        assertTrue(runtimeSmoke.contains("crazy creeper custom lightning/charged/swelling behavior remains follow-up"));
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
