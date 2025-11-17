package crazywoddman.warium_additions.network;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(WariumAdditions.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    
    private static int packetId = 0;
    
    public static void register() {
        CHANNEL.messageBuilder(LaunchKeyPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .encoder(LaunchKeyPacket::encode)
            .decoder(LaunchKeyPacket::decode)
            .consumerMainThread(LaunchKeyPacket::handle)
            .add();

        CHANNEL.messageBuilder(ShootKeyPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .encoder(ShootKeyPacket::encode)
            .decoder(ShootKeyPacket::decode)
            .consumerMainThread(ShootKeyPacket::handle)
            .add();
    }
}