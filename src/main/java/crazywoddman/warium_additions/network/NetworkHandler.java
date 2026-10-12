package crazywoddman.warium_additions.network;

import java.util.function.Function;

import crazywoddman.warium_additions.utils.WadditionsUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;

    protected static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        WadditionsUtil.location("main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    
    public static void register() {
        register(ConfigSyncPacket.class, ConfigSyncPacket::decode, NetworkDirection.PLAY_TO_SERVER);
        register(ShootPacket.class, ShootPacket::decode, NetworkDirection.PLAY_TO_SERVER);
        register(AimPacket.class, AimPacket::decode, NetworkDirection.PLAY_TO_SERVER);
        register(BoltPacket.class, BoltPacket::decode, NetworkDirection.PLAY_TO_SERVER);
    }

    private static <T extends Packet> void register(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        CHANNEL.messageBuilder(clazz, packetId++, direction)
            .encoder(Packet::encode)
            .decoder(decoder)
            .consumerMainThread((packet, context) -> packet.handle(context))
            .add();
    }
}