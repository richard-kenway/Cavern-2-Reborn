package com.richardkenway.cavernreborn.core.progression;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

public final class CavernProgressionPolicy {
    private static final Map<String, Integer> SCORE_BY_BLOCK = createScoreByBlock();

    private CavernProgressionPolicy() {
    }

    public static boolean countsTowardProgression(String dimensionId, String blockId) {
        return CavernDimensions.isCavern(requireText(dimensionId, "dimensionId"))
            && SCORE_BY_BLOCK.containsKey(requireText(blockId, "blockId"));
    }

    public static int scoreForBlock(String blockId) {
        return SCORE_BY_BLOCK.getOrDefault(requireText(blockId, "blockId"), 0);
    }

    public static Map<String, Integer> scoreByBlock() {
        return SCORE_BY_BLOCK;
    }

    private static Map<String, Integer> createScoreByBlock() {
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("minecraft:coal_ore", 1);
        scores.put("minecraft:deepslate_coal_ore", 1);
        scores.put("minecraft:copper_ore", 1);
        scores.put("minecraft:deepslate_copper_ore", 1);
        scores.put("minecraft:iron_ore", 2);
        scores.put("minecraft:deepslate_iron_ore", 2);
        scores.put("minecraft:redstone_ore", 2);
        scores.put("minecraft:deepslate_redstone_ore", 2);
        scores.put("minecraft:gold_ore", 3);
        scores.put("minecraft:deepslate_gold_ore", 3);
        scores.put("minecraft:lapis_ore", 3);
        scores.put("minecraft:deepslate_lapis_ore", 3);
        scores.put("minecraft:raw_copper_block", 4);
        scores.put("minecraft:diamond_ore", 5);
        scores.put("minecraft:deepslate_diamond_ore", 5);
        scores.put("minecraft:emerald_ore", 6);
        scores.put("minecraft:deepslate_emerald_ore", 6);
        scores.put("minecraft:raw_iron_block", 6);
        return Collections.unmodifiableMap(scores);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
