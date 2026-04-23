package com.richardkenway.cavernreborn.app.registry;

import java.util.EnumMap;
import java.util.List;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, CavernReborn.MOD_ID);
    public static final int HEXCITE_DURABILITY_MULTIPLIER = 20;

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HEXCITE = ARMOR_MATERIALS.register("hexcite", () -> new ArmorMaterial(
        Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
            defense.put(ArmorItem.Type.HELMET, 3);
            defense.put(ArmorItem.Type.CHESTPLATE, 8);
            defense.put(ArmorItem.Type.LEGGINGS, 6);
            defense.put(ArmorItem.Type.BOOTS, 3);
        }),
        18,
        SoundEvents.ARMOR_EQUIP_DIAMOND,
        () -> Ingredient.of(ModRegistries.HEXCITE.get()),
        List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "hexcite"))),
        1.0F,
        0.0F
    ));

    private ModArmorMaterials() {
    }

    public static void register(IEventBus modEventBus) {
        ARMOR_MATERIALS.register(modEventBus);
    }
}
