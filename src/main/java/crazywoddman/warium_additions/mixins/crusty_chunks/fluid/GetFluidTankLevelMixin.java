package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import java.util.concurrent.atomic.AtomicInteger;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

@Mixin(targets = {
    "net.mcreator.crustychunks.procedures.FuelTankTickProcedure$10",
    "net.mcreator.crustychunks.procedures.FuelTankTickProcedure$20"
})
public class GetFluidTankLevelMixin {
    @Redirect(
        method = "lambda$getFluidTankLevel$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/fluids/FluidStack;getAmount()I"
        ),
        remap = false
    )
    private static int redirectPut(FluidStack fluid, AtomicInteger integer, int tank, IFluidHandler handler) {
        int amount = fluid.getAmount();
        return handler.getTankCapacity(0) > amount ? 0 : amount;
    }
}