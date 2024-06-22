package net.lycoris_systems.advancedspellbooks.registries;

import net.lycoris_systems.advancedspellbooks.AdvancedSpellbooks;
import net.lycoris_systems.advancedspellbooks.items.ItemHexCastersSpellbook;
import net.lycoris_systems.advancedspellbooks.items.ItemHexEditorsSpellbook;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AdvancedSpellbooks.MODID);


    //咒法学
    public static final RegistryObject<Item> CASTER_SPELL_BOOK = ITEMS.register("hex_caster_spellbook",
            () -> new ItemHexCastersSpellbook(new Item.Properties().tab(CreativeTabRegistry.MAIN).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EDITOR_SPELL_BOOK = ITEMS.register("hex_editor_spellbook",
            () -> new ItemHexEditorsSpellbook(new Item.Properties().tab(CreativeTabRegistry.MAIN).stacksTo(1).rarity(Rarity.UNCOMMON)));


    public static RegistryObject<Item> simpleBlockItem(RegistryObject<Block> block, Item.Properties properties){
        String id = block.getId().getPath();
        return ITEMS.register(id, ()-> new BlockItem(block.get(), properties));
    }

    public static RegistryObject<Item> simpleItem(String id, Item.Properties properties){
        return ITEMS.register(id, () -> new Item(properties));
    }


    public static void setup(IEventBus eventBus){
        ITEMS.register(eventBus);
        AdvancedSpellbooks.LOGGER.info("Item Registry Setup");
    }
}
