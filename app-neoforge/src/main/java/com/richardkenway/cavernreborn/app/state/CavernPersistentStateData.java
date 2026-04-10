package com.richardkenway.cavernreborn.app.state;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.mojang.logging.LogUtils;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.data.state.CavernStateMappers;
import com.richardkenway.cavernreborn.data.state.PortalReturnStateData;
import com.richardkenway.cavernreborn.data.state.PortalWorldIndexData;

import org.slf4j.Logger;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

public final class CavernPersistentStateData extends SavedData {
    public static final String FILE_ID = "cavernreborn_control_plane";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PLAYER_RETURN_STATES_TAG = "PlayerReturnStates";
    private static final String WORLD_PORTAL_INDICES_TAG = "WorldPortalIndices";
    private static final String PLAYER_ID_TAG = "PlayerId";
    private static final String WORLD_KEY_TAG = "WorldKey";
    private static final String PORTAL_KEY_TAG = "PortalKey";
    private static final String RETURN_DIMENSION_ID_TAG = "ReturnDimensionId";
    private static final String RETURN_X_TAG = "ReturnX";
    private static final String RETURN_Y_TAG = "ReturnY";
    private static final String RETURN_Z_TAG = "ReturnZ";
    private static final String PORTALS_TAG = "Portals";
    private static final String PLACEMENTS_TAG = "Placements";
    private static final String X_TAG = "X";
    private static final String Y_TAG = "Y";
    private static final String Z_TAG = "Z";
    private static final String AXIS_TAG = "Axis";

    private final Map<UUID, PortalReturnState> playerReturnStates;
    private final Map<String, PortalWorldIndex> worldPortalIndices;

    public CavernPersistentStateData() {
        this(new LinkedHashMap<>(), new LinkedHashMap<>());
    }

    private CavernPersistentStateData(
        Map<UUID, PortalReturnState> playerReturnStates,
        Map<String, PortalWorldIndex> worldPortalIndices
    ) {
        this.playerReturnStates = new LinkedHashMap<>(playerReturnStates);
        this.worldPortalIndices = new LinkedHashMap<>(worldPortalIndices);
    }

    static SavedData.Factory<CavernPersistentStateData> factory() {
        return new SavedData.Factory<>(CavernPersistentStateData::new, CavernPersistentStateData::load, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);
    }

    static CavernPersistentStateData load(CompoundTag tag, HolderLookup.Provider registries) {
        Map<UUID, PortalReturnState> playerReturnStates = new LinkedHashMap<>();
        Map<String, PortalWorldIndex> worldPortalIndices = new LinkedHashMap<>();

        ListTag playerStatesTag = tag.getList(PLAYER_RETURN_STATES_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : playerStatesTag) {
            if (!(entry instanceof CompoundTag playerStateTag)) {
                continue;
            }

            tryLoadPlayerReturnState(playerStateTag).ifPresent(state -> playerReturnStates.put(state.playerId(), state.returnState()));
        }

        ListTag worldIndicesTag = tag.getList(WORLD_PORTAL_INDICES_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : worldIndicesTag) {
            if (!(entry instanceof CompoundTag worldIndexTag)) {
                continue;
            }

            tryLoadWorldPortalIndex(worldIndexTag).ifPresent(state -> worldPortalIndices.put(state.worldKey(), state.worldIndex()));
        }

        return new CavernPersistentStateData(playerReturnStates, worldPortalIndices);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag playerStatesTag = new ListTag();
        playerReturnStates.forEach((playerId, returnState) -> playerStatesTag.add(serializePlayerReturnState(playerId, returnState)));
        tag.put(PLAYER_RETURN_STATES_TAG, playerStatesTag);

        ListTag worldIndicesTag = new ListTag();
        worldPortalIndices.forEach((worldKey, worldIndex) -> worldIndicesTag.add(serializeWorldPortalIndex(worldKey, worldIndex)));
        tag.put(WORLD_PORTAL_INDICES_TAG, worldIndicesTag);

        return tag;
    }

    public Optional<PortalReturnState> loadPlayerReturnState(UUID playerId) {
        return Optional.ofNullable(playerReturnStates.get(playerId));
    }

    public void savePlayerReturnState(UUID playerId, PortalReturnState returnState) {
        playerReturnStates.put(playerId, returnState);
        setDirty();
    }

    public void clearPlayerReturnState(UUID playerId) {
        if (playerReturnStates.remove(playerId) != null) {
            setDirty();
        }
    }

    public PortalWorldIndex loadWorldPortalIndex(String worldKey) {
        return worldPortalIndices.getOrDefault(worldKey, PortalWorldIndex.empty());
    }

    public void saveWorldPortalIndex(String worldKey, PortalWorldIndex worldIndex) {
        if (worldIndex.portalsByKey().isEmpty()) {
            if (worldPortalIndices.remove(worldKey) != null) {
                setDirty();
            }
            return;
        }

        worldPortalIndices.put(worldKey, worldIndex);
        setDirty();
    }

    private static CompoundTag serializePlayerReturnState(UUID playerId, PortalReturnState returnState) {
        PortalReturnStateData stateData = CavernStateMappers.toData(returnState);
        CompoundTag tag = new CompoundTag();
        tag.putString(PLAYER_ID_TAG, playerId.toString());
        tag.putString(PORTAL_KEY_TAG, stateData.portalKey());
        tag.putString(RETURN_DIMENSION_ID_TAG, stateData.returnDimensionId());
        tag.putInt(RETURN_X_TAG, stateData.returnX());
        tag.putInt(RETURN_Y_TAG, stateData.returnY());
        tag.putInt(RETURN_Z_TAG, stateData.returnZ());
        return tag;
    }

    private static Optional<PlayerReturnStateEntry> tryLoadPlayerReturnState(CompoundTag tag) {
        try {
            UUID playerId = UUID.fromString(tag.getString(PLAYER_ID_TAG));
            PortalReturnStateData stateData = new PortalReturnStateData(
                tag.getString(PORTAL_KEY_TAG),
                tag.getString(RETURN_DIMENSION_ID_TAG),
                tag.getInt(RETURN_X_TAG),
                tag.getInt(RETURN_Y_TAG),
                tag.getInt(RETURN_Z_TAG)
            );
            return Optional.of(new PlayerReturnStateEntry(playerId, CavernStateMappers.fromData(stateData)));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent player return-state entry", exception);
            return Optional.empty();
        }
    }

    private static CompoundTag serializeWorldPortalIndex(String worldKey, PortalWorldIndex worldIndex) {
        PortalWorldIndexData indexData = CavernStateMappers.toData(worldIndex);
        CompoundTag tag = new CompoundTag();
        tag.putString(WORLD_KEY_TAG, worldKey);

        ListTag portalsTag = new ListTag();
        indexData.portalsByKey().forEach((portalKey, placements) -> {
            CompoundTag portalTag = new CompoundTag();
            portalTag.putString(PORTAL_KEY_TAG, portalKey);

            ListTag placementsTag = new ListTag();
            for (PortalWorldIndexData.PortalPlacementData placement : placements) {
                CompoundTag placementTag = new CompoundTag();
                placementTag.putInt(X_TAG, placement.x());
                placementTag.putInt(Y_TAG, placement.y());
                placementTag.putInt(Z_TAG, placement.z());
                placementTag.putString(AXIS_TAG, placement.axis());
                placementsTag.add(placementTag);
            }

            portalTag.put(PLACEMENTS_TAG, placementsTag);
            portalsTag.add(portalTag);
        });

        tag.put(PORTALS_TAG, portalsTag);
        return tag;
    }

    private static Optional<WorldPortalIndexEntry> tryLoadWorldPortalIndex(CompoundTag tag) {
        try {
            String worldKey = tag.getString(WORLD_KEY_TAG);
            Map<String, java.util.Set<PortalWorldIndexData.PortalPlacementData>> portalsByKey = new LinkedHashMap<>();
            ListTag portalsTag = tag.getList(PORTALS_TAG, Tag.TAG_COMPOUND);
            for (Tag portalEntry : portalsTag) {
                if (!(portalEntry instanceof CompoundTag portalTag)) {
                    continue;
                }

                String portalKey = portalTag.getString(PORTAL_KEY_TAG);
                java.util.Set<PortalWorldIndexData.PortalPlacementData> placements = new java.util.LinkedHashSet<>();
                ListTag placementsTag = portalTag.getList(PLACEMENTS_TAG, Tag.TAG_COMPOUND);
                for (Tag placementEntry : placementsTag) {
                    if (!(placementEntry instanceof CompoundTag placementTag)) {
                        continue;
                    }

                    placements.add(new PortalWorldIndexData.PortalPlacementData(
                        placementTag.getInt(X_TAG),
                        placementTag.getInt(Y_TAG),
                        placementTag.getInt(Z_TAG),
                        placementTag.contains(AXIS_TAG, Tag.TAG_STRING)
                            ? placementTag.getString(AXIS_TAG)
                            : PortalWorldIndex.PortalPlacement.AXIS_X
                    ));
                }

                portalsByKey.put(portalKey, placements);
            }

            PortalWorldIndexData indexData = new PortalWorldIndexData(portalsByKey);
            return Optional.of(new WorldPortalIndexEntry(worldKey, CavernStateMappers.fromData(indexData)));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent world portal-index entry", exception);
            return Optional.empty();
        }
    }

    private record PlayerReturnStateEntry(UUID playerId, PortalReturnState returnState) {
    }

    private record WorldPortalIndexEntry(String worldKey, PortalWorldIndex worldIndex) {
    }
}
