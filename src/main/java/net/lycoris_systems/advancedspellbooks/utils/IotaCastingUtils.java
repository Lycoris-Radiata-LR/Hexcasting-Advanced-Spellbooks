package net.lycoris_systems.advancedspellbooks.utils;

import at.petrak.hexcasting.api.spell.SpellList;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.casting.ControllerInfo;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.iota.ListIota;
import at.petrak.hexcasting.api.spell.iota.PatternIota;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class IotaCastingUtils {

    public static boolean tryCast(ServerLevel level, CastingHarness ctx, Iota iota){
        if(iota == null) return false;
        if(iota instanceof ListIota listIota){
            List<Iota> list = new ArrayList<>();
            SpellList spellList = listIota.getList();
            for(int i = 0; i < spellList.size(); i++){
                list.add(spellList.getAt(i));
            }
            ControllerInfo info = ctx.executeIotas(list, level);
            return info.getResolutionType().getSuccess();
        }
        if(iota instanceof PatternIota patternIota){
            ControllerInfo info = ctx.executeIota(patternIota, level);
            return info.getResolutionType().getSuccess();
        }
        return false;
    }

    public static boolean addToStack(CastingHarness ctx, Iota iota){
        if(iota == null) return false;
        List<Iota> existingIota = ctx.getStack();
        existingIota.add(iota);
        ctx.setStack(existingIota);
        return true;
    }

}
