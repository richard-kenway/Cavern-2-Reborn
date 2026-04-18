package com.richardkenway.cavernreborn.core.state;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record PortalWorldIndex(Map<String, Set<PortalPlacement>> portalsByKey) {
    static final int MAX_PLACEMENTS_PER_PORTAL_KEY = 8;
    private static final int MAX_DISPLACED_PLACEMENTS_PER_PORTAL_KEY = 2;

    public PortalWorldIndex {
        portalsByKey = copyIndex(portalsByKey);
    }

    public static PortalWorldIndex empty() {
        return new PortalWorldIndex(Map.of());
    }

    public PortalWorldIndex withPortal(String portalKey, PortalPlacement placement) {
        return withRetainedPortal(portalKey, placement, DisplacedPortalRetention.none());
    }

    public PortalWorldIndex withRetainedPortal(
        String portalKey,
        PortalPlacement placement,
        DisplacedPortalRetention retentionHint
    ) {
        String normalizedPortalKey = requireText(portalKey, "portalKey");
        Objects.requireNonNull(placement, "placement");
        Objects.requireNonNull(retentionHint, "retentionHint");

        Map<String, Set<PortalPlacement>> updatedIndex = new LinkedHashMap<>(portalsByKey);
        Set<PortalPlacement> placements = prioritizePlacement(
            updatedIndex.getOrDefault(normalizedPortalKey, Set.of()),
            placement,
            retentionHint.displacedPlacements()
        );
        placements = trimToLimit(placements);
        updatedIndex.put(normalizedPortalKey, placements);

        return new PortalWorldIndex(updatedIndex);
    }

    public PortalWorldIndex withRetainedPortal(
        String portalKey,
        PortalPlacement placement,
        Set<PortalPlacement> displacedPlacements
    ) {
        return withRetainedPortal(
            portalKey,
            placement,
            DisplacedPortalRetention.withDisplacedPlacements(displacedPlacements)
        );
    }

    public PortalWorldIndex withReplacementPortal(
        String portalKey,
        PortalPlacement stalePlacement,
        PortalPlacement replacementPlacement
    ) {
        return withRetainedReplacementPortal(portalKey, stalePlacement, replacementPlacement, DisplacedPortalRetention.none());
    }

    public PortalWorldIndex withRetainedReplacementPortal(
        String portalKey,
        PortalPlacement stalePlacement,
        PortalPlacement replacementPlacement,
        DisplacedPortalRetention retentionHint
    ) {
        String normalizedPortalKey = requireText(portalKey, "portalKey");
        Objects.requireNonNull(stalePlacement, "stalePlacement");
        Objects.requireNonNull(replacementPlacement, "replacementPlacement");
        Objects.requireNonNull(retentionHint, "retentionHint");

        if (stalePlacement.equals(replacementPlacement)) {
            return withRetainedPortal(normalizedPortalKey, replacementPlacement, retentionHint);
        }

        Map<String, Set<PortalPlacement>> updatedIndex = new LinkedHashMap<>(portalsByKey);
        Set<PortalPlacement> placements = prioritizePlacement(
            updatedIndex.getOrDefault(normalizedPortalKey, Set.of()),
            replacementPlacement,
            retentionHint.displacedPlacements()
        );
        placements.remove(stalePlacement);
        placements = trimToLimit(placements);

        if (placements.isEmpty()) {
            updatedIndex.remove(normalizedPortalKey);
        } else {
            updatedIndex.put(normalizedPortalKey, placements);
        }

        return new PortalWorldIndex(updatedIndex);
    }

    public PortalWorldIndex withRetainedReplacementPortal(
        String portalKey,
        PortalPlacement stalePlacement,
        PortalPlacement replacementPlacement,
        Set<PortalPlacement> displacedPlacements
    ) {
        return withRetainedReplacementPortal(
            portalKey,
            stalePlacement,
            replacementPlacement,
            DisplacedPortalRetention.withDisplacedPlacements(displacedPlacements)
        );
    }

    public PortalWorldIndex withoutPortal(String portalKey, PortalPlacement placement) {
        String normalizedPortalKey = requireText(portalKey, "portalKey");
        Objects.requireNonNull(placement, "placement");

        Map<String, Set<PortalPlacement>> updatedIndex = new LinkedHashMap<>(portalsByKey);
        Set<PortalPlacement> placements = new LinkedHashSet<>(updatedIndex.getOrDefault(normalizedPortalKey, Set.of()));
        placements.remove(placement);

        if (placements.isEmpty()) {
            updatedIndex.remove(normalizedPortalKey);
        } else {
            updatedIndex.put(normalizedPortalKey, placements);
        }

        return new PortalWorldIndex(updatedIndex);
    }

    public Set<PortalPlacement> placementsFor(String portalKey) {
        return portalsByKey.getOrDefault(requireText(portalKey, "portalKey"), Set.of());
    }

    public Optional<PortalPlacement> firstPlacementFor(String portalKey) {
        Set<PortalPlacement> placements = placementsFor(portalKey);
        return placements.stream().findFirst();
    }

    private static Set<PortalPlacement> prioritizePlacement(
        Set<PortalPlacement> existingPlacements,
        PortalPlacement prioritizedPlacement,
        Set<PortalPlacement> displacedPlacements
    ) {
        LinkedHashSet<PortalPlacement> prioritizedPlacements = new LinkedHashSet<>();
        prioritizedPlacements.add(prioritizedPlacement);
        existingPlacements.stream()
            .filter(existingPlacement -> !existingPlacement.equals(prioritizedPlacement))
            .filter(existingPlacement -> !displacedPlacements.contains(existingPlacement))
            .forEach(prioritizedPlacements::add);
        int retainedLowerPriorityPlacements = 0;
        for (PortalPlacement existingPlacement : existingPlacements) {
            if (existingPlacement.equals(prioritizedPlacement)) {
                continue;
            }
            if (!displacedPlacements.contains(existingPlacement)) {
                continue;
            }
            if (retainedLowerPriorityPlacements >= MAX_DISPLACED_PLACEMENTS_PER_PORTAL_KEY) {
                break;
            }

            prioritizedPlacements.add(existingPlacement);
            retainedLowerPriorityPlacements += 1;
        }
        return prioritizedPlacements;
    }

    private static Set<PortalPlacement> trimToLimit(Set<PortalPlacement> placements) {
        if (placements.size() <= MAX_PLACEMENTS_PER_PORTAL_KEY) {
            return placements;
        }

        LinkedHashSet<PortalPlacement> trimmedPlacements = new LinkedHashSet<>();
        int retainedPlacements = 0;
        for (PortalPlacement placement : placements) {
            if (retainedPlacements >= MAX_PLACEMENTS_PER_PORTAL_KEY) {
                break;
            }

            trimmedPlacements.add(placement);
            retainedPlacements += 1;
        }

        return trimmedPlacements;
    }

    private static Map<String, Set<PortalPlacement>> copyIndex(Map<String, Set<PortalPlacement>> source) {
        Objects.requireNonNull(source, "portalsByKey");
        Map<String, Set<PortalPlacement>> copy = new LinkedHashMap<>();
        source.forEach((portalKey, placements) -> copy.put(
            requireText(portalKey, "portalKey"),
            Collections.unmodifiableSet(trimToLimit(new LinkedHashSet<>(Objects.requireNonNull(placements, "placements"))))
        ));
        return Collections.unmodifiableMap(copy);
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }

    public record PortalPlacement(int x, int y, int z, String axis) {
        public static final String AXIS_X = "x";
        public static final String AXIS_Z = "z";

        public PortalPlacement(int x, int y, int z) {
            this(x, y, z, AXIS_X);
        }

        public PortalPlacement {
            axis = normalizeAxis(axis);
        }

        public boolean isXAxis() {
            return AXIS_X.equals(axis);
        }

        private static String normalizeAxis(String axis) {
            String normalizedAxis = requireText(axis, "axis").toLowerCase(java.util.Locale.ROOT);
            if (AXIS_X.equals(normalizedAxis) || AXIS_Z.equals(normalizedAxis)) {
                return normalizedAxis;
            }

            throw new IllegalArgumentException("axis must be 'x' or 'z'");
        }
    }

    public record DisplacedPortalRetention(Set<PortalPlacement> displacedPlacements) {
        public DisplacedPortalRetention {
            displacedPlacements = Set.copyOf(Objects.requireNonNull(displacedPlacements, "displacedPlacements"));
        }

        public static DisplacedPortalRetention none() {
            return new DisplacedPortalRetention(Set.of());
        }

        public static DisplacedPortalRetention withDisplacedPlacements(Set<PortalPlacement> displacedPlacements) {
            return new DisplacedPortalRetention(displacedPlacements);
        }
    }
}
