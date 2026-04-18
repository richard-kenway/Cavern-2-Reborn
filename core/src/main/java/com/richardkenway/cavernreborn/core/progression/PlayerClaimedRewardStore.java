package com.richardkenway.cavernreborn.core.progression;

import java.util.UUID;

public interface PlayerClaimedRewardStore {
    CavernPlayerRewardState load(UUID playerId);

    void save(CavernPlayerRewardState rewardState);

    void clear(UUID playerId);
}
