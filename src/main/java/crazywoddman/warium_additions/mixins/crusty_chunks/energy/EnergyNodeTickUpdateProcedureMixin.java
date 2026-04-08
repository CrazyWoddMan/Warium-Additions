package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.EnergyNodeTickUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.util.IEnergyNodeAccessor;

@Mixin(EnergyNodeTickUpdateProcedure.class)
public class EnergyNodeTickUpdateProcedureMixin {
    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void cancelExecute(LevelAccessor world, double x, double y, double z, BlockState blockstate, CallbackInfo ci) {
        if (world.getBlockEntity(BlockPos.containing(x, y, z)) instanceof IEnergyNodeAccessor accessor)
            accessor.updateTransferinfo();
            
        ci.cancel();
    }
}
