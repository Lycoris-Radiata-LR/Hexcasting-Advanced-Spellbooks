package net.lycoris_systems.advancedspellbooks.client;


import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.items.ItemFocus;
import at.petrak.hexcasting.common.items.ItemSpellbook;
import at.petrak.hexcasting.xplat.IClientXplatAbstractions;
import net.lycoris_systems.advancedspellbooks.items.ItemHexCastersSpellbook;
import net.lycoris_systems.advancedspellbooks.items.ItemHexEditorsSpellbook;
import net.lycoris_systems.advancedspellbooks.registries.ItemRegistry;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static at.petrak.hexcasting.client.RegisterClientStuff.makeIotaStorageColorizer;

public class HexCompactClient {

    public static void init(){
        registerDataHolderOverrides((IotaHolderItem) ItemRegistry.CASTER_SPELL_BOOK.get(),
                (itemStack) -> ((ItemSpellbook)ItemRegistry.CASTER_SPELL_BOOK.get()).readIotaTag(itemStack)!=null,
                ItemSpellbook::isSealed);
        registerDataHolderOverrides((IotaHolderItem) ItemRegistry.EDITOR_SPELL_BOOK.get(),
                (itemStack) -> ((ItemSpellbook)ItemRegistry.EDITOR_SPELL_BOOK.get()).readIotaTag(itemStack)!=null,
                ItemSpellbook::isSealed);
    }

    // From Hex Casting Mod
    // https://github.com/FallingColors/HexMod/blob/f569ba1530666f43e84584e446e6fd060395b5f4/Common/src/main/java/at/petrak/hexcasting/client/RegisterClientStuff.java#L305
    // Copied due to original method being private
    private static void registerDataHolderOverrides(IotaHolderItem item, Predicate<ItemStack> hasIota, Predicate<ItemStack> isSealed) {
        IClientXplatAbstractions.INSTANCE.registerItemProperty((Item)item, ItemFocus.OVERLAY_PRED, (stack, level, holder, holderID) -> {
            if (!hasIota.test(stack) && !NBTHelper.hasString(stack, "VisualOverride")) {
                return 0.0F;
            } else {
                return !isSealed.test(stack) ? 1.0F : 2.0F;
            }
        });
    }

    // https://github.com/FallingColors/HexMod/blob/f569ba1530666f43e84584e446e6fd060395b5f4/Common/src/main/java/at/petrak/hexcasting/client/RegisterClientStuff.java#L123
    public static void registerColorProviders(BiConsumer<ItemColor, Item> itemColorRegistry) {
        ItemHexEditorsSpellbook hexEditorsSpellbook = (ItemHexEditorsSpellbook) ItemRegistry.EDITOR_SPELL_BOOK.get();
        Objects.requireNonNull(hexEditorsSpellbook);
        itemColorRegistry.accept(makeIotaStorageColorizer(hexEditorsSpellbook::getColor), ItemRegistry.EDITOR_SPELL_BOOK.get());
        ItemHexCastersSpellbook hexCastersSpellbook = (ItemHexCastersSpellbook) ItemRegistry.CASTER_SPELL_BOOK.get();
        Objects.requireNonNull(hexCastersSpellbook);
        itemColorRegistry.accept(makeIotaStorageColorizer(hexCastersSpellbook::getColor), ItemRegistry.CASTER_SPELL_BOOK.get());
    }
}
