package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRegistryLookupReadiness {
    private static final List<CaveniaRegistryLookupRequirementContract> CONTRACTS = List.of(
        contract(
            CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_INVENTORIED,
            "CaveniaLegacyToModernBiomeKeyMappings",
            CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady(),
            "Candidate modern biome keys are inventoried, but no runtime registry resolution exists yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_STILL_STRING_ONLY,
            "CaveniaLegacyToModernBiomeKeyMappings",
            true,
            "Candidate modern biome keys intentionally remain plain strings until runtime registry resolution is designed."
        ),
        contract(
            CaveniaRegistryLookupRequirement.REGISTRY_ACCESS_SOURCE_DECISION,
            "CaveniaAdapterCodecRegistrationReadiness",
            false,
            "No registry access source decision exists for the adapter runtime path yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.BIOME_REGISTRY_REFERENCE,
            "CaveniaRegistryLookupReadiness",
            false,
            "No biome registry reference contract exists for runtime lookup yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.RESOURCE_KEY_CONVERSION,
            "CaveniaRegistryLookupReadiness",
            false,
            "No conversion contract exists from string biome ids to runtime biome keys yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.HOLDER_RESOLUTION,
            "CaveniaRegistryLookupReadiness",
            false,
            "No runtime holder-resolution contract exists for adapter biome candidates yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.MISSING_BIOME_FALLBACK_DECISION,
            "CaveniaRegistryLookupReadiness",
            false,
            "No missing-biome fallback decision exists for runtime lookup failures yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.RUNTIME_LOOKUP_CONTEXT,
            "CaveniaBiomeSelectionAdapterContract",
            false,
            "No runtime lookup context exists for the biome-selection adapter contract yet."
        ),
        contract(
            CaveniaRegistryLookupRequirement.ADAPTER_RESULT_TO_RUNTIME_BIOME,
            "CaveniaBiomeSelectionAdapterResult",
            false,
            "No runtime conversion exists from adapter result data to resolved runtime biomes yet."
        )
    );

    private CaveniaRegistryLookupReadiness() {
    }

    public static List<CaveniaRegistryLookupRequirementContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaRegistryLookupRequirement> requirements() {
        return CONTRACTS.stream()
            .map(CaveniaRegistryLookupRequirementContract::requirement)
            .toList();
    }

    public static Optional<CaveniaRegistryLookupRequirementContract> contractFor(CaveniaRegistryLookupRequirement requirement) {
        return CONTRACTS.stream()
            .filter(contract -> contract.requirement() == requirement)
            .findFirst();
    }

    public static int requirementCount() {
        return CONTRACTS.size();
    }

    public static boolean candidateKeysInventoried() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return true;
    }

    public static boolean registryLookupReadinessReady() {
        return true;
    }

    public static boolean registryLookupRuntimeReady() {
        return false;
    }

    public static boolean allRequirementsRuntimeBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaRegistryLookupRequirementContract::readyForRuntime);
    }

    public static boolean anyRequirementReadyForRuntime() {
        return CONTRACTS.stream().anyMatch(CaveniaRegistryLookupRequirementContract::readyForRuntime);
    }

    public static boolean anyRegistryLookupAvailable() {
        return CONTRACTS.stream().anyMatch(CaveniaRegistryLookupRequirementContract::registryLookupAvailable);
    }

    public static boolean anyRegistryVerified() {
        return CONTRACTS.stream().anyMatch(CaveniaRegistryLookupRequirementContract::registryVerified);
    }

    public static boolean anyRuntimeBiomeResolved() {
        return CONTRACTS.stream().anyMatch(CaveniaRegistryLookupRequirementContract::runtimeBiomeResolved);
    }

    public static boolean registryAccessSourceReady() {
        return false;
    }

    public static boolean biomeRegistryReferenceReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean holderResolutionReady() {
        return false;
    }

    public static boolean missingBiomeFallbackReady() {
        return false;
    }

    public static boolean runtimeLookupContextReady() {
        return false;
    }

    public static boolean adapterResultToRuntimeBiomeReady() {
        return false;
    }

    public static boolean adapterShapeReady() {
        return CaveniaBiomeSelectionAdapterContract.adapterShapeReady();
    }

    public static boolean adapterRuntimeReady() {
        return false;
    }

    public static boolean codecRegistrationReadinessReady() {
        return CaveniaAdapterCodecRegistrationReadiness.codecRegistrationReadinessReady();
    }

    public static boolean codecRegistrationRuntimeReady() {
        return false;
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

    public static boolean modernBiomeMappingReady() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static int adapterEntryCount() {
        return CaveniaBiomeSelectionAdapterContract.entryCount();
    }

    public static int adapterTotalWeight() {
        return CaveniaBiomeSelectionAdapterContract.totalWeight();
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaBiomeSelectionAdapterContract.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaBiomeSelectionAdapterContract.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaBiomeSelectionAdapterContract.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRegistryLookupRequirementContract contract(
        CaveniaRegistryLookupRequirement requirement,
        String sourceContractName,
        boolean prerequisiteSatisfied,
        String blocker
    ) {
        return new CaveniaRegistryLookupRequirementContract(
            requirement,
            sourceContractName,
            prerequisiteSatisfied,
            false,
            false,
            false,
            false,
            false,
            blocker
        );
    }
}
