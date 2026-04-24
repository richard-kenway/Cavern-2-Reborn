package com.richardkenway.cavernreborn.core.combat;

public final class CavenicSwordHitPolicy {
    private CavenicSwordHitPolicy() {
    }

    public static boolean shouldResetCooldown(int invulnerableTime, int hurtTime) {
        return invulnerableTime > 0 || hurtTime > 0;
    }
}
