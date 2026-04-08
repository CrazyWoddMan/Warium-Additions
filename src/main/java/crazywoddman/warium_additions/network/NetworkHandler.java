package crazywoddman.warium_additions.network;

import java.util.function.Function;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;

    protected static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    
    public static void register() {
        register(ConfigSyncPacket.class, ConfigSyncPacket::decode, NetworkDirection.PLAY_TO_SERVER);
    }

    private static <T extends AbstractPacket> void register(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        CHANNEL.messageBuilder(clazz, packetId++, direction)
            .encoder(AbstractPacket::encode)
            .decoder(decoder)
            .consumerMainThread((packet, context) -> packet.handle(context))
            .add();
    }
}