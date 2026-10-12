package crazywoddman.warium_additions.events;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.config.WadditionsConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WadditionsClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        WadditionsConfigScreen.register();
    }
}