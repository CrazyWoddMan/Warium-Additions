package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankInputBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankModuleBlockEntity;
import net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity;
import net.mcreator.crustychunks.block.entity.LightCombustionEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmalPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin(
    value = {
        FuelTankBlockEntity.class,
        FuelTankModuleBlockEntity.class,
        FuelTankInputBlockEntity.class,
        LightCombustionEngineBlockEntity.class,
        SmallDieselEngineBlockEntity.class,
        MediumPetrolEngineBlockEntity.class,
        SmalPetrolEngineBlockEntity.class,
        EngineCyllinderBlockEntity.class,
        JetTurbineBlockEntity.class
    },
    remap = false
)
public class FuelTanksBlockEntityMixin {

    @Shadow(remap = false)
    private FluidTank fluidTank;

    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 1000),
        require = 0
    )
    private static int modifyCapacity(int value) {
        return Config.SERVER.fuelTanksCapacity.get();
    }

     @Inject(
        method = "<init>",
        at = @At("RETURN")
    )
    private void acceptAnyFluid(BlockPos position, BlockState state, CallbackInfo callback) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;

        fluidTank.setValidator(stack -> {
            if (blockEntity instanceof LightCombustionEngineBlockEntity || blockEntity instanceof SmallDieselEngineBlockEntity || blockEntity instanceof EngineCyllinderBlockEntity)
                return WariumAdditionsUtil.compareFluids(stack.getFluid(), CrustyChunksModFluids.DIESEL.get());

            if (blockEntity instanceof MediumPetrolEngineBlockEntity || blockEntity instanceof SmalPetrolEngineBlockEntity)
                return WariumAdditionsUtil.compareFluids(stack.getFluid(), CrustyChunksModFluids.PETROLIUM.get());

            else if (blockEntity instanceof JetTurbineBlockEntity)
                return WariumAdditionsUtil.compareFluids(stack.getFluid(), CrustyChunksModFluids.KEROSENE.get());

            else
                return true;
        });
    }
}
