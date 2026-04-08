package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.procedures.ArtilleryReloadScriptProcedure;
import net.mcreator.crustychunks.procedures.BCReloadScriptProcedure;
import net.minecraft.world.entity.Entity;
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
    BCReloadScriptProcedure.class,
    ArtilleryReloadScriptProcedure.class,
})
public class CannonsCreativeReload {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;setCount(I)V"
        )
    )
    private static void redirectAmmoConsume(ItemStack stack, int count, LevelAccessor level, double x, double y, double z, Entity entity) {
        if (!((Player)entity).isCreative())
            stack.setCount(count);
    }
}
