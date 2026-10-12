package crazywoddman.warium_additions.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

interface P2SPacket extends Packet {

    @Override 
    default void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();

        if (player != null)
            handle(player);
            
    }

    void handle(ServerPlayer sender);
}