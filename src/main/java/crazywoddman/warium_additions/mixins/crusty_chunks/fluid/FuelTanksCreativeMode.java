package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity;
import net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity;
import net.mcreator.crustychunks.block.entity.SmallPetrolEngineBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin(targets = {
    "net.mcreator.crustychunks.block.entity.FuelTankBlockEntity$1",
    "net.mcreator.crustychunks.block.entity.MediumDieselEngineBlockEntity$1",
    "net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity$1",
    "net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity$1",
    "net.mcreator.crustychunks.block.entity.SmallPetrolEngineBlockEntity$1",
    "net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity$1",
    "net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity$1",
})
public abstract class FuelTanksCreativeMode extends FluidTank {
    private FuelTanksCreativeMode(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }

    private BlockEntity blockEntity;

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void captureOwner(@Coerce BlockEntity blockEntity, int capacity, Predicate<?> validator, CallbackInfo ci) {
        this.blockEntity = blockEntity;
    }

    @Override
    public FluidStack getFluid() {
        if (blockEntity.getBlockState().getValue(WariumAdditionsUtil.CREATIVE_MODE)) {
            Fluid fluid;

            if (blockEntity instanceof MediumDieselEngineBlockEntity || blockEntity instanceof SmallDieselEngineBlockEntity || blockEntity instanceof EngineCyllinderBlockEntity)
                fluid = CrustyChunksModFluids.DIESEL.get();
            else if (blockEntity instanceof MediumPetrolEngineBlockEntity || blockEntity instanceof SmallPetrolEngineBlockEntity)
                fluid = CrustyChunksModFluids.PETROLIUM.get();
            else if (blockEntity instanceof JetTurbineBlockEntity)
                fluid = CrustyChunksModFluids.KEROSENE.get();
            else fluid = super.getFluid().getFluid();

            return new FluidStack(fluid, getCapacity());
        }

        return super.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return blockEntity.getBlockState().getValue(WariumAdditionsUtil.CREATIVE_MODE) ? getCapacity() : super.getFluidAmount();
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (blockEntity.getBlockState().getValue(WariumAdditionsUtil.CREATIVE_MODE)) {
            Fluid fluid;

            if (blockEntity instanceof MediumDieselEngineBlockEntity || blockEntity instanceof SmallDieselEngineBlockEntity || blockEntity instanceof EngineCyllinderBlockEntity)
                fluid = CrustyChunksModFluids.DIESEL.get();
            else if (blockEntity instanceof MediumPetrolEngineBlockEntity || blockEntity instanceof SmallPetrolEngineBlockEntity)
                fluid = CrustyChunksModFluids.PETROLIUM.get();
            else if (blockEntity instanceof JetTurbineBlockEntity)
                fluid = CrustyChunksModFluids.KEROSENE.get();
            else fluid = getFluid().getFluid();

            return new FluidStack(fluid, maxDrain);
        }

        return super.drain(maxDrain, action);
    }
}
