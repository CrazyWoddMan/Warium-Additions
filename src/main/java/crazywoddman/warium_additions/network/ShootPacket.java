package crazywoddman.warium_additions.network;

import crazywoddman.warium_additions.utils.FireArmActions;
import crazywoddman.warium_additions.utils.WadditionsClientUtil;
import net.mcreator.crustychunks.network.CrustyChunksModVariables;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class ShootPacket implements P2SPacket {
    private static int stamp = -4;

    public static void send() {
        WadditionsClientUtil.getLocalPlayer().ifPresent(player -> {
            if (player.tickCount - stamp >= 4) {
                if (FireArmActions.get(player.getItemInHand(InteractionHand.MAIN_HAND)) != null) {
                    stamp = player.tickCount;
                    NetworkHandler.CHANNEL.sendToServer(new ShootPacket());
                }
            }
        });
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {}

    static ShootPacket decode(FriendlyByteBuf buf) {
        return new ShootPacket();
    }
    
    @Override
    public void handle(ServerPlayer sender) {
        sender.getCapability(CrustyChunksModVariables.PLAYER_VARIABLES_CAPABILITY).ifPresent(capability -> {
            ItemStack hand = sender.getItemInHand(InteractionHand.MAIN_HAND);
            FireArmActions.get(hand).shoot().accept(sender.level(), sender.getX(), sender.getY(), sender.getZ(), sender, hand);
        });
    }
}