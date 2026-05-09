package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeEntry;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeTopFilterPolicy;

public final class CaveniaBiomeSelectionSkeleton {
    private static final CaveniaBiomeSelectionSkeleton INSTANCE = new CaveniaBiomeSelectionSkeleton();
    private static final List<String> LEGACY_BIOME_NAMES = CaveniaBiomeTopFilterPolicy.entries()
        .stream()
        .map(CaveniaBiomeEntry::legacyBiomeName)
        .toList();

    private CaveniaBiomeSelectionSkeleton() {
    }

    public static CaveniaBiomeSelectionSkeleton create() {
        return INSTANCE;
    }

    public int entryCount() {
        return CaveniaBiomeTopFilterPolicy.entries().size();
    }

    public int totalWeight() {
        return CaveniaBiomeTopFilterPolicy.totalWeight();
    }

    public List<String> legacyBiomeNames() {
        return LEGACY_BIOME_NAMES;
    }

    public boolean hasLegacyBiome(String legacyBiomeName) {
        return CaveniaBiomeTopFilterPolicy.containsLegacyBiomeName(legacyBiomeName);
    }

    public Optional<String> topBlockForLegacyBiomeName(String legacyBiomeName) {
        return CaveniaBiomeTopFilterPolicy.topBlockForLegacyBiomeName(legacyBiomeName);
    }

    public boolean isRuntimeBiomeSource() {
        return false;
    }

    public boolean codecRegistered() {
        return false;
    }

    public boolean requiresBiomeSourceStrategyBeforeActivation() {
        return true;
    }
}
