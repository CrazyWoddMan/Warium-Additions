package crazywoddman.warium_additions.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {
    protected abstract void encode(FriendlyByteBuf buf);
    protected abstract void handle(Supplier<NetworkEvent.Context> ctx);
}