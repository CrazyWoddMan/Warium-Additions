package crazywoddman.warium_additions.network;

import crazywoddman.warium_additions.utils.FireArmActions;
import crazywoddman.warium_additions.utils.WadditionsClientUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class BoltPacket implements P2SPacket {

    public static void send() {
        WadditionsClientUtil.getLocalPlayer().ifPresent(player -> {
            if (FireArmActions.get(player.getItemInHand(InteractionHand.MAIN_HAND)) != null)
                NetworkHandler.CHANNEL.sendToServer(new BoltPacket());
        });
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {}

    static BoltPacket decode(FriendlyByteBuf buf) {
        return new BoltPacket();
    }
    
    @Override
    public void handle(ServerPlayer sender) {
        ItemStack hand = sender.getItemInHand(InteractionHand.MAIN_HAND);
        FireArmActions.get(hand).bolt().accept(sender.level(), sender.getX(), sender.getY(), sender.getZ(), sender, hand);
    }
}