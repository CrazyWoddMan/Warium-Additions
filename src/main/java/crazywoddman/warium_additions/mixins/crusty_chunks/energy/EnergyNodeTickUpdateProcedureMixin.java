package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.EnergyNodeTickUpdateProcedure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnergyNodeTickUpdateProcedure.class)
public class EnergyNodeTickUpdateProcedureMixin {
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
