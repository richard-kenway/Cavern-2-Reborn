package com.richardkenway.cavernreborn.core.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CavenicBowModeTest {
    @Test
    void serializedIdsRemainStable() {
        assertEquals("normal", CavenicBowMode.NORMAL.serializedId());
        assertEquals("rapid", CavenicBowMode.RAPID.serializedId());
        assertEquals("snipe", CavenicBowMode.SNIPE.serializedId());
        assertEquals("torch", CavenicBowMode.TORCH.serializedId());
    }

    @Test
    void cycleOrderFollowsLegacySequenceAndWraps() {
        assertEquals(CavenicBowMode.RAPID, CavenicBowMode.NORMAL.next());
        assertEquals(CavenicBowMode.SNIPE, CavenicBowMode.RAPID.next());
        assertEquals(CavenicBowMode.TORCH, CavenicBowMode.SNIPE.next());
        assertEquals(CavenicBowMode.NORMAL, CavenicBowMode.TORCH.next());
    }

    @Test
    void missingOrUnknownSerializedIdsFallBackToNormal() {
        assertEquals(CavenicBowMode.NORMAL, CavenicBowMode.fromSerializedId(null));
        assertEquals(CavenicBowMode.NORMAL, CavenicBowMode.fromSerializedId(""));
        assertEquals(CavenicBowMode.NORMAL, CavenicBowMode.fromSerializedId("invalid"));
    }

    @Test
    void knownSerializedIdsResolveToExpectedModes() {
        assertEquals(CavenicBowMode.NORMAL, CavenicBowMode.fromSerializedId("normal"));
        assertEquals(CavenicBowMode.RAPID, CavenicBowMode.fromSerializedId("rapid"));
        assertEquals(CavenicBowMode.SNIPE, CavenicBowMode.fromSerializedId("snipe"));
        assertEquals(CavenicBowMode.TORCH, CavenicBowMode.fromSerializedId("torch"));
    }
}
