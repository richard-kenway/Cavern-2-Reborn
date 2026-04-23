package com.richardkenway.cavernreborn.app.mining;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

class MagniteArmorIsolationTest {
    @Test
    void magniteArmorDoesNotLeakIntoMiningOrbOrCompassPaths() throws IOException {
        String policySource = Files.readString(resolveProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "mining", "MiningAssistPolicy.java"
        ));
        String miningAssistSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "mining", "CavernMiningAssistEvents.java"
        ));
        String progressionSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "progression", "CavernMiningProgressionEvents.java"
        ));
        String oreCompassSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "OreCompassItem.java"
        ));
        String clientEventsSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        ));

        assertTrue(policySource.contains("REQUIRED_TOOL_ID = \"cavernreborn:hexcite_pickaxe\""));
        assertTrue(miningAssistSource.contains("MiningAssistPolicy.evaluate("));

        for (String forbiddenNeedle : List.of(
            "magnite_helmet",
            "magnite_chestplate",
            "magnite_leggings",
            "magnite_boots",
            "MAGNITE_HELMET",
            "MAGNITE_CHESTPLATE",
            "MAGNITE_LEGGINGS",
            "MAGNITE_BOOTS"
        )) {
            assertTrue(!miningAssistSource.contains(forbiddenNeedle), "Mining Assist path must not reference " + forbiddenNeedle);
            assertTrue(!progressionSource.contains(forbiddenNeedle), "Miner's Orb progression path must not reference " + forbiddenNeedle);
            assertTrue(!oreCompassSource.contains(forbiddenNeedle), "Ore Compass path must not reference " + forbiddenNeedle);
            assertTrue(!clientEventsSource.contains(forbiddenNeedle), "Client event hooks must not special-case " + forbiddenNeedle);
        }
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
