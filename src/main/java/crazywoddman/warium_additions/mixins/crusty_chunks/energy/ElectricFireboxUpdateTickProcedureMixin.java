package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.ElectricFireboxUpdateTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.ElectricFireboxHeatProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ElectricFireboxUpdateTickProcedure.class)
public class ElectricFireboxUpdateTickProcedureMixin {

    @Redirect(
        method = "execute",
        slice = @Slice(to = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;sendBlockUpdated(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;I)V",
            ordinal = 1,
            remap = true
        )),
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        )
    )
    private static void redirectPut(CompoundTag compound, String key, double value) {}

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 20.0, ordinal = 0),
        remap = false
    )
    private static double modifyEnergy(double value, LevelAccessor world, double x, double y, double z) {
        return -1.0;
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    private static boolean modifyEnergy(Level level, BlockPos pos) {
        boolean consuming =!level.hasNeighborSignal(pos) && level
            .getBlockEntity(pos)
            .getCapability(ForgeCapabilities.ENERGY)
            .orElse(null)
            .extractEnergy(1, false)
        > 0;

        if (WariumAdditions.createLoaded)
            ElectricFireboxHeatProvider.setHeatLevel(consuming ? Config.SERVER.electricFireboxHeat.get() : "NONE", pos, level);

        return consuming;
    }
}
