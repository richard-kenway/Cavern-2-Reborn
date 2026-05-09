package com.richardkenway.cavernreborn.core.spawn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CaveniaSpawnProviderPolicyTest {
    @Test
    void defaultMonsterSpawnRemainsPinned() {
        assertEquals(200, CaveniaSpawnProviderPolicy.DEFAULT_MONSTER_SPAWN);
        assertEquals(0, CaveniaSpawnProviderPolicy.MIN_MONSTER_SPAWN);
        assertEquals(5000, CaveniaSpawnProviderPolicy.MAX_MONSTER_SPAWN);
    }

    @Test
    void monsterSpawnClampStaysSourceConfirmed() {
        assertEquals(0, CaveniaSpawnProviderPolicy.clampMonsterSpawn(-1));
        assertEquals(200, CaveniaSpawnProviderPolicy.clampMonsterSpawn(200));
        assertEquals(5000, CaveniaSpawnProviderPolicy.clampMonsterSpawn(5001));
    }

    @Test
    void defaultCrazySpawnChanceRemainsPinned() {
        assertEquals(0.1D, CaveniaSpawnProviderPolicy.DEFAULT_CRAZY_SPAWN_CHANCE);
        assertEquals(0.0D, CaveniaSpawnProviderPolicy.MIN_CRAZY_SPAWN_CHANCE);
        assertEquals(1.0D, CaveniaSpawnProviderPolicy.MAX_CRAZY_SPAWN_CHANCE);
    }

    @Test
    void crazySpawnChanceClampStaysSourceConfirmed() {
        assertEquals(0.0D, CaveniaSpawnProviderPolicy.clampCrazySpawnChance(-0.1D));
        assertEquals(0.1D, CaveniaSpawnProviderPolicy.clampCrazySpawnChance(0.1D));
        assertEquals(1.0D, CaveniaSpawnProviderPolicy.clampCrazySpawnChance(1.1D));
    }

    @Test
    void crazySpawnChanceEnabledHelperKeepsZeroDisabled() {
        assertFalse(CaveniaSpawnProviderPolicy.isCrazyRosterChanceEnabled(0.0D));
        assertTrue(CaveniaSpawnProviderPolicy.isCrazyRosterChanceEnabled(0.0000001D));
    }

    @Test
    void scanRangeMappingRemainsSourceConfirmed() {
        assertEquals(50, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.0D));
        assertEquals(50, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.1D));
        assertEquals(32, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.1000001D));
        assertEquals(32, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.2D));
        assertEquals(16, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.2000001D));
        assertEquals(16, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.4D));
        assertEquals(8, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.4000001D));
        assertEquals(8, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.6D));
        assertEquals(4, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.6000001D));
        assertEquals(4, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.8D));
        assertEquals(0, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(0.8000001D));
        assertEquals(0, CaveniaSpawnProviderPolicy.scanRangeForCrazySpawnChance(1.0D));
    }

    @Test
    void verticalScanRangeMappingRemainsSourceConfirmed() {
        assertEquals(25, CaveniaSpawnProviderPolicy.verticalScanRangeFor(50));
        assertEquals(16, CaveniaSpawnProviderPolicy.verticalScanRangeFor(32));
        assertEquals(8, CaveniaSpawnProviderPolicy.verticalScanRangeFor(16));
        assertEquals(4, CaveniaSpawnProviderPolicy.verticalScanRangeFor(8));
        assertEquals(2, CaveniaSpawnProviderPolicy.verticalScanRangeFor(4));
        assertEquals(1, CaveniaSpawnProviderPolicy.verticalScanRangeFor(1));
        assertEquals(0, CaveniaSpawnProviderPolicy.verticalScanRangeFor(0));
    }

    @Test
    void crazyRosterActivationHelperStaysNonRuntimeAndSourceConfirmed() {
        assertFalse(CaveniaSpawnProviderPolicy.canUseCrazyRoster(0.0D, true, false));
        assertFalse(CaveniaSpawnProviderPolicy.canUseCrazyRoster(0.1D, false, false));
        assertFalse(CaveniaSpawnProviderPolicy.canUseCrazyRoster(0.1D, true, true));
        assertTrue(CaveniaSpawnProviderPolicy.canUseCrazyRoster(0.1D, true, false));
        assertTrue(CaveniaSpawnProviderPolicy.canUseCrazyRoster(1.0D, true, true));
    }

    @Test
    void normalRosterRemainsSourceConfirmed() {
        assertEquals(
            List.of(
                new CaveniaSpawnEntry("EntityCavenicSkeleton", "cavernreborn:cavenic_skeleton", 20, 1, 1),
                new CaveniaSpawnEntry("EntityCavenicCreeper", "cavernreborn:cavenic_creeper", 30, 1, 1),
                new CaveniaSpawnEntry("EntityCavenicZombie", "cavernreborn:cavenic_zombie", 30, 2, 2),
                new CaveniaSpawnEntry("EntityCavenicSpider", "cavernreborn:cavenic_spider", 30, 1, 1),
                new CaveniaSpawnEntry("EntityCavenicWitch", "cavernreborn:cavenic_witch", 15, 1, 1),
                new CaveniaSpawnEntry("EntityCavenicBear", "cavernreborn:cavenic_bear", 30, 1, 1),
                new CaveniaSpawnEntry("EntityCaveman", CaveniaSpawnProviderPolicy.DEFERRED_CAVEMAN_REBORN_ENTITY_ID, 35, 1, 1)
            ),
            CaveniaSpawnProviderPolicy.normalRoster()
        );
    }

    @Test
    void crazyRosterRemainsSourceConfirmed() {
        assertEquals(
            List.of(
                new CaveniaSpawnEntry("EntityCrazySkeleton", "cavernreborn:crazy_skeleton", 1, 1, 1),
                new CaveniaSpawnEntry("EntityCrazyCreeper", "cavernreborn:crazy_creeper", 1, 1, 1),
                new CaveniaSpawnEntry("EntityCrazyZombie", "cavernreborn:crazy_zombie", 1, 1, 1),
                new CaveniaSpawnEntry("EntityCrazySpider", "cavernreborn:crazy_spider", 1, 1, 1)
            ),
            CaveniaSpawnProviderPolicy.crazyRoster()
        );
        assertEquals(4, CaveniaSpawnProviderPolicy.crazyRoster().size());
    }

    @Test
    void deferredCavemanEntryRemainsExplicitlyNonRuntime() {
        CaveniaSpawnEntry cavemanEntry = CaveniaSpawnProviderPolicy.normalRoster().get(6);

        assertEquals("EntityCaveman", cavemanEntry.legacyClassName());
        assertEquals(CaveniaSpawnProviderPolicy.DEFERRED_CAVEMAN_REBORN_ENTITY_ID, cavemanEntry.rebornEntityId());
        assertFalse(cavemanEntry.hasActiveRebornEntityId());
    }
}
