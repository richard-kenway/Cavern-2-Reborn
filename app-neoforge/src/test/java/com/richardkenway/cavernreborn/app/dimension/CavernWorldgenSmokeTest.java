package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernWorldgenSmokeTest {
    private static final int[] SAMPLE_XZ = {-96, -48, 0, 48, 96};
    private static final int[] HOT_BAND_Y = {-56, -48, -40};
    private static final int[] MID_BAND_Y = {0, 24, 48};
    private static final int[] ARRIVAL_BAND_Y = {64, 80};
    private static final int[] UPPER_BAND_Y = {96, 112};

    @Test
    void finalDensitySamplingStaysMixedAndVerticallyDifferentiated() throws IOException {
        JsonObject containedCaves = readJsonResource("data/cavernreborn/worldgen/noise_settings/contained_caves.json");
        DensityEvaluator evaluator = new DensityEvaluator(
            containedCaves.getAsJsonObject("noise_router").getAsJsonObject("final_density")
        );

        BandProfile hotBand = sampleBand(evaluator, HOT_BAND_Y);
        BandProfile midBand = sampleBand(evaluator, MID_BAND_Y);
        BandProfile arrivalBand = sampleBand(evaluator, ARRIVAL_BAND_Y);
        BandProfile upperBand = sampleBand(evaluator, UPPER_BAND_Y);

        assertTrue(midBand.solidRatio() > 0.20D && midBand.solidRatio() < 0.90D, () -> "midBand=" + midBand);
        assertTrue(upperBand.solidRatio() > 0.10D && upperBand.solidRatio() < 0.85D, () -> "upperBand=" + upperBand);
        assertTrue(midBand.voidRatio() > 0.10D, () -> "midBand=" + midBand);
        assertTrue(upperBand.voidRatio() > 0.15D, () -> "upperBand=" + upperBand);

        assertTrue(hotBand.solidRatio() > midBand.solidRatio(), () -> "hot=" + hotBand + ", mid=" + midBand);
        assertTrue(upperBand.solidRatio() > arrivalBand.solidRatio(), () -> "upper=" + upperBand + ", arrival=" + arrivalBand);
        assertTrue(Math.abs(upperBand.solidRatio() - hotBand.solidRatio()) > 0.08D, () -> "hot=" + hotBand + ", upper=" + upperBand);
        assertTrue(Math.abs(midBand.solidRatio() - arrivalBand.solidRatio()) > 0.05D, () -> "mid=" + midBand + ", arrival=" + arrivalBand);
    }

    @Test
    void surfaceRulesKeepHotBandBelowArrivalSearchFloor() throws Exception {
        JsonObject containedCaves = readJsonResource("data/cavernreborn/worldgen/noise_settings/contained_caves.json");
        JsonObject surfaceRule = containedCaves.getAsJsonObject("surface_rule");
        SurfaceRuleEvaluator evaluator = new SurfaceRuleEvaluator(surfaceRule);
        int arrivalSearchFloor = CavernDimensions.CAVERN_ENTRY_Y - verticalSearchWindow();

        String hotFloor = evaluator.resolveFloor("cavernreborn:stone_depths", -56);
        String transitionFloor = evaluator.resolveFloor("cavernreborn:stone_depths", -40);
        String safeFloor = evaluator.resolveFloor("cavernreborn:stone_depths", arrivalSearchFloor);
        String hotCeiling = evaluator.resolveCeiling("cavernreborn:stone_depths", -56);
        String safeCeiling = evaluator.resolveCeiling("cavernreborn:stone_depths", arrivalSearchFloor);
        String upperHighlandFloor = evaluator.resolveFloor("cavernreborn:highland_hollows", 48);
        String lowerHighlandFloor = evaluator.resolveFloor("cavernreborn:highland_hollows", 8);

        assertEquals("minecraft:magma_block", hotFloor);
        assertEquals("minecraft:basalt", transitionFloor);
        assertEquals("minecraft:basalt", hotCeiling);
        assertNotNull(safeFloor, () -> "safeFloor did not resolve at y=" + arrivalSearchFloor);
        assertNotNull(safeCeiling, () -> "safeCeiling did not resolve at y=" + arrivalSearchFloor);
        assertFalse(isHotBandBlock(safeFloor), () -> "safeFloor=" + safeFloor);
        assertFalse(isHotBandBlock(safeCeiling), () -> "safeCeiling=" + safeCeiling);
        assertEquals("minecraft:calcite", upperHighlandFloor);
        assertEquals("minecraft:tuff", lowerHighlandFloor);
        assertNotEquals(hotFloor, safeFloor);
        assertTrue(-32 < CavernDimensions.MIN_SAFE_Y);
        assertTrue(-32 < CavernDimensions.CAVERN_ENTRY_Y);
        assertTrue(-32 < arrivalSearchFloor, () -> "arrivalSearchFloor=" + arrivalSearchFloor);
    }

    private static BandProfile sampleBand(DensityEvaluator evaluator, int[] ys) {
        int totalSamples = 0;
        int solidSamples = 0;

        for (int y : ys) {
            for (int x : SAMPLE_XZ) {
                for (int z : SAMPLE_XZ) {
                    totalSamples++;
                    if (evaluator.compute(x, y, z) > 0.0D) {
                        solidSamples++;
                    }
                }
            }
        }

        return new BandProfile(solidSamples, totalSamples);
    }

    private static boolean isHotBandBlock(String blockId) {
        return "minecraft:magma_block".equals(blockId)
            || "minecraft:basalt".equals(blockId)
            || "minecraft:blackstone".equals(blockId);
    }

    private static int verticalSearchWindow() throws Exception {
        Field field = CavernArrivalPlacementResolver.class.getDeclaredField("VERTICAL_SEARCH_WINDOW");
        field.setAccessible(true);
        return field.getInt(null);
    }

    private static JsonObject readJsonResource(String resourcePath) throws IOException {
        ClassLoader classLoader = CavernWorldgenSmokeTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalStateException("Missing test resource: " + resourcePath);
        }

        try (InputStream stream = inputStream) {
            String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            return JsonParser.parseString(json).getAsJsonObject();
        }
    }

    private record BandProfile(int solidSamples, int totalSamples) {
        private double solidRatio() {
            return (double) solidSamples / totalSamples;
        }

        private double voidRatio() {
            return 1.0D - solidRatio();
        }
    }

    private static final class DensityEvaluator {
        private final JsonObject root;
        private final Map<String, JsonObject> densityFunctionCache = new HashMap<>();

        private DensityEvaluator(JsonObject root) {
            this.root = root;
        }

        private double compute(int x, int y, int z) {
            return evaluate(root, x, y, z);
        }

        private double evaluate(JsonElement element, int x, int y, int z) {
            if (element == null || element.isJsonNull()) {
                return 0.0D;
            }
            if (element.isJsonPrimitive()) {
                if (element.getAsJsonPrimitive().isNumber()) {
                    return element.getAsDouble();
                }
                return referencedValue(element.getAsString(), x, y, z);
            }

            JsonObject object = element.getAsJsonObject();
            String type = object.get("type").getAsString();

            return switch (type) {
                case "minecraft:add" -> evaluate(object.get("argument1"), x, y, z)
                    + evaluate(object.get("argument2"), x, y, z);
                case "minecraft:mul" -> evaluate(object.get("argument1"), x, y, z)
                    * evaluate(object.get("argument2"), x, y, z);
                case "minecraft:min" -> Math.min(
                    evaluate(object.get("argument1"), x, y, z),
                    evaluate(object.get("argument2"), x, y, z)
                );
                case "minecraft:max" -> Math.max(
                    evaluate(object.get("argument1"), x, y, z),
                    evaluate(object.get("argument2"), x, y, z)
                );
                case "minecraft:cache_once", "minecraft:interpolated", "minecraft:blend_density" ->
                    evaluate(object.get("argument"), x, y, z);
                case "minecraft:squeeze" -> squeeze(evaluate(object.get("argument"), x, y, z));
                case "minecraft:y_clamped_gradient" -> yClampedGradient(object, y);
                case "minecraft:range_choice" -> rangeChoice(object, x, y, z);
                default -> throw new IllegalStateException("Unsupported density type in smoke test: " + type);
            };
        }

        private double rangeChoice(JsonObject object, int x, int y, int z) {
            double input = evaluate(object.get("input"), x, y, z);
            double minInclusive = object.get("min_inclusive").getAsDouble();
            double maxExclusive = object.get("max_exclusive").getAsDouble();
            JsonElement selected = input >= minInclusive && input < maxExclusive
                ? object.get("when_in_range")
                : object.get("when_out_of_range");
            return evaluate(selected, x, y, z);
        }

        private double referencedValue(String reference, int x, int y, int z) {
            return switch (reference) {
                case "minecraft:y" -> y;
                case "minecraft:overworld/caves/spaghetti_2d" -> oscillate(x, y, z, 0.071D, 0.031D, 0.067D, 0.2D);
                case "minecraft:overworld/caves/spaghetti_roughness_function" ->
                    -0.18D + 0.22D * oscillate(x, y, z, 0.043D, 0.059D, 0.047D, 1.3D);
                case "minecraft:overworld/caves/entrances" -> 0.72D * oscillate(x, y, z, 0.037D, 0.073D, 0.041D, 2.1D);
                case "minecraft:overworld/caves/pillars" ->
                    -0.12D + 0.42D * oscillate(x, y, z, 0.021D, 0.019D, 0.024D, 0.7D);
                case "minecraft:overworld/caves/noodle" -> 0.48D * oscillate(x, y, z, 0.053D, 0.081D, 0.049D, 1.9D);
                case "minecraft:nether/base_3d_noise" ->
                    0.38D * oscillate(x, y, z, 0.028D, 0.034D, 0.031D, 0.5D)
                        + 0.16D * oscillate(x, y, z, 0.061D, 0.017D, 0.057D, 2.7D);
                default -> {
                    if (reference.startsWith("cavernreborn:")) {
                        yield evaluate(loadDensityFunction(reference), x, y, z);
                    }
                    throw new IllegalStateException("Unsupported density reference in smoke test: " + reference);
                }
            };
        }

        private JsonObject loadDensityFunction(String reference) {
            return densityFunctionCache.computeIfAbsent(reference, key -> {
                String resourcePath = "data/cavernreborn/worldgen/density_function/"
                    + key.substring("cavernreborn:".length())
                    + ".json";
                try {
                    return readJsonResource(resourcePath);
                } catch (IOException exception) {
                    throw new IllegalStateException("Missing density resource for smoke test: " + key, exception);
                }
            });
        }

        private static double yClampedGradient(JsonObject object, int y) {
            int fromY = object.get("from_y").getAsInt();
            int toY = object.get("to_y").getAsInt();
            double fromValue = object.get("from_value").getAsDouble();
            double toValue = object.get("to_value").getAsDouble();

            if (y <= fromY) {
                return fromValue;
            }
            if (y >= toY) {
                return toValue;
            }

            double alpha = (double) (y - fromY) / (double) (toY - fromY);
            return fromValue + (toValue - fromValue) * alpha;
        }

        private static double squeeze(double value) {
            double squeezed = value / 2.0D - (value * value * value) / 24.0D;
            return clamp(squeezed, -1.0D, 1.0D);
        }

        private static double oscillate(int x, int y, int z, double sx, double sy, double sz, double phase) {
            double wave = StrictMath.sin(x * sx + y * sy + z * sz + phase)
                + 0.5D * StrictMath.cos(x * sx * 0.73D - y * sy * 1.19D + z * sz * 0.81D - phase * 0.5D);
            return clamp(wave / 1.5D, -1.0D, 1.0D);
        }

        private static double clamp(double value, double min, double max) {
            return Math.max(min, Math.min(max, value));
        }
    }

    private static final class SurfaceRuleEvaluator {
        private final JsonObject floorRule;
        private final JsonObject ceilingRule;

        private SurfaceRuleEvaluator(JsonObject surfaceRule) {
            JsonArray topSequence = surfaceRule.getAsJsonArray("sequence");
            this.floorRule = findSurfaceRule(topSequence, "floor");
            this.ceilingRule = findSurfaceRule(topSequence, "ceiling");
        }

        private String resolveFloor(String biomeId, int y) {
            return resolveBlock(floorRule, new SurfaceContext(biomeId, y, "floor"));
        }

        private String resolveCeiling(String biomeId, int y) {
            return resolveBlock(ceilingRule, new SurfaceContext(biomeId, y, "ceiling"));
        }

        private static JsonObject findSurfaceRule(JsonArray rules, String surfaceType) {
            for (JsonElement element : rules) {
                JsonObject candidate = element.getAsJsonObject();
                if (!candidate.has("if_true")) {
                    continue;
                }

                JsonObject predicate = candidate.getAsJsonObject("if_true");
                if (!"minecraft:stone_depth".equals(predicate.get("type").getAsString())) {
                    continue;
                }
                if (surfaceType.equals(predicate.get("surface_type").getAsString())) {
                    return candidate.getAsJsonObject("then_run");
                }
            }

            throw new IllegalStateException("Missing surface rule for " + surfaceType);
        }

        private static String resolveBlock(JsonObject rule, SurfaceContext context) {
            String type = rule.get("type").getAsString();

            return switch (type) {
                case "minecraft:sequence" -> resolveSequence(rule.getAsJsonArray("sequence"), context);
                case "minecraft:condition" -> evaluatePredicate(rule.getAsJsonObject("if_true"), context)
                    ? resolveBlock(rule.getAsJsonObject("then_run"), context)
                    : null;
                case "minecraft:block" -> rule.getAsJsonObject("result_state").get("Name").getAsString();
                default -> throw new IllegalStateException("Unsupported surface rule type in smoke test: " + type);
            };
        }

        private static String resolveSequence(JsonArray sequence, SurfaceContext context) {
            for (JsonElement element : sequence) {
                String resolved = resolveBlock(element.getAsJsonObject(), context);
                if (resolved != null) {
                    return resolved;
                }
            }
            return null;
        }

        private static boolean evaluatePredicate(JsonObject predicate, SurfaceContext context) {
            String type = predicate.get("type").getAsString();

            return switch (type) {
                case "minecraft:stone_depth" -> context.surfaceType().equals(predicate.get("surface_type").getAsString());
                case "minecraft:biome" -> {
                    JsonArray biomeIds = predicate.getAsJsonArray("biome_is");
                    boolean matches = false;
                    for (JsonElement element : biomeIds) {
                        if (context.biomeId().equals(element.getAsString())) {
                            matches = true;
                            break;
                        }
                    }
                    yield matches;
                }
                case "minecraft:not" -> !evaluatePredicate(predicate.getAsJsonObject("invert"), context);
                case "minecraft:y_above" -> context.y() >= predicate.getAsJsonObject("anchor").get("absolute").getAsInt();
                default -> throw new IllegalStateException("Unsupported surface predicate in smoke test: " + type);
            };
        }
    }

    private record SurfaceContext(String biomeId, int y, String surfaceType) {
    }
}
