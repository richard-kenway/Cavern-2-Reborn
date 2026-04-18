package com.richardkenway.cavernreborn.app.dimension;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

final class IndexedPlacementRevalidationPolicy {
    private static final int MAX_REVALIDATION_RELINK_XZ_DRIFT = 2;
    private static final int MAX_REVALIDATION_RELINK_Y_DRIFT = 1;

    private IndexedPlacementRevalidationPolicy() {
    }

    static IndexedPlacementRevalidationSnapshot classifyIndexedPlacements(
        String targetDimensionId,
        Set<PortalWorldIndex.PortalPlacement> indexedPlacements,
        PlayerTravelContext player
    ) {
        Objects.requireNonNull(targetDimensionId, "targetDimensionId");
        Objects.requireNonNull(indexedPlacements, "indexedPlacements");
        Objects.requireNonNull(player, "player");

        Map<PortalWorldIndex.PortalPlacement, PortalWorldIndex.PortalPlacement> liveReplacements = new LinkedHashMap<>();
        Map<PortalWorldIndex.PortalPlacement, PortalWorldIndex.PortalPlacement> relinkableReplacements = new LinkedHashMap<>();
        Set<PortalWorldIndex.PortalPlacement> displacedPlacements = new LinkedHashSet<>();
        Set<PortalWorldIndex.PortalPlacement> deadPlacements = new LinkedHashSet<>();

        for (PortalWorldIndex.PortalPlacement indexedPlacement : indexedPlacements) {
            Optional<PortalWorldIndex.PortalPlacement> resolvedPlacement = player.resolvePortalAt(
                targetDimensionId,
                indexedPlacement.x(),
                indexedPlacement.y(),
                indexedPlacement.z()
            );

            if (resolvedPlacement.isPresent()) {
                liveReplacements.put(indexedPlacement, resolvedPlacement.get());
                continue;
            }

            Optional<PortalWorldIndex.PortalPlacement> relinkedPlacement = player.findPortalNear(
                targetDimensionId,
                indexedPlacement.x(),
                indexedPlacement.y(),
                indexedPlacement.z()
            );

            if (relinkedPlacement.isPresent() && isWithinAllowedRelinkDrift(indexedPlacement, relinkedPlacement.get())) {
                relinkableReplacements.put(indexedPlacement, relinkedPlacement.get());
            } else if (relinkedPlacement.isPresent()) {
                displacedPlacements.add(indexedPlacement);
            } else {
                deadPlacements.add(indexedPlacement);
            }
        }

        return new IndexedPlacementRevalidationSnapshot(
            Set.copyOf(liveReplacements.keySet()),
            Set.copyOf(relinkableReplacements.keySet()),
            Set.copyOf(displacedPlacements),
            Set.copyOf(deadPlacements),
            Map.copyOf(liveReplacements),
            Map.copyOf(relinkableReplacements)
        );
    }

    private static boolean isWithinAllowedRelinkDrift(
        PortalWorldIndex.PortalPlacement stalePlacement,
        PortalWorldIndex.PortalPlacement relinkedPlacement
    ) {
        int xDrift = Math.abs(stalePlacement.x() - relinkedPlacement.x());
        int yDrift = Math.abs(stalePlacement.y() - relinkedPlacement.y());
        int zDrift = Math.abs(stalePlacement.z() - relinkedPlacement.z());

        return xDrift <= MAX_REVALIDATION_RELINK_XZ_DRIFT
            && yDrift <= MAX_REVALIDATION_RELINK_Y_DRIFT
            && zDrift <= MAX_REVALIDATION_RELINK_XZ_DRIFT;
    }

    static record IndexedPlacementRevalidationSnapshot(
        Set<PortalWorldIndex.PortalPlacement> liveIndexedPlacements,
        Set<PortalWorldIndex.PortalPlacement> relinkableIndexedPlacements,
        Set<PortalWorldIndex.PortalPlacement> displacedIndexedPlacements,
        Set<PortalWorldIndex.PortalPlacement> deadIndexedPlacements,
        Map<PortalWorldIndex.PortalPlacement, PortalWorldIndex.PortalPlacement> liveReplacements,
        Map<PortalWorldIndex.PortalPlacement, PortalWorldIndex.PortalPlacement> relinkableReplacements
    ) {
        Set<PortalWorldIndex.PortalPlacement> regenerationEligibleIndexedPlacements() {
            return deadIndexedPlacements;
        }

        Optional<PortalWorldIndex.PortalPlacement> liveReplacement(PortalWorldIndex.PortalPlacement indexedPlacement) {
            return Optional.ofNullable(liveReplacements.get(indexedPlacement));
        }

        Optional<PortalWorldIndex.PortalPlacement> relinkableReplacement(PortalWorldIndex.PortalPlacement indexedPlacement) {
            return Optional.ofNullable(relinkableReplacements.get(indexedPlacement));
        }

        PortalWorldIndex withRetainedPortal(
            String portalKey,
            PortalWorldIndex worldIndex,
            PortalWorldIndex.PortalPlacement resolvedPlacement
        ) {
            return removeDeadIndexedPlacements(portalKey, worldIndex)
                .withPortal(portalKey, resolvedPlacement, displacedIndexedPlacements);
        }

        PortalWorldIndex withRetainedReplacementPortal(
            String portalKey,
            PortalWorldIndex worldIndex,
            PortalWorldIndex.PortalPlacement stalePlacement,
            PortalWorldIndex.PortalPlacement replacementPlacement
        ) {
            return removeDeadIndexedPlacements(portalKey, worldIndex)
                .withReplacementPortal(portalKey, stalePlacement, replacementPlacement, displacedIndexedPlacements);
        }

        private PortalWorldIndex removeDeadIndexedPlacements(String portalKey, PortalWorldIndex worldIndex) {
            String normalizedPortalKey = Objects.requireNonNull(portalKey, "portalKey");
            Objects.requireNonNull(worldIndex, "worldIndex");
            PortalWorldIndex refreshedIndex = worldIndex;

            for (PortalWorldIndex.PortalPlacement deadPlacement : deadIndexedPlacements) {
                refreshedIndex = refreshedIndex.withoutPortal(normalizedPortalKey, deadPlacement);
            }

            return refreshedIndex;
        }
    }
}
