package com.richardkenway.cavernreborn.data.state;

import java.util.Objects;

public record TeleportContextData(
    String portalKey,
    double entryOffsetX,
    double entryOffsetY,
    double entryOffsetZ,
    String approachFacing
) {
    public TeleportContextData {
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
