package com.richardkenway.cavernreborn.core.worldgen;

import java.util.Objects;

public enum CaveniaCaveCarverReplacement {
    GRAVEL("minecraft:gravel"),
    WATER("minecraft:water"),
    AIR("minecraft:air");

    private final String blockId;

    CaveniaCaveCarverReplacement(String blockId) {
        this.blockId = Objects.requireNonNull(blockId, "blockId");
    }

    public String blockId() {
        return blockId;
    }
}
