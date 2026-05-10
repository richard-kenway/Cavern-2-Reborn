package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaLegacyToModernBiomeKeyMappings {
    private static final List<CaveniaLegacyToModernBiomeKeyMappingEntry> ENTRIES = List.of(
        entry(
            "OCEAN",
            "minecraft:ocean",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy OCEAN still points at the direct modern ocean candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "PLAINS",
            "minecraft:plains",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy PLAINS still points at the direct modern plains candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "DESERT",
            "minecraft:desert",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy DESERT still points at the direct modern desert candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "DESERT_HILLS",
            "minecraft:desert",
            CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT,
            "Legacy DESERT_HILLS is inventoried as the parent desert candidate because this slice does not choose a separate modern hill mapping."
        ),
        entry(
            "FOREST",
            "minecraft:forest",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy FOREST still points at the direct modern forest candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "FOREST_HILLS",
            "minecraft:forest",
            CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT,
            "Legacy FOREST_HILLS is inventoried as the parent forest candidate because this slice does not choose a separate modern hill mapping."
        ),
        entry(
            "TAIGA",
            "minecraft:taiga",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy TAIGA still points at the direct modern taiga candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "TAIGA_HILLS",
            "minecraft:taiga",
            CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT,
            "Legacy TAIGA_HILLS is inventoried as the parent taiga candidate because this slice does not choose a separate modern hill mapping."
        ),
        entry(
            "JUNGLE",
            "minecraft:jungle",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy JUNGLE still points at the direct modern jungle candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "JUNGLE_HILLS",
            "minecraft:jungle",
            CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT,
            "Legacy JUNGLE_HILLS is inventoried as the parent jungle candidate because this slice does not choose a separate modern hill mapping."
        ),
        entry(
            "SWAMPLAND",
            "minecraft:swamp",
            CaveniaBiomeKeyMappingKind.RENAMED,
            "Legacy SWAMPLAND is inventoried as the renamed modern swamp candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "EXTREME_HILLS",
            "minecraft:windswept_hills",
            CaveniaBiomeKeyMappingKind.RENAMED,
            "Legacy EXTREME_HILLS is inventoried as the renamed modern windswept hills candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "SAVANNA",
            "minecraft:savanna",
            CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT,
            "Legacy SAVANNA still points at the direct modern savanna candidate, but this slice does not verify runtime registry access."
        ),
        entry(
            "MESA",
            "minecraft:badlands",
            CaveniaBiomeKeyMappingKind.RENAMED,
            "Legacy MESA is inventoried as the renamed modern badlands candidate, but this slice does not verify runtime registry access."
        )
    );

    private CaveniaLegacyToModernBiomeKeyMappings() {
    }

    public static List<CaveniaLegacyToModernBiomeKeyMappingEntry> entries() {
        return ENTRIES;
    }

    public static List<String> legacyBiomeNames() {
        return ENTRIES.stream()
            .map(CaveniaLegacyToModernBiomeKeyMappingEntry::legacyBiomeName)
            .toList();
    }

    public static List<String> candidateModernBiomeKeys() {
        return ENTRIES.stream()
            .map(CaveniaLegacyToModernBiomeKeyMappingEntry::candidateModernBiomeKey)
            .toList();
    }

    public static Optional<CaveniaLegacyToModernBiomeKeyMappingEntry> entryForLegacyBiomeName(String legacyBiomeName) {
        return ENTRIES.stream()
            .filter(entry -> entry.legacyBiomeName().equals(legacyBiomeName))
            .findFirst();
    }

    public static Optional<String> candidateKeyForLegacyBiomeName(String legacyBiomeName) {
        return entryForLegacyBiomeName(legacyBiomeName)
            .map(CaveniaLegacyToModernBiomeKeyMappingEntry::candidateModernBiomeKey);
    }

    public static Optional<CaveniaBiomeKeyMappingKind> mappingKindForLegacyBiomeName(String legacyBiomeName) {
        return entryForLegacyBiomeName(legacyBiomeName)
            .map(CaveniaLegacyToModernBiomeKeyMappingEntry::mappingKind);
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static int legacyBiomeEntryCount() {
        return CaveniaBiomeSourceStrategyPlan.legacyBiomeEntryCount();
    }

    public static int legacyBiomeTotalWeight() {
        return CaveniaBiomeSourceStrategyPlan.legacyBiomeTotalWeight();
    }

    public static boolean allLegacyBiomesHaveCandidates() {
        return legacyBiomeNames().equals(CaveniaBiomeSourceStrategyPlan.legacyBiomeNames());
    }

    public static boolean allCandidatesInventoried() {
        return ENTRIES.stream().allMatch(CaveniaLegacyToModernBiomeKeyMappingEntry::candidateInventoried);
    }

    public static boolean anyFinalRuntimeMapping() {
        return ENTRIES.stream().anyMatch(CaveniaLegacyToModernBiomeKeyMappingEntry::finalRuntimeMapping);
    }

    public static boolean allFinalRuntimeMappingsReady() {
        return ENTRIES.stream().allMatch(CaveniaLegacyToModernBiomeKeyMappingEntry::runtimeReady);
    }

    public static boolean anyRegistryVerified() {
        return ENTRIES.stream().anyMatch(CaveniaLegacyToModernBiomeKeyMappingEntry::registryVerified);
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean modernBiomeMappingReady() {
        return false;
    }

    public static boolean candidateInventoryReady() {
        return allLegacyBiomesHaveCandidates() && allCandidatesInventoried();
    }

    public static boolean planIsNonRuntime() {
        return CaveniaBiomeSourceStrategyPlan.planIsNonRuntime();
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaBiomeSourceStrategyPlan.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaBiomeSourceStrategyPlan.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaBiomeSourceStrategyPlan.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaLegacyToModernBiomeKeyMappingEntry entry(
        String legacyBiomeName,
        String candidateModernBiomeKey,
        CaveniaBiomeKeyMappingKind mappingKind,
        String rationale
    ) {
        return new CaveniaLegacyToModernBiomeKeyMappingEntry(
            legacyBiomeName,
            candidateModernBiomeKey,
            mappingKind,
            true,
            false,
            false,
            false,
            rationale
        );
    }
}
