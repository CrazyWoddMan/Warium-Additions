package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.GeigerCounterHandTickProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.compat.curios.CuriosUtil;


@Mixin(GeigerCounterHandTickProcedure.class)
public class GeigerCounterHandTickProcedureMixin {
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    private static ItemStack redirectItem(LivingEntity livingEntity, LevelAccessor world, double x, double y, double z, Entity entity) {
        ItemStack stack = livingEntity.getOffhandItem();
        if (stack == ItemStack.EMPTY && livingEntity instanceof Player player)
            if (CuriosUtil.checkForItem(player, "belt", CrustyChunksModItems.GEIGER_COUNTER.get()))
                return new ItemStack(CrustyChunksModItems.GEIGER_COUNTER.get());

        return stack;
    }

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
