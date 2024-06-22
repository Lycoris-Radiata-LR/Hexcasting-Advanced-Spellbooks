package net.lycoris_systems.advancedspellbooks.items;

import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.iota.Iota;
import net.lycoris_systems.advancedspellbooks.utils.IotaCastingUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ItemHexCastersSpellbook extends AbstractAdvancedSpellbook implements IotaHolderItem {

    @Override
    int getCooldown() {
        return 100;
    }

    public ItemHexCastersSpellbook(Properties properties) {
        super(properties);
    }

    @Override
    boolean castIota(ServerLevel level, CastingHarness harness, Iota iota, ServerPlayer player) {
        return IotaCastingUtils.tryCast(level, harness, iota);
    }
}
