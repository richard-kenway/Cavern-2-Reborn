package com.richardkenway.cavernreborn.app.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

class CavernPersistentStateDataTest {
    @Test
    void saveAndLoadRoundTripPlayerReturnStateAndWorldPortalIndex() {
        UUID playerId = UUID.randomUUID();
        PortalReturnState returnState = new PortalReturnState("cavern_portal", "minecraft:overworld", 12, 70, 14);
        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("cavern_portal", new PortalWorldIndex.PortalPlacement(1, 64, 2, PortalWorldIndex.PortalPlacement.AXIS_X))
            .withPortal("cavern_portal", new PortalWorldIndex.PortalPlacement(3, 65, 4, PortalWorldIndex.PortalPlacement.AXIS_Z));

        CavernPersistentStateData savedData = new CavernPersistentStateData();
        savedData.savePlayerReturnState(playerId, returnState);
        savedData.saveWorldPortalIndex("cavernreborn:cavern", worldIndex);

        CompoundTag serialized = savedData.save(new CompoundTag(), null);
        CavernPersistentStateData restored = CavernPersistentStateData.load(serialized, null);

        assertEquals(returnState, restored.loadPlayerReturnState(playerId).orElseThrow());
        assertEquals(worldIndex, restored.loadWorldPortalIndex("cavernreborn:cavern"));
        assertEquals(
            worldIndex.firstPlacementFor("cavern_portal"),
            restored.loadWorldPortalIndex("cavernreborn:cavern").firstPlacementFor("cavern_portal")
        );
    }

    @Test
    void saveWorldPortalIndexRemovesEmptyIndices() {
        CavernPersistentStateData savedData = new CavernPersistentStateData();
        savedData.saveWorldPortalIndex(
            "minecraft:overworld",
            PortalWorldIndex.empty().withPortal("portal", new PortalWorldIndex.PortalPlacement(1, 2, 3, PortalWorldIndex.PortalPlacement.AXIS_Z))
        );

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

    @Test
    void loadWorldPortalIndexDefaultsMissingAxisTagToXAxis() {
        CompoundTag serialized = new CompoundTag();
        ListTag worldIndices = new ListTag();
        CompoundTag worldIndexTag = new CompoundTag();
        worldIndexTag.putString("WorldKey", "cavernreborn:cavern");

        CompoundTag portalTag = new CompoundTag();
        portalTag.putString("PortalKey", "cavern_portal");

        CompoundTag placementTag = new CompoundTag();
        placementTag.putInt("X", 7);
        placementTag.putInt("Y", 64);
        placementTag.putInt("Z", 8);

        ListTag placements = new ListTag();
        placements.add(placementTag);
        portalTag.put("Placements", placements);

        ListTag portals = new ListTag();
        portals.add(portalTag);
        worldIndexTag.put("Portals", portals);
        worldIndices.add(worldIndexTag);
        serialized.put("WorldPortalIndices", worldIndices);
        serialized.put("PlayerReturnStates", new ListTag());

        CavernPersistentStateData restored = CavernPersistentStateData.load(serialized, null);

        assertEquals(
            PortalWorldIndex.empty().withPortal("cavern_portal", new PortalWorldIndex.PortalPlacement(7, 64, 8, PortalWorldIndex.PortalPlacement.AXIS_X)),
            restored.loadWorldPortalIndex("cavernreborn:cavern")
        );
    }

    @Test
    void loadWorldPortalIndexSkipsInvalidPlacementButKeepsValidOnes() {
        CompoundTag serialized = new CompoundTag();
        ListTag worldIndices = new ListTag();
        CompoundTag worldIndexTag = new CompoundTag();
        worldIndexTag.putString("WorldKey", "cavernreborn:cavern");

        CompoundTag portalTag = new CompoundTag();
        portalTag.putString("PortalKey", "cavern_portal");

        CompoundTag validPlacementTag = new CompoundTag();
        validPlacementTag.putInt("X", 7);
        validPlacementTag.putInt("Y", 64);
        validPlacementTag.putInt("Z", 8);
        validPlacementTag.putString("Axis", PortalWorldIndex.PortalPlacement.AXIS_Z);

        CompoundTag invalidPlacementTag = new CompoundTag();
        invalidPlacementTag.putInt("X", 1);
        invalidPlacementTag.putInt("Y", 2);
        invalidPlacementTag.putInt("Z", 3);
        invalidPlacementTag.putString("Axis", "bad-axis");

        ListTag placements = new ListTag();
        placements.add(validPlacementTag);
        placements.add(invalidPlacementTag);
        portalTag.put("Placements", placements);

        ListTag portals = new ListTag();
        portals.add(portalTag);
        worldIndexTag.put("Portals", portals);
        worldIndices.add(worldIndexTag);
        serialized.put("WorldPortalIndices", worldIndices);
        serialized.put("PlayerReturnStates", new ListTag());

        CavernPersistentStateData restored = CavernPersistentStateData.load(serialized, null);

        PortalWorldIndex restoredIndex = restored.loadWorldPortalIndex("cavernreborn:cavern");
        assertEquals(1, restoredIndex.placementsFor("cavern_portal").size());
        assertEquals(
            new PortalWorldIndex.PortalPlacement(7, 64, 8, PortalWorldIndex.PortalPlacement.AXIS_Z),
            restoredIndex.firstPlacementFor("cavern_portal").orElseThrow()
        );
    }
}
