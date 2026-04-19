package com.richardkenway.cavernreborn.app.progression;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;

public final class CavernCatalogGuiMenu extends ChestMenu {
    public static final Component TITLE = Component.literal("CAVERN Catalog")
        .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD);
    private static final int ROW_COUNT = 3;

    private final UUID ownerId;
    private final SimpleContainer container;
    private final CavernCatalogAccess catalogAccess;
    private final LongSupplier currentTimeMillis;
    private Map<Integer, String> entryIdsBySlot = Map.of();

    public CavernCatalogGuiMenu(
        int containerId,
        Inventory playerInventory,
        ServerPlayer owner,
        CavernCatalogAccess catalogAccess,
        LongSupplier currentTimeMillis
    ) {
        this(
            containerId,
            playerInventory,
            new SimpleContainer(CavernCatalogGuiLayout.SLOT_COUNT),
            owner,
            catalogAccess,
            currentTimeMillis
        );
    }

    private CavernCatalogGuiMenu(
        int containerId,
        Inventory playerInventory,
        SimpleContainer container,
        ServerPlayer owner,
        CavernCatalogAccess catalogAccess,
        LongSupplier currentTimeMillis
    ) {
        super(MenuType.GENERIC_9x3, containerId, playerInventory, container, ROW_COUNT);
        this.ownerId = Objects.requireNonNull(owner, "owner").getUUID();
        this.container = Objects.requireNonNull(container, "container");
        this.catalogAccess = Objects.requireNonNull(catalogAccess, "catalogAccess");
        this.currentTimeMillis = Objects.requireNonNull(currentTimeMillis, "currentTimeMillis");
        refreshContents();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (slotId >= 0 && slotId < CavernCatalogGuiLayout.SLOT_COUNT) {
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return;
            }
            routeClick(
                ownerId,
                slotId,
                clickType,
                new ServerClickActor(serverPlayer, catalogAccess),
                () -> entryIdsBySlot,
                catalogAccess,
                this::currentTimeMillis,
                this::refreshContents
            );
            return;
        }
        super.clicked(slotId, button, clickType, player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive() && player.getUUID().equals(ownerId);
    }

    private void refreshContents() {
        CavernCatalogAccess.CavernCatalogView view = catalogAccess.inspect(ownerId, currentTimeMillis());
        applyLayout(CavernCatalogGuiLayout.create(view.snapshot(), view.entries()));
        broadcastFullState();
    }

    private void applyLayout(CavernCatalogGuiLayout.Layout layout) {
        for (int i = 0; i < CavernCatalogGuiLayout.SLOT_COUNT; i++) {
            container.setItem(i, toItemStack(layout.slotItems().get(i)));
        }
        container.setChanged();
        entryIdsBySlot = layout.entryIdsBySlot();
    }

    private long currentTimeMillis() {
        return currentTimeMillis.getAsLong();
    }

    private static ItemStack toItemStack(CavernCatalogGuiLayout.SlotView slotView) {
        ItemStack stack = new ItemStack(resolveItem(slotView.itemId()));
        stack.set(
            DataComponents.CUSTOM_NAME,
            Component.literal(slotView.title()).withStyle(slotView.nameColor())
        );
        if (!slotView.lore().isEmpty()) {
            stack.set(
                DataComponents.LORE,
                new ItemLore(slotView.lore().stream()
                    .<Component>map(line -> Component.literal(line).withStyle(ChatFormatting.GRAY))
                    .toList())
            );
        }
        stack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        return stack;
    }

    private static Item resolveItem(String itemId) {
        ResourceLocation resourceLocation = ResourceLocation.parse(itemId);
        if (BuiltInRegistries.ITEM.containsKey(resourceLocation)) {
            return BuiltInRegistries.ITEM.get(resourceLocation);
        }
        return Items.BARRIER;
    }

    static boolean routeClick(
        UUID ownerId,
        int slotId,
        ClickType clickType,
        ClickActor actor,
        Supplier<Map<Integer, String>> entryIdsBySlotSupplier,
        CavernCatalogAccess catalogAccess,
        LongSupplier currentTimeMillis,
        Runnable refreshContents
    ) {
        UUID normalizedOwnerId = Objects.requireNonNull(ownerId, "ownerId");
        ClickActor normalizedActor = Objects.requireNonNull(actor, "actor");
        Supplier<Map<Integer, String>> normalizedEntryIdsBySlotSupplier = Objects.requireNonNull(
            entryIdsBySlotSupplier,
            "entryIdsBySlotSupplier"
        );
        CavernCatalogAccess normalizedCatalogAccess = Objects.requireNonNull(catalogAccess, "catalogAccess");
        LongSupplier normalizedCurrentTimeMillis = Objects.requireNonNull(currentTimeMillis, "currentTimeMillis");
        Runnable normalizedRefreshContents = Objects.requireNonNull(refreshContents, "refreshContents");

        if (!normalizedOwnerId.equals(normalizedActor.playerId()) || clickType != ClickType.PICKUP) {
            return false;
        }
        if (slotId == CavernCatalogGuiLayout.REFRESH_SLOT) {
            normalizedRefreshContents.run();
            return true;
        }

        String entryId = normalizedEntryIdsBySlotSupplier.get().get(slotId);
        if (entryId == null) {
            return false;
        }

        java.util.Optional<CavernCatalogUseResult> result = normalizedCatalogAccess.use(
            normalizedOwnerId,
            entryId,
            normalizedCurrentTimeMillis.getAsLong()
        );
        if (result.isEmpty()) {
            return false;
        }
        normalizedActor.onResult(result.get());
        normalizedRefreshContents.run();
        return true;
    }

    interface ClickActor {
        UUID playerId();

        void onResult(CavernCatalogUseResult result);
    }

    private record ServerClickActor(ServerPlayer player, CavernCatalogAccess catalogAccess) implements ClickActor {
        private ServerClickActor {
            Objects.requireNonNull(player, "player");
            Objects.requireNonNull(catalogAccess, "catalogAccess");
        }

        @Override
        public UUID playerId() {
            return player.getUUID();
        }

        @Override
        public void onResult(CavernCatalogUseResult result) {
            if (result.granted()) {
                catalogAccess.grant(player, result);
            }
            player.sendSystemMessage(Component.literal(CavernCatalogUseFeedbackFormatter.format(result)));
        }
    }
}
