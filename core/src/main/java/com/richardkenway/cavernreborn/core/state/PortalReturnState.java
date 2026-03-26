package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;

public record PortalReturnState(String portalKey, String returnDimensionId, int returnX, int returnY, int returnZ) {
    public PortalReturnState {
        portalKey = requireText(portalKey, "portalKey");
        returnDimensionId = requireText(returnDimensionId, "returnDimensionId");
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
