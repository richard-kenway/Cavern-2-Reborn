package com.richardkenway.cavernreborn.core.state;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record PortalWorldIndex(Map<String, Set<PortalPlacement>> portalsByKey) {
    public PortalWorldIndex {
        portalsByKey = copyIndex(portalsByKey);
    }

    public static PortalWorldIndex empty() {
        return new PortalWorldIndex(Map.of());
    }

    public PortalWorldIndex withPortal(String portalKey, PortalPlacement placement) {
        String normalizedPortalKey = requireText(portalKey, "portalKey");
        Objects.requireNonNull(placement, "placement");

        Map<String, Set<PortalPlacement>> updatedIndex = new LinkedHashMap<>(portalsByKey);
        Set<PortalPlacement> placements = new LinkedHashSet<>(updatedIndex.getOrDefault(normalizedPortalKey, Set.of()));
        placements.add(placement);
        updatedIndex.put(normalizedPortalKey, placements);

        return new PortalWorldIndex(updatedIndex);
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

    private static Map<String, Set<PortalPlacement>> copyIndex(Map<String, Set<PortalPlacement>> source) {
        Objects.requireNonNull(source, "portalsByKey");
        Map<String, Set<PortalPlacement>> copy = new LinkedHashMap<>();
        source.forEach((portalKey, placements) -> copy.put(
            requireText(portalKey, "portalKey"),
            Collections.unmodifiableSet(new LinkedHashSet<>(Objects.requireNonNull(placements, "placements")))
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
}
