package net.lycoris_systems.advancedspellbooks.items;

import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.common.items.ItemSpellbook;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAdvancedSpellbook extends ItemSpellbook{

    int getCooldown(){
        return 20;
    }

    public AbstractAdvancedSpellbook(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(level.isClientSide() ||
                !(level instanceof ServerLevel serverLevel) ||
                !(player instanceof ServerPlayer serverPlayer)){
            return InteractionResultHolder.success(stack);
        }
        Iota iotaOnBook = readIota(stack, serverLevel);
        CastingHarness harness = IXplatAbstractions.INSTANCE.getHarness(serverPlayer, hand);
        boolean success = this.castIota(serverLevel, harness, iotaOnBook, serverPlayer);
        player.awardStat(Stats.ITEM_USED.get(this));
        if(success){
            serverPlayer.getCooldowns().addCooldown(this, this.getCooldown());
            IXplatAbstractions.INSTANCE.setHarness(serverPlayer, harness);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    abstract boolean castIota(ServerLevel level, CastingHarness harness, Iota iota, ServerPlayer player);

}
