package net.lycoris_systems.advancedspellbooks.mixin;

import at.petrak.hexcasting.client.ShiftScrollListener;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShiftScrollListener.class)
public class MixinHexcastingIsScrollableInvoker {
    @Invoker(value = "IsScrollableItem", remap = false)
    public static boolean InvokeIsScrollableitem(Item item){
        throw new AssertionError();
    }
}
