package com.richardkenway.cavernreborn.app.mining;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class MagniteMiningAssistIsolationTest {
    @Test
    void miningAssistStillRequiresHexcitePickaxeOnly() throws IOException {
        String policySource = Files.readString(resolveProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "mining", "MiningAssistPolicy.java"
        ));
        String eventSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "mining", "CavernMiningAssistEvents.java"
        ));

        assertTrue(policySource.contains("REQUIRED_TOOL_ID = \"cavernreborn:hexcite_pickaxe\""));
        assertTrue(eventSource.contains("MiningAssistPolicy.evaluate("));
        assertTrue(eventSource.contains("BuiltInRegistries.ITEM.getKey(tool.getItem()).toString()"));
        assertTrue(eventSource.contains("player.getMainHandItem()"));
        assertTrue(!eventSource.contains("magnite_pickaxe"));
        assertTrue(!eventSource.contains("MAGNITE_PICKAXE"));
        assertTrue(!eventSource.contains("custom pickaxe"));
        assertTrue(!eventSource.contains("ModItemTags"));
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
