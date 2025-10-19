package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.mcreator.crustychunks.block.entity.BlockMinerBlockEntity;
import net.mcreator.crustychunks.block.entity.OilFireboxBlockEntity;
import net.mcreator.crustychunks.block.entity.RefineryTowerBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin({RefineryTowerBlockEntity.class, OilFireboxBlockEntity.class, BlockMinerBlockEntity.class})
public class UniqueTankBlockEntitiesMixin {

    @Unique
    private FluidTank fluidTank;
    
    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void injectFluidTank(BlockPos position, BlockState state, CallbackInfo ci) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        String type = ForgeRegistries.BLOCKS.getKey(blockEntity.getBlockState().getBlock()).getPath();
        this.fluidTank = new FluidTank(switch (type) {
            case "oil_firebox" -> Config.SERVER.oilFireboxCapacity.get();
            case "block_miner" -> Config.SERVER.blockMinerCapacity.get();
            default -> Config.SERVER.refineryTowerCapacity.get();
        }) {
            @Override
            protected void onContentsChanged() {
                super.onContentsChanged();
                blockEntity.getLevel().sendBlockUpdated(
                        blockEntity.getBlockPos(),
                        blockEntity.getBlockState(),
                        blockEntity.getBlockState(),
                        2
                    );
            }
            @Override
            public int fill(FluidStack stack, FluidAction action) {
                Fluid fluid = fluidTank.getFluid().getFluid();

                return super.fill(
                    fluid != Fluids.EMPTY && WariumAdditionsUtil.compareFluids(stack.getFluid(), fluid)
                    ? new FluidStack(fluid, stack.getAmount())
                    : stack,
                    action
                );
            }
        };

        this.fluidTank.setValidator(fluidStack -> {
            Fluid fluid = fluidStack.getFluid();
            return WariumAdditionsUtil.compareFluids(fluid, switch (type) {
                case "oil_firebox" -> CrustyChunksModFluids.KEROSENE.get();
                case "block_miner" -> CrustyChunksModFluids.DIESEL.get();
                default -> fluid;
            });
        });
    }

    @Inject(
        method = "load",
        at = @At("TAIL")
    )
    private void injectFluidRead(CompoundTag tag, CallbackInfo ci) {
        this.fluidTank.readFromNBT(tag.getCompound("fluidTank"));
    }

    @Inject(
        method = "saveAdditional",
        at = @At("TAIL")
    )
    private void injectFluidSave(CompoundTag tag, CallbackInfo ci) {
        tag.put("fluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    private void injectFluidCapability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.FLUID_HANDLER)
            cir.setReturnValue(LazyOptional.of(() -> this.fluidTank).cast());
    }
}
