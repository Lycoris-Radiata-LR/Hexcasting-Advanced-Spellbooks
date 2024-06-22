package net.lycoris_systems.advancedspellbooks.mixin;

import at.petrak.hexcasting.common.network.MsgShiftScrollSyn;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MsgShiftScrollSyn.class)
public interface MixinHexcastingShiftScrollSyn {
    @Invoker(value = "spellbook", remap = false)
    public void invokeSpellbookScroll(ServerPlayer sender, InteractionHand hand, ItemStack stack, double delta);
}
