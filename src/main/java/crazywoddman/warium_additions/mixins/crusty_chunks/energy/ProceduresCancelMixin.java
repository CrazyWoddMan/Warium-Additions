package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.EnergyDistributionNodeCapacityProcedure;
import net.mcreator.crustychunks.procedures.EnergyNodeTickUpdateProcedure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EnergyDistributionNodeCapacityProcedure.class, EnergyNodeTickUpdateProcedure.class})
public class ProceduresCancelMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void cancelExecute(CallbackInfo ci) {
        ci.cancel();
    }
}
