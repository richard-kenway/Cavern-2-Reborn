package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicBowDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_BOW_BASELINE = resolveProjectFile("docs", "cavenic-bow-baseline-mvp.md");
    private static final Path CAVENIC_BOW_MODE_STATE = resolveProjectFile("docs", "cavenic-bow-mode-state-mvp.md");
    private static final Path CAVENIC_BOW_SNIPE = resolveProjectFile("docs", "cavenic-bow-snipe-mode-mvp.md");
    private static final Path CAVENIC_BOW_RAPID = resolveProjectFile("docs", "cavenic-bow-rapid-mode-mvp.md");
    private static final Path CAVENIC_BOW_TORCH = resolveProjectFile("docs", "cavenic-bow-torch-mode-mvp.md");
    private static final Path CAVENIC_BOW_RELEASE = resolveProjectFile("docs", "cavenic-bow-release-semantics-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicBowBaselineModeStateSnipeRapidTorchAndReleaseSemanticsSlices() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Bow Baseline MVP"));
        assertTrue(readme.contains("Cavenic Bow Mode State & Cycling MVP"));
        assertTrue(readme.contains("Cavenic Bow Snipe Mode MVP"));
        assertTrue(readme.contains("Cavenic Bow Rapid Mode MVP"));
        assertTrue(readme.contains("Cavenic Bow Torch Mode MVP"));
        assertTrue(readme.contains("Cavenic Bow Release Semantics Regression MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-baseline-mvp.md"));
        assertTrue(readme.contains("docs/cavenic-bow-mode-state-mvp.md"));
        assertTrue(readme.contains("docs/cavenic-bow-snipe-mode-mvp.md"));
        assertTrue(readme.contains("docs/cavenic-bow-rapid-mode-mvp.md"));
        assertTrue(readme.contains("docs/cavenic-bow-torch-mode-mvp.md"));
        assertTrue(readme.contains("docs/cavenic-bow-release-semantics-mvp.md"));
        assertTrue(readme.contains("cavenic_bow"));
        assertTrue(readme.contains("stack-local mode state"));
        assertTrue(readme.contains("sneak-use mode cycling"));
        assertTrue(readme.contains("bounded full-charge Snipe boost on vanilla arrows"));
        assertTrue(readme.contains("bounded Rapid power ramp on vanilla arrows"));
        assertTrue(readme.contains("bounded Torch marker-and-placement behavior on vanilla arrows"));
        assertTrue(readme.contains("real release-path regression coverage"));
        assertTrue(readme.contains("custom projectile entities"));
    }

    @Test
    void cavenicBowBaselineDocStatesLegacyReferencesAndBoundedBaseline() throws IOException {
        String doc = Files.readString(CAVENIC_BOW_BASELINE);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("vanilla bow baseline"));
        assertTrue(doc.contains("does not repair with `minecraft:stick`"));
        assertTrue(doc.contains("docs/cavenic-bow-mode-state-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-snipe-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-rapid-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-torch-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-release-semantics-mvp.md"));
        assertTrue(doc.contains("Legacy custom bow behavior remains follow-up work"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicBowModeStateDocStatesLegacyReferencesStorageCyclingAndBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_BOW_MODE_STATE);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("NORMAL -> RAPID -> SNIPE -> TORCH"));
        assertTrue(doc.contains("`DataComponents.CUSTOM_DATA`"));
        assertTrue(doc.contains("Sneak + use"));
        assertTrue(doc.contains("docs/cavenic-bow-snipe-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-rapid-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-torch-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-release-semantics-mvp.md"));
        assertTrue(doc.contains("vanilla bow shooting behavior"));
        assertTrue(doc.contains("`EntityRapidArrow`"));
        assertTrue(doc.contains("`EntityTorchArrow`"));
        assertTrue(doc.contains("custom projectile behavior remains out of scope"));
    }

    @Test
    void cavenicBowSnipeDocStatesLegacyReferencesBoundedBehaviorAndBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_BOW_SNIPE);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("Legacy `cavern.entity.projectile.EntityRapidArrow`"));
        assertTrue(doc.contains("Legacy `cavern.entity.projectile.EntityTorchArrow`"));
        assertTrue(doc.contains("NORMAL -> RAPID -> SNIPE -> TORCH"));
        assertTrue(doc.contains("minimum power threshold is pinned to `1.0F`"));
        assertTrue(doc.contains("projectile velocity multiplier is pinned to `1.5F`"));
        assertTrue(doc.contains("projectile base damage multiplier is pinned to `1.5D`"));
        assertTrue(doc.contains("extra bow durability cost is pinned to `1`"));
        assertTrue(doc.contains("docs/cavenic-bow-rapid-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-torch-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-release-semantics-mvp.md"));
        assertTrue(doc.contains("The fired projectile remains the normal vanilla arrow type"));
        assertTrue(doc.contains("RAPID mode shooting behavior"));
        assertTrue(doc.contains("TORCH mode shooting behavior"));
        assertTrue(doc.contains("custom projectile entity registration"));
    }

    @Test
    void cavenicBowRapidDocStatesLegacyReferencesBoundedBehaviorAndBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_BOW_RAPID);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic.BowMode`"));
        assertTrue(doc.contains("Legacy `cavern.entity.projectile.EntityRapidArrow`"));
        assertTrue(doc.contains("Legacy `cavern.entity.projectile.EntityTorchArrow`"));
        assertTrue(doc.contains("NORMAL -> RAPID -> SNIPE -> TORCH"));
        assertTrue(doc.contains("power multiplier is pinned to `2.4F`"));
        assertTrue(doc.contains("adjusted shot power is capped at `1.0F`"));
        assertTrue(doc.contains("extra bow durability cost remains `0`"));
        assertTrue(doc.contains("docs/cavenic-bow-torch-mode-mvp.md"));
        assertTrue(doc.contains("docs/cavenic-bow-release-semantics-mvp.md"));
        assertTrue(doc.contains("The fired projectile remains the normal vanilla arrow type"));
        assertTrue(doc.contains("does not yet port `EntityRapidArrow`"));
        assertTrue(doc.contains("TORCH mode shooting behavior"));
        assertTrue(doc.contains("custom projectile entity registration"));
    }

    @Test
    void cavenicBowTorchDocStatesLegacyReferencesBoundedBehaviorAndBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_BOW_TORCH);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic.BowMode`"));
        assertTrue(doc.contains("Legacy `cavern.entity.projectile.EntityTorchArrow`"));
        assertTrue(doc.contains("Legacy `cavern.entity.projectile.EntityRapidArrow`"));
        assertTrue(doc.contains("NORMAL -> RAPID -> SNIPE -> TORCH"));
        assertTrue(doc.contains("scoreboard-tag marker"));
        assertTrue(doc.contains("consumes one `minecraft:torch`"));
        assertTrue(doc.contains("creative shooters do not consume torches"));
        assertTrue(doc.contains("standing `minecraft:torch`"));
        assertTrue(doc.contains("horizontal `minecraft:wall_torch`"));
        assertTrue(doc.contains("never replaces liquids"));
        assertTrue(doc.contains("does not yet port `EntityTorchArrow`"));
        assertTrue(doc.contains("RAPID and SNIPE remain unchanged"));
        assertTrue(doc.contains("docs/cavenic-bow-release-semantics-mvp.md"));
        assertTrue(doc.contains("custom projectile entity registration"));
    }

    @Test
    void cavenicBowReleaseSemanticsDocStatesVanillaCoverageAndBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_BOW_RELEASE);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("Vanilla `net.minecraft.world.item.BowItem#use`"));
        assertTrue(doc.contains("Vanilla `net.minecraft.world.item.BowItem#releaseUsing`"));
        assertTrue(doc.contains("Vanilla `net.minecraft.world.item.ProjectileWeaponItem.draw`"));
        assertTrue(doc.contains("survival arrow behavior"));
        assertTrue(doc.contains("creative/no-arrow behavior"));
        assertTrue(doc.contains("Infinity behavior"));
        assertTrue(doc.contains("Torch consumption stays separate from arrow consumption"));
        assertTrue(doc.contains("wall torch orientation"));
        assertTrue(doc.contains("`EntityRapidArrow`"));
        assertTrue(doc.contains("`EntityTorchArrow`"));
        assertTrue(doc.contains("custom projectile entities remain out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicBowReleaseSemanticsCoverageAndProjectileBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic bow runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic bow max-damage and damageable-item smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow repairability with `cavernreborn:cavenic_orb`"));
        assertTrue(runtimeSmoke.contains("cavenic bow default mode/runtime stack-state smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow mode cycling order and persistence"));
        assertTrue(runtimeSmoke.contains("cavenic bow sneak-use mode-cycling smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow real release-path NORMAL survival arrow-consumption smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow real release-path survival no-arrow smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow real release-path creative no-arrow smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow real release-path Infinity smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow RAPID adjusted-shot-power smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow RAPID higher-velocity vanilla-arrow smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow RAPID no-extra-durability smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow RAPID still spawning a vanilla arrow entity"));
        assertTrue(runtimeSmoke.contains("cavenic bow full-charge SNIPE projectile boost smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow full-charge SNIPE extra-durability smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow full-charge SNIPE still spawning a vanilla arrow entity"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH vanilla-arrow marker smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH valid block-placement smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH invalid-target no-placement smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH torch-consumption smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH wall-torch orientation smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH no-custom-entity smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow RAPID not inheriting the SNIPE damage multiplier"));
        assertTrue(runtimeSmoke.contains("cavenic bow TORCH not inheriting RAPID or SNIPE behavior"));
        assertTrue(runtimeSmoke.contains("cavenic bow recipe manager resolution"));
        assertTrue(runtimeSmoke.contains("custom projectile behavior"));
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
