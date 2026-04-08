package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.EngineCyllinderOnTickUpdateProcedure;
import net.mcreator.crustychunks.procedures.EngineUpdateProcedure;
import net.mcreator.crustychunks.procedures.PetrolEngineUpdateProcedure;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import crazywoddman.warium_additions.config.Config;

@Mixin({
    EngineUpdateProcedure.class,
    PetrolEngineUpdateProcedure.class,
    EngineCyllinderOnTickUpdateProcedure.class
})
public class EnginesUpdateProcedureMixin {
    
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
        constant = @Constant(doubleValue = 10.0),
        remap = false
    )
    private static double modifyMaxThrottle(double value) {
        return Config.SERVER.maxThrottle.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(to = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;isClientSide()Z",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 50.0),
        remap = false,
        require = 0
    )
    private static double modifyPowerMediumDiesel(double value) {
        return Config.SERVER.mediumDieselEnginePower.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(to = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;isClientSide()Z",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 35.0),
        remap = false,
        require = 0
    )
    private static double modifyPowerSmallDiesel(double value) {
        return Config.SERVER.smallDieselEnginePower.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(to = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;isClientSide()Z",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 60.0),
        remap = false,
        require = 0
    )
    private static double modifyPowerMediumPetrol(double value) {
        return Config.SERVER.mediumPetrolEnginePower.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(to = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;isClientSide()Z",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 40.0),
        remap = false,
        require = 0
    )
    private static double modifyPowerSmallPetrol(double value) {
        return Config.SERVER.smallPetrolEnginePower.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;isClientSide()Z",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 50.0),
        remap = false,
        require = 0
    )
    private static double modifyPowerLargeEngine(double value, LevelAccessor world, double x, double y, double z) {
        return Config.SERVER.largeEnginePower.get();
    }
}
