package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.procedures.ArtilleryFireScriptProcedure;
import net.mcreator.crustychunks.procedures.BCFireScriptProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.mixins.crusty_chunks.BlocksCreativeMode;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

/**
 * <b>Creative Mode</b> handeling for cannon blocks.
 * <p>In this mode cannons don't consume ammo
 * @see BlocksCreativeMode
 */
@Mixin({
    BCFireScriptProcedure.class,
    ArtilleryFireScriptProcedure.class
})
public class CannonsCreativeMode {

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Charge"),
        remap = false,
        require = 0
    )
    private static String trickChargeCheck(String value, LevelAccessor level, double x, double y, double z) {
        return WariumAdditionsUtil.checkIfCreative(level, x, y, z) ? "Loaded" : value;
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V"
        )
    )
    private static void redirectAmmoConsume(CompoundTag tag, String key, boolean value, LevelAccessor level, double x, double y, double z) {
        if (!key.equals("Loaded") || !WariumAdditionsUtil.checkIfCreative(level, x, y, z))
            tag.putBoolean(key, value);
    }
}
