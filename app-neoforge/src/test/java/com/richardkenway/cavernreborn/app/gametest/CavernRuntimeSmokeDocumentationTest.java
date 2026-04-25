package com.richardkenway.cavernreborn.app.gametest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavernRuntimeSmokeDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SCRIPT = resolveProjectFile("scripts", "runtime-smoke.sh");

    @Test
    void readmeDocumentsAutomatedRuntimeSmokeEntryPoint() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Automated Runtime Validation"));
        assertTrue(readme.contains("NeoForge GameTest"));
        assertTrue(readme.contains("docs/runtime-smoke.md"));
        assertTrue(readme.contains("./gradlew --no-daemon :app-neoforge:runGameTestServer"));
        assertTrue(readme.contains("scripts/runtime-smoke.sh"));
        assertTrue(readme.contains("hexcite"));
        assertTrue(readme.contains("randomite"));
        assertTrue(readme.contains("fissured_stone"));
        assertTrue(readme.contains("Mining Assist"));
        assertTrue(readme.contains("Miner's Orb MVP"));
        assertTrue(readme.contains("docs/miner-orb-mvp.md"));
        assertTrue(readme.contains("Ore Compass MVP"));
        assertTrue(readme.contains("docs/ore-compass-mvp.md"));
        assertTrue(readme.contains("bounded tracking UX follow-up"));
        assertTrue(readme.contains("track that target visually"));
        assertTrue(readme.contains("Hexcite Armor MVP"));
        assertTrue(readme.contains("docs/hexcite-armor-mvp.md"));
        assertTrue(readme.contains("Aquamarine Utility Tools MVP"));
        assertTrue(readme.contains("docs/aquamarine-tool-mvp.md"));
        assertTrue(readme.contains("Magnite Tool Set MVP"));
        assertTrue(readme.contains("docs/magnite-tool-mvp.md"));
        assertTrue(readme.contains("Magnite Armor MVP"));
        assertTrue(readme.contains("docs/magnite-armor-mvp.md"));
        assertTrue(readme.contains("Acresia Crop & Food MVP"));
        assertTrue(readme.contains("docs/acresia-crop-mvp.md"));
        assertTrue(readme.contains("renewable cave snack crop"));
        assertTrue(readme.contains("full farming system"));
        assertTrue(readme.contains("Cavenic Shroom & Orb MVP"));
        assertTrue(readme.contains("docs/cavenic-shroom-mvp.md"));
        assertTrue(readme.contains("hazardous flora"));
        assertTrue(readme.contains("Cavenic Bow Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Bow Mode State & Cycling MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-mode-state-mvp.md"));
        assertTrue(readme.contains("Cavenic Bow Snipe Mode MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-snipe-mode-mvp.md"));
        assertTrue(readme.contains("Cavenic Bow Rapid Mode MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-rapid-mode-mvp.md"));
        assertTrue(readme.contains("Cavenic Bow Torch Mode MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-torch-mode-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Legacy Orb Drop MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-orb-drop-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-damage-behavior-mvp.md"));
        assertTrue(readme.contains("legacy `1/8` `cavenic_orb` drop"));
        assertTrue(readme.contains("legacy fall-damage reduction and fire-damage immunity"));
        assertTrue(readme.contains("Cavenic Skeleton Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-skeleton-baseline-mvp.md"));
        assertTrue(readme.contains("second direct Cavenic mob foundation"));
        assertTrue(readme.contains("Cavenic Skeleton Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-skeleton-natural-spawn-mvp.md"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("Cavenic Skeleton Legacy Orb Drop MVP"));
        assertTrue(readme.contains("docs/cavenic-skeleton-orb-drop-mvp.md"));
        assertTrue(readme.contains("legacy `1/5` `cavenic_orb` drop"));
        assertTrue(readme.contains("Cavenic Skeleton Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-skeleton-damage-behavior-mvp.md"));
        assertTrue(readme.contains("legacy fall-damage reduction and fire-damage immunity"));
        assertTrue(readme.contains("Cavenic Creeper Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Creeper Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Creeper Legacy Orb Drop MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-orb-drop-mvp.md"));
        assertTrue(readme.contains("Cavenic Creeper Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-damage-behavior-mvp.md"));
        assertTrue(readme.contains("third direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("legacy `1/5` `cavenic_orb` drop"));
        assertTrue(readme.contains("legacy fall-damage reduction and fire-damage immunity"));
    }

    @Test
    void runtimeSmokeDocCoversCommandsAndScope() throws IOException {
        String doc = Files.readString(RUNTIME_SMOKE);

        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
        assertTrue(doc.contains("runtime registry availability for tranche 2 content"));
        assertTrue(doc.contains("acresia runtime registry ids"));
        assertTrue(doc.contains("acresia seed planting smoke"));
        assertTrue(doc.contains("acresia mature normal harvest smoke"));
        assertTrue(doc.contains("acresia shear-harvest regrowth smoke"));
        assertTrue(doc.contains("acresia edible fruit wiring"));
        assertTrue(doc.contains("acresia worldgen configured/placed key resolution"));
        assertTrue(doc.contains("cavenic shroom runtime registry ids"));
        assertTrue(doc.contains("cavenic shroom bounded collision nausea"));
        assertTrue(doc.contains("cavenic shroom bounded shear-harvest helper wiring"));
        assertTrue(doc.contains("cavenic orb drop policy/runtime smoke"));
        assertTrue(doc.contains("cavenic bow runtime registry id"));
        assertTrue(doc.contains("cavenic bow max-damage and damageable-item smoke"));
        assertTrue(doc.contains("cavenic bow repairability with `cavernreborn:cavenic_orb`"));
        assertTrue(doc.contains("cavenic bow no-repair-with-stick smoke"));
        assertTrue(doc.contains("cavenic bow default mode/runtime stack-state smoke"));
        assertTrue(doc.contains("cavenic bow mode cycling order and persistence"));
        assertTrue(doc.contains("cavenic bow sneak-use mode-cycling smoke"));
        assertTrue(doc.contains("cavenic bow RAPID adjusted-shot-power smoke"));
        assertTrue(doc.contains("cavenic bow RAPID higher-velocity vanilla-arrow smoke"));
        assertTrue(doc.contains("cavenic bow RAPID no-extra-durability smoke"));
        assertTrue(doc.contains("cavenic bow RAPID still spawning a vanilla arrow entity"));
        assertTrue(doc.contains("cavenic bow RAPID not inheriting the SNIPE damage multiplier"));
        assertTrue(doc.contains("cavenic bow full-charge SNIPE projectile boost smoke"));
        assertTrue(doc.contains("cavenic bow full-charge SNIPE extra-durability smoke"));
        assertTrue(doc.contains("cavenic bow full-charge SNIPE still spawning a vanilla arrow entity"));
        assertTrue(doc.contains("cavenic bow TORCH vanilla-arrow marker smoke"));
        assertTrue(doc.contains("cavenic bow TORCH valid block-placement smoke"));
        assertTrue(doc.contains("cavenic bow TORCH invalid-target no-placement smoke"));
        assertTrue(doc.contains("cavenic bow TORCH torch-consumption smoke"));
        assertTrue(doc.contains("cavenic bow TORCH no-custom-entity smoke"));
        assertTrue(doc.contains("cavenic bow TORCH not inheriting RAPID or SNIPE behavior"));
        assertTrue(doc.contains("cavenic zombie runtime registry id"));
        assertTrue(doc.contains("cavenic zombie attribute registration smoke"));
        assertTrue(doc.contains("cavenic zombie hostile runtime spawn smoke"));
        assertTrue(doc.contains("cavenic zombie spawn egg resolution"));
        assertTrue(doc.contains("cavenic zombie spawn egg entity-creation smoke"));
        assertTrue(doc.contains("cavenic zombie natural spawn placement registration"));
        assertTrue(doc.contains("cavenic zombie CAVERN-only spawn predicate smoke"));
        assertTrue(doc.contains("cavenic zombie biome modifier registry smoke"));
        assertTrue(doc.contains("cavenic zombie biome tag resolution"));
        assertTrue(doc.contains("cavenic zombie vanilla loot-table baseline smoke"));
        assertTrue(doc.contains("cavenic zombie legacy orb-drop event wiring smoke"));
        assertTrue(doc.contains("cavenic zombie legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(doc.contains("cavenic zombie legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("cavenic zombie legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("cavenic zombie generic-damage baseline smoke"));
        assertTrue(doc.contains("cavenic skeleton runtime registry id"));
        assertTrue(doc.contains("cavenic skeleton attribute registration smoke"));
        assertTrue(doc.contains("cavenic skeleton hostile runtime spawn smoke"));
        assertTrue(doc.contains("cavenic skeleton spawn egg resolution"));
        assertTrue(doc.contains("cavenic skeleton spawn egg entity-creation smoke"));
        assertTrue(doc.contains("cavenic skeleton natural spawn placement registration"));
        assertTrue(doc.contains("cavenic skeleton CAVERN-only spawn predicate smoke"));
        assertTrue(doc.contains("cavenic skeleton biome modifier registry smoke"));
        assertTrue(doc.contains("cavenic skeleton biome tag resolution"));
        assertTrue(doc.contains("cavenic skeleton vanilla loot-table baseline smoke"));
        assertTrue(doc.contains("cavenic skeleton legacy orb-drop event wiring smoke"));
        assertTrue(doc.contains("cavenic skeleton legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(doc.contains("cavenic skeleton legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("cavenic skeleton legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("cavenic skeleton generic-damage baseline smoke"));
        assertTrue(doc.contains("cavenic creeper runtime registry id"));
        assertTrue(doc.contains("cavenic creeper attribute registration smoke"));
        assertTrue(doc.contains("cavenic creeper hostile runtime spawn smoke"));
        assertTrue(doc.contains("cavenic creeper vanilla loot-table baseline smoke"));
        assertTrue(doc.contains("cavenic creeper spawn egg resolution"));
        assertTrue(doc.contains("cavenic creeper spawn egg entity-creation smoke"));
        assertTrue(doc.contains("cavenic creeper natural spawn placement registration"));
        assertTrue(doc.contains("cavenic creeper CAVERN-only spawn predicate smoke"));
        assertTrue(doc.contains("cavenic creeper biome modifier registry smoke"));
        assertTrue(doc.contains("cavenic creeper biome tag resolution"));
        assertTrue(doc.contains("cavenic creeper legacy orb-drop event wiring smoke"));
        assertTrue(doc.contains("cavenic creeper legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(doc.contains("cavenic creeper legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("cavenic creeper legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("cavenic creeper generic-damage baseline smoke"));
        assertTrue(doc.contains("cavenic bow enchantment applicability"));
        assertTrue(doc.contains("cavenic bow cavenic item tag resolution"));
        assertTrue(doc.contains("cavenic bow recipe manager resolution"));
        assertTrue(doc.contains("cavenic shroom worldgen configured/placed key resolution"));
        assertTrue(doc.contains("aquamarine tool runtime registry ids"));
        assertTrue(doc.contains("aquamarine tool repairability with `cavernreborn:aquamarine`"));
        assertTrue(doc.contains("aquamarine tool enchantment applicability"));
        assertTrue(doc.contains("aquamarine underwater utility policy/runtime smoke"));
        assertTrue(doc.contains("magnite tool runtime registry ids"));
        assertTrue(doc.contains("magnite tool repairability with `cavernreborn:magnite_ingot`"));
        assertTrue(doc.contains("magnite tool enchantment applicability"));
        assertTrue(doc.contains("magnite brittle max-damage/runtime smoke"));
        assertTrue(doc.contains("magnite no-unintended-Mining-Assist runtime smoke"));
        assertTrue(doc.contains("magnite armor runtime registry ids"));
        assertTrue(doc.contains("magnite armor repairability with `cavernreborn:magnite_ingot`"));
        assertTrue(doc.contains("magnite armor enchantment applicability"));
        assertTrue(doc.contains("magnite armor durability and slot/equippable runtime behavior"));
        assertTrue(doc.contains("hexcite normal and Silk Touch loot paths"));
        assertTrue(doc.contains("randomite curated runtime loot output"));
        assertTrue(doc.contains("hexcite pickaxe enchantment applicability"));
        assertTrue(doc.contains("hexcite armor runtime registry ids"));
        assertTrue(doc.contains("hexcite armor repairability with `cavernreborn:hexcite`"));
        assertTrue(doc.contains("hexcite armor durability and slot/equippable runtime behavior"));
        assertTrue(doc.contains("hexcite armor enchantment applicability"));
        assertTrue(doc.contains("miner_orb runtime registry availability"));
        assertTrue(doc.contains("randomite allowed-drop runtime coverage that now includes miner_orb in the curated pool"));
        assertTrue(doc.contains("miner_orb bonus policy/runtime smoke"));
        assertTrue(doc.contains("ore_compass runtime registry availability"));
        assertTrue(doc.contains("ore_compass target tag resolution"));
        assertTrue(doc.contains("ore_compass scanner nearest-target behavior"));
        assertTrue(doc.contains("ore_compass stored-target state round-trip"));
        assertTrue(doc.contains("ore_compass tracking policy/runtime ids"));
        assertTrue(doc.contains("unsupported/fissured/storage exclusion during ore_compass scans"));
        assertTrue(doc.contains("Mining Assist policy/runtime ids"));
        assertTrue(doc.contains("bounded same-block vein assist with hexcite_pickaxe"));
        assertTrue(doc.contains("no unlock means no assist"));
        assertTrue(doc.contains("sneaking disables assist"));
        assertTrue(doc.contains("fissured_stone exclusion"));
        assertTrue(doc.contains("fissured stone no-drop behavior"));
        assertTrue(doc.contains("fissured stone survival effect behavior"));
        assertTrue(doc.contains("fissured stone creative guard"));
        assertTrue(doc.contains("fissured stone non-destructive behavior"));
        assertTrue(doc.contains("progression policy ids/scores"));
        assertTrue(doc.contains("worldgen configured/placed feature runtime resolution"));
        assertTrue(doc.contains("./gradlew --no-daemon :app-neoforge:runGameTestServer"));
        assertTrue(doc.contains("docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:runGameTestServer"));
        assertTrue(doc.contains("scripts/runtime-smoke.sh"));
        assertTrue(doc.contains("minecraft:empty"));
        assertTrue(doc.contains("app-neoforge/src/gameteststructures/empty.snbt"));
        assertTrue(doc.contains("GameTestRegistry.register(CavernSpecialOreGameTests.class)"));
        assertTrue(doc.contains("unguarded `GameTestRegistry.register"));
        assertTrue(doc.contains("GameTestHooks.isGametestEnabled()"));
        assertTrue(doc.contains("infrastructure workaround"));
        assertTrue(doc.contains("does not change gameplay behavior"));
        assertTrue(doc.contains("manual client smoke is still needed"));
        assertTrue(doc.contains("actual client-visible needle feel still requires manual smoke"));
        assertTrue(doc.contains("actual worn hexcite armor appearance"));
        assertTrue(doc.contains("actual worn magnite armor appearance"));
        assertTrue(doc.contains("actual Acresia in-world farming feel and growth cadence"));
        assertTrue(doc.contains("actual Cavenic Shroom hazard feel, sparse spread feel and harvest feel"));
        assertTrue(doc.contains("actual Cavenic Bow draw, release and visual client feel"));
        assertTrue(doc.contains("actual Cavenic Zombie renderer/model visual feel"));
        assertTrue(doc.contains("actual Cavenic Skeleton renderer/model visual feel"));
        assertTrue(doc.contains("actual Cavenic Creeper renderer/model visual feel"));
        assertTrue(doc.contains("actual long-run cavenic creeper orb-drop rate balance"));
        assertTrue(doc.contains("actual long-running Cavenic Creeper fire/lava gameplay feel"));
        assertTrue(doc.contains("actual long-run Cavenic Creeper population balance inside CAVERN"));
        assertTrue(doc.contains("actual long-run cavenic zombie orb-drop rate balance"));
        assertTrue(doc.contains("actual long-run cavenic skeleton orb-drop rate balance"));
        assertTrue(doc.contains("actual long-running Cavenic Zombie fire/lava gameplay feel"));
        assertTrue(doc.contains("actual long-running Cavenic Skeleton fire/lava gameplay feel"));
        assertTrue(doc.contains("actual Cavenic Creeper fuse and explosion feel"));
        assertTrue(doc.contains("actual long-run Cavenic Skeleton population balance inside CAVERN"));
        assertTrue(doc.contains("legacy Cavenic Bow Rapid/Torch custom projectile behavior and client feel beyond the current bounded Snipe, Rapid and Torch slices"));
        assertTrue(doc.contains("actual long-run Cavenic Zombie population balance inside CAVERN"));
        assertTrue(doc.contains("actual underwater aquamarine mining feel"));
        assertTrue(doc.contains("actual brittle magnite mining feel"));
        assertTrue(doc.contains("portal UX"));
        assertTrue(doc.contains("rendering"));
    }

    @Test
    void runtimeSmokeScriptExists() {
        assertTrue(Files.exists(SCRIPT));
        assertTrue(Files.isExecutable(SCRIPT));
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
