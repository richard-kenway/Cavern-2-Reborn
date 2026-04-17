package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PortalCollisionEligibilityPolicyTest {
    @Test
    void legacyCollisionEligibilityMatrixMatchesExpectedPolicyOutcomes() {
        record Case(
            String label,
            boolean alive,
            boolean spectator,
            boolean crouching,
            boolean passenger,
            boolean vehicle,
            boolean projectile,
            boolean cooldown,
            boolean player,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility expected
        ) {}

        List<Case> cases = new ArrayList<>();
        cases.add(new Case("dead", false, false, false, false, false, false, false, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_DEAD));
        cases.add(new Case("spectator", true, true, false, false, false, false, false, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_SPECTATOR));
        cases.add(new Case("crouching", true, false, true, false, false, false, false, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_CROUCHING));
        cases.add(new Case("passenger", true, false, false, true, false, false, false, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PASSENGER));
        cases.add(new Case("vehicle", true, false, false, false, true, false, false, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_VEHICLE));
        cases.add(new Case("projectile", true, false, false, false, false, true, false, false,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PROJECTILE));
        cases.add(new Case("cooldown", true, false, false, false, false, false, true, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PORTAL_COOLDOWN));
        cases.add(new Case("player valid", true, false, false, false, false, false, false, true,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER));
        cases.add(new Case("non-player valid", true, false, false, false, false, false, false, false,
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER));

        for (Case scenario : cases) {
            assertEquals(
                scenario.expected(),
                PortalCollisionEligibilityPolicy.classify(
                    scenario.alive(),
                    scenario.spectator(),
                    scenario.crouching(),
                    scenario.passenger(),
                    scenario.vehicle(),
                    scenario.projectile(),
                    scenario.cooldown(),
                    scenario.player()
                ),
                scenario.label()
            );
        }
    }

    @Test
    void onlyPlayerEligibilityCanStartPortalTransport() {
        assertTrue(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER.shouldTriggerPortalCollision());
        assertTrue(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_PLAYER.shouldTransportPlayer());

        assertTrue(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER.shouldTriggerPortalCollision());
        assertFalse(
            PortalCollisionEligibilityPolicy.PortalCollisionEligibility.ALLOW_NON_PLAYER.shouldTransportPlayer());
    }

    @Test
    void ignoreStatesDoNotTriggerPortalCollisionOrTransport() {
        for (PortalCollisionEligibilityPolicy.PortalCollisionEligibility eligibility :
            List.of(
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_DEAD,
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_SPECTATOR,
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_CROUCHING,
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PASSENGER,
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_VEHICLE,
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PROJECTILE,
                PortalCollisionEligibilityPolicy.PortalCollisionEligibility.IGNORE_PORTAL_COOLDOWN
            )
        ) {
            assertFalse(eligibility.shouldTriggerPortalCollision());
            assertFalse(eligibility.shouldTransportPlayer());
        }
    }
}
