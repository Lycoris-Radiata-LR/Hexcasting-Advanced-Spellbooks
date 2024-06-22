package net.lycoris_systems.advancedspellbooks.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.lycoris_systems.advancedspellbooks.AdvancedSpellbooks.MODID;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AdvancedSpellbooksClient {
    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event){
        HexCompactClient.init();
    }

    @SubscribeEvent
    public static void itemColorHandlerSetup(RegisterColorHandlersEvent.Item event){
        HexCompactClient.registerColorProviders((event::register));
    }
}
