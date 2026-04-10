package com.richardkenway.cavernreborn.app.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

class SavedDataBackedWorldPortalIndexRepositoryTest {
    @Test
    void usesPersistentStateWhenAvailable() {
        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("portal", new PortalWorldIndex.PortalPlacement(1, 2, 3, PortalWorldIndex.PortalPlacement.AXIS_Z));
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        SavedDataBackedWorldPortalIndexRepository repository = new SavedDataBackedWorldPortalIndexRepository(
            () -> Optional.of(persistentState),
            new InMemoryWorldPortalIndexRepository()
        );

        repository.save("cavernreborn:cavern", worldIndex);

        assertEquals(worldIndex, persistentState.loadWorldPortalIndex("cavernreborn:cavern"));
        assertEquals(worldIndex, repository.load("cavernreborn:cavern"));
    }

    @Test
    void fallsBackToInMemoryStoreWhenPersistentStateIsUnavailable() {
        PortalWorldIndex worldIndex = PortalWorldIndex.empty()
            .withPortal("portal", new PortalWorldIndex.PortalPlacement(1, 2, 3, PortalWorldIndex.PortalPlacement.AXIS_Z));
        SavedDataBackedWorldPortalIndexRepository repository = new SavedDataBackedWorldPortalIndexRepository(
            Optional::<CavernPersistentStateData>empty,
            new InMemoryWorldPortalIndexRepository()
        );

        repository.save("cavernreborn:cavern", worldIndex);

        assertEquals(worldIndex, repository.load("cavernreborn:cavern"));
    }
}
