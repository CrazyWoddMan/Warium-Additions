package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.ReturnPowerProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin(ReturnPowerProcedure.class)
public class ReturnPowerProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void rewriteExecute(LevelAccessor world, double x, double y, double z, CallbackInfoReturnable<String> cir) {
        IEnergyStorage storage = world.getBlockEntity(BlockPos.containing(x, y, z)).getCapability(ForgeCapabilities.ENERGY).orElse(null);
        cir.setReturnValue(storage == null ? "" : (WariumAdditionsUtil.formatFE(storage.getEnergyStored()) + "/" + WariumAdditionsUtil.formatFE(storage.getMaxEnergyStored())));
    }
}
