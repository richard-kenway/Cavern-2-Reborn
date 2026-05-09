package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CaveniaActiveFoundationReadinessPlanTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path POPULATION_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path ACCESS_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path SPAWN_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-policy-mvp.md");
    private static final Path CRAZY_ROSTER_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path CAVEMAN_DOC = resolveProjectFile("docs", "caveman-cavenia-normal-roster-boundary.md");

    @Test
    void readinessPlanExistsAndPinsTheCurrentInactiveFoundationState() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String planDoc = Files.readString(PLAN_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String accessDoc = Files.readString(ACCESS_DOC);
        String spawnProviderDoc = Files.readString(SPAWN_PROVIDER_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String cavemanDoc = Files.readString(CAVEMAN_DOC);

        assertTrue(planDoc.contains("Cavenia Active Foundation Readiness / Implementation Plan"));
        assertTrue(planDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(planDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(planDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(planDoc.contains("`CaveniaSpawnProviderPolicy`"));
        assertTrue(planDoc.contains("`CaveniaSpawnEntry`"));
        assertTrue(planDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(planDoc.contains("nearby `ICavenicMob` whose `isNonBoss()` returns `false`"));
        assertTrue(planDoc.contains("`dimension/cavenia.json`"));
        assertTrue(planDoc.contains("`dimension_type/cavenia.json`"));
        assertTrue(planDoc.contains("`CAVENIA_LOCATION`"));
        assertTrue(planDoc.contains("`CAVENIA_LEVEL_KEY`"));
        assertTrue(planDoc.contains("no active Cavenia spawning"));
        assertTrue(planDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(planDoc.contains("Phase 0: Completed readiness inputs"));
        assertTrue(planDoc.contains("Phase 1: Active Cavenia key/type and inactive runtime identity MVP"));
        assertTrue(planDoc.contains("Phase 2: Minimal terrain/generator MVP"));
        assertTrue(planDoc.contains("Phase 3: Cave carver MVP"));
        assertTrue(planDoc.contains("Phase 4: Biome provider and surface/top/filter MVP"));
        assertTrue(planDoc.contains("Phase 5: VEINS/content MVP"));
        assertTrue(planDoc.contains("Phase 6: Population MVP"));
        assertTrue(planDoc.contains("Phase 7: Entry/access MVP"));
        assertTrue(planDoc.contains("Phase 8: Spawn provider MVP"));
        assertTrue(planDoc.contains("Phase 9: Caveman full parity"));
        assertTrue(planDoc.contains("First Active MVP Candidate"));
        assertTrue(planDoc.contains("Cavenia Active Foundation Technical Spike"));
        assertTrue(planDoc.contains("Minimum Acceptance Checklist Before Enabling Cavenia"));
        assertTrue(planDoc.contains("active dimension key/type agreed"));
        assertTrue(planDoc.contains("safe generator strategy agreed"));
        assertTrue(planDoc.contains("no broken teleport/access path"));
        assertTrue(planDoc.contains("no fake `CAVERN` spawn path"));
        assertTrue(planDoc.contains("spawn provider consumes the non-runtime policy"));
        assertTrue(planDoc.contains("Caveman handling explicitly decided"));
        assertTrue(planDoc.contains("validation loop green"));

        assertTrue(readme.contains("Cavenia Active Foundation Readiness / Implementation Plan"));
        assertTrue(readme.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Active Foundation Readiness / Implementation Plan"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia active-foundation readiness-plan boundary"));

        assertTrue(foundationDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(populationDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(accessDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(spawnProviderDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(policyDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(cavemanDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }
}
