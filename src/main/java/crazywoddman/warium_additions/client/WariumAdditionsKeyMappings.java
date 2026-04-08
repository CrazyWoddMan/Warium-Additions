package crazywoddman.warium_additions.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import crazywoddman.warium_additions.WariumAdditions;
import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.network.ADSMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WariumAdditionsKeyMappings {
    public static final KeyMapping ADS_HOLD_KEY = new KeyMapping(
        "key." + WariumAdditions.MODID + ".ads.hold",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_LEFT_SHIFT,
        "key.categories." + WariumAdditions.MODID
    ) {
        @Override
        public void setDown(boolean isDown) {
            if (isDown() != isDown) {
                CrustyChunksMod.PACKET_HANDLER.sendToServer(new ADSMessage(0, 0));
                ADSMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }
            super.setDown(isDown);
        }
    };

    @SubscribeEvent
    public static void KeyMapping(RegisterKeyMappingsEvent event) {
        event.register(ADS_HOLD_KEY);
    }
}