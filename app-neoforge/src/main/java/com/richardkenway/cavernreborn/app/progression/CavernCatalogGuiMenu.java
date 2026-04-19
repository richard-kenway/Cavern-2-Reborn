package com.richardkenway.cavernreborn.app.progression;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.LongSupplier;

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
            if (!player.getUUID().equals(ownerId) || clickType != ClickType.PICKUP) {
                return;
            }
            if (slotId == CavernCatalogGuiLayout.REFRESH_SLOT) {
                refreshContents();
                return;
            }

            String entryId = entryIdsBySlot.get(slotId);
            if (entryId == null) {
                return;
            }

            java.util.Optional<CavernCatalogUseResult> result = catalogAccess.use(ownerId, entryId, currentTimeMillis());
            if (result.isPresent() && player instanceof ServerPlayer serverPlayer) {
                CavernCatalogUseResult resolvedResult = result.get();
                catalogAccess.grant(serverPlayer, resolvedResult);
                serverPlayer.sendSystemMessage(Component.literal(CavernCatalogUseFeedbackFormatter.format(resolvedResult)));
                refreshContents();
            }
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
}
