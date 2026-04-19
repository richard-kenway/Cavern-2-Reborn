package com.richardkenway.cavernreborn.app.progression;

import com.richardkenway.cavernreborn.core.progression.CavernServiceRequestResult;

public final class CavernServiceRequestFeedbackFormatter {
    private CavernServiceRequestFeedbackFormatter() {
    }

    public static String format(CavernServiceRequestResult result) {
        if (result.granted()) {
            return String.format(
                "Service used: %s",
                result.service().label()
            );
        }
        if (result.locked()) {
            return String.format(
                "%s is locked (requires %s rank)",
                result.service().label(),
                result.service().requiredRank().id()
            );
        }
        if (result.onCooldown()) {
            return String.format(
                "%s is on cooldown",
                result.service().label()
            );
        }
        if (result.alreadyUsed()) {
            return String.format(
                "%s has already been used",
                result.service().label()
            );
        }
        return String.format(
            "Cannot use %s",
            result.service().label()
        );
    }
}
