package net.lycoris_systems.advancedspellbooks.registries;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class CreativeTabRegistry {
    public static final CreativeModeTab MAIN = new CreativeModeTab("advanced_spellbooks_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.CASTER_SPELL_BOOK.get());
        }
    };
}
