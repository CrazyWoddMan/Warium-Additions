package crazywoddman.warium_additions.network;

import crazywoddman.warium_additions.config.ConfigValueHolder;
import crazywoddman.warium_additions.config.WadditionsConfig;
import net.minecraft.commands.Commands;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record ConfigSyncPacket(Object[] values) implements P2SPacket {
    private static final byte
    BOOLEAN = 0,
    INTEGER = 1,
    DOUBLE  = 2,
    STRING  = 3;

    public static void send() {
        NetworkHandler.CHANNEL.sendToServer(new ConfigSyncPacket(WadditionsConfig.VALUES.stream().map(ConfigValueHolder::get).toArray(Object[]::new)));
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.values.length);

        for (Object value : this.values) {
            if (value instanceof Boolean b) {
                buf.writeByte(BOOLEAN);
                buf.writeBoolean(b);
            } else if (value instanceof Integer i) {
                buf.writeByte(INTEGER);
                buf.writeInt(i);
            } else if (value instanceof Double d) {
                buf.writeByte(DOUBLE);
                buf.writeDouble(d);
            } else if (value instanceof String s) {
                buf.writeByte(STRING);
                buf.writeUtf(s);
            } else {
                throw new IllegalStateException("Unknown ConfigValue value type");
            }
        }
    }
    
    static ConfigSyncPacket decode(FriendlyByteBuf buf) {
        Object[] values = new Object[buf.readByte()];

        for (int i = 0; i < values.length; i++) {
            values[i] = switch (buf.readByte()) {
                case BOOLEAN -> buf.readBoolean();
                case INTEGER -> buf.readInt();
                case DOUBLE  -> buf.readDouble();
                case STRING  -> buf.readUtf();
                default -> throw new IllegalStateException("Unknown ConfigValue value type");
            };
        }

        return new ConfigSyncPacket(values);
    }
    
    @Override
    public void handle(ServerPlayer player) {
        if (player.hasPermissions(Commands.LEVEL_ADMINS))
            for (int i = 0; i < this.values.length; i++)
                WadditionsConfig.VALUES.get(i).set(this.values[i]);
    }
}