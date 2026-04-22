package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavernProgressionDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path PROGRESSION_BASELINE = resolveProjectFile("docs", "progression-baseline.md");

    @Test
    void readmeDocumentsCurrentProgressionBaselineAndInspectionPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("minimal server-side `CAVERN` progression shell"));
        assertTrue(readme.contains("first player-facing progression layer"));
        assertTrue(readme.contains("CavernProgressionPolicy.java"));
        assertTrue(readme.contains("/cavern progression"));
        assertTrue(readme.contains("/cavern rank"));
        assertTrue(readme.contains("/cavern rewards"));
        assertTrue(readme.contains("/cavern claim <reward>"));
        assertTrue(readme.contains("/cavern services"));
        assertTrue(readme.contains("/cavern request <service>"));
        assertTrue(readme.contains("/cavern catalog"));
        assertTrue(readme.contains("/cavern use <entry>"));
        assertTrue(readme.contains("/cavern menu"));
        assertTrue(readme.contains("/cavern menu use <entry>"));
        assertTrue(readme.contains("/cavern gui"));
        assertTrue(readme.contains("Miner's Insight"));
        assertTrue(readme.contains("Mining Assist"));
        assertTrue(readme.contains("journeyman"));
        assertTrue(readme.contains("hexcite_pickaxe"));
        assertTrue(readme.contains("docs/mining-assist-mvp.md"));
        assertTrue(readme.contains("apprentice_supply_cache"));
        assertTrue(readme.contains("journeyman_supply_cache"));
        assertTrue(readme.contains("torch_supply"));
        assertTrue(readme.contains("climbing_supply"));
        assertTrue(readme.contains("tiered"));
        assertTrue(readme.contains("clickable chat menu"));
        assertTrue(readme.contains("CavernPlayerMenuFormatter.java"));
        assertTrue(readme.contains("chest-like inventory view"));
        assertTrue(readme.contains("CavernCatalogAccess.java"));
        assertTrue(readme.contains("CavernCatalogGuiLayout.java"));
        assertTrue(readme.contains("CavernCatalogGuiMenu.java"));
        assertTrue(readme.contains("CavernProgressionReward.java"));
        assertTrue(readme.contains("CavernInteractionService.java"));
        assertTrue(readme.contains("docs/progression-baseline.md"));
        assertTrue(readme.contains("uncanceled non-creative player block breaks"));
        assertTrue(readme.contains("aquamarine_ore"));
        assertTrue(readme.contains("magnite_ore"));
        assertTrue(readme.contains("randomite_ore"));
        assertTrue(readme.contains("fissured_stone"));
        assertTrue(readme.contains("hexcite_ore"));
    }

    @Test
    void progressionBaselineDocCallsOutInputsPersistenceAndChecklist() throws IOException {
        String doc = Files.readString(PROGRESSION_BASELINE);

        assertTrue(doc.contains("BlockEvent.BreakEvent"));
        assertTrue(doc.contains("cavernreborn_control_plane"));
        assertTrue(doc.contains("CavernMiningProgressionEvents.java"));
        assertTrue(doc.contains("CavernProgressionCommands.java"));
        assertTrue(doc.contains("CavernProgressionUnlock.java"));
        assertTrue(doc.contains("CavernProgressionReward.java"));
        assertTrue(doc.contains("CavernRewardService.java"));
        assertTrue(doc.contains("CavernCatalogEntry.java"));
        assertTrue(doc.contains("CavernCatalogUseResult.java"));
        assertTrue(doc.contains("Miner's Insight"));
        assertTrue(doc.contains("Mining Assist"));
        assertTrue(doc.contains("hexcite_pickaxe"));
        assertTrue(doc.contains("journeyman"));
        assertTrue(doc.contains("6"));
        assertTrue(doc.contains("do not add extra progression score"));
        assertTrue(doc.contains("apprentice_supply_cache"));
        assertTrue(doc.contains("journeyman_supply_cache"));
        assertTrue(doc.contains("torch_supply"));
        assertTrue(doc.contains("climbing_supply"));
        assertTrue(doc.contains("torch x16, bread x8"));
        assertTrue(doc.contains("torch x24, cooked_beef x8, water_bucket x1"));
        assertTrue(doc.contains("apprentice"));
        assertTrue(doc.contains("journeyman"));
        assertTrue(doc.contains("veteran"));
        assertTrue(doc.contains("master"));
        assertTrue(doc.contains("cavernreborn:magnite_ore"));
        assertTrue(doc.contains("cavernreborn:aquamarine_ore"));
        assertTrue(doc.contains("cavernreborn:randomite_ore"));
        assertTrue(doc.contains("cavernreborn:fissured_stone"));
        assertTrue(doc.contains("cavernreborn:hexcite_ore"));
        assertTrue(doc.contains("Mine the same ore outside `CAVERN`"));
        assertTrue(doc.contains("Restart the server"));
        assertTrue(doc.contains("/cavern rank"));
        assertTrue(doc.contains("/cavern rewards"));
        assertTrue(doc.contains("/cavern claim apprentice_supply_cache"));
        assertTrue(doc.contains("/cavern claim journeyman_supply_cache"));
        assertTrue(doc.contains("/cavern services"));
        assertTrue(doc.contains("/cavern request torch_supply"));
        assertTrue(doc.contains("/cavern request climbing_supply"));
        assertTrue(doc.contains("/cavern catalog"));
        assertTrue(doc.contains("/cavern use apprentice_supply_cache"));
        assertTrue(doc.contains("/cavern use journeyman_supply_cache"));
        assertTrue(doc.contains("/cavern use climbing_supply"));
        assertTrue(doc.contains("/cavern menu"));
        assertTrue(doc.contains("/cavern menu use apprentice_supply_cache"));
        assertTrue(doc.contains("/cavern menu use journeyman_supply_cache"));
        assertTrue(doc.contains("/cavern menu use climbing_supply"));
        assertTrue(doc.contains("/cavern gui"));
        assertTrue(doc.contains("Sneak while mining"));
        assertTrue(doc.contains("clickable `CLAIM` / `USE` actions"));
        assertTrue(doc.contains("CavernPlayerMenuFormatter.java"));
        assertTrue(doc.contains("CavernCatalogAccess.java"));
        assertTrue(doc.contains("CavernCatalogGuiLayout.java"));
        assertTrue(doc.contains("CavernCatalogGuiMenu.java"));
        assertTrue(doc.contains("chest-like inventory container"));
        assertTrue(doc.contains("tiered"));
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
