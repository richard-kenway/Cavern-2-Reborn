package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicCreeperDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_CREEPER_BASELINE_MVP = resolveProjectFile("docs", "cavenic-creeper-baseline-mvp.md");
    private static final Path CAVENIC_CREEPER_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-creeper-natural-spawn-mvp.md");
    private static final Path CAVENIC_CREEPER_ORB_DROP_MVP = resolveProjectFile("docs", "cavenic-creeper-orb-drop-mvp.md");
    private static final Path CAVENIC_CREEPER_DAMAGE_BEHAVIOR_MVP = resolveProjectFile("docs", "cavenic-creeper-damage-behavior-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicCreeperBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Creeper Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Creeper Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Creeper Legacy Orb Drop MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-orb-drop-mvp.md"));
        assertTrue(readme.contains("Cavenic Creeper Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-damage-behavior-mvp.md"));
        assertTrue(readme.contains("cavenic_creeper"));
        assertTrue(readme.contains("third direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("legacy `1/5` `cavenic_orb` drop"));
        assertTrue(readme.contains("legacy fall-damage reduction and fire-damage immunity"));
    }

    @Test
    void cavenicCreeperBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_CREEPER_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_creeper`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_creeper_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Creeper`"));
        assertTrue(doc.contains("max health: `30.0`"));
        assertTrue(doc.contains("movement speed: `0.2`"));
        assertTrue(doc.contains("knockback resistance: `0.85`"));
        assertTrue(doc.contains("follow range intentionally stays on the vanilla creeper baseline"));
        assertTrue(doc.contains("XP reward is pinned to `13`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x2E8B57`"));
        assertTrue(doc.contains("reuses the vanilla creeper renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla creeper loot table as its base drop source"));
        assertTrue(doc.contains("Legacy `EntityCavenicCreeper` also added a `1/5` `cavenic_orb` drop."));
        assertTrue(doc.contains("That orb-drop follow-up is now documented separately in `docs/cavenic-creeper-orb-drop-mvp.md`."));
        assertTrue(doc.contains("`fuseTime = 15`"));
        assertTrue(doc.contains("`explosionRadius = 5`"));
        assertTrue(doc.contains("Custom explosion behavior remains out of scope"));
        assertTrue(doc.contains("The bounded natural-spawn follow-up is documented separately in `docs/cavenic-creeper-natural-spawn-mvp.md`"));
        assertTrue(doc.contains("The bounded legacy orb-drop follow-up is now documented separately in `docs/cavenic-creeper-orb-drop-mvp.md`."));
        assertTrue(doc.contains("The bounded legacy damage-behavior follow-up is now documented separately in `docs/cavenic-creeper-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("custom AI remains out of scope"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicCreeperNaturalSpawnDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_CREEPER_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("SpawnPlacementTypes.ON_GROUND"));
        assertTrue(doc.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(doc.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(doc.contains("data/cavernreborn/neoforge/biome_modifier/cavenic_creeper_spawns.json"));
        assertTrue(doc.contains("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_creeper.json"));
        assertTrue(doc.contains("weight: `30`"));
        assertTrue(doc.contains("group size: `1..1`"));
        assertTrue(doc.contains("Natural spawning is deliberately limited to `CAVERN`"));
        assertTrue(doc.contains("The bounded legacy orb-drop follow-up is documented separately in `docs/cavenic-creeper-orb-drop-mvp.md`"));
        assertTrue(doc.contains("custom loot beyond that orb-drop follow-up remains out of scope"));
        assertTrue(doc.contains("The bounded legacy damage-behavior follow-up is documented separately in `docs/cavenic-creeper-damage-behavior-mvp.md`"));
        assertTrue(doc.contains("custom explosion behavior remains out of scope"));
    }

    @Test
    void cavenicCreeperOrbDropDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_CREEPER_ORB_DROP_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("`dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`"));
        assertTrue(doc.contains("`rand.nextInt(5) == 0`"));
        assertTrue(doc.contains("The chance is `1/5`."));
        assertTrue(doc.contains("vanilla creeper loot table as the base drop source"));
        assertTrue(doc.contains("`LivingDropsEvent` listener in `app.entity.CavenicCreeperLootEvents`"));
        assertTrue(doc.contains("`CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND = 5`"));
        assertTrue(doc.contains("Looting does not affect the orb chance"));
        assertTrue(doc.contains("The orb drop does not require a player kill"));
        assertTrue(doc.contains("No progression, dimension or economy hook changes affect the orb drop."));
        assertTrue(doc.contains("The separate legacy fall/fire damage follow-up is documented in `docs/cavenic-creeper-damage-behavior-mvp.md`"));
        assertTrue(doc.contains("Legacy fuse/explosion tuning remains out of scope"));
        assertTrue(doc.contains("Natural spawn values remain out of scope"));
    }

    @Test
    void cavenicCreeperDamageBehaviorDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_CREEPER_DAMAGE_BEHAVIOR_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("legacy `attackEntityFrom(DamageSource source, float damage)` override"));
        assertTrue(doc.contains("`damage *= 0.35F`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("entity-local `hurt(DamageSource source, float damage)` override"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("Fall damage is reduced to `35%`"));
        assertTrue(doc.contains("All modern fire-tagged damage sources are ignored"));
        assertTrue(doc.contains("lava"));
        assertTrue(doc.contains("vanilla creeper AI remains unchanged"));
        assertTrue(doc.contains("attributes, natural spawning and `1/5` orb-drop behavior remain unchanged"));
        assertTrue(doc.contains("`fuseTime = 15` and `explosionRadius = 5` remain out of scope"));
        assertTrue(doc.contains("Custom explosion behavior remains out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicCreeperDamageCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic creeper runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic creeper attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic creeper spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper natural spawn placement registration"));
        assertTrue(runtimeSmoke.contains("cavenic creeper CAVERN-only spawn predicate smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper biome modifier registry smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper biome tag resolution"));
        assertTrue(runtimeSmoke.contains("cavenic creeper legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper legacy fall-damage reduction smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper legacy fire-damage immunity smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper generic-damage baseline smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Creeper renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual long-running Cavenic Creeper fire/lava gameplay feel"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Creeper fuse and explosion feel"));
        assertTrue(runtimeSmoke.contains("actual long-run cavenic creeper orb-drop rate balance"));
        assertTrue(runtimeSmoke.contains("actual long-run Cavenic Creeper population balance inside CAVERN"));
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
