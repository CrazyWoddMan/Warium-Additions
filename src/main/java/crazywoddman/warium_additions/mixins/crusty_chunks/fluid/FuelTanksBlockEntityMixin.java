package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity;
import net.mcreator.crustychunks.block.entity.FlameThrowerBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankInputBlockEntity;
import net.mcreator.crustychunks.block.entity.FuelTankModuleBlockEntity;
import net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.OilFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.RefineryTowerBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallPetrolEngineBlockEntity;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.WadditionsConfig;
import crazywoddman.warium_additions.data.WadditionsTags;

@Mixin(
    value = {
        FuelTankBlockEntity.class,
        FuelTankModuleBlockEntity.class,
        FuelTankInputBlockEntity.class,
        MediumDieselEngineBlockEntity.class,
        SmallDieselEngineBlockEntity.class,
        MediumPetrolEngineBlockEntity.class,
        SmallPetrolEngineBlockEntity.class,
        EngineCyllinderBlockEntity.class,
        JetTurbineBlockEntity.class,
        FlameThrowerBlockEntity.class,
        RefineryTowerBlockEntity.class,
        OilFireboxBlockEntity.class
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
    private static int wadditions$capacity(int value) {
        return WadditionsConfig.FUEL_TANKS_CAPACITY.get();
    }

    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 16000),
        require = 0
    )
    private static int wadditions$refineryCapacity(int value) {
        return WadditionsConfig.REFINERY_TOWER_CAPACITY.get();
    }

    @SuppressWarnings("deprecation")
    @Inject(
        method = "<init>",
        at = @At("RETURN")
    )
    private void wadditions$acceptAnyFluid(BlockPos position, BlockState state, CallbackInfo callback) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;

        fluidTank.setValidator(stack -> {
            Fluid fluid = stack.getFluid();
            
            if (blockEntity instanceof MediumDieselEngineBlockEntity
             || blockEntity instanceof SmallDieselEngineBlockEntity
             || blockEntity instanceof EngineCyllinderBlockEntity
            ) {
                return fluid.is(WadditionsTags.Fluids.DIESEL);
            }
            
            if (blockEntity instanceof MediumPetrolEngineBlockEntity
             || blockEntity instanceof SmallPetrolEngineBlockEntity
             || blockEntity instanceof FlameThrowerBlockEntity
            ) {
                return fluid.is(WadditionsTags.Fluids.PETROL);
            }
            
            if (blockEntity instanceof JetTurbineBlockEntity
             || blockEntity instanceof OilFireboxBlockEntity
            ) {
                return fluid.is(WadditionsTags.Fluids.KEROSENE);
            }
            
            return true;
        });
    }
}
