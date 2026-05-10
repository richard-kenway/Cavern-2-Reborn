package com.richardkenway.cavernreborn.app.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeEntry;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeTopFilterPolicy;

public final class CaveniaWeightedBiomeSelectionAlgorithm {
    private static final List<CaveniaWeightedBiomeSelectionEntry> ENTRIES = buildEntries();

    private CaveniaWeightedBiomeSelectionAlgorithm() {
    }

    public static List<CaveniaWeightedBiomeSelectionEntry> entries() {
        return ENTRIES;
    }

    public static List<String> legacyBiomeNames() {
        return ENTRIES.stream()
            .map(CaveniaWeightedBiomeSelectionEntry::legacyBiomeName)
            .toList();
    }

    public static List<String> candidateModernBiomeKeys() {
        return ENTRIES.stream()
            .map(CaveniaWeightedBiomeSelectionEntry::candidateModernBiomeKey)
            .toList();
    }

    public static Optional<CaveniaWeightedBiomeSelectionEntry> entryForLegacyBiomeName(String legacyBiomeName) {
        return ENTRIES.stream()
            .filter(entry -> entry.legacyBiomeName().equals(legacyBiomeName))
            .findFirst();
    }

    public static Optional<CaveniaWeightedBiomeSelectionEntry> entryForWeightValue(int weightValue) {
        int normalizedWeightValue = Math.floorMod(weightValue, totalWeight());

        return ENTRIES.stream()
            .filter(entry ->
                entry.inclusiveStartWeight() <= normalizedWeightValue
                    && normalizedWeightValue < entry.exclusiveEndWeight()
            )
            .findFirst();
    }

    public static CaveniaWeightedBiomeSelectionResult selectByWeightValue(int inputValue) {
        int normalizedWeightValue = Math.floorMod(inputValue, totalWeight());
        CaveniaWeightedBiomeSelectionEntry entry = entryForWeightValue(inputValue)
            .orElseThrow(() -> new IllegalStateException("Expected a weighted biome entry for " + normalizedWeightValue));

        return new CaveniaWeightedBiomeSelectionResult(
            inputValue,
            normalizedWeightValue,
            entry.legacyBiomeName(),
            entry.candidateModernBiomeKey(),
            entry.weight(),
            entry.finalRuntimeMapping(),
            entry.registryVerified(),
            entry.runtimeReady()
        );
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static int totalWeight() {
        return CaveniaBiomeTopFilterPolicy.totalWeight();
    }

    public static int firstWeightValue() {
        return 0;
    }

    public static int lastWeightValue() {
        return totalWeight() - 1;
    }

    public static boolean allEntriesHavePositiveWeights() {
        return ENTRIES.stream().allMatch(entry -> entry.weight() > 0);
    }

    public static boolean allWeightRangesContiguous() {
        if (ENTRIES.isEmpty()) {
            return false;
        }

        if (ENTRIES.get(0).inclusiveStartWeight() != firstWeightValue()) {
            return false;
        }

        for (int i = 1; i < ENTRIES.size(); i++) {
            if (ENTRIES.get(i - 1).exclusiveEndWeight() != ENTRIES.get(i).inclusiveStartWeight()) {
                return false;
            }
        }

        return ENTRIES.get(ENTRIES.size() - 1).exclusiveEndWeight() == totalWeight();
    }

    public static boolean allLegacyBiomesHaveCandidateKeys() {
        return legacyBiomeNames().equals(CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeNames());
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean weightedSelectionAlgorithmReady() {
        return allEntriesHavePositiveWeights() && allWeightRangesContiguous() && allLegacyBiomesHaveCandidateKeys();
    }

    public static boolean weightedSelectionAlgorithmRuntimeReady() {
        return false;
    }

    public static boolean anyFinalRuntimeMapping() {
        return ENTRIES.stream().anyMatch(CaveniaWeightedBiomeSelectionEntry::finalRuntimeMapping);
    }

    public static boolean anyRegistryVerified() {
        return ENTRIES.stream().anyMatch(CaveniaWeightedBiomeSelectionEntry::registryVerified);
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean modernBiomeMappingReady() {
        return false;
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

    private static List<CaveniaWeightedBiomeSelectionEntry> buildEntries() {
        List<CaveniaWeightedBiomeSelectionEntry> entries = new ArrayList<>();
        int startWeight = 0;

        for (CaveniaBiomeEntry biomeEntry : CaveniaBiomeTopFilterPolicy.entries()) {
            String candidateModernBiomeKey = CaveniaLegacyToModernBiomeKeyMappings
                .candidateKeyForLegacyBiomeName(biomeEntry.legacyBiomeName())
                .orElseThrow(() -> new IllegalStateException("Expected candidate modern biome key for " + biomeEntry.legacyBiomeName()));
            int endWeight = startWeight + biomeEntry.weight();

            entries.add(new CaveniaWeightedBiomeSelectionEntry(
                biomeEntry.legacyBiomeName(),
                candidateModernBiomeKey,
                biomeEntry.weight(),
                startWeight,
                endWeight,
                true,
                false,
                false,
                false
            ));

            startWeight = endWeight;
        }

        return List.copyOf(entries);
    }
}
