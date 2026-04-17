package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;

public final class PortalCollisionEligibilityPolicy {
    public enum PortalCollisionEligibility {
        IGNORE_DEAD(true, false),
        IGNORE_SPECTATOR(true, false),
        IGNORE_CROUCHING(true, false),
        IGNORE_PASSENGER(true, false),
        IGNORE_VEHICLE(true, false),
        IGNORE_PROJECTILE(true, false),
        IGNORE_PORTAL_COOLDOWN(true, false),
        ALLOW_NON_PLAYER(false, false),
        ALLOW_PLAYER(false, true);

        private final boolean ignorePortalCollision;
        private final boolean allowPlayerTransport;

        PortalCollisionEligibility(boolean ignorePortalCollision, boolean allowPlayerTransport) {
            this.ignorePortalCollision = ignorePortalCollision;
            this.allowPlayerTransport = allowPlayerTransport;
        }

        public boolean shouldTriggerPortalCollision() {
            return !ignorePortalCollision;
        }

        public boolean shouldTransportPlayer() {
            return allowPlayerTransport;
        }
    }

    private PortalCollisionEligibilityPolicy() {
    }

    public static PortalCollisionEligibility classify(Entity entity, boolean portalCooldown) {
        Objects.requireNonNull(entity, "entity");

        return classify(
            entity.isAlive(),
            entity.isSpectator(),
            entity.isCrouching(),
            entity.isPassenger(),
            entity.isVehicle(),
            entity instanceof Projectile,
            portalCooldown,
            entity instanceof ServerPlayer
        );
    }

    public static PortalCollisionEligibility classify(
        boolean alive,
        boolean spectator,
        boolean crouching,
        boolean passenger,
        boolean vehicle,
        boolean projectile,
        boolean portalCooldown,
        boolean player
    ) {
        if (!alive) {
            return PortalCollisionEligibility.IGNORE_DEAD;
        }

        if (spectator) {
            return PortalCollisionEligibility.IGNORE_SPECTATOR;
        }

        if (crouching) {
            return PortalCollisionEligibility.IGNORE_CROUCHING;
        }

        if (passenger) {
            return PortalCollisionEligibility.IGNORE_PASSENGER;
        }

        if (vehicle) {
            return PortalCollisionEligibility.IGNORE_VEHICLE;
        }

        if (projectile) {
            return PortalCollisionEligibility.IGNORE_PROJECTILE;
        }

        if (portalCooldown) {
            return PortalCollisionEligibility.IGNORE_PORTAL_COOLDOWN;
        }

        if (player) {
            return PortalCollisionEligibility.ALLOW_PLAYER;
        }

        return PortalCollisionEligibility.ALLOW_NON_PLAYER;
    }
}
