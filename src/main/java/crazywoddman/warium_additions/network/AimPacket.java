package crazywoddman.warium_additions.network;

import net.mcreator.crustychunks.network.CrustyChunksModVariables;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record AimPacket(boolean aim) implements P2SPacket {

    public static void send(boolean aim) {
        NetworkHandler.CHANNEL.sendToServer(new AimPacket(aim));
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.aim);
    }

    static AimPacket decode(FriendlyByteBuf buf) {
        return new AimPacket(buf.readBoolean());
    }
    
    @Override
    public void handle(ServerPlayer sender) {
        sender.getCapability(CrustyChunksModVariables.PLAYER_VARIABLES_CAPABILITY).ifPresent(cap -> {
            cap.AimDownSights = this.aim;
            cap.syncPlayerVariables(sender);
        });

    }
}