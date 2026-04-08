package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.GeigerCounterHandTickProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(GeigerCounterHandTickProcedure.class)
public class GeigerCounterHandTickProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    private static void redirectDouble(Player player, Component component, boolean b) {
        String text = component.getString();
        String rad = text.substring(21);
        
        if (!rad.equals("0"))
            player.displayClientMessage(
                Component
                    .translatable("effect.crusty_chunks.radiation")
                    .append(": " + rad)
                    .withStyle(ChatFormatting.getByCode(text.toCharArray()[1])),
                b
            );
    }
}
