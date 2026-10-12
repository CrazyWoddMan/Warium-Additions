package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.procedures.EngineCyllinderOnTickUpdateProcedure;
import net.mcreator.crustychunks.procedures.EngineUpdateProcedure;
import net.mcreator.crustychunks.procedures.HeavyFlameThrowerFireScriptProcedure;
import net.mcreator.crustychunks.procedures.JetTurbineUpdateTickProcedure;
import net.mcreator.crustychunks.procedures.LightTurbineEngineUpdateProcedure;
import net.mcreator.crustychunks.procedures.PetrolEngineUpdateProcedure;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({
    EngineUpdateProcedure.class,
    PetrolEngineUpdateProcedure.class,
    EngineCyllinderOnTickUpdateProcedure.class,
    LightTurbineEngineUpdateProcedure.class,
    JetTurbineUpdateTickProcedure.class,
    HeavyFlameThrowerFireScriptProcedure.class
})
public class FuelContainingTickProcedureMixin {
    
    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/fluids/FluidStack;isFluidEqual(Lnet/minecraftforge/fluids/FluidStack;)Z"
        ),
        remap = false
    )
    private static boolean wadditions$isFluidEqual(FluidStack fluid1, FluidStack fluid2, LevelAccessor world, double x, double y, double z) {
        return true;
    }
}
