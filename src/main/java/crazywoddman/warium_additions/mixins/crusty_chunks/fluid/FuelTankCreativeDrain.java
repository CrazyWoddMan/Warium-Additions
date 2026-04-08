package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.block.entity.FuelTankBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin(targets = "net.mcreator.crustychunks.block.entity.FuelTankBlockEntity$1")
public abstract class FuelTankCreativeDrain extends FluidTank {
    private FuelTankCreativeDrain(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }

    private BlockEntity blockEntity;

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void captureOwner(FuelTankBlockEntity blockEntity, int capacity, Predicate<?> validator, CallbackInfo ci) {
        this.blockEntity = blockEntity;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return blockEntity.getBlockState().getValue(WariumAdditionsUtil.CREATIVE_MODE) ? new FluidStack(getFluid(), maxDrain) : super.drain(maxDrain, action);
    }
}
