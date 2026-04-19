package com.richardkenway.cavernreborn.app.state;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.mojang.logging.LogUtils;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerProgressionState;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerRewardState;
import com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState;
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
    private static final String PLAYER_MINING_PROGRESSIONS_TAG = "PlayerMiningProgressions";
    private static final String PLAYER_REWARD_STATES_TAG = "PlayerRewardStates";
    private static final String PLAYER_SERVICE_STATES_TAG = "PlayerServiceStates";
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
    private static final String COUNTED_BLOCKS_TAG = "CountedBlocks";
    private static final String PROGRESSION_SCORE_TAG = "ProgressionScore";
    private static final String MINED_BLOCKS_TAG = "MinedBlocks";
    private static final String BLOCK_ID_TAG = "BlockId";
    private static final String COUNT_TAG = "Count";
    private static final String CLAIMED_REWARDS_TAG = "ClaimedRewards";
    private static final String REWARD_ID_TAG = "RewardId";
    private static final String SERVICE_ID_TAG = "ServiceId";
    private static final String LAST_USED_MILLIS_TAG = "LastUsedMillis";
    private static final String SERVICE_USAGES_TAG = "ServiceUsages";

    private final Map<UUID, PortalReturnState> playerReturnStates;
    private final Map<UUID, CavernPlayerProgressionState> playerMiningProgressions;
    private final Map<UUID, CavernPlayerRewardState> playerRewardStates;
    private final Map<UUID, CavernPlayerServiceState> playerServiceStates;
    private final Map<String, PortalWorldIndex> worldPortalIndices;

    public CavernPersistentStateData() {
        this(new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashMap<>());
    }

    private CavernPersistentStateData(
        Map<UUID, PortalReturnState> playerReturnStates,
        Map<UUID, CavernPlayerProgressionState> playerMiningProgressions,
        Map<UUID, CavernPlayerRewardState> playerRewardStates,
        Map<UUID, CavernPlayerServiceState> playerServiceStates,
        Map<String, PortalWorldIndex> worldPortalIndices
    ) {
        this.playerReturnStates = new LinkedHashMap<>(playerReturnStates);
        this.playerMiningProgressions = new LinkedHashMap<>(playerMiningProgressions);
        this.playerRewardStates = new LinkedHashMap<>(playerRewardStates);
        this.playerServiceStates = new LinkedHashMap<>(playerServiceStates);
        this.worldPortalIndices = new LinkedHashMap<>(worldPortalIndices);
    }

    static SavedData.Factory<CavernPersistentStateData> factory() {
        return new SavedData.Factory<>(CavernPersistentStateData::new, CavernPersistentStateData::load, DataFixTypes.SAVED_DATA_COMMAND_STORAGE);
    }

    static CavernPersistentStateData load(CompoundTag tag, HolderLookup.Provider registries) {
        Map<UUID, PortalReturnState> playerReturnStates = new LinkedHashMap<>();
        Map<UUID, CavernPlayerProgressionState> playerMiningProgressions = new LinkedHashMap<>();
        Map<UUID, CavernPlayerRewardState> playerRewardStates = new LinkedHashMap<>();
        Map<UUID, CavernPlayerServiceState> playerServiceStates = new LinkedHashMap<>();
        Map<String, PortalWorldIndex> worldPortalIndices = new LinkedHashMap<>();

        ListTag playerStatesTag = tag.getList(PLAYER_RETURN_STATES_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : playerStatesTag) {
            if (!(entry instanceof CompoundTag playerStateTag)) {
                continue;
            }

            tryLoadPlayerReturnState(playerStateTag).ifPresent(state -> playerReturnStates.put(state.playerId(), state.returnState()));
        }

        ListTag playerMiningProgressionsTag = tag.getList(PLAYER_MINING_PROGRESSIONS_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : playerMiningProgressionsTag) {
            if (!(entry instanceof CompoundTag playerProgressionTag)) {
                continue;
            }

            tryLoadPlayerMiningProgression(playerProgressionTag)
                .ifPresent(state -> playerMiningProgressions.put(state.playerId(), state.progressionState()));
        }

        ListTag playerRewardStatesTag = tag.getList(PLAYER_REWARD_STATES_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : playerRewardStatesTag) {
            if (!(entry instanceof CompoundTag playerRewardStateTag)) {
                continue;
            }

            tryLoadPlayerRewardState(playerRewardStateTag)
                .ifPresent(state -> playerRewardStates.put(state.playerId(), state.rewardState()));
        }

        ListTag playerServiceStatesTag = tag.getList(PLAYER_SERVICE_STATES_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : playerServiceStatesTag) {
            if (!(entry instanceof CompoundTag playerServiceStateTag)) {
                continue;
            }

            tryLoadPlayerServiceState(playerServiceStateTag)
                .ifPresent(state -> playerServiceStates.put(state.playerId(), state.serviceState()));
        }

        ListTag worldIndicesTag = tag.getList(WORLD_PORTAL_INDICES_TAG, Tag.TAG_COMPOUND);
        for (Tag entry : worldIndicesTag) {
            if (!(entry instanceof CompoundTag worldIndexTag)) {
                continue;
            }

            tryLoadWorldPortalIndex(worldIndexTag).ifPresent(state -> worldPortalIndices.put(state.worldKey(), state.worldIndex()));
        }

        return new CavernPersistentStateData(playerReturnStates, playerMiningProgressions, playerRewardStates, playerServiceStates, worldPortalIndices);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag playerStatesTag = new ListTag();
        playerReturnStates.forEach((playerId, returnState) -> playerStatesTag.add(serializePlayerReturnState(playerId, returnState)));
        tag.put(PLAYER_RETURN_STATES_TAG, playerStatesTag);

        ListTag playerMiningProgressionsTag = new ListTag();
        playerMiningProgressions.forEach((playerId, progressionState) ->
            playerMiningProgressionsTag.add(serializePlayerMiningProgression(playerId, progressionState))
        );
        tag.put(PLAYER_MINING_PROGRESSIONS_TAG, playerMiningProgressionsTag);

        ListTag playerRewardStatesTag = new ListTag();
        playerRewardStates.forEach((playerId, rewardState) -> playerRewardStatesTag.add(serializePlayerRewardState(playerId, rewardState)));
        tag.put(PLAYER_REWARD_STATES_TAG, playerRewardStatesTag);

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

    public CavernPlayerProgressionState loadPlayerMiningProgression(UUID playerId) {
        return playerMiningProgressions.getOrDefault(playerId, CavernPlayerProgressionState.empty(playerId));
    }

    public void savePlayerMiningProgression(CavernPlayerProgressionState progressionState) {
        if (progressionState.isEmpty()) {
            clearPlayerMiningProgression(progressionState.playerId());
            return;
        }

        playerMiningProgressions.put(progressionState.playerId(), progressionState);
        setDirty();
    }

    public void clearPlayerMiningProgression(UUID playerId) {
        if (playerMiningProgressions.remove(playerId) != null) {
            setDirty();
        }
    }

    public CavernPlayerRewardState loadPlayerRewardState(UUID playerId) {
        return playerRewardStates.getOrDefault(playerId, CavernPlayerRewardState.empty(playerId));
    }

    public void savePlayerRewardState(CavernPlayerRewardState rewardState) {
        if (rewardState.isEmpty()) {
            clearPlayerRewardState(rewardState.playerId());
            return;
        }

        playerRewardStates.put(rewardState.playerId(), rewardState);
        setDirty();
    }

    public void clearPlayerRewardState(UUID playerId) {
        if (playerRewardStates.remove(playerId) != null) {
            setDirty();
        }
    }

    public CavernPlayerServiceState loadPlayerServiceState(UUID playerId) {
        return playerServiceStates.getOrDefault(Objects.requireNonNull(playerId, "playerId"), CavernPlayerServiceState.empty(playerId));
    }

    public void savePlayerServiceState(CavernPlayerServiceState serviceState) {
        if (serviceState.isEmpty()) {
            playerServiceStates.remove(serviceState.playerId());
            return;
        }
        playerServiceStates.put(serviceState.playerId(), serviceState);
    }

    public void clearPlayerServiceState(UUID playerId) {
        playerServiceStates.remove(Objects.requireNonNull(playerId, "playerId"));
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

    private static CompoundTag serializePlayerMiningProgression(UUID playerId, CavernPlayerProgressionState progressionState) {
        CompoundTag tag = new CompoundTag();
        tag.putString(PLAYER_ID_TAG, playerId.toString());
        tag.putInt(COUNTED_BLOCKS_TAG, progressionState.countedBlocks());
        tag.putInt(PROGRESSION_SCORE_TAG, progressionState.progressionScore());

        ListTag minedBlocksTag = new ListTag();
        progressionState.minedBlocksById().forEach((blockId, count) -> {
            CompoundTag blockCountTag = new CompoundTag();
            blockCountTag.putString(BLOCK_ID_TAG, blockId);
            blockCountTag.putInt(COUNT_TAG, count);
            minedBlocksTag.add(blockCountTag);
        });
        tag.put(MINED_BLOCKS_TAG, minedBlocksTag);
        return tag;
    }

    private static Optional<PlayerMiningProgressionEntry> tryLoadPlayerMiningProgression(CompoundTag tag) {
        try {
            UUID playerId = UUID.fromString(tag.getString(PLAYER_ID_TAG));
            Map<String, Integer> minedBlocksById = new LinkedHashMap<>();
            ListTag minedBlocksTag = tag.getList(MINED_BLOCKS_TAG, Tag.TAG_COMPOUND);
            for (Tag entry : minedBlocksTag) {
                if (!(entry instanceof CompoundTag blockCountTag)) {
                    continue;
                }

                String blockId = requireText(blockCountTag.getString(BLOCK_ID_TAG), BLOCK_ID_TAG);
                int count = blockCountTag.getInt(COUNT_TAG);
                if (count <= 0) {
                    throw new IllegalArgumentException(COUNT_TAG + " must be positive");
                }
                minedBlocksById.put(blockId, count);
            }

            CavernPlayerProgressionState progressionState = new CavernPlayerProgressionState(
                playerId,
                tag.getInt(COUNTED_BLOCKS_TAG),
                tag.getInt(PROGRESSION_SCORE_TAG),
                minedBlocksById
            );
            return Optional.of(new PlayerMiningProgressionEntry(playerId, progressionState));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent player mining-progression entry", exception);
            return Optional.empty();
        }
    }

    private static CompoundTag serializePlayerRewardState(UUID playerId, CavernPlayerRewardState rewardState) {
        CompoundTag tag = new CompoundTag();
        tag.putString(PLAYER_ID_TAG, playerId.toString());

        ListTag claimedRewardsTag = new ListTag();
        rewardState.claimedRewardIds().forEach(rewardId -> {
            CompoundTag rewardTag = new CompoundTag();
            rewardTag.putString(REWARD_ID_TAG, rewardId);
            claimedRewardsTag.add(rewardTag);
        });
        tag.put(CLAIMED_REWARDS_TAG, claimedRewardsTag);
        return tag;
    }

    private static Optional<PlayerRewardStateEntry> tryLoadPlayerRewardState(CompoundTag tag) {
        try {
            UUID playerId = UUID.fromString(tag.getString(PLAYER_ID_TAG));
            Set<String> claimedRewardIds = new LinkedHashSet<>();
            ListTag claimedRewardsTag = tag.getList(CLAIMED_REWARDS_TAG, Tag.TAG_COMPOUND);
            for (Tag entry : claimedRewardsTag) {
                if (!(entry instanceof CompoundTag rewardTag)) {
                    continue;
                }

                claimedRewardIds.add(requireText(rewardTag.getString(REWARD_ID_TAG), REWARD_ID_TAG));
            }

            return Optional.of(new PlayerRewardStateEntry(playerId, new CavernPlayerRewardState(playerId, claimedRewardIds)));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent player reward-state entry", exception);
            return Optional.empty();
        }
    }

    private static CompoundTag serializePlayerServiceState(UUID playerId, CavernPlayerServiceState serviceState) {
        CompoundTag tag = new CompoundTag();
        tag.putString(PLAYER_ID_TAG, playerId.toString());

        ListTag serviceUsagesTag = new ListTag();
        serviceState.lastUsedServiceTimestamps().forEach((serviceId, lastUsedMillis) -> {
            CompoundTag serviceTag = new CompoundTag();
            serviceTag.putString(SERVICE_ID_TAG, serviceId);
            serviceTag.putLong(LAST_USED_MILLIS_TAG, lastUsedMillis);
            serviceUsagesTag.add(serviceTag);
        });
        tag.put(SERVICE_USAGES_TAG, serviceUsagesTag);
        return tag;
    }

    private static Optional<PlayerServiceStateEntry> tryLoadPlayerServiceState(CompoundTag tag) {
        try {
            UUID playerId = UUID.fromString(tag.getString(PLAYER_ID_TAG));
            Map<String, Long> serviceTimestamps = new LinkedHashMap<>();
            ListTag serviceUsagesTag = tag.getList(SERVICE_USAGES_TAG, Tag.TAG_COMPOUND);
            for (Tag entry : serviceUsagesTag) {
                if (!(entry instanceof CompoundTag serviceTag)) {
                    continue;
                }

                String serviceId = requireText(serviceTag.getString(SERVICE_ID_TAG), SERVICE_ID_TAG);
                long lastUsedMillis = serviceTag.getLong(LAST_USED_MILLIS_TAG);
                serviceTimestamps.put(serviceId, lastUsedMillis);
            }

            return Optional.of(new PlayerServiceStateEntry(playerId, new CavernPlayerServiceState(playerId, serviceTimestamps)));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent player service-state entry", exception);
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
            Map<String, java.util.Set<PortalWorldIndex.PortalPlacement>> portalsByKey = new LinkedHashMap<>();
            ListTag portalsTag = tag.getList(PORTALS_TAG, Tag.TAG_COMPOUND);
            for (Tag portalEntry : portalsTag) {
                if (!(portalEntry instanceof CompoundTag portalTag)) {
                    continue;
                }

                String portalKey = portalTag.getString(PORTAL_KEY_TAG);
                java.util.Set<PortalWorldIndex.PortalPlacement> placements = new java.util.LinkedHashSet<>();
                ListTag placementsTag = portalTag.getList(PLACEMENTS_TAG, Tag.TAG_COMPOUND);
                for (Tag placementEntry : placementsTag) {
                    if (!(placementEntry instanceof CompoundTag placementTag)) {
                        continue;
                    }

                    tryLoadPortalPlacement(placementTag).ifPresent(placements::add);
                }

                try {
                    String normalizedPortalKey = requireText(portalKey, PORTAL_KEY_TAG);
                    if (!placements.isEmpty()) {
                        portalsByKey.put(normalizedPortalKey, placements);
                    }
                } catch (RuntimeException exception) {
                    LOGGER.warn("Skipping invalid persistent portal-key entry for world portal index", exception);
                }
            }

            return Optional.of(new WorldPortalIndexEntry(requireText(worldKey, WORLD_KEY_TAG), new PortalWorldIndex(portalsByKey)));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent world portal-index entry", exception);
            return Optional.empty();
        }
    }

    private static Optional<PortalWorldIndex.PortalPlacement> tryLoadPortalPlacement(CompoundTag placementTag) {
        try {
            return Optional.of(new PortalWorldIndex.PortalPlacement(
                placementTag.getInt(X_TAG),
                placementTag.getInt(Y_TAG),
                placementTag.getInt(Z_TAG),
                placementTag.contains(AXIS_TAG, Tag.TAG_STRING)
                    ? placementTag.getString(AXIS_TAG)
                    : PortalWorldIndex.PortalPlacement.AXIS_X
            ));
        } catch (RuntimeException exception) {
            LOGGER.warn("Skipping invalid persistent portal placement entry", exception);
            return Optional.empty();
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }

    private record PlayerReturnStateEntry(UUID playerId, PortalReturnState returnState) {
    }

    private record PlayerMiningProgressionEntry(UUID playerId, CavernPlayerProgressionState progressionState) {
    }

    private record PlayerRewardStateEntry(UUID playerId, CavernPlayerRewardState rewardState) {
    }

    private record PlayerServiceStateEntry(UUID playerId, CavernPlayerServiceState serviceState) {
    }

    private record WorldPortalIndexEntry(String worldKey, PortalWorldIndex worldIndex) {
    }
}
