package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.JetTurbineUpdateTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

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

    @ModifyConstant(
        method = "execute",
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/level/Level;sendBlockUpdated(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;I)V",
                ordinal = 0,
                remap = true
            )
        ),
        constant = @Constant(doubleValue = 0.0, ordinal = 0),
        remap = false
    )
    private static double modifyFirstZero(double power, LevelAccessor world, double x, double y, double z) {
        return Config.SERVER.maxThrottle.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/level/Level;sendBlockUpdated(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;I)V",
                ordinal = 0,
                remap = true
            )
        ),
        constant = @Constant(doubleValue = 0.0, ordinal = 1),
        remap = false
    )
    private static double modifySecondZero(double power, LevelAccessor world, double x, double y, double z) {
        int throttle = WariumAdditionsUtil.getThrottle(world.getBlockEntity(BlockPos.containing(x, y, z)), 0);
        return throttle == 0 ? 0.0 : ((double) Config.SERVER.jetTurbinePower.get() / Config.SERVER.maxThrottle.get() * Math.abs(throttle));
    }
}
