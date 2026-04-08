package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.procedures.ACFireScriptProcedure;
import net.mcreator.crustychunks.procedures.HMGFireScriptProcedure;
import net.mcreator.crustychunks.procedures.LACFireScriptProcedure;
import net.mcreator.crustychunks.procedures.LMGFireScriptBlockProcedure;
import net.mcreator.crustychunks.procedures.MGFireScriptProcedure;
import net.mcreator.crustychunks.procedures.MinigunFireScriptProcedure;
import net.mcreator.crustychunks.procedures.RACFireScriptProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.mixins.crusty_chunks.BlocksCreativeMode;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

/**
 * <b>Creative Mode</b> handeling for machine gun and autocannon blocks.
 * <p>In this mode weapons don't consume ammo and don't output casings
 * @see BlocksCreativeMode
 */
@Mixin({
    LMGFireScriptBlockProcedure.class,
    MGFireScriptProcedure.class,
    HMGFireScriptProcedure.class,
    MinigunFireScriptProcedure.class,
    RACFireScriptProcedure.class,
    ACFireScriptProcedure.class,
    LACFireScriptProcedure.class
})
public class TurretsCreativeMode {

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

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        )
    )
    private static boolean redirectCasingDrop(ServerLevel level, Entity entity, LevelAccessor world, double x, double y, double z) {
        return (entity instanceof ItemEntity && WariumAdditionsUtil.checkIfCreative(level, x, y, z)) ? false : level.addFreshEntity(entity);
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Ammo", ordinal = 0),
        remap = false
    )
    private static String trickAmmoCheck(String value, LevelAccessor level, double x, double y, double z) {
        return WariumAdditionsUtil.checkIfCreative(level, x, y, z) ? "Barrels" : value;
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/mcreator/crustychunks/procedures/RACFireScriptProcedure$13;getValue(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Ljava/lang/String;)D"
        ),
        remap = false,
        require = 0
    )
    private static double redirectAmmoCheckRAC(@Coerce Object object, LevelAccessor level, BlockPos ammodrum, String tag, LevelAccessor world, double x, double y, double z) {
        return ammoCheck(level, ammodrum, tag, x, y, z);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/mcreator/crustychunks/procedures/ACFireScriptProcedure$15;getValue(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Ljava/lang/String;)D"
        ),
        remap = false,
        require = 0
    )
    private static double redirectAmmoCheckAC(@Coerce Object object, LevelAccessor level, BlockPos ammodrum, String tag, LevelAccessor world, double x, double y, double z) {
        return ammoCheck(level, ammodrum, tag, x, y, z);
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/mcreator/crustychunks/procedures/LACFireScriptProcedure$15;getValue(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Ljava/lang/String;)D"
        ),
        remap = false,
        require = 0
    )
    private static double redirectAmmoCheckLAC(@Coerce Object object, LevelAccessor level, BlockPos ammodrum, String tag, LevelAccessor world, double x, double y, double z) {
        return ammoCheck(level, ammodrum, tag, x, y, z);
    }

    private static double ammoCheck(LevelAccessor level, BlockPos pos, String tag, double x, double y, double z) {
        if (WariumAdditionsUtil.checkIfCreative(level, x, y, z))
            return 1;
        else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            return blockEntity == null ? -1 : blockEntity.getPersistentData().getDouble(tag);
        }
    }
}
