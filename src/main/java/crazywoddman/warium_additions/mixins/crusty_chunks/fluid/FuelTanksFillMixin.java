package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin(
    targets = {
        "net.mcreator.crustychunks.block.entity.FuelTankBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.FuelTankModuleBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.FuelTankInputBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.LightCombustionEngineBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.SmallDieselEngineBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.MediumPetrolEngineBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.SmalPetrolEngineBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.EngineCyllinderBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.JetTurbineBlockEntity$1",
        "net.mcreator.crustychunks.block.entity.FlameThrowerBlockEntity$1"
    }
)
public abstract class FuelTanksFillMixin extends FluidTank {
    public FuelTanksFillMixin(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }
    @Override
    public int fill(FluidStack stack, FluidAction action) {
        Fluid fluid = this.fluid.getFluid();
      
        return super.fill(
            fluid != Fluids.EMPTY && WariumAdditionsUtil.compareFluids(stack.getFluid(), fluid)
            ? new FluidStack(fluid, stack.getAmount())
            : stack,
            action
        );
    }
}
