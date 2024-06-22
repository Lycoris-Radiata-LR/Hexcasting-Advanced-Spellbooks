package net.lycoris_systems.advancedspellbooks.mixin;

import at.petrak.hexcasting.client.ShiftScrollListener;
import net.lycoris_systems.advancedspellbooks.registries.ItemRegistry;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShiftScrollListener.class)
public class MixinHexcastingScrollItem {

    @Inject(method = "IsScrollableItem(Lnet/minecraft/world/item/Item;)Z", at=@At("HEAD"), cancellable = true, remap = false)
    @Unique
    private static void same$isScrollableItem(Item item, CallbackInfoReturnable<Boolean> cir){
        if(
                item == ItemRegistry.CASTER_SPELL_BOOK.get() ||
                item == ItemRegistry.EDITOR_SPELL_BOOK.get()
            ){
            cir.setReturnValue(true);
        }
    }
}
