package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.mcreator.crustychunks.procedures.FuelTankInputTickProcedure;
import net.mcreator.crustychunks.procedures.FuelTankModuleOnTickUpdateProcedure;
import net.mcreator.crustychunks.procedures.FuelTankTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(
    value = {
        FuelTankTickProcedure.class,
        FuelTankModuleOnTickUpdateProcedure.class,
        FuelTankInputTickProcedure.class
    }
)
public class FuelTanksTickProcedureMixin {
    private static Fluid realFluid;
    private static BlockPos neighbor;

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void captureFluid(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        world.getBlockEntity(BlockPos.containing(x, y, z)).getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
            FluidStack fluid = handler.getFluidInTank(0);
            realFluid = fluid.isEmpty() ? null : fluid.getFluid();
        });

        if (realFluid == null)
            ci.cancel();
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/BlockPos;containing(DDD)Lnet/minecraft/core/BlockPos;",
            ordinal = 4
        )
    )
    private static BlockPos redirectIsFluidEqual(double nx, double ny, double nz, LevelAccessor world, double x, double y, double z) {
        neighbor = BlockPos.containing(nx, ny, nz);
        return neighbor;
    }

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/fluids/FluidStack;isFluidEqual(Lnet/minecraftforge/fluids/FluidStack;)Z"
        ),
        remap = false
    )
    private static boolean redirectIsFluidEqual(FluidStack fluid1, FluidStack fluid2, LevelAccessor world, double x, double y, double z) {
        return fluid2.getFluid() == CrustyChunksModFluids.KEROSENE.get();
    }

    @ModifyVariable(
        method = "execute",
        name = "amount",
        at = @At(value = "STORE"),
        remap = false
    )
    private static double modifyAmount(double original, LevelAccessor world, double x, double y, double z) {
        return neighbor == null ? 0 : Optional
            .ofNullable(world.getBlockEntity(neighbor))
            .map(blockEntity -> blockEntity
                .getCapability(ForgeCapabilities.FLUID_HANDLER)
                .map(cap -> cap.fill(new FluidStack(realFluid, 250), FluidAction.SIMULATE))
                .orElse(0)
            )
            .orElse(0);
    }

    @Redirect(
        method = "*",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/fluids/capability/IFluidHandler;fill(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)I"
        ),
        remap = false
    )
    private static int redirectFill(IFluidHandler handler, FluidStack fluid, FluidAction action) {
        return handler.fill(new FluidStack(realFluid, fluid.getAmount()), action);
    }
}
