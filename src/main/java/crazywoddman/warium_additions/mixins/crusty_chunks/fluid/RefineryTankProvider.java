package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.block.entity.RefineryBlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.WadditionsConfig;

/**
 * Adds Fluid Tank and Fluid Capability to Oil Refinrery so it could consume Crude Oil
 */
@Mixin(RefineryBlockEntity.class)
public class RefineryTankProvider {
    private FluidTank fluidTank;

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void wadditions$init(BlockPos position, BlockState state, CallbackInfo callback) {
        this.fluidTank = new FluidTank(WadditionsConfig.REFINERY_CAPACITY.get());
    }

    @Inject(
        method = "load",
        at = @At("TAIL")
    )
    private void wadditions$load(CompoundTag tag, CallbackInfo ci) {
        this.fluidTank.readFromNBT(tag.getCompound("fluidTank"));
    }

    @Inject(
        method = "saveAdditional",
        at = @At("TAIL")
    )
    private void wadditions$saveAdditional(CompoundTag tag, CallbackInfo ci) {
        tag.put("fluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
    }

    @Inject(
        method = "getCapability",
        at = @At("RETURN"),
        remap = false,
        cancellable = true
    )
    private void wadditions$getCability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.FLUID_HANDLER)
            cir.setReturnValue(LazyOptional.of(() -> this.fluidTank).cast());
    }
}
