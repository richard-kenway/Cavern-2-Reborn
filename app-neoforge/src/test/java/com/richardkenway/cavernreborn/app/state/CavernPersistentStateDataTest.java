package com.richardkenway.cavernreborn.app.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

import net.minecraft.nbt.CompoundTag;

class CavernPersistentStateDataTest {
    @Test
    void saveAndLoadRoundTripPlayerReturnStateAndWorldPortalIndex() {
        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern_portal", "minecraft:overworld", 12, 70, 14);
        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("cavern_portal", new PortalWorldIndex.PortalPlacement(1, 64, 2))
            .withPortal("cavern_portal", new PortalWorldIndex.PortalPlacement(3, 65, 4));

        CavernPersistentStateData savedData = new CavernPersistentStateData();
        savedData.savePlayerReturnState(playerId, returnState);
        savedData.saveWorldPortalIndex("cavernreborn:cavern", worldIndex);

        CompoundTag serialized = savedData.save(new CompoundTag(), null);
        CavernPersistentStateData restored = CavernPersistentStateData.load(serialized, null);

        assertEquals(returnState, restored.loadPlayerReturnState(playerId).orElseThrow());
        assertEquals(worldIndex, restored.loadWorldPortalIndex("cavernreborn:cavern"));
    }

    @Test
    void saveWorldPortalIndexRemovesEmptyIndices() {
        CavernPersistentStateData savedData = new CavernPersistentStateData();
        savedData.saveWorldPortalIndex("minecraft:overworld", PortalWorldIndex.empty().withPortal("portal", new PortalWorldIndex.PortalPlacement(1, 2, 3)));

        savedData.saveWorldPortalIndex("minecraft:overworld", PortalWorldIndex.empty());

        assertEquals(PortalWorldIndex.empty(), savedData.loadWorldPortalIndex("minecraft:overworld"));
    }

    @Test
    void clearPlayerReturnStateRemovesSavedEntry() {
        UUID playerId = UUID.randomUUID();
        CavernPersistentStateData savedData = new CavernPersistentStateData();
        savedData.savePlayerReturnState(playerId, new PortalReturnState("portal", "minecraft:overworld", 1, 2, 3));

        savedData.clearPlayerReturnState(playerId);

        assertFalse(savedData.loadPlayerReturnState(playerId).isPresent());
    }
}
