package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.junit.jupiter.api.Test;

class PortalEntryReceiptStateMapperTest {
    @Test
    void savesAndLoadsPortalEntryReceipt() {
        CompoundTag tag = new CompoundTag();
        PortalEntryReceipt originalReceipt = new PortalEntryReceipt(
            new PortalReturnState("portal", "minecraft:overworld", 11, 70, 11),
            new TeleportContext("portal", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(11, 70, 11, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        PortalEntryReceiptStateMapper.save(tag, originalReceipt);
        PortalEntryReceipt loadedReceipt = PortalEntryReceiptStateMapper.load(tag).orElseThrow();

        assertEquals(originalReceipt, loadedReceipt);
    }

    @Test
    void clearsStoredPortalEntryReceipt() {
        CompoundTag tag = new CompoundTag();
        PortalEntryReceiptStateMapper.save(tag, new PortalEntryReceipt(
            new PortalReturnState("portal", "minecraft:overworld", 11, 70, 11),
            new TeleportContext("portal", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(11, 70, 11, PortalWorldIndex.PortalPlacement.AXIS_X)
        ));

        assertTrue(tag.contains("CavernPortalEntryReceipt", Tag.TAG_COMPOUND));

        PortalEntryReceiptStateMapper.clear(tag);

        assertFalse(tag.contains("CavernPortalEntryReceipt", Tag.TAG_COMPOUND));
    }

    @Test
    void loadReturnsEmptyWhenTagMissing() {
        CompoundTag tag = new CompoundTag();

        assertFalse(PortalEntryReceiptStateMapper.load(tag).isPresent());
    }
}

