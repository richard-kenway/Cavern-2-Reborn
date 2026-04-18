package com.richardkenway.cavernreborn.core.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class PortalStateModelsTest {
    @Test
    void portalReturnStateRejectsBlankPortalKey() {
        assertThrows(IllegalArgumentException.class, () -> new PortalReturnState(" ", "minecraft:overworld", 10, 64, 10));
    }

    @Test
    void teleportContextKeepsTransientPlacementData() {
        TeleportContext context = new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north");

        assertEquals("cavern", context.portalKey());
        assertEquals(0.25D, context.entryOffsetX());
        assertEquals(0.5D, context.entryOffsetY());
        assertEquals(0.75D, context.entryOffsetZ());
        assertEquals("north", context.approachFacing());
    }

    @Test
    void portalWorldIndexKeepsPortalPlacementsUnique() {
        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 2, 3));

        assertEquals(1, index.placementsFor("cavern").size());
    }

    @Test
    void portalWorldIndexPromotesMostRecentPlacementToFront() {
        PortalWorldIndex.PortalPlacement olderPlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);
        PortalWorldIndex.PortalPlacement newerPlacement = new PortalWorldIndex.PortalPlacement(4, 5, 6);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", olderPlacement)
            .withPortal("cavern", newerPlacement);

        assertEquals(newerPlacement, index.firstPlacementFor("cavern").orElseThrow());
    }

    @Test
    void portalWorldIndexReusingExistingPlacementPromotesItToFront() {
        PortalWorldIndex.PortalPlacement firstPlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);
        PortalWorldIndex.PortalPlacement secondPlacement = new PortalWorldIndex.PortalPlacement(4, 5, 6);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", firstPlacement)
            .withPortal("cavern", secondPlacement)
            .withPortal("cavern", firstPlacement);

        assertEquals(firstPlacement, index.firstPlacementFor("cavern").orElseThrow());
        assertEquals(2, index.placementsFor("cavern").size());
    }

    @Test
    void portalWorldIndexReplacingIndexedPlacementRetainsCompetingEntries() {
        PortalWorldIndex.PortalPlacement stalePlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);
        PortalWorldIndex.PortalPlacement replacementPlacement = new PortalWorldIndex.PortalPlacement(4, 5, 6);
        PortalWorldIndex.PortalPlacement competingPlacement = new PortalWorldIndex.PortalPlacement(7, 8, 9);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", stalePlacement)
            .withPortal("cavern", competingPlacement)
            .withReplacementPortal("cavern", stalePlacement, replacementPlacement);

        assertEquals(replacementPlacement, index.firstPlacementFor("cavern").orElseThrow());
        assertEquals(2, index.placementsFor("cavern").size());
        assertEquals(
            competingPlacement,
            index.placementsFor("cavern").stream().skip(1).findFirst().orElseThrow()
        );
    }

    @Test
    void portalWorldIndexReplacingWithSamePlacementDoesNotDropHistory() {
        PortalWorldIndex.PortalPlacement firstPlacement = new PortalWorldIndex.PortalPlacement(1, 2, 3);

        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", firstPlacement)
            .withReplacementPortal("cavern", firstPlacement, firstPlacement);

        assertEquals(1, index.placementsFor("cavern").size());
        assertEquals(firstPlacement, index.firstPlacementFor("cavern").orElseThrow());
    }

    @Test
    void portalWorldIndexCapsHistoryToConfiguredMaximumAndDropsOldestEntries() {
        int maxPlacementsPerPortalKey = PortalWorldIndex.MAX_PLACEMENTS_PER_PORTAL_KEY;
        PortalWorldIndex index = PortalWorldIndex.empty();
        for (int i = 0; i < maxPlacementsPerPortalKey + 3; i++) {
            index = index.withPortal("cavern", new PortalWorldIndex.PortalPlacement(i, i, i));
        }

        assertEquals(maxPlacementsPerPortalKey, index.placementsFor("cavern").size());
        assertEquals(
            new PortalWorldIndex.PortalPlacement(maxPlacementsPerPortalKey + 2, maxPlacementsPerPortalKey + 2, maxPlacementsPerPortalKey + 2),
            index.firstPlacementFor("cavern").orElseThrow()
        );
        assertFalse(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(0, 0, 0)));
        assertFalse(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(1, 1, 1)));
        assertFalse(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(2, 2, 2)));
        assertEquals(
            new PortalWorldIndex.PortalPlacement(3, 3, 3),
            index.placementsFor("cavern").stream().skip(maxPlacementsPerPortalKey - 1).findFirst().orElseThrow()
        );
    }

    @Test
    void portalWorldIndexReplacingPlacementRespectsCapAndKeepsReplacementAtHead() {
        int maxPlacementsPerPortalKey = PortalWorldIndex.MAX_PLACEMENTS_PER_PORTAL_KEY;
        PortalWorldIndex index = PortalWorldIndex.empty();
        for (int i = 0; i < maxPlacementsPerPortalKey; i++) {
            index = index.withPortal("cavern", new PortalWorldIndex.PortalPlacement(i, 0, i));
        }

        PortalWorldIndex.PortalPlacement stalePlacement = new PortalWorldIndex.PortalPlacement(3, 0, 3);
        PortalWorldIndex.PortalPlacement replacementPlacement = new PortalWorldIndex.PortalPlacement(99, 0, 99);
        PortalWorldIndex replacedIndex = index.withReplacementPortal("cavern", stalePlacement, replacementPlacement);

        assertEquals(replacementPlacement, replacedIndex.firstPlacementFor("cavern").orElseThrow());
        assertEquals(maxPlacementsPerPortalKey, replacedIndex.placementsFor("cavern").size());
        assertFalse(replacedIndex.placementsFor("cavern").contains(stalePlacement));
        assertTrue(replacedIndex.placementsFor("cavern").contains(replacementPlacement));
    }

    @Test
    void portalWorldIndexLimitsLowerPriorityHistoryWhilePromotingFreshPlacementsUnderCapPressure() {
        int maxPlacementsPerPortalKey = PortalWorldIndex.MAX_PLACEMENTS_PER_PORTAL_KEY;
        PortalWorldIndex.PortalPlacement livePlacementA = new PortalWorldIndex.PortalPlacement(1, 64, 1);
        PortalWorldIndex.PortalPlacement livePlacementB = new PortalWorldIndex.PortalPlacement(2, 64, 2);
        PortalWorldIndex.PortalPlacement livePlacementC = new PortalWorldIndex.PortalPlacement(3, 64, 3);
        PortalWorldIndex.PortalPlacement livePlacementD = new PortalWorldIndex.PortalPlacement(4, 64, 4);
        PortalWorldIndex.PortalPlacement displacedPlacementA = new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement displacedPlacementB = new PortalWorldIndex.PortalPlacement(60, 70, 60, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement displacedPlacementC = new PortalWorldIndex.PortalPlacement(90, 70, 90, PortalWorldIndex.PortalPlacement.AXIS_X);
        PortalWorldIndex.PortalPlacement displacedPlacementD = new PortalWorldIndex.PortalPlacement(120, 70, 120, PortalWorldIndex.PortalPlacement.AXIS_Z);
        PortalWorldIndex.PortalPlacement freshPlacement = new PortalWorldIndex.PortalPlacement(99, 70, 99, PortalWorldIndex.PortalPlacement.AXIS_X);

        Set<PortalWorldIndex.PortalPlacement> placements = new LinkedHashSet<>();
        placements.add(livePlacementA);
        placements.add(livePlacementB);
        placements.add(livePlacementC);
        placements.add(livePlacementD);
        placements.add(displacedPlacementA);
        placements.add(displacedPlacementB);
        placements.add(displacedPlacementC);
        placements.add(displacedPlacementD);

        Map<String, Set<PortalWorldIndex.PortalPlacement>> portalsByKey = new LinkedHashMap<>();
        portalsByKey.put("cavern", placements);

        PortalWorldIndex index = new PortalWorldIndex(portalsByKey);
        PortalWorldIndex refreshedIndex = index.withPortal(
            "cavern",
            freshPlacement,
            Set.of(displacedPlacementA, displacedPlacementB, displacedPlacementC, displacedPlacementD)
        );

        assertEquals(freshPlacement, refreshedIndex.firstPlacementFor("cavern").orElseThrow());
        assertEquals(
            List.of(
                freshPlacement,
                livePlacementA,
                livePlacementB,
                livePlacementC,
                livePlacementD,
                displacedPlacementA,
                displacedPlacementB
            ),
            List.copyOf(refreshedIndex.placementsFor("cavern"))
        );
        assertTrue(refreshedIndex.placementsFor("cavern").contains(livePlacementA));
        assertTrue(refreshedIndex.placementsFor("cavern").contains(displacedPlacementA));
        assertTrue(refreshedIndex.placementsFor("cavern").contains(displacedPlacementB));
        assertFalse(refreshedIndex.placementsFor("cavern").contains(displacedPlacementC));
        assertFalse(refreshedIndex.placementsFor("cavern").contains(displacedPlacementD));
        assertEquals(7, refreshedIndex.placementsFor("cavern").size());
        assertTrue(refreshedIndex.placementsFor("cavern").size() <= maxPlacementsPerPortalKey);
    }

    @Test
    void portalWorldIndexConstructorNormalizesOversizedPlacementHistory() {
        int maxPlacementsPerPortalKey = PortalWorldIndex.MAX_PLACEMENTS_PER_PORTAL_KEY;
        Set<PortalWorldIndex.PortalPlacement> oversizedPlacements = new LinkedHashSet<>();
        for (int i = 0; i < maxPlacementsPerPortalKey + 3; i++) {
            oversizedPlacements.add(new PortalWorldIndex.PortalPlacement(i, 64, i));
        }

        Map<String, Set<PortalWorldIndex.PortalPlacement>> portalsByKey = new LinkedHashMap<>();
        portalsByKey.put("cavern", oversizedPlacements);

        PortalWorldIndex index = new PortalWorldIndex(portalsByKey);

        assertEquals(maxPlacementsPerPortalKey, index.placementsFor("cavern").size());
        assertEquals(new PortalWorldIndex.PortalPlacement(0, 64, 0), index.firstPlacementFor("cavern").orElseThrow());
        assertFalse(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(maxPlacementsPerPortalKey, 64, maxPlacementsPerPortalKey)));
        assertFalse(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(maxPlacementsPerPortalKey + 1, 64, maxPlacementsPerPortalKey + 1)));
        assertFalse(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(maxPlacementsPerPortalKey + 2, 64, maxPlacementsPerPortalKey + 2)));
    }

    @Test
    void portalWorldIndexPreservesFreshPlacementsWhileChurningBeyondCap() {
        int maxPlacementsPerPortalKey = PortalWorldIndex.MAX_PLACEMENTS_PER_PORTAL_KEY;
        PortalWorldIndex index = PortalWorldIndex.empty()
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(1, 64, 1))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(2, 64, 2))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(3, 64, 3))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(4, 64, 4))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(5, 64, 5))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(6, 64, 6))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(7, 64, 7))
            .withPortal("cavern", new PortalWorldIndex.PortalPlacement(8, 64, 8));

        index = index.withPortal("cavern", new PortalWorldIndex.PortalPlacement(3, 64, 3));
        index = index.withPortal("cavern", new PortalWorldIndex.PortalPlacement(4, 64, 4));

        PortalWorldIndex.PortalPlacement freshPlacementA = new PortalWorldIndex.PortalPlacement(3, 64, 3);
        PortalWorldIndex.PortalPlacement freshPlacementB = new PortalWorldIndex.PortalPlacement(4, 64, 4);
        for (int i = 100; i < 100 + maxPlacementsPerPortalKey + 12; i++) {
            index = index.withPortal("cavern", new PortalWorldIndex.PortalPlacement(i, 64, i));
            if (i % 3 == 0) {
                index = index.withPortal("cavern", freshPlacementA);
            }
            if (i % 3 == 1) {
                index = index.withPortal("cavern", freshPlacementB);
            }
        }
        int lastCreatedPlacement = 100 + maxPlacementsPerPortalKey + 11;

        assertEquals(maxPlacementsPerPortalKey, index.placementsFor("cavern").size());
        assertTrue(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(3, 64, 3)));
        assertTrue(index.placementsFor("cavern").contains(new PortalWorldIndex.PortalPlacement(4, 64, 4)));
        assertEquals(
            new PortalWorldIndex.PortalPlacement(lastCreatedPlacement, 64, lastCreatedPlacement),
            index.firstPlacementFor("cavern").orElseThrow()
        );
    }
}
