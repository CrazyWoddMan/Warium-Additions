package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.procedures.HeavyFlameThrowerFireScriptProcedure;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({HeavyFlameThrowerFireScriptProcedure.class})
public class HeavyFlameThrowerFireScriptProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/fluids/FluidStack;isFluidEqual(Lnet/minecraftforge/fluids/FluidStack;)Z"
        ),
        remap = false
    )
    private static boolean redirectIsFluidEqual(FluidStack fluid1, FluidStack fluid2, LevelAccessor world, double x, double y, double z) {
        return true;
    }
}
