package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;

public final class CavernProgressionConsequences {
    public static final int MINERS_INSIGHT_BONUS_EXPERIENCE = 1;

    private CavernProgressionConsequences() {
    }

    public static int bonusExperienceFor(CavernProgressionSnapshot snapshot) {
        CavernProgressionSnapshot normalizedSnapshot = Objects.requireNonNull(snapshot, "snapshot");
        return normalizedSnapshot.hasUnlocked(CavernProgressionUnlock.MINERS_INSIGHT)
            ? MINERS_INSIGHT_BONUS_EXPERIENCE
            : 0;
    }
}
