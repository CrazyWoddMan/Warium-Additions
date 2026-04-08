package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.procedures.ArtilleryReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.AutocannonDrumReloadProcedure;
import net.mcreator.crustychunks.procedures.BCReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.CountermeasureDispenserReloadProcedure;
import net.mcreator.crustychunks.procedures.HardpointReloadProcedure;
import net.mcreator.crustychunks.procedures.LargeRocketPodReloadProcedure;
import net.mcreator.crustychunks.procedures.MortarOnBlockRightClickedProcedure;
import net.mcreator.crustychunks.procedures.RocketPodReloadProcedure;
import net.mcreator.crustychunks.procedures.SmokeLauncherReloadProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Prevents ammo consumption for players in Creative Mode
 */
@Mixin({
    AutocannonDrumReloadProcedure.class,
    HardpointReloadProcedure.class,
    SmokeLauncherReloadProcedure.class,
    MortarOnBlockRightClickedProcedure.class,
    BCReloadScriptProcedure.class,
    ArtilleryReloadScriptProcedure.class,
    RocketPodReloadProcedure.class,
    LargeRocketPodReloadProcedure.class,
    CountermeasureDispenserReloadProcedure.class,
})
public class WeaponBlocksCreativeReload {

    /**
     * For everything except drum
     */
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"
        ),
        require = 0
    )
    private static void redirectAmmoConsume(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (!((Player)entity).isCreative())
            entity.setItemInHand(hand, stack);
    }

    /**
     * For drum
     */
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"
        ),
        require = 0
    )
    private static void redirectAmmoConsume(ItemStack stack, int amount, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!((Player)entity).isCreative())
            stack.shrink(amount);
    }
}
