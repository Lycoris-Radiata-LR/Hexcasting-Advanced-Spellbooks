package net.lycoris_systems.advancedspellbooks.registries;

import net.lycoris_systems.advancedspellbooks.AdvancedSpellbooks;
import net.lycoris_systems.advancedspellbooks.recipes.RecipeUpgradeSpellbook;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AdvancedSpellbooks.MODID);

    public static final RegistryObject<RecipeSerializer<RecipeUpgradeSpellbook>> UPGRADE_SPELL_BOOK =
            SERIALIZERS.register("upgrade_spell_book", () -> RecipeUpgradeSpellbook.Serializer.INSTANCE);

    public static void setup(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        AdvancedSpellbooks.LOGGER.info("Recipes Setup");
    }
}
