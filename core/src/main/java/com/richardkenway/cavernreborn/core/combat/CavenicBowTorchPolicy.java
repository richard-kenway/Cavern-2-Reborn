package com.richardkenway.cavernreborn.core.combat;

public final class CavenicBowTorchPolicy {
    public static final float VELOCITY_MULTIPLIER = 1.0F;
    public static final double BASE_DAMAGE_MULTIPLIER = 1.0D;
    public static final int EXTRA_DURABILITY_COST = 0;

    private CavenicBowTorchPolicy() {
    }

    public static boolean shouldMarkShot(CavenicBowMode mode, boolean creativeShooter, boolean hasTorchAmmo) {
        return mode == CavenicBowMode.TORCH && (creativeShooter || hasTorchAmmo);
    }
}
