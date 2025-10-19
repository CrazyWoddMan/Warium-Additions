package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.MotorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.config.Config;

@Mixin(MotorTickProcedure.class)
public class MotorTickProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        )
    )
    private static void redirectPutDouble(CompoundTag compound, String key, double value) {
        if (key.equals("KineticPower"))
            compound.putDouble(key, Config.SERVER.kineticToFeRate.get() * value);
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 0.0, ordinal = 5),
        remap = false
    )
    private static double modifyZero(double power, LevelAccessor world, double x, double y, double z) {
        int energy = world
            .getBlockEntity(BlockPos.containing(x, y, z))
            .getCapability(ForgeCapabilities.ENERGY)
            .orElse(null)
            .getEnergyStored();
        
        return energy > 0 ? -1.0 : 0.0;
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    private static boolean redirectHasNeighborSignal(Level level, BlockPos pos) {
        return !level.hasNeighborSignal(pos);
    }

    @ModifyVariable(
        method = "execute",
        name = "Power",
        at = @At(value = "STORE", ordinal = 1),
        remap = false
    )
    private static double modifyPower(double value, LevelAccessor world, double x, double y, double z) {
        return world
            .getBlockEntity(BlockPos.containing(x, y, z))
            .getCapability(ForgeCapabilities.ENERGY)
            .orElse(null)
            .getEnergyStored();
    }
}
