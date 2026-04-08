package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.procedures.CountermeasureDispenserFireProcedure;
import net.mcreator.crustychunks.procedures.LargeRocketPodFireProcedure;
import net.mcreator.crustychunks.procedures.RocketPodFireScriptProcedure;
import net.mcreator.crustychunks.procedures.SmokeLauncherFireScriptProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.mixins.crusty_chunks.BlocksCreativeMode;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

/**
 * <b>Creative Mode</b> handeling for pods.
 * <p>In this mode pods consume ammo and don't output casings
 * @see BlocksCreativeMode
 */
@Mixin({
    RocketPodFireScriptProcedure.class,
    LargeRocketPodFireProcedure.class,
    CountermeasureDispenserFireProcedure.class,
    SmokeLauncherFireScriptProcedure.class
})
public class PodsCreativeMode {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        )
    )
    private static void redirectAmmoConsume(CompoundTag tag, String key, double value, LevelAccessor level, double x, double y, double z) {
        if (!key.equals("Ammo") || !WariumAdditionsUtil.checkIfCreative(level, x, y, z))
            tag.putDouble(key, value);
    }
}
