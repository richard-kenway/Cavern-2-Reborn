package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;

public record TeleportContext(
    String portalKey,
    double entryOffsetX,
    double entryOffsetY,
    double entryOffsetZ,
    String approachFacing
) {
    public TeleportContext {
        portalKey = requireText(portalKey, "portalKey");
        approachFacing = requireText(approachFacing, "approachFacing");
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
