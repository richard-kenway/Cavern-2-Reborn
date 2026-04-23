package com.richardkenway.cavernreborn.app.mining;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class AquamarineAquaToolEventsTest {
    @Test
    void eventHelperDelegatesToBoundedPolicy() {
        CavernAquamarineToolEvents events = new CavernAquamarineToolEvents();

        assertEquals(20.0F, events.adjustedBreakSpeed(true, true, false, 2.0F));
        assertEquals(10.0F, events.adjustedBreakSpeed(true, true, true, 2.0F));
        assertEquals(2.0F, events.adjustedBreakSpeed(false, true, false, 2.0F));
        assertEquals(2.0F, events.adjustedBreakSpeed(true, false, false, 2.0F));
    }

    @Test
    void eventSourceUsesAquamarineTagAndAvoidsOtherSystems() throws IOException {
        String modSource = Files.readString(resolveProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"));
        String eventSource = Files.readString(resolveProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "mining", "CavernAquamarineToolEvents.java"
        ));

        assertTrue(modSource.contains("new CavernAquamarineToolEvents()"));
        assertTrue(eventSource.contains("AquamarineAquaToolPolicy.evaluate("));
        assertTrue(eventSource.contains("ModItemTags.AQUA_TOOLS"));
        assertTrue(eventSource.contains("event.setNewSpeed("));
        assertTrue(eventSource.contains("player.isEyeInFluid(FluidTags.WATER)"));
        assertTrue(eventSource.contains("EnchantmentHelper.getItemEnchantmentLevel("));
        assertTrue(!eventSource.contains("progression"));
        assertTrue(!eventSource.contains("giveExperiencePoints"));
        assertTrue(!eventSource.contains("FriendlyByteBuf"));
        assertTrue(!eventSource.contains("CustomPacketPayload"));
        assertTrue(!eventSource.contains("canBreatheUnderwater"));
        assertTrue(!eventSource.contains("DepthStrider"));
        assertTrue(!eventSource.contains("moveRelative"));
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
