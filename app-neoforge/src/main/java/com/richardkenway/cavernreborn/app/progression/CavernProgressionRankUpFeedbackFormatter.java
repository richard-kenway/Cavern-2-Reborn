package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionConsequences;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUnlock;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUpdateResult;

public final class CavernProgressionRankUpFeedbackFormatter {
    private CavernProgressionRankUpFeedbackFormatter() {
    }

    public static Optional<String> formatActionBar(CavernProgressionUpdateResult update) {
        CavernProgressionUpdateResult normalizedUpdate = Objects.requireNonNull(update, "update");
        if (!normalizedUpdate.rankAdvanced()) {
            return Optional.empty();
        }

        return Optional.of(
            "CAVERN rank up: " + normalizedUpdate.currentSnapshot().rank().id()
                + " (" + normalizedUpdate.currentSnapshot().progressionScore() + " score)"
        );
    }

    public static Optional<String> formatUnlockMessage(CavernProgressionUpdateResult update) {
        CavernProgressionUpdateResult normalizedUpdate = Objects.requireNonNull(update, "update");
        if (normalizedUpdate.unlockJustReached(CavernProgressionUnlock.MINERS_INSIGHT)) {
            return Optional.of(
                "Miner's Insight unlocked: counted ores in CAVERN now grant +"
                    + CavernProgressionConsequences.MINERS_INSIGHT_BONUS_EXPERIENCE
                    + " bonus XP."
            );
        }
        if (normalizedUpdate.unlockJustReached(CavernProgressionUnlock.MINING_ASSIST)) {
            return Optional.of("Mining Assist unlocked: hexcite_pickaxe now breaks up to 6 extra connected ores in CAVERN.");
        }
        return Optional.empty();
    }
}
