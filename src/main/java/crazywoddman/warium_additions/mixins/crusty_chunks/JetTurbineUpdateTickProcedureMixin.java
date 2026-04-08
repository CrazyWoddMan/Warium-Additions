package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.JetTurbineUpdateTickProcedure;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import crazywoddman.warium_additions.config.Config;

@Mixin(JetTurbineUpdateTickProcedure.class)
public class JetTurbineUpdateTickProcedureMixin {

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

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 10.0, ordinal = 0),
        remap = false
    )
    private static double modifyMaxThrottle(double value) {
        return Config.SERVER.maxThrottle.get();
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 51.0),
        remap = false
    )
    private static double modifyPowerTurbine(double value) {
        return Config.SERVER.jetTurbinePower.get();
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(doubleValue = 50.0, ordinal = 1),
        remap = false
    )
    private static double modifyPowerTurbineAfterBurner(double value) {
        return Config.SERVER.jetTurbinePower.get() - 1;
    }
}
