package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import com.mojang.logging.LogUtils;
import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;
import com.richardkenway.cavernreborn.data.state.CavernStateMappers;
import com.richardkenway.cavernreborn.data.state.PortalReturnStateData;
import com.richardkenway.cavernreborn.data.state.TeleportContextData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.slf4j.Logger;

final class PortalEntryReceiptStateMapper {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String ENTRY_RECEIPT_ROOT_TAG = "CavernPortalEntryReceipt";
    private static final String RETURN_STATE_TAG = "ReturnState";
    private static final String TELEPORT_CONTEXT_TAG = "TeleportContext";
    private static final String SOURCE_PORTAL_PLACEMENT_TAG = "SourcePortalPlacement";

    private static final String PORTAL_KEY_TAG = "PortalKey";
    private static final String RETURN_DIMENSION_ID_TAG = "ReturnDimensionId";
    private static final String RETURN_X_TAG = "ReturnX";
    private static final String RETURN_Y_TAG = "ReturnY";
    private static final String RETURN_Z_TAG = "ReturnZ";

    private static final String ENTRY_OFFSET_X_TAG = "EntryOffsetX";
    private static final String ENTRY_OFFSET_Y_TAG = "EntryOffsetY";
    private static final String ENTRY_OFFSET_Z_TAG = "EntryOffsetZ";
    private static final String APPROACH_FACING_TAG = "ApproachFacing";

    private static final String X_TAG = "X";
    private static final String Y_TAG = "Y";
    private static final String Z_TAG = "Z";
    private static final String AXIS_TAG = "Axis";

    private PortalEntryReceiptStateMapper() {
    }

    static void save(CompoundTag entityTag, PortalEntryReceipt entryReceipt) {
        Objects.requireNonNull(entityTag, "entityTag");
        Objects.requireNonNull(entryReceipt, "entryReceipt");

        entityTag.put(ENTRY_RECEIPT_ROOT_TAG, toTag(entryReceipt));
    }

    static Optional<PortalEntryReceipt> load(CompoundTag entityTag) {
        Objects.requireNonNull(entityTag, "entityTag");

        if (!entityTag.contains(ENTRY_RECEIPT_ROOT_TAG, Tag.TAG_COMPOUND)) {
            return Optional.empty();
        }

        CompoundTag entryReceiptTag = entityTag.getCompound(ENTRY_RECEIPT_ROOT_TAG);
        return fromTag(entryReceiptTag);
    }

    static void clear(CompoundTag entityTag) {
        Objects.requireNonNull(entityTag, "entityTag");
        entityTag.remove(ENTRY_RECEIPT_ROOT_TAG);
    }

    private static CompoundTag toTag(PortalEntryReceipt entryReceipt) {
        Objects.requireNonNull(entryReceipt, "entryReceipt");

        CompoundTag tag = new CompoundTag();
        tag.put(RETURN_STATE_TAG, toTag(entryReceipt.returnState()));
        tag.put(TELEPORT_CONTEXT_TAG, toTag(entryReceipt.teleportContext()));
        tag.put(SOURCE_PORTAL_PLACEMENT_TAG, toTag(entryReceipt.sourcePortalPlacement()));
        return tag;
    }

    private static CompoundTag toTag(PortalReturnState returnState) {
        PortalReturnStateData stateData = CavernStateMappers.toData(returnState);
        CompoundTag tag = new CompoundTag();
        tag.putString(PORTAL_KEY_TAG, stateData.portalKey());
        tag.putString(RETURN_DIMENSION_ID_TAG, stateData.returnDimensionId());
        tag.putInt(RETURN_X_TAG, stateData.returnX());
        tag.putInt(RETURN_Y_TAG, stateData.returnY());
        tag.putInt(RETURN_Z_TAG, stateData.returnZ());
        return tag;
    }

    private static CompoundTag toTag(TeleportContext teleportContext) {
        TeleportContextData contextData = CavernStateMappers.toData(teleportContext);
        CompoundTag tag = new CompoundTag();
        tag.putString(PORTAL_KEY_TAG, contextData.portalKey());
        tag.putDouble(ENTRY_OFFSET_X_TAG, contextData.entryOffsetX());
        tag.putDouble(ENTRY_OFFSET_Y_TAG, contextData.entryOffsetY());
        tag.putDouble(ENTRY_OFFSET_Z_TAG, contextData.entryOffsetZ());
        tag.putString(APPROACH_FACING_TAG, contextData.approachFacing());
        return tag;
    }

    private static CompoundTag toTag(PortalWorldIndex.PortalPlacement portalPlacement) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(X_TAG, portalPlacement.x());
        tag.putInt(Y_TAG, portalPlacement.y());
        tag.putInt(Z_TAG, portalPlacement.z());
        tag.putString(AXIS_TAG, portalPlacement.axis());
        return tag;
    }

    private static Optional<PortalEntryReceipt> fromTag(CompoundTag tag) {
        try {
            PortalReturnState returnState = fromTagPortalReturnState(tag.getCompound(RETURN_STATE_TAG));
            TeleportContext teleportContext = fromTagTeleportContext(tag.getCompound(TELEPORT_CONTEXT_TAG));
            PortalWorldIndex.PortalPlacement sourcePortalPlacement = fromTagPortalPlacement(tag.getCompound(SOURCE_PORTAL_PLACEMENT_TAG));
            return Optional.of(new PortalEntryReceipt(returnState, teleportContext, sourcePortalPlacement));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid entity portal-entry receipt tag", exception);
            return Optional.empty();
        }
    }

    private static PortalReturnState fromTagPortalReturnState(CompoundTag tag) {
        PortalReturnStateData stateData = new PortalReturnStateData(
            tag.getString(PORTAL_KEY_TAG),
            tag.getString(RETURN_DIMENSION_ID_TAG),
            tag.getInt(RETURN_X_TAG),
            tag.getInt(RETURN_Y_TAG),
            tag.getInt(RETURN_Z_TAG)
        );
        return CavernStateMappers.fromData(stateData);
    }

    private static TeleportContext fromTagTeleportContext(CompoundTag tag) {
        TeleportContextData contextData = new TeleportContextData(
            tag.getString(PORTAL_KEY_TAG),
            tag.getDouble(ENTRY_OFFSET_X_TAG),
            tag.getDouble(ENTRY_OFFSET_Y_TAG),
            tag.getDouble(ENTRY_OFFSET_Z_TAG),
            tag.getString(APPROACH_FACING_TAG)
        );
        return CavernStateMappers.fromData(contextData);
    }

    private static PortalWorldIndex.PortalPlacement fromTagPortalPlacement(CompoundTag tag) {
        return new PortalWorldIndex.PortalPlacement(
            tag.getInt(X_TAG),
            tag.getInt(Y_TAG),
            tag.getInt(Z_TAG),
            tag.contains(AXIS_TAG, Tag.TAG_STRING) ? tag.getString(AXIS_TAG) : PortalWorldIndex.PortalPlacement.AXIS_X
        );
    }
}
