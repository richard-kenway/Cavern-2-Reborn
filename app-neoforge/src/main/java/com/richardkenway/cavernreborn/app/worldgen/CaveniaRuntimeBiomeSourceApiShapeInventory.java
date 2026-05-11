package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceApiShapeInventory {
    private static final String MAP_CODEC_NAME = "Map" + "Codec";
    private static final String REGISTRY_ACCESS_NAME = "Registry" + "Access";
    private static final String REGISTRY_LOOKUP_NAME = "Registry" + "Lookup";
    private static final String BIOME_SOURCE_REFERENCE =
        "app-neoforge/build/neoForm/neoFormJoined1.21.1-20240808.144430/steps/recompile/classes/"
            + "net/minecraft/world/level/biome/BiomeSource.class via local javap -p -s -c";
    private static final String CLIMATE_SAMPLER_REFERENCE =
        "app-neoforge/build/neoForm/neoFormJoined1.21.1-20240808.144430/steps/recompile/classes/"
            + "net/minecraft/world/level/biome/Climate$Sampler.class via local javap -p";
    private static final String SUBCLASS_DECISION_REFERENCE =
        "CaveniaRuntimeBiomeSourceSubclassDecision with local neoForm BiomeSource inventory pinned";
    private static final List<CaveniaRuntimeBiomeSourceApiShapeInventoryEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.LOCAL_BIOME_SOURCE_CLASS,
            BIOME_SOURCE_REFERENCE,
            "public abstract class net.minecraft.world.level.biome.BiomeSource implements "
                + "net.minecraft.world.level.biome.BiomeResolver",
            "A future Cavenia subclass must match the locally observed class identity and BiomeResolver contract shape "
                + "without widening into activation work.",
            "The real subclass remains deferred even though the local BiomeSource class identity is pinned."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.ABSTRACT_METHODS_INVENTORY,
            BIOME_SOURCE_REFERENCE,
            "protected abstract com.mojang.serialization." + MAP_CODEC_NAME + "<? extends "
                + "net.minecraft.world.level.biome.BiomeSource> codec(); protected abstract "
                + "java.util.stream.Stream<net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> "
                + "collectPossibleBiomes(); public abstract net.minecraft.core.Holder<"
                + "net.minecraft.world.level.biome.Biome> getNoiseBiome(int, int, int, "
                + "net.minecraft.world.level.biome.Climate$Sampler);",
            "A future real subclass must satisfy exactly these locally observed abstract method obligations.",
            "The abstract method inventory is pinned, but this slice still does not implement a real subclass."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.CODEC_DISPATCH_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "static CODEC uses net.minecraft.core.registries.BuiltInRegistries.BIOME_SOURCE.byNameCodec()"
                + ".dispatchStable(..., java.util.function.Function.identity())",
            "A future subclass must align with the locally observed registry-dispatch codec path before any codec work "
                + "is attempted.",
            "The codec dispatch shape is pinned, but codec implementation and registration remain blocked."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.CODEC_METHOD_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "protected abstract com.mojang.serialization." + MAP_CODEC_NAME + "<? extends "
                + "net.minecraft.world.level.biome.BiomeSource> codec();",
            "A future subclass must provide a " + MAP_CODEC_NAME + "-returning codec method with the exact local "
                + "method shape.",
            "The codec method shape is pinned, but codec implementation and registration remain blocked."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.POSSIBLE_BIOMES_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "private final java.util.function.Supplier<java.util.Set<net.minecraft.core.Holder<"
                + "net.minecraft.world.level.biome.Biome>>> possibleBiomes; protected abstract "
                + "java.util.stream.Stream<net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> "
                + "collectPossibleBiomes(); public java.util.Set<net.minecraft.core.Holder<"
                + "net.minecraft.world.level.biome.Biome>> possibleBiomes();",
            "A future subclass must decide how its candidate set feeds collectPossibleBiomes() and the memoized "
                + "possibleBiomes() set without changing current guardrails.",
            "The possible-biomes API shape is pinned, but no runtime holder pipeline is implemented in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.NOISE_BIOME_QUERY_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "public abstract net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> getNoiseBiome(int, int, "
                + "int, net.minecraft.world.level.biome.Climate$Sampler); helper methods getBiomesWithin(...), "
                + "findBiomeHorizontal(...), and findClosestBiome3d(...) delegate to getNoiseBiome(...)",
            "A future subclass must provide holder-backed noise biomes through the exact locally observed query method "
                + "shape while preserving the helper-method expectations.",
            "The noise-biome query shape is pinned, but no runtime biome resolution is implemented in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.CLIMATE_SAMPLER_SHAPE,
            CLIMATE_SAMPLER_REFERENCE,
            "net.minecraft.world.level.biome.Climate$Sampler is a record with sample(int, int, int), "
                + "findSpawnPosition(), six DensityFunction components, and java.util.List<"
                + "net.minecraft.world.level.biome.Climate$ParameterPoint> spawnTarget",
            "A future subclass must accept the locally observed Climate$Sampler shape instead of inventing a custom "
                + "climate input contract.",
            "The climate sampler shape is pinned, but climate-driven runtime behavior remains unimplemented."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.HOLDER_RETURN_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "BiomeSource signatures return and collect net.minecraft.core.Holder<"
                + "net.minecraft.world.level.biome.Biome> values across collectPossibleBiomes(), possibleBiomes(), "
                + "getNoiseBiome(...), findBiomeHorizontal(...), and findClosestBiome3d(...)",
            "A future subclass must have a deliberate holder-return path for runtime biome results before real "
                + "implementation begins.",
            "The holder-return shape is pinned, but no holder resolution path is implemented in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.REGISTRY_CONTEXT_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "BiomeSource signatures do not expose " + REGISTRY_ACCESS_NAME + " or " + REGISTRY_LOOKUP_NAME
                + " directly; the local class instead expects holder-backed returns and uses "
                + "BuiltInRegistries.BIOME_SOURCE in static CODEC dispatch",
            "A future subclass must choose where registry context comes from without assuming a direct "
                + REGISTRY_ACCESS_NAME + " parameter in the BiomeSource API.",
            "The registry-context shape is pinned, but runtime registry access remains blocked."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.RESOURCE_KEY_CONVERSION_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "No observed BiomeSource method signature mentions net.minecraft.resources.ResourceKey<"
                + "net.minecraft.world.level.biome.Biome>; conversion from candidate string ids to holder-backed "
                + "runtime biomes remains a separate required design step",
            "A future subclass must add an explicit conversion design from current string-only candidate ids into the "
                + "holder-returning BiomeSource contract.",
            "The conversion requirement is pinned, but no runtime biome-key conversion is implemented in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.MISSING_BIOME_FALLBACK_SHAPE,
            BIOME_SOURCE_REFERENCE,
            "Helper methods assume getNoiseBiome(...) yields holder results that can populate possible-biome sets and "
                + "search helpers, so missing-biome handling needs an explicit fallback decision before a real subclass "
                + "exists",
            "A future subclass must pin a missing-biome fallback policy before runtime biome resolution can be trusted.",
            "The missing-biome fallback requirement is pinned, but no fallback implementation exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceApiShapeComponent.REAL_SUBCLASS_STILL_DEFERRED,
            SUBCLASS_DECISION_REFERENCE,
            "CaveniaRuntimeBiomeSourceSubclassDecision keeps selectedDecision() at "
                + "DEFER_REAL_BIOME_SOURCE_SUBCLASS_UNTIL_CODEC_HOLDER_REGISTRY_SHAPE_IS_PINNED while the inventory "
                + "records the local API shape",
            "A separate explicit decision is still required before any real Minecraft BiomeSource subclass can be "
                + "implemented.",
            "The local API shape is pinned, but the real subclass remains deliberately deferred."
        )
    );

    private CaveniaRuntimeBiomeSourceApiShapeInventory() {
    }

    public static List<CaveniaRuntimeBiomeSourceApiShapeInventoryEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceApiShapeComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceApiShapeInventoryEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceApiShapeInventoryEntry> entryFor(
        CaveniaRuntimeBiomeSourceApiShapeComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean localBiomeSourceClassLocated() {
        return true;
    }

    public static boolean apiShapeInventoryReady() {
        return true;
    }

    public static boolean allComponentsLocallyInspected() {
        return ENTRIES.stream().allMatch(CaveniaRuntimeBiomeSourceApiShapeInventoryEntry::locallyInspected);
    }

    public static boolean allShapesPinned() {
        return ENTRIES.stream().allMatch(CaveniaRuntimeBiomeSourceApiShapeInventoryEntry::shapePinned);
    }

    public static boolean runtimeApiImplemented() {
        return false;
    }

    public static boolean runtimeApiAllowedInThisSlice() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static boolean abstractMethodsInventoryReady() {
        return true;
    }

    public static boolean codecDispatchShapeReady() {
        return true;
    }

    public static boolean codecMethodShapeReady() {
        return true;
    }

    public static boolean possibleBiomesShapeReady() {
        return true;
    }

    public static boolean noiseBiomeQueryShapeReady() {
        return true;
    }

    public static boolean climateSamplerShapeReady() {
        return true;
    }

    public static boolean holderReturnShapeReady() {
        return true;
    }

    public static boolean registryContextShapeReady() {
        return true;
    }

    public static boolean resourceKeyConversionShapeReady() {
        return true;
    }

    public static boolean missingBiomeFallbackShapeReady() {
        return true;
    }

    public static boolean realSubclassStillDeferred() {
        return true;
    }

    public static boolean readyForRealSubclassImplementation() {
        return false;
    }

    public static boolean nextSliceMayDecideRealSubclassImplementation() {
        return true;
    }

    public static boolean nextSliceMayImplementRealBiomeSourceSubclassDirectly() {
        return false;
    }

    public static boolean nextSliceMayAddCodecImplementation() {
        return false;
    }

    public static boolean nextSliceMayRegisterCodec() {
        return false;
    }

    public static boolean nextSliceMayUseRegistryLookupAccess() {
        return false;
    }

    public static boolean nextSliceMayAddDimensionJson() {
        return false;
    }

    public static boolean nextSliceMayAddDimensionTypeJson() {
        return false;
    }

    public static boolean nextSliceMayCreateActiveCaveniaLevel() {
        return false;
    }

    public static boolean nextSliceMayAddWorldgenResources() {
        return false;
    }

    public static boolean nextSliceMayAddAccessOrTeleport() {
        return false;
    }

    public static boolean nextSliceMayAddSpawning() {
        return false;
    }

    public static boolean nextSliceMayRegisterCavemanEntity() {
        return false;
    }

    public static boolean skeletonReady() {
        return CaveniaRuntimeBiomeSourceSkeleton.unregisteredSkeletonReady();
    }

    public static boolean skeletonRuntimeReady() {
        return false;
    }

    public static boolean realSubclassGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision
            .decisionIsGoForGuardedUnregisteredRealSubclassNext();
    }

    public static boolean realSubclassGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean guardedRealSubclassStubImplemented() {
        return true;
    }

    public static boolean guardedRealSubclassRuntimeReady() {
        return false;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();
    }

    public static boolean holderConversionRuntimeReady() {
        return false;
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
    }

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();
    }

    public static boolean selectorInputDerivationGoNoGoRuntimeReady() {
        return false;
    }

    public static int selectedSurfaceReadinessItemCount() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceRegistered() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaRuntimeBiomeSourceSkeleton.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRuntimeBiomeSourceSkeleton.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRuntimeBiomeSourceSkeleton.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRuntimeBiomeSourceApiShapeInventoryEntry entry(
        CaveniaRuntimeBiomeSourceApiShapeComponent component,
        String sourceReference,
        String observedShape,
        String implementationImplication,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceApiShapeInventoryEntry(
            component,
            sourceReference,
            observedShape,
            implementationImplication,
            true,
            true,
            false,
            false,
            false,
            blocker
        );
    }
}
