package com.richardkenway.cavernreborn.app.portal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class PortalInteractionCooldowns {
    private final Map<String, Long> cooldownsByInteractionKey = new HashMap<>();

    public boolean isOnCooldown(String interactionKey, long gameTime) {
        Objects.requireNonNull(interactionKey, "interactionKey");

        Long cooldownUntil = cooldownsByInteractionKey.get(interactionKey);
        if (cooldownUntil == null) {
            return false;
        }

        if (cooldownUntil <= gameTime) {
            cooldownsByInteractionKey.remove(interactionKey);
            return false;
        }

        return true;
    }

    public void markCooldown(String interactionKey, long gameTime, long durationTicks) {
        Objects.requireNonNull(interactionKey, "interactionKey");
        if (durationTicks <= 0L) {
            throw new IllegalArgumentException("durationTicks must be positive");
        }

        cooldownsByInteractionKey.put(interactionKey, gameTime + durationTicks);
    }
}
