package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.LongSupplier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

public final class CavernCatalogGuiOpener {
    private final CavernCatalogAccess catalogAccess;
    private final LongSupplier currentTimeMillis;
    private final MenuHostFactory menuHostFactory;

    public CavernCatalogGuiOpener(CavernCatalogAccess catalogAccess) {
        this(catalogAccess, System::currentTimeMillis, ServerPlayerMenuHost::new);
    }

    CavernCatalogGuiOpener(
        CavernCatalogAccess catalogAccess,
        LongSupplier currentTimeMillis,
        MenuHostFactory menuHostFactory
    ) {
        this.catalogAccess = Objects.requireNonNull(catalogAccess, "catalogAccess");
        this.currentTimeMillis = Objects.requireNonNull(currentTimeMillis, "currentTimeMillis");
        this.menuHostFactory = Objects.requireNonNull(menuHostFactory, "menuHostFactory");
    }

    public boolean open(ServerPlayer player) {
        return open(menuHostFactory.create(player));
    }

    boolean open(MenuHost menuHost) {
        return Objects.requireNonNull(menuHost, "menuHost")
            .openMenu(menuProvider())
            .isPresent();
    }

    private MenuProvider menuProvider() {
        return new SimpleMenuProvider(
            (containerId, inventory, menuPlayer) -> {
                if (!(menuPlayer instanceof ServerPlayer serverPlayer)) {
                    throw new IllegalStateException("CAVERN GUI requires a server-side player");
                }
                return new CavernCatalogGuiMenu(
                    containerId,
                    inventory,
                    serverPlayer,
                    catalogAccess,
                    currentTimeMillis
                );
            },
            CavernCatalogGuiMenu.TITLE
        );
    }

    interface MenuHost {
        OptionalInt openMenu(MenuProvider provider);
    }

    interface MenuHostFactory {
        MenuHost create(ServerPlayer player);
    }

    private record ServerPlayerMenuHost(ServerPlayer player) implements MenuHost {
        private ServerPlayerMenuHost {
            Objects.requireNonNull(player, "player");
        }

        @Override
        public OptionalInt openMenu(MenuProvider provider) {
            return player.openMenu(provider);
        }
    }
}
