package crazywoddman.warium_additions.client;

import com.mojang.blaze3d.platform.InputConstants;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.network.AimPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WadditionsKeyMappings {
    public static final KeyMapping
    ADS_HOLD_KEY = new KeyMapping(
        "key.%s.%s".formatted(WariumAdditions.MODID, "ads.hold"),
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.UNKNOWN.getValue(),
        "Warium Additions"
    ) {
        @Override
        public void setDown(boolean isDown) {
            if (isDown() != isDown && Minecraft.getInstance().getConnection() != null)
                AimPacket.send(isDown);

            super.setDown(isDown);
        }
    },
    SHOOT = new KeyMapping(
        "key.%s.%s".formatted(WariumAdditions.MODID, "firearm.shoot"),
        KeyConflictContext.IN_GAME,
        InputConstants.Type.MOUSE,
        1,
        "Warium Additions"
    ),
    BOLT_ACTION = new KeyMapping(
        "key.%s.%s".formatted(WariumAdditions.MODID, "bolt.action"),
        KeyConflictContext.IN_GAME,
        InputConstants.Type.MOUSE,
        0,
        "Warium Additions"
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ADS_HOLD_KEY);
        event.register(SHOOT);
        event.register(BOLT_ACTION);
    }
}