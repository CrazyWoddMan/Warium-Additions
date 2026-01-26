package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallPetrolEngineBlockEntity;
import net.mcreator.crustychunks.procedures.EngineCyllinderOnTickUpdateProcedure;
import net.mcreator.crustychunks.procedures.EngineUpdateProcedure;
import net.mcreator.crustychunks.procedures.PetrolEngineUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

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
        BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));
        double maxPower;

        if (blockEntity instanceof MediumDieselEngineBlockEntity)
            maxPower = Config.SERVER.mediumDieselEnginePower.get();
        else if (blockEntity instanceof SmallDieselEngineBlockEntity)
            maxPower = Config.SERVER.smallDieselEnginePower.get();
        else if (blockEntity instanceof MediumPetrolEngineBlockEntity)
            maxPower = Config.SERVER.mediumPetrolEnginePower.get();
        else if (blockEntity instanceof SmallPetrolEngineBlockEntity)
            maxPower = Config.SERVER.smallPetrolEnginePower.get();
        else if (blockEntity instanceof EngineCyllinderBlockEntity)
            maxPower = Config.SERVER.largeEnginePower.get();
        else
            throw new IllegalStateException("Unsupported BlockEntity for this procedure: [" + ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(blockEntity.getType()) + "]");

        int throttle = WariumAdditionsUtil.getThrottle(blockEntity, 0);

        return throttle == 0 ? 0.0 : (maxPower / Config.SERVER.maxThrottle.get() * Math.abs(throttle));
    }
}
