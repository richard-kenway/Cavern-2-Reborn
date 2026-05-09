package com.richardkenway.cavernreborn.core.spawn;

import java.util.List;

public final class CaveniaSpawnProviderPolicy {
    public static final int DEFAULT_MONSTER_SPAWN = 200;
    public static final int MIN_MONSTER_SPAWN = 0;
    public static final int MAX_MONSTER_SPAWN = 5000;
    public static final double DEFAULT_CRAZY_SPAWN_CHANCE = 0.1D;
    public static final double MIN_CRAZY_SPAWN_CHANCE = 0.0D;
    public static final double MAX_CRAZY_SPAWN_CHANCE = 1.0D;
    public static final String DEFERRED_REBORN_ENTITY_ID_PREFIX = "deferred:";
    public static final String DEFERRED_CAVEMAN_REBORN_ENTITY_ID = DEFERRED_REBORN_ENTITY_ID_PREFIX + "caveman";

    private static final List<CaveniaSpawnEntry> NORMAL_ROSTER = List.of(
        new CaveniaSpawnEntry("EntityCavenicSkeleton", "cavernreborn:cavenic_skeleton", 20, 1, 1),
        new CaveniaSpawnEntry("EntityCavenicCreeper", "cavernreborn:cavenic_creeper", 30, 1, 1),
        new CaveniaSpawnEntry("EntityCavenicZombie", "cavernreborn:cavenic_zombie", 30, 2, 2),
        new CaveniaSpawnEntry("EntityCavenicSpider", "cavernreborn:cavenic_spider", 30, 1, 1),
        new CaveniaSpawnEntry("EntityCavenicWitch", "cavernreborn:cavenic_witch", 15, 1, 1),
        new CaveniaSpawnEntry("EntityCavenicBear", "cavernreborn:cavenic_bear", 30, 1, 1),
        new CaveniaSpawnEntry("EntityCaveman", DEFERRED_CAVEMAN_REBORN_ENTITY_ID, 35, 1, 1)
    );

    private static final List<CaveniaSpawnEntry> CRAZY_ROSTER = List.of(
        new CaveniaSpawnEntry("EntityCrazySkeleton", "cavernreborn:crazy_skeleton", 1, 1, 1),
        new CaveniaSpawnEntry("EntityCrazyCreeper", "cavernreborn:crazy_creeper", 1, 1, 1),
        new CaveniaSpawnEntry("EntityCrazyZombie", "cavernreborn:crazy_zombie", 1, 1, 1),
        new CaveniaSpawnEntry("EntityCrazySpider", "cavernreborn:crazy_spider", 1, 1, 1)
    );

    private CaveniaSpawnProviderPolicy() {
    }

    public static int clampMonsterSpawn(int value) {
        return Math.max(MIN_MONSTER_SPAWN, Math.min(MAX_MONSTER_SPAWN, value));
    }

    public static double clampCrazySpawnChance(double value) {
        return Math.max(MIN_CRAZY_SPAWN_CHANCE, Math.min(MAX_CRAZY_SPAWN_CHANCE, value));
    }

    public static boolean isCrazyRosterChanceEnabled(double chance) {
        return clampCrazySpawnChance(chance) > 0.0D;
    }

    public static int scanRangeForCrazySpawnChance(double chance) {
        double clampedChance = clampCrazySpawnChance(chance);

        if (clampedChance <= 0.1D) {
            return 50;
        }
        if (clampedChance <= 0.2D) {
            return 32;
        }
        if (clampedChance <= 0.4D) {
            return 16;
        }
        if (clampedChance <= 0.6D) {
            return 8;
        }
        if (clampedChance <= 0.8D) {
            return 4;
        }

        return 0;
    }

    public static int verticalScanRangeFor(int range) {
        return range > 1 ? range / 2 : range;
    }

    public static boolean canUseCrazyRoster(double chance, boolean chanceRollPassed, boolean nearbyBossLikeCavenicMobFound) {
        return chanceRollPassed && canUseCrazyRosterAfterChanceRoll(chance, nearbyBossLikeCavenicMobFound);
    }

    public static boolean canUseCrazyRosterAfterChanceRoll(double chance, boolean nearbyBossLikeCavenicMobFound) {
        if (!isCrazyRosterChanceEnabled(chance)) {
            return false;
        }

        return scanRangeForCrazySpawnChance(chance) <= 0 || !nearbyBossLikeCavenicMobFound;
    }

    public static List<CaveniaSpawnEntry> normalRoster() {
        return NORMAL_ROSTER;
    }

    public static List<CaveniaSpawnEntry> crazyRoster() {
        return CRAZY_ROSTER;
    }
}
