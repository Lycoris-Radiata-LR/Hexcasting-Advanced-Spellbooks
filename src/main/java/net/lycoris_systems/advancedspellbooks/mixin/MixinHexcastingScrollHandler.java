package net.lycoris_systems.advancedspellbooks.mixin;

import at.petrak.hexcasting.common.network.MsgShiftScrollSyn;
import net.lycoris_systems.advancedspellbooks.registries.ItemRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MsgShiftScrollSyn.class)
public class MixinHexcastingScrollHandler {

    //referenced from HexGloop mod's method
    //https://github.com/SamsTheNerd/HexGloop/blob/6124c030d8df2e6570ff36a4bd952b2f6b41030c/common/src/main/java/com/samsthenerd/hexgloop/mixins/wnboi/MixinHandleScrolling.java#L29
    @Inject(at = @At("TAIL"), method = "handleForHand", remap = false)
    private void handleForHand(ServerPlayer sender, InteractionHand hand, double delta, CallbackInfo ci) {
        if(delta != 0){
            ItemStack stack = sender.getItemInHand(hand);
            if(
                    stack.getItem().equals(ItemRegistry.CASTER_SPELL_BOOK.get())
                    || stack.getItem().equals(ItemRegistry.EDITOR_SPELL_BOOK.get())
            ){
                ((MixinHexcastingShiftScrollSyn) this).invokeSpellbookScroll(sender, hand, stack, delta);
            }
        }
    }
}
