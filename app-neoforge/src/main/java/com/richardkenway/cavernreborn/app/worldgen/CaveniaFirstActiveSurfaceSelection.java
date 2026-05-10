package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaFirstActiveSurfaceSelection {
    private static final List<CaveniaFirstActiveSurfaceSelectionReason> SELECTED_REASONS = List.of(
        CaveniaFirstActiveSurfaceSelectionReason.PREREQUISITE_FOR_GENERATOR_HOST,
        CaveniaFirstActiveSurfaceSelectionReason.PREREQUISITE_FOR_DIMENSION_BINDING,
        CaveniaFirstActiveSurfaceSelectionReason.LOWER_RUNTIME_SIDE_EFFECT_RISK,
        CaveniaFirstActiveSurfaceSelectionReason.DOES_NOT_REQUIRE_DIMENSION_JSON_YET,
        CaveniaFirstActiveSurfaceSelectionReason.KEEPS_ACCESS_AND_SPAWN_BLOCKED,
        CaveniaFirstActiveSurfaceSelectionReason.REQUIRES_FUTURE_EXPLICIT_RUNTIME_BIOME_SOURCE_MVP
    );
    private static final List<CaveniaFirstActiveSurfaceCandidate> CANDIDATES = List.of(
        candidate(
            CaveniaActivationSurface.GENERATOR_HOST,
            CaveniaActivationReadinessMatrix.generatorHostRequirementCount(),
            false,
            2,
            "CaveniaGeneratorHostContracts",
            "Generator-host activation remains blocked until a biome-source strategy exists and runtime generator work is chosen explicitly.",
            List.of(CaveniaFirstActiveSurfaceSelectionReason.PREREQUISITE_FOR_GENERATOR_HOST)
        ),
        candidate(
            CaveniaActivationSurface.BIOME_SOURCE_STRATEGY,
            CaveniaActivationReadinessMatrix.biomeSourceStrategyRequirementCount(),
            true,
            1,
            "CaveniaBiomeSourceStrategyContracts",
            "Biome-source strategy stays selected first, but activation remains blocked until a later explicit runtime-biome-source slice.",
            SELECTED_REASONS
        ),
        candidate(
            CaveniaActivationSurface.DIMENSION_RESOURCE,
            CaveniaActivationReadinessMatrix.dimensionResourceRequirementCount(),
            false,
            3,
            "CaveniaDimensionResourceContracts",
            "Dimension-resource activation remains deferred until biome-source strategy and generator-host work are more explicit.",
            List.of(CaveniaFirstActiveSurfaceSelectionReason.DOES_NOT_REQUIRE_DIMENSION_JSON_YET)
        ),
        candidate(
            CaveniaActivationSurface.ACCESS_TRAVEL,
            CaveniaActivationReadinessMatrix.accessTravelRequirementCount(),
            false,
            5,
            "CaveniaAccessTravelContracts",
            "Access/travel activation remains deferred because a selected first slice should keep travel blocked and avoid active level work.",
            List.of(CaveniaFirstActiveSurfaceSelectionReason.KEEPS_ACCESS_AND_SPAWN_BLOCKED)
        ),
        candidate(
            CaveniaActivationSurface.SPAWN_HOST,
            CaveniaActivationReadinessMatrix.spawnHostRequirementCount(),
            false,
            6,
            "CaveniaSpawnHostContracts",
            "Spawn-host activation remains deferred because the first selected slice should keep Cavenia-only spawning blocked.",
            List.of(CaveniaFirstActiveSurfaceSelectionReason.KEEPS_ACCESS_AND_SPAWN_BLOCKED)
        ),
        candidate(
            CaveniaActivationSurface.WORLDGEN_RESOURCE,
            CaveniaActivationReadinessMatrix.worldgenResourceRequirementCount(),
            false,
            4,
            "CaveniaWorldgenResourceContracts",
            "Worldgen-resource activation remains deferred because the first selected slice should avoid resource-side effects and keep runtime binding absent.",
            List.of(CaveniaFirstActiveSurfaceSelectionReason.LOWER_RUNTIME_SIDE_EFFECT_RISK)
        )
    );

    private CaveniaFirstActiveSurfaceSelection() {
    }

    public static List<CaveniaFirstActiveSurfaceCandidate> candidates() {
        return CANDIDATES;
    }

    public static CaveniaFirstActiveSurfaceCandidate selectedCandidate() {
        return CANDIDATES.stream()
            .filter(CaveniaFirstActiveSurfaceCandidate::selected)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Missing selected first active Cavenia surface candidate"));
    }

    public static CaveniaActivationSurface selectedSurface() {
        return selectedCandidate().surface();
    }

    public static String selectedSurfaceName() {
        return selectedSurface().name();
    }

    public static int selectedRequirementCount() {
        return selectedCandidate().requirementCount();
    }

    public static boolean selectedSurfaceIsBiomeSourceStrategy() {
        return selectedSurface() == CaveniaActivationSurface.BIOME_SOURCE_STRATEGY;
    }

    public static boolean selectionIsDecisionOnly() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean selectedSurfaceRemainsBlocked() {
        return selectedCandidate().remainsBlocked();
    }

    public static boolean allCandidatesRemainBlocked() {
        return CANDIDATES.stream().allMatch(CaveniaFirstActiveSurfaceCandidate::remainsBlocked);
    }

    public static boolean allCandidatesDisallowActivationInThisSlice() {
        return CANDIDATES.stream().noneMatch(CaveniaFirstActiveSurfaceCandidate::activationAllowedInThisSlice);
    }

    public static Optional<CaveniaFirstActiveSurfaceCandidate> candidateFor(CaveniaActivationSurface surface) {
        return CANDIDATES.stream()
            .filter(candidate -> candidate.surface() == surface)
            .findFirst();
    }

    public static List<CaveniaFirstActiveSurfaceSelectionReason> selectionReasons() {
        return selectedCandidate().reasons();
    }

    public static int readinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int readinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean allReadinessSurfacesBlocked() {
        return CaveniaActivationReadinessMatrix.allSurfacesBlocked();
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static boolean biomeSourceRuntimeReady() {
        return CaveniaActivationReadinessMatrix.biomeSourceRuntimeReady();
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaActivationReadinessMatrix.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaActivationReadinessMatrix.dimensionTypeJsonPresent();
    }

    public static boolean accessCanTeleport() {
        return CaveniaActivationReadinessMatrix.accessCanTeleport();
    }

    public static boolean spawnHostCanSpawn() {
        return CaveniaActivationReadinessMatrix.spawnHostCanSpawn();
    }

    public static boolean worldgenCanAffectWorldgen() {
        return CaveniaActivationReadinessMatrix.worldgenCanAffectWorldgen();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaActivationReadinessMatrix.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaFirstActiveSurfaceCandidate candidate(
        CaveniaActivationSurface surface,
        int requirementCount,
        boolean selected,
        int selectionRank,
        String sourceContractName,
        String blocker,
        List<CaveniaFirstActiveSurfaceSelectionReason> reasons
    ) {
        return new CaveniaFirstActiveSurfaceCandidate(
            surface,
            requirementCount,
            selected,
            selectionRank,
            false,
            true,
            sourceContractName,
            blocker,
            reasons
        );
    }
}
