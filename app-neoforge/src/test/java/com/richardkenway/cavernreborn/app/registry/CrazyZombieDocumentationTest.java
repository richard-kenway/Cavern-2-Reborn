package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazyZombieDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path DOC = resolveProjectFile("docs", "crazy-zombie-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazyZombieBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Zombie Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-zombie-baseline-mvp.md"));
        assertTrue(readme.contains("first legacy crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_zombie`"));
        assertTrue(readme.contains("vanilla zombie loot baseline"));
        assertTrue(readme.contains("crazy skeleton, crazy creeper and crazy spider remain follow-up candidates"));
    }

    @Test
    void crazyZombieBaselineDocStatesLegacyReferencesSelectionRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(DOC);

        assertTrue(doc.contains("`EntityCrazyZombie`"));
        assertTrue(doc.contains("`EntityCrazySkeleton`"));
        assertTrue(doc.contains("`EntityCrazyCreeper`"));
        assertTrue(doc.contains("`EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazyZombie`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`assets/cavern/textures/entity/crazy_zombie.png`"));
        assertTrue(doc.contains("`assets/cavern/lang/en_us.lang`"));
        assertTrue(doc.contains("`cavernreborn:crazy_zombie`"));
        assertTrue(doc.contains("`cavernreborn:crazy_zombie_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Zombie`"));
        assertTrue(doc.contains("Legacy `EntityCrazyZombie` extends `EntityCavenicZombie`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicZombie` baseline"));
        assertTrue(doc.contains("max health: `2000.0`"));
        assertTrue(doc.contains("generic.max_health"));
        assertTrue(doc.contains("cap"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("follow range: `50.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("attack damage: `7.5`"));
        assertTrue(doc.contains("Movement speed intentionally stays on the vanilla zombie baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("base color: `0x909090`"));
        assertTrue(doc.contains("spot color: `0x00A0A0`"));
        assertTrue(doc.contains("reuses the vanilla zombie renderer/model path"));
        assertTrue(doc.contains("vanilla zombie loot table as its base drop source"));
        assertTrue(doc.contains("Natural spawning is intentionally out of scope"));
        assertTrue(doc.contains("Custom loot, `cavenic_orb` drops, fall/fire damage behavior and inherited Cavenic boss-like follow-ups remain out of scope"));
        assertTrue(doc.contains("boss bar / sky-darkening"));
        assertTrue(doc.contains("crazy particle trail"));
        assertTrue(doc.contains("knockback-on-hit behavior"));
        assertTrue(doc.contains("Crazy Skeleton remains a follow-up because it depends on the legacy `EntityAIAttackCavenicBow` path"));
        assertTrue(doc.contains("Crazy Creeper remains a follow-up because its direct legacy identity depends on a huge fuse/explosion branch"));
        assertTrue(doc.contains("Crazy Spider remains a follow-up because it carries stronger blindness/poison combat hooks"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
    }

    @Test
    void runtimeSmokeDocMentionsCrazyZombieBaselineCoverage() throws IOException {
        String doc = Files.readString(RUNTIME_SMOKE);

        assertTrue(doc.contains("crazy zombie runtime registry id"));
        assertTrue(doc.contains("crazy zombie attribute registration smoke"));
        assertTrue(doc.contains("crazy zombie hostile runtime spawn smoke"));
        assertTrue(doc.contains("crazy zombie spawn egg resolution"));
        assertTrue(doc.contains("crazy zombie spawn egg entity-creation smoke"));
        assertTrue(doc.contains("crazy zombie vanilla loot-table baseline smoke"));
        assertTrue(doc.contains("actual Crazy Zombie renderer/model visual feel"));
        assertTrue(doc.contains("actual Crazy Zombie boss-bar, particle and melee-knockback feel"));
        assertTrue(doc.contains("Crazy Zombie natural spawning"));
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
