package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.HardpointReloadProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HardpointReloadProcedure.class)
public class HardpointReloadProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"
        )
    )
    private static void redirectSetItemInHand(LivingEntity livingEntity, InteractionHand hand, ItemStack stack) {
        if (livingEntity instanceof Player player && !player.isCreative())
            livingEntity.setItemInHand(hand, stack);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        )
    )
    private static boolean redirectAddFreshEntity(ServerLevel level, Entity entityToSpawn, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity instanceof Player player && !player.isCreative())
            return level.addFreshEntity(entityToSpawn);
        
        return false;
    }
}
