package crazywoddman.warium_additions.mixins.crusty_chunks.firearms;

import net.mcreator.crustychunks.procedures.BoltActionRifleEntitySwingsItemProcedure;
import net.mcreator.crustychunks.procedures.ShotgunActionScriptProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.config.WadditionsConfig;

/**
 * Prevents ammo consumption and casings drop for players in Creative Mode
 */
@Mixin({
    ShotgunActionScriptProcedure.class,
    BoltActionRifleEntitySwingsItemProcedure.class
})
public class CreativeBoltMixin {
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        ),
        require = 0
    )
    private static boolean wadditions$addFreshEntity(ServerLevel level, Entity casing, LevelAccessor world, double x, double y, double z, Entity entity) {
        return !WadditionsConfig.CREATIVE_AMMO_CONSUME.get() && ((Player)entity).isCreative() && casing instanceof ItemEntity ? false : level.addFreshEntity(casing);
    }
}
