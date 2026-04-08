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
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
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

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

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
    private static int modifyCapacity(int value) {
        return Config.SERVER.fuelTanksCapacity.get();
    }

    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 16000),
        require = 0
    )
    private static int modifyRefineryTowerCapacity(int value) {
        return Config.SERVER.refineryTowerCapacity.get();
    }

     @Inject(
        method = "<init>",
        at = @At("RETURN")
    )
    private void acceptAnyFluid(BlockPos position, BlockState state, CallbackInfo callback) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;

        fluidTank.setValidator(stack -> {
            Fluid fluid = stack.getFluid();
            
            if (blockEntity instanceof MediumDieselEngineBlockEntity || blockEntity instanceof SmallDieselEngineBlockEntity || blockEntity instanceof EngineCyllinderBlockEntity)
                return WariumAdditionsUtil.compareFluids(fluid, CrustyChunksModFluids.DIESEL.get());

            if (blockEntity instanceof MediumPetrolEngineBlockEntity || blockEntity instanceof SmallPetrolEngineBlockEntity || blockEntity instanceof FlameThrowerBlockEntity)
                return WariumAdditionsUtil.compareFluids(fluid, CrustyChunksModFluids.PETROLIUM.get());

            else if (blockEntity instanceof JetTurbineBlockEntity || blockEntity instanceof OilFireboxBlockEntity)
                return WariumAdditionsUtil.compareFluids(fluid, CrustyChunksModFluids.KEROSENE.get());

            else
                return true;
        });
    }
}
