package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.block.entity.FlameThrowerBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.mixins.crusty_chunks.BlocksCreativeMode;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

/**
 * <b>Creative Mode</b> handeling for the <b>Heavy Flame Thrower</b>
 * <p>In this mode Heavy Flame Thrower doesn't require fluid to fire
 * @see BlocksCreativeMode
 */
@Mixin(targets = "net.mcreator.crustychunks.block.entity.FlameThrowerBlockEntity$1")
public class HeavyFlameThrowerCreativeMode extends FluidTank {

    private HeavyFlameThrowerCreativeMode() {
        super(0);
    }

    private BlockEntity blockEntity;

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void captureOwner(FlameThrowerBlockEntity blockEntity, int capacity, Predicate<?> validator, CallbackInfo ci) {
        this.blockEntity = blockEntity;
    }

    @Override
    public int getFluidAmount() {
        return checkIfCreative() ? getCapacity() : super.getFluidAmount();
    }

    @Override
    public FluidStack getFluid() {
        FluidStack stack = super.getFluid();

        return checkIfCreative()
            ? new FluidStack(stack.isEmpty() ? CrustyChunksModFluids.PETROLIUM.get() : stack.getFluid(), getCapacity())
            : stack;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return new FluidStack(this.fluid, maxDrain);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return resource;
    }

    private boolean checkIfCreative() {
        return WariumAdditionsUtil.checkIfCreative(this.blockEntity.getBlockState());
    }
}
