package com.richardkenway.cavernreborn.core.combat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CavenicBowTorchPolicyTest {
    @Test
    void torchConstantsRemainPinned() {
        assertTrue(CavenicBowTorchPolicy.VELOCITY_MULTIPLIER == 1.0F);
        assertTrue(CavenicBowTorchPolicy.BASE_DAMAGE_MULTIPLIER == 1.0D);
        assertTrue(CavenicBowTorchPolicy.EXTRA_DURABILITY_COST == 0);
    }

    @Test
    void onlyTorchModeIsEligible() {
        assertFalse(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.NORMAL, false, true));
        assertFalse(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.RAPID, false, true));
        assertFalse(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.SNIPE, false, true));
        assertTrue(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.TORCH, false, true));
    }

    @Test
    void torchModeRequiresTorchAmmoUnlessCreative() {
        assertFalse(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.TORCH, false, false));
        assertTrue(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.TORCH, true, false));
        assertTrue(CavenicBowTorchPolicy.shouldMarkShot(CavenicBowMode.TORCH, false, true));
    }
}
