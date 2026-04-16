package crazywoddman.warium_additions.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import crazywoddman.warium_additions.config.Config;

public class ConfigSyncPacket extends AbstractPacket {
    private final Object value;
    private final int index;
    
    private ConfigSyncPacket(ConfigValue<?> value) {
        this.value = value.get();
        this.index = Config.getIndex(value);
    }

    private ConfigSyncPacket(Object value, int index) {
        this.value = value;
        this.index = index;
    }

    public static void send(ConfigValue<?> value) {
        NetworkHandler.CHANNEL.sendToServer(new ConfigSyncPacket(value));
    }
    
    @Override
    protected void encode(FriendlyByteBuf buf) {
        if (this.value instanceof Integer i) {
            buf.writeByte(Config.ValueTypes.INTEGER.ordinal());
            buf.writeInt(i);
        } else if (this.value instanceof Double d) {
            buf.writeByte(Config.ValueTypes.DOUBLE.ordinal());
            buf.writeDouble(d);
        } else if (this.value instanceof Boolean b) {
            buf.writeByte(Config.ValueTypes.BOOLEAN.ordinal());
            buf.writeBoolean(b);
        } else if (this.value instanceof String s) {
            buf.writeByte(Config.ValueTypes.STRING.ordinal());
            buf.writeUtf(s);
        }
        else throw new IllegalStateException("Illegal config value type");

        buf.writeVarInt(this.index);
    }
    
    protected static ConfigSyncPacket decode(FriendlyByteBuf buf) {
        System.out.println("received ConfigSyncPacket. Decoding...");
        return new ConfigSyncPacket(switch (Config.ValueTypes.values()[buf.readByte()]) {
            case INTEGER -> Integer.valueOf(buf.readInt());
            case DOUBLE -> Double.valueOf(buf.readDouble());
            case BOOLEAN -> Boolean.valueOf(buf.readBoolean());
            case STRING -> buf.readUtf();
        }, buf.readVarInt());
    }
    
    @Override
    protected void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();

        if (player == null || !player.hasPermissions(4))
            return;

        Config.set(this.index, this.value);
    }
}