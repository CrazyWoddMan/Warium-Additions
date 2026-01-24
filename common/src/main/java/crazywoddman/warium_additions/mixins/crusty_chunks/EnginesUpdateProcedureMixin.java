package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.procedures.EngineCyllinderOnTickUpdateProcedure;
import net.mcreator.crustychunks.procedures.EngineUpdateProcedure;
import net.mcreator.crustychunks.procedures.PetrolEngineUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin({
    EngineUpdateProcedure.class,
    PetrolEngineUpdateProcedure.class,
    EngineCyllinderOnTickUpdateProcedure.class
})
public class EnginesUpdateProcedureMixin {

    private static String engineType;

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false,
        require = 0
    )
    private static void injectEnginesExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        engineType = ForgeRegistries.BLOCKS.getKey(world.getBlockState(BlockPos.containing(x, y, z)).getBlock()).getPath();
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Diesel"),
        require = 0,
        remap = false
    )
    private static String modifyDiesel(String value) {
        return "";
    }

    @ModifyConstant(
        method = "execute",
        constant = @Constant(stringValue = "Petrol"),
        require = 0,
        remap = false
    )
    private static String modifyPetrol(String value) {
        return "";
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

    @ModifyConstant(
        method = "execute",
        slice = @Slice(from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;sendBlockUpdated(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;I)V",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 0.0, ordinal = 0),
        remap = false
    )
    private static double modifyFirstZero(double power, LevelAccessor world, double x, double y, double z) {
        return Config.SERVER.maxThrottle.get();
    }

    @ModifyConstant(
        method = "execute",
        slice = @Slice(from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;sendBlockUpdated(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;I)V",
            ordinal = 0,
            remap = true
        )),
        constant = @Constant(doubleValue = 0.0, ordinal = 1),
        remap = false
    )
    private static double modifySecondZero(double power, LevelAccessor world, double x, double y, double z) {
        int throttle = WariumAdditionsUtil.getThrottle(world.getBlockEntity(BlockPos.containing(x, y, z)), 0);
        double maxPower = switch (engineType) {
            case "light_combustion_engine" -> Config.SERVER.mediumDieselEnginePower.get();
            case "small_diesel_engine" -> Config.SERVER.smallDieselEnginePower.get();
            case "medium_petrol_engine" -> Config.SERVER.mediumPetrolEnginePower.get();
            case "smal_petrol_engine" -> Config.SERVER.mediumDieselEnginePower.get();
            case "engine_cyllinder" -> Config.SERVER.largeEnginePower.get();
            default -> 0;
        };
        
        return throttle == 0 ? 0.0 : (maxPower / Config.SERVER.maxThrottle.get() * Math.abs(throttle));
    }
}
