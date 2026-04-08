package crazywoddman.warium_additions.mixins.valkyrien_warium;

import net.mcreator.valkyrienwarium.procedures.LiquidRocketProcedureProcedure;
import net.mcreator.valkyrienwarium.procedures.TestThrusterOnTickUpdateProcedure;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({TestThrusterOnTickUpdateProcedure.class, LiquidRocketProcedureProcedure.class})
public class FuelContainingTickProcedureMixin {

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