package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.procedures.HeavyFlameThrowerFireScriptProcedure;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Other mods fuel acceptor during the fire procedure for the <b>Heavy Flame Thrower</b>
 * @see FlamethrowerReloadScriptProcedureMixin
 * @see FuelTanksBlockEntityMixin
 */
@Mixin(HeavyFlameThrowerFireScriptProcedure.class)
public class HeavyFlameThrowerFireMixin {

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
