package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;

public final class PortalCollisionEligibilityPolicy {
    public enum PortalCollisionEligibility {
        IGNORE_DEAD(true, false, false),
        IGNORE_SPECTATOR(true, false, false),
        IGNORE_CROUCHING(true, false, false),
        IGNORE_PORTAL_INELIGIBLE(true, false, false),
        IGNORE_PASSENGER(true, false, false),
        IGNORE_VEHICLE(true, false, false),
        IGNORE_PROJECTILE(true, false, false),
        IGNORE_PORTAL_COOLDOWN(true, false, false),
        ALLOW_NON_PLAYER(false, false, true),
        ALLOW_PLAYER(false, true, false);

        private final boolean ignorePortalCollision;
        private final boolean allowPlayerTransport;
        private final boolean allowNonPlayerTransport;

        PortalCollisionEligibility(boolean ignorePortalCollision, boolean allowPlayerTransport, boolean allowNonPlayerTransport) {
            this.ignorePortalCollision = ignorePortalCollision;
            this.allowPlayerTransport = allowPlayerTransport;
            this.allowNonPlayerTransport = allowNonPlayerTransport;
        }

        public boolean shouldTriggerPortalCollision() {
            return !ignorePortalCollision;
        }

        public boolean shouldTransportPlayer() {
            return allowPlayerTransport;
        }

        public boolean shouldTransportNonPlayer() {
            return allowNonPlayerTransport;
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
            entity.canUsePortal(false),
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
        boolean canUsePortal,
        boolean player
    ) {
        if (!alive) {
            return PortalCollisionEligibility.IGNORE_DEAD;
        }

        if (!canUsePortal) {
            return PortalCollisionEligibility.IGNORE_PORTAL_INELIGIBLE;
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
