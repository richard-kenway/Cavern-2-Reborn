package com.richardkenway.cavernreborn.app.dimension;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

final class IndexedPlacementRevalidationPolicy {
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

            if (relinkedPlacement.isPresent()) {
                relinkableReplacements.put(indexedPlacement, relinkedPlacement.get());
            } else {
                deadPlacements.add(indexedPlacement);
            }
        }

        return new IndexedPlacementRevalidationSnapshot(
            Set.copyOf(liveReplacements.keySet()),
            Set.copyOf(relinkableReplacements.keySet()),
            Set.copyOf(deadPlacements),
            Map.copyOf(liveReplacements),
            Map.copyOf(relinkableReplacements)
        );
    }

    static record IndexedPlacementRevalidationSnapshot(
        Set<PortalWorldIndex.PortalPlacement> liveIndexedPlacements,
        Set<PortalWorldIndex.PortalPlacement> relinkableIndexedPlacements,
        Set<PortalWorldIndex.PortalPlacement> deadIndexedPlacements,
        Map<PortalWorldIndex.PortalPlacement, PortalWorldIndex.PortalPlacement> liveReplacements,
        Map<PortalWorldIndex.PortalPlacement, PortalWorldIndex.PortalPlacement> relinkableReplacements
    ) {
        Optional<PortalWorldIndex.PortalPlacement> liveReplacement(PortalWorldIndex.PortalPlacement indexedPlacement) {
            return Optional.ofNullable(liveReplacements.get(indexedPlacement));
        }

        Optional<PortalWorldIndex.PortalPlacement> relinkableReplacement(PortalWorldIndex.PortalPlacement indexedPlacement) {
            return Optional.ofNullable(relinkableReplacements.get(indexedPlacement));
        }
    }
}
