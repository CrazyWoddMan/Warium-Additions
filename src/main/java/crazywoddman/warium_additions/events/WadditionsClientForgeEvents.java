package crazywoddman.warium_additions.events;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.client.PatchedCrosshairOverlay;
import crazywoddman.warium_additions.client.WadditionsKeyMappings;
import crazywoddman.warium_additions.network.BoltPacket;
import crazywoddman.warium_additions.network.ShootPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WariumAdditions.MODID, value = Dist.CLIENT)
public class WadditionsClientForgeEvents {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onRenderCrosshairPre(RenderGuiOverlayEvent.Pre event) {
        PatchedCrosshairOverlay.onRenderCrosshairPre(event);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (WadditionsKeyMappings.BOLT_ACTION.consumeClick())
                BoltPacket.send();

            if (WadditionsKeyMappings.SHOOT.consumeClick() || WadditionsKeyMappings.SHOOT.isDown())
                ShootPacket.send();
        }
    }
}